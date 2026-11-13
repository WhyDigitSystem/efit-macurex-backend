package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.entity.BranchVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPlanningResponseDTO {

    private Long id;

    private LocalDate fromDate;

    private String docId;

    private LocalDate docDate;

    private String mrpType;

    private String createdBy;

    private String updatedBy;

    private boolean active;
    
    private boolean cancel;

    private String cancelRemarks;

    private Long orgId;
    
    private BranchResponseDTO branch;

    private String financialYear;
}