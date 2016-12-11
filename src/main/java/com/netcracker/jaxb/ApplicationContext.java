package com.netcracker.jaxb;

import com.netcracker.jaxb.annotations.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Map<String,Class<?>> components;
    private static ApplicationContext instance;
    private ApplicationContext() {
        components =  ClassFinder.getClassesFromPackage(new File("out\\production\\jaxbdb\\"),"", Component.class);
    }

    public static ApplicationContext getInstance() {
        if(instance==null){
            instance = new ApplicationContext();
        }
        return instance;
    }

    public Map<String,Class<?>> getComponents() {
        return (Map<String,Class<?>>)((HashMap<String,Class<?>>)components).clone();
    }
}
