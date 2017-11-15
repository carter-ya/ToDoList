package com.ifengxue.todolist.web.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("更新项目")
@EqualsAndHashCode(callSuper = true)
public class RenameProjectRequest extends NewProjectRequest {

  private static final long serialVersionUID = 9044375967268007633L;
}
