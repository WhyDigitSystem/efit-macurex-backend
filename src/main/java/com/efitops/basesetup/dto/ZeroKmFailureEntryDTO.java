package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZeroKmFailureEntryDTO {

    private Long id;

    private Long branch;

    private String docId;

    private LocalDate docDate;

    private Long customer;

    private String partyName;

    private boolean active;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
}