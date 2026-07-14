package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ExportPackingListDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.ExportPackingListVO;
import com.efitops.basesetup.service.ExportPackingListService;

@CrossOrigin
@RestController
@RequestMapping("/api/exportpackinglist") 
public class ExportPackingListController  extends BaseController{

	@Autowired
	ExportPackingListService  exportPackingListService;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MasterController.class);

	@GetMapping("/getExportPackingListByOrgId")
	public ResponseEntity<ResponseDTO> getExportPackingListByOrgId(@RequestParam Long orgid,@RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getExportPackingListByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ExportPackingListVO> exportPackingListVO = new ArrayList<>();
		try {
			exportPackingListVO = exportPackingListService.getExportPackingListByOrgId(orgid, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ExportPackingListVO information get successfully");
			responseObjectsMap.put("exportPackingListVO", exportPackingListVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ExportPackingListVO information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getExportPackingListById")
	public ResponseEntity<ResponseDTO> getExportPackingListById(@RequestParam Long id) {
		String methodName = "getExportPackingListById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ExportPackingListVO exportPackingListVO = null;
		try {
			exportPackingListVO = exportPackingListService.getExportPackingListById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ExportPackingListVO found by ID");
			responseObjectsMap.put("exportPackingListVO", exportPackingListVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "ExportPackingListVO not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "ExportPackingListVO not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getExportPackingListDocId")
	public ResponseEntity<ResponseDTO> getExportPackingListDocId(@RequestParam Long orgId,@RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getExportPackingListDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = exportPackingListService.getExportPackingListDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "exportPackingListDocId DocId information retrieved successfully");
			responseObjectsMap.put("exportPackingListDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve exportPackingListDocId  information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/updateCreateExportPackingList")
	public ResponseEntity<ResponseDTO> updateCreateExportPackingList(@Valid @RequestBody ExportPackingListDTO exportPackingListDTO) {
		String methodName = "updateCreateExportPackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> exportPackingListVO = exportPackingListService.updateCreateExportPackingList(exportPackingListDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, exportPackingListVO.get("message"));
			responseObjectsMap.put("exportPackingListVO", exportPackingListVO.get("exportPackingListVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getCustomerNameAndCodeForExportPackingList")
	public ResponseEntity<ResponseDTO> getCustomerNameAndCodeForExportPackingList(@RequestParam Long orgId) {
		String methodName = "getCustomerNameAndCodeFromRouteCardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = exportPackingListService.getCustomerNameAndCodeForExportPackingList(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerName retrieved successfully");
			responseObjectsMap.put("exportPackingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve CustomerName",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getCustomerDetailsForExportPackingList")
	public ResponseEntity<ResponseDTO> getCustomerDetailsForExportPackingList(@RequestParam Long orgId,@RequestParam String customerCode) {
		String methodName = "getCustomerDetailsForExportPackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = exportPackingListService.getCustomerDetailsForExportPackingList(orgId,customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Details retrieved successfully");
			responseObjectsMap.put("exportPackingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Customer Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
 
	
	@GetMapping("/getAllCountryForExportPackingList")
	public ResponseEntity<ResponseDTO> getAllCountryForExportPackingList(@RequestParam Long orgId) {
		String methodName = "getAllCountryForExportPackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = exportPackingListService.getAllCountryForExportPackingList(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CountryName retrieved successfully");
			responseObjectsMap.put("exportPackingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve CountryName",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("/getSalesOrderNoForExportPackingList")
	public ResponseEntity<ResponseDTO> getSalesOrderNoForExportPackingList(@RequestParam Long orgId,@RequestParam String customerCode) {
		String methodName = "getSalesOrderNoForExportPackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = exportPackingListService.getSalesOrderNoForExportPackingList(orgId,customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalesOrderNo retrieved successfully");
			responseObjectsMap.put("exportPackingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SalesOrderNo",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getSalesOrderDetailsForExportPackingList")
	public ResponseEntity<ResponseDTO> getSalesOrderDetailsForExportPackingList(@RequestParam Long orgId,@RequestParam String salesOrderNo) {
		String methodName = "getSalesOrderDetailsForExportPackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = exportPackingListService.getSalesOrderDetailsForExportPackingList(orgId,salesOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalesOrderDetails retrieved successfully");
			responseObjectsMap.put("exportPackingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SalesOrderDetails",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("/getCustomerDetailsForExportPackingListReport")
	public ResponseEntity<ResponseDTO> getCustomerDetailsForExportPackingListReport(@RequestParam Long orgId,@RequestParam String CustomerCode) {
		String methodName = "getCustomerDetailsForExportPackingListReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = exportPackingListService.getCustomerDetailsForExportPackingListReport(orgId,CustomerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerDetails retrieved successfully");
			responseObjectsMap.put("exportPackingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SalesOrderDetails",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getItemDetailsFromExportPackingListReport")
	public ResponseEntity<ResponseDTO> getItemDetailsFromExportPackingListReport(@RequestParam Long orgId,
			@RequestParam String salesOrderNo,@RequestParam String exportPackingListDocId) {
		String methodName = "getCustomerNameFromPartyMasterPacking()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = exportPackingListService.getItemDetailsFromExportPackingListReport(orgId, salesOrderNo,exportPackingListDocId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item Details retrieved successfully");
			responseObjectsMap.put("salesOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Item Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
//
//	// Export
//	@GetMapping("/getExportPackingListReport")
//	public ResponseEntity<ResponseDTO> getExportPackingListReport(@RequestParam Long orgId,
//			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,@RequestParam String customername,@RequestParam String salesorderno) {
//		String methodName = "getExportPackingListReport()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<Map<String, Object>> mapp = new ArrayList<>();
//
//		try {
//			mapp = exportPackingListService.getExportPackingListReport(orgId, fromdate, todate,customername,salesorderno);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Export Packing List Report  retrieved successfully");
//			responseObjectsMap.put("exportPackingListVO", mapp);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Export Packing List Report ", errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
	
	
	@GetMapping("/getExportPackingListReport")
	public ResponseEntity<ResponseDTO> getExportPackingListReport(
	        @RequestParam Long orgId,
	        @RequestParam(required = false) String fromdate, 
	        @RequestParam(required = false) String todate,
	        @RequestParam String customername,
	        @RequestParam String salesorderno) 
	{
	    String methodName = "getExportPackingListReport()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    List<Map<String, Object>> mapp = new ArrayList<>();
	      
	    try {
	        mapp = exportPackingListService.getExportPackingListReport(
	            orgId, fromdate, todate, customername, salesorderno);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }
	    
	    if (StringUtils.isBlank(errorMsg)) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, 
	            "Export Packing List Report retrieved successfully");
	        responseObjectsMap.put("exportPackingListVO", mapp);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        responseDTO = createServiceResponseError(
	            responseObjectsMap, 
	            "Failed to retrieve Export Packing List Report", 
	            errorMsg);
	    }
	    
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

}
