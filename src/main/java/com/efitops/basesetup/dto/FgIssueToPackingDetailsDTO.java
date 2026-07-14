package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FgIssueToPackingDetailsDTO {

	private String partName;
	private String partDesc;
	private BigDecimal totalQty;
	private BigDecimal issueQty;
}
