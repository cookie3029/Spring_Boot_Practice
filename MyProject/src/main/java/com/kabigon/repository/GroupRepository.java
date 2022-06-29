package com.kabigon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabigon.domain.group.GroupEntity;
import com.kabigon.domain.user.UserEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
	GroupEntity findByGroupNo(Long groupNo);
	GroupEntity findByOwner(UserEntity owner);
}
