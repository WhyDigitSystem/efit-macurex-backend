package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JOShortCloseItemResponseDTO {

	private Long id;
	private String itemDescription;
	private String itemCode;
}
