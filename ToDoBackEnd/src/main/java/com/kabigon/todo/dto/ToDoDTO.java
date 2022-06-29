package com.kabigon.todo.dto;

import java.time.LocalDateTime;

import com.kabigon.todo.model.ToDoEntity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoDTO {
	private String id;
	private String title;
	private boolean done;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	// 생성자는 리턴타입이 없고 메서드 이름은 클래스가 동일
	// ToDoEntity를 매개변수로 해서 ToDoDTO를 생성
	// 매개변수에 final을 붙인 것은 데이터의 정합성을 위해서 붙임(안 붙여도 됨)
	public ToDoDTO(final ToDoEntity todo) {
		this.id = todo.getId();
		this.title = todo.getTitle();
		this.done = todo.isDone();
		this.regDate = todo.getRegDate();
		this.modDate = todo.getModDate();
	}
	
	// DTO를 Entity로 변환해주는 메서드
	public static ToDoEntity toEntity(final ToDoDTO dto) {
		return ToDoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
	}
}
