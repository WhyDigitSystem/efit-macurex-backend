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

import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.VendorComplaintEntryDTO;
import com.efitops.basesetup.service.VendorComplaintService;

@CrossOrigin
@RestController
@RequestMapping("/api/vendorComplaintEntry")

public class VendorComplaintController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InitialPlanningController.class);

	@Autowired
	VendorComplaintService vendorComplaintService;

	@PutMapping("/updateCreateVendorComplaintEntry")
	public ResponseEntity<ResponseDTO> updateCreateVendorComplaintEntry(
			@RequestBody VendorComplaintEntryDTO vendorComplaintEntryDTO) {

		String methodName = "updateCreateVendorComplaintEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = vendorComplaintService
					.updateCreateVendorComplaintEntry(vendorComplaintEntryDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("vendorComplaintEntryVO", responseMap.get("vendorComplaintEntryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getVendorComplaintEntryById")
	public ResponseEntity<ResponseDTO> getVendorComplaintEntryById(@RequestParam Long id) {

		String methodName = "getVendorComplaintEntryById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		VendorComplaintEntryResponseDTO response = null;

		try {

			response = vendorComplaintService.getVendorComplaintEntryById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Vendor Complaint Entry information retrieved successfully");

			responseObjectsMap.put("vendorComplaintEntryVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Vendor Complaint Entry information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getVendorComplaintEntryByOrgId")
	public ResponseEntity<ResponseDTO> getVendorComplaintEntryByOrgId(@RequestParam Long orgId) {

		String methodName = "getVendorComplaintEntryByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<VendorComplaintEntryResponseDTO> responseList = new ArrayList<>();

		try {

			responseList = vendorComplaintService.getVendorComplaintEntryByOrgId(orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Vendor Complaint Entry information retrieved successfully");

			responseObjectsMap.put("vendorComplaintEntryVO", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Vendor Complaint Entry information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getVendorComplaintEntryDocId")
	public ResponseEntity<ResponseDTO> getVendorComplaintEntryDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getVendorComplaintEntryDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String response = null;

		try {

			response = vendorComplaintService.getVendorComplaintEntryDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Vendor Complaint Entry DocId retrieved successfully");

			responseObjectsMap.put("docId", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Vendor Complaint Entry DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFgItemDropdownForVendorComplaintEntry")
	public ResponseEntity<ResponseDTO> getFgItemDropdownForVendorComplaintEntry(@RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getFgItemDropdownForVendorComplaintEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		Map<String, Object> response = null;

		try {

			response = vendorComplaintService.getFgItemDropdownForVendorComplaintEntry(branch, orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FG Item information retrieved successfully");

			responseObjectsMap.put("itemList", response.get("itemList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FG Item information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDropdownForVendorComplaintEntry")
	public ResponseEntity<ResponseDTO> getItemDropdownForVendorComplaintEntry(@RequestParam Long supplier,
			@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getItemDropdownForVendorComplaintEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		Map<String, Object> response = null;

		try {

			response = vendorComplaintService.getItemDropdownForVendorComplaintEntry(supplier, branch, orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information retrieved successfully");

			responseObjectsMap.put("itemList", response.get("itemList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Item information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
}
