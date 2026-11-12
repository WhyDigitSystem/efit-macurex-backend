package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialTransferReturnNoteDetailsDTO {

	private Long item;
	private Long unit;
	private BigDecimal availableQty;
	private BigDecimal qty;
	private BigDecimal rate;
	private String reasonForRejectionTransfer;
	private Long supplier;
}
