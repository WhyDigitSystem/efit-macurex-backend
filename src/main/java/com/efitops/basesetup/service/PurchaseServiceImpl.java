package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseEnquiryDetailsDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseIndentDetailsDTO;
import com.efitops.basesetup.dto.PurchaseIndentSummaryDTO;
import com.efitops.basesetup.dto.PurchaseInvoiceDTO;
import com.efitops.basesetup.dto.PurchaseInvoiceItemDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseOrderDetailsDTO;
import com.efitops.basesetup.dto.PurchaseQuotationAttachmentDTO;
import com.efitops.basesetup.dto.PurchaseQuotationDTO;
import com.efitops.basesetup.dto.PurchaseQuotationDetailsDTO;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.dto.PurchaseReturnItemDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.PurchaseDetailsVO;
import com.efitops.basesetup.entity.PurchaseEnquiryDetailsVO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentDetailsVO;
import com.efitops.basesetup.entity.PurchaseIndentSummaryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseInvoiceItemVO;
import com.efitops.basesetup.entity.PurchaseInvoiceVO;
import com.efitops.basesetup.entity.PurchaseOrderDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;
import com.efitops.basesetup.entity.PurchaseQuotationAttachmentVO;
import com.efitops.basesetup.entity.PurchaseQuotationDetailsVO;
import com.efitops.basesetup.entity.PurchaseQuotationImagesVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;
import com.efitops.basesetup.entity.PurchaseReturnItemVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.entity.QuoteRevisionVO;
import com.efitops.basesetup.entity.StockDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DepartmentRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.EmployeeRepo;
import com.efitops.basesetup.repo.ItemRepo;
import com.efitops.basesetup.repo.PartyMasterRepo;
import com.efitops.basesetup.repo.PurchaseDetailsRepo;
import com.efitops.basesetup.repo.PurchaseEnquiryDetailsRepo;
import com.efitops.basesetup.repo.PurchaseEnquiryRepo;
import com.efitops.basesetup.repo.PurchaseIndentDetailsRepo;
import com.efitops.basesetup.repo.PurchaseIndentRepo;
import com.efitops.basesetup.repo.PurchaseIndentSummaryRepo;
import com.efitops.basesetup.repo.PurchaseInvoiceItemRepo;
import com.efitops.basesetup.repo.PurchaseInvoiceRepo;
import com.efitops.basesetup.repo.PurchaseOrderDetailsRepo;
import com.efitops.basesetup.repo.PurchaseOrderPendingRepo;
import com.efitops.basesetup.repo.PurchaseOrderRepo;
import com.efitops.basesetup.repo.PurchaseQuotationAttachmentRepo;
import com.efitops.basesetup.repo.PurchaseQuotationDetailsRepo;
import com.efitops.basesetup.repo.PurchaseQuotationImagesRepo;
import com.efitops.basesetup.repo.PurchaseQuotationRepo;
import com.efitops.basesetup.repo.PurchaseReturnItemRepo;
import com.efitops.basesetup.repo.PurchaseReturnRepo;
import com.efitops.basesetup.repo.QuoteRevisionRepo;
import com.efitops.basesetup.repo.StockDetailsRepo;

