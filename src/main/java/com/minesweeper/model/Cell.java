package com.minesweeper.model;

import java.io.Serializable;

public class Cell implements Serializable {
    private boolean mine;
    private int minesAround;
    private boolean visible;
    private boolean redFlag;
    private boolean questionMark;

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isRedFlag() {
        return redFlag;
    }

    public void setRedFlag(boolean redFlag) {
        this.redFlag = redFlag;
    }

    public boolean isQuestionMark() {
        return questionMark;
    }

    public void setQuestionMark(boolean questionMark) {
        this.questionMark = questionMark;
    }
}
