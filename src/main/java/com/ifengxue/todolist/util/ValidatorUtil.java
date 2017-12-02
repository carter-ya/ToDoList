package com.ifengxue.todolist.util;

import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.rest.ApiException;
import java.util.Set;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public final class ValidatorUtil {

  public static final String EMAIL_REGEX = "[.a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+";
  public static final int EMAIL_MIN_LENGTH = 5;
  public static final int EMAIL_MAX_LENGTH = 190;
  private static final javax.validation.Validator VALIDATOR =
      javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
  private static final Pattern PHONE_PATTERN = Pattern.compile("[1][0-9]{10}");
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

  /**
   * 验证对象
   *
   * @throws ConstraintViolationException 被验证对象包含错误则抛出该异常
   */
  public static void validate(Object target) throws ConstraintViolationException {
    Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(target);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }
  }

  public static boolean isPhone(String phone) {
    return phone != null && PHONE_PATTERN.matcher(phone).matches();
  }

  public static void validatePhone(String phone) {
    if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
      throw new ApiException(GatewayError.INVALID_PHONE);
    }
  }

  public static boolean isEmail(String email) {
    return email != null && (email.length() >= EMAIL_MIN_LENGTH
        && email.length() <= EMAIL_MAX_LENGTH)
        && EMAIL_PATTERN.matcher(email).matches();
  }

  public static void validateEmail(String email) {
    if (email == null || email.length() < EMAIL_MIN_LENGTH || email.length() > EMAIL_MAX_LENGTH
        || !EMAIL_PATTERN.matcher(email).matches()) {
      throw new ApiException(GatewayError.INVALID_EMAIL);
    }
  }
}