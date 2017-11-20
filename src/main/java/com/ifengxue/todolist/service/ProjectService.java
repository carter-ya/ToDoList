package com.ifengxue.todolist.service;

import static java.util.stream.Collectors.toList;

import com.ifengxue.base.rest.ApiException;
import com.ifengxue.todolist.entity.Project;
import com.ifengxue.todolist.enums.GatewayError;
import com.ifengxue.todolist.enums.ProjectState;
import com.ifengxue.todolist.repository.ProjectRepository;
import com.ifengxue.todolist.web.request.NewProjectRequest;
import com.ifengxue.todolist.web.request.RenameProjectRequest;
import com.ifengxue.todolist.web.response.ProjectResponse;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目服务
 */
@Service
public class ProjectService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

  @Autowired
  private ProjectRepository projectRepository;

  public Project save(Long userId, NewProjectRequest request) {
    Project project = Project.from(userId, request);
    project = projectRepository.save(project);
    return project;
  }

  public List<ProjectResponse> findProjectsByUserId(Long userId) {
    return projectRepository.findByUserId(userId).stream().map(ProjectResponse::from)
        .collect(toList());
  }

  /**
   * 项目重命名
   *
   * @param userId 用户ID
   * @param projectId 项目ID
   * @param request 请求数据
   */
  public void renameProject(Long userId, Long projectId, RenameProjectRequest request) {
    Project project = findProject(projectId, userId);
    project.setName(request.getName());
    projectRepository.save(project);
    LOGGER.info("项目 {} 重命名为 {} 成功", projectId, request.getName());
  }

  /**
   * 删除项目
   *
   * @param userId 用户ID
   * @param projectId 项目ID
   */
  public void deleteProject(Long userId, Long projectId) {
    Project project = findProject(projectId, userId);
    project.setState(ProjectState.DELETED.getCode());
    projectRepository.save(project);
    LOGGER.info("项目 {} 删除成功", project);
  }

  public Project findProject(Long projectId, Long userId) {
    return Optional.ofNullable(projectRepository.findByIdAndUserId(projectId, userId))
        .orElseThrow(() -> new ApiException(GatewayError.PROJECT_NOT_FOUND));
  }
}
