package com.ifengxue.todolist.enums;

import com.ifengxue.todolist.rest.ApiException;
import java.util.Arrays;

public enum UserState {
  VALID(1, "valid"),
  FROZEN(2, "frozen");
  private final Integer code;
  private final String value;

  UserState(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public Integer getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public static UserState find(Integer code) {
    return Arrays.stream(values())
        .filter(userState -> userState.code.equals(code))
        .findAny()
        .orElseThrow(() -> new ApiException(GatewayError.BAD_ARGUMENT, code));
  }

  public static UserState find(String value) {
    return Arrays.stream(values())
        .filter(userState -> userState.value.equals(value))
        .findAny()
        .orElseThrow(() -> new ApiException(GatewayError.BAD_ARGUMENT, value));
  }
}
