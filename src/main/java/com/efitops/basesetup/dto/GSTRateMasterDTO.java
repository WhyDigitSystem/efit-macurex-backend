package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GSTRateMasterDTO {
	 private Long id;

	    @NotNull(message = "Category is required")
	    private Long category;

	    @NotNull(message = "HSN/SAC Code is required")
	    private Long hsnSacCode;

	    private String description;

	    private LocalDate wef;

	    private boolean taxable;

	    private BigDecimal rate;

	    private BigDecimal igst;

	    private BigDecimal sgst;

	    private BigDecimal cgst;

	    private boolean duplicateCheck;

	    @NotNull(message = "Organization is required")
	    private Long orgId;

	    private Long branch;

	    private String financialYear;

	    private String createdBy;


	    private String cancelRemarks;

	    private boolean active;


}
