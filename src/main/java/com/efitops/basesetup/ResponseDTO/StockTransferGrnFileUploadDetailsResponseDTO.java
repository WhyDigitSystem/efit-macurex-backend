package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferGrnFileUploadDetailsResponseDTO {

	private Long id;
	private Long fileSize;
	private String contentType;
	private LocalDateTime uploadOn;
	private String fileName;
	private String filePath;
	private String name;
}
