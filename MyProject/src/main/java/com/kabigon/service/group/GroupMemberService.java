package com.kabigon.service.group;

import java.util.List;

import com.kabigon.domain.group.GroupEntity;
import com.kabigon.domain.group.GroupMemberEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.group.GroupMemberDTO;
import com.kabigon.dto.group.GroupResponseDTO;

public interface GroupMemberService {
	public GroupResponseDTO save(GroupMemberDTO dto);
	public GroupResponseDTO delete(GroupMemberDTO dto);
	public GroupResponseDTO joinGroup(GroupMemberDTO dto);
	
	public GroupMemberEntity getEntity(GroupMemberDTO dto);

	public List<UserEntity> getMemberList(GroupEntity group);
	
	
	public default GroupMemberEntity dtoToEntity(GroupMemberDTO dto) {
		return GroupMemberEntity.builder()
				.member(UserEntity.builder().userNo(dto.getUserNo()).build())
				.group(GroupEntity.builder().groupNo(dto.getGroupNo()).build())
				.applyState(dto.getApplyState())
				.build();
	}
}
