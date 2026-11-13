package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsDTO {
	private String drawing;

	private String partNo;

	private String issue;

	private String remarks;

}