@Repository
public class PurchaseServiceImpl implements PurchaseService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImpl.class);

	@Autowired
	PurchaseIndentRepo purchaseIndentRepo;

	@Autowired
	PurchaseIndentSummaryRepo purchaseIndentSummaryRepo;

	@Autowired
	PurchaseIndentDetailsRepo purchaseIndentDetailsRepo;

	@Autowired
	PartyMasterRepo partyMasterRepo;

	@Autowired
	ItemRepo itemRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	PurchaseEnquiryRepo purchaseEnquiryRepo;

	@Autowired
	PurchaseEnquiryDetailsRepo purchaseEnquiryDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	PurchaseQuotationRepo purchaseQuotationRepo;

	@Autowired
	PurchaseQuotationDetailsRepo purchaseQuotationDetailsRepo;

	@Autowired
	PurchaseQuotationAttachmentRepo purchaseQuotationAttachmentRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	PurchaseReturnRepo purchaseReturnRepo;

	@Autowired
	PurchaseReturnItemRepo purchaseReturnItemRepo;

	@Autowired
	PurchaseInvoiceRepo purchaseInvoiceRepo;

	@Autowired
	PurchaseInvoiceItemRepo purchaseInvoiceItemRepo;

	@Autowired
	PurchaseOrderRepo purchaseOrderRepo;

	@Autowired
	PurchaseOrderDetailsRepo purchaseOrderDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	PurchaseOrderPendingRepo purchaseOrderPendingRepo;
	@Autowired
	PurchaseDetailsRepo purchaseDetailsRepo;

	@Autowired
	QuoteRevisionRepo quoteRevisionRepo;

	@Autowired
	PurchaseQuotationImagesRepo purchaseQuotationImagesRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public Map<String, Object> updateCreatePurchaseIndent(@Valid PurchaseIndentDTO purchaseIndentDTO)
			throws ApplicationException {

		PurchaseIndentVO purchaseIndentVO;
		String message = null;
		String screenCode = "PI";
		PurchaseIndentVO oldPurchaseIndent = null;

		if (ObjectUtils.isEmpty(purchaseIndentDTO.getId())) {

			purchaseIndentVO = new PurchaseIndentVO();
			purchaseIndentVO.setCreatedBy(purchaseIndentDTO.getCreatedBy());
			purchaseIndentVO.setUpdatedBy(purchaseIndentDTO.getCreatedBy());

			String docId = purchaseIndentRepo.getPurchaseIndentByDocId(purchaseIndentDTO.getOrgId(),
					purchaseIndentDTO.getFinYear(), purchaseIndentDTO.getBranchCode(), screenCode);

			purchaseIndentVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseIndentDTO.getOrgId(),
							purchaseIndentDTO.getFinYear(), purchaseIndentDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "PurchaseIndent Creation SuccessFully";

		} else {

			oldPurchaseIndent = purchaseIndentRepo.findById(purchaseIndentDTO.getId())
		            .orElseThrow(() -> new ApplicationException("purchaseIndent not found"));

			oldPurchaseIndent.getPurchaseIndentDetailsVO().size(); // load
			oldPurchaseIndent.getPurchaseIndentSummaryVO().size(); // load

		    entityManager.detach(oldPurchaseIndent); // detach snapshot
			purchaseIndentVO = purchaseIndentRepo.findById(purchaseIndentDTO.getId()).orElseThrow(
					() -> new ApplicationException("PurchaseIndent  Not Found with id: " + purchaseIndentDTO.getId()));
			purchaseIndentVO.setUpdatedBy(purchaseIndentDTO.getCreatedBy());

			message = "PurchaseIndent Updation Successfully";
		}

		purchaseIndentVO = getPurchaseIndentVOFromPurchaseIndentDTO(purchaseIndentVO, purchaseIndentDTO);
		purchaseIndentRepo.save(purchaseIndentVO);
		
		commonNotificationService.generateNotification(purchaseIndentVO.getScreenCode(), purchaseIndentVO.getId(), oldPurchaseIndent, purchaseIndentVO);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("purchaseIndentVO", purchaseIndentVO);
		return response;

	}

	private PurchaseIndentVO getPurchaseIndentVOFromPurchaseIndentDTO(PurchaseIndentVO purchaseIndentVO,
			@Valid PurchaseIndentDTO purchaseIndentDTO) {

		purchaseIndentVO.setIndentType(purchaseIndentDTO.getIndentType());
		purchaseIndentVO.setCustomerName(purchaseIndentDTO.getCustomerName());
		purchaseIndentVO.setCustomerCode(purchaseIndentDTO.getCustomerCode());
		purchaseIndentVO.setWorkOrderNo(purchaseIndentDTO.getWorkOrderNo());
		purchaseIndentVO.setDepartment(purchaseIndentDTO.getDepartment());
		purchaseIndentVO.setFgPart(purchaseIndentDTO.getFgPart());
		purchaseIndentVO.setFgPartDesc(purchaseIndentDTO.getFgPartDesc());
		purchaseIndentVO.setFgQty(purchaseIndentDTO.getFgQty());
		purchaseIndentVO.setRequestedBy(purchaseIndentDTO.getRequestedBy());
		purchaseIndentVO.setCustomerPoNo(purchaseIndentDTO.getCustomerPoNo());
		purchaseIndentVO.setOrgId(purchaseIndentDTO.getOrgId());
		purchaseIndentVO.setFinYear(purchaseIndentDTO.getFinYear());
		purchaseIndentVO.setBranchCode(purchaseIndentDTO.getBranchCode());
		purchaseIndentVO.setBranch(purchaseIndentDTO.getBranch());

		if (purchaseIndentDTO.getId() != null) {

			List<PurchaseIndentDetailsVO> purchaseIndentDetailsVOs = purchaseIndentDetailsRepo
					.findByPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentDetailsRepo.deleteAll(purchaseIndentDetailsVOs);

			List<PurchaseIndentSummaryVO> purchaseIndentSummaryVOs = purchaseIndentSummaryRepo
					.findByPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentSummaryRepo.deleteAll(purchaseIndentSummaryVOs);

		}

		List<PurchaseIndentDetailsVO> purchaseIndentDetailsVOs = new ArrayList<PurchaseIndentDetailsVO>();
		for (PurchaseIndentDetailsDTO purchaseIndentDetailsDTO : purchaseIndentDTO.getPurchaseIndentDetailsDTO()) {

			PurchaseIndentDetailsVO purchaseIndentDetailsVO = new PurchaseIndentDetailsVO();
			purchaseIndentDetailsVO.setItem(purchaseIndentDetailsDTO.getItem());
			purchaseIndentDetailsVO.setItemDescription(purchaseIndentDetailsDTO.getItemDescription());
			purchaseIndentDetailsVO.setUom(purchaseIndentDetailsDTO.getUom());
			purchaseIndentDetailsVO.setReqQty(purchaseIndentDetailsDTO.getReqQty());
			purchaseIndentDetailsVO.setAvlStock(purchaseIndentDetailsDTO.getAvlStock());
//			long indentQty = purchaseIndentDetailsDTO.getReqQty()
//		              - purchaseIndentDetailsDTO.getAvlStock();

			purchaseIndentDetailsVO.setIndentQty(purchaseIndentDetailsDTO.getIndentQty());

			purchaseIndentDetailsVO.setPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentDetailsVOs.add(purchaseIndentDetailsVO);
		}

		purchaseIndentVO.setPurchaseIndentDetailsVO(purchaseIndentDetailsVOs);

		List<PurchaseIndentSummaryVO> purchaseIndentSummaryVOs = new ArrayList<PurchaseIndentSummaryVO>();
		for (PurchaseIndentSummaryDTO purchaseIndentSummaryDTO : purchaseIndentDTO.getPurchaseIndentSummaryDTO()) {

			PurchaseIndentSummaryVO purchaseIndentSummaryVO = new PurchaseIndentSummaryVO();

			purchaseIndentSummaryVO.setVerifiedBy(purchaseIndentSummaryDTO.getVerifiedBy());
			purchaseIndentSummaryVO.setCancelRemarks(purchaseIndentSummaryDTO.getCancelRemarks());

			purchaseIndentSummaryVO.setPurchaseIndentVO(purchaseIndentVO);
			purchaseIndentSummaryVOs.add(purchaseIndentSummaryVO);
		}

		purchaseIndentVO.setPurchaseIndentSummaryVO(purchaseIndentSummaryVOs);
		return purchaseIndentVO;
	}

	@Override
	public String getpurchaseIndentDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "PI";
		String result = purchaseIndentRepo.getPurchaseIndentByDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId, String finYear, String branchCode) {
		return purchaseIndentRepo.getAllPurchaseIndentByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public Optional<PurchaseIndentVO> getPurchaseIndentById(Long id) {
		return purchaseIndentRepo.getPurchaseIndentById(id);
	}

	@Override
	public List<Map<String, Object>> getCustomerNameForPurchaseIndent(Long orgId) {
		Set<Object[]> cstname = purchaseIndentRepo.findCustomerDetails(orgId);
		return getCustomerDetails(cstname);
	}

	private List<Map<String, Object>> getCustomerDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getIndentType(Long orgId) {
		Set<Object[]> materialType = purchaseIndentRepo.findIndentType(orgId);
		return getItem(materialType);
	}

	private List<Map<String, Object>> getItem(Set<Object[]> materialType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : materialType) {
			Map<String, Object> map = new HashMap<>();
			map.put("materialType", ch[0] != null ? ch[0].toString() : ""); // Empty string if null

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDepartmentForPurchase(Long orgId) {
		Set<Object[]> departmentDetails = purchaseIndentRepo.getDepartmentDetails(orgId);
		return getDepart(departmentDetails);
	}

	private List<Map<String, Object>> getDepart(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
//			map.put("departmentId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("departmentName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getRequestedByForPurchase(Long orgId) {
		Set<Object[]> requestedByDetails = purchaseIndentRepo.getRequestedByDetails(orgId);
		return getRequested(requestedByDetails);
	}

	private List<Map<String, Object>> getRequested(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
//			map.put("employeeId", ch[0] != null ? Integer.parseInt(ch[0].toString()) : 0);
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getVerifiedByForPurchase(Long orgId) {
		Set<Object[]> verifiedByDetails = purchaseIndentRepo.getVerifiedByForPurchase(orgId);
		return getVerifiedByForPurchase(verifiedByDetails);
	}

	private List<Map<String, Object>> getVerifiedByForPurchase(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getBomItemDetailsForPurchase(Long orgId, String fgPart) {
		Set<Object[]> item = purchaseIndentRepo.findBomItemDetailsForPurchase(orgId, fgPart);
		return getItem1(item);
	}

	private List<Map<String, Object>> getItem1(Set<Object[]> it) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : it) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryUnit", ch[2] != null ? ch[2].toString() : "");
			map.put("bomQty", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getWorkOrderNoForPurchaseIndent(Long orgId, String customerCode) {
		Set<Object[]> workOrderNo = purchaseIndentRepo.findWorkOrderNoForPurchaseIndent(orgId, customerCode);
		return getWorkOrderNoForPurchaseIndent(workOrderNo);
	}

	private List<Map<String, Object>> getWorkOrderNoForPurchaseIndent(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getWorkOrderDetailsForPurchaseIndent(Long orgId, String workOrderNo) {
		Set<Object[]> workOrderDtls = purchaseIndentRepo.findWorkOrderDetailsForPurchaseIndent(orgId, workOrderNo);
		return getWorkOrderDetailsForPurchaseIndent(workOrderDtls);
	}

	private List<Map<String, Object>> getWorkOrderDetailsForPurchaseIndent(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("fgPart", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPartDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("fgQty", ch[2] != null ? ch[2].toString() : "");
			map.put("customerPoNo", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	// PurchaseEnquiry

	@Override
	public Map<String, Object> updateCreatePurchaseEnquiry(@Valid PurchaseEnquiryDTO purchaseEnquiryDTO)
			throws ApplicationException {

		PurchaseEnquiryVO purchaseEnquiryVO;
		String message = null;
		String screenCode = "PE";
		
		PurchaseEnquiryVO oldPurchaseEnquiry = null;

		if (ObjectUtils.isEmpty(purchaseEnquiryDTO.getId())) {

			purchaseEnquiryVO = new PurchaseEnquiryVO();

			String docId = purchaseEnquiryRepo.getPurchaseEnquiryByDocId(purchaseEnquiryDTO.getOrgId(),
					purchaseEnquiryDTO.getFinYear(), purchaseEnquiryDTO.getBranchCode(), screenCode);

			purchaseEnquiryVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseEnquiryDTO.getOrgId(),
							purchaseEnquiryDTO.getFinYear(), purchaseEnquiryDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseEnquiryVO.setCreatedBy(purchaseEnquiryDTO.getCreatedBy());
			purchaseEnquiryVO.setUpdatedBy(purchaseEnquiryDTO.getCreatedBy());

			message = "PurchaseEnquiry Created SuccessFully";
		} else {
			
			oldPurchaseEnquiry = purchaseEnquiryRepo.findById(purchaseEnquiryDTO.getId())
		            .orElseThrow(() -> new ApplicationException("purchaseEnquiry not found"));

			oldPurchaseEnquiry.getPurchaseEnquiryDetailsVO().size(); // load

		    entityManager.detach(oldPurchaseEnquiry); // detach snapshot

			purchaseEnquiryVO = purchaseEnquiryRepo.findById(purchaseEnquiryDTO.getId())
					.orElseThrow(() -> new ApplicationException(
							"purchaseenquiry  Not Found with id: " + purchaseEnquiryDTO.getId()));
			purchaseEnquiryVO.setUpdatedBy(purchaseEnquiryDTO.getCreatedBy());

			message = "PurchaseEnquiry Updation Successfully";

		}

		purchaseEnquiryVO = getPurchaseEnquiryVOFromPurchaseEnquiryDTO(purchaseEnquiryVO, purchaseEnquiryDTO);
		purchaseEnquiryRepo.save(purchaseEnquiryVO);
		commonNotificationService.generateNotification(purchaseEnquiryVO.getScreenCode(), purchaseEnquiryVO.getId(), oldPurchaseEnquiry, purchaseEnquiryVO);

		Map<String, Object> reponse = new HashMap<String, Object>();
		reponse.put("message", message);
		reponse.put("purchaseEnquiryVO", purchaseEnquiryVO);
		return reponse;

	}

	private PurchaseEnquiryVO getPurchaseEnquiryVOFromPurchaseEnquiryDTO(PurchaseEnquiryVO purchaseEnquiryVO,
			@Valid PurchaseEnquiryDTO purchaseEnquiryDTO) {

		purchaseEnquiryVO.setCustomerName(purchaseEnquiryDTO.getCustomerName());
		purchaseEnquiryVO.setCustomerCode(purchaseEnquiryDTO.getCustomerCode());
		purchaseEnquiryVO.setWorkOrderNo(purchaseEnquiryDTO.getWorkOrderNo());
		purchaseEnquiryVO.setPurchaseIndentNo(purchaseEnquiryDTO.getPurchaseIndentNo());
		purchaseEnquiryVO.setCustomerPoNo(purchaseEnquiryDTO.getCustomerPoNo());
		purchaseEnquiryVO.setFgPartName(purchaseEnquiryDTO.getFgPartName());
		purchaseEnquiryVO.setFgPartDesc(purchaseEnquiryDTO.getFgPartDesc());
		purchaseEnquiryVO.setSupplierName(purchaseEnquiryDTO.getSupplierName());
		purchaseEnquiryVO.setSupplierCode(purchaseEnquiryDTO.getSupplierCode());
		purchaseEnquiryVO.setContactPerson(purchaseEnquiryDTO.getContactPerson());
		purchaseEnquiryVO.setContactNo(purchaseEnquiryDTO.getContactNo());
		purchaseEnquiryVO.setEnquiryType(purchaseEnquiryDTO.getEnquiryType());
		purchaseEnquiryVO.setEnquiryDueDate(purchaseEnquiryDTO.getEnquiryDueDate());
		purchaseEnquiryVO.setExpectedDeliveryDate(purchaseEnquiryDTO.getExpectedDeliveryDate());
		purchaseEnquiryVO.setSummary(purchaseEnquiryDTO.getSummary());
		purchaseEnquiryVO.setCreatedBy(purchaseEnquiryDTO.getCreatedBy());
		purchaseEnquiryVO.setCancelRemarks(purchaseEnquiryDTO.getCancelRemarks());
		purchaseEnquiryVO.setOrgId(purchaseEnquiryDTO.getOrgId());
		purchaseEnquiryVO.setBranch(purchaseEnquiryDTO.getBranch());
		purchaseEnquiryVO.setFinYear(purchaseEnquiryDTO.getFinYear());
		purchaseEnquiryVO.setBranchCode(purchaseEnquiryDTO.getBranchCode());

		if (purchaseEnquiryDTO.getId() != null) {

			List<PurchaseEnquiryDetailsVO> detailsVOs = purchaseEnquiryDetailsRepo
					.findByPurchaseEnquiryVO(purchaseEnquiryVO);
			purchaseEnquiryDetailsRepo.deleteAll(detailsVOs);

		}

		List<PurchaseEnquiryDetailsVO> purchaseEnquiryDetailsVOs = new ArrayList<PurchaseEnquiryDetailsVO>();

		for (PurchaseEnquiryDetailsDTO purchaseEnquiryDetailsDTO : purchaseEnquiryDTO.getPurchaseEnquiryDetailsDTO()) {

			PurchaseEnquiryDetailsVO purchaseEnquiryDetailsVO = new PurchaseEnquiryDetailsVO();

			purchaseEnquiryDetailsVO.setItem(purchaseEnquiryDetailsDTO.getItem());
			purchaseEnquiryDetailsVO.setItemDesc(purchaseEnquiryDetailsDTO.getItemDesc());
			purchaseEnquiryDetailsVO.setUnit(purchaseEnquiryDetailsDTO.getUnit());
			purchaseEnquiryDetailsVO.setQtyRequired(purchaseEnquiryDetailsDTO.getQtyRequired());
			purchaseEnquiryDetailsVO.setRemarks(purchaseEnquiryDetailsDTO.getRemarks());

			purchaseEnquiryDetailsVO.setPurchaseEnquiryVO(purchaseEnquiryVO);
			purchaseEnquiryDetailsVOs.add(purchaseEnquiryDetailsVO);

		}

		purchaseEnquiryVO.setPurchaseEnquiryDetailsVO(purchaseEnquiryDetailsVOs);

		return purchaseEnquiryVO;
	}

	@Override
	public List<PurchaseEnquiryVO> getAllPurchaseEnquiryByOrgId(Long orgId, String finYear, String branchCode) {
		return purchaseEnquiryRepo.getPurchaseEnquiry(orgId, finYear, branchCode);
	}

	@Override
	public Optional<PurchaseEnquiryVO> getAllPurchaseEnquiryById(Long id) {
		return purchaseEnquiryRepo.getPurchaseEnquiryById(id);
	}

	@Override
	public String getPurchaseEnquiryByDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "PE";
		String result = purchaseEnquiryRepo.getPurchaseEnquiryByDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getSupplierNameForPurchaseEnquiry(Long orgId) {
		Set<Object[]> supplierName = purchaseEnquiryRepo.findSupplierNameForPurchaseEnquiry(orgId);
		return getSupplierNameForPurchaseEnquiry(supplierName);
	}

	private List<Map<String, Object>> getSupplierNameForPurchaseEnquiry(Set<Object[]> supplierName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : supplierName) {
			Map<String, Object> map = new HashMap<>();
			map.put("supplierName", ch[0] != null ? ch[0].toString() : "");
			map.put("supplierCode", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getContactPersonDetailsForPurchaseEnquiry(Long orgId, String supplierCode) {
		Set<Object[]> ContactPersonDetails = purchaseEnquiryRepo.findContactPersonDetailsForPurchaseEnquiry(orgId,
				supplierCode);
		return getContactPersonDetailsForPurchaseEnquiry(ContactPersonDetails);
	}

	private List<Map<String, Object>> getContactPersonDetailsForPurchaseEnquiry(Set<Object[]> ContactPersonDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : ContactPersonDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("contactPerson", ch[0] != null ? ch[0].toString() : "");
			map.put("contactNo", ch[1] != null ? ch[1].toString() : "");
//			map.put("taxType", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getPurchaseIndentNoForPurchaseEnquiry(Long orgId, String customerCode,
			String workOrderNo) {
		Set<Object[]> purchaseIndentNo = purchaseEnquiryRepo.findPurchaseIndentNoForPurchaseEnquiry(orgId, customerCode,
				workOrderNo);
		return getPurchaseIndentNoForPurchaseEnquiry(purchaseIndentNo);
	}

	private List<Map<String, Object>> getPurchaseIndentNoForPurchaseEnquiry(Set<Object[]> purchaseIndentNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : purchaseIndentNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("purchaseIndentNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getItemDetailsForPurchaseEnquiry(Long orgId, String purchaseIndentNo,
			String fgItem) {
		Set<Object[]> itemDetails = purchaseEnquiryRepo.findItemDetailsForPurchaseEnquiry(orgId, purchaseIndentNo,
				fgItem);
		return getItemDetailsForPurchaseEnquiry(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsForPurchaseEnquiry(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("uom", ch[2] != null ? ch[2].toString() : "");
			map.put("qtyRequired", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);

			map.put("indentQty", ch[4] != null ? new BigDecimal(ch[4].toString()) : BigDecimal.ZERO);

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getWorkOrderNoForPurchaseEnquiry(Long orgId, String customerCode) {
		Set<Object[]> workOrderNo = purchaseEnquiryRepo.findWorkOrderNoForPurchaseEnquiry(orgId, customerCode);
		return getWorkOrderNoForPurchaseEnquiry(workOrderNo);
	}

	private List<Map<String, Object>> getWorkOrderNoForPurchaseEnquiry(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPart", ch[1] != null ? ch[1].toString() : "");
			map.put("fgPartDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("fgQty", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	// PurchaseQuotation

	@Override
	public List<PurchaseQuotationVO> getAllPurchaseQuotationByOrgId(Long orgId, String finYear, String branchCode) {
		return purchaseQuotationRepo.getAllPurchaseQuotationByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public List<Map<String, Object>> findByTaxCode(Long orgId, String branchCode, String supplierCode,
			String partyType) {
		Set<Object[]> chType = purchaseQuotationRepo.findByTaxCode(orgId, branchCode, supplierCode, partyType);
		return findByTaxCode(chType);
	}

	private List<Map<String, Object>> findByTaxCode(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("taxCode", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> findByUnitForPurchaseQuatation(Long orgId, String itemName) {
		Set<Object[]> chType = purchaseQuotationRepo.findByUnitForPurchaseQuatation(orgId, itemName);
		return findByUnitForPurchaseQuatation(chType);
	}

	private List<Map<String, Object>> findByUnitForPurchaseQuatation(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemName", ch[0] != null ? ch[0].toString() : "");
			map.put("unitPrice", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public Optional<PurchaseQuotationVO> getPurchaseQuotationById(Long id) {
		return purchaseQuotationRepo.getAllPurchaseQuotationById(id);
	}

//	@Override
//	public Map<String, Object> updateCreatePurchaseQuotation(@Valid PurchaseQuotationDTO purchaseQuotationDTO)
//			throws ApplicationException {
//
//		PurchaseQuotationVO purchaseQuotationVO = new PurchaseQuotationVO();
//		String message = null;
//		String screenCode = "PQ";
//
//		if (ObjectUtils.isEmpty(purchaseQuotationDTO.getId())) {
//
//			purchaseQuotationVO.setCreatedBy(purchaseQuotationDTO.getCreatedBy());
//			purchaseQuotationVO.setUpdatedBy(purchaseQuotationDTO.getCreatedBy());
//
//			String docId = purchaseQuotationRepo.getPurchaseQuotationByDocId(purchaseQuotationDTO.getOrgId(),
//					purchaseQuotationDTO.getFinYear(), purchaseQuotationDTO.getBranchCode(), screenCode);
//
//			purchaseQuotationVO.setDocId(docId);
//
////        							// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseQuotationDTO.getOrgId(),
//							purchaseQuotationDTO.getFinYear(), purchaseQuotationDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			message = "PurchaseQuotation Creation SuccessFully";
//
//		} else {
//
//			purchaseQuotationVO = purchaseQuotationRepo.findById(purchaseQuotationDTO.getId())
//					.orElseThrow(() -> new ApplicationException(
//							"PurchaseQuotation  Not Found with id: " + purchaseQuotationDTO.getId()));
//			purchaseQuotationVO.setUpdatedBy(purchaseQuotationDTO.getCreatedBy());
//
//			message = "PurchaseQuotation Updation Successfully";
//
//		}
//
//		purchaseQuotationVO = getPurchaseQuotationVOFromPurchaseQuotationDTO(purchaseQuotationVO, purchaseQuotationDTO);
//		purchaseQuotationRepo.save(purchaseQuotationVO);
//
//		Map<String, Object> response = new HashMap<String, Object>();
//		response.put("message", message);
//		response.put("purchaseQuotationVO", purchaseQuotationVO);
//		return response;
//
//	}
//
//	BigDecimal basicPrice = BigDecimal.ZERO;
//	BigDecimal discountAmount = BigDecimal.ZERO;
//	BigDecimal quoteAmount = BigDecimal.ZERO;
//
//	private PurchaseQuotationVO getPurchaseQuotationVOFromPurchaseQuotationDTO(PurchaseQuotationVO purchaseQuotationVO,
//			@Valid PurchaseQuotationDTO purchaseQuotationDTO) {
//
//		if (purchaseQuotationDTO.getId() != null) {
//			List<PurchaseQuotationDetailsVO> purchaseQuotationDetailsVOs = purchaseQuotationDetailsRepo
//					.findByPurchaseQuotationVO(purchaseQuotationVO);
//			purchaseQuotationDetailsRepo.deleteAll(purchaseQuotationDetailsVOs);
//
//			List<PurchaseQuotationAttachmentVO> purchaseQuotationAttachmentVOs = purchaseQuotationAttachmentRepo
//					.findByPurchaseQuotationVO(purchaseQuotationVO);
//			purchaseQuotationAttachmentRepo.deleteAll(purchaseQuotationAttachmentVOs);
//		}
//
//		List<PurchaseQuotationDetailsVO> purchaseQuotationDetailsVOs = new ArrayList<>();
//		for (PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO : purchaseQuotationDTO
//				.getPurchaseQuotationDetailsDTO()) {
//
//			PurchaseQuotationDetailsVO purchaseQuotationDetailsVO = new PurchaseQuotationDetailsVO();
//			purchaseQuotationDetailsVO.setItem(purchaseQuotationDetailsDTO.getItem());
//			purchaseQuotationDetailsVO.setItemDesc(purchaseQuotationDetailsDTO.getItemDesc());
//			purchaseQuotationDetailsVO.setUnit(purchaseQuotationDetailsDTO.getUnit());
//			purchaseQuotationDetailsVO.setQty(purchaseQuotationDetailsDTO.getQty());
//			purchaseQuotationDetailsVO.setUnitPrice(purchaseQuotationDetailsDTO.getUnitPrice());
//
//			basicPrice = purchaseQuotationDetailsDTO.getQty().multiply(purchaseQuotationDetailsDTO.getUnitPrice());
//			purchaseQuotationDetailsVO.setBasicPrice(basicPrice);
//
//			purchaseQuotationDetailsVO.setDiscount(purchaseQuotationDetailsDTO.getDiscount());
//
//			discountAmount = basicPrice.divide(BigDecimal.valueOf(100))
//					.multiply(purchaseQuotationDetailsDTO.getDiscount());
//			purchaseQuotationDetailsVO.setDiscountAmount(discountAmount);
//
//			quoteAmount = basicPrice.subtract(discountAmount);
//			purchaseQuotationDetailsVO.setQuoteAmount(quoteAmount);
//
//			purchaseQuotationDetailsVO.setPurchaseQuotationVO(purchaseQuotationVO);
//			purchaseQuotationDetailsVOs.add(purchaseQuotationDetailsVO);
//		}
//
//		purchaseQuotationVO.setPurchaseQuotationDetailsVO(purchaseQuotationDetailsVOs);
//
//		// Header
//		purchaseQuotationVO.setCustomerName(purchaseQuotationDTO.getCustomerName());
//		purchaseQuotationVO.setCustomerCode(purchaseQuotationDTO.getCustomerCode());
//		purchaseQuotationVO.setWorkOrderNo(purchaseQuotationDTO.getWorkOrderNo());
//		purchaseQuotationVO.setEnquiryNo(purchaseQuotationDTO.getEnquiryNo());
//		purchaseQuotationVO.setEnquiryDate(purchaseQuotationDTO.getEnquiryDate());
//		purchaseQuotationVO.setSupplierName(purchaseQuotationDTO.getSupplierName());
//		purchaseQuotationVO.setSupplierId(purchaseQuotationDTO.getSupplierId());
//		purchaseQuotationVO.setValidTill(purchaseQuotationDTO.getValidTill());
//		purchaseQuotationVO.setKindAttention(purchaseQuotationDTO.getKindAttention());
//		purchaseQuotationVO.setTaxCode(purchaseQuotationDTO.getTaxCode());
//		purchaseQuotationVO.setContactPerson(purchaseQuotationDTO.getContactPerson());
//		purchaseQuotationVO.setContactNo(purchaseQuotationDTO.getContactNo());
//		purchaseQuotationVO.setQStatus(purchaseQuotationDTO.getQStatus());
//		purchaseQuotationVO.setGrossAmount(basicPrice);
//		purchaseQuotationVO.setNetAmount(quoteAmount);
//		purchaseQuotationVO.setTotalDiscount(discountAmount);
//		purchaseQuotationVO.setNarration(purchaseQuotationDTO.getNarration());
//		purchaseQuotationVO.setBranch(purchaseQuotationDTO.getBranch());
//		purchaseQuotationVO.setBranchCode(purchaseQuotationDTO.getBranchCode());
//		purchaseQuotationVO.setFinYear(purchaseQuotationDTO.getFinYear());
//
//		// Convert AmountInWords
//		purchaseQuotationVO.setAmountInWords(amountInWordsConverterService.convert(quoteAmount.longValue()));
//
//		purchaseQuotationVO.setOrgId(purchaseQuotationDTO.getOrgId());
//
//		List<PurchaseQuotationAttachmentVO> purchaseQuotationAttachmentVOs = new ArrayList<>();
//		for (PurchaseQuotationAttachmentDTO purchaseQuotationAttachmentDTO : purchaseQuotationDTO
//				.getPurchaseQuotationAttachmentDTO()) {
//
//			PurchaseQuotationAttachmentVO purchaseQuotationAttachmentVO = new PurchaseQuotationAttachmentVO();
//
//			purchaseQuotationAttachmentVO.setFileName(purchaseQuotationAttachmentDTO.getFileName());
//
//			purchaseQuotationAttachmentVO.setPurchaseQuotationVO(purchaseQuotationVO);
//			purchaseQuotationAttachmentVOs.add(purchaseQuotationAttachmentVO);
//		}
//
//		purchaseQuotationVO.setPurchaseQuotationAttachmentVO(purchaseQuotationAttachmentVOs);
//		return purchaseQuotationVO;
//	}

	@Override
	@Transactional
	public Map<String, Object> updateCreatePurchaseQuotation(@Valid PurchaseQuotationDTO purchaseQuotationDTO)
			throws ApplicationException {

		String screenCode = "PQ";
		PurchaseQuotationVO purchaseQuotationVO = new PurchaseQuotationVO();
		String iterationValue = "";
		String message;

		PurchaseQuotationVO oldPurchaseQuotation = null;
		if (ObjectUtils.isNotEmpty(purchaseQuotationDTO.getId())) {
			
			oldPurchaseQuotation = purchaseQuotationRepo.findById(purchaseQuotationDTO.getId())
		            .orElseThrow(() -> new ApplicationException("purchaseQuotation not found"));

			oldPurchaseQuotation.getPurchaseQuotationDetailsVO().size(); // load
			oldPurchaseQuotation.getPurchaseQuotationAttachmentVO().size(); // load
			oldPurchaseQuotation.getDocuments().size(); // load


		    entityManager.detach(oldPurchaseQuotation); // detach snapshot

			purchaseQuotationVO = purchaseQuotationRepo.findById(purchaseQuotationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Quotation Not Found!"));
			purchaseQuotationVO.setUpdatedBy(purchaseQuotationDTO.getCreatedBy());

			String iteration = purchaseQuotationRepo.getPurchaseEnquiryNameId(purchaseQuotationDTO.getOrgId(),
					purchaseQuotationDTO.getCustomerName(), purchaseQuotationDTO.getId());

			System.out.println("Original iteration: " + iteration);
			if (iteration == null || iteration.isEmpty()) {
			    throw new ApplicationException("Iteration value is null. Cannot process quotation update.");
			}

			Pattern pattern = Pattern.compile("(.+)-PQ(\\d+)");
			Matcher matcher = pattern.matcher(iteration);

			if (matcher.matches()) {

				String prefix = matcher.group(1);
				String pqVersion = matcher.group(2);

				int version = Integer.parseInt(pqVersion);
				version++;

				iterationValue = prefix + "-PQ" + version;

				purchaseQuotationVO.setIterations(iterationValue);

				// Update count
				int count = purchaseQuotationRepo.getCount(purchaseQuotationDTO.getOrgId(),
						purchaseQuotationDTO.getCustomerName(), purchaseQuotationDTO.getId());
				count++;
				purchaseQuotationVO.setCount(count);

				List<PurchaseQuotationDetailsDTO> quotationDetailsVO1 = purchaseQuotationDTO
						.getPurchaseQuotationDetailsDTO();
				if (quotationDetailsVO1 != null && !quotationDetailsVO1.isEmpty()) {
					for (PurchaseQuotationDetailsDTO detailsVO : quotationDetailsVO1) {
						QuoteRevisionVO stockDetailsVOFrom = new QuoteRevisionVO();
						stockDetailsVOFrom.setOrgId(purchaseQuotationVO.getOrgId());
						stockDetailsVOFrom.setDocId(purchaseQuotationVO.getDocId());
						stockDetailsVOFrom.setDocDate(purchaseQuotationVO.getDocDate());
						stockDetailsVOFrom.setSourceId(purchaseQuotationVO.getId());
						stockDetailsVOFrom.setSourceDocId(purchaseQuotationVO.getEnquiryNo());
						stockDetailsVOFrom.setSourceDocDate(purchaseQuotationVO.getEnquiryDate());
						stockDetailsVOFrom.setCustomerName(purchaseQuotationVO.getCustomerName());
						stockDetailsVOFrom.setCustomerCode(purchaseQuotationVO.getCustomerCode());
						stockDetailsVOFrom.setCreatedBy(purchaseQuotationVO.getCreatedBy());
						stockDetailsVOFrom.setBranch(purchaseQuotationVO.getBranch());
						stockDetailsVOFrom.setBranchCode(purchaseQuotationVO.getBranchCode());
//							stockDetailsVOFrom.setActive(true);
						stockDetailsVOFrom.setFinYear(purchaseQuotationVO.getFinYear());
						stockDetailsVOFrom.setContactNo(purchaseQuotationVO.getContactNo());
						stockDetailsVOFrom.setStatus(purchaseQuotationVO.getQStatus());
						stockDetailsVOFrom.setContactName(purchaseQuotationVO.getContactPerson());
						stockDetailsVOFrom.setKindAttention(purchaseQuotationVO.getKindAttention());
						stockDetailsVOFrom.setUpdatedBy(purchaseQuotationVO.getUpdatedBy());
						stockDetailsVOFrom.setIterations(purchaseQuotationVO.getIterations());
						stockDetailsVOFrom.setCount(purchaseQuotationVO.getCount());
						stockDetailsVOFrom.setSourceScreenCode(purchaseQuotationVO.getScreenCode());
						stockDetailsVOFrom.setSourceScreenName(purchaseQuotationVO.getScreenName());
						stockDetailsVOFrom.setSupplierCode(purchaseQuotationVO.getSupplierId());
						stockDetailsVOFrom.setScreenName(screenCode);
						stockDetailsVOFrom.setGrossAmount(purchaseQuotationVO.getGrossAmount());
						stockDetailsVOFrom.setDiscount(detailsVO.getDiscount());
						stockDetailsVOFrom.setNetAmount(purchaseQuotationVO.getNetAmount());

						stockDetailsVOFrom.setPartNo(detailsVO.getItem());
						stockDetailsVOFrom.setPartDesc(detailsVO.getItemDesc());
						stockDetailsVOFrom.setSellingPrice(detailsVO.getUnitPrice());
						stockDetailsVOFrom.setQty(detailsVO.getQty());
						stockDetailsVOFrom.setPrice(detailsVO.getUnitPrice().multiply(detailsVO.getQty()));

						BigDecimal discountAmount = detailsVO.getDiscount()
								.multiply(detailsVO.getUnitPrice().multiply(detailsVO.getQty()))
								.divide(BigDecimal.valueOf(100));
						stockDetailsVOFrom.setDiscountAmount(discountAmount);
						stockDetailsVOFrom.setAmount(
								(detailsVO.getUnitPrice().multiply(detailsVO.getQty()).subtract(discountAmount)));

						quoteRevisionRepo.save(stockDetailsVOFrom);
					}

				}
			} else {
				throw new IllegalArgumentException("Invalid iteration format: " + iteration);
			}
			getPurchaseQuotationVOFromPurchaseQuotationDTO(purchaseQuotationDTO, purchaseQuotationVO);
			message = "Quotation Updated Successfully";
		} else {

			// GETDOCID API

			String docId = purchaseQuotationRepo.getPurchaseQuotationByDocId(purchaseQuotationDTO.getOrgId(),
					purchaseQuotationDTO.getFinYear(), purchaseQuotationDTO.getBranchCode(), screenCode);
			purchaseQuotationVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseQuotationDTO.getOrgId(),
							purchaseQuotationDTO.getFinYear(), purchaseQuotationDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			String iteration = purchaseQuotationRepo.getPurchaseEnquiryIdIteration(purchaseQuotationDTO.getOrgId(),
					purchaseQuotationDTO.getCustomerName(), purchaseQuotationDTO.getEnquiryNo());
			System.out.println(iteration);
//               int number = 1;
			purchaseQuotationVO.setIterations(iteration);
			purchaseQuotationVO.setCount(1);
			purchaseQuotationVO.setUpdatedBy(purchaseQuotationDTO.getCreatedBy());
			purchaseQuotationVO.setCreatedBy(purchaseQuotationDTO.getCreatedBy());
			message = "PurchaseQuotation Created Successfully";

			getPurchaseQuotationVOFromPurchaseQuotationDTO(purchaseQuotationDTO, purchaseQuotationVO);
			PurchaseQuotationVO savedpurchaseQuotationVO = purchaseQuotationRepo.save(purchaseQuotationVO);
			List<PurchaseQuotationDetailsVO> quotationDetailsVO1 = savedpurchaseQuotationVO
					.getPurchaseQuotationDetailsVO();
			if (quotationDetailsVO1 != null && !quotationDetailsVO1.isEmpty()) {
				for (PurchaseQuotationDetailsVO detailsVO : quotationDetailsVO1) {
					QuoteRevisionVO stockDetailsVOFrom = new QuoteRevisionVO();
					stockDetailsVOFrom.setOrgId(purchaseQuotationVO.getOrgId());
					stockDetailsVOFrom.setDocId(purchaseQuotationVO.getDocId());
					stockDetailsVOFrom.setDocDate(purchaseQuotationVO.getDocDate());
					stockDetailsVOFrom.setSourceId(purchaseQuotationVO.getId());
					stockDetailsVOFrom.setKindAttention(purchaseQuotationVO.getKindAttention());
					stockDetailsVOFrom.setCustomerName(purchaseQuotationVO.getCustomerName());
					stockDetailsVOFrom.setCreatedBy(purchaseQuotationVO.getCreatedBy());
					stockDetailsVOFrom.setFinYear(purchaseQuotationVO.getFinYear());
					stockDetailsVOFrom.setContactNo(purchaseQuotationVO.getContactNo());
					stockDetailsVOFrom.setStatus(purchaseQuotationVO.getQStatus());
					stockDetailsVOFrom.setBranch(purchaseQuotationVO.getBranch());
					stockDetailsVOFrom.setBranchCode(purchaseQuotationVO.getBranchCode());
					stockDetailsVOFrom.setContactNo(purchaseQuotationVO.getContactNo());
					stockDetailsVOFrom.setCustomerCode(purchaseQuotationVO.getCustomerCode());
					stockDetailsVOFrom.setSourceDocId(purchaseQuotationVO.getEnquiryNo());
					stockDetailsVOFrom.setCount(purchaseQuotationVO.getCount());
					stockDetailsVOFrom.setSourceDocDate(purchaseQuotationVO.getEnquiryDate());
					stockDetailsVOFrom.setIterations(purchaseQuotationVO.getIterations());
					stockDetailsVOFrom.setUpdatedBy(purchaseQuotationVO.getUpdatedBy());
					stockDetailsVOFrom.setSourceScreenCode(purchaseQuotationVO.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(purchaseQuotationVO.getScreenName());
					stockDetailsVOFrom.setSourceScreenName(purchaseQuotationVO.getScreenName());
					stockDetailsVOFrom.setSupplierCode(purchaseQuotationVO.getSupplierId());
					stockDetailsVOFrom.setGrossAmount(purchaseQuotationVO.getGrossAmount());
					stockDetailsVOFrom.setDiscount(detailsVO.getDiscount());
					stockDetailsVOFrom.setNetAmount(purchaseQuotationVO.getNetAmount());
					stockDetailsVOFrom.setPartNo(detailsVO.getItem());
					stockDetailsVOFrom.setPartDesc(detailsVO.getItemDesc());
					stockDetailsVOFrom.setSellingPrice(detailsVO.getUnitPrice());
					stockDetailsVOFrom.setQty(detailsVO.getQty());

					stockDetailsVOFrom.setPrice(detailsVO.getBasicPrice());

					stockDetailsVOFrom.setAmount(detailsVO.getQuoteAmount());
					stockDetailsVOFrom.setDiscountAmount(detailsVO.getDiscountAmount());

					quoteRevisionRepo.save(stockDetailsVOFrom);
				}
			}
		}

		commonNotificationService.generateNotification(purchaseQuotationVO.getScreenCode(), purchaseQuotationVO.getId(), oldPurchaseQuotation, purchaseQuotationVO);

		Map<String, Object> response = new HashMap<>();
		response.put("purchaseQuotationVO", purchaseQuotationVO);
		response.put("message", message);
		return response;
	}

	private void getPurchaseQuotationVOFromPurchaseQuotationDTO(@Valid PurchaseQuotationDTO purchaseQuotationDTO,
			PurchaseQuotationVO purchaseQuotationVO) {

		if (purchaseQuotationDTO.getId() != null) {

			List<PurchaseQuotationDetailsVO> purchaseQuotationDetailsVOs = purchaseQuotationDetailsRepo
					.findByPurchaseQuotationVO(purchaseQuotationVO);

			purchaseQuotationDetailsRepo.deleteAll(purchaseQuotationDetailsVOs);

			List<PurchaseQuotationAttachmentVO> purchaseQuotationAttachmentVOs = purchaseQuotationAttachmentRepo
					.findByPurchaseQuotationVO(purchaseQuotationVO);

			purchaseQuotationAttachmentRepo.deleteAll(purchaseQuotationAttachmentVOs);
		}

		BigDecimal totalBasicPrice = BigDecimal.ZERO;
		BigDecimal totalDiscountAmount = BigDecimal.ZERO;
		BigDecimal totalQuoteAmount = BigDecimal.ZERO;

		List<PurchaseQuotationDetailsVO> purchaseQuotationDetailsVOs = new ArrayList<>();

		for (PurchaseQuotationDetailsDTO purchaseQuotationDetailsDTO : purchaseQuotationDTO
				.getPurchaseQuotationDetailsDTO()) {

			PurchaseQuotationDetailsVO purchaseQuotationDetailsVO = new PurchaseQuotationDetailsVO();

			purchaseQuotationDetailsVO.setItem(purchaseQuotationDetailsDTO.getItem());
			purchaseQuotationDetailsVO.setItemDesc(purchaseQuotationDetailsDTO.getItemDesc());
			purchaseQuotationDetailsVO.setUnit(purchaseQuotationDetailsDTO.getUnit());
			purchaseQuotationDetailsVO.setQty(purchaseQuotationDetailsDTO.getQty());
			purchaseQuotationDetailsVO.setUnitPrice(purchaseQuotationDetailsDTO.getUnitPrice());

			BigDecimal basicPrice = purchaseQuotationDetailsDTO.getQty()
					.multiply(purchaseQuotationDetailsDTO.getUnitPrice());

			purchaseQuotationDetailsVO.setBasicPrice(basicPrice);
			purchaseQuotationDetailsVO.setDiscount(purchaseQuotationDetailsDTO.getDiscount());

			BigDecimal discountAmount = basicPrice.multiply(purchaseQuotationDetailsDTO.getDiscount())
					.divide(BigDecimal.valueOf(100));

			purchaseQuotationDetailsVO.setDiscountAmount(discountAmount);

			BigDecimal quoteAmount = basicPrice.subtract(discountAmount);

			purchaseQuotationDetailsVO.setQuoteAmount(quoteAmount);
			purchaseQuotationDetailsVO.setPurchaseQuotationVO(purchaseQuotationVO);
			purchaseQuotationDetailsVOs.add(purchaseQuotationDetailsVO);

			totalBasicPrice = totalBasicPrice.add(basicPrice);
			totalDiscountAmount = totalDiscountAmount.add(discountAmount);
			totalQuoteAmount = totalQuoteAmount.add(quoteAmount);
		}

		purchaseQuotationVO.setPurchaseQuotationDetailsVO(purchaseQuotationDetailsVOs);

		// Header
		purchaseQuotationVO.setCustomerName(purchaseQuotationDTO.getCustomerName());
		purchaseQuotationVO.setCustomerCode(purchaseQuotationDTO.getCustomerCode());
		purchaseQuotationVO.setWorkOrderNo(purchaseQuotationDTO.getWorkOrderNo());
		purchaseQuotationVO.setEnquiryNo(purchaseQuotationDTO.getEnquiryNo());
		purchaseQuotationVO.setEnquiryDate(purchaseQuotationDTO.getEnquiryDate());
		purchaseQuotationVO.setSupplierName(purchaseQuotationDTO.getSupplierName());
		purchaseQuotationVO.setSupplierId(purchaseQuotationDTO.getSupplierId());
		purchaseQuotationVO.setValidTill(purchaseQuotationDTO.getValidTill());
		purchaseQuotationVO.setKindAttention(purchaseQuotationDTO.getKindAttention());
		purchaseQuotationVO.setTaxCode(purchaseQuotationDTO.getTaxCode());
		purchaseQuotationVO.setContactPerson(purchaseQuotationDTO.getContactPerson());
		purchaseQuotationVO.setContactNo(purchaseQuotationDTO.getContactNo());
		purchaseQuotationVO.setQStatus(purchaseQuotationDTO.getQStatus());
		purchaseQuotationVO.setGrossAmount(totalBasicPrice);
		purchaseQuotationVO.setTotalDiscount(totalDiscountAmount);
		purchaseQuotationVO.setNetAmount(totalQuoteAmount);
		purchaseQuotationVO.setNarration(purchaseQuotationDTO.getNarration());
		purchaseQuotationVO.setBranch(purchaseQuotationDTO.getBranch());
		purchaseQuotationVO.setBranchCode(purchaseQuotationDTO.getBranchCode());
		purchaseQuotationVO.setFinYear(purchaseQuotationDTO.getFinYear());
		purchaseQuotationVO.setAmountInWords(amountInWordsConverterService.convert(totalQuoteAmount.longValue()));
		purchaseQuotationVO.setOrgId(purchaseQuotationDTO.getOrgId());

		List<PurchaseQuotationAttachmentVO> purchaseQuotationAttachmentVOs = new ArrayList<>();

		for (PurchaseQuotationAttachmentDTO purchaseQuotationAttachmentDTO : purchaseQuotationDTO
				.getPurchaseQuotationAttachmentDTO()) {

			PurchaseQuotationAttachmentVO purchaseQuotationAttachmentVO = new PurchaseQuotationAttachmentVO();

			purchaseQuotationAttachmentVO.setFileName(purchaseQuotationAttachmentDTO.getFileName());
			purchaseQuotationAttachmentVO.setPurchaseQuotationVO(purchaseQuotationVO);
			purchaseQuotationAttachmentVOs.add(purchaseQuotationAttachmentVO);
		}

		purchaseQuotationVO.setPurchaseQuotationAttachmentVO(purchaseQuotationAttachmentVOs);

	}

	@Override
	public String getpurchaseQuotationDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "PQ";
		String result = purchaseQuotationRepo.getPurchaseQuotationByDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public String getPurchaseEnquiryIdIteration(Long orgId, String clientName, String enquiryNo) {
		return purchaseQuotationRepo.getPurchaseEnquiryIdIteration(orgId, clientName, enquiryNo);

	}

	@Override
	public List<Map<String, Object>> getPurchaseEnquiryNoForPurchaseQuotation(Long orgId, String customerCode,
			String workOrderNo) {
		Set<Object[]> purchaseEnquiryNo = purchaseQuotationRepo.findPurchaseEnquiryNoForPurchaseQuotation(orgId,
				customerCode, workOrderNo);
		return getPurchaseEnquiryNoForPurchaseQuotation(purchaseEnquiryNo);
	}

	private List<Map<String, Object>> getPurchaseEnquiryNoForPurchaseQuotation(Set<Object[]> purchaseEnquiryNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : purchaseEnquiryNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("purchaseEnquiryNo", ch[0] != null ? ch[0].toString() : "");
			map.put("purchaseDate", ch[1] != null ? ch[1].toString() : "");
			map.put("SupplierName", ch[2] != null ? ch[2].toString() : "");
			map.put("SupplierCode", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getItemDetailsForPurchaseQuotation(Long orgId, String purchaseEnquiryNo) {
		Set<Object[]> itemDetails = purchaseQuotationRepo.findItemDetailsForPurchaseQuotation(orgId, purchaseEnquiryNo);
		return getItemDetailsForPurchaseQuotation(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsForPurchaseQuotation(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("uom", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public PurchaseQuotationAttachmentVO uploadPurchaseQuatationAttachementsInBloob(MultipartFile file, Long id)
			throws IOException {

		PurchaseQuotationAttachmentVO purchaseQuotationAttachmentVO = purchaseQuotationAttachmentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("PurchaseQuotationAttachment not found for id: " + id));

		purchaseQuotationAttachmentVO.setAttachements(file.getBytes());
		return purchaseQuotationAttachmentRepo.save(purchaseQuotationAttachmentVO);
	}

//	@Override
//	public PurchaseQuotationAttachmentVO uploadPurchaseQuatationAttachementsInBloob(
//	        MultipartFile file, Long purchaseQuotationId) throws IOException {
//
//	    PurchaseQuotationAttachmentVO vo =
//	        purchaseQuotationAttachmentRepo
//	            .findByPurchaseQuotationVO_Id(purchaseQuotationId)
//	            .orElseGet(() -> {
//	                PurchaseQuotationAttachmentVO newVO =
//	                        new PurchaseQuotationAttachmentVO();
//
//	                PurchaseQuotationVO quotation =
//	                    purchaseQuotationRepo.findById(purchaseQuotationId)
//	                    .orElseThrow(() -> new RuntimeException(
//	                        "PurchaseQuotation not found: " + purchaseQuotationId));
//
//	                newVO.setPurchaseQuotationVO(quotation);
//	                return newVO;
//	            });
//
//	    vo.setAttachements(file.getBytes());
//	    return purchaseQuotationAttachmentRepo.save(vo);
//	}
//

	@Override
	public List<Map<String, Object>> getWorkOrderNoForPurchaseQuotation(Long orgId, String customerCode) {
		Set<Object[]> workOrderNo = purchaseQuotationRepo.findWorkOrderNoForPurchaseQuotation(orgId, customerCode);
		return getWorkOrderNoForPurchaseQuotation(workOrderNo);
	}

	private List<Map<String, Object>> getWorkOrderNoForPurchaseQuotation(Set<Object[]> workOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("workOrderNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getWorkOrderDetailsForPurchaseQuotation(Long orgId, String customerCode) {
		Set<Object[]> workOrderDtls = purchaseQuotationRepo.findWorkOrderDetailsForPurchaseQuotation(orgId,
				customerCode);
		return getWorkOrderDetailsForPurchaseQuotation(workOrderDtls);
	}

	private List<Map<String, Object>> getWorkOrderDetailsForPurchaseQuotation(Set<Object[]> workOrderDtls) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderDtls) {
			Map<String, Object> map = new HashMap<>();
			map.put("fgPart", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPartDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("fgQty", ch[2] != null ? ch[2].toString() : "");
			map.put("customerPoNo", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	// purchaseReturn

//	@Override
//	public Map<String, Object> createUpdatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO)
//			throws ApplicationException {
//		PurchaseReturnVO purchaseReturnVO = new PurchaseReturnVO();
//		String message;
//		String screenCode = "PCR";
//		if (ObjectUtils.isNotEmpty(purchaseReturnDTO.getId())) {
//			purchaseReturnVO = purchaseReturnRepo.findById(purchaseReturnDTO.getId())
//					.orElseThrow(() -> new ApplicationException("PurchaseReturn Enquiry details"));
//			purchaseReturnVO.setUpdatedBy(purchaseReturnDTO.getCreatedBy());
//			createUpdatedPurchaseReturnVOFromPurchaseReturnDTO(purchaseReturnDTO, purchaseReturnVO);
//			message = "PurchaseReturn Updated Successfully";
//
//		} else {
//
//			String docId = purchaseReturnRepo.getPurchaseReturnDocId(purchaseReturnDTO.getOrgId(),
//					purchaseReturnDTO.getFinYear(), purchaseReturnDTO.getBranchCode(), screenCode);
//			purchaseReturnVO.setDocId(docId);
//
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseReturnDTO.getOrgId(),
//							purchaseReturnDTO.getFinYear(), purchaseReturnDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			purchaseReturnVO.setCreatedBy(purchaseReturnDTO.getCreatedBy());
//			purchaseReturnVO.setUpdatedBy(purchaseReturnDTO.getCreatedBy());
//			createUpdatedPurchaseReturnVOFromPurchaseReturnDTO(purchaseReturnDTO, purchaseReturnVO);
//			message = "PurchaseReturn Created Successfully";
//		}
//
//		PurchaseReturnVO savedPurchase = purchaseReturnRepo.save(purchaseReturnVO);
//
//		List<PurchaseReturnItemVO> purchaseReturnItemDetails = savedPurchase.getPurchaseReturnItemVO();
//		if (purchaseReturnItemDetails != null && !purchaseReturnItemDetails.isEmpty()) {
//			if ("CONFIRM".equalsIgnoreCase(savedPurchase.getStatus())) {
//				for (PurchaseReturnItemVO purchaseDetailsVO : purchaseReturnItemDetails) {
//					StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
//					stockDetailsVOFrom.setOrgId(savedPurchase.getOrgId());
//					stockDetailsVOFrom.setDocId(savedPurchase.getDocId());
//					stockDetailsVOFrom.setDocDate(savedPurchase.getDocDate());
//					stockDetailsVOFrom.setRefNo(savedPurchase.getId());
//					stockDetailsVOFrom.setLocation(savedPurchase.getToLocation());
//					stockDetailsVOFrom.setRefDate(savedPurchase.getDocDate());
//					stockDetailsVOFrom.setQty(purchaseDetailsVO.getRejectQty());
//					stockDetailsVOFrom.setUpdatedBy(savedPurchase.getUpdatedBy());
//					stockDetailsVOFrom.setPartno(purchaseDetailsVO.getItemCode());
//					stockDetailsVOFrom.setPartDesc(purchaseDetailsVO.getItemName());
//					stockDetailsVOFrom.setSupplierName(savedPurchase.getSupplierName());
//					stockDetailsVOFrom.setSupplierCode(savedPurchase.getSupplierCode());
//					stockDetailsVOFrom.setSourceId(savedPurchase.getId());
//					stockDetailsVOFrom.setStatus(savedPurchase.getStatus());
//					stockDetailsVOFrom.setSourceScreenCode(savedPurchase.getScreenCode());
//					stockDetailsVOFrom.setSourceScreenName(savedPurchase.getScreenName());
//					stockDetailsVOFrom.setPlusOrMinus("m");
//					stockDetailsVOFrom.setRate(purchaseDetailsVO.getPoRate());
//					stockDetailsVOFrom.setAmount(savedPurchase.getTotalAmount());
//					stockDetailsVOFrom.setCustomer(savedPurchase.getCustomerName());
//					stockDetailsRepo.save(stockDetailsVOFrom);
//				}
//			}
//		}
//
//		List<PurchaseReturnItemVO> purchaseReturnItemVOs = savedPurchase.getPurchaseReturnItemVO();
//
//		Long sourceId = savedPurchase.getId();
//		if (sourceId != null) { // Null check for safety
//		    List<PurchaseOrderPendingVO> purchaseOrderPendingList = purchaseOrderPendingRepo.findBySourceId(sourceId);
//		    
//		    if (!purchaseOrderPendingList.isEmpty()) { // Check if records exist
//		        purchaseOrderPendingRepo.deleteAll(purchaseOrderPendingList);
//		    }
//		}
//		
//		if (purchaseReturnItemVOs != null && !purchaseReturnItemVOs.isEmpty()) {
//
//			for (PurchaseReturnItemVO purchaseReturnItemVO : purchaseReturnItemVOs) {
//
//				PurchaseOrderPendingVO purchaseOrderPendingVO = new PurchaseOrderPendingVO();
//				purchaseOrderPendingVO.setPoNo(savedPurchase.getPoNo());
//				purchaseOrderPendingVO.setCustomerName(savedPurchase.getCustomerName());
//				purchaseOrderPendingVO.setCustomerCode(savedPurchase.getCustomerCode());
//				purchaseOrderPendingVO.setSupplierName(savedPurchase.getSupplierName());
//				purchaseOrderPendingVO.setSupplierCode(savedPurchase.getSupplierCode());
//				purchaseOrderPendingVO.setItem(purchaseReturnItemVO.getItemCode());
//				purchaseOrderPendingVO.setItemDesc(purchaseReturnItemVO.getItemName());
//				purchaseOrderPendingVO.setQty(purchaseReturnItemVO.getRejectQty());
//				purchaseOrderPendingVO.setTaxType(purchaseReturnItemVO.getTaxCode());
////			purchaseOrderPendingVO.setuom(purchaseOrderDetailsVO.getUom());
//				purchaseOrderPendingVO.setPrice(purchaseReturnItemVO.getUnitPrice());
//				purchaseOrderPendingVO.setAmount(purchaseReturnItemVO.getAmount());
//				purchaseOrderPendingVO.setTaxValue(purchaseReturnItemVO.getTaxValue());
//				purchaseOrderPendingVO.setLandedValue(purchaseReturnItemVO.getLandedValue());
//				purchaseOrderPendingVO.setPlusOrMinus("P");
//				purchaseOrderPendingVO.setSourceId(savedPurchase.getId());
//				purchaseOrderPendingVO.setPoPendingCancel(savedPurchase.isPoPendingCancel());
//
//	
//				purchaseOrderPendingVO.setFinYear(savedPurchase.getFinYear());
//				purchaseOrderPendingVO.setCreatedBy(savedPurchase.getCreatedBy());
//				purchaseOrderPendingVO.setUpdatedBy(savedPurchase.getUpdatedBy());
//				purchaseOrderPendingVO.setBranch(savedPurchase.getBranch());
//				purchaseOrderPendingVO.setBranchCode(savedPurchase.getBranchCode());
//				purchaseOrderPendingVO.setOrgId(savedPurchase.getOrgId());
//				purchaseOrderPendingRepo.save(purchaseOrderPendingVO);
//			}
//		}
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("purchaseReturnVO", purchaseReturnVO);
//		response.put("message", message);
//		return response;
//	}
//
//	private void createUpdatedPurchaseReturnVOFromPurchaseReturnDTO(PurchaseReturnDTO purchaseReturnDTO,
//			PurchaseReturnVO purchaseReturnVO) {
//		purchaseReturnVO.setSupplierName(purchaseReturnDTO.getSupplierName());
//		purchaseReturnVO.setSupplierCode(purchaseReturnDTO.getSupplierCode());
//		purchaseReturnVO.setCustomerName(purchaseReturnDTO.getCustomerName());
//		purchaseReturnVO.setCustomerCode(purchaseReturnDTO.getCustomerCode());
//		purchaseReturnVO.setPurchaseInvoiceNo(purchaseReturnDTO.getPurchaseInvoiceNo());
//		purchaseReturnVO.setPurchaseInvoiceDate(purchaseReturnDTO.getPurchaseInvoiceDate());
//		purchaseReturnVO.setPoNo(purchaseReturnDTO.getPoNo());
//		purchaseReturnVO.setGstNo(purchaseReturnDTO.getGstNo());
//		purchaseReturnVO.setGstState(purchaseReturnDTO.getGstState());
//		purchaseReturnVO.setAddress(purchaseReturnDTO.getAddress());
//		purchaseReturnVO.setGatePassNo(purchaseReturnDTO.getGatePassNo());
//		purchaseReturnVO.setIsReverseChrg(purchaseReturnDTO.getIsReverseChrg());
//		purchaseReturnVO.setCurrency(purchaseReturnDTO.getCurrency());
//		purchaseReturnVO.setExchangeRate(purchaseReturnDTO.getExchangeRate());
//		purchaseReturnVO.setInvDcNo(purchaseReturnDTO.getInvDcNo());
//		purchaseReturnVO.setInvDcDate(purchaseReturnDTO.getInvDcDate());
//		purchaseReturnVO.setGstType(purchaseReturnDTO.getGstType());
//		purchaseReturnVO.setToLocation(purchaseReturnDTO.getToLocation());
//		purchaseReturnVO.setRemarks(purchaseReturnDTO.getRemarks());
//		purchaseReturnVO.setStatus(purchaseReturnDTO.getStatus());
//		purchaseReturnVO.setPoPendingCancel(purchaseReturnDTO.isPoPendingCancel());
//		purchaseReturnVO.setOrgId(purchaseReturnDTO.getOrgId());
//		purchaseReturnVO.setActive(purchaseReturnDTO.isActive());
//		purchaseReturnVO.setBranch(purchaseReturnDTO.getBranch());
//		purchaseReturnVO.setBranchCode(purchaseReturnDTO.getBranchCode());
//		purchaseReturnVO.setFinYear(purchaseReturnDTO.getFinYear());
//		purchaseReturnVO.setPoPendingCancel(purchaseReturnDTO.isPoPendingCancel());
//
//
//		BigDecimal totalAmount = BigDecimal.ZERO;
//		BigDecimal netAmount = BigDecimal.ZERO;
//		BigDecimal totalTaxAmount = BigDecimal.ZERO;
//
//		if (ObjectUtils.isNotEmpty(purchaseReturnDTO.getId())) {
//			List<PurchaseReturnItemVO> purchaseReturnItemVO1 = purchaseReturnItemRepo
//					.findByPurchaseReturnVO(purchaseReturnVO);
//			purchaseReturnItemRepo.deleteAll(purchaseReturnItemVO1);
//
//		}
//
//		List<PurchaseReturnItemVO> purchaseReturnItemVOs = new ArrayList<>();
//		for (PurchaseReturnItemDTO purchaseReturnItemDTO : purchaseReturnDTO.getPurchaseReturnItemDTO()) {
//			PurchaseReturnItemVO purchaseReturnItemVO = new PurchaseReturnItemVO();
//			purchaseReturnItemVO.setItemCode(purchaseReturnItemDTO.getItemCode());
//			purchaseReturnItemVO.setItemName(purchaseReturnItemDTO.getItemName());
//			purchaseReturnItemVO.setHsnSacCode(purchaseReturnItemDTO.getHsnSacCode());
//			purchaseReturnItemVO.setTaxCode(purchaseReturnItemDTO.getTaxCode());
//			purchaseReturnItemVO.setPrimaryUnit(purchaseReturnItemDTO.getPrimaryUnit());
//			purchaseReturnItemVO.setPoRate(purchaseReturnItemDTO.getPoRate());
//			purchaseReturnItemVO.setRejectQty(purchaseReturnItemDTO.getRejectQty());
//			purchaseReturnItemVO.setUnitPrice(purchaseReturnItemDTO.getUnitPrice());
//			purchaseReturnItemVO.setOrderQty(purchaseReturnItemDTO.getOrderQty());
//			purchaseReturnItemVO.setChallanQty(purchaseReturnItemDTO.getChallanQty());
//			purchaseReturnItemVO.setSgst(purchaseReturnItemDTO.getSgst());
//			purchaseReturnItemVO.setCgst(purchaseReturnItemDTO.getCgst());
//			purchaseReturnItemVO.setIgst(purchaseReturnItemDTO.getIgst());
//
//			BigDecimal taxAmount = BigDecimal.ZERO;
//			BigDecimal landedValues = BigDecimal.ZERO;
//
//			BigDecimal amountSet = purchaseReturnItemDTO.getUnitPrice().multiply(purchaseReturnItemDTO.getRejectQty());
//			purchaseReturnItemVO.setAmount(amountSet);
//
//			if (purchaseReturnVO.getGstType() == null || purchaseReturnVO.getGstType().isEmpty()
//					|| !purchaseReturnVO.getGstType().equalsIgnoreCase("INTRA")
//							&& !purchaseReturnVO.getGstType().equalsIgnoreCase("INTER")) {
//				purchaseReturnItemVO.setIgst(BigDecimal.ZERO);
//				purchaseReturnItemVO.setCgst(BigDecimal.ZERO);
//				purchaseReturnItemVO.setSgst(BigDecimal.ZERO);
//				purchaseReturnItemVO.setTaxValue(BigDecimal.ZERO);
//			} else {
//				if (purchaseReturnVO.getGstType().equalsIgnoreCase("INTER")) {
//					purchaseReturnItemVO.setIgst(purchaseReturnItemDTO.getIgst());
//					BigDecimal igstAmount = purchaseReturnItemDTO.getIgst().multiply(purchaseReturnItemVO.getAmount())
//							.divide(BigDecimal.valueOf(100));
//					purchaseReturnItemVO.setCgst(BigDecimal.ZERO);
//					purchaseReturnItemVO.setSgst(BigDecimal.ZERO);
//					taxAmount = igstAmount;
//					purchaseReturnItemVO.setTaxValue(taxAmount);
//				} else if (purchaseReturnVO.getGstType().equalsIgnoreCase("INTRA")) {
//					purchaseReturnItemVO.setCgst(purchaseReturnItemDTO.getCgst());
//					purchaseReturnItemVO.setSgst(purchaseReturnItemDTO.getSgst());
//					BigDecimal sgstAmount = purchaseReturnItemDTO.getSgst().multiply(purchaseReturnItemVO.getAmount())
//							.divide(BigDecimal.valueOf(100));
//					BigDecimal cgstAmount = purchaseReturnItemDTO.getCgst().multiply(purchaseReturnItemVO.getAmount())
//							.divide(BigDecimal.valueOf(100));
//					purchaseReturnItemVO.setIgst(BigDecimal.ZERO);
//					taxAmount = cgstAmount.add(sgstAmount);
//					purchaseReturnItemVO.setTaxValue(taxAmount);
//				}
//			}
//			totalTaxAmount = totalTaxAmount.add(purchaseReturnItemVO.getTaxValue());
//
//			landedValues = purchaseReturnItemVO.getAmount().add(purchaseReturnItemVO.getTaxValue());
//			purchaseReturnItemVO.setLandedValue(landedValues);
//			netAmount = netAmount.add(purchaseReturnItemVO.getLandedValue());
//			totalAmount = purchaseReturnItemVO.getTaxValue().add(purchaseReturnItemVO.getLandedValue());
//
//			purchaseReturnItemVO.setPurchaseReturnVO(purchaseReturnVO);
//			purchaseReturnItemVOs.add(purchaseReturnItemVO);
//		}
//
//		purchaseReturnVO.setTotalAmount(totalAmount);
//		purchaseReturnVO.setNetAmount(netAmount);
//		purchaseReturnVO.setTotalAmountTax(totalTaxAmount);
//		purchaseReturnVO
//				.setAmountInWords(amountInWordsConverterService.convert(purchaseReturnVO.getTotalAmount().longValue()));
//		purchaseReturnVO.setPurchaseReturnItemVO(purchaseReturnItemVOs);
//
//	}

	@Override
	public Map<String, Object> createUpdatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO)
			throws ApplicationException {
		PurchaseReturnVO purchaseReturnVO = new PurchaseReturnVO();
		String message;
		String screenCode = "PCR";
		PurchaseReturnVO oldPurchaseReturn = null;
		if (ObjectUtils.isNotEmpty(purchaseReturnDTO.getId())) {
			
			oldPurchaseReturn = purchaseReturnRepo.findById(purchaseReturnDTO.getId())
		            .orElseThrow(() -> new ApplicationException("purchaseReturn not found"));

			oldPurchaseReturn.getPurchaseReturnItemVO().size(); // load
			
		    entityManager.detach(oldPurchaseReturn); // detach snapshot
		    
			purchaseReturnVO = purchaseReturnRepo.findById(purchaseReturnDTO.getId())
					.orElseThrow(() -> new ApplicationException("PurchaseReturn Enquiry details"));
			purchaseReturnVO.setUpdatedBy(purchaseReturnDTO.getCreatedBy());
			createUpdatedPurchaseReturnVOFromPurchaseReturnDTO(purchaseReturnDTO, purchaseReturnVO);
			message = "PurchaseReturn Updated Successfully";

		} else {

			String docId = purchaseReturnRepo.getPurchaseReturnDocId(purchaseReturnDTO.getOrgId(),
					purchaseReturnDTO.getFinYear(), purchaseReturnDTO.getBranchCode(), screenCode);
			purchaseReturnVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseReturnDTO.getOrgId(),
							purchaseReturnDTO.getFinYear(), purchaseReturnDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			createUpdatedPurchaseReturnVOFromPurchaseReturnDTO(purchaseReturnDTO, purchaseReturnVO);
			purchaseReturnVO.setCreatedBy(purchaseReturnDTO.getCreatedBy());
			purchaseReturnVO.setUpdatedBy(purchaseReturnDTO.getCreatedBy());

			PurchaseReturnVO savedPurchase = purchaseReturnRepo.save(purchaseReturnVO);

			List<PurchaseReturnItemVO> purchaseReturnItemDetails = savedPurchase.getPurchaseReturnItemVO();
			if (purchaseReturnItemDetails != null && !purchaseReturnItemDetails.isEmpty()) {
				for (PurchaseReturnItemVO purchaseDetailsVO : purchaseReturnItemDetails) {
					StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
					stockDetailsVOFrom.setOrgId(savedPurchase.getOrgId());
					stockDetailsVOFrom.setDocId(savedPurchase.getDocId());
					stockDetailsVOFrom.setDocDate(savedPurchase.getDocDate());
					stockDetailsVOFrom.setRefNo(savedPurchase.getId());
					stockDetailsVOFrom.setLocation(savedPurchase.getToLocation());
					stockDetailsVOFrom.setRefDate(savedPurchase.getDocDate());
					stockDetailsVOFrom.setQty(purchaseDetailsVO.getRejectQty().negate());
					stockDetailsVOFrom.setUpdatedBy(savedPurchase.getUpdatedBy());
					stockDetailsVOFrom.setPartno(purchaseDetailsVO.getItemCode());
					stockDetailsVOFrom.setPartDesc(purchaseDetailsVO.getItemName());
					stockDetailsVOFrom.setPartyName(savedPurchase.getSupplierName());
					stockDetailsVOFrom.setPartyCode(savedPurchase.getSupplierCode());
					stockDetailsVOFrom.setSourceId(savedPurchase.getId());
					stockDetailsVOFrom.setStatus("CONFIRM");
					stockDetailsVOFrom.setSourceScreenCode(savedPurchase.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(savedPurchase.getScreenName());
					stockDetailsVOFrom.setBranchCode(savedPurchase.getBranchCode());
					stockDetailsVOFrom.setBranch(savedPurchase.getBranch());
					stockDetailsVOFrom.setPlusOrMinus("m");
					stockDetailsVOFrom.setRate(purchaseDetailsVO.getPoRate());
					stockDetailsVOFrom.setAmount(savedPurchase.getTotalAmount());
					stockDetailsVOFrom.setCustomer(savedPurchase.getCustomerName());
					stockDetailsRepo.save(stockDetailsVOFrom);
				}
			}

			message = "PurchaseReturn Created Successfully";
		}

		commonNotificationService.generateNotification(purchaseReturnVO.getScreenCode(), purchaseReturnVO.getId(), oldPurchaseReturn, purchaseReturnVO);

		Map<String, Object> response = new HashMap<>();
		response.put("purchaseReturnVO", purchaseReturnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedPurchaseReturnVOFromPurchaseReturnDTO(PurchaseReturnDTO purchaseReturnDTO,
			PurchaseReturnVO purchaseReturnVO) {
		purchaseReturnVO.setSupplierName(purchaseReturnDTO.getSupplierName());
		purchaseReturnVO.setSupplierCode(purchaseReturnDTO.getSupplierCode());
		purchaseReturnVO.setCustomerName(purchaseReturnDTO.getCustomerName());
		purchaseReturnVO.setCustomerCode(purchaseReturnDTO.getCustomerCode());
		purchaseReturnVO.setPurchaseInvoiceNo(purchaseReturnDTO.getPurchaseInvoiceNo());
		purchaseReturnVO.setPurchaseInvoiceDate(purchaseReturnDTO.getPurchaseInvoiceDate());
		purchaseReturnVO.setPoNo(purchaseReturnDTO.getPoNo());
		purchaseReturnVO.setGstNo(purchaseReturnDTO.getGstNo());
		purchaseReturnVO.setGstState(purchaseReturnDTO.getGstState());
		purchaseReturnVO.setAddress(purchaseReturnDTO.getAddress());
		purchaseReturnVO.setGatePassNo(purchaseReturnDTO.getGatePassNo());
		purchaseReturnVO.setIsReverseChrg(purchaseReturnDTO.getIsReverseChrg());
		purchaseReturnVO.setCurrency(purchaseReturnDTO.getCurrency());
		purchaseReturnVO.setExchangeRate(purchaseReturnDTO.getExchangeRate());
		purchaseReturnVO.setInvDcNo(purchaseReturnDTO.getInvDcNo());
		purchaseReturnVO.setInvDcDate(purchaseReturnDTO.getInvDcDate());
		purchaseReturnVO.setGstType(purchaseReturnDTO.getGstType());
		purchaseReturnVO.setToLocation(purchaseReturnDTO.getToLocation());
		purchaseReturnVO.setRemarks(purchaseReturnDTO.getRemarks());
		purchaseReturnVO.setStatus(purchaseReturnDTO.getStatus());
		purchaseReturnVO.setPoPendingCancel(purchaseReturnDTO.isPoPendingCancel());
		purchaseReturnVO.setOrgId(purchaseReturnDTO.getOrgId());
		purchaseReturnVO.setActive(purchaseReturnDTO.isActive());
		purchaseReturnVO.setBranch(purchaseReturnDTO.getBranch());
		purchaseReturnVO.setBranchCode(purchaseReturnDTO.getBranchCode());
		purchaseReturnVO.setFinYear(purchaseReturnDTO.getFinYear());
		purchaseReturnVO.setPoPendingCancel(purchaseReturnDTO.isPoPendingCancel());

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(purchaseReturnDTO.getId())) {
			List<PurchaseReturnItemVO> purchaseReturnItemVO1 = purchaseReturnItemRepo
					.findByPurchaseReturnVO(purchaseReturnVO);
			purchaseReturnItemRepo.deleteAll(purchaseReturnItemVO1);

		}

		List<PurchaseReturnItemVO> purchaseReturnItemVOs = new ArrayList<>();
		for (PurchaseReturnItemDTO purchaseReturnItemDTO : purchaseReturnDTO.getPurchaseReturnItemDTO()) {
			PurchaseReturnItemVO purchaseReturnItemVO = new PurchaseReturnItemVO();
			purchaseReturnItemVO.setItemCode(purchaseReturnItemDTO.getItemCode());
			purchaseReturnItemVO.setItemName(purchaseReturnItemDTO.getItemName());
			purchaseReturnItemVO.setHsnSacCode(purchaseReturnItemDTO.getHsnSacCode());
			purchaseReturnItemVO.setTaxCode(purchaseReturnItemDTO.getTaxCode());
			purchaseReturnItemVO.setPrimaryUnit(purchaseReturnItemDTO.getPrimaryUnit());
			purchaseReturnItemVO.setPoRate(purchaseReturnItemDTO.getPoRate());
			purchaseReturnItemVO.setRejectQty(purchaseReturnItemDTO.getRejectQty());
			purchaseReturnItemVO.setUnitPrice(purchaseReturnItemDTO.getUnitPrice());
			purchaseReturnItemVO.setOrderQty(purchaseReturnItemDTO.getOrderQty());
			purchaseReturnItemVO.setChallanQty(purchaseReturnItemDTO.getChallanQty());
			purchaseReturnItemVO.setSgst(purchaseReturnItemDTO.getSgst());
			purchaseReturnItemVO.setCgst(purchaseReturnItemDTO.getCgst());
			purchaseReturnItemVO.setIgst(purchaseReturnItemDTO.getIgst());

			BigDecimal taxAmount = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;

			BigDecimal amountSet = purchaseReturnItemDTO.getUnitPrice().multiply(purchaseReturnItemDTO.getRejectQty());
			purchaseReturnItemVO.setAmount(amountSet);

			totalAmount = totalAmount.add(amountSet);

			if (purchaseReturnVO.getGstType() == null || purchaseReturnVO.getGstType().isEmpty()
					|| !purchaseReturnVO.getGstType().equalsIgnoreCase("INTRA")
							&& !purchaseReturnVO.getGstType().equalsIgnoreCase("INTER")) {
				purchaseReturnItemVO.setIgst(BigDecimal.ZERO);
				purchaseReturnItemVO.setCgst(BigDecimal.ZERO);
				purchaseReturnItemVO.setSgst(BigDecimal.ZERO);
				purchaseReturnItemVO.setTaxValue(BigDecimal.ZERO);
			} else {
				if (purchaseReturnVO.getGstType().equalsIgnoreCase("INTER")) {
					purchaseReturnItemVO.setIgst(purchaseReturnItemDTO.getIgst());
					BigDecimal igstAmount = purchaseReturnItemDTO.getIgst().multiply(purchaseReturnItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					purchaseReturnItemVO.setCgst(BigDecimal.ZERO);
					purchaseReturnItemVO.setSgst(BigDecimal.ZERO);
					taxAmount = igstAmount;
					purchaseReturnItemVO.setTaxValue(taxAmount);
				} else if (purchaseReturnVO.getGstType().equalsIgnoreCase("INTRA")) {
					purchaseReturnItemVO.setCgst(purchaseReturnItemDTO.getCgst());
					purchaseReturnItemVO.setSgst(purchaseReturnItemDTO.getSgst());
					BigDecimal sgstAmount = purchaseReturnItemDTO.getSgst().multiply(purchaseReturnItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					BigDecimal cgstAmount = purchaseReturnItemDTO.getCgst().multiply(purchaseReturnItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					purchaseReturnItemVO.setIgst(BigDecimal.ZERO);
					taxAmount = cgstAmount.add(sgstAmount);
					purchaseReturnItemVO.setTaxValue(taxAmount);
				}
			}
			totalTaxAmount = totalTaxAmount.add(purchaseReturnItemVO.getTaxValue());

			landedValues = purchaseReturnItemVO.getAmount().add(purchaseReturnItemVO.getTaxValue());
			purchaseReturnItemVO.setLandedValue(landedValues);
			netAmount = netAmount.add(purchaseReturnItemVO.getLandedValue());
//			totalAmount = purchaseReturnItemVO.getTaxValue().add(purchaseReturnItemVO.getLandedValue());

			purchaseReturnItemVO.setPurchaseReturnVO(purchaseReturnVO);
			purchaseReturnItemVOs.add(purchaseReturnItemVO);
		}

		purchaseReturnVO.setTotalAmount(netAmount);
		purchaseReturnVO.setNetAmount(totalAmount);
		purchaseReturnVO.setTotalAmountTax(totalTaxAmount);
		purchaseReturnVO
				.setAmountInWords(amountInWordsConverterService.convert(purchaseReturnVO.getTotalAmount().longValue()));
		purchaseReturnVO.setPurchaseReturnItemVO(purchaseReturnItemVOs);

		purchaseReturnVO.setPurchaseReturnItemVO(purchaseReturnItemVOs);

		purchaseReturnRepo.save(purchaseReturnVO);

	}

	@Override
	public List<PurchaseReturnVO> getAllPurchaseReturnByOrgId(Long orgId, String finYear, String branchCode) {

		return purchaseReturnRepo.getAllPurchaseReturnByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public PurchaseReturnVO getPurchaseReturnById(Long id) {

		return purchaseReturnRepo.getPurchaseReturnById(id);
	}

	@Override
	public String getPurchaseReturnDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "PCR";
		String result = purchaseReturnRepo.getPurchaseReturnDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPurchaseInvoiceNumberFromPurchaseInvoice(Long orgId, String supplierCode) {
		Set<Object[]> chType = purchaseReturnRepo.getPurchaseInvoiceNumberFromPurchaseInvoice(orgId, supplierCode);
		return getPurchaseInvoiceNumber(chType);
	}

	private List<Map<String, Object>> getPurchaseInvoiceNumber(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("purchaseInvoiceNo", ch[0] != null ? ch[0].toString() : "");
			map.put("purchaseInvoiceDate", ch[1] != null ? ch[1].toString() : "");
			map.put("grnTime", ch[2] != null ? ch[2].toString() : "");
			map.put("poNo", ch[3] != null ? ch[3].toString() : "");
			map.put("gstNo", ch[4] != null ? ch[4].toString() : "");
			map.put("address", ch[5] != null ? ch[5].toString() : "");
			map.put("gatePassNo", ch[6] != null ? ch[6].toString() : "");
			map.put("currency", ch[7] != null ? ch[7].toString() : "");
			map.put("exchangeRate", ch[8] != null ? ch[8].toString() : "");
			map.put("invdcNo", ch[9] != null ? ch[9].toString() : "");
			map.put("invdcDate", ch[10] != null ? ch[10].toString() : "");
			map.put("gstType", ch[11] != null ? ch[11].toString() : "");
			map.put("customerName", ch[12] != null ? ch[12].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getLocationFromStockLocation(Long orgId) {
		Set<Object[]> chType = purchaseReturnRepo.getLocationFromStockLocation(orgId);
		return getLocation(chType);
	}

	private List<Map<String, Object>> getLocation(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("location", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemCodeAndItemDescFromPurchsaeInvoice(Long orgId, String purchaseInvoiceNo) {
		Set<Object[]> chType = purchaseReturnRepo.getItemCodeAndItemDescFromPurchsaeInvoice(orgId, purchaseInvoiceNo);
		return getItemCodeAndItemDesc(chType);
	}

	private List<Map<String, Object>> getItemCodeAndItemDesc(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("hsnScaCode", ch[2] != null ? ch[2].toString() : "");
			map.put("taxType", ch[3] != null ? ch[3].toString() : "");
			map.put("primaryUnit", ch[4] != null ? ch[4].toString() : "");
			map.put("poRate", ch[5] != null ? ch[5].toString() : "");
			map.put("unitPrice", ch[6] != null ? ch[6].toString() : "");
			map.put("rejectQty", ch[7] != null ? ch[7].toString() : "");
			map.put("orderQty", ch[8] != null ? ch[8].toString() : "");
			map.put("challanQty", ch[9] != null ? ch[9].toString() : "");
			map.put("amount", ch[10] != null ? ch[10].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// purchaseInvoice

	@Override
	public Map<String, Object> createUpdatePurchaseInvoice(PurchaseInvoiceDTO purchaseInvoiceDTO)
			throws ApplicationException {
		PurchaseInvoiceVO purchaseInvoiceVO = new PurchaseInvoiceVO();
		String message;
		String screenCode = "PCI";
		PurchaseInvoiceVO oldPurchaseInvoice = null;
		
		if (ObjectUtils.isNotEmpty(purchaseInvoiceDTO.getId())) {
			
			oldPurchaseInvoice = purchaseInvoiceRepo.findById(purchaseInvoiceDTO.getId())
		            .orElseThrow(() -> new ApplicationException("purchaseInvoice not found"));

			oldPurchaseInvoice.getPurchaseInvoiceItemVO().size(); // load
			
		    entityManager.detach(oldPurchaseInvoice); // detach snapshot
		    
			purchaseInvoiceVO = purchaseInvoiceRepo.findById(purchaseInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("PurchaseInvoice Enquiry details"));
			message = "PurchaseInvoice Updated Successfully";
			purchaseInvoiceVO.setUpdatedBy(purchaseInvoiceDTO.getCreatedBy());

		} else {

			String docId = purchaseInvoiceRepo.getPurchaseInvoiceDocId(purchaseInvoiceDTO.getOrgId(),
					purchaseInvoiceDTO.getFinYear(), purchaseInvoiceDTO.getBranchCode(), screenCode);
			purchaseInvoiceVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseInvoiceDTO.getOrgId(),
							purchaseInvoiceDTO.getFinYear(), purchaseInvoiceDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseInvoiceVO.setCreatedBy(purchaseInvoiceDTO.getCreatedBy());
			purchaseInvoiceVO.setUpdatedBy(purchaseInvoiceDTO.getCreatedBy());

			message = "PurchaseInvoice Created Successfully";
		}
		createUpdatedPurchaseInvoiceVOFromPurchaseInvoiceDTO(purchaseInvoiceDTO, purchaseInvoiceVO);
		purchaseInvoiceRepo.save(purchaseInvoiceVO);
		commonNotificationService.generateNotification(purchaseInvoiceVO.getScreenCode(), purchaseInvoiceVO.getId(), oldPurchaseInvoice, purchaseInvoiceVO);

		Map<String, Object> response = new HashMap<>();
		response.put("purchaseInvoiceVO", purchaseInvoiceVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedPurchaseInvoiceVOFromPurchaseInvoiceDTO(PurchaseInvoiceDTO purchaseInvoiceDTO,
			PurchaseInvoiceVO purchaseInvoiceVO) {
		purchaseInvoiceVO.setSupplierName(purchaseInvoiceDTO.getSupplierName());
		purchaseInvoiceVO.setPoNo(purchaseInvoiceDTO.getPoNo());
		purchaseInvoiceVO.setGrnNo(purchaseInvoiceDTO.getGrnNo());
		purchaseInvoiceVO.setGrnDate(purchaseInvoiceDTO.getGrnDate());
		purchaseInvoiceVO.setLocation(purchaseInvoiceDTO.getLocation());
		purchaseInvoiceVO.setInWardNo(purchaseInvoiceDTO.getInWardNo());
		purchaseInvoiceVO.setSupplierCode(purchaseInvoiceDTO.getSupplierCode());
		purchaseInvoiceVO.setGstState(purchaseInvoiceDTO.getGstState());
		purchaseInvoiceVO.setGstNo(purchaseInvoiceDTO.getGstNo());
		purchaseInvoiceVO.setIsReverseChrg(purchaseInvoiceDTO.getIsReverseChrg());
		purchaseInvoiceVO.setAddress(purchaseInvoiceDTO.getAddress());
		purchaseInvoiceVO.setCurrency(purchaseInvoiceDTO.getCurrency());
		purchaseInvoiceVO.setExchangeRate(purchaseInvoiceDTO.getExchangeRate());
		purchaseInvoiceVO.setInvDcNo(purchaseInvoiceDTO.getInvDcNo());
		purchaseInvoiceVO.setInvDcDate(purchaseInvoiceDTO.getInvDcDate());
		purchaseInvoiceVO.setGstType(purchaseInvoiceDTO.getGstType());
		purchaseInvoiceVO.setCustomerName(purchaseInvoiceDTO.getCustomerName());
		purchaseInvoiceVO.setRemarks(purchaseInvoiceDTO.getRemarks());
		purchaseInvoiceVO.setCnt(purchaseInvoiceDTO.getCnt());
		purchaseInvoiceVO.setOrgId(purchaseInvoiceDTO.getOrgId());
		purchaseInvoiceVO.setActive(purchaseInvoiceDTO.isActive());
		purchaseInvoiceVO.setBranch(purchaseInvoiceDTO.getBranch());
		purchaseInvoiceVO.setBranchCode(purchaseInvoiceDTO.getBranchCode());
		purchaseInvoiceVO.setFinYear(purchaseInvoiceDTO.getFinYear());

		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(purchaseInvoiceDTO.getId())) {
			List<PurchaseInvoiceItemVO> purchaseInvoiceItemVO1 = purchaseInvoiceItemRepo
					.findByPurchaseInvoiceVO(purchaseInvoiceVO);
			purchaseInvoiceItemRepo.deleteAll(purchaseInvoiceItemVO1);

		}

		List<PurchaseInvoiceItemVO> purchaseInvoiceItemVOs = new ArrayList<>();
		for (PurchaseInvoiceItemDTO purchaseInvoiceItemDTO : purchaseInvoiceDTO.getPurchaseInvoiceItemDTO()) {
			PurchaseInvoiceItemVO purchaseInvoiceItemVO = new PurchaseInvoiceItemVO();
			purchaseInvoiceItemVO.setItemCode(purchaseInvoiceItemDTO.getItemCode());
			purchaseInvoiceItemVO.setItemName(purchaseInvoiceItemDTO.getItemName());
			purchaseInvoiceItemVO.setHsnSacCode(purchaseInvoiceItemDTO.getHsnSacCode());
			purchaseInvoiceItemVO.setTaxtype(purchaseInvoiceItemDTO.getTaxtype());
			purchaseInvoiceItemVO.setPrimaryUnit(purchaseInvoiceItemDTO.getPrimaryUnit());
			purchaseInvoiceItemVO.setPoRate(purchaseInvoiceItemDTO.getPoRate());
			purchaseInvoiceItemVO.setReceivedQty(purchaseInvoiceItemDTO.getReceivedQty());
			purchaseInvoiceItemVO.setAcceptQty(purchaseInvoiceItemDTO.getAcceptQty());
			purchaseInvoiceItemVO.setUnitPrice(purchaseInvoiceItemDTO.getUnitPrice());
			purchaseInvoiceItemVO.setOrderQty(purchaseInvoiceItemDTO.getOrderQty());
			purchaseInvoiceItemVO.setChallanQty(purchaseInvoiceItemDTO.getChallanQty());
			purchaseInvoiceItemVO.setReceivedQty(purchaseInvoiceItemDTO.getReceivedQty());
			purchaseInvoiceItemVO.setPoDetailsId(purchaseInvoiceItemDTO.getPoDetailsId());

			BigDecimal taxAmount = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;

			BigDecimal amountSet = purchaseInvoiceItemDTO.getPoRate().multiply(purchaseInvoiceItemDTO.getAcceptQty());
			purchaseInvoiceItemVO.setAmount(amountSet);
			grossAmount = grossAmount.add(purchaseInvoiceItemVO.getAmount());

			if (purchaseInvoiceVO.getGstType() == null || purchaseInvoiceVO.getGstType().isEmpty()
					|| !purchaseInvoiceVO.getGstType().equalsIgnoreCase("INTRA")
							&& !purchaseInvoiceVO.getGstType().equalsIgnoreCase("INTER")) {
				purchaseInvoiceItemVO.setIgst(BigDecimal.ZERO);
				purchaseInvoiceItemVO.setCgst(BigDecimal.ZERO);
				purchaseInvoiceItemVO.setSgst(BigDecimal.ZERO);
				purchaseInvoiceItemVO.setTaxValue(BigDecimal.ZERO);
			} else {
				if (purchaseInvoiceVO.getGstType().equalsIgnoreCase("INTER")) {
					purchaseInvoiceItemVO.setIgst(purchaseInvoiceItemDTO.getIgst());
					BigDecimal igstAmount = purchaseInvoiceItemDTO.getIgst().multiply(purchaseInvoiceItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					purchaseInvoiceItemVO.setCgst(BigDecimal.ZERO);
					purchaseInvoiceItemVO.setSgst(BigDecimal.ZERO);
					taxAmount = igstAmount;
					purchaseInvoiceItemVO.setTaxValue(taxAmount);
				} else if (purchaseInvoiceVO.getGstType().equalsIgnoreCase("INTRA")) {
					purchaseInvoiceItemVO.setCgst(purchaseInvoiceItemDTO.getCgst());
					purchaseInvoiceItemVO.setSgst(purchaseInvoiceItemDTO.getSgst());
					BigDecimal sgstAmount = purchaseInvoiceItemDTO.getSgst().multiply(purchaseInvoiceItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					BigDecimal cgstAmount = purchaseInvoiceItemDTO.getCgst().multiply(purchaseInvoiceItemVO.getAmount())
							.divide(BigDecimal.valueOf(100));
					purchaseInvoiceItemVO.setIgst(BigDecimal.ZERO);
					taxAmount = cgstAmount.add(sgstAmount);
					purchaseInvoiceItemVO.setTaxValue(taxAmount);
				}
			}
			totalTaxAmount = totalTaxAmount.add(purchaseInvoiceItemVO.getTaxValue());

			landedValues = purchaseInvoiceItemVO.getAmount().add(purchaseInvoiceItemVO.getTaxValue());
			purchaseInvoiceItemVO.setLandedValue(landedValues);
			netAmount = netAmount.add(purchaseInvoiceItemVO.getLandedValue());

			purchaseInvoiceItemVO.setPurchaseInvoiceVO(purchaseInvoiceVO);
			purchaseInvoiceItemVOs.add(purchaseInvoiceItemVO);
		}

		purchaseInvoiceVO.setGrossAmount(grossAmount);
		purchaseInvoiceVO.setNetAmount(netAmount);
		purchaseInvoiceVO.setTotalAmountTax(totalTaxAmount);
		purchaseInvoiceVO.setPurchaseInvoiceItemVO(purchaseInvoiceItemVOs);

	}

	@Override
	public List<PurchaseInvoiceVO> getAllPurchaseInvoiceByOrgId(Long orgId, String finYear, String branchCode) {

		return purchaseInvoiceRepo.getAllPurchaseInvoiceByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public PurchaseInvoiceVO getPurchaseInvoiceById(Long id) {

		return purchaseInvoiceRepo.getPurchaseInvoiceById(id);
	}

	@Override
	public String getPurchaseInvoiceDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "PCI";
		String result = purchaseInvoiceRepo.getPurchaseInvoiceDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderPoNumber(Long orgId, String supplierCode) {
		Set<Object[]> chType = purchaseInvoiceRepo.getPurchaseOrderPoNumber(orgId, supplierCode);
		return getPoNo(chType);
	}

	private List<Map<String, Object>> getPoNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("poNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getGrnNoAndGrnDateFromGrnDetails(Long orgId, String poNo) {
		Set<Object[]> chType = purchaseInvoiceRepo.getGrnNoAndGrnDateFromGrnDetails(orgId, poNo);
		return getGrnNoAndGrnDate(chType);
	}

	private List<Map<String, Object>> getGrnNoAndGrnDate(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnNo", ch[0] != null ? ch[0].toString() : "");
			map.put("grnDate", ch[1] != null ? ch[1].toString() : "");
			map.put("location", ch[2] != null ? ch[2].toString() : "");
			map.put("inwordNo", ch[3] != null ? ch[3].toString() : "");
			map.put("supplierCode", ch[4] != null ? ch[4].toString() : "");
			map.put("gstNo", ch[5] != null ? ch[5].toString() : "");
			map.put("address", ch[6] != null ? ch[6].toString() : "");
			map.put("currency", ch[7] != null ? ch[7].toString() : "");
			map.put("exchangeRate", ch[8] != null ? ch[8].toString() : "");
			map.put("grnClearTime", ch[9] != null ? ch[9].toString() : "");
			map.put("invdcNo", ch[10] != null ? ch[10].toString() : "");
			map.put("invdcDate", ch[11] != null ? ch[11].toString() : "");
			map.put("gstType", ch[12] != null ? ch[12].toString() : "");
			map.put("customerName", ch[13] != null ? ch[13].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getItemCodeAndItemDescFromGrn(Long orgId, String grnNo) {
		Set<Object[]> chType = purchaseInvoiceRepo.getItemCodeAndItemDescFromGrn(orgId, grnNo);
		return getItemCode(chType);
	}

	private List<Map<String, Object>> getItemCode(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("hsnScaCode", ch[2] != null ? ch[2].toString() : "");
			map.put("taxType", ch[3] != null ? ch[3].toString() : "");
			map.put("primaryUnit", ch[4] != null ? ch[4].toString() : "");
			map.put("poRate", ch[5] != null ? ch[5].toString() : "");
			map.put("receivedQty", ch[6] != null ? ch[6].toString() : "");
			map.put("acceptQty", ch[7] != null ? ch[7].toString() : "");
			map.put("orderQty", ch[8] != null ? ch[8].toString() : "");
			map.put("challanQty", ch[9] != null ? ch[9].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPoDetailsId(String docId, String item, Long orgId) {
		Set<Object[]> chType = purchaseInvoiceRepo.getPoDetailsId(docId, item, orgId);
		return getPoDetails(chType);
	}

	private List<Map<String, Object>> getPoDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("poDetailsId", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// purchase Order
	@Override
	public List<PurchaseOrderVO> getPurchaseOrderByOrgId(Long orgId, String finYear, String branchCode) {
		List<PurchaseOrderVO> purchaseOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			purchaseOrderVO = purchaseOrderRepo.findPurchaseOrderByOrgId(orgId, finYear, branchCode);
		}
		return purchaseOrderVO;
	}

	@Override
	public List<PurchaseOrderVO> getPurchaseOrderById(Long id) {
		List<PurchaseOrderVO> purchaseOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			purchaseOrderVO = purchaseOrderRepo.getPurchaseOrderById(id);
		}
		return purchaseOrderVO;
	}

//	@Override
//	public Map<String, Object> updateCreatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO)
//			throws ApplicationException {
//		PurchaseOrderVO purchaseOrderVO = new PurchaseOrderVO();
//		String message;
//		String screenCode = "PONO";
//		if (ObjectUtils.isNotEmpty(purchaseOrderDTO.getId())) {
//			purchaseOrderVO = purchaseOrderRepo.findById(purchaseOrderDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Invalid PO details"));
//			message = "Purchase Order Updated Successfully";
//			purchaseOrderVO.setUpdatedBy(purchaseOrderDTO.getCreatedBy());
//
//		} else {
//
//			String docId = purchaseOrderRepo.getPurchaseOrderDocId(purchaseOrderDTO.getOrgId(),
//					purchaseOrderDTO.getFinYear(), purchaseOrderDTO.getBranchCode(), screenCode);
//			purchaseOrderVO.setDocId(docId);
//
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseOrderDTO.getOrgId(),
//							purchaseOrderDTO.getFinYear(), purchaseOrderDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			purchaseOrderVO.setCreatedBy(purchaseOrderDTO.getCreatedBy());
//			purchaseOrderVO.setUpdatedBy(purchaseOrderDTO.getCreatedBy());
//
//			message = "Enquiry Created Successfully";
//		}
//		createUpdatePurchaseorderVOByPurchaseorderDTO(purchaseOrderDTO, purchaseOrderVO);
//		PurchaseOrderVO savedPurchaseOrderVO = purchaseOrderRepo.save(purchaseOrderVO);
//		
//		List<PurchaseOrderDetailsVO> purchaseOrderDetailsVOs = savedPurchaseOrderVO.getPurchaseOrderDetailsVO();
//
//		Long sourceId = savedPurchaseOrderVO.getId();
//		if (sourceId != null) { // Null check for safety
//		    List<PurchaseOrderPendingVO> purchaseOrderPendingList = purchaseOrderPendingRepo.findBySourceId(sourceId);
//		    
//		    if (!purchaseOrderPendingList.isEmpty()) { // Check if records exist
//		        purchaseOrderPendingRepo.deleteAll(purchaseOrderPendingList);
//		    }
//		}
//		
//		if (purchaseOrderDetailsVOs != null && !purchaseOrderDetailsVOs.isEmpty()) {
//
//			for (PurchaseOrderDetailsVO purchaseOrderDetailsVO : purchaseOrderDetailsVOs) {
//				PurchaseOrderPendingVO purchaseOrderPendingVO = new PurchaseOrderPendingVO();
//				purchaseOrderPendingVO.setPoNo(savedPurchaseOrderVO.getDocId());
//				purchaseOrderPendingVO.setCustomerName(savedPurchaseOrderVO.getCustomerName());
//				purchaseOrderPendingVO.setCustomerCode(savedPurchaseOrderVO.getCustomerCode());
//				purchaseOrderPendingVO.setWorkOrderNo(savedPurchaseOrderVO.getWorkOrderNo());
//				purchaseOrderPendingVO.setSupplierName(savedPurchaseOrderVO.getSupplierName());
//				purchaseOrderPendingVO.setSupplierCode(savedPurchaseOrderVO.getSupplierCode());
//				purchaseOrderPendingVO.setItem(purchaseOrderDetailsVO.getItem());
//				purchaseOrderPendingVO.setItemDesc(purchaseOrderDetailsVO.getItemDesc());
//				purchaseOrderPendingVO.setQty(purchaseOrderDetailsVO.getQty());
//				purchaseOrderPendingVO.setTaxType(purchaseOrderDetailsVO.getTaxType());
////				purchaseOrderPendingVO.setuom(purchaseOrderDetailsVO.getUom());
//				purchaseOrderPendingVO.setPrice(purchaseOrderDetailsVO.getPrice());
//				purchaseOrderPendingVO.setAmount(purchaseOrderDetailsVO.getAmount());
//				purchaseOrderPendingVO.setTaxValue(purchaseOrderDetailsVO.getTaxValue());
//				purchaseOrderPendingVO.setLandedValue(purchaseOrderDetailsVO.getLandedValue());
//				purchaseOrderPendingVO.setPlusOrMinus("P");
//				purchaseOrderPendingVO.setSourceId(savedPurchaseOrderVO.getId());
//				purchaseOrderPendingVO.setPoPendingCancel(savedPurchaseOrderVO.isPoPendingCancel());
//
//
//				purchaseOrderPendingVO.setFinYear(savedPurchaseOrderVO.getFinYear());
//				purchaseOrderPendingVO.setCreatedBy(savedPurchaseOrderVO.getCreatedBy());
//				purchaseOrderPendingVO.setUpdatedBy(savedPurchaseOrderVO.getUpdatedBy());
//				purchaseOrderPendingVO.setBranch(savedPurchaseOrderVO.getBranch());
//				purchaseOrderPendingVO.setBranchCode(savedPurchaseOrderVO.getBranchCode());
//				purchaseOrderPendingVO.setOrgId(savedPurchaseOrderVO.getOrgId());
//				purchaseOrderPendingRepo.save(purchaseOrderPendingVO);
//			}
//		}
//
//		
//		Map<String, Object> response = new HashMap<>();
//		response.put("purchaseOrderVO", purchaseOrderVO);
//		response.put("message", message);
//		return response;
//
//	}
//
//	private void createUpdatePurchaseorderVOByPurchaseorderDTO(@Valid PurchaseOrderDTO purchaseOrderDTO,
//			PurchaseOrderVO purchaseOrderVO) throws ApplicationException {
//		purchaseOrderVO.setCustomerName(purchaseOrderDTO.getCustomerName());
//		purchaseOrderVO.setCustomerCode(purchaseOrderDTO.getCustomerCode());
//		purchaseOrderVO.setWorkOrderNo(purchaseOrderDTO.getWorkOrderNo());
//		purchaseOrderVO.setBasedOn(purchaseOrderDTO.getBasedOn());
//		purchaseOrderVO.setQuotationNo(purchaseOrderDTO.getQuotationNo());
//		purchaseOrderVO.setPurchaseIndentNo(purchaseOrderDTO.getPurchaseIndentNo());
//		purchaseOrderVO.setSupplierName(purchaseOrderDTO.getSupplierName());
//		purchaseOrderVO.setSupplierCode(purchaseOrderDTO.getSupplierCode());
//		purchaseOrderVO.setContactPerson(purchaseOrderDTO.getContactPerson());
//		purchaseOrderVO.setMobileNo(purchaseOrderDTO.getMobileNo());
//		purchaseOrderVO.setEmail(purchaseOrderDTO.getEmail());
//		purchaseOrderVO.setCity(purchaseOrderDTO.getCity());
//		purchaseOrderVO.setState(purchaseOrderDTO.getState());
//		purchaseOrderVO.setCountry(purchaseOrderDTO.getCountry());
//		purchaseOrderVO.setTaxCode(purchaseOrderDTO.getTaxCode());
//		purchaseOrderVO.setAddress(purchaseOrderDTO.getAddress());
//		purchaseOrderVO.setRemarks(purchaseOrderDTO.getRemarks());
//		purchaseOrderVO.setOrgId(purchaseOrderDTO.getOrgId());
//		purchaseOrderVO.setBranch(purchaseOrderDTO.getBranch());
//		purchaseOrderVO.setBranchCode(purchaseOrderDTO.getBranchCode());
//		purchaseOrderVO.setFinYear(purchaseOrderDTO.getFinYear());
//		purchaseOrderVO.setPoPendingCancel(purchaseOrderDTO.isPoPendingCancel());
//
//		purchaseOrderVO.setCreatedBy(purchaseOrderDTO.getCreatedBy());
//		BigDecimal grossAmount = BigDecimal.ZERO;
//		BigDecimal netAmount = BigDecimal.ZERO;
//		BigDecimal totalTaxAmount = BigDecimal.ZERO;
//		BigDecimal totalLandedAmount = BigDecimal.ZERO;
//
//		if (ObjectUtils.isNotEmpty(purchaseOrderVO.getId())) {
//			List<PurchaseOrderDetailsVO> purchaseOrderDetailsVo1 = purchaseOrderDetailsRepo
//					.findByPurchaseOrderVO(purchaseOrderVO);
//			purchaseOrderDetailsRepo.deleteAll(purchaseOrderDetailsVo1);
//		}
//
//		List<PurchaseOrderDetailsVO> purchaseOrderDetailsVOs = new ArrayList<>();
//		for (PurchaseOrderDetailsDTO purchaseOrderDetailsDTO : purchaseOrderDTO.getPurchaseOrderDetailsDTO()) {
//			PurchaseOrderDetailsVO purchaseOrderDetailsVO = new PurchaseOrderDetailsVO();
//			purchaseOrderDetailsVO.setItem(purchaseOrderDetailsDTO.getItem());
//			purchaseOrderDetailsVO.setItemDesc(purchaseOrderDetailsDTO.getItemDesc());
//			purchaseOrderDetailsVO.setHsnSacCode(purchaseOrderDetailsDTO.getHsnSacCode());
//			purchaseOrderDetailsVO.setTaxType(purchaseOrderDetailsDTO.getTaxType());
//			purchaseOrderDetailsVO.setUom(purchaseOrderDetailsDTO.getUom());
//			purchaseOrderDetailsVO.setQty(purchaseOrderDetailsDTO.getQty());
//			purchaseOrderDetailsVO.setPrice(purchaseOrderDetailsDTO.getPrice());
//			purchaseOrderDetailsVO.setPrevRate(purchaseOrderDetailsDTO.getPrevRate());
//			purchaseOrderDetailsVO.setDiscount(purchaseOrderDetailsDTO.getDiscount());
//
//			purchaseOrderDetailsVO.setIgst(purchaseOrderDetailsDTO.getIgst());
//			purchaseOrderDetailsVO.setSgst(purchaseOrderDetailsDTO.getSgst());
//			purchaseOrderDetailsVO.setHsnSacCode(purchaseOrderDetailsDTO.getHsnSacCode());
//			purchaseOrderDetailsVO.setCgst(purchaseOrderDetailsDTO.getCgst());
//
//			BigDecimal taxAmount = BigDecimal.ZERO;
//			BigDecimal landedValues = BigDecimal.ZERO;
//
//			BigDecimal amountSet = purchaseOrderDetailsDTO.getPrice().multiply(purchaseOrderDetailsDTO.getQty());
//			purchaseOrderDetailsVO.setAmount(amountSet);
//
//			grossAmount = grossAmount.add(purchaseOrderDetailsVO.getAmount());
//			BigDecimal discountAmount = purchaseOrderDetailsVO.getAmount()
//					.multiply(purchaseOrderDetailsDTO.getDiscount()).divide(BigDecimal.valueOf(100));
//			purchaseOrderDetailsVO.setDiscountAmt(discountAmount);
//			BigDecimal amountSubtractDiscountAmount = purchaseOrderDetailsVO.getAmount()
//					.subtract(purchaseOrderDetailsVO.getDiscountAmt());
//			purchaseOrderDetailsVO.setNetAmount(amountSubtractDiscountAmount);
//			netAmount = netAmount.add(amountSubtractDiscountAmount);
//
//			BigDecimal sgstamount = purchaseOrderDetailsDTO.getSgst()
//					.multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));
//			BigDecimal cgstamount = purchaseOrderDetailsDTO.getCgst()
//					.multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));
//			BigDecimal igstamount = purchaseOrderDetailsDTO.getIgst()
//					.multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));
//
//			purchaseOrderDetailsVO.setSgstAmount(sgstamount);
//			purchaseOrderDetailsVO.setIgstAmount(igstamount);
//			purchaseOrderDetailsVO.setCgstAmount(cgstamount);
//			taxAmount = taxAmount.add(cgstamount).add(sgstamount).add(igstamount);
//			purchaseOrderDetailsVO.setTaxValue(taxAmount);
//			totalTaxAmount = totalTaxAmount.add(purchaseOrderDetailsVO.getTaxValue());
//
//			landedValues = amountSubtractDiscountAmount.add(purchaseOrderDetailsVO.getTaxValue());
//			purchaseOrderDetailsVO.setLandedValue(landedValues);
//			totalLandedAmount = totalLandedAmount.add(purchaseOrderDetailsVO.getLandedValue());
//
//			purchaseOrderDetailsVO.setPurchaseOrderVO(purchaseOrderVO); // Set the reference in child entity
//			purchaseOrderDetailsVOs.add(purchaseOrderDetailsVO);
//		}
//
//		purchaseOrderVO.setGrossAmount(grossAmount);
//		purchaseOrderVO.setNetAmount(netAmount);
//		purchaseOrderVO.setTotalLandedAmount(totalLandedAmount);
//
//		purchaseOrderVO.setTotalAmountTax(totalTaxAmount);
//		purchaseOrderVO.setAmtInWords(
//				amountInWordsConverterService.convert(purchaseOrderVO.getTotalLandedAmount().longValue()));
//
//		purchaseOrderVO.setPurchaseOrderDetailsVO(purchaseOrderDetailsVOs);
//
//	}

	@Override
	public Map<String, Object> updateCreatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO)
			throws ApplicationException {
		PurchaseOrderVO purchaseOrderVO = new PurchaseOrderVO();
		String message;
		String screenCode = "PONO";
		PurchaseOrderVO oldPurchaseOrder =null;
		if (ObjectUtils.isNotEmpty(purchaseOrderDTO.getId())) {
			
			oldPurchaseOrder = purchaseOrderRepo.findById(purchaseOrderDTO.getId())
		            .orElseThrow(() -> new ApplicationException("purchaseOrder not found"));

			oldPurchaseOrder.getPurchaseOrderDetailsVO().size(); // load
			
		    entityManager.detach(oldPurchaseOrder); // detach snapshot
		    
			purchaseOrderVO = purchaseOrderRepo.findById(purchaseOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid PO details"));
			message = "Purchase Order Updated Successfully";
			purchaseOrderVO.setUpdatedBy(purchaseOrderDTO.getCreatedBy());

		} else {

			String docId = purchaseOrderRepo.getPurchaseOrderDocId(purchaseOrderDTO.getOrgId(),
					purchaseOrderDTO.getFinYear(), purchaseOrderDTO.getBranchCode(), screenCode);
			purchaseOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(purchaseOrderDTO.getOrgId(),
							purchaseOrderDTO.getFinYear(), purchaseOrderDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseOrderVO.setCreatedBy(purchaseOrderDTO.getCreatedBy());
			purchaseOrderVO.setUpdatedBy(purchaseOrderDTO.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdatePurchaseorderVOByPurchaseorderDTO(purchaseOrderDTO, purchaseOrderVO);
		PurchaseOrderVO savedPurchaseOrderVO = purchaseOrderRepo.save(purchaseOrderVO);
		commonNotificationService.generateNotification(savedPurchaseOrderVO.getScreenCode(), savedPurchaseOrderVO.getId(), oldPurchaseOrder, savedPurchaseOrderVO);

		List<PurchaseOrderDetailsVO> purchaseOrderDetailsVOs = savedPurchaseOrderVO.getPurchaseOrderDetailsVO();

		if (purchaseOrderDetailsVOs != null && !purchaseOrderDetailsVOs.isEmpty()) {
			for (PurchaseOrderDetailsVO purchaseOrderDetailsVO : purchaseOrderDetailsVOs) {
				PurchaseDetailsVO purchaseOrderPendingVO = new PurchaseDetailsVO();
				purchaseOrderPendingVO.setDocId(savedPurchaseOrderVO.getDocId());
				purchaseOrderPendingVO.setSupplierName(savedPurchaseOrderVO.getSupplierName());
				purchaseOrderPendingVO.setSupplierCode(savedPurchaseOrderVO.getSupplierCode());
				purchaseOrderPendingVO.setCustomer(savedPurchaseOrderVO.getCustomerName());
				purchaseOrderPendingVO.setPartno(purchaseOrderDetailsVO.getItem());
				purchaseOrderPendingVO.setPartDesc(purchaseOrderDetailsVO.getItemDesc());
				purchaseOrderPendingVO.setQty(purchaseOrderDetailsVO.getQty());
				purchaseOrderPendingVO.setQty(purchaseOrderDetailsVO.getQty());
				purchaseOrderPendingVO.setAmount(purchaseOrderDetailsVO.getAmount());
				purchaseOrderPendingVO.setPlusOrMinus("P");
				purchaseOrderPendingVO.setSourceId(savedPurchaseOrderVO.getId());
				purchaseOrderPendingVO.setFinYear(savedPurchaseOrderVO.getFinYear());
				purchaseOrderPendingVO.setCreatedBy(savedPurchaseOrderVO.getCreatedBy());
				purchaseOrderPendingVO.setUpdatedBy(savedPurchaseOrderVO.getUpdatedBy());
				purchaseOrderPendingVO.setBranch(savedPurchaseOrderVO.getBranch());
				purchaseOrderPendingVO.setBranchCode(savedPurchaseOrderVO.getBranchCode());
				purchaseOrderPendingVO.setOrgId(savedPurchaseOrderVO.getOrgId());
				purchaseDetailsRepo.save(purchaseOrderPendingVO);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("purchaseOrderVO", purchaseOrderVO);
		response.put("message", message);
		return response;

	}

	private void createUpdatePurchaseorderVOByPurchaseorderDTO(@Valid PurchaseOrderDTO purchaseOrderDTO,
			PurchaseOrderVO purchaseOrderVO) throws ApplicationException {
		purchaseOrderVO.setCustomerName(purchaseOrderDTO.getCustomerName());
		purchaseOrderVO.setCustomerCode(purchaseOrderDTO.getCustomerCode());
		purchaseOrderVO.setWorkOrderNo(purchaseOrderDTO.getWorkOrderNo());
		purchaseOrderVO.setBasedOn(purchaseOrderDTO.getBasedOn());
		purchaseOrderVO.setQuotationNo(purchaseOrderDTO.getQuotationNo());
		purchaseOrderVO.setPurchaseIndentNo(purchaseOrderDTO.getPurchaseIndentNo());
		purchaseOrderVO.setSupplierName(purchaseOrderDTO.getSupplierName());
		purchaseOrderVO.setSupplierCode(purchaseOrderDTO.getSupplierCode());
		purchaseOrderVO.setContactPerson(purchaseOrderDTO.getContactPerson());
		purchaseOrderVO.setMobileNo(purchaseOrderDTO.getMobileNo());
		purchaseOrderVO.setEmail(purchaseOrderDTO.getEmail());
		purchaseOrderVO.setCity(purchaseOrderDTO.getCity());
		purchaseOrderVO.setState(purchaseOrderDTO.getState());
		purchaseOrderVO.setCountry(purchaseOrderDTO.getCountry());
		purchaseOrderVO.setTaxCode(purchaseOrderDTO.getTaxCode());
		purchaseOrderVO.setAddress(purchaseOrderDTO.getAddress());
		purchaseOrderVO.setRemarks(purchaseOrderDTO.getRemarks());
		purchaseOrderVO.setOrgId(purchaseOrderDTO.getOrgId());
		purchaseOrderVO.setBranch(purchaseOrderDTO.getBranch());
		purchaseOrderVO.setBranchCode(purchaseOrderDTO.getBranchCode());
		purchaseOrderVO.setFinYear(purchaseOrderDTO.getFinYear());
		purchaseOrderVO.setPoPendingCancel(purchaseOrderDTO.isPoPendingCancel());

		purchaseOrderVO.setCreatedBy(purchaseOrderDTO.getCreatedBy());
		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;
		BigDecimal totalLandedAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(purchaseOrderVO.getId())) {
			List<PurchaseOrderDetailsVO> purchaseOrderDetailsVo1 = purchaseOrderDetailsRepo
					.findByPurchaseOrderVO(purchaseOrderVO);
			purchaseOrderDetailsRepo.deleteAll(purchaseOrderDetailsVo1);
		}

		List<PurchaseOrderDetailsVO> purchaseOrderDetailsVOs = new ArrayList<>();
		for (PurchaseOrderDetailsDTO purchaseOrderDetailsDTO : purchaseOrderDTO.getPurchaseOrderDetailsDTO()) {
			PurchaseOrderDetailsVO purchaseOrderDetailsVO = new PurchaseOrderDetailsVO();
			purchaseOrderDetailsVO.setItem(purchaseOrderDetailsDTO.getItem());
			purchaseOrderDetailsVO.setItemDesc(purchaseOrderDetailsDTO.getItemDesc());
			purchaseOrderDetailsVO.setHsnSacCode(purchaseOrderDetailsDTO.getHsnSacCode());
			purchaseOrderDetailsVO.setTaxType(purchaseOrderDetailsDTO.getTaxType());
			purchaseOrderDetailsVO.setUom(purchaseOrderDetailsDTO.getUom());
			if (purchaseOrderDetailsDTO.getQty() == null
					|| purchaseOrderDetailsDTO.getQty().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Qty must be greater than zero.");
			}

			purchaseOrderDetailsVO.setQty(purchaseOrderDetailsDTO.getQty());

			purchaseOrderDetailsVO.setPrice(purchaseOrderDetailsDTO.getPrice());
			purchaseOrderDetailsVO.setPrevRate(purchaseOrderDetailsDTO.getPrevRate());
			purchaseOrderDetailsVO.setDiscount(purchaseOrderDetailsDTO.getDiscount());

			purchaseOrderDetailsVO.setIgst(purchaseOrderDetailsDTO.getIgst());
			purchaseOrderDetailsVO.setSgst(purchaseOrderDetailsDTO.getSgst());
			purchaseOrderDetailsVO.setHsnSacCode(purchaseOrderDetailsDTO.getHsnSacCode());
			purchaseOrderDetailsVO.setCgst(purchaseOrderDetailsDTO.getCgst());

			BigDecimal taxAmount = BigDecimal.ZERO;
			BigDecimal landedValues = BigDecimal.ZERO;

			BigDecimal amountSet = purchaseOrderDetailsDTO.getPrice().multiply(purchaseOrderDetailsDTO.getQty());
			purchaseOrderDetailsVO.setAmount(amountSet);

			grossAmount = grossAmount.add(purchaseOrderDetailsVO.getAmount());
			BigDecimal discountAmount = purchaseOrderDetailsVO.getAmount()
					.multiply(purchaseOrderDetailsDTO.getDiscount()).divide(BigDecimal.valueOf(100));
			purchaseOrderDetailsVO.setDiscountAmt(discountAmount);
			BigDecimal amountSubtractDiscountAmount = purchaseOrderDetailsVO.getAmount()
					.subtract(purchaseOrderDetailsVO.getDiscountAmt());
			purchaseOrderDetailsVO.setNetAmount(amountSubtractDiscountAmount);
			netAmount = netAmount.add(amountSubtractDiscountAmount);

			BigDecimal sgstamount = purchaseOrderDetailsDTO.getSgst()
					.multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));
			BigDecimal cgstamount = purchaseOrderDetailsDTO.getCgst()
					.multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));
			BigDecimal igstamount = purchaseOrderDetailsDTO.getIgst()
					.multiply(amountSubtractDiscountAmount.divide(BigDecimal.valueOf(100)));

			purchaseOrderDetailsVO.setSgstAmount(sgstamount);
			purchaseOrderDetailsVO.setIgstAmount(igstamount);
			purchaseOrderDetailsVO.setCgstAmount(cgstamount);
			taxAmount = taxAmount.add(cgstamount).add(sgstamount).add(igstamount);
			purchaseOrderDetailsVO.setTaxValue(taxAmount);
			totalTaxAmount = totalTaxAmount.add(purchaseOrderDetailsVO.getTaxValue());

			landedValues = amountSubtractDiscountAmount.add(purchaseOrderDetailsVO.getTaxValue());
			purchaseOrderDetailsVO.setLandedValue(landedValues);
			totalLandedAmount = totalLandedAmount.add(purchaseOrderDetailsVO.getLandedValue());

			purchaseOrderDetailsVO.setPurchaseOrderVO(purchaseOrderVO); // Set the reference in child entity
			purchaseOrderDetailsVOs.add(purchaseOrderDetailsVO);
		}

		purchaseOrderVO.setGrossAmount(grossAmount);
		purchaseOrderVO.setNetAmount(netAmount);
		purchaseOrderVO.setTotalLandedAmount(totalLandedAmount);

		purchaseOrderVO.setTotalAmountTax(totalTaxAmount);
		purchaseOrderVO.setAmtInWords(
				amountInWordsConverterService.convert(purchaseOrderVO.getTotalLandedAmount().longValue()));

		purchaseOrderVO.setPurchaseOrderDetailsVO(purchaseOrderDetailsVOs);

	}

	@Override
	public String getPurchaseOrderDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "PONO";
		String result = purchaseOrderRepo.getPurchaseOrderDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getSupplierAddressForPurchaseOrder(Long orgId, String supplierName) {
		Set<Object[]> chType = purchaseOrderRepo.findgetSupplierAddressForPurchaseOrder(orgId, supplierName);
		return getSupplierAddressForPurchaseOrder(chType);
	}

	private List<Map<String, Object>> getSupplierAddressForPurchaseOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("contactperson", ch[0] != null ? ch[0].toString() : "");
			map.put("contact", ch[1] != null ? ch[1].toString() : "");
			map.put("full_address", ch[2] != null ? ch[2].toString() : "");
			map.put("stategstin", ch[3] != null ? ch[3].toString() : "");
//			map.put("taxcode", ch[4] != null ? ch[4].toString() : "");
			map.put("state", ch[4] != null ? ch[4].toString() : "");
			map.put("pincode", ch[5] != null ? ch[5].toString() : "");
			map.put("city", ch[6] != null ? ch[6].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseIndentForPurchaseOrder(Long orgId, String customerCode,
			String workorderno, String basedOn) {

		LOGGER.info("Fetching PurchaseIndent: orgId={}, customerCode={}, workorderno={}, basedOn={}", orgId,
				customerCode, workorderno, basedOn);

		Set<String> docIds = purchaseOrderRepo.findgetPurchaseIndentForPurchaseOrder(orgId, customerCode, workorderno,
				basedOn);

		List<Map<String, Object>> result = new ArrayList<>();

		for (String docId : docIds) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", docId);
			result.add(map);
		}

		LOGGER.info("PurchaseIndent docIds found: {}", result);
		return result;
	}

	@Override
	public List<Map<String, Object>> getQuotationForPurchaseOrder(Long orgId, String customerCode, String workorderno,
			String basedOn) {
		Set<Object[]> chType = purchaseOrderRepo.findgetQuotationForPurchaseOrder(orgId, customerCode, workorderno,
				basedOn);
		return getQuotationForPurchaseOrder(chType);
	}

	private List<Map<String, Object>> getQuotationForPurchaseOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemForPurchaseOrder(Long orgId, String purchaseIndentNo, String quotationNo) {
		Set<Object[]> chType = purchaseOrderRepo.findgetItemForPurchaseOrder(orgId, purchaseIndentNo, quotationNo);
		return getItemForPurchaseOrder(chType);
	}

	private List<Map<String, Object>> getItemForPurchaseOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("indentqty", ch[2] != null ? ch[2].toString() : "");
			map.put("uom", ch[3] != null ? ch[3].toString() : "");
			map.put("taxslab", ch[4] != null ? ch[4].toString() : "");
			map.put("price", ch[5] != null ? ch[5].toString() : "");

			List1.add(map);
		}
		return List1;
	}

//	@Override
//	public List<Map<String, Object>> getpurchaseindentavlstock(Long orgId, String item) {
//	    Set<Object[]> stockSet = purchaseIndentRepo.findPurchaseIndentAvlStock(orgId, item);
//	    return mapPurchaseIndentAvlStock(stockSet, item);
//	}
//
//	private List<Map<String, Object>> mapPurchaseIndentAvlStock(Set<Object[]> stockSet, String item) {
//
//	    List<Map<String, Object>> resultList = new ArrayList<>();
//
//	    for (Object[] ch : stockSet) {
//
//	        Map<String, Object> map = new HashMap<>();
//
//	        // item name from parameter
//	        map.put("itemName", item);
//
//	        // available stock
//	        map.put("stock",
//	                (ch[0] != null && !ch[0].toString().trim().isEmpty())
//	                        ? Integer.parseInt(ch[0].toString())
//	                        : 0
//	        );
//
//	        resultList.add(map);
//	    }
//
//	    return resultList;
//	}

	@Override
	public List<Map<String, Object>> getpurchaseindentavlstock(Long orgId, String item) {

		Set<Object[]> stockSet = purchaseIndentRepo.findPurchaseIndentAvlStock(orgId, item);
		List<Map<String, Object>> result = new ArrayList<>();

		// ✅ If no stock found → return item with stock 0
		if (stockSet == null || stockSet.isEmpty()) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemName", item); // use request parameter
			map.put("stock", 0);
			result.add(map);
			return result;
		}

		// ✅ Map DB results
		for (Object[] ch : stockSet) {

			Map<String, Object> map = new HashMap<>();

			// item name / code
			map.put("itemName", ch[0] != null ? ch[0].toString() : item);

			// stock quantity (SAFE)
			map.put("stock", ch[1] instanceof Number ? ((Number) ch[1]).intValue() : 0);

			result.add(map);
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> findByIgstAndSgstPercentageForPurchaseQrder(Long orgId, String taxType,
			String taxCode) {
		Set<Object[]> chType = purchaseQuotationRepo.findByIgstAndSgstPercentageForPurchaseQrder(orgId, taxType,
				taxCode);
		return findByIgstAndSgstPercentageForPurchaseQrder(chType);
	}

	private List<Map<String, Object>> findByIgstAndSgstPercentageForPurchaseQrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("igstPercentage", ch[0] != null ? ch[0].toString() : "");
			map.put("cgstPercentage", ch[1] != null ? ch[1].toString() : "");
			map.put("sgstPercentage", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseIndentReport(Long orgId, String branchCode, String customerName,
			String status, String fromDate, String toDate) {
		Set<Object[]> purchaseIndentReport = purchaseIndentRepo.getPurchaseIndentReport(orgId, branchCode, customerName,
				status, fromDate, toDate);
		return getPurchaseIndentReport(purchaseIndentReport);
	}

	private List<Map<String, Object>> getPurchaseIndentReport(Set<Object[]> purchaseIndentReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : purchaseIndentReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("customerCode", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : "");
			map.put("fgPart", ch[5] != null ? ch[5].toString() : "");
			map.put("fgPartDesc", ch[6] != null ? ch[6].toString() : "");
			map.put("item", ch[7] != null ? ch[7].toString() : "");
			map.put("indentqty", ch[8] != null ? ch[8].toString() : "");
			map.put("itemdesc", ch[9] != null ? ch[9].toString() : "");
			map.put("purchaseIndentId", ch[10] != null ? ch[10].toString() : "");
			map.put("purchaseOrderId", ch[11] != null ? ch[11].toString() : "");
			map.put("purchaseEnquiryId", ch[12] != null ? ch[12].toString() : "");
			map.put("status", ch[13] != null ? ch[13].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseEnquiryReport(Long orgId, String branchCode, String supplierName,
			String status, String fromDate, String toDate) {
		Set<Object[]> purchaseEnquiryReport = purchaseEnquiryRepo.getPurchaseEnquiryReport(orgId, branchCode,
				supplierName, status, fromDate, toDate);
		return getPurchaseEnquiryReport(purchaseEnquiryReport);
	}

	private List<Map<String, Object>> getPurchaseEnquiryReport(Set<Object[]> purchaseEnquiryReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : purchaseEnquiryReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("customerCode", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : "");
			map.put("fgPart", ch[5] != null ? ch[5].toString() : "");
			map.put("fgPartDesc", ch[6] != null ? ch[6].toString() : "");
			map.put("supplierName", ch[7] != null ? ch[7].toString() : "");
			map.put("supplierCode", ch[8] != null ? ch[8].toString() : "");
			map.put("workOrderNo", ch[9] != null ? ch[9].toString() : "");
			map.put("contactNo", ch[10] != null ? ch[10].toString() : "");
			map.put("contactPerson", ch[11] != null ? ch[11].toString() : "");
			map.put("item", ch[12] != null ? ch[12].toString() : "");
			map.put("itemDesc", ch[13] != null ? ch[13].toString() : "");
			map.put("qtyRequired", ch[14] != null ? ch[14].toString() : "");
			map.put("purchaseEnquiryId", ch[15] != null ? ch[15].toString() : "");
			map.put("status", ch[16] != null ? ch[16].toString() : "");
			map.put("enquiryDueDate", ch[17] != null ? ch[17].toString() : "");
			map.put("quotationId", ch[18] != null ? ch[18].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseReturnReport(Long orgId, String branchCode, String finYear,
			String customerName, String fromDate, String toDate) {
		Set<Object[]> purchaseReturnReport = purchaseReturnRepo.getPurchaseReturnReport(orgId, branchCode, finYear,
				customerName, fromDate, toDate);
		return getPurchaseReturnReport(purchaseReturnReport);
	}

	private List<Map<String, Object>> getPurchaseReturnReport(Set<Object[]> purchaseReturnReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : purchaseReturnReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("customerCode", ch[3] != null ? ch[3].toString() : "");
			map.put("gatePassNo", ch[4] != null ? ch[4].toString() : "");
			map.put("invDcNo", ch[5] != null ? ch[5].toString() : "");
			map.put("poNo", ch[6] != null ? ch[6].toString() : "");
			map.put("purchaseInvoiceNo", ch[7] != null ? ch[7].toString() : "");
			map.put("supplierName", ch[8] != null ? ch[8].toString() : "");
			map.put("supplierCode", ch[9] != null ? ch[9].toString() : "");
			map.put("toLocation", ch[10] != null ? ch[10].toString() : "");
			map.put("totalAmount", ch[11] != null ? ch[11].toString() : "");
			map.put("itemName", ch[12] != null ? ch[12].toString() : "");
			map.put("itemCode", ch[13] != null ? ch[13].toString() : "");
			map.put("rejectQty", ch[14] != null ? ch[14].toString() : "");
			map.put("orderQty", ch[15] != null ? ch[15].toString() : "");
			map.put("amount", ch[16] != null ? ch[16].toString() : "");
			map.put("landedValue", ch[17] != null ? ch[17].toString() : "");
			map.put("purchaseReturnId", ch[18] != null ? ch[18].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderDetails(Long orgId, String supplierName, String status) {
		Set<Object[]> chType = purchaseOrderRepo.getPurchaseOrderDetails(orgId, supplierName, status);
		return getPurchaseOrderDetails(chType);
	}

	private List<Map<String, Object>> getPurchaseOrderDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : "");
			map.put("docid", ch[1] != null ? ch[1].toString() : "");
			map.put("item", ch[2] != null ? ch[2].toString() : "");
			map.put("orderedqty", ch[3] != null ? ch[3].toString() : "");
			map.put("orgid", ch[4] != null ? ch[4].toString() : "");
			map.put("receivedqty", ch[5] != null ? ch[5].toString() : "");
			map.put("status", ch[6] != null ? ch[6].toString() : "");
			map.put("id", ch[7] != null ? ch[7].toString() : "");
			map.put("supplierName", ch[8] != null ? ch[8].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseQuotationDetailsReport(Long orgId, String branchCode,
			String customerName, String fromDate, String toDate) {
		Set<Object[]> quotationDetailsReport = purchaseQuotationRepo.getPurchaseQuotationDetailsReport(orgId,
				branchCode, customerName, fromDate, toDate);
		return getPurchaseQuotationDetailsReport(quotationDetailsReport);
	}

	private List<Map<String, Object>> getPurchaseQuotationDetailsReport(Set<Object[]> quotationDetailsReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : quotationDetailsReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("iterations", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("sourceDocId", ch[4] != null ? ch[4].toString() : "");
			map.put("sourceDocDate", ch[5] != null ? ch[5].toString() : "");
			map.put("kindAttention", ch[6] != null ? ch[6].toString() : "");
			map.put("sourceId", ch[7] != null ? ch[7].toString() : "");
			map.put("partNo", ch[8] != null ? ch[8].toString() : "");
			map.put("partDesc", ch[9] != null ? ch[9].toString() : "");
			map.put("qty", ch[10] != null ? ch[10].toString() : "");
			map.put("sellingPrice", ch[11] != null ? ch[11].toString() : "");
			map.put("price", ch[12] != null ? ch[12].toString() : "");
			map.put("discount", ch[13] != null ? ch[13].toString() : "");
			map.put("discountAmount", ch[14] != null ? ch[14].toString() : "");
			map.put("amount", ch[15] != null ? ch[15].toString() : "");
			map.put("contactNo", ch[16] != null ? ch[16].toString() : "");
			map.put("count", ch[17] != null ? ch[17].toString() : "");
			map.put("status", ch[18] != null ? ch[18].toString() : "");
			map.put("supplierName", ch[19] != null ? ch[19].toString() : "");
			map.put("supplierCode", ch[20] != null ? ch[20].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseInvoiceDetails(Long orgId, String supplierName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = purchaseInvoiceRepo.getPurchaseInvoiceDetails(orgId, supplierName, fromDate, toDate,
				branchCode);
		return getPurchaseInvoiceDetails(chType);
	}

	private List<Map<String, Object>> getPurchaseInvoiceDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docDate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("grnNo", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("invDcNo", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("inwardNo", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("gstNo", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("location", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("poNo", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("supplierName", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("supplierCode", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("itemCode", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("itemName", ch[11] != null ? ch[11].toString() : ""); // 11
			map.put("primaryUnit", ch[12] != null ? ch[12].toString() : ""); // 12
			map.put("poRate", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13
			map.put("acceptQty", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 14
			map.put("amount", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 15
			map.put("igst", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 16
			map.put("taxValue", ch[17] != null ? new BigDecimal(ch[17].toString()) : BigDecimal.ZERO); // 17
			map.put("totalAmount", ch[18] != null ? new BigDecimal(ch[18].toString()) : BigDecimal.ZERO); // 18

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseInvoiceSummaryDetails(Long orgId, String supplierName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = purchaseInvoiceRepo.getPurchaseInvoiceSummaryDetails(orgId, supplierName, fromDate,
				toDate, branchCode);
		return getPurchaseInvoiceSummaryDetails(chType);
	}

	private List<Map<String, Object>> getPurchaseInvoiceSummaryDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docDate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("grnNo", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("grnDate", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("invDcNo", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("inwardNo", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("gstNo", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("location", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("poNo", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("supplierName", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("supplierCode", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("gstType", ch[11] != null ? ch[11].toString() : ""); // 11
			map.put("grossAmount", ch[12] != null ? new BigDecimal(ch[12].toString()) : BigDecimal.ZERO); // 12
			map.put("totalAmountTax", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13
			map.put("netAmount", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 14
			map.put("igst", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 15
			map.put("cgst", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 16
			map.put("sgst", ch[17] != null ? new BigDecimal(ch[17].toString()) : BigDecimal.ZERO); // 17

			List1.add(map);
		}
		return List1;
	}

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdatePurchaseImages(MultipartFile[] files, String docId, String screenName,
			String module, List<String> fileNames) throws ApplicationException, IOException {

		PurchaseQuotationVO inspection = purchaseQuotationRepo.findByDocId(docId);

		inspection = purchaseQuotationRepo.save(inspection);

		// Create folder
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// Delete old DB attachments
		List<PurchaseQuotationImagesVO> oldDocs = purchaseQuotationImagesRepo.findByPurchaseQuotationVO(inspection);
		purchaseQuotationImagesRepo.deleteAll(oldDocs);

		if (inspection.getDocuments() != null) {
			inspection.getDocuments().clear();
		} else {
			inspection.setDocuments(new ArrayList<>());
		}

		// Delete old physical files
		for (PurchaseQuotationImagesVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// Save new files
		replaceDocuments(inspection, files, docFolder, docId, fileNames);

		Map<String, Object> response = new HashMap<>();
		response.put("thirdPartyInspectionVO", inspection);

		return response;
	}

	private void replaceDocuments(PurchaseQuotationVO inspection, MultipartFile[] files, Path docFolder, String docId,
			List<String> fileNames) throws IOException {

		if (files == null || files.length == 0) {
			return;
		}

		saveFiles(inspection, files, docFolder, docId, fileNames);
	}

	private void saveFiles(PurchaseQuotationVO inspection, MultipartFile[] files, Path docFolder, String docId,
			List<String> fileNames) throws IOException {

		try {
			createDirectory(docFolder);

			for (int i = 0; i < files.length; i++) {

				MultipartFile file = files[i];

				String currentFileName = null;
				if (fileNames != null && fileNames.size() > i) {
					currentFileName = fileNames.get(i);
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "file";
				}

				String extension = "";
				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + docId + extension;

				Path filePath = docFolder.resolve(fileName);

				try (InputStream is = file.getInputStream()) {
					Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/purchase/viewPurchaseQuotationImages/").toUriString();

				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				PurchaseQuotationImagesVO attach = new PurchaseQuotationImagesVO();
				attach.setPurchaseQuotationVO(inspection);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileype(file.getContentType());
				attach.setFilesize(file.getSize());
				attach.setFileNames(currentFileName);
				attach.setUploadOn(LocalDateTime.now());

				inspection.getDocuments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafely(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectory(Path path) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewPurchaseQuotationImages(HttpServletRequest request) throws IOException {
		return serveFile(request, "/api/purchase/viewPurchaseQuotationImages/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException {

		String uri = request.getRequestURI();

		String relativePath = uri.replace(apiPrefix, "");

		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
		Path filePath = baseDir.resolve(relativePath).normalize();

		if (!filePath.startsWith(baseDir)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		if (!Files.exists(filePath)) {
			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

}
