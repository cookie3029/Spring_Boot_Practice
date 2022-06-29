package com.kabigon.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabigon.todo.model.ToDoEntity;
import com.kabigon.todo.persistence.ToDoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

	private final ToDoRepository toDoRepository;
	
	// 유효성 검사 메서드 - 인터페이스에 만들어도 되고 ServiceImpl에 만들어도 되는데 
	// 인터페이스에 만들면 public으로 만들어야 해서 외부에서 호출할 수 있게 됩니다.
	// 외부에서 호출할 수 없도록 할 때는 ServiceImpl에서 private로 구현하고 외부에서 호출이 가능하도록
	// 하고자 한다면 Service 인터페이스에 default로 만들면 됩니다.
	private void validate(final ToDoEntity entity) {
		if(entity == null) {
			log.warn("Entity Is Null");
			throw new RuntimeException("Entity Cannot Be Null");
		}		
		if(entity.getUserId() == null) {
			log.warn("Unknown User");
			throw new RuntimeException("Unknown User");
		}
	}
	
	@Override
	// 데이터 삽입
	public List<ToDoEntity> create(ToDoEntity entity) {
		// 유효성 검사
		validate(entity);
		
		// 데이터 삽입
		toDoRepository.save(entity);
		
		// 삽입한 유저의 데이터를 전부 조회해서 리턴
		return toDoRepository.findByUserId(entity.getUserId());
	}

	@Override
	// 데이터 조회
	public List<ToDoEntity> retrieve(String userId) {
		// 유저의 데이터를 전부 조회해서 리턴
		return toDoRepository.findByUserId(userId);
	}

	@Override
	// 데이터 상세보기
	public ToDoEntity detail(String id) {
		ToDoEntity toDo = null;
		Optional<ToDoEntity> result = toDoRepository.findById(id);
		
		if(result.isPresent()) {
			toDo = result.get();
		}
		
		return toDo;
	}

	@Override
	// 데이터 수정
	public List<ToDoEntity> update(ToDoEntity entity) {
		validate(entity);
		// toDoRepository.save(entity);
		
		// 데이터의 존재 여부 확인
		ToDoEntity toDo = detail(entity.getId());
		
		// 데이터가 존재하면 수정
		if(toDo != null) {
			toDoRepository.save(entity);
		}
		
		// 유저의 모든 데이터를 리턴
		return toDoRepository.findByUserId(entity.getUserId());
	}

	@Override
	public List<ToDoEntity> delete(ToDoEntity entity) {
		validate(entity);
		
		// 데이터의 존재 여부 확인
		ToDoEntity toDo = detail(entity.getId());
		
		// 데이터가 존재하면 삭제
		if(toDo != null) {
			toDoRepository.delete(entity);
			// toDoRepository.deleteById(entity.getId());
		}
		
		return toDoRepository.findByUserId(entity.getUserId());
	}
}
