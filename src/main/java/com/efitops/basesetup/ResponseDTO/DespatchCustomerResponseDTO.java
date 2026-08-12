package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchCustomerResponseDTO {
	  private Long id;

	    private String customerCode;

	    private String customerName;

	    private BigDecimal partyCreditLimit;

}
