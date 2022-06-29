package com.kabigon.service.follow;

import java.util.List;

import com.kabigon.domain.follow.FollowEntity;
import com.kabigon.domain.todo.TodoEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.Follow.FollowResponseDTO;
import com.kabigon.dto.Follow.FollowStateResponseDTO;
import com.kabigon.dto.todo.TodoRequestDTO;
import com.kabigon.dto.user.UserResponseDTO;

public interface FollowService {
	public FollowEntity getEntity(Long userNo, Long partnerNo);

	public FollowResponseDTO setFollow(Long userNo, Long partnerNo);
	public FollowResponseDTO setUnFollow(Long userNo, Long partnerNo);
	public FollowResponseDTO isfollowBack(Long userNo, Long partnerNo);
	
	public List<UserResponseDTO> followerList(Long userNo);
	public List<UserResponseDTO> followingList(Long userNo);
	public List<UserResponseDTO> followBackList(Long userNo); // 사용자 친구 리스트 (맞팔)
	public List<FollowStateResponseDTO> followState(String findName);
	
	public int checkPublicStateToTarget(Long userNO, Long partnerNo);
}
