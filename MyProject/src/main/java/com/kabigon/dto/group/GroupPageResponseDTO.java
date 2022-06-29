package com.kabigon.dto.group;

import java.util.List;

import com.kabigon.dto.user.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GroupPageResponseDTO {
	private Long groupNo;
    private UserResponseDTO owner;
    private List<UserResponseDTO> memberList;
    
    private String groupName;
    private String groupIntro;
    private String groupPicture;

    private Boolean isPublic;
}
