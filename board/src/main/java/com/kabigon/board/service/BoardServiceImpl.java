package com.kabigon.board.service;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kabigon.board.dto.BoardDTO;
import com.kabigon.board.dto.PageRequestDTO;
import com.kabigon.board.dto.PageResultDTO;
import com.kabigon.board.model.Board;
import com.kabigon.board.model.Member;
import com.kabigon.board.persistence.BoardRepository;
import com.kabigon.board.persistence.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	@Override
	public Long register(BoardDTO dto) {
		log.info(dto);
		
		Board board = dtoToEntity(dto);
		
		boardRepository.save(board);
		
		return board.getBno();
	}

	@Override
	public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
		log.info(pageRequestDTO);
		
		// Entity를 DTO로 변환해주는 함수 생성
		// Repository에 있는 메서드의 결과가 Object[]인데 이 배열의 요소를 가지고 
		// BoardDTO를 생성해서 출력해야 함
		Function<Object[], BoardDTO> fn = (en -> 
			entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));
		
		// 데이터를 조회 - bno의 내림차순 적용
		// 상황에 따라서는 regDate나 modDate로 정렬하는 경우도 있음
		// Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
		
		Page<Object[]> result = boardRepository.searchPage(
				pageRequestDTO.getType(), 
				pageRequestDTO.getKeyword(),
				pageRequestDTO.getPageable(Sort.by("bno").descending()));
			
		return new PageResultDTO<>(result, fn);
	}

	@Override
	public BoardDTO getBoard(Long bno) {
		// bno를 이용해서 하나의 데이터 가져오기
		// Board, Member, Long - 댓글 개수
		Object result = boardRepository.getBoardByBno(bno);
		Object[] arr = (Object[]) result;
		
		return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
	}

	// 이 메서드 안의 작업은 하나의 트랜젝션으로 처리해달라고 요청
	@Transactional
	@Override
	public void removeWithReplies(Long bno) {
		// 댓글 삭제
		replyRepository.deleteByBno(bno);
		
		// 게시글 삭제
		boardRepository.deleteById(bno);
	}

	@Transactional
	@Override
	public void modifyBoard(BoardDTO dto) {
		// 데이터의 존재 여부를 확인
		Optional<Board> board = boardRepository.findById(dto.getBno());
		
		if(board.isPresent()) {
			board.get().changeTitle(dto.getTitle());
			board.get().changeContent(dto.getContent());
			
			boardRepository.save(board.get());
		}
	}
}
