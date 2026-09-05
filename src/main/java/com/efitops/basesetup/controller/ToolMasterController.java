package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.ToolMasterResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.EngineeringChangeRecordDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ToolMasterDTO;
import com.efitops.basesetup.service.ToolMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/toolmaster")

public class ToolMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);

	@Autowired
	ToolMasterService toolMasterService;

	@PostMapping(value = "/updateCreateToolMaster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseDTO updateCreateToolMaster(@RequestPart("toolMasterVO") ToolMasterDTO toolMasterDTO,
//			@RequestBody ToolMasterDTO toolMasterDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = toolMasterService.updateCreateToolMaster(toolMasterDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));
			responseObjectsMap.put("toolMasterVO", response.get("toolMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return responseDTO;
	}

	@GetMapping("/getToolMasterById")
	public ResponseEntity<ResponseDTO> getToolMasterById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			ToolMasterResponseDTO toolMasterResponseDTO = toolMasterService.getToolMasterById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tool Master Fetched Successfully");

			responseObjectsMap.put("toolMasterVO", toolMasterResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getToolMasterByOrgId")
	public ResponseEntity<ResponseDTO> getToolMasterByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<ToolMasterResponseDTO> toolMasterList = toolMasterService.getToolMasterByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tool Master Details Fetched Successfully");

			responseObjectsMap.put("toolMasterList", toolMasterList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

//	location dropdown

	@GetMapping("/getLocationForToolMaster")
	public ResponseEntity<ResponseDTO> getLocationForToolMaster(@RequestParam Long orgId, @RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> locationList = toolMasterService.getLocationForToolMaster(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Location Details Fetched Successfully");

			responseObjectsMap.put("locationList", locationList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

//	Enngineering change record

	@PostMapping(value = "/updateCreateEngineeringChangeRecord", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

	public ResponseDTO updateCreateEngineeringChangeRecord(

			@RequestPart("engineeringChangeRecordVO") EngineeringChangeRecordDTO engineeringChangeRecordDTO,

//	      @RequestBody EngineeringChangeRecordDTO engineeringChangeRecordDTO,

			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = toolMasterService
					.updateCreateEngineeringChangeRecord(engineeringChangeRecordDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));
			responseObjectsMap.put("engineeringChangeRecordVO", response.get("engineeringChangeRecordVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return responseDTO;

	}

	@GetMapping("/getEngineeringChangeRecordByOrgId")
	public ResponseDTO getEngineeringChangeRecordByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = toolMasterService.getEngineeringChangeRecordByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Success");

			responseObjectsMap.put("engineeringChangeRecordVO", response.get("engineeringChangeRecordVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return responseDTO;
	}

	@GetMapping("/getEngineeringChangeRecordById")
	public ResponseDTO getEngineeringChangeRecordById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = toolMasterService.getEngineeringChangeRecordById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Success");
			responseObjectsMap.put("engineeringChangeRecordVO", response.get("engineeringChangeRecordVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return responseDTO;
	}

	@GetMapping("/getEngineeringChangeRecordDocId")
	public ResponseEntity<ResponseDTO> getEngineeringChangeRecordDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getEngineeringChangeRecordDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = toolMasterService.getEngineeringChangeRecordDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Engineering Change Record DocId information retrieved successfully");

			responseObjectsMap.put("engineeringChangeRecordDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Engineering Change Record DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

}
