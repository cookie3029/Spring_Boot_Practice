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

import com.kabigon.domain.common.BaseEntity;
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
@Table(name = "GROUP_TBL")
public class GroupEntity extends BaseEntity {
	@Id
	@Column(name = "GROUP_NO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_NO", nullable = false)
	private UserEntity owner;
	
	@Column(name = "GROUP_NAME", nullable = false)
	private String groupName;
	
	@Column(name = "IS_PUBLIC")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean isPublic;
	
	@Column(name = "GROUP_PICTURE")
	private String groupPicture;
	
	@Column(name = "GROUP_INTRO")
	private String groupIntro;
	
	public void renewGroupInfo(String groupName, String groupIntro, String groupPicture, Boolean isPublic) {
		this.groupName = groupName;
		this.groupIntro = groupIntro;
		this.groupPicture = groupPicture;
		this.isPublic = isPublic;
	}
	
	public void updateGroupOwner(UserEntity owner) {
		this.owner = owner;
	}
}
