package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.efitops.basesetup.dto.GSTRateMasterDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.service.TransportMasterService;



@CrossOrigin
@RestController
@RequestMapping("/api/dev")
public class DevController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);
	@Autowired
	TransportMasterService transportMasterService;
	
	 @PutMapping("/updateCreateListOfValues")
		public ResponseEntity<ResponseDTO> updateCreateListOfValues(@RequestBody ListOfValuesDTO listOfValuesDTO) {
			String methodName = "updateCreateListOfValues()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> listOfValuesVO = transportMasterService.updateCreateListOfValues(listOfValuesDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, listOfValuesVO.get("message"));
				responseObjectsMap.put("listOfValuesVO", listOfValuesVO.get("listOfValuesVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
	 @GetMapping("/getListOfValuesById")
	 public ResponseEntity<ResponseDTO> getListOfValuesById(@RequestParam Long id) {

	     String methodName = "getListOfValuesById()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	    	 ListOfValuesVO listOfValuesVO = transportMasterService.getListOfValuesById(id);

	         responseObjectsMap.put("listOfValuesVO", listOfValuesVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getListOfValuesByOrgId")
	 public ResponseEntity<ResponseDTO> getListOfValuesByOrgId(@RequestParam Long orgId,@RequestParam Long branchId) {

	     String methodName = "getListOfValuesByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         List<ListOfValuesVO> listOfVlaues = transportMasterService.getListOfValuesByOrgId(orgId,branchId);

	         responseObjectsMap.put("listOfValues", listOfVlaues);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	//GST Rate Master
	 
	 @PutMapping("/updateCreateGSTRateMaster")
		public ResponseEntity<ResponseDTO> updateCreateGSTRateMaster(@RequestBody GSTRateMasterDTO gSTRateMasterDTO) {
			String methodName = "updateCreateGSTRateMaster()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> gSTRateMasterVO = transportMasterService.updateCreateGSTRateMaster(gSTRateMasterDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, gSTRateMasterVO.get("message"));
				responseObjectsMap.put("gSTRateMasterVO", gSTRateMasterVO.get("gSTRateMasterVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
	 @GetMapping("/getGSTRateMasterById")
	 public ResponseEntity<ResponseDTO> getGSTRateMasterById(@RequestParam Long id) {

	     String methodName = "getGSTRateMasterById()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	    	 GSTRateMasterVO gSTRateMasterVO = transportMasterService.getGSTRateMasterById(id);

	         responseObjectsMap.put("gSTRateMasterVO", gSTRateMasterVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getGSTRateByOrgId")
	 public ResponseEntity<ResponseDTO> getGSTRateByOrgId(@RequestParam Long orgId,@RequestParam Long branchId) {

	     String methodName = "getGSTRateByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         List<GSTRateMasterVO> transportList = transportMasterService.getGSTRateByOrgId(orgId,branchId);

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
	 //Service Accounting Masters
	 
	 @PutMapping("/updateCreateServiceAccMaster")
		public ResponseEntity<ResponseDTO> updateCreateServiceAccMaster(@RequestBody ServiceAccMasterDTO serviceAccMasterDTO) {
			String methodName = "updateCreateServiceAccMaster()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> serviceAccMasterVO = transportMasterService.updateCreateServiceAccMaster(serviceAccMasterDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, serviceAccMasterVO.get("message"));
				responseObjectsMap.put("serviceAccMasterVO", serviceAccMasterVO.get("serviceAccMasterVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
	 @GetMapping("/getServiceAccMasterById")
	 public ResponseEntity<ResponseDTO> getServiceAccMasterById(@RequestParam Long id) {

	     String methodName = "getServiceAccMasterById()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	    	 ServiceAccMasterVO serviceAccMasterVO = transportMasterService.getServiceNameById(id);

	         responseObjectsMap.put("serviceAccMasterVO", serviceAccMasterVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getServiceAccMasterByOrgId")
	 public ResponseEntity<ResponseDTO> getServiceAccMasterByOrgId(@RequestParam Long orgId,@RequestParam Long branchId) {

	     String methodName = "getServiceAccMasterByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         List<ServiceAccMasterVO> transportList = transportMasterService.getServiceNameByOrgId(orgId,branchId);

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
	 
	 
}
