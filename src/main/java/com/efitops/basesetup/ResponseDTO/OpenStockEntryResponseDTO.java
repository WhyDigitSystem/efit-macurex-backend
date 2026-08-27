package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;


import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenStockEntryResponseDTO {
	
	 private Long id;
	
	 private BranchResponseDTO branch;
	 
	 private LocalDate asOnDate;
	 
	 private LocationMasterResponseDTO location;
	 
	 private ItemResponse1DTO item;
	 
	 private BigDecimal qty;

	 private BigDecimal rate;
		
	 private BigDecimal amount;

	 private String remarks;
		
	 private boolean active;
		
	 private Long orgId;

	 private String createdBy;
		 
	 private String cancelRemarks;
		

}
