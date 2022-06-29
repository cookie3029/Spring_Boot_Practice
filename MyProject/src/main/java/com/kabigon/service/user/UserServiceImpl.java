package com.kabigon.service.user;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kabigon.domain.user.UserEntity;
import com.kabigon.dto.common.FileResponseDTO;
import com.kabigon.dto.user.UserRequestDTO;
import com.kabigon.dto.user.UserResponseDTO;
import com.kabigon.repository.UserRepository;
import com.kabigon.service.common.FileUploadProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final HttpSession httpSession;
	private final UserRepository userRepository;
	private final FileUploadProvider fileUploadProvider;

	@Override
	public UserResponseDTO registUser(UserRequestDTO dto) {
		// 아이디 중복 체크
		if (Optional.ofNullable(userRepository.findByUserId(dto.getUserId())).isPresent()) {
			return UserResponseDTO.builder().error("이미 존재하는 아이디입니다.").build();
		}

		// 파일 업로드 처리
		// 전송 받은 파일을 가져오기
		MultipartFile uploadFile = dto.getPicture();

		// 전송된 파일이 있다면
		if (uploadFile.isEmpty() == false) {
			FileResponseDTO fileResponseDTO = fileUploadProvider.uploadFile("UserProfile", uploadFile);

			// 이미지 경로를 DTO에 설정
			dto.setUserPicture(fileResponseDTO.getRealUploadPath().replace("\\", "/") + "/" + fileResponseDTO.getUuid()
					+ fileResponseDTO.getFileName());
		}

		UserEntity user = dtoToEntity(dto);

		userRepository.save(user);

		return UserResponseDTO.builder().userName(user.getUserName()).build();
	}

	@Override
	public UserResponseDTO updateUser(UserRequestDTO dto) {
		// 전송된 이미지를 확인
		MultipartFile uploadFile = dto.getPicture();

		// 전송된 이미지가 있다면 파일 업로드
		if (uploadFile.isEmpty() == false) {
			// 이미지 파일 만 업로드하기 위해서 이미지 파일이 아니면 작업 중단
			FileResponseDTO fileResponseDTO = fileUploadProvider.uploadFile("UserProfile", uploadFile);

			// 이미지 경로를 DTO에 설정
			dto.setUserPicture(fileResponseDTO.getRealUploadPath() + "/" + fileResponseDTO.getUuid()
					+ fileResponseDTO.getFileName());
		} else {
			// 전송된 이미지가 없다면 이전 이미지 사진의 경로를 세팅
			dto.setUserPicture(getUser(dto).getUserPicture());
		}

		UserEntity user = userRepository.findByUserNo(dto.getUserNo());
		user.renewData(dto.getUserPw(), dto.getUserName(), dto.getUserIntro(), dto.getUserPicture());

		userRepository.save(user);

		return entityToDto(user);
	}

	@Override
	public UserResponseDTO loginUser(UserRequestDTO dto) {
		// 아이디를 가지고 데이터를 찾아옵니다.
		UserEntity user = userRepository.findByUserId(dto.getUserId());

		if (user != null) {
			// 비밀번호 확인
			if (BCrypt.checkpw(dto.getUserPw(), user.getUserPw())) {

				// 로그인에 성공했을 때 로그인한 시간을 업데이트
				ZonedDateTime nowUTC = ZonedDateTime.now(ZoneId.of("UTC"));
				LocalDateTime now = nowUTC.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();

				UserEntity updateUser = UserEntity.builder().userNo(user.getUserNo()).userId(user.getUserId())
						.userPw(user.getUserPw()).userName(user.getUserName()).userPicture(user.getUserPicture())
						.userIntro(user.getUserIntro()).isManager(user.getIsManager()).lastLoginDate(now).build();

				userRepository.save(updateUser);

				httpSession.setAttribute("loginUser", updateUser.getUserNo());

				return entityToDto(updateUser);
			} else {
				return UserResponseDTO.builder().error("잘못된 비밀번호입니다.").build();
			}
		} else {
			// 존재하지 않는 회원번호면
			return UserResponseDTO.builder().error("존재하지 않는 회원입니다.").build();
		}
	}

	@Override
	public UserResponseDTO deleteUser(UserRequestDTO dto) {

		if (Optional.ofNullable(getUser(dto)).isPresent()) {
			UserEntity user = getUser(dto);
			userRepository.delete(user);

			return entityToDto(user);
		}
		return UserResponseDTO.builder().error("존재하지 않는 회원입니다.").build();
	}

	@Override
	public UserEntity getUser(UserRequestDTO dto) {
		return userRepository.findByUserNo(dto.getUserNo());
	}

	@Override
	public Long getCurrentUserNo() {
		if (httpSession.getAttribute("loginUser") != null) {
			return (Long) httpSession.getAttribute("loginUser");
		}
		return null;
	}

	@Override
	public ResponseEntity<Object> download(String path) {
		return fileUploadProvider.download("UserProfile", path);
	}

	@Override
	public List<UserResponseDTO> getUserByName(String name) {
		List<UserEntity> users = userRepository.findAllByUserNameLike(name);
		System.out.println(users);
		return users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
	}

}
