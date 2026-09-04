package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineMasterDTO {

    private Long id;

    private Long branch;

    private Long department;

    private Long type;

    private String machineInstrumentNo;

    private String machineInstrumentName;

    private String calibrationRequired;

    private Long location;

    private String processNo;

    private Long machineInstrumentCategory;

    private String section;

    private String model;

    private String serialNo;

    private String status;

    private String manufacturedBy;

    private Long madeIn;

    private Long purchasedFrom;

    private String modeOfPurchase;

    private String machineInstrumentIncharge;

    private String machineInstrumentUsedFor;

    private String pmChecklistNo;

    private String remarks;

    private String make;
    
    //image
    
    private String machineInstrumentImageName;
    
    private String machineOrInstrument;

    private boolean active;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;
    
    //technicalinfo
    
    private LocalDate installationDate;

    private BigDecimal powerConsumption;

    private BigDecimal consumption;

    private BigDecimal powerProduced;

    private String technicalSpecification;

    private BigDecimal capacity;

    private Long unit;

    private BigDecimal bedSizeMm;

    private BigDecimal currentInAmps;

    private BigDecimal voltage;

    private BigDecimal cushionTonnage;

    private BigDecimal parallelity;

    private Long machineType;

    private BigDecimal hourlyRate;

    private BigDecimal machineInstrumentWeight;

    private Long uom;

    private LocalDate warrantyStartDate;

    private LocalDate warrantyEndDate;

    private LocalDate lastCalibratedDate;

    private LocalDate nextDueDate;

    private String lifeCycleYear;

    private String range;

    private String errorAllowed;

    private String frequencyOfCalibration;

    private BigDecimal instrumentCost;

    private BigDecimal calibrationCost;

    private String calibrationAgency;

    private String certificateNo;

    private BigDecimal shutHeightMm;

    private BigDecimal strokeMm;

    private BigDecimal cushion;

    private BigDecimal hp;

    private String hcNo;

    private BigDecimal rangeSize;

    private BigDecimal leastcount;

    private BigDecimal goSize;

    private BigDecimal noGoSize;

    private BigDecimal ramSize;

    private BigDecimal throatDepth;

    private BigDecimal throatGap;

    private LocalDate maintenanceDate;
    
     
    private List<MachineSpareDetailsDTO> machineSpareDetailsDTO;
    
    
    private List<MachineHistoryDTO> machineHistoryDTO;
    
    
    private List<MachineMasterAttachmentDTO> machineMasterAttachmentDTO;
}