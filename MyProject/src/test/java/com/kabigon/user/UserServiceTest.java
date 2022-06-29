package com.kabigon.user;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.user.UserRequestDTO;
import com.kabigon.dto.user.UserResponseDTO;
import com.kabigon.service.user.UserService;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void userRegisterTest() {
		LongStream.rangeClosed(1, 30).forEach(i -> {
			UserRequestDTO dto = UserRequestDTO.builder()
					.userId("Paori" + i)
					.userPw("1234")
					.userName("파오리"+ i)
					.build();
			
			userService.registUser(dto);
		});
	}
	
	// @Test
	public void loginTest() {
		UserResponseDTO response = userService.loginUser(UserRequestDTO.builder().userId("Paori1").userPw("1234").build());
		System.out.println(response);
	}
	
	// @Test
	public void updateTest() {
		UserRequestDTO dto = UserRequestDTO.builder().userNo(29L).userId("King_Of_Paori1").userName("파오리왕").userPw("1111").build();
		UserResponseDTO response = userService.updateUser(dto);
		
		System.out.println(response);
	}
	
	// @Test
	public void deleteTest() {
		UserRequestDTO dto = UserRequestDTO.builder().userId("Paori27").build();
		UserResponseDTO response = userService.deleteUser(dto);
		
		System.out.println(response);
	}

}
