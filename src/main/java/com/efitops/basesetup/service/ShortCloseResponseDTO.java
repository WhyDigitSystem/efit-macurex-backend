package com.efitops.basesetup.service;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDetailsResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortCloseResponseDTO {

	private Long id;

	private String docId;

	private LocalDate docDate;

	private CustomerResponseDetailsDTO customerId;

	private String createdBy;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private BranchResponseDTO branch;

	private List<SalesOrderShortCloseDetailsResponseDTO> salesOrderShortCloseDetailsResponseDTO;

}
