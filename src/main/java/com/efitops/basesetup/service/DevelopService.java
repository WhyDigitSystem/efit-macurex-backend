package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentContractDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentItemDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentResponseDto;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDto;
import com.efitops.basesetup.dto.SalesOrderAmendmentDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentResponseDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DevelopService {

	EnquiryResponseDTO getEnquiryById(Long id) throws ApplicationException;

	List<EnquiryResponseDTO> getEnquiryByOrgId(Long orgId, Long branchId) throws ApplicationException;

//	Map<String, Object> uploadEnquiryAttachment(Long enquiryId, MultipartFile file) throws ApplicationException;
//
//	ResponseEntity<byte[]> viewEnquiryAttachment(Long attachmentId) throws ApplicationException;

	Map<String, Object> updateCreateEnquiry(EnquiryDTO enquiryDTO, MultipartFile[] files) throws ApplicationException;

	// SALES RETURN

//	Map<String, Object> createUpdateSalesReturn(SalesReturnDTO salesReturnDTO) throws ApplicationException;
//
//	SalesReturnResponseDTO getSalesReturnById(Long id) throws ApplicationException;
//
//	List<SalesReturnResponseDTO> getAllSalesReturn(Long orgId, Long branch) throws ApplicationException;

	// salesorderamendment

	Map<String, Object> createUpdateSalesOrderAmendment(SalesOrderAmendmentDTO salesOrderAmendmentDTO)
			throws ApplicationException;

	SalesOrderAmendmentResponseDTO getSalesOrderAmendmentById(Long id) throws ApplicationException;

	List<SalesOrderAmendmentResponseDTO> getSalesOrderAmendmentByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getOrderAcceptanceBySalesOrderAmendment(Long orgId, Long branch)
			throws ApplicationException;

//	Map<String, Object> getItemDropdownBySalesOrderAmendment(
//	        Long salesContractId,
//	        Long orgId,
//	        Long branch)
//	        throws ApplicationException;

	List<Map<String, Object>> getOrderAcceptanceItemsWithAmendment(String docId, Long orgId, Long branch)
			throws ApplicationException;

	Integer getSalesOrderAmdRevisionNo(String salesOrderNo, Long item, Long orgId, Long branch)
			throws ApplicationException;

	// PurchaseContractAmendment

	Map<String, Object> createUpdatePurchaseContractAmendment(PurchaseContractAmendmentDto purchaseContractAmendmentDto,
			MultipartFile[] files) throws ApplicationException;

	PurchaseContractAmendmentResponseDto getPurchaseContractAmendmentById(Long id) throws ApplicationException;

	List<PurchaseContractAmendmentResponseDto> getPurchaseContractAmendmentByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	List<PurchaseContractAmendmentContractDropdownResponseDto> getContractNoDropdownforPurchaseContractAmendment(
			Long orgId, Long branch) throws ApplicationException;

	List<PurchaseContractAmendmentItemDropdownResponseDto> getItemDropdownForPurchaseContractAmendment(Long contractId)
			throws ApplicationException;

	Integer getPurchaseContractAmdRevisionNo(String contractNo, Long orgId, Long branch) throws ApplicationException;

	String getEnquiryDocId(Long orgId, String financialYear, String screenCode);

}
