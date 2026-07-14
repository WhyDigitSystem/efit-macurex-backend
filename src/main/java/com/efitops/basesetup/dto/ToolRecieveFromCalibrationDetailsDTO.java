package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolRecieveFromCalibrationDetailsDTO {
	private String instrumentId;
	private String instrumentName;
	private String instrumentDesc;
	private LocalDate calibratedDate;
	private LocalDate dueDate;
	private String frequencyForCalib;
	private String status;
	private BigDecimal issQty;
	private BigDecimal recievdQty;
	private BigDecimal scorpQty;
	private String calibrationCertificate;
	private MultipartFile[] files;

}
