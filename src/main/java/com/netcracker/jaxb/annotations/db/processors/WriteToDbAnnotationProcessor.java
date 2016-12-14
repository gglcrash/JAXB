package com.netcracker.jaxb.annotations.db.processors;

import com.netcracker.jaxb.ApplicationContext;
import com.netcracker.jaxb.annotations.Processor;
import com.netcracker.jaxb.annotations.db.WriteToDb;
import com.netcracker.jaxb.jdbc.MyConnection;
import com.netcracker.jaxb.templates.Ship;

import java.lang.reflect.*;
import java.util.HashMap;

public class WriteToDbAnnotationProcessor implements Processor {

    public WriteToDbAnnotationProcessor() {
        connection = MyConnection.getInstance();
    }

    MyConnection connection;

    public void checkAnnotation(Class<?> cl, Object instance) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException {

        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(WriteToDb.class)) {
                if (((HashMap) ApplicationContext.getInstance().getComponents()).containsValue(field.getType())) {

                    field.setAccessible(true);

                    if(field.getType().isAssignableFrom(Ship.class)) {
                        Ship tmpShip = (Ship)field.get(instance);
                        connection.insertShipIntoDb(tmpShip.getName(), tmpShip.getX(), tmpShip.getY());
                    }

                }
            }
        }
    }
}
