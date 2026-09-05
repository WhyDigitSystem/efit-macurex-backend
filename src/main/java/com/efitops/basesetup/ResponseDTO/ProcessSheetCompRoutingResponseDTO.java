package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessSheetCompRoutingResponseDTO {
	
	private Long id;

    private BranchResponseDTO branch;

    private ListOfValuesDetailsResponseDTO fgSfgItemType;

    private ItemResponse1DTO fgSfgItemCode;

    private String itemDescription;

    private String bomId;

    private boolean active;
    
    private BigDecimal totalMcValue;
    
    private BigDecimal totalLabourValue;
    
    private BigDecimal totalToolFixtureValue;
    
    private BigDecimal totalConsumablesValue;
    
    private BigDecimal totalOperationValue;
    
    private EmployeeMasterResponseDetailsDTO preparedBy;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;

    private List<ProcessSheetCompRoutingDetailResponseDTO> processSheetCompRoutingDetailResponseDTO;
    
    private List<ProcessSheetCompRoutingMachineResponseDTO> processSheetCompRoutingMachineResponseDTO;
    
    private List<ProcessSheetToolFixtureDetailsResponseDTO> processSheetToolFixtureDetailsResponseDTO;

    

}
