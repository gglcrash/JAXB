package com.netcracker.jaxb.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Никита on 11.12.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlType {
    String[] propOrder();
    String name();
}
