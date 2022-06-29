package com.paori.mobileServer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.paori.mobileServer.dto.ItemDTO;
import com.paori.mobileServer.dto.PageRequestItemDTO;
import com.paori.mobileServer.dto.PageResponseItemDTO;
import com.paori.mobileServer.model.Item;
import com.paori.mobileServer.persistence.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	private final ItemRepository itemRepository;

	// 파일이 업로드 될 경로 가져오기
	@Value("${com.paori.upload.path}")
	private String uploadPath;

	// 디렉토리 생성
	// 업로드한 날짜 별로 이미지를 저장하기 위해서 날짜별로 디렉토리를 만들어서 경로를 리턴하는 메서드
	private String makeFolder() {
		// 오늘 날짜를 문자열로 생성
		String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// 문자열을 치환 - /를 운영체제의 디렉토리 구분자로 치환
		String realUploadPath = str.replace("/", File.separator);

		// 디렉토리 생성
		File uploadPathDir = new File(uploadPath, realUploadPath);
		if (uploadPathDir.exists() == false) {
			uploadPathDir.mkdirs();
		}
		System.out.println(realUploadPath);

		return realUploadPath;
	}

	// 삽입이나 수정 그리고 삭제시 작업 시간을 기록하는 메서드
	// 이 시간을 읽어서 데이터가 변경되었는지 확인
	private void updateDate() {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream("./updateDate.dat"), true)) {
			// 현재 날짜 및 시간 가져오기
			String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS"));

			pw.println(str);
			pw.flush();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	@Override
	public Long registerItem(ItemDTO dto) {
		// 파일 업로드 처리
		// 전송 받은 파일을 가져오기
		MultipartFile uploadFile = dto.getImage();
		// 전송된 파일이 있다면
		if (uploadFile.isEmpty() == false) {
			// 이미지 파일 만 업로드하기 위해서 이미지 파일이 아니면 작업 중단
			if (uploadFile.getContentType().startsWith("image") == false) {
				return null;
			}

			// 원본 파일의 파일 이름 찾아오기
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

			// 파일을 업로드할 디렉토리 경로를 생성
			String realUploadPath = makeFolder();

			// 업로드 할 파일의 경로를 생성
			String uuid = UUID.randomUUID().toString(); // 파일 이름의 중복을 피하기 위해서 생성
			String saveName = uploadPath + File.separator + realUploadPath + File.separator + uuid + fileName;
			Path savePath = Paths.get(saveName);

			try {
				// 파일 업로드
				uploadFile.transferTo(savePath);

				// 이미지 경로를 DTO에 설정
				dto.setPictureurl(realUploadPath.replace('\\', '/') + "/" + uuid + fileName);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}

		Item item = dtoToEntity(dto);

		itemRepository.save(item);
		updateDate();

		return item.getItemid();
	}

	@Override
	public ItemDTO getItem(ItemDTO dto) {
		Long itemid = dto.getItemid();
		Optional<Item> optional = itemRepository.findById(itemid);

		if (optional.isPresent()) {
			return entityToDto(optional.get());
		}
		return null;
	}

	@Override
	public Long updateItem(ItemDTO dto) {
		// 삽입할 때는 이미지가 업승면 이미지 업로드를 처리하지 않거나 기본 이미지를 설정하지만
		// 수정을 할 대는 이미지가 없다는 것은 수정할 이미지가 없다는 의미가 될 수 있습니다.
		if (dto.getImage().isEmpty() == false) {
			// 업로드된 파일을 가져오기
			MultipartFile uploadFile = dto.getImage();

			// 원본 파일 이름 찾아오기
			String originalName = uploadFile.getOriginalFilename();

			// IE나 Edge에서는 전체 파일 경로가 오기 때문에 마지막 역슬래쉬 위치를 찾아서 뒤의 부분만 가져와야 합니다.
			String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

			// 업로드할 디렉토리 경로를 생성
			// 회원 정보 이미지와 아이템 이미지를 구변해서 저장하고자 하면 makeFolder 메서드를 각각 구현
			String realUploadPath = makeFolder();

			// 파일 이름 중복을 최소화하기 위한 UUID 생성
			String uuid = UUID.randomUUID().toString();

			// 파일 이름 중간에 _를 이용해서 구분
			// 교재나 검색한 소스를 볼 때 \나 /가 보이면 앞 뒤 문맥을 읽어봐야 합니다.
			// 그래서 이 기호가 디렉토리 기호라면 File.separator로 변경하는 부분을 고려해야 합니다.
			// 교재를 볼 때는 어떤 운영체제에서 작성한 것인지 확인하고 교재를 읽어보시는 것이 좋습니다.
			String saveName = uploadPath + File.separator + realUploadPath + File.separator + uuid + fileName;

			// 실제 전송할 경로를 생성 - jdk 1.7 이상에서 지원
			Path savePath = Paths.get(saveName);

			try {
				// 파일 전송
				uploadFile.transferTo(savePath);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
			}
			
			// 파일의 경로를 저장
			dto.setPictureurl(realUploadPath.replace('\\', '/') + "/" + uuid);
		} else {
			// 업로드할 파일이 없을 때 이전 내용을 그대로 적용
			dto.setPictureurl(getItem(dto).getPictureurl());
		}

		// 데이터베이스에서 수정
		Item item = dtoToEntity(dto);
		
		// 수정한 날짜 업데이트
		updateDate();
		
		itemRepository.save(item);
		
		return item.getItemid();
	}

	@Override
	public Long deleteItem(ItemDTO dto) {
		Item item = dtoToEntity(dto);
		Long itemid = item.getItemid();

		itemRepository.deleteById(item.getItemid());
		updateDate();

		return itemid;
	}

	@Override
	public PageResponseItemDTO getList(PageRequestItemDTO dto) {
		Sort sort = Sort.by("itemid").descending();
		Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize(), sort);

		Page<Item> page = itemRepository.findAll(pageable);

		PageResponseItemDTO result = new PageResponseItemDTO();
		result.setTotalPage(page.getTotalPages());
		result.makePageList(pageable);

		List<ItemDTO> list = new ArrayList<>();

		page.get().forEach(item -> {
			list.add(entityToDto(item));
		});

		result.setItemList(list);

		return result;
	}

	@Override
	public String updatedate() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./updateDate.dat")))) {
			String str = br.readLine();
			
			return str;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
}
