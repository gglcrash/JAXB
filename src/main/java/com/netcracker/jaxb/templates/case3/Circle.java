package com.netcracker.jaxb.templates.case3;

import com.netcracker.jaxb.annotations.Component;

@Component("Circle")
public class Circle extends AbstractFigure{
    int radius;

    public int getRadius() {
        return radius;
    }

    public Circle setRadius(int radius) {
        this.radius = radius;
        return this;
    }
}
