package com.netcracker.jaxb.annotations;


import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value=RUNTIME)
public @interface JaxbElement {
    String name() default "";
}