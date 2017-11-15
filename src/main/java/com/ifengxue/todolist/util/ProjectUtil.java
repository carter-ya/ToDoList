package com.ifengxue.todolist.util;

import com.ifengxue.todolist.entity.Project;

public final class ProjectUtil {

  /**
   * 任务总数量自增1
   */
  public static void increTotalTask(Project project) {
    project.setTotalTask(project.getTotalTask() + 1);
  }

  /**
   * 任务总数量自减1
   */
  public static void decreTotalTask(Project project) {
    project.setTotalTask(project.getTotalTask() - 1);
  }

  /**
   * 任务总完成数量自增1
   */
  public static void increTotalFinishedTask(Project project) {
    project.setTotalFinishedTask(project.getTotalFinishedTask() + 1);
  }

  /**
   * 任务总完成数量自减1
   */
  public static void decreTotalFinishedTask(Project project) {
    project.setTotalFinishedTask(project.getTotalFinishedTask() - 1);
  }
}
