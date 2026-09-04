package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CountryResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.IssuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.IssuesResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationIssuesResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineHistoryResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineMasterAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineSpareDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.OpenStockEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ParameterMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentAttachmentResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentCustomerResponceDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentDetailsItemResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentDetailsResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderAmendmentAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderAmendmentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderAmendmentDtailsItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderAmendmentResponceDTO;
import com.efitops.basesetup.ResponseDTO.ToolCategoryDetailRepo;
import com.efitops.basesetup.ResponseDTO.ToolCategoryDetailResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolCategoryResponseDTO;
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
import com.efitops.basesetup.dto.IssuesDTO;
import com.efitops.basesetup.dto.IssuesDetailsDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.MachineHistoryDTO;
import com.efitops.basesetup.dto.MachineMasterAttachmentDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.MachineSpareDetailsDTO;
import com.efitops.basesetup.dto.OpenStockEntryDto;
import com.efitops.basesetup.dto.ParameterMasterDTO;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDetailsDto;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDto;
import com.efitops.basesetup.dto.PurchaseOrderAmendmentDTO;
import com.efitops.basesetup.dto.PurchaseOrderAmendmentDetailsDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentDetailsDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentResponseDTO;
import com.efitops.basesetup.dto.ToolCategoryDTO;
import com.efitops.basesetup.dto.ToolCategoryDetailDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CountryVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.EnquiryAttachmentVO;
import com.efitops.basesetup.entity.EnquiryDetailsVO;
import com.efitops.basesetup.entity.EnquiryTermsandCondVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.IssuesDetailsVO;
import com.efitops.basesetup.entity.IssuesVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.MachineHistoryVO;
import com.efitops.basesetup.entity.MachineMasterAttachmentVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineSpareDetailsVO;
import com.efitops.basesetup.entity.OpenStockEntryVO;
import com.efitops.basesetup.entity.ParameterMasterVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentAttachmentVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentVO;
import com.efitops.basesetup.entity.PurchaseOrderAmendmentAttachmentVO;
import com.efitops.basesetup.entity.PurchaseOrderAmendmentDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderAmendmentVO;
import com.efitops.basesetup.entity.SalesOrderAmendmentDetailsVO;
import com.efitops.basesetup.entity.SalesOrderAmendmentVO;
import com.efitops.basesetup.entity.ToolCategoryDetailVO;
import com.efitops.basesetup.entity.ToolCategoryVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CountryRepo;
import com.efitops.basesetup.repository.CustomerContactDetailsRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.EnquiryAttachmentRepo;
import com.efitops.basesetup.repository.EnquiryDetailsRepo;
import com.efitops.basesetup.repository.EnquiryRepo;
import com.efitops.basesetup.repository.EnquiryTermsandCondRepo;
import com.efitops.basesetup.repository.GstRateMasterRepo;
import com.efitops.basesetup.repository.IssuesDetailsRepo;
import com.efitops.basesetup.repository.IssuesRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.MachineHistoryRepo;
import com.efitops.basesetup.repository.MachineMasterAttachmentRepo;
import com.efitops.basesetup.repository.MachineMasterRepo;
import com.efitops.basesetup.repository.MachineSpareDetailsRepo;
import com.efitops.basesetup.repository.OpenStockEntryRepo;
import com.efitops.basesetup.repository.OrderAcceptanceRepo;
import com.efitops.basesetup.repository.ParameterMasterRepo;
import com.efitops.basesetup.repository.PurchaseContractAmendmentAttachmentRepo;
import com.efitops.basesetup.repository.PurchaseContractAmendmentDetailsRepo;
import com.efitops.basesetup.repository.PurchaseContractAmendmentRepo;
import com.efitops.basesetup.repository.PurchaseContractRepo;
import com.efitops.basesetup.repository.PurchaseOrderAmendmentAttachmentRepo;
import com.efitops.basesetup.repository.PurchaseOrderAmendmentDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderAmendmentRepo;
import com.efitops.basesetup.repository.PurchaseOrderRepo;
import com.efitops.basesetup.repository.SalesContractDetailsRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleDetailsRepo;
import com.efitops.basesetup.repository.SalesDeliverySchedulePlanRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleRepo;
import com.efitops.basesetup.repository.SalesOrderAmendmentDetailsRepo;
import com.efitops.basesetup.repository.SalesOrderAmendmentRepo;
import com.efitops.basesetup.repository.SalesReturnRepo;
import com.efitops.basesetup.repository.ToolCategoryRepo;
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

	@Autowired
	private PurchaseOrderAmendmentRepo purchaseOrderAmendmentRepo;

	@Autowired
	private PurchaseOrderAmendmentDetailsRepo purchaseOrderAmendmentDetailsRepo;

	@Autowired
	private PurchaseOrderAmendmentAttachmentRepo purchaseOrderAmendmentAttachmentRepo;

	@Autowired
	private PurchaseOrderRepo purchaseOrderRepo;

	@Autowired
	private OpenStockEntryRepo openStockEntryRepo;

	@Autowired
	private IssuesRepo issuesRepo;

	@Autowired
	private IssuesDetailsRepo issuesDetailsRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private ParameterMasterRepo parameterMasterRepo;

	@Autowired
	private MachineMasterRepo machineMasterRepo;

	@Autowired
	private MachineMasterAttachmentRepo machineMasterAttachmentRepo;

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private MachineSpareDetailsRepo machineSpareDetailsRepo;

	@Autowired
	private MachineHistoryRepo machineHistoryRepo;

	@Autowired
	private ToolCategoryRepo toolCategoryRepo;

	@Autowired
	private ToolCategoryDetailRepo toolCategoryDetailRepo;

	@Value("${purchase.contract.amendment.upload.path}")
	private String uploadPath1;

	@Value("${server.base-url}")
	private String serverBaseUrl;

	@Value("${machinemaster.upload.path}")
	private String machineMasterUploadPath;

	@Autowired
	PurchaseContractAmendmentAttachmentRepo purchaseContractAmendmentAttachmentRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

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
	public Map<String, Object> updateCreateEnquiry(EnquiryDTO enquiryDTO, MultipartFile[] files)
			throws ApplicationException {

		String screenCode = "EQN";

		Map<String, Object> response = new HashMap<>();

		String message;

		EnquiryVO enquiryVO;

		if (ObjectUtils.isEmpty(enquiryDTO.getId())) {

			enquiryVO = new EnquiryVO();

			String docId = enquiryRepo.getEnquiryDocId(enquiryDTO.getOrgId(), enquiryDTO.getFinancialYear(),
					screenCode);

			enquiryVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(enquiryDTO.getOrgId(), enquiryDTO.getFinancialYear(),
							screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

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
			if (enquiryVO.getEnquiryAttachment() != null && !enquiryVO.getEnquiryAttachment().isEmpty()) {

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

	private void createUpdateEnquiryVOByEnquiryDTO(EnquiryDTO enquiryDTO, EnquiryVO enquiryVO)
			throws ApplicationException {

		// ================= Header =================

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

			EmployeeMasterVO contact = employeeMasterRepo.findById(enquiryDTO.getContactNameId())
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

	private void saveAttachments(MultipartFile[] files, EnquiryVO enquiryVO) throws ApplicationException {

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

				String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

				Path path = Paths.get(uploadPath, fileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
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

			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private EnquiryResponseDTO buildEnquiryResponse(EnquiryVO enquiryVO) {

		EnquiryResponseDTO responseDTO = new EnquiryResponseDTO();

		// ================= Header =================

		responseDTO.setId(enquiryVO.getId());
		responseDTO.setDocId(enquiryVO.getDocId());
		responseDTO.setDocDate(enquiryVO.getDocDate());
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

			responseDTO.setBranch(new BranchResponseDTO(enquiryVO.getBranch().getId(),
					enquiryVO.getBranch().getBranchCode(), enquiryVO.getBranch().getBranchName()));
		}

		// ================= Party =================

		if (enquiryVO.getCustomer() != null) {

			responseDTO.setCustomerVO(new CustomerResponse1DTO(enquiryVO.getCustomer().getId(),
					enquiryVO.getCustomer().getCustomerName()));
		}

		// ================= Contact =================

		if (enquiryVO.getContactName() != null) {

			responseDTO.setContactName(new EmployeeResponseDTO(enquiryVO.getContactName().getId(),
					enquiryVO.getContactName().getEmployeeName()));
		}

		// ================= Details =================

		List<EnquiryDetailsReponseDTO> detailResponse = new ArrayList<>();

		if (enquiryVO.getEnquiryDetails() != null) {

			for (EnquiryDetailsVO detail : enquiryVO.getEnquiryDetails()) {

				EnquiryDetailsReponseDTO detailDTO = new EnquiryDetailsReponseDTO();

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

		List<EnquiryTermsandCondResponseDTO> termsResponse = new ArrayList<>();

		if (enquiryVO.getEnquiryTermsandCond() != null) {

			for (EnquiryTermsandCondVO terms : enquiryVO.getEnquiryTermsandCond()) {

				EnquiryTermsandCondResponseDTO dto = new EnquiryTermsandCondResponseDTO();

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

		List<EnquiryAttachmentResponseDTO> attachmentResponse = new ArrayList<>();

		if (enquiryVO.getEnquiryAttachment() != null) {

			for (EnquiryAttachmentVO attachment : enquiryVO.getEnquiryAttachment()) {

				EnquiryAttachmentResponseDTO dto = new EnquiryAttachmentResponseDTO();

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

		EnquiryVO enquiryVO = enquiryRepo.findById(id).orElseThrow(() -> new ApplicationException("Enquiry Not Found"));

		return buildEnquiryResponse(enquiryVO);
	}

	@Override
	public List<EnquiryResponseDTO> getEnquiryByOrgId(Long orgId, Long branchId) throws ApplicationException {

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

	/// SALES RETURN

//	@Override
//	@Transactional
//	public Map<String, Object> createUpdateSalesReturn(SalesReturnDTO salesReturnDTO) throws ApplicationException {

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
//		return null;
//	}
//
//	@Override
//	public List<SalesReturnResponseDTO> getAllSalesReturn(Long orgId, Long branch) throws ApplicationException {
//
////	        List<SalesReturnVO> salesReturnList =
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
//		return null;
//	}
//
//	@Override
//	public SalesReturnResponseDTO getSalesReturnById(Long id) throws ApplicationException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	// salesorderamendment

	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesOrderAmendment(SalesOrderAmendmentDTO salesOrderAmendmentDTO)
			throws ApplicationException {

		SalesOrderAmendmentVO salesOrderAmendmentVO = new SalesOrderAmendmentVO();

		String message;

		if (ObjectUtils.isNotEmpty(salesOrderAmendmentDTO.getId())) {

			salesOrderAmendmentVO = salesOrderAmendmentRepo.findById(salesOrderAmendmentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Sales Order Amendment Not Found"));

			createUpdateSalesOrderAmendmentVOByDTO(salesOrderAmendmentDTO, salesOrderAmendmentVO);

			message = "Sales Order Amendment Updated Successfully";

		} else {

			createUpdateSalesOrderAmendmentVOByDTO(salesOrderAmendmentDTO, salesOrderAmendmentVO);

			message = "Sales Order Amendment Created Successfully";
		}

		SalesOrderAmendmentVO savedSalesOrderAmendment = salesOrderAmendmentRepo.save(salesOrderAmendmentVO);

		if (ObjectUtils.isNotEmpty(salesOrderAmendmentDTO.getId())) {

			salesOrderAmendmentDetailsRepo.deleteBySalesOrderAmendmentVO(savedSalesOrderAmendment);
		}

		if (salesOrderAmendmentDTO.getDetails() != null) {

			for (SalesOrderAmendmentDetailsDTO detailDTO : salesOrderAmendmentDTO.getDetails()) {

				SalesOrderAmendmentDetailsVO detailVO = new SalesOrderAmendmentDetailsVO();

				if (detailDTO.getItem() != null) {

					ItemMasterVO itemVO = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

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

		savedSalesOrderAmendment = salesOrderAmendmentRepo.findById(savedSalesOrderAmendment.getId())
				.orElseThrow(() -> new ApplicationException("Sales Order Amendment Not Found"));

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("salesOrderAmendmentVO", salesOrderAmendmentResponse(savedSalesOrderAmendment));

		return response;

	}

	private void createUpdateSalesOrderAmendmentVOByDTO(SalesOrderAmendmentDTO dto, SalesOrderAmendmentVO vo)
			throws ApplicationException {

		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

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

	private SalesOrderAmendmentResponseDTO salesOrderAmendmentResponse(SalesOrderAmendmentVO salesOrderAmendmentVO) {

		SalesOrderAmendmentResponseDTO responseDTO = new SalesOrderAmendmentResponseDTO();

		responseDTO.setId(salesOrderAmendmentVO.getId());

		if (salesOrderAmendmentVO.getBranch() != null) {
			responseDTO.setBranchId(salesOrderAmendmentVO.getBranch().getId());

			responseDTO.setBranchName(salesOrderAmendmentVO.getBranch().getBranchName());
		}

		responseDTO.setDocId(salesOrderAmendmentVO.getDocId());
		responseDTO.setDocDate(salesOrderAmendmentVO.getDocDate());
		responseDTO.setSalesOrderNumber(salesOrderAmendmentVO.getSalesOrderNumber());
		responseDTO.setPartyPoAmendmentNo(salesOrderAmendmentVO.getPartyPoAmendmentNo());
		responseDTO.setSalesOrderDate(salesOrderAmendmentVO.getSalesOrderDate());
		responseDTO.setPartyPoAmendmentDate(salesOrderAmendmentVO.getPartyPoAmendmentDate());
		responseDTO.setPoNo(salesOrderAmendmentVO.getPoNo());
		responseDTO.setRevisionNo(salesOrderAmendmentVO.getRevisionNo());
		responseDTO.setPoDate(salesOrderAmendmentVO.getPoDate());
		responseDTO.setRemarks(salesOrderAmendmentVO.getRemarks());

		responseDTO.setOrgId(salesOrderAmendmentVO.getOrgId());
		responseDTO.setCreatedBy(salesOrderAmendmentVO.getCreatedBy());
		responseDTO.setUpdatedBy(salesOrderAmendmentVO.getUpdatedBy());
		responseDTO.setActive(salesOrderAmendmentVO.isActive());
		responseDTO.setCancel(salesOrderAmendmentVO.isCancel());
		responseDTO.setCancelRemarks(salesOrderAmendmentVO.getCancelRemarks());
		responseDTO.setScreenName(salesOrderAmendmentVO.getScreenName());
		responseDTO.setScreenCode(salesOrderAmendmentVO.getScreenCode());

		// Fetch child table directly
		List<SalesOrderAmendmentDetailsVO> detailList = salesOrderAmendmentDetailsRepo
				.findBySalesOrderAmendmentVO_Id(salesOrderAmendmentVO.getId());

		List<SalesOrderAmendmentDetailsResponseDTO> details = new ArrayList<>();

		for (SalesOrderAmendmentDetailsVO detailVO : detailList) {

			SalesOrderAmendmentDetailsResponseDTO detailDTO = new SalesOrderAmendmentDetailsResponseDTO();

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

				ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();

				itemDTO.setId(detailVO.getItem().getId());

				itemDTO.setItemCode(detailVO.getItem().getItemCode());

				itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

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

			detailDTO.setOldDeliveryDate(detailVO.getOldDeliveryDate());

			detailDTO.setNewDeliveryDate(detailVO.getNewDeliveryDate());

			details.add(detailDTO);
		}

		responseDTO.setSalesOrderAmendmentDetails(details);

		return responseDTO;
	}

	@Override
	public SalesOrderAmendmentResponseDTO getSalesOrderAmendmentById(Long id) throws ApplicationException {

		SalesOrderAmendmentVO salesOrderAmendmentVO = salesOrderAmendmentRepo.getSalesOrderAmendmentById(id);

		if (salesOrderAmendmentVO == null) {

			throw new ApplicationException("Sales Order Amendment Not Found");
		}

		return salesOrderAmendmentResponse(salesOrderAmendmentVO);
	}

	@Override
	public List<SalesOrderAmendmentResponseDTO> getSalesOrderAmendmentByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<SalesOrderAmendmentVO> salesOrderAmendmentList = salesOrderAmendmentRepo
				.getSalesOrderAmendmentByOrgId(orgId, branch);

		if (salesOrderAmendmentList == null || salesOrderAmendmentList.isEmpty()) {

			throw new ApplicationException("Sales Order Amendment Not Found");
		}

		List<SalesOrderAmendmentResponseDTO> responseList = new ArrayList<>();

		for (SalesOrderAmendmentVO salesOrderAmendmentVO : salesOrderAmendmentList) {

			responseList.add(salesOrderAmendmentResponse(salesOrderAmendmentVO));
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getOrderAcceptanceBySalesOrderAmendment(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> orderAcceptanceList = salesContractRepo.getOrderAcceptanceBySalesOrderAmendment(orgId, branch);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : orderAcceptanceList) {

			Map<String, Object> map = new HashMap<>();

			map.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);
			map.put("docId", obj[1] != null ? obj[1].toString() : "");
			map.put("docDate", obj[2] != null ? obj[2] : null);
			map.put("customerPurchaseOrderNo", obj[3] != null ? obj[3].toString() : "");
			map.put("customerPurchaseOrderDate", obj[4] != null ? obj[4] : null);

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
	public List<Map<String, Object>> getOrderAcceptanceItemsWithAmendment(String docId, Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> itemDetails = orderAcceptanceRepo.getOrderAcceptanceItemsWithAmendment(docId, orgId, branch);

		return getOrderAcceptanceItemDetails(itemDetails);
	}

	private List<Map<String, Object>> getOrderAcceptanceItemDetails(List<Object[]> itemDetails) {

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
	public Integer getSalesOrderAmdRevisionNo(String salesOrderNo, Long item, Long orgId, Long branch)
			throws ApplicationException {

		Integer revisionNo = salesOrderAmendmentRepo.getSalesOrderAmdRevisionNo(salesOrderNo, item, orgId, branch);

		return revisionNo;
	}

	// PurchaseContractAmendment

	@Override
	@Transactional
	public Map<String, Object> createUpdatePurchaseContractAmendment(
			PurchaseContractAmendmentDto purchaseContractAmendmentDto, MultipartFile[] files)
			throws ApplicationException {

		PurchaseContractAmendmentVO purchaseContractAmendmentVO;
		String message;

		if (ObjectUtils.isNotEmpty(purchaseContractAmendmentDto.getId())) {

			purchaseContractAmendmentVO = purchaseContractAmendmentRepo.findById(purchaseContractAmendmentDto.getId())
					.orElseThrow(() -> new ApplicationException("Purchase Contract Amendment Not Found"));

			purchaseContractAmendmentVO.setUpdatedBy(purchaseContractAmendmentDto.getCreatedBy());

			message = "Purchase Contract Amendment Updated Successfully";

		} else {

			purchaseContractAmendmentVO = new PurchaseContractAmendmentVO();

			purchaseContractAmendmentVO.setCreatedBy(purchaseContractAmendmentDto.getCreatedBy());

			purchaseContractAmendmentVO.setUpdatedBy(purchaseContractAmendmentDto.getCreatedBy());

			message = "Purchase Contract Amendment Created Successfully";
		}

		// Header + Child Mapping
		createUpdatePurchaseContractAmendmentVOByDTO(purchaseContractAmendmentDto, purchaseContractAmendmentVO);

		// Save Header
		purchaseContractAmendmentVO = purchaseContractAmendmentRepo.save(purchaseContractAmendmentVO);

		// Save Attachments
		saveAttachments(files, purchaseContractAmendmentVO);

		// Response
		PurchaseContractAmendmentResponseDto responseDTO = purchaseContractAmendmentResponse(
				purchaseContractAmendmentVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("purchaseContractAmendmentVO", responseDTO);

		return response;
	}

	private void createUpdatePurchaseContractAmendmentVOByDTO(PurchaseContractAmendmentDto dto,
			PurchaseContractAmendmentVO vo) throws ApplicationException {

		// =========================
		// Branch
		// =========================

		if (dto.getBranch() != null) {

			BranchVO branchVO = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branchVO);
		}

		// =========================
		// Customer
		// =========================

		if (dto.getCustomer() != null) {

			CustomerVO customerVO = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

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

			purchaseContractAmendmentDetailsRepo.deleteByPurchaseContractAmendmentVO(vo);
		}

		// =========================
		// Save Grid
		// =========================

		List<PurchaseContractAmendmentDetailsVO> detailsList = new ArrayList<>();

		if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {

			for (PurchaseContractAmendmentDetailsDto detailDto : dto.getDetails()) {

				PurchaseContractAmendmentDetailsVO detailVO = new PurchaseContractAmendmentDetailsVO();

				detailVO.setPurchaseContractAmendmentVO(vo);

				// =========================
				// Item
				// =========================

				if (detailDto.getItem() != null) {

					ItemMasterVO itemVO = itemMasterRepo.findById(detailDto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(itemVO);
				}

				// =========================
				// Unit
				// =========================

				if (detailDto.getUnit() != null) {

					UnitMasterVO unitVO = unitMasterRepo.findById(detailDto.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unitVO);
				}

				detailVO.setOldRate(detailDto.getOldRate());

				detailVO.setNewRate(detailDto.getNewRate());

				detailVO.setValidFrom(detailDto.getValidFrom());

				detailVO.setValidTo(detailDto.getValidTo());

				detailVO.setNewValidFrom(detailDto.getNewValidFrom());

				detailVO.setNewValidTo(detailDto.getNewValidTo());

				detailsList.add(detailVO);
			}
		}

		purchaseContractAmendmentDetailsRepo.saveAll(detailsList);
	}

	private PurchaseContractAmendmentResponseDto purchaseContractAmendmentResponse(PurchaseContractAmendmentVO vo) {

		PurchaseContractAmendmentResponseDto responseDto = new PurchaseContractAmendmentResponseDto();

		responseDto.setId(vo.getId());

		// =========================
		// Branch
		// =========================

		if (vo.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(vo.getBranch().getId());

			branchResponseDTO.setBranchName(vo.getBranch().getBranchName());

			responseDto.setBranch(branchResponseDTO);
		}

		// =========================
		// Party
		// =========================

		if (vo.getCustomer() != null) {

			PurchaseContractAmendmentCustomerResponceDto customerResponseDTO = new PurchaseContractAmendmentCustomerResponceDto();

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

		List<PurchaseContractAmendmentDetailsResponseDto> detailResponseList = new ArrayList<>();

		List<PurchaseContractAmendmentDetailsVO> detailVOList = purchaseContractAmendmentDetailsRepo
				.findByPurchaseContractAmendmentVO(vo);

		for (PurchaseContractAmendmentDetailsVO detailVO : detailVOList) {

			PurchaseContractAmendmentDetailsResponseDto detailResponse = new PurchaseContractAmendmentDetailsResponseDto();

			detailResponse.setId(detailVO.getId());

			if (detailVO.getItem() != null) {

				PurchaseContractAmendmentDetailsItemResponseDto itemResponse = new PurchaseContractAmendmentDetailsItemResponseDto();

				itemResponse.setItemCode(detailVO.getItem().getItemCode());

				itemResponse.setItemDescription(detailVO.getItem().getItemDescription());

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

		List<PurchaseContractAmendmentAttachmentResponseDto> attachmentResponseList = new ArrayList<>();

		List<PurchaseContractAmendmentAttachmentVO> attachmentVOList = purchaseContractAmendmentAttachmentRepo
				.findByPurchaseContractAmendmentVO(vo);

		if (attachmentVOList != null) {

			for (PurchaseContractAmendmentAttachmentVO fileVO : attachmentVOList) {

				PurchaseContractAmendmentAttachmentResponseDto fileDTO = new PurchaseContractAmendmentAttachmentResponseDto();

				fileDTO.setId(fileVO.getId());

				fileDTO.setName(fileVO.getName());

				fileDTO.setFileName(fileVO.getFileName());

				String urlPath = uploadPath.replace("C:/", "/").replace("\\", "/");

				fileDTO.setFilePath(serverBaseUrl + urlPath + fileVO.getFileName());

				fileDTO.setFileSize(fileVO.getFileSize());

				fileDTO.setContentType(fileVO.getContentType());

				fileDTO.setUploadOn(fileVO.getUploadOn());

				attachmentResponseList.add(fileDTO);
			}
		}

		responseDto.setAttachments(attachmentResponseList);

		return responseDto;

	}

	private void saveAttachments(MultipartFile[] files, PurchaseContractAmendmentVO purchaseContractAmendmentVO)
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

			List<PurchaseContractAmendmentAttachmentVO> oldAttachments = purchaseContractAmendmentAttachmentRepo
					.findByPurchaseContractAmendmentVO(purchaseContractAmendmentVO);

			for (PurchaseContractAmendmentAttachmentVO oldAttachment : oldAttachments) {

				// Delete physical file
				if (oldAttachment.getFilePath() != null) {

					File oldFile = new File(oldAttachment.getFilePath());

					if (oldFile.exists()) {
						oldFile.delete();
					}
				}
			}

			// Delete old attachment records from DB
			if (!oldAttachments.isEmpty()) {

				purchaseContractAmendmentAttachmentRepo
						.deleteByPurchaseContractAmendmentVO(purchaseContractAmendmentVO);
			}

			// ==========================================
			// Save New Attachments
			// ==========================================

			List<PurchaseContractAmendmentAttachmentVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalFileName = file.getOriginalFilename();

				String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

				Path path = Paths.get(uploadPath1, uniqueFileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}

				PurchaseContractAmendmentAttachmentVO attachment = new PurchaseContractAmendmentAttachmentVO();

				attachment.setPurchaseContractAmendmentVO(purchaseContractAmendmentVO);

				attachment.setName(originalFileName);

				attachment.setFileName(uniqueFileName);

				attachment.setFilePath(path.toString());

				attachment.setFileSize(file.getSize());

				attachment.setContentType(file.getContentType());

				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			// Save new attachment records
			List<PurchaseContractAmendmentAttachmentVO> savedAttachments = purchaseContractAmendmentAttachmentRepo
					.saveAll(attachmentList);

			purchaseContractAmendmentVO.setPurchaseContractAmendmentAttachment(savedAttachments);

		} catch (IOException e) {

			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	@Override
	public PurchaseContractAmendmentResponseDto getPurchaseContractAmendmentById(Long id) throws ApplicationException {

		PurchaseContractAmendmentVO purchaseContractAmendmentVO = purchaseContractAmendmentRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Purchase Contract Amendment Not Found"));

		return purchaseContractAmendmentResponse(purchaseContractAmendmentVO);
	}

	@Override
	public List<PurchaseContractAmendmentResponseDto> getPurchaseContractAmendmentByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<PurchaseContractAmendmentVO> voList = purchaseContractAmendmentRepo.findByOrgId(orgId, branch);

		List<PurchaseContractAmendmentResponseDto> responseList = new ArrayList<>();

		for (PurchaseContractAmendmentVO vo : voList) {

			responseList.add(purchaseContractAmendmentResponse(vo));
		}

		return responseList;
	}

	// ContractNoDropdownforPurchaseContractAmendment

	@Override
	public Map<String, Object> getContractNoDropdownforPurchaseContractAmendment(Long orgId, Long branch,
			Long customerId) throws ApplicationException {

		List<Object[]> result = purchaseContractAmendmentRepo.findContractNoDropdownforPurchaseContractAmendment(orgId,
				branch, customerId);

		Map<String, Object> response = new HashMap<>();

		response.put("contractList", getContractNoDetails(result));

		return response;
	}

	private List<Map<String, Object>> getContractNoDetails(List<Object[]> result) {

		List<Map<String, Object>> contractList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> contract = new HashMap<>();

			contract.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			contract.put("contractNo", obj[1] != null ? obj[1].toString() : null);

			contractList.add(contract);
		}

		return contractList;
	}

	// itemdropdownforpurchasecontractamendment

	@Override
	public Map<String, Object> getPurchaseContractAmendmentItemCodeDropdown(String docId, Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> result = purchaseContractAmendmentRepo.getPurchaseContractAmendmentItemCodeDropdown(docId,
				branch, orgId);

		Map<String, Object> response = new HashMap<>();

		response.put("itemCodeList", getPurchaseContractItemCodeDetails(result));

		return response;
	}

	private List<Map<String, Object>> getPurchaseContractItemCodeDetails(List<Object[]> result) {

		List<Map<String, Object>> itemCodeList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> itemCode = new HashMap<>();

			itemCode.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			itemCode.put("itemCode", obj[1] != null ? obj[1].toString() : null);

			itemCode.put("itemDescription", obj[2] != null ? obj[2].toString() : null);

			itemCode.put("unitId", obj[3] != null ? ((Number) obj[3]).longValue() : null);

			itemCodeList.add(itemCode);
		}

		return itemCodeList;
	}

	@Override
	public Integer getPurchaseContractAmdRevisionNo(String contractNo, Long orgId, Long branch)
			throws ApplicationException {

		Integer revisionNo = purchaseContractAmendmentRepo.getPurchaseContractAmdRevisionNo(contractNo, orgId, branch);

		return revisionNo;
	}

	@Override
	public String getEnquiryDocId(Long orgId, String financialYear, String screenCode) {
		String screenCode1 = "EQN";
		String result = enquiryRepo.getEnquiryDocId(orgId, financialYear, screenCode1);
		return result;
	}

//purchase order amendment

	@Override
	@Transactional
	public Map<String, Object> updateCreatePurchaseOrderAmendment(PurchaseOrderAmendmentDTO purchaseOrderAmendmentDTO,
			MultipartFile[] files) throws ApplicationException {

		String screenCode = "POA"; // Use your actual screen code

		PurchaseOrderAmendmentVO purchaseOrderAmendmentVO;

		String message;

		if (ObjectUtils.isNotEmpty(purchaseOrderAmendmentDTO.getId())) {

			purchaseOrderAmendmentVO = purchaseOrderAmendmentRepo.findById(purchaseOrderAmendmentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Purchase Order Amendment Not Found"));

			purchaseOrderAmendmentVO.setUpdatedBy(purchaseOrderAmendmentDTO.getCreatedBy());

			message = "Purchase Order Amendment Updated Successfully";

		} else {

			purchaseOrderAmendmentVO = new PurchaseOrderAmendmentVO();

			purchaseOrderAmendmentVO.setCreatedBy(purchaseOrderAmendmentDTO.getCreatedBy());

			purchaseOrderAmendmentVO.setUpdatedBy(purchaseOrderAmendmentDTO.getCreatedBy());

			message = "Purchase Order Amendment Created Successfully";
		}

		// Header + Details
		createUpdatePurchaseOrderAmendmentVO(purchaseOrderAmendmentDTO, purchaseOrderAmendmentVO);

		// Save Header
		// Save Header
		purchaseOrderAmendmentVO = purchaseOrderAmendmentRepo.save(purchaseOrderAmendmentVO);

		// Update Attachments
		if (files != null && files.length > 0) {

			// Get old attachments
			List<PurchaseOrderAmendmentAttachmentVO> oldAttachments = purchaseOrderAmendmentAttachmentRepo
					.findByPurchaseOrderAmendmentVO(purchaseOrderAmendmentVO);

			// Delete old files from folder
			for (PurchaseOrderAmendmentAttachmentVO attachment : oldAttachments) {

				if (attachment.getFilePath() != null) {

					File file = new File(attachment.getFilePath());

					if (file.exists()) {
						file.delete();
					}
				}
			}

			// Delete old attachment records
			purchaseOrderAmendmentAttachmentRepo.deleteAll(oldAttachments);

			// Clear parent collection
			purchaseOrderAmendmentVO.getAttachments().clear();

			// Save new files
			saveAttachments(files, purchaseOrderAmendmentVO);

			// Save again
			purchaseOrderAmendmentRepo.save(purchaseOrderAmendmentVO);
		}

		// Build Response
		PurchaseOrderAmendmentResponceDTO responseDTO = buildPurchaseOrderAmendmentResponse(purchaseOrderAmendmentVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("purchaseOrderAmendmentVO", responseDTO);

		return response;
	}

	private void createUpdatePurchaseOrderAmendmentVO(PurchaseOrderAmendmentDTO dto, PurchaseOrderAmendmentVO vo)
			throws ApplicationException {

		// ======================================================
		// Header Mapping
		// ======================================================

		vo.setBelongsTo(dto.getBelongsTo());
		vo.setPurchaseordernumber(dto.getPurchaseordernumber());
//    vo.setCurrency(dto.getCurrency());
//    vo.setRefNo(dto.getRefNo());
//    vo.setRefDate(dto.getRefDate());
//    vo.setExchangeRate(dto.getExchangeRate());
		vo.setRevisionNo(dto.getRevisionNo());

		vo.setFreightType(dto.getFreightType());
		vo.setPackingType(dto.getPackingType());
		vo.setInsuranceAmount(dto.getInsuranceAmount());
		vo.setModeOfDespatch(dto.getModeOfDespatch());
		vo.setTaxDescription(dto.getTaxDescription());
		vo.setRemarks(dto.getRemarks());

		vo.setOrgId(dto.getOrgId());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setCreatedBy(dto.getCreatedBy());
		;
		// ======================================================
		// Branch
		// ======================================================

		if (dto.getBranch() != null && dto.getBranch() > 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		// ======================================================
		// Customer
		// ======================================================

		if (dto.getCustomer() != null && dto.getCustomer() > 0) {

			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			vo.setCustomer(customer);
		}

		// ======================================================
		// Update - Delete Old Details & Attachments
		// ======================================================

		if (ObjectUtils.isNotEmpty(vo.getId())) {

			List<PurchaseOrderAmendmentDetailsVO> details = purchaseOrderAmendmentDetailsRepo
					.findByPurchaseOrderAmendmentVO(vo);

			purchaseOrderAmendmentDetailsRepo.deleteAll(details);

		}

		// ======================================================
		// Details Mapping
		// ======================================================

		List<PurchaseOrderAmendmentDetailsVO> detailList = new ArrayList<>();

		if (dto.getDetails() != null) {

			for (PurchaseOrderAmendmentDetailsDTO detailDTO : dto.getDetails()) {

				PurchaseOrderAmendmentDetailsVO detailVO = new PurchaseOrderAmendmentDetailsVO();

				// ---------------------------------------------
				// Item
				// ---------------------------------------------

				if (detailDTO.getItem() != null && detailDTO.getItem() > 0) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				// ---------------------------------------------
				// Unit
				// ---------------------------------------------

				if (detailDTO.getUnit() != null && detailDTO.getUnit() > 0) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				detailVO.setOldQty(detailDTO.getOldQty());
				detailVO.setNewQty(detailDTO.getNewQty());

				detailVO.setOldRate(detailDTO.getOldRate());
				detailVO.setNewRate(detailDTO.getNewRate());

				detailVO.setOldDeliveryDate(detailDTO.getOldDeliveryDate());

				detailVO.setNewDeliveryDate(detailDTO.getNewDeliveryDate());

				detailVO.setPurchaseOrderAmendmentVO(vo);

				detailList.add(detailVO);
			}
		}

		vo.setDetails(detailList);
	}

	@Value("${purchaseorderamendment.upload.path}")
	private String purchaseOrderAmendmentUploadPath;

	private void saveAttachments(MultipartFile[] files, PurchaseOrderAmendmentVO purchaseOrderAmendmentVO)
			throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			File folder = new File(purchaseOrderAmendmentUploadPath);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			List<PurchaseOrderAmendmentAttachmentVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalFileName = file.getOriginalFilename();

				String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

				Path path = Paths.get(purchaseOrderAmendmentUploadPath, uniqueFileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}

				PurchaseOrderAmendmentAttachmentVO attachment = new PurchaseOrderAmendmentAttachmentVO();

				attachment.setPurchaseOrderAmendmentVO(purchaseOrderAmendmentVO);

				attachment.setName(originalFileName);

				attachment.setFileName(uniqueFileName);

				attachment.setFilePath(path.toString());

				attachment.setFileSize(file.getSize());

				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);

			}

			List<PurchaseOrderAmendmentAttachmentVO> savedAttachments = purchaseOrderAmendmentAttachmentRepo
					.saveAll(attachmentList);

			purchaseOrderAmendmentVO.setAttachments(savedAttachments);

		} catch (IOException e) {

			throw new ApplicationException("File Upload Failed : " + e.getMessage());

		}
	}

	private PurchaseOrderAmendmentResponceDTO buildPurchaseOrderAmendmentResponse(PurchaseOrderAmendmentVO vo) {

		PurchaseOrderAmendmentResponceDTO dto = new PurchaseOrderAmendmentResponceDTO();

		dto.setId(vo.getId());
		dto.setDocId(vo.getDocId());
		dto.setDocDate(vo.getDocDate());

		dto.setBelongsTo(vo.getBelongsTo());
		dto.setPurchaseordernumber(vo.getPurchaseordernumber());
//        dto.setCurrency(vo.getCurrency());
//       dto.setRefNo(vo.getRefNo());
//       dto.setRefDate(vo.getRefDate());
//        dto.setExchangeRate(vo.getExchangeRate());
		dto.setRevisionNo(vo.getRevisionNo());

		dto.setFreightType(vo.getFreightType());
		dto.setPackingType(vo.getPackingType());
		dto.setInsuranceAmount(vo.getInsuranceAmount());
		dto.setModeOfDespatch(vo.getModeOfDespatch());
		dto.setTaxDescription(vo.getTaxDescription());
		dto.setRemarks(vo.getRemarks());

		dto.setOrgId(vo.getOrgId());
		dto.setCreatedBy(vo.getCreatedBy());
		dto.setCancelRemarks(vo.getCancelRemarks());
		dto.setActive(vo.isActive());

		dto.setScreenCode(vo.getScreenCode());
		dto.setScreenName(vo.getScreenName());

		// branch

		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());

			dto.setBranch(branchDTO);
		}

		// customer
		if (vo.getCustomer() != null) {

			CustomerResponseDetailsDTO customerDTO = new CustomerResponseDetailsDTO();

			customerDTO.setId(vo.getCustomer().getId());
			customerDTO.setCustomerCode(vo.getCustomer().getCustomerCode());
			customerDTO.setCustomerName(vo.getCustomer().getCustomerName());

			dto.setCustomer(customerDTO);

		}

		// Details
		List<PurchaseOrderAmendmentDetailsResponseDTO> detailsList = new ArrayList<>();

		if (vo.getDetails() != null) {

			for (PurchaseOrderAmendmentDetailsVO detailVO : vo.getDetails()) {

				PurchaseOrderAmendmentDetailsResponseDTO detailDTO = new PurchaseOrderAmendmentDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());

				// Item
				if (detailVO.getItem() != null) {

					PurchaseOrderAmendmentDtailsItemResponseDTO itemDTO = new PurchaseOrderAmendmentDtailsItemResponseDTO();

					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailDTO.setItem(itemDTO);
				}

				// Unit
				if (detailVO.getUnit() != null) {

					UnitResponseDTO unitDTO = new UnitResponseDTO();

					unitDTO.setId(detailVO.getUnit().getId());
					unitDTO.setUnitId(detailVO.getUnit().getUnitId());

					detailDTO.setUnit(unitDTO);
				}

				// Remaining fields
				detailDTO.setOldQty(detailVO.getOldQty());
				detailDTO.setNewQty(detailVO.getNewQty());
				detailDTO.setOldRate(detailVO.getOldRate());
				detailDTO.setNewRate(detailVO.getNewRate());
				detailDTO.setOldDeliveryDate(detailVO.getOldDeliveryDate());
				detailDTO.setNewDeliveryDate(detailVO.getNewDeliveryDate());

				detailsList.add(detailDTO);
			} // <-- closes for
		} // <-- closes if

		dto.setDetails(detailsList);

		// Attachments
		List<PurchaseOrderAmendmentAttachmentResponseDTO> attachmentList = new ArrayList<>();

		if (vo.getAttachments() != null) {

			for (PurchaseOrderAmendmentAttachmentVO attachmentVO : vo.getAttachments()) {

				PurchaseOrderAmendmentAttachmentResponseDTO attachmentDTO = new PurchaseOrderAmendmentAttachmentResponseDTO();

				attachmentDTO.setId(attachmentVO.getId());
				attachmentDTO.setName(attachmentVO.getName());
				attachmentDTO.setFileName(attachmentVO.getFileName());

				String urlPath = purchaseOrderAmendmentUploadPath.replace("C:/", "/").replace("\\", "/");

				attachmentDTO.setFilePath(serverBaseUrl + urlPath + attachmentVO.getFileName());

				attachmentDTO.setFileSize(attachmentVO.getFileSize());
				attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

				attachmentList.add(attachmentDTO);
			}
		}

		dto.setAttachments(attachmentList);

		return dto;

	}

	@Override
	public PurchaseOrderAmendmentResponceDTO getPurchaseOrderAmendmentById(Long id) throws ApplicationException {

		PurchaseOrderAmendmentVO purchaseOrderAmendmentVO = purchaseOrderAmendmentRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Purchase Order Amendment Not Found"));

		return buildPurchaseOrderAmendmentResponse(purchaseOrderAmendmentVO);
	}

	@Override
	public List<PurchaseOrderAmendmentResponceDTO> getPurchaseOrderAmendmentByOrgId(Long orgId)
			throws ApplicationException {

		List<PurchaseOrderAmendmentVO> purchaseOrderAmendmentVOList = purchaseOrderAmendmentRepo
				.findByOrgIdAndCancelFalse(orgId);

		if (purchaseOrderAmendmentVOList == null || purchaseOrderAmendmentVOList.isEmpty()) {
			throw new ApplicationException("Purchase Order Amendment not found");
		}

		List<PurchaseOrderAmendmentResponceDTO> responseList = new ArrayList<>();

		for (PurchaseOrderAmendmentVO purchaseOrderAmendmentVO : purchaseOrderAmendmentVOList) {

			PurchaseOrderAmendmentResponceDTO responseDTO = buildPurchaseOrderAmendmentResponse(
					purchaseOrderAmendmentVO);

			responseList.add(responseDTO);
		}

		return responseList;
	}

	// revision number

	@Override
	public Integer getPurchaseOrderAmendmentRevisionNo(String purchaseOrderNumber, Long orgId, Long branch)
			throws ApplicationException {

		Integer revisionNo = purchaseOrderAmendmentRepo.getPurchaseOrderAmendmentRevisionNo(purchaseOrderNumber, orgId,
				branch);

		return revisionNo;
	}

	// purchaseorderamendmentponumberdropdown

	@Override
	public Map<String, Object> getPurchaseOrderAmendmentforCustomer(Long customer, Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> result = purchaseOrderAmendmentRepo.getPurchaseOrderAmendmentforCustomer(customer, branch,
				orgId);

		Map<String, Object> response = new HashMap<>();
		response.put("purchaseOrderList", getPurchaseOrderDetails(result));

		return response;
	}

	private List<Map<String, Object>> getPurchaseOrderDetails(List<Object[]> result) {

		List<Map<String, Object>> purchaseOrderList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> purchaseOrder = new HashMap<>();

			purchaseOrder.put("id", obj[3] != null ? ((Number) obj[3]).longValue() : null);

			purchaseOrder.put("docId", obj[0] != null ? obj[0].toString() : null);

			purchaseOrder.put("docDate", obj[1] != null ? obj[1].toString() : null);

			purchaseOrder.put("belongsTo", obj[2] != null ? obj[2].toString() : null);

			purchaseOrderList.add(purchaseOrder);
		}

		return purchaseOrderList;
	}

	// purchaseorderamendmentitemdropdown

	@Override
	public List<Map<String, Object>> getPurchaseOrderAmendmentItemCodeDropdown(String docId, Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> itemList = purchaseOrderAmendmentRepo.getPurchaseOrderAmendmentItemCodeDropdown(docId, branch,
				orgId);

		if (itemList.isEmpty()) {

			throw new ApplicationException("No Item Details Found");
		}

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemCode", obj[0]);
			map.put("hsnSacCode", obj[1]);

			responseList.add(map);
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getCurrencyExchangeRateforPurchaseOrderAmendment(Long customer, Long orgId,
			Long branch) throws ApplicationException {

		Set<Object[]> result = purchaseOrderAmendmentRepo.getCurrencyExchangeRateforPurchaseOrderAmendment(customer,
				orgId, branch);

		List<Map<String, Object>> currencyDetails = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> currency = new HashMap<>();

			currency.put("currencyId", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			currency.put("currency", obj[1] != null ? obj[1].toString() : null);

			currency.put("exchangeRate", obj[2] != null ? ((Number) obj[2]).doubleValue() : null);

			currency.put("buyingExRate", obj[3] != null ? ((Number) obj[3]).doubleValue() : null);

			currencyDetails.add(currency);
		}

		return currencyDetails;
	}

	// DOCID

	@Override
	public String getPurchaseOrderAmendmentDocId(Long orgId, String financialYear, String screenCode) {

		String screenCode1 = "POA";

		String result = purchaseOrderAmendmentRepo.getPurchaseOrderAmendmentDocId(orgId, financialYear, screenCode1);

		return result;
	}

	// openstockentry

	@Override
	@Transactional
	public Map<String, Object> createUpdateOpenStockEntry(OpenStockEntryDto openStockEntryDto)
			throws ApplicationException {

		OpenStockEntryVO openStockEntryVO;
		String message;

		if (ObjectUtils.isNotEmpty(openStockEntryDto.getId())) {

			openStockEntryVO = openStockEntryRepo.findById(openStockEntryDto.getId())
					.orElseThrow(() -> new ApplicationException("Open Stock Entry Not Found"));

			openStockEntryVO.setUpdatedBy(openStockEntryDto.getCreatedBy());

			message = "Open Stock Entry Updated Successfully";

		} else {

			openStockEntryVO = new OpenStockEntryVO();

			openStockEntryVO.setCreatedBy(openStockEntryDto.getCreatedBy());

			openStockEntryVO.setUpdatedBy(openStockEntryDto.getCreatedBy());

			message = "Open Stock Entry Created Successfully";
		}

		// Header Mapping
		createUpdateOpenStockEntryVOByDTO(openStockEntryDto, openStockEntryVO);

		// Save
		openStockEntryVO = openStockEntryRepo.save(openStockEntryVO);

		// Response
		OpenStockEntryResponseDTO responseDTO = buildOpenStockEntryResponse(openStockEntryVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("openStockEntryVO", responseDTO);

		return response;
	}

	private void createUpdateOpenStockEntryVOByDTO(OpenStockEntryDto dto, OpenStockEntryVO vo)
			throws ApplicationException {

		// Branch
		if (dto.getBranch() != null && dto.getBranch() > 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		// Location
		if (dto.getLocation() != null && dto.getLocation() > 0) {

			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			vo.setLocation(location);
		}

		// Item
		if (dto.getItem() != null && dto.getItem() > 0) {

			ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
					.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

			vo.setItem(item);
		}

		vo.setAsOnDate(dto.getAsOnDate());

		vo.setQty(dto.getQty());

		vo.setRate(dto.getRate());

		vo.setAmount(dto.getAmount());

		vo.setRemarks(dto.getRemarks());

		vo.setActive(dto.isActive());

		vo.setOrgId(dto.getOrgId());

		vo.setCreatedBy(dto.getCreatedBy());

		vo.setCancelRemarks(dto.getCancelRemarks());

		vo.setScreenName("OPENSTOCKENTRY");

		vo.setScreenCode("OSE");
	}

	private OpenStockEntryResponseDTO buildOpenStockEntryResponse(OpenStockEntryVO openStockEntryVO) {

		OpenStockEntryResponseDTO responseDTO = new OpenStockEntryResponseDTO();

		responseDTO.setId(openStockEntryVO.getId());

		responseDTO.setAsOnDate(openStockEntryVO.getAsOnDate());

		responseDTO.setQty(openStockEntryVO.getQty());

		responseDTO.setRate(openStockEntryVO.getRate());

		responseDTO.setAmount(openStockEntryVO.getAmount());

		responseDTO.setRemarks(openStockEntryVO.getRemarks());

		responseDTO.setActive(openStockEntryVO.isActive());

		responseDTO.setOrgId(openStockEntryVO.getOrgId());

		responseDTO.setCreatedBy(openStockEntryVO.getCreatedBy());

		responseDTO.setCancelRemarks(openStockEntryVO.getCancelRemarks());

		// Branch
		if (openStockEntryVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(openStockEntryVO.getBranch().getId());

			branchDTO.setBranchCode(openStockEntryVO.getBranch().getBranchCode());

			branchDTO.setBranchName(openStockEntryVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		// Location
		if (openStockEntryVO.getLocation() != null) {

			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

			locationDTO.setId(openStockEntryVO.getLocation().getId());

			locationDTO.setLocationName(openStockEntryVO.getLocation().getLocationName());

			responseDTO.setLocation(locationDTO);
		}

		// Item
		if (openStockEntryVO.getItem() != null) {

			ItemResponse1DTO itemDTO = new ItemResponse1DTO();

			itemDTO.setId(openStockEntryVO.getItem().getId());

			itemDTO.setItemCode(openStockEntryVO.getItem().getItemCode());

			itemDTO.setItemDescription(openStockEntryVO.getItem().getItemDescription());

			if (openStockEntryVO.getItem().getPrimaryUnit() != null) {

				UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

				unitDTO.setId(openStockEntryVO.getItem().getPrimaryUnit().getId());

				unitDTO.setUnitId(openStockEntryVO.getItem().getPrimaryUnit().getUnitId());

				unitDTO.setUnitDescription(openStockEntryVO.getItem().getPrimaryUnit().getDescription());

				itemDTO.setUnit(unitDTO);
			}
			responseDTO.setItem(itemDTO);

		}

		return responseDTO;

	}

	@Override
	public OpenStockEntryResponseDTO getOpenStockEntryById(Long id) throws ApplicationException {

		OpenStockEntryVO openStockEntryVO = openStockEntryRepo.findById(id).orElse(null);

		if (openStockEntryVO == null) {

			throw new ApplicationException("Open Stock Entry Not Found");
		}

		return buildOpenStockEntryResponse(openStockEntryVO);
	}

	@Override
	public List<OpenStockEntryResponseDTO> getOpenStockEntryByOrgId(Long orgId, Long branchId)
			throws ApplicationException {

		BranchVO branchVO = branchRepo.findById(branchId)
				.orElseThrow(() -> new ApplicationException("Branch Not Found"));

		List<OpenStockEntryVO> openStockEntryList = openStockEntryRepo.findByOrgIdAndBranch(orgId, branchVO);

		if (openStockEntryList == null || openStockEntryList.isEmpty()) {

			throw new ApplicationException("Open Stock Entry Not Found");
		}

		List<OpenStockEntryResponseDTO> responseList = new ArrayList<>();

		for (OpenStockEntryVO openStockEntryVO : openStockEntryList) {

			responseList.add(buildOpenStockEntryResponse(openStockEntryVO));
		}

		return responseList;
	}

	// itemcodedropdownforopenstockentry

	@Override
	public Map<String, Object> getOpenStockEntryItemCodeDropdown(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> result = openStockEntryRepo.getOpenStockEntryItemCodeDropdown(orgId, branch);

		Map<String, Object> response = new HashMap<>();

		response.put("itemCodeList", getOpenStockEntryItemCodeDetails(result));

		return response;
	}

	private List<Map<String, Object>> getOpenStockEntryItemCodeDetails(List<Object[]> result) {

		List<Map<String, Object>> itemCodeList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> itemCode = new HashMap<>();

			itemCode.put("itemCode", obj[0] != null ? obj[0].toString() : null);

			itemCode.put("itemDescription", obj[1] != null ? obj[1].toString() : null);

			itemCode.put("unitId", obj[2] != null ? obj[2].toString() : null);

			itemCode.put("id", obj[3] != null ? Long.valueOf(obj[3].toString()) : null);

			itemCodeList.add(itemCode);
		}

		return itemCodeList;
	}

	@Override
	public String getOpenStockEntryDocId(Long orgId, String financialYear, String screenCode) {

		String screenCode1 = "OSE";

		String result = openStockEntryRepo.getOpenStockEntryDocId(orgId, financialYear, screenCode);

		return result;
	}

	// Issues

	@Override
	@Transactional
	public Map<String, Object> createUpdateIssues(IssuesDTO issuesDto) throws ApplicationException {

		String screenCode = "ISU";

		IssuesVO issuesVO = new IssuesVO();

		String message;

		// =========================
		// Create / Update
		// =========================

		if (ObjectUtils.isNotEmpty(issuesDto.getId())) {

			// =========================
			// Update
			// =========================

			issuesVO = issuesRepo.findById(issuesDto.getId())
					.orElseThrow(() -> new ApplicationException("Issues Not Found"));

			issuesVO.setUpdatedBy(issuesDto.getCreatedBy());

			message = "Issues Updated Successfully";

		} else {

			// =========================
			// Generate Document ID
			// =========================

			String docId = issuesRepo.getIssuesDocId(issuesDto.getOrgId(), issuesDto.getFinancialYear(), screenCode);

			issuesVO.setDocId(docId);

			// =========================
			// Document Type Mapping
			// =========================

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(issuesDto.getOrgId(), issuesDto.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {

				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// =========================
			// Created / Updated By
			// =========================

			issuesVO.setCreatedBy(issuesDto.getCreatedBy());

			issuesVO.setUpdatedBy(issuesDto.getCreatedBy());

			message = "Issues Created Successfully";
		}

		// =========================
		// Header Mapping
		// =========================

		createUpdateIssuesVO(issuesDto, issuesVO);

		// =========================
		// Save Header + Details
		// =========================

		IssuesVO savedVO = issuesRepo.save(issuesVO);

		// =========================
		// Response
		// =========================

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("issuesVO", buildIssuesResponse(savedVO));

		return response;
	}

	private void createUpdateIssuesVO(IssuesDTO dto, IssuesVO issuesVO) throws ApplicationException {

		// =========================
		// Branch
		// =========================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			issuesVO.setBranch(branch);
		}

		// =========================
		// Department
		// =========================

		if (dto.getDepartment() != null && dto.getDepartment() != 0) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			issuesVO.setDepartment(department);
		}

		// =========================
		// Document Date
		// =========================

//	   issuesVO.setDocDate(dto.getDocDate());

		// =========================
		// Belongs To
		// =========================

		issuesVO.setBelongsTo(dto.getBelongsTo());

		// =========================
		// Time
		// =========================

		issuesVO.setTime(dto.getTime());

		// =========================
		// Reference No
		// =========================

		issuesVO.setRefNo(dto.getRefNo());

		// =========================
		// Reference Date
		// =========================

		issuesVO.setRefDate(dto.getRefDate());

		// =========================
		// Indent No
		// =========================

		issuesVO.setIndentNo(dto.getIndentNo());

		// =========================
		// Issue From
		// =========================

		if (dto.getIssueFrom() != null && dto.getIssueFrom() != 0) {

			LocationVO issueFrom = locationRepo.findById(dto.getIssueFrom())
					.orElseThrow(() -> new ApplicationException("Issue From Location Not Found"));

			issuesVO.setIssueFrom(issueFrom);
		}

		// =========================
		// Issue To
		// =========================

		if (dto.getIssueTo() != null && dto.getIssueTo() != 0) {

			LocationVO issueTo = locationRepo.findById(dto.getIssueTo())
					.orElseThrow(() -> new ApplicationException("Issue To Location Not Found"));

			issuesVO.setIssueTo(issueTo);
		}

		// =========================
		// Narration
		// =========================

		issuesVO.setNarration(dto.getNarration());

		// =========================
		// Active
		// =========================

		issuesVO.setActive(dto.isActive());

		// =========================
		// Organization
		// =========================

		issuesVO.setOrgId(dto.getOrgId());

		// =========================
		// Created By
		// =========================

		issuesVO.setCreatedBy(dto.getCreatedBy());

		// =========================
		// Cancel Remarks
		// =========================

		issuesVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================
		// Screen Details
		// =========================

		issuesVO.setScreenCode("ISU");

		issuesVO.setScreenName("ISSUES");

		// =====================================================
		// Delete Existing Details During Update
		// =====================================================

		if (dto.getId() != null) {

			List<IssuesDetailsVO> oldDetails = issuesDetailsRepo.findByIssuesVO(issuesVO);

			if (oldDetails != null && !oldDetails.isEmpty()) {

				issuesDetailsRepo.deleteAll(oldDetails);
			}

			// Clear existing collection
			issuesVO.getDetails().clear();
		}

		// =========================
		// Details Mapping
		// =========================

		List<IssuesDetailsVO> detailsList = new ArrayList<>();

		if (dto.getIssuesDetails() != null && !dto.getIssuesDetails().isEmpty()) {

			for (IssuesDetailsDTO detailDTO : dto.getIssuesDetails()) {

				IssuesDetailsVO detailVO = new IssuesDetailsVO();

				// =========================
				// Item
				// =========================

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				// =========================
				// Quantity Available
				// =========================

				detailVO.setQtyAvailable(detailDTO.getQtyAvailable());

				// =========================
				// Indent Quantity
				// =========================

				detailVO.setIndentQty(detailDTO.getIndentQty());

				// =========================
				// Previously Issued Quantity
				// =========================

				detailVO.setPreviouslyIssuedQty(detailDTO.getPreviouslyIssuedQty());

				// =========================
				// Pending Quantity
				// =========================

				detailVO.setPendingQty(detailDTO.getPendingQty());

				// =========================
				// Issue Quantity
				// =========================

				detailVO.setQty(detailDTO.getQty());

				// =========================
				// Rate
				// =========================

				detailVO.setRate(detailDTO.getRate());

				// =========================
				// Amount
				// Amount = Qty * Rate
				// =========================

				if (detailDTO.getQty() != null && detailDTO.getRate() != null) {

					BigDecimal amount = detailDTO.getQty().multiply(detailDTO.getRate()).setScale(2,
							RoundingMode.HALF_UP);

					detailVO.setAmount(amount);
				}

				// =========================
				// Parent Mapping
				// =========================

				detailVO.setIssuesVO(issuesVO);

				// =========================
				// Add Details
				// =========================

				detailsList.add(detailVO);
			}

			// =========================
			// Set Details To Parent
			// =========================

			issuesVO.setDetails(detailsList);
		}
	}

	private IssuesResponseDTO buildIssuesResponse(IssuesVO issuesVO) {

		IssuesResponseDTO responseDTO = new IssuesResponseDTO();

		// =========================
		// Basic Details
		// =========================

		responseDTO.setId(issuesVO.getId());

		responseDTO.setDocId(issuesVO.getDocId());

		responseDTO.setDocDate(issuesVO.getDocDate());

		responseDTO.setBelongsTo(issuesVO.getBelongsTo());

		responseDTO.setTime(issuesVO.getTime());

		responseDTO.setRefNo(issuesVO.getRefNo());

		responseDTO.setRefDate(issuesVO.getRefDate());

		responseDTO.setIndentNo(issuesVO.getIndentNo());

		responseDTO.setNarration(issuesVO.getNarration());

		responseDTO.setActive(issuesVO.isActive());

		responseDTO.setOrgId(issuesVO.getOrgId());

		responseDTO.setCreatedBy(issuesVO.getCreatedBy());

		responseDTO.setCancelRemarks(issuesVO.getCancelRemarks());

		// =========================
		// Branch Response
		// =========================

		if (issuesVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(issuesVO.getBranch().getId());

			branchDTO.setBranchCode(issuesVO.getBranch().getBranchCode());

			branchDTO.setBranchName(issuesVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		// =========================
		// Department Response
		// =========================

		if (issuesVO.getDepartment() != null) {

			DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

			departmentDTO.setId(issuesVO.getDepartment().getId());

			departmentDTO.setDepartmentName(issuesVO.getDepartment().getDepartmentName());

			responseDTO.setDepartment(departmentDTO);
		}

		// =========================
		// Issue From Response
		// =========================

		if (issuesVO.getIssueFrom() != null) {

			LocationIssuesResponseDTO locationDTO = new LocationIssuesResponseDTO();

			locationDTO.setId(issuesVO.getIssueFrom().getId());

			locationDTO.setLocationName(issuesVO.getIssueFrom().getLocationName());

			responseDTO.setIssueFrom(locationDTO);
		}

		// =========================
		// Issue To Response
		// =========================

		if (issuesVO.getIssueTo() != null) {

			LocationIssuesResponseDTO locationDTO = new LocationIssuesResponseDTO();

			locationDTO.setId(issuesVO.getIssueTo().getId());

			locationDTO.setLocationName(issuesVO.getIssueTo().getLocationName());

			responseDTO.setIssueTo(locationDTO);
		}

		// =========================
		// Details Response
		// =========================

		List<IssuesDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (issuesVO.getDetails() != null && !issuesVO.getDetails().isEmpty()) {

			for (IssuesDetailsVO detailVO : issuesVO.getDetails()) {

				IssuesDetailsResponseDTO detailDTO = buildIssuesDetailsResponse(detailVO);

				detailResponseList.add(detailDTO);
			}
		}

		responseDTO.setIssuesDetails(detailResponseList);

		return responseDTO;
	}

	private IssuesDetailsResponseDTO buildIssuesDetailsResponse(IssuesDetailsVO detailsVO) {

		IssuesDetailsResponseDTO responseDTO = new IssuesDetailsResponseDTO();

		// =========================
		// Basic Details
		// =========================

		responseDTO.setId(detailsVO.getId());

		responseDTO.setQtyAvailable(detailsVO.getQtyAvailable());

		responseDTO.setIndentQty(detailsVO.getIndentQty());

		responseDTO.setPreviouslyIssuedQty(detailsVO.getPreviouslyIssuedQty());

		responseDTO.setPendingQty(detailsVO.getPendingQty());

		responseDTO.setQty(detailsVO.getQty());

		responseDTO.setRate(detailsVO.getRate());

		responseDTO.setAmount(detailsVO.getAmount());

		// =========================
		// Item Response
		// =========================

		if (detailsVO.getItem() != null) {

			ItemResponse1DTO itemDTO = new ItemResponse1DTO();

			itemDTO.setId(detailsVO.getItem().getId());

			itemDTO.setItemCode(detailsVO.getItem().getItemCode());

			itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());

			// =========================
			// Primary Unit
			// =========================

			if (detailsVO.getItem().getPrimaryUnit() != null) {

				UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

				unitDTO.setId(detailsVO.getItem().getPrimaryUnit().getId());

				unitDTO.setUnitId(detailsVO.getItem().getPrimaryUnit().getUnitId());

				unitDTO.setUnitDescription(detailsVO.getItem().getPrimaryUnit().getDescription());

				itemDTO.setUnit(unitDTO);
			}

			responseDTO.setItem(itemDTO);
		}

		return responseDTO;
	}

	@Override
	public IssuesResponseDTO getIssuesById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {

			throw new ApplicationException("Invalid Id");
		}

		IssuesVO issuesVO = issuesRepo.findById(id).orElseThrow(() -> new ApplicationException("Issues Not Found"));

		return buildIssuesResponse(issuesVO);
	}

	@Override
	public List<IssuesResponseDTO> getIssuesByOrgId(Long orgId, Long branch) throws ApplicationException {

		BranchVO branchVO = branchRepo.findById(branch).orElseThrow(() -> new ApplicationException("Branch Not Found"));

		List<IssuesVO> issuesList = issuesRepo.findByOrgIdAndBranch(orgId, branchVO);

		if (issuesList == null || issuesList.isEmpty()) {

			throw new ApplicationException("No Issues Details Found");
		}

		List<IssuesResponseDTO> responseList = new ArrayList<>();

		for (IssuesVO issuesVO : issuesList) {

			responseList.add(buildIssuesResponse(issuesVO));
		}

		return responseList;
	}

	// issuesfromdropdown

	@Override
	public Map<String, Object> getIssueFromLocationDropdown(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> result = issuesRepo.getIssueFromLocationDropdown(orgId, branch);

		Map<String, Object> response = new HashMap<>();

		response.put("locationList", getIssueFromLocationDetails(result));

		return response;
	}

	private List<Map<String, Object>> getIssueFromLocationDetails(List<Object[]> result) {

		List<Map<String, Object>> locationList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> location = new HashMap<>();

			location.put("id", obj[0] != null ? Long.valueOf(obj[0].toString()) : null);

			location.put("locationId", obj[1] != null ? obj[1].toString() : null);

			location.put("locationName", obj[2] != null ? obj[2].toString() : null);

			locationList.add(location);
		}

		return locationList;
	}

	// Issuetodropdown

	@Override
	public Map<String, Object> getIssueToLocationDropdown(Long orgId, Long branch, Long issueFrom)
			throws ApplicationException {

		List<Object[]> result = issuesRepo.getIssueToLocationDropdown(orgId, branch, issueFrom);

		Map<String, Object> response = new HashMap<>();

		response.put("locationList", getIssueToLocationDetails(result));

		return response;
	}

	private List<Map<String, Object>> getIssueToLocationDetails(List<Object[]> result) {

		List<Map<String, Object>> locationList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> location = new HashMap<>();

			location.put("id", obj[0] != null ? Long.valueOf(obj[0].toString()) : null);

			location.put("locationId", obj[1] != null ? obj[1].toString() : null);

			location.put("locationName", obj[2] != null ? obj[2].toString() : null);

			locationList.add(location);
		}

		return locationList;
	}

	// issuesindentnumberdropdown

	@Override
	public Map<String, Object> getIssueIndentNoDropdown(Long orgId, Long branch) throws ApplicationException {

		List<Object[]> result = issuesRepo.getIssueIndentNoDropdown(orgId, branch);

		Map<String, Object> response = new HashMap<>();

		response.put("indentNoList", getIssueIndentNoDetails(result));

		return response;
	}

	private List<Map<String, Object>> getIssueIndentNoDetails(List<Object[]> result) {

		List<Map<String, Object>> indentNoList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> indentNo = new HashMap<>();

			indentNo.put("id", obj[0] != null ? Long.valueOf(obj[0].toString()) : null);

			indentNo.put("indentNo", obj[1] != null ? obj[1].toString() : null);

			indentNo.put("docDate", obj[2] != null ? obj[2].toString() : null);

			indentNoList.add(indentNo);
		}

		return indentNoList;
	}

	// issuesitemcodedropdown

	@Override
	public Map<String, Object> getIssueItemCodeDropdown(Long orgId, Long branch, String indentNo)
			throws ApplicationException {

		List<Object[]> result = issuesRepo.getIssueItemCodeDropdown(orgId, branch, indentNo);

		Map<String, Object> response = new HashMap<>();

		response.put("itemCodeList", getIssueItemCodeDetails(result));

		return response;
	}

	private List<Map<String, Object>> getIssueItemCodeDetails(List<Object[]> result) {

		List<Map<String, Object>> itemCodeList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> itemCode = new HashMap<>();

			itemCode.put("id", obj[0] != null ? Long.valueOf(obj[0].toString()) : null);

			itemCode.put("itemCode", obj[1] != null ? obj[1].toString() : null);

			itemCode.put("itemDescription", obj[2] != null ? obj[2].toString() : null);

			itemCode.put("unitId", obj[3] != null ? obj[3].toString() : null);

			itemCode.put("stock", obj[4] != null ? obj[4].toString() : null);

			itemCodeList.add(itemCode);
		}

		return itemCodeList;
	}

	// docidissues

	@Override
	public String getIssuesDocId(Long orgId, String financialYear) {

		String screenCode1 = "ISU";

		String result = issuesRepo.getIssuesDocId(orgId, financialYear, screenCode1);

		return result;
	}
	// ParameterMaster

	@Override
	@Transactional
	public Map<String, Object> createUpdateParameterMaster(ParameterMasterDTO parameterMasterDTO)
			throws ApplicationException {

		ParameterMasterVO parameterMasterVO;

		String message;

		if (ObjectUtils.isNotEmpty(parameterMasterDTO.getId())) {

			parameterMasterVO = parameterMasterRepo.findById(parameterMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Parameter Master Not Found"));

			parameterMasterVO.setUpdatedBy(parameterMasterDTO.getCreatedBy());

			message = "Parameter Master Updated Successfully";

		} else {

			parameterMasterVO = new ParameterMasterVO();

			parameterMasterVO.setCreatedBy(parameterMasterDTO.getCreatedBy());

			parameterMasterVO.setUpdatedBy(parameterMasterDTO.getCreatedBy());

			message = "Parameter Master Created Successfully";
		}

		// Header Mapping
		createUpdateParameterMasterVOByDTO(parameterMasterDTO, parameterMasterVO);

		// Save
		parameterMasterVO = parameterMasterRepo.save(parameterMasterVO);

		// Response
		ParameterMasterResponseDTO responseDTO = buildParameterMasterResponse(parameterMasterVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("parameterMasterVO", responseDTO);

		return response;
	}

	private void createUpdateParameterMasterVOByDTO(ParameterMasterDTO dto, ParameterMasterVO vo)
			throws ApplicationException {

		// Parameter Code
		vo.setParameterCode(dto.getParameterCode());

		// Parameter Description
		vo.setParameterDescription(dto.getParameterDescription());

		// Parameter Type
		if (dto.getParameterType() != null && dto.getParameterType() > 0) {

			ListOfValuesDetailsVO parameterType = listOfValuesDetailsRepo.findById(dto.getParameterType())
					.orElseThrow(() -> new ApplicationException("Parameter Type Not Found"));

			vo.setParameterType(parameterType);
		}

		vo.setActive(dto.isActive());

		vo.setOrgId(dto.getOrgId());

		vo.setCreatedBy(dto.getCreatedBy());

		vo.setUpdatedBy(dto.getUpdatedBy());

		vo.setCancel(dto.isCancel());

		vo.setCancelRemarks(dto.getCancelRemarks());

		vo.setScreenName("PARAMETERMASTER");

		vo.setScreenCode("PM");
	}

	private ParameterMasterResponseDTO buildParameterMasterResponse(ParameterMasterVO parameterMasterVO) {

		ParameterMasterResponseDTO responseDTO = new ParameterMasterResponseDTO();

		responseDTO.setId(parameterMasterVO.getId());

		responseDTO.setParameterCode(parameterMasterVO.getParameterCode());

		responseDTO.setParameterDescription(parameterMasterVO.getParameterDescription());

		responseDTO.setActive(parameterMasterVO.isActive());

		responseDTO.setOrgId(parameterMasterVO.getOrgId());

		responseDTO.setCreatedBy(parameterMasterVO.getCreatedBy());

		responseDTO.setCancelRemarks(parameterMasterVO.getCancelRemarks());

		responseDTO.setScreenName(parameterMasterVO.getScreenName());

		responseDTO.setScreenCode(parameterMasterVO.getScreenCode());

		// Parameter Type

		if (parameterMasterVO.getParameterType() != null) {

			ListOfValuesDetailsResponseDTO parameterTypeDTO = new ListOfValuesDetailsResponseDTO();

			parameterTypeDTO.setId(parameterMasterVO.getParameterType().getId());

			parameterTypeDTO.setCode(parameterMasterVO.getParameterType().getValueCode());

			parameterTypeDTO.setDescription(parameterMasterVO.getParameterType().getValueDescription());

			responseDTO.setParameterType(parameterTypeDTO);
		}

		return responseDTO;
	}

	@Override
	public ParameterMasterResponseDTO getParameterMasterById(Long id) throws ApplicationException {

		ParameterMasterVO parameterMasterVO = parameterMasterRepo.findById(id).orElse(null);

		if (parameterMasterVO == null) {
			throw new ApplicationException("Parameter Master Not Found");
		}

		return buildParameterMasterResponse(parameterMasterVO);
	}

	@Override
	public List<ParameterMasterResponseDTO> getParameterMasterByOrgId(Long orgId) throws ApplicationException {

		List<ParameterMasterVO> parameterMasterList = parameterMasterRepo.findByOrgId(orgId);

		if (parameterMasterList == null || parameterMasterList.isEmpty()) {

			throw new ApplicationException("Parameter Master Not Found");
		}

		List<ParameterMasterResponseDTO> responseList = new ArrayList<>();

		for (ParameterMasterVO parameterMasterVO : parameterMasterList) {

			responseList.add(buildParameterMasterResponse(parameterMasterVO));
		}

		return responseList;
	}

	// machine/instrumentmaster

	@Override
	@Transactional
	public Map<String, Object> updateCreateMachineMaster(MachineMasterDTO machineMasterDTO, MultipartFile[] files)
			throws ApplicationException {

		MachineMasterVO machineMasterVO;
		String message;

		// =========================================================
		// CREATE / UPDATE
		// =========================================================

		if (ObjectUtils.isNotEmpty(machineMasterDTO.getId())) {

			machineMasterVO = machineMasterRepo.findById(machineMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Machine Master"));

			// =====================================================
			// DELETE OLD ATTACHMENT FILES + DB RECORDS
			// =====================================================

			deleteOldMachineMasterAttachments(machineMasterVO);

			machineMasterVO.setUpdatedBy(machineMasterDTO.getUpdatedBy());

			message = "Machine Master Updated Successfully";

		} else {

			machineMasterVO = new MachineMasterVO();

			machineMasterVO.setCreatedBy(machineMasterDTO.getCreatedBy());
			machineMasterVO.setUpdatedBy(machineMasterDTO.getUpdatedBy());

			message = "Machine Master Created Successfully";
		}

		// =========================================================
		// MAP MASTER + OTHER CHILDREN
		// =========================================================

		createUpdateMachineMasterVO(machineMasterDTO, machineMasterVO);

		// =========================================================
		// SAVE MASTER
		// =========================================================

		MachineMasterVO savedVO = machineMasterRepo.save(machineMasterVO);

		// =========================================================
		// SAVE NEW UPLOADED FILES
		// =========================================================

		try {
			List<MachineMasterAttachmentVO> attachments = saveAttachments(files, savedVO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// =========================================================
		// RESPONSE
		// =========================================================

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("machineMasterVO", machineMasterResponse(savedVO));

		return response;
	}

	private void deleteOldMachineMasterAttachments(MachineMasterVO machineMasterVO) {

		List<MachineMasterAttachmentVO> oldAttachments = machineMasterAttachmentRepo
				.findByMachineMasterVO(machineMasterVO);

		if (oldAttachments == null || oldAttachments.isEmpty()) {

			System.out.println("NO OLD ATTACHMENTS FOUND");

			return;
		}

		for (MachineMasterAttachmentVO attachment : oldAttachments) {

			try {

				String filePath = attachment.getFilePath();

				System.out.println("OLD FILE PATH = " + filePath);

				if (filePath != null && !filePath.trim().isEmpty()) {

					Path path = Paths.get(filePath);

					if (Files.exists(path)) {

						Files.delete(path);

						System.out.println("OLD FILE DELETED = " + path);

					} else {

						System.out.println("OLD FILE DOES NOT EXIST = " + path);
					}
				}

			} catch (Exception e) {

				System.out.println("ERROR DELETING FILE = " + e.getMessage());
			}
		}

		// =====================================================
		// DELETE OLD ATTACHMENT RECORDS FROM DATABASE
		// =====================================================

		machineMasterAttachmentRepo.deleteByMachineMasterVO(machineMasterVO);

		System.out.println("OLD ATTACHMENT RECORDS DELETED FROM DATABASE");

		// Clear Hibernate collection also
		if (machineMasterVO.getMachineMasterAttachmentVO() != null) {

			machineMasterVO.getMachineMasterAttachmentVO().clear();
		}
	}

	private List<MachineMasterAttachmentVO> saveAttachments(MultipartFile[] files, MachineMasterVO machineMasterVO)
			throws IOException {

		List<MachineMasterAttachmentVO> attachments = new ArrayList<>();

		if (files == null || files.length == 0) {

			System.out.println("NO FILES RECEIVED");

			return attachments;
		}

		Path uploadDir = Paths.get(machineMasterUploadPath);

		Files.createDirectories(uploadDir);

		System.out.println("UPLOAD DIRECTORY = " + uploadDir.toAbsolutePath());

		for (MultipartFile file : files) {

			if (file == null || file.isEmpty()) {

				System.out.println("FILE IS EMPTY");

				continue;
			}

			String originalFileName = file.getOriginalFilename();

			if (originalFileName == null || originalFileName.trim().isEmpty()) {

				continue;
			}

			String fileName = UUID.randomUUID() + "_" + Paths.get(originalFileName).getFileName().toString();

			Path targetPath = uploadDir.resolve(fileName);

			System.out.println("======================================");

			System.out.println("ORIGINAL FILE = " + originalFileName);

			System.out.println("FILE SIZE = " + file.getSize());

			System.out.println("TARGET PATH = " + targetPath.toAbsolutePath());

			System.out.println("======================================");

			// =====================================================
			// SAVE PHYSICAL FILE
			// =====================================================

			Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("FILE EXISTS AFTER COPY = " + Files.exists(targetPath));

			// =====================================================
			// CREATE ATTACHMENT
			// =====================================================

			MachineMasterAttachmentVO attachment = new MachineMasterAttachmentVO();

			attachment.setMachineMasterVO(machineMasterVO);

			attachment.setFileName(originalFileName);

			attachment.setFilePath(targetPath.toString());

			attachment.setFileSize(file.getSize());

			attachment.setContentType(file.getContentType());

			attachment.setName(originalFileName);

			attachment.setUploadOn(LocalDateTime.now());

			// =====================================================
			// ADD TO LIST
			// =====================================================

			attachments.add(attachment);

			// =====================================================
			// VERY IMPORTANT
			// ADD TO PARENT COLLECTION
			// =====================================================

			if (machineMasterVO.getMachineMasterAttachmentVO() == null) {

				machineMasterVO.setMachineMasterAttachmentVO(new ArrayList<>());
			}

			machineMasterVO.getMachineMasterAttachmentVO().add(attachment);
		}

		// =========================================================
		// SAVE ATTACHMENTS
		// =========================================================

		if (!attachments.isEmpty()) {

			machineMasterAttachmentRepo.saveAll(attachments);
		}

		return attachments;
	}

	private void createUpdateMachineMasterVO(MachineMasterDTO dto, MachineMasterVO vo) throws ApplicationException {

		// =========================================================
		// MASTER MAPPING
		// =========================================================

		if (dto.getBranch() != null) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		if (dto.getDepartment() != null) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			vo.setDepartment(department);
		}

		if (dto.getType() != null) {

			ListOfValuesDetailsVO type = listOfValuesDetailsRepo.findById(dto.getType())
					.orElseThrow(() -> new ApplicationException("Type Not Found"));

			vo.setType(type);
		}

		if (dto.getLocation() != null) {

			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			vo.setLocation(location);
		}

		if (dto.getMachineInstrumentCategory() != null) {

			ToolCategoryDetailVO category = toolCategoryDetailRepo.findById(dto.getMachineInstrumentCategory())
					.orElseThrow(() -> new ApplicationException("Machine Instrument Category Not Found"));

			vo.setMachineInstrumentCategory(category);
		}

		if (dto.getMadeIn() != null) {

			CountryVO country = countryRepo.findById(dto.getMadeIn())
					.orElseThrow(() -> new ApplicationException("Country Not Found"));

			vo.setMadeIn(country);
		}

		if (dto.getPurchasedFrom() != null) {

			CustomerVO customer = customerRepo.findById(dto.getPurchasedFrom())
					.orElseThrow(() -> new ApplicationException("Purchased From Customer Not Found"));

			vo.setPurchasedFrom(customer);
		}

		// =========================================================
		// SIMPLE MASTER FIELDS
		// =========================================================

		vo.setMachineInstrumentNo(dto.getMachineInstrumentNo());

		vo.setMachineInstrumentName(dto.getMachineInstrumentName());

		vo.setCalibrationRequired(dto.getCalibrationRequired());

		vo.setProcessNo(dto.getProcessNo());

		vo.setSection(dto.getSection());

		vo.setModel(dto.getModel());

		vo.setSerialNo(dto.getSerialNo());

		vo.setStatus(dto.getStatus());

		vo.setManufacturedBy(dto.getManufacturedBy());

		vo.setModeOfPurchase(dto.getModeOfPurchase());

		vo.setMachineInstrumentIncharge(dto.getMachineInstrumentIncharge());

		vo.setMachineInstrumentUsedFor(dto.getMachineInstrumentUsedFor());

		vo.setPmChecklistNo(dto.getPmChecklistNo());

		vo.setRemarks(dto.getRemarks());

		vo.setMake(dto.getMake());

		vo.setMachineInstrumentImageName(dto.getMachineInstrumentImageName());

		vo.setMachineOrInstrument(dto.getMachineOrInstrument());

		vo.setActive(dto.isActive());

		vo.setOrgId(dto.getOrgId());

		vo.setCancel(dto.isCancel());

		vo.setCancelRemarks(dto.getCancelRemarks());

		vo.setScreenName(dto.getScreenName());

		vo.setScreenCode(dto.getScreenCode());

//		technical info
		vo.setMachineInstrumentNo(dto.getMachineInstrumentNo());
		vo.setMachineInstrumentName(dto.getMachineInstrumentName());
		vo.setCalibrationRequired(dto.getCalibrationRequired());
		vo.setProcessNo(dto.getProcessNo());
		vo.setSection(dto.getSection());
		vo.setModel(dto.getModel());
		vo.setSerialNo(dto.getSerialNo());
		vo.setStatus(dto.getStatus());
		vo.setManufacturedBy(dto.getManufacturedBy());
		vo.setModeOfPurchase(dto.getModeOfPurchase());
		vo.setMachineInstrumentIncharge(dto.getMachineInstrumentIncharge());
		vo.setMachineInstrumentUsedFor(dto.getMachineInstrumentUsedFor());
		vo.setPmChecklistNo(dto.getPmChecklistNo());
		vo.setRemarks(dto.getRemarks());
		vo.setMake(dto.getMake());
		vo.setMachineInstrumentImageName(dto.getMachineInstrumentImageName());
		vo.setMachineOrInstrument(dto.getMachineOrInstrument());

		// ============================================================
		// MACHINE SPECIFICATION DETAILS
		// ============================================================

		vo.setInstallationDate(dto.getInstallationDate());
		vo.setPowerConsumption(dto.getPowerConsumption());
		vo.setConsumption(dto.getConsumption());
		vo.setPowerProduced(dto.getPowerProduced());
		vo.setTechnicalSpecification(dto.getTechnicalSpecification());
		vo.setCapacity(dto.getCapacity());
		vo.setBedSizeMm(dto.getBedSizeMm());
		vo.setCurrentInAmps(dto.getCurrentInAmps());
		vo.setVoltage(dto.getVoltage());
		vo.setCushionTonnage(dto.getCushionTonnage());
		vo.setParallelity(dto.getParallelity());
		vo.setHourlyRate(dto.getHourlyRate());
		vo.setMachineInstrumentWeight(dto.getMachineInstrumentWeight());

		// ============================================================
		// WARRANTY / CALIBRATION DETAILS
		// ============================================================

		vo.setWarrantyStartDate(dto.getWarrantyStartDate());
		vo.setWarrantyEndDate(dto.getWarrantyEndDate());
		vo.setLastCalibratedDate(dto.getLastCalibratedDate());
		vo.setNextDueDate(dto.getNextDueDate());
		vo.setLifeCycleYear(dto.getLifeCycleYear());
		vo.setRange(dto.getRange());
		vo.setErrorAllowed(dto.getErrorAllowed());
		vo.setFrequencyOfCalibration(dto.getFrequencyOfCalibration());
		vo.setInstrumentCost(dto.getInstrumentCost());
		vo.setCalibrationCost(dto.getCalibrationCost());
		vo.setCalibrationAgency(dto.getCalibrationAgency());
		vo.setCertificateNo(dto.getCertificateNo());

		// ============================================================
		// MACHINE DIMENSION / CAPACITY DETAILS
		// ============================================================

		vo.setShutHeightMm(dto.getShutHeightMm());
		vo.setStrokeMm(dto.getStrokeMm());
		vo.setCushion(dto.getCushion());
		vo.setHp(dto.getHp());
		vo.setHcNo(dto.getHcNo());
		vo.setRangeSize(dto.getRangeSize());
		vo.setLeastcount(dto.getLeastcount());
		vo.setGoSize(dto.getGoSize());
		vo.setNoGoSize(dto.getNoGoSize());
		vo.setRamSize(dto.getRamSize());
		vo.setThroatDepth(dto.getThroatDepth());
		vo.setThroatGap(dto.getThroatGap());
		vo.setMaintenanceDate(dto.getMaintenanceDate());

		// =========================================================
		// SPARE DETAILS
		// =========================================================

		vo.getMachineSpareDetailsVO().clear();

		if (dto.getMachineSpareDetailsDTO() != null) {

			for (MachineSpareDetailsDTO detailDTO : dto.getMachineSpareDetailsDTO()) {

				MachineSpareDetailsVO detailVO = new MachineSpareDetailsVO();

				// -------------------------------------------------
				// Spare Item
				// -------------------------------------------------

				if (detailDTO.getSpareId() != null) {

					ItemMasterVO spare = itemMasterRepo.findById(detailDTO.getSpareId())
							.orElseThrow(() -> new ApplicationException("Spare Item Not Found"));

					detailVO.setSpareId(spare);
				}

				// -------------------------------------------------
				// Unit
				// -------------------------------------------------

				if (detailDTO.getUnit() != null) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Spare Unit Not Found"));

					detailVO.setUnit(unit);
				}

				// -------------------------------------------------
				// Spare Fields
				// -------------------------------------------------

				detailVO.setSpareDescription(detailDTO.getSpareDescription());

				detailVO.setQuantity(detailDTO.getQuantity());

				detailVO.setCritical(detailDTO.isCritical());

				detailVO.setModelNo(detailDTO.getModelNo());

				detailVO.setSerialNo(detailDTO.getSerialNo());

				detailVO.setManufacturer(detailDTO.getManufacturer());

				detailVO.setWarrantyTillDate(detailDTO.getWarrantyTillDate());

				detailVO.setCalibrationRequired(detailDTO.getCalibrationRequired());

				detailVO.setLastCalibratedDate(detailDTO.getLastCalibratedDate());

				// -------------------------------------------------
				// Parent
				// -------------------------------------------------

				detailVO.setMachineMasterVO(vo);

				vo.getMachineSpareDetailsVO().add(detailVO);
			}
		}

		// =========================================================
		// HISTORY
		// =========================================================

		vo.getMachineHistoryVO().clear();

		if (dto.getMachineHistoryDTO() != null) {

			for (MachineHistoryDTO detailDTO : dto.getMachineHistoryDTO()) {

				MachineHistoryVO detailVO = new MachineHistoryVO();

				detailVO.setDate(detailDTO.getDate());

				detailVO.setDescription(detailDTO.getDescription());

				detailVO.setChangedDate(detailDTO.getChangedDate());

				detailVO.setCost(detailDTO.getCost());

				detailVO.setPurpose(detailDTO.getPurpose());

				detailVO.setRemarks(detailDTO.getRemarks());

				// -------------------------------------------------
				// Parent
				// -------------------------------------------------

				detailVO.setMachineMasterVO(vo);

				vo.getMachineHistoryVO().add(detailVO);
			}
		}

//    // =========================================================
//    // ATTACHMENTS
//    // =========================================================
//
//    vo.getMachineMasterAttachmentVO().clear();
//
//    if (dto.getMachineMasterAttachmentDTO() != null) {
//
//        for (MachineMasterAttachmentDTO attachmentDTO :
//                dto.getMachineMasterAttachmentDTO()) {
//
//            MachineMasterAttachmentVO attachmentVO =
//                    new MachineMasterAttachmentVO();
//
//            attachmentVO.setName(
//                    attachmentDTO.getName());
//
//            attachmentVO.setFileName(
//                    attachmentDTO.getFileName());
//
//            attachmentVO.setFilePath(
//                    attachmentDTO.getFilePath());
//
//            attachmentVO.setFileSize(
//                    attachmentDTO.getFileSize());
//
//            attachmentVO.setContentType(
//                    attachmentDTO.getContentType());
//
//            attachmentVO.setUploadOn(
//                    attachmentDTO.getUploadOn());
//
//            // -------------------------------------------------
//            // Parent
//            // -------------------------------------------------
//
//            attachmentVO.setMachineMasterVO(vo);
//
//            vo.getMachineMasterAttachmentVO()
//                    .add(attachmentVO);
//        }
//    }
	}

	private MachineMasterResponseDTO machineMasterResponse(MachineMasterVO vo) {

		MachineMasterResponseDTO dto = new MachineMasterResponseDTO();

		dto.setId(vo.getId());

		// =========================================================
		// BRANCH
		// =========================================================

		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());

			dto.setBranch(branchDTO);
		}

		// =========================================================
		// DEPARTMENT
		// =========================================================

		if (vo.getDepartment() != null) {

			DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();

			departmentDTO.setId(vo.getDepartment().getId());
			departmentDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());
			departmentDTO.setDepartmentName(vo.getDepartment().getDepartmentName());

			dto.setDepartment(departmentDTO);
		}

		// =========================================================
		// TYPE
		// =========================================================

		if (vo.getType() != null) {

			ListOfValuesDetailsResponseDTO typeDTO = new ListOfValuesDetailsResponseDTO();

			typeDTO.setId(vo.getType().getId());
			typeDTO.setCode(vo.getType().getValueCode());
			typeDTO.setDescription(vo.getType().getValueDescription());

			dto.setType(typeDTO);
		}

		// =========================================================
		// LOCATION
		// =========================================================

		if (vo.getLocation() != null) {

			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

			locationDTO.setId(vo.getLocation().getId());
//                locationDTO.setLocationId(
//                        vo.getLocation().getLocationId());
			locationDTO.setLocationName(vo.getLocation().getLocationName());

			dto.setLocation(locationDTO);
		}

		// =========================================================
		// MACHINE CATEGORY
		// =========================================================

		if (vo.getMachineInstrumentCategory() != null) {

			ToolCategoryDetailResponseDTO categoryDTO = new ToolCategoryDetailResponseDTO();

			categoryDTO.setId(vo.getMachineInstrumentCategory().getId());
			
			categoryDTO.setCategory(vo.getMachineInstrumentCategory().getCategory());
			
			
			
			dto.setMachineInstrumentCategory(categoryDTO);
		}

		// =========================================================
		// COUNTRY
		// =========================================================

		if (vo.getMadeIn() != null) {

			CountryResponseDTO countryDTO = new CountryResponseDTO();

			countryDTO.setId(vo.getMadeIn().getId());
			countryDTO.setCountryCode(vo.getMadeIn().getCountryCode());
			countryDTO.setCountryName(vo.getMadeIn().getCountryName());

			dto.setMadeIn(countryDTO);
		}

		// =========================================================
		// PURCHASED FROM
		// =========================================================

		if (vo.getPurchasedFrom() != null) {

			CustomerResponse1DTO customerDTO = new CustomerResponse1DTO();

			customerDTO.setId(vo.getPurchasedFrom().getId());

//                customerDTO.setCustomerCode(
//                        vo.getPurchasedFrom().getCustomerCode());

			customerDTO.setCustomerName(vo.getPurchasedFrom().getCustomerName());

			dto.setPurchasedFrom(customerDTO);
		}

		// =========================================================
		// MASTER FIELDS
		// =========================================================

		dto.setMachineInstrumentNo(vo.getMachineInstrumentNo());

		dto.setMachineInstrumentName(vo.getMachineInstrumentName());

		dto.setCalibrationRequired(vo.getCalibrationRequired());

		dto.setProcessNo(vo.getProcessNo());

		dto.setSection(vo.getSection());

		dto.setModel(vo.getModel());

		dto.setSerialNo(vo.getSerialNo());

		dto.setStatus(vo.getStatus());

		dto.setManufacturedBy(vo.getManufacturedBy());

		dto.setModeOfPurchase(vo.getModeOfPurchase());

		dto.setMachineInstrumentIncharge(vo.getMachineInstrumentIncharge());

		dto.setMachineInstrumentUsedFor(vo.getMachineInstrumentUsedFor());

		dto.setPmChecklistNo(vo.getPmChecklistNo());

		dto.setRemarks(vo.getRemarks());

		dto.setMake(vo.getMake());

		dto.setMachineInstrumentImageName(vo.getMachineInstrumentImageName());

		dto.setMachineOrInstrument(vo.getMachineOrInstrument());

		dto.setActive(vo.isActive());

		dto.setOrgId(vo.getOrgId());

		dto.setCreatedBy(vo.getCreatedBy());

		dto.setUpdatedBy(vo.getUpdatedBy());

		dto.setCancel(vo.isCancel());

		dto.setCancelRemarks(vo.getCancelRemarks());

		// =========================================================
		// TECHNICAL INFO
		// =========================================================

		dto.setInstallationDate(vo.getInstallationDate());

		dto.setPowerConsumption(vo.getPowerConsumption());

		dto.setConsumption(vo.getConsumption());

		dto.setPowerProduced(vo.getPowerProduced());

		dto.setTechnicalSpecification(vo.getTechnicalSpecification());

		dto.setCapacity(vo.getCapacity());

		// =========================================================
		// UNIT - ITEM MASTER
		// =========================================================

		if (vo.getUnit() != null) {

			ItemResponse1DTO unitDTO = new ItemResponse1DTO();

			unitDTO.setId(vo.getUnit().getId());

			// Add other ItemResponse1DTO fields here if required
			// Example:
			// unitDTO.setItemCode(vo.getUnit().getItemCode());
			// unitDTO.setItemName(vo.getUnit().getItemName());

			dto.setUnit(unitDTO);
		}

		// =========================================================
		// MACHINE TECHNICAL DETAILS
		// =========================================================

		dto.setBedSizeMm(vo.getBedSizeMm());

		dto.setCurrentInAmps(vo.getCurrentInAmps());

		dto.setVoltage(vo.getVoltage());

		dto.setCushionTonnage(vo.getCushionTonnage());

		dto.setParallelity(vo.getParallelity());

		// =========================================================
		// MACHINE TYPE - LIST OF VALUES
		// =========================================================

		if (vo.getMachineType() != null) {

			ListOfValuesDetailsResponseDTO machineTypeDTO = new ListOfValuesDetailsResponseDTO();

			machineTypeDTO.setId(vo.getMachineType().getId());

			machineTypeDTO.setCode(vo.getMachineType().getValueCode());

			machineTypeDTO.setDescription(vo.getMachineType().getValueDescription());

			dto.setMachineType(machineTypeDTO);
		}

		// =========================================================
		// RATE / WEIGHT
		// =========================================================

		dto.setHourlyRate(vo.getHourlyRate());

		dto.setMachineInstrumentWeight(vo.getMachineInstrumentWeight());

		// =========================================================
		// UOM - UNIT MASTER
		// =========================================================

		if (vo.getUom() != null) {

			UnitMasterResponseDTO uomDTO = new UnitMasterResponseDTO();

			uomDTO.setId(vo.getUom().getId());

			// Add other UnitMasterResponseDTO fields here if required
			// Example:
			// uomDTO.setUnitCode(vo.getUom().getUnitCode());
			// uomDTO.setUnitName(vo.getUom().getUnitName());

			dto.setUom(uomDTO);
		}

		// =========================================================
		// WARRANTY DETAILS
		// =========================================================

		dto.setWarrantyStartDate(vo.getWarrantyStartDate());

		dto.setWarrantyEndDate(vo.getWarrantyEndDate());

		dto.setLastCalibratedDate(vo.getLastCalibratedDate());

		dto.setNextDueDate(vo.getNextDueDate());

		dto.setLifeCycleYear(vo.getLifeCycleYear());

		// =========================================================
		// CALIBRATION DETAILS
		// =========================================================

		dto.setRange(vo.getRange());

		dto.setErrorAllowed(vo.getErrorAllowed());

		dto.setFrequencyOfCalibration(vo.getFrequencyOfCalibration());

		dto.setInstrumentCost(vo.getInstrumentCost());

		dto.setCalibrationCost(vo.getCalibrationCost());

		dto.setCalibrationAgency(vo.getCalibrationAgency());

		dto.setCertificateNo(vo.getCertificateNo());

		// =========================================================
		// MACHINE DIMENSION / TECHNICAL DETAILS
		// =========================================================

		dto.setShutHeightMm(vo.getShutHeightMm());

		dto.setStrokeMm(vo.getStrokeMm());

		dto.setCushion(vo.getCushion());

		dto.setHp(vo.getHp());

		dto.setHcNo(vo.getHcNo());

		dto.setRangeSize(vo.getRangeSize());

		dto.setLeastcount(vo.getLeastcount());

		dto.setGoSize(vo.getGoSize());

		dto.setNoGoSize(vo.getNoGoSize());

		dto.setRamSize(vo.getRamSize());

		dto.setThroatDepth(vo.getThroatDepth());

		dto.setThroatGap(vo.getThroatGap());

		dto.setMaintenanceDate(vo.getMaintenanceDate());

//            dto.setScreenName(
//                    vo.getScreenName());
//
//            dto.setScreenCode(
//                    vo.getScreenCode());

		// =========================================================
		// TECHNICAL INFORMATION RESPONSE
		// =========================================================

		// =========================================================
		// SPARE DETAILS RESPONSE
		// =========================================================

		List<MachineSpareDetailsResponseDTO> spareList = new ArrayList<>();

		if (vo.getMachineSpareDetailsVO() != null) {

			for (MachineSpareDetailsVO detailVO : vo.getMachineSpareDetailsVO()) {

				MachineSpareDetailsResponseDTO detailDTO = new MachineSpareDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());

				// -------------------------------------------------
				// Spare Item
				// -------------------------------------------------

				if (detailVO.getSpareId() != null) {

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getSpareId().getId());

					itemDTO.setItemCode(detailVO.getSpareId().getItemCode());

					itemDTO.setItemDescription(detailVO.getSpareId().getItemDescription());

					detailDTO.setSpareId(itemDTO);
				}

				detailDTO.setSpareDescription(detailVO.getSpareDescription());

				// -------------------------------------------------
				// Unit
				// -------------------------------------------------

				if (detailVO.getUnit() != null) {

					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

					unitDTO.setId(detailVO.getUnit().getId());

					unitDTO.setUnitId(detailVO.getUnit().getUnitId());

					unitDTO.setUnitDescription(detailVO.getUnit().getDescription());

					detailDTO.setUnit(unitDTO);
				}

				detailDTO.setQuantity(detailVO.getQuantity());

				detailDTO.setCritical(detailVO.isCritical());

				detailDTO.setModelNo(detailVO.getModelNo());

				detailDTO.setSerialNo(detailVO.getSerialNo());

				detailDTO.setManufacturer(detailVO.getManufacturer());

				detailDTO.setWarrantyTillDate(detailVO.getWarrantyTillDate());

				detailDTO.setCalibrationRequired(detailVO.getCalibrationRequired());

				detailDTO.setLastCalibratedDate(detailVO.getLastCalibratedDate());

				spareList.add(detailDTO);
			}
		}

		dto.setMachineSpareDetailsResponseDTO(spareList);

		// =========================================================
		// HISTORY RESPONSE
		// =========================================================

		List<MachineHistoryResponseDTO> historyList = new ArrayList<>();

		if (vo.getMachineHistoryVO() != null) {

			for (MachineHistoryVO detailVO : vo.getMachineHistoryVO()) {

				MachineHistoryResponseDTO detailDTO = new MachineHistoryResponseDTO();

				detailDTO.setId(detailVO.getId());

				detailDTO.setDate(detailVO.getDate());

				detailDTO.setDescription(detailVO.getDescription());

				detailDTO.setChangedDate(detailVO.getChangedDate());

				detailDTO.setCost(detailVO.getCost());

				detailDTO.setPurpose(detailVO.getPurpose());

				detailDTO.setRemarks(detailVO.getRemarks());

				historyList.add(detailDTO);
			}
		}

		dto.setMachineHistoryResponseDTO(historyList);

		// =========================================================
		// ATTACHMENT RESPONSE
		// =========================================================

		List<MachineMasterAttachmentResponseDTO> attachmentList = new ArrayList<>();

		if (vo.getMachineMasterAttachmentVO() != null) {

			for (MachineMasterAttachmentVO attachmentVO : vo.getMachineMasterAttachmentVO()) {

				MachineMasterAttachmentResponseDTO attachmentDTO = new MachineMasterAttachmentResponseDTO();

				attachmentDTO.setId(attachmentVO.getId());

				attachmentDTO.setName(attachmentVO.getName());

				attachmentDTO.setFileName(attachmentVO.getFileName());

				attachmentDTO.setFilePath(attachmentVO.getFilePath());

				attachmentDTO.setFileSize(attachmentVO.getFileSize());

				attachmentDTO.setContentType(attachmentVO.getContentType());

				attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

				attachmentList.add(attachmentDTO);
			}
		}

		dto.setMachineMasterAttachmentResponseDTO(attachmentList);

		return dto;
	}

	@Override
	public MachineMasterResponseDTO getMachineMasterById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		MachineMasterVO machineMasterVO = machineMasterRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Machine Master Not Found"));

		return machineMasterResponse(machineMasterVO);
	}

	@Override
	public List<MachineMasterResponseDTO> getMachineMasterByOrgId(Long orgId, Long branch) throws ApplicationException {

		BranchVO branchVO = branchRepo.findById(branch).orElseThrow(() -> new ApplicationException("Branch Not Found"));

		List<MachineMasterVO> machineMasterList = machineMasterRepo.findByOrgIdAndBranch(orgId, branchVO);

		if (machineMasterList == null || machineMasterList.isEmpty()) {
			throw new ApplicationException("No Machine Master Details Found");
		}

		List<MachineMasterResponseDTO> responseList = new ArrayList<>();

		for (MachineMasterVO machineMasterVO : machineMasterList) {
			responseList.add(machineMasterResponse(machineMasterVO));
		}

		return responseList;
	}

	// machine/instrumentcategory

	@Override
	public Map<String, Object> getToolCategoryforMachineMaster( Long orgId)
			throws ApplicationException {

		List<Object[]> result = machineMasterRepo.getToolCategoryforMachineMaster( orgId);

		Map<String, Object> response = new HashMap<>();

		response.put("toolCategoryList", getToolCategoryDetails(result));

		return response;
	}

	private List<Map<String, Object>> getToolCategoryDetails(List<Object[]> result) {

		List<Map<String, Object>> toolCategoryList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> toolCategory = new HashMap<>();

			toolCategory.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			toolCategory.put("category", obj[1] != null ? obj[1].toString() : null);

			toolCategoryList.add(toolCategory);
		}

		return toolCategoryList;
	}

//TOOL CATEGORY

	@Override
	@Transactional
	public Map<String, Object> createUpdateToolCategory(ToolCategoryDTO toolCategoryDTO) throws ApplicationException {

		ToolCategoryVO toolCategoryVO;
		String message;

		// ============================================================
		// CREATE / UPDATE HEADER
		// ============================================================

		if (ObjectUtils.isNotEmpty(toolCategoryDTO.getId())) {

			toolCategoryVO = toolCategoryRepo.findById(toolCategoryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Tool Category Not Found"));

			toolCategoryVO.setUpdatedBy(toolCategoryDTO.getCreatedBy());

			message = "Tool Category Updated Successfully";

		} else {

			toolCategoryVO = new ToolCategoryVO();

			toolCategoryVO.setCreatedBy(toolCategoryDTO.getCreatedBy());
			toolCategoryVO.setUpdatedBy(toolCategoryDTO.getCreatedBy());

			message = "Tool Category Created Successfully";
		}

		// ============================================================
		// HEADER MAPPING
		// ============================================================

		createUpdateToolCategoryVOByDTO(toolCategoryDTO, toolCategoryVO);

		// ============================================================
		// CHILD MAPPING
		// ============================================================

		if (toolCategoryDTO.getToolCategoryDetailDTO() != null) {

			List<ToolCategoryDetailVO> detailList = new ArrayList<>();

			for (ToolCategoryDetailDTO detailDTO : toolCategoryDTO.getToolCategoryDetailDTO()) {

				ToolCategoryDetailVO detailVO;

				// ----------------------------------------------------
				// Existing Detail
				// ----------------------------------------------------

				if (ObjectUtils.isNotEmpty(detailDTO.getId())) {

					detailVO = toolCategoryDetailRepo.findById(detailDTO.getId())
							.orElseThrow(() -> new ApplicationException("Tool Category Detail Not Found"));

				} else {

					// ------------------------------------------------
					// New Detail
					// ------------------------------------------------

					detailVO = new ToolCategoryDetailVO();
				}

				// ----------------------------------------------------
				// Detail Mapping
				// ----------------------------------------------------

				detailVO.setCategory(detailDTO.getCategory());

				// Parent Mapping
				detailVO.setToolCategoryVO(toolCategoryVO);

				detailList.add(detailVO);
			}

			// Set child list to parent
			toolCategoryVO.setToolCategoryDetailVO(detailList);
		}

		// ============================================================
		// SAVE
		// ============================================================

		toolCategoryVO = toolCategoryRepo.save(toolCategoryVO);

		// ============================================================
		// RESPONSE
		// ============================================================

		ToolCategoryResponseDTO responseDTO = buildToolCategoryResponse(toolCategoryVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("toolCategoryVO", responseDTO);

		return response;
	}

	private void createUpdateToolCategoryVOByDTO(ToolCategoryDTO dto, ToolCategoryVO vo) throws ApplicationException {

		vo.setApllicableFor(dto.getApllicableFor());

		vo.setActive(dto.isActive());

		vo.setOrgId(dto.getOrgId());

		vo.setCreatedBy(dto.getCreatedBy());

		vo.setCancelRemarks(dto.getCancelRemarks());

		vo.setScreenName("TOOLCATEGORY");

		vo.setScreenCode("TC");
	}

	private ToolCategoryResponseDTO buildToolCategoryResponse(ToolCategoryVO toolCategoryVO) {

		ToolCategoryResponseDTO responseDTO = new ToolCategoryResponseDTO();

		responseDTO.setId(toolCategoryVO.getId());

		responseDTO.setApllicableFor(toolCategoryVO.getApllicableFor());

		responseDTO.setActive(toolCategoryVO.isActive());

		responseDTO.setOrgId(toolCategoryVO.getOrgId());

		responseDTO.setCreatedBy(toolCategoryVO.getCreatedBy());

		responseDTO.setCancelRemarks(toolCategoryVO.getCancelRemarks());

		// ============================================================
		// CHILD DETAILS
		// ============================================================

		if (toolCategoryVO.getToolCategoryDetailVO() != null) {

			List<ToolCategoryDetailResponseDTO> detailResponseList = new ArrayList<>();

			for (ToolCategoryDetailVO detailVO : toolCategoryVO.getToolCategoryDetailVO()) {

				ToolCategoryDetailResponseDTO detailDTO = new ToolCategoryDetailResponseDTO();

				detailDTO.setId(detailVO.getId());

				detailDTO.setCategory(detailVO.getCategory());

				detailResponseList.add(detailDTO);
			}

			responseDTO.setToolCategoryDetailResponseDTO(detailResponseList);
		}

		return responseDTO;
	}

	@Override
	public ToolCategoryResponseDTO getToolCategoryById(Long id) throws ApplicationException {

		ToolCategoryVO toolCategoryVO = toolCategoryRepo.findById(id).orElse(null);

		if (toolCategoryVO == null) {
			throw new ApplicationException("Tool Category Not Found");
		}

		return buildToolCategoryResponse(toolCategoryVO);
	}

	@Override
	public List<ToolCategoryResponseDTO> getToolCategoryByOrgId(Long orgId) throws ApplicationException {

		List<ToolCategoryVO> toolCategoryList = toolCategoryRepo.findByOrgId(orgId);

		if (toolCategoryList == null || toolCategoryList.isEmpty()) {
			throw new ApplicationException("Tool Category Not Found");
		}

		List<ToolCategoryResponseDTO> responseList = new ArrayList<>();

		for (ToolCategoryVO toolCategoryVO : toolCategoryList) {

			responseList.add(buildToolCategoryResponse(toolCategoryVO));
		}

		return responseList;
	}

	
}
