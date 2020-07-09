package com.minesweeper.service;

import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.BoardStatusDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;

import java.util.List;

public interface MinesweeperService {
    /**
     * Creates a new board ready for play
     *
     * @param newBoardRequestDTO board parameters
     * @return new board
     */
    BoardDTO createNewBoard(NewBoardRequestDTO newBoardRequestDTO);

    /**
     * Returns a board for a given id
     *
     * @param boardId board id
     * @return board
     */
    BoardDTO getBoard(long boardId);

    /**
     * Returns all boards for a given user id
     *
     * @param userId
     * @return list of boards
     */
    List<BoardStatusDTO> getUserBoards(long userId);

    /**
     * Pauses a board
     *
     * @param boardId
     */
    void pauseBoard(long boardId);

    /**
     * Cancels a board
     *
     * @param boardId board id
     */
    void cancelBoard(long boardId);

    /**
     * Plays a previously paused board
     *
     * @param boardId board id
     * @return
     */
    BoardDTO playBoard(long boardId);

    /**
     * Changes the status of a cell
     * (The user has selected a cell to be revealed / flagged / marked)
     * The board is updated and returned
     *
     * @param cellUpdateDTO update data
     * @return updated board
     */
    BoardDTO updateCell(CellUpdateDTO cellUpdateDTO);
}
