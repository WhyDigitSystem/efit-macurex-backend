package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.DcForSubContractDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.dto.JobWorkOutDTO;
import com.efitops.basesetup.dto.RecieveFromSubcontractDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDTO;
import com.efitops.basesetup.dto.SubContractInvoiceDTO;
import com.efitops.basesetup.dto.SubContractQuotationDTO;
import com.efitops.basesetup.entity.DcForSubContractVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.entity.JobWorkOutVO;
import com.efitops.basesetup.entity.RecieveFromSubcontractVO;
import com.efitops.basesetup.entity.SubContractEnquiryVO;
import com.efitops.basesetup.entity.SubContractInvoiceVO;
import com.efitops.basesetup.entity.SubContractQuotationVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface IssueToSubContractorService {

	// IssueToSubContractor
	Map<String, Object> createUpdateIssueToSubContractor(IssueToSubContractorDTO issueToSubContractorDTO)
			throws ApplicationException;

	List<IssueToSubContractorVO> getAllIssueToSubContractorByOrgId(Long orgId, String finYear, String branchCode);

	List<IssueToSubContractorVO> getIssueToSubContractorById(Long id);

	String getIssueToSubContractorDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getRouteCardNoAndItemNo(Long orgId);

	List<Map<String, Object>> getDepartmentName(Long orgId);

	List<Map<String, Object>> getProcessNameFormItemWiseProcess(Long orgId, String item);

	// DcForSubContract

	List<DcForSubContractVO> getDcforSCByOrgId(Long orgId, String finYear, String branchCode);

	List<DcForSubContractVO> getDcforSCById(Long id);

	Map<String, Object> updateCreateDcForSubContract(DcForSubContractDTO dcForSubContractDTO)
			throws ApplicationException;

	String getDcForSubContractDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getIssueSCNoForDcForSubContracto(Long orgId);

	List<Map<String, Object>> getAddressForDcForSubContract(Long orgId, String customerName);

	List<Map<String, Object>> getSubContractorName(Long orgId);

	List<Map<String, Object>> getItenNameAndDescFromIssue(Long orgId, String scIssueNo);

	// SubContractEnquiry

	Map<String, Object> createUpdateSubContractEnquiry(SubContractEnquiryDTO subContractEnquiryDTO)
			throws ApplicationException;

	List<SubContractEnquiryVO> getAllSubContractEnquiryByOrgId(Long orgId, String finYear, String branchCode);

	List<SubContractEnquiryVO> getSubContractEnquiryById(Long id);

	String getSubContractEnquiryDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getSubContractCustomerNameAndCode(Long orgId);

	List<Map<String, Object>> getSubContractContactNameAndNo(Long orgId, String subContractorName);

	List<Map<String, Object>> getSubContractPartNoAndDescription(Long orgId, String scIssueNo);

	List<Map<String, Object>> getSubRouteCardNo(Long orgId);

	List<Map<String, Object>> getScIssueNoFormSubContract(Long orgId, String routeCardNo);

	// SubContractQuotation

	Map<String, Object> createUpdateSubContractQuotation(SubContractQuotationDTO subContractQuotationDTO)
			throws ApplicationException;

	List<SubContractQuotationVO> getAllSubContractQuotationByOrgId(Long orgId, String finYear, String branchCode);

	List<SubContractQuotationVO> getSubContractQuotationById(Long id);

	String getSubContractQuotationDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getEnquiryNoFromSubContractEnquiry(Long orgId);

	List<Map<String, Object>> getDocDateFromSubEnquiry(Long orgId, String docId);

	List<Map<String, Object>> getPartNoPartDescFromSubEnquiry(Long orgId, String docId);

	// SubContractInvoice

	Map<String, Object> createUpdateSubContractInvoice(SubContractInvoiceDTO subContractInvoiceDTO)
			throws ApplicationException;

	List<SubContractInvoiceVO> getAllSubContractInvoiceByOrgId(Long orgId, String finYear, String branchCode);

	List<SubContractInvoiceVO> getSubContractInvoiceById(Long id);

	String getSubContractInvoiceDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getJobWorkOutOrderNo(Long orgId);

	List<Map<String, Object>> getJobWorkOutOrderFromPartNoAndDesc(Long orgId, String docId);

	// JobWorkOut

	Map<String, Object> createUpdateJobWorkOut(JobWorkOutDTO jobWorkOutDTO) throws ApplicationException;

	List<JobWorkOutVO> getAllJobWorkOutByOrgId(Long orgId, String finYear, String branchCode);

	List<JobWorkOutVO> getAllJobWorkOutById(Long id);

	String getJobWorkOutDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getDCNumberFromDcForSubContract(Long orgId);

	List<Map<String, Object>> getPoNumberFromPurchase(Long orgId, String routeCardNo);

	List<Map<String, Object>> getQuotationNumberFromSubContract(Long orgId, String routeCardNo);

	List<Map<String, Object>> getItemAndItemDescFromDcForSubContract(Long orgId, String dcNumber, String routeCardNo);

	// RECIEVE FROM SUB-CONTRACT

	List<RecieveFromSubcontractVO> getRecieveFromSubcontractByOrgId(Long orgId, String finYear, String branchCode);

	List<RecieveFromSubcontractVO> getRecieveFromSubcontractById(Long id);

	Map<String, Object> updateCreateRecieveFromSubcontract(@Valid RecieveFromSubcontractDTO recieveFromSubcontractDTO)
			throws ApplicationException;

	String getRecieveFromSubcontractDocId(Long orgId, String finYear, String branchCode);

	// JobWorkOutOrder
	List<Map<String, Object>> getDcSubContractorDocIdForJobWorkOutOrder(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getPurchaseOrderDocIdForJobWorkOutOrder(Long orgId, String finYear, String branchCode,
			String routeCardNo);

	List<Map<String, Object>> getSubContractQuotationDocIdForJobWorkOutOrder(Long orgId, String finYear,
			String branchCode, String routeCardNo);

	List<Map<String, Object>> getIssueNoForReceiveFromSubContractor(Long orgId, String finYear, String branchCode,
			String routeCardNo);

	List<Map<String, Object>> getJobWorkOutOrderNoForReceiveFromSubContractor(Long orgId, String finYear,
			String branchCode, String routeCardNo);

	List<Map<String, Object>> getPartNameAndPartDescForReceiveFromSubContractor(Long orgId, String finYear,
			String branchCode, String routeCardNo, String issueNo);

	List<Map<String, Object>> getRateFromSubContractQuotation(Long orgId, String finYear, String branchCode,
			String subContractQuotationDocId, String routeCardNo, String part);

	String getSubContractEnquiryIdIteration(Long orgId, String clientName, String enquiryNo);

	List<Map<String, Object>> getIssueToSubContractorDetails(Long orgId, String fromdate, String todate, String status,
			String routeCardNo);

	List<Map<String, Object>> getSubContractEnquiryDetails(Long orgId, String fromdate, String todate,
			String subContractorName);

	List<Map<String, Object>> getRecieveFromSubContractDetails(Long orgId, String fromdate, String todate,
			String status, String routeCardNo);

	List<Map<String, Object>> getDeliveryChallanSubContractorReport(Long orgId, String fromDate, String toDate,
			String routeCardNo);

	List<Map<String, Object>> getSubContractorInvoiceReport(Long orgId, String fromDate, String toDate,
			String routeCardNo);

	List<Map<String, Object>> getJobWorkOutDetails(Long orgId, String contractorName, String fromDate, String toDate,
			String branchCode);

	List<Map<String, Object>> getJobWorkOutSummaryDetails(Long orgId, String contractorName, String fromDate,
			String toDate, String branchCode);

	List<Map<String, Object>> getSubContractQuotationDetailsReport(Long orgId, String branchCode,
			String subContractName, String fromDate, String toDate);

	Map<String, Object> createUpdateSubContractQuotation(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException, IOException;

	List<ImageResponseDTO> getSubContractQuotationImages(Long id) throws Exception;

}
