package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import com.efitops.basesetup.ResponseDTO.DailyPatrolInspectionResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.DailyPatrolImageResponseDTO;
import com.efitops.basesetup.dto.DailyPatrolInspectionDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;
import com.efitops.basesetup.service.DailyPatrolInspectionService;

@CrossOrigin
@RestController
@RequestMapping("/api/dailypatrolinspectioncontroller")
public class DailyPatrolInspectionController extends BaseController {

	@Autowired
	DailyPatrolInspectionService dailyPatrolInspectionService;

	static final Logger LOGGER = LoggerFactory.getLogger(DailyPatrolInspectionController.class);

//	@PutMapping("/updateCreateDailyPatrolInspection")
//	public ResponseEntity<ResponseDTO> updateCreateDailyPatrolInspection(@Valid @RequestBody DailyPatrolInspectionDTO dailyPatrolInspectionDTO) {
//		String methodName = "updateCreatePurchaseIndent()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//
//		try {
//			Map<String, Object> dailyPatrolInspectionVO = dailyPatrolInspectionService.updateCreateDailyPatrolInspection(dailyPatrolInspectionDTO);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, dailyPatrolInspectionVO.get("message"));
//			responseObjectsMap.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO.get("dailyPatrolInspectionVO")); // Corrected key
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

