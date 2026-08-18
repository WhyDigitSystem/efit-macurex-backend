package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
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
	public ResponseEntity<ResponseDTO> getPurchaseOrderById(@RequestParam Long id, @RequestParam String type) {

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

}