package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentAttachmentResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentContractDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentCustomerResponceDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentDetailsItemResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentDetailsResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentItemDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentResponseDto;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.EnquiryAttachmentResponseDTO;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryDetailsDTO;
import com.efitops.basesetup.dto.EnquiryDetailsReponseDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.EnquiryTermsandCondDTO;
import com.efitops.basesetup.dto.EnquiryTermsandCondResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDetailsDto;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDto;
import com.efitops.basesetup.dto.SalesOrderAmendmentDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentDetailsDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentResponseDTO;
import com.efitops.basesetup.dto.SalesReturnDTO;
import com.efitops.basesetup.dto.SalesReturnResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.EnquiryAttachmentVO;
import com.efitops.basesetup.entity.EnquiryDetailsVO;
import com.efitops.basesetup.entity.EnquiryTermsandCondVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentAttachmentVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentVO;
import com.efitops.basesetup.entity.SalesOrderAmendmentDetailsVO;
import com.efitops.basesetup.entity.SalesOrderAmendmentVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerContactDetailsRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.EnquiryAttachmentRepo;
import com.efitops.basesetup.repository.EnquiryDetailsRepo;
import com.efitops.basesetup.repository.EnquiryRepo;
import com.efitops.basesetup.repository.EnquiryTermsandCondRepo;
import com.efitops.basesetup.repository.GstRateMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.OrderAcceptanceRepo;
import com.efitops.basesetup.repository.PurchaseContractAmendmentAttachmentRepo;
import com.efitops.basesetup.repository.PurchaseContractAmendmentDetailsRepo;
import com.efitops.basesetup.repository.PurchaseContractAmendmentRepo;
import com.efitops.basesetup.repository.PurchaseContractRepo;
import com.efitops.basesetup.repository.SalesContractDetailsRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleDetailsRepo;
import com.efitops.basesetup.repository.SalesDeliverySchedulePlanRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleRepo;
import com.efitops.basesetup.repository.SalesOrderAmendmentDetailsRepo;
import com.efitops.basesetup.repository.SalesOrderAmendmentRepo;
import com.efitops.basesetup.repository.SalesReturnRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class DevelopServiceImpl implements DevelopService {

	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopServiceImpl.class);

	@Autowired
	EnquiryRepo enquiryRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private EnquiryDetailsRepo enquiryDetailsRepo;

	@Autowired
	private EnquiryTermsandCondRepo enquiryTermsandCondRepo;

	@Autowired
	private EnquiryAttachmentRepo enquiryAttachmentRepo;

	@Autowired
	private CustomerContactDetailsRepo customerContactDetailsRepo;
	
	@Autowired
	private SalesDeliveryScheduleRepo salesDeliveryScheduleRepo; 
	
	@Autowired
	private SalesDeliveryScheduleDetailsRepo salesDeliveryScheduleDetailsRepo; 
	
	@Autowired
	private SalesContractRepo salesContractRepo;

	@Autowired
	private SalesContractDetailsRepo salesContractDetailsRepo;
	
	@Autowired
	private SalesDeliverySchedulePlanRepo salesDeliverySchedulePlanRepo;
	
	@Autowired
	private SalesReturnRepo salesReturnRepo;
	
	@Autowired
	private ListOfValuesRepo listOfValuesRepo;
	
	@Autowired
	private ListOfValuesDetailsRepo listOfValuesDetailsRepo;
	
	@Autowired
	private LocationRepo locationRepo;
	
	@Autowired
	private EmployeeMasterRepo employeeMasterRepo;
	
	
	@Autowired
	private GstRateMasterRepo gstRateMasterRepo;
	
	@Autowired
	private UnitMasterRepo unitMasterRepo;
	
	
	@Autowired
	private SalesOrderAmendmentRepo salesOrderAmendmentRepo;
	
	
	@Autowired
	private SalesOrderAmendmentDetailsRepo salesOrderAmendmentDetailsRepo;
	
	@Autowired
	private OrderAcceptanceRepo orderAcceptanceRepo;
	
	@Autowired
	private PurchaseContractAmendmentRepo purchaseContractAmendmentRepo;
	
	
	@Autowired
	private PurchaseContractAmendmentDetailsRepo purchaseContractAmendmentDetailsRepo;
	
	
	@Autowired
	private PurchaseContractRepo purchaseContractRepo;
	
	
	
	@Value("${purchase.contract.amendment.upload.path}")
	private String uploadPath1;

	@Value("${server.base-url}")
	private String serverBaseUrl;

	
	@Autowired
	PurchaseContractAmendmentAttachmentRepo purchaseContractAmendmentAttachmentRepo;
	
	
	
	
	

	
	
	
