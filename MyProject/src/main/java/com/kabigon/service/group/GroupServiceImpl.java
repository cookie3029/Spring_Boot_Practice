package com.kabigon.service.group;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.kabigon.domain.group.GroupEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.common.FileResponseDTO;
import com.kabigon.dto.group.GroupPageResponseDTO;
import com.kabigon.dto.group.GroupRequestDTO;
import com.kabigon.dto.group.GroupResponseDTO;
import com.kabigon.dto.user.UserRequestDTO;
import com.kabigon.dto.user.UserResponseDTO;
import com.kabigon.repository.GroupRepository;
import com.kabigon.service.common.FileUploadProvider;
import com.kabigon.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
	private final UserService userService;
	private final GroupMemberService groupMemberService;
	
	private final GroupRepository groupRepository;
	private final FileUploadProvider fileUploadProvider;

	@Override
	public GroupResponseDTO createGroup(GroupRequestDTO dto) {
		UserEntity user = userService.getUser(UserRequestDTO.builder().userNo(dto.getUserNo()).build());

		MultipartFile uploadFile = dto.getPicture();

		if (uploadFile.isEmpty() == false) {
			FileResponseDTO fileResponseDTO = fileUploadProvider.uploadFile("GroupProfile", uploadFile);

			dto.setGroupPicture(fileResponseDTO.getRealUploadPath().replace("\\", "/") + "/" 
					+ fileResponseDTO.getUuid() + fileResponseDTO.getFileName());
		} else {
			dto.setGroupPicture("GroupProfile/group_basic.png");
		}

		GroupEntity group = dtoToEntity(dto, user);
		
		groupRepository.save(group);
		
		return GroupResponseDTO.builder().isSuccess(true).build();
	}

	@Override
	public GroupResponseDTO updateGroupInfo(GroupRequestDTO dto) {
		GroupEntity group = groupRepository.findByGroupNo(dto.getGroupNo());
		
		MultipartFile uploadFile = dto.getPicture();

		if (uploadFile.isEmpty() == false) {
			FileResponseDTO fileResponseDTO = fileUploadProvider.uploadFile("GroupProfile", uploadFile);

			dto.setGroupPicture(fileResponseDTO.getRealUploadPath().replace("\\", "/") + "/" 
					+ fileResponseDTO.getUuid() + fileResponseDTO.getFileName());
		} else {
			dto.setGroupPicture(group.getGroupPicture());
		}
		
		group.renewGroupInfo(dto.getGroupName(), dto.getGroupIntro(), dto.getGroupPicture(), dto.getIsPublic());
		groupRepository.save(group);
		
		return GroupResponseDTO.builder().isSuccess(true).build();
	}


	@Override
	public GroupResponseDTO changeGroupMaster(GroupRequestDTO dto) {
		GroupEntity group = groupRepository.findByGroupNo(dto.getGroupNo());
		UserEntity user = group.getOwner();
		
		group.updateGroupOwner(userService.getUser(UserRequestDTO.builder().userNo(dto.getUserNo()).build()));
		
		return GroupResponseDTO.builder().isSuccess(true).build();
	}

	@Override
	public GroupResponseDTO deleteGroup(GroupRequestDTO dto) {
		GroupEntity group = groupRepository.findByGroupNo(dto.getGroupNo());
		
		groupRepository.delete(group);
		
		return GroupResponseDTO.builder().isSuccess(true).build();
	}

	@Override
	public GroupPageResponseDTO getGroupPageList(GroupRequestDTO dto) {
		GroupEntity group = groupRepository.findByGroupNo(dto.getGroupNo());
		
		UserEntity user = userService.getUser(UserRequestDTO.builder().userNo(dto.getUserNo()).build());
		UserResponseDTO userResponseDTO = userService.entityToDto(user);
		
		List<UserEntity> list = groupMemberService.getMemberList(group);
		
		return GroupPageResponseDTO.builder()
				.groupNo(group.getGroupNo())
				.groupName(group.getGroupName())
				.groupIntro(group.getGroupIntro())
				.isPublic(group.getIsPublic())
				.groupIntro(group.getGroupIntro())
				.groupPicture(group.getGroupPicture())
				.owner(userResponseDTO)
				.memberList(list.stream()
						.map(member -> userService.entityToDto(member))
						.collect(Collectors.toList()))
				.build();
	}

}
