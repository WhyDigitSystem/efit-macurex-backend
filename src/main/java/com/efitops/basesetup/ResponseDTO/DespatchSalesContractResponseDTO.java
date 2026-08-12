package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchSalesContractResponseDTO {
	private String customerContractNo;
	private Long salesContractId;
	private LocalDate contractDate;
	private String invoiceType;

}
