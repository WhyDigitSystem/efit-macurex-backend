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
import com.efitops.basesetup.dto.HolidayMasterDTO;
import com.efitops.basesetup.dto.LMEDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.LocationDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.dto.TSBankDTO;
import com.efitops.basesetup.dto.TaxDefinitionDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;
import com.efitops.basesetup.service.TransportMasterService;



@CrossOrigin
@RestController
@RequestMapping("/api/dev")
public class DevController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);
	@Autowired
	TransportMasterService transportMasterService;
	
	//Ts bank master
	 
	@PostMapping("/createUpdateBankMaster")
	 public ResponseEntity<ResponseDTO> createUpdateBankMaster(@RequestBody TSBankDTO tSBankDTO) {

	     String methodName = "createUpdateBankMaster()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         Map<String, Object> createdTSBankVO = transportMasterService.createUpdateBankMaster(tSBankDTO);

	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdTSBankVO.get("message"));
	         responseObjectsMap.put("tSBankVO", createdTSBankVO.get("tSBankVO"));

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();

	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getBankMasterById")
		public ResponseEntity<ResponseDTO> getBankMasterById(@RequestParam Long id) {
			String methodName = "getBankMasterById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			TSBankVO tSBankVO = new TSBankVO();
			try {
				tSBankVO = transportMasterService.getBankMasterById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bank information get successfully");
				responseObjectsMap.put("tSBankVO", tSBankVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Bank information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
	 
	 @GetMapping("/getBankMasterByOrgId")
	 public ResponseEntity<ResponseDTO> getBankMasterByOrgId(@RequestParam Long orgId) {
	     String methodName = "getBankMasterByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     String errorMsg = null;
	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     ResponseDTO responseDTO = null;
	     List<TSBankVO> bankList = new ArrayList<>();

	     try {
	    	 bankList = transportMasterService.getBankMasterByOrgId(orgId);
	     } catch (Exception e) {
	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	     }

	     if (StringUtils.isBlank(errorMsg)) {
	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bank information retrieved successfully");
	         responseObjectsMap.put("bankList", bankList);
	         responseDTO = createServiceResponse(responseObjectsMap);
	     } else {
	         responseDTO = createServiceResponseError(responseObjectsMap,
	                 "Bank information retrieval failed", errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	//Tax Definition 
	 
	 @PostMapping("/updateCreateTaxDefinition")
	 public ResponseEntity<ResponseDTO> updateCreateTaxDefinition(@RequestBody TaxDefinitionDTO taxDefinitionDTO) {

	     String methodName = "updateCreateTaxDefinition()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         Map<String, Object> createdTaxDefinitionVO = transportMasterService.updateCreateTaxDefinition(taxDefinitionDTO);

	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdTaxDefinitionVO.get("message"));
	         responseObjectsMap.put("taxDefinitionVO", createdTaxDefinitionVO.get("taxDefinitionVO"));

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();

	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getTaxDefinitionById")
		public ResponseEntity<ResponseDTO> getTaxDefinitionById(@RequestParam Long id) {
			String methodName = "getTaxDefinitionById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			TaxDefinitionVO taxDefinitionVO = new TaxDefinitionVO();
			try {
				taxDefinitionVO = transportMasterService.getTaxDefinitionById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TaxDefinition  information get successfully");
				responseObjectsMap.put("taxDefinitionVO", taxDefinitionVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "TaxDefinition  information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
	 
	 @GetMapping("/getTaxDefinitionByOrgId")
	 public ResponseEntity<ResponseDTO> getTaxDefinitionByOrgId(@RequestParam Long orgId , @RequestParam Long branch) {
	     String methodName = "getTaxDefinitionByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     String errorMsg = null;
	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     ResponseDTO responseDTO = null;
	     List<TaxDefinitionVO> taxList = new ArrayList<>();

	     try {
	    	 taxList = transportMasterService.getTaxDefinitionByOrgId(orgId,branch);
	     } catch (Exception e) {
	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	     }

	     if (StringUtils.isBlank(errorMsg)) {
	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TaxDefinition information retrieved successfully");
	         responseObjectsMap.put("taxList", taxList);
	         responseDTO = createServiceResponse(responseObjectsMap);
	     } else {
	         responseDTO = createServiceResponseError(responseObjectsMap,
	                 "TaxDefinition information retrieval failed", errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	     return ResponseEntity.ok().body(responseDTO);
	 }
	
	 
	 //Holiday Master
	 
	 @PutMapping("/updateCreateHolidayMaster")
		public ResponseEntity<ResponseDTO> updateCreateHolidayMaster(@RequestBody HolidayMasterDTO holidayMasterDTO) {
			String methodName = "updateCreateHolidayMaster()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> holidayMasterVO = transportMasterService.updateCreateHolidayMaster(holidayMasterDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, holidayMasterVO.get("message"));
				responseObjectsMap.put("holidayMasterVO", holidayMasterVO.get("holidayMasterVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
	 @GetMapping("/getHolidayMasterById")
	 public ResponseEntity<ResponseDTO> getHolidayMasterById(@RequestParam Long id) {

	     String methodName = "getHolidayMasterById()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	    	 HolidayMasterVO holidayMasterVO = transportMasterService.getHolidayMasterById(id);

	         responseObjectsMap.put("holidayMasterVO", holidayMasterVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getHolidayMasterByOrgId")
	 public ResponseEntity<ResponseDTO> getHolidayMasterByOrgId(@RequestParam Long orgId,@RequestParam Long branch) {

	     String methodName = "getHolidayMasterByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         List<HolidayMasterVO> holidayMasterVO = transportMasterService.getHolidayMasterByOrgId(orgId,branch);

	         responseObjectsMap.put("holidayMasterVO", holidayMasterVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }

}

