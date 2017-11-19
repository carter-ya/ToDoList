package com.ifengxue.todolist.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置该方法不需要授权检查
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreAuth {

  /**
   * true:忽略授权;false:检查授权
   */
  boolean ignore() default true;
}