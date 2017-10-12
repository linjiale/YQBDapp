package com.yqbd.yqbdapp.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface VoDataFields {
}
