package com.paori.mobileServer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paori.mobileServer.model.LoginInfo;

public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long>{

}
