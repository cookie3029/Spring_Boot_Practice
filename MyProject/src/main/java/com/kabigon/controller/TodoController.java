package com.kabigon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabigon.dto.todo.TodoRequestDTO;
import com.kabigon.dto.todo.TodoResponseDTO;
import com.kabigon.service.todo.TodoService;
import com.kabigon.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {
	private final TodoService todoService;

	@PostMapping("create")
	public ResponseEntity<?> createTodo(@RequestBody TodoRequestDTO dto) {
		TodoResponseDTO response = todoService.create(dto);

		return ResponseEntity.ok().body(response);
	}

	@PostMapping("update/{todoNo}")
	public ResponseEntity<?> update(@PathVariable Long todoNo) {
		TodoResponseDTO response = todoService.update(todoNo);

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("current/{userNo}")
	public ResponseEntity<?> getCurrentTodoList(@PathVariable Long userNo) {
		List<TodoResponseDTO> response = todoService.getCurrentTodoList(userNo);

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("pre/{userNo}")
	public ResponseEntity<?> getPreTodoList(@PathVariable Long userNo) {
		List<TodoResponseDTO> response = todoService.getPreTodoList(userNo);

		return ResponseEntity.ok().body(response);
	}

	@PostMapping("{todoNo}")
	public ResponseEntity<?> deleteTodo(@PathVariable Long todoNo) {
		TodoResponseDTO response = todoService.delete(todoNo);
		
		return ResponseEntity.ok().body(response);
	}
}
