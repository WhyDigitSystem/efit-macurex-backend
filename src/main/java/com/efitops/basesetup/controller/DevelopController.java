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
import com.efitops.basesetup.dto.DocumentTypeMappingDTO;

import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.service.DevelopService;



@CrossOrigin
@RestController
@RequestMapping("/api/develop")
public class  DevelopController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopController.class);

    @Autowired
    private DevelopService developService;
    
    
  //documenttypemapping
	
  		@PutMapping("/updateCreateDocumentTypeMapping")
  		public ResponseEntity<ResponseDTO> updateCreateDocumnentTypeMapping(
  		        @RequestBody DocumentTypeMappingDTO documentTypeMappingDTO) {

  		    String methodName = "updateCreateDocumentTypeMapping()";
  		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  		    String errorMsg = null;
  		    Map<String, Object> responseObjectsMap = new HashMap<>();
  		    ResponseDTO responseDTO = null;

  		    try {

  		        Map<String, Object> documentTypeMappingVO =
  		        		developService.updateCreateDocumentTypeMapping(documentTypeMappingDTO);

  		        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
  		                documentTypeMappingVO.get("message"));
  		        responseObjectsMap.put(
  		                "documentTypeMappingVO",
  		                documentTypeMappingVO.get("documentTypeMappingMasterVO"));

  		        responseDTO = createServiceResponse(responseObjectsMap);

  		    } catch (Exception e) {

  		        errorMsg = e.getMessage();
  		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

  		        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
  		    }

  		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

  		    return ResponseEntity.ok().body(responseDTO);
  		}
  		
  		@GetMapping("/getDocumentTypeMappingById")
  		public ResponseEntity<ResponseDTO> getDocumentTypeMappingById(@RequestParam Long id) {

  		    String methodName = "getDocumentTypeMappingById()";
  		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  		    Map<String, Object> responseObjectsMap = new HashMap<>();
  		    String errorMsg = null;
  		    ResponseDTO responseDTO = null;

  		    try {

  		    	DocumentTypeMappingVO documentTypeMappingVO =
  		        		developService.getDocumentTypeMappingById(id);

  		        responseObjectsMap.put("documentTypeMappingMasterVO", documentTypeMappingVO);

  		        responseDTO = createServiceResponse(responseObjectsMap);

  		    } catch (Exception e) {

  		        errorMsg = e.getMessage();
  		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

  		        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
  		    }

  		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

  		    return ResponseEntity.ok().body(responseDTO);
  		}
  		
  		@GetMapping("/getDocumentTypeMappingByOrgId")
  		public ResponseEntity<ResponseDTO> getDocumentTypeMappingByOrgId(
  		        @RequestParam Long orgId,
  		        @RequestParam Long branch) {

  		    String methodName = "getDocumentTypeMappingByOrgId()";
  		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  		    Map<String, Object> responseObjectsMap = new HashMap<>();
  		    String errorMsg = null;
  		    ResponseDTO responseDTO = null;

  		    try {

  		        List<DocumentTypeMappingVO> documentTypeMappingList =
  		        		developService.getDocumnentTypeMappingByOrgId(orgId, branch);

  		        responseObjectsMap.put("documentTypeMappingMasterList", documentTypeMappingList);

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
	
	




