package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GateOutwardEntryDTO {
	
	    private Long id;

	    private Long plantId;

	    private String docId;
	    
	    private String serialNo;


	    private String breakDown;

	    private LocalDate docdate;

	    private String breakDownNo;

	    private LocalTime outwardTime;

	    private Long materialType;

	    private Long materialTakenOutBy;

	    private Long materialSentTo;

	    private String challanNo;

	    private String vehicleNo;

	    private String remarks;

	    private boolean active;

	    private Long orgId;

	    private String financialYear;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    private List<GateOutwardEntryDetailDTO>gateOutwardEntryDetailDTO;

}
