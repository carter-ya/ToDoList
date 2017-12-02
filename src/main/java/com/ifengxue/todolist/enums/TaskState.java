package com.ifengxue.todolist.enums;

import com.ifengxue.todolist.rest.ApiException;
import java.util.Arrays;

public enum TaskState {
  NEW(1, "new", "新建"),
  PROGRESS(2, "progress", "进行中"),
  FINISHED(3, "finished", "已完成"),
  DELETED(4, "deleted", "已删除");
  private final Integer code;
  private final String value;
  private final String cnValue;

  TaskState(Integer code, String value, String cnValue) {
    this.code = code;
    this.value = value;
    this.cnValue = cnValue;
  }

  public Integer getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public String getCnValue() {
    return cnValue;
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
