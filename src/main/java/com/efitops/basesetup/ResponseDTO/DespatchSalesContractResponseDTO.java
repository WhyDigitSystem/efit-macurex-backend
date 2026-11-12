package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchSalesContractResponseDTO {
	private String orderAccepCustomerContractNo;
	private LocalDate date;
	private Long id;
	private String type;
	

}
