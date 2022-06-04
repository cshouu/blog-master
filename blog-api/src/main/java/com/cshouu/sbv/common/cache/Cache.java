package com.cshouu.sbv.common.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    //时间
    long expire() default 1*60*1000;
    //名字
    String name() default "";
}
