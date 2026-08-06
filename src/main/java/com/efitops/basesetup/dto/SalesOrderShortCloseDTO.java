package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderShortCloseDTO {

	private Long id;

	private Long customerId;

	private String docId;

	private String createdBy;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branchId;

	private List<SalesOrderShortCloseDetailsDTO> salesOrderShortCloseDetailsDTO;

}
