package com.efitops.basesetup.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialTransferReturnNoteDTO {

    private Long id;
    private String belongsTo;
    private String type;
    
    private Long fromLocation;
    private Long toLocation;
    private Long fgItem;
    private Long preparedBy;
    private Long branch;

    private String schOrderNo;
    
    private String approvedByPm;
    private String approvedByQc;
    private String approvedByStores;
    private String narration;

    private Long orgId;
    private String financialYear;
    private String createdBy;
    private boolean active;
    private boolean cancel;
    private String cancelRemarks;

    private List<MaterialTransferReturnNoteDetailsDTO> materialTransferReturnNoteDetailsDTO;
}