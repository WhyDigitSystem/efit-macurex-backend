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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.FgIssueToPackingDTO;
import com.efitops.basesetup.dto.FinalFgPartStockUpdateDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.FgIssueToPackingVO;
import com.efitops.basesetup.entity.FinalFgPartStockUpdateVO;
import com.efitops.basesetup.service.AssemblyService;

@CrossOrigin
@RestController
@RequestMapping("/api/assembly")
public class AssemblyController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(AssemblyController.class);

	@Autowired
	AssemblyService assemblyService;

	@GetMapping("/getAllFgPartStockUpdateVOByOrgId")
	public ResponseEntity<ResponseDTO> getAllFgPartStockUpdateVOByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllFgPartStockUpdateVOByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FinalFgPartStockUpdateVO> finalFgPartStockUpdateVO = new ArrayList<>();
		try {
			finalFgPartStockUpdateVO = assemblyService.getAllFgPartStockUpdateVOByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FgPartStockUpdateVO information get successfully By OrgId");
			responseObjectsMap.put("finalFgPartStockUpdateVO", finalFgPartStockUpdateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FgPartStockUpdateVO information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getFgPartStockUpdateVOById")
	public ResponseEntity<ResponseDTO> getFgPartStockUpdateVOById(@RequestParam Long id) {
		String methodName = "getFgPartStockUpdateVOById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		FinalFgPartStockUpdateVO finalFgPartStockUpdateVO = new FinalFgPartStockUpdateVO();
		try {
			finalFgPartStockUpdateVO = assemblyService.getFgPartStockUpdateVOById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FinalFgPartStockUpdateVO information get successfully By id");
			responseObjectsMap.put("finalFgPartStockUpdateVO", finalFgPartStockUpdateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FinalFgPartStockUpdateVO information receive failedById", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFgPartStockUpdateDocId")
	public ResponseEntity<ResponseDTO> getFgPartStockUpdateDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getFgPartStockUpdateDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = assemblyService.getFgPartStockUpdateDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FgPartStockUpdateDocId information retrieved successfully");
			responseObjectsMap.put("fgPartStockUpdateDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve FgPartStockUpdateDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateFgPartStockUpdate")
	public ResponseEntity<ResponseDTO> updateCreateFgPartStockUpdate(
			@RequestBody FinalFgPartStockUpdateDTO finalFgPartStockUpdateDTO) {
		String methodName = "updateCreateFgPartStockUpdate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> finalFgPartStockUpdateVO = assemblyService
					.updateCreateFgPartStockUpdate(finalFgPartStockUpdateDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, finalFgPartStockUpdateVO.get("message"));
			responseObjectsMap.put("finalFgPartStockUpdateVO",
					finalFgPartStockUpdateVO.get("finalFgPartStockUpdateVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardEntryNoFromFgPartStockUpdate")
	public ResponseEntity<ResponseDTO> getRouteCardEntryNoFromFgPartStockUpdate(@RequestParam Long orgId) {

		String methodName = "getRouteCardEntryNoFromFgPartStockUpdate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> finalFgPartStockUpdateVO = new ArrayList<>();
		try {
			finalFgPartStockUpdateVO = assemblyService.getRouteCardEntryNoFromFgPartStockUpdate(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" RouteCardEntryNo For FgPartStockUpdate information retrieved successfully");
			responseObjectsMap.put("finalFgPartStockUpdateVO", finalFgPartStockUpdateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  RouteCardEntryNo For FgPartStockUpdate information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardEntryDetailsFromFgPartStockUpdate")
	public ResponseEntity<ResponseDTO> getRouteCardEntryDetailsFromFgPartStockUpdate(@RequestParam Long orgId,
			@RequestParam String routeCardEntryNo) {

		String methodName = "getRouteCardEntryDetailsFromFgPartStockUpdate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> finalFgPartStockUpdateVO = new ArrayList<>();
		try {
			finalFgPartStockUpdateVO = assemblyService.getRouteCardEntryDetailsFromFgPartStockUpdate(orgId,
					routeCardEntryNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" RouteCardEntryDetails For FgPartStockUpdate information retrieved successfully");
			responseObjectsMap.put("finalFgPartStockUpdateVO", finalFgPartStockUpdateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  RouteCardEntryDetails For FgPartStockUpdate information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsFromFgPartStockUpdate")
	public ResponseEntity<ResponseDTO> getItemDetailsFromFgPartStockUpdate(@RequestParam Long orgId,
			@RequestParam String fgPartName) {

		String methodName = "getItemDetailsFromFgPartStockUpdate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> finalFgPartStockUpdateVO = new ArrayList<>();
		try {
			finalFgPartStockUpdateVO = assemblyService.getItemDetailsFromFgPartStockUpdate(orgId, fgPartName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ItemDetails For FgPartStockUpdate information retrieved successfully");
			responseObjectsMap.put("finalFgPartStockUpdateVO", finalFgPartStockUpdateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  ItemDetails For FgPartStockUpdate information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPriceDetails")
	public ResponseEntity<ResponseDTO> getPriceDetails(@RequestParam Long orgId, @RequestParam String itemName) {

		String methodName = "getPriceDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> finalFgPartStockUpdateVO = new ArrayList<>();
		try {
			finalFgPartStockUpdateVO = assemblyService.getPriceDetails(orgId, itemName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ItemDetailsPrice For FgPartStockUpdate information retrieved successfully");
			responseObjectsMap.put("finalFgPartStockUpdateVO", finalFgPartStockUpdateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  ItemDetails For FgPartStockUpdate information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// FgIssueToPacking

	@GetMapping("/getAllFgIssueToPackingVOByOrgId")
	public ResponseEntity<ResponseDTO> getAllFgIssueToPackingVOByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllFgIssueToPackingVOByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FgIssueToPackingVO> fgIssueToPackingVO = new ArrayList<>();
		try {
			fgIssueToPackingVO = assemblyService.getAllFgIssueToPackingVOByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FgIssueToPackingVO information get successfully By OrgId");
			responseObjectsMap.put("fgIssueToPackingVO", fgIssueToPackingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FgIssueToPackingVO information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getFgIssueToPackingVOById")
	public ResponseEntity<ResponseDTO> getFgIssueToPackingVOById(@RequestParam Long id) {
		String methodName = "getFgIssueToPackingVOById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		FgIssueToPackingVO fgIssueToPackingVO = new FgIssueToPackingVO();
		try {
			fgIssueToPackingVO = assemblyService.getFgIssueToPackingVOById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FgIssueToPackingVO information get successfully By id");
			responseObjectsMap.put("fgIssueToPackingVO", fgIssueToPackingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FgIssueToPackingVO information receive failedById", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFgIssueToPackingDocId")
	public ResponseEntity<ResponseDTO> getFgIssueToPackingDocId(@RequestParam Long orgId) {

		String methodName = "getFgIssueToPackingDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = assemblyService.getFgIssueToPackingDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FgIssueToPackingDocId information retrieved successfully");
			responseObjectsMap.put("fgIssueToPackingDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve FgIssueToPackingDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateFgIssueToPacking")
	public ResponseEntity<ResponseDTO> updateCreateFgIssueToPacking(
			@RequestBody FgIssueToPackingDTO fgIssueToPackingDTO) {
		String methodName = "updateCreateFgIssueToPacking()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> fgIssueToPackingVO = assemblyService.updateCreateFgIssueToPacking(fgIssueToPackingDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, fgIssueToPackingVO.get("message"));
			responseObjectsMap.put("fgIssueToPackingVO", fgIssueToPackingVO.get("fgIssueToPackingVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeptfromFgIssueToPacking")
	public ResponseEntity<ResponseDTO> getDeptfromFgIssueToPacking(@RequestParam Long orgId) {

		String methodName = "getDeptfromFgIssueToPacking()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> fgIssueToPackingVO = new ArrayList<>();
		try {
			fgIssueToPackingVO = assemblyService.getDeptfromFgIssueToPacking(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ItemDetails For FgIssueToPacking information retrieved successfully");
			responseObjectsMap.put("fgIssueToPackingVO", fgIssueToPackingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  ItemDetails For FgIssueToPacking information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardEntryNoFromFgIssueToPacking")
	public ResponseEntity<ResponseDTO> getRouteCardEntryNoFromFgIssueToPacking(@RequestParam Long orgId) {

		String methodName = "getRouteCardEntryNoFromFgIssueToPacking()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> fgIssueToPackingVO = new ArrayList<>();
		try {
			fgIssueToPackingVO = assemblyService.getRouteCardEntryNoFromFgIssueToPacking(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" RouteCardEntryNo For FgIssueToPacking information retrieved successfully");
			responseObjectsMap.put("fgIssueToPackingVO", fgIssueToPackingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  RouteCardEntryNo For FgIssueToPacking information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsFromFgIssueToPacking")
	public ResponseEntity<ResponseDTO> getItemDetailsFromFgIssueToPacking(@RequestParam Long orgId,
			@RequestParam String routeCardEntryNo) {

		String methodName = "getItemDetailsFromFgIssueToPacking()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> fgIssueToPackingVO = new ArrayList<>();
		try {
			fgIssueToPackingVO = assemblyService.getItemDetailsFromFgIssueToPacking(orgId, routeCardEntryNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ItemDetails For FgIssueToPacking information retrieved successfully");
			responseObjectsMap.put("fgIssueToPackingVO", fgIssueToPackingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  ItemDetails For FgIssueToPacking information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPartNameAndDesc")
	public ResponseEntity<ResponseDTO> getPartNameAndDesc(@RequestParam Long orgId) {
		String methodName = "getPartNameAndDesc()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = assemblyService.getPartNameAndDesc(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PartName Details retrieved successfully");
			responseObjectsMap.put("quotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartName Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getFinalFgPartStockUpdateReport")
	public ResponseEntity<ResponseDTO> getFinalFgPartStockUpdateReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,@RequestParam String partName) {
		String methodName = "getFinalFgPartStockUpdateReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = assemblyService.getFinalFgPartStockUpdateReport(orgId, fromDate, toDate,partName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseIndentReport Details retrieved successfully");
			responseObjectsMap.put("quotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseIndentReport Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getFgIssueToPackingReport")
	public ResponseEntity<ResponseDTO> getFgIssueToPackingReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,@RequestParam String routeCardNo) {
		String methodName = "getFgIssueToPackingReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = assemblyService.getFgIssueToPackingReport(orgId, fromDate, toDate,routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseIndentReport Details retrieved successfully");
			responseObjectsMap.put("quotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseIndentReport Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
