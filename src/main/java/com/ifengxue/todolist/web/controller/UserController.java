package com.ifengxue.todolist.web.controller;

import com.ifengxue.base.rest.ApiResponse;
import com.ifengxue.todolist.service.UserService;
import com.ifengxue.todolist.web.annotation.IgnoreAuth;
import com.ifengxue.todolist.web.context.GatewayContext;
import com.ifengxue.todolist.web.request.LoginRequest;
import com.ifengxue.todolist.web.request.RegisterUserRequest;
import com.ifengxue.todolist.web.request.RenameUserRequest;
import com.ifengxue.todolist.web.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@Validated
@Api(description = "用户控制器")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  @ApiOperation("用户注册")
  @IgnoreAuth
  public ApiResponse<UserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request) {
    return ApiResponse.ok(UserResponse.from(userService.registerUser(request)));
  }

  @PostMapping("/login")
  @ApiOperation("用户登录")
  @IgnoreAuth
  public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {
    return ApiResponse.ok(userService.login(request));
  }

  @GetMapping("/current")
  @ApiOperation("获取当前用户信息")
  public ApiResponse<UserResponse> findUserInfo() {
    return ApiResponse.ok(UserResponse.from(GatewayContext.USER_HOLDER.get()));
  }

  @PostMapping("/nickname")
  @ApiOperation("重新设置昵称")
  public ApiResponse<Void> renameNickname(@Valid @RequestBody RenameUserRequest request) {
    userService.renameUser(GatewayContext.getCurrentUser(), request);
    return ApiResponse.ok();
  }
}
