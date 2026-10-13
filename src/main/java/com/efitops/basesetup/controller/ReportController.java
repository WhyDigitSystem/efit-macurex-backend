package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.ReportService;

@CrossOrigin
@RestController

@RequestMapping("/api/report")

public class ReportController extends BaseController {
	
	@Autowired
	private ReportService reportService;
	
	
	@GetMapping("/getRegularSaleCustomerWiseCumlReport")
	public ResponseEntity<ResponseDTO> getRegularSaleCustomerWiseCumlReport(
	        @RequestParam Long orgId,
	        @RequestParam String fromdate,
	        @RequestParam String todate) {

	    String methodName = "getRegularSaleCustomerWiseCumlReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap =
	                reportService.getRegularSaleCustomerWiseCumlReport(
	                        fromdate,
	                        todate,
	                        orgId);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg,
	                e);

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        errorMsg,
	                        errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getDespatchInstructionReport")
	public ResponseEntity<ResponseDTO> getDespatchInstructionReport(
	        @RequestParam Long orgId,
	        @RequestParam Long branch,
	        @RequestParam String fromdate,
	        @RequestParam String todate,
	        @RequestParam String division) {

	    String methodName = "getDespatchInstructionReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap =
	                reportService.getDespatchInstructionReport(
	                        fromdate,
	                        todate,
	                        division,
	                        orgId,
	                        branch);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg,
	                e);

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        errorMsg,
	                        errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSalesRegisterAppliancesReport")
	public ResponseEntity<ResponseDTO> getSalesRegisterAppliancesReport(
	        @RequestParam Long orgId,
	        @RequestParam Long branch,
	        @RequestParam String fromdate,
	        @RequestParam String todate) {

	    String methodName = "getSalesRegisterAppliancesReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String errorMsg = null;

	    try {

	        responseObjectsMap =
	                reportService.getSalesRegisterAppliancesReport(
	                        fromdate,
	                        todate,
	                        orgId,
	                        branch);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg,
	                e);

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        errorMsg,
	                        errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getCustomerSalesRegisterAppliancesReport")
	public ResponseEntity<ResponseDTO> getCustomerSalesRegisterAppliancesReport(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getCustomerSalesRegisterAppliancesReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	    	List<Map<String, Object>> customerResponse =
	    	        reportService.getCustomerSalesRegisterAppliancesReport(
	    	                orgId,
	    	                branch);
	    	

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Customer information retrieved successfully");

	        responseObjectsMap.put(
	                "customerList",
	                customerResponse);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Customer information retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}
}