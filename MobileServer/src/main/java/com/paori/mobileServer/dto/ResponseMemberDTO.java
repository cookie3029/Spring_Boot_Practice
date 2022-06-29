package com.paori.mobileServer.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMemberDTO {
	private String error;
	
	private String email;
	
	private String tel;
	private String name;
	private String imageurl;
	
	private Boolean ismanager;
	
	private LocalDateTime regdate;
	private LocalDateTime moddate;
	private LocalDateTime lastlogindate;
}
