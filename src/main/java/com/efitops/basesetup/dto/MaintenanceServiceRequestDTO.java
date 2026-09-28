package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MaintenanceServiceRequestDTO {
	
	
	    private Long id;

//	    private String docId;

//	    private LocalDate docDate;

	    private Long belongTo;

	    private Long department;

	    private String mailId;

	    private String reportedTime;

	    private String phoneNo;

	    private String completed;

	    private Long priority;

	    private LocalDate closingDate;

	    private Long requestedBy;

	    private Long preparedBy;

	    private String approvedBy;

	    private String serviceRequired;

	    private String remarks;
	    
	    private boolean active;

	    private Long orgId;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    
	    
	    

}
