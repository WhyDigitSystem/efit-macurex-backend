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
import com.efitops.basesetup.dto.MaterialTypeDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.UomDTO;
import com.efitops.basesetup.entity.BomVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DesignationVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.MaterialTypeVO;
import com.efitops.basesetup.entity.UomVO;
import com.efitops.basesetup.service.EfitMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/efitmaster")
public class EfitMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(EfitMasterController.class);

	EfitMasterService efitMasterService;

	
	
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
