package com.netcracker.jaxb.managers;

public class DbManager extends EntityManager{
    private String path;
    public DbManager(String str){
        path = str;
    }


    @Override
    public void marshall() {
        super.marshall();
    }

    @Override
    public void unmarshall() {
        super.unmarshall();
    }
}
