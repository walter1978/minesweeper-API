package com.minesweeper.controller;

import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;
import com.minesweeper.service.MinesweeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MinesweeperController {
    @Autowired
    private MinesweeperService minesweeperService;

    @PostMapping("/board")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDTO newBoard(@RequestBody NewBoardRequestDTO newBoardRequestDTO) {
        return minesweeperService.createNewBoard(newBoardRequestDTO);
    }

    @GetMapping("/board/{boardId}")
    public BoardDTO getBoard(@PathVariable String boardId) {
        return minesweeperService.getBoard(boardId);
    }

    @PutMapping("/board-status/pause/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pauseGame(@PathVariable String boardId) {
        minesweeperService.pauseGame(boardId);
    }

    @PutMapping("/board-status/continue/{boardId}")
    public BoardDTO continueGame(@PathVariable String boardId) {
        return minesweeperService.continueGame(boardId);
    }

    @PutMapping("/cell")
    public BoardDTO updateCell(@RequestBody CellUpdateDTO cellUpdateDTO) {
        return minesweeperService.updateCell(cellUpdateDTO);
    }
}
