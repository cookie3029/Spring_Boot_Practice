package com.kabigon.memo.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kabigon.memo.dto.MemoDTO;
import com.kabigon.memo.dto.PageRequestDTO;
import com.kabigon.memo.dto.PageResponseDTO;
import com.kabigon.memo.model.Memo;
import com.kabigon.memo.model.QMemo;
import com.kabigon.memo.persistence.MemoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// 로그 기록을 위한 어노테이션 - log.레벨(메시지)를 이용해서 로그를 출력하는 것이 가능
@Log4j2
@Service
// 생성자를 이용해서 주입하기 위한 어노테이션
// Autowired를 이용해서 주입받으면 주입받는 시점을 예측하기 어렵기 때문에 비추천
@RequiredArgsConstructor
public class MemoServiceImpl implements MemoService {
	// 주입받기 위한 Repository - 생성자에서 주입받기 위해서는 final로 만들어져야 합니다.
	private final MemoRepository memoRepository;
	
	public Long insertMemo(MemoDTO dto) {
		log.info("=========데이터 삽입=========");
		log.info(dto);
		
		// DTO를 Entity로 변환
		Memo memo = dtoToEntity(dto);
		
		// 데이터 저장하고 저장한 내용을 memo에 다시 기록
		memoRepository.save(memo);
		
		return memo.getGno();
	}
	
	// 검색 조건을 만들어 주는 메서드
	private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
		// querydal에서 사용할 검색 조건을 만드는 객체 생성
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		// 검색 항목을 읽음
		String type = requestDTO.getType();
		
		// querydsl이 Entity에 적용할 검색 Entity를 제공하는데 그 객체를 가져옴
		QMemo qMemo = QMemo.memo;
		
		// 검색에 사용할 값
		String keyword = requestDTO.getKeyword();
		
		// gno 값이 0보다 큰 데이터만 조회
		BooleanExpression expression = qMemo.gno.gt(0L);
		
		booleanBuilder.and(expression);
		
		// 검색 조건이 없을 때
		if(type == null || type.trim().length() == 0) {
			return booleanBuilder;
		}
		
		// 검색 조건이 있는 경우
		BooleanBuilder conditionBuilder = new BooleanBuilder();
		
		if(type.contains("t")) {
			conditionBuilder.or(qMemo.title.contains(keyword));
		}
		if(type.contains("c")) {
			conditionBuilder.or(qMemo.content.contains(keyword));
		}
		if(type.contains("w")) {
			conditionBuilder.or(qMemo.writer.contains(keyword));
		}
		
		// 모든 조건 통합
		booleanBuilder.and(conditionBuilder);
		
		return booleanBuilder;
	}

	public PageResponseDTO<MemoDTO, Memo> getList(PageRequestDTO requestDTO) {
		// 정렬 조건을 적용해서 페이징 객체를 생성
		Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
		
		// 검색 조건을 생성
		BooleanBuilder booleanBuilder = getSearch(requestDTO);
		
		// 검색과 정렬 조건을 적용해서 조회
		Page<Memo> result = memoRepository.findAll(booleanBuilder, pageable);

		// Entity를 DTO로 변환해주는 함수 설정
		Function<Memo, MemoDTO> fn = (entity -> entityToDto(entity));
		
		return new PageResponseDTO<>(result, fn);
	}

	public MemoDTO read(Long gno) {
		// 기본 키를 이용해서 데이터 찾아오기
		Optional<Memo> memo = memoRepository.findById(gno);
		
		return memo.isPresent() ? entityToDto(memo.get()) : null;
	}

	public void modify(MemoDTO dto) {
		// 데이터 찾아오기
		Optional<Memo> result = memoRepository.findById(dto.getGno());
		
		if(result.isPresent()) {
			Memo memo = result.get();
			memo.changeTitle(dto.getTitle());
			memo.changeContent(dto.getContent());
			memoRepository.save(memo);
		}
	}

	public void remove(Long gno) {
		// 데이터 찾아오기
		Optional<Memo> result = memoRepository.findById(gno);
		
		if(result.isPresent()) {
			memoRepository.deleteById(gno);
		}
	}
}
