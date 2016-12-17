package com.netcracker.jaxb.templates.case3;

public abstract class AbstractFigure {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public AbstractFigure setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public AbstractFigure setY(int y) {
        this.y = y;
        return this;
    }
}
