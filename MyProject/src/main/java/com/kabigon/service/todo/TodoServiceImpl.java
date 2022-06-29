package com.kabigon.service.todo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kabigon.domain.todo.TodoEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.todo.TodoRequestDTO;
import com.kabigon.dto.todo.TodoResponseDTO;
import com.kabigon.dto.user.UserRequestDTO;
import com.kabigon.repository.TodoRepository;
import com.kabigon.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
	private final UserService userService;
	private final TodoRepository todoRepository;
	
	@Override
	public TodoResponseDTO create(TodoRequestDTO dto) {
		TodoEntity todo = dtoToEntity(dto);
		
		todoRepository.save(todo);
		
		return TodoResponseDTO.builder().isSuccess(true).build();
	}
	
	@Override
	public TodoResponseDTO update(Long todoNo) {
		TodoEntity todo = todoRepository.findByTodoNo(todoNo);		
		
		if(todo == null) {
			return TodoResponseDTO.builder().isSuccess(false).error("해당 게시글이 존재하지 않습니다.").build();
		}
		
		todo.updateTodo();
		todoRepository.save(todo);
		
		return TodoResponseDTO.builder().isSuccess(true).build();
	}
	
	@Override
	public TodoResponseDTO delete(Long todoNo) {
		TodoEntity todo = todoRepository.findByTodoNo(todoNo);	
		
		if(todo == null) {
			return TodoResponseDTO.builder().isSuccess(false).error("해당 게시글이 존재하지 않습니다.").build();
		}
		
		todoRepository.delete(todo);
		
		return TodoResponseDTO.builder().isSuccess(true).build();
	}
	
	@Override
	public List<TodoResponseDTO> getCurrentTodoList(Long userNo) {
		UserRequestDTO dto = UserRequestDTO.builder().userNo(userNo).build();
		UserEntity user = userService.getUser(dto);
		
		List<TodoEntity> list = todoRepository.getCurrentTodoList(user, LocalDateTime.now());
		return list.stream().map(todo -> {
			return entityToDto(todo);
		}).collect(Collectors.toList());
	}
	
	@Override
	public List<TodoResponseDTO> getPreTodoList(Long userNo) {
		UserRequestDTO dto = UserRequestDTO.builder().userNo(userNo).build();
		UserEntity user = userService.getUser(dto);
		
		List<TodoEntity> list = todoRepository.getPreTodoList(user, LocalDateTime.now());
		
		return list.stream().map(todo -> {
			return entityToDto(todo);
		}).collect(Collectors.toList());
	}
}
