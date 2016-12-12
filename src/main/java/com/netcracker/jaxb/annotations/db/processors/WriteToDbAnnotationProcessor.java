package com.netcracker.jaxb.annotations.db.processors;

import com.netcracker.jaxb.ApplicationContext;
import com.netcracker.jaxb.annotations.db.WriteToDb;
import com.netcracker.jaxb.jdbc.MyConnection;
import com.netcracker.jaxb.templates.ClassA;

import java.lang.reflect.*;
import java.util.HashMap;

public class WriteToDbAnnotationProcessor implements Processor {

    public WriteToDbAnnotationProcessor() {
        connection = MyConnection.getConnection();
    }

    MyConnection connection;

    public void checkAnnotation(Class<?> cl, Object instance) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException {

        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(WriteToDb.class)) {
                if (((HashMap) ApplicationContext.getInstance().getComponents()).containsValue(field.getType())) {
                    int modifiers = field.getModifiers();
                    field.setAccessible(true);

                    if(field.getType().isAssignableFrom(ClassA.class)) {
                        ClassA tmpClassA = (ClassA)field.get(instance);
                        connection.insertIntoDb(tmpClassA.getX(),tmpClassA.getY(),tmpClassA.getName());
                    }

                }
            }
        }
    }
}
