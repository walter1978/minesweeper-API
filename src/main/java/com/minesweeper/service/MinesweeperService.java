package com.minesweeper.service;

import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.BoardStatusDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;

import java.util.List;

public interface MinesweeperService {
    BoardDTO createNewBoard(NewBoardRequestDTO newBoardRequestDTO);

    BoardDTO getBoard(long boardId);

    List<BoardStatusDTO> getUserBoards(long userId);

    void pause(long boardId);

    void cancel(long boardId);

    BoardDTO play(long boardId);

    BoardDTO updateCell(CellUpdateDTO cellUpdateDTO);
}
