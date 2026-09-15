package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZeroKmFailureEntryResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String docId;

    private LocalDate docDate;

    private CustomerResponse1DTO customer;

    private String partyName;
    
    private String remarks;

    private boolean active;

    private Long orgId;
    
    private String financialYear;

   
	private String createdBy;
	
	private String updatedBy;
	
    private boolean cancel;

    private String cancelRemarks;
    
    private List<ZeroEntryDetailResponseDTO> zeroEntryDetailResponseDTO;
    
}


