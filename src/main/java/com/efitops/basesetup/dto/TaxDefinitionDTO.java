package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.entity.TaxDefinitionDetailsVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDefinitionDTO {

	private long id;
	private Long module;
	private Long taxNo;
	private Long branch;
	private String taxDescription;
	private LocalDate docDate;
	private LocalDate effectiveDate;
	private String fillCopyOF;
	private String PrintName;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private String cancelRemarks;
	
	private List<TaxDefinitionDetailsDTO> details;
}
