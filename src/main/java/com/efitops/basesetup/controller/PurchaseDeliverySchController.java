package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.PurchaseDeliverySchService;
@CrossOrigin
@RestController
@RequestMapping("/api/purchasedeliveryschedule")

public class PurchaseDeliverySchController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseDeliverySchController.class);
	
	@Autowired
	PurchaseDeliverySchService purchaseDeliverySchService;
	
	@PostMapping("/updateCreatePurchaseDeliverySchedule")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseDeliverySchedule(
	        @RequestBody PurchaseDeliveryScheduleDTO purchaseDeliveryScheduleDTO) {

	    String methodName = "updateCreatePurchaseDeliverySchedule()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        Map<String, Object> responseMap = purchaseDeliverySchService
	                .updateCreatePurchaseDeliverySchedule(purchaseDeliveryScheduleDTO);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                responseMap.get("message"));

	        responseObjectsMap.put("purchaseDeliveryScheduleVO",
	                responseMap.get("purchaseDeliveryScheduleVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                errorMsg,
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseDeliveryScheduleById")
	public ResponseEntity<ResponseDTO> getPurchaseDeliveryScheduleById(
	        @RequestParam Long id) {

	    String methodName = "getPurchaseDeliveryScheduleById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        PurchaseDeliveryScheduleResponseDTO purchaseDeliveryScheduleResponseDTO =
	        		purchaseDeliverySchService.getPurchaseDeliveryScheduleById(id);

	        responseObjectsMap.put("purchaseDeliveryScheduleVO",
	                purchaseDeliveryScheduleResponseDTO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                errorMsg,
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	@GetMapping("/getPurchaseDeliveryScheduleByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseDeliveryScheduleByOrgId(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getPurchaseDeliveryScheduleByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        List<PurchaseDeliveryScheduleResponseDTO> responseList =
	        		purchaseDeliverySchService
	                        .getPurchaseDeliveryScheduleByOrgId(orgId, branch);

	        responseObjectsMap.put("purchaseDeliveryScheduleVO", responseList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                errorMsg,
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	}

