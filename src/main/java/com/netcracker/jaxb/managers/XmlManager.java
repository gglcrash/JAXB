package com.netcracker.jaxb.managers;

public class XmlManager extends EntityManager{
    private String path;
    public XmlManager(String str){
        path = str;
    }


    @Override
    public void marshall() {
        super.marshall();
    }

    @Override
    protected void unmarshall() {
        super.unmarshall();
    }
}
