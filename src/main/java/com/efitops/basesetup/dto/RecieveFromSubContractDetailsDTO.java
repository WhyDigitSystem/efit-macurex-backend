package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecieveFromSubContractDetailsDTO {

	private String partName;
	private String partDesc;
	private BigDecimal issueQty;
	private BigDecimal recieveQty;
	private BigDecimal pendingQty;
	private String remarks;

}
