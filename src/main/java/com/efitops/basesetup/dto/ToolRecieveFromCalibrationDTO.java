package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolRecieveFromCalibrationDTO {
	private Long id;
	private String issueNo;
	private String issueDate;
	private String recievedFrom;
	private String recievedBy;
	private String remarks;
	private String narration;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private boolean active;

	private List<ToolRecieveFromCalibrationDetailsDTO> toolRecieveFromCalibrationDetailsDTO;

//	private List<ToolRecieveFromCalibrationImagesDTO> toolRecieveFromCalibrationImagesDTO;

}
