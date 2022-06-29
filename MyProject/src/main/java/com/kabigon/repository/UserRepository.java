package com.kabigon.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabigon.domain.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUserId(String userId);
	UserEntity findByUserNo(Long userNo);
	UserEntity findByUserName(String userName);
	
	List<UserEntity> findAllByUserNameLike(String userName);
}
