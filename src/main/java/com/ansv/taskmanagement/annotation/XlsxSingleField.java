package com.ansv.taskmanagement.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface XlsxSingleField {
    int columnIndex();
    String name();
}
