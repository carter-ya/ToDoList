package com.ifengxue.todolist.service;

import static java.util.stream.Collectors.toList;

import com.ifengxue.base.rest.ApiException;
import com.ifengxue.todolist.entity.Project;
import com.ifengxue.todolist.entity.Task;
import com.ifengxue.todolist.entity.TaskComment;
import com.ifengxue.todolist.entity.TaskComment.SimpleNClob;
import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.enums.TaskState;
import com.ifengxue.todolist.repository.TaskCommentRepository;
import com.ifengxue.todolist.repository.TaskRepository;
import com.ifengxue.todolist.service.transaction.TaskTransaction;
import com.ifengxue.todolist.util.ProjectUtil;
import com.ifengxue.todolist.web.request.NewTaskRequest;
import com.ifengxue.todolist.web.request.RenameTaskRequest;
import com.ifengxue.todolist.web.response.TaskResponse;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import java.io.InputStream;
import java.sql.SQLException;
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
  @Autowired
  private TaskCommentRepository commentRepository;

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
   * 任务重命名
   */
  public void renameTask(Long userId, Long projectId, Long parentId, Long taskId,
      RenameTaskRequest request) {
    Task task = findTask(userId, projectId, parentId, taskId);
    if (task.getTitle().equals(request.getName())) {
      throw new ApiException(GatewayError.DATA_ERROR);
    }
    task.setTitle(request.getName());
    taskRepository.save(task);
    LOGGER.info("用户 {} 重命名任务 {} 为 {} 成功", userId, taskId, request.getName());
  }

  /**
   * 设置任务优先级
   */
  public void setPriority(Long userId, Long projectId, Long parentId, Long taskID, int priority) {
    Task task = findTask(userId, projectId, parentId, taskID);
    if (task.getPriority().equals(priority)) {
      throw new ApiException(GatewayError.DATA_ERROR);
    }
    task.setPriority(priority);
    taskRepository.save(task);
    LOGGER.info("用户 {} 设置任务 {} 优先级为 {} 成功", userId, taskID, priority);
  }

  /**
   * 设置任务开始时间
   */
  public void setStartedAt(Long userId, Long projectId, Long parentId, Long taskId,
      long startedAt) {
    Task task = findTask(userId, projectId, parentId, taskId);
    if (task.getStartedAt().equals(startedAt) && startedAt != 0) {
      throw new ApiException(GatewayError.DATA_ERROR);
    }
    if (startedAt > 0 && !task.getEndedAt().equals(0L) && startedAt > task.getEndedAt()) {
      throw new ApiException(GatewayError.START_TIME_GREATER_END_TIME);
    }
    task.setStartedAt(startedAt);
    taskRepository.save(task);
    LOGGER.info("用户 {} 设置任务 {} 开始时间为 {} 成功", userId, taskId, startedAt);
  }

  /**
   * 设置任务截止时间
   */
  public void setEndedAt(Long userId, Long projectId, Long parentId, Long taskId, Long endedAt) {
    Task task = findTask(userId, projectId, parentId, taskId);
    if (task.getEndedAt().equals(endedAt) && endedAt != 0) {
      throw new ApiException(GatewayError.DATA_ERROR);
    }
    if (endedAt > 0 && !task.getEndedAt().equals(0L) && endedAt < task.getStartedAt()) {
      throw new ApiException(GatewayError.START_TIME_GREATER_END_TIME);
    }
    task.setEndedAt(endedAt);
    taskRepository.save(task);
    LOGGER.info("用户 {} 设置任务 {} 结束时间为 {} 成功", userId, taskId, endedAt);
  }

  /**
   * 开始任务
   */
  public void startTask(Long userId, Long projectId, Long parentId, Long taskId) {
    Task task = findTask(userId, projectId, parentId, taskId);
    if (TaskState.PROGRESS.getCode().equals(task.getState())) {
      throw new ApiException(GatewayError.DATA_ERROR);
    }
    // 检查是否有必要更新已完成任务数量
    Project project = null;
    if (TaskState.FINISHED.getCode().equals(task.getState())) {
      project = projectService.findProject(projectId, userId);
      ProjectUtil.decreTotalFinishedTask(project);
    }
    long now = System.currentTimeMillis();
    task.setStartedAt(now);
    task.setFinishedAt(0L);
    if (!task.getEndedAt().equals(0L) && task.getEndedAt() < now) {
      task.setEndedAt(0L);
    }
    if (project == null) {
      taskRepository.save(task);
    } else {
      taskTransaction.save(project, task, null);
    }
    LOGGER.info("用户 {} 开始任务 {} 成功", userId, taskId);
  }

  /**
   * 完成任务
   */
  public void finishTask(Long userId, Long projectId, Long parentId, Long taskId) {
    Task task = findTask(userId, projectId, parentId, taskId);
    if (TaskState.FINISHED.getCode().equals(task.getState())) {
      throw new ApiException(GatewayError.DATA_ERROR);
    }
    long now = System.currentTimeMillis();
    task.setFinishedAt(now);
    task.setState(TaskState.FINISHED.getCode());
    Project project = projectService.findProject(projectId, userId);
    ProjectUtil.increTotalFinishedTask(project);
    taskTransaction.save(project, task, null);
    LOGGER.info("用户 {} 完成任务 {} 成功", userId, taskId);
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
      // 检查父任务是否需要更新->当前父任务的下级只有一个任务了（也就是要被删除的任务）
      if (taskRepository.countByParentId(task.getParentId()) == 1) {
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

  /**
   * 获取注释
   */
  public InputStream findComment(Long userId, Long projectId, Long parentId, Long taskId) {
    Task task = findTask(userId, projectId, parentId, taskId);
    TaskComment taskComment = commentRepository.findByTaskId(task.getId());
    if (taskComment == null) {
      return new ByteInputStream();
    } else {
      try {
        return taskComment.getContent().getAsciiStream();
      } catch (SQLException e) {
        throw new ApiException(GatewayError.INTERNAL_ERROR, e);
      }
    }
  }

  /**
   * 修改备注信息
   */
  public void modifyComment(Long userId, Long projectId, Long parentId, Long taskId,
      InputStream stream, long contentLength) {
    Task task = findTask(userId, projectId, parentId, taskId);
    TaskComment taskComment = Optional.ofNullable(commentRepository.findByTaskId(task.getId()))
        .map(tc -> {
          tc.setContent(new SimpleNClob(stream, contentLength));
          return tc;
        })
        .orElseGet(() -> TaskComment.from(task.getId(), stream, contentLength));
    commentRepository.save(taskComment);
    LOGGER.info("用户 {} 修改任务 {} 备注成功", userId, taskId);
  }

  public Task findTask(Long userId, Long projectId, Long parentId, Long taskId) {
    Task task = Optional.ofNullable(taskRepository.findByIdAndUserId(taskId, userId))
        .orElseThrow(() -> new ApiException(GatewayError.TASK_NOT_FOUND));
    if (!task.getParentId().equals(parentId) || !task.getProjectId().equals(projectId)) {
      throw new ApiException(GatewayError.TASK_NOT_FOUND);
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
        .orElseThrow(() -> new ApiException(GatewayError.TASK_NOT_FOUND));
  }
}
