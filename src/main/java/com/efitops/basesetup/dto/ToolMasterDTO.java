package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterDTO {

    private Long id;

    private Long branch;

    private Long type;

    private Long department;

    private String toolNo;

    private String toolDescription;

    private String PMChecklistNo;

    private String toolCategory;

    private Long location;

    private String drawingNo;

    private String serialNo;

    private String manufacturedBy;

    private String section;

    private String status;

    private Long madeIn;

    private Long purchaseFrom;

    private Long modeOfPurchase;

    private Long toolIncharge;

    private String toolUsedFor;

    private Long toolOwnership;

    private Long presentLocation;

    private BigDecimal toolCost;

    private String cavityNumber;

    private String remarks;

    private String toolName;

    private String image;

    private Long orgId;

    private String financialYear;

    private boolean active;

    private String cancelRemarks;

    private String createdBy;
    
    private List<ToolMasterTechnicalInfoDetailsDTO>toolMasterTechnicalInfoDetailsDTO;
    
    private List<ToolMasterSpareDetailsDTO>toolMasterSpareDetailsDTO;
    
    private List<ToolMasterComponentOutPutDetailsDTO>toolMasterComponentOutPutDetailsDTO;
    
    private List<ToolMasterMachineHistoryDetailsDTO>toolMasterMachineHistoryDetailsDTO;
    
    private List<ToolMasterAttachementDTO>toolMasterAttachementDTO;

    
    

    

 
}

