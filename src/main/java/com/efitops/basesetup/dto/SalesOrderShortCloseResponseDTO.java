package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.entity.SalesOrderShortCloseFileDetailsVO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderShortCloseResponseDTO {

	private Long id;

	private String docId;

	private CustomerResponseDetailsDTO customerId;

	private String createdBy;
	
	private String updatedBy;
	
	private LocalDate docDate=LocalDate.now();


	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private BranchResponseDTO branchId;

	private List<SalesOrderShortCloseDetailsResponseDTO> salesOrderShortCloseDetailsResponseDTO;
	
	private List<SalesOrderShortCloseFileDetailsResponseDTO> salesOrderShortCloseFileDetailsResponseDTO;

}
