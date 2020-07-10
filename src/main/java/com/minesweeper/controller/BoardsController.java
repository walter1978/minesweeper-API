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
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/boards")
public class BoardsController {
    @Autowired
    private MinesweeperService minesweeperService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDTO newBoard(@RequestBody NewBoardRequestDTO newBoardRequestDTO) {
        return minesweeperService.createNewBoard(newBoardRequestDTO);
    }

    @GetMapping("/{boardId}")
    public BoardDTO getBoard(@PathVariable long boardId) {
        return minesweeperService.getBoard(boardId);
    }

    @GetMapping("/user/{userId}")
    public List<BoardStatusDTO> getUserBoards(@PathVariable long userId) {
        return minesweeperService.getUserBoards(userId);
    }

    @PutMapping("/status/paused/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pauseGame(@PathVariable long boardId) {
        minesweeperService.pauseBoard(boardId);
    }

    @PutMapping("/status/canceled/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelGame(@PathVariable long boardId) {
        minesweeperService.cancelBoard(boardId);
    }

    @PutMapping("/status/playing/{boardId}")
    public BoardDTO continueGame(@PathVariable long boardId) {
        return minesweeperService.playBoard(boardId);
    }

    @PutMapping("/cell")
    public BoardDTO updateCell(@RequestBody CellUpdateDTO cellUpdateDTO) {
        return minesweeperService.updateCell(cellUpdateDTO);
    }
}
