package com.kabigon.service.group;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kabigon.domain.group.GroupEntity;
import com.kabigon.domain.group.GroupMemberEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.group.GroupMemberDTO;
import com.kabigon.dto.group.GroupResponseDTO;
import com.kabigon.repository.GroupMemberRepository;
import com.kabigon.repository.GroupRepository;
import com.kabigon.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupMemberServiceImpl implements GroupMemberService {
	private final GroupMemberRepository groupMemberRepository;
	private final GroupRepository groupRepository;
	private final UserService userService;
	
	
	@Override
	public GroupResponseDTO save(GroupMemberDTO dto) {
		GroupMemberEntity groupMemberEntity = dtoToEntity(dto);
		
		groupMemberRepository.save(groupMemberEntity);
		
		return GroupResponseDTO.builder().isSuccess(true).build();
	}

	@Override
	public GroupResponseDTO delete(GroupMemberDTO dto) {
		GroupMemberEntity groupMemberEntity = getEntity(dto);
		
		groupMemberRepository.delete(groupMemberEntity);
		
		return GroupResponseDTO.builder().isSuccess(true).build();
	}

	@Override
	public GroupMemberEntity getEntity(GroupMemberDTO dto) {
		UserEntity member = UserEntity.builder().userNo(dto.getUserNo()).build();
		GroupEntity group = GroupEntity.builder().groupNo(dto.getGroupNo()).build();
		
		return groupMemberRepository.findByMemberAndGroup(member, group);
	}

	@Override
	public GroupResponseDTO joinGroup(GroupMemberDTO dto) {
		if(!checkApplyState(dto)) {
			dto.setApplyState(true);
			groupMemberRepository.save(dtoToEntity(dto));
		}
		
		return GroupResponseDTO.builder().isSuccess(true).build();
	}
	
	private Boolean checkApplyState(GroupMemberDTO dto) {
		GroupMemberEntity groupMemberEntity = getEntity(dto);
		
        return groupMemberEntity.getApplyState();
    }

	@Override
	public List<UserEntity> getMemberList(GroupEntity group) {
		return groupMemberRepository.getMembers(group);
	}
}
