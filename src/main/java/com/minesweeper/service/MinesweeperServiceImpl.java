package com.minesweeper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minesweeper.dao.BoardDAO;
import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.BoardStatusDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;
import com.minesweeper.exception.BoardNotFoundException;
import com.minesweeper.mapper.MinesweeperModelMapper;
import com.minesweeper.model.Board;
import com.minesweeper.model.Cell;
import com.minesweeper.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MinesweeperServiceImpl implements MinesweeperService {
    @Autowired
    private BoardDAO boardDAO;
    @Autowired
    private MinesweeperModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

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

    @Override
    public void pause(long boardId) {
        final Board board = loadBoard(boardId);
        board.setStatus(Status.PAUSED);
        board.updateTotalTime();

        boardDAO.save(board);
    }

    @Override
    public void cancel(long boardId) {
        final Board board = loadBoard(boardId);
        board.setStatus(Status.CANCELED);
        board.updateTotalTime();

        boardDAO.save(board);
    }

    @Override
    public BoardDTO play(long boardId) {
        final Board board = loadBoard(boardId);
        board.setStatus(Status.PLAYING);
        board.setLastTimePlayed(Instant.now());

        boardDAO.save(board);

        return modelMapper.mapToBoardDTO(board);
    }

    @Override
    public BoardDTO updateCell(CellUpdateDTO cellUpdateDTO) {
        final Board board = loadBoard(cellUpdateDTO.getBoardId());
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
        final Cell[][] cells = new Cell[board.getRows()][board.getColumns()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                cells[i][j] = new Cell();
            }
        }

        board.setCells(cells);
    }
}
