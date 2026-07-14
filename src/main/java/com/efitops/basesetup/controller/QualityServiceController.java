package com.efitops.basesetup.controller;

import java.io.IOException;
//import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.DailyPatrolResponseDTO;
import com.efitops.basesetup.dto.DocumentNumberChangeDTO;
import com.efitops.basesetup.dto.EcnApprovalRecordDTO;
import com.efitops.basesetup.dto.EngineeringChangeNoticeRegisterDTO;
import com.efitops.basesetup.dto.FinalInspectionImageResponseDTO;
import com.efitops.basesetup.dto.FinalInspectionReportDTO;
import com.efitops.basesetup.dto.FinalInspectionResponseDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.IncomingImageResponseDTO;
import com.efitops.basesetup.dto.IncomingMaterialInspectionDTO;
import com.efitops.basesetup.dto.IncomingMaterialResposeDTO;
import com.efitops.basesetup.dto.InprocessImageResponseDTO;
import com.efitops.basesetup.dto.InprocessInspectionDTO;
import com.efitops.basesetup.dto.InprocessResponseDTO;
import com.efitops.basesetup.dto.NPDImageResponseDTO;
import com.efitops.basesetup.dto.NcProductRegisterDTO;
import com.efitops.basesetup.dto.NpdDTO;
import com.efitops.basesetup.dto.ProcessNonConformanceReportDTO;
import com.efitops.basesetup.dto.QADRegisterDTO;
import com.efitops.basesetup.dto.QualityDocumentChangeRecordDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SampleResponseDTO;
import com.efitops.basesetup.dto.SettingResposeDTO;
import com.efitops.basesetup.entity.DocumentNumberChangeVO;
import com.efitops.basesetup.entity.EcnApprovalRecordVO;
import com.efitops.basesetup.entity.EngineeringChangeNoticeRegisterVO;
import com.efitops.basesetup.entity.FinalInspectionReportVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;
import com.efitops.basesetup.entity.NcProductRegisterVO;
import com.efitops.basesetup.entity.NpdVO;
import com.efitops.basesetup.entity.ProcessNonConformanceReportVO;
import com.efitops.basesetup.entity.QADRegisterVO;
import com.efitops.basesetup.entity.QualityDocumentChangeRecordVO;
import com.efitops.basesetup.service.QualityService;

