package com.kabigon.dto.Follow;

import com.kabigon.dto.user.UserResponseDTO;

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
public class FollowStateResponseDTO {
	private UserResponseDTO userInfo;
	private Boolean isFollowing;		// User Follows To Partner
	private Boolean isFollower;		// Partner Follows To User
}
