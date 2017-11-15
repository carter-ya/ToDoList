package com.ifengxue.todolist.web.controller;

import com.ifengxue.base.rest.ApiResponse;
import com.ifengxue.todolist.service.UserService;
import com.ifengxue.todolist.web.context.GatewayContext;
import com.ifengxue.todolist.web.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@Validated
@Api(description = "用户控制器")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/current")
  @ApiOperation("获取当前用户信息")
  public ApiResponse<UserResponse> findUserInfo() {
    return ApiResponse.ok(UserResponse.from(GatewayContext.USER_HOLDER.get()));
  }
}
