package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterSpareDetailsDTO {


    private Long sparePartId;

    private String modelNo;

    private String serialNo;

    private String manufacturer;

    private LocalDate warrantyTillDate;

    private String calibrationReq;

    private LocalDate lastCalibDate;

    private LocalDate nextCalibDate;

   
}

