package com.ifengxue.todolist.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  /**
   * 设置令牌过期时间
   *
   * @param token 令牌
   * @param expiredAt 过期时间
   */
  void expire(String token, Long expiredAt);

  /**
   * 设置令牌过期时间
   *
   * @param userId 令牌
   * @param expiredAt 过期时间
   */
  void expire(Long userId, Long expiredAt);

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  class UserToken {

    private Long userId;
    private String token;
    private Long expiredAt;
  }
}
