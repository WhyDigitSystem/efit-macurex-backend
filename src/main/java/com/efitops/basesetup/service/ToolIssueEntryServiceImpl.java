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

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.ToolIssueEntryDTO;
import com.efitops.basesetup.dto.ToolIssueEntryImageResponseDTO;
import com.efitops.basesetup.dto.ToolRecieveFromCalibrationDTO;
import com.efitops.basesetup.dto.ToolRecieveFromCalibrationDetailsDTO;
import com.efitops.basesetup.dto.ToolsIssueToCalibrationDTO;
import com.efitops.basesetup.dto.ToolsIssueToCalibrationDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EcnApprovalRecordVO;
import com.efitops.basesetup.entity.EcnAttachmentVO;
import com.efitops.basesetup.entity.EnquiryAttachmentVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.RecieveFromCalibrationDetailsImagesVO;
import com.efitops.basesetup.entity.ToolIssueEntryAttachmentVO;
import com.efitops.basesetup.entity.ToolIssueEntryVO;
import com.efitops.basesetup.entity.ToolIssueToCalibrationAttachmentVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationAttachmentVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationDetailsVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationImagesVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationDetailsVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ToolIssueEntryAttachmentRepo;
import com.efitops.basesetup.repo.ToolIssueEntryRepo;
import com.efitops.basesetup.repo.ToolIssueToCalibrationAttachmentRepo;
import com.efitops.basesetup.repo.ToolRecieveFromCalibrationAttachmentRepo;
import com.efitops.basesetup.repo.ToolRecieveFromCalibrationDetailsRepo;
import com.efitops.basesetup.repo.ToolRecieveFromCalibrationImagesRepo;
import com.efitops.basesetup.repo.ToolRecieveFromCalibrationRepo;
import com.efitops.basesetup.repo.ToolsIssueToCalibrationDetailsRepo;
import com.efitops.basesetup.repo.ToolsIssueToCalibrationRepo;

