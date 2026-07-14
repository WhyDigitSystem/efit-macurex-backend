package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractEnquiryDetailsDTO {
	private String part;
	private String partDescription;
	private String process;
	private BigDecimal qty;
	private LocalDate deliveryDate;
	private String remarks;

}
