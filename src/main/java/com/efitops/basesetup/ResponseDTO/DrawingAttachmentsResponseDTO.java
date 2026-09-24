package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawingAttachmentsResponseDTO {

    private Long id;

    private ListOfValuesDetailsResponseDTO typeOfItem;

    private ItemResponse1DTO fgPartNo;

    private String fgPartDescription;

    private boolean active;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
    private List<DrawingAttachmentDetailResponseDTO> drawingAttachmentDetailResponseDTO;

}