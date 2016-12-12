package com.netcracker.jaxb.annotations.db;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value=RUNTIME)
public @interface LoadFromDb {
    String name();
}