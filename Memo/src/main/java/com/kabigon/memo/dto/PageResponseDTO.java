package com.kabigon.memo.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

// 재활용을 위해서 템플릿을 적용
// DTO는 변환할 DTO 클래스가 대입되고
// EN은 Entity와 DTO 사이의 변환을 수행해 줄 메서드
// Java에서는 Generic이라고 하고 객체 지향에서 템플릿 프로그래밍이라고 합니다.
// 알고리즘을 구현할 때, 혹은 프레임워크를 만들 때 중요!
@Data
public class PageResponseDTO<DTO, EN> {
	// DTO의 List
	private List<DTO> dtoList;
	
	// 전체 페이지 개수
	private int totalPage;
	
	// 현재 페이지 번호
	private int page;
	
	// 이전 페이지 목록 여부
	private boolean prev;
	
	// 다음 페이지 목록 여부
	private boolean next;
	
	// 출력할 페이지 번호 개수
	private int size;
	
	// 시작하는 페이지 번호
	private int start;
	
	// 끝나는 페이지 번호
	private int end;
	
	// 출력할 페이지 번호 목록
	private List<Integer> pageList;
	
	// 출력할 페이지 번호를 계산하는 메서드
	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() + 1;
		this.size = pageable.getPageSize();
		
		int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;
			
		start = tempEnd - 9;
		prev = start > 1;
		end = totalPage > tempEnd ? tempEnd : totalPage;
		
		next = end != totalPage;
		
		pageList = IntStream.rangeClosed(start,  end).boxed().collect(Collectors.toList());
	}
	
	// Page 객체와 함수를 적용해서 List로 변환해주는 메서드
	public PageResponseDTO(Page<EN> result, Function<EN, DTO> fn) {
		// 출력할 데이터 목록 생성
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		
		// 페이지 번호 생성
		totalPage = result.getTotalPages();
		
		makePageList(result.getPageable());
	}
}
