package com.paori.mobileServer.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

// Lombok 라이브러리에서 속성의 getter 메서드를 만들어주는 어노테이션
@Getter
// 테이블로 생성하지 말고 매핑 정보만 사용하겠다는 의미
@MappedSuperclass 
// Entity의 변경 사항이 발생했을 때 작업을 수행
@EntityListeners(value = {AuditingEntityListener.class})
abstract public class BaseEntity {
	// 생성 날짜를 이용
	@CreatedDate
	// 테이블에 만들어질 때는 regdate라는 컬럼으로 생성하고 수정은 할 수 없도록 설정
	@Column(name = "regdate", updatable = false)
	private LocalDateTime regDate;
	
	// 마지막 수정 날짜를 이용
	@LastModifiedDate
	// 테이블에 만들어질 때는 moddate라는 컬럼으로 생성
	@Column(name = "moddate")
	private LocalDateTime modDate;
}
