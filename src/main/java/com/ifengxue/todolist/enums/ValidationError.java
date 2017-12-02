package com.ifengxue.todolist.enums;

import static com.ifengxue.todolist.web.request.RegisterUserRequest.NICKNAME_MAX_LENGTH;
import static com.ifengxue.todolist.web.request.RegisterUserRequest.NICKNAME_MIN_LENGTH;
import static com.ifengxue.todolist.web.request.RegisterUserRequest.PASSWORD_MAX_LENGTH;
import static com.ifengxue.todolist.web.request.RegisterUserRequest.PASSWORD_MIN_LENGTH;

import com.ifengxue.todolist.rest.ApiException;
import com.ifengxue.todolist.rest.Error;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ValidationError implements Error {
  INVALID_NAME("invalid name", "无效的名称"),
  INVALID_ID("invalid id", "无效的ID"),
  INVALID_DATE("invalid date", "无效的日期"),
  INVALID_PHONE("invalid phone", "无效的手机号"),
  INVALID_EMAIL("invalid email", "无效的游戏"),
  INVALID_ACCOUNT("invalid account", "无效的账号"),
  INVALID_PASSWORD("invalid password",
      "密码长度必须在" + PASSWORD_MIN_LENGTH + "-" + PASSWORD_MAX_LENGTH + "之间"),
  INVALID_NICKNAME("invalid nickname",
      "昵称长度必须在" + NICKNAME_MIN_LENGTH + "-" + NICKNAME_MAX_LENGTH + "之间"),
  ILLEGAL_NICKNAME("illegal nickname", "昵称中包含非法字符");
  private final String code;
  private final String value;
  private static final Map<String, ValidationError> NAME_KEYED_MAP = new HashMap<>();

  static {
    try {
      for (Field field : ValidationError.class.getFields()) {
        if (field.getType() == ValidationError.class) {
          NAME_KEYED_MAP.put(field.getName(), (ValidationError) field.get(ValidationError.class));
        }
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  ValidationError(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String getErrorCode() {
    return getCode();
  }

  @Override
  public String getErrorMessage() {
    return getValue();
  }

  public static ValidationError find(String name) {
    return Optional.ofNullable(NAME_KEYED_MAP.get(name))
        .orElseThrow(() -> new ApiException(GatewayError.INTERNAL_ERROR));
  }
}
