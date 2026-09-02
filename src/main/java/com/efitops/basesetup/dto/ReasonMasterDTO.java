package com.efitops.basesetup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReasonMasterDTO {

    private Long id;

    private Long department;

    private Long reason;

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
}