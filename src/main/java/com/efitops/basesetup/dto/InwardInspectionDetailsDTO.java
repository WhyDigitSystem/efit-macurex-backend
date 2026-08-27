package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardInspectionDetailsDTO {

    private Long id;

    private Long item;

    private String inspection;

    private String stk;

    private String drawingNo;

    private BigDecimal orderQty;

    private Long purchaseUnit;

    private Long primaryUnit;

    private BigDecimal receivedQty;

    private Long receivedUnit;

    private BigDecimal acceptQty;

    private Long receivedLocation;

    private Long acceptUnit;

    private String inspectionReportReceived;

    private String tcReceived;

    private String batchNo;

    private BigDecimal qtyAccOnDevtn;

    private BigDecimal accQtyAfterSegn;

    private BigDecimal reworkQty;

    private Long reworkLocation;

    private BigDecimal totAccQty;

    private BigDecimal conversionFactor;

    private BigDecimal totalAccQtyInPrimaryUnit;

    private Long reworkUnit;

    private BigDecimal rejectQty;

    private Long rejectedLocation;

    private String reason;

    private Long rejectUnit;

    private BigDecimal rate;

    private BigDecimal totalReceivedQty;

    private BigDecimal sampleSize;

    private List<InwardInspectionMeasurementsDTO> inwardInspectionMeasurementsDTO;

}
