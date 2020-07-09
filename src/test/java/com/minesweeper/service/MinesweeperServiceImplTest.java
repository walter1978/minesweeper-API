package com.minesweeper.service;

import com.minesweeper.dao.BoardDAO;
import com.minesweeper.dto.Action;
import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;
import com.minesweeper.mapper.MinesweeperModelMapper;
import com.minesweeper.model.Board;
import com.minesweeper.model.Cell;
import com.minesweeper.model.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class MinesweeperServiceImplTest {
    private static int ROWS = 10;
    private static int COLUMNS = 10;
    private static String MINE = "*";
    private static String EMPTY_CELL = "_";
    private static String VISIBLE_CELL = "+";
    private static String NOT_VISIBLE_CELL = "_";
    // * = mine, _ = empty cell, 1-8 mines around
    private static String BOARD =
            "111_______" +
            "1*1_______" +
            "111_______" +
            "__________" +
            "__________" +
            "__________" +
            "_111______" +
            "_1*1______" +
            "_111______" +
            "__________";
    // _ = not visible cell, + = visible cell
    private static String EXPECTED_VISIBILITY =
            "__++++++++" +
            "__++++++++" +
            "++++++++++" +
            "++++++++++" +
            "++++++++++" +
            "++++++++++" +
            "++++++++++" +
            "++_+++++++" +
            "++++++++++" +
            "++++++++++";
    @Mock
    private BoardDAO boardDAO;
    @Mock
    private MinesweeperModelMapper modelMapper;
    @InjectMocks
    private MinesweeperService subject = new MinesweeperServiceImpl();
    private BoardDTO boardDTO = new BoardDTO();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(modelMapper.mapToBoardDTO(any())).thenReturn(boardDTO);
    }

    @After
    public void cleanup() {
        reset(boardDAO, modelMapper);
    }

    @Test
    public void createBoardTest() {
        final NewBoardRequestDTO newBoardRequestDTO = new NewBoardRequestDTO();
        newBoardRequestDTO.setUserId(1);
        newBoardRequestDTO.setRows(ROWS);
        newBoardRequestDTO.setColumns(COLUMNS);
        newBoardRequestDTO.setMines(10);

        final BoardDTO boardDTO = subject.createNewBoard(newBoardRequestDTO);

        assertEquals(this.boardDTO, boardDTO);

        final ArgumentCaptor<Board> boardArgumentCaptor = ArgumentCaptor.forClass(Board.class);
        verify(boardDAO).save(boardArgumentCaptor.capture());
        final Board savedBoard = boardArgumentCaptor.getValue();
        assertEquals(newBoardRequestDTO.getUserId(), savedBoard.getUserId());
        assertEquals(newBoardRequestDTO.getRows(), savedBoard.getRows());
        assertEquals(newBoardRequestDTO.getColumns(), savedBoard.getColumns());
        assertEquals(newBoardRequestDTO.getMines(), savedBoard.getMines());
        assertEquals( 0, savedBoard.getVisibleCells());
        assertEquals(Status.PLAYING, savedBoard.getStatus());
        assertNotNull(savedBoard.getStartTime());
        assertNotNull(savedBoard.getLastTimePlayed());
        assertNotNull(savedBoard.getCells());
        assertFalse(savedBoard.isCompleted());
    }

    @Test
    public void pauseBoardTest() {
        final Instant boardStartTime = Instant.now().minus(Duration.ofDays(1));
        final Board board = new Board();
        board.setBoardId(1);
        board.setStartTime(boardStartTime);
        board.setLastTimePlayed(boardStartTime);
        when(boardDAO.findById(board.getBoardId())).thenReturn(Optional.of(board));

        subject.pauseBoard(board.getBoardId());

        final ArgumentCaptor<Board> boardArgumentCaptor = ArgumentCaptor.forClass(Board.class);
        verify(boardDAO).save(boardArgumentCaptor.capture());
        final Board savedBoard = boardArgumentCaptor.getValue();
        assertEquals(savedBoard.getStatus(), com.minesweeper.model.Status.PAUSED);
        assertEquals(savedBoard.getStartTime(), boardStartTime);
        assertTrue(savedBoard.getLastTimePlayed().toEpochMilli() > boardStartTime.toEpochMilli());
    }

    @Test
    public void playBoardTest() {
        final Instant boardStartTime = Instant.now().minus(Duration.ofDays(1));
        final Board board = new Board();
        board.setBoardId(1);
        board.setStartTime(boardStartTime);
        board.setLastTimePlayed(boardStartTime);
        board.setStatus(Status.PAUSED);
        when(boardDAO.findById(board.getBoardId())).thenReturn(Optional.of(board));

        subject.playBoard(board.getBoardId());

        final ArgumentCaptor<Board> boardArgumentCaptor = ArgumentCaptor.forClass(Board.class);
        verify(boardDAO).save(boardArgumentCaptor.capture());
        final Board savedBoard = boardArgumentCaptor.getValue();
        assertEquals(savedBoard.getStatus(), Status.PLAYING);
        assertEquals(savedBoard.getStartTime(), boardStartTime);
        assertNotNull(savedBoard.getLastTimePlayed());
    }

    @Test
    public void revealTest() {
        final long boardId = 1;
        final Board board = buildBoard(boardId, BOARD);
        when(boardDAO.findById(boardId)).thenReturn(Optional.of(board));

        final String[][] expectedVisibilityMatrix = buildMatrixWithExpectedVisibility(EXPECTED_VISIBILITY);

        final CellUpdateDTO cellUpdateDTO = new CellUpdateDTO();
        cellUpdateDTO.setBoardId(boardId);
        cellUpdateDTO.setX(3);
        cellUpdateDTO.setY(4);
        cellUpdateDTO.setAction(Action.REVEAL);

        subject.updateCell(cellUpdateDTO);

        // verifies visible cells after the update
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                final Cell cell = board.getCell(i, j);
                if (expectedVisibilityMatrix[i][j].equals(VISIBLE_CELL)) {
                    assertTrue(cell.isVisible());
                } else if (expectedVisibilityMatrix[i][j].equals(NOT_VISIBLE_CELL)) {
                    assertFalse(cell.isVisible());
                }
            }
        }

        assertFalse(board.isCompleted());
        assertEquals(board.getStatus(), com.minesweeper.model.Status.PLAYING);
        assertEquals(95, board.getVisibleCells());

        verify(boardDAO).save(eq(board));
    }

    @Test
    public void redFlagTest() {
        final long boardId = 1;
        final Board board = buildBoard(boardId, BOARD);
        when(boardDAO.findById(boardId)).thenReturn(Optional.of(board));

        final CellUpdateDTO cellUpdateDTO = new CellUpdateDTO();
        cellUpdateDTO.setBoardId(board.getBoardId());
        cellUpdateDTO.setX(4);
        cellUpdateDTO.setY(4);
        cellUpdateDTO.setAction(Action.RED_FLAG);

        subject.updateCell(cellUpdateDTO);

        final ArgumentCaptor<Board> boardArgumentCaptor = ArgumentCaptor.forClass(Board.class);
        verify(boardDAO).save(boardArgumentCaptor.capture());
        final Board savedBoard = boardArgumentCaptor.getValue();
        assertTrue(savedBoard.getCell(cellUpdateDTO.getX(), cellUpdateDTO.getY()).isRedFlag());
    }

    @Test
    public void questionMarkTest() {
        final long boardId = 1;
        final Board board = buildBoard(boardId, BOARD);
        when(boardDAO.findById(boardId)).thenReturn(Optional.of(board));

        final CellUpdateDTO cellUpdateDTO = new CellUpdateDTO();
        cellUpdateDTO.setBoardId(board.getBoardId());
        cellUpdateDTO.setX(4);
        cellUpdateDTO.setY(4);
        cellUpdateDTO.setAction(Action.QUESTION_MARK);

        subject.updateCell(cellUpdateDTO);

        final ArgumentCaptor<Board> boardArgumentCaptor = ArgumentCaptor.forClass(Board.class);
        verify(boardDAO).save(boardArgumentCaptor.capture());
        final Board savedBoard = boardArgumentCaptor.getValue();
        assertTrue(savedBoard.getCell(cellUpdateDTO.getX(), cellUpdateDTO.getY()).isQuestionMark());
    }

    private Board buildBoard(long boardId, String boardMatrix) {
        final Board board = new Board();
        board.setBoardId(boardId);
        board.setUserId(1);
        board.setRows(ROWS);
        board.setColumns(COLUMNS);
        board.setMines(2);
        board.setStatus(com.minesweeper.model.Status.PLAYING);

        Cell[][] cells = new Cell[ROWS][COLUMNS];
        int row = 0;
        int column;
        for (String rowData : boardMatrix.split("(?<=\\G.{" + COLUMNS + "})")) {
            column = 0;
            for (String cellData : rowData.split("(?<=\\G.)")) {
                final Cell cell = new Cell();
                if (cellData.equals(MINE)) {
                    cell.setMine(true);
                } else if (!cellData.equals(EMPTY_CELL)) {
                    cell.setMinesAround(Integer.valueOf(cellData));
                }
                cells[row][column] = cell;
                column++;
            }
            row++;
        }
        board.setCells(cells);

        return board;
    }

    private String[][] buildMatrixWithExpectedVisibility(String expectedVisibilityMatrix) {
        String[][] matrix = new String[ROWS][COLUMNS];
        int row = 0;
        int column;
        for (String rowData : expectedVisibilityMatrix.split("(?<=\\G.{" + COLUMNS + "})")) {
            column = 0;
            for (String cellData : rowData.split("(?<=\\G.)")) {
                matrix[row][column] = cellData;
                column++;
            }
            row++;
        }

        return matrix;
    }
}
