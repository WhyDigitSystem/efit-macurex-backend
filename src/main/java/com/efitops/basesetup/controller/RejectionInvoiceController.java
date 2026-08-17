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

import com.efitops.basesetup.ResponseDTO.ProformaInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.RejectionInvoiceResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ProformaInvoiceDTO;
import com.efitops.basesetup.dto.RejectionInvoiceDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.RejectionInvoiceService;

@RestController
@RequestMapping("/api/rejectionInvoice")
public class RejectionInvoiceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(RejectionInvoiceController.class);

	@Autowired
	RejectionInvoiceService rejectionInvoiceService;

	@GetMapping("/getRejectionInvoiceById")
	public ResponseEntity<ResponseDTO> getRejectionInvoiceById(@RequestParam Long id) {

		String methodName = "getRejectionInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			RejectionInvoiceResponseDTO rejectionInvoiceResponseDTO = rejectionInvoiceService
					.getRejectionInvoiceById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Rejection Invoice information retrieved successfully");

			responseObjectsMap.put("rejectionInvoiceResponseVO", rejectionInvoiceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Rejection Invoice information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getRejectionInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getRejectionInvoiceByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getRejectionInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<RejectionInvoiceResponseDTO> rejectionInvoiceResponseDTO = rejectionInvoiceService
					.getRejectionInvoiceByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Rejection Invoice information retrieved successfully");

			responseObjectsMap.put("rejectionInvoiceResponseVO", rejectionInvoiceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Rejection Invoice information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateRejectionInvoice")
	public ResponseEntity<ResponseDTO> createUpdateRejectionInvoice(
			@RequestBody RejectionInvoiceDTO rejectionInvoiceDTO) {
		String methodName = "createUpdateRejectionInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> rejectionInvoiceVO = rejectionInvoiceService
					.createUpdateRejectionInvoice(rejectionInvoiceDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, rejectionInvoiceVO.get("message"));
			responseObjectsMap.put("rejectionInvoiceVO", rejectionInvoiceVO.get("rejectionInvoiceVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRejectionInvoiceDocId")
	public ResponseEntity<ResponseDTO> getRejectionInvoiceDocId(@RequestParam Long orgId,
			@RequestParam String screenCode) {

		String methodName = "getRejectionInvoiceDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = rejectionInvoiceService.getRejectionInvoiceDocId(orgId, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Rejection Invoice DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Rejection Invoice DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTaxValue")
	public ResponseEntity<ResponseDTO> getTaxValue(@RequestParam Long orgId, @RequestParam Long hsn) {
		String methodName = "getTaxValue()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = rejectionInvoiceService.getTaxValue(orgId, hsn);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tax Percentage retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Tax Percentage", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsResponse")
	public ResponseEntity<ResponseDTO> getItemDetailsResponse(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getItemDetailsResponse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = rejectionInvoiceService.getItemDetailsResponse(orgId, branch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item details retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Item details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Proforma

	@GetMapping("/getProformaInvoiceById")
	public ResponseEntity<ResponseDTO> getProformaInvoiceById(@RequestParam Long id) {

		String methodName = "getProformaInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			ProformaInvoiceResponseDTO proformaInvoiceResponseDTO = rejectionInvoiceService.getProformaInvoiceById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Proforma Invoice information retrieved successfully");

			responseObjectsMap.put("proformaInvoiceResponseVO", proformaInvoiceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Proforma Invoice information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getProformaInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getProformaInvoiceByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getProformaInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<ProformaInvoiceResponseDTO> proformaInvoiceResponseDTO = rejectionInvoiceService
					.getProformaInvoiceByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Proforma Invoice information retrieved successfully");

			responseObjectsMap.put("proformaInvoiceResponseVO", proformaInvoiceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Proforma Invoice information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateProformaInvoice")
	public ResponseEntity<ResponseDTO> createUpdateProformaInvoice(@RequestBody ProformaInvoiceDTO proformaInvoiceDTO) {
		String methodName = "createUpdateProformaInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> proformaInvoiceVO = rejectionInvoiceService
					.createUpdateProformaInvoice(proformaInvoiceDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, proformaInvoiceVO.get("message"));
			responseObjectsMap.put("proformaInvoiceVO", proformaInvoiceVO.get("proformaInvoiceVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProformaInvoiceDocId")
	public ResponseEntity<ResponseDTO> getProformaInvoiceDocId(@RequestParam Long orgId,
			@RequestParam String financialYear, @RequestParam String screenCode) {

		String methodName = "getProformaInvoiceDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = rejectionInvoiceService.getProformaInvoiceDocId(orgId, financialYear, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Proforma Invoice DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Proforma Invoice DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//	@GetMapping("/getGstState")
//	public ResponseEntity<ResponseDTO> getGstState(@RequestParam Long orgId, @RequestParam Long customer) {
//		String methodName = "getGstState()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<Map<String, Object>> mapp = new ArrayList<>();
//
//		try {
//			mapp = rejectionInvoiceService.getGstState(orgId, customer);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item details retrieved successfully");
//			responseObjectsMap.put("mapp", mapp);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Item details", errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

}
