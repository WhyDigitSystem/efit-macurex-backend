package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDeliveryScheduleShortCloseDTO {

	private Long id;
	private String belongsTo;
	private Long supplierCode;

	private String type;

	private String purchaseOrderScheduleNo;

	private String referenceForShortClose;

	private String createdBy;

	private String narration;

	private boolean active;

	private boolean cancel;

	private String updatedBy;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branch;

	private List<PurchaseOrderDeliveryScheduleShortCloseDetailsDTO> purchaseOrderDeliveryScheduleShortCloseDetailsDTO;

}
