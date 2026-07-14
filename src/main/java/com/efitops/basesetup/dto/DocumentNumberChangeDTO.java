package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentNumberChangeDTO {

	private Long id;
	private String documentScreenName;
	private String createdBy;
	private LocalDate docDate;

	private String branch;
	private Long orgId;
	private String branchCode;

	private String finYear;

	private List<DocumentNumberChangeDetailsDTO> documentNumberChangeDetailsDTO;

}
