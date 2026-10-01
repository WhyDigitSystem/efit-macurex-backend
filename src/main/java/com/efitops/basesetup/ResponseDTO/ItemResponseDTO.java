package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.Data;

@Data
public class ItemResponseDTO {

	private Long id;
    private String itemCode;
    private String itemDescription;
    private UnitMasterResponseDTO unit;
}
