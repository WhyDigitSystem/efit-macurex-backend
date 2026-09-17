package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String docId;

    private LocalDate docDate;

    private String belongsTo;

    private DepartmentResponseDTO department;

    private CustomerDropdownResponseDTO vendor;

    private LocationMasterResponseDTO vendorLocation;

    private String gstState;

    private String gatePassNo;

    private Boolean isIGSTAppl;

    private String scheduleNo;

    private String gstnNo;

    private String rework;

    private String gstType;

    private boolean revsChrg;

    private LocalDate schStartDate;

    private ServiceAccMasterResponse1DTO serviceName;

    private LocalDate schEndDate;

    private HsnResponseDTO sacCode;

    private String contractNo;

    private String taxType;

    private String supplierDcNo;

    private BigDecimal taxPercentage;

    private LocalDate supplierDcDate;

    private String grnClearTime;

    private BigDecimal basicAmount;

    private String remarks;

    private BigDecimal totalTax;

    private BigDecimal totalAmount;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private List<SubContractingGRNDetailsResponseDTO> details;

    private List<SubContractingGRNTaxDetailsResponseDTO> taxDetails;
}