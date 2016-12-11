package com.netcracker.jaxb.context;

import javax.xml.namespace.QName;

/**
 * Created by Никита on 11.12.2016.
 */
public class ParsableElement {
    Class clazz;
    QName qname;
    Object[] values;
    ParsableElement[] childs;
    public ParsableElement(Class clazz, Object[] values, ParsableElement[] childs){
        this.clazz = clazz;
        this.values = values;
        this.childs = childs;
    }
}
