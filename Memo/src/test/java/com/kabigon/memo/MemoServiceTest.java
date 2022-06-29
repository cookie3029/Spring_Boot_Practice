package com.kabigon.memo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.memo.dto.MemoDTO;
import com.kabigon.memo.dto.PageRequestDTO;
import com.kabigon.memo.dto.PageResponseDTO;
import com.kabigon.memo.model.Memo;
import com.kabigon.memo.service.MemoService;

@SpringBootTest
public class MemoServiceTest {
	@Autowired
	private MemoService memoService;
	
	// @Test
	// 삽입 테스트
	public void testInsert() {
		MemoDTO dto = MemoDTO.builder()
				.title("데이터 삽입 테스트")
				.content("삽입 성공")
				.writer("잠만보")
				.build();
		
		System.out.println(memoService.insertMemo(dto));
		// 데이터 베이스에 가서 확인
		// select * from memo order by gno desc;
	}
	
	// 목록 보기 테스트 - 목록 테스트
	// @Test
	public void testList() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
		PageResponseDTO<MemoDTO, Memo> resultDTO = memoService.getList(pageRequestDTO);
		
		for(MemoDTO memoDTO : resultDTO.getDtoList()) {
			System.out.println(memoDTO);
		}
	}

	// 목록 보기 테스트 - 목록과 페이지 번호 테스트
	// @Test
	public void testPageList() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(51).size(10).build();
		PageResponseDTO<MemoDTO, Memo> resultDTO = memoService.getList(pageRequestDTO);
		
		for (MemoDTO memoDTO : resultDTO.getDtoList()) {
			System.out.println(memoDTO);
		}
		
		// 이전과 다음 페이지 존재 여부
		System.out.println("이전 : " + resultDTO.isPrev());
		System.out.println("다음 : " + resultDTO.isNext());
		
		// 전체 페이지 개수
		System.out.println("전체 페이지 개수 : " + resultDTO.getTotalPage());
		
		// 페이지 번호 목록
		System.out.println(resultDTO.getPageList());
	}
	
	@Test
	public void testListSearch() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).type("tc").keyword("파이리").build();
		PageResponseDTO<MemoDTO, Memo> result = memoService.getList(pageRequestDTO);
		
		System.out.println(result.getDtoList());
	}
}
