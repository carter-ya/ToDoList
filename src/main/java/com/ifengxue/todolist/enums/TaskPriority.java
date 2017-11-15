package com.ifengxue.todolist.enums;

import com.ifengxue.base.rest.ApiException;
import java.util.Arrays;

public enum TaskPriority {
  LEVEL_ONE(1, "level 1"),
  LEVEL_TWO(2, "level 2"),
  LEVEL_THREE(3, "level 3"),
  LEVEL_FOUR(4, "level 4"),
  LEVEL_FIVE(5, "level 5"),
  LEVEL_SIX(6, "level 6"),
  LEVEL_SEVEN(7, "level 7"),
  LEVEL_EIGHT(8, "level 8"),
  LEVEL_NINE(9, "level 9"),
  LEVEL_TEN(10, "level 10");
  private final Integer code;
  private final String value;

  TaskPriority(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public Integer getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public static TaskPriority find(Integer code) {
    return Arrays.stream(values())
        .filter(taskPriority -> taskPriority.code.equals(code))
        .findAny()
        .orElseThrow(() -> new ApiException(GatewayError.BAD_ARGUMENT, code));
  }

  public static TaskPriority find(String value) {
    return Arrays.stream(values())
        .filter(taskPriority -> taskPriority.value.equals(value))
        .findAny()
        .orElseThrow(() -> new ApiException(GatewayError.BAD_ARGUMENT, value));
  }
}
