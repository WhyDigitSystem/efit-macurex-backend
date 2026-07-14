package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.QuotationDTO;
import com.efitops.basesetup.dto.WorkOrderDTO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.QuotationVO;
import com.efitops.basesetup.entity.QuoteRevisionVO;
import com.efitops.basesetup.entity.WorkOrderVO;
import com.efitops.basesetup.exception.ApplicationException;

import io.jsonwebtoken.io.IOException;

@Service
public interface CustomerEnquiryService {

	// Enquiry

	Map<String, Object> createUpdateEnquiry(EnquiryDTO enquiryDTO) throws ApplicationException;

	List<EnquiryVO> getAllEnquiryByOrgId(Long orgId, String finYear, String branchCode);

	EnquiryVO getEnquiryById(Long id);

	String getEnquiryDocId(Long orgId, String finYear,String branchCode);

	List<Map<String, Object>> getCustomerNameAndCode(Long orgId);

	List<Map<String, Object>> getContactNameAndNo(Long orgId, String partyCode);

	List<Map<String, Object>> getPartNoAndDescription(Long orgId);

	List<Map<String, Object>> getDrawingNoAndRevisionNo(String partNo, Long orgId);
	

	List<Map<String, Object>>  getEnquiryDetails(Long orgId, String status,String partyName ) throws ApplicationException;
	// Quotation

	Map<String, Object> createUpdateQuotation(@Valid QuotationDTO quotationDTO) throws ApplicationException;

	List<QuotationVO> getAllQuotationByOrgId(Long orgId, String finYear, String branchCode);

	QuotationVO getQuotationById(Long id);

	String getQuotationDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getEnquiryNoAndDate(Long orgId, String customerCode);

	List<Map<String, Object>> getProductionManager(Long orgId);

	List<Map<String, Object>> getPartNoAndPartDesBasedOnEnquiryNo(Long orgId, String docId, String customerCode);

	// WorkOrder

	Map<String, Object> createUpdateWorkOrder(WorkOrderDTO workOrderDTO) throws ApplicationException;

	List<WorkOrderVO> getAllWorkOrderByOrgId(Long orgId, String finYear, String branchCode);

	WorkOrderVO getWorkOrderById(Long id);

	String getWorkOrderDocId(Long orgId, String finYear,String branchCode);

	List<Map<String, Object>> getQuotationNumber(Long orgId, String custmoerId);

	List<Map<String, Object>> getWorkOrderPartNo(Long orgId, String docId, String custmoerId);

	List<Map<String, Object>> getWorkOrderShowsDetails(Long orgId, String branchCode, String itemCode);

	List<Map<String, Object>> getWorkOrderReport(Long orgId,String branchCode,String customerCode,String status);

	List<Map<String, Object>> getQuotationByOrgid(Long orgId);

	String getEnquiryIdIteration(Long orgId, String clientName, String enquiryNo);

	List<Map<String, Object>> getQuotationDetailsReport(Long orgId, String branchCode, String customerName,
			String fromDate, String toDate,String status);

	List<QuoteRevisionVO> getCountQuoteRevision(Long orgId, String docId);

	Map<String, Object> createUpdateEnquiry(MultipartFile[] files, String docId, String screenName, String module)
			throws ApplicationException, java.io.IOException;

	ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws java.io.IOException, java.io.IOException;

	Map<String, Object> createUpdateQuotation(MultipartFile[] files, String docId, String screenName, String module)
			throws ApplicationException, java.io.IOException;

	List<ImageResponseDTO> getEnquiryImages(Long id) throws Exception;

	List<Map<String, Object>> getItemDetailsWithoutQuotationId(Long orgId);
	
	
}
