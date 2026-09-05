package com.efitops.basesetup.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.GateInwardEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.InternalIndentResponseDTO;
import com.efitops.basesetup.ResponseDTO.PhysicalStockReConcilationResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.InternalIndentDTO;
import com.efitops.basesetup.dto.PhysicalStockReConcilationDTO;
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

	List<Map<String, Object>> getCustomerNameDropdownForGateInwardEntry(Long branch, Long orgId)
			throws ApplicationException;

//	purchase contract

	List<Map<String, Object>> getSupplierDropdownForPurchaseContract(Long branch, Long orgId);

	PurchaseContractResponseDTO getPurchaseContractById(Long id) throws ApplicationException;

	List<PurchaseContractResponseDTO> getPurchaseContractByOrgId(Long branch, Long orgId) throws ApplicationException;

	List<Map<String, Object>> getEmployeeDropdownPurchaseContract(Long branch, Long orgId);

	Map<String, Object> updateCreatePurchaseContract(PurchaseContractDTO purchaseContractDTO, MultipartFile[] files)
			throws ApplicationException;

	Map<String, Object> getPurchaseContractItems(Long supplier, Long branch, Long orgId) throws ApplicationException;

//	Purchase bill 
	Map<String, Object> createUpdatePurchaseBill(PurchaseBillDTO purchaseBillDTO) throws ApplicationException;

	Map<String, Object> getSuppliersForPurchaseBill(Long orgId, Long branch) throws ApplicationException;

	PurchaseBillResponseDTO getPurchaseBillById(Long id) throws ApplicationException;

	List<PurchaseBillResponseDTO> getPurchaseBillByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> getItemsForPurchaseDeliverySchedule(String purchasecontractnumber, Long customer, Long branch,
			Long orgId) throws ApplicationException;

	Map<String, Object> getPurchaseOrderNumberForPurchaseDeliverySchedule(Long customer, LocalDate docdt, Long branch,
			Long orgId) throws ApplicationException;

	String getPurchaseDeliveryScheduleDocId(Long orgId, String financialYear);

	Map<String, Object> getGrnNoDropdownforPurchaseBill(Long orgId, Long branch, Long supplier)
			throws ApplicationException;

	Map<String, Object> getItemDropDownForPurchaseBill(Long orgId, Long branch, Long supplier, String grnNo)
			throws ApplicationException;

//	internal indent
	Map<String, Object> updateCreateInternalIndent(InternalIndentDTO internalIndentDTO) throws ApplicationException;

	List<Map<String, Object>> getItemDropdownForInternalIndent(Long branch, Long orgId) throws ApplicationException;

	List<InternalIndentResponseDTO> getInternalIndentByOrgId(Long orgId, Long branch) throws ApplicationException;

	InternalIndentResponseDTO getInternalIndentById(Long id) throws ApplicationException;

	String getInternalIndentDocId(Long orgId, String financialYear);

//	physical stock reconcilation

	Map<String, Object> updateCreatePhysicalStockReConcilation(
			PhysicalStockReConcilationDTO physicalStockReConcilationDTO) throws ApplicationException;

	List<PhysicalStockReConcilationResponseDTO> getPhysicalStockReConcilationByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	PhysicalStockReConcilationResponseDTO getPhysicalStockReConcilationById(Long id) throws ApplicationException;

	List<Map<String, Object>> getLocationDropdownForPhysicalStockReConcilation(Long locationType, Long branch,
			Long orgId) throws ApplicationException;

	String getPhysicalStockReConcilationDocId(Long orgId, String financialYear);

//	purchase order amendment dropdown
	

	

}
