package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.JobOrderVO;
import com.efitops.basesetup.service.JobOrderService;

@RestController
@RequestMapping("/api/jobOrder")
public class JobOrderController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(JobOrderController.class);

	@Autowired
	JobOrderService jobOrderService;

	@GetMapping("/getAllJobOrderByOrgId")
	public ResponseEntity<ResponseDTO> getAllJobOrderByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllJobOrderByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobOrderVO> jobOrderVO = new ArrayList<>();
		try {
			jobOrderVO = jobOrderService.getAllJobOrderByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobOrder information get successfully By OrgId");
			responseObjectsMap.put("jobOrderVO", jobOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "JobOrder information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getJobOrderById")
	public ResponseEntity<ResponseDTO> getJobOrderById(@RequestParam(required = false) Long id) {
		String methodName = "getJobOrderById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobOrderVO> jobOrderVO = new ArrayList<>();
		try {
			jobOrderVO = jobOrderService.getJobOrderById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobOrder information get successfully By id");
			responseObjectsMap.put("jobOrderVO", jobOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "JobOrder information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateJobOrder")
	public ResponseEntity<ResponseDTO> updateCreateJobOrder(@RequestBody JobOrderDTO jobOrderDTO) {
		String methodName = "updateCreateJobOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> jobOrderVO = jobOrderService.createUpdateJobOrder(jobOrderDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, jobOrderVO.get("message"));
			responseObjectsMap.put("jobOrderVO", jobOrderVO.get("jobOrderVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobOrderByDocId")
	public ResponseEntity<ResponseDTO> getJobOrderByDocId(@RequestParam Long orgId) {
		String methodName = "getJobOrderByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String jobOrderVO = null;
		try {
			jobOrderVO = jobOrderService.getJobOrderDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OriginBill information get successfully By docid");
			responseObjectsMap.put("jobOrderVO", jobOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"OriginBill information receive failed By docid", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getShift")
	public ResponseEntity<ResponseDTO> getShift(@RequestParam Long orgId) {
		String methodName = "getShift()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> shift = new ArrayList<>();
		try {
			shift = jobOrderService.getShift(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Shift Details information get successfully By OrgId");
			responseObjectsMap.put("shift", shift);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Shift Details information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getOperationName")
	public ResponseEntity<ResponseDTO> getOperationName(@RequestParam Long orgId, @RequestParam String routeCardNo) {
		String methodName = "getOperationName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> operation = new ArrayList<>();
		try {
			operation = jobOrderService.getOperationName(orgId, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Operation Details information get successfully By OrgId");
			responseObjectsMap.put("operation", operation);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Operation Details information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getOperatorName")
	public ResponseEntity<ResponseDTO> getOperatorName(@RequestParam Long orgId) {
		String methodName = "getOperatorName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> operation = new ArrayList<>();
		try {
			operation = jobOrderService.getOperatorName(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Operator Details information get successfully By OrgId");
			responseObjectsMap.put("operation", operation);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Operator Details information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTimings")
	public ResponseEntity<ResponseDTO> getTimings(@RequestParam Long orgId, @RequestParam String shiftCode) {
		String methodName = "getTimings()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> timings = new ArrayList<>();
		try {
			timings = jobOrderService.getTimings(orgId, shiftCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Timings Details information get successfully By OrgId");
			responseObjectsMap.put("timings", timings);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Timings Details information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getUnitforJobOrder")
	public ResponseEntity<ResponseDTO> getUnitforJobOrder(@RequestParam Long orgId, @RequestParam String partNo) {
		String methodName = "getUnitforJobOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> unit = new ArrayList<>();
		try {
			unit = jobOrderService.getUnitforJobOrder(orgId, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit Details information get successfully By OrgId");
			responseObjectsMap.put("unit", unit);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Unit Details information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardNoAndDetailsforJobOrder")
	public ResponseEntity<ResponseDTO> getRouteCardNoAndDetailsforJobOrder(@RequestParam Long orgId) {
		String methodName = "getRouteCardNoAndDetailsforJobOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> routeCard = new ArrayList<>();
		try {
			routeCard = jobOrderService.getRouteCardNoAndDetailsforJobOrder(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Route Card Details information get successfully By OrgId");
			responseObjectsMap.put("routeCardNo", routeCard);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Route Card Details information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

// Report APIs

	// jobOrder report APIs

	@GetMapping("/getJobOrderReport")
	public ResponseEntity<ResponseDTO> getJobOrderReport(@RequestParam Long orgId, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String status,String routeCardNo) {

		String methodName = "getJobOrderReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> reportList = jobOrderService.getJobOrderReport(orgId, fromDate, toDate, status,routeCardNo);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order Report retrieved successfully");
			responseObjectsMap.put("jobOrderReport", reportList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Job Order Report",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

}
