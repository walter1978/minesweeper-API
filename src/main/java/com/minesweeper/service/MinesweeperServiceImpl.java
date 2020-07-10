package com.minesweeper.service;

import com.minesweeper.dao.BoardDAO;
import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.BoardStatusDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;
import com.minesweeper.exception.BoardNotFoundException;
import com.minesweeper.exception.BoardNotPlayableException;
import com.minesweeper.mapper.MinesweeperModelMapper;
import com.minesweeper.model.Board;
import com.minesweeper.model.Cell;
import com.minesweeper.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MinesweeperServiceImpl implements MinesweeperService {
    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private MinesweeperModelMapper modelMapper;

    @Transactional
    @Override
    public BoardDTO createNewBoard(NewBoardRequestDTO newBoardRequestDTO) {
        final Instant currentTime = Instant.now();
        Board board = new Board();
        board.setUserId(newBoardRequestDTO.getUserId());
        board.setRows(newBoardRequestDTO.getRows());
        board.setColumns(newBoardRequestDTO.getColumns());
        board.setMines(newBoardRequestDTO.getMines());
        board.setStartTime(currentTime);
        board.setLastTimePlayed(currentTime);
        board.setStatus(Status.PLAYING);

        populateBoard(board);
        board = boardDAO.save(board);

        return modelMapper.mapToBoardDTO(board);
    }

    @Override
    public BoardDTO getBoard(long boardId) {
        final Board board = loadBoard(boardId);
        return modelMapper.mapToBoardDTO(board);
    }

    @Override
    public List<BoardStatusDTO> getUserBoards(long userId) {
        final List<Board> boards = boardDAO.findByUserId(userId);
        return modelMapper.mapToBoardStatusDTOs(boards);
    }

    @Transactional
    @Override
    public void pauseBoard(long boardId) {
        final Board board = loadBoard(boardId);
        board.setStatus(Status.PAUSED);
        board.setLastTimePlayed(Instant.now());
        board.updateTotalTime();

        boardDAO.save(board);
    }

    @Transactional
    @Override
    public void cancelBoard(long boardId) {
        final Board board = loadBoard(boardId);
        board.setStatus(Status.CANCELED);
        board.setLastTimePlayed(Instant.now());
        board.updateTotalTime();

        boardDAO.save(board);
    }

    @Transactional
    @Override
    public BoardDTO playBoard(long boardId) {
        final Board board = loadBoard(boardId);

        if (!Status.PAUSED.equals(board.getStatus())) {
            throw new BoardNotPlayableException();
        }

        board.setStatus(Status.PLAYING);
        board.setLastTimePlayed(Instant.now());

        boardDAO.save(board);

        return modelMapper.mapToBoardDTO(board);
    }

    @Transactional
    @Override
    public BoardDTO updateCell(CellUpdateDTO cellUpdateDTO) {
        final Board board = loadBoard(cellUpdateDTO.getBoardId());
        final Cell cell = board.getCell(cellUpdateDTO.getX(), cellUpdateDTO.getY());

        switch (cellUpdateDTO.getAction()) {
            case REVEAL:
                if (cell.isMine()) {
                    // mine selected - game over
                   board.gameOver();
                } else {
                    if (board.allCellsRevealed()) {
                        // all cells without a mine are visible - user wins / game over
                        board.userWins();
                        board.gameOver();
                    } else {
                        // reveal subyacent cells
                        revealCells(board, cellUpdateDTO.getX(), cellUpdateDTO.getY());
                    }
                }
                break;
            case RED_FLAG:
                cell.setRedFlag(true);
                break;
            case QUESTION_MARK:
                cell.setQuestionMark(true);
                break;
            default:
                throw new RuntimeException(String.format("Action not supported: %s", cellUpdateDTO.getAction()));
        }

        boardDAO.save(board);

        return modelMapper.mapToBoardDTO(board);
    }

    private Board loadBoard(long boardId) {
        final Optional<Board> boardOpt = boardDAO.findById(boardId);
        if (!boardOpt.isPresent()) {
            throw new BoardNotFoundException();
        }

        return boardOpt.get();
    }


    private void populateBoard(Board board)  {
        // initializes cells
        final Cell[][] cells = new Cell[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                cells[i][j] = new Cell();
            }
        }

        // generates mines1234

        for (int i = 0; i < board.getMines(); i++) {
            int x;
            int y;
            do {
                x = (int) (Math.random() * board.getRows());
                y = (int) (Math.random() * board.getColumns());
            } while (cells[x][y].isMine());

            // adds mine
            cells[x][y].setMine(true);

            // updates cells around
            for (int x2 = Math.max(0, x - 1); x2 < Math.min(board.getRows(), x + 2); x2++) {
                for (int y2 = Math.max(0, y - 1); y2 < Math.min(board.getColumns(), y + 2); y2++) {
                    if (!cells[x2][y2].isMine()) {
                        cells[x2][y2].setMinesAround(cells[x2][y2].getMinesAround() + 1);
                    }
                }
            }
        }

        board.setCells(cells);
    }

    private void revealCells(Board board, int x, int y)  {
        for (int x2 = Math.max(0, x - 1); x2 < Math.min(board.getRows(), x + 2); x2++) {
            for (int y2 = Math.max(0, y - 1); y2 < Math.min(board.getColumns(), y + 2); y2++) {
                final Cell cell = board.getCell(x2, y2);
                if (!cell.isVisible()) {
                    board.getCell(x2, y2).setVisible(true);
                    board.increaseVisibleCells();
                    if (board.getCell(x2, y2).getMinesAround() == 0) {
                        revealCells(board, x2, y2);
                    }
                }
            }
        }
    }
}
