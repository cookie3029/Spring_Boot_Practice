package com.kabigon.board.service;

import com.kabigon.board.dto.BoardDTO;
import com.kabigon.board.dto.PageRequestDTO;
import com.kabigon.board.dto.PageResultDTO;
import com.kabigon.board.model.Board;
import com.kabigon.board.model.Member;

public interface BoardService {
	// 게시물 등록을 위한 메서드
	Long register(BoardDTO dto);
	
	// 목록 보기 메서드
	PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
	
	// 상세보기 메서드
	BoardDTO getBoard(Long bno);
	
	// 게시글 수정 메서드
	void modifyBoard(BoardDTO dto);
	
	// 게시글 삭제 메서드
	void removeWithReplies(Long bno);
	
	
	// DTO를 Entity로 변환해주는 메서드
	default Board dtoToEntity(BoardDTO dto) {
		Member member = Member.builder().email(dto.getMemberEmail()).build();
		
		Board board = Board.builder().bno(dto.getBno())
				.title(dto.getTitle())
				.content(dto.getContent())
				.member(member)
				.build();
		
		return board;
	}
	
	// Entity를 DTO로 변환해주는 메서드
	default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
		BoardDTO boardDTO = BoardDTO.builder()
				.bno(board.getBno())
				.title(board.getTitle())
				.content(board.getContent())
				.regDate(board.getRegDate())
				.modDate(board.getModDate())
				.memberEmail(member.getEmail())
				.memberName(member.getName())
				.replyCount(replyCount.intValue())
				.build();
		
		return boardDTO;
	}
}
