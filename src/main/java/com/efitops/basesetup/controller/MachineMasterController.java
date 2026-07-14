package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.efitops.basesetup.dto.DrawingMasterDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.StockLocationDTO;
import com.efitops.basesetup.entity.DrawingMasterAttachmentsVO;
import com.efitops.basesetup.entity.DrawingMasterDetailsVO;
import com.efitops.basesetup.entity.DrawingMasterVO;
import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.StockLocationVO;
import com.efitops.basesetup.service.MachineMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/machinemaster")
public class MachineMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(MachineMasterController.class);

	@Autowired
	MachineMasterService machineMasterService;

	@PutMapping("/updateCreateMachineMaster")
	public ResponseEntity<ResponseDTO> updateCreateMachineMaster(
			@Valid @RequestBody MachineMasterDTO machineMasterDTO) {
		String methodName = "updateCreateMachineMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> machineMaster = machineMasterService.updateCreateMachineMaster(machineMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, machineMaster.get("message"));
			responseObjectsMap.put("machineMasterVO", machineMaster.get("machineMasterVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineMasterDocId")
	public ResponseEntity<ResponseDTO> getMachineMasterDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String BranchCode) {

		String methodName = "getMachineMasterDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = machineMasterService.getMachineMasterDocId(orgId, finYear, BranchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MachineMaster DocId information retrieved successfully");
			responseObjectsMap.put("machineMasterDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve MachineMaster DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineMasterByDocId")
	public ResponseEntity<ResponseDTO> getMachineMasterByDocId(@RequestParam Long orgId, @RequestParam String docId) {
		String methodName = "getMachineMasterByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		MachineMasterVO machineMasterVO = new MachineMasterVO();
		try {
			machineMasterVO = machineMasterService.getMachineMasterByDocId(orgId, docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MachineMaster information get successfully By docid");
			responseObjectsMap.put("machineMasterVO", machineMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MachineMaster information receive failed By docid", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllMachineMasterByOrgId")
	public ResponseEntity<ResponseDTO> getAllMachineMasterByOrgId(@RequestParam Long orgId,
			@RequestParam String BranchCode) {
		String methodName = "getAllMachineMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MachineMasterVO> machineMasterVOs = new ArrayList<>();
		try {
			machineMasterVOs = machineMasterService.getAllMachineMasterByOrgId(orgId, BranchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MachineMaster information get successfully By OrgId");
			responseObjectsMap.put("machineMasterVO", machineMasterVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MachineMaster information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllMachineMasterById")
	public ResponseEntity<ResponseDTO> getAllMachineMasterById(@RequestParam(required = false) Long id) {
		String methodName = "getAllMachineMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		// List<MachineMasterVO> machineMasterVOs = new ArrayList<>();
		Optional<MachineMasterVO> machineMasterVOs = null;
		try {
			machineMasterVOs = machineMasterService.getAllMachineMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MachineMaster information get successfully By Id");
			responseObjectsMap.put("machineMasterVO", machineMasterVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MachineMaster information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/uploadMachineAttachementsInBloob")
	public ResponseEntity<ResponseDTO> uploadMachineAttachementsInBloob(@RequestParam("file") MultipartFile file,
			@RequestParam Long id) {
		String methodName = "uploadMachineAttachementsInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		MachineMasterVO machineMasterVO = null;
		try {
			machineMasterVO = machineMasterService.uploadMachineAttachementsInBloob(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload attachements", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Attachments Successfully Upload");
			responseObjectsMap.put("machineMasterVO", machineMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Attachments Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// STOCKLOCATION

	@PutMapping("/updateCreateStockLocation")
	public ResponseEntity<ResponseDTO> updateCreateStockLocation(
			@Valid @RequestBody StockLocationDTO stockLocationDTO) {
		String methodName = "updateCreateStockLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> stockLocationVO = machineMasterService.updateCreateStockLocation(stockLocationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, stockLocationVO.get("message"));
			responseObjectsMap.put("stockLocationVO", stockLocationVO.get("stockLocationVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllStockLocationByOrgId")
	public ResponseEntity<ResponseDTO> getAllStockLocationByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getAllStockLocationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StockLocationVO> stockLocationVOs = new ArrayList<>();
		try {
			stockLocationVOs = machineMasterService.getAllStockLocationByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Stock Location information get successfully By OrgId");
			responseObjectsMap.put("stockLocationVO", stockLocationVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Stock Location information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllStockLocationById")
	public ResponseEntity<ResponseDTO> getAllStockLocationById(@RequestParam(required = false) Long id) {
		String methodName = "getAllMachineMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		// List<MachineMasterVO> machineMasterVOs = new ArrayList<>();
		Optional<StockLocationVO> stockLocationVO = null;
		try {
			stockLocationVO = machineMasterService.getAllStockLocationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock Location information get successfully By Id");
			responseObjectsMap.put("stockLocationVO", stockLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Stock Location information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getCompanyForStockLocation")
	public ResponseEntity<ResponseDTO> getCompanyForStockLocation(@RequestParam(required = false) Long orgId) {
		String methodName = "getCompanyForStockLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> companyVO = new ArrayList<>();
		try {
			companyVO = machineMasterService.getCompanyForStockLocation(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Company information get successfully By OrgId");
			responseObjectsMap.put("companyVO", companyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Company information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// DRAWING MASTER

	@PutMapping("/updateDrawingMaster")
	public ResponseEntity<ResponseDTO> updateDrawingMaster(@Valid @RequestBody DrawingMasterDTO drawingMasterDTO) {
		String methodName = "updateDrawingMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> drawingMasterVO = machineMasterService.updateDrawingMaster(drawingMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, drawingMasterVO.get("message"));
			responseObjectsMap.put("drawingMasterVO", drawingMasterVO.get("drawingMasterVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllDrawingMasterByOrgId")
	public ResponseEntity<ResponseDTO> getAllDrawingMasterByOrgId(@RequestParam Long orgId,
			@RequestParam String branchCode) {
		String methodName = "getAllDrawingMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DrawingMasterVO> drawingMasterVOs = new ArrayList<>();
		try {
			drawingMasterVOs = machineMasterService.getAllDrawingMasterByOrgId(orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Drawing Master information get successfully By OrgId");
			responseObjectsMap.put("drawingMasterVO", drawingMasterVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Drawing Master information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllDrawingMasterById")
	public ResponseEntity<ResponseDTO> getAllDrawingMasterById(@RequestParam(required = false) Long id) {
		String methodName = "getAllDrawingMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<DrawingMasterVO> drawingMasterVO = null;
		try {
			drawingMasterVO = machineMasterService.getAllDrawingMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Drawing Master information get successfully By Id");
			responseObjectsMap.put("drawingMasterVO", drawingMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Drawing Master information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getFGSFGPartDetailsForDrawingMaster")
	public ResponseEntity<ResponseDTO> getFGSFGPartDetailsForDrawingMaster(@RequestParam(required = false) Long orgId) {
		String methodName = "getFGSFGPartDetailsForDrawingMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> drawingMasterVO = new ArrayList<>();
		try {
			drawingMasterVO = machineMasterService.getFGSFGPartDetailsForDrawingMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FGSFGPartDetails information get successfully By OrgId");
			responseObjectsMap.put("drawingMasterVO", drawingMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FGSFGPartDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDrawingMasterDocId")
	public ResponseEntity<ResponseDTO> getDrawingMasterDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getDrawingMasterDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = machineMasterService.getDrawingMasterDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DrawingMaster DocId information retrieved successfully");
			responseObjectsMap.put("drawingMasterDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DrawingMaster DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// DRAWING MASTER MULTIPLE FILE UPLOAD

	@PutMapping("/uploadDrawingAttachmentsInBloob")
	public ResponseEntity<ResponseDTO> uploadDrawingAttachmentsInBloob(@RequestParam("id") Long headerId,
			@RequestParam("files") List<MultipartFile> files) {

		String methodName = "uploadDrawingAttachmentsInBloob()";
		LOGGER.debug("Starting method: " + methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			if (files.isEmpty()) {
				throw new IllegalArgumentException("No files uploaded.");
			}

			// Pass header ID and files to service
			List<DrawingMasterAttachmentsVO> savedAttachments = machineMasterService
					.uploadDrawingAttachmentsInBloob(headerId, files);

			responseObjectsMap.put("message", "DrawingAttachments Attachments successfully uploaded.");
			responseObjectsMap.put("attachments", savedAttachments);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("Error uploading attachments", e);
			responseDTO = createServiceResponseError(responseObjectsMap, "DrawingAttachments Attachment upload failed",
					e.getMessage());
		}

		LOGGER.debug("Ending method: " + methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/uploadDrawingDetailsAttachmentsInBloob")
	public ResponseEntity<ResponseDTO> uploadDrawingDetailsAttachmentsInBloob(@RequestParam("id") Long headerId,
			@RequestParam("files") List<MultipartFile> files) {

		String methodName = "uploadDrawingDetailsAttachmentsInBloob()";
		LOGGER.debug("Starting method: " + methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			if (files.isEmpty()) {
				throw new IllegalArgumentException("No files uploaded.");
			}

			// Pass header ID and files to service
			List<DrawingMasterDetailsVO> savedAttachments = machineMasterService
					.uploadDrawingDetailsAttachmentsInBloob(headerId, files);

			responseObjectsMap.put("message", "DrawingDetails Attachments successfully uploaded.");
			responseObjectsMap.put("attachments", savedAttachments);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("Error uploading attachments", e);
			responseDTO = createServiceResponseError(responseObjectsMap, "DrawingDetails Attachment upload failed",
					e.getMessage());
		}

		LOGGER.debug("Ending method: " + methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/createUpdateMachineMaster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateMachineMaster(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateMachineMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = machineMasterService.createUpdateMachineMaster(files, docId,
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

	@GetMapping("/files/**")
	public ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException {
		return machineMasterService.viewFile(request);
	}

	@GetMapping("/MachineMaster/{id}")
	public ResponseEntity<List<ImageResponseDTO>> getMachineMasterImages(@PathVariable Long id) throws Exception {

		List<ImageResponseDTO> response = machineMasterService.getMachineMasterImages(id);

		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/createUpdateDrawingMasterDocumentImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdatePurchaseImages(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module, @RequestParam List<String> fileNames) {

		String methodName = "createUpdateDrawingMasterDocumentImage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = machineMasterService.createUpdateDrawingMasterDocumentImage(files,
					docId, screenName, module, fileNames);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("drawingMasterVO", serviceResponse.get("drawingMasterVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewDocumets/**")
	public ResponseEntity<byte[]> viewDocumets(HttpServletRequest request) throws IOException {
		return machineMasterService.viewDocumets(request);
	}

	@PostMapping(value = "/createUpdateDrawingMasterDocumentSubImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateDrawingMasterDocumentSubImage(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module, @RequestParam List<String> fileNames) {

		String methodName = "createUpdateDrawingMasterDocumentSubImage()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = machineMasterService.createUpdateDrawingMasterDocumentSubImage(files,
					docId, screenName, module, fileNames);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("drawingMasterVO", serviceResponse.get("drawingMasterVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewDocumetsSub/**")
	public ResponseEntity<byte[]> viewDocumetsSub(HttpServletRequest request) throws IOException {
		return machineMasterService.viewDocumetsSub(request);
	}
}