@RestController
@RequestMapping("/api/quality")
public class QualityServiceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(QualityServiceController.class);

	@Autowired
	QualityService qualityService;

	// IncomingMaterialInspection

	@GetMapping("/getAllIncomingMaterialInspectionByOrgId")
	public ResponseEntity<ResponseDTO> getAllIncomingMaterialInspectionByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllIncomingMaterialInspectionByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IncomingMaterialInspectionVO> incomingMaterialInspectionVO = new ArrayList<>();
		try {
			incomingMaterialInspectionVO = qualityService.getAllIncomingMaterialInspectionByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IncomingMaterialInspection information get successfully ByOrgId");
			responseObjectsMap.put("incomingMaterialInspectionVO", incomingMaterialInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IncomingMaterialInspection information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIncomingMaterialInspectionById")
	public ResponseEntity<ResponseDTO> getIncomingMaterialInspectionById(@RequestParam Long id) {
		String methodName = "getIncomingMaterialInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		IncomingMaterialInspectionVO incomingMaterialInspectionVO = new IncomingMaterialInspectionVO();
		try {
			incomingMaterialInspectionVO = qualityService.getIncomingMaterialInspectionById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IncomingMaterialInspection information get successfully By id");
			responseObjectsMap.put("incomingMaterialInspectionVO", incomingMaterialInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IncomingMaterialInspection information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateIncomingMaterialInspection")
	public ResponseEntity<ResponseDTO> createUpdateIncomingMaterialInspection(
			@RequestBody IncomingMaterialInspectionDTO incomingMaterialInspectionDTO) {
		String methodName = "createUpdateIncomingMaterialInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> incomingMaterialInspectionVO = qualityService
					.createUpdateIncomingMaterialInspection(incomingMaterialInspectionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, incomingMaterialInspectionVO.get("message"));
			responseObjectsMap.put("incomingMaterialInspectionVO",
					incomingMaterialInspectionVO.get("incomingMaterialInspectionVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIncomingMaterialInspectionDocId")
	public ResponseEntity<ResponseDTO> getIncomingMaterialInspectionDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getIncomingMaterialInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getIncomingMaterialInspectionDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IncomingMaterialInspectionDocId information retrieved successfully");
			responseObjectsMap.put("incomingMaterialInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve IncomingMaterialInspectionDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoAndSubContractGrnNo")
	public ResponseEntity<ResponseDTO> getGrnNoAndSubContractGrnNo(@RequestParam Long orgId,
			@RequestParam String type) {
		String methodName = "getGrnNoAndSubContractGrnNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getGrnNoAndSubContractGrnNo(orgId, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnNo Details retrieved successfully");
			responseObjectsMap.put("grnVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnAndSubContractGrnDetails")
	public ResponseEntity<ResponseDTO> getGrnAndSubContractGrnDetails(@RequestParam Long orgId,
			@RequestParam String grnNo) {
		String methodName = "getGrnAndSubContractGrnDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getGrnAndSubContractGrnDetails(orgId, grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnNo Details retrieved successfully");
			responseObjectsMap.put("grnVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemNoFromGrn")
	public ResponseEntity<ResponseDTO> getItemNoFromGrn(@RequestParam Long orgId, @RequestParam String grnNo) {
		String methodName = "getItemNoFromGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getItemNoFromGrn(orgId, grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemCode Details retrieved successfully");
			responseObjectsMap.put("grnVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemCode Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// InprocessInspection

	@GetMapping("/getAllInprocessInspectionByOrgId")
	public ResponseEntity<ResponseDTO> getAllInprocessInspectionByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllInprocessInspectionByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<InprocessInspectionVO> inprocessInspectionVO = new ArrayList<>();
		try {
			inprocessInspectionVO = qualityService.getAllInprocessInspectionByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"InprocessInspection information get successfully ByOrgId");
			responseObjectsMap.put("inprocessInspectionVO", inprocessInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"InprocessInspection information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getInprocessInspectionById")
	public ResponseEntity<ResponseDTO> getInprocessInspectionById(@RequestParam Long id) {
		String methodName = "getInprocessInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		InprocessInspectionVO inprocessInspectionVO = new InprocessInspectionVO();
		try {
			inprocessInspectionVO = qualityService.getInprocessInspectionById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"InprocessInspection information get successfully By id");
			responseObjectsMap.put("inprocessInspectionVO", inprocessInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"InprocessInspection information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateInprocessInspection")
	public ResponseEntity<ResponseDTO> createUpdateInprocessInspection(
			@RequestBody InprocessInspectionDTO inprocessInspectionDTO) {
		String methodName = "createUpdateInprocessInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> inprocessInspectionVO = qualityService
					.createUpdateInprocessInspection(inprocessInspectionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, inprocessInspectionVO.get("message"));
			responseObjectsMap.put("inprocessInspectionVO", inprocessInspectionVO.get("inprocessInspectionVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInprocessInspectionDocId")
	public ResponseEntity<ResponseDTO> getInprocessInspectionDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getInprocessInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getInprocessInspectionDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"InprocessInspectionDocId information retrieved successfully");
			responseObjectsMap.put("inprocessInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve InprocessInspectionDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardDocIdForInProcessInspection")
	public ResponseEntity<ResponseDTO> getDocIdFromRouteCardNumber(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getDocIdFromRouteCardNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getDocIdFromRouteCardNumber(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RoutrCardNumber Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RoutrCardNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDrawingNumberForInProcessInspection")
	public ResponseEntity<ResponseDTO> getDrawingNumberForInProcessInspection(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String fgPartno) {
		String methodName = "getDrawingNumberForInProcessInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getDrawingNumberForInProcessInspection(orgId, finYear, branchCode, fgPartno);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DrawingNumber Details retrieved successfully");
			responseObjectsMap.put("drawingMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DrawingNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEmployeeNameFromEmployeeMaster")
	public ResponseEntity<ResponseDTO> getEmployeeFromEmployeeMaster(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getEmployeeNameFromEmployeeMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getEmployeeNameFromEmployeeMaster(orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmployeeName Details retrieved successfully");
			responseObjectsMap.put("employeeVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve EmployeeName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// FinalInspectionReport

	@GetMapping("/getAllFinalInspectionReportByOrgId")
	public ResponseEntity<ResponseDTO> getAllFinalInspectionReportByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllFinalInspectionReportByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<FinalInspectionReportVO> finalInspectionReportVO = new ArrayList<>();
		try {
			finalInspectionReportVO = qualityService.getAllFinalInspectionReportByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FinalInspectionReport information get successfully ByOrgId");
			responseObjectsMap.put("finalInspectionReportVO", finalInspectionReportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FinalInspectionReport information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getFinalInspectionReportById")
	public ResponseEntity<ResponseDTO> getFinalInspectionReportById(@RequestParam Long id) {
		String methodName = "getFinalInspectionReportById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		FinalInspectionReportVO finalInspectionReportVO = new FinalInspectionReportVO();
		try {
			finalInspectionReportVO = qualityService.getFinalInspectionReportById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FinalInspectionReport information get successfully By id");
			responseObjectsMap.put("finalInspectionReportVO", finalInspectionReportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FinalInspectionReport information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateFinalInspectionReport")
	public ResponseEntity<ResponseDTO> createUpdateFinalInspectionReport(
			@RequestBody FinalInspectionReportDTO finalInspectionReportDTO) {
		String methodName = "createUpdateFinalInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> finalInspectionReportVO = qualityService
					.createUpdateFinalInspectionReport(finalInspectionReportDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, finalInspectionReportVO.get("message"));
			responseObjectsMap.put("finalInspectionReportVO", finalInspectionReportVO.get("finalInspectionReportVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFinalInspectionReportDocId")
	public ResponseEntity<ResponseDTO> getFinalInspectionReportDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getFinalInspectionReportDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getFinalInspectionReportDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FinalInspectionReportDocId information retrieved successfully");
			responseObjectsMap.put("finalInspectionReportDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve FinalInspectionReportDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNameForFinalInspectionReport")
	public ResponseEntity<ResponseDTO> getPartNameForFinalInspectionReport(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String routeCardNo) {
		String methodName = "getPartNameForFinalInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getPartNameForFinalInspectionReport(orgId, finYear, branchCode, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartName Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardNumberForFinalInspectionReport")
	public ResponseEntity<ResponseDTO> getRouteCardNumberForFinalInspectionReport(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getRouteCardNumberForFinalInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getRouteCardNumberForFinalInspectionReport(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardNumber Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RouteCardNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// getIncomingMaterialInspectionReport

	@GetMapping("/getIncomingMaterialInspectionReport")
	public ResponseEntity<ResponseDTO> getIncomingMaterialInspectionReport(@RequestParam Long orgId,
			@RequestParam String grnNo, @RequestParam String supplierName, @RequestParam String type) {
		String methodName = "getIncomingMaterialInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getIncomingMaterialInspectionReport(orgId, grnNo, supplierName, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IncomingMaterialInspection Details retrieved successfully");
			responseObjectsMap.put("incomingMaterialInspectionVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve IncomingMaterialInspection Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSupplierNameForIncomingMaterialInspectionReport")
	public ResponseEntity<ResponseDTO> getSupplierNameForIncomingMaterialInspectionReport(@RequestParam Long orgId,
			@RequestParam String branchCode) {

		String methodName = "getSupplierNameForIncomingMaterialInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> supplierList = qualityService
					.getSupplierNameForIncomingMaterialInspectionReport(orgId, branchCode);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier details retrieved successfully");
			responseObjectsMap.put("supplierList", supplierList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("Error in {}", methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Supplier details",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getGrnNoForIncomingMaterialInspectionReport")
	public ResponseEntity<ResponseDTO> getGrnNoForIncomingMaterialInspectionReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String supplierName) {

		String methodName = "getSupplierNameForIncomingMaterialInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> supplierList = qualityService.getGrnNoForIncomingMaterialInspectionReport(orgId,
					branchCode, supplierName);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnNo details retrieved successfully");
			responseObjectsMap.put("supplierList", supplierList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("Error in {}", methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnNo details",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	// InProcessInspectionReport

	@GetMapping("/getInProcessInspectionReport")
	public ResponseEntity<ResponseDTO> getInProcessInspectionReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String routeCardNo) {
		String methodName = "getInProcessInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getInProcessInspectionReport(orgId, branchCode, fromDate, toDate, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"InProcessInspectionReport Details retrieved successfully");
			responseObjectsMap.put("inProcessInspectionVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve InProcessInspectionReport Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFinalInspectionReportDetails")
	public ResponseEntity<ResponseDTO> getFinalInspectionReportDetails(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate) {
		String methodName = "getFinalInspectionReportDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> finalInspectionReportDetailsVO = new ArrayList<>();
		try {
			finalInspectionReportDetailsVO = qualityService.getFinalInspectionReportDetails(orgId, fromdate, todate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Final Inspection Report Details get successfully");
			responseObjectsMap.put("finalInspectionReportDetailsVO", finalInspectionReportDetailsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Final Inspection Report Details receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// EC

	@GetMapping("/getEngineeringChangeNoticeRegisterByOrgId")
	public ResponseEntity<ResponseDTO> getEngineeringChangeNoticeRegisterByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getEngineeringChangeNoticeRegisterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EngineeringChangeNoticeRegisterVO> engineeringChangeNoticeRegisterVO = new ArrayList<>();
		try {
			engineeringChangeNoticeRegisterVO = qualityService.getEngineeringChangeNoticeRegisterByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"EngineeringChangeNoticeRegister information get successfully ByOrgId");
			responseObjectsMap.put("engineeringChangeNoticeRegisterVO", engineeringChangeNoticeRegisterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"EngineeringChangeNoticeRegister information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getEngineeringChangeNoticeRegisterById")
	public ResponseEntity<ResponseDTO> getEngineeringChangeNoticeRegisterById(@RequestParam Long id) {
		String methodName = "getEngineeringChangeNoticeRegisterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		EngineeringChangeNoticeRegisterVO engineeringChangeNoticeRegisterVO = new EngineeringChangeNoticeRegisterVO();
		try {
			engineeringChangeNoticeRegisterVO = qualityService.getEngineeringChangeNoticeRegisterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"EngineeringChangeNoticeRegister information get successfully By id");
			responseObjectsMap.put("engineeringChangeNoticeRegisterVO", engineeringChangeNoticeRegisterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"EngineeringChangeNoticeRegister information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateEngineeringChangeNoticeRegister")
	public ResponseEntity<ResponseDTO> createUpdateEngineeringChangeNoticeRegister(
			@RequestBody EngineeringChangeNoticeRegisterDTO engineeringChangeNoticeRegisterDTO) {
		String methodName = "createUpdateEngineeringChangeNoticeRegister()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> engineeringChangeNoticeRegisterVO = qualityService
					.createUpdateEngineeringChangeNoticeRegister(engineeringChangeNoticeRegisterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, engineeringChangeNoticeRegisterVO.get("message"));
			responseObjectsMap.put("engineeringChangeNoticeRegisterVO",
					engineeringChangeNoticeRegisterVO.get("engineeringChangeNoticeRegisterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEngineeringChangeNoticeRegisterDocId")
	public ResponseEntity<ResponseDTO> getEngineeringChangeNoticeRegisterDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getEngineeringChangeNoticeRegisterDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getEngineeringChangeNoticeRegisterDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"EngineeringChangeNoticeRegisterDocId information retrieved successfully");
			responseObjectsMap.put("engineeringChangeNoticeRegisterDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve EngineeringChangeNoticeRegisterDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getNpdByOrgId")
	public ResponseEntity<ResponseDTO> getNpdByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getNpdByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<NpdVO> npdVO = new ArrayList<>();
		try {
			npdVO = qualityService.getNpdByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Npd information get successfully ByOrgId");
			responseObjectsMap.put("npdVO", npdVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Npd information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getNpdById")
	public ResponseEntity<ResponseDTO> getNpdById(@RequestParam Long id) {
		String methodName = "getNpdById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		NpdVO npdVO = new NpdVO();
		try {
			npdVO = qualityService.getNpdById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Npd information get successfully By id");
			responseObjectsMap.put("npdVO", npdVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Npd information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateNpd")
	public ResponseEntity<ResponseDTO> createUpdateNpd(@RequestBody NpdDTO npdDTO) {
		String methodName = "createUpdateIncomingMaterialInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> npdVO = qualityService.createUpdateNpd(npdDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, npdVO.get("message"));
			responseObjectsMap.put("npdVO", npdVO.get("npdVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getNpdDocId")
	public ResponseEntity<ResponseDTO> getNpdDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getNpdDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getNpdDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "NpdDocId information retrieved successfully");
			responseObjectsMap.put("npdDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve NpdDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomerNameFormPartyMaster")
	public ResponseEntity<ResponseDTO> getCustomerNameFormPartyMaster(@RequestParam Long orgId) {
		String methodName = "getCustomerNameFormPartyMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> partyName = qualityService.getCustomerNameFormPartyMaster(orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnNo details retrieved successfully");
			responseObjectsMap.put("partyName", partyName);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("Error in {}", methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnNo details",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPartNameFormPartyMaster")
	public ResponseEntity<ResponseDTO> getPartNameFormPartyMaster(@RequestParam Long orgId) {
		String methodName = "getPartNameFormPartyMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getPartNameFormPartyMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Part Details retrieved successfully");
			responseObjectsMap.put("itemVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Part Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEmployeeName")
	public ResponseEntity<ResponseDTO> getEmployeeName(@RequestParam Long orgId) {
		String methodName = "getGrnNoFromGrnScreen()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getEmployeeName(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Part Details retrieved successfully");
			responseObjectsMap.put("employeeVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Part Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Process

	@GetMapping("/getAllProcessNonConformanceReportByOrgId")
	public ResponseEntity<ResponseDTO> getAllProcessNonConformanceReportByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllProcessNonConformanceReportByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProcessNonConformanceReportVO> processNonConformanceReportVO = new ArrayList<>();
		try {
			processNonConformanceReportVO = qualityService.getAllProcessNonConformanceReportByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProcessNonConformanceReport information get successfully ByOrgId");
			responseObjectsMap.put("processNonConformanceReportVO", processNonConformanceReportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProcessNonConformanceReport information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getProcessNonConformanceReportById")
	public ResponseEntity<ResponseDTO> getProcessNonConformanceReportById(@RequestParam Long id) {
		String methodName = "getProcessNonConformanceReportById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ProcessNonConformanceReportVO processNonConformanceReportVO = new ProcessNonConformanceReportVO();
		try {
			processNonConformanceReportVO = qualityService.getProcessNonConformanceReportById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProcessNonConformanceReport information get successfully By id");
			responseObjectsMap.put("processNonConformanceReportVO", processNonConformanceReportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProcessNonConformanceReport information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateProcessNonConformanceReport")
	public ResponseEntity<ResponseDTO> createUpdateProcessNonConformanceReport(
			@RequestBody ProcessNonConformanceReportDTO processNonConformanceReportDTO) {
		String methodName = "createUpdateProcessNonConformanceReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> processNonConformanceReportVO = qualityService
					.createUpdateProcessNonConformanceReport(processNonConformanceReportDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, processNonConformanceReportVO.get("message"));
			responseObjectsMap.put("processNonConformanceReportVO",
					processNonConformanceReportVO.get("processNonConformanceReportVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessNonConformanceReportDocId")
	public ResponseEntity<ResponseDTO> getProcessNonConformanceReportDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getProcessNonConformanceReportDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getProcessNonConformanceReportDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProcessNonConformanceReportDocId information retrieved successfully");
			responseObjectsMap.put("processNonConformanceReportDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ProcessNonConformanceReportDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// QCDR

	@GetMapping("/getAllQualityDocumentChangeRecordByOrgId")
	public ResponseEntity<ResponseDTO> getAllQualityDocumentChangeRecordByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllQualityDocumentChangeRecordByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<QualityDocumentChangeRecordVO> qualityDocumentChangeRecordVO = new ArrayList<>();
		try {
			qualityDocumentChangeRecordVO = qualityService.getAllQualityDocumentChangeRecordByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"QualityDocumentChangeRecord information get successfully ByOrgId");
			responseObjectsMap.put("qualityDocumentChangeRecordVO", qualityDocumentChangeRecordVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"QualityDocumentChangeRecord information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllQualityDocumentChangeRecordById")
	public ResponseEntity<ResponseDTO> getAllQualityDocumentChangeRecordById(@RequestParam Long id) {
		String methodName = "getAllQualityDocumentChangeRecordById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		QualityDocumentChangeRecordVO qualityDocumentChangeRecordVO = new QualityDocumentChangeRecordVO();
		try {
			qualityDocumentChangeRecordVO = qualityService.getAllQualityDocumentChangeRecordById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"QualityDocumentChangeRecord information get successfully By id");
			responseObjectsMap.put("qualityDocumentChangeRecordVO", qualityDocumentChangeRecordVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"QualityDocumentChangeRecord information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateQualityDocumentChangeRecord")
	public ResponseEntity<ResponseDTO> createUpdateQualityDocumentChangeRecord(
			@RequestBody QualityDocumentChangeRecordDTO qualityDocumentChangeRecordDTO) {
		String methodName = "createUpdateQualityDocumentChangeRecord()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> qualityDocumentChangeRecordVO = qualityService
					.createUpdateQualityDocumentChangeRecord(qualityDocumentChangeRecordDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, qualityDocumentChangeRecordVO.get("message"));
			responseObjectsMap.put("qualityDocumentChangeRecordVO",
					qualityDocumentChangeRecordVO.get("qualityDocumentChangeRecordVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getQualityDocumentChangeRecordDocId")
	public ResponseEntity<ResponseDTO> getQualityDocumentChangeRecordDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getProcessNonConformanceReportDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getQualityDocumentChangeRecordDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"QualityDocumentChangeRecordDocId information retrieved successfully");
			responseObjectsMap.put("qualityDocumentChangeRecordDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve QualityDocumentChangeRecordDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEmployeeNameAndDesignation")
	public ResponseEntity<ResponseDTO> getEmployeeNameAndDesignation(@RequestParam Long orgId) {
		String methodName = "getEmployeeNameAndDesignation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getEmployeeNameAndDesignation(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmployeeName Details retrieved successfully");
			responseObjectsMap.put("employeeMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve EmployeeName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// ECN

	@GetMapping("/getAllEcnApprovalRecordByOrgId")
	public ResponseEntity<ResponseDTO> getAllEcnApprovalRecordByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllEcnApprovalRecordByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EcnApprovalRecordVO> ecnApprovalRecordVO = new ArrayList<>();
		try {
			ecnApprovalRecordVO = qualityService.getAllEcnApprovalRecordByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"EcnApprovalRecord information get successfully ByOrgId");
			responseObjectsMap.put("ecnApprovalRecordVO", ecnApprovalRecordVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"EcnApprovalRecord information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllEcnApprovalRecordById")
	public ResponseEntity<ResponseDTO> getAllEcnApprovalRecordById(@RequestParam Long id) {
		String methodName = "getAllEcnApprovalRecordById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		EcnApprovalRecordVO ecnApprovalRecordVO = new EcnApprovalRecordVO();
		try {
			ecnApprovalRecordVO = qualityService.getAllEcnApprovalRecordById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"EcnApprovalRecord information get successfully By id");
			responseObjectsMap.put("ecnApprovalRecordVO", ecnApprovalRecordVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"EcnApprovalRecord information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateEcnApprovalRecord")
	public ResponseEntity<ResponseDTO> createUpdateEcnApprovalRecord(
			@RequestBody EcnApprovalRecordDTO ecnApprovalRecordDTO) {
		String methodName = "createUpdateEcnApprovalRecord()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> ecnApprovalRecordVO = qualityService
					.createUpdateEcnApprovalRecord(ecnApprovalRecordDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, ecnApprovalRecordVO.get("message"));
			responseObjectsMap.put("ecnApprovalRecordVO", ecnApprovalRecordVO.get("ecnApprovalRecordVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEcnApprovalRecordDocId")
	public ResponseEntity<ResponseDTO> getEcnApprovalRecordDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getEcnApprovalRecordDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getEcnApprovalRecordDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"QualityDocumentChangeRecordDocId information retrieved successfully");
			responseObjectsMap.put("qualityDocumentChangeRecordDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve QualityDocumentChangeRecordDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getQualityDocumentChangeRecordReport")
	public ResponseEntity<ResponseDTO> getQualityDocumentChangeRecordReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		String methodName = "getQualityDocumentChangeRecordReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> qualityDocumentChangeRecordReport = new ArrayList<>();
		try {
			qualityDocumentChangeRecordReport = qualityService.getQualityDocumentChangeRecordReport(orgId, branchCode,
					fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"qualityDocumentChangeRecordReport get successfully By OrgId");
			responseObjectsMap.put("qualityDocumentChangeRecordReport", qualityDocumentChangeRecordReport);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"qualityDocumentChangeRecordReport information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getEcnApprovalRecordReport")
	public ResponseEntity<ResponseDTO> getEcnApprovalRecordReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		String methodName = "getEcnApprovalRecordReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> ecnApprovalRecordReport = new ArrayList<>();
		try {
			ecnApprovalRecordReport = qualityService.getEcnApprovalRecordReport(orgId, branchCode, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ecnApprovalRecordReport get successfully By OrgId");
			responseObjectsMap.put("ecnApprovalRecordReport", ecnApprovalRecordReport);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ecnApprovalRecordReport information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateQADRegister")
	public ResponseEntity<ResponseDTO> updateCreateQADRegister(@RequestBody QADRegisterDTO qadRegisterDTO) {
		String methodName = "updateCreateQADRegister()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> qadRegisterVO = qualityService.updateCreateQADRegister(qadRegisterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, qadRegisterVO.get("message"));
			responseObjectsMap.put("qadRegisterVO", qadRegisterVO.get("qadRegisterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
//	@GetMapping("/getQADRegisterByOrgId")
//	public ResponseEntity<ResponseDTO> getQADRegisterByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
//			@RequestParam String branchCode) {
//		String methodName = "getQADRegisterByOrgId()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<QADRegisterVO> qadRegisterVO = new ArrayList<>();
//		try {
//			qadRegisterVO = qualityService.getAllQADRegisterByOrgId(orgId, finYear, branchCode);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "QADRegister information get successfully ByOrgId");
//			responseObjectsMap.put("qadRegisterVO", qadRegisterVO);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"QADRegister information receive failed By OrgId", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//
//	}

	@GetMapping("/getQADRegisterById")
	public ResponseEntity<ResponseDTO> getQADRegisterById(@RequestParam Long id) {
		String methodName = "getQADRegisterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		QADRegisterVO qadRegisterVO = new QADRegisterVO();
		try {
			qadRegisterVO = qualityService.getQADRegisterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "QADRegister information get successfully By Id");
			responseObjectsMap.put("qadRegisterVO", qadRegisterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "QADRegister information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// NPD Report
	@GetMapping("/getNpdReport")
	public ResponseEntity<ResponseDTO> getNpdReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate) {
		String methodName = "getNpdReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getNpdReport(orgId, fromdate, todate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "NPD Report  retrieved successfully");
			responseObjectsMap.put("npdVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieveNPD Report", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Engineering Change Notice Register Report

	@GetMapping("/getEngineeringChangeNoticeRegisterReport")
	public ResponseEntity<ResponseDTO> getEngineeringChangeNoticeRegisterReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate) {
		String methodName = "getEngineeringChangeNoticeRegisterReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getEngineeringChangeNoticeRegisterReport(orgId, fromdate, todate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Engineering Change Notice Register Report  retrieved successfully");
			responseObjectsMap.put("engineeringChangeNoticeRegisterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Engineering Change Notice Register Report", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/incomingMaterialUpload")
	public ResponseEntity<ResponseDTO> incomingMaterialUpload(@RequestPart MultipartFile files) {
		String methodName = "incomingMaterialUpload()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			List<IncomingMaterialResposeDTO> exelRespose = qualityService.getIncomingMaterialRespose(files);
			responseObjectsMap.put("exelRespose", exelRespose);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/inprocessInspectionUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> inprocessInspectionUpload(@RequestPart("files") MultipartFile files) {

		String methodName = "inprocessInspectionUpload()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			// Important check
			if (files == null || files.isEmpty()) {
				throw new RuntimeException("Please upload a valid Excel file");
			}

			List<InprocessResponseDTO> uploadExcel = qualityService.getInprocessResponse(files);

			responseObjectsMap.put("uploadExcel", uploadExcel);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PostMapping(value = "/settingApprovalUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> settingApprovalUpload(@RequestPart("files") MultipartFile files) {

		String methodName = "settingApprovalUpload()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			List<SettingResposeDTO> uploadExel = qualityService.getSettingResponse(files);
			responseObjectsMap.put("uploadExel", uploadExel);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/sampleApprovalUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> sampleApprovalUpload(@RequestPart("files") MultipartFile files) {

		String methodName = "sampleApprovalUpload()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			List<SampleResponseDTO> uploadExel = qualityService.getSampleResponse(files);
			responseObjectsMap.put("uploadExel", uploadExel);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateNCProductRegister")
	public ResponseEntity<ResponseDTO> updateCreateNCProductRegister(
			@RequestBody NcProductRegisterDTO ncProductRegisterDTO) {
		String methodName = "updateCreateNCProductRegister()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> ncProductRegisterVO = qualityService
					.updateCreateNCProductRegister(ncProductRegisterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, ncProductRegisterVO.get("message"));
			responseObjectsMap.put("NCProductRegisterVO", ncProductRegisterVO.get("ncProductRegisterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getNCProductRegisterOrgId")
	public ResponseEntity<ResponseDTO> getNcProductRegisterOrgId(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String finYear) {
		String methodName = "getNcProductRegisterOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<NcProductRegisterVO> ncProductRegisterVO = new ArrayList<>();
		try {
			ncProductRegisterVO = qualityService.getNCProductRegisterOrgId(orgId, branchCode, finYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"NC Product Register Information get successfully ByOrgId");
			responseObjectsMap.put("ncProductRegisterVO", ncProductRegisterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"NC Product Register Information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getNCProductRegisterById")
	public ResponseEntity<ResponseDTO> getNcProductRegisterById(@RequestParam Long id) {
		String methodName = "getNcProductRegisterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		NcProductRegisterVO ncProductRegisterVO = new NcProductRegisterVO();
		try {
			ncProductRegisterVO = qualityService.getNCProductRegisterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"NC Product Register Information get successfully By Id");
			responseObjectsMap.put("ncProductRegisterVO", ncProductRegisterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"NC Product Register Information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/dailyPatrolInspectionUpload")
	public ResponseEntity<ResponseDTO> dailyPatrolInspectionUpload(@RequestPart MultipartFile files) {
		String methodName = "dailyPatrolInspectionUpload()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			List<DailyPatrolResponseDTO> uploadExel = qualityService.getDailyPatrolResponse(files);
			responseObjectsMap.put("uploadExel", uploadExel);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/finalInspectionUpload")
	public ResponseEntity<ResponseDTO> finalInspectionUpload(@RequestPart MultipartFile file) {
		String methodName = "finalInspectionUpload()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			List<FinalInspectionResponseDTO> uploadexel = qualityService.getFinalInspectionResponse(file);
			responseObjectsMap.put("uploadexel", uploadexel);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getNcProductRegisterDocId")
	public ResponseEntity<ResponseDTO> getNcProductRegisterDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getNcProductRegisterDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getNcProductRegisterDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"NcProductRegisterDocId information retrieved successfully");
			responseObjectsMap.put("ncProductRegisterDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve NcProductRegisterDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getNCProductRegisterReport")
	public ResponseEntity<ResponseDTO> getNCProductRegisterReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam String partNo) {
		String methodName = "getNCProductRegisterReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getNCProductRegisterReport(orgId, fromdate, todate, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "NC Product Register Report  retrieved successfully");
			responseObjectsMap.put("ncProductRegisterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve NC Product Register Report", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/createUpdateIncomingMaterialInspection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateIncomingMaterialInspection(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateIncomingMaterialInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateIncomingMaterialInspection(files, docId,
					screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("incomingMaterialInspectionVO", serviceResponse.get("incomingMaterialInspectionVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}

	}

	@GetMapping("/getDrawingNo")
	public ResponseEntity<ResponseDTO> getDrawingNo(@RequestParam Long orgId, @RequestParam String partNo) {
		String methodName = "getDrawingNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getDrawingNo(orgId, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DrawingNo  retrieved successfully");
			responseObjectsMap.put("drawingVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DrawingNo Report",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDrawingOldRevNo")
	public ResponseEntity<ResponseDTO> getDrawingOldRevNo(@RequestParam Long orgId, @RequestParam String drawingNo) {
		String methodName = "getDrawingOldRevNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getDrawingOldRevNo(orgId, drawingNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DrawingOldRevNo retrieved successfully");
			responseObjectsMap.put("drawingVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DrawingOldRevNo Report",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessNonConformanceReport")
	public ResponseEntity<ResponseDTO> getProcessNonConformanceReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam String partNo) {
		String methodName = "getProcessNonConformanceReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getProcessNonConformanceReport(orgId, fromdate, todate, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProcessNonConformanceReport  retrieved successfully");
			responseObjectsMap.put("processNonConformanceReportVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ProcessNonConformanceReport", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocumentNumberChangeByOrgId")
	public ResponseEntity<ResponseDTO> getDocumentNumberChangeByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getDocumentNumberChangeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DocumentNumberChangeVO> documentNumberChangeVO = new ArrayList<>();
		try {
			documentNumberChangeVO = qualityService.getDocumentNumberChangeByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DocumentNumberChangeVO information get successfully ByOrgId");
			responseObjectsMap.put("documentNumberChangeVO", documentNumberChangeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DocumentNumberChange information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDocumentNumberChangeById")
	public ResponseEntity<ResponseDTO> getDocumentNumberChangeById(@RequestParam Long id) {
		String methodName = "getDocumentNumberChangeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DocumentNumberChangeVO documentNumberChangeVO = new DocumentNumberChangeVO();
		try {
			documentNumberChangeVO = qualityService.getDocumentNumberChangeById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DocumentNumberChange information get successfully By id");
			responseObjectsMap.put("documentNumberChangeVO", documentNumberChangeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DocumentNumberChange information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateDocumentNumberChange")
	public ResponseEntity<ResponseDTO> createUpdateDocumentNumberChange(
			@RequestBody DocumentNumberChangeDTO documentNumberChangeDTO) {
		String methodName = "createUpdateDocumentNumberChange()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> documentNumberChangeVO = qualityService
					.createUpdateDocumentNumberChange(documentNumberChangeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentNumberChangeVO.get("message"));
			responseObjectsMap.put("documentNumberChangeVO", documentNumberChangeVO.get("documentNumberChangeVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocumentNumberChangeDocId")
	public ResponseEntity<ResponseDTO> getDocumentNumberChangeDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getDocumentNumberChangeDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getDocumentNumberChangeDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DocumentNumberChangeDocId information retrieved successfully");
			responseObjectsMap.put("documentNumberChangeDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DocumentNumberChangeDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// npd file upload
	@PostMapping(value = "/createNpdAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createNpdAttachment(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createNpdAttachment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateNpd(files, docId, screenName, module);

			responseMap.put("message", "");
			responseMap.put("npdVO", serviceResponse.get("npdVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}

	}

	@GetMapping("/getDocumentFormateNumber")
	public ResponseEntity<ResponseDTO> getDocumentFormateNumber(@RequestParam Long orgId,
			@RequestParam String screenName) {
		String methodName = "getDocumentFormateNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getDocumentFormateNumber(orgId, screenName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocumentFormateNumber retrieved successfully");
			responseObjectsMap.put("inprocessInspectionVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DocumentFormateNumber Report", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// File Upload for ECN Approval record
	@PostMapping(value = "/createEcnAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createEcnAttachment(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createEcnAttachment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateEcn(files, docId, screenName, module);

			responseMap.put("message", "");
			responseMap.put("ecnApprovalRecordVO", serviceResponse.get("ecnApprovalRecordVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}

	}

	@GetMapping("/getListOfGrnNumbers")
	public ResponseEntity<ResponseDTO> getListOfGrnNumbers(@RequestParam Long orgId, @RequestParam String type) {
		String methodName = "getListOfGrnNumbers()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getListOfGrnNumbers(orgId, type);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "grn Details retrieved successfully");
			responseObjectsMap.put("incomingMaterialInspectionVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve grn Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/createUpdateFinalInspection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateFinalInspection(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateFinalInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateFinalInspection(files, docId, screenName,
					module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("inprocessInspectionVO", serviceResponse.get("inprocessInspectionVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewFilesFinal/**")
	public ResponseEntity<byte[]> viewFilesFinal(HttpServletRequest request) throws IOException {
		return qualityService.viewFilesFinal(request);
	}

	@PostMapping(value = "/createUpdateProcessNonConformanceReport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateProcessNonConformanceReport(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateProcessNonConformanceReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateProcessNonConformanceReport(files, docId,
					screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("processNonConformanceReportVO", serviceResponse.get("processNonConformanceReportVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/files/**")
	public ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException {
		return qualityService.viewFile(request);
	}

	@GetMapping("/viewFileProcessNon/**")
	public ResponseEntity<byte[]> viewFileProcessNon(HttpServletRequest request) throws IOException {
		return qualityService.viewFileProcessNon(request);
	}

	@PostMapping(value = "/createUpdateEngineeringChangeNoticeRegister", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateEngineeringChangeNoticeRegister(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateEngineeringChangeNoticeRegister()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateEngineeringChangeNoticeRegister(files,
					docId, screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("engineeringChangeNoticeRegisterVO",
					serviceResponse.get("engineeringChangeNoticeRegisterVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewFileEngineering/**")
	public ResponseEntity<byte[]> viewFileEngineering(HttpServletRequest request) throws IOException {
		return qualityService.viewFileEngineering(request);
	}

	@PostMapping(value = "/createUpdateQualityDocumentChangeRecord", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateQualityDocumentChangeRecord(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateQualityDocumentChangeRecord()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateQualityDocumentChangeRecord(files, docId,
					screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("qualityDocumentChangeRecordVO", serviceResponse.get("qualityDocumentChangeRecordVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewFileDocument/**")
	public ResponseEntity<byte[]> viewFileDocument(HttpServletRequest request) throws IOException {
		return qualityService.viewFileEngineering(request);
	}

	@PostMapping(value = "/createUpdateNCProductRegister", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateNCProductRegister(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateNCProductRegister()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateNCProductRegister(files, docId, screenName,
					module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("ncProductRegisterVO", serviceResponse.get("ncProductRegisterVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewFileNcProduct/**")
	public ResponseEntity<byte[]> viewFileNcProduct(HttpServletRequest request) throws IOException {
		return qualityService.viewFileNcProduct(request);
	}

	@GetMapping("/getQADRegisterReport")
	public ResponseEntity<ResponseDTO> getQADRegisterReport(@RequestParam Long orgId, @RequestParam String docName) {
		String methodName = "getQADRegisterReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getQADRegisterReport(orgId, docName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " QAD Register Report  retrieved successfully");
			responseObjectsMap.put("qadRegisterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve QAD Register Report ",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/createUpdateInprocessInspection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateInprocessInspection(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateInprocessInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = qualityService.createUpdateInprocessInspection(files, docId,
					screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("inprocessInspectionVO", serviceResponse.get("inprocessInspectionVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/EcnImages/{id}")
	public ResponseEntity<List<ImageResponseDTO>> getImages(@PathVariable Long id) throws Exception {

	    List<ImageResponseDTO> response = qualityService.getAllImages(id);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/engineeringChangeNoticeRegister/Images/{id}")
	public ResponseEntity<List<ImageResponseDTO>> getEngineeringChangeNoticeRegisterImages(@PathVariable Long id)
			throws Exception {

		List<ImageResponseDTO> response = qualityService.getEngineeringChangeNoticeRegisterImages(id);

		return ResponseEntity.ok(response);
	}

	// incoming material inspection image attachment

	
	@GetMapping("/IncomingfMaterialInspectionImages/{id}")
	public ResponseEntity<List<ImageResponseDTO>> getIncomingMaterialInspectionImages(@PathVariable Long id)
			throws Exception {

		List<ImageResponseDTO> response = qualityService.getIncomingMaterialInspectionImages(id);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/getQADRegisterByDocId")
	public ResponseEntity<ResponseDTO> getQADRegisterByDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getQADRegisterByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = qualityService.getQADRegisterByDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"qadRegisterByDocId information retrieved successfully");
			responseObjectsMap.put("qadRegisterByDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve qadRegisterByDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
  
  @GetMapping("/NCProductRegister/Images/{id}")
	public ResponseEntity<List<ImageResponseDTO>> getNCProductRegisterImages(@PathVariable Long id) throws Exception {

		List<ImageResponseDTO> response = qualityService.getNCProductRegisterImages(id);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/ProcessNonConformanceReportImages/{id}")
	public ResponseEntity<List<ImageResponseDTO>> getProcessNonConformanceReportImages(@PathVariable Long id)
			throws Exception {

		List<ImageResponseDTO> response = qualityService.getProcessNonConformanceReportImages(id);

		return ResponseEntity.ok(response);
	}
    
	@GetMapping("/toolRecieveFromCalibrationVO/Images/{id}")
	public ResponseEntity<List<ImageResponseDTO>> toolRecieveFromCalibrationVO(@PathVariable Long id) throws Exception {

	    List<ImageResponseDTO> response = qualityService.toolRecieveFromCalibrationVO(id);

	    return ResponseEntity.ok(response);
	}
  
  
   	@GetMapping("/InprocessInspectionImages/{id}")
	public ResponseEntity<List<InprocessImageResponseDTO>> getInprocessInspectionImages(@PathVariable Long id)
			throws Exception {

		List<InprocessImageResponseDTO> response = qualityService.getInprocessInspectionImages(id);

		return ResponseEntity.ok(response);
	}

// FinalInspectionImageAttachment

	@GetMapping("/FinalInspectionReportImages/{id}")
	public ResponseEntity<List<FinalInspectionImageResponseDTO>> getFinalInspectionReportImages(@PathVariable Long id)
			throws Exception {

		List<FinalInspectionImageResponseDTO> response = qualityService.getFinalInspectionReportImages(id);

		return ResponseEntity.ok(response);
	}

	// NPDImageAttachment

	@GetMapping("/NPDImages/{id}")
	public ResponseEntity<List<NPDImageResponseDTO>> getNPDImages(@PathVariable Long id) throws Exception {

		List<NPDImageResponseDTO> response = qualityService.getNPDImages(id);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/viewFileInprocess/**")
	public ResponseEntity<byte[]> viewFileInprocess(HttpServletRequest request) throws IOException {
		return qualityService.viewFileInprocess(request);
	}

	@GetMapping("/getNPDdetails")
	public ResponseEntity<ResponseDTO> getNPDdetails(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getNPDdetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = qualityService.getNPDdetails(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "NPD Details retrieved successfully");
			responseObjectsMap.put("npdVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve NPD Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	

}
