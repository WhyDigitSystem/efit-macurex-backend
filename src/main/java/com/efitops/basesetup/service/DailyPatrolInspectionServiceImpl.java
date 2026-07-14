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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.efitops.basesetup.ResponseDTO.DailyPatrolInspectionDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DailyPatrolInspectionResponseDTO;
import com.efitops.basesetup.dto.DailyPatrolImageResponseDTO;
import com.efitops.basesetup.dto.DailyPatrolInspectionDTO;
import com.efitops.basesetup.dto.DailyPatrolInspectionDetails1DTO;
import com.efitops.basesetup.dto.DailyPatrolInspectionDocumentsDTO;
import com.efitops.basesetup.dto.DailyPatrolInspectionFinalDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.entity.DailyPatrolInspectionAttachmentVO;
import com.efitops.basesetup.entity.DailyPatrolInspectionDetails1VO;
import com.efitops.basesetup.entity.DailyPatrolInspectionDocumentsVO;
import com.efitops.basesetup.entity.DailyPatrolInspectionFinalVO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DailyPatrolInspectionAttachmentRepo;
import com.efitops.basesetup.repo.DailyPatrolInspectionDetails1Repo;
import com.efitops.basesetup.repo.DailyPatrolInspectionDocumentsRepo;
import com.efitops.basesetup.repo.DailyPatrolInspectionFinalRepo;
import com.efitops.basesetup.repo.DailyPatrolInspectionRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.MachineMasterRepo;

@Service
public class DailyPatrolInspectionServiceImpl implements DailyPatrolInspectionService {

	@Autowired
	DailyPatrolInspectionRepo dailyPatrolInspectionRepo;

	@Autowired
	DailyPatrolInspectionDetails1Repo dailyPatrolInspectionDetails1Repo;

	@Autowired
	DailyPatrolInspectionFinalRepo dailyPatrolInspectionFinalRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	MachineMasterRepo machineMasterRepo;

	@Autowired
	DailyPatrolInspectionDocumentsRepo dailyPatrolInspectionDocumentsRepo;

	@Autowired
	DailyPatrolInspectionAttachmentRepo dailyPatrolInspectionAttachmentRepo;
	
	@Autowired
    private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;

