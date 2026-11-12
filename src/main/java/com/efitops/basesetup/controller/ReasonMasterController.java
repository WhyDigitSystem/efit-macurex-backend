package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.ResponseDTO.ReasonMasterResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ReasonMasterDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.ReasonMasterService;

@CrossOrigin
@RestController

@RequestMapping("/api/reasonmaster")

public class ReasonMasterController extends BaseController {
	
	
	@Autowired
	private ReasonMasterService reasonMasterService;
	
	
	@PutMapping("/createUpdateReasonMaster")
	public ResponseEntity<ResponseDTO> createUpdateReasonMaster(
	        @RequestBody ReasonMasterDTO reasonMasterDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> reasonMasterMap =
	        		reasonMasterService.createUpdateReasonMaster(reasonMasterDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                reasonMasterMap.get("message"));

	        responseObjectsMap.put(
	                "reasonMasterVO",
	                reasonMasterMap.get("reasonMasterVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getReasonMasterById")
	public ResponseEntity<ResponseDTO> getReasonMasterById(@RequestParam Long id) {

	    String methodName = "getReasonMasterById()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    try {

	        ReasonMasterResponseDTO reasonMasterResponseDTO =
	                reasonMasterService.getReasonMasterById(id);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Reason Master information retrieved successfully");

	        responseObjectsMap.put(
	                "reasonMasterVO",
	                reasonMasterResponseDTO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Reason Master information retrieval failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}


	@GetMapping("/getReasonMasterByOrgId")
	public ResponseEntity<ResponseDTO> getReasonMasterByOrgId(
	        @RequestParam Long orgId) {

	    String methodName = "getReasonMasterByOrgId()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        List<ReasonMasterResponseDTO> reasonMasterResponseDTO =
	        		reasonMasterService
	        		.getReasonMasterByOrgId(orgId);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Reason Master information retrieved successfully");

	        responseObjectsMap.put(
	                "reasonMasterResponseVO",
	                reasonMasterResponseDTO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Reason Master information retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}

}
