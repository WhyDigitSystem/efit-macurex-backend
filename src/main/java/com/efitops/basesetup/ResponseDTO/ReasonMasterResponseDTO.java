package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReasonMasterResponseDTO {

    private Long id;

    private DepartmentResponseDTO department;

    private ListOfValuesDetailsResponseDTO reason;

    private String reasonCode;

    private String reasonDescription;

    private String narration;

    private boolean active;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private String activeStatus;

    private String cancelStatus;
}

