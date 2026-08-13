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

import com.efitops.basesetup.ResponseDTO.OtherSalesInvoiceResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.OtherSalesInvoiceDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.OtherSalesInvoiceService;

@RestController
@RequestMapping("/api/otherSalesInvoice")
public class OtherSalesInvoiceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(OtherSalesInvoiceController.class);

	@Autowired
	OtherSalesInvoiceService otherSalesInvoiceService;

	@GetMapping("/getOtherSalesInvoiceById")
	public ResponseEntity<ResponseDTO> getOtherSalesInvoiceById(@RequestParam Long id) {

		String methodName = "getOtherSalesInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			OtherSalesInvoiceResponseDTO otherSalesInvoiceResponseDTO = otherSalesInvoiceService
					.getOtherSalesInvoiceById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Invoice information retrieved successfully");

			responseObjectsMap.put("otherSalesInvoiceResponseVO", otherSalesInvoiceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Quotation information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getOtherSalesInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getOtherSalesInvoiceByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getOtherSalesInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<OtherSalesInvoiceResponseDTO> otherSalesInvoiceResponseDTO = otherSalesInvoiceService
					.getOtherSalesInvoiceByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Order information retrieved successfully");

			responseObjectsMap.put("otherSalesInvoiceResponseVO", otherSalesInvoiceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Order information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateOtherSalesInvoice")
	public ResponseEntity<ResponseDTO> createUpdateOtherSalesInvoice(
			@RequestBody OtherSalesInvoiceDTO otherSalesInvoiceDTO) {
		String methodName = "createUpdateOtherSalesInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> otherSalesInvoiceVO = otherSalesInvoiceService
					.createUpdateOtherSalesInvoice(otherSalesInvoiceDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, otherSalesInvoiceVO.get("message"));
			responseObjectsMap.put("otherSalesInvoiceVO", otherSalesInvoiceVO.get("otherSalesInvoiceVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getOtherSalesInvoiceDocId")
	public ResponseEntity<ResponseDTO> getOtherSalesInvoiceDocId(@RequestParam Long orgId,
			@RequestParam String screenCode) {

		String methodName = "getOtherSalesInvoiceDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = otherSalesInvoiceService.getOtherSalesInvoiceDocId(orgId, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InvoiceDocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve InvoiceDocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getExchangeRate")
	public ResponseEntity<ResponseDTO> getExchangeRate(@RequestParam Long orgId, @RequestParam Long currency) {
		String methodName = "getExchangeRate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = otherSalesInvoiceService.getExchangeRate(orgId, currency);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ExChange Report retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve  ExChange", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTaxPercentage")
	public ResponseEntity<ResponseDTO> getTaxPercentage(@RequestParam Long orgId, @RequestParam Long hsn) {
		String methodName = "getTaxPercentage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = otherSalesInvoiceService.getTaxPercentage(orgId, hsn);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tax Report retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve  Tax", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsBasedDesPatch")
	public ResponseEntity<ResponseDTO> getItemDetailsBasedDesPatch(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam(required = false) Long despatch) {
		String methodName = "getItemDetailsBasedDesPatch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = otherSalesInvoiceService.getItemDetailsBasedDesPatch(orgId, branch, despatch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve  Item", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getSalesOrderNo")
	public ResponseEntity<ResponseDTO> getSalesOrderNo(@RequestParam Long customer) {
		String methodName = "getSalesOrderNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = otherSalesInvoiceService.getSalesOrderNo(customer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve  Sales", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}