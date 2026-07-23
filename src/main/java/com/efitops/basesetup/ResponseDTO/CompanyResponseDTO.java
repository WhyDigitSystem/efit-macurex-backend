package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDTO {

    private Long id;

    private String companyCode;
    private String companyName;
    private String email;
    private String phoneNo;
    private String panNo;
    private String gst;
    private String cin;
    private String officialWebsite;
    private String industryType;
    private String companySize;

    // Country
    private Long countryId;
    private String countryName;

    // State
    private Long stateId;
    private String stateName;

    // City
    private Long cityId;
    private String cityName;

    private String pincode;
    private String ceo;
    private String registeredAddress;
    private String selectPlan;
    private int trialPeriod;
    private String maxUsers;
    private String storageLimit;

    private String adminName;
    private String adminEmail;
    private String adminMobileNo;

    private byte[] companyLogo;

    private String createdBy;
    private String updatedBy;

    private String termsAndConditions;
    private String cancelRemarks;

    private String screenCode;
    private String screenName;

    private String active;
    private String cancel;

    private CreatedUpdatedDate commonDate;
}