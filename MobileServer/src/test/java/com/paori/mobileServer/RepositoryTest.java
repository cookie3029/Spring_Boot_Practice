package com.paori.mobileServer;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.paori.mobileServer.model.Item;
import com.paori.mobileServer.model.Member;
import com.paori.mobileServer.persistence.ItemRepository;
import com.paori.mobileServer.persistence.MemberRepository;

@SpringBootTest
public class RepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	// Member에 데이터 삽입
	@Test
	public void testRegisterMember() {
		String password = BCrypt.hashpw("123456", BCrypt.gensalt());

		Member member = Member.builder().email("paori@test.com").password(password).name("파오리").imageurl("paori.png")
				.tel("010-1234-1234").ismanager(false).build();

		memberRepository.save(member);
	}

	// 회원 정보 가져오기 - 수정이나 로그인에서 사용
	// @Test
	public void testGetMember() {
		Optional<Member> optional = memberRepository.findById("paori@test.com");

		if (optional.isPresent()) {
			Member member = optional.get();
			System.out.println(member);

			// 로그인은 이렇게 데이터를 가져와서 비밀번호를 비교하면 됩니다.
		} else {
			System.out.println("존재하지 않는 데이터입니다.");
		}
	}

	// 데이터 수정
	// @Test
	public void testUpdateMember() {
		String password = BCrypt.hashpw("111111", BCrypt.gensalt());

		Member member = Member.builder().email("paori@test.com").password(password).name("파오리").imageurl("paori.png")
				.tel("010-11111-1234").ismanager(false).build();

		memberRepository.save(member);
	}

	// 데이터 삭제
	// @Test
	public void testDeleteMember() {
		Member member = Member.builder().email("paori@test.com").build();

		memberRepository.delete(member);
		// memberRepository.deleteById(member.getEmail());
	}

	// 이름으로 데이터 조회
	// @Test
	public void testFindName() {
		String name = "파오리";
		List<Member> list = memberRepository.findMemberByName(name);
		System.out.println(list);
		System.out.println(list.get(0).getModDate());

		name = "잠만보";
		list = memberRepository.findMemberByName(name);
		System.out.println(list);
		
		if(list.size() > 0) {
			System.out.println("데이터가 존재합니다.");
		} else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
	}
	
	@Autowired
	private ItemRepository itemRepository;
	
	// Item 삽입을 테스트
	@Test
	public void testInsertItem() {
		// 외래키를 생성
		Member member = Member.builder()
				.email("paori@test.com")
				.build();
		
		for(int i = 100; i < 200; i++) {
			Item item = Item.builder()
					.itemname("대파_" + i)
					.price(2500)
					.description("파오리의 대파")
					.pictureurl("greenOnion.png")
					.member(member)
					.build();
			
			itemRepository.save(item);
		}
	}
	
	// 데이터 전체 보기 테스트
	// @Test
	public void getAll() {
		List<Item> list =itemRepository.findAll();
		System.out.println(list);
	}
	
	// 페이징과 정렬
	// @Test
	public void getPaging() {
		Sort sort = Sort.by("itemid").descending();
		Pageable pageable = PageRequest.of(0, 10, sort);
		Page<Item> page = itemRepository.findAll(pageable);
		page.get().forEach(item -> {
			System.out.println(item);
		});
	}
	
	// 외래키를 이용한 조회
	// @Test
	public void getFindByMember() {
		Member member = Member.builder()
				.email("paori@test.com")
				.build();
				
		List<Item> list = itemRepository.findItemByMember(member);
		System.out.println(list);
	}
	
	// 데이터 1개 가져오기
	// @Test
	public void getItem() {
		Optional<Item> item =itemRepository.findById(200L);
		
		if(item.isPresent()) {
			System.out.println(item.get());
		} else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
	}
	
	// 데이터 수정
	// @Test
	public void updateItem() {
		Member member = Member.builder()
				.email("paori@test.com")
				.build();
		
		Item item = Item.builder()
				.itemid(100L)
				.itemname("양파")
				.price(5000)
				.pictureurl("onion.png")
				.description("파오리의 양파")
				.member(member)
				.build();
		
		itemRepository.save(item);
	}
	
	// 데이터 삭제
	// @Test
	public void deleteItem() {
		Item item = Item.builder()
				.itemid(101L)
				.build();
		
		itemRepository.delete(item);
	}
}
