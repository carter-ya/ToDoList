package com.ifengxue.todolist.repository;

import com.ifengxue.todolist.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  @Query("select p from Project p where p.userId = ?1 and p.state = 1")
  List<Project> findByUserId(Long userId);

  @Query("select p from Project p where p.id = ?1 and p.userId = ?2 and p.state = 1")
  Project findByIdAndUserId(Long projectId, Long userId);
}
