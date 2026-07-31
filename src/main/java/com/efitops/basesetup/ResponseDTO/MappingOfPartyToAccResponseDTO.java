package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappingOfPartyToAccResponseDTO {

    private Long id;

    private Long docId;

    private LocalDate docDate;

    private LocalDate asOnDate;

    private MappingBranchResponseDTO branch;

    private MappingCategoryResponseDTO category;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private String active;

    private List<MappingDetailsResponseDTO> details;

}