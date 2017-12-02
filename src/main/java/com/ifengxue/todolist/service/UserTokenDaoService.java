package com.ifengxue.todolist.service;

import com.ifengxue.todolist.repository.UserTokenRepository;
import com.ifengxue.todolist.util.BeanUtil;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户令牌服务数据库实现
 */
@Service
public class UserTokenDaoService implements UserTokenService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenDaoService.class);
  @Autowired
  private UserTokenRepository userTokenRepository;

  @Override
  public void save(UserToken token) {
    com.ifengxue.todolist.entity.UserToken userToken = BeanUtil
        .copyProperties(token, com.ifengxue.todolist.entity.UserToken.class);
    userTokenRepository.save(userToken);
    LOGGER.info("用户 {} 保存令牌成功", token.getUserId());
  }

  @Override
  public UserToken findByToken(String token) {
    return Optional.ofNullable(userTokenRepository.findByToken(token))
        .map(ut -> BeanUtil.copyProperties(ut, UserToken.class)).orElse(null);
  }

  @Override
  public void deleteToken(String token) {
    userTokenRepository.deleteByToken(token);
  }

  @Override
  public void deleteToken(Long userId) {
    userTokenRepository.deleteByUserId(userId);
  }

  @Override
  public void expire(String token, Long expiredAt) {
    userTokenRepository.updateExpiredAt(token, expiredAt);
  }

  @Override
  public void expire(Long userId, Long expiredAt) {
    userTokenRepository.updateExpiredAt(userId, expiredAt);
  }
}
