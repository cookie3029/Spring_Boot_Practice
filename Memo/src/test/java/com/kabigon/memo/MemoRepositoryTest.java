package com.kabigon.memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.kabigon.memo.dto.MemoDTO;
import com.kabigon.memo.dto.PageRequestDTO;
import com.kabigon.memo.dto.PageResponseDTO;
import com.kabigon.memo.model.Memo;
import com.kabigon.memo.model.QMemo;
import com.kabigon.memo.persistence.MemoRepository;
import com.kabigon.memo.service.MemoService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@SpringBootTest
public class MemoRepositoryTest {
	@Autowired
	private MemoRepository memoRepository;
	
	// 데이터 삽입 테스트
	// @Test
	public void insertMemo() {
		// 300개의 정수 모임을 생성하고 순회
		IntStream.rangeClosed(1, 300).forEach(i -> {
			// 데이터 생성
			Memo memo = Memo.builder()
					.title("테스트 글_" + i)
					.content("컨텐츠_" + i)
					.writer("유저_" + (i % 10))
					.build();
			
			// 데이터 삽입
			memoRepository.save(memo);
		});
		// 확인은 데이터베이스 아래 구문 수행
		/*
			 select *
			 from memo;
		*/
	}
	
	// 데이터 수정 테스트
	// @Test
	public void updateMemo() {
		// 수정할 데이터 가져오기
		Optional<Memo> result = memoRepository.findById(300L);
		
		if(result.isPresent()) {
			Memo memo = result.get();
			
			memo.changeTitle("수정된 제목");
			memo.changeContent("수정된 내용");
			
			memoRepository.save(memo);
		} else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
		// 확인은 데이터베이스 아래 구문 수행
		/*
			 select *
			 from memo
			 where gno = 300;
		*/
	}
	
	// 데이터 삭제 테스트
	// @Test
	public void deleteMemo() {
		// 삭제 데이터 가져오기
		Optional<Memo> result =memoRepository.findById(300L);
		
		if(result.isPresent()) {
			Memo memo = result.get();
			memoRepository.delete(memo);
		} else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
		// 확인은 데이터베이스 아래 구문 수행
		/*
			 select *
			 from memo
			 where gno = 300;
		*/
	}
	
	// title이 테스트 글_1인 데이터 조회
	// @Test
	public void findByTitle() {
		List<Memo> list =memoRepository.findByTitle("테스트 글_1");
		System.out.println(list);
	}
	
	// @Test
	public void modifyMemo() {
		int result = memoRepository.updateMemo("제목", "내용", 299L);
		System.out.println(result);
	}
	
	// title에 1이 포함된 데이터를 조회
	// @Test
	public void testQuery1() {
		// gno의 내림차순으로 정렬해서 0번 페이지의 데이터 중 10개를 가져오는 Pageable 객체
		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
		
		// QueryDSL이 만들어준 클래스를 이용해서 쿼리 생성
		// QueryDSL은 Entity에 명령을 직접 수행하지 않고 Q로 시작하는 별도의 메모리 공간에 명령을  수행
		QMemo qMemo = QMemo.memo;
		
		// title에 1이 포함된 쿼리를 생성
		String title = "1";
		
		// 검색할 조건을 가지는 JPQL(SQL) 생성기
		BooleanBuilder builder = new BooleanBuilder();
		
		// title에 1이 포함된 데이터를 조회하는 쿼리
		BooleanExpression expression = qMemo.title.contains(title);
		
		// 실제 쿼리로 생성 where title like "%1%"
		builder.and(expression);
		
		// 쿼리 수행
		Page<Memo> result = memoRepository.findAll(builder, pageable);
		
		for(Memo memo : result) {
			System.out.println(memo);
		}
	}
	
	// title 또는 content에 1이 포함되어 있고 gno가 0보다 큰 데이터 조회
	// @Test
	public void testQuery2() {
		// gno의 내림차순으로 정렬해서 0번 페이지의 데이터 중 10개를 가져오는 Pageable 객체
		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
		
		// QueryDSL이 만들어준 클래스를 이용해서 쿼리 생성
		// QueryDSL은 Entity에 명령을 직접 수행하지 않고 Q로 시작하는 별도의 메모리 공간에 명령을  수행
		QMemo qMemo = QMemo.memo;
		
		// 검색할 조건을 가지는 JPQL(SQL) 생성기
		BooleanBuilder builder = new BooleanBuilder();
		
		// 조건을 생성
		String keyword = "1";
		
		// title에 포함된 것
		BooleanExpression expTitle = qMemo.title.contains(keyword);
		
		// content에 포함된 것
		BooleanExpression expContent = qMemo.content.contains(keyword);
		
		// 2개의 조건을 or로 묶어주기
		BooleanExpression expAll = expTitle.or(expContent);
		
		builder.and(expAll);
		
		// gno가 0보다 큰 조건을 and로 묶어주기
		builder.and(qMemo.gno.gt(0L));
		
		// 쿼리 수행
		Page<Memo> result = memoRepository.findAll(builder, pageable);
		
		for (Memo memo : result) {
			System.out.println(memo);
		}
	}
}