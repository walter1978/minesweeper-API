package com.minesweeper.model;

import com.minesweeper.converter.CellsConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "boards", indexes = {@Index(name = "user",  columnList="userId", unique = false)})
public class Board implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;
    private long userId;
    private Instant startTime;
    private Instant endTime;
    private Instant lastTimePlayed;
    private long totalTime;
    private Status status;
    private boolean completed;
    private int rows;
    private int columns;
    private int mines;
    private int visibleCells;
    @Lob
    @Convert(converter = CellsConverter.class)
    private Cell cells[][];

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public Instant getLastTimePlayed() {
        return lastTimePlayed;
    }

    public void setLastTimePlayed(Instant lastTimePlayed) {
        this.lastTimePlayed = lastTimePlayed;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void updateTotalTime() {
        totalTime = totalTime + ((Instant.now().toEpochMilli() - getLastTimePlayed().toEpochMilli()) / 1000);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return boardId == board.boardId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId);
    }
}
