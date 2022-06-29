package com.kabigon.follow;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.domain.follow.FollowEntity;
import com.kabigon.dto.Follow.FollowResponseDTO;
import com.kabigon.dto.Follow.FollowStateResponseDTO;
import com.kabigon.dto.user.UserResponseDTO;
import com.kabigon.service.follow.FollowService;

@SpringBootTest
public class FollowServiceTest {
	@Autowired
	private FollowService followService;

	// @Test
	@Transactional
	public void getFollowEntity() {
		FollowEntity entity = followService.getEntity(93L, 94L);
		
		System.out.println(entity.getFollowUser().toString());
		System.out.println(entity.getFollowPartner().toString());
	}

	// @Test
	public void setFollow() {
		FollowResponseDTO dto = followService.setFollow(94L, 97L);
		System.out.println(dto.toString());
	}
	
	// @Test
	public void setUnFollow() {
		FollowResponseDTO dto = followService.setUnFollow(94L, 97L);
		
		System.out.println(dto.toString());
	}
	
	// @Test
	public void getFollowerList() {
		List<UserResponseDTO> list = followService.followerList(93L);
		
		for (UserResponseDTO userResponseDTO : list) {
			System.out.println(userResponseDTO);
		}
	}
	
	// @Test
	public void followingList() {
		List<UserResponseDTO> list = followService.followingList(93L);
		
		for (UserResponseDTO userResponseDTO : list) {
			System.out.println(userResponseDTO);
		}
	}
	
	// @Test
	public void followBackList() {
		List<UserResponseDTO> dto = followService.followBackList(93L);
		
		for (UserResponseDTO userResponseDTO : dto) {
			System.out.println(userResponseDTO);
		}
	}
	
	@Test
	public void isFollowBack() {
		System.out.println(followService.isfollowBack(93L, 97L));
	}
}
