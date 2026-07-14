package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessDoneDetailsDTO {

	private Long id;
	private String process;
	private String status;
	private String remarks;
}
