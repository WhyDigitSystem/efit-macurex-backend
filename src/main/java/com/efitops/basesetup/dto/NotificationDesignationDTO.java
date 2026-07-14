package com.efitops.basesetup.dto;

import java.util.List;

import javax.persistence.Column;

import com.efitops.basesetup.entity.NotificationDesignationDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotificationDesignationDTO {
	
	private Long id;
	
	private String createdBy;	
	
	private String branch;
//	private int count;
	

		
	private Long orgId;

	private String branchCode;

	private String finYear;
	
	private List<String> designationName;
	private List<String> designationCode;
	
	List<NotificationDesignationDetailsDTO> notificationDesignationDetailsDTO;
	
	
}
