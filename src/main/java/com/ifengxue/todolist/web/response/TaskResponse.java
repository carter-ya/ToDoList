package com.ifengxue.todolist.web.response;

import com.ifengxue.todolist.entity.Task;
import com.ifengxue.todolist.enums.TaskState;
import com.ifengxue.todolist.util.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("任务信息")
public class TaskResponse {

  @ApiModelProperty("创建时间")
  private Long createdAt;
  @ApiModelProperty("任务截止时间")
  private Long endedAt;
  @ApiModelProperty("是否包含子任务")
  private Boolean empty;
  @ApiModelProperty("任务编号")
  private Long id;
  @ApiModelProperty("父任务编号")
  private Long parentId;
  @ApiModelProperty("优先级")
  private Integer priority;
  @ApiModelProperty("项目编号")
  private Long projectId;
  @ApiModelProperty("任务开始时间")
  private Long startedAt;
  @ApiModelProperty("任务标题")
  private String title;
  @ApiModelProperty("任务状态")
  private String state;

  public static TaskResponse from(Task task) {
    TaskResponse response = BeanUtil.copyProperties(task, TaskResponse.class, "state");
    response.setState(TaskState.find(task.getState()).getCnValue());
    return response;
  }
}
