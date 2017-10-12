package com.yqbd.yqbdapp.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
public @interface ExecutionMode {
    ExecutionThreadMode value() default ExecutionThreadMode.ASYNC;
}
