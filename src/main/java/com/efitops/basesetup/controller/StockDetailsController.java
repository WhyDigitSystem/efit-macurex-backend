package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.StockDetailsService;


@RestController
@RequestMapping("/api/stockdetails")
public class StockDetailsController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(StockDetailsController.class);

	@Autowired
	StockDetailsService stockDetailsService;
	
	@GetMapping("/getAvailableStockQtyForGRN")
	public ResponseEntity<ResponseDTO> getAvailableStockQtyForGRN(
	        @RequestParam Long itemId,
	        @RequestParam Long locationId,
	        @RequestParam Long orgId,
	        @RequestParam Long branchId) {

	    String methodName = "getAvailableStockQtyForGRN()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO = null;

	    Map<String, Object> availableStock =
	            new HashMap<>();

	    try {

	        availableStock =
	                stockDetailsService.getAvailableStockQtyForGRN(
	                        itemId,
	                        locationId,
	                        orgId,
	                        branchId);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);
	    }

	    if (StringUtils.isEmpty(errorMsg)) {

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Available stock get successfully");

	        responseObjectsMap.put(
	                "availableStock",
	                availableStock);

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } else {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed to get available stock",
	                        errorMsg);
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity
	            .ok()
	            .body(responseDTO);
	}
	
	
}
