package com.minesweeper.controller;

import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.BoardStatusDTO;
import com.minesweeper.dto.CellUpdateDTO;
import com.minesweeper.dto.NewBoardRequestDTO;
import com.minesweeper.service.MinesweeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MinesweeperController {
    @Autowired
    private MinesweeperService minesweeperService;

    @PostMapping("/boards")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDTO newBoard(@RequestBody NewBoardRequestDTO newBoardRequestDTO) {
        return minesweeperService.createNewBoard(newBoardRequestDTO);
    }

    @GetMapping("/boards/{boardId}")
    public BoardDTO getBoard(@PathVariable long boardId) {
        return minesweeperService.getBoard(boardId);
    }

    @GetMapping("/boards/user/{userId}")
    public List<BoardStatusDTO> getUserBoard(@PathVariable long userId) {
        return minesweeperService.getUserBoards(userId);
    }

    @PutMapping("/boards/status/paused/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pauseGame(@PathVariable long boardId) {
        minesweeperService.pause(boardId);
    }

    @PutMapping("/boards/status/canceled/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelGame(@PathVariable long boardId) {
        minesweeperService.cancel(boardId);
    }

    @PutMapping("/boards/status/playing/{boardId}")
    public BoardDTO continueGame(@PathVariable long boardId) {
        return minesweeperService.play(boardId);
    }

    @PutMapping("/boards/cell")
    public BoardDTO updateCell(@RequestBody CellUpdateDTO cellUpdateDTO) {
        return minesweeperService.updateCell(cellUpdateDTO);
    }
}
