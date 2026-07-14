package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.dto.SampleApprovalDTO;
import com.efitops.basesetup.dto.SampleApprovalDetailsDTO;
import com.efitops.basesetup.dto.SampleImageResponseDTO;
import com.efitops.basesetup.dto.SettingApprovalDTO;
import com.efitops.basesetup.dto.SettingApprovalDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;
import com.efitops.basesetup.entity.SampleApprovalAttachmentVO;
import com.efitops.basesetup.entity.SampleApprovalDetailsVO;
import com.efitops.basesetup.entity.SampleApprovalVO;
import com.efitops.basesetup.entity.SettingApprovalAttachmentVO;
import com.efitops.basesetup.entity.SettingApprovalDetailsVO;
import com.efitops.basesetup.entity.SettingApprovalDocumentsVO;
import com.efitops.basesetup.entity.SettingApprovalVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.SampleApprovalAttachmentRepo;
import com.efitops.basesetup.repo.SampleApprovalDetailsRepo;
import com.efitops.basesetup.repo.SampleApprovalImagesRepo;
import com.efitops.basesetup.repo.SampleApprovalRepo;
import com.efitops.basesetup.repo.SampleImageResponseRepo;
import com.efitops.basesetup.repo.SettingApprovalAttachmentRepo;
import com.efitops.basesetup.repo.SettingApprovalDetailsRepo;
import com.efitops.basesetup.repo.SettingApprovalDocumentsRepo;
import com.efitops.basesetup.repo.SettingApprovalRepo;

@Service
public class QualityApprovalServiceImpl implements QualityApprovalServive {

	@Autowired
	SettingApprovalRepo settingApprovalRepo;

	@Autowired
	SettingApprovalDetailsRepo settingApprovalDetailsRepo;

	@Autowired
	SampleApprovalRepo sampleApprovalRepo;

	@Autowired
	SampleApprovalDetailsRepo sampleApprovalDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	SettingApprovalDocumentsRepo settingApprovalDocumentsRepo;

	@Autowired
	SampleApprovalImagesRepo sampleApprovalImagesRepo;

	@Autowired
	SettingApprovalAttachmentRepo settingApprovalAttachmentRepo;

	@Autowired
	SampleApprovalAttachmentRepo sampleApprovalAttachmentRepo;
	
	@Autowired
	SampleImageResponseRepo  sampleImageResponseRepo;
	
