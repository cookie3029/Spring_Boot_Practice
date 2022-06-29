package com.kabigon.dto.user;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
	private Long userNo;

	private String userId;
	private String userPw;
	
	private String userName;
	private String userPicture;
	private String userIntro;
	
	private LocalDateTime joinDate;
	private LocalDateTime renewDate;
	private LocalDateTime lastLoginDate;

	private Boolean isManager;
	
	// 전송된 파일의 내용을 저장할 속성
	// 한번에 업로드 하는 파일이 여러 개라면 MultipartFile []
	private MultipartFile picture;
}
