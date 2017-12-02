package com.ifengxue.todolist.service;

import com.ifengxue.todolist.entity.User;
import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.repository.UserRepository;
import com.ifengxue.todolist.rest.ApiException;
import com.ifengxue.todolist.service.UserTokenService.UserToken;
import com.ifengxue.todolist.util.ValidatorUtil;
import com.ifengxue.todolist.web.ApplicationConfig;
import com.ifengxue.todolist.web.request.LoginRequest;
import com.ifengxue.todolist.web.request.RegisterUserRequest;
import com.ifengxue.todolist.web.request.RenameUserRequest;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 */
@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;
  @Autowired
  @Qualifier("userTokenDaoService")
  private UserTokenService tokenService;
  @Autowired
  private ApplicationConfig config;

  /**
   * 注册账户
   */
  public User registerUser(RegisterUserRequest request) {
    AtomicBoolean isPhone = new AtomicBoolean(false);
    User existUser = findUser(request.getAccount(), isPhone);
    if (existUser != null) {
      throw new ApiException(GatewayError.DUPLICATE_ACCOUNT, request.getAccount());
    }
    SecureRandom sr = new SecureRandom();
    byte[] buf = new byte[8];
    sr.nextBytes(buf);
    String salt = Hex.encodeHexString(buf);
    String password = passwordHash(request.getPassword(), salt);
    User registerUser;
    if (isPhone.get()) {
      registerUser = User.fromPhone(request.getNickname(), request.getAccount(), password, salt);
    } else {
      registerUser = User.fromEmail(request.getNickname(), request.getAccount(), password, salt);
    }
    userRepository.save(registerUser);
    LOGGER.info("用户 {} 账号 {} 昵称 {} 注册成功", registerUser.getId(), request.getAccount(),
        request.getNickname());
    return registerUser;
  }

  /**
   * 用户登录
   */
  public String login(LoginRequest request) {
    User user = findUser(request.getAccount(), new AtomicBoolean());
    if (user == null) {
      throw new ApiException(GatewayError.USER_NOT_FOUND);
    }
    String password = passwordHash(request.getPassword(), user.getSalt());
    if (!password.equals(user.getPassword())) {
      throw new ApiException(GatewayError.INVALID_PASSWORD);
    }
    // 删除历史的令牌
    tokenService.deleteToken(user.getId());
    // 保存新令牌
    UserToken token = UserToken.builder().userId(user.getId())
        .token(DigestUtils.md5Hex(user.getId() + "@" + System.currentTimeMillis()))
        .expiredAt(config.getToken().getValidPeriod() * 60 * 1000 + System.currentTimeMillis())
        .build();
    tokenService.save(token);
    return token.getToken();
  }

  /**
   * 重置昵称
   */
  public void renameUser(User user, RenameUserRequest request) {
    if (user.getNickname().equals(request.getNickname())) {
      throw new ApiException(GatewayError.DATA_ERROR);
    }
    user.setNickname(request.getNickname());
    userRepository.save(user);
    LOGGER.info("用户 {} 重置昵称为 {} 成功", user.getId(), request.getNickname());
  }

  public User findUserById(Long userId) {
    return Optional.ofNullable(userRepository.findOne(userId))
        .orElseThrow(() -> new ApiException(GatewayError.USER_NOT_FOUND));
  }

  /**
   * 根据账号查找用户，并返回账户类型
   *
   * @param isPhone 同样作为返回值返回
   */
  private User findUser(String account, AtomicBoolean isPhone) {
    if (ValidatorUtil.isPhone(account)) {
      isPhone.set(true);
      return userRepository.findByPhone(account);
    } else {
      isPhone.set(false);
      return userRepository.findByEmail(account);
    }
  }

  /**
   * 原始密码hash
   */
  private String passwordHash(String originalPassword, String salt) {
    return DigestUtils.sha512Hex(originalPassword + "&" + salt);
  }
}
