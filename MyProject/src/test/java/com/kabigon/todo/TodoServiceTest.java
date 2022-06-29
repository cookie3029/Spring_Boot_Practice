package com.kabigon.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.dto.todo.TodoResponseDTO;
import com.kabigon.service.todo.TodoService;

@SpringBootTest
public class TodoServiceTest {
	@Autowired
	private TodoService todoService;
	
	@Test
	public void updateTodo() {
		TodoResponseDTO dto = todoService.update(106L);
		
		System.out.println(dto.toString());
	}
}
