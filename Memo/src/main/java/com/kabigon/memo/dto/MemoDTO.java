package com.kabigon.memo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Controller와 Service 사이의 데이터 전달을 위한 클래스
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoDTO {
	private Long gno;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
