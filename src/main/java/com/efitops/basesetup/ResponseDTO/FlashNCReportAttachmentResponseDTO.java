package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashNCReportAttachmentResponseDTO {
	
	  private Long id;

	    private String name;

	    private String fileName;

	    private String filePath;

	    private Long fileSize;

	    private String contentType;

	    private LocalDateTime uploadOn;
	    
	    private List<FlashNCReportAttachmentResponseDTO> flashNCReportAttachmentResponseDTO;

}
