package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EnquiryAttachmentResponseDTO;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryDetailsDTO;
import com.efitops.basesetup.dto.EnquiryDetailsReponseDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.EnquiryTermsandCondDTO;
import com.efitops.basesetup.dto.EnquiryTermsandCondResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerContactDetailsVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.EnquiryAttachmentVO;
import com.efitops.basesetup.entity.EnquiryDetailsVO;
import com.efitops.basesetup.entity.EnquiryTermsandCondVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerContactDetailsRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.EnquiryAttachmentRepo;
import com.efitops.basesetup.repository.EnquiryDetailsRepo;
import com.efitops.basesetup.repository.EnquiryRepo;
import com.efitops.basesetup.repository.EnquiryTermsandCondRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;

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
	        if (enquiryVO.getEnquiryAttachment() != null) {

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

	        CustomerContactDetailsVO contact =
	                customerContactDetailsRepo.findById(enquiryDTO.getContactNameId())
	                        .orElseThrow(() -> new ApplicationException("Contact Not Found"));

	        enquiryVO.setContactName(contact);
	    }

	    // ================= Details =================

	    List<EnquiryDetailsVO> detailsList = new ArrayList<>();

	    if (enquiryDTO.getEnquiryDetails() != null) {

	        for (EnquiryDetailsDTO dto : enquiryDTO.getEnquiryDetails()) {

	            EnquiryDetailsVO detail = new EnquiryDetailsVO();

	            if (dto.getItemcode() != null) {

	                ItemMasterVO item = itemMasterRepo.findById(dto.getItemcode())
	                        .orElseThrow(() -> new ApplicationException("Item Not Found"));

	                detail.setItemcode(item);
	            }

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

	            Files.copy(
	                    file.getInputStream(),
	                    path,
	                    StandardCopyOption.REPLACE_EXISTING);

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

//	    if (enquiryVO.getBranch() != null) {
//
//	        responseDTO.setBranch(
//	                new BranchResponseDTO(
//	                        enquiryVO.getBranch().getId(),
//	                        enquiryVO.getBranch().getBranchCode(),
//	                        enquiryVO.getBranch().getBranchName()));
//	    }

	    // ================= Party =================

//	    if (enquiryVO.getPartyid() != null) {
//
//	        responseDTO.setPartyId(
//	                new CustomerResponseDTO(
//	                        enquiryVO.getPartyid().getId(),
//	                        enquiryVO.getPartyid().getCustomerName()));
//	    }

	    // ================= Contact =================

//	    if (enquiryVO.getContactName() != null) {
//
//	        responseDTO.setContactName(
//	                new CustomerContactResponseDTO(
//	                        enquiryVO.getContactName().getId(),
//	                        enquiryVO.getContactName().getContactName()));
//	    }

	    // ================= Details =================

	    List<EnquiryDetailsReponseDTO> detailResponse = new ArrayList<>();

	    if (enquiryVO.getEnquiryDetails() != null) {

	        for (EnquiryDetailsVO detail : enquiryVO.getEnquiryDetails()) {

	            EnquiryDetailsReponseDTO detailDTO =
	                    new EnquiryDetailsReponseDTO();

	            detailDTO.setId(detail.getId());

	            if (detail.getItemcode() != null) {

	                detailDTO.setItemcode(detail.getItemcode().getId());
	            }

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
	public EnquiryVO getEnquiryById(Long id) throws ApplicationException {

		return enquiryRepo.findById(id).orElseThrow(() -> new ApplicationException("Enquiry Not Found"));
	}

	@Override
	public List<EnquiryVO> getEnquiryByOrgId(Long orgId, Long branchId) throws ApplicationException {

		return enquiryRepo.findByOrgIdAndBranch(orgId, branchId);
	}

	@Override
	@Transactional
	public Map<String, Object> uploadEnquiryAttachment(Long enquiryId, MultipartFile file) throws ApplicationException {

		EnquiryVO enquiry = enquiryRepo.findById(enquiryId)
				.orElseThrow(() -> new ApplicationException("Enquiry Not Found"));

		try {

			String uploadDir = "uploads/enquiry/";

			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

			Path filePath = uploadPath.resolve(fileName);

			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			EnquiryAttachmentVO attachment = new EnquiryAttachmentVO();

			attachment.setName(file.getOriginalFilename());
			attachment.setFileName(fileName);
			attachment.setFilePath(filePath.toString());
			attachment.setFileSize(file.getSize());
			attachment.setContentType(file.getContentType());
			attachment.setUploadOn(LocalDateTime.now());
			attachment.setEnquiryVO(enquiry);

			enquiryAttachmentRepo.save(attachment);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Attachment Uploaded Successfully");

			return response;

		} catch (IOException e) {

			throw new ApplicationException("Unable to Upload File");
		}
	} // <-- closes uploadEnquiryAttachment()

	@Override
	public ResponseEntity<byte[]> viewEnquiryAttachment(Long attachmentId) throws ApplicationException {

		EnquiryAttachmentVO attachment = enquiryAttachmentRepo.findById(attachmentId)
				.orElseThrow(() -> new ApplicationException("Attachment Not Found"));

		try {

			Path path = Paths.get(attachment.getFilePath());

			byte[] data = Files.readAllBytes(path);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + attachment.getFileName() + "\"")
					.contentType(MediaType.APPLICATION_PDF).body(data);

		} catch (IOException e) {

			throw new ApplicationException("Unable to View Attachment");
		}
	}
}
