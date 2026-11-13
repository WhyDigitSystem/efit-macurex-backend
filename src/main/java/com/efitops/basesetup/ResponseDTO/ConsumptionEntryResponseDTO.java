package com.efitops.basesetup.ResponseDTO;


import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionEntryResponseDTO {

    private Long id;
    private String docId;
    private LocalDate docDate;
    private String type;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String consumption;
    private ListOfValuesResponseDTO entryType;
    private String narration;
    
    private String createdBy;
    private String updatedBy;
    private String active;
    private String cancel;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    private Long orgId;
    private String financialYear;

    private LocationMasterResponseDTO location;
    private BranchResponseDTO branch;

    // Details Lists
    private List<ConsumptionEntryDetailsResponseDTO> consumptionEntryDetailsResponseDTO;
    private List<RmConsumptionEntryDetailsResponseDTO> rmConsumptionEntryDetailsResponseDTO;
}