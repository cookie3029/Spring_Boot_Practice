package com.kabigon.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.board.dto.BoardDTO;
import com.kabigon.board.dto.PageRequestDTO;
import com.kabigon.board.dto.PageResultDTO;
import com.kabigon.board.dto.ReplyDTO;
import com.kabigon.board.service.BoardService;
import com.kabigon.board.service.ReplyService;

@SpringBootTest
public class ServiceTest {
	@Autowired
	private BoardService boardService;

	@Autowired
	private ReplyService replyService;
	
	// @Test
	public void testRegister() {
		BoardDTO dto = BoardDTO.builder()
				.title("Test Title")
				.content("Test Content")
				.memberEmail("pikachu11@test.com")
				.build();
		
		Long bno = boardService.register(dto);
		System.out.println(bno);
	}
	
	// @Test
	public void testList() {
		PageRequestDTO pageRequestDTO = new PageRequestDTO();
		
		PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
		
		for(BoardDTO boardDTO : result.getDtoList()) {
			System.out.println(boardDTO);
		}
	}
	
	// @Test
	public void testGetBoard() {
		Long bno = 87L;		
		BoardDTO boardDTO = boardService.getBoard(bno);
		System.out.println(boardDTO);
	}
	
	// @Test
	public void testDeleteBoard() {
		Long bno = 16L;
		boardService.removeWithReplies(bno);
	}
	
	// @Test
	public void testModifyBoard() {
		BoardDTO boardDTO =  BoardDTO.builder()
				.bno(1L)
				.title("수정된 제목")
				.content("수정된 내용")
				.build();
		
		boardService.modifyBoard(boardDTO);
	}
	
	// 댓글 목록 가져오기 테스트
	@Test
	public void testGetList() {
		Long bno = 87L;
		List<ReplyDTO> replydtoList = replyService.getList(bno);
		replydtoList.forEach(replyDTO -> System.out.println(replyDTO));
	}
}
