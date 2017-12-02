package com.ifengxue.todolist.web.controller;

import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.enums.ValidationError;
import com.ifengxue.todolist.rest.ApiException;
import com.ifengxue.todolist.rest.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常处理器
 */
@ControllerAdvice
public class ApiErrorHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiErrorHandler.class);

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ApiResponse<Void> handler(HttpServletRequest request, Exception ex) {
    if (ex instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException manve = (MethodArgumentNotValidException) ex;
      FieldError fe = manve.getBindingResult().getFieldError();
      if (fe != null) {
        ValidationError ve = ValidationError.find(fe.getDefaultMessage());
        return ApiResponse.error(ve);
      } else {
        ObjectError ge = manve.getBindingResult().getGlobalError();
        LOGGER.warn("Programming error: cannot convert err code: " + fe.getDefaultMessage(), ex);
        return ApiResponse.error(GatewayError.BAD_ARGUMENT.getErrorCode(),
            "Object " + ge.getObjectName() + " is invalid.");
      }
    }
    if (ex instanceof ConstraintViolationException) {
      ConstraintViolationException firstOne = ((ConstraintViolationException) ex)
          .getConstraintViolations()
          .toArray(new ConstraintViolationException[]{})[0];
      ValidationError ve = ValidationError.find(firstOne.getMessage());
      return ApiResponse.error(ve);
    }
    if (ex instanceof HttpMessageNotReadableException) {
      return ApiResponse.error(GatewayError.BAD_ARGUMENT);
    }
    if (ex instanceof ApiException) {
      ApiException ae = (ApiException) ex;
      LOGGER.warn(
          "Handler exception:" + ae.getErrorCode() + ", error message:" + ae.getErrorMessage(), ex);
      return ApiResponse.error(ae);
    } else {
      LOGGER.error("Handler not expected exception", ex);
      return ApiResponse.error(GatewayError.INTERNAL_ERROR);
    }
  }
}
