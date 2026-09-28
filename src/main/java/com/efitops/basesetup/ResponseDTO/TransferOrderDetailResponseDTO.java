package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferOrderDetailResponseDTO {

    private Long id;

    private LocalDate orderDate;

    private ItemResponse1DTO itemCode;

    private String itemDescription;

    private LocalDate scheduleDate;

    private Double qty;

    private String unit;

    private Double purQty;

    private String purUnit;

    private CustomerResponse1DTO supplierId;

    private String supplierName;

    private String type;

    private String combineWith;

    private String transId;

    private String contractNo;
}