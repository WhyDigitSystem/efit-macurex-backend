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

import com.efitops.basesetup.ResponseDTO.CustomerResonse1DTO;
import com.efitops.basesetup.ResponseDTO.EnquiryCusContactResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.EnquiryAttachmentResponseDTO;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryDetailsDTO;
import com.efitops.basesetup.dto.EnquiryDetailsReponseDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.EnquiryTermsandCondDTO;
import com.efitops.basesetup.dto.EnquiryTermsandCondResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDetailsDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesDeliverySchedulePlanDTO;
import com.efitops.basesetup.dto.SalesDeliverySchedulePlanResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
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
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.SalesContractDetailsVO;
import com.efitops.basesetup.entity.SalesContractVO;
import com.efitops.basesetup.entity.SalesDeliveryScheduleDetailsVO;
import com.efitops.basesetup.entity.SalesDeliverySchedulePlanVO;
import com.efitops.basesetup.entity.SalesDeliveryScheduleVO;
import com.efitops.basesetup.entity.SalesReturnVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerContactDetailsRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.EnquiryAttachmentRepo;
import com.efitops.basesetup.repository.EnquiryDetailsRepo;
import com.efitops.basesetup.repository.EnquiryRepo;
import com.efitops.basesetup.repository.EnquiryTermsandCondRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.SalesContractDetailsRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleDetailsRepo;
import com.efitops.basesetup.repository.SalesDeliverySchedulePlanRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleRepo;
import com.efitops.basesetup.repository.SalesReturnRepo;

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
	EmployeeMasterRepo employeeMasterRepo;
	
	
	
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

	        enquiryVO.setPartyid(customer);
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

	    if (enquiryVO.getPartyid() != null) {

	        responseDTO.setCustomerVO(
	                new CustomerResonse1DTO(
	                        enquiryVO.getPartyid().getId(),
	                        enquiryVO.getPartyid().getCustomerName()));
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
	
	//salesdeliveryschedule
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesDeliverySchedule(
	        SalesDeliveryScheduleDTO salesDeliveryScheduleDTO)
	        throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    String message;

	    SalesDeliveryScheduleVO salesDeliveryScheduleVO;

	    if (ObjectUtils.isEmpty(salesDeliveryScheduleDTO.getId())) {

	        salesDeliveryScheduleVO = new SalesDeliveryScheduleVO();

	        salesDeliveryScheduleVO.setCreatedBy(
	                salesDeliveryScheduleDTO.getCreatedBy());

	        salesDeliveryScheduleVO.setUpdatedBy(
	                salesDeliveryScheduleDTO.getCreatedBy());

	        message = "Sales Delivery Schedule Created Successfully";

	    } else {

	        salesDeliveryScheduleVO = salesDeliveryScheduleRepo
	                .findById(salesDeliveryScheduleDTO.getId())
	                .orElseThrow(() ->
	                        new ApplicationException("Sales Delivery Schedule Not Found"));

	        salesDeliveryScheduleVO.setUpdatedBy(
	                salesDeliveryScheduleDTO.getCreatedBy());

	        
	     // Delete old Grid Details
	        salesDeliveryScheduleDetailsRepo
	                .deleteAll(salesDeliveryScheduleVO.getDetails());

	        salesDeliveryScheduleVO.getDetails().clear();

	        // Delete old Delivery Schedule
	        salesDeliverySchedulePlanRepo
	                .deleteAll(salesDeliveryScheduleVO.getDeliverySchedules());

	        salesDeliveryScheduleVO.getDeliverySchedules().clear();

	        message = "Sales Delivery Schedule Updated Successfully";
	        
	    }

	    // Header + Grid Mapping
	    createUpdateSalesDeliveryScheduleVOByDTO(
	            salesDeliveryScheduleDTO,
	            salesDeliveryScheduleVO);

	    // Save Header + Details
	    salesDeliveryScheduleVO =
	            salesDeliveryScheduleRepo.save(salesDeliveryScheduleVO);

	    // Reload Latest Data
	    salesDeliveryScheduleVO =
	            salesDeliveryScheduleRepo.findById(
	                    salesDeliveryScheduleVO.getId())
	            .orElseThrow(() ->
	                    new ApplicationException("Sales Delivery Schedule Not Found"));

	    // Response
	    SalesDeliveryScheduleResponseDTO responseDTO =
	            buildSalesDeliveryScheduleResponse(
	                    salesDeliveryScheduleVO);

	    response.put("message", message);
	    response.put("salesDeliverySchedule", responseDTO);

	    return response;
	}
	
	private void createUpdateSalesDeliveryScheduleVOByDTO(
	        SalesDeliveryScheduleDTO salesDeliveryScheduleDTO,
	        SalesDeliveryScheduleVO salesDeliveryScheduleVO)
	        throws ApplicationException {

	    // ================= Header =================

	    salesDeliveryScheduleVO.setDlvNo(salesDeliveryScheduleDTO.getDlvNo());
	    salesDeliveryScheduleVO.setDlvDate(salesDeliveryScheduleDTO.getDlvDate());
	    salesDeliveryScheduleVO.setMonthOfSchedule(salesDeliveryScheduleDTO.getMonthOfSchedule());
	    salesDeliveryScheduleVO.setBelongsTo(salesDeliveryScheduleDTO.getBelongsTo());
	    salesDeliveryScheduleVO.setMonthYear(salesDeliveryScheduleDTO.getMonthYear());
	    salesDeliveryScheduleVO.setRemarks(salesDeliveryScheduleDTO.getRemarks());

	    salesDeliveryScheduleVO.setOrgId(salesDeliveryScheduleDTO.getOrgId());
	    salesDeliveryScheduleVO.setFinancialYear(salesDeliveryScheduleDTO.getFinancialYear());

	    salesDeliveryScheduleVO.setCancelRemarks(salesDeliveryScheduleDTO.getCancelRemarks());

	    if (salesDeliveryScheduleDTO.getActive() != null) {
	        salesDeliveryScheduleVO.setActive(salesDeliveryScheduleDTO.getActive());
	    }

	    if (salesDeliveryScheduleDTO.getCancel() != null) {
	        salesDeliveryScheduleVO.setCancel(salesDeliveryScheduleDTO.getCancel());
	    }

	    salesDeliveryScheduleVO.setScreenCode(salesDeliveryScheduleDTO.getScreenCode());
	    salesDeliveryScheduleVO.setScreenName(salesDeliveryScheduleDTO.getScreenName());

	    // ================= Branch =================

	    if (salesDeliveryScheduleDTO.getBranchId() != null) {

	        BranchVO branch = branchRepo.findById(salesDeliveryScheduleDTO.getBranchId())
	                .orElseThrow(() -> new ApplicationException("Branch Not Found"));

	        salesDeliveryScheduleVO.setBranch(branch);
	    }

	    // ================= Customer =================

	    if (salesDeliveryScheduleDTO.getCustomerId() != null) {

	        CustomerVO customer = customerRepo.findById(salesDeliveryScheduleDTO.getCustomerId())
	                .orElseThrow(() -> new ApplicationException("Customer Not Found"));

	        salesDeliveryScheduleVO.setCustomer(customer);
	    }

	    // ================= Details =================

	    List<SalesDeliveryScheduleDetailsVO> detailsList = new ArrayList<>();

	    if (salesDeliveryScheduleDTO.getDetails() != null) {

	        for (SalesDeliveryScheduleDetailsDTO dto : salesDeliveryScheduleDTO.getDetails()) {

	            SalesDeliveryScheduleDetailsVO detail = new SalesDeliveryScheduleDetailsVO();

	            // Sales Contract
	            if (dto.getSalesContractId() != null) {

	                SalesContractVO salesContract = salesContractRepo.findById(dto.getSalesContractId())
	                        .orElseThrow(() -> new ApplicationException("Sales Contract Not Found"));

	                detail.setSalesContract(salesContract);
	            }

	            // Sales Contract Detail
	            if (dto.getSalesContractDetailsId() != null) {

	                SalesContractDetailsVO salesContractDetails =
	                        salesContractDetailsRepo.findById(dto.getSalesContractDetailsId())
	                        .orElseThrow(() -> new ApplicationException("Sales Contract Detail Not Found"));

	                detail.setSalesContractDetails(salesContractDetails);
	            }

	            // Item
	            if (dto.getItemId() != null) {

	                ItemMasterVO item = itemMasterRepo.findById(dto.getItemId())
	                        .orElseThrow(() -> new ApplicationException("Item Not Found"));

	                detail.setItem(item);
	            }

	            detail.setActualPlannedQty(dto.getActualPlannedQty());

	            detail.setSalesDeliverySchedule(salesDeliveryScheduleVO);

	            detailsList.add(detail);
	        }
	    }

	    salesDeliveryScheduleVO.setDetails(detailsList);
	    
	 // ================= Delivery Schedule Mapping =================

	    List<SalesDeliverySchedulePlanVO> deliveryPlanList = new ArrayList<>();

	    if (salesDeliveryScheduleDTO.getDeliverySchedule() != null
	            && !salesDeliveryScheduleDTO.getDeliverySchedule().isEmpty()) {

	        for (SalesDeliverySchedulePlanDTO planDTO
	                : salesDeliveryScheduleDTO.getDeliverySchedule()) {

	            SalesDeliverySchedulePlanVO planVO =
	                    new SalesDeliverySchedulePlanVO();

	            // Parent Header
	            planVO.setSalesDeliverySchedule(salesDeliveryScheduleVO);

	            // Parent Schedule Detail
	            if (planDTO.getSalesDeliveryScheduleDetailsId() != null) {

	                SalesDeliveryScheduleDetailsVO detailsVO =
	                        salesDeliveryScheduleDetailsRepo
	                                .findById(
	                                        planDTO.getSalesDeliveryScheduleDetailsId())
	                                .orElseThrow(() ->
	                                        new ApplicationException(
	                                                "Sales Delivery Schedule Details Not Found"));

	                planVO.setSalesDeliveryScheduleDetails(detailsVO);
	            }

	            planVO.setDayNo(planDTO.getDayNo());
	            planVO.setDeliveryDate(planDTO.getDeliveryDate());
	            planVO.setWeekNo(planDTO.getWeekNo());
	            planVO.setDayName(planDTO.getDayName());
	            planVO.setDeliveryQty(planDTO.getDeliveryQty());

	            deliveryPlanList.add(planVO);
	        }
	    }

	    salesDeliveryScheduleVO.setDeliverySchedules(deliveryPlanList);
	}
	
	private SalesDeliveryScheduleResponseDTO buildSalesDeliveryScheduleResponse(
	        SalesDeliveryScheduleVO salesDeliveryScheduleVO) {

	    SalesDeliveryScheduleResponseDTO responseDTO = new SalesDeliveryScheduleResponseDTO();

	    // ================= Header =================

	    responseDTO.setId(salesDeliveryScheduleVO.getId());
	    responseDTO.setDlvNo(salesDeliveryScheduleVO.getDlvNo());
	    responseDTO.setDlvDate(salesDeliveryScheduleVO.getDlvDate());
	    responseDTO.setMonthOfSchedule(salesDeliveryScheduleVO.getMonthOfSchedule());
	    responseDTO.setBelongsTo(salesDeliveryScheduleVO.getBelongsTo());
	    responseDTO.setMonthYear(salesDeliveryScheduleVO.getMonthYear());
	    responseDTO.setRemarks(salesDeliveryScheduleVO.getRemarks());

	    responseDTO.setOrgId(salesDeliveryScheduleVO.getOrgId());
	    responseDTO.setFinancialYear(salesDeliveryScheduleVO.getFinancialYear());

	    responseDTO.setCreatedBy(salesDeliveryScheduleVO.getCreatedBy());
	    responseDTO.setUpdatedBy(salesDeliveryScheduleVO.getUpdatedBy());

	    responseDTO.setCancelRemarks(salesDeliveryScheduleVO.getCancelRemarks());

	    responseDTO.setActive(salesDeliveryScheduleVO.getActive());
	    responseDTO.setCancel(salesDeliveryScheduleVO.getCancel());

	    responseDTO.setScreenCode(salesDeliveryScheduleVO.getScreenCode());
	    responseDTO.setScreenName(salesDeliveryScheduleVO.getScreenName());

	    // ================= Branch =================

	    if (salesDeliveryScheduleVO.getBranch() != null) {

	        responseDTO.setBranchId(salesDeliveryScheduleVO.getBranch().getId());
	        responseDTO.setBranchName(salesDeliveryScheduleVO.getBranch().getBranchName());

	    }

	    // ================= Customer =================

	    if (salesDeliveryScheduleVO.getCustomer() != null) {

	        responseDTO.setCustomerId(salesDeliveryScheduleVO.getCustomer().getId());
	        responseDTO.setCustomerCode(salesDeliveryScheduleVO.getCustomer().getCustomerCode());
	        responseDTO.setCustomerName(salesDeliveryScheduleVO.getCustomer().getCustomerName());

	    }

	    // ================= Details =================

	    List<SalesDeliveryScheduleDetailsResponseDTO> detailsResponse =
	            new ArrayList<>();

	    if (salesDeliveryScheduleVO.getDetails() != null) {

	        for (SalesDeliveryScheduleDetailsVO detailVO :
	                salesDeliveryScheduleVO.getDetails()) {

	            SalesDeliveryScheduleDetailsResponseDTO detailResponse =
	                    new SalesDeliveryScheduleDetailsResponseDTO();

	            detailResponse.setId(detailVO.getId());

	            // Sales Contract

	            if (detailVO.getSalesContract() != null) {

	                detailResponse.setSalesContractId(
	                        detailVO.getSalesContract().getId());

	                detailResponse.setSalesContractNo(
	                        detailVO.getSalesContract().getCustomerContractNo());

	                detailResponse.setInvoiceType(
	                        detailVO.getSalesContract().getInvoiceType());
	            }

	            // Sales Contract Detail

	            if (detailVO.getSalesContractDetails() != null) {

	                detailResponse.setSalesContractDetailsId(
	                        detailVO.getSalesContractDetails().getId());

	                if (detailVO.getSalesContractDetails().getQuantity() != null) {

	                    detailResponse.setOrderQty(
	                            detailVO.getSalesContractDetails()
	                                    .getQuantity()
	                                    .doubleValue());

	                    // Temporary
	                    detailResponse.setPendingQty(
	                            detailVO.getSalesContractDetails()
	                                    .getQuantity()
	                                    .doubleValue());
	                }

	            }

	            // Item

	            if (detailVO.getItem() != null) {

	                detailResponse.setItemId(detailVO.getItem().getId());

	                detailResponse.setItemCode(
	                        detailVO.getItem().getItemCode());

	                detailResponse.setItemDescription(
	                        detailVO.getItem().getItemDescription());

	                if (detailVO.getItem().getPrimaryUnit() != null) {

	                    detailResponse.setUnit(
	                            detailVO.getItem()
	                                    .getPrimaryUnit()
	                                    .getDescription());

	                }

	            }

	            detailResponse.setActualPlannedQty(
	                    detailVO.getActualPlannedQty());

	            detailsResponse.add(detailResponse);

	        }

	    }

	    responseDTO.setDetails(detailsResponse);
	    
	    
	    
	    List<SalesDeliverySchedulePlanResponseDTO> deliveryResponseList = new ArrayList<>();

	    if (salesDeliveryScheduleVO.getDeliverySchedules() != null) {

	        for (SalesDeliverySchedulePlanVO planVO :
	                salesDeliveryScheduleVO.getDeliverySchedules()) {

	            SalesDeliverySchedulePlanResponseDTO planResponse =
	                    new SalesDeliverySchedulePlanResponseDTO();

	            planResponse.setId(planVO.getId());

	            if (planVO.getSalesDeliverySchedule() != null) {
	                planResponse.setSalesDeliveryScheduleId(
	                        planVO.getSalesDeliverySchedule().getId());
	            }

	            if (planVO.getSalesDeliveryScheduleDetails() != null) {
	                planResponse.setSalesDeliveryScheduleDetailsId(
	                        planVO.getSalesDeliveryScheduleDetails().getId());
	            }

	            planResponse.setDayNo(planVO.getDayNo());
	            planResponse.setDeliveryDate(planVO.getDeliveryDate());
	            planResponse.setWeekNo(planVO.getWeekNo());
	            planResponse.setDayName(planVO.getDayName());
	            planResponse.setDeliveryQty(planVO.getDeliveryQty());

	            deliveryResponseList.add(planResponse);
	        }
	    }

	    responseDTO.setDeliverySchedules(deliveryResponseList);

	 
	    return responseDTO;
	}
	
	@Override
	public SalesDeliveryScheduleResponseDTO getSalesDeliveryScheduleById(Long id)
	        throws ApplicationException {

	    SalesDeliveryScheduleVO salesDeliveryScheduleVO =
	            salesDeliveryScheduleRepo.findById(id)
	            .orElseThrow(() ->
	                    new ApplicationException("Sales Delivery Schedule Not Found"));

	    return buildSalesDeliveryScheduleResponse(salesDeliveryScheduleVO);
	}
	
	@Override
	public List<SalesDeliveryScheduleResponseDTO> getAllSalesDeliverySchedule(
	        Long orgId,
	        Long branchId)
	        throws ApplicationException {

	    List<SalesDeliveryScheduleVO> scheduleList =
	            salesDeliveryScheduleRepo.findByOrgIdAndBranch(orgId, branchId);

	    List<SalesDeliveryScheduleResponseDTO> responseList = new ArrayList<>();

	    for (SalesDeliveryScheduleVO scheduleVO : scheduleList) {

	        responseList.add(buildSalesDeliveryScheduleResponse(scheduleVO));
	    }

	    return responseList;
	}
	
	@Override
	public Map<String, Object> getItemDropdown(Long salesContractId)
	        throws ApplicationException {

	    List<Object[]> list =
	            salesContractDetailsRepo.getItemDropdown(salesContractId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : list) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("itemId", obj[0]);
	        map.put("itemCode", obj[1]);
	        map.put("itemDescription", obj[2]);
	        map.put("unit", obj[3]);
	        map.put("orderQty", obj[4]);

	        responseList.add(map);
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "Item Dropdown Loaded Successfully");
	    response.put("itemList", responseList);

	    return response;
	}
	
	@Override
	public Map<String, Object> getContractNo() throws ApplicationException {

	    String methodName = "getContractNo";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    List<SalesContractVO> contractList = salesContractRepo.getContractNo();

	    for (SalesContractVO vo : contractList) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("id", vo.getId());
	        map.put("contractNo", vo.getCustomerContractNo());
	        map.put("invoiceType", vo.getInvoiceType());

	        responseList.add(map);
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "Contract No Dropdown Loaded Successfully");
	    response.put("contractList", responseList);

	    return response;
	}
	
	///SALES RETURN
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesReturn(
	        SalesReturnDTO salesReturnDTO)
	        throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    String message;

	    SalesReturnVO salesReturnVO;

	    if (ObjectUtils.isEmpty(salesReturnDTO.getId())) {

	        salesReturnVO = new SalesReturnVO();

	        salesReturnVO.setCreatedBy(
	                salesReturnDTO.getCreatedBy());

	        salesReturnVO.setUpdatedBy(
	                salesReturnDTO.getCreatedBy());

	        message = "Sales Return Created Successfully";

	    } else {

	        salesReturnVO = salesReturnRepo
	                .findById(salesReturnDTO.getId())
	                .orElseThrow(() ->
	                        new ApplicationException("Sales Return Not Found"));

	        salesReturnVO.setUpdatedBy(
	                salesReturnDTO.getUpdatedBy());

	        message = "Sales Return Updated Successfully";
	    }

	    // Header Mapping
	    createUpdateSalesReturnVOByDTO(
	            salesReturnDTO,
	            salesReturnVO);

	    // Save
	    salesReturnVO =
	            salesReturnRepo.save(salesReturnVO);

	    // Reload
	    salesReturnVO =
	            salesReturnRepo.findById(
	                    salesReturnVO.getId())
	            .orElseThrow(() ->
	                    new ApplicationException("Sales Return Not Found"));

	    // Response
	    SalesReturnResponseDTO responseDTO =
	            buildSalesReturnResponse(
	                    salesReturnVO);

	    response.put("message", message);
	    response.put("salesReturn", responseDTO);

	    return response;
	}
	
	private void createUpdateSalesReturnVOByDTO(
	        SalesReturnDTO salesReturnDTO,
	        SalesReturnVO salesReturnVO)
	        throws ApplicationException {

	    // ================= Document =================

	    salesReturnVO.setDocNo(salesReturnDTO.getDocNo());
	    salesReturnVO.setDocDate(salesReturnDTO.getDocDate());

	    // ================= Invoice =================

	    salesReturnVO.setInvoiceNo(salesReturnDTO.getInvoiceNo());
	    salesReturnVO.setInvoiceDate(salesReturnDTO.getInvoiceDate());

	    salesReturnVO.setCustomerInvoiceNo(
	            salesReturnDTO.getCustomerInvoiceNo());

	    salesReturnVO.setCustomerInvoiceDate(
	            salesReturnDTO.getCustomerInvoiceDate());

	    salesReturnVO.setGatePassNo(
	            salesReturnDTO.getGatePassNo());

	    // ================= Other Details =================

	    salesReturnVO.setApprovedByAccounts(
	            salesReturnDTO.getApprovedByAccounts());

	    salesReturnVO.setCurrency(
	            salesReturnDTO.getCurrency());

	    salesReturnVO.setExchangeRate(
	            salesReturnDTO.getExchangeRate());

	    salesReturnVO.setReferenceNo(
	            salesReturnDTO.getReferenceNo());

	    salesReturnVO.setReferenceDate(
	            salesReturnDTO.getReferenceDate());

	    // ================= Common =================

	    salesReturnVO.setOrgId(
	            salesReturnDTO.getOrgId());

	    salesReturnVO.setFinancialYear(
	            salesReturnDTO.getFinancialYear());

	    salesReturnVO.setCancelRemarks(
	            salesReturnDTO.getCancelRemarks());

	    if (salesReturnDTO.getActive() != null) {
	        salesReturnVO.setActive(
	                salesReturnDTO.getActive());
	    }

	    if (salesReturnDTO.getCancel() != null) {
	        salesReturnVO.setCancel(
	                salesReturnDTO.getCancel());
	    }

	    salesReturnVO.setScreenCode(
	            salesReturnDTO.getScreenCode());

	    salesReturnVO.setScreenName(
	            salesReturnDTO.getScreenName());

	    // ================= Branch =================

	    if (salesReturnDTO.getBranchId() != null) {

	        BranchVO branch = branchRepo
	                .findById(salesReturnDTO.getBranchId())
	                .orElseThrow(() ->
	                        new ApplicationException("Branch Not Found"));

	        salesReturnVO.setBranch(branch);
	    }

	    // ================= Belongs To =================

	    if (salesReturnDTO.getBelongsToId() != null) {

	        ListOfValuesDetailsVO belongsTo =
	                listOfValuesDetailsRepo
	                .findById(salesReturnDTO.getBelongsToId())
	                .orElseThrow(() ->
	                        new ApplicationException("Belongs To Not Found"));

	        salesReturnVO.setBelongsTo(belongsTo);
	    }

	    // ================= Customer =================

	    if (salesReturnDTO.getCustomerId() != null) {

	        CustomerVO customer = customerRepo
	                .findById(salesReturnDTO.getCustomerId())
	                .orElseThrow(() ->
	                        new ApplicationException("Customer Not Found"));

	        salesReturnVO.setCustomer(customer);
	    }

	    // ================= Location =================

	    if (salesReturnDTO.getLocationId() != null) {

	        LocationVO location = locationRepo
	                .findById(salesReturnDTO.getLocationId())
	                .orElseThrow(() ->
	                        new ApplicationException("Location Not Found"));

	        salesReturnVO.setLocation(location);
	    }

	    // ================= Return Type =================

	    if (salesReturnDTO.getReturnTypeId() != null) {

	        ListOfValuesDetailsVO returnType =
	                listOfValuesDetailsRepo
	                .findById(salesReturnDTO.getReturnTypeId())
	                .orElseThrow(() ->
	                        new ApplicationException("Return Type Not Found"));

	        salesReturnVO.setReturnType(returnType);
	    }

	    // ================= Invoice Reference Type =================

	    if (salesReturnDTO.getInvoiceReferenceTypeId() != null) {

	        ListOfValuesDetailsVO invoiceRefType =
	                listOfValuesDetailsRepo
	                .findById(salesReturnDTO.getInvoiceReferenceTypeId())
	                .orElseThrow(() ->
	                        new ApplicationException(
	                                "Invoice Reference Type Not Found"));

	        salesReturnVO.setInvoiceReferenceType(
	                invoiceRefType);
	    }
	}
	    

	    private SalesReturnResponseDTO buildSalesReturnResponse(
	            SalesReturnVO salesReturnVO) {

	        SalesReturnResponseDTO responseDTO =
	                new SalesReturnResponseDTO();

	        // ================= Document =================

	        responseDTO.setId(salesReturnVO.getId());
	        responseDTO.setDocNo(salesReturnVO.getDocNo());
	        responseDTO.setDocDate(salesReturnVO.getDocDate());

	        // ================= Invoice =================

	        responseDTO.setInvoiceNo(salesReturnVO.getInvoiceNo());
	        responseDTO.setInvoiceDate(salesReturnVO.getInvoiceDate());

	        responseDTO.setCustomerInvoiceNo(
	                salesReturnVO.getCustomerInvoiceNo());

	        responseDTO.setCustomerInvoiceDate(
	                salesReturnVO.getCustomerInvoiceDate());

	        responseDTO.setGatePassNo(
	                salesReturnVO.getGatePassNo());

	        // ================= Branch =================

	        if (salesReturnVO.getBranch() != null) {

	            responseDTO.setBranchId(
	                    salesReturnVO.getBranch().getId());

	            responseDTO.setBranchName(
	                    salesReturnVO.getBranch().getBranchName());
	        }

	        // ================= Belongs To =================

	        if (salesReturnVO.getBelongsTo() != null) {

	            responseDTO.setBelongsToId(
	                    salesReturnVO.getBelongsTo().getId());

	            responseDTO.setBelongsTo(
	            		salesReturnVO.getBelongsTo().getValueDescription());
	        }

	        // ================= Customer =================

	        if (salesReturnVO.getCustomer() != null) {

	            CustomerVO customer = salesReturnVO.getCustomer();

	            responseDTO.setCustomerId(customer.getId());
	            responseDTO.setCustomerCode(customer.getCustomerCode());
	            responseDTO.setCustomerName(customer.getCustomerName());

	            if (customer.getGstState() != null) {
	                responseDTO.setPartyGSTState(
	                        customer.getGstState().getStateName());
	            }

	            responseDTO.setGstNo(customer.getGstNo());

	            responseDTO.setIsIgstApplicable(
	                    customer.isGstApplicable() ? "YES" : "NO");
	        }

	        // ================= Location =================

	        if (salesReturnVO.getLocation() != null) {

	            responseDTO.setLocationId(
	                    salesReturnVO.getLocation().getId());

	            responseDTO.setLocationCode(
	                    salesReturnVO.getLocation().getLocationId());

	            responseDTO.setLocationName(
	                    salesReturnVO.getLocation().getLocationName());
	        }

	        // ================= Return Type =================

	        if (salesReturnVO.getReturnType() != null) {

	            responseDTO.setReturnTypeId(
	                    salesReturnVO.getReturnType().getId());

	            responseDTO.setReturnType(
	            		salesReturnVO.getBelongsTo().getValueDescription());
	        }

	        // ================= Invoice Reference Type =================

	        if (salesReturnVO.getInvoiceReferenceType() != null) {

	            responseDTO.setInvoiceReferenceTypeId(
	                    salesReturnVO.getInvoiceReferenceType().getId());

	            responseDTO.setInvoiceReferenceType(
	            		salesReturnVO.getBelongsTo().getValueDescription());
	        }

	        // ================= Other =================

	        responseDTO.setApprovedByAccounts(
	                salesReturnVO.getApprovedByAccounts());

	        responseDTO.setCurrency(
	                salesReturnVO.getCurrency());

	        responseDTO.setExchangeRate(
	                salesReturnVO.getExchangeRate());

	        responseDTO.setReferenceNo(
	                salesReturnVO.getReferenceNo());

	        responseDTO.setReferenceDate(
	                salesReturnVO.getReferenceDate());

	        // ================= Common =================

	        responseDTO.setOrgId(
	                salesReturnVO.getOrgId());

	        responseDTO.setFinancialYear(
	                salesReturnVO.getFinancialYear());

	        responseDTO.setCreatedBy(
	                salesReturnVO.getCreatedBy());

	        responseDTO.setUpdatedBy(
	                salesReturnVO.getUpdatedBy());

	        responseDTO.setCancelRemarks(
	                salesReturnVO.getCancelRemarks());

	        responseDTO.setActive(
	                salesReturnVO.isActive() ? "Active" : "In-Active");

	        responseDTO.setCancel(
	                salesReturnVO.isCancel() ? "T" : "F");

	        responseDTO.setScreenName(
	                salesReturnVO.getScreenName());

	        return responseDTO;
	    
	}
	    
	    
	    @Override
	    public SalesReturnResponseDTO getSalesReturnById(Long id)
	            throws ApplicationException {

	        SalesReturnVO salesReturnVO = salesReturnRepo.findById(id)
	                .orElseThrow(() ->
	                        new ApplicationException("Sales Return Not Found"));

	        return buildSalesReturnResponse(salesReturnVO);
	    }
	    
	    
	    @Override
	    public List<SalesReturnResponseDTO> getAllSalesReturn(
	            Long orgId,
	            Long branch)
	            throws ApplicationException {

	        List<SalesReturnVO> salesReturnList =
	                salesReturnRepo.findByOrgIdAndBranch(
	                        orgId,
	                        branch);

	        List<SalesReturnResponseDTO> responseList =
	                new ArrayList<>();

	        for (SalesReturnVO salesReturnVO : salesReturnList) {

	            responseList.add(
	                    buildSalesReturnResponse(salesReturnVO));
	        }

	        return responseList;
	    }
}