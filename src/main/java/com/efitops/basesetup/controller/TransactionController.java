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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.service.TransactionService;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends BaseController  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterController.class);
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping("/createUpdateSalesDeliverySchedule")
	public ResponseEntity<ResponseDTO> createUpdateSalesDeliverySchedule(
	        @RequestBody SalesDeliveryScheduleDTO salesDeliveryScheduleDTO) {

	    String methodName = "createUpdateSalesDeliverySchedule";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> responseMap =
	        		transactionService.createUpdateSalesDeliverySchedule(
	                        salesDeliveryScheduleDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                responseMap.get("message"));

	        responseObjectsMap.put(
	                "salesDeliverySchedule",
	                responseMap.get("salesDeliverySchedule"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage(),
	                e);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSalesDeliveryScheduleById")
	public ResponseEntity<ResponseDTO> getSalesDeliveryScheduleById(
	        @RequestParam Long id) {

	    String methodName = "getSalesDeliveryScheduleById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    SalesDeliveryScheduleResponseDTO salesDeliverySchedule = null;

	    try {

	        salesDeliverySchedule =
	        		transactionService.getSalesDeliveryScheduleById(id);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg)) {

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Sales Delivery Schedule information retrieved successfully");

	        responseObjectsMap.put(
	                "salesDeliverySchedule",
	                salesDeliverySchedule);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Sales Delivery Schedule information retrieval failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesDeliveryScheduleByOrgId")
	public ResponseEntity<ResponseDTO> getSalesDeliveryScheduleByOrgId(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getSalesDeliveryScheduleByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    List<SalesDeliveryScheduleResponseDTO> salesDeliveryScheduleList =
	            new ArrayList<>();

	    try {

	        salesDeliveryScheduleList =
	        		transactionService.getAllSalesDeliverySchedule(
	                        orgId,
	                        branch);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);
	    }

	    if (StringUtils.isBlank(errorMsg)) {

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Sales Delivery Schedule information retrieved successfully");

	        responseObjectsMap.put(
	                "salesDeliveryScheduleList",
	                salesDeliveryScheduleList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Sales Delivery Schedule information retrieval failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}

	//contract dropdown

	@GetMapping("/getContractNoDropdown")
	public ResponseEntity<ResponseDTO> getContractNoDropdown(@RequestParam Long orgId,@RequestParam Long branch) {

	    String methodName = "getContractNoDropdown";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	    	Map<String, Object> responseMap =
	    			transactionService.getContractNo( orgId,  branch);
	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                responseMap.get("message"));

	        responseObjectsMap.put(
	                "contractList",
	                responseMap.get("contractList"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage(),
	                e);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}


	//item dropdown

	@GetMapping("/getItemDropdown")
	public ResponseEntity<ResponseDTO> getItemDropdown(
	        @RequestParam String docId) {

	    String methodName = "getItemDropdown";

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> responseMap =
	        		transactionService.getItemDropdown(docId);

	        responseObjectsMap.put("message", responseMap.get("message"));
	        responseObjectsMap.put("itemList", responseMap.get("itemList"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}

	
	@GetMapping("/getCustomerDetails")
	public ResponseEntity<ResponseDTO> getCustomerDetails(
			  @RequestParam Long orgId,
		        @RequestParam Long branch) {

	    String methodName = "getItemDropdown";

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> responseMap =
	        		transactionService.getAllCustomerDetails(orgId,branch);

	        responseObjectsMap.put("message", responseMap.get("message"));
	        responseObjectsMap.put("customerDetails", responseMap.get("customerDetails"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	

}
