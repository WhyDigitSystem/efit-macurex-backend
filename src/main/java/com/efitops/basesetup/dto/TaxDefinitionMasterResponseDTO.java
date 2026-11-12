package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDefinitionMasterResponseDTO {

    private Long id;

    private ListOfVlauesDetailsResponseDTO module;

    private Long taxNo;

    private BranchResponseDTO branch;

    private String taxDescription;

    private LocalDate docDate;

    private LocalDate effectiveDate;

    private String fillCopyOF;

    private String printName;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private Boolean active;

    private String cancelRemarks;

    private List<TaxDefinitionDetailsResponseDTO> details;
}
