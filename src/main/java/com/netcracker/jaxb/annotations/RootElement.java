package com.netcracker.jaxb.annotations;


import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value=RUNTIME)
public @interface RootElement {
    String name() default "";
}