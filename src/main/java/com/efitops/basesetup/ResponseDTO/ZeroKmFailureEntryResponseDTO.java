package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

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

    private CustomerResponseDTO customer;

    private String partyName;

    private boolean active;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
}


