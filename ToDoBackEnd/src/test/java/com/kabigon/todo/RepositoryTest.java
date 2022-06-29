package com.kabigon.todo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.todo.model.ToDoEntity;
import com.kabigon.todo.persistence.ToDoRepository;

@SpringBootTest
public class RepositoryTest {
	@Autowired
	private ToDoRepository toDoRepository;
	
	// 삽입 확인
	// @Test
	public void testInsert() {
		ToDoEntity toDoEntity = ToDoEntity.builder().userId("Kabigon").title("테스트용 데이터").build();
		toDoRepository.save(toDoEntity);
	}
	
	// 수정 확인
	// @Test
	public void testUpdate() {
		ToDoEntity toDoEntity = ToDoEntity.builder()
				.id("4028b70b80f4447b0180f44480ad0000")
				.userId("Kabigon")
				.title("수정 데이터")
				.build();
		
		toDoRepository.save(toDoEntity);
	}
	
	// 하나의 데이터를 가져오는 메서드 확인
	// @Test
	public void testDetail() {
		// 기본키를 가지고 데이터를 조회
		Optional<ToDoEntity> result = toDoRepository.findById("4028b70b80f4447b0180f44480ad0000");
		
		// 데이터가 존재할 때
		if(result.isPresent()) {
			System.out.println(result.get());
		} else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
	}
	
	// 기본키가 아닌 것을 가지고 조회
	// @Test
	public void testList() {
		// 기본키가 아닌 조건을 가지고 데이터를 조회
		List<ToDoEntity> result = toDoRepository.findByUserId("Kabigon");
		
		// 데이터가 존재할 때
		if(result.size() > 0) {
			for(ToDoEntity todo : result) {
				System.out.println(todo);
			}
		} else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
	}
	
	// 삭제는 기본키를 가지고 지우는 것과 Entity를 이용해서 지우는 2가지를 제공
	@Test
	public void testDelete() {
		toDoRepository.deleteById("4028b70b80f4447b0180f44480ad0000");
	}
}
