package com.ifengxue.todolist.entity;

import com.ifengxue.todolist.enums.TaskState;
import com.ifengxue.todolist.web.request.NewTaskRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_task")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Task extends AbstractEntity<Long> {

  private static final long serialVersionUID = 3775384908636766651L;
  @Column(nullable = false)
  private Long userId;
  @Column(nullable = false)
  private Long projectId;
  @Column(nullable = false)
  @Builder.Default
  private Long parentId = 0L;
  @Column(nullable = false)
  @Builder.Default
  private String title = "";
  @Column(nullable = false)
  @Builder.Default
  private Integer priority = 5;
  @Column(nullable = false)
  @Builder.Default
  private Boolean empty = Boolean.TRUE;
  @Column(nullable = false)
  @Builder.Default
  private Long startedAt = 0L;
  @Column(nullable = false)
  @Builder.Default
  private Long endedAt = 0L;
  @Column(nullable = false)
  @Builder.Default
  private Long finishedAt = 0L;
  @Column(nullable = false)
  @Builder.Default
  private Integer state = TaskState.NEW.getCode();

  public static Task fromNew(Long userId, Long projectId, Long parentId, NewTaskRequest request) {
    return Task.builder().userId(userId).projectId(projectId).parentId(parentId)
        .title(request.getName()).build();
  }
}
