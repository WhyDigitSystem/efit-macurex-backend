package com.efitops.basesetup.service;

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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryDetailsDTO;
import com.efitops.basesetup.dto.EnquirySummaryDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.QuotationDTO;
import com.efitops.basesetup.dto.QuotationDetailsDTO;
import com.efitops.basesetup.dto.WorkOrderDTO;
import com.efitops.basesetup.dto.WorkOrderDetailsDTO;
import com.efitops.basesetup.dto.WorkOrderTermsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EnquiryAttachmentVO;
import com.efitops.basesetup.entity.EnquiryDetailsVO;
import com.efitops.basesetup.entity.EnquirySummaryVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.PendingWorkOrderDetailsVO;
import com.efitops.basesetup.entity.QuotationAttachmentVO;
import com.efitops.basesetup.entity.QuotationDetailsVO;
import com.efitops.basesetup.entity.QuotationVO;
import com.efitops.basesetup.entity.QuoteRevisionVO;
import com.efitops.basesetup.entity.WorkOrderDetailsVO;
import com.efitops.basesetup.entity.WorkOrderTermsVO;
import com.efitops.basesetup.entity.WorkOrderVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.EnquiryAttachmentRepo;
import com.efitops.basesetup.repo.EnquiryDetailsRepo;
import com.efitops.basesetup.repo.EnquiryRepo;
import com.efitops.basesetup.repo.EnquirySummaryRepo;
import com.efitops.basesetup.repo.PendingWorkOrderDetailsRepo;
import com.efitops.basesetup.repo.QuotationAttachmentRepo;
import com.efitops.basesetup.repo.QuotationDetailsRepo;
import com.efitops.basesetup.repo.QuotationRepo;
import com.efitops.basesetup.repo.QuoteRevisionRepo;
import com.efitops.basesetup.repo.WorkOrderDetailsRepo;
import com.efitops.basesetup.repo.WorkOrderRepo;
import com.efitops.basesetup.repo.WorkOrderTermsRepo;

