package com.efitops.basesetup.dto;

import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDTO {

    private Long id;

    private Long orgId;
    private String locationId;
    private String locationName;
    private String address;
    private Long phoneNo;
    private String faxNo;
    private String email;
    private String considerMrp;

    private BranchResponseDTO branchId;
    private BranchResponseDTO plantId;
    private ListOfVlauesDetailsResponseDTO locationTypeId;
    private ListOfVlauesDetailsResponseDTO belongsToId;
    private EmployeeMasterDetailsReponseDTO contactPersonNameId;
    private CustomerResponseDetailsDTO partyNameId;

    private String financialYear;

    private String createdBy;
    private String cancelRemarks;
}