@Service
public class ToolIssueEntryServiceImpl implements ToolIssueEntryService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ToolIssueEntryServiceImpl.class);
	@Autowired
	ToolIssueEntryRepo toolIssueEntryRepo;

	@Autowired
	ToolsIssueToCalibrationRepo toolsIssueToCalibrationRepo;

	@Autowired
	ToolRecieveFromCalibrationRepo toolRecieveFromCalibrationRepo;

	@Autowired
	ToolRecieveFromCalibrationDetailsRepo toolRecieveFromCalibrationDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	ToolsIssueToCalibrationDetailsRepo toolsIssueToCalibrationDetailsRepo;

	@Autowired
	ToolRecieveFromCalibrationImagesRepo toolRecieveFromCalibrationImagesRepo;

	@Autowired
	ToolIssueToCalibrationAttachmentRepo toolIssueToCalibrationAttachmentRepo;
	
	@Autowired
	ToolIssueEntryAttachmentRepo toolIssueEntryAttachmentRepo;

	@Autowired
	ToolRecieveFromCalibrationAttachmentRepo toolRecieveFromCalibrationAttachmentRepo;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public List<ToolIssueEntryVO> getToolIssueEntryByOrgId(Long orgId, String finYear, String branchCode) {
		List<ToolIssueEntryVO> toolIssueEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			toolIssueEntryVO = toolIssueEntryRepo.findToolIssueEntryByOrgId(orgId, finYear, branchCode);
		}
		return toolIssueEntryVO;
	}

	@Override
	public List<ToolIssueEntryVO> getToolIssueEntryById(Long id) {
		List<ToolIssueEntryVO> toolIssueEntryVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			toolIssueEntryVO = toolIssueEntryRepo.getToolIssueEntryById(id);
		}
		return toolIssueEntryVO;
	}

	@Override
	public Map<String, Object> updateCreateToolIssueEntry(ToolIssueEntryDTO toolIssueEntryDTO)
			throws ApplicationException {
		ToolIssueEntryVO toolIssueEntryVO = new ToolIssueEntryVO();
		String screenCode = "TIE";
		String message = null;
		if (ObjectUtils.isNotEmpty(toolIssueEntryDTO.getId())) {
			toolIssueEntryVO = toolIssueEntryRepo.findById(toolIssueEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Gst details"));
			message = "Tool issue Entry Updated Successfully";
		} else {

			String docId = toolIssueEntryRepo.getToolIssueEntryDocId(toolIssueEntryDTO.getOrgId(),
					toolIssueEntryDTO.getFinYear(), toolIssueEntryDTO.getBranchCode(), screenCode);
			toolIssueEntryVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(toolIssueEntryDTO.getOrgId(),
							toolIssueEntryDTO.getFinYear(), toolIssueEntryDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			toolIssueEntryVO.setCreatedBy(toolIssueEntryDTO.getCreatedBy());
			toolIssueEntryVO.setUpdatedBy(toolIssueEntryDTO.getCreatedBy());
			message = "Tool issue Entry Created Successfully";
		}
		createUpdateToolIssueEntryVOByToolIssueEntryDTO(toolIssueEntryDTO, toolIssueEntryVO);
		toolIssueEntryRepo.save(toolIssueEntryVO);
		Map<String, Object> response = new HashMap<>();
		response.put("toolIssueEntryVO", toolIssueEntryVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateToolIssueEntryVOByToolIssueEntryDTO(ToolIssueEntryDTO toolIssueEntryDTO,
			ToolIssueEntryVO toolIssueEntryVO) {
		toolIssueEntryVO.setInstrumentCode(toolIssueEntryDTO.getInstrumentCode().toUpperCase());
		toolIssueEntryVO.setInstrumentName(toolIssueEntryDTO.getInstrumentName());
		toolIssueEntryVO.setInstrumentDesc(toolIssueEntryDTO.getInstrumentDesc());
		toolIssueEntryVO.setSeqCode(toolIssueEntryDTO.getSeqCode());
		toolIssueEntryVO.setInstrumentRange(toolIssueEntryDTO.getInstrumentRange());
		toolIssueEntryVO.setLocation(toolIssueEntryDTO.getLocation());
		toolIssueEntryVO.setOrgId(toolIssueEntryDTO.getOrgId());
		toolIssueEntryVO.setBranch(toolIssueEntryDTO.getBranch());
		toolIssueEntryVO.setBranchCode(toolIssueEntryDTO.getBranchCode());
		toolIssueEntryVO.setFinYear(toolIssueEntryDTO.getFinYear());
		toolIssueEntryVO.setCreatedBy(toolIssueEntryDTO.getCreatedBy());
		// toolIssueEntryVO.setActive(toolIssueEntryDTO.isActive());
		toolIssueEntryVO.setLeastCount(toolIssueEntryDTO.getLeastCount());
		toolIssueEntryVO.setLocation(toolIssueEntryDTO.getLocation());
		toolIssueEntryVO.setFrequencyOfCalib(toolIssueEntryDTO.getFrequencyOfCalib());
		toolIssueEntryVO.setCalibratedDate(toolIssueEntryDTO.getCalibratedDate());
		toolIssueEntryVO.setDueForCalib(toolIssueEntryDTO.getDueForCalib());
		toolIssueEntryVO.setCalibratedCertificateNo(toolIssueEntryDTO.getCalibratedCertificateNo());
		toolIssueEntryVO.setPreparedBy(toolIssueEntryDTO.getPreparedBy());
		toolIssueEntryVO.setApporvedBy(toolIssueEntryDTO.getApporvedBy());
		toolIssueEntryVO.setRemarks(toolIssueEntryDTO.getRemarks());
		toolIssueEntryVO.setCustomerName(toolIssueEntryDTO.getCustomerName());

	}

	@Override
	public String getToolIssueEntryDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "TIE";
		return toolIssueEntryRepo.getToolIssueEntryDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getInstrumentforTollIssueForEntry(Long orgId, String finYear, String branchCode) {
		Set<Object[]> instrument = toolIssueEntryRepo.findInstrumentforTollIssueForEntry(orgId, finYear, branchCode);
		return getInstrumentforTollIssueForEntryForGRN(instrument);
	}

	private List<Map<String, Object>> getInstrumentforTollIssueForEntryForGRN(Set<Object[]> chCode) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("instrumentName", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("instrumentCode", ch[1] != null ? ch[1].toString() : "");
			map.put("ranges", ch[2] != null ? ch[2].toString() : "");
			map.put("leastcount", ch[3] != null ? ch[3].toString() : "");
			map.put("calibrationfrequence", ch[4] != null ? ch[4].toString() : "");

			instrumrntname.add(map);
		}
		return instrumrntname;
	}

	@Override
	public List<Map<String, Object>> getCustomerNameforTollIssueForEntry(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> customerName = toolsIssueToCalibrationRepo.getCustomerNameforTollIssueForEntry(orgId, finYear,
				branchCode);
		return getCustomerNameforTollIssueForEntry(customerName);
	}

	private List<Map<String, Object>> getCustomerNameforTollIssueForEntry(Set<Object[]> chCode) {
		List<Map<String, Object>> customerName = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("customerName", ch[0] != null ? ch[0].toString() : ""); // Empty string if null

			customerName.add(map);
		}
		return customerName;
	}

	@Override
	public List<Map<String, Object>> getlastcountforTollIssueForEntry(Long orgId) {
		Set<Object[]> instrument = toolIssueEntryRepo.getlastcountforTollIssueForEntry(orgId);
		return getlastcountforToll(instrument);
	}

	private List<Map<String, Object>> getlastcountforToll(Set<Object[]> instrument) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : instrument) {
			Map<String, Object> map = new HashMap<>();
			map.put("lastcount", ch[0] != null ? ch[0].toString() : ""); // Empty
			instrumrntname.add(map);
		}
		return instrumrntname;
	}

	@Override
	public List<ToolsIssueToCalibrationVO> getToolsIssueToCalibrationByOrgId(Long orgId, String finYear,
			String branchCode) {
		return toolsIssueToCalibrationRepo.findToolsIssueToCalibrationByOrgId(orgId, finYear, branchCode);

	}

	@Override
	public ToolsIssueToCalibrationVO getToolsIssueToCalibrationById(Long id) {

		return toolsIssueToCalibrationRepo.findToolsIssueToCalibrationById(id);

	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateToolsIssueToCalibration(
			ToolsIssueToCalibrationDTO toolsIssueToCalibrationDTO) throws ApplicationException {

		String message;
		String screenCode = "TIC";
		ToolsIssueToCalibrationVO toolsIssueToCalibrationVO;

		// ================= UPDATE =================
		if (toolsIssueToCalibrationDTO.getId() != null) {

			toolsIssueToCalibrationVO = toolsIssueToCalibrationRepo.findById(toolsIssueToCalibrationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Tools Issue To Calibration not found"));

			toolsIssueToCalibrationVO.setUpdatedBy(toolsIssueToCalibrationDTO.getCreatedBy());

			message = "Updated Successfully";

		}
		// ================= CREATE =================
		else {

			toolsIssueToCalibrationVO = new ToolsIssueToCalibrationVO();

			String docId = toolsIssueToCalibrationRepo.getToolsIssueToCalibrationDocId(
					toolsIssueToCalibrationDTO.getOrgId(), toolsIssueToCalibrationDTO.getFinYear(),
					toolsIssueToCalibrationDTO.getBranchCode(), screenCode);

			toolsIssueToCalibrationVO.setDocId(docId);

			DocumentTypeMappingDetailsVO docVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(toolsIssueToCalibrationDTO.getOrgId(),
							toolsIssueToCalibrationDTO.getFinYear(), toolsIssueToCalibrationDTO.getBranchCode(),
							screenCode);

			if (docVO == null) {
				throw new ApplicationException("Document mapping not found");
			}

			docVO.setLastno(docVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(docVO);

			toolsIssueToCalibrationVO.setCreatedBy(toolsIssueToCalibrationDTO.getCreatedBy());
			toolsIssueToCalibrationVO.setUpdatedBy(toolsIssueToCalibrationDTO.getCreatedBy());

			message = "toolsIssueToCalibration Created Successfully";
		}

		// ===== MAP DTO → VO (PARENT + CHILD) =====
		createUpdateToolsIssueToCalibrationVOByToolsIssueToCalibrationDTO(toolsIssueToCalibrationDTO,
				toolsIssueToCalibrationVO);

		// ===== SAVE AFTER MAPPING =====
		toolsIssueToCalibrationRepo.save(toolsIssueToCalibrationVO);

		Map<String, Object> response = new HashMap<>();
		response.put("toolsIssueToCalibrationVO", toolsIssueToCalibrationVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateToolsIssueToCalibrationVOByToolsIssueToCalibrationDTO(
			@Valid ToolsIssueToCalibrationDTO dto, ToolsIssueToCalibrationVO vo) {

		vo.setIssuePartyName(dto.getIssuePartyName());
		vo.setIssuePartyAddress(dto.getIssuePartyAddress());

		vo.setRemarks(dto.getRemarks());
		vo.setNarration(dto.getNarration());
		vo.setIssueCreatedBy(dto.getIssueCreatedBy());
		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setFinYear(dto.getFinYear());
		vo.setActive(dto.isActive());

		// ===== UPDATE CASE: DELETE OLD CHILDREN =====
		if (dto.getId() != null) {
			List<ToolsIssueToCalibrationDetailsVO> oldDetails = toolsIssueToCalibrationDetailsRepo
					.findByToolsIssueToCalibrationVO(vo);
			toolsIssueToCalibrationDetailsRepo.deleteAll(oldDetails);
		}

		BigDecimal totalQty = BigDecimal.ZERO;

		// ===== ADD NEW CHILDREN =====
		List<ToolsIssueToCalibrationDetailsVO> detailsVOList = new ArrayList<>();

		if (dto.getToolsIssueToCalibrationDetailsDTO() != null) {
			for (ToolsIssueToCalibrationDetailsDTO d : dto.getToolsIssueToCalibrationDetailsDTO()) {

				ToolsIssueToCalibrationDetailsVO detailVO = new ToolsIssueToCalibrationDetailsVO();

				detailVO.setInstrumentId(d.getInstrumentId());
				detailVO.setInstrumentName(d.getInstrumentName());
				detailVO.setInstrumentDesc(d.getInstrumentDesc());
				detailVO.setIssQty(d.getIssQty());
				detailVO.setUnit(d.getUnit());
				totalQty = totalQty.add(detailVO.getIssQty());
				detailVO.setToolsIssueToCalibrationVO(vo);

				detailsVOList.add(detailVO);
			}
		}

		vo.setTotalQty(totalQty);

		vo.setToolsIssueToCalibrationDetailsVO(detailsVOList);
	}

	@Override
	public String getToolsIssueToCalibrationDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "TIC";
		return toolsIssueToCalibrationRepo.getToolsIssueToCalibrationDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getInstrumentdetforToolIssueForcalibration(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> instrument = toolsIssueToCalibrationRepo.findInstrumentdetforToolIssueForcalibration(orgId,
				finYear, branchCode);
		return getInstrumentdetforToolIssueForcalibration(instrument);
	}

	private List<Map<String, Object>> getInstrumentdetforToolIssueForcalibration(Set<Object[]> chCode) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("instrumentcode", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("instrumentname", ch[1] != null ? ch[1].toString() : "");
			map.put("instrumentdesc", ch[2] != null ? ch[2].toString() : "");
			map.put("unit",ch[3] != null? ch[3].toString() : " ");

			instrumrntname.add(map);
		}
		return instrumrntname;
	}

	@Override
	public List<Map<String, Object>> getPartyMasterDetailsforToolIssueForcalibration(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> instrument = toolsIssueToCalibrationRepo.getPartyMasterDetailsforToolIssueForcalibration(orgId,
				finYear, branchCode);
		return getPartyMasterDetailsforToolIssueForcalibration(instrument);
	}

	private List<Map<String, Object>> getPartyMasterDetailsforToolIssueForcalibration(Set<Object[]> chCode) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("partyName", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("partyAddress", ch[1] != null ? ch[1].toString() : "");

			instrumrntname.add(map);
		}
		return instrumrntname;
	}

	// toolsrecievedFromcalibration

	@Override
	public List<ToolRecieveFromCalibrationVO> getToolsRecieveFromCalibrationByOrgId(Long orgId, String finYear,
			String branchCode) {
		return toolRecieveFromCalibrationRepo.findToolsRecieveFromCalibrationByOrgId(orgId, finYear, branchCode);
	}

	@Override
	public ToolRecieveFromCalibrationVO getToolsRecieveFromCalibrationById(Long id) {
		return toolRecieveFromCalibrationRepo.findToolsRecieveFromCalibrationById(id);

	}

	@Override
	@Transactional
	public Map<String, Object> updateCreateToolsRecieveFromCalibration(ToolRecieveFromCalibrationDTO dto)
			throws ApplicationException {

		String message;
		String screenCode = "TRC";
		ToolRecieveFromCalibrationVO vo;

		// ============ UPDATE ============
		if (dto.getId() != null) {

			vo = toolRecieveFromCalibrationRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Tool Receive From Calibration not found"));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Updated Successfully";
		}
		// ============ CREATE ============
		else {

			vo = new ToolRecieveFromCalibrationVO();

			String docId = toolRecieveFromCalibrationRepo.getToolsRecieveFromCalibrationDocId(dto.getOrgId(),
					dto.getFinYear(), dto.getBranchCode(), screenCode);

			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO docVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(dto.getOrgId(), dto.getFinYear(),
							dto.getBranchCode(), screenCode);

			if (docVO == null) {
				throw new ApplicationException("Document mapping not found for TRC");
			}

			docVO.setLastno(docVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(docVO);

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());

			message = "Tool Receive From Calibration Created Successfully";
		}

		// ===== MAP DTO → VO =====
		createUpdateToolsRecieveFromCalibrationVOByDTO(dto, vo);

		// ===== SAVE AFTER MAPPING =====
		toolRecieveFromCalibrationRepo.save(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("toolRecieveFromCalibrationVO", vo);
		response.put("message", message);

		return response;
	}

	private void createUpdateToolsRecieveFromCalibrationVOByDTO(@Valid ToolRecieveFromCalibrationDTO dto,
			ToolRecieveFromCalibrationVO vo) {

		vo.setIssueNo(dto.getIssueNo());
		vo.setIssueDate(dto.getIssueDate());
		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setFinYear(dto.getFinYear());
		vo.setRemarks(dto.getRemarks());
		vo.setNarration(dto.getNarration());
		vo.setRecievedBy(dto.getRecievedBy());
		vo.setRecievedFrom(dto.getRecievedFrom());
		vo.setActive(dto.isActive());

		// ===== UPDATE CASE: DELETE OLD CHILD =====
		if (dto.getId() != null) {
			List<ToolRecieveFromCalibrationDetailsVO> oldDetails = toolRecieveFromCalibrationDetailsRepo
					.findByToolRecieveFromCalibrationVO(vo);
			toolRecieveFromCalibrationDetailsRepo.deleteAll(oldDetails);
		}

		List<ToolRecieveFromCalibrationDetailsVO> detailsList = new ArrayList<>();

		if (dto.getToolRecieveFromCalibrationDetailsDTO() != null) {
			for (ToolRecieveFromCalibrationDetailsDTO d : dto.getToolRecieveFromCalibrationDetailsDTO()) {

				ToolRecieveFromCalibrationDetailsVO detail = new ToolRecieveFromCalibrationDetailsVO();

				detail.setInstrumentId(d.getInstrumentId());
				detail.setInstrumentName(d.getInstrumentName());
				detail.setInstrumentDesc(d.getInstrumentDesc());
				detail.setIssQty(d.getIssQty());
				detail.setCalibratedDate(d.getCalibratedDate());
				detail.setDueDate(d.getDueDate());
				detail.setFrequencyForCalib(d.getFrequencyForCalib());
				detail.setStatus(d.getStatus());
				detail.setRecievdQty(d.getRecievdQty());
				detail.setScorpQty(d.getScorpQty());
				detail.setStatus(d.getStatus());
				detail.setCalibrationCertificate(d.getCalibrationCertificate());
				detail.setToolRecieveFromCalibrationVO(vo);
				detailsList.add(detail);
			}
		}

		vo.setToolRecieveFromCalibrationDetailsVO(detailsList);

//		List<ToolRecieveFromCalibrationImagesVO> toolRecieveFromCalibrationImagesVOs = new ArrayList<>();
//		for (ToolRecieveFromCalibrationImagesDTO sampleApprovalImagesDTO : dto.getToolRecieveFromCalibrationImagesDTO()) {
//			ToolRecieveFromCalibrationImagesVO toolRecieveFromCalibrationImagesVO = new ToolRecieveFromCalibrationImagesVO();
//			toolRecieveFromCalibrationImagesVO.setToolRecieveFromCalibrationVO(vo);
//			toolRecieveFromCalibrationImagesVOs.add(toolRecieveFromCalibrationImagesVO);
//		}
//		vo.setToolRecieveFromCalibrationImagesVO(toolRecieveFromCalibrationImagesVOs);

	}

	@Override
	public String getToolsRecieveFromCalibrationDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "TRC";
		return toolRecieveFromCalibrationRepo.getToolsRecieveFromCalibrationDocId(orgId, finYear, branchCode,
				screenCode);
	}

	@Override
	public List<Map<String, Object>> getIssueDetailsforToolIssueNoForRecieveFormCalibration(Long orgId, String finYear,
			String branchCode) {
		Set<Object[]> instrument = toolRecieveFromCalibrationRepo
				.getIssueDetailsforToolIssueNoForRecieveFormCalibration(orgId, finYear, branchCode);
		return getIssueDetailsforToolIssueNoForRecieveFormCalibration(instrument);
	}

	private List<Map<String, Object>> getIssueDetailsforToolIssueNoForRecieveFormCalibration(Set<Object[]> chCode) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("docdate", ch[1] != null ? ch[1].toString() : "");
			map.put("issuepartyname", ch[2] != null ? ch[2].toString() : "");

			instrumrntname.add(map);
		}
		return instrumrntname;
	}

	@Override
	public List<Map<String, Object>> getInstrumentdetforToolIssueNoForinstrumentRecieveFormCalibration(Long orgId,
			String finYear, String branchCode, String issueNo) {
		Set<Object[]> instrument = toolRecieveFromCalibrationRepo
				.findInstrumentdetforToolIssueNoForRecieveFormCalibration(orgId, finYear, branchCode, issueNo);
		return getInstrumentdetforToolIssueNoForinstrumentRecieveFormCalibration(instrument);
	}

	private List<Map<String, Object>> getInstrumentdetforToolIssueNoForinstrumentRecieveFormCalibration(
			Set<Object[]> chCode) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("instrumentid", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("instrumentname", ch[1] != null ? ch[1].toString() : "");
//			map.put("issuepartyname", ch[2] != null ? ch[2].toString() : "");

			instrumrntname.add(map);
		}
		return instrumrntname;
	}

