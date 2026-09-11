package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RootCauseAnalysisDTO {
	
	 private Long id;

	    private Long branch;

	    private Long complaintNo;

	    private Long itemCode;

	    private LocalDate complaintDate;

	    private String itemDescription;

	    private String complaintType;

	    private Long customerId;

	    

	    private String detailsOfComplaint;

	    private boolean active;

	    // Summary
	    private String narration;

	    private Long orgId;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    
	    private List<RootCauseAnalysisDetailsDTO> rootCauseAnalysisDetails;

	   

}
