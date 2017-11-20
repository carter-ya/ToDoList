package com.ifengxue.todolist.enums;

import com.ifengxue.base.rest.Error;

public enum GatewayError implements Error {
  BAD_ARGUMENT("bad argument", "错误的参数: %s"),
  INTERNAL_ERROR("internal error", "系统内部错误"),
  USER_NOT_FOUND("user not found", "不存在的用户"),
  PROJECT_NOT_FOUND("project not found", "不存在的项目"),
  TASK_NOT_FOUND("task not found", "不存在的任务"),
  DATA_ERROR("data error", "数据状态错误"),
  START_TIME_GREATER_END_TIME("start time greater end time", "开始时间晚于截止时间"),
  INVALID_PHONE("invalid phone", "无效的手机号: %s"),
  INVALID_EMAIL("invalid email", "无效的邮箱: %s"),
  DUPLICATE_ACCOUNT("duplicate account", "重复的账号: %s"),
  INVALID_PASSWORD("invalid password", "密码不正确"),
  INVALID_TOKEN("invalid token", "无效的令牌");
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
