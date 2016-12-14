package com.netcracker.jaxb.managers;

public class EntityManager {

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

    protected void marshall(){
    }

    protected void unmarshall(){
    }
}
