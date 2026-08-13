package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstructionScheduleResponseDTO {
	  private Long salesDeliveryScheduleId;
	    private String dlvNo;
	    private LocalDate dlvdate;
	    private String invoiceType;
//	    private Long salesDeliveryScheduleDetailsId;

//	    private Long itemId;
//	    private String itemCode;
//	    private String itemDescription;

	    private Double actualPlannedQty;
	    private BigDecimal dispatchedQty;
	    private BigDecimal balanceQty;

}
