package com.efitops.basesetup.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.GateInwardEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.PurchaseBillDTO;
import com.efitops.basesetup.dto.PurchaseContractDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDTO;
import com.efitops.basesetup.dto.ResponseDTO;
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
	

	List<Map<String, Object>> getSupplierDropdownForPurchaseContract(Long branch, Long orgId);

	PurchaseContractResponseDTO getPurchaseContractById(Long id) throws ApplicationException;

	List<PurchaseContractResponseDTO> getPurchaseContractByOrgId(Long branch, Long orgId) throws ApplicationException;

	List<Map<String, Object>> getEmployeeDropdownPurchaseContract(Long branch, Long orgId);

	Map<String, Object> updateCreatePurchaseContract(PurchaseContractDTO purchaseContractDTO, MultipartFile[] files)
			throws ApplicationException;

	Map<String, Object> getPurchaseContractItems(Long supplier, Long branch, Long orgId)
			throws ApplicationException;

//	Purchase bill 
	Map<String, Object> createUpdatePurchaseBill(PurchaseBillDTO purchaseBillDTO) throws ApplicationException;

	Map<String, Object> getSuppliersForPurchaseBill(Long orgId, Long branch) throws ApplicationException;

	PurchaseBillResponseDTO getPurchaseBillById(Long id) throws ApplicationException;

	List<PurchaseBillResponseDTO> getPurchaseBillByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> getItemsForPurchaseDeliverySchedule(String purchasecontractnumber, Long customer, Long branch,
			Long orgId) throws ApplicationException;

	Map<String, Object> getPurchaseOrderNumberForPurchaseDeliverySchedule(Long customer, LocalDate docdt, Long branch,
			Long orgId) throws ApplicationException;
	

	

}
