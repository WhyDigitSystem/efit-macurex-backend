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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

import com.efitops.basesetup.dto.DailyPatrolImageResponseDTO;
import com.efitops.basesetup.dto.DailyPatrolResponseDTO;
import com.efitops.basesetup.dto.DocumentNumberChangeDTO;
import com.efitops.basesetup.dto.DocumentNumberChangeDetailsDTO;
import com.efitops.basesetup.dto.EcnApprovalRecordDTO;
import com.efitops.basesetup.dto.EngineeringChangeNoticeRegisterDTO;
import com.efitops.basesetup.dto.EngineeringChangeNoticeRegisterDetailsDTO;
import com.efitops.basesetup.dto.FinalInspectionImageResponseDTO;
import com.efitops.basesetup.dto.FinalInspectionReportDTO;
import com.efitops.basesetup.dto.FinalInspectionResponseDTO;
import com.efitops.basesetup.dto.FirAppearanceInspectionDTO;
import com.efitops.basesetup.dto.FirDimensionalInspectionDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.IncomingImageResponseDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionAppearanceDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDetailsDTO;
import com.efitops.basesetup.dto.IncomingMaterialResposeDTO;
import com.efitops.basesetup.dto.InprocessImageResponseDTO;
import com.efitops.basesetup.dto.InprocessInspectionAppearanceDTO;
import com.efitops.basesetup.dto.InprocessInspectionDTO;
import com.efitops.basesetup.dto.InprocessInspectionDetailsDTO;
import com.efitops.basesetup.dto.InprocessResponseDTO;
import com.efitops.basesetup.dto.NPDImageResponseDTO;
import com.efitops.basesetup.dto.NcProductRegisterDTO;
import com.efitops.basesetup.dto.NcProductRegisterDetailsDTO;
import com.efitops.basesetup.dto.NpdDTO;
import com.efitops.basesetup.dto.NpdDetailsDTO;
import com.efitops.basesetup.dto.ProcessNonConformanceReportDTO;
import com.efitops.basesetup.dto.QADRegisterDTO;
import com.efitops.basesetup.dto.QADRegisterDetailsDTO;
import com.efitops.basesetup.dto.QualityDocumentChangeRecordDTO;
import com.efitops.basesetup.dto.SampleResponseDTO;
import com.efitops.basesetup.dto.SettingResposeDTO;
import com.efitops.basesetup.entity.DailyPatrolInspectionAttachmentVO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;
import com.efitops.basesetup.entity.DocumentChangeRecordAttachmentVO;
import com.efitops.basesetup.entity.DocumentNumberChangeDetailsVO;
import com.efitops.basesetup.entity.DocumentNumberChangeVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EcnApprovalRecordVO;
import com.efitops.basesetup.entity.EcnAttachmentVO;
import com.efitops.basesetup.entity.EngineeringChangeNoticeRegisterAttachmentVO;
import com.efitops.basesetup.entity.EngineeringChangeNoticeRegisterDetailsVO;
import com.efitops.basesetup.entity.EngineeringChangeNoticeRegisterVO;
import com.efitops.basesetup.entity.FinalInspectionReportAttachmentVO;
import com.efitops.basesetup.entity.FinalInspectionReportVO;
import com.efitops.basesetup.entity.FirAppearanceInspectionVO;
import com.efitops.basesetup.entity.FirDimensionalInspectionVO;
import com.efitops.basesetup.entity.IncomingAttachmentVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionAppearanceVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionDetailsVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.entity.InprocessInspectionAppearanceVO;
import com.efitops.basesetup.entity.InprocessInspectionAttachmentVO;
import com.efitops.basesetup.entity.InprocessInspectionDetailsVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;
import com.efitops.basesetup.entity.NCProductRegisterDetailsAttachmentVO;
import com.efitops.basesetup.entity.NcProductRegisterDetailsVO;
import com.efitops.basesetup.entity.NcProductRegisterVO;
import com.efitops.basesetup.entity.NpdAttachmentVO;
import com.efitops.basesetup.entity.NpdDetailsVO;
import com.efitops.basesetup.entity.NpdVO;
import com.efitops.basesetup.entity.ProcessNonConformanceReportAttachmentVO;
import com.efitops.basesetup.entity.ProcessNonConformanceReportVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.entity.QADRegisterDetailsVO;
import com.efitops.basesetup.entity.QADRegisterVO;
import com.efitops.basesetup.entity.QualityDocumentChangeRecordVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationAttachmentVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DailyPatrolInspectionRepo;
import com.efitops.basesetup.repo.DocumentChangeRecordAttachmentRepo;
import com.efitops.basesetup.repo.DocumentNumberChangeDetailsRepo;
import com.efitops.basesetup.repo.DocumentNumberChangeRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.EcnApprovalRecordRepo;
import com.efitops.basesetup.repo.EcnAttachmentRepo;
import com.efitops.basesetup.repo.EngineeringChangeNoticeRegisterAttachmentRepo;
import com.efitops.basesetup.repo.EngineeringChangeNoticeRegisterDetailsRepo;
import com.efitops.basesetup.repo.EngineeringChangeNoticeRegisterRepo;
import com.efitops.basesetup.repo.FinalInspectionReportAttachmentRepo;
import com.efitops.basesetup.repo.FinalInspectionReportRepo;
import com.efitops.basesetup.repo.FirAppearanceInspectionRepo;
import com.efitops.basesetup.repo.FirDimensionalInspectionRepo;
import com.efitops.basesetup.repo.IncomingAttachmentRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionAppearanceRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionDetailsRepo;
import com.efitops.basesetup.repo.IncomingMaterialInspectionRepo;
import com.efitops.basesetup.repo.InprocessInspectionAppearanceRepo;
import com.efitops.basesetup.repo.InprocessInspectionAttachmentRepo;
import com.efitops.basesetup.repo.InprocessInspectionDetailsRepo;
import com.efitops.basesetup.repo.InprocessInspectionRepo;
import com.efitops.basesetup.repo.NCProductRegisterDetailsAttachmentRepo;
import com.efitops.basesetup.repo.NcProductRegisterDetailsRepo;
import com.efitops.basesetup.repo.NcProductRegisterRepo;
import com.efitops.basesetup.repo.NpdAttachmentRepo;
import com.efitops.basesetup.repo.NpdDetailsRepo;
import com.efitops.basesetup.repo.NpdRepo;
import com.efitops.basesetup.repo.ProcessNonConformanceReportAttachmentRepo;
import com.efitops.basesetup.repo.ProcessNonConformanceReportRepo;
import com.efitops.basesetup.repo.QADRegisterDetailsRepo;
import com.efitops.basesetup.repo.QADRegisterRepo;
import com.efitops.basesetup.repo.QualityDocumentChangeRecordRepo;
import com.efitops.basesetup.repo.ToolRecieveFromCalibrationRepo;

@Service
public class QualityServiceImpl implements QualityService {

	public static final Logger LOGGER = LoggerFactory.getLogger(QualityServiceImpl.class);

	@Autowired
	QADRegisterRepo qadRegisterRepo;

	@Autowired
	QADRegisterDetailsRepo qADRegisterDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	IncomingMaterialInspectionRepo incomingMaterialInspectionRepo;

	@Autowired
	IncomingMaterialInspectionDetailsRepo incomingMaterialInspectionDetailsRepo;

	@Autowired
	IncomingMaterialInspectionAppearanceRepo incomingMaterialInspectionAppearanceRepo;

	@Autowired
	InprocessInspectionRepo inprocessInspectionRepo;

	@Autowired
	InprocessInspectionDetailsRepo inprocessInspectionDetailsRepo;

	@Autowired
	InprocessInspectionAppearanceRepo inprocessInspectionAppearanceRepo;

	@Autowired
	FinalInspectionReportRepo finalInspectionReportRepo;

	@Autowired
	FirDimensionalInspectionRepo firDimensionalInspectionRepo;

	@Autowired
	FirAppearanceInspectionRepo firAppearanceInspectionRepo;

	@Autowired
	EngineeringChangeNoticeRegisterRepo engineeringChangeNoticeRegisterRepo;

	@Autowired
	EngineeringChangeNoticeRegisterDetailsRepo engineeringChangeNoticeRegisterDetailsRepo;

	@Autowired
	NpdRepo npdRepo;

	@Autowired
	NpdDetailsRepo npdDetailsRepo;

	@Autowired
	ProcessNonConformanceReportRepo processNonConformanceReportRepo;

	@Autowired
	QualityDocumentChangeRecordRepo qualityDocumentChangeRecordRepo;

	@Autowired
	EcnApprovalRecordRepo ecnApprovalRecordRepo;

	@Autowired
	NcProductRegisterRepo ncProductRegisterRepo;

	@Autowired
	NcProductRegisterDetailsRepo ncProductRegisterDetailsRepo;

	@Autowired
	DocumentNumberChangeRepo documentNumberChangeRepo;

	@Autowired
	DocumentNumberChangeDetailsRepo documentNumberChangeDetailsRepo;

	@Autowired
	IncomingAttachmentRepo incomingAttachmentRepo;

	@Autowired
	NpdAttachmentRepo npdAttachmentRepo;

	@Autowired
	EcnAttachmentRepo ecnAttachmentRepo;

	@Autowired
	FinalInspectionReportAttachmentRepo finalInspectionReportAttachmentRepo;

	@Autowired
	ProcessNonConformanceReportAttachmentRepo processNonConformanceReportAttachmentRepo;

	@Autowired
	EngineeringChangeNoticeRegisterAttachmentRepo engineeringChangeNoticeRegisterAttachmentRepo;

	@Autowired
	DocumentChangeRecordAttachmentRepo documentChangeRecordAttachmentRepo;

	@Autowired
	NCProductRegisterDetailsAttachmentRepo ncProductRegisterDetailsAttachmentRepo;

	@Autowired
	InprocessInspectionAttachmentRepo inprocessInspectionAttachmentRepo;

	@Autowired
	DailyPatrolInspectionRepo dailyPatrolInspectionRepo;

  @Autowired
	ToolRecieveFromCalibrationRepo toolRecieveFromCalibrationRepo;
  
  @Autowired
	private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${file.upload-dir}")
	private String uploadDir;

	// IncomingMaterialInspection

