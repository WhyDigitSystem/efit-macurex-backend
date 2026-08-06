package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnquiryDetailsReponseDTO {

	private Long id;

	private String itemCode;

	private String itemDescription;

	private Integer annualquantity;

	private LocalDate dlrydate;

	private String needrdapproval;

	private LocalDate quoteduedate;

	private String remarks;

}
