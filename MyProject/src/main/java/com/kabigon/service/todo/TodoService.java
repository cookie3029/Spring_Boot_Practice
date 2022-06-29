package com.kabigon.service.todo;

import java.util.List;

import com.kabigon.domain.todo.TodoEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.todo.TodoRequestDTO;
import com.kabigon.dto.todo.TodoResponseDTO;

public interface TodoService {
	public TodoResponseDTO create(TodoRequestDTO dto);
	
    public TodoResponseDTO update(Long todoNo);
    public TodoResponseDTO delete(Long todoNo);
    
    public List<TodoResponseDTO> getCurrentTodoList(Long userNo);
    public List<TodoResponseDTO> getPreTodoList(Long userNo);
    
	public default TodoEntity dtoToEntity(TodoRequestDTO dto) {
		UserEntity user = UserEntity.builder().userNo(dto.getUserNo()).build();
		
		return TodoEntity.builder()
				.todoUser(user)
				.todoContent(dto.getTodoContent())
				.todoColor(dto.getTodoColor())
				.expiryDate(dto.getExpiryDate())
				.build();
	}
	
	public default TodoResponseDTO entityToDto(TodoEntity todo) {
		return TodoResponseDTO.builder()
				.todoNo(todo.getTodoNo())
				.todoContent(todo.getTodoContent())
				.todoColor(todo.getTodoColor())
				.expiryDate(todo.getExpiryDate())
				.isComplete(todo.getIsComplete())
				.build();
	}
}
