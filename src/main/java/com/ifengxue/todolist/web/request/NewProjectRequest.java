package com.ifengxue.todolist.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("新建项目")
@Data
public class NewProjectRequest implements Serializable {

  private static final long serialVersionUID = -2207354905305827196L;
  @NotEmpty(message = "INVALID_NAME")
  @Pattern(regexp = "\\S{1,100}", message = "INVALID_NAME")
  @ApiModelProperty("项目名称")
  private String name;
}
