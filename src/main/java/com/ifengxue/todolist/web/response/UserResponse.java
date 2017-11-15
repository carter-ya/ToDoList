package com.ifengxue.todolist.web.response;

import com.ifengxue.todolist.entity.User;
import com.ifengxue.todolist.util.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息")
public class UserResponse {

  @ApiModelProperty("创建时间")
  private Long createdAt;
  @ApiModelProperty("邮箱")
  private String email;
  @ApiModelProperty("头像")
  private String icon;
  @ApiModelProperty("用户编号")
  private Long id;
  @ApiModelProperty("昵称")
  private String nickname;
  @ApiModelProperty("手机号")
  private String phone;

  public static UserResponse from(User user) {
    return BeanUtil.copyProperties(user, UserResponse.class);
  }
}
