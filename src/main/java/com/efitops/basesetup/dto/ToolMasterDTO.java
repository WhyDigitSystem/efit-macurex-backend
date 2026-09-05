package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    
    private BigDecimal toolWeight;

    private Long unit;

    private String toolFixtureSize;

    private String lifeOfTool;

    private Long lifeType;

    private BigDecimal reconditionFreq;

    private BigDecimal setUpTimeInMinutes;

    private BigDecimal completedLifeCycle;

    private String toolMadeOf;

    private String technicalSpecification;

    private BigDecimal noOfStokesCompleted;

    private BigDecimal strokesCompletedAfterReconditioning;

    private LocalDate reconditionedDate;

    private BigDecimal toolFixtureCost;

    private BigDecimal toolFixtureAmortizedRecovered;
    
   
    
    private List<ToolMasterSpareDetailsDTO>toolMasterSpareDetailsDTO;
    
    private List<ToolMasterComponentOutPutDetailsDTO>toolMasterComponentOutPutDetailsDTO;
    
    private List<ToolMasterMachineHistoryDetailsDTO>toolMasterMachineHistoryDetailsDTO;
    
   

    
    

    

 
}

