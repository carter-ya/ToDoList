package com.ifengxue.todolist.entity;

import com.ifengxue.base.entity.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_user_token")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserToken extends AbstractEntity<Long> {

  private static final long serialVersionUID = 3561252302960578201L;
  @Column(name = "user_id", nullable = false)
  private Long userId;
  @Column(nullable = false)
  private String token;
  @Column(name = "expired_at", nullable = false)
  private Long expiredAt;

  public static UserToken from(Long userId, String token, Long expiredAt) {
    UserToken userToken = new UserToken();
    userToken.setUserId(userId);
    userToken.setToken(token);
    userToken.setExpiredAt(expiredAt);
    return userToken;
  }
}
