package com.efitops.basesetup.dto;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NpdDetailsDTO {
	private String documentRefNo;
	private String customer;
	private String partNo;
	private String partName;
	private LocalDate currentDate;
	private String revision;
	private String approvedBy;
	private String remarks;

}
