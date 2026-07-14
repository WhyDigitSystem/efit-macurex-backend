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
import com.efitops.basesetup.dto.PackingListDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.PackingListVO;
import com.efitops.basesetup.service.PackingListService;

@RestController
@RequestMapping("/api/packingList")
public class PackingListController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PackingListController.class);

	@Autowired
	PackingListService packingListService;

	@GetMapping("/getAllPackingListByOrgId")
	public ResponseEntity<ResponseDTO> getAllPackingListByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getAllPackingListByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PackingListVO> packingListVO = new ArrayList<>();
		try {
			packingListVO = packingListService.getAllPackingListByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingList information get successfully By OrgId");
			responseObjectsMap.put("packingListVO", packingListVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PackingList information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPackingListById")
	public ResponseEntity<ResponseDTO> getPackingListById(@RequestParam(required = false) Long id) {
		String methodName = "getPackingListById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PackingListVO packingListVO = new PackingListVO();
		try {
			packingListVO = packingListService.getPackingListById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingList information get successfully By id");
			responseObjectsMap.put("packingListVO", packingListVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PackingList information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreatePackingList")
	public ResponseEntity<ResponseDTO> updateCreatePackingList(@RequestBody PackingListDTO packingListDTO) {
		String methodName = "updateCreatePackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> packingListVO = packingListService.createUpdatePackingList(packingListDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, packingListVO.get("message"));
			responseObjectsMap.put("packingListVO", packingListVO.get("packingListVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPackingListByDocId")
	public ResponseEntity<ResponseDTO> getPackingListByDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getPackingListByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String packingListVO = null;
		try {
			packingListVO = packingListService.getPackingListDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingList  information get successfully By docid");
			responseObjectsMap.put("packingListVO", packingListVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PackingList information receive failed By docid", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomerNameFromPartyMasterPacking")
	public ResponseEntity<ResponseDTO> getCustomerNameFromPartyMasterPacking(@RequestParam Long orgId) {
		String methodName = "getCustomerNameFromPartyMasterPacking()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = packingListService.getCustomerNameFromPartyMasterPacking(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartyName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartyName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocIdFromSalesOrderNo")
	public ResponseEntity<ResponseDTO> getDocIdFromSalesOrderNo(@RequestParam Long orgId,
			@RequestParam String customerName) {
		String methodName = "getDocIdFromSalesOrderNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = packingListService.getDocIdFromSalesOrderNo(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalesOrderNo Details retrieved successfully");
			responseObjectsMap.put("salesOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SalesOrderNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoFromSalesOrder")
	public ResponseEntity<ResponseDTO> getPartNoFromSalesOrder(@RequestParam Long orgId,
			@RequestParam String salesOrderNo) {
		String methodName = "getCustomerNameFromPartyMasterPacking()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = packingListService.getPartNoFromSalesOrder(orgId, salesOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("salesOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPackingListDetails")
	public ResponseEntity<ResponseDTO> getPackingListDetails(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam String customername, @RequestParam String salesorderno) {
		String methodName = "getPackingListDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> packinglistVO = new ArrayList<>();
		try {
			packinglistVO = packingListService.getPackingListDetails(orgId, fromdate, todate, customername,
					salesorderno);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Local Packing List Details get successfully");
			responseObjectsMap.put("packinglistVO", packinglistVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Local Packing List Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
