package com.efitops.basesetup.dto;

import com.efitops.basesetup.entity.ItemMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractAmdDetailsDTO {
	private Long id;
	private Long item;
	private Double oldRate;
	private Double newRate;
	private String validFrom;
	private String validTo;
	private String newValidDate;


}
