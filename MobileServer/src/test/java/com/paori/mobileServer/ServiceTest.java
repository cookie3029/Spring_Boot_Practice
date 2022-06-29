package com.paori.mobileServer;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import com.paori.mobileServer.dto.ItemDTO;
import com.paori.mobileServer.dto.MemberDTO;
import com.paori.mobileServer.dto.PageRequestItemDTO;
import com.paori.mobileServer.dto.PageResponseItemDTO;
import com.paori.mobileServer.service.ItemService;
import com.paori.mobileServer.service.MemberService;

@SpringBootTest
public class ServiceTest {
	@Autowired
	private MemberService memberService;
	
	// 회원 가입 테스트
	// @Test
	public void testRegisterMember() {
		// 처음 추가를 할 때는 성공해야 하고 email과 name을 중복된 데이터로 설정을 해서 확인
		MemberDTO dto = MemberDTO.builder()
				.email("paori@naver.com")
				.password("123456")
				.name("파오리")
				.imageurl("paori.png")
				.ismanager(false)
				.tel("010-1111-1111")
				.build();
		
		String result = memberService.registerMember(dto);
		System.out.println(result);
	}
	
	// 회원 정보 가져오기
	// @Test
	public void testGetMember() {
		MemberDTO dto = MemberDTO.builder()
				.email("paori@naver.com")
				.build();
		
		MemberDTO result = memberService.getMember(dto);
		System.out.println(result);
	}
	
	// 로그인 테스트
	// @Test
	public void testLoginMember() {
		MemberDTO dto = MemberDTO.builder()
				.email("paori@naver.com")
				.password("123456")
				.build();
		
		MemberDTO result = memberService.loginMember(dto);
		System.out.println(result);
		
		// 오늘 날짜를 문자열로 생성
		// Calendar cal = Calendar.getInstance(); 
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String d = sdf.format(date);
		
		try {
			FileOutputStream fos = new FileOutputStream("E:\\" + d + ".txt", true);
			PrintWriter pw = new PrintWriter(fos);
			pw.println("내용");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 멤버 수정 테스트
	// @Test
	public void updateMember() {
		MemberDTO dto = MemberDTO.builder()
				.email("paori@naver.com")
				.password("123456")
				.name("Paori")
				.imageurl("greenOnion.png")
				.build();
		
		String result = memberService.updateMember(dto);
		System.out.println(result);
	}
	
	// 멤버 삭제 테스트
	// @Test
	public void deleteMember() {
		MemberDTO dto = MemberDTO.builder()
				.email("paori@naver.com")
				.build();
		
		String result = memberService.deleteMember(dto);
		System.out.println(result);
	}
	
	@Autowired
	private ItemService itemService;
	
	// 데이터 삽입
	// @Test
	public void testInsertItem() {
		for(int i = 0; i < 100; i++) {
			ItemDTO dto = ItemDTO.builder()
					.itemname("PopCorn_" + i)
					.price(3000)
					.description("팝콘_" + i)
					.pictureurl("popcorn_" + i + ".png")
					.email("paori@naver.com")
					.build();
			
			Long itemid = itemService.registerItem(dto);
			System.out.println(itemid);
		}
	}
	
	// 데이터 1개 가져오기
	// @Test
	public void testGetItem() {
		ItemDTO dto = ItemDTO.builder()
				.itemid(1L)
				.build();
		
		System.out.println(itemService.getItem(dto));
		
		dto = ItemDTO.builder()
				.itemid(105L)
				.build();
		
		System.out.println(itemService.getItem(dto));
	}
	
	// 페이지 단위로 가져오기
	// @Test
	public void testGetList() {
		PageRequestItemDTO dto = PageRequestItemDTO.builder()
				.page(2)
				.size(10)
				.build();
		
		PageResponseItemDTO result = itemService.getList(dto);
		
		System.out.println(result);
	}
	
	// 데이터 수정
	@Test
	public void testUpdateItem() {
		ItemDTO dto = ItemDTO.builder()
				.itemid(203L)
				.itemname("PopCorn_Special")
				.price(3000)
				.description("팝콘 스페셜")
				.pictureurl("popcorn_special.png")
				.email("paori@naver.com")
				.build();
		
		Long itemid = itemService.updateItem(dto);
		
		System.out.println(itemid);
	}
	
	// 아이템 삭제
	// @Test
	public void testDeleteItem() {
		ItemDTO dto = ItemDTO.builder()
				.itemid(204L)
				.build();
			
		Long itemid = itemService.deleteItem(dto);
		
		System.out.println(itemid);
	}
}
