package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GateOutwardEntryResponseDTO {
	
	
	    private Long id;

	    private BranchResponseDTO plantId;

	    private String docId;
	    
	    private String serialNo;

	    private String breakDown;

	    private LocalDate docdate;

	    private String breakDownNo;

	    private LocalTime outwardTime;

	    private ToolCategoryDetailResponseDTO materialType;

	    private EmployeeResponseDTO materialTakenOutBy;

	    private CustomerResponse1DTO materialSentTo;

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
	    
	    
	    private List<GateOutwardEntryDetailResponseDTO>gateOutwardEntryDetailResponseDTO;

}
