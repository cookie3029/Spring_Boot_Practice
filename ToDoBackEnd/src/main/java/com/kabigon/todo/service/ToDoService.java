package com.kabigon.todo.service;

import java.util.List;

import com.kabigon.todo.model.ToDoEntity;

public interface ToDoService {
	// 데이터 삽입
	public List<ToDoEntity> create(final ToDoEntity entity);
	
	// userId에 해당하는 데이터 조회
	public List<ToDoEntity> retrieve(final String userId);
	
	// id에 해당하는 데이터 조회
	public ToDoEntity detail(final String id);
	
	// 데이터 수정
	public List<ToDoEntity> update(final ToDoEntity entity);
	
	// 데이터 삭제
	public List<ToDoEntity> delete(final ToDoEntity entity);
	
	// 지난 번에는 DTO와 Entity의 변환 Service에서 했습니다.
	// 장점은 DTO와 Entity의 변환 작업을 Service에서 호출하기 때문에 자기한테 만들어져 있으면 코드가 읽기 편해진다는 것
	// 단점은 Business Logic과 그렇지 않은 Logic이 한 곳에 있어서 역할의 경계가 애매해지고 유지보수가 어려워질 수 있다는 것
}
