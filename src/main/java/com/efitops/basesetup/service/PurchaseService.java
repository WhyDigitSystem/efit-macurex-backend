package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseInvoiceDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseQuotationDTO;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseInvoiceVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;
import com.efitops.basesetup.entity.PurchaseQuotationAttachmentVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PurchaseService {

	Map<String, Object> updateCreatePurchaseIndent(@Valid PurchaseIndentDTO purchaseIndentDTO)
			throws ApplicationException;

	List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId, String finYear, String branchCode);

	Optional<PurchaseIndentVO> getPurchaseIndentById(Long id);

	List<Map<String, Object>> getCustomerNameForPurchaseIndent(Long orgId);

	List<Map<String, Object>> getIndentType(Long orgId);

	List<Map<String, Object>> getDepartmentForPurchase(Long orgId);

	List<Map<String, Object>> getRequestedByForPurchase(Long orgId);

	List<Map<String, Object>> getBomItemDetailsForPurchase(Long orgId, String fgPart);

	String getpurchaseIndentDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getpurchaseindentavlstock(Long orgId, String item);

	List<Map<String, Object>> getWorkOrderNoForPurchaseIndent(Long orgId, String customerCode);

	List<Map<String, Object>> getWorkOrderDetailsForPurchaseIndent(Long orgId, String workOrderNo);

	List<Map<String, Object>> getVerifiedByForPurchase(Long orgId);

	// PurchaseEnquiry

	Map<String, Object> updateCreatePurchaseEnquiry(@Valid PurchaseEnquiryDTO purchaseEnquiryDTO)
			throws ApplicationException;

	List<PurchaseEnquiryVO> getAllPurchaseEnquiryByOrgId(Long orgId, String finYear, String branchCode);

	Optional<PurchaseEnquiryVO> getAllPurchaseEnquiryById(Long id);

	String getPurchaseEnquiryByDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getSupplierNameForPurchaseEnquiry(Long orgId);

	List<Map<String, Object>> getContactPersonDetailsForPurchaseEnquiry(Long orgId, String supplierCode);

	List<Map<String, Object>> getPurchaseIndentNoForPurchaseEnquiry(Long orgId, String customerCode,
			String workOrderNo);

	List<Map<String, Object>> getItemDetailsForPurchaseEnquiry(Long orgId, String purchaseIndentNo, String fgItem);

	List<Map<String, Object>> getWorkOrderNoForPurchaseEnquiry(Long orgId, String customerCode);

//	List<Map<String, Object>> getWorkOrderDetailsForPurchaseEnquiry(Long orgId, String workOrderNo);

	// PurchaseQuotation

	List<PurchaseQuotationVO> getAllPurchaseQuotationByOrgId(Long orgId, String finYear, String branchCode);

	Optional<PurchaseQuotationVO> getPurchaseQuotationById(Long id);

	Map<String, Object> updateCreatePurchaseQuotation(@Valid PurchaseQuotationDTO purchaseQuotationDTO)
			throws ApplicationException;

	String getpurchaseQuotationDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getPurchaseEnquiryNoForPurchaseQuotation(Long orgId, String customerCode,
			String workOrderNo);

	List<Map<String, Object>> getItemDetailsForPurchaseQuotation(Long orgId, String purchaseEnquiryNo);

	PurchaseQuotationAttachmentVO uploadPurchaseQuatationAttachementsInBloob(MultipartFile file, Long id)
			throws IOException;

	List<Map<String, Object>> getWorkOrderNoForPurchaseQuotation(Long orgId, String customerCode);

	List<Map<String, Object>> getWorkOrderDetailsForPurchaseQuotation(Long orgId, String workOrderNo);

	List<Map<String, Object>> findByTaxCode(Long orgId, String branchCode, String supplierCode, String partyType);

	List<Map<String, Object>> findByUnitForPurchaseQuatation(Long orgId, String itemName);

