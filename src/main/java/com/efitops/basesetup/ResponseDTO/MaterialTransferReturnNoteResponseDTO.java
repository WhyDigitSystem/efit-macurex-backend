package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterDetailsReponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialTransferReturnNoteResponseDTO {

    private Long id;
    private String docId;
    private LocalDate docDate;
    private String belongsTo;
    private String type;
    private String schOrderNo;
    private LocalTime time;
    private BigDecimal totalValue;
    private String approvedByPm;
    private String approvedByQc;
    private String approvedByStores;
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

    private LocationMasterResponseDTO fromLocation;
    private LocationMasterResponseDTO toLocation;
    private ItemMasterDetailsResponseImportDTO fgItem;
    private EmployeeMasterDetailsReponseDTO preparedBy; 
    private BranchResponseDTO branch;

    private List<MaterialTransferReturnNoteDetailsResponseDTO> itemDetails;
}