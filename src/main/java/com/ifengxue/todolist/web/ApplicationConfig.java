package com.ifengxue.todolist.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 应用配置
 */
@ConfigurationProperties(prefix = "todolist")
@Data
@Component
public class ApplicationConfig {

  private TokenConfig token;

  @Data
  public static class TokenConfig {

    /**
     * token有效期
     */
    int validPeriod;
  }
}
