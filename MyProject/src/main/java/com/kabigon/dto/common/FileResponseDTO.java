package com.kabigon.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDTO {
	private Boolean isSuccess;

	private String error;

	private String uuid;
	private String fileName;
	private String realUploadPath;
}
