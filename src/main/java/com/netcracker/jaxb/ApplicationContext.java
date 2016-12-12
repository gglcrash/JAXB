package com.netcracker.jaxb;

import com.netcracker.jaxb.annotations.Component;
import com.netcracker.jaxb.annotations.db.processors.WriteToDbAnnotationProcessor;
import com.netcracker.jaxb.annotations.db.processors.Processor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Map<String,Class<?>> components;
    private static ApplicationContext instance;
    private ArrayList<Processor> analyzers = new ArrayList<Processor>();
    private ApplicationContext() {
        components =  ClassFinder.getClassesFromPackage(new File("target\\"),"", Component.class);
        analyzers.add(new WriteToDbAnnotationProcessor());
    }

    public static ApplicationContext getInstance() {
        if(instance==null){
            instance = new ApplicationContext();
        }
        return instance;
    }

    public void checkFields(Object clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {

        for (Processor analyzer : analyzers) {
            analyzer.checkAnnotation(clazz.getClass(), clazz);
        }
    }

    public Map<String,Class<?>> getComponents() {
        return (Map<String,Class<?>>)((HashMap<String,Class<?>>)components).clone();
    }
}
