package com.efitops.basesetup.dto;

import javax.persistence.Column;

import com.efitops.basesetup.entity.PartyTypeVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyTypeDTO {

	private Long id;
	private String partyType;
	private String partyTypeCode;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private boolean cancel;
	private String cancelRemarks;
}
