package com.kabigon.service.group;

import com.kabigon.domain.group.GroupEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.group.GroupPageResponseDTO;
import com.kabigon.dto.group.GroupRequestDTO;
import com.kabigon.dto.group.GroupResponseDTO;

public interface GroupService {
	public GroupResponseDTO createGroup(GroupRequestDTO dto);
	public GroupResponseDTO updateGroupInfo(GroupRequestDTO dto);
	public GroupResponseDTO changeGroupMaster(GroupRequestDTO dto);
	public GroupResponseDTO deleteGroup(GroupRequestDTO dto);
	public GroupPageResponseDTO getGroupPageList(GroupRequestDTO dto);
		
	public default GroupEntity dtoToEntity(GroupRequestDTO dto, UserEntity owner) {
		return GroupEntity.builder()
				.owner(owner)
				.groupName(dto.getGroupName())
				.groupPicture(dto.getGroupPicture())
				.groupIntro(dto.getGroupIntro())
				.isPublic(true)
				.build();
	}
}
