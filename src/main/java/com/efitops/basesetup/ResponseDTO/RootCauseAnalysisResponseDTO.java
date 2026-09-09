package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RootCauseAnalysisResponseDTO {
	
	 private Long id;

	    private BranchResponseDTO branch;

	    private Long docId;

	    private LocalDate docDate;

	    private Long complaintNo;

	    private ItemMasterResponseDetailsDTO itemCode;

	    private LocalDate complaintDate;

	    private String itemDescription;

	    private String complaintType;

	    private Long customerId;

	    private String customerName;

	    private String customerPartNo;

	    private String detailsOfComplaint;

	    private boolean active;

	    // Summary
	    private String narration;
	    
	    //default

	    private Long orgId;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    
	    private List<RootCauseAnalysisDetailsResponseDTO> rootCauseAnalysisDetailsResponseDTO;
	    

}