@Service
public class CustomerEnquiryServiceImpl implements CustomerEnquiryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CustomerEnquiryServiceImpl.class);

	@Autowired
	EnquiryRepo enquiryRepo;

	@Autowired
	EnquiryDetailsRepo enquiryDetailsRepo;

	@Autowired
	EnquirySummaryRepo enquirySummaryRepo;

	@Autowired
	QuotationRepo quotationRepo;

	@Autowired
	QuotationDetailsRepo quotationDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	WorkOrderRepo workOrderRepo;

	@Autowired
	WorkOrderDetailsRepo workOrderDetailsRepo;

	@Autowired
	WorkOrderTermsRepo workOrderTermsRepo;

	@Autowired
	PendingWorkOrderDetailsRepo pendingWorkOrderDetailsRepo;

	@Autowired
	QuoteRevisionRepo quoteRevisionRepo;

	@Autowired
	EnquiryAttachmentRepo enquiryAttachmentRepo;

	@Autowired
	QuotationAttachmentRepo quotationAttachmentRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;


	// Enquiry

	@Override
	public Map<String, Object> createUpdateEnquiry(EnquiryDTO enquiryDTO) throws ApplicationException {
		EnquiryVO enquiryVO = new EnquiryVO();
		String message;
		String screenCode = "ENY";
		EnquiryVO oldEnquiry = null;
		if (ObjectUtils.isNotEmpty(enquiryDTO.getId())) {
			
			oldEnquiry = enquiryRepo.findById(enquiryDTO.getId())
		            .orElseThrow(() -> new ApplicationException("Enquiry master not found"));

			oldEnquiry.getEnquiryDetailsVO().size(); // load
		    entityManager.detach(oldEnquiry); // detach snapshot
		    
			enquiryVO = enquiryRepo.findById(enquiryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Enquiry details"));
			message = "Enquiry Updated Successfully";
			enquiryVO.setUpdatedBy(enquiryDTO.getCreatedBy());

		} else {

			String docId = enquiryRepo.getEnquiryDocId(enquiryDTO.getOrgId(), enquiryDTO.getFinYear(),
					enquiryDTO.getBranchCode(), screenCode);
			enquiryVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(enquiryDTO.getOrgId(), enquiryDTO.getFinYear(),
							enquiryDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			enquiryVO.setCreatedBy(enquiryDTO.getCreatedBy());
			enquiryVO.setUpdatedBy(enquiryDTO.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdatedEnquiryVOFromEnquiryDTO(enquiryDTO, enquiryVO);
		commonNotificationService.generateNotification(enquiryVO.getScreenCode(), enquiryVO.getId(), oldEnquiry, enquiryVO);

		enquiryRepo.save(enquiryVO);
		Map<String, Object> response = new HashMap<>();
		response.put("enquiryVO", enquiryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedEnquiryVOFromEnquiryDTO(EnquiryDTO enquiryDTO, EnquiryVO enquiryVO) {
		enquiryVO.setEnquiryType(enquiryDTO.getEnquiryType());
		enquiryVO.setCustomer(enquiryDTO.getCustomer());
		enquiryVO.setCustomerCode(enquiryDTO.getCustomerCode());
		enquiryVO.setEnquiryDueDate(enquiryDTO.getEnquiryDueDate());
		enquiryVO.setContactName(enquiryDTO.getContactName());
		enquiryVO.setContactNo(enquiryDTO.getContactNo());
		enquiryVO.setOrgId(enquiryDTO.getOrgId());
		enquiryVO.setBranch(enquiryDTO.getBranch());
		enquiryVO.setBranchCode(enquiryDTO.getBranchCode());
		enquiryVO.setFinYear(enquiryDTO.getFinYear());
		enquiryVO.setActive(enquiryDTO.isActive());
		enquiryVO.setCreatedBy(enquiryDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(enquiryDTO.getId())) {
			List<EnquiryDetailsVO> materialDetailVO1 = enquiryDetailsRepo.findByEnquiryVO(enquiryVO);
			enquiryDetailsRepo.deleteAll(materialDetailVO1);

			List<EnquirySummaryVO> materialDetailVO2 = enquirySummaryRepo.findByEnquiryVO(enquiryVO);
			enquirySummaryRepo.deleteAll(materialDetailVO2);
		}

		List<EnquiryDetailsVO> enquiryDetailsVOs = new ArrayList<>();
		for (EnquiryDetailsDTO enquiryDetailsDTO : enquiryDTO.getEnquiryDetailsDTO()) {
			EnquiryDetailsVO enquiryDetailsVO = new EnquiryDetailsVO();
			enquiryDetailsVO.setPartCode(enquiryDetailsDTO.getPartCode());
			enquiryDetailsVO.setPartDescription(enquiryDetailsDTO.getPartDescription());
			enquiryDetailsVO.setDrawingNo(enquiryDetailsDTO.getDrawingNo());
			enquiryDetailsVO.setRevisionNo(enquiryDetailsDTO.getRevisionNo());
			enquiryDetailsVO.setUnit(enquiryDetailsDTO.getUnit());
			enquiryDetailsVO.setRequireQty(enquiryDetailsDTO.getRequireQty());
			enquiryDetailsVO.setDeliveryDate(enquiryDetailsDTO.getDeliveryDate());
			enquiryDetailsVO.setRemarks(enquiryDetailsDTO.getRemarks());
			enquiryDetailsVO.setEnquiryVO(enquiryVO);
			enquiryDetailsVOs.add(enquiryDetailsVO);
		}
		enquiryVO.setEnquiryDetailsVO(enquiryDetailsVOs);

		List<EnquirySummaryVO> enquirySummaryVOs = new ArrayList<>();
		for (EnquirySummaryDTO enquirySummaryDTO : enquiryDTO.getEnquirySummaryDTO()) {
			EnquirySummaryVO enquirySummaryVO = new EnquirySummaryVO();
			enquirySummaryVO.setAnyAdditionalInverstment(enquirySummaryDTO.getAnyAdditionalInverstment());
			enquirySummaryVO.setAdditionalManPower(enquirySummaryDTO.getAdditionalManPower());
			enquirySummaryVO.setTimeFrame(enquirySummaryDTO.getTimeFrame());
			enquirySummaryVO.setExpectedTimeForDeliverySample(enquirySummaryDTO.getExpectedTimeForDeliverySample());
			enquirySummaryVO.setRegularProduction(enquirySummaryDTO.getRegularProduction());
			enquirySummaryVO.setInitialReviewComments(enquirySummaryDTO.getInitialReviewComments());
			enquirySummaryVO.setDetailReview(enquirySummaryDTO.getDetailReview());
			enquirySummaryVO.setConclusion(enquirySummaryDTO.getConclusion());
			enquirySummaryVO.setRemarks(enquirySummaryDTO.getRemarks());
			enquirySummaryVO.setEnquiryVO(enquiryVO);
			enquirySummaryVOs.add(enquirySummaryVO);
		}
		enquiryVO.setEnquirySummaryVO(enquirySummaryVOs);
	}

	@Override
	public List<EnquiryVO> getAllEnquiryByOrgId(Long orgId, String finYear, String branchCode) {

		return enquiryRepo.getAllEnquiryByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public EnquiryVO getEnquiryById(Long id) {

		return enquiryRepo.getEnquiryById(id);
	}

	@Override
	public String getEnquiryDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "ENY";
		return enquiryRepo.getEnquiryDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getCustomerNameAndCode(Long orgId) {
		Set<Object[]> chType = enquiryRepo.getCustomerNameAndCode(orgId);
		return getCustomerName(chType);
	}

	private List<Map<String, Object>> getCustomerName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("customer", ch[0] != null ? ch[0].toString() : "");
			map.put("customerCode", ch[1] != null ? ch[1].toString() : "");
			map.put("currency", ch[2] != null ? ch[2].toString() : "");
			map.put("taxCode", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getContactNameAndNo(Long orgId, String partyCode) {
		Set<Object[]> chType = enquiryRepo.getContactNameAndNo(orgId, partyCode);
		return getContactName(chType);
	}

	private List<Map<String, Object>> getContactName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("contactName", ch[0] != null ? ch[0].toString() : "");
			map.put("contactNo", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNoAndDescription(Long orgId) {
		Set<Object[]> chType = enquiryRepo.getPartNoAndDescription(orgId);
		return getPartNo(chType);
	}

	private List<Map<String, Object>> getPartNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("unit", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDrawingNoAndRevisionNo(String partNo, Long orgId) {
		Set<Object[]> chType = enquiryRepo.getDrawingNoAndRevisionNo(partNo, orgId);
		return getDrawingNo(chType);
	}

	private List<Map<String, Object>> getDrawingNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingNo", ch[0] != null ? ch[0].toString() : "");
			map.put("revisionNo", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// Quotation

//	@Override
//	public Map<String, Object> createUpdateQuotation(QuotationDTO quotationDTO) throws ApplicationException {
//		QuotationVO quotationVO = new QuotationVO();
//		String message;
//		String screenCode = "QOT";
//		if (ObjectUtils.isNotEmpty(quotationDTO.getId())) {
//			quotationVO = quotationRepo.findById(quotationDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Quotation Enquiry details"));
//			message = "Quotation Updated Successfully";
//			quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());
//
//		} else {
//
//			String docId = quotationRepo.getQuotationDocId(quotationDTO.getOrgId(), quotationDTO.getFinYear(),
//					quotationDTO.getBranchCode(), screenCode);
//			quotationVO.setDocId(docId);
//
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(quotationDTO.getOrgId(), quotationDTO.getFinYear(),
//							quotationDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			quotationVO.setCreatedBy(quotationDTO.getCreatedBy());
//			quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());
//
//			message = "Quotation Created Successfully";
//		}
//		
//		createUpdatedQuotationVOFromQuotationDTO(quotationDTO, quotationVO);
//		quotationRepo.save(quotationVO);
//		Map<String, Object> response = new HashMap<>();
//		response.put("quotationVO", quotationVO);
//		response.put("message", message);
//		return response;
//	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateQuotation(@Valid QuotationDTO quotationDTO) throws ApplicationException {

		String screenCode = "QOT";
		QuotationVO quotationVO = new QuotationVO();
		String iterationValue = "";
		String message;
		QuotationVO oldQuotation = null;


		if (ObjectUtils.isNotEmpty(quotationDTO.getId())) {

			oldQuotation = quotationRepo.findById(quotationDTO.getId())
		            .orElseThrow(() -> new ApplicationException("Quotation master not found"));

			oldQuotation.getQuotationDetailsVO().size(); // load
			oldQuotation.getDocuments().size(); // load

		    entityManager.detach(oldQuotation); // detach snapshot
		    
			quotationVO = quotationRepo.findById(quotationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Quotation Not Found!"));
			quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());

			String iteration = quotationRepo.getEnquiryNameId(quotationDTO.getOrgId(), quotationDTO.getCustomerName(),
					quotationDTO.getId());

			System.out.println("Original iteration: " + iteration);
			if (iteration == null || iteration.isEmpty()) {
			    throw new ApplicationException("Iteration value is null. Cannot process quotation update.");
			}

			Pattern pattern = Pattern.compile("([A-Z0-9]+)(\\d+)-QOT(\\d+)");
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

				iterationValue = prefix + numberStr + "-QOT" + qotNumber;
				System.out.println("Final Iteration Value: " + iterationValue);

				quotationVO.setIterations(iterationValue);

				// Update count
				int count = quotationRepo.getCount(quotationDTO.getOrgId(), quotationDTO.getCustomerName(),
						quotationDTO.getId());
				count++;
				quotationVO.setCount(count);

				List<QuotationDetailsDTO> quotationDetailsVO1 = quotationDTO.getQuotationDetailsDTO();
				if (quotationDetailsVO1 != null && !quotationDetailsVO1.isEmpty()) {
					for (QuotationDetailsDTO detailsVO : quotationDetailsVO1) {
						QuoteRevisionVO stockDetailsVOFrom = new QuoteRevisionVO();
						stockDetailsVOFrom.setOrgId(quotationVO.getOrgId());
						stockDetailsVOFrom.setDocId(quotationVO.getDocId());
						stockDetailsVOFrom.setDocDate(quotationVO.getDocDate());
						stockDetailsVOFrom.setSourceId(quotationVO.getId());
						stockDetailsVOFrom.setSourceDocId(quotationVO.getEnquiryNo());
						stockDetailsVOFrom.setSourceDocDate(quotationVO.getEnquiryDate());
						stockDetailsVOFrom.setCustomerName(quotationVO.getCustomerName());
						stockDetailsVOFrom.setCustomerCode(quotationVO.getCustomerId());
						stockDetailsVOFrom.setCreatedBy(quotationVO.getCreatedBy());
						stockDetailsVOFrom.setBranch(quotationVO.getBranch());
						stockDetailsVOFrom.setBranchCode(quotationVO.getBranchCode());
						stockDetailsVOFrom.setValidTill(quotationVO.getValidTill());
//							stockDetailsVOFrom.setActive(true);
						stockDetailsVOFrom.setFinYear(quotationVO.getFinYear());
						stockDetailsVOFrom.setContactNo(quotationVO.getContactNo());
						stockDetailsVOFrom.setStatus(quotationVO.getStatus());
						stockDetailsVOFrom.setProducationManager(quotationVO.getProductionManager());
						stockDetailsVOFrom.setKindAttention(quotationVO.getKindAttention());
						stockDetailsVOFrom.setUpdatedBy(quotationVO.getUpdatedBy());
						stockDetailsVOFrom.setIterations(quotationVO.getIterations());
						stockDetailsVOFrom.setCount(quotationVO.getCount());
						stockDetailsVOFrom.setSourceScreenCode(quotationVO.getScreenCode());
						stockDetailsVOFrom.setSourceScreenName(quotationVO.getScreenName());
						stockDetailsVOFrom.setGrossAmount(quotationVO.getGrossAmount());
						stockDetailsVOFrom.setDiscount(detailsVO.getDiscount());
						stockDetailsVOFrom.setNetAmount(quotationVO.getNetAmount());

						stockDetailsVOFrom.setPartNo(detailsVO.getPartCode());
						stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescription());
						stockDetailsVOFrom.setSellingPrice(detailsVO.getUnitPrice());
						stockDetailsVOFrom.setQty(detailsVO.getQtyOffered());
						stockDetailsVOFrom.setPrice(detailsVO.getUnitPrice().multiply(detailsVO.getQtyOffered()));

						BigDecimal discountAmount = detailsVO.getDiscount()
								.multiply(detailsVO.getUnitPrice().multiply(detailsVO.getQtyOffered()))
								.divide(BigDecimal.valueOf(100));
						stockDetailsVOFrom.setDiscountAmount(discountAmount);
						stockDetailsVOFrom.setAmount((detailsVO.getUnitPrice().multiply(detailsVO.getQtyOffered())
								.subtract(discountAmount)));

						quoteRevisionRepo.save(stockDetailsVOFrom);
					}

				}
			} else {
				throw new IllegalArgumentException("Invalid iteration format: " + iteration);
			}
			createUpdatedQuotationVOFromQuotationDTO(quotationDTO, quotationVO);
			message = "Quotation Updated Successfully";
		} else {

			// GETDOCID API

			String docId = quotationRepo.getQuotationDocId(quotationDTO.getOrgId(), quotationDTO.getFinYear(),
					quotationDTO.getBranchCode(), screenCode);
			quotationVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(quotationDTO.getOrgId(), quotationDTO.getFinYear(),
							quotationDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			String iteration = quotationRepo.getEnquiryIdIteration(quotationDTO.getOrgId(),
					quotationDTO.getCustomerName(), quotationDTO.getEnquiryNo());

			System.out.println(iteration);
//               int number = 1;	
			quotationVO.setIterations(iteration);
			quotationVO.setCount(1);
			quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());
			quotationVO.setCreatedBy(quotationDTO.getCreatedBy());
			message = "Quotation Created Successfully";

			createUpdatedQuotationVOFromQuotationDTO(quotationDTO, quotationVO);
			QuotationVO savedQuotationVO = quotationRepo.save(quotationVO);
			List<QuotationDetailsVO> quotationDetailsVO1 = savedQuotationVO.getQuotationDetailsVO();
			if (quotationDetailsVO1 != null && !quotationDetailsVO1.isEmpty()) {
				for (QuotationDetailsVO detailsVO : quotationDetailsVO1) {
					QuoteRevisionVO stockDetailsVOFrom = new QuoteRevisionVO();
					stockDetailsVOFrom.setOrgId(quotationVO.getOrgId());
					stockDetailsVOFrom.setDocId(quotationVO.getDocId());
					stockDetailsVOFrom.setDocDate(quotationVO.getDocDate());
					stockDetailsVOFrom.setSourceId(quotationVO.getId());
					stockDetailsVOFrom.setProducationManager(quotationVO.getProductionManager());
					stockDetailsVOFrom.setKindAttention(quotationVO.getKindAttention());
					stockDetailsVOFrom.setCustomerName(quotationVO.getCustomerName());
					stockDetailsVOFrom.setCreatedBy(quotationVO.getCreatedBy());
					stockDetailsVOFrom.setFinYear(quotationVO.getFinYear());
					stockDetailsVOFrom.setContactNo(quotationVO.getContactNo());
					stockDetailsVOFrom.setBranch(quotationVO.getBranch());
					stockDetailsVOFrom.setValidTill(quotationVO.getValidTill());
					stockDetailsVOFrom.setBranchCode(quotationVO.getBranchCode());
					stockDetailsVOFrom.setCustomerCode(quotationVO.getCustomerId());
					stockDetailsVOFrom.setStatus(quotationVO.getStatus());
					stockDetailsVOFrom.setContactNo(quotationVO.getContactNo());
					stockDetailsVOFrom.setSourceDocId(quotationVO.getEnquiryNo());
					stockDetailsVOFrom.setCount(quotationVO.getCount());
					stockDetailsVOFrom.setSourceDocDate(quotationVO.getEnquiryDate());
					stockDetailsVOFrom.setIterations(quotationVO.getIterations());
					stockDetailsVOFrom.setUpdatedBy(quotationVO.getUpdatedBy());
					stockDetailsVOFrom.setSourceScreenCode(quotationVO.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(quotationVO.getScreenName());
					stockDetailsVOFrom.setGrossAmount(quotationVO.getGrossAmount());
					stockDetailsVOFrom.setDiscount(detailsVO.getDiscount());
					stockDetailsVOFrom.setNetAmount(quotationVO.getNetAmount());
					stockDetailsVOFrom.setPartNo(detailsVO.getPartCode());
					stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescription());
					stockDetailsVOFrom.setSellingPrice(detailsVO.getUnitPrice());
					stockDetailsVOFrom.setQty(detailsVO.getQtyOffered());

					stockDetailsVOFrom.setPrice(detailsVO.getBasicPrice());

					stockDetailsVOFrom.setAmount(detailsVO.getQuoteAmount());
					stockDetailsVOFrom.setDiscountAmount(detailsVO.getDiscountAmount());

					quoteRevisionRepo.save(stockDetailsVOFrom);
				}
			}
		}
		commonNotificationService.generateNotification(quotationVO.getScreenCode(), quotationVO.getId(), oldQuotation, quotationVO);

		Map<String, Object> response = new HashMap<>();
		response.put("quotationVO", quotationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedQuotationVOFromQuotationDTO(QuotationDTO quotationDTO, QuotationVO quotationVO) {
		quotationVO.setCustomerName(quotationDTO.getCustomerName());
		quotationVO.setCustomerId(quotationDTO.getCustomerId());
		quotationVO.setEnquiryNo(quotationDTO.getEnquiryNo());
		quotationVO.setEnquiryDate(quotationDTO.getEnquiryDate());
		quotationVO.setValidTill(quotationDTO.getValidTill());
		quotationVO.setKindAttention(quotationDTO.getKindAttention());
		quotationVO.setTaxCode(quotationDTO.getTaxCode());
		quotationVO.setProductionManager(quotationDTO.getProductionManager());
		quotationVO.setCurrency(quotationDTO.getCurrency());
		quotationVO.setContactNo(quotationDTO.getContactNo());
		quotationVO.setOrgId(quotationDTO.getOrgId());
		quotationVO.setBranch(quotationDTO.getBranch());
		quotationVO.setBranchCode(quotationDTO.getBranchCode());
		quotationVO.setFinYear(quotationDTO.getFinYear());
		quotationVO.setActive(quotationDTO.isActive());
		quotationVO.setCreatedBy(quotationDTO.getCreatedBy());

		BigDecimal grocessAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(quotationDTO.getId())) {
			List<QuotationDetailsVO> quotationDetailsVO1 = quotationDetailsRepo.findByQuotationVO(quotationVO);
			quotationDetailsRepo.deleteAll(quotationDetailsVO1);

		}

		List<QuotationDetailsVO> quotationDetailsVOs = new ArrayList<>();
		for (QuotationDetailsDTO quotationDetailsDTO : quotationDTO.getQuotationDetailsDTO()) {
			QuotationDetailsVO quotationDetailsVO = new QuotationDetailsVO();
			quotationDetailsVO.setPartCode(quotationDetailsDTO.getPartCode());
			quotationDetailsVO.setPartDescription(quotationDetailsDTO.getPartDescription());
			quotationDetailsVO.setDrawingNo(quotationDetailsDTO.getDrawingNo());
			quotationDetailsVO.setRevisionNo(quotationDetailsDTO.getRevisionNo());
			quotationDetailsVO.setUnit(quotationDetailsDTO.getUnit());
			
			if (quotationDetailsDTO.getUnitPrice() == null || 
				    quotationDetailsDTO.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
				    
				    throw new IllegalArgumentException("Unit Price cannot be negative or null");
				}

			quotationDetailsVO.setUnitPrice(quotationDetailsDTO.getUnitPrice());
			
			if (quotationDetailsDTO.getQtyOffered() == null || 
				    quotationDetailsDTO.getQtyOffered().compareTo(BigDecimal.ZERO) < 0) {
				    
				    throw new IllegalArgumentException("Quantity Offered cannot be negative or null");
				}

				if (quotationDetailsDTO.getDiscount() == null || 
				    quotationDetailsDTO.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
				    
				    throw new IllegalArgumentException("Discount cannot be negative or null");
				}

				quotationDetailsVO.setQtyOffered(quotationDetailsDTO.getQtyOffered());
				quotationDetailsVO.setDiscount(quotationDetailsDTO.getDiscount());
			quotationDetailsVO.setQtyOffered(quotationDetailsDTO.getQtyOffered());
			quotationDetailsVO.setDiscount(quotationDetailsDTO.getDiscount());

			BigDecimal discountamount;

			BigDecimal amountSet = quotationDetailsDTO.getUnitPrice().multiply(quotationDetailsDTO.getQtyOffered());
			quotationDetailsVO.setBasicPrice(amountSet);

			grocessAmount = grocessAmount.add(amountSet);

			discountamount = quotationDetailsVO.getBasicPrice().multiply(quotationDetailsDTO.getDiscount())
					.divide(BigDecimal.valueOf(100));
			quotationDetailsVO.setDiscountAmount(discountamount);
			quotationDetailsVO.setQuoteAmount(
					quotationDetailsVO.getBasicPrice().subtract(quotationDetailsVO.getDiscountAmount()));

			netAmount = netAmount.add(quotationDetailsVO.getQuoteAmount());
			quotationDetailsVO.setDeliveryDate(quotationDetailsDTO.getDeliveryDate());
			quotationDetailsVO.setQuotationVO(quotationVO);
			quotationDetailsVOs.add(quotationDetailsVO);
		}
		quotationVO.setGrossAmount(grocessAmount);
		quotationVO.setNetAmount(netAmount);

//		quotationVO.setAmountInWords(amountInWordsConverterService.convert(quotationVO.getNetAmount().longValue()));
		if (quotationVO.getNetAmount() != null &&
			    quotationVO.getNetAmount().compareTo(BigDecimal.ZERO) > 0) {

			    quotationVO.setAmountInWords(
			        amountInWordsConverterService.convert(
			            quotationVO.getNetAmount().longValue()
			        )
			    );
			} else {
			    quotationVO.setAmountInWords("ZERO");
			}
		quotationVO.setQuotationDetailsVO(quotationDetailsVOs);
	}

	@Override
	public List<QuotationVO> getAllQuotationByOrgId(Long orgId, String finYear, String branchCode) {

		return quotationRepo.getAllQuotationByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public QuotationVO getQuotationById(Long id) {

		return quotationRepo.getQuotationById(id);
	}

	@Override
	public String getQuotationDocId(Long orgId, String finYear, String branchCode) {

		String screenCode = "QOT";

		return quotationRepo.getQuotationDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public String getEnquiryIdIteration(Long orgId, String clientName, String enquiryNo) {
		return quotationRepo.getEnquiryIdIteration(orgId, clientName, enquiryNo);

	}

	@Override
	public List<Map<String, Object>> getEnquiryNoAndDate(Long orgId, String customerCode) {
		Set<Object[]> chType = quotationRepo.getEnquiryNoAndDate(orgId, customerCode);
		return getEnquiryNo(chType);
	}

	private List<Map<String, Object>> getEnquiryNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("enquiryDocNo", ch[0] != null ? ch[0].toString() : "");
			map.put("enquiryDocDate", ch[1] != null ? ch[1].toString() : "");
			map.put("kindAttention", ch[2] != null ? ch[2].toString() : "");
			map.put("contactNo", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getProductionManager(Long orgId) {
		Set<Object[]> chType = quotationRepo.getProductionManager(orgId);
		return getProduction(chType);
	}

	private List<Map<String, Object>> getProduction(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("productionManager", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNoAndPartDesBasedOnEnquiryNo(Long orgId, String docId,
			String customerCode) {
		Set<Object[]> chType = quotationRepo.getPartNoAndPartDesBasedOnEnquiryNo(orgId, docId, customerCode);
		return getPartNoAndPartDes(chType);
	}

	private List<Map<String, Object>> getPartNoAndPartDes(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("drawingNo", ch[2] != null ? ch[2].toString() : "");
			map.put("revisionNo", ch[3] != null ? ch[3].toString() : "");
			map.put("unit", ch[4] != null ? ch[4].toString() : "");
			map.put("qtyOffered", ch[5] != null ? ch[5].toString() : "");
			map.put("price", ch[6] != null ? ch[6].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// WorkOrder

	@Override
	public Map<String, Object> createUpdateWorkOrder(WorkOrderDTO workOrderDTO) throws ApplicationException {
		WorkOrderVO workOrderVO = new WorkOrderVO();
		String message;
		String screenCode = "WOP";
		WorkOrderVO oldWorkOrder = null;
		if (ObjectUtils.isNotEmpty(workOrderDTO.getId())) {
			
			oldWorkOrder = workOrderRepo.findById(workOrderDTO.getId())
		            .orElseThrow(() -> new ApplicationException("Quotation master not found"));

			oldWorkOrder.getWorkOrderDetailsVO().size(); // load
			oldWorkOrder.getWorkOrderTermsVO().size(); // load

		    entityManager.detach(oldWorkOrder); // detach snapshot
			
			workOrderVO = workOrderRepo.findById(workOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("WorkOrder Enquiry details"));
			workOrderVO.setUpdatedBy(workOrderDTO.getCreatedBy());
			createUpdatedWorkOrderVOFromWorkOrderDTO(workOrderDTO, workOrderVO);
			message = "WorkOrder Updated Successfully";

		} else {

			String docId = workOrderRepo.getWorkOrderDocId(workOrderDTO.getOrgId(), workOrderDTO.getFinYear(),
					workOrderDTO.getBranchCode(), screenCode);
			workOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(workOrderDTO.getOrgId(), workOrderDTO.getFinYear(),
							workOrderDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			workOrderVO.setCreatedBy(workOrderDTO.getCreatedBy());
			workOrderVO.setUpdatedBy(workOrderDTO.getCreatedBy());
			createUpdatedWorkOrderVOFromWorkOrderDTO(workOrderDTO, workOrderVO);

			message = "WorkOrder Created Successfully";
		}

		WorkOrderVO savedWorkOrder = workOrderRepo.save(workOrderVO);
		commonNotificationService.generateNotification(workOrderVO.getScreenCode(), workOrderVO.getId(), oldWorkOrder, workOrderVO);

		List<WorkOrderDetailsVO> workOrderDetailsVOs = savedWorkOrder.getWorkOrderDetailsVO();
		for (WorkOrderDetailsVO detailsVO : workOrderDetailsVOs) {
			PendingWorkOrderDetailsVO pendingWorkOrder = new PendingWorkOrderDetailsVO();
			pendingWorkOrder.setOrgId(savedWorkOrder.getOrgId());
			pendingWorkOrder.setRefDate(savedWorkOrder.getDocDate());
			pendingWorkOrder.setRefNo(savedWorkOrder.getId());
			pendingWorkOrder.setPlusOrMinus("p");
			pendingWorkOrder.setSourceScreenCode(savedWorkOrder.getScreenCode());
			pendingWorkOrder.setSourceScreenName(savedWorkOrder.getScreenName());
			pendingWorkOrder.setQty(detailsVO.getOrdQty().multiply(BigDecimal.valueOf(1)));
			pendingWorkOrder.setPartno(detailsVO.getPartNo());
			pendingWorkOrder.setPartDesc(detailsVO.getPartName());
			pendingWorkOrder.setCreatedBy(savedWorkOrder.getCreatedBy());
			pendingWorkOrder.setUpdatedBy(savedWorkOrder.getUpdatedBy());
			pendingWorkOrder.setSourceId(savedWorkOrder.getId());
			pendingWorkOrder.setWorkOrderNo(savedWorkOrder.getDocId());
			pendingWorkOrder.setWorkorderdate(savedWorkOrder.getDocDate());
			pendingWorkOrder.setCustomerName(savedWorkOrder.getCustomerName());
			pendingWorkOrder.setCustomerPoNo(savedWorkOrder.getCustomerPoNo());
			pendingWorkOrder.setCustomerCode(savedWorkOrder.getCustomerCode());
			pendingWorkOrderDetailsRepo.save(pendingWorkOrder);

		}
		Map<String, Object> response = new HashMap<>();
		response.put("workOrderVO", workOrderVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedWorkOrderVOFromWorkOrderDTO(WorkOrderDTO workOrderDTO, WorkOrderVO workOrderVO) {
		workOrderVO.setCustomerName(workOrderDTO.getCustomerName());
		workOrderVO.setCustomerCode(workOrderDTO.getCustomerCode());
		workOrderVO.setCustomerPoNo(workOrderDTO.getCustomerPoNo());
		workOrderVO.setQuotationNo(workOrderDTO.getQuotationNo());
		workOrderVO.setQuotationId(workOrderDTO.getQuotationId());
		workOrderVO.setCurrency(workOrderDTO.getCurrency());
		workOrderVO.setCustomerDueDate(workOrderDTO.getCustomerDueDate());
		workOrderVO.setDueDate(workOrderDTO.getDueDate());
		workOrderVO.setProductionMgr(workOrderDTO.getProductionMgr());
		workOrderVO.setCustomerSpecialRequirement(workOrderDTO.getCustomerSpecialRequirement());
		workOrderVO.setCreatedBy(workOrderDTO.getCreatedBy());
		workOrderVO.setOrgId(workOrderDTO.getOrgId());
		workOrderVO.setBranch(workOrderDTO.getBranch());
		workOrderVO.setBranchCode(workOrderDTO.getBranchCode());
		workOrderVO.setFinYear(workOrderDTO.getFinYear());
		workOrderVO.setActive(workOrderDTO.isActive());

		if (ObjectUtils.isNotEmpty(workOrderDTO.getId())) {
			List<WorkOrderDetailsVO> workOrderItemParticularsVO1 = workOrderDetailsRepo.findByWorkOrderVO(workOrderVO);
			workOrderDetailsRepo.deleteAll(workOrderItemParticularsVO1);

			List<WorkOrderTermsVO> workOrderTermsAndConditionsVO1 = workOrderTermsRepo.findByWorkOrderVO(workOrderVO);
			workOrderTermsRepo.deleteAll(workOrderTermsAndConditionsVO1);
		}

		BigDecimal requiredQty;
		List<WorkOrderDetailsVO> workOrderDetailsVOs = new ArrayList<>();
		for (WorkOrderDetailsDTO workOrderDetailsDTO : workOrderDTO.getWorkOrderDetailsDTO()) {
			WorkOrderDetailsVO workOrderDetailsVO = new WorkOrderDetailsVO();
			workOrderDetailsVO.setPartNo(workOrderDetailsDTO.getPartNo());
			workOrderDetailsVO.setPartName(workOrderDetailsDTO.getPartName());
			workOrderDetailsVO.setDrawingNo(workOrderDetailsDTO.getDrawingNo());
			workOrderDetailsVO.setRevisionNo(workOrderDetailsDTO.getRevisionNo());
			workOrderDetailsVO.setUom(workOrderDetailsDTO.getUom());
			workOrderDetailsVO.setOrdQty(workOrderDetailsDTO.getOrdQty());
			workOrderDetailsVO.setFreeQty(workOrderDetailsDTO.getFreeQty());
			workOrderDetailsVO.setAvailableStockQty(workOrderDetailsDTO.getAvailableStockQty());
			requiredQty = workOrderDetailsDTO.getOrdQty().add(workOrderDetailsDTO.getFreeQty())
					.subtract(workOrderDetailsDTO.getAvailableStockQty());
			workOrderDetailsVO.setRequiredQty(requiredQty);
			workOrderDetailsVO.setWorkOrderVO(workOrderVO);
			workOrderDetailsVOs.add(workOrderDetailsVO);
		}
		workOrderVO.setWorkOrderDetailsVO(workOrderDetailsVOs);

		List<WorkOrderTermsVO> workOrderTermsVOs = new ArrayList<>();
		for (WorkOrderTermsDTO workOrderTermsDTO : workOrderDTO.getWorkOrderTermsDTO()) {
			WorkOrderTermsVO workOrderTermsVO = new WorkOrderTermsVO();
			workOrderTermsVO.setTemplate(workOrderTermsDTO.getTemplate());
			workOrderTermsVO.setDescription(workOrderTermsDTO.getDescription());
			workOrderTermsVO.setWorkOrderVO(workOrderVO);
			workOrderTermsVOs.add(workOrderTermsVO);
		}
		workOrderVO.setWorkOrderTermsVO(workOrderTermsVOs);

	}

	@Override
	public List<WorkOrderVO> getAllWorkOrderByOrgId(Long orgId, String finYear, String branchCode) {

		return workOrderRepo.getAllWorkOrderByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public WorkOrderVO getWorkOrderById(Long id) {

		return workOrderRepo.getWorkOrderById(id);
	}

	@Override
	public String getWorkOrderDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "WOP";
		return workOrderRepo.getWorkOrderDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getQuotationNumber(Long orgId, String custmoerId) {
		Set<Object[]> chType = workOrderRepo.getQuotationNumber(orgId, custmoerId);
		return getQuotation(chType);
	}

	private List<Map<String, Object>> getQuotation(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("quotationNo", ch[0] != null ? ch[0].toString() : "");
			map.put("productionmanager", ch[1] != null ? ch[1].toString() : "");
			map.put("quotationId", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getWorkOrderPartNo(Long orgId, String docId, String custmoerId) {
		Set<Object[]> chType = workOrderRepo.getWorkOrderPartNo(orgId, docId, custmoerId);
		return getWorkOrder(chType);
	}

	private List<Map<String, Object>> getWorkOrder(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partCode", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("drawingNo", ch[2] != null ? ch[2].toString() : "");
			map.put("revisionNo", ch[3] != null ? ch[3].toString() : "");
			map.put("uom", ch[4] != null ? ch[4].toString() : "");
			map.put("orderQty", ch[5] != null ? ch[5].toString() : "");
			map.put("customerName", ch[6] != null ? ch[6].toString() : "");
			map.put("customerCode", ch[7] != null ? ch[7].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getWorkOrderShowsDetails(Long orgId, String branchCode, String itemCode) {
		Set<Object[]> chType = workOrderRepo.getWorkOrderShowsDetails(orgId, branchCode, itemCode);
		return getWorkOrderShowsDetails(chType);
	}

	private List<Map<String, Object>> getWorkOrderShowsDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("availableQty", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getWorkOrderReport(Long orgId, String branchCode, String customerCode,
			String status) {
		Set<Object[]> workOrderReport = workOrderRepo.getWorkOrderReport(orgId, branchCode, customerCode, status);
		return getWorkOrderReport(workOrderReport);
	}

	private List<Map<String, Object>> getWorkOrderReport(Set<Object[]> workOrderReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : workOrderReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("customerName", ch[2] != null ? ch[2].toString() : "");
			map.put("customerCode", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNo", ch[4] != null ? ch[4].toString() : "");
			map.put("partNo", ch[5] != null ? ch[5].toString() : "");
			map.put("partName", ch[6] != null ? ch[6].toString() : "");
			map.put("requiredQty", ch[7] != null ? ch[7].toString() : "");
			map.put("packedQty", ch[8] != null ? ch[8].toString() : "");
			map.put("dueDate", ch[9] != null ? ch[9].toString() : "");
			map.put("enquiryId", ch[10] != null ? ch[10].toString() : "");
			map.put("status", ch[11] != null ? ch[11].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getEnquiryDetails(Long orgId, String status, String partyName) {
		Set<Object[]> chType = enquiryRepo.getEnquiryDetails(orgId, status, partyName);
		return getEnquiryDetails(chType);
	}

	private List<Map<String, Object>> getEnquiryDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("enquirydate", ch[1] != null ? ch[1].toString() : "");
			map.put("docid", ch[2] != null ? ch[2].toString() : "");
			map.put("customer", ch[3] != null ? ch[3].toString() : "");
			map.put("contactname", ch[4] != null ? ch[4].toString() : "");
			map.put("status", ch[5] != null ? ch[5].toString() : "");
			map.put("enquiryId", ch[6] != null ? ch[6].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getQuotationByOrgid(Long orgId) {
		Set<Object[]> chType = quotationRepo.findQutationsByOrgId(orgId);
		return getQuotationsByOrgid(chType);
	}

	private List<Map<String, Object>> getQuotationsByOrgid(Set<Object[]> chType) {
		List<Map<String, Object>> List2 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("quotationid", ch[0] != null ? ch[0].toString() : "");
			map.put("docid", ch[1] != null ? ch[1].toString() : "");
			map.put("quotationdate", ch[2] != null ? ch[2].toString() : "");
			map.put("customername", ch[3] != null ? ch[3].toString() : "");
			map.put("enquiryno", ch[4] != null ? ch[4].toString() : "");
			map.put("kindattention", ch[5] != null ? ch[5].toString() : "");

			List2.add(map);
		}
		return List2;
	}

	@Override
	public List<Map<String, Object>> getQuotationDetailsReport(Long orgId, String branchCode, String customerName,
			String fromDate, String toDate, String status) {
		Set<Object[]> quotationDetailsReport = quotationRepo.getQuotationDetailsReport(orgId, branchCode, customerName,
				fromDate, toDate, status);
		return getQuotationDetailsReport(quotationDetailsReport);
	}

	private List<Map<String, Object>> getQuotationDetailsReport(Set<Object[]> quotationDetailsReport) {
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
			map.put("producationManager", ch[17] != null ? ch[17].toString() : "");
			map.put("count", ch[18] != null ? ch[18].toString() : "");
			map.put("validTill", ch[19] != null ? ch[19].toString() : "");
			map.put("status", ch[20] != null ? ch[20].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<QuoteRevisionVO> getCountQuoteRevision(Long orgId, String docId) {

		return quoteRevisionRepo.getCountQuoteRevision(orgId, docId);
	}

	// Enquiry Attachment

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateEnquiry(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		EnquiryVO enquiryVO = enquiryRepo.findByDocId(docId);

		String message = "Enquiry updated successfully";

		// BASIC MAPPING

		enquiryVO = enquiryRepo.save(enquiryVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<EnquiryAttachmentVO> oldDocs = enquiryAttachmentRepo.findByEnquiryVO(enquiryVO);
		enquiryAttachmentRepo.deleteAll(oldDocs);

		if (enquiryVO.getDocuments() != null) {
			enquiryVO.getDocuments().clear();
		} else {
			enquiryVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (EnquiryAttachmentVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(enquiryVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("enquiryVO", enquiryVO);

		return response;
	}

	private void replaceDocuments(EnquiryVO enquiry, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(enquiry, files, docFolder, docId);
	}

	private void saveFiles(EnquiryVO enquiry, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

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
						.path("/api/customerenquiry/files/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				EnquiryAttachmentVO attach = new EnquiryAttachmentVO();
				attach.setEnquiryVO(enquiry);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileype(file.getContentType());
				attach.setFilesize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (enquiry.getDocuments() == null) {
					enquiry.setDocuments(new ArrayList<>());
				}

				enquiry.getDocuments().add(attach);
			}

// Save vehicle once
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFile(request, "/api/customerenquiry/files/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException, java.io.IOException {

		String uri = request.getRequestURI();

// Remove API prefix
		String relativePath = uri.replace(apiPrefix, "");

// Decode URL
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

// If DB path contains /uploads, ensure consistency
		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
		Path filePath = baseDir.resolve(relativePath).normalize();

// 🔐 Security check
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

//quatation
	@Override
	@Transactional
	public Map<String, Object> createUpdateQuotation(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		
		QuotationVO quotationVO = quotationRepo.findByDocId(docId);
	

		String message = "Quotation updated successfully";

		// BASIC MAPPING

		quotationVO = quotationRepo.save(quotationVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<QuotationAttachmentVO> oldDocs = quotationAttachmentRepo.findByQuotationVO(quotationVO);
		quotationAttachmentRepo.deleteAll(oldDocs);

		if (quotationVO.getDocuments() != null) {
			quotationVO.getDocuments().clear();
		} else {
			quotationVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (QuotationAttachmentVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(quotationVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("quotationVO", quotationVO);

		return response;
	}

	private void replaceDocuments(QuotationVO quotation, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(quotation, files, docFolder, docId);
	}

	private void saveFiles(QuotationVO quotation, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

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
						.path("/api/customerenquiry/files/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				QuotationAttachmentVO attach = new QuotationAttachmentVO();
				attach.setQuotationVO(quotation);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileype(file.getContentType());
				attach.setFilesize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (quotation.getDocuments() == null) {
					quotation.setDocuments(new ArrayList<>());
				}

				quotation.getDocuments().add(attach);
			}

//Save vehicle once
//		enquiryRepo.save(enquiry);

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafely1(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectory1(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ImageResponseDTO> getEnquiryImages(Long id) throws Exception {

		EnquiryVO record = enquiryRepo.getAllEnquiryById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<EnquiryAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (EnquiryAttachmentVO attachment : docs) {

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
	
	
	@Override
	public List<Map<String, Object>> getItemDetailsWithoutQuotationId(Long orgId) {
		Set<Object[]> chType = workOrderRepo.getItemDetailsWithoutQuotationId(orgId);
		return getItemDetailsWithoutQuotationId(chType);
	}

	private List<Map<String, Object>> getItemDetailsWithoutQuotationId(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partCode", ch[0] != null ? ch[0].toString() : "");
			map.put("partDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("drawingNo", ch[2] != null ? ch[2].toString() : "");
			map.put("revisionNo", ch[3] != null ? ch[3].toString() : "");
			map.put("uom", ch[4] != null ? ch[4].toString() : "");
			map.put("orderQty", ch[5] != null ? ch[5].toString() : "");

			List1.add(map);
		}
		return List1;
	}

}
