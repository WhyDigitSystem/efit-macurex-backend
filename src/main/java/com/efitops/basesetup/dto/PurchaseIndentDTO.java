package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDTO {

    @JsonAlias({ "purchaseindent_id" })
    private Long id;

    private String indentNo; // ignored on create, auto-generated

    private Long plant; // branch id

    private Long belongsTo; // listofvaluesdetails id

    private LocalDate indentDate;

    private Long department; // department id

    private Long preparedBy; // employeemaster id

    private Long byWhom; // employeemaster id

    private boolean approved;

    private String remarks; // Indent Summary - direct field, no child table

    private Long orgId;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private List<PurchaseIndentDetailsDTO> details;
}