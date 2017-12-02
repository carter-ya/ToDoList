package com.ifengxue.todolist.web.interceptor;

import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.rest.ApiException;
import com.ifengxue.todolist.service.UserService;
import com.ifengxue.todolist.service.UserTokenService;
import com.ifengxue.todolist.service.UserTokenService.UserToken;
import com.ifengxue.todolist.web.ApplicationConfig;
import com.ifengxue.todolist.web.annotation.IgnoreAuth;
import com.ifengxue.todolist.web.context.GatewayContext;
import com.ifengxue.todolist.web.controller.UserController;
import java.lang.reflect.Method;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 审计控制器
 */
@Component
public class AuditInterceptor extends HandlerInterceptorAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuditInterceptor.class);
  private static final String WEB_PACKAGE_NAME = UserController.class.getPackage().getName();
  @Autowired
  private UserService userService;
  @Autowired
  private UserTokenService userTokenService;
  @Autowired
  private ApplicationConfig config;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HandlerMethod hm = (HandlerMethod) handler;
    Method m = hm.getMethod();
    // 只过滤指定包下的控制器
    if (!WEB_PACKAGE_NAME.equals(m.getDeclaringClass().getPackage().getName())) {
      return true;
    }
    IgnoreAuth ignoreAuth = Optional.ofNullable(m.getAnnotation(IgnoreAuth.class))
        .orElse(m.getDeclaringClass().getAnnotation(IgnoreAuth.class));
    if (ignoreAuth != null && ignoreAuth.ignore()) {
      return true;
    }
    String token = request.getHeader("Token");
    if (StringUtils.isEmpty(token)) {
      throw new ApiException(GatewayError.INVALID_TOKEN);
    }
    UserToken userToken = Optional.ofNullable(userTokenService.findByToken(token))
        .orElseThrow(() -> new ApiException(GatewayError.INVALID_TOKEN));
    if (userToken.getExpiredAt() < System.currentTimeMillis()) {
      LOGGER.debug("用户 {} 的令牌 {} 已过期", userToken.getUserId(), userToken.getToken());
      // 令牌已过期，删除过期token
      userTokenService.deleteToken(token);
      throw new ApiException(GatewayError.INVALID_TOKEN);
    }
    GatewayContext.USER_HOLDER.set(userService.findUserById(userToken.getUserId()));
    // 更新过期时间
    userTokenService.expire(GatewayContext.getCurrentUserId(),
        config.getToken().getValidPeriod() * 60 * 1000 + System.currentTimeMillis());
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    // 释放资源
    GatewayContext.USER_HOLDER.remove();
  }
}
