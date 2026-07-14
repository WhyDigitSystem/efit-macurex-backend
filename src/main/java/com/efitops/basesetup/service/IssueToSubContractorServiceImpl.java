package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.dto.DcForSubContractDTO;
import com.efitops.basesetup.dto.DcForSubContractDetailsDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDetailsDTO;
import com.efitops.basesetup.dto.JobWorkOutDTO;
import com.efitops.basesetup.dto.JobWorkOutDetailsDTO;
import com.efitops.basesetup.dto.RecieveFromSubContractDetailsDTO;
import com.efitops.basesetup.dto.RecieveFromSubcontractDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDetailsDTO;
import com.efitops.basesetup.dto.SubContractInvoiceDTO;
import com.efitops.basesetup.dto.SubContractInvoiceDetailsDTO;
import com.efitops.basesetup.dto.SubContractInvoiceTermsDTO;
import com.efitops.basesetup.dto.SubContractQuotationDTO;
import com.efitops.basesetup.dto.SubContractQuotationDetailsDTO;
import com.efitops.basesetup.entity.DcForSubContractDetailsVO;
import com.efitops.basesetup.entity.DcForSubContractVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.IssueToSubContractorDetailsVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.entity.JobWorkOutDetailsVO;
import com.efitops.basesetup.entity.JobWorkOutVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.entity.QuoteRevisionVO;
import com.efitops.basesetup.entity.RecieveFromSubContractDetailsVO;
import com.efitops.basesetup.entity.RecieveFromSubcontractVO;
import com.efitops.basesetup.entity.SubContractEnquiryDetailsVO;
import com.efitops.basesetup.entity.SubContractEnquiryVO;
import com.efitops.basesetup.entity.SubContractInvoiceDetailsVO;
import com.efitops.basesetup.entity.SubContractInvoiceTermsVO;
import com.efitops.basesetup.entity.SubContractInvoiceVO;
import com.efitops.basesetup.entity.SubContractQuotationDetailsVO;
import com.efitops.basesetup.entity.SubContractQuotationVO;
import com.efitops.basesetup.entity.SubContractorQuotationAttachmentVO;
import com.efitops.basesetup.entity.ThirdPartyAttachmentsVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DcForSubContractDetailsRepo;
import com.efitops.basesetup.repo.DcForSubContractRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.IssueToSubContractorDetailsRepo;
import com.efitops.basesetup.repo.IssueToSubContractorRepo;
import com.efitops.basesetup.repo.JobWorkOutDetailsRepo;
import com.efitops.basesetup.repo.JobWorkOutRepo;
import com.efitops.basesetup.repo.QuoteRevisionRepo;
import com.efitops.basesetup.repo.RecieveFromSubcontractDetailsRepo;
import com.efitops.basesetup.repo.RecieveFromSubcontractRepo;
import com.efitops.basesetup.repo.SubContractEnquiryDetailsRepo;
import com.efitops.basesetup.repo.SubContractEnquiryRepo;
import com.efitops.basesetup.repo.SubContractInvoiceDetailsRepo;
import com.efitops.basesetup.repo.SubContractInvoiceRepo;
import com.efitops.basesetup.repo.SubContractInvoiceTermsRepo;
import com.efitops.basesetup.repo.SubContractQuotationDetailsRepo;
import com.efitops.basesetup.repo.SubContractQuotationRepo;

@Service
public class IssueToSubContractorServiceImpl implements IssueToSubContractorService {

	public static final Logger LOGGER = LoggerFactory.getLogger(IssueToSubContractorServiceImpl.class);

	@Autowired
	IssueToSubContractorRepo issueToSubContractorRepo;

	@Autowired
	IssueToSubContractorDetailsRepo issueToSubContractorDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	DcForSubContractRepo dcForSubContractRepo;

	@Autowired
	DcForSubContractDetailsRepo dcForSubContractDetailsRepo;

	@Autowired
	SubContractEnquiryRepo subContractEnquiryRepo;

	@Autowired
	SubContractEnquiryDetailsRepo subContractEnquiryDetailsRepo;

	@Autowired
	SubContractQuotationRepo subContractQuotationRepo;

	@Autowired
	SubContractQuotationDetailsRepo subContractQuotationDetailsRepo;

	@Autowired
	SubContractInvoiceRepo subContractInvoiceRepo;

	@Autowired
	SubContractInvoiceDetailsRepo subContractInvoiceDetailsRepo;

	@Autowired
	SubContractInvoiceTermsRepo subContractInvoiceTermsRepo;

	@Autowired
	JobWorkOutRepo jobWorkOutRepo;

	@Autowired
	JobWorkOutDetailsRepo jobWorkOutDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	RecieveFromSubcontractRepo recieveFromSubcontractRepo;

	@Autowired
	RecieveFromSubcontractDetailsRepo recieveFromSubcontractDetailsRepo;

	@Autowired
	QuoteRevisionRepo quoteRevisionRepo;

	@Autowired
	SubContractorQuotationAttachmentRepo subContractorQuotationAttachmentRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;

