package com.hm.bigdata.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于日志操作的自定义注解
 * @author tx
 * @version 1.0
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LogOperation {
	public String description();
	public int op_type();
	public int model_id();
	public String client() default "web";
}

