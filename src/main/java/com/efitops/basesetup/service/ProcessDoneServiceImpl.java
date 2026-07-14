package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ProcessDoneDTO;
import com.efitops.basesetup.dto.ProcessDoneDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ProcessDoneDetailsVO;
import com.efitops.basesetup.entity.ProcessDoneVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.ProcessDoneDetailsRepo;
import com.efitops.basesetup.repo.ProcessDoneRepo;

@Service
public class ProcessDoneServiceImpl implements ProcessDoneService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProcessDoneServiceImpl.class);

	@Autowired
	ProcessDoneRepo processDoneRepo;

	@Autowired
	ProcessDoneDetailsRepo processDoneDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public List<ProcessDoneVO> getAllProcessDoneByOrgId(Long orgId) {
		List<ProcessDoneVO> processDoneVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ProcessDone BY OrgId : {}", orgId);
			processDoneVO = processDoneRepo.getAllProcessDoneByOrgId(orgId);
		}
		return processDoneVO;
	}

	@Override
	public List<ProcessDoneVO> getProcessDoneById(Long id) {
		List<ProcessDoneVO> processDoneVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ProcessDone BY Id : {}", id);
			processDoneVO = processDoneRepo.getAllProcessDoneById(id);
		}
		return processDoneVO;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateProcessDone(ProcessDoneDTO processDoneDTO) throws ApplicationException {

		String screenCode = "PD";
		ProcessDoneVO oldProcessDone= null;
		
		ProcessDoneVO processDoneVO;
		String message;

		if (ObjectUtils.isNotEmpty(processDoneDTO.getId())) {
			oldProcessDone = processDoneRepo.findById(processDoneDTO.getId())
					.orElseThrow(() -> new ApplicationException("ProcessDone not found"));

			oldProcessDone.getProcessDoneDetailsVO().size(); // load

			entityManager.detach(oldProcessDone); // detach snapshot

			processDoneVO = processDoneRepo.findById(processDoneDTO.getId())
					.orElseThrow(() -> new ApplicationException("Process Done not found"));

			processDoneVO.setModifiedBy(processDoneDTO.getCreatedBy());
			message = "Process Done Updated Successfully";

		} else {

			processDoneVO = new ProcessDoneVO();

			String docId = processDoneRepo.getProcessDoneDocId(processDoneDTO.getOrgId(), screenCode);
			processDoneVO.setDocId(docId);

			DocumentTypeMappingDetailsVO docMap = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(processDoneDTO.getOrgId(),
							processDoneDTO.getFinYear(), processDoneDTO.getBranchCode(), screenCode);

			docMap.setLastno(docMap.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(docMap);

			processDoneVO.setCreatedBy(processDoneDTO.getCreatedBy());
			processDoneVO.setModifiedBy(processDoneDTO.getCreatedBy());

			message = "Process Done Created Successfully";
		}

		createUpdateProcessDoneVOByProcessDoneDTO(processDoneDTO, processDoneVO);
		processDoneRepo.save(processDoneVO);
		commonNotificationService.generateNotification(processDoneVO.getScreenCode(), processDoneVO.getId(), oldProcessDone,
				processDoneVO);

		Map<String, Object> response = new HashMap<>();
		response.put("processDoneVO", processDoneVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateProcessDoneVOByProcessDoneDTO(ProcessDoneDTO processDoneDTO, ProcessDoneVO processDoneVO) {

		// Map fields from DTO to VO
		processDoneVO.setOrgId(processDoneDTO.getOrgId());
		processDoneVO.setBranch(processDoneDTO.getBranch());
		processDoneVO.setBranchCode(processDoneDTO.getBranchCode());
		processDoneVO.setFinYear(processDoneDTO.getFinYear());
		processDoneVO.setActive(processDoneDTO.isActive());
		processDoneVO.setNarration(processDoneDTO.getNarration());
		processDoneVO.setDocId(processDoneDTO.getDocId());
		processDoneVO.setDocDate(processDoneDTO.getDocDate());
		processDoneVO.setCustomerName(processDoneDTO.getCustomerName());
		processDoneVO.setRouteCardNo(processDoneDTO.getRouteCardNo());
		processDoneVO.setJobOrderNo(processDoneDTO.getJobOrderNo());
		processDoneVO.setFgPartName(processDoneDTO.getFgPartName());
		processDoneVO.setFgPartNo(processDoneDTO.getFgPartNo());
		processDoneVO.setFrom(processDoneDTO.getFrom());
		processDoneVO.setTo(processDoneDTO.getTo());
		processDoneVO.setPlacingLocation(processDoneDTO.getPlacingLocation());
		processDoneVO.setQty(processDoneDTO.getQty());

		if (ObjectUtils.isNotEmpty(processDoneVO.getId())) {
			List<ProcessDoneDetailsVO> processDoneDetailsVO1 = processDoneDetailsRepo
					.findByProcessDoneVO(processDoneVO);
			processDoneDetailsRepo.deleteAll(processDoneDetailsVO1);
		}

		List<ProcessDoneDetailsVO> processDoneDetailsVOs = new ArrayList<>();
		for (ProcessDoneDetailsDTO processDoneDetailsDTO : processDoneDTO.getProcessDoneDetailsDTO()) {

			ProcessDoneDetailsVO processDoneDetailsVO = new ProcessDoneDetailsVO();
			processDoneDetailsVO.setProcess(processDoneDetailsDTO.getProcess());
			processDoneDetailsVO.setStatus(processDoneDetailsDTO.getStatus());
			processDoneDetailsVO.setRemarks(processDoneDetailsDTO.getRemarks());
			processDoneDetailsVO.setProcessDoneVO(processDoneVO);
			processDoneDetailsVOs.add(processDoneDetailsVO);
		}
		processDoneVO.setProcessDoneDetailsVO(processDoneDetailsVOs);
	}

	@Override
	public String getProcessDoneDocId(Long orgId) {
		String screenCode = "PD";
		return processDoneRepo.getProcessDoneDocId(orgId, screenCode);
	}

	@Override
	public List<Map<String, Object>> getRouteCardNo(Long orgId, String customerName) {
		Set<Object[]> routeNo = processDoneRepo.getRouteCardNo(orgId, customerName);
		return getRouteDetails(routeNo);
	}

	private List<Map<String, Object>> getRouteDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("fgPartName", ch[1] != null ? ch[1].toString() : "");
			map.put("fgPartDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getJobCardNo(Long orgId, String routeCardNo) {
		Set<String> routeNo = processDoneRepo.getJobCardNo(orgId, routeCardNo);
		return getJobDetails(routeNo);
	}

	private List<Map<String, Object>> getJobDetails(Set<String> jobCards) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (String jobCardNo : jobCards) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobCardNo", jobCardNo != null ? jobCardNo : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getFrom(Long orgId) {
		Set<Object[]> routeNo = processDoneRepo.getFrom(orgId);
		return getFromDetails(routeNo);
	}

	private List<Map<String, Object>> getFromDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("from", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getTo(Long orgId) {
		Set<Object[]> routeNo = processDoneRepo.getTo(orgId);
		return getToDetails(routeNo);
	}

	private List<Map<String, Object>> getToDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("to", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getProcessFromItemMaster(Long orgId, String fgPartNo) {
		Set<Object[]> routeNo = processDoneRepo.getProcessFromItemMaster(orgId, fgPartNo);
		return getProcessFromItemMaster(routeNo);
	}

	private List<Map<String, Object>> getProcessFromItemMaster(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("process", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getProcessDoneReport(Long orgId, String fromDate, String toDate,
			String routeCardEntry) {
		Set<Object[]> reportData = processDoneRepo.getProcessDoneReport(orgId, fromDate, toDate, routeCardEntry);
		return mapProcessDoneReport(reportData);
	}

	private List<Map<String, Object>> mapProcessDoneReport(Set<Object[]> reportData) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {
			Map<String, Object> map = new HashMap<>();
			map.put("processDoneId", ch[0] != null ? ch[0].toString() : "");
			map.put("docId", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("routeCardNo", ch[4] != null ? ch[4].toString() : "");
			map.put("jobOrderNo", ch[5] != null ? ch[5].toString() : "");
			map.put("fgPartName", ch[6] != null ? ch[6].toString() : "");
			map.put("fgPartNo", ch[7] != null ? ch[7].toString() : "");
			map.put("fromProcess", ch[8] != null ? ch[8].toString() : "");
			map.put("toProcess", ch[9] != null ? ch[9].toString() : "");
			map.put("placingLocation", ch[10] != null ? ch[10].toString() : "");
			map.put("qty", ch[11] != null ? ch[11].toString() : "");
			map.put("process", ch[12] != null ? ch[12].toString() : "");
			map.put("status", ch[13] != null ? ch[13].toString() : "");
			map.put("remarks", ch[14] != null ? ch[14].toString() : "");
			list.add(map);
		}

		return list;
	}
}
