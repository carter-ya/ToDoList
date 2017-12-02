package com.ifengxue.todolist.enums;

import com.ifengxue.todolist.rest.ApiException;
import java.util.Arrays;

public enum ProjectState {
  VALID(1, "valid"),
  DELETED(2, "deleted");
  private final Integer code;
  private final String value;

  ProjectState(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public Integer getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public static ProjectState find(Integer code) {
    return Arrays.stream(values())
        .filter(projectState -> projectState.code.equals(code))
        .findAny()
        .orElseThrow(() -> new ApiException(GatewayError.BAD_ARGUMENT, code));
  }

  public static ProjectState find(String value) {
    return Arrays.stream(values())
        .filter(projectState -> projectState.value.equals(value))
        .findAny()
        .orElseThrow(() -> new ApiException(GatewayError.BAD_ARGUMENT, value));
  }
}
