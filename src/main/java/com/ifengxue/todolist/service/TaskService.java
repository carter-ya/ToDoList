package com.ifengxue.todolist.service;

import static java.util.stream.Collectors.toList;

import com.ifengxue.base.rest.ApiException;
import com.ifengxue.todolist.entity.Project;
import com.ifengxue.todolist.entity.Task;
import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.enums.TaskState;
import com.ifengxue.todolist.repository.TaskRepository;
import com.ifengxue.todolist.service.transaction.TaskTransaction;
import com.ifengxue.todolist.util.ProjectUtil;
import com.ifengxue.todolist.web.request.NewTaskRequest;
import com.ifengxue.todolist.web.response.TaskResponse;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private ProjectService projectService;
  @Autowired
  private TaskTransaction taskTransaction;

  public TaskResponse save(Long userId, Long projectId, Long parentId, NewTaskRequest request) {
    Project project = projectService.findProject(projectId, userId);
    Task parentTask = null;
    // 检查是否有父任务
    if (!parentId.equals(0L)) {
      parentTask = findByIdAndUserIdAndProjectId(parentId, userId, projectId);
      parentTask.setEmpty(Boolean.FALSE);
    }
    Task task = Task.fromNew(userId, projectId, parentId, request);
    ProjectUtil.increTotalTask(project);
    taskTransaction.save(project, task, parentTask);
    LOGGER.info("用户 {} 创建任务成功，任务ID {}", userId, task.getId());
    return TaskResponse.from(task);
  }

  /**
   * 删除任务
   *
   * @param userId 用户ID
   * @param taskId 任务ID
   */
  public void deleteTask(Long userId, Long projectId, Long parentId, Long taskId) {
    Task task = findTask(userId, projectId, parentId, taskId);
    task.setState(TaskState.DELETED.getCode());
    Task parentTask = null;
    if (!task.getParentId().equals(0L)) {
      // 检查是否有父任务
      parentTask = taskRepository.findOne(task.getParentId());
      // 检查父任务是否需要更新
      if (taskRepository.countByParentId(task.getParentId()) == 0) {
        parentTask.setEmpty(Boolean.TRUE);
      }
    }
    // 更新任务信息
    Project project = projectService.findProject(task.getProjectId(), userId);
    ProjectUtil.decreTotalTask(project);
    if (TaskState.FINISHED.getCode().equals(task.getState())) {
      ProjectUtil.decreTotalFinishedTask(project);
    }
    taskTransaction.save(project, task, parentTask);
    LOGGER.info("用户 {} 删除任务 {} 成功", userId, taskId);
  }

  public Task findTask(Long userId, Long projectId, Long parentId, Long taskId) {
    Task task = Optional.ofNullable(taskRepository.findByIdAndUserId(taskId, userId))
        .orElseThrow(() -> new ApiException(GatewayError.TASK_NOT_FOUND, taskId, userId));
    if (!task.getParentId().equals(parentId) || !task.getProjectId().equals(projectId)) {
      throw new ApiException(GatewayError.TASK_NOT_FOUND, taskId, userId);
    }
    return task;
  }

  public List<TaskResponse> findByProjectIdAndTaskId(Long projectId, Long taskId) {
    return taskRepository.findByProjectIdAndParentId(projectId, taskId).stream()
        .map(TaskResponse::from).collect(toList());
  }

  private Task findByIdAndUserIdAndProjectId(Long taskId, Long userId, Long projectId) {
    return Optional
        .ofNullable(taskRepository.findByIdAndUserIdAndProjectId(taskId, userId, projectId))
        .orElseThrow(() -> new ApiException(GatewayError.TASK_NOT_FOUND, taskId, userId));
  }
}
