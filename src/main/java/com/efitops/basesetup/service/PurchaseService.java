package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.*;
import com.efitops.basesetup.dto.*;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.exception.ApplicationException;

public interface PurchaseService {

    // ---------- Purchase Contract ----------
//    Map<String, Object> updateCreatePurchaseContract(PurchaseContractDTO dto, MultipartFile[] files) throws ApplicationException;
//    PurchaseContractResponseDTO getPurchaseContractById(Long id) throws ApplicationException;
//    List<PurchaseContractResponseDTO> getPurchaseContractByOrgId(Long orgId, Long branchId) throws ApplicationException;
//    String getPurchaseContractDocId(Long orgId, String finYear, Long branch);

    // ---------- Purchase Delivery Schedule ----------
//    Map<String, Object> updateCreatePurchaseDeliverySchedule(PurchaseDeliveryScheduleDTO dto) throws ApplicationException;
//    PurchaseDeliveryScheduleResponseDTO getPurchaseDeliveryScheduleById(Long id) throws ApplicationException;
//    List<PurchaseDeliveryScheduleResponseDTO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branchId) throws ApplicationException;
//    String getPurchaseDeliveryScheduleDocId(Long orgId, String finYear, Long branch);

    // ---------- Purchase Bill ----------
//    Map<String, Object> updateCreatePurchaseBill(PurchaseBillDTO dto) throws ApplicationException;
//    PurchaseBillResponseDTO getPurchaseBillById(Long id) throws ApplicationException;
//    List<PurchaseBillResponseDTO> getPurchaseBillByOrgId(Long orgId, Long branchId) throws ApplicationException;
//    String getPurchaseBillDocId(Long orgId, String finYear, Long branch);

    

    // ---------- Purchase Short Close ----------
    Map<String, Object> updateCreatePurchaseShortClose(PurchaseShortCloseDTO dto) throws ApplicationException;
    PurchaseShortCloseResponseDTO getPurchaseShortCloseById(Long id) throws ApplicationException;
    List<PurchaseShortCloseResponseDTO> getPurchaseShortCloseByOrgId(Long orgId, Long branchId) throws ApplicationException;
    String getPurchaseShortCloseDocId(Long orgId, String finYear, Long branch);

    // ---------- Local Purchase Order ----------
//    Map<String, Object> updateCreateLocalPurchaseOrder(LocalPurchaseOrderDTO dto, MultipartFile[] files) throws ApplicationException;
//    LocalPurchaseOrderResponseDTO getLocalPurchaseOrderById(Long id) throws ApplicationException;
//    List<LocalPurchaseOrderResponseDTO> getLocalPurchaseOrderByOrgId(Long orgId, Long branchId) throws ApplicationException;
//    String getLocalPurchaseOrderDocId(Long orgId, String finYear, Long branch);
//	purchase indent
    Map<String, Object> createUpdatePurchaseIndent(PurchaseIndentDTO purchaseIndentDTO, MultipartFile[] files)
			throws ApplicationException;
	PurchaseIndentResponseDTO getPurchaseIndentById(Long id) throws ApplicationException;
	List<PurchaseIndentResponseDTO> getPurchaseIndentByOrgId(Long orgId, Long branch) throws ApplicationException;
	List<Map<String, Object>> getPurchaseIndentPreparedByDropdown(Long orgId, Long branch);
	List<Map<String, Object>> getPurchaseIndentItemDropdown(Long orgId, Long branch);
	List<Map<String, Object>> getPurchaseIndentConversionFactorDropdown(Long orgId, Long branch,Long fromUnit,Long toUnit);
	List<Map<String, Object>> getPurchaseIndentDepartmentDropdown(Long orgId, Long branch);
    
    
    //purchase indent
 // Purchase Indent
//    Map<String, Object> createUpdatePurchaseIndent(PurchaseIndentDTO purchaseIndentDTO)
//            throws ApplicationException;
//
//    PurchaseIndentResponseDTO getPurchaseIndentById(Long id)
//            throws ApplicationException;
//
//    List<PurchaseIndentResponseDTO> getPurchaseIndentByOrgId(Long orgId, Long branch)
//            throws ApplicationException;
//
//    List<PurchaseIndentDepartmentDropdownResponseDTO> getPurchaseIndentDepartmentDropdown(
//            Long orgId, Long branch)
//            throws ApplicationException;
//
//    List<PurchaseIndentPreparedByDropdownResponseDTO> getPurchaseIndentPreparedByDropdown(
//            Long orgId, Long branch)
//            throws ApplicationException;
//    
//    
//    List<PurchaseIndentByWhomDropdownResponseDTO>
//    getPurchaseIndentByWhomDropdown(Long orgId, Long branch)
//            throws ApplicationException;
//	List<PurchaseIndentItemDropdownResponseDTO> getPurchaseIndentItemDropdown(Long orgId, Long branch)
//			throws ApplicationException;
//	
//	
//	List<PurchaseIndentConversionFactorDropdownResponseDTO>
//	getPurchaseIndentConversionFactorDropdown(Long orgId, Long branch)
//	        throws ApplicationException;
//	Map<String, Object> createUpdatePurchaseIndent(PurchaseIndentDTO purchaseIndentDTO, MultipartFile[] files)
//			throws ApplicationException;
//	String getPurchaseIndentDocId(Long orgId, String financialYear, String screenCode);

}