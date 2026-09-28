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

import com.efitops.basesetup.ResponseDTO.PreDeliveryInspectionResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionEntryResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PreDeliveryInspectionDTO;
import com.efitops.basesetup.dto.ProductionEntryDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.ProductionEntryService;

@RestController
@RequestMapping("/api/productionEntry")
public class ProductionEntryController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProductionEntryController.class);

	@Autowired
	private ProductionEntryService productionEntryService;

	@GetMapping("/getProductionEntryById")
	public ResponseEntity<ResponseDTO> getProductionEntryById(@RequestParam Long id) {

		String methodName = "getProductionEntryById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			ProductionEntryResponseDTO productionEntryResponseDTO = productionEntryService.getProductionEntryById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Entry information retrieved successfully");

			responseObjectsMap.put("productionEntryResponseVO", productionEntryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Production Entry retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getProductionEntryDocId")
	public ResponseEntity<ResponseDTO> getProductionEntryDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getProductionEntryDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = productionEntryService.getProductionEntryDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Entry DocId information retrieved successfully");

			responseObjectsMap.put("productionEntryDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Production Entry DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateProductionEntry")
	public ResponseEntity<ResponseDTO> createUpdateProductionEntry(@RequestBody ProductionEntryDTO productionEntryDTO) {

		String methodName = "createUpdateProductionEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> productionEntryVO = productionEntryService
					.createUpdateProductionEntry(productionEntryDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, productionEntryVO.get("message"));

			responseObjectsMap.put("productionEntryVO", productionEntryVO.get("productionEntryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProductionEntryByOrgId")
	public ResponseEntity<ResponseDTO> getProductionEntryByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getProductionEntryByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<ProductionEntryResponseDTO> productionEntryResponseDTO = productionEntryService
					.getProductionEntryByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Entry information retrieved successfully");

			responseObjectsMap.put("productionEntryResponseVO", productionEntryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Production Entry information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSchNoFromProductionEntry")
	public ResponseEntity<ResponseDTO> getSchNoFromProductionEntry(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam Long fgItem) {

		String methodName = "getSchNoFromProductionEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = productionEntryService.getSchNoFromProductionEntry(orgId, branch, fgItem);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FgItem retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FgItem", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBomNoFromProductionEntry")
	public ResponseEntity<ResponseDTO> getBomNoFromProductionEntry(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getBomNoFromProductionEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = productionEntryService.getBomNoFromProductionEntry(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "bomNo retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve bomNo", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	// Macures

	@GetMapping("/getPreDeliveryInspectionById")
	public ResponseEntity<ResponseDTO> getPreDeliveryInspectionById(@RequestParam Long id) {

		String methodName = "getPreDeliveryInspectionById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			PreDeliveryInspectionResponseDTO preDeliveryInspectionResponseDTO = productionEntryService
					.getPreDeliveryInspectionById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Pre Delivery Inspection information retrieved successfully");

			responseObjectsMap.put("preDeliveryInspectionResponseVO", preDeliveryInspectionResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Pre Delivery Inspection retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPreDeliveryInspectionDocId")
	public ResponseEntity<ResponseDTO> getPreDeliveryInspectionDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getPreDeliveryInspectionDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = productionEntryService.getPreDeliveryInspectionDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Pre Delivery Inspection DocId information retrieved successfully");

			responseObjectsMap.put("preDeliveryInspectionDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Pre Delivery Inspection DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdatePreDeliveryInspection")
	public ResponseEntity<ResponseDTO> createUpdatePreDeliveryInspection(
			@RequestBody PreDeliveryInspectionDTO preDeliveryInspectionDTO) {

		String methodName = "createUpdatePreDeliveryInspection()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> preDeliveryInspectionVO = productionEntryService
					.createUpdatePreDeliveryInspection(preDeliveryInspectionDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, preDeliveryInspectionVO.get("message"));

			responseObjectsMap.put("preDeliveryInspectionVO", preDeliveryInspectionVO.get("preDeliveryInspectionVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPreDeliveryInspectionByOrgId")
	public ResponseEntity<ResponseDTO> getPreDeliveryInspectionByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getPreDeliveryInspectionByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<PreDeliveryInspectionResponseDTO> preDeliveryInspectionResponseDTO = productionEntryService
					.getPreDeliveryInspectionByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Pre Delivery Inspection information retrieved successfully");

			responseObjectsMap.put("preDeliveryInspectionResponseVO", preDeliveryInspectionResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Pre Delivery Inspection information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getFgTransferSlipNo")
	public ResponseEntity<ResponseDTO> getFgTransferSlipNo(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getFgTransferSlipNo()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = productionEntryService.getFgTransferSlipNo(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FgTransferSlipNo retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FgTransferSlipNo",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsFromFgTransferSlipNo")
	public ResponseEntity<ResponseDTO> getItemDetailsFromFgTransferSlipNo(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam String transferSlipNo) {

		String methodName = "getItemDetailsFromFgTransferSlipNo()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = productionEntryService.getItemDetailsFromFgTransferSlipNo(orgId, branch, transferSlipNo);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Item details from FgTransferSlipNo retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve item details from FgTransferSlipNo", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInitialPlanningNo")
	public ResponseEntity<ResponseDTO> getInitialPlanningNo(@RequestParam Long orgId, @RequestParam Long item) {

		String methodName = "getInitialPlanningNo()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = productionEntryService.getInitialPlanningNo(orgId, item);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InitialPlanningNo retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve InitialPlanningNo",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInspectionDetailsFromFgTransferSlipNo")
	public ResponseEntity<ResponseDTO> getInspectionDetailsFromFgTransferSlipNo(@RequestParam Long orgId,
			@RequestParam Long item, @RequestParam String transferSlipNo) {

		String methodName = "getInspectionDetailsFromFgTransferSlipNo()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mapp = new ArrayList<>();

		try {

			mapp = productionEntryService.getInspectionDetailsFromFgTransferSlipNo(orgId, item, transferSlipNo);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Inspection details from FgTransferSlipNo retrieved successfully");

			responseObjectsMap.put("mapp", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve inspection details from FgTransferSlipNo", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

}
