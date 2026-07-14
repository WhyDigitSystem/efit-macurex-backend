package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesItemParticularsDTO {
	private String partNo;
	private String partDesc;
	private String workOrderNo;
	private String CustomerPoNo;
	private LocalDate dueDate;
	private BigDecimal unitPrice;
	private BigDecimal qtyOfferd;
	private BigDecimal exRate;
	private BigDecimal discount;
	private BigDecimal igst;
	private BigDecimal cgst;
	private BigDecimal sgst;
	private String taxCode;

}
