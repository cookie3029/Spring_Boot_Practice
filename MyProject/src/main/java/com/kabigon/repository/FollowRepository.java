package com.kabigon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabigon.domain.follow.FollowEntity;
import com.kabigon.domain.user.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
	public FollowEntity findByFollowUserAndFollowPartner(UserEntity user, UserEntity partner);
	public List<FollowEntity> findByFollowUser(UserEntity user);
	public List<FollowEntity> findByFollowPartner(UserEntity partner);
}
