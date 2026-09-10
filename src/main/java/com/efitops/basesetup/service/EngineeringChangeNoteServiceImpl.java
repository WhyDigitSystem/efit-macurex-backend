package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
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

import com.efitops.basesetup.ResponseDTO.ChangeRequiredResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocumentsChangesResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocumentsResponseDTO;
import com.efitops.basesetup.ResponseDTO.EngineeringChangeNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.InspectionTestingResponseDTO;
import com.efitops.basesetup.ResponseDTO.PdfAttachmentBomResponseDTO;
import com.efitops.basesetup.ResponseDTO.PdfAttachmentDrawingResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProcessChangesResponseDTO;
import com.efitops.basesetup.ResponseDTO.RemarksResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ChangeRequiredDTO;
import com.efitops.basesetup.dto.DocumentsChangesDTO;
import com.efitops.basesetup.dto.DocumentsDTO;
import com.efitops.basesetup.dto.EngineeringChangeNoteDTO;
import com.efitops.basesetup.dto.InspectionTestingDTO;
import com.efitops.basesetup.dto.ProcessChangesDTO;
import com.efitops.basesetup.dto.RemarksDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.ChangeRequiredVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DocumentsChangesVO;
import com.efitops.basesetup.entity.DocumentsVO;
import com.efitops.basesetup.entity.EngineeringChangeNoteVO;
import com.efitops.basesetup.entity.InspectionTestingVO;
import com.efitops.basesetup.entity.PdfAttachmentBomVO;
import com.efitops.basesetup.entity.PdfAttachmentDrawingVO;
import com.efitops.basesetup.entity.ProcessChangesVO;
import com.efitops.basesetup.entity.RemarksVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.ChangeRequiredRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.DocumentsChangesRepo;
import com.efitops.basesetup.repository.DocumentsRepo;
import com.efitops.basesetup.repository.EngineeringChangeNoteRepo;
import com.efitops.basesetup.repository.InspectionTestingRepo;
import com.efitops.basesetup.repository.PdfAttachmentBomRepo;
import com.efitops.basesetup.repository.PdfAttachmentDrawingRepo;
import com.efitops.basesetup.repository.ProcessChangesRepo;
import com.efitops.basesetup.repository.RemarksRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EngineeringChangeNoteServiceImpl implements EngineeringChangeNoteService {

	@Autowired
	private EngineeringChangeNoteRepo engineeringChangeNoteRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	private RemarksRepo remarksRepo;

	@Autowired
	private ChangeRequiredRepo changeRequiredRepo;

	@Autowired
	private DocumentsChangesRepo documentsChangesRepo;

	@Autowired
	private DocumentsRepo documentsRepo;

	@Autowired
	private ProcessChangesRepo processChangesRepo;

	@Autowired
	private InspectionTestingRepo inspectionTestingRepo;

	@Autowired
	private PdfAttachmentDrawingRepo pdfAttachmentDrawingRepo;

	@Autowired
	private PdfAttachmentBomRepo pdfAttachmentBomRepo;

	@Value("${engineering.change.note.upload.path}")
	private String engineeringChangeNoteUploadPath;

	@Override
	public EngineeringChangeNoteResponseDTO getEngineeringChangeNoteById(Long id) throws ApplicationException {
		EngineeringChangeNoteVO engineeringChangeNoteVO = engineeringChangeNoteRepo.getEngineeringChangeNoteById(id);

		if (engineeringChangeNoteVO == null) {
			throw new ApplicationException("Engineering Change Note Not Found");
		}

		return buildEngineeringChangeNoteResponse(engineeringChangeNoteVO);
	}

	@Override
	public List<EngineeringChangeNoteResponseDTO> getEngineeringChangeNoteByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<EngineeringChangeNoteVO> engineeringChangeNoteList = engineeringChangeNoteRepo
				.getEngineeringChangeNoteByOrgId(orgId, branch);

		if (engineeringChangeNoteList == null || engineeringChangeNoteList.isEmpty()) {
			throw new ApplicationException("Engineering Change Note Not Found");
		}

		List<EngineeringChangeNoteResponseDTO> responseList = new ArrayList<>();

		for (EngineeringChangeNoteVO engineeringChangeNoteVO : engineeringChangeNoteList) {
			responseList.add(buildEngineeringChangeNoteResponse(engineeringChangeNoteVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateEngineeringChangeNote(EngineeringChangeNoteDTO engineeringChangeNoteDTO,
			MultipartFile[] drawingFiles, MultipartFile[] bomFiles) throws ApplicationException {
		EngineeringChangeNoteVO engineeringChangeNoteVO;
		String message;

		if (ObjectUtils.isNotEmpty(engineeringChangeNoteDTO.getId())) {
			engineeringChangeNoteVO = engineeringChangeNoteRepo.findById(engineeringChangeNoteDTO.getId())
					.orElseThrow(() -> new ApplicationException("Engineering Change Note Not Found"));
			engineeringChangeNoteVO.setUpdatedBy(engineeringChangeNoteDTO.getCreatedBy());
			message = "Engineering Change Note Updated Successfully";
		} else {
			engineeringChangeNoteVO = new EngineeringChangeNoteVO();
			String screenCode = "ECN";
			String docId = engineeringChangeNoteRepo.getEngineeringChangeNoteDocId(engineeringChangeNoteDTO.getOrgId(),
					engineeringChangeNoteDTO.getFinancialYear(), screenCode);
			engineeringChangeNoteVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(engineeringChangeNoteDTO.getOrgId(),
							engineeringChangeNoteDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			engineeringChangeNoteVO.setCreatedBy(engineeringChangeNoteDTO.getCreatedBy());
			engineeringChangeNoteVO.setUpdatedBy(engineeringChangeNoteDTO.getCreatedBy());
			message = "Engineering Change Note Created Successfully";
		}

		setEngineeringChangeNoteValues(engineeringChangeNoteDTO, engineeringChangeNoteVO);

		engineeringChangeNoteVO = engineeringChangeNoteRepo.save(engineeringChangeNoteVO);

		saveDrawingAttachments(drawingFiles, engineeringChangeNoteVO);
		saveBomAttachments(bomFiles, engineeringChangeNoteVO);

		EngineeringChangeNoteResponseDTO engineeringChangeNoteResponse = buildEngineeringChangeNoteResponse(
				engineeringChangeNoteVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("engineeringChangeNoteVO", engineeringChangeNoteResponse);

		return response;
	}

	private void setEngineeringChangeNoteValues(EngineeringChangeNoteDTO dto, EngineeringChangeNoteVO vo)
			throws ApplicationException {
		vo.setFromDepartment(dto.getFromDepartment());
		vo.setProductName(dto.getProductName());
		vo.setCustomerName(dto.getCustomerName());
		vo.setProductNo(dto.getProductNo());
		vo.setCustomerPartNo(dto.getCustomerPartNo());

		// Part Details
		vo.setPartNo(dto.getPartNo());
		vo.setPartDescription(dto.getPartDescription());

		// Reason for Change
		vo.setCustomerRequirements(dto.getCustomerRequirements());
		vo.setValueEngineering(dto.getValueEngineering());
		vo.setQualityRequirements(dto.getQualityRequirements());
		vo.setPurchaseRequirements(dto.getPurchaseRequirements());
		vo.setInCaseOthersPleaseMentionDetails(dto.getInCaseOthersPleaseMentionDetails());

		// Store and Logistics
		vo.setDoesChangeChangeThePart(dto.getDoesChangeChangeThePart());
		vo.setIfYesAction(dto.getIfYesAction());
		vo.setPartToBeReworked(dto.getPartToBeReworked());
		vo.setPartToBeScrapped(dto.getPartToBeScrapped());

		// What is the stock at
		vo.setStores(dto.getStores());
		vo.setWIP(dto.getWIP());
		vo.setSupplierIncludePo(dto.getSupplierIncludePo());
		vo.setCostOfStockPlusWIP(dto.getCostOfStockPlusWIP());

		// Document Changes Required
		vo.setControlPlanReviewedAndUpdated(dto.getControlPlanReviewedAndUpdated());
		vo.setAnyChangeInWorkInstructionSOP(dto.getAnyChangeInWorkInstructionSOP());

		// Stock and Design / Process Validation
		vo.setExistingStockCanBeUsedTillStockIsExhausted(dto.getExistingStockCanBeUsedTillStockIsExhausted());
		vo.setIfNoCostOfObselecence(dto.getIfNoCostOfObselecence());
		vo.setProcessValidationRequired(dto.getProcessValidationRequired());
		vo.setValidationDetail(dto.getValidationDetail());
		vo.setValidationReportToBeAttached(dto.getValidationReportToBeAttached());
		vo.setIsThereanyBillOfMaterialChangeRequired(dto.getIsThereanyBillOfMaterialChangeRequired());
		vo.setIFYESPleasemention(dto.getIFYESPleasemention());
		vo.setExpectedDateOfCompletion(dto.getExpectedDateOfCompletion());

		// Conclusion
		vo.setChangesAccepted(dto.getChangesAccepted());
		vo.setChangesRejected(dto.getChangesRejected());
		vo.setChangesInvolvingCost(dto.getChangesInvolvingCost());
		vo.setChangesCanBeImplementedBy(dto.getChangesCanBeImplementedBy());
		vo.setConformationOnImplementationByQAD(dto.getConformationOnImplementationByQAD());
		vo.setCustomerApproval(dto.getCustomerApproval());

		// CFT Approval/Concurrence
		vo.setApprovalByTDCMgr(dto.getApprovalByTDCMgr());
		vo.setAcceptedByQADMgr(dto.getAcceptedByQADMgr());
		vo.setNonAcceptedQADReason(dto.getNonAcceptedQADReason());
		vo.setAcceptedByPURMgr(dto.getAcceptedByPURMgr());
		vo.setNonAcceptedPURReason(dto.getNonAcceptedPURReason());
		vo.setAcceptedbyPRODMgr(dto.getAcceptedbyPRODMgr());
		vo.setNonAcceptedPRODReason(dto.getNonAcceptedPRODReason());
		vo.setAcceptedByStoresMgr(dto.getAcceptedByStoresMgr());
		vo.setNonAcceptedStoreReason(dto.getNonAcceptedStoreReason());

		// Common Fields
		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setCancelRemarks(dto.getCancelRemarks());

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {

			List<RemarksVO> oldRemarks = remarksRepo.findByEngineeringChangeNoteVO(vo);
			remarksRepo.deleteAll(oldRemarks);

			List<ChangeRequiredVO> oldChangeRequired = changeRequiredRepo.findByEngineeringChangeNoteVO(vo);
			changeRequiredRepo.deleteAll(oldChangeRequired);

			List<DocumentsChangesVO> oldDocumentsChanges = documentsChangesRepo.findByEngineeringChangeNoteVO(vo);
			documentsChangesRepo.deleteAll(oldDocumentsChanges);

			List<DocumentsVO> oldDocuments = documentsRepo.findByEngineeringChangeNoteVO(vo);
			documentsRepo.deleteAll(oldDocuments);

			List<ProcessChangesVO> oldProcessChanges = processChangesRepo.findByEngineeringChangeNoteVO(vo);
			processChangesRepo.deleteAll(oldProcessChanges);

			List<InspectionTestingVO> oldInspectionTesting = inspectionTestingRepo.findByEngineeringChangeNoteVO(vo);
			inspectionTestingRepo.deleteAll(oldInspectionTesting);
		}

		List<RemarksVO> remarksList = new ArrayList<>();
		if (dto.getRemarksDTO() != null) {
			for (RemarksDTO remarkDTO : dto.getRemarksDTO()) {
				RemarksVO remarksVO = new RemarksVO();
				remarksVO.setIndicate1(remarkDTO.getIndicate1());
				remarksVO.setIndicate2(remarkDTO.getIndicate2());
				remarksVO.setEngineeringChangeNoteVO(vo);
				remarksList.add(remarksVO);
			}
		}
		vo.setRemarksVO(remarksList);

		// Set Change Required
		List<ChangeRequiredVO> changeRequiredList = new ArrayList<>();
		if (dto.getChangeRequiredDTO() != null) {
			for (ChangeRequiredDTO changeDTO : dto.getChangeRequiredDTO()) {
				ChangeRequiredVO changeRequiredVO = new ChangeRequiredVO();
				changeRequiredVO.setFixtures(changeDTO.getFixtures());
				changeRequiredVO.setAnyChanges(changeDTO.getAnyChanges());
				changeRequiredVO.setEstimatedCost(changeDTO.getEstimatedCost());
				changeRequiredVO.setLeadTime(changeDTO.getLeadTime());
				changeRequiredVO.setEngineeringChangeNoteVO(vo);
				changeRequiredList.add(changeRequiredVO);
			}
		}
		vo.setChangeRequiredVO(changeRequiredList);

		// Set Documents Changes
		List<DocumentsChangesVO> documentsChangesList = new ArrayList<>();
		if (dto.getDocumentsChangesDTO() != null) {
			for (DocumentsChangesDTO docChangeDTO : dto.getDocumentsChangesDTO()) {
				DocumentsChangesVO documentsChangesVO = new DocumentsChangesVO();
				documentsChangesVO.setSopNo(docChangeDTO.getSopNo());
				documentsChangesVO.setStationNo(docChangeDTO.getStationNo());
				documentsChangesVO.setCompletionDate(docChangeDTO.getCompletionDate());
				documentsChangesVO.setRemarks(docChangeDTO.getRemarks());
				documentsChangesVO.setEngineeringChangeNoteVO(vo);
				documentsChangesList.add(documentsChangesVO);
			}
		}
		vo.setDocumentsChangesVO(documentsChangesList);

		// Set Documents
		List<DocumentsVO> documentsList = new ArrayList<>();
		if (dto.getDocumentsDTO() != null) {
			for (DocumentsDTO docDTO : dto.getDocumentsDTO()) {
				DocumentsVO documentsVO = new DocumentsVO();
				documentsVO.setDrawing(docDTO.getDrawing());
				documentsVO.setPartNo(docDTO.getPartNo());
				documentsVO.setIssue(docDTO.getIssue());
				documentsVO.setRemarks(docDTO.getRemarks());
				documentsVO.setEngineeringChangeNoteVO(vo);
				documentsList.add(documentsVO);
			}
		}
		vo.setDocumentsVO(documentsList);

		// Set Process Changes
		List<ProcessChangesVO> processChangesList = new ArrayList<>();
		if (dto.getProcessChangesDTO() != null) {
			for (ProcessChangesDTO processDTO : dto.getProcessChangesDTO()) {
				ProcessChangesVO processChangesVO = new ProcessChangesVO();
				processChangesVO.setProcessChange(processDTO.getProcessChange());
				processChangesVO.setLayOut(processDTO.getLayOut());
				processChangesVO.setActions(processDTO.getActions());
				processChangesVO.setEstimatedCost(processDTO.getEstimatedCost());
				processChangesVO.setLeadTime(processDTO.getLeadTime());
				processChangesVO.setEngineeringChangeNoteVO(vo);
				processChangesList.add(processChangesVO);
			}
		}
		vo.setProcessChangesVO(processChangesList);

		// Set Inspection Testing
		List<InspectionTestingVO> inspectionTestingList = new ArrayList<>();
		if (dto.getInspectionTestingDTO() != null) {
			for (InspectionTestingDTO inspectionDTO : dto.getInspectionTestingDTO()) {
				InspectionTestingVO inspectionTestingVO = new InspectionTestingVO();
				inspectionTestingVO.setNewGauge(inspectionDTO.getNewGauge());
				inspectionTestingVO.setEstimatedCost(inspectionDTO.getEstimatedCost());
				inspectionTestingVO.setLeadTime(inspectionDTO.getLeadTime());
				inspectionTestingVO.setEngineeringChangeNoteVO(vo);
				inspectionTestingList.add(inspectionTestingVO);
			}
		}
		vo.setInspectionTestingVO(inspectionTestingList);
	}

	private EngineeringChangeNoteResponseDTO buildEngineeringChangeNoteResponse(EngineeringChangeNoteVO vo) {
		EngineeringChangeNoteResponseDTO responseDTO = new EngineeringChangeNoteResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setFromDepartment(vo.getFromDepartment());
		responseDTO.setProductName(vo.getProductName());
		responseDTO.setCustomerName(vo.getCustomerName());
		responseDTO.setProductNo(vo.getProductNo());
		responseDTO.setCustomerPartNo(vo.getCustomerPartNo());

		// Part Details
		responseDTO.setPartNo(vo.getPartNo());
		responseDTO.setPartDescription(vo.getPartDescription());

		// Reason for Change
		responseDTO.setCustomerRequirements(vo.getCustomerRequirements());
		responseDTO.setValueEngineering(vo.getValueEngineering());
		responseDTO.setQualityRequirements(vo.getQualityRequirements());
		responseDTO.setPurchaseRequirements(vo.getPurchaseRequirements());
		responseDTO.setInCaseOthersPleaseMentionDetails(vo.getInCaseOthersPleaseMentionDetails());

		// Store and Logistics
		responseDTO.setDoesChangeChangeThePart(vo.getDoesChangeChangeThePart());
		responseDTO.setIfYesAction(vo.getIfYesAction());
		responseDTO.setPartToBeReworked(vo.getPartToBeReworked());
		responseDTO.setPartToBeScrapped(vo.getPartToBeScrapped());

		// What is the stock at
		responseDTO.setStores(vo.getStores());
		responseDTO.setWIP(vo.getWIP());
		responseDTO.setSupplierIncludePo(vo.getSupplierIncludePo());
		responseDTO.setCostOfStockPlusWIP(vo.getCostOfStockPlusWIP());

		// Document Changes Required
		responseDTO.setControlPlanReviewedAndUpdated(vo.getControlPlanReviewedAndUpdated());
		responseDTO.setAnyChangeInWorkInstructionSOP(vo.getAnyChangeInWorkInstructionSOP());

		// Stock and Design / Process Validation
		responseDTO.setExistingStockCanBeUsedTillStockIsExhausted(vo.getExistingStockCanBeUsedTillStockIsExhausted());
		responseDTO.setIfNoCostOfObselecence(vo.getIfNoCostOfObselecence());
		responseDTO.setProcessValidationRequired(vo.getProcessValidationRequired());
		responseDTO.setValidationDetail(vo.getValidationDetail());
		responseDTO.setValidationReportToBeAttached(vo.getValidationReportToBeAttached());
		responseDTO.setIsThereanyBillOfMaterialChangeRequired(vo.getIsThereanyBillOfMaterialChangeRequired());
		responseDTO.setIFYESPleasemention(vo.getIFYESPleasemention());
		responseDTO.setExpectedDateOfCompletion(vo.getExpectedDateOfCompletion());

		// Conclusion
		responseDTO.setChangesAccepted(vo.getChangesAccepted());
		responseDTO.setChangesRejected(vo.getChangesRejected());
		responseDTO.setChangesInvolvingCost(vo.getChangesInvolvingCost());
		responseDTO.setChangesCanBeImplementedBy(vo.getChangesCanBeImplementedBy());
		responseDTO.setConformationOnImplementationByQAD(vo.getConformationOnImplementationByQAD());
		responseDTO.setCustomerApproval(vo.getCustomerApproval());

		// CFT Approval/Concurrence
		responseDTO.setApprovalByTDCMgr(vo.getApprovalByTDCMgr());
		responseDTO.setAcceptedByQADMgr(vo.getAcceptedByQADMgr());
		responseDTO.setNonAcceptedQADReason(vo.getNonAcceptedQADReason());
		responseDTO.setAcceptedByPURMgr(vo.getAcceptedByPURMgr());
		responseDTO.setNonAcceptedPURReason(vo.getNonAcceptedPURReason());
		responseDTO.setAcceptedbyPRODMgr(vo.getAcceptedbyPRODMgr());
		responseDTO.setNonAcceptedPRODReason(vo.getNonAcceptedPRODReason());
		responseDTO.setAcceptedByStoresMgr(vo.getAcceptedByStoresMgr());
		responseDTO.setNonAcceptedStoreReason(vo.getNonAcceptedStoreReason());

		// Common Fields
		responseDTO.setActiveStatus(vo.getActiveStatus());
		responseDTO.setCancelStatus(vo.getCancelStatus());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		// Set Remarks
		List<RemarksResponseDTO> remarksResponseList = new ArrayList<>();
		if (vo.getRemarksVO() != null && !vo.getRemarksVO().isEmpty()) {
			for (RemarksVO remarksVO : vo.getRemarksVO()) {
				RemarksResponseDTO remarksDTO = new RemarksResponseDTO();
				remarksDTO.setId(remarksVO.getId());
				remarksDTO.setIndicate1(remarksVO.getIndicate1());
				remarksDTO.setIndicate2(remarksVO.getIndicate2());
				remarksResponseList.add(remarksDTO);
			}
		}
		responseDTO.setRemarksResponseDTO(remarksResponseList);

		// Set Change Required
		List<ChangeRequiredResponseDTO> changeRequiredResponseList = new ArrayList<>();
		if (vo.getChangeRequiredVO() != null && !vo.getChangeRequiredVO().isEmpty()) {
			for (ChangeRequiredVO changeVO : vo.getChangeRequiredVO()) {
				ChangeRequiredResponseDTO changeDTO = new ChangeRequiredResponseDTO();
				changeDTO.setId(changeVO.getId());
				changeDTO.setFixtures(changeVO.getFixtures());
				changeDTO.setAnyChanges(changeVO.getAnyChanges());
				changeDTO.setEstimatedCost(changeVO.getEstimatedCost());
				changeDTO.setLeadTime(changeVO.getLeadTime());
				changeRequiredResponseList.add(changeDTO);
			}
		}
		responseDTO.setChangeRequiredResponseDTO(changeRequiredResponseList);

		// Set Documents Changes
		List<DocumentsChangesResponseDTO> documentsChangesResponseList = new ArrayList<>();
		if (vo.getDocumentsChangesVO() != null && !vo.getDocumentsChangesVO().isEmpty()) {
			for (DocumentsChangesVO docVO : vo.getDocumentsChangesVO()) {
				DocumentsChangesResponseDTO docDTO = new DocumentsChangesResponseDTO();
				docDTO.setId(docVO.getId());
				docDTO.setSopNo(docVO.getSopNo());
				docDTO.setStationNo(docVO.getStationNo());
				docDTO.setCompletionDate(docVO.getCompletionDate());
				docDTO.setRemarks(docVO.getRemarks());
				documentsChangesResponseList.add(docDTO);
			}
		}
		responseDTO.setDocumentsChangesResponseDTO(documentsChangesResponseList);

		// Set Documents
		List<DocumentsResponseDTO> documentsResponseList = new ArrayList<>();
		if (vo.getDocumentsVO() != null && !vo.getDocumentsVO().isEmpty()) {
			for (DocumentsVO docVO : vo.getDocumentsVO()) {
				DocumentsResponseDTO docDTO = new DocumentsResponseDTO();
				docDTO.setId(docVO.getId());
				docDTO.setDrawing(docVO.getDrawing());
				docDTO.setPartNo(docVO.getPartNo());
				docDTO.setIssue(docVO.getIssue());
				docDTO.setRemarks(docVO.getRemarks());
				documentsResponseList.add(docDTO);
			}
		}
		responseDTO.setDocumentsResponseDTO(documentsResponseList);

		// Set Process Changes
		List<ProcessChangesResponseDTO> processChangesResponseList = new ArrayList<>();
		if (vo.getProcessChangesVO() != null && !vo.getProcessChangesVO().isEmpty()) {
			for (ProcessChangesVO processVO : vo.getProcessChangesVO()) {
				ProcessChangesResponseDTO processDTO = new ProcessChangesResponseDTO();
				processDTO.setId(processVO.getId());
				processDTO.setProcessChange(processVO.getProcessChange());
				processDTO.setLayOut(processVO.getLayOut());
				processDTO.setActions(processVO.getActions());
				processDTO.setEstimatedCost(processVO.getEstimatedCost());
				processDTO.setLeadTime(processVO.getLeadTime());
				processChangesResponseList.add(processDTO);
			}
		}
		responseDTO.setProcessChangesResponseDTO(processChangesResponseList);

		// Set Inspection Testing
		List<InspectionTestingResponseDTO> inspectionTestingResponseList = new ArrayList<>();
		if (vo.getInspectionTestingVO() != null && !vo.getInspectionTestingVO().isEmpty()) {
			for (InspectionTestingVO inspectionVO : vo.getInspectionTestingVO()) {
				InspectionTestingResponseDTO inspectionDTO = new InspectionTestingResponseDTO();
				inspectionDTO.setId(inspectionVO.getId());
				inspectionDTO.setNewGauge(inspectionVO.getNewGauge());
				inspectionDTO.setEstimatedCost(inspectionVO.getEstimatedCost());
				inspectionDTO.setLeadTime(inspectionVO.getLeadTime());
				inspectionTestingResponseList.add(inspectionDTO);
			}
		}
		responseDTO.setInspectionTestingResponseDTO(inspectionTestingResponseList);

		// Set PDF Attachment Drawings
		List<PdfAttachmentDrawingResponseDTO> drawingResponseList = new ArrayList<>();
		if (vo.getPdfAttachmentDrawingVO() != null && !vo.getPdfAttachmentDrawingVO().isEmpty()) {
			for (PdfAttachmentDrawingVO drawingVO : vo.getPdfAttachmentDrawingVO()) {
				PdfAttachmentDrawingResponseDTO drawingDTO = new PdfAttachmentDrawingResponseDTO();
				drawingDTO.setId(drawingVO.getId());
				drawingDTO.setName(drawingVO.getName());
				drawingDTO.setFileName(drawingVO.getFileName());
				drawingDTO.setFilePath(drawingVO.getFilePath());
				drawingDTO.setFileSize(drawingVO.getFileSize());
				drawingDTO.setContentType(drawingVO.getContentType());
				drawingDTO.setUploadOn(drawingVO.getUploadOn());
				drawingResponseList.add(drawingDTO);
			}
		}
		responseDTO.setPdfAttachmentDrawingResponseDTO(drawingResponseList);

		// Set PDF Attachment BOMs
		List<PdfAttachmentBomResponseDTO> bomResponseList = new ArrayList<>();
		if (vo.getPdfAttachmentBomVO() != null && !vo.getPdfAttachmentBomVO().isEmpty()) {
			for (PdfAttachmentBomVO bomVO : vo.getPdfAttachmentBomVO()) {
				PdfAttachmentBomResponseDTO bomDTO = new PdfAttachmentBomResponseDTO();
				bomDTO.setId(bomVO.getId());
				bomDTO.setName(bomVO.getName());
				bomDTO.setFileName(bomVO.getFileName());
				bomDTO.setFilePath(bomVO.getFilePath());
				bomDTO.setFileSize(bomVO.getFileSize());
				bomDTO.setContentType(bomVO.getContentType());
				bomDTO.setUploadOn(bomVO.getUploadOn());
				bomResponseList.add(bomDTO);
			}
		}
		responseDTO.setPdfAttachmentBomResponseDTO(bomResponseList);

		return responseDTO;
	}

	private void saveDrawingAttachments(MultipartFile[] files, EngineeringChangeNoteVO engineeringChangeNoteVO)
			throws ApplicationException {
		if (files == null || files.length == 0) {
			return;
		}

		try {
			Path folderPath = Paths.get(engineeringChangeNoteUploadPath, "engineeringchangenote", "drawings",
					engineeringChangeNoteVO.getId().toString());
			createDirectories(folderPath);

			if (ObjectUtils.isNotEmpty(engineeringChangeNoteVO.getId())) {
				List<PdfAttachmentDrawingVO> existingAttachments = pdfAttachmentDrawingRepo
						.findByEngineeringChangeNoteVO(engineeringChangeNoteVO);
				if (existingAttachments != null && !existingAttachments.isEmpty()) {
					pdfAttachmentDrawingRepo.deleteAll(existingAttachments);
				}
			}

			List<PdfAttachmentDrawingVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {
				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "drawing";
				}

				originalName = originalName.replaceAll("\\s+", "_");
				String extension = "";

				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + engineeringChangeNoteVO.getId() + extension;
				Path filePath = folderPath.resolve(fileName);

				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/engineeringchangenote/viewDrawingFile/").toUriString();

				String relativePath = engineeringChangeNoteUploadPath.replace("\\", "/");
				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				String publicUrl = baseUrl + relativePath;

				PdfAttachmentDrawingVO attachment = new PdfAttachmentDrawingVO();
				attachment.setEngineeringChangeNoteVO(engineeringChangeNoteVO);
				attachment.setName(file.getOriginalFilename());
				attachment.setFileName(fileName);
				attachment.setFilePath(publicUrl);
				attachment.setFileSize(file.getSize());
				attachment.setContentType(file.getContentType());
				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			if (!attachmentList.isEmpty()) {
				List<PdfAttachmentDrawingVO> saved = pdfAttachmentDrawingRepo.saveAll(attachmentList);
				engineeringChangeNoteVO.setPdfAttachmentDrawingVO(saved);
			}

		} catch (IOException e) {
			throw new ApplicationException("Drawing File Upload Failed : " + e.getMessage());
		}
	}

	private void saveBomAttachments(MultipartFile[] files, EngineeringChangeNoteVO engineeringChangeNoteVO)
			throws ApplicationException {
		if (files == null || files.length == 0) {
			return;
		}

		try {
			Path folderPath = Paths.get(engineeringChangeNoteUploadPath, "engineeringchangenote", "bom",
					engineeringChangeNoteVO.getId().toString());
			createDirectories(folderPath);

			if (ObjectUtils.isNotEmpty(engineeringChangeNoteVO.getId())) {
				List<PdfAttachmentBomVO> existingAttachments = pdfAttachmentBomRepo
						.findByEngineeringChangeNoteVO(engineeringChangeNoteVO);
				if (existingAttachments != null && !existingAttachments.isEmpty()) {
					pdfAttachmentBomRepo.deleteAll(existingAttachments);
				}
			}

			List<PdfAttachmentBomVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {
				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "bom";
				}

				originalName = originalName.replaceAll("\\s+", "_");
				String extension = "";

				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + engineeringChangeNoteVO.getId() + extension;
				Path filePath = folderPath.resolve(fileName);

				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/engineeringchangenote/viewBomFile/").toUriString();

				String relativePath = engineeringChangeNoteUploadPath.replace("\\", "/");
				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				String publicUrl = baseUrl + relativePath;

				PdfAttachmentBomVO attachment = new PdfAttachmentBomVO();
				attachment.setEngineeringChangeNoteVO(engineeringChangeNoteVO);
				attachment.setName(file.getOriginalFilename());
				attachment.setFileName(fileName);
				attachment.setFilePath(publicUrl);
				attachment.setFileSize(file.getSize());
				attachment.setContentType(file.getContentType());
				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			if (!attachmentList.isEmpty()) {
				List<PdfAttachmentBomVO> saved = pdfAttachmentBomRepo.saveAll(attachmentList);
				engineeringChangeNoteVO.setPdfAttachmentBomVO(saved);
			}

		} catch (IOException e) {
			throw new ApplicationException("BOM File Upload Failed : " + e.getMessage());
		}
	}

	private void createDirectories(Path path) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewDrawingFile(HttpServletRequest request) throws IOException {
		return serveFile(request, "/api/engineeringchangenote/viewDrawingFile/", engineeringChangeNoteUploadPath);
	}

	@Override
	public ResponseEntity<byte[]> viewBomFile(HttpServletRequest request) throws IOException {
		return serveFile(request, "/api/engineeringchangenote/viewBomFile/", engineeringChangeNoteUploadPath);
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

	@Override
	public String getEngineeringChangeNoteDocId(Long orgId, String financialYear) {
		String screenCode = "ECN";
		return engineeringChangeNoteRepo.getEngineeringChangeNoteDocId(orgId, financialYear, screenCode);
	}
}