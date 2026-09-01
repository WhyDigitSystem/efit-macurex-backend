package com.efitops.basesetup.dto;

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
    
    
    private List<MachineTechnicalInfoDTO> machineTechnicalInfoDTO;
    
    
    private List<MachineSpareDetailsDTO> machineSpareDetailsDTO;
    
    
    private List<MachineHistoryDTO> machineHistoryDTO;
    
    
    private List<MachineMasterAttachmentDTO> machineMasterAttachmentDTO;
}