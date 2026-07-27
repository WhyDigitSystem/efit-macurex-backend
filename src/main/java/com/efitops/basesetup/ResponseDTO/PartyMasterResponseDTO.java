package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyMasterResponseDTO {

    private Long id;

    private PartyCategoryResponseDTO partyCategory;
    private PartyCategoryResponseDTO partyCategory1;
    private PartyCategoryResponseDTO partyCategory2;

    private SupplierCategoryResponseDTO supplierCategory;

    private BranchResponseDTO branch;

    private LogisticsResponseDTO logistics;

    private boolean registered;
    private String salutation;
    private String partyType;
    private String vendorId;
    private String partyName;
    private String isGroupCompany;
    private String zone;
    private String vendorCode;
    private String groupName;
    private String legalName;
    private String tradeName;
    private String belongsTo;
    private double partyCreditLimit;	
    private int partyCreditPeriod;
    private boolean excisable;
    private String gstType;
    private String gstNo;
    private boolean igstApplicable;
    private String date;
    private Long orgId;
    private String createdBy;
    private String updatedBy;
    private boolean active;
    private String cancelRemarks;
}