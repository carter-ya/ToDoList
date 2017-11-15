package com.ifengxue.todolist;

import com.ifengxue.todolist.entity.Project;
import com.ifengxue.todolist.entity.Task;
import com.ifengxue.todolist.entity.User;
import com.ifengxue.todolist.util.BeanUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ResponseGenerator {

  public static void generate(Class<?> clazz, String suffix, String path,
      String... ignoreProperties) {
    PropertyDescriptor[] descriptors = BeanUtil.findPropertyDescriptors(clazz);
    Set<String> ignorePropertySet =
        (ignoreProperties == null || ignoreProperties.length == 0) ? Collections.emptySet()
            : new HashSet<>(Arrays.asList(ignoreProperties));
    StringBuilder sb = new StringBuilder(1024);
    sb.append("import ").append(clazz.getName()).append(";\n");
    boolean supportSwaggerUI = false;
    try {
      Class.forName("io.swagger.annotations.ApiModel");
      supportSwaggerUI = true;
      sb.append("import io.swagger.annotations.ApiModel;\n");
      sb.append("import io.swagger.annotations.ApiModelProperty;\n");
    } catch (ClassNotFoundException e) {
    }
    boolean supportLombok = false;
    try {
      Class.forName("lombok.Data");
      supportLombok = true;
      sb.append("import lombok.Data;\n\n");
    } catch (ClassNotFoundException e) {
    }
    if (supportLombok) {
      sb.append("@Data\n");
    }
    if (supportSwaggerUI) {
      sb.append("@ApiModel(\"\")\n");
    }
    sb.append("public class ").append(clazz.getSimpleName()).append(suffix).append(" {\n");
    String tab = "  ";
    for (PropertyDescriptor descriptor : descriptors) {
      String name = descriptor.getName();
      if (ignorePropertySet.contains(name)) {
        continue;
      }
      if (supportSwaggerUI) {
        sb.append(tab).append("@ApiModelProperty(\"\")\n");
      }
      sb.append(tab).append("private ").append(descriptor.getPropertyType().getSimpleName())
          .append(" ").append(name).append(";\n");
    }
    sb.append(tab).append("public static ").append(clazz.getSimpleName()).append(suffix)
        .append(" from(").append(clazz.getSimpleName()).append(" ").append(
        Introspector.decapitalize(clazz.getSimpleName())).append(") {\n");
    sb.append(tab).append(tab).append("return null;\n");
    sb.append(tab).append("}\n");
    sb.append("}\n");
    if (path != null) {
      try {
        Files.write(Paths.get(path), sb.toString().getBytes(StandardCharsets.UTF_8));
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(new StringSelection(sb.toString()), null);
    }
  }

  public static void main(String[] args) {
    generate(Task.class, "Response", null, "class", "new", "userId", "state", "updatedAt", "version");
  }
}
