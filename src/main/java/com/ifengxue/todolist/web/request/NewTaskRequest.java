package com.ifengxue.todolist.web.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("新建任务")
@EqualsAndHashCode(callSuper = true)
public class NewTaskRequest extends NewProjectRequest {
  private static final long serialVersionUID = -9023036475769007137L;
}
