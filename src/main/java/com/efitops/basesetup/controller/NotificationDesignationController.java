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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.NotificationDesignationDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.NotificationDesignationVO;
import com.efitops.basesetup.service.NotificationDesignationService;


@RestController
@RequestMapping("/api/NotificationDesignationController")



public class NotificationDesignationController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(NotificationDesignationController.class);
	
	@Autowired
	NotificationDesignationService notificationDesignationService;
	
	@PutMapping(value = "/createUpdateNotificationDesignation")
	public ResponseEntity<ResponseDTO> createUpdateNotificationDesignation(@RequestBody NotificationDesignationDTO notificationDesignationDTO) {

		String methodName = "createUpdateNotificationDesignation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> NotificationDesignationVO = notificationDesignationService
					.createUpdateNotificationDesignation(notificationDesignationDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, NotificationDesignationVO.get("message"));

			responseObjectsMap.put("notificationDesignationVO", NotificationDesignationVO.get("notificationDesignationVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAllNotificationDesignationByOrgId1")
	public ResponseEntity<ResponseDTO> getAllNotificationDesignationByOrgId1(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllNotificationDesignationByOrgId1()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<NotificationDesignationVO> notificationDesignationVO = new ArrayList<>();
		try {
			notificationDesignationVO = notificationDesignationService.getAllNotificationDesignationByOrgId1(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"NotificationDesignation information get successfully ByOrgId");
			responseObjectsMap.put("notificationDesignationVO", notificationDesignationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"NotificationDesignation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getNotificationDesignationById1")
	public ResponseEntity<ResponseDTO> getNotificationDesignationById(@RequestParam Long id) {
		String methodName = "getNotificationDesignationById1()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		NotificationDesignationVO notificationDesignationVO = new NotificationDesignationVO();
		try {
			notificationDesignationVO = notificationDesignationService.getNotificationDesignationById1(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"NotificationDesignation information get successfully By id");
			responseObjectsMap.put("notificationDesignationVO", notificationDesignationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"NotificationDesignation information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getNotificationDesignationDocId1")
	public ResponseEntity<ResponseDTO> getNotificationDesignationDocId1(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getNotificationDesignationDocId1()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = notificationDesignationService.getNotificationDesignationDocId1(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"NotificationDesignation information retrieved successfully");
			responseObjectsMap.put("notificationDesignationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve IncomingMaterialInspectionDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}


