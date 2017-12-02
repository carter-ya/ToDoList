package com.ifengxue.todolist.web.controller;

import com.ifengxue.todolist.rest.ApiResponse;
import com.ifengxue.todolist.service.ProjectService;
import com.ifengxue.todolist.web.context.GatewayContext;
import com.ifengxue.todolist.web.request.NewProjectRequest;
import com.ifengxue.todolist.web.request.RenameProjectRequest;
import com.ifengxue.todolist.web.response.ProjectResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
@Validated
@RequestMapping("/v1/projects/")
@Api(description = "项目控制器")
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @PostMapping("/")
  @ApiOperation("新建项目")
  public ApiResponse<ProjectResponse> newProject(@Valid @RequestBody NewProjectRequest request) {
    return ApiResponse.ok(
        ProjectResponse.from(projectService.save(GatewayContext.getCurrentUserId(), request)));
  }

  @GetMapping("/")
  @ApiOperation("获取所有项目")
  public ApiResponse<List<ProjectResponse>> findProjects() {
    return ApiResponse.ok(projectService.findProjectsByUserId(GatewayContext.getCurrentUserId()));
  }

  @GetMapping("/{projectId:\\d{1,19}}")
  @ApiOperation("获取项目信息")
  public ApiResponse<ProjectResponse> findProject(@PathVariable("projectId") Long projectId) {
    return ApiResponse.ok(ProjectResponse
        .from(projectService.findProject(projectId, GatewayContext.getCurrentUserId())));
  }

  @PostMapping("/{projectId:\\d{1,19}}/rename")
  @ApiOperation("项目重命名")
  public ApiResponse<Void> renameProject(@Valid @RequestBody RenameProjectRequest request,
      @PathVariable("projectId") Long projectId) {
    projectService.renameProject(GatewayContext.getCurrentUserId(), projectId, request);
    return ApiResponse.ok(null);
  }

  @PostMapping("/{projectId:\\d{1,19}}/delete")
  @ApiOperation("删除项目")
  public ApiResponse<Void> deleteProject(@PathVariable("projectId") Long projectId) {
    projectService.deleteProject(GatewayContext.getCurrentUserId(), projectId);
    return ApiResponse.ok(null);
  }
}
