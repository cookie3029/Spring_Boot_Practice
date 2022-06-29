package com.kabigon.movie.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(value= {AuditingEntityListener.class})
abstract public class BaseEntity {
	@CreatedDate
	@Column(name="regdate", updatable=false)
	private LocalDateTime regDate;

	@LastModifiedDate
	@Column(name="moddate")
	private LocalDateTime modDate;
}
