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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.BranchDTO;
import com.efitops.basesetup.dto.FinancialYearDTO;
import com.efitops.basesetup.dto.GSTRateMasterDTO;
import com.efitops.basesetup.dto.LMEDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.LocationDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.service.TransportMasterService;



@CrossOrigin
@RestController
@RequestMapping("/api/dev")
public class DevController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);
	@Autowired
	TransportMasterService transportMasterService;
	//Location Master
	
	 @PutMapping("/updateCreateLocationMaster")
		public ResponseEntity<ResponseDTO> updateCreateLocationMaster(@RequestBody LocationDTO locationDTO) {
			String methodName = "updateCreateLocationMaster()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> LocationVO = transportMasterService.updateCreateLocationMaster(locationDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, LocationVO.get("message"));
				responseObjectsMap.put("locationVO", LocationVO.get("locationVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
	 @GetMapping("/getLocationMasterById")
	 public ResponseEntity<ResponseDTO> getLocationMasterById(@RequestParam Long id) {

	     String methodName = "getLocationMasterById()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	    	 LocationVO locationVO = transportMasterService.getLocationById(id);

	         responseObjectsMap.put("locationVO", locationVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getLocationByOrgId")
	 public ResponseEntity<ResponseDTO> getLocationByOrgId(@RequestParam Long orgId,@RequestParam Long branch) {

	     String methodName = "getLocationByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         List<LocationVO> transportList = transportMasterService.getLocationByOrgId(orgId,branch);

	         responseObjectsMap.put("transportList", transportList);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }

	 //LME 
	 @PutMapping("/updateCreateLMEMaster")
		public ResponseEntity<ResponseDTO> updateCreateLMEMaster(@RequestBody LMEDTO lMEDTO) {
			String methodName = "updateCreateLMEMaster()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> LMEVO = transportMasterService.updateCreateLMEMaster(lMEDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, LMEVO.get("message"));
				responseObjectsMap.put("lMEVO", LMEVO.get("lMEVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
	 @GetMapping("/getLMEMasterById")
	 public ResponseEntity<ResponseDTO> getLMEMasterById(@RequestParam Long id) {

	     String methodName = "getLMEMasterById()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	    	 LMEVO lMEVO = transportMasterService.getLMEById(id);

	         responseObjectsMap.put("lMEVO", lMEVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getLMEByOrgId")
	 public ResponseEntity<ResponseDTO> getLMEByOrgId(@RequestParam Long orgId,@RequestParam Long branch) {

	     String methodName = "getLMEByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         List<LMEVO> transportList = transportMasterService.getLMEByOrgId(orgId,branch);

	         responseObjectsMap.put("transportList", transportList);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 //Financial Year
	 @PostMapping("/createUpdateFinancialYear")
	 public ResponseEntity<ResponseDTO> createUpdateFinancialYear(@RequestBody FinancialYearDTO financialYearDTO) {

	     String methodName = "createUpdateFinancialYear()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         Map<String, Object> createdFinancialYearVO = transportMasterService.createUpdateFinancialYear(financialYearDTO);

	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdFinancialYearVO.get("message"));
	         responseObjectsMap.put("financialYearVO", createdFinancialYearVO.get("financialYearVO"));

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();

	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/geFinancialYearById")
		public ResponseEntity<ResponseDTO> geFinancialYearById(@RequestParam Long id) {
			String methodName = "geFinancialYearById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			FinancialYearVO financialYearVO = new FinancialYearVO();
			try {
				financialYearVO = transportMasterService.getFinancialYearById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Financial Year information get successfully");
				responseObjectsMap.put("financialYearVO", financialYearVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Financial Year information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
	 
	 @GetMapping("/getFinancialYearByOrgId")
	 public ResponseEntity<ResponseDTO> getFinancialYearByOrgId(@RequestParam Long orgId) {
	     String methodName = "getFinancialYearByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     String errorMsg = null;
	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     ResponseDTO responseDTO = null;
	     List<FinancialYearVO> branchList = new ArrayList<>();

	     try {
	         branchList = transportMasterService.getFinancialYearByOrgId(orgId);
	     } catch (Exception e) {
	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	     }

	     if (StringUtils.isBlank(errorMsg)) {
	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Financial Year information retrieved successfully");
	         responseObjectsMap.put("branchList", branchList);
	         responseDTO = createServiceResponse(responseObjectsMap);
	     } else {
	         responseDTO = createServiceResponseError(responseObjectsMap,
	                 "Financial Year information retrieval failed", errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	     return ResponseEntity.ok().body(responseDTO);
	 }

}

