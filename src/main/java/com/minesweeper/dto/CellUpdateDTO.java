package com.minesweeper.dto;

import java.io.Serializable;
import java.time.Instant;

public abstract class CellUpdateDTO implements Serializable {
    private long boardId;
    private String xPos;
    private Instant yPos;
    private Action action;

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public String getxPos() {
        return xPos;
    }

    public void setxPos(String xPos) {
        this.xPos = xPos;
    }

    public Instant getyPos() {
        return yPos;
    }

    public void setyPos(Instant yPos) {
        this.yPos = yPos;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