	@Override
	public Map<String, Object> createUpdateIncomingMaterialInspection(
			IncomingMaterialInspectionDTO incomingMaterialInspectionDTO) throws ApplicationException {
		IncomingMaterialInspectionVO incomingMaterialInspectionVO = new IncomingMaterialInspectionVO();
		String message;
		String screenCode = "INMI";
		IncomingMaterialInspectionVO oldIncomingMaterialInspection = null;
		
		if (ObjectUtils.isNotEmpty(incomingMaterialInspectionDTO.getId())) {
			oldIncomingMaterialInspection = incomingMaterialInspectionRepo.findById(incomingMaterialInspectionDTO.getId())
		            .orElseThrow(() -> new ApplicationException("IncomingMaterialInspection not found"));

			oldIncomingMaterialInspection.getIncomingMaterialInspectionDetailsVO().size(); // load
			oldIncomingMaterialInspection.getIncomingMaterialInspectionAppearanceVO().size(); // load
			
			entityManager.detach(oldIncomingMaterialInspection); // detach snapshot
			
			
			incomingMaterialInspectionVO = incomingMaterialInspectionRepo
					.findById(incomingMaterialInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid IncomingMaterialInspection details"));
			message = "IncomingMaterialInspection Updated Successfully";
			incomingMaterialInspectionVO.setUpdatedBy(incomingMaterialInspectionDTO.getCreatedBy());

		} else {

			String docId = incomingMaterialInspectionRepo.getIncomingMaterialInspectionDocId(
					incomingMaterialInspectionDTO.getOrgId(), incomingMaterialInspectionDTO.getFinYear(),
					incomingMaterialInspectionDTO.getBranchCode(), screenCode);
			incomingMaterialInspectionVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(incomingMaterialInspectionDTO.getOrgId(),
							incomingMaterialInspectionDTO.getFinYear(), incomingMaterialInspectionDTO.getBranchCode(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			incomingMaterialInspectionVO.setCreatedBy(incomingMaterialInspectionDTO.getCreatedBy());
			incomingMaterialInspectionVO.setUpdatedBy(incomingMaterialInspectionDTO.getCreatedBy());

			message = "IncomingMaterialInspection Created Successfully";
		}
		createUpdatedIncomingMaterialInspectionVOFromIncomingMaterialInspectionDTO(incomingMaterialInspectionDTO,
				incomingMaterialInspectionVO);
		incomingMaterialInspectionRepo.save(incomingMaterialInspectionVO);
		commonNotificationService.generateNotification(incomingMaterialInspectionVO.getScreenCode(), incomingMaterialInspectionVO.getId(), oldIncomingMaterialInspection,
				incomingMaterialInspectionVO);
		
		Map<String, Object> response = new HashMap<>();
		response.put("incomingMaterialInspectionVO", incomingMaterialInspectionVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedIncomingMaterialInspectionVOFromIncomingMaterialInspectionDTO(
			IncomingMaterialInspectionDTO incomingMaterialInspectionDTO,
			IncomingMaterialInspectionVO incomingMaterialInspectionVO) throws ApplicationException {
		incomingMaterialInspectionVO.setMaterialType(incomingMaterialInspectionDTO.getMaterialType());
		incomingMaterialInspectionVO.setGrnNo(incomingMaterialInspectionDTO.getGrnNo());
		incomingMaterialInspectionVO.setPoNo(incomingMaterialInspectionDTO.getPoNo());
		incomingMaterialInspectionVO.setSupplierName(incomingMaterialInspectionDTO.getSupplierName());
		incomingMaterialInspectionVO.setDcInvNo(incomingMaterialInspectionDTO.getDcInvNo());
		incomingMaterialInspectionVO.setType(incomingMaterialInspectionDTO.getType());
		incomingMaterialInspectionVO.setItemNo(incomingMaterialInspectionDTO.getItemNo());
		incomingMaterialInspectionVO.setMaterial(incomingMaterialInspectionDTO.getMaterial());
		validateQty(incomingMaterialInspectionDTO.getQtyReceived(), "Qty Received");
		incomingMaterialInspectionVO.setQtyReceived(incomingMaterialInspectionDTO.getQtyReceived());

		validateQty(incomingMaterialInspectionDTO.getInspectedQuantity(), "Inspected Quantity");
		incomingMaterialInspectionVO.setInspectedQuantity(incomingMaterialInspectionDTO.getInspectedQuantity());
		incomingMaterialInspectionVO.setDocumentFormatNo(incomingMaterialInspectionDTO.getDocumentFormatNo());
		incomingMaterialInspectionVO.setDate(incomingMaterialInspectionDTO.getDate());

		//// VisualInspection
		incomingMaterialInspectionVO.setTestCertificate(incomingMaterialInspectionDTO.getTestCertificate());
		incomingMaterialInspectionVO.setAcceptedQty(incomingMaterialInspectionDTO.getAcceptedQty());
		incomingMaterialInspectionVO.setRework(incomingMaterialInspectionDTO.getRework());
		incomingMaterialInspectionVO.setSegregate(incomingMaterialInspectionDTO.getSegregate());
		incomingMaterialInspectionVO
				.setConcessionallyAccepted(incomingMaterialInspectionDTO.getConcessionallyAccepted());
		incomingMaterialInspectionVO.setScrap(incomingMaterialInspectionDTO.getScrap());

		// Summary
		incomingMaterialInspectionVO.setInspectedBy(incomingMaterialInspectionDTO.getInspectedBy());
		incomingMaterialInspectionVO.setInspectedDate(incomingMaterialInspectionDTO.getInspectedDate());
		incomingMaterialInspectionVO.setDcInvNo(incomingMaterialInspectionDTO.getDcInvNo());
		incomingMaterialInspectionVO.setApprovedBy(incomingMaterialInspectionDTO.getApprovedBy());
		incomingMaterialInspectionVO.setApprovedDate(incomingMaterialInspectionDTO.getApprovedDate());
		incomingMaterialInspectionVO.setNaration(incomingMaterialInspectionDTO.getNaration());

		incomingMaterialInspectionVO.setOrgId(incomingMaterialInspectionDTO.getOrgId());
		incomingMaterialInspectionVO.setBranch(incomingMaterialInspectionDTO.getBranch());
		incomingMaterialInspectionVO.setBranchCode(incomingMaterialInspectionDTO.getBranchCode());
		incomingMaterialInspectionVO.setFinYear(incomingMaterialInspectionDTO.getFinYear());
		incomingMaterialInspectionVO.setActive(incomingMaterialInspectionDTO.isActive());
		incomingMaterialInspectionVO.setCreatedBy(incomingMaterialInspectionDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(incomingMaterialInspectionDTO.getId())) {
			List<IncomingMaterialInspectionDetailsVO> IncomingMaterialInspectionDetailsVO1 = incomingMaterialInspectionDetailsRepo
					.findByIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
			incomingMaterialInspectionDetailsRepo.deleteAll(IncomingMaterialInspectionDetailsVO1);

			List<IncomingMaterialInspectionAppearanceVO> IncomingMaterialInspectionAppearanceVO1 = incomingMaterialInspectionAppearanceRepo
					.findByIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
			incomingMaterialInspectionAppearanceRepo.deleteAll(IncomingMaterialInspectionAppearanceVO1);
		}

		List<IncomingMaterialInspectionDetailsVO> incomingMaterialInspectionDetailsVOs = new ArrayList<>();
		for (IncomingMaterialInspectionDetailsDTO incomingMaterialInspectionDetailsDTO : incomingMaterialInspectionDTO
				.getIncomingMaterialInspectionDetailsDTO()) {
			IncomingMaterialInspectionDetailsVO incomingMaterialInspectionDetailsVO = new IncomingMaterialInspectionDetailsVO();
			incomingMaterialInspectionDetailsVO.setParameter(incomingMaterialInspectionDetailsDTO.getParameter());
			incomingMaterialInspectionDetailsVO
					.setSpecification(incomingMaterialInspectionDetailsDTO.getSpecification());
			incomingMaterialInspectionDetailsVO.setLsl(incomingMaterialInspectionDetailsDTO.getLsl());
			incomingMaterialInspectionDetailsVO.setUsl(incomingMaterialInspectionDetailsDTO.getUsl());
			incomingMaterialInspectionDetailsVO
					.setObservedvalue(incomingMaterialInspectionDetailsDTO.getObservedvalue());
			incomingMaterialInspectionDetailsVO.setSample1(incomingMaterialInspectionDetailsDTO.getSample1());
			incomingMaterialInspectionDetailsVO.setSample2(incomingMaterialInspectionDetailsDTO.getSample2());
			incomingMaterialInspectionDetailsVO.setSample3(incomingMaterialInspectionDetailsDTO.getSample3());
			incomingMaterialInspectionDetailsVO.setSample4(incomingMaterialInspectionDetailsDTO.getSample4());
			incomingMaterialInspectionDetailsVO.setSample5(incomingMaterialInspectionDetailsDTO.getSample5());
			incomingMaterialInspectionDetailsVO.setSample6(incomingMaterialInspectionDetailsDTO.getSample6());
			incomingMaterialInspectionDetailsVO.setSample7(incomingMaterialInspectionDetailsDTO.getSample7());
			incomingMaterialInspectionDetailsVO.setSample8(incomingMaterialInspectionDetailsDTO.getSample8());
			incomingMaterialInspectionDetailsVO.setRemarks(incomingMaterialInspectionDetailsDTO.getRemarks());
			incomingMaterialInspectionDetailsVO.setIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
			incomingMaterialInspectionDetailsVOs.add(incomingMaterialInspectionDetailsVO);
		}
		incomingMaterialInspectionVO.setIncomingMaterialInspectionDetailsVO(incomingMaterialInspectionDetailsVOs);

		List<IncomingMaterialInspectionAppearanceVO> incomingMaterialInspectionAppearanceVOs = new ArrayList<>();
		for (IncomingMaterialInspectionAppearanceDTO incomingMaterialInspectionAppearanceDTO : incomingMaterialInspectionDTO
				.getIncomingMaterialInspectionAppearanceDTO()) {
			IncomingMaterialInspectionAppearanceVO incomingMaterialInspectionAppearanceVO = new IncomingMaterialInspectionAppearanceVO();
			incomingMaterialInspectionAppearanceVO
					.setCharacteristics(incomingMaterialInspectionAppearanceDTO.getCharacteristics());
			incomingMaterialInspectionAppearanceVO
					.setMethodOfInspection(incomingMaterialInspectionAppearanceDTO.getMethodOfInspection());
			incomingMaterialInspectionAppearanceVO
					.setSpecifications(incomingMaterialInspectionAppearanceDTO.getSpecifications());
			incomingMaterialInspectionAppearanceVO.setIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
			incomingMaterialInspectionAppearanceVOs.add(incomingMaterialInspectionAppearanceVO);
		}
		incomingMaterialInspectionVO.setIncomingMaterialInspectionAppearanceVO(incomingMaterialInspectionAppearanceVOs);
	}

	private void validateQty(int qty, String fieldName) throws ApplicationException {
		if (qty <= 0) {
			throw new ApplicationException(fieldName + " must be greater than zero.");
		}
	}

	@Override
	public List<IncomingMaterialInspectionVO> getAllIncomingMaterialInspectionByOrgId(Long orgId, String finYear,
			String branchCode) {

		return incomingMaterialInspectionRepo.getAllIncomingMaterialInspectionByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public IncomingMaterialInspectionVO getIncomingMaterialInspectionById(Long id) {

		return incomingMaterialInspectionRepo.getIncomingMaterialInspectionById(id);
	}

	@Override
	public String getIncomingMaterialInspectionDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "INMI";
		String result = incomingMaterialInspectionRepo.getIncomingMaterialInspectionDocId(orgId, finYear, branchCode,
				ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getGrnAndSubContractGrnDetails(Long orgId, String grnNo) {
		Set<Object[]> chType = incomingMaterialInspectionRepo.getGrnAndSubContractGrnDetails(orgId, grnNo);
		return getGrnAndSubContractGrnDetails(chType);
	}

	private List<Map<String, Object>> getGrnAndSubContractGrnDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnNo", ch[0] != null ? ch[0].toString() : "");
			map.put("poNo", ch[1] != null ? ch[1].toString() : "");
			map.put("supplierName", ch[2] != null ? ch[2].toString() : "");
			map.put("dcInvNo", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getItemNoFromGrn(Long orgId, String grnNo) {
		Set<Object[]> chType = incomingMaterialInspectionRepo.getItemNoFromGrn(orgId, grnNo);
		return getItemNo(chType);
	}

	private List<Map<String, Object>> getItemNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", ch[0] != null ? ch[0].toString() : "");
			map.put("itemDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("receivedQty", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getGrnNoAndSubContractGrnNo(Long orgId, String type) {
		Set<Object[]> chType = incomingMaterialInspectionRepo.getGrnNoAndSubContractGrnNo(orgId, type);
		return getGrnNoAndSubContractGrnNo(chType);
	}

	private List<Map<String, Object>> getGrnNoAndSubContractGrnNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// InprocesInspection

	@Override
	public Map<String, Object> createUpdateInprocessInspection(InprocessInspectionDTO inprocessInspectionDTO)
			throws ApplicationException {
		InprocessInspectionVO inprocessInspectionVO = new InprocessInspectionVO();
		String message;
		String screenCode = "IIN";
		InprocessInspectionVO oldInprocessInspection = null;
		
		if (ObjectUtils.isNotEmpty(inprocessInspectionDTO.getId())) {
			oldInprocessInspection = inprocessInspectionRepo.findById(inprocessInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("InprocessInspection not found"));

			oldInprocessInspection.getInprocessInspectionDetailsVO().size(); // load
			oldInprocessInspection.getInprocessInspectionAppearanceVO().size(); // load


			entityManager.detach(oldInprocessInspection); // detach snapshot
			
			inprocessInspectionVO = inprocessInspectionRepo.findById(inprocessInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid InprocessInspection details"));
			message = "InprocessInspection Updated Successfully";
			inprocessInspectionVO.setUpdatedBy(inprocessInspectionDTO.getCreatedBy());

		} else {

			String docId = inprocessInspectionRepo.getInprocessInspectionDocId(inprocessInspectionDTO.getOrgId(),
					inprocessInspectionDTO.getFinYear(), inprocessInspectionDTO.getBranchCode(), screenCode);
			inprocessInspectionVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(inprocessInspectionDTO.getOrgId(),
							inprocessInspectionDTO.getFinYear(), inprocessInspectionDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			inprocessInspectionVO.setCreatedBy(inprocessInspectionDTO.getCreatedBy());
			inprocessInspectionVO.setUpdatedBy(inprocessInspectionDTO.getCreatedBy());

			message = "InprocessInspection Created Successfully";
		}
		createUpdatedInprocessInspectionVOFromInprocessInspectionDTO(inprocessInspectionDTO, inprocessInspectionVO);
		inprocessInspectionRepo.save(inprocessInspectionVO);
		commonNotificationService.generateNotification(inprocessInspectionVO.getScreenCode(), inprocessInspectionVO.getId(), oldInprocessInspection,
				inprocessInspectionVO);
		
		
		Map<String, Object> response = new HashMap<>();
		response.put("inprocessInspectionVO", inprocessInspectionVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedInprocessInspectionVOFromInprocessInspectionDTO(
			InprocessInspectionDTO inprocessInspectionDTO, InprocessInspectionVO inprocessInspectionVO) {
		inprocessInspectionVO.setRouteCardNo(inprocessInspectionDTO.getRouteCardNo());
		inprocessInspectionVO.setWorkOrderNo(inprocessInspectionDTO.getWorkOrderNo());
		inprocessInspectionVO.setPartNo(inprocessInspectionDTO.getPartNo());
		inprocessInspectionVO.setPartName(inprocessInspectionDTO.getPartName());
		inprocessInspectionVO.setMaterialDrawingNo(inprocessInspectionDTO.getMaterialDrawingNo());
		inprocessInspectionVO.setCustomer(inprocessInspectionDTO.getCustomer());
		inprocessInspectionVO.setLotQty(inprocessInspectionDTO.getLotQty());
		inprocessInspectionVO.setDrawingNo(inprocessInspectionDTO.getDrawingNo());
		inprocessInspectionVO.setReceivedQty(inprocessInspectionDTO.getReceivedQty());
		inprocessInspectionVO.setSampleQty(inprocessInspectionDTO.getSampleQty());

		inprocessInspectionVO.setOrgId(inprocessInspectionDTO.getOrgId());
		inprocessInspectionVO.setBranch(inprocessInspectionDTO.getBranch());
		inprocessInspectionVO.setBranchCode(inprocessInspectionDTO.getBranchCode());
		inprocessInspectionVO.setFinYear(inprocessInspectionDTO.getFinYear());
		inprocessInspectionVO.setActive(inprocessInspectionDTO.isActive());
		inprocessInspectionVO.setCreatedBy(inprocessInspectionDTO.getCreatedBy());
		inprocessInspectionVO.setDocumentFormatNo(inprocessInspectionDTO.getDocumentFormatNo());

		// Summary
		inprocessInspectionVO.setCheckedBy(inprocessInspectionDTO.getCheckedBy());
		inprocessInspectionVO.setApprovedBy(inprocessInspectionDTO.getApprovedBy());
		inprocessInspectionVO.setNaration(inprocessInspectionDTO.getNaration());

		if (ObjectUtils.isNotEmpty(inprocessInspectionDTO.getId())) {
			List<InprocessInspectionDetailsVO> inprocessInspectionDetailsVO1 = inprocessInspectionDetailsRepo
					.findByInprocessInspectionVO(inprocessInspectionVO);
			inprocessInspectionDetailsRepo.deleteAll(inprocessInspectionDetailsVO1);

			List<InprocessInspectionAppearanceVO> inprocessInspectionAppearanceVO1 = inprocessInspectionAppearanceRepo
					.findByInprocessInspectionVO(inprocessInspectionVO);
			inprocessInspectionAppearanceRepo.deleteAll(inprocessInspectionAppearanceVO1);
		}

		List<InprocessInspectionDetailsVO> inprocessInspectionDetailsVOs = new ArrayList<>();
		for (InprocessInspectionDetailsDTO inprocessInspectionDetailsDTO : inprocessInspectionDTO
				.getInprocessInspectionDetailsDTO()) {
			InprocessInspectionDetailsVO inprocessInspectionDetailsVO = new InprocessInspectionDetailsVO();
			inprocessInspectionDetailsVO.setCharacteristics(inprocessInspectionDetailsDTO.getCharacteristics());
			inprocessInspectionDetailsVO.setMethodOfInspection(inprocessInspectionDetailsDTO.getMethodOfInspection());
			inprocessInspectionDetailsVO.setSpecification(inprocessInspectionDetailsDTO.getSpecification());
			inprocessInspectionDetailsVO.setLsl(inprocessInspectionDetailsDTO.getLsl());
			inprocessInspectionDetailsVO.setUsl(inprocessInspectionDetailsDTO.getUsl());
			inprocessInspectionDetailsVO.setSample1(inprocessInspectionDetailsDTO.getSample1());
			inprocessInspectionDetailsVO.setSample2(inprocessInspectionDetailsDTO.getSample2());
			inprocessInspectionDetailsVO.setSample3(inprocessInspectionDetailsDTO.getSample3());
			inprocessInspectionDetailsVO.setSample4(inprocessInspectionDetailsDTO.getSample4());
			inprocessInspectionDetailsVO.setSample5(inprocessInspectionDetailsDTO.getSample5());
			inprocessInspectionDetailsVO.setSample6(inprocessInspectionDetailsDTO.getSample6());
			inprocessInspectionDetailsVO.setSample7(inprocessInspectionDetailsDTO.getSample7());
			inprocessInspectionDetailsVO.setSample8(inprocessInspectionDetailsDTO.getSample8());
			inprocessInspectionDetailsVO.setRemarks(inprocessInspectionDetailsDTO.getRemarks());
			inprocessInspectionDetailsVO.setInprocessInspectionVO(inprocessInspectionVO);
			inprocessInspectionDetailsVOs.add(inprocessInspectionDetailsVO);
		}
		inprocessInspectionVO.setInprocessInspectionDetailsVO(inprocessInspectionDetailsVOs);

		List<InprocessInspectionAppearanceVO> inprocessInspectionAppearanceVOs = new ArrayList<>();
		for (InprocessInspectionAppearanceDTO inprocessInspectionAppearanceDTO : inprocessInspectionDTO
				.getInprocessInspectionAppearanceDTO()) {
			InprocessInspectionAppearanceVO inprocessInspectionAppearanceVO = new InprocessInspectionAppearanceVO();
			inprocessInspectionAppearanceVO.setCharacteristics(inprocessInspectionAppearanceDTO.getCharacteristics());
			inprocessInspectionAppearanceVO
					.setMethodOfInspection(inprocessInspectionAppearanceDTO.getMethodOfInspection());
			inprocessInspectionAppearanceVO.setSpecification(inprocessInspectionAppearanceDTO.getSpecification());
			inprocessInspectionAppearanceVO.setObservation(inprocessInspectionAppearanceDTO.getObservation());
			inprocessInspectionAppearanceVO.setRemarks1(inprocessInspectionAppearanceDTO.getRemarks1());
			inprocessInspectionAppearanceVO.setInprocessInspectionVO(inprocessInspectionVO);
			inprocessInspectionAppearanceVOs.add(inprocessInspectionAppearanceVO);
		}
		inprocessInspectionVO.setInprocessInspectionAppearanceVO(inprocessInspectionAppearanceVOs);
	}

	@Override
	public List<InprocessInspectionVO> getAllInprocessInspectionByOrgId(Long orgId, String finYear, String branchCode) {

		return inprocessInspectionRepo.getAllInprocessInspectionByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public InprocessInspectionVO getInprocessInspectionById(Long id) {

		return inprocessInspectionRepo.getInprocessInspectionById(id);
	}

	@Override
	public String getInprocessInspectionDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "IIN";
		String result = inprocessInspectionRepo.getInprocessInspectionDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getDocIdFromRouteCardNumber(Long orgId, String finYear, String branchCode) {
		Set<Object[]> chType = inprocessInspectionRepo.getDocIdFromRouteCardNumber(orgId, finYear, branchCode);
		return getDocIdFromRoute(chType);
	}

	private List<Map<String, Object>> getDocIdFromRoute(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNumber", ch[0] != null ? ch[0].toString() : "");
			map.put("workOrderNumber", ch[1] != null ? ch[1].toString() : "");
			map.put("partNo", ch[2] != null ? ch[2].toString() : "");
			map.put("partName", ch[3] != null ? ch[3].toString() : "");
			map.put("customerName", ch[4] != null ? ch[4].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDrawingNumberForInProcessInspection(Long orgId, String finYear,
			String branchCode, String fgPartno) {
		Set<Object[]> chType = inprocessInspectionRepo.getDrawingNumberForInProcessInspection(orgId, finYear,
				branchCode, fgPartno);
		return getDrawingNumber(chType);
	}

	private List<Map<String, Object>> getDrawingNumber(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getEmployeeNameFromEmployeeMaster(Long orgId, String branchCode) {
		Set<Object[]> chType = inprocessInspectionRepo.getEmployeeNameFromEmployeeMaster(orgId, branchCode);
		return getEmployee(chType);
	}

	private List<Map<String, Object>> getEmployee(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			map.put("employeeCode", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	// FinalInspectionReport

	@Override
	public Map<String, Object> createUpdateFinalInspectionReport(FinalInspectionReportDTO finalInspectionReportDTO)
			throws ApplicationException {
		FinalInspectionReportVO finalInspectionReportVO = new FinalInspectionReportVO();
		String message;
		String screenCode = "FINR";
		FinalInspectionReportVO oldFinalInspectionReport = null;
		
		if (ObjectUtils.isNotEmpty(finalInspectionReportDTO.getId())) {
			oldFinalInspectionReport = finalInspectionReportRepo.findById(finalInspectionReportDTO.getId())
		            .orElseThrow(() -> new ApplicationException("RouteCardEntry not found"));

			oldFinalInspectionReport.getFirDimensionalInspectionVO().size(); // load
			oldFinalInspectionReport.getFirAppearanceInspectionVO().size(); // load
			
			entityManager.detach(oldFinalInspectionReport); // detach snapshot
			
			finalInspectionReportVO = finalInspectionReportRepo.findById(finalInspectionReportDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid FinalInspectionReport details"));
			message = "FinalInspectionReport Updated Successfully";
			finalInspectionReportVO.setUpdatedBy(finalInspectionReportDTO.getCreatedBy());

		} else {

			String docId = finalInspectionReportRepo.getFinalInspectionReportDocId(finalInspectionReportDTO.getOrgId(),
					finalInspectionReportDTO.getFinYear(), finalInspectionReportDTO.getBranchCode(), screenCode);
			finalInspectionReportVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(finalInspectionReportDTO.getOrgId(),
							finalInspectionReportDTO.getFinYear(), finalInspectionReportDTO.getBranchCode(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			finalInspectionReportVO.setCreatedBy(finalInspectionReportDTO.getCreatedBy());
			finalInspectionReportVO.setUpdatedBy(finalInspectionReportDTO.getCreatedBy());

			message = "FinalInspectionReport Created Successfully";
		}
		createUpdatedFinalInspectionReportVOFromFinalInspectionReportDTO(finalInspectionReportDTO,
				finalInspectionReportVO);
		finalInspectionReportRepo.save(finalInspectionReportVO);
		commonNotificationService.generateNotification(finalInspectionReportVO.getScreenCode(), finalInspectionReportVO.getId(), oldFinalInspectionReport,
				finalInspectionReportVO);

		
		
		Map<String, Object> response = new HashMap<>();
		response.put("finalInspectionReportVO", finalInspectionReportVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedFinalInspectionReportVOFromFinalInspectionReportDTO(
			FinalInspectionReportDTO finalInspectionReportDTO, FinalInspectionReportVO finalInspectionReportVO) {
		finalInspectionReportVO.setRouteCard(finalInspectionReportDTO.getRouteCard());
		finalInspectionReportVO.setPartName(finalInspectionReportDTO.getPartName());
		finalInspectionReportVO.setPartNo(finalInspectionReportDTO.getPartNo());
		finalInspectionReportVO.setUntis(finalInspectionReportDTO.getUntis());
		finalInspectionReportVO.setCustomer(finalInspectionReportDTO.getCustomer());
		finalInspectionReportVO.setPoNo(finalInspectionReportDTO.getPoNo());
		finalInspectionReportVO.setInspectionDate(finalInspectionReportDTO.getInspectionDate());
		finalInspectionReportVO.setInvoiceNo(finalInspectionReportDTO.getInvoiceNo());
		finalInspectionReportVO.setLotQty(finalInspectionReportDTO.getLotQty());
		finalInspectionReportVO.setSampleQty(finalInspectionReportDTO.getSampleQty());

		finalInspectionReportVO.setOrgId(finalInspectionReportDTO.getOrgId());
		finalInspectionReportVO.setBranch(finalInspectionReportDTO.getBranch());
		finalInspectionReportVO.setBranchCode(finalInspectionReportDTO.getBranchCode());
		finalInspectionReportVO.setFinYear(finalInspectionReportDTO.getFinYear());
		finalInspectionReportVO.setActive(finalInspectionReportDTO.isActive());
		finalInspectionReportVO.setCreatedBy(finalInspectionReportDTO.getCreatedBy());
		finalInspectionReportVO.setDocumentFormatNo(finalInspectionReportDTO.getDocumentFormatNo());

		// Summary
		finalInspectionReportVO.setCheckedBy(finalInspectionReportDTO.getCheckedBy());
		finalInspectionReportVO.setApprovedBy(finalInspectionReportDTO.getApprovedBy());
		finalInspectionReportVO.setNaration(finalInspectionReportDTO.getNaration());

		if (ObjectUtils.isNotEmpty(finalInspectionReportDTO.getId())) {
			List<FirDimensionalInspectionVO> firDimensionalInspectionVO1 = firDimensionalInspectionRepo
					.findByFinalInspectionReportVO(finalInspectionReportVO);
			firDimensionalInspectionRepo.deleteAll(firDimensionalInspectionVO1);

			List<FirAppearanceInspectionVO> firAppearanceInspectionVO1 = firAppearanceInspectionRepo
					.findByFinalInspectionReportVO(finalInspectionReportVO);
			firAppearanceInspectionRepo.deleteAll(firAppearanceInspectionVO1);
		}

		List<FirDimensionalInspectionVO> firDimensionalInspectionVOs = new ArrayList<>();
		for (FirDimensionalInspectionDTO firDimensionalInspectionDTO : finalInspectionReportDTO
				.getFirDimensionalInspectionDTO()) {
			FirDimensionalInspectionVO firDimensionalInspectionVO = new FirDimensionalInspectionVO();
			firDimensionalInspectionVO.setCharacteristics(firDimensionalInspectionDTO.getCharacteristics());
			firDimensionalInspectionVO.setMethodOfInspection(firDimensionalInspectionDTO.getMethodOfInspection());
			firDimensionalInspectionVO.setSpecification(firDimensionalInspectionDTO.getSpecification());
			firDimensionalInspectionVO.setLsl(firDimensionalInspectionDTO.getLsl());
			firDimensionalInspectionVO.setUsl(firDimensionalInspectionDTO.getUsl());
			firDimensionalInspectionVO.setSample1(firDimensionalInspectionDTO.getSample1());
			firDimensionalInspectionVO.setSample2(firDimensionalInspectionDTO.getSample2());
			firDimensionalInspectionVO.setSample3(firDimensionalInspectionDTO.getSample3());
			firDimensionalInspectionVO.setSample4(firDimensionalInspectionDTO.getSample4());
			firDimensionalInspectionVO.setSample5(firDimensionalInspectionDTO.getSample5());
			firDimensionalInspectionVO.setSample6(firDimensionalInspectionDTO.getSample6());
			firDimensionalInspectionVO.setSample7(firDimensionalInspectionDTO.getSample7());
			firDimensionalInspectionVO.setSample8(firDimensionalInspectionDTO.getSample8());
			firDimensionalInspectionVO.setSample9(firDimensionalInspectionDTO.getSample9());
			firDimensionalInspectionVO.setSample10(firDimensionalInspectionDTO.getSample10());
			firDimensionalInspectionVO.setRemarks(firDimensionalInspectionDTO.getRemarks());
			firDimensionalInspectionVO.setFinalInspectionReportVO(finalInspectionReportVO);
			firDimensionalInspectionVOs.add(firDimensionalInspectionVO);
		}
		finalInspectionReportVO.setFirDimensionalInspectionVO(firDimensionalInspectionVOs);

		List<FirAppearanceInspectionVO> firAppearanceInspectionVOs = new ArrayList<>();
		for (FirAppearanceInspectionDTO firAppearanceInspectionDTO : finalInspectionReportDTO
				.getFirAppearanceInspectionDTO()) {
			FirAppearanceInspectionVO firAppearanceInspectionVO = new FirAppearanceInspectionVO();
			firAppearanceInspectionVO.setCharacteristics(firAppearanceInspectionDTO.getCharacteristics());
			firAppearanceInspectionVO.setMethodOfInspection(firAppearanceInspectionDTO.getMethodOfInspection());
			firAppearanceInspectionVO.setSpecification(firAppearanceInspectionDTO.getSpecification());
			firAppearanceInspectionVO.setLsl(firAppearanceInspectionDTO.getLsl());
			firAppearanceInspectionVO.setUsl(firAppearanceInspectionDTO.getUsl());
			firAppearanceInspectionVO.setObservation(firAppearanceInspectionDTO.getObservation());
			firAppearanceInspectionVO.setRemarks(firAppearanceInspectionDTO.getRemarks());
			firAppearanceInspectionVO.setFinalInspectionReportVO(finalInspectionReportVO);
			firAppearanceInspectionVOs.add(firAppearanceInspectionVO);
		}
		finalInspectionReportVO.setFirAppearanceInspectionVO(firAppearanceInspectionVOs);
	}

	@Override
	public List<FinalInspectionReportVO> getAllFinalInspectionReportByOrgId(Long orgId, String finYear,
			String branchCode) {

		return finalInspectionReportRepo.getAllFinalInspectionReportByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public FinalInspectionReportVO getFinalInspectionReportById(Long id) {

		return finalInspectionReportRepo.getFinalInspectionReportById(id);
	}

	@Override
	public String getFinalInspectionReportDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "FINR";
		String result = finalInspectionReportRepo.getFinalInspectionReportDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPartNameForFinalInspectionReport(Long orgId, String finYear, String branchCode,
			String routeCardNumber) {
		Set<Object[]> chType = finalInspectionReportRepo.getPartNameForFinalInspectionReport(orgId, finYear, branchCode,
				routeCardNumber);
		return getPartNameForFinalInspectionReport(chType);
	}

	private List<Map<String, Object>> getPartNameForFinalInspectionReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partName", ch[0] != null ? ch[0].toString() : "");
			map.put("partNo", ch[1] != null ? ch[1].toString() : "");
			map.put("units", ch[2] != null ? ch[2].toString() : "");
			map.put("customer", ch[3] != null ? ch[3].toString() : "");
			map.put("poNo", ch[4] != null ? ch[4].toString() : "");
			map.put("invoiceNo", ch[5] != null ? ch[5].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRouteCardNumberForFinalInspectionReport(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> chType = finalInspectionReportRepo.getRouteCardNumberForFinalInspectionReport(orgId, finYear,
				branchCode);
		return getRouteCardNumbergetForFinalInspectionReport(chType);
	}

	private List<Map<String, Object>> getRouteCardNumbergetForFinalInspectionReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNumber", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSupplierNameForIncomingMaterialInspectionReport(Long orgId, String branchCode) {

		LOGGER.info("Fetching suppliers for orgId={}, branchCode={}", orgId, branchCode);

		Set<String> suppliers = incomingMaterialInspectionRepo.getSupplierNameForIncomingMaterialInspectionReport(orgId,
				branchCode);

		List<Map<String, Object>> result = new ArrayList<>();

		for (String supplier : suppliers) {
			Map<String, Object> obj = new LinkedHashMap<>();
			obj.put("supplierName", supplier);
			result.add(obj);
		}

		LOGGER.info("Suppliers found: {}", result);
		return result;
	}

	@Override
	public List<Map<String, Object>> getGrnNoForIncomingMaterialInspectionReport(Long orgId, String branchCode,
			String supplierName) {

		LOGGER.info("Fetching suppliers for orgId={}, branchCode={}", orgId, branchCode, supplierName);

		Set<String> grnNos = incomingMaterialInspectionRepo.getGrnNoForIncomingMaterialInspectionReport(orgId,
				branchCode, supplierName);

		List<Map<String, Object>> result = new ArrayList<>();

		for (String grnNo : grnNos) {
			Map<String, Object> obj = new LinkedHashMap<>();
			obj.put("grnNo", grnNo);
			result.add(obj);
		}

		LOGGER.info("Suppliers found: {}", result);
		return result;
	}

	// InprocessInspectionReport

	@Override
	public List<Map<String, Object>> getInProcessInspectionReport(Long orgId, String branchCode, String fromDate,
			String toDate, String routeCardNo) {
		Set<Object[]> chType = inprocessInspectionRepo.getInProcessInspectionReport(orgId, branchCode, fromDate, toDate,
				routeCardNo);
		return getInProcessInspectionReport(chType);
	}

	private List<Map<String, Object>> getInProcessInspectionReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> obj = new LinkedHashMap<>();

			obj.put("inprocessInspectionId", ch[0] != null ? ch[0] : null);
			obj.put("active", ch[1] != null ? ch[1] : null);
			obj.put("approvedBy", ch[2] != null ? ch[2].toString() : "");
			obj.put("checkedBy", ch[3] != null ? ch[3].toString() : "");
			obj.put("customer", ch[4] != null ? ch[4].toString() : "");
			obj.put("docDate", ch[5] != null ? ch[5] : null);
			obj.put("docId", ch[6] != null ? ch[6].toString() : "");
			obj.put("documentFormatNo", ch[7] != null ? ch[7].toString() : "");
			obj.put("drawingNo", ch[8] != null ? ch[8].toString() : "");
			obj.put("lotQty", ch[9] != null ? ch[9] : 0);
			obj.put("materialDrawingNo", ch[10] != null ? ch[10].toString() : "");
			obj.put("naration", ch[11] != null ? ch[11].toString() : "");
			obj.put("orgId", ch[12] != null ? ch[12] : null);
			obj.put("partName", ch[13] != null ? ch[13].toString() : "");
			obj.put("partNo", ch[14] != null ? ch[14].toString() : "");
			obj.put("receivedQty", ch[15] != null ? ch[15] : 0);
			obj.put("routeCardNo", ch[16] != null ? ch[16].toString() : "");
			obj.put("sampleQty", ch[17] != null ? ch[17] : 0);
			obj.put("workOrderNo", ch[18] != null ? ch[18].toString() : "");
			obj.put("branch", ch[19] != null ? ch[19].toString() : "");
			obj.put("branchCode", ch[20] != null ? ch[20].toString() : "");
			obj.put("finYear", ch[21] != null ? ch[21].toString() : "");

			List1.add(obj);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getFinalInspectionReportDetails(Long orgId, String fromdate, String todate) {
		Set<Object[]> finalInspectionReportDetails = finalInspectionReportRepo.getFinalInspectionReportDetails(orgId,
				fromdate, todate);
		return getFinalInspectionReportDetails(finalInspectionReportDetails);
	}

	private List<Map<String, Object>> getFinalInspectionReportDetails(Set<Object[]> finalInspectionReportDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : finalInspectionReportDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgid", ch[0] != null ? ch[0].toString() : "");
			map.put("docid", ch[1] != null ? ch[1].toString() : "");
			map.put("docdate", ch[2] != null ? ch[2].toString() : "");
			map.put("inspectiondate", ch[3] != null ? ch[3].toString() : "");
			map.put("invoiceno", ch[4] != null ? ch[4].toString() : "");
			map.put("routecardno", ch[5] != null ? ch[5].toString() : "");
			map.put("partname", ch[6] != null ? ch[6].toString() : "");
			map.put("partno", ch[7] != null ? ch[7].toString() : "");
			map.put("units", ch[8] != null ? ch[8].toString() : "");
			map.put("customer", ch[9] != null ? ch[9].toString() : "");
			map.put("pono", ch[10] != null ? ch[10].toString() : "");
			map.put("lotqty", ch[11] != null ? ch[11].toString() : "");
			map.put("sampleqty", ch[12] != null ? ch[12].toString() : "");
			map.put("documentformatno", ch[13] != null ? ch[13].toString() : "");
			map.put("characterestics", ch[14] != null ? ch[14].toString() : "");
			map.put("methodofinspection", ch[15] != null ? ch[15].toString() : "");
			map.put("sample1", ch[16] != null ? ch[16].toString() : "");
			map.put("sample2", ch[17] != null ? ch[17].toString() : "");
			map.put("sample3", ch[18] != null ? ch[18].toString() : "");
			map.put("specification", ch[19] != null ? ch[19].toString() : "");
			map.put("observation", ch[20] != null ? ch[20].toString() : "");
			map.put("finalInspectionReportId", ch[21] != null ? ch[21].toString() : "");
			List1.add(map);
		}

		return List1;
	}
	// EngineeringChangeNoticeRegister

	@Override
	public Map<String, Object> createUpdateEngineeringChangeNoticeRegister(
			EngineeringChangeNoticeRegisterDTO engineeringChangeNoticeRegisterDTO) throws ApplicationException {
		EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegisterVO = new EngineeringChangeNoticeRegisterVO();
		String message;
		String screenCode = "ECNR";
		if (ObjectUtils.isNotEmpty(engineeringChangeNoticeRegisterDTO.getId())) {
			engineeringChangeNoticeRegisterVO = engineeringChangeNoticeRegisterRepo
					.findById(engineeringChangeNoticeRegisterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid EngineeringChangeNoticeRegister details"));
			message = "EngineeringChangeNoticeRegister Updated Successfully";
			engineeringChangeNoticeRegisterVO.setUpdatedBy(engineeringChangeNoticeRegisterDTO.getCreatedBy());

		} else {

			String docId = engineeringChangeNoticeRegisterRepo.getEngineeringChangeNoticeRegisterDocId(
					engineeringChangeNoticeRegisterDTO.getOrgId(), engineeringChangeNoticeRegisterDTO.getFinYear(),
					engineeringChangeNoticeRegisterDTO.getBranchCode(), screenCode);
			engineeringChangeNoticeRegisterVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(engineeringChangeNoticeRegisterDTO.getOrgId(),
							engineeringChangeNoticeRegisterDTO.getFinYear(),
							engineeringChangeNoticeRegisterDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			engineeringChangeNoticeRegisterVO.setCreatedBy(engineeringChangeNoticeRegisterDTO.getCreatedBy());
			engineeringChangeNoticeRegisterVO.setUpdatedBy(engineeringChangeNoticeRegisterDTO.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdatedEngineeringChangeNoticeRegisterVOFromEngineeringChangeNoticeRegisterDTO(
				engineeringChangeNoticeRegisterDTO, engineeringChangeNoticeRegisterVO);
		engineeringChangeNoticeRegisterRepo.save(engineeringChangeNoticeRegisterVO);
		Map<String, Object> response = new HashMap<>();
		response.put("engineeringChangeNoticeRegisterVO", engineeringChangeNoticeRegisterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedEngineeringChangeNoticeRegisterVOFromEngineeringChangeNoticeRegisterDTO(
			EngineeringChangeNoticeRegisterDTO engineeringChangeNoticeRegisterDTO,
			EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegisterVO) {
		engineeringChangeNoticeRegisterVO
				.setDocumentFormateNo(engineeringChangeNoticeRegisterDTO.getDocumentFormateNo());
		engineeringChangeNoticeRegisterVO.setNarration(engineeringChangeNoticeRegisterDTO.getNarration());
		engineeringChangeNoticeRegisterVO.setOrgId(engineeringChangeNoticeRegisterDTO.getOrgId());
		engineeringChangeNoticeRegisterVO.setBranch(engineeringChangeNoticeRegisterDTO.getBranch());
		engineeringChangeNoticeRegisterVO.setBranchCode(engineeringChangeNoticeRegisterDTO.getBranchCode());
		engineeringChangeNoticeRegisterVO.setFinYear(engineeringChangeNoticeRegisterDTO.getFinYear());
		engineeringChangeNoticeRegisterVO.setCreatedBy(engineeringChangeNoticeRegisterDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(engineeringChangeNoticeRegisterDTO.getId())) {
			List<EngineeringChangeNoticeRegisterDetailsVO> engineeringChangeNoticeRegisterDetailsVO1 = engineeringChangeNoticeRegisterDetailsRepo
					.findByEngineeringChangeNoticeRegisterVO(engineeringChangeNoticeRegisterVO);
			engineeringChangeNoticeRegisterDetailsRepo.deleteAll(engineeringChangeNoticeRegisterDetailsVO1);

		}

		List<EngineeringChangeNoticeRegisterDetailsVO> engineeringChangeNoticeRegisterDetailsVOs = new ArrayList<>();
		for (EngineeringChangeNoticeRegisterDetailsDTO engineeringChangeNoticeRegisterDetailsDTO : engineeringChangeNoticeRegisterDTO
				.getEngineeringChangeNoticeRegisterDetailsDTO()) {
			EngineeringChangeNoticeRegisterDetailsVO engineeringChangeNoticeRegisterDetailsVO = new EngineeringChangeNoticeRegisterDetailsVO();
			engineeringChangeNoticeRegisterDetailsVO
					.setIntEcNno(engineeringChangeNoticeRegisterDetailsDTO.getIntEcNno());
			engineeringChangeNoticeRegisterDetailsVO
					.setCustomer(engineeringChangeNoticeRegisterDetailsDTO.getCustomer());
			engineeringChangeNoticeRegisterDetailsVO
					.setEncRefNo(engineeringChangeNoticeRegisterDetailsDTO.getEncRefNo());
			engineeringChangeNoticeRegisterDetailsVO
					.setPartName(engineeringChangeNoticeRegisterDetailsDTO.getPartName());
			engineeringChangeNoticeRegisterDetailsVO
					.setOldRevDate(engineeringChangeNoticeRegisterDetailsDTO.getOldRevDate());
			engineeringChangeNoticeRegisterDetailsVO.setDateRev(engineeringChangeNoticeRegisterDetailsDTO.getDateRev());
			engineeringChangeNoticeRegisterDetailsVO
					.setDetailsOfRevision(engineeringChangeNoticeRegisterDetailsDTO.getDetailsOfRevision());
			engineeringChangeNoticeRegisterDetailsVO
					.setReasonForRevision(engineeringChangeNoticeRegisterDetailsDTO.getReasonForRevision());
			engineeringChangeNoticeRegisterDetailsVO.setOldRev(engineeringChangeNoticeRegisterDetailsDTO.getOldRev());
			engineeringChangeNoticeRegisterDetailsVO
					.setVerified(engineeringChangeNoticeRegisterDetailsDTO.getVerified());
			engineeringChangeNoticeRegisterDetailsVO.setSlNo(engineeringChangeNoticeRegisterDetailsDTO.getSlNo());

			engineeringChangeNoticeRegisterDetailsVO.setRemarks(engineeringChangeNoticeRegisterDetailsDTO.getRemarks());
			engineeringChangeNoticeRegisterDetailsVO
					.setEngineeringChangeNoticeRegisterVO(engineeringChangeNoticeRegisterVO);
			engineeringChangeNoticeRegisterDetailsVOs.add(engineeringChangeNoticeRegisterDetailsVO);
		}
		engineeringChangeNoticeRegisterVO
				.setEngineeringChangeNoticeRegisterDetailsVO(engineeringChangeNoticeRegisterDetailsVOs);

	}

	@Override
	public List<EngineeringChangeNoticeRegisterVO> getEngineeringChangeNoticeRegisterByOrgId(Long orgId, String finYear,
			String branchCode) {

		return engineeringChangeNoticeRegisterRepo.getEngineeringChangeNoticeRegisterByOrgId(orgId, finYear,
				branchCode);
	}

	@Override
	public EngineeringChangeNoticeRegisterVO getEngineeringChangeNoticeRegisterById(Long id) {

		return engineeringChangeNoticeRegisterRepo.getEngineeringChangeNoticeRegisterById(id);
	}

	@Override
	public String getEngineeringChangeNoticeRegisterDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "ECNR";
		return engineeringChangeNoticeRegisterRepo.getEngineeringChangeNoticeRegisterDocId(orgId, finYear, branchCode,
				screenCode);
	}

	@Override
	public List<Map<String, Object>> getCustomerNameFormPartyMaster(Long orgId) {
		Set<Object[]> finalInspectionReportDetails = engineeringChangeNoticeRegisterRepo
				.getCustomerNameFormPartyMaster(orgId);
		return getCustomerNameFormPartyMaster(finalInspectionReportDetails);
	}

	private List<Map<String, Object>> getCustomerNameFormPartyMaster(Set<Object[]> finalInspectionReportDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : finalInspectionReportDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("customer", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNameFormPartyMaster(Long orgId) {
		Set<Object[]> finalInspectionReportDetails = engineeringChangeNoticeRegisterRepo
				.getPartNameFormPartyMaster(orgId);
		return getPartNameFormPartyMaster(finalInspectionReportDetails);
	}

	private List<Map<String, Object>> getPartNameFormPartyMaster(Set<Object[]> finalInspectionReportDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : finalInspectionReportDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemDesc", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}

		return List1;
	}

	//

	@Override
	public Map<String, Object> createUpdateNpd(NpdDTO npdDTO) throws ApplicationException {
		NpdVO npdVO = new NpdVO();
		String message;
		String screenCode = "NPD";
		if (ObjectUtils.isNotEmpty(npdDTO.getId())) {
			npdVO = npdRepo.findById(npdDTO.getId()).orElseThrow(() -> new ApplicationException("Invalid Npd details"));
			message = "EngineeringChangeNoticeRegister Updated Successfully";
			npdVO.setUpdatedBy(npdDTO.getCreatedBy());

		} else {

			String docId = npdRepo.getNpdDocId(npdDTO.getOrgId(), npdDTO.getFinYear(), npdDTO.getBranchCode(),
					screenCode);
			npdVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(npdDTO.getOrgId(), npdDTO.getFinYear(),
							npdDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			npdVO.setCreatedBy(npdDTO.getCreatedBy());
			npdVO.setUpdatedBy(npdDTO.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdatedNpdVOFromNpdDTO(npdDTO, npdVO);
		npdRepo.save(npdVO);
		Map<String, Object> response = new HashMap<>();
		response.put("npdVO", npdVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedNpdVOFromNpdDTO(NpdDTO npdDTO, NpdVO npdVO) {
		npdVO.setDocumentFormateNo(npdDTO.getDocumentFormateNo());
		npdVO.setNarration(npdDTO.getNarration());
		npdVO.setOrgId(npdDTO.getOrgId());
		npdVO.setBranch(npdDTO.getBranch());
		npdVO.setBranchCode(npdDTO.getBranchCode());
		npdVO.setFinYear(npdDTO.getFinYear());
		npdVO.setCreatedBy(npdDTO.getCreatedBy());

		if (ObjectUtils.isNotEmpty(npdDTO.getId())) {
			List<NpdDetailsVO> npdDetailsVO1 = npdDetailsRepo.findByNpdVO(npdVO);
			npdDetailsRepo.deleteAll(npdDetailsVO1);

		}

		List<NpdDetailsVO> npdDetailsVOs = new ArrayList<>();
		for (NpdDetailsDTO npdDetailsDTO : npdDTO.getNpdDetailsDTO()) {
			NpdDetailsVO npdDetailsVO = new NpdDetailsVO();
			npdDetailsVO.setDocumentRefNo(npdDetailsDTO.getDocumentRefNo());
			npdDetailsVO.setCustomer(npdDetailsDTO.getCustomer());
			npdDetailsVO.setPartNo(npdDetailsDTO.getPartNo());
			npdDetailsVO.setPartName(npdDetailsDTO.getPartName());
			npdDetailsVO.setCurrentDate(npdDetailsDTO.getCurrentDate());
			npdDetailsVO.setRevision(npdDetailsDTO.getRevision());
			npdDetailsVO.setApprovedBy(npdDetailsDTO.getApprovedBy());
			npdDetailsVO.setRemarks(npdDetailsDTO.getRemarks());

			npdDetailsVO.setNpdVO(npdVO);
			npdDetailsVOs.add(npdDetailsVO);
		}
		npdVO.setNpdDetailsVO(npdDetailsVOs);

	}

	@Override
	public List<NpdVO> getNpdByOrgId(Long orgId, String finYear, String branchCode) {

		return npdRepo.getNpdByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public NpdVO getNpdById(Long id) {

		return npdRepo.getNpdById(id);
	}

	@Override
	public String getNpdDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "NPD";
		return npdRepo.getNpdDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getEmployeeName(Long orgId) {
		Set<Object[]> finalInspectionReportDetails = npdRepo.getEmployeeName(orgId);
		return getEmployeeName(finalInspectionReportDetails);
	}

	private List<Map<String, Object>> getEmployeeName(Set<Object[]> finalInspectionReportDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : finalInspectionReportDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}

		return List1;
	}

	// Process

	@Override
	public Map<String, Object> createUpdateProcessNonConformanceReport(
			ProcessNonConformanceReportDTO processNonConformanceReportDTO) throws ApplicationException {
		ProcessNonConformanceReportVO processNonConformanceReportVO = new ProcessNonConformanceReportVO();
		String message;
		String screenCode = "PNCR";
		if (ObjectUtils.isNotEmpty(processNonConformanceReportDTO.getId())) {
			processNonConformanceReportVO = processNonConformanceReportRepo
					.findById(processNonConformanceReportDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Npd details"));
			message = "EngineeringChangeNoticeRegister Updated Successfully";
			processNonConformanceReportVO.setUpdatedBy(processNonConformanceReportDTO.getCreatedBy());

		} else {

			String docId = processNonConformanceReportRepo.getProcessNonConformanceReportDocId(
					processNonConformanceReportDTO.getOrgId(), processNonConformanceReportDTO.getFinYear(),
					processNonConformanceReportDTO.getBranchCode(), screenCode);
			processNonConformanceReportVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(processNonConformanceReportDTO.getOrgId(),
							processNonConformanceReportDTO.getFinYear(), processNonConformanceReportDTO.getBranchCode(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			processNonConformanceReportVO.setCreatedBy(processNonConformanceReportDTO.getCreatedBy());
			processNonConformanceReportVO.setUpdatedBy(processNonConformanceReportDTO.getCreatedBy());

			message = "ProcessNonConformanceReport Created Successfully";
		}
		createUpdatedProcessNonConformanceReportVOFromProcessNonConformanceReportDTO(processNonConformanceReportDTO,
				processNonConformanceReportVO);
		processNonConformanceReportRepo.save(processNonConformanceReportVO);
		Map<String, Object> response = new HashMap<>();
		response.put("processNonConformanceReportVO", processNonConformanceReportVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedProcessNonConformanceReportVOFromProcessNonConformanceReportDTO(
			ProcessNonConformanceReportDTO processNonConformanceReportDTO,
			ProcessNonConformanceReportVO processNonConformanceReportVO) throws ApplicationException {
		processNonConformanceReportVO.setQtyAvailable(processNonConformanceReportDTO.getQtyAvailable());
		processNonConformanceReportVO.setQtyDefective(processNonConformanceReportDTO.getQtyDefective());
		processNonConformanceReportVO.setBriefdescription(processNonConformanceReportDTO.getBriefdescription());
		processNonConformanceReportVO.setRootCause(processNonConformanceReportDTO.getRootCause());
		processNonConformanceReportVO.setDisPosition(processNonConformanceReportDTO.getDisPosition());
		processNonConformanceReportVO.setProcess(processNonConformanceReportDTO.getProcess());
		processNonConformanceReportVO.setResponsibility(processNonConformanceReportDTO.getResponsibility());
		processNonConformanceReportVO.setCorrectiveAction(processNonConformanceReportDTO.getCorrectiveAction());
		processNonConformanceReportVO.setPartNo(processNonConformanceReportDTO.getPartNo());
		processNonConformanceReportVO.setVerify(processNonConformanceReportDTO.getVerify());
		processNonConformanceReportVO.setPartType(processNonConformanceReportDTO.getPartType());
		processNonConformanceReportVO.setAdequacy(processNonConformanceReportDTO.getAdequacy());
		processNonConformanceReportVO.setCreated(processNonConformanceReportDTO.getCreated());

		validateTargetDate(processNonConformanceReportDTO.getTargetDate(), "Target Date");
		processNonConformanceReportVO.setTargetDate(processNonConformanceReportDTO.getTargetDate());

		processNonConformanceReportVO.setDate(processNonConformanceReportDTO.getDate());
		processNonConformanceReportVO
				.setActualDateOfCompletion(processNonConformanceReportDTO.getActualDateOfCompletion());
		processNonConformanceReportVO
				.setEffectivenessOfCorrective(processNonConformanceReportDTO.getEffectivenessOfCorrective());
		processNonConformanceReportVO.setDrawingNo(processNonConformanceReportDTO.getDrawingNo());
		processNonConformanceReportVO.setPartName(processNonConformanceReportDTO.getPartName());
		processNonConformanceReportVO.setDocumentFormateNo(processNonConformanceReportDTO.getDocumentFormateNo());
		processNonConformanceReportVO.setSignature(processNonConformanceReportDTO.getSignature());
		processNonConformanceReportVO.setNarration(processNonConformanceReportDTO.getNarration());

		processNonConformanceReportVO.setOrgId(processNonConformanceReportDTO.getOrgId());
		processNonConformanceReportVO.setBranch(processNonConformanceReportDTO.getBranch());
		processNonConformanceReportVO.setBranchCode(processNonConformanceReportDTO.getBranchCode());
		processNonConformanceReportVO.setCreatedBy(processNonConformanceReportDTO.getCreatedBy());
		processNonConformanceReportVO.setFinYear(processNonConformanceReportDTO.getFinYear());

	}

	private void validateTargetDate(LocalDate targetDate, String fieldName) throws ApplicationException {

		if (targetDate == null) {
			throw new ApplicationException(fieldName + " must not be null.");
		}

		LocalDate today = LocalDate.now();

		if (targetDate.isBefore(today)) {
			throw new ApplicationException(fieldName + " must be today or a future date.");
		}
	}

	@Override
	public List<ProcessNonConformanceReportVO> getAllProcessNonConformanceReportByOrgId(Long orgId, String finYear,
			String branchCode) {

		return processNonConformanceReportRepo.getAllProcessNonConformanceReportByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public ProcessNonConformanceReportVO getProcessNonConformanceReportById(Long id) {

		return processNonConformanceReportRepo.getProcessNonConformanceReportById(id);
	}

	@Override
	public String getProcessNonConformanceReportDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "PNCR";
		return processNonConformanceReportRepo.getProcessNonConformanceReportDocId(orgId, finYear, branchCode,
				screenCode);
	}

	// QAD Regsiter

	@Override
	public Map<String, Object> updateCreateQADRegister(QADRegisterDTO qadRegisterDTO) throws ApplicationException {
		QADRegisterVO qadRegisterVO = new QADRegisterVO();
		String message;
		String screenCode = "QAD";
		if (ObjectUtils.isNotEmpty(qadRegisterDTO.getId())) {
			qadRegisterVO = qadRegisterRepo.findById(qadRegisterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Sales details"));
			qadRegisterVO.setUpdatedBy(qadRegisterDTO.getCreatedBy());
			getQADRegisterVOFromQADRegisterDTO(qadRegisterDTO, qadRegisterVO);
			message = "QAD Register Updated Successfully";
		} else {
			String docId = qadRegisterRepo.getQADRegisterByDocId(qadRegisterDTO.getOrgId(), qadRegisterDTO.getFinYear(),
					qadRegisterDTO.getBranchCode(), screenCode);
			qadRegisterVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(qadRegisterDTO.getOrgId(),
							qadRegisterDTO.getFinYear(), qadRegisterDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			qadRegisterVO.setCreatedBy(qadRegisterDTO.getCreatedBy());
			qadRegisterVO.setUpdatedBy(qadRegisterDTO.getCreatedBy());

			message = "ProcessNonConformanceReport Created Successfully";
		}
		getQADRegisterVOFromQADRegisterDTO(qadRegisterDTO, qadRegisterVO);
		message = "QAD Register Created Successfully";
		qadRegisterRepo.save(qadRegisterVO);
		Map<String, Object> response = new HashMap<>();
		response.put("qadRegisterVO", qadRegisterVO);
		response.put("message", message);
		return response;
	}

	private void getQADRegisterVOFromQADRegisterDTO(QADRegisterDTO qadRegisterDTO, QADRegisterVO qadRegisterVO) {
		qadRegisterVO.setDocname(qadRegisterDTO.getDocname());
		qadRegisterVO.setDocformatno(qadRegisterDTO.getDocformatno());
		qadRegisterVO.setApprovedby(qadRegisterDTO.getApprovedby());
		qadRegisterVO.setNarration(qadRegisterDTO.getNarration());
		qadRegisterVO.setCreatedBy(qadRegisterDTO.getCreatedBy());
		qadRegisterVO.setCancelRemarks(qadRegisterDTO.getCancelRemarks());

		qadRegisterVO.setBranch(qadRegisterDTO.getBranch());
		qadRegisterVO.setBranchCode(qadRegisterDTO.getBranchCode());
		qadRegisterVO.setFinYear(qadRegisterDTO.getFinYear());
		qadRegisterVO.setOrgId(qadRegisterDTO.getOrgId());

		qadRegisterVO.setSummary(qadRegisterDTO.getSummary());

		if (ObjectUtils.isNotEmpty(qadRegisterDTO.getId())) {

			List<QADRegisterDetailsVO> oldDetails = qADRegisterDetailsRepo.findByqadRegisterVO(qadRegisterVO);

			if (ObjectUtils.isNotEmpty(oldDetails)) {
				qADRegisterDetailsRepo.deleteAll(oldDetails);
			}
		}

		List<QADRegisterDetailsVO> qADRegisterDetailsVOs = new ArrayList<>();

		for (QADRegisterDetailsDTO qADRegisterDetailsDTO : qadRegisterDTO.getQADRegisterDetailsDTO()) {

			QADRegisterDetailsVO qADRegisterDetailsVO = new QADRegisterDetailsVO();
			qADRegisterDetailsVO.setDocumentNo(qADRegisterDetailsDTO.getDocumentNo());
			qADRegisterDetailsVO.setOlddocissue(qADRegisterDetailsDTO.getOlddocissue());
			qADRegisterDetailsVO.setOlddocrev(qADRegisterDetailsDTO.getOlddocrev());
			qADRegisterDetailsVO.setNewdocissue(qADRegisterDetailsDTO.getNewdocissue());
			qADRegisterDetailsVO.setNewdocrev(qADRegisterDetailsDTO.getNewdocrev());
			qADRegisterDetailsVO.setAdmendmentdetails(qADRegisterDetailsDTO.getAdmendmentdetails());
			qADRegisterDetailsVO.setReasonforadmendment(qADRegisterDetailsDTO.getReasonforadmendment());
			qADRegisterDetailsVO.setRemarks(qADRegisterDetailsDTO.getRemarks());
			qADRegisterDetailsVO.setReviewedby(qADRegisterDetailsDTO.getReviewedby());

			qADRegisterDetailsVO.setQadRegisterVO(qadRegisterVO);

			qADRegisterDetailsVOs.add(qADRegisterDetailsVO);
		}

		qadRegisterVO.setQadRegisterDetailsVO(qADRegisterDetailsVOs);

	}

	@Override
	public List<QADRegisterVO> getAllQADRegisterByOrgId(Long orgId, String finYear, String branchCode) {

		return qadRegisterRepo.getAllQADRegisterByOrgId(orgId, finYear, branchCode);

	}

	@Override
	public QADRegisterVO getQADRegisterById(Long id) {

		return qadRegisterRepo.getQADRegisterById(id);
	}

	@Override
	public String getQADRegisterByDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "QAD";
		return qadRegisterRepo.getQADRegisterByDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getEngineeringChangeNoticeRegisterReport(Long orgId, String fromdate,
			String todate) {
		Set<Object[]> engineeringChangeNoticeRegisterReport = engineeringChangeNoticeRegisterRepo
				.getEngineeringChangeNoticeRegisterReport(orgId, fromdate, todate);
		return getEngineeringChangeNoticeRegisterReport(engineeringChangeNoticeRegisterReport);
	}

	private List<Map<String, Object>> getEngineeringChangeNoticeRegisterReport(
			Set<Object[]> engineeringChangeNoticeRegisterReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : engineeringChangeNoticeRegisterReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("engineeringchangenoticeregisterid", ch[0] != null ? ch[0].toString() : "");
			map.put("orgid", ch[1] != null ? ch[1].toString() : "");
			map.put("docformatno", ch[2] != null ? ch[2].toString() : "");
			map.put("customer", ch[3] != null ? ch[3].toString() : "");
			map.put("daterev", ch[4] != null ? ch[4].toString() : "");
			map.put("detailsofrevision", ch[5] != null ? ch[5].toString() : "");
			map.put("encrefno", ch[6] != null ? ch[6].toString() : "");
			map.put("intecno", ch[7] != null ? ch[7].toString() : "");
			map.put("oldrev", ch[8] != null ? ch[8].toString() : "");
			map.put("oldrevdate", ch[9] != null ? ch[9].toString() : "");
			map.put("partname", ch[10] != null ? ch[10].toString() : "");
			map.put("reasonofrivision", ch[11] != null ? ch[11].toString() : "");
			map.put("verified", ch[12] != null ? ch[12].toString() : "");

			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<IncomingMaterialResposeDTO> getIncomingMaterialRespose(MultipartFile file)
			throws EncryptedDocumentException, IOException, ApplicationException {
		List<IncomingMaterialResposeDTO> materialRespose = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
			Row headerRow = sheet.getRow(0);
			List<String> errorMessages = new ArrayList<>();
			// Validate header
			for (Row row : sheet) {

				if (row.getRowNum() < 7) {
					continue; // Skip this iteration
				}
				try {
					String slno = getStringCellValue1(row.getCell(0));
					String parameter = getStringCellValue1(row.getCell(1));
					String specification = getStringCellValue1(row.getCell(2));
					String lsl = getStringCellValue1(row.getCell(3));
					String usl = getStringCellValue1(row.getCell(4));
					String obValue = getStringCellValue1(row.getCell(5));
					String s1 = getStringCellValue1(row.getCell(6));
					String s2 = getStringCellValue1(row.getCell(7));
					String s3 = getStringCellValue1(row.getCell(8));
					String s4 = getStringCellValue1(row.getCell(9));
					String s5 = getStringCellValue1(row.getCell(10));
					String s6 = getStringCellValue1(row.getCell(11));
					String s7 = getStringCellValue1(row.getCell(12));
					String s8 = getStringCellValue1(row.getCell(13));
					String remarks = getStringCellValue1(row.getCell(14));

					// Create and populate TrailBalanceVO object
					IncomingMaterialResposeDTO dto = new IncomingMaterialResposeDTO();
					dto.setSlno(slno);
					dto.setParameter(parameter);
					dto.setSpecifications(specification);
					dto.setLsl(lsl);
					dto.setUsl(usl);
					dto.setObservedValue(obValue);
					dto.setSample1(s1);
					dto.setSample2(s2);
					dto.setSample3(s3);
					dto.setSample4(s4);
					dto.setSample5(s5);
					dto.setSample6(s6);
					dto.setSample7(s7);
					dto.setSample8(s8);
					dto.setRemarks(remarks);
					materialRespose.add(dto);
				} catch (Exception e) {
					errorMessages.add("Row No " + (row.getRowNum() + 1) + ": " + e.getMessage());
					String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());

				}
			}
			if (!errorMessages.isEmpty()) {
				throw new ApplicationException("Excel upload failed. " + String.join(", ", errorMessages)
						+ ". Except for these lines, all other data has been uploaded.");
			}

		} catch (IOException e) {
			throw new ApplicationException(
					"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
		}

		return materialRespose;
	}

	private String getStringCellValue1(Cell cell) {

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
			} else {
				double numericValue = cell.getNumericCellValue();
				if (numericValue == (int) numericValue) {
					return String.valueOf((int) numericValue);
				} else {
					return BigDecimal.valueOf(numericValue).toPlainString();
				}
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	private boolean isRowEmpty1(Row row) {
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<InprocessResponseDTO> getInprocessResponse(MultipartFile file)
			throws EncryptedDocumentException, IOException, ApplicationException {
		List<InprocessResponseDTO> inprocessResponse = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
			Row headerRow = sheet.getRow(0);
			List<String> errorMessages = new ArrayList<>();
			// Validate header
			for (Row row : sheet) {

				if (row.getRowNum() < 7) {
					continue; // Skip this iteration
				}
				try {
					String slno = getStringCellValue1(row.getCell(0));
					String characteristics = getStringCellValue1(row.getCell(1));
					String methodOfInspection = getStringCellValue1(row.getCell(2));
					String specification = getStringCellValue1(row.getCell(3));
					String lsl = getStringCellValue1(row.getCell(4));
					String usl = getStringCellValue1(row.getCell(5));
					String s1 = getStringCellValue1(row.getCell(6));
					String s2 = getStringCellValue1(row.getCell(7));
					String s3 = getStringCellValue1(row.getCell(8));
					String s4 = getStringCellValue1(row.getCell(9));
					String s5 = getStringCellValue1(row.getCell(10));
					String s6 = getStringCellValue1(row.getCell(11));
					String s7 = getStringCellValue1(row.getCell(12));
					String s8 = getStringCellValue1(row.getCell(13));
					String remarks = getStringCellValue1(row.getCell(14));

					// Create and populate TrailBalanceVO object
					InprocessResponseDTO dto = new InprocessResponseDTO();
					dto.setSlno(slno);
					dto.setCharactericstics(characteristics);
					dto.setMethodOfInspection(methodOfInspection);
					dto.setSpecification(specification);
					dto.setLsl(lsl);
					dto.setUsl(usl);
					dto.setS1(s1);
					dto.setS2(s2);
					dto.setS3(s3);
					dto.setS4(s4);
					dto.setS5(s5);
					dto.setS6(s6);
					dto.setS7(s7);
					dto.setS8(s8);
					dto.setRemarks(remarks);
					inprocessResponse.add(dto);
				} catch (Exception e) {
					errorMessages.add("Row No " + (row.getRowNum() + 1) + ": " + e.getMessage());
					String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());

				}
			}
			if (!errorMessages.isEmpty()) {
				throw new ApplicationException("Excel upload failed. " + String.join(", ", errorMessages)
						+ ". Except for these lines, all other data has been uploaded.");
			}

		} catch (IOException e) {
			throw new ApplicationException(
					"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
		}

		return inprocessResponse;
	}

	private String getStringCellValue(Cell cell) {

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
			} else {
				double numericValue = cell.getNumericCellValue();
				if (numericValue == (int) numericValue) {
					return String.valueOf((int) numericValue);
				} else {
					return BigDecimal.valueOf(numericValue).toPlainString();
				}
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	private boolean isRowEmpty(Row row) {
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<SettingResposeDTO> getSettingResponse(MultipartFile files)
			throws EncryptedDocumentException, IOException, ApplicationException {
		List<SettingResposeDTO> settingResponse = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
			Row headerRow = sheet.getRow(0);
			List<String> errorMessages = new ArrayList<>();
			// Validate header
			for (Row row : sheet) {

				if (row.getRowNum() < 7) {
					continue; // Skip this iteration
				}
				try {
					String slno = getStringCellValue1(row.getCell(0));
					String characteristics = getStringCellValue1(row.getCell(1));
					String specification = getStringCellValue1(row.getCell(2));
					String methodOfInspection = getStringCellValue1(row.getCell(3));
					String lsl = getStringCellValue1(row.getCell(4));
					String usl = getStringCellValue1(row.getCell(5));
					String setter1 = getStringCellValue1(row.getCell(6));
					String quality1 = getStringCellValue1(row.getCell(7));
					String remarks = getStringCellValue1(row.getCell(8));

					// Create and populate TrailBalanceVO object
					SettingResposeDTO dto = new SettingResposeDTO();
					dto.setSlno(slno);
					dto.setCharacteristics(characteristics);
					dto.setMethodOfInspection(specification);
					dto.setSpecification(methodOfInspection);
					dto.setLsl(lsl);
					dto.setUsl(usl);
					dto.setSetter1(setter1);
					dto.setQuality1(quality1);
					dto.setRemarks(remarks);
					settingResponse.add(dto);
				} catch (Exception e) {
					errorMessages.add("Row No " + (row.getRowNum() + 1) + ": " + e.getMessage());
					String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());

				}
			}
			if (!errorMessages.isEmpty()) {
				throw new ApplicationException("Excel upload failed. " + String.join(", ", errorMessages)
						+ ". Except for these lines, all other data has been uploaded.");
			}

		} catch (IOException e) {
			throw new ApplicationException(
					"Failed to process file: " + files.getOriginalFilename() + " - " + e.getMessage());
		}

		return settingResponse;
	}

	private String getStringCellValue2(Cell cell) {

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
			} else {
				double numericValue = cell.getNumericCellValue();
				if (numericValue == (int) numericValue) {
					return String.valueOf((int) numericValue);
				} else {
					return BigDecimal.valueOf(numericValue).toPlainString();
				}
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}

//	private boolean isRowEmpty(Row row) {
//		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
//			Cell cell = row.getCell(cellNum);
//			if (cell != null && cell.getCellType() != CellType.BLANK) {
//				return false;
//			}
//		}
//		return true;
	}

	@Override
	public List<SampleResponseDTO> getSampleResponse(MultipartFile files)
			throws EncryptedDocumentException, IOException, ApplicationException {
		List<SampleResponseDTO> sampleResponse = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
			Row headerRow = sheet.getRow(0);
			List<String> errorMessages = new ArrayList<>();
			// Validate header
			for (Row row : sheet) {

				if (row.getRowNum() < 7) {
					continue; // Skip this iteration
				}
				try {
					String slno = getStringCellValue1(row.getCell(0));
					String characteristics = getStringCellValue1(row.getCell(1));
					String methodOfInspection = getStringCellValue1(row.getCell(2));
					String specification = getStringCellValue1(row.getCell(3));
					String lsl = getStringCellValue1(row.getCell(4));
					String usl = getStringCellValue1(row.getCell(5));
					String s1 = getStringCellValue1(row.getCell(6));
					String s2 = getStringCellValue1(row.getCell(7));
					String s3 = getStringCellValue1(row.getCell(8));
					String s4 = getStringCellValue1(row.getCell(9));
					String s5 = getStringCellValue1(row.getCell(10));
					String operator1 = getStringCellValue1(row.getCell(11));
					String o1 = getStringCellValue1(row.getCell(12));
					String o2 = getStringCellValue1(row.getCell(13));
					String o3 = getStringCellValue1(row.getCell(14));
					String o4 = getStringCellValue1(row.getCell(15));
					String o5 = getStringCellValue1(row.getCell(16));
					String status = getStringCellValue1(row.getCell(17));

					// Create and populate TrailBalanceVO object
					SampleResponseDTO dto = new SampleResponseDTO();
					dto.setSlno(slno);
					dto.setCharacteristics(characteristics);
					dto.setMethodOfInspection(specification);
					dto.setSpecification(methodOfInspection);
					dto.setLsl(lsl);
					dto.setUsl(usl);
					dto.setS1(s1);
					dto.setS2(s2);
					dto.setS3(s3);
					dto.setS4(s4);
					dto.setS5(s5);
					dto.setOperator1(operator1);
					dto.setO2(o2);
					dto.setO3(o3);
					dto.setO4(o4);
					dto.setO5(o5);
					dto.setStatus(status);
					sampleResponse.add(dto);
				} catch (Exception e) {
					errorMessages.add("Row No " + (row.getRowNum() + 1) + ": " + e.getMessage());
					String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());

				}
			}
			if (!errorMessages.isEmpty()) {
				throw new ApplicationException("Excel upload failed. " + String.join(", ", errorMessages)
						+ ". Except for these lines, all other data has been uploaded.");
			}

		} catch (IOException e) {
			throw new ApplicationException(
					"Failed to process file: " + files.getOriginalFilename() + " - " + e.getMessage());
		}

		return sampleResponse;
	}

	@Override
	public Map<String, Object> createUpdateQualityDocumentChangeRecord(
			QualityDocumentChangeRecordDTO qualityDocumentChangeRecordDTO) throws ApplicationException {
		QualityDocumentChangeRecordVO qualityDocumentChangeRecordVO = new QualityDocumentChangeRecordVO();
		String message;
		String screenCode = "QDCR";
		if (ObjectUtils.isNotEmpty(qualityDocumentChangeRecordDTO.getId())) {
			qualityDocumentChangeRecordVO = qualityDocumentChangeRecordRepo
					.findById(qualityDocumentChangeRecordDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Npd details"));
			
			message = "EngineeringChangeNoticeRegister Updated Successfully";
			qualityDocumentChangeRecordVO.setUpdatedBy(qualityDocumentChangeRecordDTO.getCreatedBy());

		} else {

			String docId = qualityDocumentChangeRecordRepo.getQualityDocumentChangeRecordDocId(
					qualityDocumentChangeRecordDTO.getOrgId(), qualityDocumentChangeRecordDTO.getFinYear(),
					qualityDocumentChangeRecordDTO.getBranchCode(), screenCode);
			qualityDocumentChangeRecordVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(qualityDocumentChangeRecordDTO.getOrgId(),
							qualityDocumentChangeRecordDTO.getFinYear(), qualityDocumentChangeRecordDTO.getBranchCode(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			qualityDocumentChangeRecordVO.setCreatedBy(qualityDocumentChangeRecordDTO.getCreatedBy());
			qualityDocumentChangeRecordVO.setUpdatedBy(qualityDocumentChangeRecordDTO.getCreatedBy());

			message = "ProcessNonConformanceReport Created Successfully";
		}
		createUpdatedQualityDocumentChangeRecordVOFromQualityDocumentChangeRecordDTO(qualityDocumentChangeRecordDTO,
				qualityDocumentChangeRecordVO);
		qualityDocumentChangeRecordRepo.save(qualityDocumentChangeRecordVO);
		Map<String, Object> response = new HashMap<>();
		response.put("qualityDocumentChangeRecordVO", qualityDocumentChangeRecordVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedQualityDocumentChangeRecordVOFromQualityDocumentChangeRecordDTO(
			QualityDocumentChangeRecordDTO qualityDocumentChangeRecordDTO,
			QualityDocumentChangeRecordVO qualityDocumentChangeRecordVO) {
		qualityDocumentChangeRecordVO.setName(qualityDocumentChangeRecordDTO.getName());
		qualityDocumentChangeRecordVO.setDesignation(qualityDocumentChangeRecordDTO.getDesignation());
		qualityDocumentChangeRecordVO.setDocumentDescription(qualityDocumentChangeRecordDTO.getDocumentDescription());
		qualityDocumentChangeRecordVO.setDocumentRefNo(qualityDocumentChangeRecordDTO.getDocumentRefNo());
		qualityDocumentChangeRecordVO
				.setCurrentRevisionStatus(qualityDocumentChangeRecordDTO.getCurrentRevisionStatus());
		qualityDocumentChangeRecordVO.setRecordDate(qualityDocumentChangeRecordDTO.getRecordDate());
		qualityDocumentChangeRecordVO
				.setDetailsOfChangeRequired(qualityDocumentChangeRecordDTO.getDetailsOfChangeRequired());
		qualityDocumentChangeRecordVO.setReasonForChange(qualityDocumentChangeRecordDTO.getReasonForChange());
		qualityDocumentChangeRecordVO.setChanges(qualityDocumentChangeRecordDTO.getChanges());
		qualityDocumentChangeRecordVO.setApprovedBy(qualityDocumentChangeRecordDTO.getApprovedBy());
		qualityDocumentChangeRecordVO
				.setNewDocumentReleaseDate(qualityDocumentChangeRecordDTO.getNewDocumentReleaseDate());
		qualityDocumentChangeRecordVO.setDocumentFormateNo(qualityDocumentChangeRecordDTO.getDocumentFormateNo());
		qualityDocumentChangeRecordVO.setSignature(qualityDocumentChangeRecordDTO.getSignature());
		qualityDocumentChangeRecordVO.setNarration(qualityDocumentChangeRecordDTO.getNarration());
		qualityDocumentChangeRecordVO.setOrgId(qualityDocumentChangeRecordDTO.getOrgId());
		qualityDocumentChangeRecordVO.setBranch(qualityDocumentChangeRecordDTO.getBranch());
		qualityDocumentChangeRecordVO.setBranchCode(qualityDocumentChangeRecordDTO.getBranchCode());
		qualityDocumentChangeRecordVO.setCreatedBy(qualityDocumentChangeRecordDTO.getCreatedBy());
		qualityDocumentChangeRecordVO.setCancelRemarks(qualityDocumentChangeRecordDTO.getCancelRemarks());
		qualityDocumentChangeRecordVO.setFinYear(qualityDocumentChangeRecordDTO.getFinYear());

	}

	@Override
	public List<QualityDocumentChangeRecordVO> getAllQualityDocumentChangeRecordByOrgId(Long orgId, String finYear,
			String branchCode) {

		return qualityDocumentChangeRecordRepo.getAllQualityDocumentChangeRecordByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public QualityDocumentChangeRecordVO getAllQualityDocumentChangeRecordById(Long id) {

		return qualityDocumentChangeRecordRepo.getAllQualityDocumentChangeRecordById(id);
	}

	@Override
	public String getQualityDocumentChangeRecordDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "QDCR";
		return qualityDocumentChangeRecordRepo.getQualityDocumentChangeRecordDocId(orgId, finYear, branchCode,
				screenCode);
	}

	@Override
	public List<Map<String, Object>> getEmployeeNameAndDesignation(Long orgId) {
		Set<Object[]> chType = qualityDocumentChangeRecordRepo.getEmployeeNameAndDesignation(orgId);
		return getEmployeeNameAndDesignation(chType);
	}

	private List<Map<String, Object>> getEmployeeNameAndDesignation(Set<Object[]> chType) {
		List<Map<String, Object>> List3 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeename", ch[0] != null ? ch[0].toString() : "");
			map.put("designation", ch[1] != null ? ch[1].toString() : "");

			List3.add(map);
		}
		return List3;
	}

	@Override
	public List<Map<String, Object>> getQualityDocumentChangeRecordReport(Long orgId, String branchCode,
			String fromDate, String toDate) {
		Set<Object[]> chType = qualityDocumentChangeRecordRepo.getQualityDocumentChangeRecordReport(orgId, branchCode,
				fromDate, toDate);
		return getQualityDocumentChangeRecordReport(chType);
	}

	private List<Map<String, Object>> getQualityDocumentChangeRecordReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("qualityDocumentChangeRecordId", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("changes", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("currentRevisionStatus", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("designation", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("detailsOfChangeRequired", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("docDate", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("docId", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("documentDescription", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("documentFormateNo", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("documentRefNo", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("name", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("reasonForChange", ch[11] != null ? ch[11].toString() : ""); // 11
			map.put("recordDate", ch[12] != null ? ch[12].toString() : ""); // 12
			map.put("signature", ch[13] != null ? ch[13].toString() : ""); // 13

			List1.add(map);
		}
		return List1;
	}

	// ECN

	@Override
	public Map<String, Object> createUpdateEcnApprovalRecord(EcnApprovalRecordDTO ecnApprovalRecordDTO)
			throws ApplicationException {
		EcnApprovalRecordVO ecnApprovalRecordVO = new EcnApprovalRecordVO();
		String message;
		String screenCode = "ECNR";
		if (ObjectUtils.isNotEmpty(ecnApprovalRecordDTO.getId())) {
			ecnApprovalRecordVO = ecnApprovalRecordRepo.findById(ecnApprovalRecordDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Npd details"));
			message = "EngineeringChangeNoticeRegister Updated Successfully";
			ecnApprovalRecordVO.setUpdatedBy(ecnApprovalRecordDTO.getCreatedBy());

		} else {

			String docId = ecnApprovalRecordRepo.getEcnApprovalRecordDocId(ecnApprovalRecordDTO.getOrgId(),
					ecnApprovalRecordDTO.getFinYear(), ecnApprovalRecordDTO.getBranchCode(), screenCode);
			ecnApprovalRecordVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(ecnApprovalRecordDTO.getOrgId(),
							ecnApprovalRecordDTO.getFinYear(), ecnApprovalRecordDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			ecnApprovalRecordVO.setCreatedBy(ecnApprovalRecordDTO.getCreatedBy());
			ecnApprovalRecordVO.setUpdatedBy(ecnApprovalRecordDTO.getCreatedBy());

			message = "ProcessNonConformanceReport Created Successfully";
		}
		createUpdatedEcnApprovalRecordVOFromEcnApprovalRecordDTO(ecnApprovalRecordDTO, ecnApprovalRecordVO);
		ecnApprovalRecordRepo.save(ecnApprovalRecordVO);
		Map<String, Object> response = new HashMap<>();
		response.put("ecnApprovalRecordVO", ecnApprovalRecordVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedEcnApprovalRecordVOFromEcnApprovalRecordDTO(EcnApprovalRecordDTO ecnApprovalRecordDTO,
			EcnApprovalRecordVO ecnApprovalRecordVO) {
		ecnApprovalRecordVO.setCustomer(ecnApprovalRecordDTO.getCustomer());
		ecnApprovalRecordVO.setPartName(ecnApprovalRecordDTO.getPartName());
		ecnApprovalRecordVO.setPartNo(ecnApprovalRecordDTO.getPartNo());
		ecnApprovalRecordVO.setDrawingNo(ecnApprovalRecordDTO.getDrawingNo());
		ecnApprovalRecordVO.setCurrentRevisionId(ecnApprovalRecordDTO.getCurrentRevisionId());
		ecnApprovalRecordVO.setCurrentRevisionDate(ecnApprovalRecordDTO.getCurrentRevisionDate());
		ecnApprovalRecordVO.setOldRev(ecnApprovalRecordDTO.getOldRev());
		ecnApprovalRecordVO.setDetailsOfRevision(ecnApprovalRecordDTO.getDetailsOfRevision());
		ecnApprovalRecordVO.setReasonForRevision(ecnApprovalRecordDTO.getReasonForRevision());
		ecnApprovalRecordVO.setRemarks(ecnApprovalRecordDTO.getRemarks());
		ecnApprovalRecordVO.setPreparedBy(ecnApprovalRecordDTO.getPreparedBy());
		ecnApprovalRecordVO.setDepartmentP(ecnApprovalRecordDTO.getDepartmentP());
		ecnApprovalRecordVO.setStageDrawingsModifiedBy(ecnApprovalRecordDTO.getStageDrawingsModifiedBy());
		ecnApprovalRecordVO.setDepartmentS(ecnApprovalRecordDTO.getDepartmentS());
		ecnApprovalRecordVO.setCheckedBy(ecnApprovalRecordDTO.getCheckedBy());
		ecnApprovalRecordVO.setDepartmentC(ecnApprovalRecordDTO.getDepartmentC());
		ecnApprovalRecordVO.setStatusC(ecnApprovalRecordDTO.getStatusC());
		ecnApprovalRecordVO.setVerifiedBy(ecnApprovalRecordDTO.getVerifiedBy());
		ecnApprovalRecordVO.setDepartmentV(ecnApprovalRecordDTO.getDepartmentV());
		ecnApprovalRecordVO.setStatusV(ecnApprovalRecordDTO.getStatusV());
		ecnApprovalRecordVO.setAprrovedBy(ecnApprovalRecordDTO.getAprrovedBy());
		ecnApprovalRecordVO.setDepartmentA(ecnApprovalRecordDTO.getDepartmentA());
		ecnApprovalRecordVO.setStatusA(ecnApprovalRecordDTO.getStatusA());
		ecnApprovalRecordVO.setDocumentFormateNo(ecnApprovalRecordDTO.getDocumentFormateNo());
		ecnApprovalRecordVO.setOrgId(ecnApprovalRecordDTO.getOrgId());
		ecnApprovalRecordVO.setBranch(ecnApprovalRecordDTO.getBranch());
		ecnApprovalRecordVO.setBranchCode(ecnApprovalRecordDTO.getBranchCode());
		ecnApprovalRecordVO.setCreatedBy(ecnApprovalRecordDTO.getCreatedBy());
		ecnApprovalRecordVO.setFinYear(ecnApprovalRecordDTO.getFinYear());

	}

	@Override
	public List<EcnApprovalRecordVO> getAllEcnApprovalRecordByOrgId(Long orgId, String finYear, String branchCode) {

		return ecnApprovalRecordRepo.getAllEcnApprovalRecordByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public EcnApprovalRecordVO getAllEcnApprovalRecordById(Long id) {

		return ecnApprovalRecordRepo.getAllEcnApprovalRecordById(id);
	}

	@Override
	public String getEcnApprovalRecordDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "ECNAR";
		return ecnApprovalRecordRepo.getEcnApprovalRecordDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getEcnApprovalRecordReport(Long orgId, String branchCode, String fromDate,
			String toDate) {
		Set<Object[]> chType = ecnApprovalRecordRepo.getEcnApprovalRecordReport(orgId, branchCode, fromDate, toDate);
		return getEcnApprovalRecordReport(chType);
	}

	private List<Map<String, Object>> getEcnApprovalRecordReport(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("ecnapprovalrecordid", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("currentRevisionDate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("currentRevisionId", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("customer", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("departmentA", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("departmentC", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("departmentP", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("departments", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("departmentV", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("detailsOfRevision", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("documentFormateNo", ch[10] != null ? ch[10].toString() : ""); // 10
			map.put("drawingNo", ch[11] != null ? ch[11].toString() : ""); // 11
			map.put("finYear", ch[12] != null ? ch[12].toString() : ""); // 12
			map.put("oldRev", ch[13] != null ? ch[13].toString() : ""); // 13
			map.put("orgId", ch[14] != null ? ch[14].toString() : ""); // 14
			map.put("partName", ch[15] != null ? ch[15].toString() : ""); // 15
			map.put("partNo", ch[16] != null ? ch[16].toString() : ""); // 16
			map.put("preparedBy", ch[17] != null ? ch[17].toString() : ""); // 17
			map.put("resonforrevision", ch[18] != null ? ch[18].toString() : ""); // 18
			map.put("remarks", ch[19] != null ? ch[19].toString() : ""); // 19
			map.put("tagedrawingsmodifiedby", ch[20] != null ? ch[20].toString() : ""); // 20
			map.put("statusA", ch[21] != null ? ch[21].toString() : ""); // 21
			map.put("statusC", ch[22] != null ? ch[22].toString() : ""); // 22
			map.put("statusV", ch[23] != null ? ch[23].toString() : ""); // 23
			map.put("verifiedBy", ch[24] != null ? ch[24].toString() : ""); // 27

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDrawingNo(Long orgId, String partNo) {
		Set<Object[]> chType = ecnApprovalRecordRepo.getDrawingNo(orgId, partNo);
		return getDrawingNo(chType);
	}

	private List<Map<String, Object>> getDrawingNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingNo", ch[0] != null ? ch[0].toString() : ""); // 0
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDrawingOldRevNo(Long orgId, String drawingNo) {
		Set<Object[]> chType = ecnApprovalRecordRepo.getDrawingOldRevNo(orgId, drawingNo);
		return getDrawingOldRevNo(chType);
	}

	private List<Map<String, Object>> getDrawingOldRevNo(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("currentRevNo", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("currentRevDate", ch[1] != null ? ch[1].toString() : ""); // 0
			map.put("oldRevNo", ch[2] != null ? ch[2].toString() : ""); // 0
			List1.add(map);
		}
		return List1;
	}

	//

	@Override
	public Map<String, Object> updateCreateNCProductRegister(NcProductRegisterDTO ncProductRegisterDTO)
			throws ApplicationException {
		NcProductRegisterVO ncProductRegisterVO = new NcProductRegisterVO();
		String message;
		String screenCode = "NCPR";
		if (ObjectUtils.isNotEmpty(ncProductRegisterDTO.getId())) {
			ncProductRegisterVO = ncProductRegisterRepo.findById(ncProductRegisterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid NC Product Register details"));
			ncProductRegisterVO.setUpdatedBy(ncProductRegisterDTO.getCreatedBy());
			getNCProductRegisterVOFromNCProductRegisterDTO(ncProductRegisterDTO, ncProductRegisterVO);
			message = "NC Product Register Updated Successfully";
		} else {

			String docId = ncProductRegisterRepo.getNcProductRegisterDocId(ncProductRegisterDTO.getOrgId(),
					ncProductRegisterDTO.getFinYear(), ncProductRegisterDTO.getBranchCode(), screenCode);

			ncProductRegisterVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(ncProductRegisterDTO.getOrgId(),
							ncProductRegisterDTO.getFinYear(), ncProductRegisterDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			ncProductRegisterVO.setCreatedBy(ncProductRegisterDTO.getCreatedBy());
			ncProductRegisterVO.setUpdatedBy(ncProductRegisterDTO.getCreatedBy());
			getNCProductRegisterVOFromNCProductRegisterDTO(ncProductRegisterDTO, ncProductRegisterVO);
			message = "Customer Invoice Created Successfully";
		}
		ncProductRegisterRepo.save(ncProductRegisterVO);
		Map<String, Object> response = new HashMap<>();
		response.put("ncProductRegisterVO", ncProductRegisterVO);
		response.put("message", message);
		return response;
	}

	private void getNCProductRegisterVOFromNCProductRegisterDTO(NcProductRegisterDTO ncProductRegisterDTO,
			NcProductRegisterVO ncProductRegisterVO) {

		ncProductRegisterVO.setId(ncProductRegisterDTO.getId());
		ncProductRegisterVO.setDocNo(ncProductRegisterDTO.getDocNo());

		ncProductRegisterVO.setCreatedBy(ncProductRegisterDTO.getCreatedBy());
		ncProductRegisterVO.setModifiedBy(ncProductRegisterDTO.getModifiedBy());
		ncProductRegisterVO.setUpdatedBy(ncProductRegisterDTO.getUpdatedBy());
		ncProductRegisterVO.setOrgId(ncProductRegisterDTO.getOrgId());
		ncProductRegisterVO.setBranch(ncProductRegisterDTO.getBranch());
		ncProductRegisterVO.setBranchCode(ncProductRegisterDTO.getBranchCode());
		ncProductRegisterVO.setFinYear(ncProductRegisterDTO.getFinYear());

		if (ncProductRegisterDTO.getId() != null) {
			List<NcProductRegisterDetailsVO> list = ncProductRegisterDetailsRepo
					.findByNcProductRegisterVO(ncProductRegisterVO);

			ncProductRegisterDetailsRepo.deleteAll(list);

		}

		List<NcProductRegisterDetailsVO> ncProductRegisterDetailsVOs = new ArrayList<>();
		for (NcProductRegisterDetailsDTO ncProductRegisterDetailsDTO : ncProductRegisterDTO
				.getNcProductRegisterDetailsDTO()) {
			NcProductRegisterDetailsVO ncProductRegisterDetailsVO = new NcProductRegisterDetailsVO();
			ncProductRegisterDetailsVO.setId(ncProductRegisterDetailsDTO.getId());
			ncProductRegisterDetailsVO.setDate(ncProductRegisterDetailsDTO.getDate());
			ncProductRegisterDetailsVO.setStage(ncProductRegisterDetailsDTO.getStage());
			ncProductRegisterDetailsVO.setPartNo(ncProductRegisterDetailsDTO.getPartNo());
			ncProductRegisterDetailsVO.setPartDescription(ncProductRegisterDetailsDTO.getPartDescription());
			ncProductRegisterDetailsVO.setProcessDescription(ncProductRegisterDetailsDTO.getProcessDescription());
			ncProductRegisterDetailsVO
					.setDetailsOfNonConformance(ncProductRegisterDetailsDTO.getDetailsOfNonConformance());
			ncProductRegisterDetailsVO.setNcQuantity(ncProductRegisterDetailsDTO.getNcQuantity());
			ncProductRegisterDetailsVO.setUnit(ncProductRegisterDetailsDTO.getUnit());
			ncProductRegisterDetailsVO.setCorrectiveaction(ncProductRegisterDetailsDTO.getCorrectiveaction());
			ncProductRegisterDetailsVO.setCapaRef(ncProductRegisterDetailsDTO.getCapaRef());
			ncProductRegisterDetailsVO.setSignature(ncProductRegisterDetailsDTO.getSignature());
			ncProductRegisterDetailsVO.setRemarks(ncProductRegisterDetailsDTO.getRemarks());

			ncProductRegisterDetailsVO.setNcProductRegisterVO(ncProductRegisterVO);
			ncProductRegisterDetailsVOs.add(ncProductRegisterDetailsVO);
		}

		ncProductRegisterVO.setNcProductRegisterDetailsVO(ncProductRegisterDetailsVOs);
	}

	@Override
	public List<NcProductRegisterVO> getNCProductRegisterOrgId(Long orgId, String branchCode, String finYear) {

		return ncProductRegisterRepo.getNCProductRegisterOrgId(orgId, branchCode, finYear);
	}

	@Override
	public NcProductRegisterVO getNCProductRegisterById(Long id) {

		return ncProductRegisterRepo.getNCProductRegisterById(id);
	}

	@Override
	public String getNcProductRegisterDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "NCPR";
		return ncProductRegisterRepo.getNcProductRegisterDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getNpdReport(Long orgId, String fromdate, String todate) {
		Set<Object[]> npdReport = npdRepo.getNpdReport(orgId, fromdate, todate);
		return getNpdReport(npdReport);
	}

	private List<Map<String, Object>> getNpdReport(Set<Object[]> npdReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : npdReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("npdid", ch[0] != null ? ch[0].toString() : "");
			map.put("orgid", ch[1] != null ? ch[1].toString() : "");
			map.put("docformatno", ch[2] != null ? ch[2].toString() : "");
			map.put("customer", ch[3] != null ? ch[3].toString() : "");
			map.put("docrefno", ch[4] != null ? ch[4].toString() : "");
			map.put("partname", ch[5] != null ? ch[5].toString() : "");
			map.put("partno", ch[6] != null ? ch[6].toString() : "");
			map.put("currentdate", ch[7] != null ? ch[7].toString() : "");
			map.put("Approvedby", ch[8] != null ? ch[8].toString() : "");
			map.put("remarks", ch[9] != null ? ch[9].toString() : "");
			map.put("revision", ch[10] != null ? ch[10].toString() : "");

			List1.add(map);
		}

		return List1;
	}

	private String getStringCellValue3(Cell cell) {

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
			} else {
				double numericValue = cell.getNumericCellValue();
				if (numericValue == (int) numericValue) {
					return String.valueOf((int) numericValue);
				} else {
					return BigDecimal.valueOf(numericValue).toPlainString();
				}
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	@Override
	public List<DailyPatrolResponseDTO> getDailyPatrolResponse(MultipartFile files)
			throws EncryptedDocumentException, IOException, ApplicationException {
		List<DailyPatrolResponseDTO> dailyPatrolResponse = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(files.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
			Row headerRow = sheet.getRow(0);
			List<String> errorMessages = new ArrayList<>();
			// Validate header
			for (Row row : sheet) {

				if (row.getRowNum() < 7) {
					continue; // Skip this iteration
				}
				try {
					String slno = getStringCellValue1(row.getCell(0));
					String characteristics = getStringCellValue1(row.getCell(1));
					String methodOfInspection = getStringCellValue1(row.getCell(2));
					String specification = getStringCellValue1(row.getCell(3));
					String lsl = getStringCellValue1(row.getCell(4));
					String usl = getStringCellValue1(row.getCell(5));
					String s1 = getStringCellValue1(row.getCell(6));
					String s2 = getStringCellValue1(row.getCell(7));
					String s3 = getStringCellValue1(row.getCell(8));
					String s4 = getStringCellValue1(row.getCell(9));
					String s5 = getStringCellValue1(row.getCell(10));
					String s6 = getStringCellValue1(row.getCell(11));
					String s7 = getStringCellValue1(row.getCell(12));
					String s8 = getStringCellValue1(row.getCell(13));
					String s9 = getStringCellValue1(row.getCell(14));
					String s10 = getStringCellValue1(row.getCell(15));
					String status = getStringCellValue1(row.getCell(16));
					String remarks = getStringCellValue1(row.getCell(17));

					// Create and populate TrailBalanceVO object
					DailyPatrolResponseDTO dto = new DailyPatrolResponseDTO();
					dto.setSlno(slno);
					dto.setCharacteristics(characteristics);
					dto.setMethodOfInspection(methodOfInspection);
					dto.setSpecification(specification);
					dto.setLsl(lsl);
					dto.setUsl(usl);
					dto.setS1(s1);
					dto.setS2(s2);
					dto.setS3(s3);
					dto.setS4(s4);
					dto.setS5(s5);
					dto.setS6(s6);
					dto.setS7(s7);
					dto.setS8(s8);
					dto.setS9(s9);
					dto.setS10(s10);
					dto.setStatus(status);
					dto.setStatus(remarks);
					dailyPatrolResponse.add(dto);
				} catch (Exception e) {
					errorMessages.add("Row No " + (row.getRowNum() + 1) + ": " + e.getMessage());
					String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());

				}
			}
			if (!errorMessages.isEmpty()) {
				throw new ApplicationException("Excel upload failed. " + String.join(", ", errorMessages)
						+ ". Except for these lines, all other data has been uploaded.");
			}

		} catch (IOException e) {
			throw new ApplicationException(
					"Failed to process file: " + files.getOriginalFilename() + " - " + e.getMessage());
		}

		return dailyPatrolResponse;
	}

	private String getStringCellValue4(Cell cell) {

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
			} else {
				double numericValue = cell.getNumericCellValue();
				if (numericValue == (int) numericValue) {
					return String.valueOf((int) numericValue);
				} else {
					return BigDecimal.valueOf(numericValue).toPlainString();
				}
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	@Override
	public List<FinalInspectionResponseDTO> getFinalInspectionResponse(MultipartFile file)
			throws EncryptedDocumentException, IOException, ApplicationException {
		List<FinalInspectionResponseDTO> finalInspectionResponse = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
			Row headerRow = sheet.getRow(0);
			List<String> errorMessages = new ArrayList<>();
			// Validate header
			for (Row row : sheet) {

				if (row.getRowNum() < 7) {
					continue; // Skip this iteration
				}
				try {
					String slno = getStringCellValue1(row.getCell(0));
					String characteristics = getStringCellValue1(row.getCell(1));
					String methodOfInspection = getStringCellValue1(row.getCell(2));
					String specification = getStringCellValue1(row.getCell(3));
					String lsl = getStringCellValue1(row.getCell(4));
					String usl = getStringCellValue1(row.getCell(5));
					String s1 = getStringCellValue1(row.getCell(6));
					String s2 = getStringCellValue1(row.getCell(7));
					String s3 = getStringCellValue1(row.getCell(8));
					String s4 = getStringCellValue1(row.getCell(9));
					String s5 = getStringCellValue1(row.getCell(10));
					String s6 = getStringCellValue1(row.getCell(11));
					String s7 = getStringCellValue1(row.getCell(12));
					String s8 = getStringCellValue1(row.getCell(13));
					String s9 = getStringCellValue1(row.getCell(14));
					String s10 = getStringCellValue1(row.getCell(15));
					String remarks = getStringCellValue1(row.getCell(16));

					// Create and populate TrailBalanceVO object
					FinalInspectionResponseDTO dto = new FinalInspectionResponseDTO();
					dto.setSlno(slno);
					dto.setCharacteristics(characteristics);
					dto.setMethodOfInspection(methodOfInspection);
					dto.setSpecification(specification);
					dto.setLsl(lsl);
					dto.setUsl(usl);
					dto.setS1(s1);
					dto.setS2(s2);
					dto.setS3(s3);
					dto.setS4(s4);
					dto.setS5(s5);
					dto.setS6(s6);
					dto.setS7(s7);
					dto.setS8(s8);
					dto.setS9(s9);
					dto.setS10(s10);
					dto.setRemarks(remarks);
					finalInspectionResponse.add(dto);
				} catch (Exception e) {
					errorMessages.add("Row No " + (row.getRowNum() + 1) + ": " + e.getMessage());
					String error = String.format("Row %d: %s", row.getRowNum() + 1, e.getMessage());

				}
			}
			if (!errorMessages.isEmpty()) {
				throw new ApplicationException("Excel upload failed. " + String.join(", ", errorMessages)
						+ ". Except for these lines, all other data has been uploaded.");
			}

		} catch (IOException e) {
			throw new ApplicationException(
					"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
		}

		return finalInspectionResponse;
	}

	private String getStringCellValue5(Cell cell) {

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
			} else {
				double numericValue = cell.getNumericCellValue();
				if (numericValue == (int) numericValue) {
					return String.valueOf((int) numericValue);
				} else {
					return BigDecimal.valueOf(numericValue).toPlainString();
				}
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	@Override
	public List<Map<String, Object>> getNCProductRegisterReport(Long orgId, String fromdate, String todate,
			String partNo) {
		Set<Object[]> ncProductRegisterReport = ncProductRegisterRepo.getNCProductRegisterReport(orgId, fromdate,
				todate, partNo);
		return getNCProductRegisterReport(ncProductRegisterReport);
	}

	private List<Map<String, Object>> getNCProductRegisterReport(Set<Object[]> ncProductRegisterReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : ncProductRegisterReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("ncproductregisterid", ch[0] != null ? ch[0].toString() : "");
			map.put("docid", ch[1] != null ? ch[1].toString() : "");
			map.put("docno", ch[2] != null ? ch[2].toString() : "");
			map.put("docdate", ch[3] != null ? ch[3].toString() : "");
			map.put("date", ch[4] != null ? ch[4].toString() : "");
			map.put("stage", ch[5] != null ? ch[5].toString() : "");
			map.put("partno", ch[6] != null ? ch[6].toString() : "");
			map.put("partdescription", ch[7] != null ? ch[7].toString() : "");
			map.put("processdescription", ch[8] != null ? ch[8].toString() : "");
			map.put("ncquantity", ch[9] != null ? ch[9].toString() : "");
			map.put("unit", ch[10] != null ? ch[10].toString() : "");
			map.put("correctiveaction", ch[11] != null ? ch[11].toString() : "");
			map.put("caparef", ch[12] != null ? ch[12].toString() : "");
			map.put("signature", ch[13] != null ? ch[13].toString() : "");
			map.put("remarks", ch[14] != null ? ch[14].toString() : "");

			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<Map<String, Object>> getProcessNonConformanceReport(Long orgId, String fromdate, String todate,
			String partNo) {
		Set<Object[]> ncProductRegisterReport = processNonConformanceReportRepo.getProcessNonConformanceReport(orgId,
				fromdate, todate, partNo);
		return getProcessNonConformanceReport(ncProductRegisterReport);
	}

	private List<Map<String, Object>> getProcessNonConformanceReport(Set<Object[]> ncProductRegisterReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : ncProductRegisterReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("processnonconformancereportid", ch[0] != null ? ch[0].toString() : "");
			map.put("actualdateofcompletion", ch[1] != null ? ch[1].toString() : "");
			map.put("adequacy", ch[2] != null ? ch[2].toString() : "");
			map.put("briefdescription", ch[3] != null ? ch[3].toString() : "");
			map.put("correctiveaction", ch[4] != null ? ch[4].toString() : "");
			map.put("created", ch[5] != null ? ch[5].toString() : "");
			map.put("date", ch[6] != null ? ch[6].toString() : "");
			map.put("disposition", ch[7] != null ? ch[7].toString() : "");
			map.put("docid", ch[8] != null ? ch[8].toString() : "");
			map.put("drawingno", ch[9] != null ? ch[9].toString() : "");
			map.put("effectivenessofcorrective", ch[10] != null ? ch[10].toString() : "");
			map.put("narration", ch[11] != null ? ch[11].toString() : "");
			map.put("partname", ch[12] != null ? ch[12].toString() : "");
			map.put("partno", ch[13] != null ? ch[13].toString() : "");
			map.put("process", ch[14] != null ? ch[14].toString() : "");
			map.put("qtyavailable", ch[15] != null ? ch[15].toString() : "");
			map.put("qtydefective", ch[16] != null ? ch[16].toString() : "");
			map.put("responsibility", ch[17] != null ? ch[17].toString() : "");
			map.put("rootcause", ch[18] != null ? ch[18].toString() : "");
			map.put("signature", ch[19] != null ? ch[19].toString() : "");
			map.put("targetdate", ch[20] != null ? ch[20].toString() : "");
			map.put("verify", ch[21] != null ? ch[21].toString() : "");
			map.put("partType", ch[22] != null ? ch[22].toString() : "");
			List1.add(map);
		}

		return List1;
	}

	// File upload for Incoming Material inspection

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateIncomingMaterialInspection(MultipartFile[] files, String docId,
			String screenName, String module) throws ApplicationException, java.io.IOException {

		IncomingMaterialInspectionVO incomingMaterialInspectionVO = incomingMaterialInspectionRepo.findByDocId(docId);

		String message = "Enquiry updated successfully";

		// BASIC MAPPING

		incomingMaterialInspectionVO = incomingMaterialInspectionRepo.save(incomingMaterialInspectionVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<IncomingAttachmentVO> oldDocs = incomingAttachmentRepo
				.findByIncomingMaterialInspectionVO(incomingMaterialInspectionVO);
		incomingAttachmentRepo.deleteAll(oldDocs);

		if (incomingMaterialInspectionVO.getDocuments() != null) {
			incomingMaterialInspectionVO.getDocuments().clear();
		} else {
			incomingMaterialInspectionVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (IncomingAttachmentVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(incomingMaterialInspectionVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("incomingMaterialInspectionVO", incomingMaterialInspectionVO);

		return response;
	}

	private void replaceDocuments(IncomingMaterialInspectionVO incoming, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(incoming, files, docFolder, docId);
	}

	private void saveFiles(IncomingMaterialInspectionVO incoming, MultipartFile[] files, Path docFolder, String docId)
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

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/quality/files/")
						.toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				IncomingAttachmentVO attach = new IncomingAttachmentVO();
				attach.setIncomingMaterialInspectionVO(incoming);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFilesize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (incoming.getDocuments() == null) {
					incoming.setDocuments(new ArrayList<>());
				}

				incoming.getDocuments().add(attach);
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
		return serveFile(request, "/api/quality/files/", uploadBasePath);
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

	@Override
	public Map<String, Object> createUpdateDocumentNumberChange(DocumentNumberChangeDTO documentNumberChangeDTO)
			throws ApplicationException {
		DocumentNumberChangeVO documentNumberChangeVO = new DocumentNumberChangeVO();
		String message;
		String screenCode = "DNC";
		if (ObjectUtils.isNotEmpty(documentNumberChangeDTO.getId())) {
			documentNumberChangeVO = documentNumberChangeRepo.findById(documentNumberChangeDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Npd details"));
			message = "EngineeringChangeNoticeRegister Updated Successfully";
			documentNumberChangeVO.setUpdatedBy(documentNumberChangeDTO.getCreatedBy());

		} else {

			String docId = documentNumberChangeRepo.getDocumentNumberChangeDocId(documentNumberChangeDTO.getOrgId(),
					documentNumberChangeDTO.getFinYear(), documentNumberChangeDTO.getBranchCode(), screenCode);
			documentNumberChangeVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(documentNumberChangeDTO.getOrgId(),
							documentNumberChangeDTO.getFinYear(), documentNumberChangeDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			documentNumberChangeVO.setCreatedBy(documentNumberChangeDTO.getCreatedBy());
			documentNumberChangeVO.setUpdatedBy(documentNumberChangeDTO.getCreatedBy());

			message = "Enquiry Created Successfully";
		}
		createUpdatedDocumentNumberChangeVOFromDocumentNumberChangeDTO(documentNumberChangeDTO, documentNumberChangeVO);
		documentNumberChangeRepo.save(documentNumberChangeVO);
		Map<String, Object> response = new HashMap<>();
		response.put("documentNumberChangeVO", documentNumberChangeVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedDocumentNumberChangeVOFromDocumentNumberChangeDTO(
			DocumentNumberChangeDTO documentNumberChangeDTO, DocumentNumberChangeVO documentNumberChangeVO) {

		documentNumberChangeVO.setDocumentScreenName(documentNumberChangeDTO.getDocumentScreenName());
		documentNumberChangeVO.setOrgId(documentNumberChangeDTO.getOrgId());
		documentNumberChangeVO.setBranch(documentNumberChangeDTO.getBranch());
		documentNumberChangeVO.setDocDate(documentNumberChangeDTO.getDocDate());
		documentNumberChangeVO.setBranchCode(documentNumberChangeDTO.getBranchCode());
		documentNumberChangeVO.setFinYear(documentNumberChangeDTO.getFinYear());
		documentNumberChangeVO.setCreatedBy(documentNumberChangeDTO.getCreatedBy());

		List<DocumentNumberChangeDetailsVO> detailsList = new ArrayList<>();

		// ✅ 1. CHECK DUPLICATE INSIDE SAME JSON
		Set<String> issueSet = new HashSet<>();
		Set<String> revisionSet = new HashSet<>();

		for (DocumentNumberChangeDetailsDTO dto : documentNumberChangeDTO.getDocumentNumberChangeDetailsDTO()) {

			String issueKey = dto.getDocumentFormateNo() + "-" + dto.getIssueNo();
			String revisionKey = dto.getDocumentFormateNo() + "-" + dto.getRevisionNo();

			if (!issueSet.add(issueKey)) {
				throw new RuntimeException(
						"Duplicate Issue No inside request for Document No: " + dto.getDocumentFormateNo());
			}

			if (!revisionSet.add(revisionKey)) {
				throw new RuntimeException(
						"Duplicate Revision No inside request for Document No: " + dto.getDocumentFormateNo());
			}
		}

		// ✅ 2. CHECK DUPLICATE IN DATABASE
		for (DocumentNumberChangeDetailsDTO detailsDTO : documentNumberChangeDTO.getDocumentNumberChangeDetailsDTO()) {

			String documentNo = detailsDTO.getDocumentFormateNo();
			Long issueNo = detailsDTO.getIssueNo();
			Long revisionNo = detailsDTO.getRevisionNo();

			List<DocumentNumberChangeDetailsVO> issueExists;
			List<DocumentNumberChangeDetailsVO> revisionExists;

			if (ObjectUtils.isNotEmpty(documentNumberChangeDTO.getId())) {

				issueExists = documentNumberChangeDetailsRepo
						.findAllByDocumentFormateNoAndIssueNoAndDocumentNumberChangeVO_IdNot(documentNo, issueNo,
								documentNumberChangeDTO.getId());

				revisionExists = documentNumberChangeDetailsRepo
						.findAllByDocumentFormateNoAndRevisionNoAndDocumentNumberChangeVO_IdNot(documentNo, revisionNo,
								documentNumberChangeDTO.getId());

			} else {

				issueExists = documentNumberChangeDetailsRepo.findAllByDocumentFormateNoAndIssueNo(documentNo, issueNo);

				revisionExists = documentNumberChangeDetailsRepo.findAllByDocumentFormateNoAndRevisionNo(documentNo,
						revisionNo);
			}

			if (!issueExists.isEmpty()) {
				throw new RuntimeException(
						"Duplicate Issue No " + issueNo + " already exists for Document No: " + documentNo);
			}

			if (!revisionExists.isEmpty()) {
				throw new RuntimeException(
						"Duplicate Revision No " + revisionNo + " already exists for Document No: " + documentNo);
			}

			DocumentNumberChangeDetailsVO detailsVO = new DocumentNumberChangeDetailsVO();

			detailsVO.setDocumentFormateNo(documentNo);
			detailsVO.setDate(detailsDTO.getDate());
			detailsVO.setIssueNo(issueNo);
			detailsVO.setRevisionNo(revisionNo);
			detailsVO.setDocumentNumberChangeVO(documentNumberChangeVO);

			detailsList.add(detailsVO);
		}

		documentNumberChangeVO.setDocumentNumberChangeDetailsVO(detailsList);
	}

	@Override
	public List<DocumentNumberChangeVO> getDocumentNumberChangeByOrgId(Long orgId, String finYear, String branchCode) {

		return documentNumberChangeRepo.getDocumentNumberChangeByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public DocumentNumberChangeVO getDocumentNumberChangeById(Long id) {

		return documentNumberChangeRepo.getDocumentNumberChangeById(id);
	}

	@Override
	public String getDocumentNumberChangeDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "DNC";
		return documentNumberChangeRepo.getDocumentNumberChangeDocId(orgId, finYear, branchCode, screenCode);
	}

	// File Upload For Npd

	@Override
	@Transactional
	public Map<String, Object> createUpdateNpd(MultipartFile[] files, String docId, String screenName, String module)
			throws ApplicationException, java.io.IOException {

		NpdVO npdVO = npdRepo.findByDocId(docId);

		String message = "npd updated successfully";

		// BASIC MAPPING

		npdVO = npdRepo.save(npdVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete od documents from DB
		List<NpdAttachmentVO> oldDocs = npdAttachmentRepo.findByNpdVO(npdVO);
		npdAttachmentRepo.deleteAll(oldDocs);

		if (npdVO.getDocuments() != null) {
			npdVO.getDocuments().clear();
		} else {
			npdVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (NpdAttachmentVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(npdVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("npdVO", npdVO);

		return response;
	}

	private void replaceDocuments(NpdVO npd, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(npd, files, docFolder, docId);
	}

	private void saveFiles(NpdVO npd, MultipartFile[] files, Path docFolder, String docId) throws java.io.IOException {

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

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/quality/files/")
						.toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				NpdAttachmentVO attach = new NpdAttachmentVO();
				attach.setNpdVO(npd);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileype(file.getContentType());
				attach.setFilesize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (npd.getDocuments() == null) {
					npd.setDocuments(new ArrayList<>());
				}

				npd.getDocuments().add(attach);
			}

// Save vehicle once
//			enquiryRepo.save(enquiry);

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
	public List<Map<String, Object>> getDocumentFormateNumber(Long orgId, String screenName) {
		Set<Object[]> ncProductRegisterReport = inprocessInspectionRepo.getDocumentFormateNumber(orgId, screenName);
		return getDocumentFormateNumber(ncProductRegisterReport);
	}

	private List<Map<String, Object>> getDocumentFormateNumber(Set<Object[]> ncProductRegisterReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : ncProductRegisterReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("documentFormateNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}

		return List1;
	}

	// Ecn

	@Override
	@Transactional
	public Map<String, Object> createUpdateEcn(MultipartFile[] files, String docId, String screenName, String module)
			throws ApplicationException, java.io.IOException {

		EcnApprovalRecordVO ecnApprovalRecordVO = ecnApprovalRecordRepo.findByDocId(docId);

		String message = "Ecn Change Record updated successfully";

		// BASIC MAPPING

		ecnApprovalRecordVO = ecnApprovalRecordRepo.save(ecnApprovalRecordVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<EcnAttachmentVO> oldDocs = ecnAttachmentRepo.findByEcnApprovalRecordVO(ecnApprovalRecordVO);
		ecnAttachmentRepo.deleteAll(oldDocs);

		if (ecnApprovalRecordVO.getDocuments() != null) {
			ecnApprovalRecordVO.getDocuments().clear();
		} else {
			ecnApprovalRecordVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (EcnAttachmentVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(ecnApprovalRecordVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("ecnApprovalRecordVO", ecnApprovalRecordVO);

		return response;
	}

	private void replaceDocuments(EcnApprovalRecordVO ecn, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(ecn, files, docFolder, docId);
	}

	private void saveFiles(EcnApprovalRecordVO ecn, MultipartFile[] files, Path docFolder, String docId)
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

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/quality/files/")
						.toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				EcnAttachmentVO attach = new EcnAttachmentVO();
				attach.setEcnApprovalRecordVO(ecn);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileype(file.getContentType());
				attach.setFilesize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (ecn.getDocuments() == null) {
					ecn.setDocuments(new ArrayList<>());
				}

				ecn.getDocuments().add(attach);
			}

			// Save vehicle once
//			enquiryRepo.save(enquiry);

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafely11(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectory11(Path path) throws IOException {
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
	public List<Map<String, Object>> getIncomingMaterialInspectionReport(Long orgId, String grnNo, String supplierName,
			String type) {
		Set<Object[]> ncProductRegisterReport = incomingMaterialInspectionRepo
				.getIncomingMaterialInspectionReport(orgId, grnNo, supplierName, type);
		return getIncomingMaterialInspectionReport(ncProductRegisterReport);
	}

	private List<Map<String, Object>> getIncomingMaterialInspectionReport(Set<Object[]> ncProductRegisterReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : ncProductRegisterReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("acceptedqty", ch[0] != null ? ch[0].toString() : "");
			map.put("dcinvno", ch[1] != null ? ch[1].toString() : "");
			map.put("docid", ch[2] != null ? ch[2].toString() : "");
			map.put("documentformatno", ch[3] != null ? ch[3].toString() : "");
			map.put("grnno", ch[4] != null ? ch[4].toString() : "");
			map.put("itemno", ch[5] != null ? ch[5].toString() : "");
			map.put("material", ch[6] != null ? ch[6].toString() : "");
			map.put("materialtype", ch[7] != null ? ch[7].toString() : "");
			map.put("pono", ch[8] != null ? ch[8].toString() : "");
			map.put("qtyreceived", ch[9] != null ? ch[9].toString() : "");
			map.put("suppliername", ch[10] != null ? ch[10].toString() : "");
			List1.add(map);
		}

		return List1;

	}

	@Override
	public List<Map<String, Object>> getListOfGrnNumbers(Long orgId, String type) {
		Set<Object[]> ncProductRegisterReport = incomingMaterialInspectionRepo.getListOfGrnNumbers(orgId, type);
		return getListOfGrnNumbers(ncProductRegisterReport);
	}

	private List<Map<String, Object>> getListOfGrnNumbers(Set<Object[]> ncProductRegisterReport) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : ncProductRegisterReport) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnNo", ch[0] != null ? ch[0].toString() : "");
			map.put("supplierName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}

		return List1;

	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateFinalInspection(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		FinalInspectionReportVO inprocessInspectionVO = finalInspectionReportRepo.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		inprocessInspectionVO = finalInspectionReportRepo.save(inprocessInspectionVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<FinalInspectionReportAttachmentVO> oldDocs = finalInspectionReportAttachmentRepo
				.findByFinalInspectionReportVO(inprocessInspectionVO);
		finalInspectionReportAttachmentRepo.deleteAll(oldDocs);

		if (inprocessInspectionVO.getDocuments() != null) {
			inprocessInspectionVO.getDocuments().clear();
		} else {
			inprocessInspectionVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (FinalInspectionReportAttachmentVO doc : oldDocs) {
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

	private void replaceDocuments(FinalInspectionReportVO inprocessInspection, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(inprocessInspection, files, docFolder, docId);
	}

	private void saveFiles(FinalInspectionReportVO inprocessInspection, MultipartFile[] files, Path docFolder,
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
						.path("/api/quality/viewFilesFinal/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				FinalInspectionReportAttachmentVO attach = new FinalInspectionReportAttachmentVO();
				attach.setFinalInspectionReportVO(inprocessInspection);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFilename(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (inprocessInspection.getDocuments() == null) {
					inprocessInspection.setDocuments(new ArrayList<>());
				}

				inprocessInspection.getDocuments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFilesFinal(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFileFinal(request, "/api/quality/viewFilesFinal/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileFinal(HttpServletRequest request, String apiPrefix, String uploadBasePath)
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
	@Transactional
	public Map<String, Object> createUpdateProcessNonConformanceReport(MultipartFile[] files, String docId,
			String screenName, String module) throws ApplicationException, java.io.IOException {

		ProcessNonConformanceReportVO processNonConformanceReportVO = processNonConformanceReportRepo
				.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		processNonConformanceReportVO = processNonConformanceReportRepo.save(processNonConformanceReportVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<ProcessNonConformanceReportAttachmentVO> oldDocs = processNonConformanceReportAttachmentRepo
				.findByProcessNonConformanceReportVO(processNonConformanceReportVO);
		processNonConformanceReportAttachmentRepo.deleteAll(oldDocs);

		if (processNonConformanceReportVO.getDocuments() != null) {
			processNonConformanceReportVO.getDocuments().clear();
		} else {
			processNonConformanceReportVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (ProcessNonConformanceReportAttachmentVO doc : oldDocs) {
			deleteFileSafelyChecked(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(processNonConformanceReportVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("processNonConformanceReportVO", processNonConformanceReportVO);

		return response;
	}

	private void replaceDocuments(ProcessNonConformanceReportVO processNonConformanceReport, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(processNonConformanceReport, files, docFolder, docId);
	}

	private void saveFiles(ProcessNonConformanceReportVO processNonConformanceReport, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		try {
			createDirectoryFinal(docFolder);

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
						.path("/api/quality/viewFileProcessNon/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				ProcessNonConformanceReportAttachmentVO attach = new ProcessNonConformanceReportAttachmentVO();
				attach.setProcessNonConformanceReportVO(processNonConformanceReport);
				attach.setFileName(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (processNonConformanceReport.getDocuments() == null) {
					processNonConformanceReport.setDocuments(new ArrayList<>());
				}

				processNonConformanceReport.getDocuments().add(attach);
			}

//Save vehicle once
//			enquiryRepo.save(enquiry);

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafelyChecked(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryFinal(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFileProcessNon(HttpServletRequest request)
			throws IOException, java.io.IOException {
		return serveFileProcessNon(request, "/api/quality/viewFileProcessNon/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileProcessNon(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException, java.io.IOException {

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
	@Transactional
	public Map<String, Object> createUpdateEngineeringChangeNoticeRegister(MultipartFile[] files, String docId,
			String screenName, String module) throws ApplicationException, java.io.IOException {

		EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegisterVO = engineeringChangeNoticeRegisterRepo
				.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		engineeringChangeNoticeRegisterVO = engineeringChangeNoticeRegisterRepo.save(engineeringChangeNoticeRegisterVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<EngineeringChangeNoticeRegisterAttachmentVO> oldDocs = engineeringChangeNoticeRegisterAttachmentRepo
				.findByEngineeringChangeNoticeRegisterVO(engineeringChangeNoticeRegisterVO);
		engineeringChangeNoticeRegisterAttachmentRepo.deleteAll(oldDocs);

		if (engineeringChangeNoticeRegisterVO.getDocuments() != null) {
			engineeringChangeNoticeRegisterVO.getDocuments().clear();
		} else {
			engineeringChangeNoticeRegisterVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (EngineeringChangeNoticeRegisterAttachmentVO doc : oldDocs) {
			deleteFileSafelyDocument(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(engineeringChangeNoticeRegisterVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("engineeringChangeNoticeRegisterVO", engineeringChangeNoticeRegisterVO);

		return response;
	}

	private void replaceDocuments(EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegister,
			MultipartFile[] files, Path docFolder, String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(engineeringChangeNoticeRegister, files, docFolder, docId);
	}

	private void saveFiles(EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegister, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		try {
			createDirectoryDocument(docFolder);

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
						.path("/api/quality/viewFileEngineering/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				EngineeringChangeNoticeRegisterAttachmentVO attach = new EngineeringChangeNoticeRegisterAttachmentVO();
				attach.setEngineeringChangeNoticeRegisterVO(engineeringChangeNoticeRegister);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (engineeringChangeNoticeRegister.getDocuments() == null) {
					engineeringChangeNoticeRegister.setDocuments(new ArrayList<>());
				}

				engineeringChangeNoticeRegister.getDocuments().add(attach);
			}

//Save vehicle once
//			enquiryRepo.save(enquiry);

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafelyDocument(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryDocument(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFileEngineering(HttpServletRequest request)
			throws IOException, java.io.IOException {
		return serveFileDocument(request, "/api/quality/viewFileEngineering/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileDocument(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException, java.io.IOException {

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
	@Transactional
	public Map<String, Object> createUpdateQualityDocumentChangeRecord(MultipartFile[] files, String docId,
			String screenName, String module) throws ApplicationException, java.io.IOException {

		QualityDocumentChangeRecordVO qualityDocumentChangeRecordVO = qualityDocumentChangeRecordRepo
				.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		qualityDocumentChangeRecordVO = qualityDocumentChangeRecordRepo.save(qualityDocumentChangeRecordVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<DocumentChangeRecordAttachmentVO> oldDocs = documentChangeRecordAttachmentRepo
				.findByQualityDocumentChangeRecordVO(qualityDocumentChangeRecordVO);
		documentChangeRecordAttachmentRepo.deleteAll(oldDocs);

		if (qualityDocumentChangeRecordVO.getDocuments() != null) {
			qualityDocumentChangeRecordVO.getDocuments().clear();
		} else {
			qualityDocumentChangeRecordVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (DocumentChangeRecordAttachmentVO doc : oldDocs) {
			deleteFileSafelyQuality(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(qualityDocumentChangeRecordVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("qualityDocumentChangeRecordVO", qualityDocumentChangeRecordVO);

		return response;
	}

	private void replaceDocuments(QualityDocumentChangeRecordVO qualityDocumentChangeRecord, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(qualityDocumentChangeRecord, files, docFolder, docId);
	}

	private void saveFiles(QualityDocumentChangeRecordVO qualityDocumentChangeRecord, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		try {
			createDirectoryQuality(docFolder);

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
						.path("/api/quality/viewFileDocument/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				DocumentChangeRecordAttachmentVO attach = new DocumentChangeRecordAttachmentVO();
				attach.setQualityDocumentChangeRecordVO(qualityDocumentChangeRecord);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFilename(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (qualityDocumentChangeRecord.getDocuments() == null) {
					qualityDocumentChangeRecord.setDocuments(new ArrayList<>());
				}

				qualityDocumentChangeRecord.getDocuments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafelyQuality(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryQuality(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFileDocument(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFileQuality(request, "/api/quality/viewFileDocument/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileQuality(HttpServletRequest request, String apiPrefix, String uploadBasePath)
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
	@Transactional
	public Map<String, Object> createUpdateNCProductRegister(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		NcProductRegisterVO ncProductRegisterVO = ncProductRegisterRepo.findByDocId(docId);

		String message = "ncproductregister updated successfully";

		// BASIC MAPPING

		ncProductRegisterVO = ncProductRegisterRepo.save(ncProductRegisterVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<NCProductRegisterDetailsAttachmentVO> oldDocs = ncProductRegisterDetailsAttachmentRepo
				.findByNcProductRegisterVO(ncProductRegisterVO);
		ncProductRegisterDetailsAttachmentRepo.deleteAll(oldDocs);

		if (ncProductRegisterVO.getDocuments() != null) {
			ncProductRegisterVO.getDocuments().clear();
		} else {
			ncProductRegisterVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (NCProductRegisterDetailsAttachmentVO doc : oldDocs) {
			deleteFileSafelyNcProduct(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(ncProductRegisterVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("ncProductRegisterVO", ncProductRegisterVO);

		return response;
	}

	private void replaceDocuments(NcProductRegisterVO ncProductRegister, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(ncProductRegister, files, docFolder, docId);
	}

	private void saveFiles(NcProductRegisterVO ncProductRegister, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

		try {
			createDirectoryNcProduct(docFolder);

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
						.path("/api/quality/viewFileNcProduct/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				NCProductRegisterDetailsAttachmentVO attach = new NCProductRegisterDetailsAttachmentVO();
				attach.setNcProductRegisterVO(ncProductRegister);
				attach.setFileName(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (ncProductRegister.getDocuments() == null) {
					ncProductRegister.setDocuments(new ArrayList<>());
				}

				ncProductRegister.getDocuments().add(attach);
			}

//Save vehicle once
//			enquiryRepo.save(enquiry);

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafelyNcProduct(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryNcProduct(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFileNcProduct(HttpServletRequest request)
			throws IOException, java.io.IOException {
		return serveFileNcProduct(request, "/api/quality/viewFileNcProduct/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileNcProduct(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException, java.io.IOException {

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
	public List<Map<String, Object>> getQADRegisterReport(Long orgId, String docName) {

		Set<Object[]> report = qadRegisterRepo.getQADRegisterReport(orgId, docName);
		return buildQadRegisterReport(report);
	}

	private List<Map<String, Object>> buildQadRegisterReport(Set<Object[]> report) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : report) {

			Map<String, Object> map = new HashMap<>();

			map.put("docname", ch[0] != null ? ch[0].toString() : "");
			map.put("docdate", ch[1] != null ? ch[1].toString() : "");
			map.put("branch", ch[2] != null ? ch[2].toString() : "");
			map.put("branchcode", ch[3] != null ? ch[3].toString() : "");
			map.put("docformatno", ch[4] != null ? ch[4].toString() : "");
			map.put("narration", ch[5] != null ? ch[5].toString() : "");
			map.put("summary", ch[6] != null ? ch[6].toString() : "");

			map.put("admendmentdetails", ch[7] != null ? ch[7].toString() : "");
			map.put("document_no", ch[8] != null ? ch[8].toString() : "");
			map.put("newdocissue", ch[9] != null ? ch[9].toString() : "");
			map.put("newdocrev", ch[10] != null ? ch[10].toString() : "");
			map.put("olddocissue", ch[11] != null ? ch[11].toString() : "");
			map.put("olddocrev", ch[12] != null ? ch[12].toString() : "");
			map.put("reasonforadmendment", ch[13] != null ? ch[13].toString() : "");
			map.put("remarks", ch[14] != null ? ch[14].toString() : "");
			map.put("reviewedby", ch[15] != null ? ch[15].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateInprocessInspection(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		InprocessInspectionVO inprocessInspectionVO = inprocessInspectionRepo.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		inprocessInspectionVO = inprocessInspectionRepo.save(inprocessInspectionVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectoryInprocess(docFolder);

		// 2️⃣ Delete old documents from DB
		List<InprocessInspectionAttachmentVO> oldDocs = inprocessInspectionAttachmentRepo
				.findByInprocessInspectionVO(inprocessInspectionVO);
		inprocessInspectionAttachmentRepo.deleteAll(oldDocs);

		if (inprocessInspectionVO.getDocuments() != null) {
			inprocessInspectionVO.getDocuments().clear();
		} else {
			inprocessInspectionVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (InprocessInspectionAttachmentVO doc : oldDocs) {
			deleteFileInproces(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(inprocessInspectionVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("inprocessInspectionVO", inprocessInspectionVO);

		return response;
	}

	private void replaceDocuments(InprocessInspectionVO inprocessInspection, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(inprocessInspection, files, docFolder, docId);
	}

	private void saveFiles(InprocessInspectionVO inprocessInspection, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		try {
			createDirectoryInprocess(docFolder);

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
						.path("/api/quality/viewFileInprocess/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				InprocessInspectionAttachmentVO attach = new InprocessInspectionAttachmentVO();
				attach.setInprocessInspectionVO(inprocessInspection);
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

			// Save vehicle once
//						enquiryRepo.save(enquiry);

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileInproces(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryInprocess(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFileInprocess(HttpServletRequest request)
			throws IOException, java.io.IOException {
		return serveFileInprocess(request, "/api/quality/viewFileInprocess/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileInprocess(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException, java.io.IOException {

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

	// Incoming material inspection Image Attachment

//		@Override
//		public List<IncomingImageResponseDTO> getIncomingMaterialInspectionImages(Long id) throws Exception {
//
//			IncomingMaterialInspectionVO record = incomingMaterialInspectionRepo
//					.getAllIncomingMaterialInspectionImagesById(id);
//
//			if (record == null) {
//				throw new RuntimeException("Record not found");
//			}
//
//			List<IncomingAttachmentVO> docs = record.getDocuments();
//
//			if (docs == null || docs.isEmpty()) {
//				throw new RuntimeException("No attachments found");
//			}
//			List<IncomingImageResponseDTO> responseList = new ArrayList<>();
//
//			for (IncomingAttachmentVO attachment : docs) {
//
//				String fileUrl = attachment.getFilePath().replace(" ", "%20");
//
//				InputStream inputStream = new URL(fileUrl).openStream();
//
//				byte[] bytes = inputStream.readAllBytes();
//
//				String base64 = Base64.getEncoder().encodeToString(bytes);
//
//				IncomingImageResponseDTO dto = new IncomingImageResponseDTO();
//				dto.setFileName(attachment.getFilename());
//				dto.setProfileImage(base64); // only base64 (like you asked)
//
//				responseList.add(dto);
//			}
//
//			return responseList;
//		}

	// Inprocessinspection Image Attachment

	@Override
	public List<InprocessImageResponseDTO> getInprocessInspectionImages(Long id) throws Exception {

		InprocessInspectionVO record = inprocessInspectionRepo.getAllInprocessInspectionImagesById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<InprocessInspectionAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<InprocessImageResponseDTO> responseList = new ArrayList<>();

		for (InprocessInspectionAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			InprocessImageResponseDTO dto = new InprocessImageResponseDTO();
			dto.setFileName(attachment.getFilename());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

	// FinalInspectionReport

	@Override
	public List<FinalInspectionImageResponseDTO> getFinalInspectionReportImages(Long id) throws Exception {

		FinalInspectionReportVO record = finalInspectionReportRepo.getAllFinalInspectionImagesById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<FinalInspectionReportAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<FinalInspectionImageResponseDTO> responseList = new ArrayList<>();

		for (FinalInspectionReportAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			FinalInspectionImageResponseDTO dto = new FinalInspectionImageResponseDTO();
			dto.setFileName(attachment.getFilename());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

	// NPDImageAttachment
	@Override
	public List<NPDImageResponseDTO> getNPDImages(Long id) throws Exception {

		NpdVO record = npdRepo.getAllNPDImagesById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<NpdAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<NPDImageResponseDTO> responseList = new ArrayList<>();

		for (NpdAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			NPDImageResponseDTO dto = new NPDImageResponseDTO();
			dto.setFileName(attachment.getFilename());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

	public List<ImageResponseDTO> getAllImages(Long id) throws Exception {

		EcnApprovalRecordVO record = ecnApprovalRecordRepo.getAllEcnApprovalRecordById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<EcnAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}

		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (EcnAttachmentVO attachment : docs) {

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
	public List<ImageResponseDTO> getEngineeringChangeNoticeRegisterImages(Long id) throws Exception {

		EngineeringChangeNoticeRegisterVO record = engineeringChangeNoticeRegisterRepo
				.getAllEngineeringChangeNoticeRegisterById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<EngineeringChangeNoticeRegisterAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (EngineeringChangeNoticeRegisterAttachmentVO attachment : docs) {

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
	public List<ImageResponseDTO> getNCProductRegisterImages(Long id) throws Exception {

		NcProductRegisterVO record = ncProductRegisterRepo.getAllNcProductRegisterById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<NCProductRegisterDetailsAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (NCProductRegisterDetailsAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			ImageResponseDTO dto = new ImageResponseDTO();
			dto.setFileName(attachment.getFileName());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

	@Override
	public List<ImageResponseDTO> getProcessNonConformanceReportImages(Long id) throws Exception {

		ProcessNonConformanceReportVO record = processNonConformanceReportRepo
				.getAllProcessNonConformanceReportById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<ProcessNonConformanceReportAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (ProcessNonConformanceReportAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			ImageResponseDTO dto = new ImageResponseDTO();
			dto.setFileName(attachment.getFileName());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

	@Override
	public List<ImageResponseDTO> getIncomingMaterialInspectionImages(Long id) throws Exception {

		IncomingMaterialInspectionVO record = incomingMaterialInspectionRepo.getAllIncomingMaterialInspectionById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<IncomingAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (IncomingAttachmentVO attachment : docs) {

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
	public List<ImageResponseDTO> toolRecieveFromCalibrationVO(Long id) throws Exception {

		ToolRecieveFromCalibrationVO record = toolRecieveFromCalibrationRepo.getAllToolRecieveFromCalibrationVOById(id);

	    if (record == null) {
	        throw new RuntimeException("Record not found");
	    }

	    List<ToolRecieveFromCalibrationAttachmentVO> docs = record.getDocuments();

	    if (docs == null || docs.isEmpty()) {
	        throw new RuntimeException("No attachments found");
	    }
	    List<ImageResponseDTO> responseList = new ArrayList<>();

	    for (ToolRecieveFromCalibrationAttachmentVO attachment : docs) {

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
	@Transactional
	public List<Map<String, Object>> getNPDdetails(Long orgId,String branchCode) {

		Set<Object[]> result = npdRepo.getNPDdetails(orgId,branchCode);
		return getNPDdetails(result);
	}

	private List<Map<String, Object>> getNPDdetails(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partyName", fs[0] != null ? fs[0].toString() : "");
			part.put("partyCode", fs[1] != null ? fs[1].toString() : "");
		

			details1.add(part);
		}
		return details1;
	}


}