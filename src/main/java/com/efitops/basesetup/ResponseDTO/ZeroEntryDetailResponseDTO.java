package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ZeroEntryDetailResponseDTO {
	
	    private Long id;

	    private String partNo;

	    private String partName;

	    private Long failureQty;

	    private String reason;

}
