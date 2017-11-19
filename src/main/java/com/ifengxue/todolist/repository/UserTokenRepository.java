package com.ifengxue.todolist.repository;

import com.ifengxue.todolist.entity.UserToken;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

  UserToken findByToken(String token);

  @Transactional
  void deleteByToken(String token);

  @Transactional
  void deleteByUserId(Long userId);
}
