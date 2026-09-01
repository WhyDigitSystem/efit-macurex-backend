package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterSpareDetailsResponseDTO {
    private Long id;

    private ItemResponse1DTO sparePartId;

    private String modelNo;

    private String serialNo;

    private String manufacturer;

    private LocalDate warrantyTillDate;

    private String calibrationReq;

    private LocalDate lastCalibDate;

    private LocalDate nextCalibDate;

}
