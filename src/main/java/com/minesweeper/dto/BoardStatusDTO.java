package com.minesweeper.dto;

import java.io.Serializable;
import java.time.Instant;

public class BoardStatusDTO implements Serializable {
    private long boardId;
    private long userId;
    private Instant startTime;
    private Instant endTime;
    private Instant lastTimePlayed;
    private long totalTime;
    private Status status;
    private boolean completed;

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
}