	public static final Logger LOGGER = LoggerFactory.getLogger(DailyPatrolInspectionServiceImpl.class);

//	@Override
//	public Map<String, Object> updateCreateDailyPatrolInspection(
//			@Valid DailyPatrolInspectionDTO dailyPatrolInspectionDTO) throws ApplicationException {
//
//		DailyPatrolInspectionVO dailyPatrolInspectionVO = null;
//		String screenCode = "DPI";
//		String message = null;
//
//		if (ObjectUtils.isEmpty(dailyPatrolInspectionDTO.getId())) {
//
//			dailyPatrolInspectionVO = new DailyPatrolInspectionVO();
//
//			String docId = dailyPatrolInspectionRepo.getDailyPatrolInspectionDocId(dailyPatrolInspectionDTO.getOrgId(),
//					dailyPatrolInspectionDTO.getFinYear(), dailyPatrolInspectionDTO.getBranchCode(), screenCode);
//			dailyPatrolInspectionVO.setDocId(docId);
//
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(dailyPatrolInspectionDTO.getOrgId(),
//							dailyPatrolInspectionDTO.getFinYear(), dailyPatrolInspectionDTO.getBranchCode(),
//							screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			dailyPatrolInspectionVO.setCreatedBy(dailyPatrolInspectionDTO.getCreatedBy());
//			dailyPatrolInspectionVO.setUpdatedBy(dailyPatrolInspectionDTO.getCreatedBy());
//
//			message = "DailyPatrolInspection Creation Successfull";
//
//		} else {
//
//			dailyPatrolInspectionVO = dailyPatrolInspectionRepo.findById(dailyPatrolInspectionDTO.getId())
//					.orElseThrow(() -> new ApplicationException(
//							"DailyPatrolInspection  Not Found with id: " + dailyPatrolInspectionDTO.getId()));
//			dailyPatrolInspectionVO.setUpdatedBy(dailyPatrolInspectionDTO.getCreatedBy());
//
//			message = "DailyPatrolInspection Updation Successfull";
//
//		}
//
//		dailyPatrolInspectionVO = getDailyPatrolInspectionVOFromDailyPatrolInspectionVO(dailyPatrolInspectionVO,
//				dailyPatrolInspectionDTO);
//		dailyPatrolInspectionRepo.save(dailyPatrolInspectionVO);
//
//		Map<String, Object> response = new HashMap<String, Object>();
//		response.put("message", message);
//		response.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);
//		return response;
//
//	}
//
//	private DailyPatrolInspectionVO getDailyPatrolInspectionVOFromDailyPatrolInspectionVO(
//			DailyPatrolInspectionVO dailyPatrolInspectionVO, @Valid DailyPatrolInspectionDTO dailyPatrolInspectionDTO) {
//
//		dailyPatrolInspectionVO.setRouteCardNo(dailyPatrolInspectionDTO.getRouteCardNo());
//		dailyPatrolInspectionVO.setPartNo(dailyPatrolInspectionDTO.getPartNo());
//		dailyPatrolInspectionVO.setPartName(dailyPatrolInspectionDTO.getPartName());
//		dailyPatrolInspectionVO.setDrgNo(dailyPatrolInspectionDTO.getDrgNo());
//		dailyPatrolInspectionVO.setShift(dailyPatrolInspectionDTO.getShift());
//		dailyPatrolInspectionVO.setMachineNo(dailyPatrolInspectionDTO.getMachineNo());
//		dailyPatrolInspectionVO.setMachineName(dailyPatrolInspectionDTO.getMachineName());
//		dailyPatrolInspectionVO.setTime(dailyPatrolInspectionDTO.getTime());
//		dailyPatrolInspectionVO.setJobOrderNo(dailyPatrolInspectionDTO.getJobOrderNo());
//		dailyPatrolInspectionVO.setDocumentFormatNo(dailyPatrolInspectionDTO.getDocumentFormatNo());
//		dailyPatrolInspectionVO.setNarration(dailyPatrolInspectionDTO.getNarration());
//		dailyPatrolInspectionVO.setCreatedBy(dailyPatrolInspectionDTO.getCreatedBy());
//		dailyPatrolInspectionVO.setActive(dailyPatrolInspectionDTO.isActive());
//		dailyPatrolInspectionVO.setOrgId(dailyPatrolInspectionDTO.getOrgId());
//		dailyPatrolInspectionVO.setBranch(dailyPatrolInspectionDTO.getBranch());
//		dailyPatrolInspectionVO.setBranchCode(dailyPatrolInspectionDTO.getBranchCode());
//		dailyPatrolInspectionVO.setFinYear(dailyPatrolInspectionDTO.getFinYear());
//
//		if (dailyPatrolInspectionDTO.getId() != null) {
//
//			List<DailyPatrolInspectionDetails1VO> dailyPatrolInspectionDetails1VOs = dailyPatrolInspectionDetails1Repo
//					.findByDailyPatrolInspectionVO(dailyPatrolInspectionVO);
//			dailyPatrolInspectionDetails1Repo.deleteAll(dailyPatrolInspectionDetails1VOs);
//
//			List<DailyPatrolInspectionFinalVO> dailyPatrolInspectionFinalVOs = dailyPatrolInspectionFinalRepo
//					.findByDailyPatrolInspectionVO(dailyPatrolInspectionVO);
//			dailyPatrolInspectionFinalRepo.deleteAll(dailyPatrolInspectionFinalVOs);
//
//		}
//
//		List<DailyPatrolInspectionDetails1VO> dailyPatrolInspectionDetails1VOs = new ArrayList<DailyPatrolInspectionDetails1VO>();
//		for (DailyPatrolInspectionDetails1DTO dailyPatrolInspectionDetails1DTO : dailyPatrolInspectionDTO
//				.getDailyPatrolInspectionDetails1DTO()) {
//
//			DailyPatrolInspectionDetails1VO dailyPatrolInspectionDetails1VO = new DailyPatrolInspectionDetails1VO();
//
//			dailyPatrolInspectionDetails1VO.setCharacteristic(dailyPatrolInspectionDetails1DTO.getCharacteristic());
//			dailyPatrolInspectionDetails1VO
//					.setMethodOfInspection(dailyPatrolInspectionDetails1DTO.getMethodOfInspection());
//			dailyPatrolInspectionDetails1VO.setLsl(dailyPatrolInspectionDetails1DTO.getLsl());
//			dailyPatrolInspectionDetails1VO.setUsl(dailyPatrolInspectionDetails1DTO.getUsl());
//			dailyPatrolInspectionDetails1VO.setSample1(dailyPatrolInspectionDetails1DTO.getSample1());
//			dailyPatrolInspectionDetails1VO.setSample2(dailyPatrolInspectionDetails1DTO.getSample2());
//			dailyPatrolInspectionDetails1VO.setSample3(dailyPatrolInspectionDetails1DTO.getSample3());
//			dailyPatrolInspectionDetails1VO.setSample4(dailyPatrolInspectionDetails1DTO.getSample4());
//			dailyPatrolInspectionDetails1VO.setSample5(dailyPatrolInspectionDetails1DTO.getSample5());
//			dailyPatrolInspectionDetails1VO.setSample6(dailyPatrolInspectionDetails1DTO.getSample6());
//			dailyPatrolInspectionDetails1VO.setSample7(dailyPatrolInspectionDetails1DTO.getSample7());
//			dailyPatrolInspectionDetails1VO.setSample8(dailyPatrolInspectionDetails1DTO.getSample8());
//			dailyPatrolInspectionDetails1VO.setSample9(dailyPatrolInspectionDetails1DTO.getSample9());
//			dailyPatrolInspectionDetails1VO.setSample10(dailyPatrolInspectionDetails1DTO.getSample10());
//			dailyPatrolInspectionDetails1VO.setStatus(dailyPatrolInspectionDetails1DTO.getStatus());
//			dailyPatrolInspectionDetails1VO.setRemarks(dailyPatrolInspectionDetails1DTO.getRemarks());
//
//			dailyPatrolInspectionDetails1VO.setDailyPatrolInspectionVO(dailyPatrolInspectionVO);
//			dailyPatrolInspectionDetails1VOs.add(dailyPatrolInspectionDetails1VO);
//		}
//
//		dailyPatrolInspectionVO.setDailyPatrolInspectionDetails1VO(dailyPatrolInspectionDetails1VOs);
//
//		// DailyPatrolInspectionFinalDTO
//
//		List<DailyPatrolInspectionFinalVO> dailyPatrolInspectionFinalVOs = new ArrayList<DailyPatrolInspectionFinalVO>();
//		for (DailyPatrolInspectionFinalDTO dailyPatrolInspectionFinaDTO : dailyPatrolInspectionDTO
//				.getDailyPatrolInspectionFinalDTO()) {
//
//			DailyPatrolInspectionFinalVO dailyPatrolInspectionFinalVO = new DailyPatrolInspectionFinalVO();
//
//			dailyPatrolInspectionFinalVO.setInCharge(dailyPatrolInspectionFinaDTO.getInCharge());
//			dailyPatrolInspectionFinalVO.setInspectionBy(dailyPatrolInspectionFinaDTO.getInspectionBy());
//			dailyPatrolInspectionFinalVO.setRemarks(dailyPatrolInspectionFinaDTO.getRemarks());
//
//			dailyPatrolInspectionFinalVO.setDailyPatrolInspectionVO(dailyPatrolInspectionVO);
//			dailyPatrolInspectionFinalVOs.add(dailyPatrolInspectionFinalVO);
//		}
//
//		dailyPatrolInspectionVO.setDailyPatrolInspectionFinalVO(dailyPatrolInspectionFinalVOs);
//
//		return dailyPatrolInspectionVO;
//	}

	@Override
	public Map<String, Object> updateCreateDailyPatrolInspection(DailyPatrolInspectionDTO dto,
			List<MultipartFile> files) throws ApplicationException, IOException {

		DailyPatrolInspectionVO vo;
		String message;
		String screenCode = "DPI";
		DailyPatrolInspectionVO oldDailyPatrolInspection = null;

		// =========================
		// CREATE / UPDATE
		// =========================

		if (dto.getId() == null) {
			oldDailyPatrolInspection = dailyPatrolInspectionRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("DailyPatrolInspection not found"));

			oldDailyPatrolInspection.getDailyPatrolInspectionDetails1VO().size(); // load
			oldDailyPatrolInspection.getDailyPatrolInspectionFinalVO().size(); // load
			oldDailyPatrolInspection.getDailyPatrolInspectionDocumentsVO().size(); // load

			entityManager.detach(oldDailyPatrolInspection); // detach snapshot

			vo = new DailyPatrolInspectionVO();

			String docId = dailyPatrolInspectionRepo.getDailyPatrolInspectionDocId(dto.getOrgId(), dto.getFinYear(),
					dto.getBranchCode(), screenCode);

			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO map = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(dto.getOrgId(), dto.getFinYear(),
							dto.getBranchCode(), screenCode);

			if (map == null) {
				throw new ApplicationException("Document Mapping Not Found");
			}

			map.setLastno(map.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(map);
			
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());

			message = "Daily Patrol Inspection Created Successfully";

		} else {

			vo = dailyPatrolInspectionRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Record Not Found"));

			vo.setUpdatedBy(dto.getCreatedBy());

			message = "Daily Patrol Inspection Updated Successfully";

			// delete old children
			dailyPatrolInspectionDetails1Repo
					.deleteAll(dailyPatrolInspectionDetails1Repo.findByDailyPatrolInspectionVO(vo));

			dailyPatrolInspectionFinalRepo.deleteAll(dailyPatrolInspectionFinalRepo.findByDailyPatrolInspectionVO(vo));

			dailyPatrolInspectionDocumentsRepo
					.deleteAll(dailyPatrolInspectionDocumentsRepo.findByDailyPatrolInspectionVO(vo));
		}

		// =========================
		// PARENT
		// =========================

		vo.setRouteCardNo(dto.getRouteCardNo());
		vo.setPartNo(dto.getPartNo());
		vo.setPartName(dto.getPartName());
		vo.setDrgNo(dto.getDrgNo());
		vo.setShift(dto.getShift());
		vo.setMachineNo(dto.getMachineNo());
		vo.setMachineName(dto.getMachineName());
		vo.setTime(dto.getTime());
		vo.setJobOrderNo(dto.getJobOrderNo());
		vo.setDocumentFormatNo(dto.getDocumentFormatNo());
		vo.setNarration(dto.getNarration());
		vo.setActive(dto.isActive());
		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setFinYear(dto.getFinYear());

		// =========================
		// DETAILS
		// =========================

		List<DailyPatrolInspectionDetails1VO> detailsList = new ArrayList<>();

		if (dto.getDailyPatrolInspectionDetails1DTO() != null) {

			for (DailyPatrolInspectionDetails1DTO d : dto.getDailyPatrolInspectionDetails1DTO()) {

				DailyPatrolInspectionDetails1VO det = new DailyPatrolInspectionDetails1VO();

				det.setCharacteristic(d.getCharacteristic());
				det.setMethodOfInspection(d.getMethodOfInspection());
				det.setLsl(d.getLsl());
				det.setUsl(d.getUsl());
				det.setSample1(d.getSample1());
				det.setSample2(d.getSample2());
				det.setSample3(d.getSample3());
				det.setSample4(d.getSample4());
				det.setSample5(d.getSample5());
				det.setSample6(d.getSample6());
				det.setSample7(d.getSample7());
				det.setSample8(d.getSample8());
				det.setSample9(d.getSample9());
				det.setSample10(d.getSample10());
				det.setStatus(d.getStatus());
				det.setRemarks(d.getRemarks());

				det.setDailyPatrolInspectionVO(vo);
				detailsList.add(det);
			}
		}

		vo.setDailyPatrolInspectionDetails1VO(detailsList);

		// =========================
		// FINAL
		// =========================

		List<DailyPatrolInspectionFinalVO> finalList = new ArrayList<>();

		if (dto.getDailyPatrolInspectionFinalDTO() != null) {

			for (DailyPatrolInspectionFinalDTO f : dto.getDailyPatrolInspectionFinalDTO()) {

				DailyPatrolInspectionFinalVO fin = new DailyPatrolInspectionFinalVO();

				fin.setInspectionBy(f.getInspectionBy());
				fin.setInCharge(f.getInCharge());
				fin.setRemarks(f.getRemarks());

				fin.setDailyPatrolInspectionVO(vo);
				finalList.add(fin);
			}
		}

		vo.setDailyPatrolInspectionFinalVO(finalList);

		// =========================
		// DOCUMENTS
		// =========================

		List<DailyPatrolInspectionDocumentsVO> docList = new ArrayList<>();

		// JSON metadata
		if (dto.getDailyPatrolInspectionDocumentsDTO() != null) {

			for (DailyPatrolInspectionDocumentsDTO d : dto.getDailyPatrolInspectionDocumentsDTO()) {

				DailyPatrolInspectionDocumentsVO doc = new DailyPatrolInspectionDocumentsVO();

				doc.setDocumentName(d.getDocumentName());
				doc.setDocumentType(d.getDocumentType());
				doc.setDailyPatrolInspectionVO(vo);

				docList.add(doc);
			}
		}

		// FILES
		if (files != null) {

			for (MultipartFile file : files) {

				DailyPatrolInspectionDocumentsVO doc = new DailyPatrolInspectionDocumentsVO();

				doc.setDocumentName(file.getOriginalFilename());
				doc.setDocumentType(file.getContentType());
				doc.setDocumentData(file.getBytes());
				doc.setDailyPatrolInspectionVO(vo);

				docList.add(doc);
			}
		}

		vo.setDailyPatrolInspectionDocumentsVO(docList);

		// =========================
		// SAVE
		// =========================

		dailyPatrolInspectionRepo.save(vo);
		commonNotificationService.generateNotification(vo.getScreenCode(), vo.getId(), oldDailyPatrolInspection,
				vo);
		
		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("dailyPatrolInspectionVO", vo);

		return response;
	}

