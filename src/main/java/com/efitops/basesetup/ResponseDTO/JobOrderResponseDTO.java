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
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private BranchResponseDTO branch;

    private DepartmentResponseDTO department;

    private String belongsTo;

    private CustomerDropdownResponseDTO vendor;

    private GSTStateMasterResponseDTO gstState;

    private String jobOrderFor;

    private boolean isIgstAppl;

    private String contractNo;

    private ServiceAccMasterResponse1DTO serviceName;

    private String indentTime;

    private HsnResponseDTO hsnSacCode;

    private String taxType;

    private BigDecimal taxPercentage;

    private String paymentTerms;

    private LocalDate deliveryDate;

    private BigDecimal amount;

    private String narration;

    private String note;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String active;

    private String cancel;

    private String cancelRemarks;

    private String screenCode;

    private String screenName;

    private List<JobOrderDetailsResponseDTO> jobOrderDetails;

    private List<JobOrderTaxDetailsResponseDTO> jobOrderTaxDetails;

    private List<JobOrderAttachmentResponseDTO> attachments;
}
