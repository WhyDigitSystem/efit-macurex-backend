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

import com.efitops.basesetup.ResponseDTO.GrnResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.GrnService;

@RestController
@RequestMapping("/api/grn")
public class GrnServiceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(GrnServiceController.class);

	@Autowired
	private GrnService grnService;

	@GetMapping("/getGrnByOrgId")
	public ResponseEntity<ResponseDTO> getGrnByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getGrnByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<GrnResponseDTO> grnList = grnService.getGrnByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GRN retrieved successfully");
			responseObjectsMap.put("grnVO", grnList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "GRN retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getGrnById")
	public ResponseEntity<ResponseDTO> getGrnById(@RequestParam Long id) {
		String methodName = "getGrnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			GrnResponseDTO grnResponse = grnService.getGrnById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GRN retrieved successfully");
			responseObjectsMap.put("grnVO", grnResponse);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "GRN retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdateGrn", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateGrn(
			@RequestPart("grn") GrnDTO grnDTO,
//			@RequestBody GrnDTO grnDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		String methodName = "createUpdateGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> grnMap = grnService.createUpdateGrn(grnDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, grnMap.get("message"));
			responseObjectsMap.put("grnVO", grnMap.get("grnVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "GRN creation/update failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/viewFile/**")
	public ResponseEntity<byte[]> viewGrnFile(HttpServletRequest request) {
		String methodName = "viewGrnFile()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			return grnService.viewGrnFile(request);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/getSupplierDetailsForGrn")
	public ResponseEntity<ResponseDTO> getSupplierDetailsForGrn(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getSupplierDetailsForGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = grnService.getSupplierDetailsForGrn(orgId, branch);
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

	@GetMapping("/getGrnDocId")
	public ResponseEntity<ResponseDTO> getGrnDocId(@RequestParam Long orgId, @RequestParam String financialYear,
			@RequestParam String screenCode) {

		String methodName = "getGrnDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = grnService.getGrnDocId(orgId, financialYear, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocId information retrieved successfully");
			responseObjectsMap.put("grnDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
