package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderShortCloseDetailsDTO {
	private String partNo;
	private String partName;
	private String drawingNo;
	private String revisionNo;
	private String uom;
	private BigDecimal orderQty;
	private BigDecimal shortageQty;

}
