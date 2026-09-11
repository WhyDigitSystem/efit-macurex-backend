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

import com.efitops.basesetup.ResponseDTO.DailyInspectionCumRejectionDataResponseDTO;
import com.efitops.basesetup.ResponseDTO.InstrumentCalibrationResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.DailyInspectionCumRejectionDataDTO;
import com.efitops.basesetup.dto.InstrumentCalibrationDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SupplierResponseEntryDTO;
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

			response = vendorComplaintService.getDailyInspectionCumRejectionDataDocId(orgId,
					financialYear);

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
}
