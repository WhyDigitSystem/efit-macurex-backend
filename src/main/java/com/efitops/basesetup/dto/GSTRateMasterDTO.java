package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GSTRateMasterDTO {
	private Long id;
	private String category;
	private String hsncode;
	private String description;
	private String wef;
	private Double igstRate;
	private Double sgstRate;
	private Double cgstRate;
	private Double rate;
	private String taxable;
	private Long orgId;
	private String createdBy;;
	private String cancelRemarks;;
    private String finYear;
	private Long branchId;
    private boolean active;

}
