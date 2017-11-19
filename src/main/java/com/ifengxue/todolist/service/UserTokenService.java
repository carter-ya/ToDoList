package com.ifengxue.todolist.service;

import lombok.Builder;
import lombok.Data;

/**
 * 用户令牌服务
 */
public interface UserTokenService {

  /**
   * 保存用户的令牌
   */
  void save(UserToken token);

  /**
   * 查找令牌
   */
  UserToken findByToken(String token);

  /**
   * 删除token
   *
   * @param token 令牌
   */
  void deleteToken(String token);

  /**
   * 删除token
   *
   * @param userId 用户编号
   */
  void deleteToken(Long userId);

  @Data
  @Builder
  class UserToken {

    private Long userId;
    private String token;
    private Long expiredAt;
  }
}
