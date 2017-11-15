package com.ifengxue.base.rest;

public interface Error {

  /**
   * 获取错误代码
   */
  String getErrorCode();

  /**
   * 获取错误描述信息
   */
  String getErrorMessage();

  /**
   * 格式化错误提示信息
   */
  static String formatErrorMessage(Error error, Object... args) {
    if (args.length == 0) {
      return error.getErrorMessage();
    } else {
      return String.format(error.getErrorMessage(), args);
    }
  }
}
