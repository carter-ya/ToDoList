package com.ifengxue.base.rest;

public class ApiException extends RuntimeException implements Error {

  private static final Object[] EMPTY_ARGS = {};
  private static final long serialVersionUID = 3950287955293204155L;
  private final Error error;
  private final String errorMessage;

  public ApiException(Error error) {
    this(error, EMPTY_ARGS);
  }

  public ApiException(Error error, Object... args) {
    super(Error.formatErrorMessage(error, args));
    this.error = error;
    this.errorMessage = getLocalizedMessage();
  }

  public ApiException(Error error, Throwable t) {
    this(error, t, EMPTY_ARGS);
  }

  public ApiException(Error error, Throwable t, Object... args) {
    super(Error.formatErrorMessage(error, args), t);
    this.error = error;
    this.errorMessage = getLocalizedMessage();
  }

  @Override
  public String getErrorCode() {
    return error.getErrorCode();
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }


}
