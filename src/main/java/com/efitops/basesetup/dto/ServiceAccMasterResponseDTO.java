package com.efitops.basesetup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAccMasterResponseDTO {

    private Long id;

    private String serviceName;

    private String serviceDescription;

    private HsnResponseImageDTO itemHsn;

    private Long orgId;

    private String active;

    private boolean cancel;

    private String createdBy;

    private String updatedBy;

    private BranchResponseDTO branch;

    private String cancelRemarks;
}