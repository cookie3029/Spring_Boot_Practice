package com.kabigon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kabigon.domain.group.GroupEntity;
import com.kabigon.domain.group.GroupMemberEntity;
import com.kabigon.domain.user.UserEntity;

public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long> {
	public GroupMemberEntity findByMemberAndGroup(UserEntity member, GroupEntity group);
	
	@Query("select gm.member from GroupMemberEntity gm where gm.group=:group")
	public List<UserEntity> getMembers(@Param("group") GroupEntity group);
}