	@Autowired
    private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public List<SettingApprovalVO> getAllSettingApprovalByOrgId(Long orgId, String finYear, String branchCode) {

		return settingApprovalRepo.getAllSettingApprovalByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public SettingApprovalVO getSettingApprovalById(Long id) {

		return settingApprovalRepo.getSettingApprovalById(id);
	}

	@Override
	public String getSettingApprovalDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "SA";
		String result = settingApprovalRepo.getSettingApprovalDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

//	@Override
//	public Map<String, Object> createUpdateSettingApproval(SettingApprovalDTO settingApprovalDTO,
//			List<MultipartFile> files) throws ApplicationException, IOException {
//		String message;
//		String screenCode = "SA";
//		SettingApprovalVO settingApprovalVO = new SettingApprovalVO();
//
//		if (settingApprovalDTO.getId() != null) {
//			// Fetch existing ItemVO for update
//			settingApprovalVO = settingApprovalRepo.findById(settingApprovalDTO.getId())
//					.orElseThrow(() -> new ApplicationException("SettingApproval not found"));
//			settingApprovalVO.setUpdatedBy(settingApprovalDTO.getCreatedBy());
//			createUpdateSettingApprovalVOBySettingApprovalDTO(settingApprovalDTO, settingApprovalVO, files);
//			message = "SettingApproval Updated Successfully";
//
//			List<SettingApprovalDetailsVO> settingApprovalDetailsVOs = settingApprovalDetailsRepo
//					.findBySettingApprovalVO(settingApprovalVO);
//			settingApprovalDetailsRepo.deleteAll(settingApprovalDetailsVOs);
//
//			List<SettingApprovalDocumentsVO> settingApprovalDocumentsVOs = settingApprovalDocumentsRepo
//					.findBySettingApprovalVO(settingApprovalVO);
//			settingApprovalDocumentsRepo.deleteAll(settingApprovalDocumentsVOs);
//
//		} else {
//
//			String docId = settingApprovalRepo.getSettingApprovalDocId(settingApprovalDTO.getOrgId(),
//					settingApprovalDTO.getFinYear(), settingApprovalDTO.getBranchCode(), screenCode);
//			settingApprovalVO.setDocId(docId);
//
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(settingApprovalDTO.getOrgId(),
//							settingApprovalDTO.getFinYear(), settingApprovalDTO.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			// Create new ItemVO
//			settingApprovalVO.setCreatedBy(settingApprovalDTO.getCreatedBy());
//			settingApprovalVO.setUpdatedBy(settingApprovalDTO.getCreatedBy());
//			createUpdateSettingApprovalVOBySettingApprovalDTO(settingApprovalDTO, settingApprovalVO, files);
//			message = "SettingApproval Created Successfully";
//		}
//
//		// Save the ItemVO
//		settingApprovalRepo.save(settingApprovalVO);
//
//		// Prepare response
//		Map<String, Object> response = new HashMap<>();
//		response.put("settingApprovalVO", settingApprovalVO);
//		response.put("message", message);
//		return response;
//	}
//
//	private void createUpdateSettingApprovalVOBySettingApprovalDTO(SettingApprovalDTO settingApprovalDTO,
//			SettingApprovalVO settingApprovalVO, List<MultipartFile> files) throws IOException {
//		settingApprovalVO.setRouteCardNo(settingApprovalDTO.getRouteCardNo());
//		settingApprovalVO.setPartName(settingApprovalDTO.getPartName());
//		settingApprovalVO.setPartNo(settingApprovalDTO.getPartNo());
//		settingApprovalVO.setDrgNo(settingApprovalDTO.getDrgNo());
//		settingApprovalVO.setOperation(settingApprovalDTO.getOperation());
//		settingApprovalVO.setCycleTime(settingApprovalDTO.getCycleTime());
//		settingApprovalVO.setMachineNo(settingApprovalDTO.getMachineNo());
//		settingApprovalVO.setMachineName(settingApprovalDTO.getMachineName());
//		settingApprovalVO.setSampleQty(settingApprovalDTO.getSampleQty());
//		settingApprovalVO.setGrnClearTime(settingApprovalDTO.getGrnClearTime());
//		settingApprovalVO.setDocFormatNo(settingApprovalDTO.getDocFormatNo());
//		settingApprovalVO.setGeneralRemarks(settingApprovalDTO.getGeneralRemarks());
//		settingApprovalVO.setOperatorName(settingApprovalDTO.getOperatorName());
//		settingApprovalVO.setSetterName(settingApprovalDTO.getSetterName());
//		settingApprovalVO.setShiftInCharge(settingApprovalDTO.getShiftInCharge());
//		settingApprovalVO.setQualityName(settingApprovalDTO.getQualityName());
//		settingApprovalVO.setNarration(settingApprovalDTO.getNarration());
//		settingApprovalVO.setOrgId(settingApprovalDTO.getOrgId());
//		settingApprovalVO.setBranch(settingApprovalDTO.getBranch());
//		settingApprovalVO.setBranchCode(settingApprovalDTO.getBranchCode());
//		settingApprovalVO.setFinYear(settingApprovalDTO.getFinYear());
//
//		List<SettingApprovalDetailsVO> settingApprovalDetailsVOs = new ArrayList<>();
//		for (SettingApprovalDetailsDTO settingApprovalDetailsDTO : settingApprovalDTO.getSettingApprovalDetailsDTO()) {
//			SettingApprovalDetailsVO settingApprovalDetailsVO = new SettingApprovalDetailsVO();
//			settingApprovalDetailsVO.setCharacteristics(settingApprovalDetailsDTO.getCharacteristics());
//			settingApprovalDetailsVO.setSpecification(settingApprovalDetailsDTO.getSpecification());
//			settingApprovalDetailsVO.setMethodOfInspection(settingApprovalDetailsDTO.getMethodOfInspection());
//			settingApprovalDetailsVO.setLsl(settingApprovalDetailsDTO.getLsl());
//			settingApprovalDetailsVO.setUsl(settingApprovalDetailsDTO.getUsl());
//			settingApprovalDetailsVO.setSetter1(settingApprovalDetailsDTO.getSetter1());
//			settingApprovalDetailsVO.setSetter2(settingApprovalDetailsDTO.getSetter2());
//			settingApprovalDetailsVO.setSetter3(settingApprovalDetailsDTO.getSetter3());
//			settingApprovalDetailsVO.setSetter4(settingApprovalDetailsDTO.getSetter4());
//			settingApprovalDetailsVO.setSetter5(settingApprovalDetailsDTO.getSetter5());
//			settingApprovalDetailsVO.setQulity1(settingApprovalDetailsDTO.getQuality1());
//			settingApprovalDetailsVO.setQulity2(settingApprovalDetailsDTO.getQuality2());
//			settingApprovalDetailsVO.setQulity3(settingApprovalDetailsDTO.getQuality3());
//			settingApprovalDetailsVO.setQulity4(settingApprovalDetailsDTO.getQuality4());
//			settingApprovalDetailsVO.setQulity5(settingApprovalDetailsDTO.getQuality5());
//			settingApprovalDetailsVO.setRemarks(settingApprovalDetailsDTO.getRemarks());
//
//			settingApprovalDetailsVO.setSettingApprovalVO(settingApprovalVO); // Set the reference in child entity
//			settingApprovalDetailsVOs.add(settingApprovalDetailsVO);
//		}
//
//		settingApprovalVO.setSettingApprovalDetailsVO(settingApprovalDetailsVOs);
//
//	}
	
	
	@Override
	public Map<String, Object> createUpdateSettingApproval(SettingApprovalDTO settingApprovalDTO) throws ApplicationException, IOException {
		String message;
		String screenCode = "SA";
		SettingApprovalVO oldSettingApproval = null;
		
		
		SettingApprovalVO settingApprovalVO = new SettingApprovalVO();

		if (settingApprovalDTO.getId() != null) {
			oldSettingApproval = settingApprovalRepo.findById(settingApprovalDTO.getId())
					.orElseThrow(() -> new ApplicationException("settingApproval not found"));

			oldSettingApproval.getSettingApprovalDetailsVO().size(); // load


			entityManager.detach(oldSettingApproval); // detach snapshot
			
			
			// Fetch existing ItemVO for update
			settingApprovalVO = settingApprovalRepo.findById(settingApprovalDTO.getId())
					.orElseThrow(() -> new ApplicationException("SettingApproval not found"));
			settingApprovalVO.setUpdatedBy(settingApprovalDTO.getCreatedBy());
			createUpdateSettingApprovalVOBySettingApprovalDTO(settingApprovalDTO, settingApprovalVO);
			message = "SettingApproval Updated Successfully";

			List<SettingApprovalDetailsVO> settingApprovalDetailsVOs = settingApprovalDetailsRepo
					.findBySettingApprovalVO(settingApprovalVO);
			settingApprovalDetailsRepo.deleteAll(settingApprovalDetailsVOs);

			List<SettingApprovalDocumentsVO> settingApprovalDocumentsVOs = settingApprovalDocumentsRepo
					.findBySettingApprovalVO(settingApprovalVO);
			settingApprovalDocumentsRepo.deleteAll(settingApprovalDocumentsVOs);

		} else {

			String docId = settingApprovalRepo.getSettingApprovalDocId(settingApprovalDTO.getOrgId(),
					settingApprovalDTO.getFinYear(), settingApprovalDTO.getBranchCode(), screenCode);
			settingApprovalVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(settingApprovalDTO.getOrgId(),
							settingApprovalDTO.getFinYear(), settingApprovalDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// Create new ItemVO
			settingApprovalVO.setCreatedBy(settingApprovalDTO.getCreatedBy());
			settingApprovalVO.setUpdatedBy(settingApprovalDTO.getCreatedBy());
			createUpdateSettingApprovalVOBySettingApprovalDTO(settingApprovalDTO, settingApprovalVO);
			message = "SettingApproval Created Successfully";
		}

		// Save the ItemVO
		settingApprovalRepo.save(settingApprovalVO);
		commonNotificationService.generateNotification(settingApprovalVO.getScreenCode(), settingApprovalVO.getId(), oldSettingApproval,
				settingApprovalVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("settingApprovalVO", settingApprovalVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSettingApprovalVOBySettingApprovalDTO(SettingApprovalDTO settingApprovalDTO,
			SettingApprovalVO settingApprovalVO) throws IOException {
		settingApprovalVO.setRouteCardNo(settingApprovalDTO.getRouteCardNo());
		settingApprovalVO.setPartName(settingApprovalDTO.getPartName());
		settingApprovalVO.setPartNo(settingApprovalDTO.getPartNo());
		settingApprovalVO.setDrgNo(settingApprovalDTO.getDrgNo());
		settingApprovalVO.setOperation(settingApprovalDTO.getOperation());
		settingApprovalVO.setCycleTime(settingApprovalDTO.getCycleTime());
		settingApprovalVO.setMachineNo(settingApprovalDTO.getMachineNo());
		settingApprovalVO.setMachineName(settingApprovalDTO.getMachineName());
		settingApprovalVO.setSampleQty(settingApprovalDTO.getSampleQty());
		settingApprovalVO.setGrnClearTime(settingApprovalDTO.getGrnClearTime());
		settingApprovalVO.setDocFormatNo(settingApprovalDTO.getDocFormatNo());
		settingApprovalVO.setGeneralRemarks(settingApprovalDTO.getGeneralRemarks());
		settingApprovalVO.setOperatorName(settingApprovalDTO.getOperatorName());
		settingApprovalVO.setSetterName(settingApprovalDTO.getSetterName());
		settingApprovalVO.setShiftInCharge(settingApprovalDTO.getShiftInCharge());
		settingApprovalVO.setQualityName(settingApprovalDTO.getQualityName());
		settingApprovalVO.setNarration(settingApprovalDTO.getNarration());
		settingApprovalVO.setOrgId(settingApprovalDTO.getOrgId());
		settingApprovalVO.setBranch(settingApprovalDTO.getBranch());
		settingApprovalVO.setBranchCode(settingApprovalDTO.getBranchCode());
		settingApprovalVO.setFinYear(settingApprovalDTO.getFinYear());

		List<SettingApprovalDetailsVO> settingApprovalDetailsVOs = new ArrayList<>();
		for (SettingApprovalDetailsDTO settingApprovalDetailsDTO : settingApprovalDTO.getSettingApprovalDetailsDTO()) {
			SettingApprovalDetailsVO settingApprovalDetailsVO = new SettingApprovalDetailsVO();
			settingApprovalDetailsVO.setCharacteristics(settingApprovalDetailsDTO.getCharacteristics());
			settingApprovalDetailsVO.setSpecification(settingApprovalDetailsDTO.getSpecification());
			settingApprovalDetailsVO.setMethodOfInspection(settingApprovalDetailsDTO.getMethodOfInspection());
			settingApprovalDetailsVO.setLsl(settingApprovalDetailsDTO.getLsl());
			settingApprovalDetailsVO.setUsl(settingApprovalDetailsDTO.getUsl());
			settingApprovalDetailsVO.setSetter1(settingApprovalDetailsDTO.getSetter1());
			settingApprovalDetailsVO.setSetter2(settingApprovalDetailsDTO.getSetter2());
			settingApprovalDetailsVO.setSetter3(settingApprovalDetailsDTO.getSetter3());
			settingApprovalDetailsVO.setSetter4(settingApprovalDetailsDTO.getSetter4());
			settingApprovalDetailsVO.setSetter5(settingApprovalDetailsDTO.getSetter5());
			settingApprovalDetailsVO.setQulity1(settingApprovalDetailsDTO.getQuality1());
			settingApprovalDetailsVO.setQulity2(settingApprovalDetailsDTO.getQuality2());
			settingApprovalDetailsVO.setQulity3(settingApprovalDetailsDTO.getQuality3());
			settingApprovalDetailsVO.setQulity4(settingApprovalDetailsDTO.getQuality4());
			settingApprovalDetailsVO.setQulity5(settingApprovalDetailsDTO.getQuality5());
			settingApprovalDetailsVO.setRemarks(settingApprovalDetailsDTO.getRemarks());

			settingApprovalDetailsVO.setSettingApprovalVO(settingApprovalVO); // Set the reference in child entity
			settingApprovalDetailsVOs.add(settingApprovalDetailsVO);
		}

		settingApprovalVO.setSettingApprovalDetailsVO(settingApprovalDetailsVOs);

	}


	@Override
	public List<Map<String, Object>> getRouteCardDetailsForSettingApproval(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> routeCardDetails = settingApprovalRepo.findRouteCardDetailsForSettingApproval(orgId, finYear,
				branchCode);
		return getRouteCardDetailsForSettingApproval(routeCardDetails);
	}

	private List<Map<String, Object>> getRouteCardDetailsForSettingApproval(Set<Object[]> routeCardDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : routeCardDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partNo", ch[1] != null ? ch[1].toString() : "");
			map.put("partName", ch[2] != null ? ch[2].toString() : "");
			map.put("operation", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDrawingNoForSettingApproval(Long orgId, String finYear, String branchCode,
			String partNo) {
		Set<Object[]> drawingNo = settingApprovalRepo.findDrawingNoForSettingApproval(orgId, finYear, branchCode,
				partNo);
		return getDrawingNoForSettingApproval(drawingNo);
	}

	private List<Map<String, Object>> getDrawingNoForSettingApproval(Set<Object[]> drawingNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : drawingNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getMachineNoForSettingApproval(Long orgId, String finYear, String branchCode) {
		Set<Object[]> machineNo = settingApprovalRepo.findMachineNoForSettingApproval(orgId, finYear, branchCode);
		return getMachineNoForSettingApproval(machineNo);
	}

	private List<Map<String, Object>> getMachineNoForSettingApproval(Set<Object[]> machineNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : machineNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("machineNo", ch[0] != null ? ch[0].toString() : "");
			map.put("machineName", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getOperatorNameForSettingApproval(Long orgId, String branchCode) {
		Set<Object[]> employeeName = settingApprovalRepo.findOperatorNameForSettingApproval(orgId, branchCode);
		return getOperatorNameForSettingApproval(employeeName);
	}

	private List<Map<String, Object>> getOperatorNameForSettingApproval(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("operatorName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSetterNameForSettingApproval(Long orgId, String branchCode) {
		Set<Object[]> employeeName = settingApprovalRepo.findSetterNameForSettingApproval(orgId, branchCode);
		return getSetterNameForSettingApproval(employeeName);
	}

	private List<Map<String, Object>> getSetterNameForSettingApproval(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("setterName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getQualityNameForSettingApproval(Long orgId, String branchCode) {
		Set<Object[]> employeeName = settingApprovalRepo.findQualityNameForSettingApproval(orgId, branchCode);
		return getQualityNameForSettingApproval(employeeName);
	}

	private List<Map<String, Object>> getQualityNameForSettingApproval(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("qualityName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getShiftInChargeForSettingApproval(Long orgId, String branchCode) {
		Set<Object[]> employeeName = settingApprovalRepo.findShiftInChargeForSettingApproval(orgId, branchCode);
		return getShiftInChargeForSettingApproval(employeeName);
	}

	private List<Map<String, Object>> getShiftInChargeForSettingApproval(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("qualityName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<SampleApprovalVO> getAllSampleApprovalByOrgId(Long orgId, String finYear, String branchCode) {

		return sampleApprovalRepo.getAllSampleApprovalByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public SampleApprovalVO getSampleApprovalById(Long id) {

		return sampleApprovalRepo.getSampleApprovalById(id);
	}

	@Override
	public String getSampleApprovalDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "SAP";
		String result = sampleApprovalRepo.getSampleApprovalDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> createUpdateSampleApproval(@Valid SampleApprovalDTO sampleApprovalDTO)
			throws ApplicationException {
		String message;
		String screenCode = "SAP";
		SampleApprovalVO oldSampleApproval = null;
		
		SampleApprovalVO sampleApprovalVO = new SampleApprovalVO();

		if (sampleApprovalDTO.getId() != null) {
			oldSampleApproval = sampleApprovalRepo.findById(sampleApprovalDTO.getId())
					.orElseThrow(() -> new ApplicationException("InprocessInspection not found"));

			oldSampleApproval.getSampleApprovalDetailsVO().size(); // load


			entityManager.detach(oldSampleApproval); // detach snapshot
			
			// Fetch existing ItemVO for update
			sampleApprovalVO = sampleApprovalRepo.findById(sampleApprovalDTO.getId())
					.orElseThrow(() -> new ApplicationException("SampleApproval not found"));
			sampleApprovalVO.setUpdatedBy(sampleApprovalDTO.getCreatedBy());
			createUpdateSampleApprovalVOBySampleApprovalDTO(sampleApprovalDTO, sampleApprovalVO);
			message = "SampleApproval Updated Successfully";

			List<SampleApprovalDetailsVO> sampleApprovalDetailsVOs = sampleApprovalDetailsRepo
					.findBySampleApprovalVO(sampleApprovalVO);
			sampleApprovalDetailsRepo.deleteAll(sampleApprovalDetailsVOs);

		} else {

			String docId = sampleApprovalRepo.getSampleApprovalDocId(sampleApprovalDTO.getOrgId(),
					sampleApprovalDTO.getFinYear(), sampleApprovalDTO.getBranchCode(), screenCode);
			sampleApprovalVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(sampleApprovalDTO.getOrgId(),
							sampleApprovalDTO.getFinYear(), sampleApprovalDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// Create new ItemVO
			sampleApprovalVO.setCreatedBy(sampleApprovalDTO.getCreatedBy());
			sampleApprovalVO.setUpdatedBy(sampleApprovalDTO.getCreatedBy());
			createUpdateSampleApprovalVOBySampleApprovalDTO(sampleApprovalDTO, sampleApprovalVO);
			message = "SampleApproval Created Successfully";
		}

		// Save the ItemVO
		sampleApprovalRepo.save(sampleApprovalVO);
		commonNotificationService.generateNotification(sampleApprovalVO.getScreenCode(), sampleApprovalVO.getId(), oldSampleApproval,
				sampleApprovalVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("sampleApprovalVO", sampleApprovalVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSampleApprovalVOBySampleApprovalDTO(@Valid SampleApprovalDTO sampleApprovalDTO,
			SampleApprovalVO sampleApprovalVO) {
		sampleApprovalVO.setRouteCardNo(sampleApprovalDTO.getRouteCardNo());
		sampleApprovalVO.setPartName(sampleApprovalDTO.getPartName());
		sampleApprovalVO.setPartNo(sampleApprovalDTO.getPartNo());
		sampleApprovalVO.setDrgNo(sampleApprovalDTO.getDrgNo());
		sampleApprovalVO.setOperation(sampleApprovalDTO.getOperation());
		sampleApprovalVO.setCycleTime(sampleApprovalDTO.getCycleTime());
		sampleApprovalVO.setMachineNo(sampleApprovalDTO.getMachineNo());
		sampleApprovalVO.setMachineName(sampleApprovalDTO.getMachineName());
		sampleApprovalVO.setSampleQty(sampleApprovalDTO.getSampleQty());
		sampleApprovalVO.setJobOrderNo(sampleApprovalDTO.getJobOrderNo());
		sampleApprovalVO.setDocFormatNo(sampleApprovalDTO.getDocFormatNo());
		sampleApprovalVO.setGeneralRemarks(sampleApprovalDTO.getGeneralRemarks());
		sampleApprovalVO.setOperatorName(sampleApprovalDTO.getOperatorName());
		sampleApprovalVO.setShift(sampleApprovalDTO.getShift());
		sampleApprovalVO.setShiftDate(sampleApprovalDTO.getShiftDate());
		sampleApprovalVO.setShiftTime(sampleApprovalDTO.getShiftTime());
		sampleApprovalVO.setShiftInCharge(sampleApprovalDTO.getShiftInCharge());
		sampleApprovalVO.setQualityName(sampleApprovalDTO.getQualityName());
		sampleApprovalVO.setNarration(sampleApprovalDTO.getNarration());
		sampleApprovalVO.setOrgId(sampleApprovalDTO.getOrgId());
		sampleApprovalVO.setBranch(sampleApprovalDTO.getBranch());
		sampleApprovalVO.setBranchCode(sampleApprovalDTO.getBranchCode());
		sampleApprovalVO.setFinYear(sampleApprovalDTO.getFinYear());

		List<SampleApprovalDetailsVO> sampleApprovalDetailsVOs = new ArrayList<>();
		for (SampleApprovalDetailsDTO sampleApprovalDetailsDTO : sampleApprovalDTO.getSampleApprovalDetailsDTO()) {
			SampleApprovalDetailsVO sampleApprovalDetailsVO = new SampleApprovalDetailsVO();
			sampleApprovalDetailsVO.setCharacteristics(sampleApprovalDetailsDTO.getCharacteristics());
			sampleApprovalDetailsVO.setSpecification(sampleApprovalDetailsDTO.getSpecification());
			sampleApprovalDetailsVO.setMethodOfInspection(sampleApprovalDetailsDTO.getMethodOfInspection());
			sampleApprovalDetailsVO.setLsl(sampleApprovalDetailsDTO.getLsl());
			sampleApprovalDetailsVO.setUsl(sampleApprovalDetailsDTO.getUsl());
			sampleApprovalDetailsVO.setSimple1(sampleApprovalDetailsDTO.getSimple1());
			sampleApprovalDetailsVO.setSimple2(sampleApprovalDetailsDTO.getSimple2());
			sampleApprovalDetailsVO.setSimple3(sampleApprovalDetailsDTO.getSimple3());
			sampleApprovalDetailsVO.setSimple4(sampleApprovalDetailsDTO.getSimple4());
			sampleApprovalDetailsVO.setSimple5(sampleApprovalDetailsDTO.getSimple5());
			sampleApprovalDetailsVO.setOperator1(sampleApprovalDetailsDTO.getOperator1());
			sampleApprovalDetailsVO.setOperator2(sampleApprovalDetailsDTO.getOperator2());
			sampleApprovalDetailsVO.setOperator3(sampleApprovalDetailsDTO.getOperator3());
			sampleApprovalDetailsVO.setOperator4(sampleApprovalDetailsDTO.getOperator4());
			sampleApprovalDetailsVO.setOperator5(sampleApprovalDetailsDTO.getOperator5());
			sampleApprovalDetailsVO.setStatus(sampleApprovalDetailsDTO.getStatus());

			sampleApprovalDetailsVO.setSampleApprovalVO(sampleApprovalVO); // Set the reference in child entity
			sampleApprovalDetailsVOs.add(sampleApprovalDetailsVO);
		}
		sampleApprovalVO.setSampleApprovalDetailsVO(sampleApprovalDetailsVOs);

//		List<SampleApprovalImagesVO> sampleApprovalImagesVOs = new ArrayList<>();
//		for (SampleApprovalImagesDTO sampleApprovalImagesDTO : sampleApprovalDTO.getSampleApprovalImagesDTO()) {
//			SampleApprovalImagesVO sampleApprovalImagesVO = new SampleApprovalImagesVO();
//			sampleApprovalImagesVO.setSampleApprovalVO(sampleApprovalVO);
//			sampleApprovalImagesVOs.add(sampleApprovalImagesVO);
//		}
//		sampleApprovalVO.setSampleApprovalDetailsVO(sampleApprovalDetailsVOs);

	}

	@Override
	public List<Map<String, Object>> getRouteCardDetailsForSampleApproval(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> routeCardDetails = sampleApprovalRepo.findRouteCardDetailsForSampleApproval(orgId, finYear,
				branchCode);
		return getRouteCardDetailsForSampleApproval(routeCardDetails);
	}

	private List<Map<String, Object>> getRouteCardDetailsForSampleApproval(Set<Object[]> routeCardDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : routeCardDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partNo", ch[1] != null ? ch[1].toString() : "");
			map.put("partName", ch[2] != null ? ch[2].toString() : "");
			map.put("operation", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDrawingMasterNoForSampleApproval(Long orgId, String finYear, String branchCode,
			String partNo) {
		Set<Object[]> drawingMasterNo = sampleApprovalRepo.findDrawingMasterNoForSampleApproval(orgId, finYear,
				branchCode, partNo);
		return DrawingMasterNoForSampleApproval(drawingMasterNo);
	}

	private List<Map<String, Object>> DrawingMasterNoForSampleApproval(Set<Object[]> drawingMasterNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : drawingMasterNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingMasterNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getMachineNoForSampleApproval(Long orgId, String finYear, String branchCode) {
		Set<Object[]> machineNo = sampleApprovalRepo.findMachineNoForSampleApproval(orgId, finYear, branchCode);
		return getMachineNoForSampleApproval(machineNo);
	}

	private List<Map<String, Object>> getMachineNoForSampleApproval(Set<Object[]> machineNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : machineNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("machineNo", ch[0] != null ? ch[0].toString() : "");
			map.put("machineName", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getJobOrderNoForSampleApproval(Long orgId, String finYear, String branchCode,
			String routeCardNo, String operation) {
		Set<Object[]> jobOrderNo = sampleApprovalRepo.findJobOrderNoForSampleApproval(orgId, finYear, branchCode,
				routeCardNo, operation);
		return getJobOrderNoForSampleApproval(jobOrderNo);
	}

	private List<Map<String, Object>> getJobOrderNoForSampleApproval(Set<Object[]> jobOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : jobOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobOrderNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getSettingApprovalReport(Long orgId, String branchCode, String fromDate,
			String toDate, String routeCardNo) {
		Set<Object[]> settingApproval = settingApprovalRepo.getSettingApprovalReport(orgId, branchCode, fromDate,
				toDate, routeCardNo);
		return getSettingApprovalReport(settingApproval);
	}

	private List<Map<String, Object>> getSettingApprovalReport(Set<Object[]> rows) {

		Map<Object, Map<String, Object>> headerMap = new LinkedHashMap<>();

		for (Object[] ch : rows) {

			Object headerId = ch[0]; // settingapprovalid

			// ===== HEADER =====
			Map<String, Object> header = headerMap.get(headerId);

			if (header == null) {
				header = new HashMap<>();

				header.put("settingapprovalid", ch[0] != null ? ch[0].toString() : "");
				header.put("docid", ch[1] != null ? ch[1].toString() : "");
				header.put("partno", ch[2] != null ? ch[2].toString() : "");
				header.put("partname", ch[3] != null ? ch[3].toString() : "");
				header.put("operation", ch[4] != null ? ch[4].toString() : "");
				header.put("operatorname", ch[5] != null ? ch[5].toString() : "");
				header.put("routeCardNo", ch[22] != null ? ch[22].toString() : "");

				header.put("details", new ArrayList<Map<String, Object>>());

				headerMap.put(headerId, header);
			}

			// ===== CHILD =====
			Map<String, Object> child = new HashMap<>();

			child.put("settingapprovaldetailsid", ch[6] != null ? ch[6].toString() : "");
			child.put("characteristics", ch[7] != null ? ch[7].toString() : "");
			child.put("lsl", ch[8] != null ? ch[8].toString() : "");
			child.put("usl", ch[9] != null ? ch[9].toString() : "");
			child.put("methodofinspection", ch[10] != null ? ch[10].toString() : "");
			child.put("specification", ch[11] != null ? ch[11].toString() : "");

			child.put("qulity1", ch[12] != null ? ch[12].toString() : "");
			child.put("qulity2", ch[13] != null ? ch[13].toString() : "");
			child.put("qulity3", ch[14] != null ? ch[14].toString() : "");
			child.put("qulity4", ch[15] != null ? ch[15].toString() : "");
			child.put("qulity5", ch[16] != null ? ch[16].toString() : "");

			child.put("setter1", ch[17] != null ? ch[17].toString() : "");
			child.put("setter2", ch[18] != null ? ch[18].toString() : "");
			child.put("setter3", ch[19] != null ? ch[19].toString() : "");
			child.put("setter4", ch[20] != null ? ch[20].toString() : "");
			child.put("setter5", ch[21] != null ? ch[21].toString() : "");

			// Add child to header
			List<Map<String, Object>> childList = (List<Map<String, Object>>) header.get("details");

			childList.add(child);
		}

		return new ArrayList<>(headerMap.values());
	}

	@Override
	public List<Map<String, Object>> getSampleApprovalDetails(Long orgId, String fromdate, String todate,
			String routeCardNo) {
		Set<Object[]> sampleApprovalDetails = sampleApprovalRepo.getSampleApprovalDetails(orgId, fromdate, todate,
				routeCardNo);
		return getSampleApprovalDetails(sampleApprovalDetails);
	}

	private List<Map<String, Object>> getSampleApprovalDetails(Set<Object[]> sampleApprovalDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : sampleApprovalDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("docdate", ch[0] != null ? ch[0].toString() : "");
			map.put("docid", ch[1] != null ? ch[1].toString() : "");
			map.put("drgno", ch[2] != null ? ch[2].toString() : "");
			map.put("joborderno", ch[3] != null ? ch[3].toString() : "");
			map.put("machinename", ch[4] != null ? ch[4].toString() : "");
			map.put("machineno", ch[5] != null ? ch[5].toString() : "");
			map.put("operation", ch[6] != null ? ch[6].toString() : "");
			map.put("operatorname", ch[7] != null ? ch[7].toString() : "");
			map.put("orgid", ch[8] != null ? ch[8].toString() : "");
			map.put("partname", ch[9] != null ? ch[9].toString() : "");
			map.put("partno", ch[10] != null ? ch[10].toString() : "");
			map.put("qualityname", ch[11] != null ? ch[11].toString() : "");
			map.put("routecardno", ch[12] != null ? ch[12].toString() : "");
			map.put("sampleqty", ch[13] != null ? ch[13].toString() : "");
			map.put("shiftday", ch[14] != null ? ch[14].toString() : "");
			map.put("shift", ch[15] != null ? ch[15].toString() : "");
			map.put("shifttime", ch[16] != null ? ch[16].toString() : "");
			map.put("characteristics", ch[17] != null ? ch[17].toString() : "");
			map.put("isl", ch[18] != null ? ch[18].toString() : "");
			map.put("operartor1", ch[19] != null ? ch[19].toString() : "");
			map.put("operartor2", ch[20] != null ? ch[20].toString() : "");
			map.put("operartor3", ch[21] != null ? ch[21].toString() : "");
			map.put("specification", ch[22] != null ? ch[22].toString() : "");
			map.put("sampleApprovalId", ch[23] != null ? ch[23].toString() : "");

			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<Map<String, Object>> getEmployeeNameBasedOnDesgnation(Long orgId, String branchCode) {
		Set<Object[]> employeeName = settingApprovalRepo.getEmployeeNameBasedOnDesgnation(orgId, branchCode);
		return getEmployeeNameBasedOnDesgnation(employeeName);
	}

	private List<Map<String, Object>> getEmployeeNameBasedOnDesgnation(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			map.put("designation", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateSettingApproval(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		SettingApprovalVO settingApprovalVO = settingApprovalRepo.findByDocId(docId);

		String message = "ncproductregister updated successfully";

		// BASIC MAPPING

		settingApprovalVO = settingApprovalRepo.save(settingApprovalVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<SettingApprovalAttachmentVO> oldDocs = settingApprovalAttachmentRepo
				.findBySettingApprovalVO(settingApprovalVO);
		settingApprovalAttachmentRepo.deleteAll(oldDocs);

		if (settingApprovalVO.getDocuments() != null) {
			settingApprovalVO.getDocuments().clear();
		} else {
			settingApprovalVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (SettingApprovalAttachmentVO doc : oldDocs) {
			deleteFileSetting(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(settingApprovalVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("settingApprovalVO", settingApprovalVO);

		return response;
	}

	private void replaceDocuments(SettingApprovalVO settingApprovalVO, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(settingApprovalVO, files, docFolder, docId);
	}

	private void saveFiles(SettingApprovalVO settingApprovalVO, MultipartFile[] files, Path docFolder, String docId)
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
						.path("/api/qualityapproval/viewFileSetting/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				SettingApprovalAttachmentVO attach = new SettingApprovalAttachmentVO();
				attach.setSettingApprovalVO(settingApprovalVO);
				attach.setFileName(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileName(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (settingApprovalVO.getDocuments() == null) {
					settingApprovalVO.setDocuments(new ArrayList<>());
				}

				settingApprovalVO.getDocuments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSetting(String path) {
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
	public ResponseEntity<byte[]> viewFileSetting(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFiles(request, "/api/qualityapproval/viewFileSetting/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFiles(HttpServletRequest request, String apiPrefix, String uploadBasePath)
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
	public Map<String, Object> createUpdateSampleApproval(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		SampleApprovalVO sampleApprovalVO = sampleApprovalRepo.findByDocId(docId);

		// BASIC MAPPING

		sampleApprovalVO = sampleApprovalRepo.save(sampleApprovalVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<SampleApprovalAttachmentVO> oldDocs = sampleApprovalAttachmentRepo
				.findBySampleApprovalVO(sampleApprovalVO);
		sampleApprovalAttachmentRepo.deleteAll(oldDocs);

		if (sampleApprovalVO.getDocuments() != null) {
			sampleApprovalVO.getDocuments().clear();
		} else {
			sampleApprovalVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (SampleApprovalAttachmentVO doc : oldDocs) {
			deleteFileSafelyNcProduct(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(sampleApprovalVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("sampleApprovalVO", sampleApprovalVO);

		return response;
	}

	private void replaceDocuments(SampleApprovalVO sampleApprovalVO, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(sampleApprovalVO, files, docFolder, docId);
	}

	private void saveFiles(SampleApprovalVO sampleApprovalVO, MultipartFile[] files, Path docFolder, String docId)
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
						.path("/api/qualityapproval/viewFileSample/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				SampleApprovalAttachmentVO attach = new SampleApprovalAttachmentVO();
				attach.setSampleApprovalVO(sampleApprovalVO);
				attach.setFileName(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileName(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (sampleApprovalVO.getDocuments() == null) {
					sampleApprovalVO.setDocuments(new ArrayList<>());
				}

				sampleApprovalVO.getDocuments().add(attach);
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
	public ResponseEntity<byte[]> viewFileSample(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFileNcProduct(request, "/api/qualityapproval/viewFileSample/", uploadBasePath);
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
	
	// SampleApproval Image Attachment
	
	@Override
	public List<SampleImageResponseDTO> getSampleApprovalImages(Long id) throws Exception {

	SampleApprovalVO record = sampleApprovalRepo
				.getAllSampleApprovalImagesById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<SampleApprovalAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<SampleImageResponseDTO> responseList = new ArrayList<>();

		for (SampleApprovalAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			SampleImageResponseDTO dto = new SampleImageResponseDTO();
			dto.setFileName(attachment.getFileName());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

}
