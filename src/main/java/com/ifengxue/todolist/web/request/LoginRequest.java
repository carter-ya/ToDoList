package com.ifengxue.todolist.web.request;

import static com.ifengxue.todolist.web.request.RegisterUserRequest.PASSWORD_MAX_LENGTH;
import static com.ifengxue.todolist.web.request.RegisterUserRequest.PASSWORD_MIN_LENGTH;

import com.ifengxue.todolist.web.validator.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel("用户登录")
public class LoginRequest {

  @Account
  @ApiModelProperty("手机号或邮箱")
  private String account;
  @NotEmpty(message = "INVALID_PASSWORD")
  @Pattern(regexp = "\\S{" + PASSWORD_MIN_LENGTH + "," + PASSWORD_MAX_LENGTH
      + "}", message = "INVALID_PASSWORD")
  @ApiModelProperty("密码")
  private String password;
}