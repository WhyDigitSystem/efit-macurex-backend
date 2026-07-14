package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueToSubContractorDetailsDTO {
	private String item;
	private String itemDescription;
	private List<String> process;
	private BigDecimal quantity;
	private String remarks;
}
