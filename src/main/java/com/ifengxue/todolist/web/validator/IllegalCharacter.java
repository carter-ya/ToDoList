package com.ifengxue.todolist.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 非法关键词验证
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = IllegalCharacterValidator.class)
public @interface IllegalCharacter {

  /**
   * 要验证的非法关键词
   */
  char[] characters() default {'%', '\'', '+', ';', '<', '>', '"', '#', '$', '*', '|', '/', ':',
      '='};

  /**
   * 错误信息
   */
  String message() default "ILLEGAL_CHARACTER";

  /**
   * @return the groups the constraint belongs to
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * @return the payload associated to the constraint
   */
  Class<?>[] groups() default {};
}