package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PoType;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.PurchaseServiceImport;

@RestController
@RequestMapping("/api/purchaseOrder")
public class PurchaseServiceImportController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImportController.class);

	@Autowired
	private PurchaseServiceImport purchaseOrderService;

	@GetMapping("/getPurchaseOrderByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getPurchaseOrderByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<PurchaseOrderResponseDTO> purchaseOrderList = purchaseOrderService.getPurchaseOrderByOrgId(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Orders retrieved successfully");
			responseObjectsMap.put("purchaseOrderVO", purchaseOrderList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Orders retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdatePurchaseOrder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdatePurchaseOrder(
			@RequestPart("purchaseOrder") PurchaseOrderDTO purchaseOrderDTO,
//		@RequestBody PurchaseOrderDTO purchaseOrderDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		String methodName = "createUpdatePurchaseOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> purchaseOrderMap = purchaseOrderService.createUpdatePurchaseOrder(purchaseOrderDTO,
					files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseOrderMap.get("message"));
			responseObjectsMap.put("purchaseOrderVO", purchaseOrderMap.get("purchaseOrderVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Order creation/update failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/viewFile/**")
	public ResponseEntity<byte[]> viewPurchaseOrderFile(HttpServletRequest request) {

		String methodName = "viewPurchaseOrderFile()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			return purchaseOrderService.viewPurchaseOrderFile(request);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/getPurchaseOrderById")
	public ResponseEntity<ResponseDTO> getPurchaseOrderById(@RequestParam Long id, @RequestParam PoType type) {

		String methodName = "getPurchaseOrderById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			PurchaseOrderResponseDTO purchaseOrderResponse = purchaseOrderService.getPurchaseOrderById(id, type);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Order retrieved successfully");
			responseObjectsMap.put("purchaseOrderVO", purchaseOrderResponse);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Order retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getItemDetailsResponsePurchaseLocal")
	public ResponseEntity<ResponseDTO> getItemDetailsResponsePurchaseLocal(@RequestParam Long orgId,
			@RequestParam Long branch) {
		String methodName = "getItemDetailsResponsePurchaseLocal()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getItemDetailsResponsePurchaseLocal(orgId, branch);
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

	@GetMapping("/getItemDetailsResponsePurchaseImport")
	public ResponseEntity<ResponseDTO> getItemDetailsResponsePurchaseImport(@RequestParam Long orgId,
			@RequestParam Long branch) {
		String methodName = "getItemDetailsResponsePurchaseImport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getItemDetailsResponsePurchaseImport(orgId, branch);
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

	@GetMapping("/getSupplierDetails")
	public ResponseEntity<ResponseDTO> getSupplierDetails(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getSupplierDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getSupplierDetails(orgId, branch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Supplier details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderDocId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDocId(@RequestParam Long orgId,
			@RequestParam String financialYear, @RequestParam String screenCode, @RequestParam PoType type) {

		String methodName = "getPurchaseOrderDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseOrderService.getPurchaseOrderDocId(orgId, financialYear, screenCode, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve  DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getExchangeRateDetails")
	public ResponseEntity<ResponseDTO> getExchangeRateDetails(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam Long currency) {
		String methodName = "getExchangeRateDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getExchangeRateDetails(orgId, branch, currency);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Exchange retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Exchange details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMutipleFactorAmount")
	public ResponseEntity<ResponseDTO> getMutipleFactorAmount(@RequestParam Long orgId, @RequestParam Long primaryUnit,
			@RequestParam Long purchaseUnit) {
		String methodName = "getMutipleFactorAmount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getMutipleFactorAmount(orgId, primaryUnit, purchaseUnit);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Rate retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Rate details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}