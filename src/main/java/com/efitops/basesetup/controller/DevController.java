package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.TransportMasterDTO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.service.TransportMasterService;



@CrossOrigin
@RestController
@RequestMapping("/api/dev")
public class DevController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);
	@Autowired
	TransportMasterService transportMasterService;
	
	
// ------------ListOfValues----------------------
    
//    @PutMapping("/updateCreateListOfValues")
//    public ResponseEntity<ResponseDTO> updateCreateListOfValues(
//            @RequestBody ListOfValuesDTO listOfValuesDTO) {
//
//        String methodName = "updateCreateListOfValues()";
//        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//        String errorMsg = null;
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO = null;
//
//        try {
//
//            Map<String, Object> responseMap =
//            		transportMasterService.updateCreateListOfValues(listOfValuesDTO);
//
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//                    responseMap.get("message"));
//            responseObjectsMap.put("listOfValuesVO",
//                    responseMap.get("listOfValuesVO"));
//            responseObjectsMap.put("listOfValuesDetailsVO",
//                    responseMap.get("listOfValuesDetailsVO"));
//
//            responseDTO = createServiceResponse(responseObjectsMap);
//
//        } catch (Exception e) {
//
//            errorMsg = e.getMessage();
//            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
//                    methodName, errorMsg);
//
//            responseDTO = createServiceResponseError(
//                    responseObjectsMap, errorMsg, errorMsg);
//        }
//
//        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//        return ResponseEntity.ok(responseDTO);
//    }
//    
//    @GetMapping("/getById/{id}")
//    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
//
//        String methodName = "getById()";
//        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//        String errorMsg = null;
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO = null;
//
//        try {
//
//            Map<String, Object> responseMap =
//            		transportMasterService.getById(id);
//
//            responseObjectsMap.put("listOfValuesVO",
//                    responseMap.get("listOfValuesVO"));
//
//            responseObjectsMap.put("listOfValuesDetailsVO",
//                    responseMap.get("listOfValuesDetailsVO"));
//
//            responseDTO = createServiceResponse(responseObjectsMap);
//
//        } catch (Exception e) {
//
//            errorMsg = e.getMessage();
//            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
//                    methodName, errorMsg);
//
//            responseDTO = createServiceResponseError(
//                    responseObjectsMap, errorMsg, errorMsg);
//        }
//
//        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//        return ResponseEntity.ok(responseDTO);
//    }
//    
//    @GetMapping("/getByOrgId/{orgId}")
//    public ResponseEntity<ResponseDTO> getByOrgId(
//            @PathVariable Long orgId) {
//
//        String methodName = "getByOrgId()";
//        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//        String errorMsg = null;
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO = null;
//
//        try {
//
//            Map<String, Object> responseMap =
//            		transportMasterService.getByOrgId(orgId);
//
//            responseObjectsMap.put("listOfValuesVO",
//                    responseMap.get("listOfValuesVO"));
//
//            responseDTO = createServiceResponse(responseObjectsMap);
//
//        } catch (Exception e) {
//
//            errorMsg = e.getMessage();
//            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
//                    methodName, errorMsg);
//
//            responseDTO = createServiceResponseError(
//                    responseObjectsMap, errorMsg, errorMsg);
//        }
//
//        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//        return ResponseEntity.ok(responseDTO);
//    }
}
