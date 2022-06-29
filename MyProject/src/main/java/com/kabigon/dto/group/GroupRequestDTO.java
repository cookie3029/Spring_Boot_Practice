package com.kabigon.dto.group;

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
public class GroupRequestDTO {
	private Long groupNo;
    private Long userNo;

    private String groupName;
    private String groupPicture;
    private String groupIntro;
    
    private Boolean isPublic;

    // 전송된 파일의 내용을 저장할 속성
	// 한번에 업로드 하는 파일이 여러 개라면 MultipartFile []
	private MultipartFile picture;
}
