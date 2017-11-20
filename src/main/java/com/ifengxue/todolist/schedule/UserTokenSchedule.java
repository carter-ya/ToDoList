package com.ifengxue.todolist.schedule;

import com.ifengxue.todolist.repository.UserTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserTokenSchedule {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenSchedule.class);
  @Autowired
  private UserTokenRepository userTokenRepository;

  /**
   * 每隔一分钟清理一次过期令牌
   */
  @Scheduled(fixedDelay = 60 * 1000)
  public void deleteExpiredToken() {
    int deleteCount = userTokenRepository.deleteByExpiredAtLessThan(System.currentTimeMillis());
    if (deleteCount > 0) {
      LOGGER.info("删除 {} 个过期令牌", deleteCount);
    }
  }
}
