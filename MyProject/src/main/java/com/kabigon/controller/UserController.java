package com.kabigon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.user.UserRequestDTO;
import com.kabigon.dto.user.UserResponseDTO;
import com.kabigon.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("register")
	public ResponseEntity<?> registerMember(UserRequestDTO dto) {
		UserResponseDTO response;

		try {
			// 데이터 삽입 처리
			response = userService.registUser(dto);
		} catch (Exception e) {
			String error = e.getMessage();
			response = UserResponseDTO.builder().error(error).build();
		}

		return ResponseEntity.ok().body(response);
	}

	@PostMapping("login")
	public ResponseEntity<?> loginMember(UserRequestDTO dto) {
		UserResponseDTO response;

		try {
			// 로그인 처리
			response = userService.loginUser(dto);

			if (response == null) {
				response = UserResponseDTO.builder().error("없는 아이디, 혹은 잘못된 비밀번호입니다.").build();
			}
		} catch (Exception e) {
			String error = e.getMessage();
			response = UserResponseDTO.builder().error(error).build();
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("{userNo}")
	public ResponseEntity<?> getMember(@PathVariable Long userNo) {
		UserResponseDTO response;
		
		try {
			// 회원 정보 가져오기
			UserRequestDTO dto = UserRequestDTO.builder().userNo(userNo).build();
			UserEntity result = userService.getUser(dto);

			if (result == null) response = UserResponseDTO.builder().error("없는 아이디입니다.").build();
			else response = userService.entityToDto(result);

		} catch (Exception e) {
			String error = e.getMessage();
			response = UserResponseDTO.builder().error(error).build();
		}

		return ResponseEntity.ok().body(response);
	}

	@PutMapping("{userNo}")
	public ResponseEntity<?> updateMember(@PathVariable Long userNo, UserRequestDTO dto) {
		UserResponseDTO response = null;
		
		try {
			// 데이터 수정
			dto.setUserNo(userNo);
			response = userService.updateUser(dto);
		} catch (Exception e) {
			String error = e.getMessage();
			response = UserResponseDTO.builder().error(error).build();
		}

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("{userNo}")
	public ResponseEntity<?> deleteMember(@PathVariable Long userNo) {
		UserResponseDTO response = null;

		try {
			// 데이터 삭제 처리
			UserRequestDTO dto = UserRequestDTO.builder().userNo(userNo).build();
			response = userService.deleteUser(dto);

		} catch (Exception e) {
			String error = e.getMessage();
			response = UserResponseDTO.builder().error(error).build();
		}

		return ResponseEntity.ok().body(response);
	}

	// 안드로이드에서는 서버의 이미지를 다운 받아야 출력할 수 있기 때문에 이미지를 다운로드
	// 받을 수 있는 요청을 생성해야 합니다.
	@GetMapping("/download")
	public ResponseEntity<Object> download(String path) {
		return userService.download(path);
	}
}
