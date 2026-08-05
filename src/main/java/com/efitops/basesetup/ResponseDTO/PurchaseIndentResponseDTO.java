package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentResponseDTO {

    private Long id;
    private String indentNo;
    private BranchResponseDTO plant;
    private ListOfVlauesDetailsResponseDTO belongsTo;
    private LocalDate indentDate;
    private DepartmentResponseDTO department;
    private EmployeeResponseDTO preparedBy;
    private EmployeeResponseDTO byWhom;
    private boolean approved;
    private String remarks;
    private Long orgId;
    private String createdBy;
    private String updatedBy;
    private String active;
    private String cancelRemarks;

    private List<PurchaseIndentDetailsResponseDTO> details;
    private List<PurchaseIndentAttachmentResponseDTO> attachments;
}