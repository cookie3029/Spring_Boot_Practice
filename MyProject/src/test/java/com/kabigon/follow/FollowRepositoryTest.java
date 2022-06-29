package com.kabigon.follow;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.kabigon.domain.follow.FollowEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.repository.FollowRepository;

@SpringBootTest
public class FollowRepositoryTest {
	@Autowired
	private FollowRepository followRepository;
	
	// @Test
	public void setFollowTest() {
		LongStream.rangeClosed(94, 96).forEach(i -> {
			UserEntity user = UserEntity.builder().userNo(i).build();
			UserEntity partner = UserEntity.builder().userNo(96L).build();
			
			FollowEntity follow = FollowEntity.builder().followUser(user).followPartner(partner).build();
			followRepository.save(follow);
		});
	}
	
	// @Test
	@Transactional
	public void getFollowList() {
		UserEntity user = UserEntity.builder().userNo(93L).build();
		List<FollowEntity> followList = followRepository.findByFollowUser(user);
		
		for (FollowEntity followEntity : followList) {
			System.out.println(followEntity.getFollowUser());
			System.out.println(followEntity.getFollowPartner());
		}
	}
	
	// @Test
	public void isFollowBack() {
		UserEntity user = UserEntity.builder().userNo(93L).build();
		UserEntity partner = UserEntity.builder().userNo(99L).build();
		
		if(Optional.ofNullable(followRepository.findByFollowUserAndFollowPartner(user, partner)).isPresent())
			if(Optional.ofNullable(followRepository.findByFollowUserAndFollowPartner(partner, user)).isPresent()) {
				System.out.println("맞팔로우 상태");
				return;
			}
		System.out.println("맞팔로우 상태 아님");
	}
	
	// @Test
	public void setUnFollowTest() {
		UserEntity user = UserEntity.builder().userNo(93L).build();
		UserEntity partner = UserEntity.builder().userNo(103L).build();
		
		FollowEntity target = followRepository.findByFollowUserAndFollowPartner(user, partner);
		followRepository.deleteById(target.getFollowNo());
	}
	
	@Test
	@Transactional
	public void getFollowerList() {
		List<FollowEntity> list = followRepository.findByFollowPartner(UserEntity.builder().userNo(93L).build());
		
		for (FollowEntity followEntity : list) {
			System.out.println(followEntity.getFollowUser().toString());
		}
	}
}
