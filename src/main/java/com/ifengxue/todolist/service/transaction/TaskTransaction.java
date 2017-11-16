package com.ifengxue.todolist.service.transaction;

import com.ifengxue.todolist.entity.Project;
import com.ifengxue.todolist.entity.Task;
import com.ifengxue.todolist.repository.ProjectRepository;
import com.ifengxue.todolist.repository.TaskRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务事务
 */
@Service
public class TaskTransaction {

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private TaskRepository taskRepository;

  /**
   * @param parentTask 父任务如果为null，则不保存
   */
  @Transactional
  public void save(Project project, Task task, Task parentTask) {
    projectRepository.save(project);
    taskRepository.save(task);
    if (parentTask != null) {
      taskRepository.save(parentTask);
    }
  }
}
