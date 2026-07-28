package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LMEDTO {
	
	private Long id;
	private Long branch;

	private Long currencyName;
	private Double lmeRate;
	private String lmeDateFrom;
	private String elmeDateTo;
	private Long orgId;
	private String finyear;
	private Boolean active;
	private String cancelRemarks;
	private String createdBy;

}
