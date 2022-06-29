package com.kabigon.board;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.kabigon.board.model.Board;
import com.kabigon.board.model.Member;
import com.kabigon.board.model.Reply;
import com.kabigon.board.persistence.BoardRepository;
import com.kabigon.board.persistence.MemberRepository;
import com.kabigon.board.persistence.ReplyRepository;

@SpringBootTest
public class RepositoryTest {
	@Autowired
	private MemberRepository memberRepository;
	
	// @Test
	public void insertMembers() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder()
					.email("pikachu" + i + "@test.com")
					.password("1111")
					.name("피카츄" + i)
					.build();
			
			memberRepository.save(member);
		});
	}
	
	@Autowired
	private BoardRepository boardRepository;
	
	// @Test
	public void insertBoards() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder().email("pikachu1@test.com").build();
			Board board = Board.builder()
						.title("제목..." + i)
						.content("내용..." + i)
						.member(member)
						.build();
			
			boardRepository.save(board);
		});
	}
	
	@Autowired
	private ReplyRepository replyRepository;
	
	// @Test
	public void insertReplies() {
		IntStream.rangeClosed(1, 300).forEach(i -> {
			// 1부터 100 사이의 정수를 랜덤하게 생성해서 Board 객체 생성
			long bno = (long)(Math.random() * 100) + 1;
			
			Board board = Board.builder().bno(bno).build();
			
			Reply reply = Reply.builder()
					.text("제목..." + i)
					.board(board)
					.build();
			
			replyRepository.save(reply);
		});
	}
	
	// 하나의 Board 데이터를 조회하는 메서드
	// @Test
	// @Transactional
	public void readBoard() {
		Optional<Board> result = boardRepository.findById(100L);
		
		// 데이터를 출력
		System.out.println(result.get());
		System.out.println(result.get().getMember());
	}
	
	// 하나의 Replyl 데이터를 조회하는 메서드
	// @Test
	public void readReply() {
		Optional<Reply> result = replyRepository.findById(700L);
		
		// 데이터를 출력
		System.out.println(result.get());
		System.out.println(result.get().getBoard());
	}
	
	// @Test
	public void testReadWithWriter() {
		// 데이터 조회
		Object result = boardRepository.getBoardWithMember(100L);
		
		// JPQL의 결과가 Object인 경우는 Object[]로 강제 형 변환해서 사용
		System.out.println(Arrays.toString((Object[]) result));
	}
	
	// @Test
	public void testGetBoardWithReply() {
		List<Object[]> result = boardRepository.getBoardWithReply(87L);
		
		for(Object[] arr : result) {
			System.out.println(Arrays.toString(arr));
		}
	}

	// @Test
	public void testWithReplyCount() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
		
		result.get().forEach(row -> {
			Object[] arr = (Object[])row;
			System.out.println(Arrays.toString(arr));
		});
	}
	
	// @Test
	public void testGetBoardByBno() {
		Object result =boardRepository.getBoardByBno(87L);
		Object[] arr = (Object[]) result;
		
		System.out.println(Arrays.toString(arr));
	}
	
	// @Test
	public void testSearch() {
		boardRepository.search();
	}
	
	// @Test
	public void testSearchPage() {
		Pageable pageable = PageRequest.of(0,  10, 
				Sort.by("bno").descending().and(Sort.by("title").ascending()));
		
		Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
		
		System.out.println(result);
	}
	
	@Test
	@Transactional
	// 여기에 @Transactional을 붙여도 해결이 되는데 이 경우는 toString을 호출할 때 하나의 트랜젝션으로 간주하고
	// Board를 가져오기 때문에 해결이 됩니다.
	public void testListByBoard() {
		List<Reply> replyList = replyRepository
				.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build());
		
		for (Reply reply : replyList) {
			System.out.println(reply);
		}
	}
}
