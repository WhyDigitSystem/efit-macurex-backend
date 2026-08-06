package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocketInvoiceDetResponseDTO {
	private String docketNo;
	private LocalDate docketDate;
	private String invoiceNo;
	private int noOfQty;
	private double weight;
	private double totalValue;
	private Double cumulativeValue;
	private String mode;

}
