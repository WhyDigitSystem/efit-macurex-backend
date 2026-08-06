package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseGstDetailsDTO {
	private Long id;
	private String customerName;
	private String custometType;
	private String customerGstNo;
	private boolean isGstApproval;

}
