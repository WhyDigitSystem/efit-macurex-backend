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
import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.GateOutwardEntryDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.GateInwardEntryVO;
import com.efitops.basesetup.entity.GateOutwardEntryVO;
import com.efitops.basesetup.service.InwardOutwardService;

@CrossOrigin
@RestController
@RequestMapping("/api/inwardoutward")
public class InwardOutwardController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardOutwardController.class);

	@Autowired
	InwardOutwardService inwardOutwardService;

	@GetMapping("/getGateInwardEntryByOrgId")
	public ResponseEntity<ResponseDTO> getGateInwardEntryByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getGateInwardEntryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GateInwardEntryVO> gateInwardEntryVO = new ArrayList<>();
		try {
			gateInwardEntryVO = inwardOutwardService.getGateInwardEntryByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"GateInwardEntry information get successfully ByOrgId");
			responseObjectsMap.put("gateInwardEntryVO", gateInwardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GateInwardEntry information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGateInwardEntryById")
	public ResponseEntity<ResponseDTO> getGateInwardEntryById(@RequestParam Long id) {
		String methodName = "getGateInwardEntryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GateInwardEntryVO> gateInwardEntryVO = new ArrayList<>();
		try {
			gateInwardEntryVO = inwardOutwardService.getGateInwardEntryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GateInwardEntry information get successfully By Id");
			responseObjectsMap.put("gateInwardEntryVO", gateInwardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GateInwardEntry information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateGateInwardEntry")
	public ResponseEntity<ResponseDTO> updateCreateGateInwardEntry(@RequestBody GateInwardEntryDTO gateInwardEntryDTO) {
		String methodName = "updateCreateGateInwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> gateInwardEntryVO = inwardOutwardService
					.updateCreateGateInwardEntry(gateInwardEntryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, gateInwardEntryVO.get("message"));
			responseObjectsMap.put("gateInwardEntryVO", gateInwardEntryVO.get("gateInwardEntryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGateInwardEntryDocId")
	public ResponseEntity<ResponseDTO> getGateInwardEntryDocId(Long orgId, String finYear, String branchCode) {

		String methodName = "getGateInwardEntryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = inwardOutwardService.getGateInwardEntryDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"GateInwardEntry DocId information retrieved successfully");
			responseObjectsMap.put("GateInwardEntryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GateInwardEntry DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderNoForGateInward")
	public ResponseEntity<ResponseDTO> getPurchaseOrderNoForGateInward(@RequestParam Long orgId,
			@RequestParam String supplierCode) {
		String methodName = "getPurchaseOrderNoForGateInward()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseOrderNo = new ArrayList<>();
		try {
			purchaseOrderNo = inwardOutwardService.getPurchaseOrderNoForGateInward(orgId, supplierCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseOrderNo for GateInward information get successfully By OrgId");
			responseObjectsMap.put("purchaseOrderNo", purchaseOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseOrderNo for GateInward information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemDetailsForGateInwardEntry")
	public ResponseEntity<ResponseDTO> getItemDetailsForGateInwardEntry(@RequestParam Long orgId,
			@RequestParam String purchaseOrderNo) {
		String methodName = "getItemDetailsForGateInwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemDetails = new ArrayList<>();
		try {
			itemDetails = inwardOutwardService.getItemDetailsForGateInwardEntry(orgId, purchaseOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ItemDetails for GateInwardEntry information get successfully By OrgId");
			responseObjectsMap.put("itemDetails", itemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ItemDetails for GateInwardEntry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// GateOutWard

	@GetMapping("/getAllGateOutwardEntryByOrgId")
	public ResponseEntity<ResponseDTO> getAllGateOutwardEntryByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllGateOutwardEntryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GateOutwardEntryVO> gateOutwardEntryVO = new ArrayList<>();
		try {
			gateOutwardEntryVO = inwardOutwardService.getAllGateOutwardEntryByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"GateOutwardEntry information get successfully ByOrgId");
			responseObjectsMap.put("gateOutwardEntryVO", gateOutwardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GateOutwardEntry information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGateOutwardEntryById")
	public ResponseEntity<ResponseDTO> getGateOutwardEntryById(@RequestParam Long id) {
		String methodName = "getGateOutwardEntryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		GateOutwardEntryVO gateOutwardEntryVO = new GateOutwardEntryVO();
		try {
			gateOutwardEntryVO = inwardOutwardService.getGateOutwardEntryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"GateOutwardEntry information get successfully By Id");
			responseObjectsMap.put("gateOutwardEntryVO", gateOutwardEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GateOutwardEntry information receive failedBy Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateGateOutwardEntry")
	public ResponseEntity<ResponseDTO> updateCreateGateOutwardEntry(
			@RequestBody GateOutwardEntryDTO gateOutwardEntryDTO) {
		String methodName = "updateCreateGateOutwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> gateOutwardEntryVO = inwardOutwardService
					.updateCreateGateOutwardEntry(gateOutwardEntryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, gateOutwardEntryVO.get("message"));
			responseObjectsMap.put("gateOutwardEntryVO", gateOutwardEntryVO.get("gateOutwardEntryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomerNameAndCodeFromGateOutwardEntry")
	public ResponseEntity<ResponseDTO> getCustomerNameAndCodeFromGateOutwardEntry(@RequestParam Long orgId) {
		String methodName = "getCustomerNameAndCodeFromGateOutwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inwardOutwardService.getCustomerNameAndCodeFromGateOutwardEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Details retrieved successfully");
			responseObjectsMap.put("gateOutwardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Customer Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeliveryChallanNoForGateOutwardEntry")
	public ResponseEntity<ResponseDTO> getDeliveryChallanNoForGateOutwardEntry(@RequestParam Long orgId,
			@RequestParam String customername, @RequestParam String type) {
		String methodName = "getDeliveryChallanNoForGateOutwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inwardOutwardService.getDeliveryChallanNoForGateOutwardEntry(orgId, customername, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DeliveryChallanNo retrieved successfully");
			responseObjectsMap.put("gateOutwardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DeliveryChallanNo",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInvoiceNoForGateOutwardEntry")
	public ResponseEntity<ResponseDTO> getInvoiceNoForGateOutwardEntry(@RequestParam Long orgId,
			@RequestParam String dcNo, @RequestParam String type) {
		String methodName = "getInvoiceNoForGateOutwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inwardOutwardService.getInvoiceNoForGateOutwardEntry(orgId, dcNo, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InvoiceNo retrieved successfully");
			responseObjectsMap.put("gateOutwardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve InvoiceNo", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsForGateOutwardEntry")
	public ResponseEntity<ResponseDTO> getItemDetailsForGateOutwardEntry(@RequestParam Long orgId,
			@RequestParam String invNo) {
		String methodName = "getItemDetailsForGateOutwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inwardOutwardService.getItemDetailsForGateOutwardEntry(orgId, invNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "itemDetails retrieved successfully");
			responseObjectsMap.put("gateOutwardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve itemDetails", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGateOutwardEntryDocId")
	public ResponseEntity<ResponseDTO> getGateOutwardEntryDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getGateOutwardEntryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = inwardOutwardService.getGateOutwardEntryDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"GateOutwardEntryDocId DocId information retrieved successfully");
			responseObjectsMap.put("gateOutwardEntryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GateOutwardEntryDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEmployeeNameDetails")
	public ResponseEntity<ResponseDTO> getEmployeeNameDetails(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getEmployeeNameDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inwardOutwardService.getEmployeeNameDetails(orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmployeeDetails retrieved successfully");
			responseObjectsMap.put("employeeMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve EmployeeDetails",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeliveryChallanDetails")
	public ResponseEntity<ResponseDTO> getDeliveryChallanDetails(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String type) {
		String methodName = "getDeliveryChallanDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inwardOutwardService.getDeliveryChallanDetails(orgId, branchCode, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocIdDetails retrieved successfully");
			responseObjectsMap.put("salesVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DocIdDetails", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInvoiceDetails")
	public ResponseEntity<ResponseDTO> getInvoiceDetails(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String deliveryChallanNo) {
		String methodName = "getInvoiceDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inwardOutwardService.getInvoiceDetails(orgId, branchCode, deliveryChallanNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocIdDetails retrieved successfully");
			responseObjectsMap.put("subContractInvoiceVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DocIdDetails", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// GateOutWardEntry Report

	@GetMapping("/getGateOutwardEntryReport")
	public ResponseEntity<ResponseDTO> getGateOutwardEntryReport(@RequestParam Long orgId,
			@RequestParam String fromDate, @RequestParam String toDate) {
		String methodName = "getGateOutwardEntryReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = inwardOutwardService.getGateOutwardEntryReport(orgId, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GateOutwardEntry Details retrieved successfully");
			responseObjectsMap.put("gateOutwardEntry", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GateOutwardEntry Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGateInwardReport")
	public ResponseEntity<ResponseDTO> getGateInwardReport(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String supplierName, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		String methodName = "getGateInwardReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> inwardDetails = new ArrayList<>();
		try {
			inwardDetails = inwardOutwardService.getGateInwardReport(orgId, branchCode, supplierName, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InwardDetails get successfully By OrgId");
			responseObjectsMap.put("inwardDetails", inwardDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"InwardDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

}
