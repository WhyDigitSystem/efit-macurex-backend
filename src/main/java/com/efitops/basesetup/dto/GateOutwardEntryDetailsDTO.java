package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GateOutwardEntryDetailsDTO {

	private String item;
	private String itemDesc;
	private String uom;
	private int qty;

}
