package com.efitops.basesetup.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyAttachmentDTO {
//	private Long attachmentId;
	private String itemId;
	private MultipartFile[] files;
}
