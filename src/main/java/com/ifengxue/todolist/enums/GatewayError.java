package com.ifengxue.todolist.enums;

import com.ifengxue.base.rest.Error;

public enum GatewayError implements Error {
  BAD_ARGUMENT("bad argument", "错误的参数: %s"),
  USER_NOT_FOUND("user not found", "不存在的用户编号: %s"),
  PROJECT_NOT_FOUND("project not found", "不存在的项目编号: %s(user: %s)"),
  TASK_NOT_FOUND("task not found", "不存在的任务编号: %s(user: %s)"),
  DATA_ERROR("data error", "数据状态错误"),
  START_TIME_GREATER_END_TIME("start time greater end time", "开始时间晚于截止时间"),;
  private final String errorCode;
  private final String errorMessage;

  GatewayError(String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  @Override
  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }
}
