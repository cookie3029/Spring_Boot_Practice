package com.kabigon.todo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabigon.todo.dto.ResponseDTO;
import com.kabigon.todo.dto.ToDoDTO;
import com.kabigon.todo.model.ToDoEntity;
import com.kabigon.todo.service.ToDoService;

import lombok.RequiredArgsConstructor;

// 데이터를 리턴하는 목적의 Controller를 위한 어노테이션
@RestController
// 공통된 URL 작성 - localhost:포트번호/todo
@RequestMapping("todo")
@RequiredArgsConstructor
public class ToDoController {
	private final ToDoService toDoService;
	
	// 데이터 삽입
	@PostMapping
	public ResponseEntity<?> createToDo(@RequestBody ToDoDTO dto) {
		try {
			// 회원 정보를 만들 수 없어서 임시로 회원 아이디를 설정
			String temporaryUserId = "temporary-user";
			
			// DTO를 Entity로 변환
			ToDoEntity entity = ToDoDTO.toEntity(dto);
			
			entity.setId(null);
			entity.setUserId(temporaryUserId);
			
			// 서비스의 삽입을 호출하고 결과를 저장
			List<ToDoEntity> entities = toDoService.create(entity);
			
			// ToDoEntity의 List를 ToDoDTO의 List로 변환
			List<ToDoDTO> list = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
			
			// 응답 객체를 생성
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(list).build();
			
			// 정상 응답 객체를 만든 후 본문은 response로 설정하여 반환
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	// 데이터 조회
	@GetMapping
	public ResponseEntity<?> retrieveToDoList() {
		String temporaryUserId = "temporary-user";
		List<ToDoEntity> entities = toDoService.retrieve(temporaryUserId);
		
		// ToDoEntity의 List를 ToDoDTO의 List로 변환
		List<ToDoDTO> list = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		
		// 응답 객체를 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(list).build();
		
		// 정상 응답 객체를 만든 후 본문은 response로 설정하여 반환
		return ResponseEntity.ok().body(response);
	}
	
	// 데이터 수정
	@PutMapping
	public ResponseEntity<?> updateToDo(@RequestBody ToDoDTO dto) {
		String temporaryUserId = "temporary-user";
		
		ToDoEntity entity = ToDoDTO.toEntity(dto);
		
		entity.setUserId(temporaryUserId);
		
		List<ToDoEntity> entities = toDoService.update(entity);
		
		// ToDoEntity의 List를 ToDoDTO의 List로 변환
		List<ToDoDTO> list = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		
		// 응답 객체를 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(list).build();
		
		// 정상 응답 객체를 만든 후 본문은 response로 설정하여 반환
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteToDo(@RequestBody ToDoDTO dto) {
		String temporaryUserId = "temporary-user";

		ToDoEntity entity = ToDoDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		
		List<ToDoEntity> entities = toDoService.delete(entity);
		
		// ToDoEntity의 List를 ToDoDTO의 List로 변환
		
		// entities.stream()은 List를 Stream으로 변환
		// map은 Stream의 모든 요소를 순서대로 매개변수로 대입된 함수를 적용해서 
		// 리턴한 값들을 가지고 다시 스트림으로 만들어주는 메서드
		// 클래스명::메서드 이름의 형태로 대입을 해야하는데 new는 생성자를 이용하겠다는 의미입니다.
		// collect는 Stream을 배열이나 List, Set, Map으로 변환해주는 함수
		
		// 여기서는 map으로 나온 결과를 List로 변환해 주었음
		List<ToDoDTO> list = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		
		// 응답 객체를 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(list).build();
		
		// 정상 응답 객체를 만든 후 본문은 response로 설정하여 반환
		return ResponseEntity.ok().body(response);
	}
}
