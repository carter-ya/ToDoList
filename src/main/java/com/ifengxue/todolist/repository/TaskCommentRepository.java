package com.ifengxue.todolist.repository;

import com.ifengxue.todolist.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

  TaskComment findByTaskId(Long taskId);
}
