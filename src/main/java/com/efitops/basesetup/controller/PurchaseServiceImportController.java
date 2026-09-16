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

import com.efitops.basesetup.ResponseDTO.BillOfMaterialResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseResponseDTO;
import com.efitops.basesetup.ResponseDTO.FgTransferSlipResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialIndentForProductionResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionScheduleOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionTransferSlipResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderDeliveryScheduleShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.BillOfMaterialDTO;
import com.efitops.basesetup.dto.DirectPurchaseDTO;
import com.efitops.basesetup.dto.FgTransferSlipDTO;
import com.efitops.basesetup.dto.MaterialIndentForProductionDTO;
import com.efitops.basesetup.dto.PoType;
import com.efitops.basesetup.dto.ProductionScheduleOrderDTO;
import com.efitops.basesetup.dto.ProductionTransferSlipDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseOrderDeliveryScheduleShortCloseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.StockTransferDTO;
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

	@GetMapping("/getIndentNoBasedLocal")
	public ResponseEntity<ResponseDTO> getIndentNoBasedLocal(@RequestParam Long orgId, @RequestParam String belongsTo,
			@RequestParam String type) {

		String methodName = "getIndentNoBasedLocal()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getIndentNoBasedLocal(orgId, belongsTo, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IndentNo retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve IndentNo details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIndentNoBasedImport")
	public ResponseEntity<ResponseDTO> getIndentNoBasedImport(@RequestParam Long orgId,
			@RequestParam(required = false) String type) {

		String methodName = "getIndentNoBasedLocal()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getIndentNoBasedImport(orgId, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IndentNo retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve IndentNo details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getHsnCodeDetails")
	public ResponseEntity<ResponseDTO> getHsnCodeDetails(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam Long item, @RequestParam String type) {
		String methodName = "getHsnCodeDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getHsnCodeDetails(orgId, branch, item, type);
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

	// ShortClose

	@GetMapping("/getPurchaseOrderDeliveryScheduleShortCloseById")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDeliveryScheduleShortCloseById(@RequestParam Long id) {

		String methodName = "getPurchaseOrderDeliveryScheduleShortCloseById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			PurchaseOrderDeliveryScheduleShortCloseResponseDTO purchaseOrderDeliveryScheduleShortCloseResponseDTO = purchaseOrderService
					.getPurchaseOrderDeliveryScheduleShortCloseById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ShortCose information retrieved successfully");

			responseObjectsMap.put("purchaseOrderDeliveryScheduleShortCloseVO",
					purchaseOrderDeliveryScheduleShortCloseResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "ShortCose information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPurchaseOrderDeliveryScheduleShortCloseByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDeliveryScheduleShortCloseByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getPurchaseOrderDeliveryScheduleShortCloseByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<PurchaseOrderDeliveryScheduleShortCloseResponseDTO> purchaseOrderDeliveryScheduleShortCloseResponseDTO = purchaseOrderService
					.getPurchaseOrderDeliveryScheduleShortCloseByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Proforma Invoice information retrieved successfully");

			responseObjectsMap.put("purchaseOrderDeliveryScheduleShortCloseVO",
					purchaseOrderDeliveryScheduleShortCloseResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Proforma Invoice information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdatePurchaseOrderDeliveryScheduleShortClose")
	public ResponseEntity<ResponseDTO> createUpdatePurchaseOrderDeliveryScheduleShortClose(
			@RequestBody PurchaseOrderDeliveryScheduleShortCloseDTO purchaseOrderDeliveryScheduleShortCloseDTO) {
		String methodName = "createUpdatePurchaseOrderDeliveryScheduleShortClose()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> purchaseOrderDeliveryScheduleShortCloseVO = purchaseOrderService
					.createUpdatePurchaseOrderDeliveryScheduleShortClose(purchaseOrderDeliveryScheduleShortCloseDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					purchaseOrderDeliveryScheduleShortCloseVO.get("message"));
			responseObjectsMap.put("purchaseOrderDeliveryScheduleShortCloseVO",
					purchaseOrderDeliveryScheduleShortCloseVO.get("purchaseOrderDeliveryScheduleShortCloseVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderDeliveryScheduleShortCloseDocId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDeliveryScheduleShortCloseDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getPurchaseOrderDeliveryScheduleShortCloseDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseOrderService.getPurchaseOrderDeliveryScheduleShortCloseDocId(orgId, financialYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieveDocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSupplierDetailsShortClose")
	public ResponseEntity<ResponseDTO> getSupplierDetailsShortClose(@RequestParam Long orgId,
			@RequestParam Long branch) {
		String methodName = "getSupplierDetailsShortClose()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getSupplierDetailsShortClose(orgId, branch);
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

	@GetMapping("/getPurchaseOrderNobasedSchedule")
	public ResponseEntity<ResponseDTO> getPurchaseOrderNobasedSchedule(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long supplier) {
		String methodName = "getPurchaseOrderNobasedSchedule()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getPurchaseOrderNobasedSchedule(orgId, branch, supplier);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocId details retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DocId details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderNobasedScheduleDetails")
	public ResponseEntity<ResponseDTO> getPurchaseOrderNobasedScheduleDetails(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long supplier, @RequestParam String purchaseOrderNo) {
		String methodName = "getPurchaseOrderNobasedScheduleDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getPurchaseOrderNobasedScheduleDetails(orgId, branch, supplier,
					purchaseOrderNo);
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

	// direct purchase

	@GetMapping("/getDirectPurchaseByOrgId")
	public ResponseEntity<ResponseDTO> getDirectPurchaseByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getDirectPurchaseByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<DirectPurchaseResponseDTO> purchaseOrderList = purchaseOrderService.getDirectPurchaseByOrgId(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Direct Purchase  retrieved successfully");
			responseObjectsMap.put("directPurchaseVO", purchaseOrderList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Direct Purchase Orders retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdateDirectPurchase", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateDirectPurchase(
			@RequestPart("directPurchase") DirectPurchaseDTO directPurchaseDTO,
//			@RequestBody DirectPurchaseDTO directPurchaseDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		String methodName = "createUpdateDirectPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> purchaseOrderMap = purchaseOrderService.createUpdateDirectPurchase(directPurchaseDTO,
					files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseOrderMap.get("message"));
			responseObjectsMap.put("directPurchaseVO", purchaseOrderMap.get("directPurchaseVO"));

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

	@GetMapping("/viewDirectPurchaseFile/**")
	public ResponseEntity<byte[]> viewDirectPurchaseFile(HttpServletRequest request) {

		String methodName = "viewDirectPurchaseFile()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			return purchaseOrderService.viewDirectPurchaseFile(request);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/getDirectPurchaseById")
	public ResponseEntity<ResponseDTO> getDirectPurchaseById(@RequestParam Long id) {

		String methodName = "getDirectPurchaseById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			DirectPurchaseResponseDTO purchaseOrderResponse = purchaseOrderService.getDirectPurchaseById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Order retrieved successfully");
			responseObjectsMap.put("directPurchaseVO", purchaseOrderResponse);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Order retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDirectPurchaseDocId")
	public ResponseEntity<ResponseDTO> getDirectPurchaseDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getPurchaseOrderDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseOrderService.getDirectPurchaseDocId(orgId, financialYear);
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

	@GetMapping("/getIssueTo")
	public ResponseEntity<ResponseDTO> getIssueTo(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getIssueTo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getIssueTo(orgId, branch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IssueTo retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve IssueTo details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemType")
	public ResponseEntity<ResponseDTO> getItemType(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam Long itemType) {
		String methodName = "getItemType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getItemType(orgId, branch, itemType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Type retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Type details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Stocktransfer

	@GetMapping("/getStockTransferById")
	public ResponseEntity<ResponseDTO> getStockTransferById(@RequestParam Long id) {

		String methodName = "getStockTransferById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			StockTransferResponseDTO stockTransferResponseDTO = purchaseOrderService.getStockTransferById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock information retrieved successfully");

			responseObjectsMap.put("stockTransferResponseVO", stockTransferResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Stock retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getStockTransferByOrgId")
	public ResponseEntity<ResponseDTO> getStockTransferByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getStockTransferByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<StockTransferResponseDTO> stockTransferResponseDTO = purchaseOrderService
					.getStockTransferByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock information retrieved successfully");

			responseObjectsMap.put("stockTransferResponseVO", stockTransferResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Stock information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateStockTransfer")
	public ResponseEntity<ResponseDTO> createUpdateStockTransfer(@RequestBody StockTransferDTO stockTransferDTO) {
		String methodName = "createUpdateStockTransfer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> stockTransferVO = purchaseOrderService.createUpdateStockTransfer(stockTransferDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, stockTransferVO.get("message"));
			responseObjectsMap.put("stockTransferVO", stockTransferVO.get("stockTransferVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStockTransferDocId")
	public ResponseEntity<ResponseDTO> getStockTransferDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getStockTransferDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseOrderService.getStockTransferDocId(orgId, financialYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"StockTransferDocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve StockTransferDocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStockTransferItemDetails")
	public ResponseEntity<ResponseDTO> getStockTransferItemDetails(@RequestParam Long orgId,
			@RequestParam Long branch) {
		String methodName = "getStockTransferItemDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getStockTransferItemDetails(orgId, branch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Item details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// PurchaseOrde

	@GetMapping("/getProductionScheduleOrderById")
	public ResponseEntity<ResponseDTO> getProductionScheduleOrderById(@RequestParam Long id) {

		String methodName = "getProductionScheduleOrderById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			ProductionScheduleOrderResponseDTO productionScheduleOrderResponseDTO = purchaseOrderService
					.getProductionScheduleOrderById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Schedule Order information retrieved successfully");

			responseObjectsMap.put("productionScheduleOrderResponseVO", productionScheduleOrderResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Production Schedule Order retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getProductionScheduleOrderByOrgId")
	public ResponseEntity<ResponseDTO> getProductionScheduleOrderByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getProductionScheduleOrderByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<ProductionScheduleOrderResponseDTO> productionScheduleOrderResponseDTO = purchaseOrderService
					.getProductionScheduleOrderByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Schedule Order information retrieved successfully");

			responseObjectsMap.put("productionScheduleOrderResponseVO", productionScheduleOrderResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Production Schedule Order information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateProductionScheduleOrder")
	public ResponseEntity<ResponseDTO> createUpdateProductionScheduleOrder(
			@RequestBody ProductionScheduleOrderDTO productionScheduleOrderDTO) {

		String methodName = "createUpdateProductionScheduleOrder()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> productionScheduleOrderVO = purchaseOrderService
					.createUpdateProductionScheduleOrder(productionScheduleOrderDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, productionScheduleOrderVO.get("message"));

			responseObjectsMap.put("productionScheduleOrderVO",
					productionScheduleOrderVO.get("productionScheduleOrderVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProductionScheduleOrderDocId")
	public ResponseEntity<ResponseDTO> getProductionScheduleOrderDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getProductionScheduleOrderDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = purchaseOrderService.getProductionScheduleOrderDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProductionScheduleOrderDocId information retrieved successfully");

			responseObjectsMap.put("productionScheduleOrderDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ProductionScheduleOrderDocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	//

	@GetMapping("/getBillOfMaterialById")
	public ResponseEntity<ResponseDTO> getBillOfMaterialById(@RequestParam Long id) {

		String methodName = "getBillOfMaterialById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			BillOfMaterialResponseDTO billOfMaterialResponseDTO = purchaseOrderService.getBillOfMaterialById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Bill Of Material information retrieved successfully");

			responseObjectsMap.put("billOfMaterialResponseVO", billOfMaterialResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Bill Of Material retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getBillOfMaterialByOrgId")
	public ResponseEntity<ResponseDTO> getBillOfMaterialByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getBillOfMaterialByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<BillOfMaterialResponseDTO> billOfMaterialResponseDTO = purchaseOrderService
					.getBillOfMaterialByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Bill Of Material information retrieved successfully");

			responseObjectsMap.put("billOfMaterialResponseVO", billOfMaterialResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Bill Of Material information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateBillOfMaterial")
	public ResponseEntity<ResponseDTO> createUpdateBillOfMaterial(@RequestBody BillOfMaterialDTO billOfMaterialDTO) {

		String methodName = "createUpdateBillOfMaterial()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> billOfMaterialVO = purchaseOrderService.createUpdateBillOfMaterial(billOfMaterialDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, billOfMaterialVO.get("message"));

			responseObjectsMap.put("billOfMaterialVO", billOfMaterialVO.get("billOfMaterialVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBillOfMaterialDocId")
	public ResponseEntity<ResponseDTO> getBillOfMaterialDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getBillOfMaterialDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = purchaseOrderService.getBillOfMaterialDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"BillOfMaterialDocId information retrieved successfully");

			responseObjectsMap.put("billOfMaterialDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve BillOfMaterialDocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFgAndSfgItemDetails")
	public ResponseEntity<ResponseDTO> getFgAndSfgItemDetails(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam String type) {
		String methodName = "getFgAndSfgItemDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getFgAndSfgItemDetails(orgId, branch, type);
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

	@GetMapping("/getGridDetailsFromBom")
	public ResponseEntity<ResponseDTO> getGridDetailsFromBom(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getGridDetailsFromBom()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getGridDetailsFromBom(orgId, branch);
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

	@GetMapping("/getScrapDetailsItem")
	public ResponseEntity<ResponseDTO> getScrapDetailsItem(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getScrapDetailsItem()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getScrapDetailsItem(orgId, branch);
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

	@GetMapping("/getSfGDocIdAndDetails")
	public ResponseEntity<ResponseDTO> getSfGDocIdAndDetails(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getSfGDocIdAndDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getSfGDocIdAndDetails(orgId, branch);
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

	@GetMapping("/getFillDetailsOf")
	public ResponseEntity<ResponseDTO> getFillDetailsOf(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam Long fgItem) {
		String methodName = "getFillDetailsOf()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getFillDetailsOf(orgId, branch, fgItem);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocId details retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DocId details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFgAndSfgItemDetailsFromProduction")
	public ResponseEntity<ResponseDTO> getFgAndSfgItemDetailsFromProduction(@RequestParam Long orgId,
			@RequestParam Long branch) {
		String methodName = "getFgAndSfgItemDetailsFromProduction()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getFgAndSfgItemDetailsFromProduction(orgId, branch);
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

	@GetMapping("/getFgAndSfgItemDetailsFromProductionDetails")
	public ResponseEntity<ResponseDTO> getFgAndSfgItemDetailsFromProductionDetails(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long bom) {
		String methodName = "getFgAndSfgItemDetailsFromProductionDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getFgAndSfgItemDetailsFromProductionDetails(orgId, branch, bom);
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

	//

	@GetMapping("/getMaterialIndentForProductionById")
	public ResponseEntity<ResponseDTO> getMaterialIndentForProductionById(@RequestParam Long id) {

		String methodName = "getMaterialIndentForProductionById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			MaterialIndentForProductionResponseDTO materialIndentForProductionResponseDTO = purchaseOrderService
					.getMaterialIndentForProductionById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Material Indent For Production information retrieved successfully");

			responseObjectsMap.put("materialIndentForProductionResponseVO", materialIndentForProductionResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Material Indent For Production retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getMaterialIndentForProductionByOrgId")
	public ResponseEntity<ResponseDTO> getMaterialIndentForProductionByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getMaterialIndentForProductionByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<MaterialIndentForProductionResponseDTO> materialIndentForProductionResponseDTO = purchaseOrderService
					.getMaterialIndentForProductionByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Material Indent For Production information retrieved successfully");

			responseObjectsMap.put("materialIndentForProductionResponseVO", materialIndentForProductionResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Material Indent For Production information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateMaterialIndentForProduction")
	public ResponseEntity<ResponseDTO> createUpdateMaterialIndentForProduction(
			@RequestBody MaterialIndentForProductionDTO materialIndentForProductionDTO) {

		String methodName = "createUpdateMaterialIndentForProduction()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> materialIndentForProductionVO = purchaseOrderService
					.createUpdateMaterialIndentForProduction(materialIndentForProductionDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, materialIndentForProductionVO.get("message"));

			responseObjectsMap.put("materialIndentForProductionVO",
					materialIndentForProductionVO.get("materialIndentForProductionVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMaterialIndentForProductionDocId")
	public ResponseEntity<ResponseDTO> getMaterialIndentForProductionDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getMaterialIndentForProductionDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = purchaseOrderService.getMaterialIndentForProductionDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MaterialIndentForProductionDocId information retrieved successfully");

			responseObjectsMap.put("materialIndentForProductionDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve MaterialIndentForProductionDocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFgAndSfgItemDetailsFromMaterial")
	public ResponseEntity<ResponseDTO> getFgAndSfgItemDetailsFromMaterial(@RequestParam Long orgId,
			@RequestParam Long branch) {
		String methodName = "getFgAndSfgItemDetailsFromMaterial()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getFgAndSfgItemDetailsFromMaterial(orgId, branch);
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

	@GetMapping("/getFgAndSfgItemDetailsFromMaterialDetails")
	public ResponseEntity<ResponseDTO> getFgAndSfgItemDetailsFromMaterialDetails(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long fgItem) {
		String methodName = "getFgAndSfgItemDetailsFromMaterialDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getFgAndSfgItemDetailsFromMaterialDetails(orgId, branch, fgItem);
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

	//

	@GetMapping("/getProductionTransferSlipById")
	public ResponseEntity<ResponseDTO> getProductionTransferSlipById(@RequestParam Long id) {

		String methodName = "getProductionTransferSlipById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			ProductionTransferSlipResponseDTO productionTransferSlipResponseDTO = purchaseOrderService
					.getProductionTransferSlipById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Transfer Slip information retrieved successfully");

			responseObjectsMap.put("productionTransferSlipResponseVO", productionTransferSlipResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Production Transfer Slip retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getProductionTransferSlipByOrgId")
	public ResponseEntity<ResponseDTO> getProductionTransferSlipByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getProductionTransferSlipByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<ProductionTransferSlipResponseDTO> productionTransferSlipResponseDTO = purchaseOrderService
					.getProductionTransferSlipByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Transfer Slip information retrieved successfully");

			responseObjectsMap.put("productionTransferSlipResponseVO", productionTransferSlipResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Production Transfer Slip information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateProductionTransferSlip")
	public ResponseEntity<ResponseDTO> createUpdateProductionTransferSlip(
			@RequestBody ProductionTransferSlipDTO productionTransferSlipDTO) {

		String methodName = "createUpdateProductionTransferSlip()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> productionTransferSlipVO = purchaseOrderService
					.createUpdateProductionTransferSlip(productionTransferSlipDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, productionTransferSlipVO.get("message"));

			responseObjectsMap.put("productionTransferSlipVO",
					productionTransferSlipVO.get("productionTransferSlipVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProductionTransferSlipDocId")
	public ResponseEntity<ResponseDTO> getProductionTransferSlipDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getProductionTransferSlipDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = purchaseOrderService.getProductionTransferSlipDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProductionTransferSlipDocId information retrieved successfully");

			responseObjectsMap.put("productionTransferSlipDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ProductionTransferSlipDocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	// Slip

	@GetMapping("/getFgTransferSlipById")
	public ResponseEntity<ResponseDTO> getFgTransferSlipById(@RequestParam Long id) {
		String methodName = "getFgTransferSlipById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			FgTransferSlipResponseDTO fgTransferSlipResponseDTO = purchaseOrderService.getFgTransferSlipById(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FG Transfer Slip information retrieved successfully");
			responseObjectsMap.put("fgTransferSlipResponseVO", fgTransferSlipResponseDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "FG Transfer Slip retrieval failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getFgTransferSlipDocId")
	public ResponseEntity<ResponseDTO> getFgTransferSlipDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {
		String methodName = "getFgTransferSlipDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";
		try {
			mapp = purchaseOrderService.getFgTransferSlipDocId(orgId, financialYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FgTransferSlipDocId information retrieved successfully");
			responseObjectsMap.put("fgTransferSlipDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FgTransferSlipDocId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateFgTransferSlip")
	public ResponseEntity<ResponseDTO> createUpdateFgTransferSlip(@RequestBody FgTransferSlipDTO fgTransferSlipDTO) {
		String methodName = "createUpdateFgTransferSlip()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> fgTransferSlipVO = purchaseOrderService.createUpdateFgTransferSlip(fgTransferSlipDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, fgTransferSlipVO.get("message"));
			responseObjectsMap.put("fgTransferSlipVO", fgTransferSlipVO.get("fgTransferSlipVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFgTransferSlipByOrgId")
	public ResponseEntity<ResponseDTO> getFgTransferSlipByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getFgTransferSlipByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		try {
			List<FgTransferSlipResponseDTO> fgTransferSlipResponseDTO = purchaseOrderService
					.getFgTransferSlipByOrgId(orgId, branch);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FG Transfer Slip information retrieved successfully");
			responseObjectsMap.put("fgTransferSlipResponseVO", fgTransferSlipResponseDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FG Transfer Slip information retrieval failed", e.getMessage());
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	//

	@GetMapping("/getFgPartNoDetails")
	public ResponseEntity<ResponseDTO> getFgPartNoDetails(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getFgPartNoDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getFgPartNoDetails(orgId, branch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FgItem details retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FgItem details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSfgPartNoDetails")
	public ResponseEntity<ResponseDTO> getSfgPartNoDetails(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getSfgPartNoDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getSfgPartNoDetails(orgId, branch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SfgItem details retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SfgItem details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSchNoFromTransferSlip")
	public ResponseEntity<ResponseDTO> getSchNoFromTransferSlip(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam(required = false) Long fgItem, @RequestParam(required = false) Long sfgItem) {
		String methodName = "getFgAndSfgItemDetailsFromMaterial()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getSchNoFromTransferSlip(orgId, branch, fgItem, sfgItem);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SchNo details retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SchNo details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBomNoFromTransferSlip")
	public ResponseEntity<ResponseDTO> getBomNoFromTransferSlip(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam(required = false) Long fgItem, @RequestParam(required = false) Long sfgItem) {
		String methodName = "getBomNoFromTransferSlip()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getBomNoFromTransferSlip(orgId, branch, fgItem, sfgItem);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bom details retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Bom details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBomNoFromTransferSlipDetails")
	public ResponseEntity<ResponseDTO> getBomNoFromTransferSlipDetails(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long bom) {
		String methodName = "getBomNoFromTransferSlipDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseOrderService.getBomNoFromTransferSlipDetails(orgId, branch, bom);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bom details retrieved successfully");
			responseObjectsMap.put("mapp", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Bom details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBomFromFgTransferSlip")
	public ResponseEntity<ResponseDTO> getBomFromFgTransferSlip(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam Long fgItem) {

		String methodName = "getBomFromFgTransferSlip()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = purchaseOrderService.getBomFromFgTransferSlip(orgId, branch, fgItem);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bom details retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Bom details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSchNoFromFgTransferSlip")
	public ResponseEntity<ResponseDTO> getSchNoFromFgTransferSlip(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getSchNoFromFgTransferSlip()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = purchaseOrderService.getSchNoFromFgTransferSlip(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SchNo details retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SchNo details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getCustomersDetailsFromTransferSlip")
	public ResponseEntity<ResponseDTO> getCustomersDetailsFromTransferSlip(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getCustomersDetailsFromTransferSlip()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = purchaseOrderService.getCustomersDetailsFromTransferSlip(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer details retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Customer details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getBomDetailsFromFgTransferSlip")
	public ResponseEntity<ResponseDTO> getBomDetailsFromFgTransferSlip(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long bom) {

		String methodName = "getBomDetailsFromFgTransferSlip()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = purchaseOrderService.getBomDetailsFromFgTransferSlip(orgId, branch, bom);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bom details retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Bom details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);

	}

}