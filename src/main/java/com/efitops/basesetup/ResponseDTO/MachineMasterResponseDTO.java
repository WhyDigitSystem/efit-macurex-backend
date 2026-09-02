package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineMasterResponseDTO {

    private Long id;

    // Plant
    private BranchResponseDTO branch;

    // Department
    private DepartmentResponseDTO department;

    // Type - List Of Values
    private ListOfValuesDetailsResponseDTO type;

    private String machineInstrumentNo;

    private String machineInstrumentName;

    private String calibrationRequired;

    // Location
    private LocationMasterResponseDTO location;

    private String processNo;

    // Machine / Instrument Category
    private ListOfValuesDetailsResponseDTO machineInstrumentCategory;

    private String section;

    private String model;

    private String serialNo;

    private String status;

    private String manufacturedBy;

    // Country
    private CountryResponseDTO madeIn;

    // Customer
    private CustomerResponse1DTO purchasedFrom;

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
    
    
    private List<MachineTechnicalInfoResponseDTO> machineTechnicalInfoResponseDTO;
    
    
    private List<MachineSpareDetailsResponseDTO> machineSpareDetailsResponseDTO;
    
    
    private List<MachineHistoryResponseDTO> machineHistoryResponseDTO;
    
    
//    private List<MachineMasterAttachmentResponseDTO> machineMasterAttachmentResponseDTO;
    

}