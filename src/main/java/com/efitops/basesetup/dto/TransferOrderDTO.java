package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransferOrderDTO {
	
	

    private Long id;

    private Long orderType;

    private String docId;

    private LocalDate docDate;

    private boolean active;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
    
    private List<TransferOrderDetailDTO> transferOrderDetailDTO;

}
