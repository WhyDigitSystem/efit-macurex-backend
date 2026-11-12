package com.efitops.basesetup.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MachineMasterAttachmentDTO {
	
	
	 private Long id;

	    private String name;

	    private String fileName;

	    private String filePath;

	    private Long fileSize;

	    private String contentType;

	    private LocalDateTime uploadOn;

}
