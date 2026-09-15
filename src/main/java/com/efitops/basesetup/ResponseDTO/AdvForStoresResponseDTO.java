package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvForStoresResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String docId;

    private LocalDate docDate;

    private String belongsTo;

    private String docNo;

    private CustomerDropdownResponseDTO customer;

    private ItemResponseDTO incomingPartNo;

    private BomResponseDTO bom;

    private String time;

    private EmployeeResponseDTO preparedBy;

    private String remarks;

    private String createdBy;

    private String updatedBy;

    private boolean active;

    private boolean cancel;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private List<AdvForStoresDetailsResponseDTO> advForStoresDetails;
}