	@PutMapping(value = "/createUpdateDailyPatrolInspection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateDailyPatrolInspection(

//			@RequestPart("data") DailyPatrolInspectionDTO dto,
			@RequestBody DailyPatrolInspectionDTO dto,

			@RequestPart(value = "files", required = false) List<MultipartFile> files) {

		String methodName = "createUpdateDailyPatrolInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> map = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> result = dailyPatrolInspectionService.updateCreateDailyPatrolInspection(dto, files);

			map.put("message", result.get("message"));
			map.put("dailyPatrolInspectionVO", result.get("dailyPatrolInspectionVO"));

			responseDTO = createServiceResponse(map);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(map, "Save Failed", e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDailyPatrolInspectionDocId")
	public ResponseEntity<ResponseDTO> getDailyPatrolInspectionDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getDailyPatrolInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = dailyPatrolInspectionService.getDailyPatrolInspectionDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DailyPatrolInspection DocId information retrieved successfully");
			responseObjectsMap.put("dailyPatrolInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DailyPatrolInspection DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDailyPatrolInspectionById")
	public ResponseEntity<ResponseDTO> getDailyPatrolInspectionById(@RequestParam Long id) {
		String methodName = "getDailyPatrolInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<DailyPatrolInspectionVO> dailyPatrolInspectionVO = null;
		try {
			dailyPatrolInspectionVO = dailyPatrolInspectionService.getDailyPatrolInspectionById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DailyPatrolInspection information get successfully");
			responseObjectsMap.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DailyPatrolInspection information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDailyPatrolInspection")
	public ResponseEntity<ResponseDTO> getAllDailyPatrolInspection(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllDailyPatrolInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DailyPatrolInspectionVO> dailyPatrolInspectionVO = new ArrayList<DailyPatrolInspectionVO>();
		try {
			dailyPatrolInspectionVO = dailyPatrolInspectionService.getAllDailyPatrolInspection(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DailyPatrolInspection All information get successfully");
			responseObjectsMap.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DailyPatrolInspection information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardNoForDailyPatrollInspection")
	public ResponseEntity<ResponseDTO> getRouteCardNoForDailyPatrollInspection(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getRouteCardNoForDailyPatrollInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> routeCardNo = new ArrayList<Map<String, Object>>();
		try {
			routeCardNo = dailyPatrolInspectionService.getRouteCardNoForDailyPatrollInspection(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardNo  information get successfully");
			responseObjectsMap.put("routeCardNo", routeCardNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RouteCardNo information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDrawingMasterNoForDailyPatrolInspection")
	public ResponseEntity<ResponseDTO> getDrawingMasterNoForDailyPatrolInspection(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String partNo) {
		String methodName = "getDrawingMasterNoForDailyPatrolInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dailyPatrolInspectionVO = new ArrayList<>();
		try {
			dailyPatrolInspectionVO = dailyPatrolInspectionService.getDrawingMasterNoForDailyPatrolInspection(orgId,
					finYear, branchCode, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DailyPatrolInspectionVO for SampleApproval information get successfully By OrgId");
			responseObjectsMap.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DailyPatrolInspectionVO for SampleApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getMachineDetailsForDailyPatrolInspection")
	public ResponseEntity<ResponseDTO> getMachineDetail(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getMachineDetailsForDailyPatrolInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> machineDeatils = new ArrayList<Map<String, Object>>();
		try {
			machineDeatils = dailyPatrolInspectionService.getMachineDetailsForDailyPatrolInspection(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Machine  information get successfully");
			responseObjectsMap.put("machineMasterVO", machineDeatils);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Machine information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getShiftDetails")
	public ResponseEntity<ResponseDTO> getShiftDetails(@RequestParam Long orgId) {
		String methodName = "getShiftDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> shiftDetails = new ArrayList<Map<String, Object>>();
		try {
			shiftDetails = dailyPatrolInspectionService.getShiftDetails(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Shift  information get successfully");
			responseObjectsMap.put("shiftDetails", shiftDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Shift information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobOrderNoForDailyPatrolInspection")
	public ResponseEntity<ResponseDTO> getJobOrderNoForDailyPatrolInspection(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String routeCardNo) {
		String methodName = "getJobOrderNoForDailyPatrolInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> jobOrderNo = new ArrayList<>();
		try {
			jobOrderNo = dailyPatrolInspectionService.getJobOrderNoForDailyPatrolInspection(orgId, finYear, branchCode,
					routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"JobOrderNo for DailyPatrolInspection information get successfully By OrgId");
			responseObjectsMap.put("jobOrderNo", jobOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"JobOrderNo for SampleApproval information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDailyPatrolInspectionDetails")
	public ResponseEntity<ResponseDTO> getDailyPatrolInspectionDetails(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate) {
		String methodName = "getDailyPatrolInspectionDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dailyPatrolInspectionVO = new ArrayList<>();
		try {
			dailyPatrolInspectionVO = dailyPatrolInspectionService.getDailyPatrolInspectionDetails(orgId, fromdate,
					todate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Daily Patrol Inspection Details get successfully");
			responseObjectsMap.put("dailyPatrolInspectionVO", dailyPatrolInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					" Daily Patrol Inspection Details receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEmployeeNameBasedOnDepartment")
	public ResponseEntity<ResponseDTO> getEmployeeNameBasedOnDepartment(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getEmployeeNameBasedOnDepartment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = dailyPatrolInspectionService.getEmployeeNameBasedOnDepartment(orgId, branchCode);
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

	@PostMapping("/previewDailyPatrolInspectionExcel")
	public ResponseEntity<ResponseDTO> previewDailyPatrolInspectionExcel(@RequestParam("files") MultipartFile file) {

		String methodName = "previewDailyPatrolInspectionExcel()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> map = new HashMap<>();
		ResponseDTO responseDTO;
		try {

			DailyPatrolInspectionResponseDTO dto = dailyPatrolInspectionService.previewDailyPatrolInspectionExcel(file);

			map.put(CommonConstant.STRING_MESSAGE, "Excel preview loaded successfully");

			map.put("dailyPatrolInspectionVO", dto);

			responseDTO = createServiceResponse(map);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(map, "Preview failed", e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getInspectionByInchargeName")
	public ResponseEntity<ResponseDTO> getInspectionByInchargeName(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getInspectionByInchargeName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = dailyPatrolInspectionService.getInspectionByInchargeName(orgId, branchCode);
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

	@PostMapping(value = "/createUpdateDailyPatrolInspection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateDailyPatrolInspection(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateDailyPatrolInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = dailyPatrolInspectionService.createUpdateDailyPatrolInspection(files,
					docId, screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("dailyPatrolInspectionVO", serviceResponse.get("dailyPatrolInspectionVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/view/**")
	public ResponseEntity<byte[]> view(HttpServletRequest request) throws IOException {
		return dailyPatrolInspectionService.view(request);
	}
	//Daily Patrol Inspection image attachement
	
	
	@GetMapping("/DailyPatrolInspectionImages/{id}")
	public ResponseEntity<List<DailyPatrolImageResponseDTO>> getDailyPatrolInspectionImages(@PathVariable Long id) throws Exception {

		List<DailyPatrolImageResponseDTO> response = dailyPatrolInspectionService.getDailyPatrolInsImages(id);

		return ResponseEntity.ok(response);
	}

//	@GetMapping("/DailyPatrolInspectionImages/{id}")
//	public ResponseEntity<List<ImageResponseDTO>> getImages(@PathVariable Long id) throws Exception {
//
//	    List<ImageResponseDTO> response = dailyPatrolInspectionService.getAllImages(id);
//
//	    return ResponseEntity.ok(response);
//	}
}
