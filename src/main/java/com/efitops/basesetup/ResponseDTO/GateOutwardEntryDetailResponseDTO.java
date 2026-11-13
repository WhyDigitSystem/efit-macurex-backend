package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GateOutwardEntryDetailResponseDTO {
	
	
	    private Long id;

	    private ItemMasterDetailsResponseImportDTO itemCode;

	    private String itemDescription;

	    private String toolMachineInstrumentNo;

	    private String toolMachineInstrumentNoDesc;

	    private UnitResponseDTO unit;

	    private BigDecimal quantity;  

}
