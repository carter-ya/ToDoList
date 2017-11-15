package com.ifengxue.todolist.entity;

import com.ifengxue.base.entity.AbstractEntity;
import com.ifengxue.todolist.enums.ProjectState;
import com.ifengxue.todolist.web.request.NewProjectRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_project")
@Data
@EqualsAndHashCode(callSuper = true)
public class Project extends AbstractEntity<Long> {

  private static final long serialVersionUID = -1091100239085243309L;
  @Column(nullable = false)
  private Long userId;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private Long totalTask = 0L;
  @Column(nullable = false)
  private Long totalFinishedTask = 0L;
  @Column(nullable = false)
  private Integer state = ProjectState.VALID.getCode();

  public static Project from(Long userId, NewProjectRequest request) {
    Project project = new Project();
    project.setUserId(userId);
    project.setName(request.getName());
    return project;
  }
}