//PurchaseReturn

	Map<String, Object> createUpdatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO) throws ApplicationException;

	List<PurchaseReturnVO> getAllPurchaseReturnByOrgId(Long orgId, String finYear, String branchCode);

	PurchaseReturnVO getPurchaseReturnById(Long id);

	String getPurchaseReturnDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getPurchaseInvoiceNumberFromPurchaseInvoice(Long orgId, String supplierCode);

	List<Map<String, Object>> getLocationFromStockLocation(Long orgId);

	List<Map<String, Object>> getItemCodeAndItemDescFromPurchsaeInvoice(Long orgId, String purchaseInvoiceNo);

	// PurchaseInvoice

	Map<String, Object> createUpdatePurchaseInvoice(PurchaseInvoiceDTO purchaseInvoiceDTO) throws ApplicationException;

	List<PurchaseInvoiceVO> getAllPurchaseInvoiceByOrgId(Long orgId, String finYear, String branchCode);

	PurchaseInvoiceVO getPurchaseInvoiceById(Long id);

	String getPurchaseInvoiceDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getPurchaseOrderPoNumber(Long orgId, String supplierName);

	List<Map<String, Object>> getGrnNoAndGrnDateFromGrnDetails(Long orgId, String poNo);

	List<Map<String, Object>> getItemCodeAndItemDescFromGrn(Long orgId, String grnNo);

	List<Map<String, Object>> getPoDetailsId(String docId, String item, Long orgId);

	// Purchase Order

	List<PurchaseOrderVO> getPurchaseOrderByOrgId(Long orgId, String finYear, String branchCode);

	List<PurchaseOrderVO> getPurchaseOrderById(Long id);

	Map<String, Object> updateCreatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) throws ApplicationException;

	// Map<String, Object> updateCreateThirdPartyInspection(ThirdPartyInspectionDTO
	// thirdPartyInspectionDTO);

	String getPurchaseOrderDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getSupplierAddressForPurchaseOrder(Long orgId, String supplierName);

	List<Map<String, Object>> getPurchaseIndentForPurchaseOrder(Long orgId, String customerCode, String workorderno,
			String basedOn);

	List<Map<String, Object>> getQuotationForPurchaseOrder(Long orgId, String customerCode, String workorderno,
			String basedOn);

	List<Map<String, Object>> getItemForPurchaseOrder(Long orgId, String purchaseIndentNo, String quotationNo);

	List<Map<String, Object>> findByIgstAndSgstPercentageForPurchaseQrder(Long orgId, String taxType, String taxCode);

	List<Map<String, Object>> getPurchaseIndentReport(Long orgId, String branchCode,
			String customerName,String status, String fromDate,String toDate);

	List<Map<String, Object>> getPurchaseEnquiryReport(Long orgId, String branchCode,
			String supplierName,String status,String fromDate,String toDate);

	List<Map<String, Object>> getPurchaseReturnReport(Long orgId, String branchCode, String finYear,
			String supplierName,String fromDate,String toDate);

	List<Map<String, Object>> getPurchaseOrderDetails(Long orgId, String supplierName, String status);

	String getPurchaseEnquiryIdIteration(Long orgId, String clientName, String enquiryNo);

	List<Map<String, Object>> getPurchaseQuotationDetailsReport(Long orgId, String branchCode, String supplierName,
			String fromDate, String toDate);

	List<Map<String, Object>> getPurchaseInvoiceDetails(Long orgId, String supplierName, String fromDate, String toDate,
			String branchCode);

	List<Map<String, Object>> getPurchaseInvoiceSummaryDetails(Long orgId, String supplierName, String fromDate,
			String toDate, String branchCode);

	Map<String, Object> createUpdatePurchaseImages(MultipartFile[] files, String docId, String screenName,
			String module, List<String> fileNames) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewPurchaseQuotationImages(HttpServletRequest request) throws IOException;

}
