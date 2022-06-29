package com.kabigon.board.service;

import java.util.List;

import com.kabigon.board.dto.ReplyDTO;
import com.kabigon.board.model.Board;
import com.kabigon.board.model.Reply;

public interface ReplyService {
	// 댓글 등록
	public Long register(ReplyDTO replyDTO);
	
	// 게시글 번호를 이용해서 댓글의 목록을 가져오는 메서드
	public List<ReplyDTO> getList(Long bno);
	
	// 댓글을 수정하는 메서드
	public void modify(ReplyDTO replyDTO);
	
	// 댓글을 삭제하는 메서드
	public void remove(Long rno);
	
	// ReplyDTO 객체를 Reply 객체로 변환하는 메서드
	default Reply dtoToEntity(ReplyDTO replyDTO) {
		// ReplyDTO는 bno를 가지고 있지만 Reply Entity는 Borad를 가지고 있습니다.
		Board board = Board.builder().bno(replyDTO.getBno()).build();
		Reply reply = Reply.builder()
				.rno(replyDTO.getRno())
				.text(replyDTO.getText())
				.replyer(replyDTO.getReplyer())
				.board(board)
				.build();
		
		return reply;
	}
	
	// Reply Entity를 ReplyDTO 객체로 변환하는 메서드
	default ReplyDTO entityToDto(Reply reply) {
		ReplyDTO dto = ReplyDTO.builder()
				.rno(reply.getRno())
				.text(reply.getText())
				.replyer(reply.getReplyer())
				.regDate(reply.getRegDate())
				.modDate(reply.getModDate())
				.build();
		
		return dto;
	}
}
