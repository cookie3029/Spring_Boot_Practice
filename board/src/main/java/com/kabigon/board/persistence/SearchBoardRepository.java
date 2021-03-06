package com.kabigon.board.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kabigon.board.model.Board;

public interface SearchBoardRepository {
	// QueryDSL을 적용한 SQL을 실행하기 위한 메서드
	Board search();
	
	// 검색을 위한 메서드
	// 3개의 항목을 묶어서 사나의 클래스로 표현해도 됩니다.
	Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
