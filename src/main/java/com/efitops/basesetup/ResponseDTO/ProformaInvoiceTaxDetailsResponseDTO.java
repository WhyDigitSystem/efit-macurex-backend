package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProformaInvoiceTaxDetailsResponseDTO {

    private Long id;

	private String particulars;

	private BigDecimal amount;


}
