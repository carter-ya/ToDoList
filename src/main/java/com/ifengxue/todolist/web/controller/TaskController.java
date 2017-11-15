package com.ifengxue.todolist.web.controller;

import com.ifengxue.base.rest.ApiResponse;
import com.ifengxue.todolist.service.TaskService;
import com.ifengxue.todolist.web.context.GatewayContext;
import com.ifengxue.todolist.web.request.NewTaskRequest;
import com.ifengxue.todolist.web.response.TaskResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/projects/{projectId:\\d{1,19}}/tasks")
@Validated
@Api(description = "任务控制器")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @GetMapping("/{parentId:\\d{1,19}}/task/{taskId:\\d{1,19}}")
  @ApiOperation("获取任务信息")
  public ApiResponse<TaskResponse> findTask(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @PathVariable("taskId") Long taskId) {
    return ApiResponse
        .ok(TaskResponse.from(
            taskService.findTask(GatewayContext.getCurrentUserId(), projectId, parentId, taskId)));
  }

  @PostMapping("/{parentId:\\d{1,19}}/")
  @ApiOperation("新建任务")
  public ApiResponse<TaskResponse> newTask(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @Valid @RequestBody
      NewTaskRequest request) {
    return ApiResponse
        .ok(taskService.save(GatewayContext.getCurrentUserId(), projectId, parentId, request));
  }

  @GetMapping("/{parentId:\\d{1,19}}/task/{taskId:\\d{1,19}}/delete")
  @ApiOperation("删除任务")
  public ApiResponse<Void> deleteTask(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @PathVariable("taskId") Long taskId) {
    taskService.deleteTask(GatewayContext.getCurrentUserId(), projectId, parentId, taskId);
    return ApiResponse.ok(null);
  }
}
