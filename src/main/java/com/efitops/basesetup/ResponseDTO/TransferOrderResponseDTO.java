package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferOrderResponseDTO {

    private Long id;

    private ListOfValuesDetailsResponseDTO orderType;

    private String docId;

    private LocalDate docDate;

    private boolean active;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
    
    private List<TransferOrderDetailResponseDTO> transferOrderDetailResponseDTO;
    
    
}