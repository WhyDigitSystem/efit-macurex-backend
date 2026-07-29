package com.efitops.basesetup.ResponseDTO;

import lombok.Data;

@Data
public class ItemResponseDTO {

	private Long id;
    private String itemCode;
    private String itemDescription;
    private UnitResponseDTO unit;
}
