package com.paori.mobileServer.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;

import com.paori.mobileServer.dto.MemberDTO;
import com.paori.mobileServer.model.Member;

public interface MemberService {
	public String registerMember(MemberDTO dto);	// 회원 삽입
	public String updateMember(MemberDTO dto);		// 회원 수정
	public String deleteMember(MemberDTO dto);		// 회원 삭제
	
	public MemberDTO loginMember(MemberDTO memberDTO);	
	public MemberDTO getMember(MemberDTO memberDTO);
	
	public ResponseEntity<Object> download(String path);
	
	// DTO 클래스의 객체를 Model 클래스의 객체로 변환
	public default Member dtoToEntity(MemberDTO dto) {
		String password = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
		
		Member member = Member.builder()
				.email(dto.getEmail())
				.tel(dto.getTel())
				.ismanager(dto.getIsmanager())
				.name(dto.getName())
				.password(password)
				.imageurl(dto.getImageurl())
				.lastlogindate(dto.getLastlogindate())
				.build();
		
		return member;
	}
	
	// Model 객체를 DTO 클래스로 변환
	public default MemberDTO entityToDto(Member member) {
		MemberDTO dto = MemberDTO.builder()
				.email(member.getEmail())
				.tel(member.getTel())
				.name(member.getName())
				.tel(member.getTel())
				.ismanager(member.getIsmanager())
				.imageurl(member.getImageurl())
				.regdate(member.getRegDate())
				.moddate(member.getModDate())
				.lastlogindate(member.getLastlogindate())
				.build();
		
		return dto;
	}
}
