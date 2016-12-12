package com.netcracker.jaxb.annotations.db.processors;

import com.netcracker.jaxb.ApplicationContext;
import com.netcracker.jaxb.annotations.db.LoadFromDb;
import com.netcracker.jaxb.annotations.db.WriteToDb;
import com.netcracker.jaxb.annotations.db.processors.Processor;
import com.netcracker.jaxb.jdbc.MyConnection;
import com.netcracker.jaxb.templates.Ship;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class LoadFromDbAnnotationProcessor implements Processor {

    public LoadFromDbAnnotationProcessor(){
        connection = MyConnection.getConnection();
    }

    MyConnection connection;

    public void checkAnnotation(Class<?> cl, Object instance) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException {
        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(LoadFromDb.class)) {
                if (((HashMap) ApplicationContext.getInstance().getComponents()).containsValue(field.getType())) {

                    field.setAccessible(true);

                    if(field.getType().isAssignableFrom(Ship.class)) {
                        Ship tmpShip = connection.getShipFromDb(field.getAnnotation(LoadFromDb.class).name());
                        field.set(instance,tmpShip);
                    }

                }
            }
        }
    }
}
