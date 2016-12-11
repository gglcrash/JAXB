package com.netcracker.jaxb.templates;

import com.netcracker.jaxb.annotations.Component;

@Component("ClassA")
public class ClassA {
    private int x;
    private int y;
    private String name;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
