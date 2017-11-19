package com.ifengxue.todolist.repository;

import com.ifengxue.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByPhone(String phone);

  User findByEmail(String email);
}
