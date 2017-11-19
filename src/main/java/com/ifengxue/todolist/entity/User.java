package com.ifengxue.todolist.entity;

import com.ifengxue.base.entity.AbstractEntity;
import com.ifengxue.todolist.enums.UserState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity<Long> {

  private static final long serialVersionUID = -1255739096044120452L;
  @Column(nullable = false)
  private String nickname = "";
  @Column(nullable = false)
  private String phone = "";
  @Column(nullable = false)
  private String email = "";
  @Column(nullable = false)
  private String password = "";
  @Column(nullable = false)
  private String salt = "";
  @Column(nullable = false)
  private String icon = "";
  @Column(nullable = false)
  private Integer state = UserState.VALID.getCode();

  public static User fromPhone(String nickname, String phone, String password, String salt) {
    User user = new User();
    user.setNickname(nickname);
    user.setPhone(phone);
    user.setPassword(password);
    user.setSalt(salt);
    return user;
  }

  public static User fromEmail(String nickname, String email, String password, String salt) {
    User user = new User();
    user.setNickname(nickname);
    user.setEmail(email);
    user.setPassword(password);
    user.setSalt(salt);
    return user;
  }
}
