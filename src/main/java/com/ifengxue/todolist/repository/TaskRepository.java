package com.ifengxue.todolist.repository;

import com.ifengxue.todolist.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

  @Query("select t from Task t where t.projectId = ?1 and t.parentId = ?2 and t.state <> 4")
  List<Task> findByProjectIdAndParentId(Long projectId, Long parentId);

  @Query("select t from Task t where t.id = ?1 and t.userId = ?2 and t.projectId = ?3 and t.state <> 4")
  Task findByIdAndUserIdAndProjectId(Long id, Long userId, Long projectId);

  @Query("select t from Task t where t.id = ?1 and t.userId = ?2 and t.state <> 4")
  Task findByIdAndUserId(Long id, Long userId);

  @Query(value = "select count(t) from Task t where t.parentId = ?1 and t.state <> 4")
  long countByParentId(Long parentId);
}
