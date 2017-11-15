package com.ifengxue.base.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel("通用响应")
public class ApiResponse<T> {

  private static final String STATUS_OK = "ok";
  private static final String STATUS_ERROR = "error";
  private static final String MESSAGE_SUCCESS = "success";
  @ApiModelProperty("接口状态:ok成功;error:失败")
  private String status;
  @ApiModelProperty("错误信息")
  private String message;
  @ApiModelProperty("错误码")
  private String errorCode;
  @ApiModelProperty("接口返回值")
  private T data;

  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(STATUS_OK, MESSAGE_SUCCESS, MESSAGE_SUCCESS, data);
  }

  public static <T> ApiResponse<T> error(Error error) {
    return new ApiResponse<>(STATUS_ERROR, error.getErrorMessage(), error.getErrorCode(), null);
  }
}
