package com.kabigon.domain.group;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kabigon.domain.converter.BooleanToYNConverter;
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
@Table(name = "GROUP_MEMBER_TBL")
public class GroupMemberEntity {
	@Id
	@Column(name = "GROUP_MEMBER_NO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gmNo ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="GROUP_NO", nullable = false)
	private GroupEntity group;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_NO", nullable = false)
	private UserEntity member;
	
	@Column(name = "GROUP_APPLY_STATE")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean applyState;
}
