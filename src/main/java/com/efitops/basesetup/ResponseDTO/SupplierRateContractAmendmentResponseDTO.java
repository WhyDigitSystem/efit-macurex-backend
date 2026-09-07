package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractAmendmentResponseDTO {

    private Long id;
    private BranchResponseDTO branch;
    private String docId;
    private LocalDate docDate;
    private String belongsTo;
    private CustomerDropdownResponseDTO customer;
    private LocalDate contractDate;
    private String contractNo;
    private LocalDate validFrom;
    private LocalDate newValidFrom;
    private LocalDate validTo;
    private LocalDate newValidTo;
    private String revisionNo;
    private String freightType;
    private String packingType;
    private BigDecimal insuranceAmount;
    private String modeOfDespatch;
    private String taxDescription;
    private EmployeeResponseDTO preparedBy;
    private EmployeeResponseDTO authorisedBy;
    private String remarks;
    private Long orgId;
    private String financialYear;
    private String active;
    private String cancel;
    private String cancelRemarks;
    private String createdBy;
    private String updatedBy;
    private String screenName;
    private String screenCode;

    private List<SupplierRateContractAmendmentItemDetailsResponseDTO>
            itemDetails;
}