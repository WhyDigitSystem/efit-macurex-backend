package com.efitops.basesetup.controller;

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

import com.efitops.basesetup.ResponseDTO.PurchaseReturnResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.PurchaseReturnService;

@RestController
@RequestMapping("/api/purchasereturn")
public class PurchaseReturnContoller extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseReturnContoller.class);

	@Autowired
	private PurchaseReturnService purchaseReturnService;

	@GetMapping("/getPurchaseReturnById")
	public ResponseEntity<ResponseDTO> getPurchaseReturnById(@RequestParam Long id) {

		String methodName = "getPurchaseReturnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			PurchaseReturnResponseDTO purchaseReturnResponseDTO = purchaseReturnService.getPurchaseReturnById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Return retrieved successfully");

			responseObjectsMap.put("purchaseReturn", purchaseReturnResponseDTO);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Purchase Return",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	/**
	 * Get Purchase Return By OrgId and Branch
	 */
	@GetMapping("/getPurchaseReturnByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseReturnByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getPurchaseReturnByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			List<PurchaseReturnResponseDTO> purchaseReturnList = purchaseReturnService.getPurchaseReturnByOrgId(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Return retrieved successfully");

			responseObjectsMap.put("purchaseReturn", purchaseReturnList);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Purchase Return",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdatePurchaseReturn")
	public ResponseEntity<ResponseDTO> createUpdatePurchaseReturn(@RequestBody PurchaseReturnDTO purchaseReturnDTO) {

		String methodName = "createUpdatePurchaseReturn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> response = purchaseReturnService.createUpdatePurchaseReturn(purchaseReturnDTO);

			responseObjectsMap.putAll(response);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to create/update Purchase Return",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseReturnDocId")
	public ResponseEntity<ResponseDTO> getPurchaseReturnDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getPurchaseReturnDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseReturnService.getPurchaseReturnDocId(orgId, financialYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocId information retrieved successfully");
			responseObjectsMap.put("purchaseReturnId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
