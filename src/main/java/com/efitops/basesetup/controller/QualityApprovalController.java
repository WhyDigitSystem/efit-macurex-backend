package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.SettingApprovalResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.InprocessImageResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SampleApprovalDTO;
import com.efitops.basesetup.dto.SampleImageResponseDTO;
import com.efitops.basesetup.dto.SettingApprovalDTO;
import com.efitops.basesetup.entity.SampleApprovalVO;
import com.efitops.basesetup.entity.SettingApprovalVO;
import com.efitops.basesetup.service.QualityApprovalServive;

@RestController
@RequestMapping("/api/qualityapproval")
public class QualityApprovalController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(QualityApprovalController.class);

	@Autowired
	QualityApprovalServive qualityApprovalServive;

	@GetMapping("/getAllSettingApprovalByOrgId")
	public ResponseEntity<ResponseDTO> getAllSettingApprovalByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSettingApprovalByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SettingApprovalVO> settingApprovalVO = new ArrayList<>();
		try {
			settingApprovalVO = qualityApprovalServive.getAllSettingApprovalByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"settingApproval information get successfully ByOrgId");
			responseObjectsMap.put("settingApprovalVO", settingApprovalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"settingApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSettingApprovalById")
	public ResponseEntity<ResponseDTO> getSettingApprovalById(@RequestParam Long id) {
		String methodName = "getSettingApprovalById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SettingApprovalVO settingApprovalVO = new SettingApprovalVO();
		try {
			settingApprovalVO = qualityApprovalServive.getSettingApprovalById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SettingApproval information get successfully By id");
			responseObjectsMap.put("settingApprovalVO", settingApprovalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SettingApproval information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSettingApprovalDocId")
	public ResponseEntity<ResponseDTO> getSettingApprovalDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getSettingApprovalDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityApprovalServive.getSettingApprovalDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SettingApprovalDocId information retrieved successfully");
			responseObjectsMap.put("settingApprovalDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SettingApprovalDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//	@PutMapping("/createUpdateSettingApproval")
//	public ResponseEntity<ResponseDTO> createUpdateSettingApproval(@RequestBody SettingApprovalDTO settingApprovalDTO) {
//		String methodName = "createUpdateSettingApproval()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		try {
//			Map<String, Object> settingApprovalVO = qualityApprovalServive
//					.createUpdateSettingApproval(settingApprovalDTO);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, settingApprovalVO.get("message"));
//			responseObjectsMap.put("settingApprovalVO", settingApprovalVO.get("settingApprovalVO"));
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

//	@PutMapping(value = "/createUpdateSettingApproval", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<ResponseDTO> createUpdateSettingApproval(
//			@RequestPart("data") SettingApprovalDTO settingApprovalDTO,
//			@RequestPart(value = "files", required = false) List<MultipartFile> files) {
//
//		String methodName = "createUpdateSettingApproval()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//
//		try {
//
//			Map<String, Object> settingApprovalVO = qualityApprovalServive
//					.createUpdateSettingApproval(settingApprovalDTO, files);
//
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, settingApprovalVO.get("message"));
//
//			responseObjectsMap.put("settingApprovalVO", settingApprovalVO.get("settingApprovalVO"));
//
//			responseDTO = createServiceResponse(responseObjectsMap);
//
//		} catch (Exception e) {
//
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//
//			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
	
	
	@PutMapping(value = "/createUpdateSettingApproval")
	public ResponseEntity<ResponseDTO> createUpdateSettingApproval(@RequestBody SettingApprovalDTO settingApprovalDTO) {

		String methodName = "createUpdateSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> settingApprovalVO = qualityApprovalServive
					.createUpdateSettingApproval(settingApprovalDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, settingApprovalVO.get("message"));

			responseObjectsMap.put("settingApprovalVO", settingApprovalVO.get("settingApprovalVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}


	@GetMapping("/getRouteCardDetailsForSettingApproval")
	public ResponseEntity<ResponseDTO> getRouteCardDetailsForSettingApproval(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getRouteCardDetailsForSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> routeCardDetails = new ArrayList<>();
		try {
			routeCardDetails = qualityApprovalServive.getRouteCardDetailsForSettingApproval(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"RouteCardDetails for SetingApproval information get successfully By OrgId");
			responseObjectsMap.put("routeCardDetails", routeCardDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"RouteCardDetails for SetingApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDrawingNoForSettingApproval")
	public ResponseEntity<ResponseDTO> getDrawingNoForSettingApproval(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String partNo) {
		String methodName = "getDrawingNoForSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> drawingNo = new ArrayList<>();
		try {
			drawingNo = qualityApprovalServive.getDrawingNoForSettingApproval(orgId, finYear, branchCode, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DrawingNo for SetingApproval information get successfully By OrgId");
			responseObjectsMap.put("drawingNo", drawingNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DrawingNo for SetingApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getMachineNoForSettingApproval")
	public ResponseEntity<ResponseDTO> getMachineNoForSettingApproval(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getMachineNoForSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> machineNo = new ArrayList<>();
		try {
			machineNo = qualityApprovalServive.getMachineNoForSettingApproval(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MachineNo for SettingApproval information get successfully By OrgId");
			responseObjectsMap.put("machineNo", machineNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MachineNo for SetingApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getOperatorNameForSettingApproval")
	public ResponseEntity<ResponseDTO> getOperatorNameForSettingApproval(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getOperatorNameForSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getOperatorNameForSettingApproval(orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"OperatorName Detail For SettingApproval retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve OperatorName Detail For SetingApproval", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSetterNameForSetingApproval")
	public ResponseEntity<ResponseDTO> getSetterNameForSettingApproval(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getSetterNameForSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getSetterNameForSettingApproval(orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SetterName Detail For SettingApproval retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SetterName Detail For SettingApproval", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getQualityNameForSettingApproval")
	public ResponseEntity<ResponseDTO> getQualityNameForSettingApproval(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getQualityNameForSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getQualityNameForSettingApproval(orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"QualityName Detail For SettingApproval retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve QualityName Detail For SettingApproval", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getShiftInChargeForSettingApproval")
	public ResponseEntity<ResponseDTO> getShiftInChargeForSettingApproval(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getShiftInChargeForSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getShiftInChargeForSettingApproval(orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ShiftInCharge Detail For SettingApproval retrieved successfully");
			responseObjectsMap.put("routeCardEntryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ShiftInCharge Detail For SettingApproval", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllSampleApprovalByOrgId")
	public ResponseEntity<ResponseDTO> getAllSampleApprovalByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSampleApprovalByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SampleApprovalVO> sampleApprovalVO = new ArrayList<>();
		try {
			sampleApprovalVO = qualityApprovalServive.getAllSampleApprovalByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SampleApproval information get successfully ByOrgId");
			responseObjectsMap.put("sampleApprovalVO", sampleApprovalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SampleApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSampleApprovalById")
	public ResponseEntity<ResponseDTO> getSampleApprovalById(@RequestParam Long id) {
		String methodName = "getSampleApprovalById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SampleApprovalVO sampleApprovalVO = new SampleApprovalVO();
		try {
			sampleApprovalVO = qualityApprovalServive.getSampleApprovalById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SampleApproval information get successfully By Id");
			responseObjectsMap.put("sampleApprovalVO", sampleApprovalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SampleApproval information receive failedBy Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSampleApprovalDocId")
	public ResponseEntity<ResponseDTO> getSampleApprovalDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getSampleApprovalDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityApprovalServive.getSampleApprovalDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SampleApprovalDocId information retrieved successfully");
			responseObjectsMap.put("sampleApprovalDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SampleApprovalDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateSampleApproval")
	public ResponseEntity<ResponseDTO> createUpdateSampleApproval(@RequestBody SampleApprovalDTO sampleApprovalDTO) {
		String methodName = "createUpdateSampleApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> sampleApprovalVO = qualityApprovalServive.createUpdateSampleApproval(sampleApprovalDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, sampleApprovalVO.get("message"));
			responseObjectsMap.put("sampleApprovalVO", sampleApprovalVO.get("sampleApprovalVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardDetailsForSampleApproval")
	public ResponseEntity<ResponseDTO> getRouteCardDetailsForSampleApproval(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getRouteCardDetailsForSampleApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> routeCardDetails = new ArrayList<>();
		try {
			routeCardDetails = qualityApprovalServive.getRouteCardDetailsForSampleApproval(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"RouteCardDetails for SampleApproval information get successfully By OrgId");
			responseObjectsMap.put("routeCardDetails", routeCardDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"RouteCardDetails for SampleApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDrawingMasterNoForSampleApproval")
	public ResponseEntity<ResponseDTO> getDrawingMasterNoForSampleApproval(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String partNo) {
		String methodName = "getDrawingMasterNoForSampleApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> drawingMasterNo = new ArrayList<>();
		try {
			drawingMasterNo = qualityApprovalServive.getDrawingMasterNoForSampleApproval(orgId, finYear, branchCode,
					partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DrawingMasterNo for SampleApproval information get successfully By OrgId");
			responseObjectsMap.put("drawingMasterNo", drawingMasterNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DrawingMasterNo for SampleApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getMachineNoForSampleApproval")
	public ResponseEntity<ResponseDTO> getMachineNoForSampleApproval(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getMachineNoForSampleApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> machineNo = new ArrayList<>();
		try {
			machineNo = qualityApprovalServive.getMachineNoForSampleApproval(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MachineNo for SampleApproval information get successfully By OrgId");
			responseObjectsMap.put("machineNo", machineNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MachineNo for SampleApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getJobOrderNoForSampleApproval")
	public ResponseEntity<ResponseDTO> getJobOrderNoForSampleApproval(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String routeCardNo,
			@RequestParam String operation) {
		String methodName = "getJobOrderNoForSampleApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> jobOrderNo = new ArrayList<>();
		try {
			jobOrderNo = qualityApprovalServive.getJobOrderNoForSampleApproval(orgId, finYear, branchCode, routeCardNo,
					operation);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"JobOrderNo for SampleApproval information get successfully By OrgId");
			responseObjectsMap.put("jobOrderNo", jobOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"JobOrderNo for SampleApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

//	//SettingApproval

	@GetMapping("/getSettingApprovalReport")
	public ResponseEntity<ResponseDTO> getSettingApprovalReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String routeCardNo) {
		String methodName = "getSettingApprovalReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> settingApproval = new ArrayList<>();
		try {
			settingApproval = qualityApprovalServive.getSettingApprovalReport(orgId, branchCode, fromDate, toDate,
					routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SettingApproval report information get successfully By OrgId");
			responseObjectsMap.put("settingApproval", settingApproval);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SettingApproval report information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSampleApprovalDetails")
	public ResponseEntity<ResponseDTO> getSampleApprovalDetails(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam String routeCardNo) {
		String methodName = "getSampleApprovalDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> sampleApprovalVO = new ArrayList<>();
		try {
			sampleApprovalVO = qualityApprovalServive.getSampleApprovalDetails(orgId, fromdate, todate, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sample Approval Details get successfully");
			responseObjectsMap.put("sampleApprovalVO", sampleApprovalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, " Sample Approval Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEmployeeNameBasedOnDesgnation")
	public ResponseEntity<ResponseDTO> getEmployeeNameBasedOnDesgnation(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getEmployeeNameBasedOnDesgnation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityApprovalServive.getEmployeeNameBasedOnDesgnation(orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmployeeName For Retrieved successfully");
			responseObjectsMap.put("employeeeNameVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve EmployeeName For EmployeeMaster", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/createUpdateSettingApproval", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateSettingApproval(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateSettingApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityApprovalServive.createUpdateSettingApproval(files, docId,
					screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("settingApprovalVO", serviceResponse.get("settingApprovalVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewFileSetting/**")
	public ResponseEntity<byte[]> viewFileSetting(HttpServletRequest request) throws IOException {
		return qualityApprovalServive.viewFileSetting(request);
	}

	@PostMapping(value = "/createUpdateSampleApproval", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateSampleApproval(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateSampleApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityApprovalServive.createUpdateSampleApproval(files, docId,
					screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("sampleApprovalVO", serviceResponse.get("sampleApprovalVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewFileSample/**")
	public ResponseEntity<byte[]> viewFileSample(HttpServletRequest request) throws IOException {
		return qualityApprovalServive.viewFileSample(request);
	}
	
	//SampleApproval  image attachment
	
			@GetMapping("/SampleApprovalImages/{id}")
			public ResponseEntity<List<SampleImageResponseDTO>> getSampleApprovalImages(@PathVariable Long id) throws Exception {

				List<SampleImageResponseDTO> response = qualityApprovalServive.getSampleApprovalImages(id);

				return ResponseEntity.ok(response);
			}
		
}
