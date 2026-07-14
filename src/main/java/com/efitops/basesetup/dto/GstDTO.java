package com.efitops.basesetup.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GstDTO {
	private Long id;
	private String gstSlab;
	private BigDecimal gstPercentage;
	private BigDecimal igstPercentage;
	private BigDecimal cgstPercentage;
	private BigDecimal sgstPercentage;
	private Long orgId;
	private String createdBy;
	private boolean active;
}
