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
public class BillOfMaterialResponseDTO {

    // ---------- Header Fields ----------
    private Long id;
    private String docId;
    private LocalDate docDate;
    private String typeOfBom;
    private String typeOfItem;
    private Long fgSfgItemCode;
    private String fgSfgItemDescription;
    private Integer revisionNo;
    private String specifications;
    private String fillDetailsOf;
    private String fillDetailsOfItem;
    private LocalDate wef;
    private String fgReferenceToProfit;
    private String fmanbou;

    // ---------- Common / Audit Fields ----------
    private String createdBy;
    private String updatedBy;
    private String active;
    private String cancel;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    private Long orgId;
    private String financialYear;

    // ---------- Branch ----------
    private BranchResponseDTO branch;

    // ---------- Child List: Material Details ----------
    private List<BillOfMaterialDetailsResponseDTO> billOfMaterialDetailsResponseDTO;
}