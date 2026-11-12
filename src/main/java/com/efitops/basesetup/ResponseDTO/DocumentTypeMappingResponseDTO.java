package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeMappingResponseDTO {

    private Long id;

    private DocumentTypeMappingBranchResponseDTO branch;

    private FinancialYearResponseDTO financialYear;

    private Long orgId;

    private String description;

    private String active;
    

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private List<DocumentTypeMappingDetailsResponseDTO> documentTypeMappingDetails;
}