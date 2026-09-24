package com.efitops.basesetup.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionSchOrderShortCloseDTO {
    private Long id;
    private Long item;
    private Long unit;
    private String narration;
    private String createdBy;
    private boolean active;
    private boolean cancel;
    private String cancelRemarks;
    private Long orgId;
    private String financialYear;
    private Long branch;

    private List<ProductionOrderDetailsDTO> productionOrderDetailsDTO;
}
