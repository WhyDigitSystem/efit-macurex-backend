package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractSupplyScheduleItemDetailsResponseDTO {

    private Long id;

    private ItemMasterResponseDetailsDTO itemCode;

    private UnitMasterResponseDTO unit;

    private BigDecimal stock;

    private BigDecimal qty;

    private BigDecimal rate;

    private List<SubContractSupplyScheduleDetailsResponseDTO> scheduleDetails;
}