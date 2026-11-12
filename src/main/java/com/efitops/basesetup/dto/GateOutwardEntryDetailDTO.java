package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GateOutwardEntryDetailDTO {
	
	
	    private Long id;

	    private Long itemCode;

	    private String itemDescription;

	    private String toolMachineInstrumentNo;

	    private String toolMachineInstrumentNoDesc;

	    private Long unit;

	    private BigDecimal quantity;

}
