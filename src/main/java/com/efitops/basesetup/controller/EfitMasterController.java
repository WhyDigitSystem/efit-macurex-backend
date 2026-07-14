package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.BomDTO;
import com.efitops.basesetup.dto.DepartmentDTO;
import com.efitops.basesetup.dto.DesignationDTO;
import com.efitops.basesetup.dto.EmployeeMasterDTO;
import com.efitops.basesetup.dto.GstDTO;
import com.efitops.basesetup.dto.ItemDTO;
import com.efitops.basesetup.dto.ItemWiseProcessMasterDTO;
import com.efitops.basesetup.dto.MaterialTypeDTO;
import com.efitops.basesetup.dto.MeasuringInstrumentsDTO;
import com.efitops.basesetup.dto.ProcessMasterDTO;
import com.efitops.basesetup.dto.RackMasterDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ShiftDTO;
import com.efitops.basesetup.dto.UomDTO;
import com.efitops.basesetup.entity.BomVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DesignationVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GstVO;
import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;
import com.efitops.basesetup.entity.MaterialTypeVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;
import com.efitops.basesetup.entity.ProcessMasterVO;
import com.efitops.basesetup.entity.RackMasterVO;
import com.efitops.basesetup.entity.ShiftVO;
import com.efitops.basesetup.entity.UomVO;
import com.efitops.basesetup.service.EfitMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/efitmaster")
public class EfitMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(IrnCreditNoteController.class);

	@Autowired
	EfitMasterService efitMasterService;

	@GetMapping("/getItemByOrgId")
	public ResponseEntity<ResponseDTO> getItemByOrgId(@RequestParam Long orgId) {
		String methodName = "getItemByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemVO> itemVO = new ArrayList<>();
		try {
			itemVO = efitMasterService.getItemByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully ByOrgId");
			responseObjectsMap.put("itemVO", itemVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemById")
	public ResponseEntity<ResponseDTO> getItemById(@RequestParam Long id) {
		String methodName = "getItemById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemVO> itemVO = new ArrayList<>();
		try {
			itemVO = efitMasterService.getItemById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully By Id");
			responseObjectsMap.put("itemVO", itemVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateItemMaster")
	public ResponseEntity<ResponseDTO> updateCreateItemMaster(@RequestBody ItemDTO itemDTO) {
		String methodName = "updateCreateItemMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> itemVO = efitMasterService.updateCreateItemMaster(itemDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, itemVO.get("message"));
			responseObjectsMap.put("itemVO", itemVO.get("itemVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPrimaryCodeFromUomMaster")
	public ResponseEntity<ResponseDTO> getPrimaryCodeFromUomMaster(@RequestParam Long orgId) {

		String methodName = "getPrimaryCodeFromUomMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getPrimaryCodeFromUomMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" PrimaryCode from UOM MASTER information retrieved successfully");
			responseObjectsMap.put("ItemVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve  PrimaryCode from UOM MASTER information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStockLocationForItemMaster")
	public ResponseEntity<ResponseDTO> getStockLocationForItemMaster(@RequestParam Long orgId) {

		String methodName = "getStockLocationForItemMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getStockLocationForItemMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" StockLocation for ItemMaster information retrieved successfully");
			responseObjectsMap.put("ItemVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve StockLocation for ItemMaster information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getTaxSlabFromGst")
	public ResponseEntity<ResponseDTO> getTaxSlabFromGst(@RequestParam Long orgId) {

		String methodName = "getTaxSlabFromGst()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getTaxSlabFromGst(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" TaxSlab from GST information retrieved successfully");
			responseObjectsMap.put("ItemVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve TaxSlab from GST information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMaterialTypeForItemMaster")
	public ResponseEntity<ResponseDTO> getMaterialTypeForItemMaster(@RequestParam Long orgId) {

		String methodName = "getMaterialTypeForItemMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getMaterialTypeForItemMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"  MaterialType for Itemmaster information retrieved successfully");
			responseObjectsMap.put("ItemVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve MaterialType for Itemmaster information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMaterialGroupFromMaterialType")
	public ResponseEntity<ResponseDTO> getMaterialGroupFromMaterialType(@RequestParam Long orgId,
			@RequestParam String materialType) {

		String methodName = "getMaterialGroupFromMaterialType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getMaterialGroupFromMaterialType(orgId, materialType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" MaterialGroup from MaterialType information retrieved successfully");
			responseObjectsMap.put("ItemVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve MaterialGroup from MaterialType information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMaterialSubGroupFromMaterialType")
	public ResponseEntity<ResponseDTO> getMaterialSubGroupFromMaterialType(@RequestParam Long orgId,
			@RequestParam String materialType, @RequestParam String materialGroup) {

		String methodName = "getMaterialSubGroupFromMaterialType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getMaterialSubGroupFromMaterialType(orgId, materialType, materialGroup);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" MaterialSubGroup from MaterialType information retrieved successfully");
			responseObjectsMap.put("ItemVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve MaterialSubGroup from MaterialType information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// MeasuringInstrument

	@GetMapping("/getMeasuringInstrumentByOrgId")
	public ResponseEntity<ResponseDTO> getMeasuringInstrumentByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getMeasuringInstrumentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MeasuringInstrumentsVO> measuringInstrumentsVO = new ArrayList<>();
		try {
			measuringInstrumentsVO = efitMasterService.getMeasuringInstrumentByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MeasuringInstruments information get successfully ByOrgId");
			responseObjectsMap.put("measuringInstrumentsVO", measuringInstrumentsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MeasuringInstruments information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getMeasuringInstrumentById")
	public ResponseEntity<ResponseDTO> getMeasuringInstrumentById(@RequestParam Long id) {
		String methodName = "getMeasuringInstrumentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MeasuringInstrumentsVO> measuringInstrumentsVO = new ArrayList<>();
		try {
			measuringInstrumentsVO = efitMasterService.getMeasuringInstrumentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MeasuringInstruments information get successfully By Id");
			responseObjectsMap.put("measuringInstrumentsVO", measuringInstrumentsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MeasuringInstruments information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateMeasuringInstruments")
	public ResponseEntity<ResponseDTO> updateCreateMeasuringInstruments(
			@RequestBody MeasuringInstrumentsDTO measuringInstrumentsDTO) {
		String methodName = "updateCreateMeasuringInstruments()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> measuringInstrumentsVO = efitMasterService
					.updateCreateMeasuringInstruments(measuringInstrumentsDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, measuringInstrumentsVO.get("message"));
			responseObjectsMap.put("measuringInstrumentsVO", measuringInstrumentsVO.get("measuringInstrumentsVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMeasuringInstrumentsDocId")
	public ResponseEntity<ResponseDTO> getMeasuringInstrumentsDocId(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branchCode) {

		String methodName = "getMeasuringInstrumentsDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = efitMasterService.getMeasuringInstrumentsDocId(orgId,finYear,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"MeasuringInstruments DocId information retrieved successfully");
			responseObjectsMap.put("MeasuringInstrumentsDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve MeasuringInstruments DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInstrumentNameFromItemMaster")
	public ResponseEntity<ResponseDTO> getInstrumentNameFromItemMaster(@RequestParam Long orgId) {

		String methodName = "getInstrumentNameFromItemMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getInstrumentNameFromItemMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" InstrumentName from ItemMaster information retrieved successfully");
			responseObjectsMap.put("measuringInstrumentVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve InstrumentName from ItemMaster information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// itemWiseProcessMasterVO

	@GetMapping("/getItemWiseProcessMasterByOrgId")
	public ResponseEntity<ResponseDTO> getItemWiseProcessMasterByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getItemWiseProcessMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemWiseProcessMasterVO> itemWiseProcessMasterVO = new ArrayList<>();
		try {
			itemWiseProcessMasterVO = efitMasterService.getItemWiseProcessMasterByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ItemWiseProcessMaster information get successfully ByOrgId");
			responseObjectsMap.put("ItemWiseProcessMasterVO", itemWiseProcessMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ItemWiseProcessMaster information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemWiseProcessMasterById")
	public ResponseEntity<ResponseDTO> getItemWiseProcessMasterById(@RequestParam Long id) {
		String methodName = "getItemWiseProcessMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ItemWiseProcessMasterVO> itemWiseProcessMasterVO = new ArrayList<>();
		try {
			itemWiseProcessMasterVO = efitMasterService.getItemWiseProcessMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ItemWiseProcessMaster information get successfully By Id");
			responseObjectsMap.put("ItemWiseProcessMasterVO", itemWiseProcessMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ItemWiseProcessMaster information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateItemWiseProcessMaster")
	public ResponseEntity<ResponseDTO> updateCreateItemWiseProcessMaster(
			@RequestBody ItemWiseProcessMasterDTO itemWiseProcessMasterDTO) {
		String methodName = "updateCreateItemWiseProcessMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> itemWiseProcessMasterVO = efitMasterService
					.updateCreateItemWiseProcessMaster(itemWiseProcessMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, itemWiseProcessMasterVO.get("message"));
			responseObjectsMap.put("itemWiseProcessMasterVO", itemWiseProcessMasterVO.get("itemWiseProcessMasterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemAndItemDescforItemWiseProcess")
	public ResponseEntity<ResponseDTO> getItemAndItemDescforItemWiseProcess(@RequestParam Long orgId) {

		String methodName = "getItemAndItemDescforItemWiseProcess()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getItemAndItemDescforItemWiseProcess(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Item and ItemDesc for ItemWiseProcess information retrieved successfully");
			responseObjectsMap.put("itemWiseProcessVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Item and ItemDesc  for ItemWiseProcess  information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessNameFromItemWiseProcess")
	public ResponseEntity<ResponseDTO> getProcessNameFromItemWiseProcess(@RequestParam Long orgId) {

		String methodName = "getProcessNameFromItemWiseProcess()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = efitMasterService.getProcessNameFromItemWiseProcess(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ProcessName from ItemWiseProcess information retrieved successfully");
			responseObjectsMap.put("ItemVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ProcessName from ItemWiseProcess information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemWiseProcessMasterDocId")
	public ResponseEntity<ResponseDTO> getItemWiseProcessMasterDocId(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branchCode) {

		String methodName = "getItemWiseProcessMasterDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = efitMasterService.getItemWiseProcessMasterDocId(orgId,finYear,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ItemWiseProcessMasterDocId information retrieved successfully");
			responseObjectsMap.put("itemWiseProcessMasterDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ItemWiseProcessMasterDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Department

	@GetMapping("/getAllDepartmentByOrgId")
	public ResponseEntity<ResponseDTO> getAllDepartmentByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getAllDepartmentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DepartmentVO> departmentVO = new ArrayList<>();
		try {
			departmentVO = efitMasterService.getAllDepartmentByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Department information get successfully ByOrgId");
			responseObjectsMap.put("departmentVO", departmentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Department information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDepartmentById")
	public ResponseEntity<ResponseDTO> getDepartmentById(@RequestParam Long id) {
		String methodName = "getDepartmentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DepartmentVO> departmentVO = new ArrayList<>();
		try {
			departmentVO = efitMasterService.getDepartmentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Department information get successfully By Id");
			responseObjectsMap.put("departmentVO", departmentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Department information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateDepartment")
	public ResponseEntity<ResponseDTO> createUpdateDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
		String methodName = "createUpdateDepartment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> departmentVO = efitMasterService.createUpdateDepartment(departmentDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, departmentVO.get("message"));
			responseObjectsMap.put("departmentVO", departmentVO.get("departmentVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDepartmentDocId")
	public ResponseEntity<ResponseDTO> getDepartmentDocId(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branchCode) {

		String methodName = "getDepartmentDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = efitMasterService.getDepartmentDocId(orgId,finYear,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DepartmentDocId information retrieved successfully");
			responseObjectsMap.put("departmentDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DepartmentDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Gst

	@GetMapping("/getAllGstByOrgId")
	public ResponseEntity<ResponseDTO> getAllGstByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllGstByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GstVO> gstVO = new ArrayList<>();
		try {
			gstVO = efitMasterService.getAllGstByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Gst information get successfully ByOrgId");
			responseObjectsMap.put("gstVO", gstVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Gst information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGstById")
	public ResponseEntity<ResponseDTO> getGstById(@RequestParam Long id) {
		String methodName = "getGstById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GstVO> gstVO = new ArrayList<>();
		try {
			gstVO = efitMasterService.getGstById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Gst information get successfully By Id");
			responseObjectsMap.put("gstVO", gstVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Gst information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateGst")
	public ResponseEntity<ResponseDTO> createUpdateGst(@Valid @RequestBody GstDTO gstDTO) {
		String methodName = "createUpdateGst()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> gstVO = efitMasterService.createUpdateGst(gstDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, gstVO.get("message"));
			responseObjectsMap.put("gstVO", gstVO.get("gstVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// ProessMaster

	@GetMapping("/getAllProcessMasterByOrgId")
	public ResponseEntity<ResponseDTO> getAllProcessMasterByOrgId(@RequestParam Long orgId,@RequestParam String branchCode ) {
		String methodName = "getAllProcessMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		try {
			processMasterVO = efitMasterService.getAllProcessMasterByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessMaster information get successfully ByOrgId");
			responseObjectsMap.put("processMasterVO", processMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProcessMaster information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getProcessMasterById")
	public ResponseEntity<ResponseDTO> getProcessMasterById(@RequestParam Long id) {
		String methodName = "getProcessMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		try {
			processMasterVO = efitMasterService.getProcessMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessMaster information get successfully By Id");
			responseObjectsMap.put("processMasterVO", processMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ProcessMaster information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateProcessMaster")
	public ResponseEntity<ResponseDTO> createUpdateProcessMaster(
			@Valid @RequestBody ProcessMasterDTO processMasterDTO) {
		String methodName = "createUpdateProcessMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> processMasterVO = efitMasterService.createUpdateProcessMaster(processMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, processMasterVO.get("message"));
			responseObjectsMap.put("processMasterVO", processMasterVO.get("processMasterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessMasterDocId")
	public ResponseEntity<ResponseDTO> getProcessMasterDocId(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branchCode) {

		String methodName = "getProcessMasterDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = efitMasterService.getProcessMasterDocId(orgId,finYear,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ProcessMasterDocId information retrieved successfully");
			responseObjectsMap.put("processMasterDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ProcessMasterDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Material Type

	@GetMapping("/getAllMaterialTypeByOrgId")
	public ResponseEntity<ResponseDTO> getAllMaterialTypeByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllMaterialTypeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		try {
			materialTypeVO = efitMasterService.getAllMaterialTypeByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MaterialType information get successfully ByOrgId");
			responseObjectsMap.put("materialTypeVO", materialTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MaterialType information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getMaterialTypeById")
	public ResponseEntity<ResponseDTO> getMaterialTypeById(@RequestParam Long id) {
		String methodName = "getMaterialTypeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		try {
			materialTypeVO = efitMasterService.getMaterialTypeById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "MaterialType information get successfully By Id");
			responseObjectsMap.put("materialTypeVO", materialTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"MaterialType information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateMaterialType")
	public ResponseEntity<ResponseDTO> createUpdateMaterialType(@Valid @RequestBody MaterialTypeDTO materialTypeDTO) {
		String methodName = "createUpdateMaterialType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> materialTypeVO = efitMasterService.createUpdateMaterialType(materialTypeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, materialTypeVO.get("message"));
			responseObjectsMap.put("materialTypeVO", materialTypeVO.get("materialTypeVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// designation master

	@GetMapping("/getDesignationByOrgId")
	public ResponseEntity<ResponseDTO> getDesignationByOrgId(@RequestParam Long orgId, @RequestParam String branchCode) {
		String methodName = "getDesignationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DesignationVO> designationVO = new ArrayList<>();
		try {
			designationVO = efitMasterService.getDesignationByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Designation information get successfully By OrgId");
			responseObjectsMap.put("designationVO", designationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DesignationVO information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDesignationById")
	public ResponseEntity<ResponseDTO> getDesignationById(@RequestParam Long id) {
		String methodName = "getDesignationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DesignationVO> designationVO = new ArrayList();
		try {
			designationVO = efitMasterService.getDesignationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Designation information get successfully By Id");
			responseObjectsMap.put("designationVO", designationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DesignationVO information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateDesignation")
	public ResponseEntity<ResponseDTO> updateCreateDesignation(@RequestBody DesignationDTO designationdto) {
		String methodName = "updateCreateTaxInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> designationVO = efitMasterService.updateCreateDesignation(designationdto);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, designationVO.get("message"));
			responseObjectsMap.put("designationVO", designationVO.get("designationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDesignationDocId")
	public ResponseEntity<ResponseDTO> getDesignationDocId(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branchCode ) {
		String methodName = "getDesignationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = efitMasterService.getDesignationDocId(orgId,finYear,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Designation DocId information retrieved successfully");
			responseObjectsMap.put("getDesignationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Designation DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getUomByOrgId")
	public ResponseEntity<ResponseDTO> getUomByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getUomByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<UomVO> uomVO = new ArrayList<>();
		try {
			uomVO = efitMasterService.getUomByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Uom information get successfully By OrgId");
			responseObjectsMap.put("uomVO", uomVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "UomVO information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getUomById")
	public ResponseEntity<ResponseDTO> getUomById(@RequestParam Long id) {
		String methodName = "getUomById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<UomVO> uomVO = new ArrayList<>();
		try {
			uomVO = efitMasterService.getUomById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Designation information get successfully By Id");
			responseObjectsMap.put("uomVO", uomVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "UomVO information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateUom")
	public ResponseEntity<ResponseDTO> updateCreateUom(@RequestBody UomDTO uomdto) {
		String methodName = "updateCreateTaxInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> uomVO = efitMasterService.updateCreateUom(uomdto);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, uomVO.get("message"));
			responseObjectsMap.put("uomVO", uomVO.get("uomVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Shiftmaster-

	@GetMapping("/getShiftByOrgId")
	public ResponseEntity<ResponseDTO> getShiftByOrgId(@RequestParam(required = false) Long orgId) {
		String methodName = "getShiftByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ShiftVO> shiftVO = new ArrayList<>();
		try {
			shiftVO = efitMasterService.getShiftByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Shift information get successfully By OrgId");
			responseObjectsMap.put("shiftVO", shiftVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "shiftVO information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getShiftById")
	public ResponseEntity<ResponseDTO> getShiftById(@RequestParam Long id) {
		String methodName = "getShiftById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ShiftVO> shiftVO = new ArrayList<>();
		try {
			shiftVO = efitMasterService.getShiftById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Shift information get successfully By Id");
			responseObjectsMap.put("shiftVO", shiftVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "UomVO information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateShift")
	public ResponseEntity<ResponseDTO> updateCreateShift(@RequestBody ShiftDTO shiftdto) {
		String methodName = "updateCreateTaxInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> shiftVO = efitMasterService.updateCreateShift(shiftdto);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, shiftVO.get("message"));
			responseObjectsMap.put("shiftVO", shiftVO.get("shiftVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Rackmaster

	@GetMapping("/getRackMasterByOrgId")
	public ResponseEntity<ResponseDTO> getRackMasterByOrgId(@RequestParam Long orgId) {
		String methodName = "getRackMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RackMasterVO> rackMasterVO = new ArrayList<>();
		try {
			rackMasterVO = efitMasterService.getRackMasterByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RackMaster information get successfully ByOrgId");
			responseObjectsMap.put("rackMasterVO", rackMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RackMaster information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getRackMasterById")
	public ResponseEntity<ResponseDTO> getRackMasterById(@RequestParam Long id) {
		String methodName = "getRackMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RackMasterVO> rackMasterVO = new ArrayList<>();
		try {
			rackMasterVO = efitMasterService.getRackMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RackMaster information get successfully By Id");
			responseObjectsMap.put("rackMasterVO", rackMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "RackMaster information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateRackMaster")
	public ResponseEntity<ResponseDTO> updateCreateRackMaster(@RequestBody RackMasterDTO rackMasterDTO) {
		String methodName = "updateCreateRackMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> shiftVO = efitMasterService.updateCreateRackMaster(rackMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, shiftVO.get("message"));
			responseObjectsMap.put("rackMasterVO", shiftVO.get("rackMasterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// BOM

	@GetMapping("/getAllBomOrgId")
	public ResponseEntity<ResponseDTO> getAllBomOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getAllBomOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BomVO> bomVO = new ArrayList<>();
		try {
			bomVO = efitMasterService.getAllBomOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bom information get successfully ByOrgId");
			responseObjectsMap.put("bomVO", bomVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Bom information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllBomId")
	public ResponseEntity<ResponseDTO> getAllBomId(@RequestParam Long id) {
		String methodName = "getAllBomId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BomVO> bomVO = new ArrayList<>();
		try {
			bomVO = efitMasterService.getAllBomId(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bom information get successfully By Id");
			responseObjectsMap.put("bomVO", bomVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Bom information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateBom")
	public ResponseEntity<ResponseDTO> createUpdateBom(@Valid @RequestBody BomDTO bomDTO) {
		String methodName = "createUpdateBom()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> bomVO = efitMasterService.createUpdateBom(bomDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, bomVO.get("message"));
			responseObjectsMap.put("bomVO", bomVO.get("bomVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBomDocId")
	public ResponseEntity<ResponseDTO> getBomDocId(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branchCode) {

		String methodName = "getBomDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = efitMasterService.getBomDocId(orgId,finYear,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BobDocId information retrieved successfully");
			responseObjectsMap.put("bomDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve BobDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFGSFGPartDetailsForBom")
	public ResponseEntity<ResponseDTO> getFGSFGPartDetailsForBom(@RequestParam(required = false) Long orgId,
			String productType) {
		String methodName = "getFGSFGPartDetailsForBom()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> FgSfg = new ArrayList<>();
		try {
			FgSfg = efitMasterService.getFGSFGPartDetailsForBOM(orgId, productType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FGSFGPartDetails information get successfully By OrgId");
			responseObjectsMap.put("FgSfg", FgSfg);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FGSFGPartDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSFGItemDetailsForBom")
	public ResponseEntity<ResponseDTO> getSFGItemDetailsForBom(@RequestParam(required = false) Long orgId) {
		String methodName = "getSFGItemDetailsForBom()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> SfgItem = new ArrayList<>();
		try {
			SfgItem = efitMasterService.getSFGItemDetailsForBOM(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FGSFGPartDetails information get successfully By OrgId");
			responseObjectsMap.put("SfgItem", SfgItem);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"FGSFGPartDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	//employeemaster
	
	@PutMapping("/updateCreateEmployeeMaster")
	public ResponseEntity<ResponseDTO> updateCreateEmployeeMaster( @Valid @RequestBody EmployeeMasterDTO employeeMasterDTO) {
		String methodName = "updateCreateEmployeeMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> employeeMasterVO = efitMasterService.updateCreateEmployeeMaster(employeeMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, employeeMasterVO.get("message"));
			responseObjectsMap.put("employeeMasterVO", employeeMasterVO.get("employeeMasterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAllEmployeeMasterByOrgId")
	public ResponseEntity<ResponseDTO> getAllEmployeeMasterByOrgId(@RequestParam Long orgId,@RequestParam String branchCode) {
		String methodName = "getAllEmployeeMasterByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeMasterVO> employeeMasterVO = new ArrayList<>();
		try {
			employeeMasterVO = efitMasterService.getAllEmployeeMasterByOrgId(orgId,branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmployeeMaster information get successfully ByOrgId");
			responseObjectsMap.put("employeeMasterVO", employeeMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"EmployeeMaster information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getEmployeeMasterById")
	public ResponseEntity<ResponseDTO> getEmployeeMasterById(@RequestParam Long id) {
		String methodName = "getEmployeeMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeMasterVO> employeeMasterVO = new ArrayList<>();
		try {
			employeeMasterVO = efitMasterService.getEmployeeMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EmployeeMaster information get successfully By Id");
			responseObjectsMap.put("employeeMasterVO", employeeMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"EmployeeMaster information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}


}
