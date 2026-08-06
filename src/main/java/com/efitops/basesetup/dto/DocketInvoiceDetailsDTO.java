package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocketInvoiceDetailsDTO {
	private String docketNo;
	private LocalDate docketDate;
	private String invoiceNo;
	private int noOfQty;
	private double weight;
	private double totalValue;
	private Double cumulativeValue;
	private String mode;

}
