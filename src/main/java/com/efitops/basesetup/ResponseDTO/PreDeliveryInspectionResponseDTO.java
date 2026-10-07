package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreDeliveryInspectionResponseDTO {

    private Long id;
    private String docId;
    private String transferSlipNo;
    private LocalDate transferSlipDate;
    private String belongsTo;
    private ItemMasterDetailsResponseImportDTO itemCode; 
    private String itemDrawingNo;
    private String prodSchOrdNo;
    private LocationMasterResponseDTO fromLocation;
    private BigDecimal stock;
    private String customerCode;
    private String lcDeliverySchNo;
    private String poNo;
    private String invNo;
    private LocalDate invDate;
    private String initialPlanNo;
    private LocalDate date;
    private LocalDate schOrdDate;
    private BigDecimal schQty;
    private BigDecimal producedQty;
    private String customerName;
    private String custPartNo;
    private LocalTime time;

    private BigDecimal qtyInspected;
    private BigDecimal qtyPassed;
    private LocationMasterResponseDTO toLocation;
    private BigDecimal rate;
    private BigDecimal rejQty;
    private LocationMasterResponseDTO rejectedLocation;
    private String reasonForRejection;
    private BigDecimal scrapQty;
    private BigDecimal reworkQty;
    private String reasonForRework;
    private String remarks;
    private EmployeeMasterResponseDetailsDTO inspectedBy;
    private EmployeeMasterResponseDetailsDTO checkedBy;

    private String createdBy;
    private String updatedBy;
    private String active;
    private String cancel;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    private Long orgId;
    private String financialYear;
    private BranchResponseDTO branch;

    private List<PreDeliveryInspectionDetailsResponseDTO> inspectionDetails;

}
