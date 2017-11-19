package com.ifengxue.todolist.web.request;

import com.ifengxue.todolist.web.validator.Account;
import com.ifengxue.todolist.web.validator.IllegalCharacter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel("注册用户")
public class RegisterUserRequest {

  public static final int PASSWORD_MIN_LENGTH = 8;
  public static final int PASSWORD_MAX_LENGTH = 32;
  public static final int NICKNAME_MIN_LENGTH = 1;
  public static final int NICKNAME_MAX_LENGTH = 32;
  @Account
  @ApiModelProperty("手机号或邮箱")
  private String account;
  @NotEmpty(message = "INVALID_PASSWORD")
  @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "INVALID_PASSWORD")
  @ApiModelProperty("密码")
  private String password;
  @NotEmpty(message = "INVALID_NICKNAME")
  @Pattern(regexp = "\\S{" + NICKNAME_MIN_LENGTH + "," + NICKNAME_MAX_LENGTH
      + "}", message = "INVALID_NICKNAME")
  @IllegalCharacter(message = "ILLEGAL_NICKNAME")
  @ApiModelProperty("昵称")
  private String nickname;
}
