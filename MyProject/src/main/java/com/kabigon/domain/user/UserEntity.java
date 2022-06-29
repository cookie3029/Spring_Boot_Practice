package com.kabigon.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;
import org.mindrot.jbcrypt.BCrypt;

import com.kabigon.domain.common.BaseEntity;
import com.kabigon.domain.converter.BooleanToYNConverter;
import com.kabigon.dto.user.UserRequestDTO;

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
@Table(name="USER_TBL")
public class UserEntity extends BaseEntity {
	@Id
	@Column(name = "USER_NO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNo;
	
	@Column(name = "USER_ID", unique = true)
	private String userId;
	
	@Column(name = "USER_PW", nullable = false)
	private String userPw;
	
	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name = "USER_PICTURE")
	private String userPicture;
	
	@Type(type = "text")
	@Column(name = "USER_INTRO")
	private String userIntro;
	
	@Column(name = "IS_MANAGER")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean isManager;
	
	@Column(name = "LAST_LOGIN_DATE")
	private LocalDateTime lastLoginDate;
	
	public void renewData(String unhashedPw, String userName, String userIntro, String userPicture) {
		String userPw = BCrypt.hashpw(unhashedPw, BCrypt.gensalt());
		
		this.userPw = userPw;
		this.userName = userName;
		this.userIntro = userIntro;
		this.userPicture = userPicture;
	}
}
