package com.ifengxue.todolist.web.response;

import com.ifengxue.todolist.entity.Project;
import com.ifengxue.todolist.util.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("项目信息")
public class ProjectResponse {

  @ApiModelProperty("创建时间")
  private Long createdAt;
  @ApiModelProperty("项目编号")
  private Long id;
  @ApiModelProperty("项目名称")
  private String name;
  @ApiModelProperty("项目总完成任务数量")
  private Long totalFinishedTask;
  @ApiModelProperty("项目总任务数量")
  private Long totalTask;

  public static ProjectResponse from(Project project) {
    return BeanUtil.copyProperties(project, ProjectResponse.class);
  }
}
