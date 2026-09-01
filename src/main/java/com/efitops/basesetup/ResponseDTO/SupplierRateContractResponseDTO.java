package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractResponseDTO {

    private Long id;

    private String docId;

    private BranchResponseDTO branch;

    private DepartmentResponseDTO department;

    private LocalDate docDate;

    private String belongsTo;

    private LocalDate validFrom;

    private LocalDate validTo;

    private CustomerDropdownResponseDTO customer;

    private String contractFor;

    private GSTStateMasterResponseDTO gstState;

    private boolean igstApplicable;

    private LocalDate deliveryDate;

    private String taxType;

    private CustomerDropdownResponseDTO serviceName;

    private HsnResponseDTO hsnSacCode;

    private boolean scrap;

    private BigDecimal taxPercentage;

    private BigDecimal discount;

    private String paymentsTerms;

    private String deliveryTerms;

    private BigDecimal freight;

    private ListOfValuesDetailsResponseDTO freightType;

    private ListOfValuesDetailsResponseDTO packingType;

    private BigDecimal insurance;

    private String modeOfDespatch;

    private BigDecimal inlandCharge;

    private EmployeeDropdownResponseDTO preparedBy;

    private EmployeeDropdownResponseDTO authoriedBy;

    private String narration;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private String active;

    private List<SupplierRateContractItemDetailsResponseDTO>
            supplierRateContractItemDetailsDTO;

    private List<SupplierRateContractTaxDetailsResponseDTO>
            supplierRateContractTaxDetailsDTO;
}