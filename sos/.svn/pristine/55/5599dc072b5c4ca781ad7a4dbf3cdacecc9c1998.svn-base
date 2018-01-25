package com.gboss.util;

import java.lang.annotation.*;

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
	public int type();
	public int model_id();
	public String client() default "web";
}
