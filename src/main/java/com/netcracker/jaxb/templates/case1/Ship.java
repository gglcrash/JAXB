package com.netcracker.jaxb.templates.case1;

import com.netcracker.jaxb.annotations.Component;

@Component("Ship")
public class Ship {
    private int x;
    private int y;
    private String name;

    public int getX() {
        return x;
    }

    public Ship setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Ship setY(int y) {
        this.y = y;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ship setName(String name) {
        this.name = name;
        return this;
    }
}
