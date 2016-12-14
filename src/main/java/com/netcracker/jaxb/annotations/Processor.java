package com.netcracker.jaxb.annotations;

import java.lang.reflect.InvocationTargetException;

public interface Processor {
    void checkAnnotation(Class<?> cl, Object instance) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException;

}
