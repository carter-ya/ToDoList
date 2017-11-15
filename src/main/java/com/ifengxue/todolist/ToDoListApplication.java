package com.ifengxue.todolist;

import com.ifengxue.todolist.entity.User;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EntityScan(basePackageClasses = User.class)
@EnableSwagger2Doc
public class ToDoListApplication {

  public static void main(String[] args) {
    SpringApplication.run(ToDoListApplication.class, args);
  }
}
