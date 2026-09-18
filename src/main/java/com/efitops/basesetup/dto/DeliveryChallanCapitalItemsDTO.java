package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanCapitalItemsDTO {

    private Long id;

    private Long branch;

    private String belongsTo;

    private Long department;

    private Long vendor;

    private String indentNo;

    private Long customerLocation;

    private String transportName;

    private String vehicleNo;

    private String dcType;

    private String approvalByStores;

    private Long preparedBy;

    private Long approvedBy;

    private String remarks;

    private String createdBy;

    private boolean active;
    
    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private List<DeliveryChallanCapitalItemsDetailsDTO> details;
}
