package com.ifengxue.todolist.enums;

import com.ifengxue.base.rest.Error;

public enum ValidationError implements Error {
  INVALID_NAME("invalid name", "无效的名称"),
  INVALID_ID("invalid id", "无效的ID");
  private final String code;
  private final String value;

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
}
