package com.minesweeper.service;

import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class MinesweeperServiceImpl implements MinesweeperService {
    @Override
    public BoardDTO createNewBoard(NewBoardRequestDTO newBoardRequestDTO) {
        return new BoardDTO();
    }

    @Override
    public BoardDTO getBoard(String boardId) {
        return new BoardDTO();
    }

    @Override
    public void pauseGame(String boardId) {

    }

    @Override
    public BoardDTO continueGame(String boardId) {
        return new BoardDTO();
    }

    @Override
    public BoardDTO updateCell(CellUpdateDTO cellUpdateDTO) {
        return new BoardDTO();
    }
}
