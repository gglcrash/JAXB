package com.netcracker.jaxb.managers;

public class EntityManager {

    protected EntityManager(){}

    public static EntityManager getInstance(String path, ConnectionType type){
        switch (type){
            case DB: {
                return new DbManager(path);
            }
            case XML: {
                return new XmlManager(path);
            }
            default:
                return null;
        }
    }

    public void marshall(Object clazz){
    }

    public void unmarshall(Object clazz){
    }

}
