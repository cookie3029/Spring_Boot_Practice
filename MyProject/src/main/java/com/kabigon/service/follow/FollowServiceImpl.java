package com.kabigon.service.follow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabigon.domain.follow.FollowEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.Follow.FollowResponseDTO;
import com.kabigon.dto.Follow.FollowStateResponseDTO;
import com.kabigon.dto.user.UserRequestDTO;
import com.kabigon.dto.user.UserResponseDTO;
import com.kabigon.repository.FollowRepository;
import com.kabigon.service.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	private final UserService userService;
	private final FollowRepository followRepository;
	
	@Override
	public FollowEntity getEntity(Long userNo, Long partnerNo) {
		UserEntity user = UserEntity.builder().userNo(userNo).build();
		UserEntity partner = UserEntity.builder().userNo(partnerNo).build();
		
		return followRepository.findByFollowUserAndFollowPartner(user, partner);
	}

	@Override
	public FollowResponseDTO setFollow(Long userNo, Long partnerNo) {		
		if(getEntity(userNo, partnerNo) == null) {
			UserEntity user = UserEntity.builder().userNo(userNo).build();
			UserEntity partner = UserEntity.builder().userNo(partnerNo).build();
			
			FollowEntity follow = FollowEntity.builder().followUser(user).followPartner(partner).build();
	
			followRepository.save(follow);
			
			return FollowResponseDTO.builder().isSuccess(true).build();
		}
		return FollowResponseDTO.builder().isSuccess(false).error("팔로우 상태입니다.").build();
	}

	@Override
	public FollowResponseDTO setUnFollow(Long userNo, Long partnerNo) {
		if(getEntity(userNo, partnerNo) != null) {
			FollowEntity follow = getEntity(userNo, partnerNo);
	
			followRepository.delete(follow);
			
			return FollowResponseDTO.builder().isSuccess(true).build();
		}
		return FollowResponseDTO.builder().isSuccess(false).error("언팔로우 상태입니다.").build();
	}

	@Override
	@Transactional
	public List<UserResponseDTO> followerList(Long userNo) {
		List<UserResponseDTO> usersInfo = new ArrayList<>();
		
		getListByPartner(userNo).forEach(follow -> {
			UserResponseDTO dto = userService.entityToDto(follow.getFollowUser());
			System.out.println(dto);
			usersInfo.add(dto);
		});
		
		return usersInfo;
	}

	@Override
	@Transactional
	public List<UserResponseDTO> followingList(Long userNo) {
		List<UserResponseDTO> usersInfo = new ArrayList<>();
		
		getListByUser(userNo).forEach(follow -> {
			UserResponseDTO dto = userService.entityToDto(follow.getFollowPartner());
			usersInfo.add(dto);
		});
		
		return usersInfo;
	}

	@Override
	@Transactional
	public List<UserResponseDTO> followBackList(Long userNo) {
		List<UserResponseDTO> followBackList = new ArrayList<>();

		List<Long> followersNo = getUsersNo(followerList(userNo));
		List<Long> followingsNo =  getUsersNo(followingList(userNo));

		if (followersNo == null || followingsNo == null) {
			return null;
		}
		
		followersNo.retainAll(followingsNo);
		
		for(Long result : followersNo) {
			UserEntity user = userService.getUser(UserRequestDTO.builder().userNo(result).build());
			followBackList.add(userService.entityToDto(user));
		}
		
		return followBackList;
	}

	@Override
	public List<FollowStateResponseDTO> followState(String findName) {
        List<FollowStateResponseDTO> followerStateResponseDtoList = new ArrayList<>();
        List<UserResponseDTO> userResponseDtoList = userService.getUserByName(findName);

        Long userNo = userService.getCurrentUserNo();
        System.out.println("=====" + userNo + "=========");
        for (UserResponseDTO partner : userResponseDtoList) {
            followerStateResponseDtoList.add(new FollowStateResponseDTO(
                    partner,
                    Optional.ofNullable(getEntity(userNo, partner.getUserNo())).isPresent(),
                    Optional.ofNullable(getEntity(partner.getUserNo(), userNo)).isPresent()
            ));
        }
        return followerStateResponseDtoList;
	}
	
	@Override
	public FollowResponseDTO isfollowBack(Long userNo, Long partnerNo) {
		UserEntity user = UserEntity.builder().userNo(userNo).build();
		UserEntity partner = UserEntity.builder().userNo(partnerNo).build();
		
		FollowEntity usersFollow = followRepository.findByFollowUserAndFollowPartner(user, partner);
		FollowEntity partnersFollow = followRepository.findByFollowUserAndFollowPartner(partner, user);
		
		if(usersFollow != null && partnersFollow != null) {
			return FollowResponseDTO.builder().isFollowBack(true).build();
		}
	
		return FollowResponseDTO.builder().isFollowBack(false).build();
	}

	@Override
	public int checkPublicStateToTarget(Long userNO, Long partnerNo) {
        if (userNO == null) return 0; // 로그아웃 사용자

        if (userNO.equals(partnerNo)) {
            return 3;
        } else if (isfollowBack(userNO, partnerNo).getIsFollowBack()) {
            return 2;
        } else if (getEntity(userNO, partnerNo) != null) {
            return 1;
        } else return 0;
	}
	
	private List<FollowEntity> getListByUser(Long userNo) {
		UserEntity user = UserEntity.builder().userNo(userNo).build();
		
		return followRepository.findByFollowUser(user);
	}
	
	private List<FollowEntity> getListByPartner(Long userNo) {
		UserEntity partner = UserEntity.builder().userNo(userNo).build();
		
		return followRepository.findByFollowPartner(partner);
	}
	
	private List<Long> getUsersNo(List<UserResponseDTO> dtoList) {
		List<Long> list = new ArrayList<>();
		
		for(UserResponseDTO dto : dtoList) {
			list.add(dto.getUserNo());
		}
		
		return list;
	}

}
