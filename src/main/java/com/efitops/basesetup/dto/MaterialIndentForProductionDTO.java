package com.efitops.basesetup.dto;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialIndentForProductionDTO {


    private Long id;

    
  
    private Long department;

    private String schOrderNo; 

    private String belongsTo;

    private Long fgItem;

    private BigDecimal schQty;

    private LocalDate scheduledDate;

    private Long toLocation;

    private Long fromLocation;

    private String createdBy;

    private boolean active ;
  
    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private Long branch;

    private List<MaterialIndentForProductionDetailsDTO> materialIndentForProductionDetailsDTO;


}
