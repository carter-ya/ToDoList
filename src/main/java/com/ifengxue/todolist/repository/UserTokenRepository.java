package com.ifengxue.todolist.repository;

import com.ifengxue.todolist.entity.UserToken;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

  UserToken findByToken(String token);

  @Transactional
  void deleteByToken(String token);

  @Transactional
  void deleteByUserId(Long userId);

  @Transactional
  @Modifying
  @Query("update UserToken token set token.expiredAt = ?2 where token.userId = ?1")
  void updateExpiredAt(Long userId, Long expiredAt);

  @Transactional
  @Modifying
  @Query("update UserToken token set token.expiredAt = ?2 where token.token = ?1")
  void updateExpiredAt(String token, Long expiredAt);

  /**
   * 删除过期的token
   */
  @Transactional
  int deleteByExpiredAtLessThan(Long expiredAt);
}