//	@Override
//	@Transactional
//	public Map<String, Object> updateCreateEnquiry(EnquiryDTO enquiryDTO) throws ApplicationException {
//		String screenCode = "EQN";
//		EnquiryVO enquiryVO = new EnquiryVO();
//		String message;
//
//		if (ObjectUtils.isNotEmpty(enquiryDTO.getId())) {
//			enquiryVO = enquiryRepo.findById(enquiryDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Enquiry Not Found"));
//			enquiryVO.setUpdatedBy(enquiryDTO.getCreatedBy());
//			createUpdateEnquiryVOByEnquiryDTO(enquiryDTO, enquiryVO);
//			message = "Enquiry Updated Successfully";
//		} else {
//			if (enquiryRepo.existsByEnquiryNoAndOrgId(enquiryDTO.getEnquiryNo(), enquiryDTO.getOrgId())) {
//				throw new ApplicationException("Enquiry Number Already Exists");
//			}
//			createUpdateEnquiryVOByEnquiryDTO(enquiryDTO, enquiryVO);
//			enquiryVO.setCreatedBy(enquiryDTO.getCreatedBy());
//			enquiryVO.setUpdatedBy(enquiryDTO.getCreatedBy());
//			message = "Enquiry Created Successfully";
//		}
//
//		EnquiryVO savedEnquiry = enquiryRepo.save(enquiryVO);
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("message", message);
//		response.put("enquiryVO", buildEnquiryResponse(savedEnquiry));
//		return response;
//	}
//
//	private EnquiryResponseDTO buildEnquiryResponse(EnquiryVO enquiryVO) {
//		EnquiryResponseDTO responseDTO = new EnquiryResponseDTO();
//
//		// ----- header / scalar fields -----
//		responseDTO.setId(enquiryVO.getId());
//		responseDTO.setEnquiryNo(enquiryVO.getEnquiryNo());
//		responseDTO.setEnquiryType(enquiryVO.getEnquiryType());
//		responseDTO.setEnquiryDate(enquiryVO.getEnquiryDate());
//		responseDTO.setPartyName(enquiryVO.getPartyName());
//		responseDTO.setPartyRefNo(enquiryVO.getPartyRefNo());
//		responseDTO.setPartyRefDate(enquiryVO.getPartyRefDate());
//		responseDTO.setEnquiryDueDate(enquiryVO.getEnquiryDueDate());
//		responseDTO.setContactEmail(enquiryVO.getContactEmail());
//		responseDTO.setStatus(enquiryVO.getStatus());
//		responseDTO.setOrgId(enquiryVO.getOrgId());
//		responseDTO.setCancelRemarks(enquiryVO.getCancelRemarks());
//		responseDTO.setCreatedBy(enquiryVO.getCreatedBy());
//
//		// ----- related entities returned as IDs only -----
//
//		// ----- Enquiry Details -----
//		List<EnquiryDetailsReponseDTO> enquiryDetailsList = new ArrayList<>();
//		if (enquiryVO.getEnquiryDetails() != null) {
//			for (EnquiryDetailsVO detailVO : enquiryVO.getEnquiryDetails()) {
//				EnquiryDetailsReponseDTO detailDTO = new EnquiryDetailsReponseDTO();
//				detailDTO.setId(detailVO.getId());
//				if (detailVO.getItemcode() != null) {
//					detailDTO.setItemcode(detailVO.getItemcode().getId());
//				}
//				detailDTO.setAnnualquantity(detailVO.getAnnualquantity());
//				detailDTO.setDlrydate(detailVO.getDlrydate());
//				detailDTO.setNeedrdapproval(detailVO.getNeedrdapproval());
//				detailDTO.setQuoteduedate(detailVO.getQuoteduedate());
//				detailDTO.setRemarks(detailVO.getRemarks());
//				enquiryDetailsList.add(detailDTO);
//			}
//		}
//		responseDTO.setEnquiryDetails(enquiryDetailsList);
//
//		// ----- Terms & Conditions -----
//		List<EnquiryTermsandCondResponseDTO> termsList = new ArrayList<>();
//		if (enquiryVO.getEnquiryTermsandCond() != null) {
//			for (EnquiryTermsandCondVO termsVO : enquiryVO.getEnquiryTermsandCond()) {
//				EnquiryTermsandCondResponseDTO termsDTO = new EnquiryTermsandCondResponseDTO();
//				termsDTO.setId(termsVO.getId());
//				termsDTO.setAdditionalInvestment(termsVO.getAdditionalInvestment());
//				termsDTO.setAdditionalManPower(termsVO.getAdditionalManPower());
//				termsDTO.setLikelyTimeFrame(termsVO.getLikelyTimeFrame());
//				termsDTO.setExpectedDeliverySample(termsVO.getExpectedDeliverySample());
//				termsDTO.setPilotBatch(termsVO.getPilotBatch());
//				termsDTO.setRegularProduction(termsVO.getRegularProduction());
//				termsDTO.setInitialReviewComments(termsVO.getInitialReviewComments());
//				termsDTO.setDetailDelivery(termsVO.getDetailDelivery());
//				termsDTO.setStatutoryRegulatoryReq(termsVO.getStatutoryRegulatoryReq());
//				termsDTO.setFollowUp(termsVO.getFollowUp());
//				termsDTO.setConclusion(termsVO.getConclusion());
//				termsDTO.setRemarks(termsVO.getRemarks());
//				termsList.add(termsDTO);
//			}
//		}
//		responseDTO.setEnquiryTermsandCond(termsList);
//
//		// ----- Attachments -----
//		List<EnquiryAttachmentResponseDTO> attachmentList = new ArrayList<>();
//		if (enquiryVO.getEnquiryAttachment() != null) {
//			for (EnquiryAttachmentVO attachVO : enquiryVO.getEnquiryAttachment()) {
//				EnquiryAttachmentResponseDTO attachDTO = new EnquiryAttachmentResponseDTO();
//				attachDTO.setId(attachVO.getId());
//				attachDTO.setName(attachVO.getName());
//				attachDTO.setFileName(attachVO.getFileName());
//				attachDTO.setFilePath(attachVO.getFilePath());
//				attachDTO.setFileSize(attachVO.getFileSize());
//				attachDTO.setContentType(attachVO.getContentType());
//				attachDTO.setUploadOn(attachVO.getUploadOn());
//				attachmentList.add(attachDTO);
//			}
//		}
//		responseDTO.setEnquiryAttachmentDTO(attachmentList);
//
//		return responseDTO;
//	}
//
//	private void createUpdateEnquiryVOByEnquiryDTO(EnquiryDTO enquiryDTO, EnquiryVO enquiryVO)
//			throws ApplicationException {
//
//		enquiryVO.setEnquiryNo(enquiryDTO.getEnquiryNo());
//		enquiryVO.setEnquiryType(enquiryDTO.getEnquiryType());
//		enquiryVO.setEnquiryDate(enquiryDTO.getEnquiryDate());
//		enquiryVO.setPartyName(enquiryDTO.getPartyName());
//		enquiryVO.setPartyRefNo(enquiryDTO.getPartyRefNo());
//		enquiryVO.setPartyRefDate(enquiryDTO.getPartyRefDate());
//		enquiryVO.setEnquiryDueDate(enquiryDTO.getEnquiryDueDate());
//		enquiryVO.setContactEmail(enquiryDTO.getContactEmail());
//		enquiryVO.setStatus(enquiryDTO.getStatus());
//		enquiryVO.setOrgId(enquiryDTO.getOrgId());
//		enquiryVO.setActive(enquiryDTO.isActive());
//		enquiryVO.setCancelRemarks(enquiryDTO.getCancelRemarks());
//
//		// Branch
//		if (enquiryDTO.getBranch() != null) {
//			BranchVO branch = branchRepo.findById(enquiryDTO.getBranch())
//					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
//			enquiryVO.setBranch(branch);
//		}
//		
//		// Party
//		if (enquiryDTO.getPartyId() != null) {
//
//		    CustomerVO party = customerRepo.findById(enquiryDTO.getPartyId())
//		            .orElseThrow(() -> new ApplicationException("Party Not Found"));
//
//		    enquiryVO.setPartyid(party);
//		}
//
//		// Customer
//		if (enquiryDTO.getContactNameId() != null) {
//			CustomerContactDetailsVO customer = customerContactDetailsRepo.findById(enquiryDTO.getContactNameId())
//					.orElseThrow(() -> new ApplicationException("Contact Not Found"));
//			enquiryVO.setContactName(customer);
//		}
//
//		// Enquiry Details
//
//		List<EnquiryDetailsVO> detailList = new ArrayList<>();
//
//		if (enquiryDTO.getEnquiryDetails() != null) {
//
//			for (EnquiryDetailsDTO dto : enquiryDTO.getEnquiryDetails()) {
//
//				EnquiryDetailsVO detail = new EnquiryDetailsVO();
//				 if (dto.getItemcode() != null) {
//
//			            ItemMasterVO item = itemMasterRepo.findById(dto.getItemcode())
//			                    .orElseThrow(() -> new ApplicationException("Item Not Found"));
//
//			            detail.setItemcode(item);
//			        }
//
//					
//					
//				
//
//				detail.setAnnualquantity(dto.getAnnualquantity());
//				detail.setDlrydate(dto.getDlrydate());
//				detail.setNeedrdapproval(dto.getNeedrdapproval());
//				detail.setQuoteduedate(dto.getQuoteduedate());
//				detail.setRemarks(dto.getRemarks());
//
//				detail.setEnquiryVO(enquiryVO);
//
//				detailList.add(detail);
//			}
//		}
//
//		enquiryVO.setEnquiryDetails(detailList);
//
//		// Terms & Conditions
//
//		List<EnquiryTermsandCondVO> termsList = new ArrayList<>();
//
//		if (enquiryDTO.getEnquiryTermsandCond() != null) {
//
//			for (EnquiryTermsandCondDTO dto : enquiryDTO.getEnquiryTermsandCond()) {
//
//				EnquiryTermsandCondVO terms = new EnquiryTermsandCondVO();
//
//				terms.setAdditionalInvestment(dto.getAdditionalInvestment());
//				terms.setAdditionalManPower(dto.getAdditionalManPower());
//				terms.setLikelyTimeFrame(dto.getLikelyTimeFrame());
//				terms.setExpectedDeliverySample(dto.getExpectedDeliverySample());
//				terms.setPilotBatch(dto.getPilotBatch());
//				terms.setRegularProduction(dto.getRegularProduction());
//				terms.setInitialReviewComments(dto.getInitialReviewComments());
//				terms.setDetailDelivery(dto.getDetailDelivery());
//				terms.setStatutoryRegulatoryReq(dto.getStatutoryRegulatoryReq());
//				terms.setFollowUp(dto.getFollowUp());
//				terms.setConclusion(dto.getConclusion());
//				terms.setRemarks(dto.getRemarks());
//
//				terms.setEnquiryVO(enquiryVO);
//
//				termsList.add(terms);
//			}
//		}
//
//		enquiryVO.setEnquiryTermsandCond(termsList);
//
//		// =======================
//		// Attachment
//		// =======================
//
//		List<EnquiryAttachmentVO> attachmentList = new ArrayList<>();
//
//		if (enquiryDTO.getEnquiryAttachmentDTO() != null) {
//
//			for (EnquiryAttachmentDTO dto : enquiryDTO.getEnquiryAttachmentDTO()) {
//
//				EnquiryAttachmentVO attachment = new EnquiryAttachmentVO();
//
//				attachment.setName(dto.getName());
//				attachment.setFileName(dto.getFileName());
//				attachment.setFilePath(dto.getFilePath());
//				attachment.setFileSize(dto.getFileSize());
//				attachment.setContentType(dto.getContentType());
//				attachment.setUploadOn(dto.getUploadOn());
//
//				attachment.setEnquiryVO(enquiryVO);
//
//				attachmentList.add(attachment);
//			}
//		}
//
//		enquiryVO.setEnquiryAttachment(attachmentList);
//	}

	
	
	
	@Override
	@Transactional
	public Map<String, Object> updateCreateEnquiry(
	        EnquiryDTO enquiryDTO,
	        MultipartFile[] files) throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    String message;

	    EnquiryVO enquiryVO;

	    if (ObjectUtils.isEmpty(enquiryDTO.getId())) {

	        enquiryVO = new EnquiryVO();

	        enquiryVO.setCreatedBy(enquiryDTO.getCreatedBy());
	        enquiryVO.setUpdatedBy(enquiryDTO.getCreatedBy());

	        message = "Enquiry Created Successfully";

	    } else {

	        enquiryVO = enquiryRepo.findById(enquiryDTO.getId())
	                .orElseThrow(() -> new ApplicationException("Enquiry Not Found"));

	        enquiryVO.setUpdatedBy(enquiryDTO.getCreatedBy());

	        // Delete old details
	        enquiryDetailsRepo.deleteAll(enquiryVO.getEnquiryDetails());

	        // Delete old terms
	        enquiryTermsandCondRepo.deleteAll(enquiryVO.getEnquiryTermsandCond());

	        // Delete old attachments
	        if (enquiryVO.getEnquiryAttachment() != null
	                && !enquiryVO.getEnquiryAttachment().isEmpty()) {

	            for (EnquiryAttachmentVO attachment : enquiryVO.getEnquiryAttachment()) {

	                try {
	                    if (attachment.getFilePath() != null) {
	                        Files.deleteIfExists(Paths.get(attachment.getFilePath()));
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }

	            enquiryAttachmentRepo.deleteAll(enquiryVO.getEnquiryAttachment());

	            // IMPORTANT
	            enquiryVO.getEnquiryAttachment().clear();
	        }

	        message = "Enquiry Updated Successfully";
	    }

	    // Header + Details + Terms
	    createUpdateEnquiryVOByEnquiryDTO(enquiryDTO, enquiryVO);

	    // Save Header
	    enquiryVO = enquiryRepo.save(enquiryVO);

	    // Save Attachments
	    saveAttachments(files, enquiryVO);

	    // Reload latest data
	    enquiryVO = enquiryRepo.findById(enquiryVO.getId())
	            .orElseThrow(() -> new ApplicationException("Enquiry Not Found"));

	    // Response
	    EnquiryResponseDTO responseDTO = buildEnquiryResponse(enquiryVO);

	    response.put("message", message);
	    response.put("enquiryVO", responseDTO);

	    return response;
	}
	
	
	private void createUpdateEnquiryVOByEnquiryDTO(
	        EnquiryDTO enquiryDTO,
	        EnquiryVO enquiryVO) throws ApplicationException {

	    // ================= Header =================

	    enquiryVO.setEnquiryNo(enquiryDTO.getEnquiryNo());
	    enquiryVO.setEnquiryType(enquiryDTO.getEnquiryType());
	    enquiryVO.setEnquiryDate(enquiryDTO.getEnquiryDate());
	    enquiryVO.setPartyName(enquiryDTO.getPartyName());
	    enquiryVO.setPartyRefNo(enquiryDTO.getPartyRefNo());
	    enquiryVO.setPartyRefDate(enquiryDTO.getPartyRefDate());
	    enquiryVO.setEnquiryDueDate(enquiryDTO.getEnquiryDueDate());
	    enquiryVO.setContactEmail(enquiryDTO.getContactEmail());
	    enquiryVO.setStatus(enquiryDTO.getStatus());

	    enquiryVO.setOrgId(enquiryDTO.getOrgId());

	    enquiryVO.setCancelRemarks(enquiryDTO.getCancelRemarks());
	    enquiryVO.setActive(enquiryDTO.isActive());

	    // ================= Branch =================

	    if (enquiryDTO.getBranch() != null) {

	        BranchVO branch = branchRepo.findById(enquiryDTO.getBranch())
	                .orElseThrow(() -> new ApplicationException("Branch Not Found"));

	        enquiryVO.setBranch(branch);
	    }

	    // ================= Party =================

	    if (enquiryDTO.getPartyId() != null) {

	        CustomerVO customer = customerRepo.findById(enquiryDTO.getPartyId())
	                .orElseThrow(() -> new ApplicationException("Customer Not Found"));

	        enquiryVO.setCustomer(customer);
	    }

	    // ================= Contact =================

	    if (enquiryDTO.getContactNameId() != null) {

	        EmployeeMasterVO contact =
	        		employeeMasterRepo.findById(enquiryDTO.getContactNameId())
	                        .orElseThrow(() -> new ApplicationException("Contact Not Found"));

	        enquiryVO.setContactName(contact);
	    }

	    // ================= Details =================

	    List<EnquiryDetailsVO> detailsList = new ArrayList<>();

	    if (enquiryDTO.getEnquiryDetails() != null) {

	        for (EnquiryDetailsDTO dto : enquiryDTO.getEnquiryDetails()) {

	            EnquiryDetailsVO detail = new EnquiryDetailsVO();

//	            if (dto.getItemcode() != null) {
//
//	                ItemMasterVO item = itemMasterRepo.findById(dto.getItemcode())
//	                        .orElseThrow(() -> new ApplicationException("Item Not Found"));
//
//	                detail.setItemcode(item);
//	            }
	            detail.setItemCode(dto.getItemcode());
	            detail.setItemDescription(dto.getItemDescription());

	            detail.setAnnualquantity(dto.getAnnualquantity());
	            detail.setDlrydate(dto.getDlrydate());
	            detail.setNeedrdapproval(dto.getNeedrdapproval());
	            detail.setQuoteduedate(dto.getQuoteduedate());
	            detail.setRemarks(dto.getRemarks());

	            detail.setEnquiryVO(enquiryVO);

	            detailsList.add(detail);
	        }
	    }

	    enquiryVO.setEnquiryDetails(detailsList);

	    // ================= Terms & Conditions =================

	    List<EnquiryTermsandCondVO> termsList = new ArrayList<>();

	    if (enquiryDTO.getEnquiryTermsandCond() != null) {

	        for (EnquiryTermsandCondDTO dto : enquiryDTO.getEnquiryTermsandCond()) {

	            EnquiryTermsandCondVO terms = new EnquiryTermsandCondVO();

	            terms.setAdditionalInvestment(dto.getAdditionalInvestment());
	            terms.setAdditionalManPower(dto.getAdditionalManPower());
	            terms.setLikelyTimeFrame(dto.getLikelyTimeFrame());
	            terms.setExpectedDeliverySample(dto.getExpectedDeliverySample());
	            terms.setPilotBatch(dto.getPilotBatch());
	            terms.setRegularProduction(dto.getRegularProduction());
	            terms.setInitialReviewComments(dto.getInitialReviewComments());
	            terms.setDetailDelivery(dto.getDetailDelivery());
	            terms.setStatutoryRegulatoryReq(dto.getStatutoryRegulatoryReq());
	            terms.setFollowUp(dto.getFollowUp());
	            terms.setConclusion(dto.getConclusion());
	            terms.setRemarks(dto.getRemarks());

	            terms.setEnquiryVO(enquiryVO);

	            termsList.add(terms);
	        }
	    }

	    enquiryVO.setEnquiryTermsandCond(termsList);

	    // NOTE:
	    // Do NOT map attachments here.
	    // Attachments are saved separately by:
	    // saveAttachments(files, enquiryVO);
	}
	
	@Value("${enquiry.upload.path}")
	private String uploadPath;

	private void saveAttachments(MultipartFile[] files, EnquiryVO enquiryVO)
	        throws ApplicationException {

	    if (files == null || files.length == 0) {
	        return;
	    }

	    try {

	        File folder = new File(uploadPath);

	        if (!folder.exists()) {
	            folder.mkdirs();
	        }

	        List<EnquiryAttachmentVO> attachmentList = new ArrayList<>();

	        for (MultipartFile file : files) {

	            if (file == null || file.isEmpty()) {
	                continue;
	            }

	            String fileName = UUID.randomUUID() + "_"
	                    + file.getOriginalFilename();

	            Path path = Paths.get(uploadPath, fileName);

	            try (InputStream inputStream = file.getInputStream()) {

	                Files.copy(
	                        inputStream,
	                        path,
	                        StandardCopyOption.REPLACE_EXISTING);
	            }

	            EnquiryAttachmentVO attachment = new EnquiryAttachmentVO();

	            attachment.setName(file.getOriginalFilename());

	            attachment.setFileName(fileName);

	            attachment.setFilePath(path.toString());

	            attachment.setFileSize(file.getSize());

	            attachment.setContentType(file.getContentType());

	            attachment.setUploadOn(LocalDateTime.now());

	            attachment.setEnquiryVO(enquiryVO);

	            attachmentList.add(attachment);
	        }

	        attachmentList = enquiryAttachmentRepo.saveAll(attachmentList);

	        enquiryVO.setEnquiryAttachment(attachmentList);

	    } catch (IOException e) {

	        throw new ApplicationException(
	                "File Upload Failed : " + e.getMessage());
	    }
	}
	
	
	private EnquiryResponseDTO buildEnquiryResponse(EnquiryVO enquiryVO) {

	    EnquiryResponseDTO responseDTO = new EnquiryResponseDTO();

	    // ================= Header =================

	    responseDTO.setId(enquiryVO.getId());
	    responseDTO.setEnquiryNo(enquiryVO.getEnquiryNo());
	    responseDTO.setEnquiryType(enquiryVO.getEnquiryType());
	    responseDTO.setEnquiryDate(enquiryVO.getEnquiryDate());
	    responseDTO.setPartyName(enquiryVO.getPartyName());
	    responseDTO.setPartyRefNo(enquiryVO.getPartyRefNo());
	    responseDTO.setPartyRefDate(enquiryVO.getPartyRefDate());
	    responseDTO.setEnquiryDueDate(enquiryVO.getEnquiryDueDate());
	    responseDTO.setContactEmail(enquiryVO.getContactEmail());
	    responseDTO.setStatus(enquiryVO.getStatus());

	    responseDTO.setOrgId(enquiryVO.getOrgId());
	    responseDTO.setCreatedBy(enquiryVO.getCreatedBy());
	    responseDTO.setCancelRemarks(enquiryVO.getCancelRemarks());
	    responseDTO.setActive(enquiryVO.getActive());

	    // ================= Branch =================

	    if (enquiryVO.getBranch() != null) {

	        responseDTO.setBranch(
	                new BranchResponseDTO(
	                        enquiryVO.getBranch().getId(),
	                        enquiryVO.getBranch().getBranchCode(),
	                        enquiryVO.getBranch().getBranchName()));
	    }

	    // ================= Party =================

	    if (enquiryVO.getCustomer() != null) {

	        responseDTO.setCustomerVO(
	                new CustomerResponse1DTO(
	                        enquiryVO.getCustomer().getId(),
	                        enquiryVO.getCustomer().getCustomerName()));
	    }

	    // ================= Contact =================

	    if (enquiryVO.getContactName() != null) {

	        responseDTO.setContactName(
	                new EmployeeResponseDTO(
	                        enquiryVO.getContactName().getId(),
	                        enquiryVO.getContactName().getEmployeeName()));
	    }

	    // ================= Details =================

	    List<EnquiryDetailsReponseDTO> detailResponse = new ArrayList<>();

	    if (enquiryVO.getEnquiryDetails() != null) {

	        for (EnquiryDetailsVO detail : enquiryVO.getEnquiryDetails()) {

	            EnquiryDetailsReponseDTO detailDTO =
	                    new EnquiryDetailsReponseDTO();

	            detailDTO.setId(detail.getId());

//	            if (detail.getItemcode() != null) {
//
//	                detailDTO.setItemcode(detail.getItemcode().getId());
//	            }
	            detailDTO.setItemCode(detail.getItemCode());
	            detailDTO.setItemDescription(detail.getItemDescription());

	            detailDTO.setAnnualquantity(detail.getAnnualquantity());
	            detailDTO.setDlrydate(detail.getDlrydate());
	            detailDTO.setNeedrdapproval(detail.getNeedrdapproval());
	            detailDTO.setQuoteduedate(detail.getQuoteduedate());
	            detailDTO.setRemarks(detail.getRemarks());

	            detailResponse.add(detailDTO);
	        }
	    }

	    responseDTO.setEnquiryDetails(detailResponse);

	    // ================= Terms =================

	    List<EnquiryTermsandCondResponseDTO> termsResponse =
	            new ArrayList<>();

	    if (enquiryVO.getEnquiryTermsandCond() != null) {

	        for (EnquiryTermsandCondVO terms : enquiryVO.getEnquiryTermsandCond()) {

	            EnquiryTermsandCondResponseDTO dto =
	                    new EnquiryTermsandCondResponseDTO();

	            dto.setId(terms.getId());
	            dto.setAdditionalInvestment(terms.getAdditionalInvestment());
	            dto.setAdditionalManPower(terms.getAdditionalManPower());
	            dto.setLikelyTimeFrame(terms.getLikelyTimeFrame());
	            dto.setExpectedDeliverySample(terms.getExpectedDeliverySample());
	            dto.setPilotBatch(terms.getPilotBatch());
	            dto.setRegularProduction(terms.getRegularProduction());
	            dto.setInitialReviewComments(terms.getInitialReviewComments());
	            dto.setDetailDelivery(terms.getDetailDelivery());
	            dto.setStatutoryRegulatoryReq(terms.getStatutoryRegulatoryReq());
	            dto.setFollowUp(terms.getFollowUp());
	            dto.setConclusion(terms.getConclusion());
	            dto.setRemarks(terms.getRemarks());

	            termsResponse.add(dto);
	        }
	    }

	    responseDTO.setEnquiryTermsandCond(termsResponse);

	    // ================= Attachments =================

	    List<EnquiryAttachmentResponseDTO> attachmentResponse =
	            new ArrayList<>();

	    if (enquiryVO.getEnquiryAttachment() != null) {

	        for (EnquiryAttachmentVO attachment : enquiryVO.getEnquiryAttachment()) {

	            EnquiryAttachmentResponseDTO dto =
	                    new EnquiryAttachmentResponseDTO();

	            dto.setId(attachment.getId());
	            dto.setName(attachment.getName());
	            dto.setFileName(attachment.getFileName());
	            dto.setFilePath(attachment.getFilePath());
	            dto.setFileSize(attachment.getFileSize());
	            dto.setContentType(attachment.getContentType());
	            dto.setUploadOn(attachment.getUploadOn());

	            attachmentResponse.add(dto);
	        }
	    }

	    responseDTO.setEnquiryAttachmentDTO(attachmentResponse);

	    return responseDTO;
	}
	
	
	
	
	
	
	@Override
	public EnquiryResponseDTO getEnquiryById(Long id) throws ApplicationException {

	    EnquiryVO enquiryVO = enquiryRepo.findById(id)
	            .orElseThrow(() -> new ApplicationException("Enquiry Not Found"));

	    return buildEnquiryResponse(enquiryVO);
	}
	
	@Override
	public List<EnquiryResponseDTO> getEnquiryByOrgId(Long orgId, Long branchId)
	        throws ApplicationException {

	    List<EnquiryVO> enquiryList = enquiryRepo.findByOrgIdAndBranch(orgId, branchId);

	    List<EnquiryResponseDTO> responseList = new ArrayList<>();

	    for (EnquiryVO enquiryVO : enquiryList) {

	        responseList.add(buildEnquiryResponse(enquiryVO));
	    }

	    return responseList;
	}
	
	
//	@Override
//	@Transactional
//	public Map<String, Object> uploadEnquiryAttachment(Long enquiryId, MultipartFile file) throws ApplicationException {
//
//		EnquiryVO enquiry = enquiryRepo.findById(enquiryId)
//				.orElseThrow(() -> new ApplicationException("Enquiry Not Found"));
//
//		try {
//
//			String uploadDir = "uploads/enquiry/";
//
//			Path uploadPath = Paths.get(uploadDir);
//
//			if (!Files.exists(uploadPath)) {
//				Files.createDirectories(uploadPath);
//			}
//
//			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//			Path filePath = uploadPath.resolve(fileName);
//
//			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//			EnquiryAttachmentVO attachment = new EnquiryAttachmentVO();
//
//			attachment.setName(file.getOriginalFilename());
//			attachment.setFileName(fileName);
//			attachment.setFilePath(filePath.toString());
//			attachment.setFileSize(file.getSize());
//			attachment.setContentType(file.getContentType());
//			attachment.setUploadOn(LocalDateTime.now());
//			attachment.setEnquiryVO(enquiry);
//
//			enquiryAttachmentRepo.save(attachment);
//
//			Map<String, Object> response = new HashMap<>();
//			response.put("message", "Attachment Uploaded Successfully");
//
//			return response;
//
//		} catch (IOException e) {
//
//			throw new ApplicationException("Unable to Upload File");
//		}
//	} // <-- closes uploadEnquiryAttachment()
//
//	@Override
//	public ResponseEntity<byte[]> viewEnquiryAttachment(Long attachmentId) throws ApplicationException {
//
//		EnquiryAttachmentVO attachment = enquiryAttachmentRepo.findById(attachmentId)
//				.orElseThrow(() -> new ApplicationException("Attachment Not Found"));
//
//		try {
//
//			Path path = Paths.get(attachment.getFilePath());
//
//			byte[] data = Files.readAllBytes(path);
//
//			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + attachment.getFileName() + "\"")
//					.contentType(MediaType.APPLICATION_PDF).body(data);
//
//		} catch (IOException e) {
//
//			throw new ApplicationException("Unable to View Attachment");
//		}
//	}
	
	
	
	///SALES RETURN
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesReturn(
	        SalesReturnDTO salesReturnDTO)
	        throws ApplicationException {

//	    SalesReturnVO salesReturnVO = new SalesReturnVO();
//
//	    String message;
//
//	    if (ObjectUtils.isNotEmpty(salesReturnDTO.getId())) {
//
//	        salesReturnVO = salesReturnRepo
//	                .findById(salesReturnDTO.getId())
//	                .orElseThrow(() ->
//	                        new ApplicationException("Invalid Sales Return Details"));
//
//	        salesReturnVO.setUpdatedBy(
//	                salesReturnDTO.getUpdatedBy());
//
//	        message = "Sales Return Updated Successfully";
//
//	    } else {
//
//	        salesReturnVO.setCreatedBy(
//	                salesReturnDTO.getCreatedBy());
//
//	        salesReturnVO.setUpdatedBy(
//	                salesReturnDTO.getCreatedBy());
//
//	        message = "Sales Return Created Successfully";
//	    }
//
//	    createUpdateSalesReturnVO(
//	            salesReturnDTO,
//	            salesReturnVO);
//
//	    SalesReturnVO savedSalesReturnVO =
//	            salesReturnRepo.save(salesReturnVO);
//
//	    Map<String, Object> response = new HashMap<>();
//
//	    response.put("message", message);
//
//	    response.put(
//	            "salesReturnVO",
//	            salesReturnResponse(savedSalesReturnVO));
//
//	    return response;
//	}
//	
//	
//	private SalesReturnResponseDTO salesReturnResponse(
//	        SalesReturnVO salesReturnVO) {
//
//	    SalesReturnResponseDTO responseDTO =
//	            new SalesReturnResponseDTO();
//
//	   
//	    // Document Details
//	    
//
//	    responseDTO.setId(salesReturnVO.getId());
//	    responseDTO.setDocNo(salesReturnVO.getDocNo());
//	    responseDTO.setDocDate(salesReturnVO.getDocDate());
//
//	   
//	    // Branch Response
//	   
//
//	    if (salesReturnVO.getBranch() != null) {
//
//	        BranchResponseDTO branchDTO = new BranchResponseDTO();
//	        branchDTO.setId(salesReturnVO.getBranch().getId());
//	        branchDTO.setBranchName(salesReturnVO.getBranch().getBranchName());
//
//	        responseDTO.setBranch(branchDTO);
//	    }
//
//	    
//	    // Belongs To Response
//	   
//	    if (salesReturnVO.getBelongsTo() != null) {
//
//	        ListOfVlauesDetailsResponseDTO dto =
//	                new ListOfVlauesDetailsResponseDTO();
//
//	        dto.setId(salesReturnVO.getBelongsTo().getId());
//	        dto.setValueDescription(
//	                salesReturnVO.getBelongsTo().getValueDescription());
//
//	        responseDTO.setBelongsTo(dto);
//	    }
//
//	    
//	    // Invoice Details
//	   
//	    responseDTO.setInvoiceNo(
//	            salesReturnVO.getInvoiceNo());
//
//	    responseDTO.setInvoiceDate(
//	            salesReturnVO.getInvoiceDate());
//
//	    responseDTO.setCustomerInvoiceNo(
//	            salesReturnVO.getCustomerInvoiceNo());
//
//	    responseDTO.setCustomerInvoiceDate(
//	            salesReturnVO.getCustomerInvoiceDate());
//
//	    responseDTO.setGatePassNo(
//	            salesReturnVO.getGatePassNo());
//
//	   
//	    // Customer Response
//	  
//
//	    if (salesReturnVO.getCustomer() != null) {
//
//	        SalesReturnCustomerResponseDTO customerDTO =
//	                new SalesReturnCustomerResponseDTO();
//
//	        customerDTO.setId(salesReturnVO.getCustomer().getId());
//	        customerDTO.setCustomerCode(salesReturnVO.getCustomer().getCustomerCode());
//	        customerDTO.setCustomerName(salesReturnVO.getCustomer().getCustomerName());
//	        customerDTO.setGstNo(salesReturnVO.getCustomer().getGstNo());
//
//	        responseDTO.setCustomer(customerDTO);
//	    }
//	    
//	   
//	    // Location Response
//	   
//
//	    if (salesReturnVO.getLocation() != null) {
//
//	        LocationResponseDTO locationDTO =
//	                new LocationResponseDTO();
//
//	        locationDTO.setId(salesReturnVO.getLocation().getId());
//	        locationDTO.setLocationId(salesReturnVO.getLocation().getLocationId());
//	        locationDTO.setLocationName(salesReturnVO.getLocation().getLocationName());
//
//	        responseDTO.setLocation(locationDTO);
//	    }
//
//	   
//	    // Return Type Response
//	   
//
//	    ListOfVlauesDetailsResponseDTO returnTypeDTO =
//	            new ListOfVlauesDetailsResponseDTO();
//
//	    returnTypeDTO.setId(salesReturnVO.getReturnType().getId());
//	    returnTypeDTO.setValueDescription(
//	            salesReturnVO.getReturnType().getValueDescription());
//
//	    responseDTO.setReturnType(returnTypeDTO);
//	   
//	    // Invoice Reference Type Response
//	    
//
//	    if (salesReturnVO.getInvoiceReferenceType() != null) {
//
//	        responseDTO.setInvoiceReferenceTypeId(
//	                salesReturnVO.getInvoiceReferenceType().getId());
//
//	        responseDTO.setInvoiceReferenceType(
//	                salesReturnVO.getInvoiceReferenceType().getValueDescription());
//	    }
//	    
//	    
//
//	  
//	    // Other Details
//	   
//
//	    responseDTO.setApprovedByAccounts(
//	            salesReturnVO.getApprovedByAccounts());
//
//	    responseDTO.setCurrency(
//	            salesReturnVO.getCurrency());
//
//	    responseDTO.setExchangeRate(
//	            salesReturnVO.getExchangeRate());
//
//	    responseDTO.setReferenceNo(
//	            salesReturnVO.getReferenceNo());
//
//	    responseDTO.setReferenceDate(
//	            salesReturnVO.getReferenceDate());
//
//	    
//	    // Common Details
//	   
//
//	    responseDTO.setOrgId(
//	            salesReturnVO.getOrgId());
//
//	    responseDTO.setFinancialYear(
//	            salesReturnVO.getFinancialYear());
//
//	    responseDTO.setCreatedBy(
//	            salesReturnVO.getCreatedBy());
//
//	    responseDTO.setUpdatedBy(
//	            salesReturnVO.getUpdatedBy());
//
//	    responseDTO.setCancelRemarks(
//	            salesReturnVO.getCancelRemarks());
//
//	   
//
//	    responseDTO.setScreenCode(
//	            salesReturnVO.getScreenCode());
//
//	    responseDTO.setScreenName(
//	            salesReturnVO.getScreenName());
//	    
//	    
//	    responseDTO.setNetAmount(
//	            salesReturnVO.getNetAmount());
//
//	    responseDTO.setAmountInWords(
//	            salesReturnVO.getAmountInWords());
//
//	    responseDTO.setNarration(
//	            salesReturnVO.getNarration());
//	    
//	    
//	    responseDTO.setNetAmount(salesReturnVO.getNetAmount());
//
//	    responseDTO.setAmountInWords(salesReturnVO.getAmountInWords());
//
//	    responseDTO.setNarration(salesReturnVO.getNarration());
//
//
//	   
//	    // Sales Return Details Grid Response
//	   
//	    List<SalesReturnDetailsResponseDto> detailResponseList = new ArrayList<>();
//
//	    if (salesReturnVO.getSalesReturnDetails() != null) {
//
//	        for (SalesReturnDetailsVO detailVO : salesReturnVO.getSalesReturnDetails()) {
//
//	            SalesReturnDetailsResponseDto detailDTO =
//	                    new SalesReturnDetailsResponseDto();
//
//	            detailDTO.setId(detailVO.getId());
//
//	            // Item
//	            if (detailVO.getItem() != null) {
//
//	                ItemMasterResponseDTO itemDTO =
//	                        new ItemMasterResponseDTO();
//
//	                itemDTO.setId(detailVO.getItem().getId());
//	                itemDTO.setItemCode(detailVO.getItem().getItemCode());
//	                itemDTO.setItemDescription(detailVO.getItem().getItemDescription());
//
//	                detailDTO.setItem(itemDTO);
//	            }
//
//	            detailDTO.setItemDescription(detailVO.getItemDescription());
//	            detailDTO.setHsnSacCode(detailVO.getHsnSacCode());
//	            detailDTO.setTaxType(detailVO.getTaxType());
//
//	            detailDTO.setTaxPercentage(
//	            	    detailVO.getTaxPercentage().getRate());
//	            if (detailVO.getUnit() != null) {
//
//	                UnitMasterResponseDTO unitDTO =
//	                        new UnitMasterResponseDTO();
//
//	                unitDTO.setId(detailVO.getUnit().getId());
//	                unitDTO.setUnitId(detailVO.getUnit().getUnitId());
//	                unitDTO.setUnitDescription(detailVO.getUnit().getDescription());
//
//	                detailDTO.setUnit(unitDTO);
//	            }        
//
//	            detailDTO.setStock(detailVO.getStock());
//	            detailDTO.setQtySold(detailVO.getQtySold());
//	            detailDTO.setReceivedQty(detailVO.getReceivedQty());
//
//	            detailDTO.setRate(detailVO.getRate());
//	            detailDTO.setRateInSelectedCurrency(detailVO.getRateInSelectedCurrency());
//
//	            detailDTO.setAmountInSelectedCurrency(detailVO.getAmountInSelectedCurrency());
//	            detailDTO.setAmount(detailVO.getAmount());
//
//	            detailDTO.setSgstRate(detailVO.getSgstRate());
//	            detailDTO.setSgstAmount(detailVO.getSgstAmount());
//
//	            detailDTO.setCgstRate(detailVO.getCgstRate());
//	            detailDTO.setCgstAmount(detailVO.getCgstAmount());
//
//	            detailDTO.setIgstRate(detailVO.getIgstRate());
//	            detailDTO.setIgstAmount(detailVO.getIgstAmount());
//
//	            detailResponseList.add(detailDTO);
//	        }
//	    }
//
//	    responseDTO.setSalesReturnDetails(detailResponseList);
//	    
//	    
//	 // ==========================================
//	 // Sales Return Tax Details Response
//	 // ==========================================
//
//	 List<SalesReturnTaxDetailsResponseDto> taxResponse =
//	         new ArrayList<>();
//
//	 if (salesReturnVO.getSalesReturnTaxDetails() != null) {
//
//	     for (SalesReturnTaxDetailsVO taxVO :
//	             salesReturnVO.getSalesReturnTaxDetails()) {
//
//	         SalesReturnTaxDetailsResponseDto taxDTO =
//	                 new SalesReturnTaxDetailsResponseDto();
//
//	         taxDTO.setId(taxVO.getId());
//
//	         if (taxVO.getParticulars() != null) {
//
//	             ListOfVlauesDetailsResponseDTO particularsDTO =
//	                     new ListOfVlauesDetailsResponseDTO();
//
//	             particularsDTO.setId(taxVO.getParticulars().getId());
//	             particularsDTO.setValueDescription(
//	                     taxVO.getParticulars().getValueDescription());
//	             taxDTO.setParticulars(particularsDTO);
//	         }
//
//	         taxDTO.setAmount(taxVO.getAmount());
//
//	         taxResponse.add(taxDTO);
//	     }
//	 }
//
//	 responseDTO.setSalesReturnTaxDetails(taxResponse);
//
//	    
//	    return responseDTO;
//	}
//	
//	private void createUpdateSalesReturnVO(
//	        SalesReturnDTO dto,
//	        SalesReturnVO salesReturnVO)
//	        throws ApplicationException {
//
//
//
//	    // Document Details
//	   
//	    salesReturnVO.setDocNo(dto.getDocNo());
//	    salesReturnVO.setDocDate(dto.getDocDate());
//
//	   
//	    // Invoice Details
//	   
//
//	    salesReturnVO.setInvoiceNo(dto.getInvoiceNo());
//	    salesReturnVO.setInvoiceDate(dto.getInvoiceDate());
//	    salesReturnVO.setCustomerInvoiceNo(dto.getCustomerInvoiceNo());
//	    salesReturnVO.setCustomerInvoiceDate(dto.getCustomerInvoiceDate());
//	    salesReturnVO.setGatePassNo(dto.getGatePassNo());
//
//	   
//	    // Other Details
//	  
//
//	    salesReturnVO.setApprovedByAccounts(dto.getApprovedByAccounts());
//	    salesReturnVO.setCurrency(dto.getCurrency());
//	    salesReturnVO.setExchangeRate(dto.getExchangeRate());
//	    salesReturnVO.setReferenceNo(dto.getReferenceNo());
//	    salesReturnVO.setReferenceDate(dto.getReferenceDate());
//
//	    
//	    // Common Details
//	    
//
//	    salesReturnVO.setOrgId(dto.getOrgId());
//	    salesReturnVO.setFinancialYear(dto.getFinancialYear());
//	    salesReturnVO.setCancelRemarks(dto.getCancelRemarks());
//
//	    if (dto.getActive() != null) {
//	        salesReturnVO.setActive(dto.getActive());
//	    }
//
//	    if (dto.getCancel() != null) {
//	        salesReturnVO.setCancel(dto.getCancel());
//	    }
//
//	    salesReturnVO.setScreenCode(dto.getScreenCode());
//	    salesReturnVO.setScreenName(dto.getScreenName());
//	    
//	    
//	
//	 // Charges Summary
//	 
//	 salesReturnVO.setNetAmount(dto.getNetAmount());
//	 salesReturnVO.setAmountInWords(dto.getAmountInWords());
//	 salesReturnVO.setNarration(dto.getNarration());
//	 
//	
//	
//	    // Branch Mapping
//	    
//	    if (dto.getBranch() != null && dto.getBranch() != 0) {
//
//	        BranchVO branchVO = branchRepo.findById(dto.getBranch())
//	                .orElseThrow(() -> new ApplicationException("Branch Not Found"));
//
//	        salesReturnVO.setBranch(branchVO);
//	    }
//	   
//	    // Belongs To Mapping
//	   
//
//	    if (dto.getBelongsTo() != null && dto.getBelongsTo() != 0) {
//
//	        ListOfValuesDetailsVO belongsToVO =
//	                listOfValuesDetailsRepo.findById(dto.getBelongsTo())
//	                .orElseThrow(() -> new ApplicationException("Belongs To Not Found"));
//
//	        salesReturnVO.setBelongsTo(belongsToVO);
//	    }
//
//	   
//	    // Customer Mapping
//	   
//
//	    if (dto.getCustomer() != null && dto.getCustomer() != 0) {
//
//	        CustomerVO customerVO =
//	                customerRepo.findById(dto.getCustomer())
//	                .orElseThrow(() -> new ApplicationException("Customer Not Found"));
//
//	        salesReturnVO.setCustomer(customerVO);
//	    }
//
//	    
//	    // Location Mapping
//	 
//	    if (dto.getLocation() != null && dto.getLocation() != 0) {
//
//	        LocationVO locationVO =
//	                locationRepo.findById(dto.getLocation())
//	                .orElseThrow(() -> new ApplicationException("Location Not Found"));
//
//	        salesReturnVO.setLocation(locationVO);
//	    }
//
//	    
//	    // Return Type Mapping
//	    
//	    if (dto.getReturnType() != null && dto.getReturnType() != 0) {
//
//	        ListOfValuesDetailsVO returnTypeVO =
//	                listOfValuesDetailsRepo.findById(dto.getReturnType())
//	                .orElseThrow(() -> new ApplicationException("Return Type Not Found"));
//
//	        salesReturnVO.setReturnType(returnTypeVO);
//	    }
//
//	    
//	    // Invoice Reference Type Mapping
//	    
//
//	    if (dto.getInvoiceReferenceTypeId() != null
//	            && dto.getInvoiceReferenceTypeId() != 0) {
//
//	        ListOfValuesDetailsVO invoiceReferenceTypeVO =
//	                listOfValuesDetailsRepo.findById(
//	                        dto.getInvoiceReferenceTypeId())
//	                .orElseThrow(() ->
//	                        new ApplicationException(
//	                                "Invoice Reference Type Not Found"));
//
//	        salesReturnVO.setInvoiceReferenceType(
//	                invoiceReferenceTypeVO);
//	    }
//	    
//	    
//	 // Sales Return Details Grid Mapping
//		
//		salesReturnVO.getSalesReturnDetails().clear();
//
//		if (dto.getSalesReturnDetails() != null) {
//
//		    for (SalesReturnDetailsDto detailDTO : dto.getSalesReturnDetails()) {
//
//		        SalesReturnDetailsVO detailVO = new SalesReturnDetailsVO();
//
//		        // Parent Mapping
//		        detailVO.setSalesReturn(salesReturnVO);
//
//		       
//		        // Item Mapping
//		        
//
//		        if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {
//
//		            ItemMasterVO itemVO = itemMasterRepo
//		                    .findById(detailDTO.getItem())
//		                    .orElseThrow(() ->
//		                            new ApplicationException("Item Not Found"));
//
//		            detailVO.setItem(itemVO);
//		        }
//
//		        
//		        // Basic Fields
//		        
//
//		        detailVO.setItemDescription(detailDTO.getItemDescription());
//
//		        detailVO.setHsnSacCode(detailDTO.getHsnSacCode());
//
//		        detailVO.setTaxType(detailDTO.getTaxType());
//
//		       
//		        // GST Rate Mapping
//		       
//		        if (detailDTO.getTaxPercentage() != null) {
//
//		            GSTRateMasterVO gstRateVO = gstRateMasterRepo
//		                    .findById(detailDTO.getTaxPercentage())
//		                    .orElseThrow(() ->
//		                            new ApplicationException("GST Rate Not Found"));
//
//		            detailVO.setTaxPercentage(gstRateVO);
//		        }
//		        
//		        // Unit Mapping
//		       
//		        if (detailDTO.getUnit() != null && detailDTO.getUnit() != 0) {
//
//		            UnitMasterVO unitVO = unitMasterRepo
//		                    .findById(detailDTO.getUnit())
//		                    .orElseThrow(() ->
//		                            new ApplicationException("Unit Not Found"));
//
//		            detailVO.setUnit(unitVO);
//		        }
//
//		        
//		        // Quantity & Amount 
//
//		        detailVO.setStock(detailDTO.getStock());
//		        detailVO.setQtySold(detailDTO.getQtySold());
//		        detailVO.setReceivedQty(detailDTO.getReceivedQty());
//
//		        detailVO.setRate(detailDTO.getRate());
//		        detailVO.setRateInSelectedCurrency(detailDTO.getRateInSelectedCurrency());
//
//		        detailVO.setAmountInSelectedCurrency(detailDTO.getAmountInSelectedCurrency());
//		        detailVO.setAmount(detailDTO.getAmount());
//
//		        
//		        // GST Calculation Fields
//		       
//
//		        detailVO.setSgstRate(detailDTO.getSgstRate());
//		        detailVO.setSgstAmount(detailDTO.getSgstAmount());
//
//		        detailVO.setCgstRate(detailDTO.getCgstRate());
//		        detailVO.setCgstAmount(detailDTO.getCgstAmount());
//
//		        detailVO.setIgstRate(detailDTO.getIgstRate());
//		        detailVO.setIgstAmount(detailDTO.getIgstAmount());
//
//		       
//
//		        salesReturnVO.getSalesReturnDetails().add(detailVO);
//		    }
//		    
//		    
//		
//		 // Sales Return Tax Details Grid Mapping
//		
//
//		 salesReturnVO.getSalesReturnTaxDetails().clear();
//
//		 if (dto.getSalesReturnTaxDetails() != null) {
//
//		     for (SalesReturnTaxDetailsDTO taxDTO : dto.getSalesReturnTaxDetails()) {
//
//		         SalesReturnTaxDetailsVO taxVO = new SalesReturnTaxDetailsVO();
//
//		         // Parent Mapping
//		         taxVO.setSalesReturn(salesReturnVO);
//
//		         // Particulars Mapping
//		         if (taxDTO.getParticulars() != null && taxDTO.getParticulars() != 0) {
//
//		             ListOfValuesDetailsVO particularsVO =
//		                     listOfValuesDetailsRepo.findById(taxDTO.getParticulars())
//		                     .orElseThrow(() ->
//		                     new ApplicationException("Particulars Not Found"));
//
//		             taxVO.setParticulars(particularsVO);
//		         }
//
//		         // Amount
//		         taxVO.setAmount(taxDTO.getAmount());
//
//		         salesReturnVO.getSalesReturnTaxDetails().add(taxVO);
//		     }
//		 }
//		
//
//		}
//	}
//	    
//	    
//	    @Override
//	    public SalesReturnResponseDTO getSalesReturnById(
//	            Long id)
//	            throws ApplicationException {
//
//	        if (ObjectUtils.isEmpty(id)) {
//	            throw new ApplicationException("Invalid Id");
//	        }
//
//	        SalesReturnVO salesReturnVO =
//	                salesReturnRepo.findById(id)
//	                .orElseThrow(() ->
//	                        new ApplicationException("Sales Return Not Found"));

//	        return salesReturnResponse(salesReturnVO);
		return null;
	    }
	    
	    @Override
	    public List<SalesReturnResponseDTO> getAllSalesReturn(
	            Long orgId,
	            Long branch)
	            throws ApplicationException {

//	        List<SalesReturnVO> salesReturnList =
//	                salesReturnRepo.findByOrgIdAndBranch(orgId, branch);
//
//	        if (salesReturnList.isEmpty()) {
//	            throw new ApplicationException("No Sales Return Details Found");
//	        }
//
//	        List<SalesReturnResponseDTO> responseList =
//	                new ArrayList<>();
//
//	        for (SalesReturnVO salesReturnVO : salesReturnList) {
//
//	            responseList.add(
//	                    salesReturnResponse(salesReturnVO));
//	        }

//	        return responseList;
	    	return null;
	    }


		@Override
		public SalesReturnResponseDTO getSalesReturnById(Long id) throws ApplicationException {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	//salesorderamendment
		
		
		@Override
		@Transactional
		public Map<String, Object> createUpdateSalesOrderAmendment(
		        SalesOrderAmendmentDTO salesOrderAmendmentDTO)
		        throws ApplicationException {

		    SalesOrderAmendmentVO salesOrderAmendmentVO =
		            new SalesOrderAmendmentVO();

		    String message;

		    if (ObjectUtils.isNotEmpty(salesOrderAmendmentDTO.getId())) {

		        salesOrderAmendmentVO =
		                salesOrderAmendmentRepo.findById(
		                        salesOrderAmendmentDTO.getId())
		                .orElseThrow(() ->
		                new ApplicationException(
		                        "Sales Order Amendment Not Found"));

		        createUpdateSalesOrderAmendmentVOByDTO(
		                salesOrderAmendmentDTO,
		                salesOrderAmendmentVO);

		        message = "Sales Order Amendment Updated Successfully";

		    } else {

		        createUpdateSalesOrderAmendmentVOByDTO(
		                salesOrderAmendmentDTO,
		                salesOrderAmendmentVO);

		        message = "Sales Order Amendment Created Successfully";
		    }

		    SalesOrderAmendmentVO savedSalesOrderAmendment =
		            salesOrderAmendmentRepo.save(salesOrderAmendmentVO);

		    if (ObjectUtils.isNotEmpty(salesOrderAmendmentDTO.getId())) {

		        salesOrderAmendmentDetailsRepo
		                .deleteBySalesOrderAmendmentVO(savedSalesOrderAmendment);
		    }
		    
		    if (salesOrderAmendmentDTO.getDetails() != null) {

		        for (SalesOrderAmendmentDetailsDTO detailDTO
		                : salesOrderAmendmentDTO.getDetails()) {

		            SalesOrderAmendmentDetailsVO detailVO =
		                    new SalesOrderAmendmentDetailsVO();

		            if (detailDTO.getItem() != null) {

		                ItemMasterVO itemVO =
		                        itemMasterRepo.findById(detailDTO.getItem())
		                        .orElseThrow(() ->
		                        new ApplicationException("Item Not Found"));

		                detailVO.setItem(itemVO);
		            }

		            detailVO.setOldQty(detailDTO.getOldQty());
		            detailVO.setOldRate(detailDTO.getOldRate());
		            detailVO.setNewQty(detailDTO.getNewQty());
		            detailVO.setNewRate(detailDTO.getNewRate());
		            detailVO.setOldDeliveryDate(detailDTO.getOldDeliveryDate());
		            detailVO.setNewDeliveryDate(detailDTO.getNewDeliveryDate());

		            detailVO.setSalesOrderAmendmentVO(savedSalesOrderAmendment);

		            salesOrderAmendmentDetailsRepo.save(detailVO);
		        }
		    }

		    savedSalesOrderAmendment =
		            salesOrderAmendmentRepo.findById(
		                    savedSalesOrderAmendment.getId())
		            .orElseThrow(() ->
		            new ApplicationException(
		                    "Sales Order Amendment Not Found"));

		    Map<String, Object> response = new HashMap<>();

		    response.put("message", message);
		    response.put("salesOrderAmendmentVO",
		            salesOrderAmendmentResponse(savedSalesOrderAmendment));

		    return response;
		    
		    
		    
		}
		
		
		private void createUpdateSalesOrderAmendmentVOByDTO(
		        SalesOrderAmendmentDTO dto,
		        SalesOrderAmendmentVO vo)
		        throws ApplicationException {

		    if (dto.getBranch() != null) {

		        BranchVO branchVO = branchRepo.findById(dto.getBranch())
		                .orElseThrow(() ->
		                        new ApplicationException("Branch Not Found"));

		        vo.setBranch(branchVO);
		    }

//		    vo.setDocId(dto.getDocId());
		    vo.setSalesOrderNumber(dto.getSalesOrderNumber());
//		    vo.setDocDate(dto.getDocDate());
		    vo.setPartyPoAmendmentNo(dto.getPartyPoAmendmentNo());
		    vo.setSalesOrderDate(dto.getSalesOrderDate());
		    vo.setPartyPoAmendmentDate(dto.getPartyPoAmendmentDate());
		    vo.setPoNo(dto.getPoNo());
		    vo.setRevisionNo(dto.getRevisionNo());
		    vo.setPoDate(dto.getPoDate());
		    vo.setRemarks(dto.getRemarks());

		    vo.setActive(dto.getActive());
		    vo.setOrgId(dto.getOrgId());
		    vo.setCreatedBy(dto.getCreatedBy());
//		    vo.setUpdatedBy(dto.getUpdatedBy());
//		    vo.setCancel(dto.isCancel());
		    vo.setCancelRemarks(dto.getCancelRemarks());
		}
		
		private SalesOrderAmendmentResponseDTO salesOrderAmendmentResponse(
		        SalesOrderAmendmentVO salesOrderAmendmentVO) {

		    SalesOrderAmendmentResponseDTO responseDTO =
		            new SalesOrderAmendmentResponseDTO();

		    responseDTO.setId(salesOrderAmendmentVO.getId());

		    if (salesOrderAmendmentVO.getBranch() != null) {
		        responseDTO.setBranchId(
		                salesOrderAmendmentVO.getBranch().getId());

		        responseDTO.setBranchName(
		                salesOrderAmendmentVO.getBranch().getBranchName());
		    }

		    responseDTO.setDocId(salesOrderAmendmentVO.getDocId());
		    responseDTO.setDocDate(salesOrderAmendmentVO.getDocDate());
		    responseDTO.setSalesOrderNumber(
		            salesOrderAmendmentVO.getSalesOrderNumber());
		    responseDTO.setPartyPoAmendmentNo(
		            salesOrderAmendmentVO.getPartyPoAmendmentNo());
		    responseDTO.setSalesOrderDate(
		            salesOrderAmendmentVO.getSalesOrderDate());
		    responseDTO.setPartyPoAmendmentDate(
		            salesOrderAmendmentVO.getPartyPoAmendmentDate());
		    responseDTO.setPoNo(salesOrderAmendmentVO.getPoNo());
		    responseDTO.setRevisionNo(salesOrderAmendmentVO.getRevisionNo());
		    responseDTO.setPoDate(salesOrderAmendmentVO.getPoDate());
		    responseDTO.setRemarks(salesOrderAmendmentVO.getRemarks());

		    responseDTO.setOrgId(salesOrderAmendmentVO.getOrgId());
		    responseDTO.setCreatedBy(salesOrderAmendmentVO.getCreatedBy());
		    responseDTO.setUpdatedBy(salesOrderAmendmentVO.getUpdatedBy());
		    responseDTO.setActive(salesOrderAmendmentVO.isActive());
		    responseDTO.setCancel(salesOrderAmendmentVO.isCancel());
		    responseDTO.setCancelRemarks(
		            salesOrderAmendmentVO.getCancelRemarks());
		    responseDTO.setScreenName(
		            salesOrderAmendmentVO.getScreenName());
		    responseDTO.setScreenCode(
		            salesOrderAmendmentVO.getScreenCode());

		    // Fetch child table directly
		    List<SalesOrderAmendmentDetailsVO> detailList =
		            salesOrderAmendmentDetailsRepo
		                    .findBySalesOrderAmendmentVO_Id(
		                            salesOrderAmendmentVO.getId());

		    List<SalesOrderAmendmentDetailsResponseDTO> details =
		            new ArrayList<>();

		    for (SalesOrderAmendmentDetailsVO detailVO : detailList) {

		        SalesOrderAmendmentDetailsResponseDTO detailDTO =
		                new SalesOrderAmendmentDetailsResponseDTO();

		        detailDTO.setId(detailVO.getId());

//		        if (detailVO.getItem() != null) {
//		        	detailDTO.setId(
//		                    detailVO.getItem().getId());
//		            detailDTO.setItemCode(
//		                    detailVO.getItem().getItemCode());
//
//		            detailDTO.setItemDescription(
//		                    detailVO.getItem().getItemDescription());
//		        }
		        
		        
		        if (detailVO.getItem() != null) {

		            ItemMasterResponseDetailsDTO itemDTO =
		                    new ItemMasterResponseDetailsDTO();

		            itemDTO.setId(
		                    detailVO.getItem().getId());

		            itemDTO.setItemCode(
		                    detailVO.getItem().getItemCode());

		            itemDTO.setItemDescription(
		                    detailVO.getItem().getItemDescription());

		            // If you need unit
//		            if (detailVO.getItem().getUnit() != null) {
//
//		                UnitMasterResponseDTO unitDTO =
//		                        new UnitMasterResponseDTO();
//
//		                unitDTO.setId(
//		                        detailVO.getItem().getUnit().getId());
//
//		                unitDTO.setDescription(
//		                        detailVO.getItem().getUnit().getDescription());
//
//		                itemDTO.setUnit(unitDTO);
//		            }

		            detailDTO.setItem(itemDTO);
		        }

		        detailDTO.setOldQty(detailVO.getOldQty());
		        detailDTO.setOldRate(detailVO.getOldRate());
		        detailDTO.setNewQty(detailVO.getNewQty());
		        detailDTO.setNewRate(detailVO.getNewRate());

		        detailDTO.setOldDeliveryDate(
		                detailVO.getOldDeliveryDate());

		        detailDTO.setNewDeliveryDate(
		                detailVO.getNewDeliveryDate());

		        details.add(detailDTO);
		    }

		    responseDTO.setSalesOrderAmendmentDetails(details);

		    return responseDTO;
		}
		
		@Override
		public SalesOrderAmendmentResponseDTO getSalesOrderAmendmentById(Long id)
		        throws ApplicationException {

		    SalesOrderAmendmentVO salesOrderAmendmentVO =
		            salesOrderAmendmentRepo.getSalesOrderAmendmentById(id);

		    if (salesOrderAmendmentVO == null) {

		        throw new ApplicationException(
		                "Sales Order Amendment Not Found");
		    }

		    return salesOrderAmendmentResponse(salesOrderAmendmentVO);
		}
		
		
		@Override
		public List<SalesOrderAmendmentResponseDTO> getSalesOrderAmendmentByOrgId(
		        Long orgId,
		        Long branch)
		        throws ApplicationException {

		    List<SalesOrderAmendmentVO> salesOrderAmendmentList =
		            salesOrderAmendmentRepo.getSalesOrderAmendmentByOrgId(
		                    orgId,
		                    branch);

		    if (salesOrderAmendmentList == null
		            || salesOrderAmendmentList.isEmpty()) {

		        throw new ApplicationException(
		                "Sales Order Amendment Not Found");
		    }

		    List<SalesOrderAmendmentResponseDTO> responseList =
		            new ArrayList<>();

		    for (SalesOrderAmendmentVO salesOrderAmendmentVO
		            : salesOrderAmendmentList) {

		        responseList.add(
		                salesOrderAmendmentResponse(
		                        salesOrderAmendmentVO));
		    }

		    return responseList;
		}
		
		@Override
		public List<Map<String, Object>> getOrderAcceptanceBySalesOrderAmendment(
		        Long orgId, Long branch) throws ApplicationException {

		    List<Object[]> orderAcceptanceList =
		            salesContractRepo.getOrderAcceptanceBySalesOrderAmendment(orgId, branch);

		    List<Map<String, Object>> responseList = new ArrayList<>();

		    for (Object[] obj : orderAcceptanceList) {

		        Map<String, Object> map = new HashMap<>();

		        map.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);
		        map.put("docId", obj[1] != null ? obj[1].toString() : "");
		        map.put("docDate", obj[2] != null ? obj[2] : null);
		        map.put("customerPurchaseOrderNo",
		                obj[3] != null ? obj[3].toString() : "");
		        map.put("customerPurchaseOrderDate",
		                obj[4] != null ? obj[4] : null);

		        responseList.add(map);
		    }

		    return responseList;
		}
		
//		
//		@Override
//		public Map<String, Object> getItemDropdownBySalesOrderAmendment(
//		        Long salesContractId,
//		        Long orgId,
//		        Long branch)
//		        throws ApplicationException {
//
//		    Map<String, Object> responseMap = new HashMap<>();
//
//		    List<Object[]> itemList =
//		            salesContractDetailsRepo.getItemDropdownBySalesOrderAmendment(
//		                    salesContractId,
//		                    orgId,
//		                    branch);
//
//		    List<SalesContractOrderAmendmentResponseDto> responseDTOList =
//		            new ArrayList<>();
//
//		    for (Object[] obj : itemList) {
//
//		    	SalesContractOrderAmendmentResponseDto dto =
//		                new SalesContractOrderAmendmentResponseDto();
//
//		        dto.setItemId(((Number)obj[0]).longValue());
//
//		        dto.setItemCode((String)obj[1]);
//
//		        dto.setItemDescription((String)obj[2]);
//
//		        responseDTOList.add(dto);
//		    }
//
//		    responseMap.put("message", "Item List Fetched Successfully");
//
//		    responseMap.put("itemList", responseDTOList);
//
//		    return responseMap;
//		}
		
		
		@Override
		public List<Map<String, Object>> getOrderAcceptanceItemsWithAmendment(
		        String docId,
		        Long orgId,
		        Long branch) throws ApplicationException {

		    List<Object[]> itemDetails =
		    		orderAcceptanceRepo.getOrderAcceptanceItemsWithAmendment(
		                    docId,
		                    orgId,
		                    branch);

		    return getOrderAcceptanceItemDetails(itemDetails);
		}

		private List<Map<String, Object>> getOrderAcceptanceItemDetails(
		        List<Object[]> itemDetails) {

		    List<Map<String, Object>> responseList = new ArrayList<>();

		    for (Object[] obj : itemDetails) {

		        Map<String, Object> map = new HashMap<>();

		        map.put("item", obj[0] != null ? ((Number) obj[0]).longValue() : null);
		        map.put("itemCode", obj[1] != null ? obj[1].toString() : "");
		        map.put("itemDescription", obj[2] != null ? obj[2].toString() : "");
		        map.put("oldQty", obj[3] != null ? obj[3] : null);
		        map.put("oldDeliveryDate", obj[4] != null ? obj[4] : null);
		        map.put("oldRate", obj[5] != null ? obj[5] : null);

		        responseList.add(map);
		    }

		    return responseList;
		}
		
		@Override
		public Integer getSalesOrderAmdRevisionNo(
		        String salesOrderNo,
		        Long item,
		        Long orgId,
		        Long branch) throws ApplicationException {

		    Integer revisionNo =
		    		salesOrderAmendmentRepo.getSalesOrderAmdRevisionNo(
		                    salesOrderNo,
		                    item,
		                    orgId,
		                    branch);

		    return revisionNo;
		}
		
		//PurchaseContractAmendment
		
				
		
		@Override
		@Transactional
		public Map<String, Object> createUpdatePurchaseContractAmendment(
		        PurchaseContractAmendmentDto purchaseContractAmendmentDto,
		        MultipartFile[] files)
		        throws ApplicationException {

		    PurchaseContractAmendmentVO purchaseContractAmendmentVO;
		    String message;

		    if (ObjectUtils.isNotEmpty(
		            purchaseContractAmendmentDto.getId())) {

		        purchaseContractAmendmentVO =
		                purchaseContractAmendmentRepo
		                        .findById(
		                                purchaseContractAmendmentDto.getId())
		                        .orElseThrow(() ->
		                                new ApplicationException(
		                                        "Purchase Contract Amendment Not Found"));

		        purchaseContractAmendmentVO.setUpdatedBy(
		                purchaseContractAmendmentDto.getCreatedBy());

		        message =
		                "Purchase Contract Amendment Updated Successfully";

		    } else {

		        purchaseContractAmendmentVO =
		                new PurchaseContractAmendmentVO();

		        purchaseContractAmendmentVO.setCreatedBy(
		                purchaseContractAmendmentDto.getCreatedBy());

		        purchaseContractAmendmentVO.setUpdatedBy(
		                purchaseContractAmendmentDto.getCreatedBy());

		        message =
		                "Purchase Contract Amendment Created Successfully";
		    }

		    // Header + Child Mapping
		    createUpdatePurchaseContractAmendmentVOByDTO(
		            purchaseContractAmendmentDto,
		            purchaseContractAmendmentVO);

		    // Save Header
		    purchaseContractAmendmentVO =
		            purchaseContractAmendmentRepo.save(
		                    purchaseContractAmendmentVO);

		    // Save Attachments
		    saveAttachments(
		            files,
		            purchaseContractAmendmentVO);

		    // Response
		    PurchaseContractAmendmentResponseDto responseDTO =
		            purchaseContractAmendmentResponse(
		                    purchaseContractAmendmentVO);

		    Map<String, Object> response =
		            new HashMap<>();

		    response.put(
		            "message",
		            message);

		    response.put(
		            "purchaseContractAmendmentVO",
		            responseDTO);

		    return response;
		}
		
		private void createUpdatePurchaseContractAmendmentVOByDTO(
		        PurchaseContractAmendmentDto dto,
		        PurchaseContractAmendmentVO vo)
		        throws ApplicationException {

		    // =========================
		    // Branch
		    // =========================

		    if (dto.getBranch() != null) {

		        BranchVO branchVO =
		                branchRepo.findById(dto.getBranch())
		                        .orElseThrow(() ->
		                                new ApplicationException(
		                                        "Branch Not Found"));

		        vo.setBranch(branchVO);
		    }

		    // =========================
		    // Customer
		    // =========================

		    if (dto.getCustomer() != null) {

		        CustomerVO customerVO =
		                customerRepo.findById(dto.getCustomer())
		                        .orElseThrow(() ->
		                                new ApplicationException(
		                                        "Customer Not Found"));

		        vo.setCustomer(customerVO);
		    }

		    // =========================
		    // Header
		    // =========================

		    vo.setBelongsTo(dto.getBelongsTo());

		    // vo.setDocId(dto.getDocId());
		    // vo.setDocDate(dto.getDocDate());

		    vo.setContractNo(dto.getContractNo());
		    vo.setContractDate(dto.getContractDate());

		    vo.setRevisionNo(dto.getRevisionNo());

		    vo.setRefNo(dto.getRefNo());
		    vo.setRefDate(dto.getRefDate());

		    // =========================
		    // Summary
		    // =========================

		    vo.setFreightType(dto.getFreightType());
		    vo.setPackingType(dto.getPackingType());
		    vo.setInsuranceAmount(dto.getInsuranceAmount());
		    vo.setModeOfDespatch(dto.getModeOfDespatch());
		    vo.setTaxDescription(dto.getTaxDescription());
		    vo.setPreparedBy(dto.getPreparedBy());
		    vo.setAuthorisedBy(dto.getAuthorisedBy());
		    vo.setRemarks(dto.getRemarks());

		    // =========================
		    // Common
		    // =========================

		    vo.setOrgId(dto.getOrgId());
		    vo.setActive(dto.isActive());
		    vo.setCancelRemarks(dto.getCancelRemarks());

		    // =========================
		    // Delete Old Details
		    // =========================

		    if (vo.getId() != null) {

		        purchaseContractAmendmentDetailsRepo
		                .deleteByPurchaseContractAmendmentVO(vo);
		    }

		    // =========================
		    // Save Grid
		    // =========================

		    List<PurchaseContractAmendmentDetailsVO>
		            detailsList = new ArrayList<>();

		    if (dto.getDetails() != null
		            && !dto.getDetails().isEmpty()) {

		        for (PurchaseContractAmendmentDetailsDto detailDto
		                : dto.getDetails()) {

		            PurchaseContractAmendmentDetailsVO detailVO =
		                    new PurchaseContractAmendmentDetailsVO();

		            detailVO.setPurchaseContractAmendmentVO(vo);

		            // =========================
		            // Item
		            // =========================

		            if (detailDto.getItem() != null) {

		                ItemMasterVO itemVO =
		                        itemMasterRepo.findById(
		                                detailDto.getItem())
		                                .orElseThrow(() ->
		                                        new ApplicationException(
		                                                "Item Not Found"));

		                detailVO.setItem(itemVO);
		            }

		            // =========================
		            // Unit
		            // =========================

		            if (detailDto.getUnit() != null) {

		                UnitMasterVO unitVO =
		                        unitMasterRepo.findById(
		                                detailDto.getUnit())
		                                .orElseThrow(() ->
		                                        new ApplicationException(
		                                                "Unit Not Found"));

		                detailVO.setUnit(unitVO);
		            }

		            detailVO.setOldRate(
		                    detailDto.getOldRate());

		            detailVO.setNewRate(
		                    detailDto.getNewRate());

		            detailVO.setValidFrom(
		                    detailDto.getValidFrom());

		            detailVO.setValidTo(
		                    detailDto.getValidTo());

		            detailVO.setNewValidFrom(
		                    detailDto.getNewValidFrom());

		            detailVO.setNewValidTo(
		                    detailDto.getNewValidTo());

		            detailsList.add(detailVO);
		        }
		    }

		    purchaseContractAmendmentDetailsRepo
		            .saveAll(detailsList);
		}
		
		private PurchaseContractAmendmentResponseDto purchaseContractAmendmentResponse(
		        PurchaseContractAmendmentVO vo) {

		    PurchaseContractAmendmentResponseDto responseDto =
		            new PurchaseContractAmendmentResponseDto();

		    responseDto.setId(vo.getId());

		    // =========================
		    // Branch
		    // =========================

		    if (vo.getBranch() != null) {

		        BranchResponseDTO branchResponseDTO =
		                new BranchResponseDTO();

		        branchResponseDTO.setId(
		                vo.getBranch().getId());

		        branchResponseDTO.setBranchName(
		                vo.getBranch().getBranchName());

		        responseDto.setBranch(branchResponseDTO);
		    }

		    // =========================
		    // Party
		    // =========================

		    if (vo.getCustomer() != null) {

		        PurchaseContractAmendmentCustomerResponceDto customerResponseDTO =
		                new PurchaseContractAmendmentCustomerResponceDto();

		        customerResponseDTO.setId(vo.getCustomer().getId());
		        customerResponseDTO.setCustomerName(vo.getCustomer().getCustomerName());

		        responseDto.setCustomer(customerResponseDTO);
		    }
		    
		       
		    // =========================
		    // Header
		    // =========================

		    responseDto.setBelongsTo(vo.getBelongsTo());
		    responseDto.setDocId(vo.getDocId());
		    responseDto.setDocDate(vo.getDocDate());

		    

		    responseDto.setContractNo(vo.getContractNo());
		    responseDto.setContractDate(vo.getContractDate());

		    responseDto.setRevisionNo(vo.getRevisionNo());

		    responseDto.setRefNo(vo.getRefNo());
		    responseDto.setRefDate(vo.getRefDate());

		    // =========================
		    // Summary
		    // =========================

		    responseDto.setFreightType(vo.getFreightType());
		    responseDto.setPackingType(vo.getPackingType());
		    responseDto.setModeOfDespatch(vo.getModeOfDespatch());
		    responseDto.setTaxDescription(vo.getTaxDescription());
		    responseDto.setPreparedBy(vo.getPreparedBy());
		    responseDto.setAuthorisedBy(vo.getAuthorisedBy());
		    responseDto.setRemarks(vo.getRemarks());

		    // =========================
		    // Common
		    // =========================

		    responseDto.setOrgId(vo.getOrgId());
		    responseDto.setCreatedBy(vo.getCreatedBy());
		    responseDto.setUpdatedBy(vo.getUpdatedBy());
		    responseDto.setCancel(vo.isCancel());
		    responseDto.setCancelRemarks(vo.getCancelRemarks());
		    responseDto.setActive(vo.isActive());

		    // =========================
		    // Details
		    // =========================

		    List<PurchaseContractAmendmentDetailsResponseDto> detailResponseList =
		            new ArrayList<>();

		    List<PurchaseContractAmendmentDetailsVO> detailVOList =
		            purchaseContractAmendmentDetailsRepo
		            .findByPurchaseContractAmendmentVO(vo);

		    for (PurchaseContractAmendmentDetailsVO detailVO : detailVOList) {

		        PurchaseContractAmendmentDetailsResponseDto detailResponse =
		                new PurchaseContractAmendmentDetailsResponseDto();

		        detailResponse.setId(detailVO.getId());

		        if (detailVO.getItem() != null) {

		            PurchaseContractAmendmentDetailsItemResponseDto itemResponse =
		                    new PurchaseContractAmendmentDetailsItemResponseDto();

		            itemResponse.setItemCode(
		                    detailVO.getItem().getItemCode());

		            itemResponse.setItemDescription(
		                    detailVO.getItem().getItemDescription());

		            detailResponse.setItem(itemResponse);
		        }

		        if (detailVO.getUnit() != null) {

		            UnitResponseDTO unitResponse = new UnitResponseDTO();

		            unitResponse.setId(detailVO.getUnit().getId());
		            unitResponse.setUnitId(detailVO.getUnit().getUnitId());
		            

		            detailResponse.setUnit(unitResponse);
		        }
		        detailResponse.setOldRate(detailVO.getOldRate());
		        detailResponse.setNewRate(detailVO.getNewRate());

		        detailResponse.setValidFrom(detailVO.getValidFrom());
		        detailResponse.setValidTo(detailVO.getValidTo());

		        detailResponse.setNewValidFrom(detailVO.getNewValidFrom());
		        detailResponse.setNewValidTo(detailVO.getNewValidTo());

		        detailResponseList.add(detailResponse);
		    }

		    responseDto.setDetails(detailResponseList);

		 // =========================
		 // Attachments
		 // =========================

		 List<PurchaseContractAmendmentAttachmentResponseDto>
		         attachmentResponseList = new ArrayList<>();

		 List<PurchaseContractAmendmentAttachmentVO>
		         attachmentVOList =
		         purchaseContractAmendmentAttachmentRepo
		                 .findByPurchaseContractAmendmentVO(vo);

		 if (attachmentVOList != null) {

		     for (PurchaseContractAmendmentAttachmentVO fileVO
		             : attachmentVOList) {

		         PurchaseContractAmendmentAttachmentResponseDto fileDTO =
		                 new PurchaseContractAmendmentAttachmentResponseDto();

		         fileDTO.setId(
		                 fileVO.getId());

		         fileDTO.setName(
		                 fileVO.getName());

		         fileDTO.setFileName(
		                 fileVO.getFileName());

		         String urlPath = uploadPath
		                 .replace("C:/", "/")
		                 .replace("\\", "/");

		         fileDTO.setFilePath(
		                 serverBaseUrl
		                 + urlPath
		                 + fileVO.getFileName());

		         fileDTO.setFileSize(
		                 fileVO.getFileSize());

		         fileDTO.setContentType(
		                 fileVO.getContentType());

		         fileDTO.setUploadOn(
		                 fileVO.getUploadOn());

		         attachmentResponseList.add(
		                 fileDTO);
		     }
		 }

		 responseDto.setAttachments(
		         attachmentResponseList);

		 return responseDto;
		 
		}
		
		
		private void saveAttachments(
		        MultipartFile[] files,
		        PurchaseContractAmendmentVO purchaseContractAmendmentVO)
		        throws ApplicationException {

		    // If no new files are uploaded, keep existing files
		    if (files == null || files.length == 0) {
		        return;
		    }

		    try {

		        File folder = new File(uploadPath1);

		        if (!folder.exists()) {
		            folder.mkdirs();
		        }

		        // ==========================================
		        // Delete Existing Attachments
		        // ==========================================

		        List<PurchaseContractAmendmentAttachmentVO> oldAttachments =
		                purchaseContractAmendmentAttachmentRepo
		                        .findByPurchaseContractAmendmentVO(
		                                purchaseContractAmendmentVO);

		        for (PurchaseContractAmendmentAttachmentVO oldAttachment
		                : oldAttachments) {

		            // Delete physical file
		            if (oldAttachment.getFilePath() != null) {

		                File oldFile =
		                        new File(oldAttachment.getFilePath());

		                if (oldFile.exists()) {
		                    oldFile.delete();
		                }
		            }
		        }

		        // Delete old attachment records from DB
		        if (!oldAttachments.isEmpty()) {

		            purchaseContractAmendmentAttachmentRepo
		                    .deleteByPurchaseContractAmendmentVO(
		                            purchaseContractAmendmentVO);
		        }

		        // ==========================================
		        // Save New Attachments
		        // ==========================================

		        List<PurchaseContractAmendmentAttachmentVO>
		                attachmentList = new ArrayList<>();

		        for (MultipartFile file : files) {

		            if (file == null || file.isEmpty()) {
		                continue;
		            }

		            String originalFileName =
		                    file.getOriginalFilename();

		            String uniqueFileName =
		                    UUID.randomUUID()
		                    + "_"
		                    + originalFileName;

		            Path path =
		                    Paths.get(
		                            uploadPath1,
		                            uniqueFileName);

		            try (InputStream inputStream =
		                         file.getInputStream()) {

		                Files.copy(
		                        inputStream,
		                        path,
		                        StandardCopyOption.REPLACE_EXISTING);
		            }

		            PurchaseContractAmendmentAttachmentVO attachment =
		                    new PurchaseContractAmendmentAttachmentVO();

		            attachment.setPurchaseContractAmendmentVO(
		                    purchaseContractAmendmentVO);

		            attachment.setName(
		                    originalFileName);

		            attachment.setFileName(
		                    uniqueFileName);

		            attachment.setFilePath(
		                    path.toString());

		            attachment.setFileSize(
		                    file.getSize());

		            attachment.setContentType(
		                    file.getContentType());

		            attachment.setUploadOn(
		                    LocalDateTime.now());

		            attachmentList.add(attachment);
		        }

		        // Save new attachment records
		        List<PurchaseContractAmendmentAttachmentVO>
		                savedAttachments =
		                purchaseContractAmendmentAttachmentRepo
		                        .saveAll(attachmentList);

		        purchaseContractAmendmentVO
		                .setPurchaseContractAmendmentAttachment(
		                        savedAttachments);

		    } catch (IOException e) {

		        throw new ApplicationException(
		                "File Upload Failed : "
		                + e.getMessage());
		    }
		}
		
		@Override
		public PurchaseContractAmendmentResponseDto getPurchaseContractAmendmentById(
		        Long id) throws ApplicationException {

		    PurchaseContractAmendmentVO purchaseContractAmendmentVO =
		            purchaseContractAmendmentRepo.findById(id)
		            .orElseThrow(() ->
		                    new ApplicationException(
		                            "Purchase Contract Amendment Not Found"));

		    return purchaseContractAmendmentResponse(
		            purchaseContractAmendmentVO);
		}
		
		
		
		@Override
		public List<PurchaseContractAmendmentResponseDto>
		        getPurchaseContractAmendmentByOrgId(
		                Long orgId,
		                Long branch)
		        throws ApplicationException {

		    List<PurchaseContractAmendmentVO> voList =
		            purchaseContractAmendmentRepo.findByOrgId(
		                    orgId,
		                    branch);

		    List<PurchaseContractAmendmentResponseDto> responseList =
		            new ArrayList<>();

		    for (PurchaseContractAmendmentVO vo : voList) {

		        responseList.add(
		                purchaseContractAmendmentResponse(vo));
		    }

		    return responseList;
		}
		
		@Override
		public List<PurchaseContractAmendmentContractDropdownResponseDto> getContractNoDropdownforPurchaseContractAmendment(
		        Long orgId,
		        Long branch) throws ApplicationException {

		    List<PurchaseContractAmendmentVO> voList =
		            purchaseContractAmendmentRepo.findContractNoDropdown(orgId, branch);

		    List<PurchaseContractAmendmentContractDropdownResponseDto> responseList =
		            new ArrayList<>();

		    for (PurchaseContractAmendmentVO vo : voList) {

		        PurchaseContractAmendmentContractDropdownResponseDto dto =
		                new PurchaseContractAmendmentContractDropdownResponseDto();

		        dto.setId(vo.getId());
		        dto.setContractNo(vo.getContractNo());

		        responseList.add(dto);
		    }

		    return responseList;
		}
		
		
		@Override
		public List<PurchaseContractAmendmentItemDropdownResponseDto>
		        getItemDropdownForPurchaseContractAmendment(
		                Long contractId)
		        throws ApplicationException {

		    List<Object[]> objectList =
		            purchaseContractRepo.getItemsByContractId(contractId);

		    List<PurchaseContractAmendmentItemDropdownResponseDto> responseList =
		            new ArrayList<>();

		    for (Object[] obj : objectList) {

		        PurchaseContractAmendmentItemDropdownResponseDto responseDto =
		                new PurchaseContractAmendmentItemDropdownResponseDto();

		        responseDto.setId(
		                ((Number) obj[0]).longValue());

		        responseDto.setItemCode(
		                (String) obj[1]);

		        responseDto.setItemDescription(
		                (String) obj[2]);

		        responseList.add(responseDto);
		    }

		    return responseList;
		}
		
		
		@Override
		public Integer getPurchaseContractAmdRevisionNo(
		        String contractNo,
		        Long orgId,
		        Long branch)
		        throws ApplicationException {

		    Integer revisionNo =
		            purchaseContractAmendmentRepo.getPurchaseContractAmdRevisionNo(
		                    contractNo,
		                    orgId,
		                    branch);

		    return revisionNo;
		}
		
		
		
}