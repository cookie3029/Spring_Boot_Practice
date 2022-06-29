package com.kabigon.dto.Follow;

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
public class FollowResponseDTO {
	private Boolean isSuccess;
	private Boolean isFollowBack;
	private String error;
}
