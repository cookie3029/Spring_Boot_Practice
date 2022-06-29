package com.kabigon.dto.user;

import java.time.LocalDateTime;

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
public class UserResponseDTO {
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
	
	private String error;
}
