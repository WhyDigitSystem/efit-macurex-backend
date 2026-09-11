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
public class ProductionScheduleOrderResponseDTO {

    // ---------- Header Fields ----------
    private Long id;
    private String docId;
    private LocalDate docDate;
    private String orderType;
    private String lcPoNo;
    private LocalDate lcPoDate;
    private Long fgSfgItemCode;
    private String compRouteNo;
    private Long bom;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;

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

    private BranchResponseDTO branch;

    private List<ProductionScheduleOrderDetailsResponseDTO> productionScheduleOrderDetailsResponseDTO;
    private List<ScheduleDetailsResponseDTO> scheduleDetailsResponseDTO;
}