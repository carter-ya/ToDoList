package com.ifengxue.todolist.web.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("重命名任务")
public class RenameTaskRequest extends NewTaskRequest {

  private static final long serialVersionUID = 2707034165000361107L;
}
