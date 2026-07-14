package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ToolIssueEntryDTO;
import com.efitops.basesetup.dto.ToolIssueEntryImageResponseDTO;
import com.efitops.basesetup.dto.ToolRecieveFromCalibrationDTO;
import com.efitops.basesetup.dto.ToolRecieveFromCalibrationDetailsDTO;
import com.efitops.basesetup.dto.ToolsIssueToCalibrationDTO;
import com.efitops.basesetup.entity.ToolIssueEntryVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;
import com.efitops.basesetup.service.ToolIssueEntryService;

@CrossOrigin
@RestController
@RequestMapping("/api/toolmanagement")
public class ToolIssueEntryController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(ToolIssueEntryController.class);

	@Autowired
	ToolIssueEntryService toolIssueEntryService;

	@GetMapping("/getToolIssueEntryByOrgId")
	public ResponseEntity<ResponseDTO> getToolIssueEntryByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getToolIssueEntryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ToolIssueEntryVO> toolIssueEntryVO = new ArrayList<>();
		try {
			toolIssueEntryVO = toolIssueEntryService.getToolIssueEntryByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ToolIssueEntry information get successfully ByOrgId");
			responseObjectsMap.put("toolIssueEntryVO", toolIssueEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ToolIssueEntry information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getToolIssueEntryById")
	public ResponseEntity<ResponseDTO> getToolIssueEntryById(@RequestParam Long id) {
		String methodName = "getToolIssueEntryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ToolIssueEntryVO> toolIssueEntryVO = new ArrayList<>();
		try {
			toolIssueEntryVO = toolIssueEntryService.getToolIssueEntryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully By Id");
			responseObjectsMap.put("toolIssueEntryVO", toolIssueEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateToolIssueEntry")
	public ResponseEntity<ResponseDTO> updateCreateToolIssueEntry(@RequestBody ToolIssueEntryDTO toolIssueEntryDTO) {
		String methodName = "updateCreateToolIssueEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> toolIssueEntryVO = toolIssueEntryService.updateCreateToolIssueEntry(toolIssueEntryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, toolIssueEntryVO.get("message"));
			responseObjectsMap.put("toolIssueEntryVO", toolIssueEntryVO.get("toolIssueEntryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getToolIssueEntryDocId")
	public ResponseEntity<ResponseDTO> getToolIssueEntryDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getToolIssueEntryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = toolIssueEntryService.getToolIssueEntryDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ToolIssueEntryDocId information retrieved successfully");
			responseObjectsMap.put("toolIssueEntryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ToolIssueEntryDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInstrumentforTollIssueForEntry")
	public ResponseEntity<ResponseDTO> getInstrumentforTollIssueForEntry(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getInstrumentforTollIssueForEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> inwardforgrn = new ArrayList<>();
		try {
			inwardforgrn = toolIssueEntryService.getInstrumentforTollIssueForEntry(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Instrumentname for Grn information get successfully By OrgId");
			responseObjectsMap.put("instrumentDetails", inwardforgrn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Instrumentname for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getCustomerNameforTollIssueForEntry")
	public ResponseEntity<ResponseDTO> getCustomerNameforTollIssueForEntry(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getCustomerNameforTollIssueForEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> customerName = new ArrayList<>();
		try {
			customerName = toolIssueEntryService.getCustomerNameforTollIssueForEntry(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" customerName information get successfully By OrgId");
			responseObjectsMap.put("customerName", customerName);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"customerName information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getlastcountforTollIssueForEntry")
	public ResponseEntity<ResponseDTO> getlastcountforTollIssueForEntry(@RequestParam(required = false) Long orgId) {
		String methodName = "getlastcountforTollIssueForEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> toolissueforentry = new ArrayList<>();
		try {
			toolissueforentry = toolIssueEntryService.getlastcountforTollIssueForEntry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Lastcount for Grn information get successfully By OrgId");
			responseObjectsMap.put("toolissueforentry", toolissueforentry);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Lastcount for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// ToolsIssueToCalibration

	@GetMapping("/getToolsIssueToCalibrationByOrgId")
	public ResponseEntity<ResponseDTO> getToolsIssueToCalibrationByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getToolsIssueToCalibrationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ToolsIssueToCalibrationVO> toolsIssueToCalibrationVO = new ArrayList<>();
		try {
			toolsIssueToCalibrationVO = toolIssueEntryService.getToolsIssueToCalibrationByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Tool Issue to Calibration information get successfully ByOrgId");
			responseObjectsMap.put("toolsIssueToCalibrationVO", toolsIssueToCalibrationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Tool Issue to Calibration information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getToolsIssueToCalibrationById")
	public ResponseEntity<ResponseDTO> getToolsIssueToCalibrationById(@RequestParam Long id) {
		String methodName = "getToolsIssueToCalibrationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ToolsIssueToCalibrationVO toolsIssueToCalibrationVO = new ToolsIssueToCalibrationVO();
		try {
			toolsIssueToCalibrationVO = toolIssueEntryService.getToolsIssueToCalibrationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Tool Issue to Calibration information get successfully By Id");
			responseObjectsMap.put("toolsIssueToCalibrationVO", toolsIssueToCalibrationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Tool Issue to Calibration information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateToolsIssueToCalibration")
	public ResponseEntity<ResponseDTO> updateCreateToolsIssueToCalibration(
			@RequestBody @Valid ToolsIssueToCalibrationDTO toolsIssueToCalibrationDTO) {

		String methodName = "updateCreateToolsIssueToCalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> serviceResponse = toolIssueEntryService
					.updateCreateToolsIssueToCalibration(toolsIssueToCalibrationDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, serviceResponse.get("message"));

			responseObjectsMap.put("toolsIssueToCalibrationVO", serviceResponse.get("toolsIssueToCalibrationVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			String errorMsg = (e.getMessage() != null) ? e.getMessage() : "Unexpected Error Occurred";

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getToolsIssueToCalibrationDocId")
	public ResponseEntity<ResponseDTO> getToolsIssueToCalibrationDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getToolsIssueToCalibrationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = toolIssueEntryService.getToolsIssueToCalibrationDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ToolsIssueToCalibrationDocId information retrieved successfully");
			responseObjectsMap.put("toolsIssueToCalibrationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ToolsIssueToCalibrationDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInstrumentdetforToolIssueForcalibration")
	public ResponseEntity<ResponseDTO> getInstrumentdetforToolIssueForcalibration(
			@RequestParam(required = false) Long orgId, @RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getInstrumentdetforToolIssueForcalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chcode = new ArrayList<>();
		try {
			chcode = toolIssueEntryService.getInstrumentdetforToolIssueForcalibration(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Instrumentname for calibration information get successfully By OrgId");
			responseObjectsMap.put("chcode", chcode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Instrumentname for calibration information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPartyMasterDetailsforToolIssueForcalibration")
	public ResponseEntity<ResponseDTO> getPartyMasterDetailsforToolIssueForcalibration(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getPartyMasterDetailsforToolIssueForcalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> partyMaster = new ArrayList<>();
		try {
			partyMaster = toolIssueEntryService.getPartyMasterDetailsforToolIssueForcalibration(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" PartyMaster for calibration information get successfully By OrgId");
			responseObjectsMap.put("partyMasterVO", partyMaster);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PartyMaster for calibration information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// ToolsRecieveFromCalibration

	@GetMapping("/getToolsRecieveFromCalibrationByOrgId")
	public ResponseEntity<ResponseDTO> getToolsRecieveFromCalibrationByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getToolsRecieveFromCalibrationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ToolRecieveFromCalibrationVO> toolRecieveFromCalibrationVO = new ArrayList<>();
		try {
			toolRecieveFromCalibrationVO = toolIssueEntryService.getToolsRecieveFromCalibrationByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Tool Recieve From Calibration information get successfully ByOrgId");
			responseObjectsMap.put("toolRecieveFromCalibrationVO", toolRecieveFromCalibrationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Tool Recieve From Calibration information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getToolsRecieveFromCalibrationById")
	public ResponseEntity<ResponseDTO> getToolsRecieveFromCalibrationById(@RequestParam Long id) {
		String methodName = "getToolsRecieveFromCalibrationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ToolRecieveFromCalibrationVO toolRecieveFromCalibrationVO = new ToolRecieveFromCalibrationVO();
		try {
			toolRecieveFromCalibrationVO = toolIssueEntryService.getToolsRecieveFromCalibrationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Tool Recieve From  Calibration information get successfully By Id");
			responseObjectsMap.put("toolRecieveFromCalibrationVO", toolRecieveFromCalibrationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Tool Recieve From  Calibration information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateToolsRecieveFromCalibration")
	public ResponseEntity<ResponseDTO> updateCreateToolsRecieveFromCalibration(
			@RequestBody @Valid ToolRecieveFromCalibrationDTO dto) {

		String methodName = "updateCreateToolsRecieveFromCalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> result = toolIssueEntryService.updateCreateToolsRecieveFromCalibration(dto);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
			responseObjectsMap.put("toolRecieveFromCalibrationVO", result.get("toolRecieveFromCalibrationVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			String errorMsg = (e.getMessage() != null) ? e.getMessage() : "Unexpected Error Occurred";

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getToolsRecieveFromCalibrationDocId")
	public ResponseEntity<ResponseDTO> getToolsRecieveFromCalibrationDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getEnquiryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = toolIssueEntryService.getToolsRecieveFromCalibrationDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ToolsRecieveFromCalibrationDocId information retrieved successfully");
			responseObjectsMap.put("toolsRecieveFromCalibrationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ToolsRecieveFromCalibrationDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIssueDetailsforToolIssueNoForRecieveFormCalibration")
	public ResponseEntity<ResponseDTO> getIssueDetailsforToolIssueNoForRecieveFormCalibration(@RequestParam Long orgId,
			@RequestParam String finyear, @RequestParam String branchCode) {
		String methodName = "getIssueDetailsforToolIssueNoForRecieveFormCalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chcode = new ArrayList<>();
		try {
			chcode = toolIssueEntryService.getIssueDetailsforToolIssueNoForRecieveFormCalibration(orgId, finyear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Instrument issue details for calibration information get successfully By OrgId");
			responseObjectsMap.put("chcode", chcode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Instrument issue details for calibration information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getInstrumentdetforToolIssueNoForinstrumentRecieveFormCalibration")
	public ResponseEntity<ResponseDTO> getInstrumentdetforToolIssueNoForinstrumentRecieveFormCalibration(
			@RequestParam Long orgId, @RequestParam String finYear, @RequestParam String branchCode,
			@RequestParam String issueNo) {
		String methodName = "getInstrumentdetforToolIssueNoForinstrumentRecieveFormCalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chcode = new ArrayList<>();
		try {
			chcode = toolIssueEntryService.getInstrumentdetforToolIssueNoForinstrumentRecieveFormCalibration(orgId,
					finYear, branchCode, issueNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Instrument name issue details for calibration information get successfully By OrgId");
			responseObjectsMap.put("chcode", chcode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Instrument name issue details for calibration information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

//	@PostMapping("/uploadFileForToolReciveFromcalibcertification")
//	public ResponseEntity<ResponseDTO> uploadFileForToolReciveFromcalibcertification(
//			@RequestParam("file") MultipartFile file, @RequestParam Long id) {
//		String methodName = "uploadFileForToolReciveFromcalibcertification()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		ToolRecieveFromCalibrationDetailsVO toolRecieveFromCalibrationDetailsVO = null;
//		try {
//			toolRecieveFromCalibrationDetailsVO = toolIssueEntryService
//					.uploadFileForToolReciveFromcalibcertification(file, id);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error("Unable To Upload File", methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"FileUpload for Tool Recieve From Calibration Details attachment Successfully Upload");
//			responseObjectsMap.put("toolRecieveFromCalibrationDetailsVO", toolRecieveFromCalibrationDetailsVO);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"FileUpload for Tool Recieve From Calibration Details attachment Upload Failed", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

	@GetMapping("/getToolsIssueEntryInstrumentCodeDesc")
	public ResponseEntity<ResponseDTO> getToolsIssueEntryInstrumentCodeDesc(
			@RequestParam(required = false) Long orgId) {
		String methodName = "getToolsIssueEntryInstrumentCodeDesc()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> toolsIssueEntry = new ArrayList<>();
		try {
			toolsIssueEntry = toolIssueEntryService.getToolsIssueEntryInstrumentCodeDesc(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" InstrumentcodeDesc for calibration information get successfully By OrgId");
			responseObjectsMap.put("toolsIssueEntry", toolsIssueEntry);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"InstrumentcodeDesc for calibration information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping(value = "/uploadFilesForCalibrationDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> uploadFilesForCalibrationDetails(
			@RequestParam("toolRecieveFromCalibrationId") Long toolRecieveFromCalibrationId,
			@RequestParam("detailsId") Long detailsId, @RequestParam("files") MultipartFile[] files)
			throws IOException {

		ToolRecieveFromCalibrationDetailsDTO dto = new ToolRecieveFromCalibrationDetailsDTO();
		dto.setFiles(files);

		Map<String, Object> response = toolIssueEntryService
				.uploadFilesForCalibrationDetails(toolRecieveFromCalibrationId, detailsId, dto);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getToolRecieveFromCalibration")
	public ResponseEntity<ResponseDTO> getToolRecieveFromCalibration(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate) {
		String methodName = "getToolRecieveFromCalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chcode = new ArrayList<>();
		try {
			chcode = toolIssueEntryService.getToolRecieveFromCalibration(orgId, fromdate, todate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RecieveFromCalibration  get successfully");
			responseObjectsMap.put("chcode", chcode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RecieveFromCalibration receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemNameAndDesc")
	public ResponseEntity<ResponseDTO> getItemNameAndDesc(@RequestParam Long orgId) {
		String methodName = "getItemNameAndDesc()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> refcode = new ArrayList<>();
		try {
			refcode = toolIssueEntryService.getItemNameAndDesc(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "item information get successfully ");
			responseObjectsMap.put("refcode", refcode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "item information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getToolIssueToCalibrationReport")
	public ResponseEntity<ResponseDTO> getToolIssueToCalibrationReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam String issuepartyname) {
		String methodName = "getToolIssueToCalibrationReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> refcode = new ArrayList<>();
		try {
			refcode = toolIssueEntryService.getToolIssueToCalibrationReport(orgId, fromdate, todate, issuepartyname);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Toolsissue to calibration information get successfully ByorgId");
			responseObjectsMap.put("refcode", refcode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Toolsissue to calibration information receive failed ByorgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInstrumentCodeAndName")
	public ResponseEntity<ResponseDTO> getInstrumentCodeAndName(@RequestParam Long orgId) {
		String methodName = "getInstrumentCodeAndName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> instrumentCodeAndName = new ArrayList<>();
		try {
			instrumentCodeAndName = toolIssueEntryService.getInstrumentCodeAndName(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "item information get successfully ");
			responseObjectsMap.put("instrumentCodeAndName", instrumentCodeAndName);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "item information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTollIssueForEntryReport")
	public ResponseEntity<ResponseDTO> getTollIssueForEntryReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam String instrumentCodeAndName) {
		String methodName = "getTollIssueForEntryReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> toolIssueEntryVO = new ArrayList<>();
		try {
			toolIssueEntryVO = toolIssueEntryService.getTollIssueForEntryReport(orgId, fromDate, toDate,
					instrumentCodeAndName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " Toll issue Entry Report get successfully");
			responseObjectsMap.put("toolIssueEntryVO", toolIssueEntryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Toll issue Entry Report failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/uploadToolRecieveFromCalibrationImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadToolRecieveFromCalibrationImages(@RequestParam("toolRecieveId") Long toolRecieveId,
			@RequestParam("files") List<MultipartFile> files) throws IOException {

		return ResponseEntity.ok(toolIssueEntryService.uploadToolRecieveFromCalibrationImages(toolRecieveId, files));
	}

	@GetMapping("/viewToolRecieveFromCalibrationImage")
	public ResponseEntity<?> viewToolRecieveFromCalibrationImage(@RequestParam Long imageId) {

		String methodName = "viewToolRecieveFromCalibrationImage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		String errorMsg = null;

		try {

			byte[] img = toolIssueEntryService.viewToolRecieveFromCalibrationImage(imageId);

			if (img == null) {
				return ResponseEntity.notFound().build();
			}

			// Get Dynamic Content Type
			String contentType = toolIssueEntryService.getImageFileType(imageId);

			if (contentType == null) {
				contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
			}

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(img);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);

			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@PostMapping(value = "/createUpdateToolsIssueToCalibration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateToolsIssueToCalibration(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateToolsIssueToCalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = toolIssueEntryService.createUpdateToolsIssueToCalibration(files,
					docId, screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("toolsIssueToCalibrationVO", serviceResponse.get("toolsIssueToCalibrationVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/ViewToolsIssueEntry/**")
	public ResponseEntity<byte[]> ViewToolsIssueEntry(HttpServletRequest request) throws IOException {
		return toolIssueEntryService.ViewToolsIssueEntry(request);
	}

	@GetMapping("/ViewToolIssueToCalibration/**")
	public ResponseEntity<byte[]> ViewToolIssueToCalibration(HttpServletRequest request) throws IOException {
		return toolIssueEntryService.ViewToolIssueToCalibration(request);
	}

	@PostMapping(value = "/createUpdateToolIssueEntry", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateToolIssueEntry(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateToolIssueEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = toolIssueEntryService.createUpdateToolIssueEntry(files, docId,
					screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("toolIssueEntryVO", serviceResponse.get("toolIssueEntryVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

//	@GetMapping("/filesToolsIssueToCalibration/**")
//	public ResponseEntity<byte[]> viewFilefilesToolsIssueToCalibration(HttpServletRequest request) throws IOException {
//		return toolIssueEntryService.filesToolsIssueToCalibration(request);
//	}

	@PostMapping(value = "/createUpdateToolRecieveFromCalibration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateToolRecieveFromCalibration(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateToolRecieveFromCalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = toolIssueEntryService.createUpdateToolRecieveFromCalibration(files,
					docId, screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("toolRecieveFromCalibrationVO", serviceResponse.get("toolRecieveFromCalibrationVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewFileTools/**")
	public ResponseEntity<byte[]> viewFileTools(HttpServletRequest request) throws IOException {
		return toolIssueEntryService.viewFileTools(request);
	}

	@GetMapping("/getToolIssueEntryImages/{id}")
	public ResponseEntity<List<ToolIssueEntryImageResponseDTO>> getToolIssueEntryImages(@PathVariable Long id)
			throws Exception {

		List<ToolIssueEntryImageResponseDTO> response = toolIssueEntryService.getToolIssueEntryImages(id);

		return ResponseEntity.ok(response);
	}

	// ToolsIssueToCalibrationImage
	@GetMapping("/ToolsIssueToCalibrationImage/{id}")
	public ResponseEntity<List<ImageResponseDTO>> ToolsIssueToCalibrationImage(@PathVariable Long id) throws Exception {

		List<ImageResponseDTO> response = toolIssueEntryService.ToolsIssueToCalibrationImage(id);

		return ResponseEntity.ok(response);

	}

}
