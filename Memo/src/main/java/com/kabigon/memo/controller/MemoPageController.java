package com.kabigon.memo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kabigon.memo.dto.MemoDTO;
import com.kabigon.memo.dto.PageRequestDTO;
import com.kabigon.memo.model.Memo;
import com.kabigon.memo.service.MemoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
// 로그 기록을 편리하게 할 수 있도록 해주는 어노테이션
@Log4j2
// 인스턴스 변수 주입을 생성자에서 자동으로 처리하도록 해주는 어노테이션
@RequiredArgsConstructor
public class MemoPageController {
	private final MemoService memoService;
	
	@GetMapping("/")
	public String main() {
		// redirect할 때는 View의 이름을 적는 것이 아니고 요청을 적어야 합니다.
		return "redirect:/memo/list";
	}
	
	// 목록보기 요청을 처리
	@GetMapping("/memo/list")
	public void list(PageRequestDTO pageRequestDTO, Model model) {
		log.info("목록 보기...");
		
		model.addAttribute("result", memoService.getList(pageRequestDTO));
	}
	
	// 삽입 화면으로 이동하는 요청을 처리
	@GetMapping("/memo/register")
	public void register() {
		log.info("데이터 삽입 화면으로 이동");
		
	}
	
	// 데이터 삽입 요청을 처리
	@PostMapping("/memo/register")
	public String register(MemoDTO dto, Model model, HttpSession session, RedirectAttributes rAttr) {
		// 여기가 제대로 출력이 안되면 요청 URL과 View 이름을 확인하고 
		// Form의 경우라면 입력 요소의 name을 확인
		log.info("파라미터 : ", dto);
		
		// 삽입
		Long gno = memoService.insertMemo(dto);
		
		// model에 msg를 저장
		// model.addAttribute("msg", gno + " 삽입 성공!!");
		// session.setAttribute("msg", gno + " 삽입 성공!!");
		
		// 리다이렉트 할 때 데이터를 전달
		rAttr.addFlashAttribute("msg", gno + " 삽입 성공!!");
		
		return "redirect:/memo/list";
	}
	
	// 상세보기와 수정 처리를 위한 메서드
	@GetMapping({"/memo/read", "/memo/modify"})
	public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
		MemoDTO memo = memoService.read(gno);
		
		model.addAttribute("dto", memo);
	}
	
	@PostMapping("/memo/modify")
	public String modify(MemoDTO memo, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes rAttr) {
		log.info("dto : " + memo);
		
		memoService.modify(memo);
		
		// 상세보기로 이동할 때 필요한 gno 값과 page 값을 설정
		rAttr.addAttribute("gno", memo.getGno());
		rAttr.addAttribute("page", requestDTO.getPage());
		rAttr.addAttribute("type", requestDTO.getType());
		rAttr.addAttribute("keyword", requestDTO.getKeyword());
		
		return "redirect:/memo/read";
	}
	
	@PostMapping("/memo/remove")
	public String remove(long gno, RedirectAttributes rAttr) {
		log.info("gno : " + gno);
		
		memoService.remove(gno);
		
		rAttr.addFlashAttribute("msg", gno + " 삭제 완료");
		
		return "redirect:/memo/list";
	}
}