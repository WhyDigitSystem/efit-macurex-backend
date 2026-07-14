package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInventoryDTO {

	private String importLocal;
	private String stockLocation;
	private int minOrderQuantity;
	private int reOrderLevel;
}