//	@Override
//	public ToolRecieveFromCalibrationDetailsVO uploadFileForToolReciveFromcalibcertification(MultipartFile file,
//			Long id) throws IOException {
//		ToolRecieveFromCalibrationDetailsVO toolRecieveFromCalibrationDetailsVO = toolRecieveFromCalibrationDetailsRepo
//				.findById(id).get();
//		toolRecieveFromCalibrationDetailsVO.setCalibrationCertificate(file.getBytes());
//		return toolRecieveFromCalibrationDetailsRepo.save(toolRecieveFromCalibrationDetailsVO);
//	}

	@Override
	public List<Map<String, Object>> getToolsIssueEntryInstrumentCodeDesc(Long orgId) {
		Set<Object[]> instrumentCodeDesc = toolIssueEntryRepo.getToolsIssueEntryInstrumentCodeDesc(orgId);
		return getToolsIssueEntryInstrumentCodeDesc(instrumentCodeDesc);
	}

	private List<Map<String, Object>> getToolsIssueEntryInstrumentCodeDesc(Set<Object[]> toolsIssueEntry) {
		List<Map<String, Object>> customerName = new ArrayList<>();
		for (Object[] ch : toolsIssueEntry) {
			Map<String, Object> map = new HashMap<>();
			map.put("instrumentCodeDesc", ch[0] != null ? ch[0].toString() : ""); // Empty string if null

			customerName.add(map);
		}
		return customerName;
	}

	@Transactional
	@Override
	public Map<String, Object> uploadFilesForCalibrationDetails(Long toolRecieveFromCalibrationId, Long detailsId,
			ToolRecieveFromCalibrationDetailsDTO dto) throws IOException {

		if (dto.getFiles() == null || dto.getFiles().length == 0) {
			throw new RuntimeException("No files provided");
		}

		ToolRecieveFromCalibrationVO master = toolRecieveFromCalibrationRepo.findById(toolRecieveFromCalibrationId)
				.orElseThrow(() -> new RuntimeException("Calibration record not found"));

		ToolRecieveFromCalibrationDetailsVO details = toolRecieveFromCalibrationDetailsRepo.findById(detailsId)
				.orElseThrow(() -> new RuntimeException("Calibration details not found"));

		/* 🔹 Delete old files */
		if (details.getRecieveFromCalibrationDetailsImagesVO() != null) {
			for (RecieveFromCalibrationDetailsImagesVO oldImg : new ArrayList<>(
					details.getRecieveFromCalibrationDetailsImagesVO())) {

				if (oldImg.getFilePath() != null) {
					Files.deleteIfExists(Paths.get(oldImg.getFilePath()));
				}
			}
			details.getRecieveFromCalibrationDetailsImagesVO().clear();
		}

		Path baseDir = Paths.get(uploadDir).resolve(toolRecieveFromCalibrationId.toString())
				.resolve(detailsId.toString());

		Files.createDirectories(baseDir);

		List<RecieveFromCalibrationDetailsImagesVO> newImages = new ArrayList<>();

		for (MultipartFile file : dto.getFiles()) {

			if (file == null || file.isEmpty())
				continue;

			String safeFileName = System.currentTimeMillis() + "_"
					+ file.getOriginalFilename().replaceAll("[\\\\/:*?\"<>|]", "_");

			Path filePath = baseDir.resolve(safeFileName);

			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			RecieveFromCalibrationDetailsImagesVO image = RecieveFromCalibrationDetailsImagesVO.builder()
					.fileName(safeFileName).filePath(filePath.toString()).toolRecieveFromCalibrationDetailsVO(details)
					.build();

			newImages.add(image);
		}

		details.getRecieveFromCalibrationDetailsImagesVO().addAll(newImages);
		toolRecieveFromCalibrationDetailsRepo.save(details);

		Map<String, Object> response = new HashMap<>();
		response.put("toolRecieveFromCalibrationId", toolRecieveFromCalibrationId);
		response.put("detailsId", detailsId);
		response.put("uploadedCount", newImages.size());
		response.put("status", "Files uploaded successfully");

		return response;
	}

	@Override
	public List<Map<String, Object>> getToolIssueToCalibrationReport(Long orgId, String fromdate, String todate,
			String issuepartyname) {
		Set<Object[]> instrument = toolsIssueToCalibrationRepo.getToolIssueToCalibrationReport(orgId, fromdate, todate,
				issuepartyname);
		return getToolIssueToCalibrationReport(instrument);
	}

	private List<Map<String, Object>> getToolIssueToCalibrationReport(Set<Object[]> instrument) {
		List<Map<String, Object>> instrumentname = new ArrayList<>();
		for (Object[] ch : instrument) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : "");
			map.put("docdate", ch[1] != null ? ch[1].toString() : "");
			map.put("issuepartyname", ch[2] != null ? ch[2].toString() : "");
			map.put("issuepartyaddress", ch[3] != null ? ch[3].toString() : "");
			map.put("instrumentid", ch[4] != null ? ch[4].toString() : "");
			map.put("instrumentname", ch[5] != null ? ch[5].toString() : "");
			map.put("instrumentdetails", ch[6] != null ? ch[6].toString() : "");

			instrumentname.add(map);

		}
		return instrumentname;

	}

	@Override
	public List<Map<String, Object>> getToolRecieveFromCalibration(Long orgId, String fromdate, String todate) {
		Set<Object[]> instrument = toolRecieveFromCalibrationRepo.getToolRecieveFromCalibration(orgId, fromdate,
				todate);
		return getToolRecieveFromCalibration(instrument);
	}

	private List<Map<String, Object>> getToolRecieveFromCalibration(Set<Object[]> chCode) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgId", ch[0] != null ? ch[0].toString() : "");
			map.put("docId", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			map.put("issueNo", ch[3] != null ? ch[3].toString() : "");
			map.put("isueDate", ch[4] != null ? ch[4].toString() : "");
			map.put("recievedFrom", ch[5] != null ? ch[5].toString() : "");
			map.put("instrumentDetails", ch[6] != null ? ch[6].toString() : "");
			map.put("status", ch[7] != null ? ch[7].toString() : "");

			instrumrntname.add(map);
		}
		return instrumrntname;

	}

	@Override
	public List<Map<String, Object>> getItemNameAndDesc(Long orgId) {
		Set<Object[]> instrument = toolRecieveFromCalibrationRepo.getItemNameAndDesc(orgId);
		return getItemNameAndDesc(instrument);
	}

	private List<Map<String, Object>> getItemNameAndDesc(Set<Object[]> instrument) {
		List<Map<String, Object>> instrumentname = new ArrayList<>();
		for (Object[] ch : instrument) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgid", ch[0] != null ? ch[0].toString() : "");
			map.put("item", ch[1] != null ? ch[1].toString() : "");

			instrumentname.add(map);

		}
		return instrumentname;
	}

	@Override
	public List<Map<String, Object>> getInstrumentCodeAndName(Long orgId) {
		Set<Object[]> instrument = toolsIssueToCalibrationRepo.getInstrumentCodeAndName(orgId);
		return getInstrumentCodeAndName(instrument);
	}

	private List<Map<String, Object>> getInstrumentCodeAndName(Set<Object[]> instrument) {
		List<Map<String, Object>> instrumentname = new ArrayList<>();
		for (Object[] ch : instrument) {
			Map<String, Object> map = new HashMap<>();
			map.put("InstrumentName", ch[0] != null ? ch[0].toString() : "");
			instrumentname.add(map);

		}
		return instrumentname;
	}

	@Override
	public List<Map<String, Object>> getTollIssueForEntryReport(Long orgId, String fromDate, String toDate,
			String instrumentCodeAndName) {
		Set<Object[]> instrument = toolIssueEntryRepo.getTollIssueForEntryReport(orgId, fromDate, toDate,
				instrumentCodeAndName);
		return getTollIssueForEntryReport(instrument);
	}

	private List<Map<String, Object>> getTollIssueForEntryReport(Set<Object[]> instrument) {
		List<Map<String, Object>> instrumrntname = new ArrayList<>();
		for (Object[] ch : instrument) {
			Map<String, Object> map = new HashMap<>();

			map.put("toolissueentryid", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("active", ch[1] != null ? ch[1].toString() : "");
			map.put("toolIssueEntryRepo", ch[2] != null ? ch[2].toString() : "");
			map.put("calibrateddate", ch[3] != null ? ch[3].toString() : "");
			map.put("docdate", ch[4] != null ? ch[4].toString() : "");
			map.put("docid", ch[5] != null ? ch[5].toString() : "");
			map.put("dueforcalib", ch[6] != null ? ch[6].toString() : "");
			map.put("frequencyofcalib", ch[7] != null ? ch[7].toString() : "");
			map.put("instrumentcode", ch[8] != null ? ch[8].toString() : "");
			map.put("instrumentdesc", ch[9] != null ? ch[9].toString() : "");
			map.put("instrumentdetails", ch[10] != null ? ch[10].toString() : "");
			map.put("instrumentname", ch[11] != null ? ch[11].toString() : "");
			map.put("instrumentrange", ch[12] != null ? ch[12].toString() : "");
			map.put("lastcount", ch[13] != null ? ch[13].toString() : "");
			map.put("leastcount", ch[14] != null ? ch[14].toString() : "");
			map.put("location", ch[15] != null ? ch[15].toString() : "");
			map.put("orgid", ch[16] != null ? ch[16].toString() : "");
			map.put("preparedby", ch[17] != null ? ch[17].toString() : "");
			map.put("seqcode", ch[18] != null ? ch[18].toString() : "");
			map.put("branch", ch[19] != null ? ch[19].toString() : "");
			map.put("branchcode", ch[20] != null ? ch[20].toString() : "");
			map.put("finyear", ch[21] != null ? ch[21].toString() : "");

			instrumrntname.add(map);
		}
		return instrumrntname;
	}

	@Transactional
	@Override
	public Map<String, Object> uploadToolRecieveFromCalibrationImages(Long toolRecieveId, List<MultipartFile> files)
			throws IOException {

		ToolRecieveFromCalibrationVO toolRecieve = toolRecieveFromCalibrationRepo.findById(toolRecieveId)
				.orElseThrow(() -> new RuntimeException("ToolRecieveFromCalibration Id not found"));

		if (toolRecieve.getToolRecieveFromCalibrationImagesVO() != null) {

			for (ToolRecieveFromCalibrationImagesVO oldImg : toolRecieve.getToolRecieveFromCalibrationImagesVO()) {

				if (oldImg.getFilePath() != null) {
					Files.deleteIfExists(Paths.get(oldImg.getFilePath()));
				}
			}

			toolRecieve.getToolRecieveFromCalibrationImagesVO().clear();
		}

		Path baseDir = Paths.get(uploadDir).resolve("ToolRecieveFromCalibrationImages")
				.resolve(String.valueOf(toolRecieveId));

		Files.createDirectories(baseDir);

		for (MultipartFile file : files) {

			if (file == null || file.isEmpty()) {
				continue;
			}

			String safeName = System.currentTimeMillis() + "_"
					+ file.getOriginalFilename().replaceAll("[\\\\/:*?\"<>|]", "_");

			Path filePath = baseDir.resolve(safeName);

			file.transferTo(filePath.toFile());

			ToolRecieveFromCalibrationImagesVO newImage = new ToolRecieveFromCalibrationImagesVO();

			newImage.setFileName(safeName);
			newImage.setFilePath(filePath.toString());
			newImage.setToolRecieveFromCalibrationVO(toolRecieve); // set parent

			toolRecieve.getToolRecieveFromCalibrationImagesVO().add(newImage);
		}

		toolRecieveFromCalibrationRepo.save(toolRecieve);

		Map<String, Object> response = new HashMap<>();
		response.put("toolRecieveId", toolRecieveId);
		response.put("uploadedCount", toolRecieve.getToolRecieveFromCalibrationImagesVO().size());
		response.put("status", "Images Uploaded Successfully");

		return response;
	}

	@Override
	public byte[] viewToolRecieveFromCalibrationImage(Long imageId) throws IOException {

		ToolRecieveFromCalibrationImagesVO image = toolRecieveFromCalibrationImagesRepo.findById(imageId)
				.orElseThrow(() -> new RuntimeException("Image not found"));

		if (image.getFilePath() == null) {
			throw new RuntimeException("File path not available");
		}

		Path path = Paths.get(image.getFilePath());

		if (!Files.exists(path)) {
			throw new IOException("File not found on server");
		}

		return Files.readAllBytes(path);
	}

	@Override
	public String getImageFileType(Long id) throws IOException {

		ToolRecieveFromCalibrationImagesVO image = toolRecieveFromCalibrationImagesRepo.findById(id).orElse(null);

		if (image == null || image.getFilePath() == null) {
			return null;
		}

		Path path = Paths.get(image.getFilePath());

		if (!Files.exists(path)) {
			return null;
		}

		return Files.probeContentType(path);
	}

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateToolsIssueToCalibration(MultipartFile[] files, String docId,
			String screenName, String module) throws ApplicationException, java.io.IOException {

		ToolsIssueToCalibrationVO toolsIssueToCalibrationVO = toolsIssueToCalibrationRepo.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		toolsIssueToCalibrationVO = toolsIssueToCalibrationRepo.save(toolsIssueToCalibrationVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectoryToolIssueToCalibration(docFolder);

		// 2️⃣ Delete old documents from DB
		List<ToolIssueToCalibrationAttachmentVO> oldDocs = toolIssueToCalibrationAttachmentRepo
				.findByToolsIssueToCalibrationVO(toolsIssueToCalibrationVO);
		toolIssueToCalibrationAttachmentRepo.deleteAll(oldDocs);

		if (toolsIssueToCalibrationVO.getDocuments() != null) {
			toolsIssueToCalibrationVO.getDocuments().clear();
		} else {
			toolsIssueToCalibrationVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (ToolIssueToCalibrationAttachmentVO doc : oldDocs) {
			deleteFileSafelyToolIssueToCalibration(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocumentsToolIssueToCalibration(toolsIssueToCalibrationVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("toolsIssueToCalibrationVO", toolsIssueToCalibrationVO);

		return response;
	}

	private void replaceDocumentsToolIssueToCalibration(ToolsIssueToCalibrationVO toolsIssueToCalibration, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFilesToolIssueToCalibration(toolsIssueToCalibration, files, docFolder, docId);
	}

	private void saveFilesToolIssueToCalibration(ToolsIssueToCalibrationVO toolsIssueToCalibration, MultipartFile[] files, Path docFolder,
			String docId) throws java.io.IOException {

		try {
			createDirectoryToolIssueToCalibration(docFolder);

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

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/toolmanagement/ViewToolIssueToCalibration/")
						.toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				ToolIssueToCalibrationAttachmentVO attach = new ToolIssueToCalibrationAttachmentVO();
				attach.setToolsIssueToCalibrationVO(toolsIssueToCalibration);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFileSize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (toolsIssueToCalibration.getDocuments() == null) {
					toolsIssueToCalibration.setDocuments(new ArrayList<>());
				}

				toolsIssueToCalibration.getDocuments().add(attach);
			}

//Save vehicle once
//			enquiryRepo.save(enquiry);

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafelyToolIssueToCalibration(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryToolIssueToCalibration(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> ViewToolIssueToCalibration(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFileToolIssueToCalibration(request, "/api/toolmanagement/ViewToolIssueToCalibration/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileToolIssueToCalibration(HttpServletRequest request, String apiPrefix, String uploadBasePath)
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
	
	
	//By Ruchitha 
	//ToolIssue Entry Attachment
	@Value("${file.upload.path}")
	private String uploadBasePath1;

	@Override
	@Transactional
	public Map<String, Object> createUpdateToolIssueEntry(MultipartFile[] files, String docId, String screenName,
			String module) throws ApplicationException, java.io.IOException {

		ToolIssueEntryVO toolIssueEntryVO = toolIssueEntryRepo.findByDocId(docId);

		String message = "Tool issue entry updated successfully";

		// BASIC MAPPING

		toolIssueEntryVO = toolIssueEntryRepo.save(toolIssueEntryVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectoryToolIssueEntry(docFolder);

		// 2️⃣ Delete old documents from DB
		List<ToolIssueEntryAttachmentVO> oldDocs = toolIssueEntryAttachmentRepo.findByToolIssueEntryVO(toolIssueEntryVO);
		 toolIssueEntryAttachmentRepo.deleteAll(oldDocs);

		if (toolIssueEntryVO.getDocuments() != null) {
			toolIssueEntryVO.getDocuments().clear();
		} else {
			toolIssueEntryVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (ToolIssueEntryAttachmentVO doc : oldDocs) {
			deleteFileSafelyToolIssueEntry(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocumentsToolIssueEntry(toolIssueEntryVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("toolIssueEntryVO", toolIssueEntryVO);

		return response;
	}

	private void replaceDocumentsToolIssueEntry(ToolIssueEntryVO enquiry, MultipartFile[] files, Path docFolder, String docId)
			throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(enquiry, files, docFolder, docId);
	}

	private void saveFiles(ToolIssueEntryVO toolIssueEntry, MultipartFile[] files, Path docFolder, String docId)
	        throws java.io.IOException {

	    try {
	    	createDirectoryToolIssueEntry(docFolder);

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
	                    .path("/api/toolmanagement/ViewToolsIssueEntry/").toUriString();

	            // convert physical path → relative path
	            String relativePath = uploadBasePath.replace("\\", "/");

	            relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

	            // Save DB entry
	            ToolIssueEntryAttachmentVO attach = new ToolIssueEntryAttachmentVO();
	            attach.setToolIssueEntryVO(toolIssueEntry);
	            attach.setFilename(fileName);
	            attach.setFilePath(baseUrl + relativePath);
	            attach.setFileype(file.getContentType());
	            attach.setFilesize(file.getSize());
	            attach.setUploadOn(LocalDateTime.now());

	            if (toolIssueEntry.getDocuments() == null) {
	                toolIssueEntry.setDocuments(new ArrayList<>());
	            }
	            toolIssueEntry.getDocuments().add(attach);
	        }

	    } catch (IOException e) {
	        throw new RuntimeException("File upload failed", e);
	    }
	}

	private void deleteFileSafelyToolIssueEntry(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryToolIssueEntry(Path path) throws IOException {
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
	public ResponseEntity<byte[]> ViewToolsIssueEntry(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveToolsIssueEntry(request, "/api/toolmanagement/ViewToolsIssueEntry/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveToolsIssueEntry(HttpServletRequest request, String apiPrefix, String uploadBasePath)
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
	@Transactional
	public Map<String, Object> createUpdateToolRecieveFromCalibration(MultipartFile[] files, String docId,
			String screenName, String module) throws ApplicationException, java.io.IOException {

		ToolRecieveFromCalibrationVO toolRecieveFromCalibrationVO = toolRecieveFromCalibrationRepo.findByDocId(docId);

		String message = "In process Inspection updated successfully";

		// BASIC MAPPING

		toolRecieveFromCalibrationVO = toolRecieveFromCalibrationRepo.save(toolRecieveFromCalibrationVO);

		// 1️⃣ Create folder: /uploads/module/screenName/docId
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectoryTools(docFolder);

		// 2️⃣ Delete old documents from DB
		List<ToolRecieveFromCalibrationAttachmentVO> oldDocs = toolRecieveFromCalibrationAttachmentRepo
				.findByToolRecieveFromCalibrationVO(toolRecieveFromCalibrationVO);
		toolRecieveFromCalibrationAttachmentRepo.deleteAll(oldDocs);

		if (toolRecieveFromCalibrationVO.getDocuments() != null) {
			toolRecieveFromCalibrationVO.getDocuments().clear();
		} else {
			toolRecieveFromCalibrationVO.setDocuments(new ArrayList<>());
		}

		// 3️⃣ Delete physical files
		for (ToolRecieveFromCalibrationAttachmentVO doc : oldDocs) {
			deleteFileTools(doc.getFilePath());
		}

		// 4️⃣ Save new files
		replaceDocuments(toolRecieveFromCalibrationVO, files, docFolder, docId);
		// RESPONSE
		// ResponseDTO responseDTO = mapToResponseDTO(enquiryVO);

		Map<String, Object> response = new HashMap<>();
		response.put("toolRecieveFromCalibrationVO", toolRecieveFromCalibrationVO);

		return response;
	}

	private void replaceDocuments(ToolRecieveFromCalibrationVO toolRecieveFromCalibrationVO, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		if (files == null || files.length == 0)
			return;

		saveFiles(toolRecieveFromCalibrationVO, files, docFolder, docId);
	}

	private void saveFiles(ToolRecieveFromCalibrationVO toolRecieveFromCalibrationVO, MultipartFile[] files,
			Path docFolder, String docId) throws java.io.IOException {

		try {
			createDirectoryTools(docFolder);

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
						.path("/api/toolmanagement/viewFileTools/").toUriString();

				// convert physical path → relative path
				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
				// Save DB entry
				ToolRecieveFromCalibrationAttachmentVO attach = new ToolRecieveFromCalibrationAttachmentVO();
				attach.setToolRecieveFromCalibrationVO(toolRecieveFromCalibrationVO);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileType(file.getContentType());
				attach.setFilesize(file.getSize());
				attach.setUploadOn(LocalDateTime.now());

				if (toolRecieveFromCalibrationVO.getDocuments() == null) {
					toolRecieveFromCalibrationVO.setDocuments(new ArrayList<>());
				}

				toolRecieveFromCalibrationVO.getDocuments().add(attach);
			}

//Save vehicle once
//			enquiryRepo.save(enquiry);

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileTools(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectoryTools(Path path) throws IOException {
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFileTools(HttpServletRequest request) throws IOException, java.io.IOException {
		return serveFileTools(request, "/api/toolmanagement/viewFileTools/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileTools(HttpServletRequest request, String apiPrefix, String uploadBasePath)
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
		public List<ToolIssueEntryImageResponseDTO> getToolIssueEntryImages(Long id) throws Exception {

			ToolIssueEntryVO record = toolIssueEntryRepo
					.getAlltoolIssueEntryById(id);

			if (record == null) {
				throw new RuntimeException("Record not found");
			}

			List<ToolIssueEntryAttachmentVO> docs = record.getDocuments();

			if (docs == null || docs.isEmpty()) {
				throw new RuntimeException("No attachments found");
			}
			List<ToolIssueEntryImageResponseDTO> responseList = new ArrayList<>();

			for (ToolIssueEntryAttachmentVO attachment : docs) {

				String fileUrl = attachment.getFilePath().replace(" ", "%20");

				InputStream inputStream = new URL(fileUrl).openStream();

				byte[] bytes = inputStream.readAllBytes();

				String base64 = Base64.getEncoder().encodeToString(bytes);

				ToolIssueEntryImageResponseDTO dto = new ToolIssueEntryImageResponseDTO();
				dto.setFileName(attachment.getFilename());
				dto.setProfileImage(base64); // only base64 (like you asked)

				responseList.add(dto);
			}

			return responseList;
		}

//		@Override
//		public List<ToolIssueEntryImageResponseDTO> getRouteCardEntryImages(Long id) {
//			// TODO Auto-generated method stub
//			return null;
//		}

	
	@Override
	public List<ImageResponseDTO> ToolsIssueToCalibrationImage(Long id) throws Exception {

	    ToolsIssueToCalibrationVO record = toolsIssueToCalibrationRepo.getAllToolsIssueToCalibrationById(id);

	    if (record == null) {
	        throw new RuntimeException("Record not found");
	    }

	    List<ToolIssueToCalibrationAttachmentVO> docs = record.getDocuments();

	    if (docs == null || docs.isEmpty()) {
	        throw new RuntimeException("No attachments found");
	    }

	    List<ImageResponseDTO> responseList = new ArrayList<>();

	    for (ToolIssueToCalibrationAttachmentVO attachment : docs) {

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
