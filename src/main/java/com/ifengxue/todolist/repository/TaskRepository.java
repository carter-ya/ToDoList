package com.ifengxue.todolist.repository;

import com.ifengxue.todolist.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

  @Query("select t from Task t where t.projectId = ?1 and t.parentId = ?2 and t.state <> 4")
  List<Task> findByProjectIdAndParentId(Long projectId, Long parentId);
}
