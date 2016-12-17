package com.netcracker.jaxb.templates.case3;

import com.netcracker.jaxb.annotations.Component;

@Component("Rectangle")
public class Rectangle extends AbstractFigure{
    int height;
    int weight;

    public int getHeight() {
        return height;
    }

    public Rectangle setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Rectangle setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
