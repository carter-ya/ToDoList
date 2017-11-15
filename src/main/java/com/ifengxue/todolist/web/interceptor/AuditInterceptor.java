package com.ifengxue.todolist.web.interceptor;

import com.ifengxue.todolist.service.UserService;
import com.ifengxue.todolist.web.context.GatewayContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 审计控制器
 */
@Component
public class AuditInterceptor extends HandlerInterceptorAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuditInterceptor.class);
  @Autowired
  private UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    //TODO : 注入测试用户
    GatewayContext.USER_HOLDER.set(userService.findUserById(1L));
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    // 释放资源
    GatewayContext.USER_HOLDER.remove();
  }
}
