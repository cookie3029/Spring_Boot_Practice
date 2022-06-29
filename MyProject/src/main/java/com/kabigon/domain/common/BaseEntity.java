package com.kabigon.domain.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {
	@CreatedDate
	@Column(name = "CREATED_DATE", updatable = false)
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(name = "MODIFIED_DATE")
	private LocalDateTime modifiedDate;
}
