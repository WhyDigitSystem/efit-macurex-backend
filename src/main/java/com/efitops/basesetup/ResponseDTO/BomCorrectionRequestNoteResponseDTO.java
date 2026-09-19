package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomCorrectionRequestNoteResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String docId;

    private EmployeeDropdownResponseDTO correctionRequestedBy;

    private LocalDate docDate;

    private EmployeeDropdownResponseDTO correctionRequestApprovedBy;

    private ItemResponse1DTO fgPartNo;

    private String productName;

    private String customerPartNo;

    private String customerName;

    private String supplier;

    private String reasonForChange;

    private EmployeeDropdownResponseDTO managerProduction;

    private EmployeeDropdownResponseDTO managerQuality;

    private EmployeeDropdownResponseDTO managerTdc;

    private EmployeeDropdownResponseDTO managerPurchase;

    private EmployeeDropdownResponseDTO authorisedSignator;

    private String decision;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private List<BomCorrectionRequestNoteDetailsResponseDTO> details;
}