package com.netcracker.jaxb.managers;

import com.netcracker.jaxb.ApplicationContext;
import com.netcracker.jaxb.annotations.RootElement;
import com.netcracker.jaxb.jdbc.MyConnection;
import com.netcracker.jaxb.templates.case1.Ship;
import com.netcracker.jaxb.templates.case2.University;
import com.netcracker.jaxb.templates.case3.AbstractFigure;
import com.netcracker.jaxb.templates.case3.Circle;
import com.netcracker.jaxb.templates.case3.Rectangle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbManager extends EntityManager{
    private String path;
    public DbManager(String str){
        path = str;
        connection = MyConnection.getInstance(str);
    }
    MyConnection connection;

    @Override
    public void marshall(Object clazz) {
        try {

            writeToDb(clazz.getClass(),clazz);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void writeToDb(Class<?> cl, Object instance) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException {

        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(RootElement.class)) {
                if (((HashMap) ApplicationContext.getInstance().getComponents()).containsValue(field.getType())) {

                    field.setAccessible(true);

                    if(field.getType().isAssignableFrom(Ship.class)) {
                        Ship tmpShip = (Ship)field.get(instance);
                        connection.insertShipIntoDb(tmpShip);
                    }

                    if(field.getType().isAssignableFrom(University.class)) {
                        University tmpUniv = (University)field.get(instance);
                        connection.insertUniversityIntoDb(tmpUniv);
                    }
                }

                if(field.getType().isAssignableFrom(List.class)){
                    field.setAccessible(true);
                    List tmpList = (List)field.get(instance);
                    for (Object elem : tmpList) {
                        if (elem.getClass().isAssignableFrom(Circle.class)) {
                            connection.insertCircleIntoDb((Circle) elem);
                        }
                        if (elem.getClass().isAssignableFrom(Rectangle.class)) {
                            connection.insertRectangleIntoDb((Rectangle) elem);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void unmarshall(Object clazz) {
        try {
            LoadFromDb(clazz.getClass(),clazz);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void LoadFromDb(Class<?> cl, Object instance) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException {
        Field[] fields = cl.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(RootElement.class)) {
                if (((HashMap) ApplicationContext.getInstance().getComponents()).containsValue(field.getType())) {

                    field.setAccessible(true);

                    if(field.getType().isAssignableFrom(Ship.class)) {
                        Ship tmpShip = connection.getShipFromDb(field.getAnnotation(RootElement.class).name());
                        field.set(instance,tmpShip);
                    }
                    if(field.getType().isAssignableFrom(University.class)) {
                        University tmpUniv = connection.getUniversityFromDb(field.getAnnotation(RootElement.class).name());
                        field.set(instance,tmpUniv);
                    }
                }
                if(field.getType().isAssignableFrom(List.class)){
                    ArrayList<AbstractFigure> tmpList = connection.getListAbstractFigureFromDb();
                    field.setAccessible(true);
                    field.set(instance,tmpList);
                }
            }
        }
    }
}
