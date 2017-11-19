package com.ifengxue.todolist.web.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 验证是否是账号
 *
 * @author LiuKeFeng
 * @date 2017-10-12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, PARAMETER})
@Documented
@Constraint(validatedBy = AccountValidator.class)
public @interface Account {

  /**
   * 验证账号类型，账号可能是手机号，可能是邮箱
   */
  boolean phoneOrEmail() default true;

  /**
   * 验证账号类型，账号只可能是手机号
   */
  boolean phone() default false;

  /**
   * 验证账号类型，账号类型只可能是邮箱
   */
  boolean email() default false;

  /**
   * @see com.ifengxue.todolist.enums.ValidationError#INVALID_PHONE
   * @see com.ifengxue.todolist.enums.ValidationError#INVALID_EMAIL
   * @see com.ifengxue.todolist.enums.ValidationError#INVALID_ACCOUNT
   */
  String message() default "INVALID_ACCOUNT";

  /**
   * @return the groups the constraint belongs to
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * @return the payload associated to the constraint
   */
  Class<?>[] groups() default {};
}