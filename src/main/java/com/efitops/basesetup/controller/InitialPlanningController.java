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

import com.efitops.basesetup.ResponseDTO.InitialPlanningResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.InitialPlanningDTO;
import com.efitops.basesetup.dto.ProblemSolvingEntryDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.InitialPlanningService;

@CrossOrigin
@RestController
@RequestMapping("/api/initialPlanning")

public class InitialPlanningController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InitialPlanningController.class);

	@Autowired
	InitialPlanningService initialPlanningService;

	@PutMapping("/updateCreateInitialPlanning")

	public ResponseEntity<ResponseDTO> updateCreateInitialPlanning(@RequestBody InitialPlanningDTO initialPlanningDTO) {

		String methodName = "updateCreateInitialPlanning()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = initialPlanningService.updateCreateInitialPlanning(initialPlanningDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("initialPlanningVO", responseMap.get("initialPlanningVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInitialPlanningById")

	public ResponseEntity<ResponseDTO> getInitialPlanningById(@RequestParam Long id) {

		String methodName = "getInitialPlanningById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		InitialPlanningResponseDTO response = null;

		try {

			response = initialPlanningService.getInitialPlanningById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Initial Planning information retrieved successfully");

			responseObjectsMap.put("initialPlanningVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Initial Planning information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInitialPlanningByOrgId")

	public ResponseEntity<ResponseDTO> getInitialPlanningByOrgId(@RequestParam Long orgId) {

		String methodName = "getInitialPlanningByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<InitialPlanningResponseDTO> responseList = new ArrayList<>();

		try {

			responseList = initialPlanningService.getInitialPlanningByOrgId(orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Initial Planning information retrieved successfully");

			responseObjectsMap.put("initialPlanningVO", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Initial Planning information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	item dropdown for initial planning
	@GetMapping("/getItemDropdownForInitialPlanning")
	public ResponseEntity<ResponseDTO> getItemDropdownForInitialPlanning(@RequestParam Long itemType,
			@RequestParam Long orgId) {

		String methodName = "getItemDropdownForInitialPlanning()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = initialPlanningService.getItemDropdownForInitialPlanning(itemType, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information retrieved successfully");

			responseObjectsMap.put("itemList", responseMap.get("itemList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Item information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	parameter dropdown for the initial planning 

	@GetMapping("/getParameterDropdownForInitialPlanning")
	public ResponseEntity<ResponseDTO> getParameterDropdownForInitialPlanning(@RequestParam Long orgId) {

		String methodName = "getParameterDropdownForInitialPlanning()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = initialPlanningService.getParameterDropdownForInitialPlanning(orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Parameter information retrieved successfully");

			responseObjectsMap.put("parameterList", responseMap.get("parameterList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Parameter information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInitialPlanningDocId")
	public ResponseEntity<ResponseDTO> getInitialPlanningDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getInitialPlanningDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = initialPlanningService.getInitialPlanningDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Initial Planning DocId information retrieved successfully");

			responseObjectsMap.put("initialPlanningDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Initial Planning DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
//	problem solving entry

	@PutMapping("/updateCreateProblemSolvingEntry")

	public ResponseEntity<ResponseDTO> updateCreateProblemSolvingEntry(
			@RequestBody ProblemSolvingEntryDTO problemSolvingEntryDTO) {

		String methodName = "updateCreateProblemSolvingEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = initialPlanningService
					.updateCreateProblemSolvingEntry(problemSolvingEntryDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("problemSolvingEntryVO", responseMap.get("problemSolvingEntryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProblemSolvingEntryDocId")
	public ResponseEntity<ResponseDTO> getProblemSolvingEntryDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getProblemSolvingEntryDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = initialPlanningService.getProblemSolvingEntryDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Problem Solving Entry DocId information retrieved successfully");

			responseObjectsMap.put("problemSolvingEntryDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Problem Solving Entry DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	teammember ,prepared by and responsible by dropdown 
	@GetMapping("/getTeamMemberDropdownForProblemSolvingEntry")
	public ResponseEntity<ResponseDTO> getTeamMemberDropdownForProblemSolvingEntry(@RequestParam Long branch,
			@RequestParam Long department, @RequestParam Long orgId) {

		String methodName = "getTeamMemberDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = initialPlanningService.getTeamMemberDropdownForProblemSolvingEntry(branch,
					department, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Team Member information retrieved successfully");

			responseObjectsMap.put("teamMemberList", responseMap.get("teamMemberList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Team Member information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
}
