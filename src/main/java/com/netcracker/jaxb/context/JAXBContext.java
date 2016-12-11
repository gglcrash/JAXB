package com.netcracker.jaxb.context;

import com.netcracker.jaxb.annotations.XMLRootElement;
import com.netcracker.jaxb.annotations.XmlType;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Никита on 11.12.2016.
 */
public class JAXBContext {
    ParsableElement root;

    public JAXBContext(Class clazz){
        createTree(clazz);
    }

    private void createTree(Class clazz){

        if(clazz.isAnnotationPresent(XMLRootElement.class)){
            xmlTypeParsing(clazz);
        }
        else
            throw new NotImplementedException();
    }

    private void xmlTypeParsing(Class clazz){
        if(clazz.isAnnotationPresent(XmlType.class)){
            int i = 0;
        }
    }
}
