package com.paori.mobileServer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paori.mobileServer.dto.LoginInfoDTO;
import com.paori.mobileServer.service.LoginInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("logininfo")
@RequiredArgsConstructor
public class LoginInfoController {
	private final LoginInfoService loginInfoService;
	
	@GetMapping("location")
	public ResponseEntity<?> setLoginInfo(LoginInfoDTO dto) {
		String response = null;
		
		loginInfoService.registerLoginInfo(dto);
		response = "성공";
		
		return ResponseEntity.ok().body(response);
	}
}
