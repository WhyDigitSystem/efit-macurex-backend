package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GSTRateMasterResponseDTO {

    private Long id;

    private CategoryResponseDTO category;

    private HsnResponseDTO hsnSacCode;

    private String description;

    private LocalDate wef;

    private boolean taxable;

    private BigDecimal rate;

    private BigDecimal igst;

    private BigDecimal sgst;

    private BigDecimal cgst;

    private boolean duplicateCheck;

    private Long orgId;


    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private String active;
}
