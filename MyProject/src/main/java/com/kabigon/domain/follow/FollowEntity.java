package com.kabigon.domain.follow;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kabigon.domain.common.BaseEntity;
import com.kabigon.domain.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FOLLOW_TBL")
public class FollowEntity extends BaseEntity {
	@Id
	@Column(name = "FOLLOW_NO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long followNo;
	
	@JoinColumn(name = "USER_NO", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity followUser;
	
	@JoinColumn(name = "PARTNER_NO", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity followPartner;
}
