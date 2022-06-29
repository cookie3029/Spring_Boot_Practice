package com.kabigon.group;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.domain.group.GroupEntity;
import com.kabigon.domain.user.UserEntity;
import com.kabigon.repository.GroupRepository;

@SpringBootTest
public class GroupRepositoryTest {
	@Autowired
	private GroupRepository groupRepository;
	
	// @Test
	public void createGroup() {
		UserEntity user = UserEntity.builder().userNo(106L).build();
		GroupEntity group = GroupEntity.builder()
				.groupName("파닭모임")
				.owner(user)
				.build();
		
		groupRepository.save(group);
	}
	
	// @Test
	public void updateGroup() {
		UserEntity user = UserEntity.builder().userNo(106L).build();
		GroupEntity group = GroupEntity.builder()
				.groupNo(1L)
				.groupName("모임")
				.owner(user)
				.build();
		
		groupRepository.save(group);
	}
	
	// @Test
	@Transactional
	public void getGroup() {
		UserEntity user = UserEntity.builder().userNo(106L).build();
		GroupEntity group = groupRepository.findByOwner(user);
		
		System.out.println(group.toString());
	}
	
	@Test
	public void deleteGroup() {
		UserEntity user = UserEntity.builder().userNo(106L).build();
		GroupEntity group = groupRepository.findByOwner(user);
		
		groupRepository.delete(group);
	}
}
