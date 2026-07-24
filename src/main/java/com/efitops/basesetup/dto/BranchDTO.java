package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDTO {
    private Long id;
    private String branchCode;
    private String branchName;
    private String branchIncharge;
    private String phoneNo;
    private String email;
    private String address;
    private String eccNo;
    private String division;
    private Long cityId;
    private String pincode;
    private Long stateId;
    private String gstinNo;
    private String panNo;
    private String cinNo;
    private String dunsNo;
    private Long orgId;
    private String createdBy;
    private boolean active;
    private String cancelRemarks;
    
    private List<BankDetailsDTO> bankDetails;
}
