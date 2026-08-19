package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.GateInwardEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.PurchaseContractDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface PurchaseDeliverySchService {

	Map<String, Object> updateCreatePurchaseDeliverySchedule(PurchaseDeliveryScheduleDTO purchaseDeliveryScheduleDTO)
			throws ApplicationException;

	PurchaseDeliveryScheduleResponseDTO getPurchaseDeliveryScheduleById(Long id) throws ApplicationException;

	List<PurchaseDeliveryScheduleResponseDTO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	Map<String, Object> getSupplierDropdownForPurchaseDeliverySchedule(Long branch, Long orgId)
			throws ApplicationException;

//gate inward entry 
	Map<String, Object> updateCreateGateInwardEntry(GateInwardEntryDTO gateInwardEntryDTO) throws ApplicationException;

	GateInwardEntryResponseDTO getGateInwardEntryById(Long id) throws ApplicationException;

	List<GateInwardEntryResponseDTO> getGateInwardEntryByOrgId(Long branch, Long orgId) throws ApplicationException;
//customername dropdown

	List<Map<String, Object>> getCustomerNameDropdownForGateInwardEntry(Long branch, Long orgId) throws ApplicationException;

//	purchase contract
	Map<String, Object> updateCreatePurchaseContract(PurchaseContractDTO purchaseContractDTO)
			throws ApplicationException;

	List<Map<String, Object>> getSupplierDropdownForPurchaseContract(Long branch, Long orgId);

	PurchaseContractResponseDTO getPurchaseContractById(Long id) throws ApplicationException;

	List<PurchaseContractResponseDTO> getPurchaseContractByOrgId(Long branch, Long orgId) throws ApplicationException;

	List<Map<String, Object>> getEmployeeDropdownPurchaseContract(Long branch, Long orgId);

}
