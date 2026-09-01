package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.IssuesResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.OpenStockEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ParameterMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentContractDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentItemDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderAmendmentResponceDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.SalesContractItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentContractDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentItemDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentResponseDto;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.IssuesDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.OpenStockEntryDto;
import com.efitops.basesetup.dto.ParameterMasterDTO;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDto;

import com.efitops.basesetup.dto.PurchaseOrderAmendmentDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;

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

	
	
	
	Integer getPurchaseContractAmdRevisionNo(String contractNo, Long orgId, Long branch) throws ApplicationException;

	String getEnquiryDocId(Long orgId, String financialYear, String screenCode);

	Map<String, Object> updateCreatePurchaseOrderAmendment(PurchaseOrderAmendmentDTO purchaseOrderAmendmentDTO,
			MultipartFile[] files) throws ApplicationException;

	PurchaseOrderAmendmentResponceDTO getPurchaseOrderAmendmentById(Long id)
	        throws ApplicationException;
	

	List<PurchaseOrderAmendmentResponceDTO> getPurchaseOrderAmendmentByOrgId(Long orgId)
	        throws ApplicationException;

	Map<String, Object> getPurchaseOrderAmendmentforCustomer(Long customer, Long branch, Long orgId)
			throws ApplicationException;

	Integer getPurchaseOrderAmendmentRevisionNo(String purchaseOrderNumber, Long orgId, Long branch)
			throws ApplicationException;

	
	List<Map<String, Object>> getCurrencyExchangeRateforPurchaseOrderAmendment(Long customer, Long orgId, Long branch)
			throws ApplicationException;

	Map<String, Object> getContractNoDropdownforPurchaseContractAmendment(Long orgId, Long branch, Long customerId)
			throws ApplicationException;

	Map<String, Object> getPurchaseContractAmendmentItemCodeDropdown(String docId, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> createUpdateOpenStockEntry(OpenStockEntryDto openStockEntryDto)
			throws ApplicationException;

	OpenStockEntryResponseDTO getOpenStockEntryById(Long id)
			throws ApplicationException;
	
	List<OpenStockEntryResponseDTO> getOpenStockEntryByOrgId(
	        Long orgId, Long branch)
	        throws ApplicationException;

	Map<String, Object> getOpenStockEntryItemCodeDropdown(Long orgId, Long branch) throws ApplicationException;

	String getOpenStockEntryDocId(Long orgId, String financialYear, String screenCode);

	Map<String, Object> createUpdateIssues(IssuesDTO issuesDto) throws ApplicationException;

	IssuesResponseDTO getIssuesById(Long id) throws ApplicationException;

	List<IssuesResponseDTO> getIssuesByOrgId(Long orgId, Long branchId) throws ApplicationException;

	Map<String, Object> getIssueFromLocationDropdown(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> getIssueToLocationDropdown(Long orgId, Long branch, Long issueFrom) throws ApplicationException;

	Map<String, Object> getIssueIndentNoDropdown(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> getIssueItemCodeDropdown(Long orgId, Long branch, String indentNo) throws ApplicationException ;

	Map<String, Object> createUpdateParameterMaster(ParameterMasterDTO parameterMasterDTO) throws ApplicationException ;

	ParameterMasterResponseDTO getParameterMasterById(Long id) throws ApplicationException ;

	List<ParameterMasterResponseDTO> getParameterMasterByOrgId(Long orgId) throws ApplicationException ;

	String getIssuesDocId(Long orgId, String financialYear);


	Map<String, Object> updateCreateMachineMaster(MachineMasterDTO machineMasterDTO, MultipartFile[] files) throws ApplicationException ;

	String getPurchaseOrderAmendmentDocId(Long orgId, String financialYear, String screenCode);

	MachineMasterResponseDTO getMachineMasterById(Long id) throws ApplicationException;

	List<MachineMasterResponseDTO> getMachineMasterByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getPurchaseOrderAmendmentItemCodeDropdown(String docId, Long branch, Long orgId)
			throws ApplicationException;

	String getMachineMasterDocId(Long orgId, String financialYear, String screenCode);
	
	
	
	
	
}
