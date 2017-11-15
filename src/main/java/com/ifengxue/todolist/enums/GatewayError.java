package com.ifengxue.todolist.enums;

import com.ifengxue.base.rest.Error;

public enum GatewayError implements Error{
  BAD_ARGUMENT("bad argument", "错误的参数: %s"),
  USER_NOT_FOUND("user not found", "不存在的用户编号: %s"),
  PROJECT_NOT_FOUND("project not found", "不存在的项目编号: %s(user: %s)"),
  TASK_NOT_FOUND("task not found", "不存在的任务编号: %s(user: %s)");
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
