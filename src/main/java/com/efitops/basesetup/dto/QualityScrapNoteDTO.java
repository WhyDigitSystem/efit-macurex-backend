package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualityScrapNoteDTO {

	private Long id;

	private Long branch;

	private LocalTime time;

	private Long belongsTo;

	private Long department;

	private Long fromLocation;

	private Long toLocation;

	private Long preparedBy;

	private Long authorizedBy;

	private BigDecimal totalScrapValue;

	private String qualityApproval;

	private String narration;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;

	private List<QualityScrapNoteDetailsDTO> qualityScrapNoteDetailsDTO;

}
