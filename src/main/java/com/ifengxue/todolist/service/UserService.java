package com.ifengxue.todolist.service;

import com.ifengxue.base.rest.ApiException;
import com.ifengxue.todolist.entity.User;
import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.repository.UserRepository;
import com.ifengxue.todolist.util.ValidatorUtil;
import com.ifengxue.todolist.web.request.RegisterUserRequest;
import com.ifengxue.todolist.web.request.RenameUserRequest;
import java.security.SecureRandom;
import java.util.Optional;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 */
@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  /**
   * 注册账户
   */
  public User registerUser(RegisterUserRequest request) {
    User existUser;
    boolean isPhone = false;
    if (ValidatorUtil.isPhone(request.getAccount())) {
      existUser = userRepository.findByPhone(request.getAccount());
      isPhone = true;
    } else {
      existUser = userRepository.findByEmail(request.getAccount());
    }
    if (existUser != null) {
      throw new ApiException(GatewayError.DUPLICATE_ACCOUNT, request.getAccount());
    }
    SecureRandom sr = new SecureRandom();
    byte[] buf = new byte[8];
    sr.nextBytes(buf);
    String salt = Hex.encodeHexString(buf);
    String password = DigestUtils.sha512Hex(request.getPassword() + "&" + salt);
    User registerUser;
    if (isPhone) {
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
        .orElseThrow(() -> new ApiException(GatewayError.USER_NOT_FOUND, userId));
  }
}
