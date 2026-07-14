package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyVendorEvaluationDTO {
	private String boughVendor;
	private String basicVenSelected;
	private String justification;
	private String slaPoints;
	private String commAgreedTerm;
}