	// IssueToSubContract
	@Override
	public Map<String, Object> createUpdateIssueToSubContractor(IssueToSubContractorDTO issueToSubContractorDTO)
			throws ApplicationException {
		IssueToSubContractorVO issueToSubContractorVO = new IssueToSubContractorVO();
		String message;
		String screenCode = "ITSC";
		IssueToSubContractorVO oldIssueToSubContractor = null;
		if (ObjectUtils.isNotEmpty(issueToSubContractorDTO.getId(   ))) {
			
			oldIssueToSubContractor = issueToSubContractorRepo.findById(issueToSubContractorDTO.getId())
		            .orElseThrow(() -> new ApplicationException("purchaseReturn not found"));

			oldIssueToSubContractor.getIssueToSubContractorDetailsVO().size(); // load
			
		    entityManager.detach(oldIssueToSubContractor); // detach snapshot
			
			issueToSubContractorVO = issueToSubContractorRepo.findById(issueToSubContractorDTO.getId())
					.orElseThrow(() -> new ApplicationException("IssueToSubContractor Enquiry details"));
			message = "IssueToSubContractor Updated Successfully";
			issueToSubContractorVO.setUpdatedBy(issueToSubContractorDTO.getCreatedBy());

		} else {

			String docId = issueToSubContractorRepo.getIssueToSubContractorDocId(issueToSubContractorDTO.getOrgId(),
					issueToSubContractorDTO.getFinYear(), issueToSubContractorDTO.getBranchCode(), screenCode);
			issueToSubContractorVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(issueToSubContractorDTO.getOrgId(),
							issueToSubContractorDTO.getFinYear(), issueToSubContractorDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			issueToSubContractorVO.setCreatedBy(issueToSubContractorDTO.getCreatedBy());
			issueToSubContractorVO.setUpdatedBy(issueToSubContractorDTO.getCreatedBy());

			message = "IssueToSubContractor Created Successfully";
		}
		createUpdatedIssueToSubContractorVOFromIssueToSubContractorDTO(issueToSubContractorDTO, issueToSubContractorVO);
		issueToSubContractorRepo.save(issueToSubContractorVO);
		commonNotificationService.generateNotification(issueToSubContractorVO.getScreenCode(), issueToSubContractorVO.getId(), oldIssueToSubContractor, issueToSubContractorVO);

		Map<String, Object> response = new HashMap<>();
		response.put("issueToSubContractorVO", issueToSubContractorVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedIssueToSubContractorVOFromIssueToSubContractorDTO(
			IssueToSubContractorDTO issueToSubContractorDTO, IssueToSubContractorVO issueToSubContractorVO) {
		issueToSubContractorVO.setRouteCardNo(issueToSubContractorDTO.getRouteCardNo());
		issueToSubContractorVO.setCustomerName(issueToSubContractorDTO.getCustomerName());
		issueToSubContractorVO.setDepartment(issueToSubContractorDTO.getDepartment());
		issueToSubContractorVO.setStatus(issueToSubContractorDTO.getStatus());
		issueToSubContractorVO.setOrgId(issueToSubContractorDTO.getOrgId());
		issueToSubContractorVO.setNarration(issueToSubContractorDTO.getNarration());
		issueToSubContractorVO.setActive(issueToSubContractorDTO.isActive());
		issueToSubContractorVO.setCreatedBy(issueToSubContractorDTO.getCreatedBy());
		issueToSubContractorVO.setBranch(issueToSubContractorDTO.getBranch());
		issueToSubContractorVO.setBranchCode(issueToSubContractorDTO.getBranchCode());
		issueToSubContractorVO.setFinYear(issueToSubContractorDTO.getFinYear());

		if (ObjectUtils.isNotEmpty(issueToSubContractorDTO.getId())) {
			List<IssueToSubContractorDetailsVO> issueToSubContractorDetailsVO1 = issueToSubContractorDetailsRepo
					.findByIssueToSubContractorVO(issueToSubContractorVO);
			issueToSubContractorDetailsRepo.deleteAll(issueToSubContractorDetailsVO1);

		}

		List<IssueToSubContractorDetailsVO> issueToSubContractorDetailsVOs = new ArrayList<>();
		for (IssueToSubContractorDetailsDTO issueToSubContractorDetailsDTO : issueToSubContractorDTO
				.getIssueToSubContractorDetailsDTO()) {
			IssueToSubContractorDetailsVO issueToSubContractorDetailsVO = new IssueToSubContractorDetailsVO();
			issueToSubContractorDetailsVO.setItem(issueToSubContractorDetailsDTO.getItem());
			issueToSubContractorDetailsVO.setItemDescription(issueToSubContractorDetailsDTO.getItemDescription());

			if (issueToSubContractorDetailsDTO.getProcess() != null
					&& !issueToSubContractorDetailsDTO.getProcess().isEmpty()) {
				String process = String.join(",", issueToSubContractorDetailsDTO.getProcess());
				issueToSubContractorDetailsVO.setProcess(process);
			} else {
				issueToSubContractorDetailsVO.setProcess(null);
			}

			issueToSubContractorDetailsVO.setQuantity(issueToSubContractorDetailsDTO.getQuantity());
			issueToSubContractorDetailsVO.setRemarks(issueToSubContractorDetailsDTO.getRemarks());

			issueToSubContractorDetailsVO.setIssueToSubContractorVO(issueToSubContractorVO);
			issueToSubContractorDetailsVOs.add(issueToSubContractorDetailsVO);
		}
		issueToSubContractorVO.setIssueToSubContractorDetailsVO(issueToSubContractorDetailsVOs);

	}

	@Override
	public List<IssueToSubContractorVO> getAllIssueToSubContractorByOrgId(Long orgId, String finYear,
			String branchCode) {

		return issueToSubContractorRepo.getAllIssueToSubContractorByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public List<IssueToSubContractorVO> getIssueToSubContractorById(Long id) {

		return issueToSubContractorRepo.getIssueToSubContractorById(id);
	}

	@Override
	public String getIssueToSubContractorDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "ITSC";
		String result = issueToSubContractorRepo.getIssueToSubContractorDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getRouteCardNoAndItemNo(Long orgId) {
		Set<Object[]> chType = issueToSubContractorRepo.getRouteCardNoAndItemNo(orgId);
		return getRouteCardNo(chType);
	}

	private List<Map<String, Object>> getRouteCardNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("customerName", ch[1] != null ? ch[1].toString() : "");
			map.put("item", ch[2] != null ? ch[2].toString() : "");
			map.put("itemDescription", ch[3] != null ? ch[3].toString() : "");
			map.put("quantity", ch[4] != null ? ch[4].toString() : "");
			map.put("woNo", ch[5] != null ? ch[5].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDepartmentName(Long orgId) {
		Set<Object[]> chType = issueToSubContractorRepo.getDepartmentName(orgId);
		return getDepartment(chType);
	}

	private List<Map<String, Object>> getDepartment(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("departmentName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getProcessNameFormItemWiseProcess(Long orgId, String item) {
		Set<Object[]> chType = issueToSubContractorRepo.getProcessNameFormItemWiseProcess(orgId, item);
		return getProcessName(chType);
	}

	private List<Map<String, Object>> getProcessName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("processName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// DcForSubContractor

	@Override
	public List<DcForSubContractVO> getDcforSCByOrgId(Long orgId, String finYear, String branchCode) {

		return dcForSubContractRepo.findDcforSCByOrgId(orgId, finYear, branchCode);

	}

	@Override
	public List<DcForSubContractVO> getDcforSCById(Long id) {
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			dcForSubContractVO = dcForSubContractRepo.getDcforSCById(id);
		}
		return dcForSubContractVO;
	}

	@Override
	public List<Map<String, Object>> getIssueSCNoForDcForSubContracto(Long orgId) {
		Set<Object[]> issuescno = dcForSubContractRepo.findIssueSCNoDetails(orgId);
		return getIssueSCNoForDcForSubContracto(issuescno);
	}

	private List<Map<String, Object>> getIssueSCNoForDcForSubContracto(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("routeCardNo", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("gstin", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getAddressForDcForSubContract(Long orgId, String customerName) {
		Set<Object[]> chType = dcForSubContractRepo.findAddressDetails(orgId, customerName);
		return findAddress(chType);
	}

	private List<Map<String, Object>> findAddress(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("address", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public Map<String, Object> updateCreateDcForSubContract(DcForSubContractDTO dcForSubContractDTO)
			throws ApplicationException {
		DcForSubContractVO dcForSubContractVO = new DcForSubContractVO();
		String message;
		String screenCode = "DCSC";
		if (ObjectUtils.isNotEmpty(dcForSubContractDTO.getId())) {
			dcForSubContractVO = dcForSubContractRepo.findById(dcForSubContractDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid DcForSubContract details"));
			message = "DcForSubContract Updated Successfully";
			dcForSubContractVO.setUpdatedBy(dcForSubContractDTO.getCreatedBy());

		} else {

			String docId = dcForSubContractRepo.getdcForSubcontractDocId(dcForSubContractDTO.getOrgId(),
					dcForSubContractDTO.getFinYear(), dcForSubContractDTO.getBranchCode(), screenCode);
			dcForSubContractVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(dcForSubContractDTO.getOrgId(),
							dcForSubContractDTO.getFinYear(), dcForSubContractDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			dcForSubContractVO.setCreatedBy(dcForSubContractDTO.getCreatedBy());
			dcForSubContractVO.setUpdatedBy(dcForSubContractDTO.getCreatedBy());

			message = "DcForSubContract Created Successfully";
		}
		createUpdateDcForSubContractVOByDcForSubContractDTO(dcForSubContractDTO, dcForSubContractVO);
		dcForSubContractRepo.save(dcForSubContractVO);
		Map<String, Object> response = new HashMap<>();
		response.put("dcForSubContractVO", dcForSubContractVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateDcForSubContractVOByDcForSubContractDTO(@Valid DcForSubContractDTO dcForSubContractDTO,
			DcForSubContractVO dcForSubContractVO) throws ApplicationException {
		dcForSubContractVO.setScIssueNo(dcForSubContractDTO.getScIssueNo());
		dcForSubContractVO.setCustomerName(dcForSubContractDTO.getCustomerName());
		dcForSubContractVO.setRouteCardNo(dcForSubContractDTO.getRouteCardNo());
		dcForSubContractVO.setNarration(dcForSubContractDTO.getNarration());
		dcForSubContractVO.setCustomerAddress(dcForSubContractDTO.getCustomerAddress());
		dcForSubContractVO.setGstNo(dcForSubContractDTO.getGstNo());
		dcForSubContractVO.setSubContractorName(dcForSubContractDTO.getSubContractorName());
		dcForSubContractVO.setSubContractorId(dcForSubContractDTO.getSubContractorId());
		dcForSubContractVO.setSubContractoraddress(dcForSubContractDTO.getSubContractoraddress());
		dcForSubContractVO.setVehicleNo(dcForSubContractDTO.getVehicleNo());
		dcForSubContractVO.setDuedate(dcForSubContractDTO.getDuedate());
		dcForSubContractVO.setDispatchThrough(dcForSubContractDTO.getDispatchThrough());
		dcForSubContractVO.setEwayBillNo(dcForSubContractDTO.getEwayBillNo());
		dcForSubContractVO.setActive(dcForSubContractDTO.isActive());
		dcForSubContractVO.setCreatedBy(dcForSubContractDTO.getCreatedBy());
		dcForSubContractVO.setOrgId(dcForSubContractDTO.getOrgId());
		dcForSubContractVO.setBranch(dcForSubContractDTO.getBranch());
		dcForSubContractVO.setBranchCode(dcForSubContractDTO.getBranchCode());
		dcForSubContractVO.setFinYear(dcForSubContractDTO.getFinYear());

		if (ObjectUtils.isNotEmpty(dcForSubContractVO.getId())) {
			List<DcForSubContractDetailsVO> dcForSubContractDetails1 = dcForSubContractDetailsRepo
					.findByDcForSubContractVO(dcForSubContractVO);
			dcForSubContractDetailsRepo.deleteAll(dcForSubContractDetails1);
		}

		List<DcForSubContractDetailsVO> dcForSubContractDetailsVOs = new ArrayList<>();
		for (DcForSubContractDetailsDTO dcForSubContractDetailsDTO : dcForSubContractDTO
				.getDcForSubContractDetailsDTO()) {
			DcForSubContractDetailsVO dcForSubContractDetailsVO = new DcForSubContractDetailsVO();
			dcForSubContractDetailsVO.setItem(dcForSubContractDetailsDTO.getItem());
			dcForSubContractDetailsVO.setItemDesc(dcForSubContractDetailsDTO.getItemDesc());
			dcForSubContractDetailsVO.setProcess(dcForSubContractDetailsDTO.getProcess());
			dcForSubContractDetailsVO.setUnit(dcForSubContractDetailsDTO.getUnit());
			if (dcForSubContractDetailsDTO.getQty() == null
					|| dcForSubContractDetailsDTO.getQty().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Qty must be greater than zero.");
			}

			dcForSubContractDetailsVO.setQty(dcForSubContractDetailsDTO.getQty());

			if (dcForSubContractDetailsDTO.getWeight() == null
					|| dcForSubContractDetailsDTO.getWeight().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Weight must be greater than zero.");
			}

			dcForSubContractDetailsVO.setWeight(dcForSubContractDetailsDTO.getWeight());
			dcForSubContractDetailsVO.setRemarks(dcForSubContractDetailsDTO.getRemarks());
			dcForSubContractDetailsVO.setDcForSubContractVO(dcForSubContractVO); // Set the reference in child entity
			dcForSubContractDetailsVOs.add(dcForSubContractDetailsVO);
		}
		dcForSubContractVO.setDcForSubContractDetailsVO(dcForSubContractDetailsVOs);
	}

	@Override
	public String getDcForSubContractDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "DCSC";
		String result = dcForSubContractRepo.getdcForSubcontractDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getSubContractorName(Long orgId) {
		Set<Object[]> chType = dcForSubContractRepo.getSubContractorName(orgId);
		return getSubContract(chType);
	}

	public List<Map<String, Object>> getSubContract(Set<Object[]> chType) {
		List<Map<String, Object>> list1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("subcontratcorName", ch[0] != null ? ch[0].toString() : "");
			map.put("constractorId", ch[1] != null ? ch[1].toString() : "");
			map.put("address", ch[2] != null ? ch[2].toString() : "");
			list1.add(map);
		}

		return list1;
	}

	@Override
	public List<Map<String, Object>> getItenNameAndDescFromIssue(Long orgId, String scIssueNo) {
		Set<Object[]> chType = dcForSubContractRepo.getItenNameAndDescFromIssue(orgId, scIssueNo);
		return getItenNameAnd(chType);
	}

	public List<Map<String, Object>> getItenNameAnd(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("item", ch[0] != null ? ch[0].toString() : "");
			map.put("itemdescription", ch[1] != null ? ch[1].toString() : "");
			map.put("process", ch[2] != null ? ch[2].toString() : "");
			map.put("quantity", ch[3] != null ? ch[3].toString() : "");
			map.put("primaryunit", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// SubContractorEnquiry

	@Override
	public Map<String, Object> createUpdateSubContractEnquiry(SubContractEnquiryDTO subContractEnquiryDTO)
			throws ApplicationException {
		SubContractEnquiryVO subContractEnquiryVO = new SubContractEnquiryVO();
		String message;
		String screenCode = "SUB";
		if (ObjectUtils.isNotEmpty(subContractEnquiryDTO.getId())) {
			subContractEnquiryVO = subContractEnquiryRepo.findById(subContractEnquiryDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractEnquiry Enquiry details"));
			message = "SubContractEnquiry Updated Successfully";
			subContractEnquiryVO.setUpdatedBy(subContractEnquiryDTO.getCreatedBy());

		} else {

			String docId = subContractEnquiryRepo.getSubContractEnquiryDocId(subContractEnquiryDTO.getOrgId(),
					subContractEnquiryDTO.getFinYear(), subContractEnquiryDTO.getBranchCode(), screenCode);
			subContractEnquiryVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(subContractEnquiryDTO.getOrgId(),
							subContractEnquiryDTO.getFinYear(), subContractEnquiryDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			subContractEnquiryVO.setCreatedBy(subContractEnquiryDTO.getCreatedBy());
			subContractEnquiryVO.setUpdatedBy(subContractEnquiryDTO.getCreatedBy());

			message = "SubContractEnquiry Created Successfully";
		}
		createUpdatedSubContractEnquiryVOFromSubContractEnquiryDTO(subContractEnquiryDTO, subContractEnquiryVO);
		subContractEnquiryRepo.save(subContractEnquiryVO);
		Map<String, Object> response = new HashMap<>();
		response.put("subContractEnquiryVO", subContractEnquiryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSubContractEnquiryVOFromSubContractEnquiryDTO(SubContractEnquiryDTO subContractEnquiryDTO,
			SubContractEnquiryVO subContractEnquiryVO) {
		subContractEnquiryVO.setEnquiryType(subContractEnquiryDTO.getEnquiryType());
		subContractEnquiryVO.setSubContractorName(subContractEnquiryDTO.getSubContractorName());
		subContractEnquiryVO.setSubContractorRefNo(subContractEnquiryDTO.getSubContractorRefNo());
		subContractEnquiryVO.setSubContractorRefDate(subContractEnquiryDTO.getSubContractorRefDate());
		subContractEnquiryVO.setRouteCardNo(subContractEnquiryDTO.getRouteCardNo());
		subContractEnquiryVO.setEnquiryDueDate(subContractEnquiryDTO.getEnquiryDueDate());
		subContractEnquiryVO.setRouteCardNo(subContractEnquiryDTO.getRouteCardNo());
		subContractEnquiryVO.setContactNo(subContractEnquiryDTO.getContactNo());
		subContractEnquiryVO.setContactName(subContractEnquiryDTO.getContactName());
		subContractEnquiryVO.setScIssueNo(subContractEnquiryDTO.getScIssueNo());
		subContractEnquiryVO.setNarration(subContractEnquiryDTO.getNarration());
		subContractEnquiryVO.setOrgId(subContractEnquiryDTO.getOrgId());
		subContractEnquiryVO.setActive(subContractEnquiryDTO.isActive());
		subContractEnquiryVO.setCreatedBy(subContractEnquiryDTO.getCreatedBy());
		subContractEnquiryVO.setFinYear(subContractEnquiryDTO.getFinYear());
		subContractEnquiryVO.setBranch(subContractEnquiryDTO.getBranch());
		subContractEnquiryVO.setBranchCode(subContractEnquiryDTO.getBranchCode());

		if (ObjectUtils.isNotEmpty(subContractEnquiryDTO.getId())) {
			List<SubContractEnquiryDetailsVO> subContractEnquiryDetailsVO1 = subContractEnquiryDetailsRepo
					.findBySubContractEnquiryVO(subContractEnquiryVO);
			subContractEnquiryDetailsRepo.deleteAll(subContractEnquiryDetailsVO1);

		}

		List<SubContractEnquiryDetailsVO> subContractEnquiryDetailsVOs = new ArrayList<>();
		for (SubContractEnquiryDetailsDTO subContractEnquiryDetailsDTO : subContractEnquiryDTO
				.getSubContractEnquiryDetailsDTO()) {
			SubContractEnquiryDetailsVO subContractEnquiryDetailsVO = new SubContractEnquiryDetailsVO();
			subContractEnquiryDetailsVO.setPart(subContractEnquiryDetailsDTO.getPart());
			subContractEnquiryDetailsVO.setPartDescription(subContractEnquiryDetailsDTO.getPartDescription());
			subContractEnquiryDetailsVO.setProcess(subContractEnquiryDetailsDTO.getProcess());
			subContractEnquiryDetailsVO.setQty(subContractEnquiryDetailsDTO.getQty());
			subContractEnquiryDetailsVO.setDeliveryDate(subContractEnquiryDetailsDTO.getDeliveryDate());
			subContractEnquiryDetailsVO.setRemarks(subContractEnquiryDetailsDTO.getRemarks());

			subContractEnquiryDetailsVO.setSubContractEnquiryVO(subContractEnquiryVO);
			subContractEnquiryDetailsVOs.add(subContractEnquiryDetailsVO);
		}
		subContractEnquiryVO.setSubContractEnquiryDetailsVO(subContractEnquiryDetailsVOs);

	}

	@Override
	public List<SubContractEnquiryVO> getAllSubContractEnquiryByOrgId(Long orgId, String finYear, String branchCode) {

		return subContractEnquiryRepo.getAllSubContractEnquiryByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public List<SubContractEnquiryVO> getSubContractEnquiryById(Long id) {

		return subContractEnquiryRepo.getSubContractEnquiryById(id);
	}

	@Override
	public String getSubContractEnquiryDocId(Long orgId, String finYear, String branchCode) {

		String ScreenCode = "SUB";
		String result = subContractEnquiryRepo.getSubContractEnquiryDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getSubContractCustomerNameAndCode(Long orgId) {
		Set<Object[]> chType = subContractEnquiryRepo.getSubContractCustomerNameAndCode(orgId);
		return getSubCustomerName(chType);
	}

	private List<Map<String, Object>> getSubCustomerName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("subContractName", ch[0] != null ? ch[0].toString() : "");
			map.put("subContractRefNo", ch[1] != null ? ch[1].toString() : "");
			map.put("taxCode", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractContactNameAndNo(Long orgId, String subContractorName) {
		Set<Object[]> chType = subContractEnquiryRepo.getSubContractContactNameAndNo(orgId, subContractorName);
		return getSubContractContactName(chType);
	}

	private List<Map<String, Object>> getSubContractContactName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("contractorName", ch[0] != null ? ch[0].toString() : "");
			map.put("contractorNo", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractPartNoAndDescription(Long orgId, String scIssueNo) {
		Set<Object[]> chType = subContractEnquiryRepo.getSubContractPartNoAndDescription(orgId, scIssueNo);
		return getSubContractPartNoAndDes(chType);
	}

	private List<Map<String, Object>> getSubContractPartNoAndDes(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("process", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubRouteCardNo(Long orgId) {
		Set<Object[]> chType = subContractEnquiryRepo.getSubRouteCardNo(orgId);
		return getSubRouteCard(chType);
	}

	private List<Map<String, Object>> getSubRouteCard(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getScIssueNoFormSubContract(Long orgId, String routeCardNo) {
		Set<Object[]> chType = subContractEnquiryRepo.getScIssueNoFormSubContract(orgId, routeCardNo);
		return getScIssueNo(chType);
	}

	private List<Map<String, Object>> getScIssueNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("scIssueNo", ch[0] != null ? ch[0].toString() : "");
			map.put("docdate", ch[1] != null ? ch[1].toString() : "");
			map.put("department", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	// SubContractQuotation

	@Override
	public Map<String, Object> createUpdateSubContractQuotation(SubContractQuotationDTO subContractQuotationDTO)
			throws ApplicationException {
		SubContractQuotationVO subContractQuotationVO = new SubContractQuotationVO();
		String message;
		String screenCode = "SCQ";
		if (ObjectUtils.isNotEmpty(subContractQuotationDTO.getId())) {
			subContractQuotationVO = subContractQuotationRepo.findById(subContractQuotationDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractQuotation Enquiry details"));
			message = "SubContractQuotation Updated Successfully";
			subContractQuotationVO.setUpdatedBy(subContractQuotationDTO.getCreatedBy());

			String iteration = subContractQuotationRepo.getSubContractEnquiryNameId(subContractQuotationDTO.getOrgId(),
					subContractQuotationDTO.getSubContractorName(), subContractQuotationDTO.getId());

			createUpdatedSubContractQuotationVOFromSubContractQuotationDTO(subContractQuotationDTO,
					subContractQuotationVO);

			System.out.println("Original iteration: " + iteration);

			Pattern pattern = Pattern.compile("([A-Z0-9]+)(\\d+)-SCQ(\\d+)");
			Matcher matcher = pattern.matcher(iteration);

			if (matcher.matches()) {
				String prefix = matcher.group(1);
				String numberStr = matcher.group(2);
				String qotStr = matcher.group(3);

				System.out.println("Prefix: " + prefix);
				System.out.println("Doc Number: " + numberStr);
				System.out.println("QOT Version: " + qotStr);

				// Increment the QOT version
				int qotNumber = Integer.parseInt(qotStr);
				qotNumber++;

				String iterationValue = prefix + numberStr + "-SCQ" + qotNumber;
				System.out.println("Final Iteration Value: " + iterationValue);

				subContractQuotationVO.setIterations(iterationValue);

				// Update count
				int count = subContractQuotationRepo.getCount(subContractQuotationDTO.getOrgId(),
						subContractQuotationDTO.getSubContractorName(), subContractQuotationDTO.getId());
				count++;
				subContractQuotationVO.setCount(count);

				List<SubContractQuotationDetailsDTO> quotationDetailsVO1 = subContractQuotationDTO
						.getSubContractQuotationDetailsDTO();
				if (quotationDetailsVO1 != null && !quotationDetailsVO1.isEmpty()) {
					for (SubContractQuotationDetailsDTO detailsVO : quotationDetailsVO1) {
						QuoteRevisionVO stockDetailsVOFrom = new QuoteRevisionVO();
						stockDetailsVOFrom.setOrgId(subContractQuotationVO.getOrgId());
						stockDetailsVOFrom.setDocId(subContractQuotationVO.getDocId());
						stockDetailsVOFrom.setDocDate(subContractQuotationVO.getDocDate());
						stockDetailsVOFrom.setSourceId(subContractQuotationVO.getId());
						stockDetailsVOFrom.setSourceDocId(subContractQuotationVO.getEnquiryNo());
						stockDetailsVOFrom.setSourceDocDate(subContractQuotationVO.getEnquiryDate());
						stockDetailsVOFrom.setCustomerName(subContractQuotationVO.getSubContractorName());
						stockDetailsVOFrom.setCreatedBy(subContractQuotationVO.getCreatedBy());
						stockDetailsVOFrom.setStatus(subContractQuotationVO.getStatus());
						stockDetailsVOFrom.setCustomerCode(subContractQuotationVO.getSubContractorId());
//							stockDetailsVOFrom.setActive(true);
						stockDetailsVOFrom.setBranch(subContractQuotationVO.getBranch());
						stockDetailsVOFrom.setStatus(subContractQuotationVO.getStatus());
						stockDetailsVOFrom.setBranchCode(subContractQuotationVO.getBranchCode());
						stockDetailsVOFrom.setFinYear(subContractQuotationVO.getFinYear());
						stockDetailsVOFrom.setContactNo(subContractQuotationVO.getContactNo());
						stockDetailsVOFrom.setContactName(subContractQuotationVO.getContactPerson());
						stockDetailsVOFrom.setGstNo(subContractQuotationVO.getGstIn());
						stockDetailsVOFrom.setUpdatedBy(subContractQuotationVO.getUpdatedBy());
						stockDetailsVOFrom.setIterations(subContractQuotationVO.getIterations());
						stockDetailsVOFrom.setCount(subContractQuotationVO.getCount());
						stockDetailsVOFrom.setSourceScreenCode(subContractQuotationVO.getScreenCode());
						stockDetailsVOFrom.setSourceScreenName(subContractQuotationVO.getScreenName());
						stockDetailsVOFrom.setGrossAmount(subContractQuotationVO.getGrossAmount());
						stockDetailsVOFrom.setDiscount(detailsVO.getDiscount());
						stockDetailsVOFrom.setNetAmount(subContractQuotationVO.getNetAmount());

						stockDetailsVOFrom.setPartNo(detailsVO.getPart());
						stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescription());
						stockDetailsVOFrom.setSellingPrice(detailsVO.getRate());
						stockDetailsVOFrom.setQty(detailsVO.getQty());
						stockDetailsVOFrom.setPrice(detailsVO.getRate().multiply(detailsVO.getQty()));

						BigDecimal discountAmount = detailsVO.getDiscount()
								.multiply(detailsVO.getRate().multiply(detailsVO.getQty()))
								.divide(BigDecimal.valueOf(100));
						stockDetailsVOFrom.setDiscountAmount(discountAmount);
						stockDetailsVOFrom
								.setAmount((detailsVO.getRate().multiply(detailsVO.getQty()).subtract(discountAmount)));

						quoteRevisionRepo.save(stockDetailsVOFrom);
					}

				}
			} else {
				throw new IllegalArgumentException("Invalid iteration format: " + iteration);
			}
			message = "SubContractQuotation Updated Successfully";

		} else {

			String docId = subContractQuotationRepo.getSubContractQuotationDocId(subContractQuotationDTO.getOrgId(),
					subContractQuotationDTO.getFinYear(), subContractQuotationDTO.getBranchCode(), screenCode);
			subContractQuotationVO.setDocId(docId);
			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(subContractQuotationDTO.getOrgId(),
							subContractQuotationDTO.getFinYear(), subContractQuotationDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			String iteration = subContractQuotationRepo.getSubContractEnquiryIdIteration(
					subContractQuotationDTO.getOrgId(), subContractQuotationDTO.getSubContractorName(),
					subContractQuotationDTO.getEnquiryNo());

			System.out.println(iteration);
//               int number = 1;
			subContractQuotationVO.setIterations(iteration);
			subContractQuotationVO.setCount(1);
			subContractQuotationVO.setCreatedBy(subContractQuotationDTO.getCreatedBy());
			subContractQuotationVO.setUpdatedBy(subContractQuotationDTO.getCreatedBy());

			message = "SubContractQuotation Created Successfully";
			createUpdatedSubContractQuotationVOFromSubContractQuotationDTO(subContractQuotationDTO,
					subContractQuotationVO);
			SubContractQuotationVO savedsubContractQuotationVO = subContractQuotationRepo.save(subContractQuotationVO);
			List<SubContractQuotationDetailsVO> quotationDetailsVO1 = savedsubContractQuotationVO
					.getSubContractQuotationDetailsVO();
			if (quotationDetailsVO1 != null && !quotationDetailsVO1.isEmpty()) {
				for (SubContractQuotationDetailsVO detailsVO : quotationDetailsVO1) {
					QuoteRevisionVO stockDetailsVOFrom = new QuoteRevisionVO();
					stockDetailsVOFrom.setOrgId(subContractQuotationVO.getOrgId());
					stockDetailsVOFrom.setDocId(subContractQuotationVO.getDocId());
					stockDetailsVOFrom.setDocDate(subContractQuotationVO.getDocDate());
					stockDetailsVOFrom.setSourceId(subContractQuotationVO.getId());
					stockDetailsVOFrom.setCustomerCode(subContractQuotationVO.getSubContractorId());
					stockDetailsVOFrom.setCustomerName(subContractQuotationVO.getSubContractorName());
					stockDetailsVOFrom.setCreatedBy(subContractQuotationVO.getCreatedBy());
					stockDetailsVOFrom.setFinYear(subContractQuotationVO.getFinYear());
					stockDetailsVOFrom.setContactNo(subContractQuotationVO.getContactNo());
					stockDetailsVOFrom.setContactName(subContractQuotationVO.getContactPerson());
					stockDetailsVOFrom.setSourceDocId(subContractQuotationVO.getEnquiryNo());
					stockDetailsVOFrom.setCount(subContractQuotationVO.getCount());
					stockDetailsVOFrom.setStatus(subContractQuotationVO.getStatus());
					stockDetailsVOFrom.setBranch(subContractQuotationVO.getBranch());
					stockDetailsVOFrom.setBranchCode(subContractQuotationVO.getBranchCode());
					stockDetailsVOFrom.setSourceDocDate(subContractQuotationVO.getEnquiryDate());
					stockDetailsVOFrom.setIterations(subContractQuotationVO.getIterations());
					stockDetailsVOFrom.setUpdatedBy(subContractQuotationVO.getUpdatedBy());
					stockDetailsVOFrom.setSourceScreenCode(subContractQuotationVO.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(subContractQuotationVO.getScreenName());
					stockDetailsVOFrom.setGrossAmount(subContractQuotationVO.getGrossAmount());
					stockDetailsVOFrom.setDiscount(detailsVO.getDiscount());
					stockDetailsVOFrom.setNetAmount(subContractQuotationVO.getNetAmount());
					stockDetailsVOFrom.setPartNo(detailsVO.getPart());
					stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescription());
					stockDetailsVOFrom.setSellingPrice(detailsVO.getRate());
					stockDetailsVOFrom.setQty(detailsVO.getQty());

					stockDetailsVOFrom.setPrice(detailsVO.getAmount());

					stockDetailsVOFrom.setAmount(detailsVO.getQuotationAmount());
					stockDetailsVOFrom.setDiscountAmount(detailsVO.getDiscountAmount());

					quoteRevisionRepo.save(stockDetailsVOFrom);
				}
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("subContractQuotationVO", subContractQuotationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSubContractQuotationVOFromSubContractQuotationDTO(
			SubContractQuotationDTO subContractQuotationDTO, SubContractQuotationVO subContractQuotationVO)
			throws ApplicationException {

		subContractQuotationVO.setEnquiryNo(subContractQuotationDTO.getEnquiryNo());
		subContractQuotationVO.setEnquiryDate(subContractQuotationDTO.getEnquiryDate());
		subContractQuotationVO.setSubContractorId(subContractQuotationDTO.getSubContractorId());
		subContractQuotationVO.setSubContractorName(subContractQuotationDTO.getSubContractorName());
		subContractQuotationVO.setVaildTill(subContractQuotationDTO.getVaildTill());
		subContractQuotationVO.setGstIn(subContractQuotationDTO.getGstIn());
		subContractQuotationVO.setRouteCardNo(subContractQuotationDTO.getRouteCardNo());
		subContractQuotationVO.setContactPerson(subContractQuotationDTO.getContactPerson());
		subContractQuotationVO.setContactNo(subContractQuotationDTO.getContactNo());
		subContractQuotationVO.setScIssueNo(subContractQuotationDTO.getScIssueNo());
		subContractQuotationVO.setNarration(subContractQuotationDTO.getNarration());
		subContractQuotationVO.setOrgId(subContractQuotationDTO.getOrgId());
		subContractQuotationVO.setActive(subContractQuotationDTO.isActive());
		subContractQuotationVO.setBranch(subContractQuotationDTO.getBranch());
		subContractQuotationVO.setBranchCode(subContractQuotationDTO.getBranchCode());
		subContractQuotationVO.setFinYear(subContractQuotationDTO.getFinYear());
		subContractQuotationVO.setStatus(subContractQuotationDTO.getStatus());

		BigDecimal grocessAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(subContractQuotationDTO.getId())) {
			List<SubContractQuotationDetailsVO> subContractQuotationDetailsVO1 = subContractQuotationDetailsRepo
					.findBySubContractQuotationVO(subContractQuotationVO);
			subContractQuotationDetailsRepo.deleteAll(subContractQuotationDetailsVO1);
		}

		List<SubContractQuotationDetailsVO> subContractQuotationDetailsVOs = new ArrayList<>();

		for (SubContractQuotationDetailsDTO subContractQuotationDetailsDTO : subContractQuotationDTO
				.getSubContractQuotationDetailsDTO()) {

			SubContractQuotationDetailsVO subContractQuotationDetailsVO = new SubContractQuotationDetailsVO();

			subContractQuotationDetailsVO.setPart(subContractQuotationDetailsDTO.getPart());
			subContractQuotationDetailsVO.setPartDescription(subContractQuotationDetailsDTO.getPartDescription());
			subContractQuotationDetailsVO.setProcess(subContractQuotationDetailsDTO.getProcess());
			subContractQuotationDetailsVO.setQty(subContractQuotationDetailsDTO.getQty());
			if (subContractQuotationDetailsDTO.getRate() == null
					|| subContractQuotationDetailsDTO.getRate().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Rate must be greater than zero.");
			}

			subContractQuotationDetailsVO.setRate(subContractQuotationDetailsDTO.getRate());

			if (subContractQuotationDetailsDTO.getDiscount() == null
			        || subContractQuotationDetailsDTO.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
			    throw new ApplicationException("Discount cannot be negative.");
			}
			subContractQuotationDetailsVO.setDiscount(subContractQuotationDetailsDTO.getDiscount());

			if (subContractQuotationDetailsDTO.getTax() == null
					|| subContractQuotationDetailsDTO.getTax().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Tax must be greater than zero.");
			}

			subContractQuotationDetailsVO.setTax(subContractQuotationDetailsDTO.getTax());

			BigDecimal discountAmount = BigDecimal.ZERO;
			BigDecimal afterDiscountAmount = BigDecimal.ZERO;
			BigDecimal afterQuotationAmount = BigDecimal.ZERO;

			BigDecimal amount = subContractQuotationDetailsDTO.getRate()
					.multiply(subContractQuotationDetailsDTO.getQty());
			subContractQuotationDetailsVO.setAmount(amount);
			grocessAmount = grocessAmount.add(amount);

			discountAmount = subContractQuotationDetailsVO.getAmount()
					.multiply(subContractQuotationDetailsDTO.getDiscount()).divide(BigDecimal.valueOf(100));

			subContractQuotationDetailsVO.setDiscountAmount(discountAmount);

			afterDiscountAmount = amount.subtract(discountAmount);
//			subContractQuotationDetailsVO.setAfterDiscountAmount(afterDiscountAmount);
//			subContractQuotationDetailsDTO.setAfterDiscountAmount(afterDiscountAmount);

			afterQuotationAmount = afterDiscountAmount.multiply(subContractQuotationDetailsDTO.getTax())
					.divide(BigDecimal.valueOf(100));

//			subContractQuotationDetailsVO.setAfterQuotationAmount(afterQuotationAmount);
//			subContractQuotationDetailsDTO.setAfterQuotationAmount(afterQuotationAmount);

			BigDecimal quotationAmount = afterDiscountAmount.add(afterQuotationAmount);
			subContractQuotationDetailsVO.setQuotationAmount(quotationAmount);
			netAmount = netAmount.add(quotationAmount);

			subContractQuotationDetailsVO.setDeliveryDate(subContractQuotationDetailsDTO.getDeliveryDate());

			subContractQuotationDetailsVO.setSubContractQuotationVO(subContractQuotationVO);

			subContractQuotationDetailsVOs.add(subContractQuotationDetailsVO);
		}

		subContractQuotationVO.setGrossAmount(grocessAmount);
		subContractQuotationVO.setNetAmount(netAmount);

		subContractQuotationVO.setAmountInWords(
				amountInWordsConverterService.convert(subContractQuotationVO.getNetAmount().longValue()));

		subContractQuotationVO.setSubContractQuotationDetailsVO(subContractQuotationDetailsVOs);
	}

	@Override
	public List<SubContractQuotationVO> getAllSubContractQuotationByOrgId(Long orgId, String finYear,
			String branchCode) {

		return subContractQuotationRepo.getAllSubContractQuotationByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public List<SubContractQuotationVO> getSubContractQuotationById(Long id) {

		return subContractQuotationRepo.getSubContractQuotationById(id);
	}

	@Override
	public String getSubContractEnquiryIdIteration(Long orgId, String clientName, String enquiryNo) {
		return subContractQuotationRepo.getSubContractEnquiryIdIteration(orgId, clientName, enquiryNo);

	}

	@Override
	public String getSubContractQuotationDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "SCQ";
		String result = subContractQuotationRepo.getSubContractQuotationDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getEnquiryNoFromSubContractEnquiry(Long orgId) {
		Set<Object[]> chType = subContractQuotationRepo.getEnquiryNoFromSubContractEnquiry(orgId);
		return getEnquiryNoFromSub(chType);
	}

	private List<Map<String, Object>> getEnquiryNoFromSub(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("enquiryNo", ch[0] != null ? ch[0].toString() : "");
			map.put("enquiryDate", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDocDateFromSubEnquiry(Long orgId, String docId) {
		Set<Object[]> chType = subContractQuotationRepo.getDocDateFromSubEnquiry(orgId, docId);
		return getDocDate(chType);
	}

	private List<Map<String, Object>> getDocDate(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("subContractId", ch[0] != null ? ch[0].toString() : "");
			map.put("validTill", ch[1] != null ? ch[1].toString() : "");
			map.put("subContractorName", ch[2] != null ? ch[2].toString() : "");
			map.put("routeCardNo", ch[3] != null ? ch[3].toString() : "");
			map.put("scIssueNo", ch[4] != null ? ch[4].toString() : "");
			map.put("gstIn", ch[5] != null ? ch[5].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNoPartDescFromSubEnquiry(Long orgId, String docId) {
		Set<Object[]> chType = subContractQuotationRepo.getPartNoPartDescFromSubEnquiry(orgId, docId);
		return getPartNoPartDescFrom(chType);
	}

	private List<Map<String, Object>> getPartNoPartDescFrom(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : "");
			map.put("partdescription", ch[1] != null ? ch[1].toString() : "");
			map.put("qty", ch[2] != null ? ch[2].toString() : "");
			map.put("process", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// SubContractInvoice

	@Override
	public Map<String, Object> createUpdateSubContractInvoice(SubContractInvoiceDTO subContractInvoiceDTO)
			throws ApplicationException {
		SubContractInvoiceVO subContractInvoiceVO = new SubContractInvoiceVO();
		String message;
		String screenCode = "SCI";
		SubContractInvoiceVO oldSubContractInvoice = null;

		if (ObjectUtils.isNotEmpty(subContractInvoiceDTO.getId())) {
			oldSubContractInvoice = subContractInvoiceRepo.findById(subContractInvoiceDTO.getId())
		            .orElseThrow(() -> new ApplicationException("SubContractInvoice not found"));

			oldSubContractInvoice.getSubContractInvoiceDetailsVO().size(); // load
			oldSubContractInvoice.getSubContractInvoiceTermsVO().size(); // load
		    entityManager.detach(oldSubContractInvoice); // detach snapshot
			subContractInvoiceVO = subContractInvoiceRepo.findById(subContractInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractInvoice Enquiry details"));
			message = "SubContractInvoice Updated Successfully";
			subContractInvoiceVO.setUpdatedBy(subContractInvoiceDTO.getCreatedBy());

		} else {

			String docId = subContractInvoiceRepo.getSubContractInvoiceDocId(subContractInvoiceDTO.getOrgId(),
					subContractInvoiceDTO.getFinYear(), subContractInvoiceDTO.getBranchCode(), screenCode);
			subContractInvoiceVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(subContractInvoiceDTO.getOrgId(),
							subContractInvoiceDTO.getFinYear(), subContractInvoiceDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			subContractInvoiceVO.setCreatedBy(subContractInvoiceDTO.getCreatedBy());
			subContractInvoiceVO.setUpdatedBy(subContractInvoiceDTO.getCreatedBy());

			message = "SubContractInvoice Created Successfully";
		}
		createUpdatedSubContractInvoiceVOFromSubContractInvoiceDTO(subContractInvoiceDTO, subContractInvoiceVO);
		subContractInvoiceRepo.save(subContractInvoiceVO);
		commonNotificationService.generateNotification(subContractInvoiceVO.getScreenCode(), subContractInvoiceVO.getId(), oldSubContractInvoice, subContractInvoiceVO);

		Map<String, Object> response = new HashMap<>();
		response.put("subContractInvoiceVO", subContractInvoiceVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedSubContractInvoiceVOFromSubContractInvoiceDTO(SubContractInvoiceDTO subContractInvoiceDTO,
			SubContractInvoiceVO subContractInvoiceVO) {
		subContractInvoiceVO.setJobWorkOrderNo(subContractInvoiceDTO.getJobWorkOrderNo());
		subContractInvoiceVO.setDcno(subContractInvoiceDTO.getDcno());
		subContractInvoiceVO.setDeliveryNoteDate(subContractInvoiceDTO.getDeliveryNoteDate());
		subContractInvoiceVO.setDispatchedThrough(subContractInvoiceDTO.getDispatchedThrough());
		subContractInvoiceVO.setRouteCardNo(subContractInvoiceDTO.getRouteCardNo());
		subContractInvoiceVO.setSubContractorCode(subContractInvoiceDTO.getSubContractorCode());
		subContractInvoiceVO.setSubContractorName(subContractInvoiceDTO.getSubContractorName());
		subContractInvoiceVO.setSubContractorAddress(subContractInvoiceDTO.getSubContractorAddress());
		subContractInvoiceVO.setOrgId(subContractInvoiceDTO.getOrgId());
		subContractInvoiceVO.setActive(subContractInvoiceDTO.isActive());
		subContractInvoiceVO.setNarration(subContractInvoiceDTO.getNarration());
		subContractInvoiceVO.setBranch(subContractInvoiceDTO.getBranch());
		subContractInvoiceVO.setBranchCode(subContractInvoiceDTO.getBranchCode());
		subContractInvoiceVO.setFinYear(subContractInvoiceDTO.getFinYear());

		if (ObjectUtils.isNotEmpty(subContractInvoiceDTO.getId())) {
			List<SubContractInvoiceDetailsVO> subContractTaxInvoiceDetailsVO1 = subContractInvoiceDetailsRepo
					.findBySubContractInvoiceVO(subContractInvoiceVO);
			subContractInvoiceDetailsRepo.deleteAll(subContractTaxInvoiceDetailsVO1);

			List<SubContractInvoiceTermsVO> subContractTermsAndConditionsVO1 = subContractInvoiceTermsRepo
					.findBySubContractInvoiceVO(subContractInvoiceVO);
			subContractInvoiceTermsRepo.deleteAll(subContractTermsAndConditionsVO1);
		}

		List<SubContractInvoiceDetailsVO> subContractInvoiceDetailsVOs = new ArrayList<>();
		for (SubContractInvoiceDetailsDTO subContractInvoiceDetailsDTO : subContractInvoiceDTO
				.getSubContractInvoiceDetailsDTO()) {
			SubContractInvoiceDetailsVO subContractInvoiceDetailsVO = new SubContractInvoiceDetailsVO();
			subContractInvoiceDetailsVO.setPartNo(subContractInvoiceDetailsDTO.getPartNo());
			subContractInvoiceDetailsVO.setPartDes(subContractInvoiceDetailsDTO.getPartDes());
			subContractInvoiceDetailsVO.setProcess(subContractInvoiceDetailsDTO.getProcess());
			subContractInvoiceDetailsVO.setQuantityNos(subContractInvoiceDetailsDTO.getQuantityNos());
			subContractInvoiceDetailsVO.setRate(subContractInvoiceDetailsDTO.getRate());
			subContractInvoiceDetailsVO.setUnits(subContractInvoiceDetailsDTO.getUnits());
			subContractInvoiceDetailsVO.setAmount(subContractInvoiceDetailsDTO.getAmount());
			subContractInvoiceDetailsVO.setCgst(subContractInvoiceDetailsDTO.getCgst());
			subContractInvoiceDetailsVO.setSgst(subContractInvoiceDetailsDTO.getSgst());
			subContractInvoiceDetailsVO.setLandedAmount(subContractInvoiceDetailsDTO.getLandedAmount());
			subContractInvoiceDetailsVO.setQuotationAmount(subContractInvoiceDetailsDTO.getQuotationAmount());
			subContractInvoiceDetailsVO.setSubContractInvoiceVO(subContractInvoiceVO);
			subContractInvoiceDetailsVOs.add(subContractInvoiceDetailsVO);
		}
		subContractInvoiceVO.setSubContractInvoiceDetailsVO(subContractInvoiceDetailsVOs);

		List<SubContractInvoiceTermsVO> subContractInvoiceTermsVOs = new ArrayList<>();
		for (SubContractInvoiceTermsDTO subContractInvoiceTermsDTO : subContractInvoiceDTO
				.getSubContractInvoiceTermsDTO()) {
			SubContractInvoiceTermsVO subContractInvoiceTermsVO = new SubContractInvoiceTermsVO();
			subContractInvoiceTermsVO.setTerms(subContractInvoiceTermsDTO.getTerms());
			subContractInvoiceTermsVO.setDescription(subContractInvoiceTermsDTO.getDescription());
			subContractInvoiceTermsVO.setSubContractInvoiceVO(subContractInvoiceVO);
			subContractInvoiceTermsVOs.add(subContractInvoiceTermsVO);
		}
		subContractInvoiceVO.setSubContractInvoiceTermsVO(subContractInvoiceTermsVOs);
	}

	@Override
	public List<SubContractInvoiceVO> getAllSubContractInvoiceByOrgId(Long orgId, String finYear, String branchCode) {

		return subContractInvoiceRepo.getAllSubContractInvoiceByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public List<SubContractInvoiceVO> getSubContractInvoiceById(Long id) {

		return subContractInvoiceRepo.getSubContractInvoiceById(id);
	}

	@Override
	public String getSubContractInvoiceDocId(Long orgId, String finYear, String branchode) {
		String ScreenCode = "SCI";
		String result = subContractInvoiceRepo.getSubContractInvoiceDocId(orgId, finYear, branchode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutOrderNo(Long orgId) {
		Set<Object[]> chType = subContractInvoiceRepo.getJobWorkOutOrderNo(orgId);
		return getWorkOrderOut(chType);
	}

	private List<Map<String, Object>> getWorkOrderOut(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobworkorderno", ch[0] != null ? ch[0].toString() : "");
			map.put("dcno", ch[1] != null ? ch[1].toString() : "");
			map.put("deliverydDate", ch[2] != null ? ch[2].toString() : "");
			map.put("dispatchedthrough", ch[3] != null ? ch[3].toString() : "");
			map.put("routeCardNo", ch[4] != null ? ch[4].toString() : "");
			map.put("contractorCode", ch[5] != null ? ch[5].toString() : "");
			map.put("contractorName", ch[6] != null ? ch[6].toString() : "");
			map.put("subContractorAddress", ch[7] != null ? ch[7].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutOrderFromPartNoAndDesc(Long orgId, String docId) {
		Set<Object[]> chType = subContractInvoiceRepo.getJobWorkOutOrderFromPartNoAndDesc(orgId, docId);
		return getJobWorkOutOrderFromPartNo(chType);
	}

	private List<Map<String, Object>> getJobWorkOutOrderFromPartNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("process", ch[2] != null ? ch[2].toString() : "");
			map.put("quantityNos", ch[3] != null ? ch[3].toString() : "");
			map.put("rate", ch[4] != null ? ch[4].toString() : "");
			map.put("amount", ch[5] != null ? ch[5].toString() : "");
			map.put("cgst", ch[6] != null ? ch[6].toString() : "");
			map.put("sgst", ch[7] != null ? ch[7].toString() : "");
			map.put("landedAmount", ch[8] != null ? ch[8].toString() : "");
			map.put("grossAmount", ch[9] != null ? ch[9].toString() : "");
			map.put("totalTaxAmount", ch[10] != null ? ch[10].toString() : "");
			map.put("netAmount", ch[11] != null ? ch[11].toString() : "");
			map.put("amountInWords", ch[12] != null ? ch[12].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	// JobWorkOutOrder

	@Override
	public Map<String, Object> createUpdateJobWorkOut(JobWorkOutDTO jobWorkOutDTO) throws ApplicationException {
		JobWorkOutVO jobWorkOutVO = new JobWorkOutVO();
		String message;
		String screenCode = "JWO";
		JobWorkOutVO oldJobWorkOut    = null;
		
		if (ObjectUtils.isNotEmpty(jobWorkOutDTO.getId())) {
			
			oldJobWorkOut = jobWorkOutRepo.findById(jobWorkOutDTO.getId())
		            .orElseThrow(() -> new ApplicationException("jobWorkOut not found"));

			oldJobWorkOut.getJobWorkOutDetailsVO().size(); // load
			
		    entityManager.detach(oldJobWorkOut); // detach snapshot
			jobWorkOutVO = jobWorkOutRepo.findById(jobWorkOutDTO.getId())
					.orElseThrow(() -> new ApplicationException("SubContractEnquiry Enquiry details"));
			message = "jobWorkOut Updated Successfully";
			jobWorkOutVO.setUpdatedBy(jobWorkOutDTO.getCreatedBy());

		} else {

			String docId = jobWorkOutRepo.getJobWorkOutDocId(jobWorkOutDTO.getOrgId(), jobWorkOutDTO.getFinYear(),
					jobWorkOutDTO.getBranchCode(), screenCode);
			jobWorkOutVO.setJobWorkOrderNo(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(jobWorkOutDTO.getOrgId(),
							jobWorkOutDTO.getFinYear(), jobWorkOutDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			jobWorkOutVO.setCreatedBy(jobWorkOutDTO.getCreatedBy());
			jobWorkOutVO.setUpdatedBy(jobWorkOutDTO.getCreatedBy());

			message = "jobWorkOut Created Successfully";
		}
		createUpdatedJodWorkOutVOFromJodWorkOuDTO(jobWorkOutDTO, jobWorkOutVO);
		jobWorkOutRepo.save(jobWorkOutVO);
		commonNotificationService.generateNotification(jobWorkOutVO.getScreenCode(), jobWorkOutVO.getId(), oldJobWorkOut, jobWorkOutVO);

		
		Map<String, Object> response = new HashMap<>();
		response.put("jobWorkOutVO", jobWorkOutVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedJodWorkOutVOFromJodWorkOuDTO(JobWorkOutDTO jobWorkOutDTO, JobWorkOutVO jobWorkOutVO)
			throws ApplicationException {
		jobWorkOutVO.setJobWorkOrderDate(jobWorkOutDTO.getJobWorkOrderDate());
		jobWorkOutVO.setDcNo(jobWorkOutDTO.getDcNo());
		jobWorkOutVO.setRouteCardNo(jobWorkOutDTO.getRouteCardNo());
		jobWorkOutVO.setPoNo(jobWorkOutDTO.getPoNo());
		jobWorkOutVO.setQuotationNo(jobWorkOutDTO.getQuotationNo());
		jobWorkOutVO.setContractorName(jobWorkOutDTO.getContractorName());
		jobWorkOutVO.setContractorCode(jobWorkOutDTO.getContractorCode());
		jobWorkOutVO.setDestination(jobWorkOutDTO.getDestination());
		jobWorkOutVO.setDurationOfProcess(jobWorkOutDTO.getDurationOfProcess());
		jobWorkOutVO.setDispatchedThrough(jobWorkOutDTO.getDispatchedThrough());
		jobWorkOutVO.setTaxType(jobWorkOutDTO.getTaxType());
		jobWorkOutVO.setTermsOfPayment(jobWorkOutDTO.getTermsOfPayment());
		jobWorkOutVO.setNarration(jobWorkOutDTO.getNarration());
		jobWorkOutVO.setOrgId(jobWorkOutDTO.getOrgId());
		jobWorkOutVO.setActive(jobWorkOutDTO.isActive());
		jobWorkOutVO.setBranch(jobWorkOutDTO.getBranch());
		jobWorkOutVO.setBranchCode(jobWorkOutDTO.getBranchCode());
		jobWorkOutVO.setFinYear(jobWorkOutDTO.getFinYear());

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalGrossAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(jobWorkOutDTO.getId())) {
			List<JobWorkOutDetailsVO> jobWorkOutDetailsVO1 = jobWorkOutDetailsRepo.findByJobWorkOutVO(jobWorkOutVO);
			jobWorkOutDetailsRepo.deleteAll(jobWorkOutDetailsVO1);

		}

		List<JobWorkOutDetailsVO> jobWorkOutDetailsVOs = new ArrayList<>();
		for (JobWorkOutDetailsDTO jobWorkOutDetailsDTO : jobWorkOutDTO.getJobWorkOutDetailsDTO()) {
			JobWorkOutDetailsVO jobWorkOutDetailsVO = new JobWorkOutDetailsVO();
			jobWorkOutDetailsVO.setPart(jobWorkOutDetailsDTO.getPart());
			jobWorkOutDetailsVO.setPartDesc(jobWorkOutDetailsDTO.getPartDesc());
			jobWorkOutDetailsVO.setProcess(jobWorkOutDetailsDTO.getProcess());
			jobWorkOutDetailsVO.setDueOn(jobWorkOutDetailsDTO.getDueOn());
			jobWorkOutDetailsVO.setTaxCode(jobWorkOutDetailsDTO.getTaxCode());
			jobWorkOutDetailsVO.setQuantityNos(jobWorkOutDetailsDTO.getQuantityNos());
			
			if (jobWorkOutDetailsDTO.getDiscount() == null 
			        || jobWorkOutDetailsDTO.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
			    throw new ApplicationException("Discount cannot be negative.");
			}
			
			
			jobWorkOutDetailsVO.setDiscount(jobWorkOutDetailsDTO.getDiscount());
			jobWorkOutDetailsVO.setRate(jobWorkOutDetailsDTO.getRate());
			jobWorkOutDetailsVO.setCgst(jobWorkOutDetailsDTO.getCgst());
			jobWorkOutDetailsVO.setSgst(jobWorkOutDetailsDTO.getSgst());
			jobWorkOutDetailsVO.setIgst(jobWorkOutDetailsDTO.getIgst());

			BigDecimal taxAmountAll = BigDecimal.ZERO;
			BigDecimal amount = BigDecimal.ZERO;

			BigDecimal setAmountGross = jobWorkOutDetailsDTO.getQuantityNos().multiply(jobWorkOutDetailsDTO.getRate());
			jobWorkOutDetailsVO.setGrossAmt(setAmountGross);

			BigDecimal discountAmount = jobWorkOutDetailsVO.getGrossAmt().multiply(jobWorkOutDetailsDTO.getDiscount())
					.divide(BigDecimal.valueOf(100));
			jobWorkOutDetailsVO.setDiscountAmount(discountAmount);

			BigDecimal netAmounts = jobWorkOutDetailsVO.getGrossAmt().subtract(jobWorkOutDetailsVO.getDiscountAmount());
			jobWorkOutDetailsVO.setNetAmount(netAmounts);
			totalGrossAmount = totalGrossAmount.add(jobWorkOutDetailsVO.getNetAmount());

			BigDecimal sgstamount = jobWorkOutDetailsDTO.getSgst().multiply(jobWorkOutDetailsVO.getNetAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal cgstamount = jobWorkOutDetailsDTO.getCgst().multiply(jobWorkOutDetailsVO.getNetAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal igstamount = jobWorkOutDetailsDTO.getIgst().multiply(jobWorkOutDetailsVO.getNetAmount())
					.divide(BigDecimal.valueOf(100));

			taxAmountAll = taxAmountAll.add(cgstamount).add(sgstamount).add(igstamount);
			jobWorkOutDetailsVO.setTaxAmt(taxAmountAll);
			totalTaxAmount = totalTaxAmount.add(jobWorkOutDetailsVO.getTaxAmt());

			amount = jobWorkOutDetailsVO.getNetAmount().add(jobWorkOutDetailsVO.getTaxAmt());
			jobWorkOutDetailsVO.setAmount(amount);
			totalAmount = totalAmount.add(jobWorkOutDetailsVO.getAmount());

			jobWorkOutDetailsVO.setJobWorkOutVO(jobWorkOutVO);
			jobWorkOutDetailsVOs.add(jobWorkOutDetailsVO);
		}
		jobWorkOutVO.setTotalAmount(totalAmount);
		jobWorkOutVO.setTotalGrossAmt(totalGrossAmount);
		jobWorkOutVO.setTotalTax(totalTaxAmount);
		jobWorkOutVO.setAmountInWords(amountInWordsConverterService.convert(jobWorkOutVO.getTotalAmount().longValue()));
		jobWorkOutVO.setJobWorkOutDetailsVO(jobWorkOutDetailsVOs);

	}

	@Override
	public List<JobWorkOutVO> getAllJobWorkOutById(Long id) {

		return jobWorkOutRepo.getAllJobWorkOutById(id);
	}

	@Override
	public String getJobWorkOutDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "JWO";
		String result = jobWorkOutRepo.getJobWorkOutDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<JobWorkOutVO> getAllJobWorkOutByOrgId(Long orgId, String finYear, String branchCode) {

		return jobWorkOutRepo.getAllJobWorkOutByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public List<Map<String, Object>> getDCNumberFromDcForSubContract(Long orgId) {
		Set<Object[]> chType = jobWorkOutRepo.getDCNumberFromDcForSubContract(orgId);
		return getDCNumberFromDc(chType);
	}

	private List<Map<String, Object>> getDCNumberFromDc(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("dcNo", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("routecardNo", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("contractorName", ch[3] != null ? ch[3].toString() : "");
			map.put("contractorCode", ch[4] != null ? ch[4].toString() : "");
			map.put("destination", ch[5] != null ? ch[5].toString() : "");
			map.put("dispatchedThrough", ch[6] != null ? ch[6].toString() : "");
			map.put("taxType", ch[7] != null ? ch[7].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPoNumberFromPurchase(Long orgId, String routeCardNo) {
		Set<Object[]> chType = jobWorkOutRepo.getPoNumberFromPurchase(orgId, routeCardNo);
		return getPoNumberFrom(chType);
	}

	private List<Map<String, Object>> getPoNumberFrom(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("poNumber", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getQuotationNumberFromSubContract(Long orgId, String routeCardNo) {
		Set<Object[]> chType = jobWorkOutRepo.getQuotationNumberFromSubContract(orgId, routeCardNo);
		return getQuotationNumber(chType);
	}

	private List<Map<String, Object>> getQuotationNumber(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("quotationNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemAndItemDescFromDcForSubContract(Long orgId, String dcNumber,
			String routeCardNo) {
		Set<Object[]> chType = jobWorkOutRepo.getItemAndItemDescFromDcForSubContract(orgId, dcNumber, routeCardNo);
		return getItemAndItemDesc(chType);
	}

	private List<Map<String, Object>> getItemAndItemDesc(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("process", ch[2] != null ? ch[2].toString() : "");
			map.put("dueDate", ch[3] != null ? ch[3].toString() : "");
			map.put("quantityNos", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// RecieveFromSubcontract

	@Override
	public List<RecieveFromSubcontractVO> getRecieveFromSubcontractByOrgId(Long orgId, String finYear,
			String branchCode) {
		List<RecieveFromSubcontractVO> recieveFromSubcontractVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received RecieveFromSubcontract BY OrgId : {}", orgId);
			recieveFromSubcontractVO = recieveFromSubcontractRepo.findRecieveFromSubcontractByOrgId(orgId, finYear,
					branchCode);
		}
		return recieveFromSubcontractVO;
	}

	@Override
	public List<RecieveFromSubcontractVO> getRecieveFromSubcontractById(Long id) {
		List<RecieveFromSubcontractVO> recieveFromSubcontractVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received RecieveFromSubcontract BY Id : {}", id);
			recieveFromSubcontractVO = recieveFromSubcontractRepo.getRecieveFromSubcontractById(id);
		}
		return recieveFromSubcontractVO;
	}

	@Override
	public Map<String, Object> updateCreateRecieveFromSubcontract(RecieveFromSubcontractDTO recieveFromSubcontractDTO)
			throws ApplicationException {
		RecieveFromSubcontractVO recieveFromSubcontractVO = new RecieveFromSubcontractVO();
		String message;
		String screenCode = "RSC";
		RecieveFromSubcontractVO oldRecieveFromSubcontract    = null;
		if (ObjectUtils.isNotEmpty(recieveFromSubcontractDTO.getId())) {
			
			oldRecieveFromSubcontract = recieveFromSubcontractRepo.findById(recieveFromSubcontractDTO.getId())
		            .orElseThrow(() -> new ApplicationException("recieveFromSubcontract not found"));

			oldRecieveFromSubcontract.getRecieveFromSubContractDetailsVO().size(); // load
			
		    entityManager.detach(oldRecieveFromSubcontract); // detach snapshot
			
			recieveFromSubcontractVO = recieveFromSubcontractRepo.findById(recieveFromSubcontractDTO.getId())
					.orElseThrow(() -> new ApplicationException("Recieve From Subcontract Enquiry details"));
			message = "Recieve From Subcontract Updated Successfully";
			recieveFromSubcontractVO.setUpdatedBy(recieveFromSubcontractDTO.getCreatedBy());

		} else {

			String docId = recieveFromSubcontractRepo.getRecieveFromSubContractDocId(
					recieveFromSubcontractDTO.getOrgId(), recieveFromSubcontractDTO.getFinYear(),
					recieveFromSubcontractDTO.getBranchCode(), screenCode);
			recieveFromSubcontractVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(recieveFromSubcontractDTO.getOrgId(),
							recieveFromSubcontractDTO.getFinYear(), recieveFromSubcontractDTO.getBranchCode(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			recieveFromSubcontractVO.setCreatedBy(recieveFromSubcontractDTO.getCreatedBy());
			recieveFromSubcontractVO.setUpdatedBy(recieveFromSubcontractDTO.getCreatedBy());

			message = "SubContractInvoice Created Successfully";
		}
		createUpdatedRecieveFromSubContractVOFromRecieveFromSubContractDTO(recieveFromSubcontractDTO,
				recieveFromSubcontractVO);
		recieveFromSubcontractRepo.save(recieveFromSubcontractVO);
		commonNotificationService.generateNotification(recieveFromSubcontractVO.getScreenCode(), recieveFromSubcontractVO.getId(), oldRecieveFromSubcontract, recieveFromSubcontractVO);

		Map<String, Object> response = new HashMap<>();
		response.put("recieveFromSubcontractVO", recieveFromSubcontractVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedRecieveFromSubContractVOFromRecieveFromSubContractDTO(
			RecieveFromSubcontractDTO recieveFromSubcontractDTO, RecieveFromSubcontractVO recieveFromSubcontractVO) {
		recieveFromSubcontractVO.setRouteCardNo(recieveFromSubcontractDTO.getRouteCardNo());
		recieveFromSubcontractVO.setIssueNo(recieveFromSubcontractDTO.getIssueNo());
		recieveFromSubcontractVO.setIssueDate(recieveFromSubcontractDTO.getIssueDate());
		recieveFromSubcontractVO.setJobWorkOutOrder(recieveFromSubcontractDTO.getJobWorkOutOrder());
		recieveFromSubcontractVO.setDcNo(recieveFromSubcontractDTO.getDcNo());
		recieveFromSubcontractVO.setDepartment(recieveFromSubcontractDTO.getDepartment());
		recieveFromSubcontractVO.setContractorName(recieveFromSubcontractDTO.getContractorName());
		recieveFromSubcontractVO.setContractorId(recieveFromSubcontractDTO.getContractorId());
		recieveFromSubcontractVO.setInvoiceNo(recieveFromSubcontractDTO.getInvoiceNo());
		recieveFromSubcontractVO.setTestCertificate(recieveFromSubcontractDTO.getTestCertificate());
		recieveFromSubcontractVO.setActive(recieveFromSubcontractDTO.isActive());
		recieveFromSubcontractVO.setOrgId(recieveFromSubcontractDTO.getOrgId());
		recieveFromSubcontractVO.setBranch(recieveFromSubcontractDTO.getBranch());
		recieveFromSubcontractVO.setBranchCode(recieveFromSubcontractDTO.getBranchCode());
		recieveFromSubcontractVO.setFinYear(recieveFromSubcontractDTO.getFinYear());

		if (ObjectUtils.isNotEmpty(recieveFromSubcontractDTO.getId())) {
			List<RecieveFromSubContractDetailsVO> recieveFromSubContractDetailsVO1 = recieveFromSubcontractDetailsRepo
					.findByRecieveFromSubcontractVO(recieveFromSubcontractVO);
			recieveFromSubcontractDetailsRepo.deleteAll(recieveFromSubContractDetailsVO1);

		}

		List<RecieveFromSubContractDetailsVO> recieveFromSubContractDetailsVOs = new ArrayList<>();
		for (RecieveFromSubContractDetailsDTO recieveFromSubContractDetailsDTO : recieveFromSubcontractDTO
				.getRecieveFromSubContractDetailsDTO()) {
			RecieveFromSubContractDetailsVO recieveFromSubContractDetailsVO = new RecieveFromSubContractDetailsVO();
			recieveFromSubContractDetailsVO.setPartName(recieveFromSubContractDetailsDTO.getPartName());
			recieveFromSubContractDetailsVO.setPartDesc(recieveFromSubContractDetailsDTO.getPartDesc());
			recieveFromSubContractDetailsVO.setIssueQty(recieveFromSubContractDetailsDTO.getIssueQty());
			recieveFromSubContractDetailsVO.setRecieveQty(recieveFromSubContractDetailsDTO.getRecieveQty());
			recieveFromSubContractDetailsVO.setPendingQty(recieveFromSubContractDetailsDTO.getPendingQty());
			recieveFromSubContractDetailsVO.setRemarks(recieveFromSubContractDetailsDTO.getRemarks());

			recieveFromSubContractDetailsVO.setRecieveFromSubcontractVO(recieveFromSubcontractVO);
			recieveFromSubContractDetailsVOs.add(recieveFromSubContractDetailsVO);
		}
		recieveFromSubcontractVO.setRecieveFromSubContractDetailsVO(recieveFromSubContractDetailsVOs);
	}

	@Override
	public String getRecieveFromSubcontractDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "RSC";
		String result = recieveFromSubcontractRepo.getRecieveFromSubContractDocId(orgId, finYear, branchCode,
				ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getDcSubContractorDocIdForJobWorkOutOrder(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> docid = dcForSubContractRepo.getDcSubContractorDocIdForJobWorkOutOrder(orgId, finYear,
				branchCode);
		return getDcSubContractorDocIdForJobWorkOutOrder(docid);
	}

	private List<Map<String, Object>> getDcSubContractorDocIdForJobWorkOutOrder(Set<Object[]> docid) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : docid) {
			Map<String, Object> map = new HashMap<>();
			map.put("dcNo", ch[0] != null ? ch[0].toString() : "");
			map.put("routeCardNo", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("customerAddress", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderDocIdForJobWorkOutOrder(Long orgId, String finYear,
			String branchCode, String routeCardNo) {
		Set<Object[]> docid = jobWorkOutRepo.getPurchaseOrderDocIdForJobWorkOutOrder(orgId, finYear, branchCode,
				routeCardNo);
		return getPurchaseOrderDocIdForJobWorkOutOrder(docid);
	}

	private List<Map<String, Object>> getPurchaseOrderDocIdForJobWorkOutOrder(Set<Object[]> docid) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : docid) {
			Map<String, Object> map = new HashMap<>();
			map.put("poNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRateFromSubContractQuotation(Long orgId, String finYear, String branchCode,
			String subContractQuotationDocId, String routeCardNo, String part) {
		Set<Object[]> docid = jobWorkOutRepo.getRateFromSubContractQuotation(orgId, finYear, branchCode,
				subContractQuotationDocId, routeCardNo, part);
		return getRateFromSubContractQuotation(docid);
	}

	private List<Map<String, Object>> getRateFromSubContractQuotation(Set<Object[]> docid) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : docid) {
			Map<String, Object> map = new HashMap<>();
			map.put("rate", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractQuotationDocIdForJobWorkOutOrder(Long orgId, String finYear,
			String branchCode, String routeCardNo) {
		Set<Object[]> docid = subContractQuotationRepo.getSubContractQuotationDocIdForJobWorkOutOrder(orgId, finYear,
				branchCode, routeCardNo);
		return getSubContractQuotationDocIdForJobWorkOutOrder(docid);
	}

	private List<Map<String, Object>> getSubContractQuotationDocIdForJobWorkOutOrder(Set<Object[]> docid) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : docid) {
			Map<String, Object> map = new HashMap<>();
			map.put("quatationNo", ch[0] != null ? ch[0].toString() : "");
			map.put("subContractorName", ch[1] != null ? ch[1].toString() : "");
			map.put("subContractorId", ch[2] != null ? ch[2].toString() : "");
			map.put("address", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getIssueNoForReceiveFromSubContractor(Long orgId, String finYear,
			String branchCode, String routeCardNo) {
		Set<Object[]> issueNo = issueToSubContractorRepo.getIssueNoForReceiveFromSubContractor(orgId, finYear,
				branchCode, routeCardNo);
		return getIssueNoForReceiveFromSubContractor(issueNo);
	}

	private List<Map<String, Object>> getIssueNoForReceiveFromSubContractor(Set<Object[]> issueNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : issueNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("issueNo", ch[0] != null ? ch[0].toString() : "");
			map.put("issueDate", ch[1] != null ? ch[1].toString() : "");
			map.put("department", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutOrderNoForReceiveFromSubContractor(Long orgId, String finYear,
			String branchCode, String routeCardNo) {
		Set<Object[]> jobWorkOutOrderNo = issueToSubContractorRepo
				.getJobWorkOutOrderNoForReceiveFromSubContractor(orgId, finYear, branchCode, routeCardNo);
		return getJobWorkOutOrderNoForReceiveFromSubContractor(jobWorkOutOrderNo);
	}

	private List<Map<String, Object>> getJobWorkOutOrderNoForReceiveFromSubContractor(Set<Object[]> jobWorkOutOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : jobWorkOutOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobWorkOutOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("dcNo", ch[1] != null ? ch[1].toString() : "");
			map.put("contractorCode", ch[2] != null ? ch[2].toString() : "");
			map.put("contractorName", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNameAndPartDescForReceiveFromSubContractor(Long orgId, String finYear,
			String branchCode, String routeCardNo, String issueNo) {
		Set<Object[]> jobWorkOutOrderNo = issueToSubContractorRepo
				.getPartNameAndPartDescForReceiveFromSubContractor(orgId, finYear, branchCode, routeCardNo, issueNo);
		return getPartNameAndPartDescForReceiveFromSubContractor(jobWorkOutOrderNo);
	}

	private List<Map<String, Object>> getPartNameAndPartDescForReceiveFromSubContractor(
			Set<Object[]> jobWorkOutOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : jobWorkOutOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("partName", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("issuedQty", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getIssueToSubContractorDetails(Long orgId, String fromdate, String todate,
			String status, String routeCardNo) {
		Set<Object[]> issueToSubContractDetails = issueToSubContractorRepo.getIssueToSubContractorDetails(orgId,
				fromdate, todate, status, routeCardNo);
		return getIssueToSubContractorDetails(issueToSubContractDetails);
	}

	private List<Map<String, Object>> getIssueToSubContractorDetails(Set<Object[]> issueToSubContractDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : issueToSubContractDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgid", ch[0] != null ? ch[0].toString() : "");
			map.put("docid", ch[1] != null ? ch[1].toString() : "");
			map.put("docdate", ch[2] != null ? ch[2].toString() : "");
			map.put("routecardno", ch[3] != null ? ch[3].toString() : "");
			map.put("customername", ch[4] != null ? ch[4].toString() : "");
			map.put("department", ch[5] != null ? ch[5].toString() : "");
			map.put("status", ch[6] != null ? ch[6].toString() : "");
			map.put("item", ch[7] != null ? ch[7].toString() : "");
			map.put("itemdesc", ch[8] != null ? ch[8].toString() : "");
			map.put("process", ch[9] != null ? ch[9].toString() : "");
			map.put("quantity", ch[10] != null ? ch[10].toString() : "");
			map.put("issueToSubContractorId", ch[11] != null ? ch[11].toString() : "");

			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractEnquiryDetails(Long orgId, String fromdate, String todate,
			String subContractorName) {
		Set<Object[]> subContractEnquiryDetails = subContractEnquiryRepo.getSubContractEnquiryDetails(orgId, fromdate,
				todate, subContractorName);
		return getSubContractEnquiryDetails(subContractEnquiryDetails);
	}

	private List<Map<String, Object>> getSubContractEnquiryDetails(Set<Object[]> subContractEnquiryDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : subContractEnquiryDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgid", ch[0] != null ? ch[0].toString() : "");
			map.put("subcontractorenquiryid", ch[1] != null ? ch[1].toString() : "");
			map.put("docid", ch[2] != null ? ch[2].toString() : "");
			map.put("docdate", ch[3] != null ? ch[3].toString() : "");
			map.put("enquiryduedate", ch[4] != null ? ch[4].toString() : "");
			map.put("subcontractorname", ch[5] != null ? ch[5].toString() : "");
			map.put("routecardno", ch[6] != null ? ch[6].toString() : "");
			map.put("part", ch[7] != null ? ch[7].toString() : "");
			map.put("partdesc", ch[8] != null ? ch[8].toString() : "");
			map.put("process", ch[9] != null ? ch[9].toString() : "");

			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<Map<String, Object>> getRecieveFromSubContractDetails(Long orgId, String fromdate, String todate,
			String status, String routeCardNo) {
		Set<Object[]> recieveFromSubContractDetails = recieveFromSubcontractRepo.getRecieveFromSubContractDetails(orgId,
				fromdate, todate, status, routeCardNo);
		return getRecieveFromSubContractDetails(recieveFromSubContractDetails);
	}

	private List<Map<String, Object>> getRecieveFromSubContractDetails(Set<Object[]> recieveFromSubContractDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : recieveFromSubContractDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : "");
			map.put("docdate", ch[1] != null ? ch[1].toString() : "");
			map.put("routecardno", ch[2] != null ? ch[2].toString() : "");
			map.put("issueno", ch[3] != null ? ch[3].toString() : "");
			map.put("issuedate", ch[4] != null ? ch[4].toString() : "");
			map.put("jobworkoutorder", ch[5] != null ? ch[5].toString() : "");
			map.put("dcno", ch[6] != null ? ch[6].toString() : "");
			map.put("department", ch[7] != null ? ch[7].toString() : "");
			map.put("contractorid", ch[8] != null ? ch[8].toString() : "");
			map.put("contractorname", ch[9] != null ? ch[9].toString() : "");
			map.put("invoiceno", ch[10] != null ? ch[10].toString() : "");
			map.put("testcertificate", ch[11] != null ? ch[11].toString() : "");
			map.put("orgid", ch[12] != null ? ch[12].toString() : "");
			map.put("status", ch[13] != null ? ch[13].toString() : "");
			map.put("recieveFromSubContractId", ch[14] != null ? ch[14].toString() : "");
			map.put("issueToSubContractorId", ch[15] != null ? ch[15].toString() : "");

			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<Map<String, Object>> getDeliveryChallanSubContractorReport(Long orgId, String fromDate, String toDate,
			String routeCardNo) {

		Set<Object[]> reportData = issueToSubContractorRepo.getDeliveryChallanSubContractorReport(orgId, fromDate,
				toDate, routeCardNo);

		return mapDeliveryChallanSubContractorReport(reportData);
	}

	private List<Map<String, Object>> mapDeliveryChallanSubContractorReport(Set<Object[]> reportData) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {

			Map<String, Object> map = new HashMap<>();

			map.put("dcId", ch[0]);
			map.put("dcNo", ch[1]);
			map.put("dcDate", ch[2]);
			map.put("scIssueNo", ch[3]);
			map.put("routeCardNo", ch[4]);
			map.put("customerName", ch[5]);
			map.put("subContractorName", ch[6]);
			map.put("vehicleNo", ch[7]);
			map.put("dueDate", ch[8]);
			map.put("dispatchThrough", ch[9]);

			map.put("item", ch[10]);
			map.put("itemDesc", ch[11]);
			map.put("process", ch[12]);
			map.put("quantity", ch[13]);
			map.put("unit", ch[14]);
			map.put("weight", ch[15]);

			list.add(map);
		}

		return list;
	}

	// SubContractor Invoice Report
	@Override
	public List<Map<String, Object>> getSubContractorInvoiceReport(Long orgId, String fromDate, String toDate,
			String routeCardNo) {

		Set<Object[]> reportData = issueToSubContractorRepo.getSubContractorInvoiceReport(orgId, fromDate, toDate,
				routeCardNo);

		return mapSubContractorInvoiceReport(reportData);
	}

	private List<Map<String, Object>> mapSubContractorInvoiceReport(Set<Object[]> reportData) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {

			Map<String, Object> map = new HashMap<>();

			map.put("dcId", ch[0]);
			map.put("dcNo", ch[1]);
			map.put("dcDate", ch[2]);
			map.put("scIssueNo", ch[3]);
			map.put("routeCardNo", ch[4]);
			map.put("customerName", ch[5]);
			map.put("subContractorName", ch[6]);
			map.put("subContractorAddress", ch[7]);
			map.put("vehicleNo", ch[8]);
			map.put("dispatchThrough", ch[9]);

			map.put("item", ch[10]);
			map.put("itemDesc", ch[11]);
			map.put("process", ch[12]);
			map.put("quantity", ch[13]);
			map.put("unit", ch[14]);
			map.put("weight", ch[15]);
			map.put("remarks", ch[16]);

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutDetails(Long orgId, String contractorName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = jobWorkOutRepo.getJobWorkOutDetails(orgId, contractorName, fromDate, toDate, branchCode);
		return getJobWorkOutDetails(chType);
	}

	private List<Map<String, Object>> getJobWorkOutDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobWorkOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("jobWorkOrderDate", ch[1] != null ? ch[1].toString() : "");
			map.put("dcNo", ch[2] != null ? ch[2].toString() : "");
			map.put("poNo", ch[3] != null ? ch[3].toString() : "");
			map.put("quotationNo", ch[4] != null ? ch[4].toString() : "");
			map.put("routeCardNo", ch[5] != null ? ch[5].toString() : "");
			map.put("contractorCode", ch[6] != null ? ch[6].toString() : "");
			map.put("contractorName", ch[7] != null ? ch[7].toString() : "");
			map.put("dispatchedThrough", ch[8] != null ? ch[8].toString() : "");
			map.put("durationOfProcess", ch[9] != null ? ch[9].toString() : "");
			map.put("taxType", ch[10] != null ? ch[10].toString() : "");
			map.put("part", ch[11] != null ? ch[11].toString() : "");
			map.put("partDesc", ch[12] != null ? ch[12].toString() : "");
			map.put("process", ch[13] != null ? ch[13].toString() : "");
			map.put("quantityNos", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO);
			map.put("rate", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO);
			map.put("taxCode", ch[16] != null ? ch[16].toString() : "");
			map.put("taxAmt", ch[17] != null ? new BigDecimal(ch[17].toString()) : BigDecimal.ZERO);
			map.put("discount", ch[18] != null ? new BigDecimal(ch[18].toString()) : BigDecimal.ZERO);
			map.put("grossAmt", ch[19] != null ? new BigDecimal(ch[19].toString()) : BigDecimal.ZERO);
			map.put("netAmount", ch[20] != null ? new BigDecimal(ch[20].toString()) : BigDecimal.ZERO);
			map.put("discountAmount", ch[21] != null ? new BigDecimal(ch[21].toString()) : BigDecimal.ZERO);
			map.put("totalAmount", ch[22] != null ? new BigDecimal(ch[22].toString()) : BigDecimal.ZERO);

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutSummaryDetails(Long orgId, String contractorName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = jobWorkOutRepo.getJobWorkOutSummaryDetails(orgId, contractorName, fromDate, toDate,
				branchCode);
		return getJobWorkOutSummaryDetails(chType);
	}

	private List<Map<String, Object>> getJobWorkOutSummaryDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobworkorderno", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("jobworkorderdate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("dcno", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("pono", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("quotationno", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("routecardno", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("contractorcode", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("contractorname", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("dispatchedthrough", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("durationofprocess", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("taxtype", ch[10] != null ? ch[10].toString() : ""); // 10

			map.put("totalgrossamt", ch[11] != null ? new BigDecimal(ch[11].toString()) : BigDecimal.ZERO); // 11
			map.put("totaltax", ch[12] != null ? new BigDecimal(ch[12].toString()) : BigDecimal.ZERO); // 12
			map.put("totalamount", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13

			map.put("igst", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 14
			map.put("cgst", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 15
			map.put("sgst", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 16

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractQuotationDetailsReport(Long orgId, String branchCode,
			String subContractName, String fromDate, String toDate) {
		Set<Object[]> quotationDetailsReport = subContractQuotationRepo.getSubContractQuotationDetailsReport(orgId,
				branchCode, subContractName, fromDate, toDate);
		return getSubContractQuotationDetailsReport(quotationDetailsReport);
	}

	private List<Map<String, Object>> getSubContractQuotationDetailsReport(Set<Object[]> quotationDetailsReport) {
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
			List1.add(map);
		}
		return List1;
	}

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateSubContractQuotation(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		SubContractQuotationVO inprocessInspectionVO = subContractQuotationRepo.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		inprocessInspectionVO = subContractQuotationRepo.save(inprocessInspectionVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<SubContractorQuotationAttachmentVO> oldDocs = subContractorQuotationAttachmentRepo
				.findBySubContractQuotationVO(inprocessInspectionVO);
		subContractorQuotationAttachmentRepo.deleteAll(oldDocs);

		if (inprocessInspectionVO.getDocuments() != null) {
			inprocessInspectionVO.getDocuments().clear();
		} else {
			inprocessInspectionVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (SubContractorQuotationAttachmentVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(inprocessInspectionVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("inprocessInspectionVO", inprocessInspectionVO);

		return response;
	}

	private void replaceDocuments(SubContractQuotationVO inprocessInspection, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(inprocessInspection, files, docFolder, docId);
	}

	private void saveFiles(SubContractQuotationVO inprocessInspection, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		try {
			createDirectory(docFolder);

			for (MultipartFile file : files) {

				String originalName = file.getOriginalFilename();

				if (originalName == null) {
					originalName = "file";
				}

				// Extract extension
				String extension = "";
				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				// New file name → original_docId.ext
				String fileName = originalName + "_" + docId + extension;

				Path filePath = docFolder.resolve(fileName);

				try (InputStream is = file.getInputStream()) {
					Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/issuetosubcontractor/files/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				SubContractorQuotationAttachmentVO attach = new SubContractorQuotationAttachmentVO();
				attach.setSubContractQuotationVO(inprocessInspection);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (inprocessInspection.getDocuments() == null) {
					inprocessInspection.setDocuments(new ArrayList<>());
				}

				inprocessInspection.getDocuments().add(attach);
			}

//Save vehicle once
//			enquiryRepo.save(enquiry);

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
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFile(request, "/api/issuetosubcontractor/files/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException, java.io.IOException {

		String uri = request.getRequestURI();

//Remove API prefix
		String relativePath = uri.replace(apiPrefix, "");

//Decode URL
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

//If DB path contains /uploads, ensure consistency
		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
		Path filePath = baseDir.resolve(relativePath).normalize();

//🔐 Security check
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

	@Override
	public List<ImageResponseDTO> getSubContractQuotationImages(Long id) throws Exception {

		SubContractQuotationVO record = subContractQuotationRepo.getAllSubContractQuotationById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<SubContractorQuotationAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (SubContractorQuotationAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			ImageResponseDTO dto = new ImageResponseDTO();
			dto.setFileName(attachment.getFilename());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

}
