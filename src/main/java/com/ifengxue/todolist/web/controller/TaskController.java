package com.ifengxue.todolist.web.controller;

import com.ifengxue.base.rest.ApiResponse;
import com.ifengxue.todolist.service.TaskService;
import com.ifengxue.todolist.web.context.GatewayContext;
import com.ifengxue.todolist.web.request.NewTaskRequest;
import com.ifengxue.todolist.web.request.RenameTaskRequest;
import com.ifengxue.todolist.web.response.TaskResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PostMapping("/{parentId:\\d{1,19}}/task/{taskId:\\d{1,19}}/rename")
  @ApiOperation("任务重命名")
  public ApiResponse<Void> renameTask(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @PathVariable("taskId") Long taskId,
      @Valid @RequestBody RenameTaskRequest request) {
    taskService.renameTask(GatewayContext.getCurrentUserId(), projectId, parentId, taskId, request);
    return ApiResponse.ok(null);
  }

  @PostMapping("/{parentId:\\d{1,19}}/task/{taskId:\\d{1,19}}/priority{priority:[1-9]}")
  @ApiOperation("设置任务优先级")
  public ApiResponse<Void> setPriority(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @PathVariable("taskId") Long taskId,
      @PathVariable("priority") Integer priority) {
    taskService
        .setPriority(GatewayContext.getCurrentUserId(), projectId, parentId, taskId, priority);
    return ApiResponse.ok(null);
  }

  @PostMapping("/{parentId:\\d{1,19}}/task/{taskId:\\d{1,19}}/startedAt")
  @ApiOperation("设置任务开始时间")
  public ApiResponse<Void> setStartedAt(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @PathVariable("taskId") Long taskId,
      @Valid
      @RequestParam(value = "startedAt", defaultValue = "0")
      @Pattern(regexp = "\\d{1,19}", message = "INVALID_DATE") String startedAt) {
    taskService
        .setStartedAt(GatewayContext.getCurrentUserId(), projectId, parentId, taskId,
            Long.parseLong(startedAt));
    return ApiResponse.ok(null);
  }

  @PostMapping("/{parentId:\\d{1,19}}/task/{taskId:\\d{1,19}}/endedAt")
  @ApiOperation("设置任务结束时间")
  public ApiResponse<Void> setEndedAt(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @PathVariable("taskId") Long taskId,
      @Valid
      @RequestParam(value = "endedAt", defaultValue = "0")
      @Pattern(regexp = "\\d{1,19}", message = "INVALID_DATE") String startedAt) {
    taskService
        .setEndedAt(GatewayContext.getCurrentUserId(), projectId, parentId, taskId,
            Long.parseLong(startedAt));
    return ApiResponse.ok(null);
  }

  @PostMapping("/{parentId:\\d{1,19}}/task/{taskId:\\d{1,19}}/start")
  @ApiOperation("开始任务")
  public ApiResponse<Void> startTask(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @PathVariable("taskId") Long taskId) {
    taskService.startTask(GatewayContext.getCurrentUserId(), projectId, parentId, taskId);
    return ApiResponse.ok(null);
  }

  @PostMapping("/{parentId:\\d{1,19}}/task/{taskId:\\d{1,19}}/finish")
  @ApiOperation("开始任务")
  public ApiResponse<Void> finishTask(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId, @PathVariable("taskId") Long taskId) {
    taskService.finishTask(GatewayContext.getCurrentUserId(), projectId, parentId, taskId);
    return ApiResponse.ok(null);
  }

  @GetMapping("/{parentId:\\d{1,19}}/")
  @ApiOperation("获取任务列表")
  public ApiResponse<List<TaskResponse>> findTasks(@PathVariable("projectId") Long projectId,
      @PathVariable("parentId") Long parentId) {
    return ApiResponse.ok(taskService.findByProjectIdAndTaskId(projectId, parentId));
  }
}
