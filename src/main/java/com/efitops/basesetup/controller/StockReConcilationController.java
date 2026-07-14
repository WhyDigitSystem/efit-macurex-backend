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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PurchaseShortCloseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.StockReConcilationDTO;
import com.efitops.basesetup.dto.WorkOrderShortCloseDTO;
import com.efitops.basesetup.entity.PurchaseShortCloseVO;
import com.efitops.basesetup.entity.StockReConcilationVO;
import com.efitops.basesetup.entity.WorkOrderShortCloseVO;
import com.efitops.basesetup.service.StockReConcilationService;

@CrossOrigin
@RestController
@RequestMapping("/api/stockreconcilation")
public class StockReConcilationController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockReConcilationController.class);

	@Autowired
	StockReConcilationService stockReConcilationService;

	@GetMapping("/getAllStockReConcilationByOrgId")
	public ResponseEntity<ResponseDTO> getAllStockReConcilationByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllStockReConcilationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StockReConcilationVO> stockReConcilationVO = new ArrayList<>();
		try {
			stockReConcilationVO = stockReConcilationService.getAllStockReConcilationByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"StockReConcilation information get successfully ByOrgId");
			responseObjectsMap.put("stockReConcilationVO", stockReConcilationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"StockReConcilation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getStockReConcilationById")
	public ResponseEntity<ResponseDTO> getStockReConcilationById(@RequestParam Long id) {
		String methodName = "getStockReConcilationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		StockReConcilationVO stockReConcilationVO = new StockReConcilationVO();
		try {
			stockReConcilationVO = stockReConcilationService.getStockReConcilationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"StockReConcilation information get successfully By Id");
			responseObjectsMap.put("stockReConcilationVO", stockReConcilationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"StockReConcilation information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateStockReConcilation")
	public ResponseEntity<ResponseDTO> updateCreateStockReConcilation(
			@Valid @RequestBody StockReConcilationDTO stockReConcilationDTO) {
		String methodName = "updateCreateStockReConcilation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> stockReConcilationVO = stockReConcilationService
					.updateCreateStockReConcilation(stockReConcilationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, stockReConcilationVO.get("message"));
			responseObjectsMap.put("stockReConcilationVO", stockReConcilationVO.get("stockReConcilationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStockReConcilationDocId")
	public ResponseEntity<ResponseDTO> getStockReConcilationDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getStockReConcilationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = stockReConcilationService.getStockReConcilationDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EnquiryDocId information retrieved successfully");
			responseObjectsMap.put("enquiryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve EnquiryDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemNameAndDesc")
	public ResponseEntity<ResponseDTO> getItemNameAndDesc(@RequestParam Long orgId) {
		String methodName = "getItemNameAndDesc()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemVO = new ArrayList<>();
		try {
			itemVO = stockReConcilationService.getItemNameAndDesc(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item  Details get successfully");
			responseObjectsMap.put("itemVO", itemVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item Details Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// StockClose

	@GetMapping("/getPurchaseShortCloseByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseShortCloseByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getPurchaseShortCloseByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseShortCloseVO> purchaseShortCloseVO = new ArrayList<>();
		try {
			purchaseShortCloseVO = stockReConcilationService.getPurchaseShortCloseByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseShortClose information get successfully ByOrgId");
			responseObjectsMap.put("purchaseShortCloseVO", purchaseShortCloseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseShortClose information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseShortCloseById")
	public ResponseEntity<ResponseDTO> getPurchaseShortCloseById(@RequestParam Long id) {
		String methodName = "getPurchaseShortCloseById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PurchaseShortCloseVO purchaseShortCloseVO = new PurchaseShortCloseVO();
		try {
			purchaseShortCloseVO = stockReConcilationService.getPurchaseShortCloseById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseShortClose information get successfully By Id");
			responseObjectsMap.put("purchaseShortCloseVO", purchaseShortCloseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseShortClose information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreatePurchaseShortClose")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseShortClose(
			@RequestBody PurchaseShortCloseDTO purchaseShortCloseDTO) {
		String methodName = "updateCreatePurchaseShortClose()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> purchaseShortCloseVO = stockReConcilationService
					.updateCreatePurchaseShortClose(purchaseShortCloseDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseShortCloseVO.get("message"));
			responseObjectsMap.put("purchaseShortCloseVO", purchaseShortCloseVO.get("purchaseShortCloseVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseShortCloseDocId")
	public ResponseEntity<ResponseDTO> getPurchaseShortCloseDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getPurchaseShortCloseDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = stockReConcilationService.getPurchaseShortCloseDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseShortCloseDocId information retrieved successfully");
			responseObjectsMap.put("purchaseShortCloseDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseShortCloseDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderDetails")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDetails(@RequestParam Long orgId, @RequestParam String poNo) {
		String methodName = "getPurchaseOrderDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseDetailsVO = new ArrayList<>();
		try {
			purchaseDetailsVO = stockReConcilationService.getPurchaseOrderDetails(orgId, poNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseDetails get successfully");
			responseObjectsMap.put("purchaseDetailsVO", purchaseDetailsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PurchaseDetails Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWorkOrderDocId")
	public ResponseEntity<ResponseDTO> getWorkOrderDocId(@RequestParam Long orgId) {
		String methodName = "getWorkOrderDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderDocId = new ArrayList<>();
		try {
			workOrderDocId = stockReConcilationService.getWorkOrderDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "workOrderDocIdDetails get successfully");
			responseObjectsMap.put("workOrderDocId", workOrderDocId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "workOrderDocIdDetails Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// workorderstockclose

	@GetMapping("/getAllWorkOrderShortCloseByOrgId")
	public ResponseEntity<ResponseDTO> getAllWorkOrderShortCloseByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllWorkOrderShortCloseByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WorkOrderShortCloseVO> workOrderShortCloseVO = new ArrayList<>();
		try {
			workOrderShortCloseVO = stockReConcilationService.getAllWorkOrderShortCloseByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"WorkOrderShortClose information get successfully ByOrgId");
			responseObjectsMap.put("workOrderShortCloseVO", workOrderShortCloseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"WorkOrderShortClose information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getWorkOrderShortCloseById")
	public ResponseEntity<ResponseDTO> getWorkOrderShortCloseById(@RequestParam Long id) {
		String methodName = "getWorkOrderShortCloseById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		WorkOrderShortCloseVO workOrderShortCloseVO = new WorkOrderShortCloseVO();
		try {
			workOrderShortCloseVO = stockReConcilationService.getWorkOrderShortCloseById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"WorkOrderShortClose information get successfully By Id");
			responseObjectsMap.put("workOrderShortCloseVO", workOrderShortCloseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"WorkOrderShortClose information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateWorkOrderShortClose")
	public ResponseEntity<ResponseDTO> createUpdateWorkOrderShortClose(
			@RequestBody WorkOrderShortCloseDTO workOrderShortCloseDTO) {
		String methodName = "createUpdateWorkOrderShortClose()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> workOrderShortCloseVO = stockReConcilationService
					.createUpdateWorkOrderShortClose(workOrderShortCloseDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, workOrderShortCloseVO.get("message"));
			responseObjectsMap.put("workOrderShortCloseVO", workOrderShortCloseVO.get("workOrderShortCloseVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWorkOrderShortCloseDocId")
	public ResponseEntity<ResponseDTO> getWorkOrderShortCloseDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getWorkOrderShortCloseDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = stockReConcilationService.getWorkOrderShortCloseDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"WorkOrderShortCloseDocId information retrieved successfully");
			responseObjectsMap.put("workOrderShortCloseDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve WorkOrderShortCloseDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWorkOrderNumber")
	public ResponseEntity<ResponseDTO> getWorkOrderNumber(@RequestParam Long orgId, @RequestParam String branchCode, @RequestParam String workOrderNo) {
		String methodName = "getWorkOrderNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderNumber = new ArrayList<>();
		try {
			workOrderNumber = stockReConcilationService.getWorkOrderNumber(orgId, branchCode,
					workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrderNumbers get successfully");
			responseObjectsMap.put("workOrderNumber", workOrderNumber);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "WorkOrderNumbers Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWorkOrderDetails")
	public ResponseEntity<ResponseDTO> getWorkOrderDetails(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String workOrderNo) {
		String methodName = "getWorkOrderDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderDetails = new ArrayList<>();
		try {
			workOrderDetails = stockReConcilationService.getWorkOrderDetails(orgId, branchCode, workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrderDetails get successfully");
			responseObjectsMap.put("workOrderDetails", workOrderDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "WorkOrderDetails Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/approveWorkOrderShortClose")
	public ResponseEntity<ResponseDTO> approveWorkOrderShortClose(@RequestParam Long orgId, @RequestParam Long id,
			@RequestParam String docId, @RequestParam String action, @RequestParam String actionBy) {
		String methodName = "approveWorkOrderShortClose()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			WorkOrderShortCloseVO workOrderShortCloseVO = stockReConcilationService.approveWorkOrderShortClose(orgId,
					id, docId, action, actionBy);
			responseObjectsMap.put("workOrderShortCloseVO", workOrderShortCloseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/approvePurchaseShortClose")
	public ResponseEntity<ResponseDTO> approvePurchaseShortClose(@RequestParam Long orgId, @RequestParam Long id,
			@RequestParam String docId, @RequestParam String action, @RequestParam String actionBy) {
		String methodName = "approvePurchaseShortClose()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			PurchaseShortCloseVO purchaseShortCloseVO = stockReConcilationService.approvePurchaseShortClose(orgId, id,
					docId, action, actionBy);
			responseObjectsMap.put("purchaseShortCloseVO", purchaseShortCloseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderDocId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDocId(@RequestParam Long orgId) {
		String methodName = "getPurchaseOrderDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseOrderDocId = new ArrayList<>();
		try {
			purchaseOrderDocId = stockReConcilationService.getPurchaseOrderDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseOrderDetails get successfully");
			responseObjectsMap.put("purchaseOrderDocId", purchaseOrderDocId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PurchaseOrderDetails Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsFromPurchaseOrderDetails")
	public ResponseEntity<ResponseDTO> getItemDetailsFromPurchaseOrderDetails(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String poNo) {
		String methodName = "getItemDetailsFromPurchaseOrderDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseDetailsVO = new ArrayList<>();
		try {
			purchaseDetailsVO = stockReConcilationService.getItemDetailsFromPurchaseOrderDetails(orgId, branchCode,
					poNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemPurchaseDetails get successfully");
			responseObjectsMap.put("purchaseDetailsVO", purchaseDetailsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ItemPurchaseDetails Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getWorkOrderShortCloseReport")
	public ResponseEntity<ResponseDTO> getWorkOrderShortCloseReport(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		String methodName = "getWorkOrderShortCloseReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderShortCloseReport = new ArrayList<>();
		try {
			workOrderShortCloseReport = stockReConcilationService.getWorkOrderShortCloseReport(orgId, branchCode, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "workOrderShortCloseReport get successfully By OrgId");
			responseObjectsMap.put("workOrderShortCloseReport", workOrderShortCloseReport);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"workOrderShortCloseReport information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getPurchaseShortCloseReport")
	public ResponseEntity<ResponseDTO> getPurchaseShortCloseReport(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		String methodName = "getPurchaseShortCloseReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseShortCloseReport = new ArrayList<>();
		try {
			purchaseShortCloseReport = stockReConcilationService.getPurchaseShortCloseReport(orgId, branchCode, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseShortCloseReport get successfully By OrgId");
			responseObjectsMap.put("purchaseShortCloseReport", purchaseShortCloseReport);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseShortCloseReport information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

}
