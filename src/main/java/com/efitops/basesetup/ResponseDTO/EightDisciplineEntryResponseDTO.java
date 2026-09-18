package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EightDisciplineEntryResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String docId;

    private LocalDate docDate;

    private String complaintType;

    private CustomerResponse1DTO customer;

    private Long complaintNo;

    private Long customerName;

    private Long itemCode;

    private String itemDescription;

    private Long rootCauseNo;

    private LocalDate rootCauseDate;

    private LocalDate dateOpened;

    private LocalDate targetDate;

    private String remarks;

    private boolean active;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
    
    private List<EightDiscipline1DetailResponseDTO> eightDiscipline1DetailResponseDTO;
    
    private List<EightDiscipline2DetailResponseDTO> eightDiscipline2DetailResponseDTO;
    
    private List<EightDiscipline3DetailResponseDTO> eightDiscipline3DetailResponseDTO;
    
    private List<EightDiscipline4DetailResponseDTO> eightDiscipline4DetailResponseDTO;
    
    private List<EightDiscipline5DetailResponseDTO> eightDiscipline5DetailResponseDTO;
    
    private List<EightDiscipline6DetailResponseDTO> eightDiscipline6DetailResponseDTO;
    
    private List<EightDiscipline7DetailResponseDTO> eightDiscipline7DetailResponseDTO;
    
    private List<EightDiscipline8DetailResponseDTO> eightDiscipline8DetailResponseDTO;
    
    
    
    
    
    
    
    
    
    
    
    
    
}