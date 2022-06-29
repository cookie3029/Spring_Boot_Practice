package com.kabigon.service.user;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;

import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.user.UserRequestDTO;
import com.kabigon.dto.user.UserResponseDTO;

public interface UserService {
	public UserResponseDTO registUser(UserRequestDTO dto);		// 회원 삽입
	public UserResponseDTO updateUser(UserRequestDTO dto);		// 회원 수정
	public UserResponseDTO deleteUser(UserRequestDTO dto);		// 회원 삭제
	public UserResponseDTO loginUser(UserRequestDTO dto);

	public List<UserResponseDTO> getUserByName(String name);
	
	public UserEntity getUser(UserRequestDTO dto);	

	public ResponseEntity<Object> download(String path);
	
	public Long getCurrentUserNo();
	
	// DTO 클래스의 객체를 Model 클래스의 객체로 변환
	public default UserEntity dtoToEntity(UserRequestDTO dto) {		
		String password = BCrypt.hashpw(dto.getUserPw(), BCrypt.gensalt());
		
		UserEntity user = UserEntity.builder()
				.userNo(dto.getUserNo())
				.userId(dto.getUserId())
				.userPw(password)
				.userName(dto.getUserName())
				.userPicture(dto.getUserPicture())
				.userIntro(dto.getUserIntro())
				.isManager(dto.getIsManager())
				.lastLoginDate(dto.getLastLoginDate())
				.build();
		
		return user;
	}
	
	// Model 객체를 DTO 클래스로 변환
	public default UserResponseDTO entityToDto(UserEntity user) {
		UserResponseDTO dto = UserResponseDTO.builder()
				.userNo(user.getUserNo())
				.userId(user.getUserId())
				.userName(user.getUserName())
				.userPicture(user.getUserPicture())
				.userIntro(user.getUserIntro())
				.isManager(user.getIsManager())
				.lastLoginDate(user.getLastLoginDate())
				.joinDate(user.getCreatedDate())
				.renewDate(user.getModifiedDate())
				.build();
		
		return dto;
	}
}
