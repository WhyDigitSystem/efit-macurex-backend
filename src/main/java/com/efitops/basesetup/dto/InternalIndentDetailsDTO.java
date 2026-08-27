package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalIndentDetailsDTO {

	private Long item;

	private BigDecimal requiredQty;

	private String purpose;

}
