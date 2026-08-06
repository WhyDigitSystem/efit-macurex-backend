package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.OrderAcceptanceDTO;
import com.efitops.basesetup.dto.OrderAcceptanceResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseResponseDTO;
import com.efitops.basesetup.service.OrderAcceptanceService;

@RestController
@RequestMapping("/api/orderAcceptance")
public class OrderAcceptanceContoller extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(OrderAcceptanceContoller.class);

	@Autowired
	OrderAcceptanceService orderAcceptanceService;

	@GetMapping("/getOrderAcceptanceById")
	public ResponseEntity<ResponseDTO> getOrderAcceptanceById(@RequestParam Long id) {

		String methodName = "getOrderAcceptanceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			OrderAcceptanceResponseDTO orderAcceptanceResponseDTO = orderAcceptanceService.getOrderAcceptanceById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation information retrieved successfully");

			responseObjectsMap.put("orderAcceptanceResponseVO", orderAcceptanceResponseDTO);

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

	@GetMapping("/getOrderAcceptanceByOrgId")
	public ResponseEntity<ResponseDTO> getOrderAcceptanceByOrgId(@RequestParam Long orgId,
			@RequestParam Long branchId) {

		String methodName = "getOrderAcceptanceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<OrderAcceptanceResponseDTO> orderAcceptanceResponseDTO = orderAcceptanceService
					.getOrderAcceptanceByOrgId(orgId, branchId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Order information retrieved successfully");

			responseObjectsMap.put("orderAcceptanceResponseVO", orderAcceptanceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Order information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdateOrderAcceptance", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateOrderAcceptance(
			@RequestPart("orderAcceptance") OrderAcceptanceDTO orderAcceptanceDTO,
//			@RequestBody OrderAcceptanceDTO orderAcceptanceDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> quotationMap = orderAcceptanceService.createUpdateOrderAcceptance(orderAcceptanceDTO,
					files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, quotationMap.get("message"));

			responseObjectsMap.put("orderAcceptanceVO", quotationMap.get("orderAcceptanceVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);

	}

	// SalesOrder

	@GetMapping("/getSalesOrderShortCloseById")
	public ResponseEntity<ResponseDTO> getSalesOrderShortCloseById(@RequestParam Long id) {

		String methodName = "getSalesOrderShortCloseById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			SalesOrderShortCloseResponseDTO salesOrderShortCloseResponseDTO = orderAcceptanceService
					.getSalesOrderShortCloseById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation information retrieved successfully");

			responseObjectsMap.put("salesOrderShortCloseResponseVO", salesOrderShortCloseResponseDTO);

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

	@GetMapping("/getSalesOrderShortCloseByOrgId")
	public ResponseEntity<ResponseDTO> getSalesOrderShortCloseByOrgId(@RequestParam Long orgId,
			@RequestParam Long branchId) {

		String methodName = "getSalesOrderShortCloseByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<SalesOrderShortCloseResponseDTO> salesOrderShortCloseResponseDTO = orderAcceptanceService
					.getSalesOrderShortCloseByOrgId(orgId, branchId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Order information retrieved successfully");

			responseObjectsMap.put("salesOrderShortCloseResponseDTO", salesOrderShortCloseResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Order information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdateSalesOrderShort", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateSalesOrderShort(
			@RequestPart("salesOrderShort") SalesOrderShortCloseDTO salesOrderShortCloseDTO,
//			@RequestBody SalesOrderShortCloseDTO salesOrderShortCloseDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> quotationMap = orderAcceptanceService
					.createUpdateSalesOrderShort(salesOrderShortCloseDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, quotationMap.get("message"));

			responseObjectsMap.put("salesOrderShortCloseVO", quotationMap.get("salesOrderShortCloseVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);

	}

}
