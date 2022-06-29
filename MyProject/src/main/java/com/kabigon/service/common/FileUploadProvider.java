package com.kabigon.service.common;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kabigon.dto.common.FileResponseDTO;

@Service
public class FileUploadProvider {
	// application.properties 파일에 작성한 속성 가져오기
	@Value("${com.paori.profile.upload.path}")
	private String uploadPath;

	// 업로드한 날짜 별로 이미지를 저장하기 위해서 날짜별로 디렉토리를 만들어서 경로를 리턴하는 메서드
	public String makeFolder(String targetDirectory) {
		// 오늘 날짜를 문자열로 생성
		String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		// 문자열을 치환 - /를 운영체제의 디렉토리 구분자로 치환
		String realUploadPath = targetDirectory + File.separator + str.replace("/", "\\");
		

		// 디렉토리 생성
		File uploadPathDir = new File(uploadPath, realUploadPath);

		if (!uploadPathDir.exists()) {
			uploadPathDir.mkdirs();
		}

		return realUploadPath;
	}

	public FileResponseDTO uploadFile(String targetDirectory, MultipartFile uploadFile) {

			// 이미지 파일 만 업로드하기 위해서 이미지 파일이 아니면 작업 중단
			if (!uploadFile.getContentType().startsWith("image")) {
				return FileResponseDTO.builder().isSuccess(false).error("이미지 파일을 업로드하셔야 합니다.").build();
			}

			// 원본 파일의 파일 이름 찾아오기
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

			// 파일을 업로드할 디렉토리 경로를 생성
			String realUploadPath = makeFolder(targetDirectory);

			// 업로드 할 파일의 경로를 생성
			String uuid = UUID.randomUUID().toString(); // 파일 이름의 중복을 피하기 위해서 생성
			String saveName = uploadPath + File.separator + realUploadPath + File.separator + uuid + fileName;
			Path savePath = Paths.get(saveName);

			try {
				// 파일 업로드
				uploadFile.transferTo(savePath);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		return FileResponseDTO.builder().isSuccess(true).uuid(uuid).fileName(fileName).realUploadPath(realUploadPath).build();
	}
	
	public ResponseEntity<Object> download(String targetDirectory, String path) {
		try {
			// 다운로드 받을 파일 경로를 생성
			Path filePath = Paths.get(uploadPath + File.separator + targetDirectory + File.separator + path);

			// 파일 resource 가져오기
			Resource resource = new InputStreamResource(Files.newInputStream(filePath));

			// 파일 정보를 헤더에 등록
			File file = new File(path);
			HttpHeaders headers = new HttpHeaders();

			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
}