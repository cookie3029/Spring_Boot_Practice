package com.kabigon.user;

import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.user.UserRequestDTO;
import com.kabigon.repository.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	// @Test
	public void saveUser() {
		LongStream.rangeClosed(1, 30).forEach(i -> {
			UserEntity user = UserEntity.builder().userId("kabigon" + i).userPw("1111").userName("카비곤" + i + "호").isManager(true)
					.build();

			userRepository.save(user);
		});
	}

	// @Test
	public void getUser() {
		UserEntity user1 = userRepository.findById(1L).get();
		
		UserEntity user2 = userRepository.findByUserNo(30L);
		UserEntity user3 = userRepository.findByUserName("카비곤25호");
		UserEntity user4 = userRepository.findByUserId("kabigon27");
		
		System.out.println(user1);
		System.out.println(user2);
		System.out.println(user3);
		System.out.println(user4);
	}

	// @Test
	public void updateUser() {
		UserEntity user = UserEntity.builder()
				.userNo(30L)
				.userId("파오리!")
				.userPw("1234")
				.userName("대왕 파오리")
				.isManager(false)
				.build();

		userRepository.save(user);
	}

	@Test
	public void deleteUser() {
		Long userNo = 1L;
		UserEntity user = UserEntity.builder().userNo(userNo).build();
		
		userRepository.delete(user);
	}

}
