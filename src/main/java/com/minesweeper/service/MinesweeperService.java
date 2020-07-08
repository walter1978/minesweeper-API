package com.minesweeper.service;

import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;

public interface MinesweeperService {
    BoardDTO createNewBoard(NewBoardRequestDTO newBoardRequestDTO);

    BoardDTO getBoard(String boardId);

    void pauseGame(String boardId);

    BoardDTO continueGame(String boardId);

    BoardDTO updateCell(CellUpdateDTO cellUpdateDTO);
}
