package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.JobOrderDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.JobOrderDetailsVO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.JobOrderDetailsRepo;
import com.efitops.basesetup.repo.JobOrderRepo;

@Service
public class JobOrderServiceImpl implements JobOrderService {

	public static final Logger LOGGER = LoggerFactory.getLogger(JobOrderServiceImpl.class);

	@Autowired
	JobOrderRepo jobOrderRepo;

	@Autowired
	JobOrderDetailsRepo jobOrderDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<JobOrderVO> getAllJobOrderByOrgId(Long orgId) {
		List<JobOrderVO> jobOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  JobOrder BY OrgId : {}", orgId);
			jobOrderVO = jobOrderRepo.getAllJobOrderByOrgId(orgId);
		}
		return jobOrderVO;
	}

	@Override
	public List<JobOrderVO> getJobOrderById(Long id) {
		List<JobOrderVO> jobOrderVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received JobOrder BY Id : {}", id);
			jobOrderVO = jobOrderRepo.getAllJobOrderById(id);
		}
		return jobOrderVO;
	}

	@Override
	public Map<String, Object> createUpdateJobOrder(JobOrderDTO jobOrderDTO) throws ApplicationException {
		String screenCode = "JO";
		JobOrderVO oldJobOrder = null;
		
		
		JobOrderVO jobOrderVO = new JobOrderVO();
		String message;
		if (ObjectUtils.isNotEmpty(jobOrderDTO.getId())) {
			oldJobOrder = jobOrderRepo.findById(jobOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("JobOrder not found"));

			oldJobOrder.getJobOrderDetailsVO().size(); // load

			entityManager.detach(oldJobOrder); // detach snapshot

			jobOrderVO = jobOrderRepo.findById(jobOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("Production Plan not found"));

			jobOrderVO.setModifiedBy(jobOrderDTO.getCreatedBy());
			createUpdateJobOrderVOByJobOrderDTO(jobOrderDTO, jobOrderVO);
			message = "JobOrder Updated Successfully";
		} else {
			// GETDOCID API
			String docId = jobOrderRepo.getJobOrderDocId(jobOrderDTO.getOrgId(), screenCode);
			jobOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(jobOrderDTO.getOrgId(), jobOrderDTO.getFinYear(),
							jobOrderDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			jobOrderVO.setCreatedBy(jobOrderDTO.getCreatedBy());
			jobOrderVO.setModifiedBy(jobOrderDTO.getCreatedBy());
			createUpdateJobOrderVOByJobOrderDTO(jobOrderDTO, jobOrderVO);
			message = "Tax Invoice Created Successfully";
		}

		jobOrderRepo.save(jobOrderVO);
		commonNotificationService.generateNotification(jobOrderVO.getScreenCode(), jobOrderVO.getId(), oldJobOrder,
				jobOrderVO);
		
		Map<String, Object> response = new HashMap<>();
		response.put("jobOrderVO", jobOrderVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateJobOrderVOByJobOrderDTO(JobOrderDTO jobOrderDTO, JobOrderVO jobOrderVO) {

		// Map fields from DTO to VO
		jobOrderVO.setOrgId(jobOrderDTO.getOrgId());
		jobOrderVO.setBranch(jobOrderDTO.getBranch());
		jobOrderVO.setBranchCode(jobOrderDTO.getBranchCode());
		jobOrderVO.setFinYear(jobOrderDTO.getFinYear());
		jobOrderVO.setActive(jobOrderDTO.isActive());
		jobOrderVO.setShift(jobOrderDTO.getShift());
		jobOrderVO.setRouteCardNo(jobOrderDTO.getRouteCardNo());
		jobOrderVO.setWorkOrderNo(jobOrderDTO.getWorkOrderNo());
		jobOrderVO.setCustomerName(jobOrderDTO.getCustomerName());
		jobOrderVO.setSupplier(jobOrderDTO.getSupplier());
		jobOrderVO.setCustomerPoNo(jobOrderDTO.getCustomerPoNo());
		jobOrderVO.setPartName(jobOrderDTO.getPartName());
		jobOrderVO.setProductionQty(jobOrderDTO.getProductionQty());
		jobOrderVO.setPartNo(jobOrderDTO.getPartNo());
		jobOrderVO.setOperationName(jobOrderDTO.getOperationName());
		jobOrderVO.setCycleTimeInSecs(jobOrderDTO.getCycleTimeInSecs());
		jobOrderVO.setNormsHr(jobOrderDTO.getNormsHr());
		jobOrderVO.setStatus(jobOrderDTO.getStatus());
		jobOrderVO.setNarration(jobOrderDTO.getNarration());

		jobOrderVO.setOperatorName(jobOrderDTO.getOperatorName());

		if (ObjectUtils.isNotEmpty(jobOrderVO.getId())) {
			List<JobOrderDetailsVO> jobOrderDetailsVO1 = jobOrderDetailsRepo.findByJobOrderVO(jobOrderVO);
			jobOrderDetailsRepo.deleteAll(jobOrderDetailsVO1);
		}
		int sum = 0;
		List<JobOrderDetailsVO> jobOrderDetailsVOs = new ArrayList<>();
		for (JobOrderDetailsDTO jobOrderDetailsDTO : jobOrderDTO.getJobOrderDetailsDTO()) {

			JobOrderDetailsVO jobOrderDetailsVO = new JobOrderDetailsVO();
			jobOrderDetailsVO.setTimeInHours(jobOrderDetailsDTO.getTimeInHours());
			jobOrderDetailsVO.setUnit(jobOrderDetailsDTO.getUnit());
			int hoursProduction = jobOrderDetailsDTO.getHoursProduction();
			jobOrderDetailsVO.setHoursProduction(hoursProduction);
			int rework = jobOrderDetailsDTO.getRework();
			jobOrderDetailsVO.setRework(rework);
			int reject = jobOrderDetailsDTO.getReject();
			jobOrderDetailsVO.setReject(reject);
			jobOrderDetailsVO.setIdealTime(jobOrderDetailsDTO.getIdealTime());
			int cumulativetest = hoursProduction - (rework + reject);
			int sum1 = cumulativetest;
			sum += sum1;
			jobOrderDetailsVO.setCumulativeTest(sum);
			jobOrderDetailsVO.setRemarks(jobOrderDetailsDTO.getRemarks());
			jobOrderDetailsVO.setJobOrderVO(jobOrderVO);
			jobOrderDetailsVOs.add(jobOrderDetailsVO);

		}
		jobOrderVO.setJobOrderDetailsVO(jobOrderDetailsVOs);
	}

	@Override
	public String getJobOrderDocId(Long orgId) {
		String screenCode = "JO";
		return jobOrderRepo.getJobOrderDocId(orgId, screenCode);
	}

	@Override
	public List<Map<String, Object>> getShift(Long orgId) {
		Set<Object[]> routeNo = jobOrderRepo.getShift(orgId);
		return getShiftDetails(routeNo);
	}

	private List<Map<String, Object>> getShiftDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("shift", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getOperationName(Long orgId, String routeCardNo) {
		Set<Object[]> routeNo = jobOrderRepo.getOperationName(orgId, routeCardNo);
		return getOperationDetails(routeNo);
	}

	private List<Map<String, Object>> getOperationDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("operationName", ch[0] != null ? ch[0].toString() : "");
			map.put("timeTakenInSec", ch[1] != null ? ch[1].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getOperatorName(Long orgId) {
		Set<Object[]> routeNo = jobOrderRepo.getOperatorName(orgId);
		return getOperatorDetails(routeNo);
	}

	private List<Map<String, Object>> getOperatorDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("operatorName", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getTimings(Long orgId, String shiftCode) {
		Set<Object[]> routeNo = jobOrderRepo.getTimings(orgId, shiftCode);
		return getTimingDetails(routeNo);
	}

	private List<Map<String, Object>> getTimingDetails(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("timings", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getUnitforJobOrder(Long orgId, String partNo) {
		Set<Object[]> unit = jobOrderRepo.getUnitforJobOrder(orgId, partNo);
		return getUnitforJobOrder(unit);
	}

	private List<Map<String, Object>> getUnitforJobOrder(Set<Object[]> unit) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : unit) {
			Map<String, Object> map = new HashMap<>();
			map.put("unit", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRouteCardNoAndDetailsforJobOrder(Long orgId) {
		Set<Object[]> routeCard = jobOrderRepo.getRouteCardNoAndDetailsforJobOrder(orgId);
		return getRouteCardNo(routeCard);
	}

	private List<Map<String, Object>> getRouteCardNo(Set<Object[]> route) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : route) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardNo", ch[0] != null ? ch[0].toString() : "");
			map.put("woSoNo", ch[1] != null ? ch[1].toString() : "");
			map.put("woSoDate", ch[2] != null ? ch[2].toString() : "");
			map.put("customerName", ch[3] != null ? ch[3].toString() : "");
			map.put("customerPoNumber", ch[4] != null ? ch[4].toString() : "");
			map.put("productionQty", ch[5] != null ? ch[5].toString() : "");
			map.put("partName", ch[6] != null ? ch[6].toString() : "");
			map.put("partDesc", ch[7] != null ? ch[7].toString() : "");

			List1.add(map);
		}
		return List1;
	}

// Report APIs

	// Job Order Report

	@Override
	public List<Map<String, Object>> getJobOrderReport(Long orgId, String fromDate, String toDate, String status,String routeCardNo) {

		Set<Object[]> reportData = jobOrderRepo.getJobOrderReport(orgId, fromDate, toDate, status,routeCardNo);

		return mapJobOrderReport(reportData);
	}

	private List<Map<String, Object>> mapJobOrderReport(Set<Object[]> reportData) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {

			Map<String, Object> map = new HashMap<>();

			map.put("jobOrderId", ch[0]);
			map.put("docId", ch[1]);
			map.put("docDate", ch[2]);
			map.put("customerName", ch[3]);
			map.put("customerPoNo", ch[4]);
			map.put("workOrderNo", ch[5]);
			map.put("routeCardNo", ch[6]);
			map.put("partNo", ch[7]);
			map.put("partName", ch[8]);
			map.put("shift", ch[9]);
			map.put("operationName", ch[10]);
			map.put("operatorName", ch[11]);
			map.put("cycleTimeInSecs", ch[12]);
			map.put("normsHr", ch[13]);
			map.put("productionQty", ch[14]);
			map.put("status", ch[15]);

			map.put("timeInHours", ch[16]);
			map.put("unit", ch[17]);
			map.put("hourProduction", ch[18]);
			map.put("rework", ch[19]);
			map.put("reject", ch[20]);
			map.put("idleTime", ch[21]);
			map.put("cumulativeTotal", ch[22]);
			map.put("remarks", ch[23]);

			list.add(map);
		}

		return list;
	}

}
