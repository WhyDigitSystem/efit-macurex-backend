package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyMasterDTO {

	private Long id;
    private Long partyCategory;
    private Long partyCategory1;
    private Long partyCategory2;
    private Long supplierCategory;
    private Long branch;
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
    private Long logistics;
    private double partyCreditLimit;
    private int partyCreditPeriod;
    private boolean excisable;
    private String gstType;
    private String gstNo;
    private boolean igstApplicable;
    private String date;
    private Long orgId;
    private String createdBy;
    private boolean active ;
    private String finYear ;
    private String cancelRemarks;
}
