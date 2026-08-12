package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortCloseItemResponseDTO {
	private Long itemId;

	private String itemCode;

	private String itemDescription;

}
