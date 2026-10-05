package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderAmendmentAttachmentResponseDTO {

	private Long id;

	// Original file name
	private String name;

	// Stored file name
	private String fileName;

	// Download/View URL
	private String filePath;

	// File size in bytes
	private Long fileSize;

	// MIME type (optional)
	private String contentType;

}