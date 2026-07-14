package com.efitops.basesetup.dto;

import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsSubmissionToBankDetailsDTO {
//	private Long id;
	private String documentName;
	private String status;
	@Lob
	private byte[] attachements;

}
