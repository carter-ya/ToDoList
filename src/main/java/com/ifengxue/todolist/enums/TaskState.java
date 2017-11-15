package com.ifengxue.todolist.enums;

import com.ifengxue.base.rest.ApiException;
import java.util.Arrays;

public enum TaskState {
  NEW(1, "new"),
  PROGRESS(2, "progress"),
  FINISHED(3, "finished"),
  DELETED(4, "deleted");
  private final Integer code;
  private final String value;

  TaskState(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  public Integer getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public static TaskState find(Integer code) {
    return Arrays.stream(values())
        .filter(taskState -> taskState.code.equals(code))
        .findAny()
        .orElseThrow(() -> new ApiException(GatewayError.BAD_ARGUMENT, code));
  }

  public static TaskState find(String value) {
    return Arrays.stream(values())
        .filter(taskState -> taskState.value.equals(value))
        .findAny()
        .orElseThrow(() -> new ApiException(GatewayError.BAD_ARGUMENT, value));
  }
}
