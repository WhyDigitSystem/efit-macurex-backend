package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardInspectionDetailsResponseDTO {

	private Long id;
	private ItemMasterDetailsResponseInwardDTO item;
	private String inspection;
	private String stk;
	private String drawingNo;
	private BigDecimal orderQty;
//    private UnitResponseDTO purchaseUnit;
//    private UnitResponseDTO primaryUnit;
	private BigDecimal receivedQty;
	private UnitResponseDTO receivedUnit;
	private BigDecimal acceptQty;
	private LocationMasterResponseDTO receivedLocation;
	private UnitResponseDTO acceptUnit;
	private String inspectionReportReceived;
	private String tcReceived;
	private String batchNo;
	private BigDecimal qtyAccOnDevtn;
	private BigDecimal accQtyAfterSegn;
	private BigDecimal reworkQty;
	private LocationMasterResponseDTO reworkLocation;
	private BigDecimal totAccQty;
	private BigDecimal conversionFactor;
	private BigDecimal totalAccQtyInPrimaryUnit;
	private UnitResponseDTO reworkUnit;
	private BigDecimal rejectQty;
	private LocationMasterResponseDTO rejectedLocation;
	private String reason;
	private UnitResponseDTO rejectUnit;
	private BigDecimal rate;
	private BigDecimal amount;
	private BigDecimal totalReceivedQty;
	private BigDecimal sampleSize;

	private List<InwardInspectionMeasurementsResponseDTO> inwardInspectionMeasurementsResponseDTO;
}
