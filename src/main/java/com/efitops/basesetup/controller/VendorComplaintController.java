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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.AuthorizationForBreakdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.CategoryMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.CauseMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDataResponseDTO;
import com.efitops.basesetup.ResponseDTO.FlashNCReportResponseDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineToolBreakdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineToolsScrapNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.PMCheckListMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.SetUpApprovalResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierChangeRequestResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.AuthorizationForBreakdownDTO;
import com.efitops.basesetup.dto.CategoryMasterDTO;
import com.efitops.basesetup.dto.CauseMasterDTO;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDTO;
import com.efitops.basesetup.dto.FlashNCReportDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDTO;
import com.efitops.basesetup.dto.MachineToolBreakdownDTO;
import com.efitops.basesetup.dto.MachineToolRectificationDTO;
import com.efitops.basesetup.dto.MachineToolsScrapNoteDTO;
import com.efitops.basesetup.dto.PMCheckListMasterDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SetUpApprovalDTO;
import com.efitops.basesetup.dto.SupplierChangeRequestDTO;
import com.efitops.basesetup.dto.SupplierResponseEntryDTO;
import com.efitops.basesetup.dto.VendorComplaintEntryDTO;
import com.efitops.basesetup.entity.MachineToolRectificationResponseDTO;
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

	@PutMapping("/updateCreateSupplierResponseEntry")
	public ResponseEntity<ResponseDTO> updateCreateSupplierResponseEntry(
			@RequestBody SupplierResponseEntryDTO supplierResponseEntryDTO) {

		String methodName = "updateCreateSupplierResponseEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = vendorComplaintService
					.updateCreateSupplierResponseEntry(supplierResponseEntryDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("supplierResponseEntryVO", responseMap.get("supplierResponseEntryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSupplierResponseEntryById")
	public ResponseEntity<ResponseDTO> getSupplierResponseEntryById(@RequestParam Long id) {

		String methodName = "getSupplierResponseEntryById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		SupplierResponseEntryResponseDTO response = null;

		try {

			response = vendorComplaintService.getSupplierResponseEntryById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Response Entry information retrieved successfully");

			responseObjectsMap.put("supplierResponseEntryVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Supplier Response Entry information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSupplierResponseEntryByOrgId")
	public ResponseEntity<ResponseDTO> getSupplierResponseEntryByOrgId(@RequestParam Long orgId) {

		String methodName = "getSupplierResponseEntryByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<SupplierResponseEntryResponseDTO> responseList = new ArrayList<>();

		try {

			responseList = vendorComplaintService.getSupplierResponseEntryByOrgId(orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Response Entry information retrieved successfully");

			responseObjectsMap.put("supplierResponseEntryVO", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Supplier Response Entry information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getComplaintNoDropdownForSupplierResponseEntry")
	public ResponseEntity<ResponseDTO> getComplaintNoDropdownForSupplierResponseEntry(@RequestParam Long orgId) {

		String methodName = "getComplaintNoDropdownForSupplierResponseEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> responseList = new ArrayList<>();

		try {

			responseList = vendorComplaintService.getComplaintNoDropdownForSupplierResponseEntry(orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Complaint No information retrieved successfully");

			responseObjectsMap.put("complaintList", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Complaint No information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSupplierResponseEntryDocId")
	public ResponseEntity<ResponseDTO> getSupplierResponseEntryDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getSupplierResponseEntryDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String response = null;

		try {

			response = vendorComplaintService.getSupplierResponseEntryDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Response Entry DocId retrieved successfully");

			responseObjectsMap.put("docId", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Supplier Response Entry DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDropDownForSupplierResponseEntry")
	public ResponseEntity<ResponseDTO> getItemDropDownForSupplierResponseEntry(@RequestParam Long supplierId,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getItemDropDownForSupplierResponseEntry()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> response = null;

		try {

			response = vendorComplaintService.getItemDropDownForSupplierResponseEntry(supplierId, orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Items retrieved successfully");

			responseObjectsMap.put("items", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Items", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateInstrumentCalibration")
	public ResponseEntity<ResponseDTO> updateCreateInstrumentCalibration(
			@RequestBody InstrumentCalibrationDTO instrumentCalibrationDTO) {

		String methodName = "updateCreateInstrumentCalibration()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = vendorComplaintService
					.updateCreateInstrumentCalibration(instrumentCalibrationDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("instrumentCalibrationVO", responseMap.get("instrumentCalibrationVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInstrumentCalibrationByOrgId")
	public ResponseEntity<ResponseDTO> getInstrumentCalibrationByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getInstrumentCalibrationByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			List<InstrumentCalibrationResponseDTO> response = vendorComplaintService
					.getInstrumentCalibrationByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Instrument Calibration Details Retrieved Successfully");

			responseObjectsMap.put("instrumentCalibrationResponseDTO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInstrumentCalibrationById")
	public ResponseEntity<ResponseDTO> getInstrumentCalibrationById(@RequestParam Long id) {

		String methodName = "getInstrumentCalibrationById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			InstrumentCalibrationResponseDTO response = vendorComplaintService.getInstrumentCalibrationById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Instrument Calibration Details Retrieved Successfully");

			responseObjectsMap.put("instrumentCalibrationResponseDTO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineNoForInstrumentCalibration")
	public ResponseEntity<ResponseDTO> getMachineNoForInstrumentCalibration(@RequestParam Long machineId,
			@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getMachineNoForInstrumentCalibration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		List<Map<String, Object>> response = null;

		try {

			response = vendorComplaintService.getMachineNoForInstrumentCalibration(machineId, branch, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Machine Details Retrieved Successfully");

			responseObjectsMap.put("machineDetails", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Machine Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateDailyInspectionCumRejectionData")
	public ResponseEntity<ResponseDTO> updateCreateDailyInspectionCumRejectionData(
			@RequestBody DailyInspectionCumRejectionDataDTO dailyInspectionCumRejectionDataDTO) {

		String methodName = "updateCreateDailyInspectionCumRejectionData()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = vendorComplaintService
					.updateCreateDailyInspectionCumRejectionData(dailyInspectionCumRejectionDataDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("dailyInspectionCumRejectionDataVO",
					responseMap.get("dailyInspectionCumRejectionDataVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDailyInspectionCumRejectionDataByOrgId")
	public ResponseEntity<ResponseDTO> getDailyInspectionCumRejectionDataByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getDailyInspectionCumRejectionDataByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			List<DailyInspectionCumRejectionDataResponseDTO> response = vendorComplaintService
					.getDailyInspectionCumRejectionDataByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Daily Inspection Cum Rejection Data Retrieved Successfully");

			responseObjectsMap.put("dailyInspectionCumRejectionData", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDailyInspectionCumRejectionDataById")
	public ResponseEntity<ResponseDTO> getDailyInspectionCumRejectionDataById(@RequestParam Long id) {

		String methodName = "getDailyInspectionCumRejectionDataById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			DailyInspectionCumRejectionDataResponseDTO response = vendorComplaintService
					.getDailyInspectionCumRejectionDataById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Daily Inspection Cum Rejection Data Retrieved Successfully");

			responseObjectsMap.put("dailyInspectionCumRejectionData", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFromLocationDropdownForDailyInspectionCumRejection")
	public ResponseEntity<ResponseDTO> getFromLocationDropdownForDailyInspectionCumRejection(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getFromLocationDropdownForDailyInspectionCumRejection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getFromLocationDropdownForDailyInspectionCumRejection(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "From Location Details Retrieved Successfully");

			responseObjectsMap.put("locationDetails", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getReworkLocationDropdownForDailyInspectionCumRejection")
	public ResponseEntity<ResponseDTO> getReworkLocationDropdownForDailyInspectionCumRejection(
			@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getReworkLocationDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> responseList = new ArrayList<>();

		try {

			responseList = vendorComplaintService.getReworkLocationDropdownForDailyInspectionCumRejection(branch,
					orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Rework Location information retrieved successfully");

			responseObjectsMap.put("reworkLocationList", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Rework Location information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRejectionLocationDropdownForDailyInspectionCumRejection")
	public ResponseEntity<ResponseDTO> getRejectionLocationDropdownForDailyInspectionCumRejection(
			@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getRejectionLocationDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> responseList = new ArrayList<>();

		try {

			responseList = vendorComplaintService.getRejectionLocationDropdownForDailyInspectionCumRejection(branch,
					orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Rejection Location information retrieved successfully");

			responseObjectsMap.put("rejectionLocationList", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Rejection Location information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getScrapLocationDropdownForDailyInspectionCumRejection")
	public ResponseEntity<ResponseDTO> getScrapLocationDropdownForDailyInspectionCumRejection(@RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getScrapLocationDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> responseList = new ArrayList<>();

		try {

			responseList = vendorComplaintService.getScrapLocationDropdownForDailyInspectionCumRejection(branch, orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Rejection Location information retrieved successfully");

			responseObjectsMap.put("rejectionLocationList", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Rejection Location information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDailyInspectionCumRejectionDataDocId")
	public ResponseEntity<ResponseDTO> getDailyInspectionCumRejectionDataDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getDailyInspectionCumRejectionDataDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String response = null;

		try {

			response = vendorComplaintService.getDailyInspectionCumRejectionDataDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Daily Inspection Cum Rejection Data DocId Retrieved Successfully");

			responseObjectsMap.put("docId", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Daily Inspection Cum Rejection Data DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateSetUpApproval")
	public ResponseEntity<ResponseDTO> updateCreateSetUpApproval(@RequestBody SetUpApprovalDTO dto) {

		String methodName = "updateCreateSetUpApproval()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			Map<String, Object> response = vendorComplaintService.updateCreateSetUpApproval(dto);

			responseObjectsMap.putAll(response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSetUpApprovalById")
	public ResponseEntity<ResponseDTO> getSetUpApprovalById(@RequestParam Long id) {

		String methodName = "getSetUpApprovalById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			SetUpApprovalResponseDTO response = vendorComplaintService.getSetUpApprovalById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Set Up Approval Retrieved Successfully");

			responseObjectsMap.put("setUpApproval", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSetUpApprovalByOrgId")
	public ResponseEntity<ResponseDTO> getSetUpApprovalByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getSetUpApprovalByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<SetUpApprovalResponseDTO> response = vendorComplaintService.getSetUpApprovalByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Set Up Approval Retrieved Successfully");

			responseObjectsMap.put("setUpApproval", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFgSfgItemDropdownForSetUpApproval")
	public ResponseEntity<ResponseDTO> getFgSfgItemDropdownForSetUpApproval(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getFgSfgItemDropdownForSetUpApproval()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getFgSfgItemDropdownForSetUpApproval(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FG and SFG Item Details Retrieved Successfully");

			responseObjectsMap.put("itemDetails", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessSheetNoForSetUpApproval")
	public ResponseEntity<ResponseDTO> getProcessSheetNoForSetUpApproval(@RequestParam Long item,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getProcessSheetNoForSetUpApproval()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getProcessSheetNoForSetUpApproval(item, orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Process Sheet Details Retrieved Successfully");

			responseObjectsMap.put("processSheetDetails", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getControlPlanDetailsForSetUpApproval")
	public ResponseEntity<ResponseDTO> getControlPlanDetailsForSetUpApproval(@RequestParam Long item,
			@RequestParam String processSheetNo, @RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getControlPlanDetailsForSetUpApproval()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getControlPlanDetailsForSetUpApproval(item,
					processSheetNo, orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Control Plan Details Retrieved Successfully");

			responseObjectsMap.put("controlPlanDetails", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSetUpApprovalDocId")
	public ResponseEntity<ResponseDTO> getSetUpApprovalDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getSetUpApprovalDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String response = null;

		try {

			response = vendorComplaintService.getSetUpApprovalDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Set Up Approval DocId Retrieved Successfully");

			responseObjectsMap.put("docId", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to Retrieve Set Up Approval DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	flash NC Report

	/*
	 * Create / Update Flash NC Report
	 */
	@PostMapping(value = "/updateCreateFlashNCReport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseDTO updateCreateFlashNCReport(@RequestPart("flashNCReportVO") FlashNCReportDTO flashNCReportDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files,
			@RequestPart(value = "images", required = false) MultipartFile[] images) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = vendorComplaintService.updateCreateFlashNCReport(flashNCReportDTO, files,
					images);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));

			responseObjectsMap.put("flashNCReportVO", response.get("flashNCReportVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return responseDTO;
	}

	/*
	 * Get Flash NC Report By ID
	 */
	@GetMapping("/getFlashNCReportById")
	public ResponseEntity<ResponseDTO> getFlashNCReportById(@RequestParam Long id) {

		String methodName = "getFlashNCReportById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			FlashNCReportResponseDTO response = vendorComplaintService.getFlashNCReportById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flash NC Report Retrieved Successfully");

			responseObjectsMap.put("flashNCReportVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	/*
	 * Get Flash NC Report By Organization And Branch
	 */
	@GetMapping("/getFlashNCReportByOrgId")
	public ResponseEntity<ResponseDTO> getFlashNCReportByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getFlashNCReportByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			List<FlashNCReportResponseDTO> response = vendorComplaintService.getFlashNCReportByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flash NC Report Details Retrieved Successfully");

			responseObjectsMap.put("flashNCReportList", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	/*
	 * Get Flash NC Report Doc ID
	 */
	@GetMapping("/getFlashNCReportDocId")
	public ResponseEntity<ResponseDTO> getFlashNCReportDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getFlashNCReportDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String response = null;

		try {

			response = vendorComplaintService.getFlashNCReportDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Flash NC Report DocId Retrieved Successfully");

			responseObjectsMap.put("docId", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Flash NC Report DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getQualityEmployeesForFlashNCReport")
	public ResponseEntity<ResponseDTO> getQualityEmployeesForFlashNCReport(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getQualityEmployeesForFlashNCReport()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getQualityEmployeesForFlashNCReport(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quality Employees Retrieved Successfully");

			responseObjectsMap.put("employeeDetails", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/viewFile/**")
	public ResponseEntity<byte[]> viewFlashNCReportFile(HttpServletRequest request) {

		String methodName = "viewFlashNCReportFile()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {

			return vendorComplaintService.viewFlashNCReportFile(request);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/getFromDeptDropdownForFlashNCReport")
	public ResponseEntity<ResponseDTO> getFromDeptDropdownForFlashNCReport(@RequestParam Long listOfValuesId) {

		String methodName = "getFromDeptDropdownForFlashNCReport()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getFromDeptDropdownForFlashNCReport(listOfValuesId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "From Department Retrieved Successfully");

			responseObjectsMap.put("fromDepartment", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getToDepartmentDropdownForFlashNCReport")
	public ResponseEntity<ResponseDTO> getToDepartmentDropdownForFlashNCReport(@RequestParam Long listOfValuesId,
			@RequestParam Long fromDept) {

		String methodName = "getToDepartmentDropdownForFlashNCReport()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getToDepartmentDropdownForFlashNCReport(listOfValuesId, fromDept);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "To Department Retrieved Successfully");

			responseObjectsMap.put("toDepartment", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMRINGRNDropdownForFlashNCReport")
	public ResponseEntity<ResponseDTO> getMRINGRNDropdownForFlashNCReport(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getMRINGRNDropdownForFlashNCReport()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;

		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getMRINGRNDropdownForFlashNCReport(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MRIN/GRN Details Retrieved Successfully");

			responseObjectsMap.put("mrinGrnDropdown", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateSupplierChangeRequest")
	public ResponseDTO updateCreateSupplierChangeRequest(
			@RequestBody SupplierChangeRequestDTO supplierChangeRequestDTO) {

		String methodName = "updateCreateSupplierChangeRequest()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			Map<String, Object> response = vendorComplaintService
					.updateCreateSupplierChangeRequest(supplierChangeRequestDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));

			responseObjectsMap.put("supplierChangeRequestVO", response.get("supplierChangeRequestVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getSupplierChangeRequestDocId")
	public ResponseDTO getSupplierChangeRequestDocId(@RequestParam Long orgId, @RequestParam String financialYear) {

		String methodName = "getSupplierChangeRequestDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			String docId = vendorComplaintService.getSupplierChangeRequestDocId(orgId, financialYear, "SCR");

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Change Request DocId Retrieved Successfully");

			responseObjectsMap.put("docId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getSupplierChangeRequestById")
	public ResponseDTO getSupplierChangeRequestById(@RequestParam Long id) {

		String methodName = "getSupplierChangeRequestById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			SupplierChangeRequestResponseDTO response = vendorComplaintService.getSupplierChangeRequestById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier Change Request Retrieved Successfully");

			responseObjectsMap.put("supplierChangeRequestVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getSupplierChangeRequestByOrgId")
	public ResponseDTO getSupplierChangeRequestByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getSupplierChangeRequestByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<SupplierChangeRequestResponseDTO> response = vendorComplaintService
					.getSupplierChangeRequestByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier Change Request Retrieved Successfully");

			responseObjectsMap.put("supplierChangeRequestVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getVendorCodeDropdownForSupplierChangeRequest")
	public ResponseDTO getVendorCodeDropdownForSupplierChangeRequest(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getVendorCodeDropdownForSupplierChangeRequest()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getVendorCodeDropdownForSupplierChangeRequest(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Vendor Code Retrieved Successfully");

			responseObjectsMap.put("vendorCode", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getPurchaseEmployeesDropdownForSupplierChangeRequest")
	public ResponseDTO getPurchaseEmployeesDropdownForSupplierChangeRequest(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getPurchaseEmployeesDropdownForSupplierChangeRequest()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getPurchaseEmployeesDropdownForSupplierChangeRequest(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Employees Retrieved Successfully");

			responseObjectsMap.put("purchaseEmployees", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getTDCEmployeesDropdownForSupplierChangeRequest")
	public ResponseDTO getTDCEmployeesDropdownForSupplierChangeRequest(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getTDCEmployeesDropdownForSupplierChangeRequest()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getTDCEmployeesDropdownForSupplierChangeRequest(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TDC Employees Retrieved Successfully");

			responseObjectsMap.put("tdcEmployees", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getProductionEmployeesDropdownSupplierChangeRequest")
	public ResponseDTO getProductionEmployeesDropdownSupplierChangeRequest(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getProductionEmployeesDropdownSupplierChangeRequest()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getProductionEmployeesDropdownSupplierChangeRequest(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Production Employees Retrieved Successfully");

			responseObjectsMap.put("productionEmployees", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@PutMapping("/updateCreateCategoryMaster")
	public ResponseDTO updateCreateCategoryMaster(@RequestBody CategoryMasterDTO categoryMasterDTO) {

		String methodName = "updateCreateCategoryMaster()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			Map<String, Object> response = vendorComplaintService.updateCreateCategoryMaster(categoryMasterDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));

			responseObjectsMap.put("categoryMasterVO", response.get("categoryMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getCategoryMasterById")
	public ResponseDTO getCategoryMasterById(@RequestParam Long id) {

		String methodName = "getCategoryMasterById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			CategoryMasterResponseDTO response = vendorComplaintService.getCategoryMasterById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Category Master Retrieved Successfully");

			responseObjectsMap.put("categoryMasterVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getCategoryMasterByOrgId")
	public ResponseDTO getCategoryMasterByOrgId(@RequestParam Long orgId) {

		String methodName = "getCategoryMasterByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<CategoryMasterResponseDTO> response = vendorComplaintService.getCategoryMasterByOrgId(orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Category Master Retrieved Successfully");

			responseObjectsMap.put("categoryMasterVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

//	cause master

	@PutMapping("/updateCreateCauseMaster")
	public ResponseDTO updateCreateCauseMaster(@RequestBody CauseMasterDTO causeMasterDTO) {

		String methodName = "updateCreateCauseMaster()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			Map<String, Object> response = vendorComplaintService.updateCreateCauseMaster(causeMasterDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));

			responseObjectsMap.put("causeMasterVO", response.get("causeMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getCauseMasterById")
	public ResponseDTO getCauseMasterById(@RequestParam Long id) {

		String methodName = "getCauseMasterById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			CauseMasterResponseDTO response = vendorComplaintService.getCauseMasterById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Cause Master Retrieved Successfully");

			responseObjectsMap.put("causeMasterVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getCauseMasterByOrgId")
	public ResponseDTO getCauseMasterByOrgId(@RequestParam Long orgId) {

		String methodName = "getCauseMasterByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<CauseMasterResponseDTO> response = vendorComplaintService.getCauseMasterByOrgId(orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Cause Master Retrieved Successfully");

			responseObjectsMap.put("causeMasterVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

//	pmchecklist  master

	@PutMapping("/updateCreatePMCheckListMaster")
	public ResponseDTO updateCreatePMCheckListMaster(@RequestBody PMCheckListMasterDTO pmCheckListMasterDTO) {

		String methodName = "updateCreatePMCheckListMaster()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			Map<String, Object> response = vendorComplaintService.updateCreatePMCheckListMaster(pmCheckListMasterDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));

			responseObjectsMap.put("pmCheckListMasterVO", response.get("pmCheckListMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return responseDTO;
	}

	@GetMapping("/getPMCheckListMasterById")
	public ResponseEntity<ResponseDTO> getPMCheckListMasterById(@RequestParam Long id) {

		String methodName = "getPMCheckListMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			PMCheckListMasterResponseDTO response = vendorComplaintService.getPMCheckListMasterById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PM Check List Master Retrieved Successfully");

			responseObjectsMap.put("pmCheckListMasterVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPMCheckListMasterByOrgId")
	public ResponseEntity<ResponseDTO> getPMCheckListMasterByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getPMCheckListMasterByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			List<PMCheckListMasterResponseDTO> response = vendorComplaintService.getPMCheckListMasterByOrgId(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PM Check List Master Retrieved Successfully");

			responseObjectsMap.put("pmCheckListMasterVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getToolMachineCategoryForPMCheckListMaster")
	public ResponseEntity<ResponseDTO> getToolMachineCategoryForPMCheckListMaster(@RequestParam Long orgId,
			@RequestParam String pmCheckListFor) {

		String methodName = "getToolMachineCategoryForPMCheckListMaster()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getToolMachineCategoryForPMCheckListMaster(orgId, pmCheckListFor);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tool Machine Category Retrieved Successfully");

			responseObjectsMap.put("toolMachineCategory", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getActivityForPMCheckListMaster")
	public ResponseEntity<ResponseDTO> getActivityForPMCheckListMaster(@RequestParam Long department,
			@RequestParam Long orgId) {

		String methodName = "getActivityForPMCheckListMaster()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getActivityForPMCheckListMaster(department,
					orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Activity Retrieved Successfully");

			responseObjectsMap.put("activity", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	machineTool breakdown

	@PostMapping(value = "/updateCreateMachineToolBreakdown", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseDTO updateCreateMachineToolBreakdown(
			@RequestPart("machineToolBreakdownVO") MachineToolBreakdownDTO machineToolBreakdownDTO,
//			@RequestBody MachineToolBreakdownDTO machineToolBreakdownDTO,

			@RequestPart(value = "files", required = false) MultipartFile[] files,
			@RequestPart(value = "images", required = false) MultipartFile[] images) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = vendorComplaintService
					.updateCreateMachineToolBreakdown(machineToolBreakdownDTO, files, images);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));

			responseObjectsMap.put("machineToolBreakdownVO", response.get("machineToolBreakdownVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return responseDTO;
	}

	/*
	 * Get Machine Tool Breakdown By ID
	 */
	@GetMapping("/getMachineToolBreakdownById")
	public ResponseEntity<ResponseDTO> getMachineToolBreakdownById(@RequestParam Long id) {

		String methodName = "getMachineToolBreakdownById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			MachineToolBreakdownResponseDTO response = vendorComplaintService.getMachineToolBreakdownById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Machine Tool Breakdown Retrieved Successfully");

			responseObjectsMap.put("machineToolBreakdownVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	/*
	 * Get Machine Tool Breakdown By Organization And Branch
	 */
	@GetMapping("/getMachineToolBreakdownByOrgId")
	public ResponseEntity<ResponseDTO> getMachineToolBreakdownByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getMachineToolBreakdownByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			List<MachineToolBreakdownResponseDTO> response = vendorComplaintService
					.getMachineToolBreakdownByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Machine Tool Breakdown Details Retrieved Successfully");

			responseObjectsMap.put("machineToolBreakdownList", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	/*
	 * Get Machine Tool Breakdown Doc ID
	 */
	@GetMapping("/getMachineToolBreakdownDocId")
	public ResponseEntity<ResponseDTO> getMachineToolBreakdownDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getMachineToolBreakdownDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String response = null;

		try {

			response = vendorComplaintService.getMachineToolBreakdownDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Machine Tool Breakdown DocId Retrieved Successfully");

			responseObjectsMap.put("docId", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Machine Tool Breakdown DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	/*
	 * View Machine Tool Breakdown File
	 */
	@GetMapping("/machineToolBreakdown/viewFile/**")
	public ResponseEntity<byte[]> viewMachineToolBreakdownFile(HttpServletRequest request) {

		String methodName = "viewMachineToolBreakdownFile()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {

			return vendorComplaintService.viewMachineToolBreakdownFile(request);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/getMachineToolForBreakdown")
	public ResponseEntity<ResponseDTO> getMachineToolForBreakdown(@RequestParam Long toolCategoryId,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getMachineToolForBreakdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getMachineToolForBreakdown(toolCategoryId,
					orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Machine/Tool Retrieved Successfully");

			responseObjectsMap.put("machineToolList", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	machine tool rectification

	@PutMapping("/updateCreateMachineToolRectification")
	public ResponseEntity<ResponseDTO> updateCreateMachineToolRectification(
			@RequestBody MachineToolRectificationDTO dto) {

		String methodName = "updateCreateMachineToolRectification()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = vendorComplaintService.updateCreateMachineToolRectification(dto);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineToolRectificationDocId")
	public ResponseEntity<ResponseDTO> getMachineToolRectificationDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getMachineToolRectificationDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			String docId = vendorComplaintService.getMachineToolRectificationDocId(orgId, financialYear);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Machine Tool Rectification DocId Retrieved Successfully");

			responseObjectsMap.put("docId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineToolRectificationByOrgId")
	public ResponseEntity<ResponseDTO> getMachineToolRectificationByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getMachineToolRectificationByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			List<MachineToolRectificationResponseDTO> response = vendorComplaintService
					.getMachineToolRectificationByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Machine Tool Rectification Details Retrieved Successfully");

			responseObjectsMap.put("machineToolRectificationList", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineToolRectificationById")
	public ResponseEntity<ResponseDTO> getMachineToolRectificationById(@RequestParam Long id) {

		String methodName = "getMachineToolRectificationById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			MachineToolRectificationResponseDTO response = vendorComplaintService.getMachineToolRectificationById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Machine Tool Rectification Details Retrieved Successfully");

			responseObjectsMap.put("machineToolRectificationVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBreakdownDetailsForRectification")
	public ResponseEntity<ResponseDTO> getBreakdownDetailsForRectification(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getBreakdownDetailsForRectification()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getBreakdownDetailsForRectification(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Breakdown Details Retrieved Successfully");

			responseObjectsMap.put("breakdownDetails", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPrepareByForMachineToolRectification")
	public ResponseEntity<ResponseDTO> getPrepareByForMachineToolRectification(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long department) {

		String methodName = "getPrepareByForMachineToolRectification()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService.getPrepareByForMachineToolRectification(orgId,
					branch, department);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Prepare By Employee Retrieved Successfully");

			responseObjectsMap.put("prepareBy", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	Authorization for breakdown

	@PutMapping("/updateCreateAuthorizationForBreakdown")
	public ResponseEntity<ResponseDTO> updateCreateAuthorizationForBreakdown(
			@RequestBody AuthorizationForBreakdownDTO dto) {

		String methodName = "updateCreateAuthorizationForBreakdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			responseObjectsMap = vendorComplaintService.updateCreateAuthorizationForBreakdown(dto);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAuthorizationForBreakdownDocId")
	public ResponseEntity<ResponseDTO> getAuthorizationForBreakdownDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getAuthorizationForBreakdownDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			String docId = vendorComplaintService.getAuthorizationForBreakdownDocId(orgId, financialYear);

			responseObjectsMap.put("docId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAuthorizationForBreakdownById")
	public ResponseEntity<ResponseDTO> getAuthorizationForBreakdownById(@RequestParam Long id) {

		String methodName = "getAuthorizationForBreakdownById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			AuthorizationForBreakdownResponseDTO response = vendorComplaintService.getAuthorizationForBreakdownById(id);

			responseObjectsMap.put("authorizationForBreakdownVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAuthorizationForBreakdownByOrgId")
	public ResponseEntity<ResponseDTO> getAuthorizationForBreakdownByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getAuthorizationForBreakdownByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<AuthorizationForBreakdownResponseDTO> response = vendorComplaintService
					.getAuthorizationForBreakdownByOrgId(orgId, branch);

			responseObjectsMap.put("authorizationForBreakdownVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineToolRectificationDetails")
	public ResponseEntity<ResponseDTO> getMachineToolRectificationDetails(@RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getMachineToolRectificationDetails()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			List<Map<String, Object>> response = vendorComplaintService
					.getMachineToolRectificationDetailsForAuthorizationBreakdown(branch, orgId);

			responseObjectsMap.put("machineToolRectificationDetails", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPMCheckListMasterDocId")
	public ResponseEntity<ResponseDTO> getPMCheckListMasterDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getPMCheckListMasterDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			String docId = vendorComplaintService.getPMCheckListMasterDocId(orgId, financialYear);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PM Check List Master DocId Retrieved Successfully");

			responseObjectsMap.put("docId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineToolsScrapNoteDocId")
	public ResponseEntity<ResponseDTO> getMachineToolsScrapNoteDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getMachineToolsScrapNoteDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String errorMsg = null;

		try {

			String docId = vendorComplaintService.getMachineToolsScrapNoteDocId(orgId, financialYear);

			responseObjectsMap.put("docId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping(value = "/updateCreateMachineToolsScrapNote", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> updateCreateMachineToolsScrapNote(
			@RequestPart("machineToolsScrapNoteDTO") MachineToolsScrapNoteDTO machineToolsScrapNoteDTO,

			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		String methodName = "updateCreateMachineToolsScrapNote()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = vendorComplaintService.updateCreateMachineToolsScrapNote(machineToolsScrapNoteDTO,
					files);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/machineToolsScrapNote/viewFile/**")
	public ResponseEntity<byte[]> viewMachineToolsScrapNoteFile(HttpServletRequest request) throws IOException {

		String methodName = "viewMachineToolsScrapNoteFile()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		ResponseEntity<byte[]> response;

		try {

			response = vendorComplaintService.viewMachineToolsScrapNoteFile(request);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			return ResponseEntity.notFound().build();
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return response;
	}

	@GetMapping("/getMachineToolsScrapNoteById")
	public ResponseEntity<ResponseDTO> getMachineToolsScrapNoteById(@RequestParam Long id) {

		String methodName = "getMachineToolsScrapNoteById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = vendorComplaintService.getMachineToolsScrapNoteById(id);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineToolsScrapNoteByOrgId")
	public ResponseEntity<List<MachineToolsScrapNoteResponseDTO>> getMachineToolsScrapNoteByOrgId(
			@RequestParam Long orgId,@RequestParam Long branch) {

		String methodName = "getMachineToolsScrapNoteByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		List<MachineToolsScrapNoteResponseDTO> responseList = new ArrayList<>();

		try {

			responseList = vendorComplaintService.getMachineToolsScrapNoteByOrgId(orgId,branch);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseList);
	}
}
