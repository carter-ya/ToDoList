package com.ifengxue.todolist.service;

import static java.util.stream.Collectors.toList;

import com.ifengxue.todolist.repository.TaskRepository;
import com.ifengxue.todolist.web.response.TaskResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

  @Autowired
  private TaskRepository taskRepository;

  public List<TaskResponse> findByProjectIdAndTaskId(Long projectId, Long taskId) {
    return taskRepository.findByProjectIdAndParentId(projectId, taskId).stream()
        .map(TaskResponse::from).collect(toList());
  }
}
