package com.ifengxue.todolist.service;

import com.ifengxue.base.rest.ApiException;
import com.ifengxue.todolist.entity.User;
import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.repository.UserRepository;
import java.util.Optional;
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

  public User findUserById(Long userId) {
    return Optional.ofNullable(userRepository.findOne(userId))
        .orElseThrow(() -> new ApiException(GatewayError.USER_NOT_FOUND, userId));
  }
}
