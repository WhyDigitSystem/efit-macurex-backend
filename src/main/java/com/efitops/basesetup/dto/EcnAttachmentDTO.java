package com.efitops.basesetup.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcnAttachmentDTO {
	

	private String fileName;
	
	private String filePath;
	
	private String filetype;
	
	private Long fileSize;
	
	private LocalDate uploadOn ;
	
	private MultipartFile[] files;
	


}
