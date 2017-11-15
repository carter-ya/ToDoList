package com.ifengxue.todolist.web.context;

import com.ifengxue.todolist.entity.User;

public final class GatewayContext {

  public static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

  public static User getCurrentUser() {
    return USER_HOLDER.get();
  }

  public static Long getCurrentUserId() {
    return getCurrentUser().getId();
  }
}
