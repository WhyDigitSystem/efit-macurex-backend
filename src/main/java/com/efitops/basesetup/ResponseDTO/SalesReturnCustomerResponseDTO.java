package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesReturnCustomerResponseDTO {
	
	 private String customerName;
	 private Long gstState;
	 private Long id;
	 private String customerCode;
	 
	 
	 private boolean gstApplicable;
	 private String gstNo;
	
	

}