	@Override
	public String getDailyPatrolInspectionDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "DPI";
		String result = dailyPatrolInspectionRepo.getDailyPatrolInspectionDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public Optional<DailyPatrolInspectionVO> getDailyPatrolInspectionById(Long id) {
		return dailyPatrolInspectionRepo.getDailyPatrolInspectionById(id);
	}

	@Override
	public List<DailyPatrolInspectionVO> getAllDailyPatrolInspection(Long orgId, String finYear, String branchCode) {

		return dailyPatrolInspectionRepo.getAllDPI(orgId, finYear, branchCode);
	}

	@Override
	public List<Map<String, Object>> getRouteCardNoForDailyPatrollInspection(Long orgId, String finYear,
			String branchCode) {

		Set<Object[]> getRoute = dailyPatrolInspectionRepo.getRouteCardNoForDailyPatrollInspection(orgId, finYear,
				branchCode);
		return getRouteDetails(getRoute);
	}

	private List<Map<String, Object>> getRouteDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partName", ch[1] != null ? ch[1].toString() : "");
			map.put("partDesc", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getMachineDetailsForDailyPatrolInspection(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> machineNo = dailyPatrolInspectionRepo.getMachineDetailsForDailyPatrolInspection(orgId, finYear,
				branchCode);
		return getMachine(machineNo);
	}

	private List<Map<String, Object>> getMachine(Set<Object[]> machineNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : machineNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("machineNo", ch[0] != null ? ch[0].toString() : "");
			map.put("machineName", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getShiftDetails(Long orgId) {
		Set<Object[]> getShift = dailyPatrolInspectionRepo.getShiftDetails1(orgId);
		return getShift1(getShift);
	}

	private List<Map<String, Object>> getShift1(Set<Object[]> chCode) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();

			map.put("shiftId", ch[0] != null ? ((Number) ch[0]).longValue() : 0L);
			map.put("shiftName", ch[1] != null ? ch[1].toString() : "");
			map.put("timings", ch[2] != null ? ch[2].toString() : "");

			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getJobOrderNoForDailyPatrolInspection(Long orgId, String finYear,
			String branchCode, String routeCardNo) {
		Set<Object[]> jobOrderNo = dailyPatrolInspectionRepo.getJobOrderNoForDailyPatrolInspection(orgId, finYear,
				branchCode, routeCardNo);
		return getJobOrderNoForDailyPatrolInspection(jobOrderNo);
	}

	private List<Map<String, Object>> getJobOrderNoForDailyPatrolInspection(Set<Object[]> jobOrderNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : jobOrderNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobOrderNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDrawingMasterNoForDailyPatrolInspection(Long orgId, String finYear,
			String branchCode, String partNo) {
		Set<Object[]> drawingMasterNo = dailyPatrolInspectionRepo.getDrawingMasterNoForDailyPatrolInspection(orgId,
				finYear, branchCode, partNo);
		return getDrawingMasterNoForDailyPatrolInspection(drawingMasterNo);
	}

	private List<Map<String, Object>> getDrawingMasterNoForDailyPatrolInspection(Set<Object[]> drawingMasterNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : drawingMasterNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("drawingMasterNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getDailyPatrolInspectionDetails(Long orgId, String fromdate, String todate) {
		Set<Object[]> dailyPatrolInspectionDetails = dailyPatrolInspectionRepo.getDailyPatrolInspectionDetails(orgId,
				fromdate, todate);
		return getDailyPatrolInspectionDetails(dailyPatrolInspectionDetails);
	}

	private List<Map<String, Object>> getDailyPatrolInspectionDetails(Set<Object[]> dailyPatrolInspectionDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : dailyPatrolInspectionDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgid", ch[0] != null ? ch[0].toString() : "");
			map.put("docid", ch[1] != null ? ch[1].toString() : "");
			map.put("docdate", ch[2] != null ? ch[2].toString() : "");
			map.put("routecardno", ch[3] != null ? ch[3].toString() : "");
			map.put("partno", ch[4] != null ? ch[4].toString() : "");
			map.put("partname", ch[5] != null ? ch[5].toString() : "");
			map.put("drgno", ch[6] != null ? ch[6].toString() : "");
			map.put("shift", ch[7] != null ? ch[7].toString() : "");
			map.put("machineno", ch[8] != null ? ch[8].toString() : "");
			map.put("machinename", ch[9] != null ? ch[9].toString() : "");
			map.put("time", ch[10] != null ? ch[10].toString() : "");
			map.put("joborderno", ch[11] != null ? ch[11].toString() : "");
			map.put("characteristic", ch[12] != null ? ch[12].toString() : "");
			map.put("sample1", ch[13] != null ? ch[13].toString() : "");
			map.put("sample2", ch[14] != null ? ch[14].toString() : "");
			map.put("sample3", ch[15] != null ? ch[15].toString() : "");
			map.put("dailyPatrolInspection", ch[16] != null ? ch[16].toString() : "");

			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<Map<String, Object>> getEmployeeNameBasedOnDepartment(Long orgId, String branchCode) {
		Set<Object[]> employeeName = dailyPatrolInspectionRepo.getEmployeeNameBasedOnDepartment(orgId, branchCode);
		return getEmployeeNameBasedOnDepartment(employeeName);
	}

	private List<Map<String, Object>> getEmployeeNameBasedOnDepartment(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			map.put("department", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public DailyPatrolInspectionResponseDTO previewDailyPatrolInspectionExcel(MultipartFile file) throws Exception {

		Workbook wb = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = wb.getSheetAt(0);

		// =====================
		// PARENT
		// =====================
		Row parentRow = sheet.getRow(1);

		DailyPatrolInspectionResponseDTO parent = new DailyPatrolInspectionResponseDTO();

		parent.setRouteCardNo(getString(parentRow.getCell(0)));
		parent.setPartNo(getString(parentRow.getCell(1)));
		parent.setPartName(getString(parentRow.getCell(2)));
		parent.setJobOrderNo(getString(parentRow.getCell(3)));

		// =====================
		// CHILD DETAILS
		// =====================
		List<DailyPatrolInspectionDetailsResponseDTO> details = new ArrayList<>();

		int rowIndex = 4; // Row5

		while (true) {

			Row row = sheet.getRow(rowIndex);

			if (row == null || getString(row.getCell(0)).isEmpty())
				break;

			DailyPatrolInspectionDetailsResponseDTO d = new DailyPatrolInspectionDetailsResponseDTO();

			d.setCharacteristic(getString(row.getCell(0)));
			d.setMethodOfInspection(getString(row.getCell(1)));
			d.setLsl(getString(row.getCell(2)));
			d.setUsl(getString(row.getCell(3)));

			d.setSample1(getString(row.getCell(4)));
			d.setSample2(getString(row.getCell(5)));
			d.setSample3(getString(row.getCell(6)));
			d.setSample4(getString(row.getCell(7)));
			d.setSample5(getString(row.getCell(8)));
			d.setSample6(getString(row.getCell(9)));
			d.setSample7(getString(row.getCell(10)));
			d.setSample8(getString(row.getCell(11)));
			d.setSample9(getString(row.getCell(12)));
			d.setSample10(getString(row.getCell(13)));

			d.setStatus(getString(row.getCell(14)));
			d.setRemarks(getString(row.getCell(15)));

			details.add(d);

			rowIndex++;
		}

		parent.setDailyPatrolInspectionDetailsResponseDTO(details);

		wb.close();

		return parent;
	}

	private String getString(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == CellType.NUMERIC)
			return String.valueOf(cell.getNumericCellValue());

		return cell.toString().trim();
	}

	@Override
	public List<Map<String, Object>> getInspectionByInchargeName(Long orgId, String branchCode) {
		Set<Object[]> employeeName = dailyPatrolInspectionRepo.getInspectionByInchargeName(orgId, branchCode);
		return getInspectionByInchargeName(employeeName);
	}

	private List<Map<String, Object>> getInspectionByInchargeName(Set<Object[]> employeeName) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : employeeName) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateDailyPatrolInspection(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		DailyPatrolInspectionVO dailyPatrolInspectionVO = dailyPatrolInspectionRepo.findByDocId(docId);

		// BASIC MAPPING

		dailyPatrolInspectionVO = dailyPatrolInspectionRepo.save(dailyPatrolInspectionVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// 2️⃣ Delete old documents from DB
		List<DailyPatrolInspectionAttachmentVO> oldDocs = dailyPatrolInspectionAttachmentRepo
				.findByDailyPatrolInspectionVO(dailyPatrolInspectionVO);
		dailyPatrolInspectionAttachmentRepo.deleteAll(oldDocs);

		if (dailyPatrolInspectionVO.getDocuments() != null) {
			dailyPatrolInspectionVO.getDocuments().clear();
		} else {
			dailyPatrolInspectionVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (DailyPatrolInspectionAttachmentVO doc : oldDocs) {
			deleteFileSafelyNcProduct(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(dailyPatrolInspectionVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);

		return response;
	}

	private void replaceDocuments(DailyPatrolInspectionVO dailyPatrolInspectionVO, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(dailyPatrolInspectionVO, files, docFolder, docId);
	}

	private void saveFiles(DailyPatrolInspectionVO dailyPatrolInspectionVO, MultipartFile[] files, Path docFolder,
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
						.path("/api/dailypatrolinspectioncontroller/view/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				DailyPatrolInspectionAttachmentVO attach = new DailyPatrolInspectionAttachmentVO();
				attach.setDailyPatrolInspectionVO(dailyPatrolInspectionVO);
				attach.setFileName(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileName(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (dailyPatrolInspectionVO.getDocuments() == null) {
					dailyPatrolInspectionVO.setDocuments(new ArrayList<>());
				}

				dailyPatrolInspectionVO.getDocuments().add(attach);
			}

			// Save vehicle once
//				enquiryRepo.save(enquiry);

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
	public ResponseEntity<byte[]> view(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFileNcProduct(request, "/api/dailypatrolinspectioncontroller/view/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileNcProduct(HttpServletRequest request, String apiPrefix,
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
	
	//dailyPatrolInspection
	@Override
	public List<ImageResponseDTO> getAllImages(Long id) throws Exception {

	    DailyPatrolInspectionVO record = dailyPatrolInspectionRepo.getAllDailyPatrolInspectionById(id);

	    if (record == null) {
	        throw new RuntimeException("Record not found");
	    }

	    List<DailyPatrolInspectionAttachmentVO> docs = record.getDocuments();

	    if (docs == null || docs.isEmpty()) {
	        throw new RuntimeException("No attachments found");
	    }

	    List<ImageResponseDTO> responseList = new ArrayList<>();

	    for (DailyPatrolInspectionAttachmentVO attachment : docs) {

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
	
	// Daily Patrol Inspection Image Attachment
	
	@Override
	public List<DailyPatrolImageResponseDTO> getDailyPatrolInsImages(Long id) throws Exception {

		DailyPatrolInspectionVO record = dailyPatrolInspectionRepo
				.getAllDailyPatrolInspectionImagesById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<DailyPatrolInspectionAttachmentVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<DailyPatrolImageResponseDTO> responseList = new ArrayList<>();

		for (DailyPatrolInspectionAttachmentVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			DailyPatrolImageResponseDTO dto = new DailyPatrolImageResponseDTO();
			dto.setFileName(attachment.getFileName());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

}
