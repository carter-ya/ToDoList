package com.ifengxue.todolist.entity;

import com.ifengxue.base.entity.AbstractEntity;
import com.ifengxue.todolist.enums.TaskPriority;
import com.ifengxue.todolist.enums.TaskState;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_task")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Task extends AbstractEntity<Long> {

  private static final long serialVersionUID = 3775384908636766651L;
  @Column(nullable = false)
  private Long userId;
  @Column(nullable = false)
  private Long projectId;
  @Column(nullable = false)
  private Long parentId = 0L;
  @Column(nullable = false)
  private String title = "";
  @Column(nullable = false)
  private Integer priority = TaskPriority.LEVEL_FIVE.getCode();
  @Column(nullable = false)
  private String commentLocation = "";
  @Column(nullable = false)
  private Long startedAt = 0L;
  @Column(nullable = false)
  private Long endedAt = 0L;
  @Column(nullable = false)
  private Integer state = TaskState.NEW.getCode();

}
