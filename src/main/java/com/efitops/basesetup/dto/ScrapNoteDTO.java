package com.efitops.basesetup.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteDTO {
    private Long id;
    private LocalDate docDate;
    private String belongsTo;
    private Long department;
    private Long fromLocation;
    private Long toLocation;
    private Long fgPart;
    private String schOrderNo;
    private Long bom;
    private Long scrapPart;
    private Long preparedBy;
    private Long authorisedBy;
    private Long scrapId;
    private BigDecimal totalScrapValue;
    private String pmApproval;
    private String qualityApproval;
    private String storeApproval;
    private String narration;
    private String createdBy;
    private boolean active;
    private boolean cancel;
    private String cancelRemarks;
    private Long orgId;
    private String financialYear;
    private Long branch;

    private List<ScrapNoteDetailsDTO> scrapNoteDetailsDTO;
    private List<ScrapNoteReasonDetailsDTO> scrapNoteReasonDetailsDTO;
}
