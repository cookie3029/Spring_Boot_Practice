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
public class LoginInfoDTO {
	private Long logininfoid;
	private String email;
	private Double longitude;
	private Double latitude;
	private String address;
	private LocalDateTime regdate;
}
