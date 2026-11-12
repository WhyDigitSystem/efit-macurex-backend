package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomCorrectionRequestNoteDTO {

    private Long id;

    private Long branch;

    private Long correctionRequestedBy;

    private Long correctionRequestApprovedBy;

    private Long fgPartNo;

    private String productName;

    private String customerPartNo;

    private String customerName;

    private String supplier;

    private String reasonForChange;

    private Long managerProduction;

    private Long managerQuality;

    private Long managerTdc;

    private Long managerPurchase;

    private Long authorisedSignator;

    private String decision;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private List<BomCorrectionRequestNoteDetailsDTO> details;
}