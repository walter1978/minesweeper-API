package com.minesweeper.dto;

import java.io.Serializable;
import java.time.Instant;

public class BoardDTO implements Serializable {
    private long boardId;
    private Instant startTime;
    private Instant endTime;
    private int totalTime;
    private Status status;
    private int rows;
    private int columns;
    private int mines;
    private int visibleCells;
    private CellDTO board[][];

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public int getVisibleCells() {
        return visibleCells;
    }

    public void setVisibleCells(int visibleCells) {
        this.visibleCells = visibleCells;
    }

    public CellDTO[][] getBoard() {
        return board;
    }

    public void setBoard(CellDTO[][] board) {
        this.board = board;
    }
}
