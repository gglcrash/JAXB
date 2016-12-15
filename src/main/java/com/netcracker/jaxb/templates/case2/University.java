package com.netcracker.jaxb.templates.case2;

import com.netcracker.jaxb.annotations.Component;

@Component("University")
public class University {
    private String name;
    private Rector rector;

    public String getName() {
        return name;
    }

    public University setName(String name) {
        this.name = name;
        return this;
    }

    public Rector getRector() {
        return rector;
    }

    public University setRector(Rector rector) {
        this.rector = rector;
        return this;
    }
}
