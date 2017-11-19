package com.ifengxue.todolist.web.request;

import static com.ifengxue.todolist.web.request.RegisterUserRequest.NICKNAME_MAX_LENGTH;
import static com.ifengxue.todolist.web.request.RegisterUserRequest.NICKNAME_MIN_LENGTH;

import com.ifengxue.todolist.web.validator.IllegalCharacter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@ApiModel("用户昵称重命名")
public class RenameUserRequest {

  @NotEmpty(message = "INVALID_NICKNAME")
  @Pattern(regexp = "\\S{" + NICKNAME_MIN_LENGTH + "," + NICKNAME_MAX_LENGTH
      + "}", message = "INVALID_NICKNAME")
  @IllegalCharacter(message = "ILLEGAL_NICKNAME")
  @ApiModelProperty("昵称")
  private String nickname;
}
