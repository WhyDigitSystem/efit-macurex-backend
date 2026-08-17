package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface PurchaseDeliverySchService  {

	Map<String, Object> updateCreatePurchaseDeliverySchedule(PurchaseDeliveryScheduleDTO purchaseDeliveryScheduleDTO)
			throws ApplicationException;

	PurchaseDeliveryScheduleResponseDTO getPurchaseDeliveryScheduleById(Long id) throws ApplicationException;

	List<PurchaseDeliveryScheduleResponseDTO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branch) throws ApplicationException;

		Map<String, Object> getSupplierDropdown(Long branch, Long orgId) throws ApplicationException;


		Map<String, Object> getPurchaseUnitForPurchaseDeliverySchedule(Long item, Long branch, Long orgId)
				throws ApplicationException;
	
	
  
}
