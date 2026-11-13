package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ToolDetailsDTO {
	private Long toolNo;
	private BigDecimal strokes;
	private BigDecimal strokesRate;
}