package com.kabigon.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kabigon.domain.todo.TodoEntity;
import com.kabigon.domain.user.UserEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
	TodoEntity findByTodoNo(Long todoNo);

	TodoEntity findByTodoUser(UserEntity user);

	@Query("select t from TodoEntity t where t.todoUser=:user and t.expiryDate > :now")
	List<TodoEntity> getCurrentTodoList(@Param("user") UserEntity user, @Param("now") LocalDateTime now);

	@Query("select t from TodoEntity t where t.todoUser=:user and t.expiryDate < :now")
	List<TodoEntity> getPreTodoList(@Param("user") UserEntity user, @Param("now") LocalDateTime now);
}
