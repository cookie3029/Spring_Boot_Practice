package com.kabigon.todo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.domain.todo.TodoEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.repository.TodoRepository;
import com.kabigon.repository.UserRepository;

@SpringBootTest
public class TodoRepositoryTest {
	@Autowired
	private TodoRepository toDoRepository;

	@Autowired
	private UserRepository userRepository;

	// @Test
	public void insertToDo() {
		UserEntity user = userRepository.findByUserNo(106L);

		TodoEntity todo = TodoEntity.builder().todoUser(user).todoContent("파닭먹기").todoColor("blue")
				.expiryDate(LocalDateTime.now()).build();

		toDoRepository.save(todo);
	}

	// @Test
	public void updateToDo() {
		UserEntity user = userRepository.findByUserNo(106L);

		TodoEntity todo = TodoEntity.builder().todoNo(1L).todoUser(user).todoContent("치킨먹기").todoColor("blue")
				.expiryDate(LocalDateTime.now()).build();

		toDoRepository.save(todo);
	}

	// @Test
	@Transactional
	public void findByTodoNo() {
		TodoEntity todo = Optional.ofNullable(toDoRepository.findByTodoNo(1L)).get();

		System.out.println(todo.toString());
	}

	@Test
	@Transactional
	public void findByTodoUser() {
		UserEntity user = UserEntity.builder().userNo(106L).build();
//		TodoEntity todo = Optional.ofNullable(toDoRepository.findByTodoUser(user)).get();
//		
//		System.out.println(todo.toString());

		List<TodoEntity> list = toDoRepository.getPreTodoList(user, LocalDateTime.now());

		for (TodoEntity todoEntity : list) {
			System.out.println(todoEntity.toString());
		}
	}

	// @Test
	public void deleteTodo() {
		UserEntity user = UserEntity.builder().userNo(106L).build();
		TodoEntity todo = Optional.ofNullable(toDoRepository.findByTodoUser(user)).get();

		toDoRepository.delete(todo);
	}
}
