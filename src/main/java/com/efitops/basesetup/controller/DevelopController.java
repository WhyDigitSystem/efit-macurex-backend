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
import com.efitops.basesetup.dto.DocumentTypeMasterDTO;
import com.efitops.basesetup.dto.DocumnentTypeMappingDTO;
import com.efitops.basesetup.dto.GSTStateMasterDTO;
import com.efitops.basesetup.dto.GradeMasterDTO;
import com.efitops.basesetup.dto.HsnDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.UnitMasterDTO;
import com.efitops.basesetup.dto.UomConversionDTO;
import com.efitops.basesetup.entity.DocumentTypeMasterVO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.UomConversionVO;
import com.efitops.basesetup.service.DevelopService;



@CrossOrigin
@RestController
@RequestMapping("/api/develop")
public class  DevelopController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopController.class);

    @Autowired
    private DevelopService developService;
    
    //HSN
    
  
    @PutMapping("/createUpdateHSN")
    public ResponseEntity<ResponseDTO> createUpdateHSN(@RequestBody HsnDTO hsnDTO) {

        String methodName = "createUpdateHSN()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        try {

            Map<String, Object> hsnVO = developService.createUpdateHSN(hsnDTO);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, hsnVO.get("message"));
            responseObjectsMap.put("hsnVO", hsnVO.get("hsnVO"));

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("/getHSNById")
    public ResponseEntity<ResponseDTO> getHSNById(@RequestParam Long id) {

        String methodName = "getHSNById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            HsnVO hsnVO = developService.getHSNById(id).orElse(null);

            responseObjectsMap.put("hsnVO", hsnVO);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
    
    @GetMapping("/getHsnByOrgId")
    public ResponseEntity<ResponseDTO> getHsnByOrgId(@RequestParam Long orgId,
                                                     @RequestParam Long branch) {

        String methodName = "getHsnByOrgId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            List<HsnVO> hsnList = developService.getHsnByOrgId(orgId, branch);

            responseObjectsMap.put("hsnList", hsnList);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
	 
	
    //Unit Master
    @PutMapping("/createUpdateUnitMaster")
    public ResponseEntity<ResponseDTO> createUpdateUnitMaster(@RequestBody UnitMasterDTO unitMasterDTO) {

        String methodName = "createUpdateUnitMaster()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        try {

            Map<String, Object> unitMasterVO = developService.createUpdateUnitMaster(unitMasterDTO);

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, unitMasterVO.get("message"));
            responseObjectsMap.put("unitMasterVO", unitMasterVO.get("unitMasterVO"));

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getUnitMasterById")
    public ResponseEntity<ResponseDTO> getUnitMasterById(@RequestParam Long id) {

        String methodName = "getUnitMasterById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            UnitMasterVO unitMasterVO = developService.getUnitMasterById(id).orElse(null);

            responseObjectsMap.put("unitMasterVO", unitMasterVO);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
    
    @GetMapping("/getUnitMasterByOrgId")
    public ResponseEntity<ResponseDTO> getUnitMasterByOrgId(@RequestParam Long orgId,
                                                            @RequestParam Long branch) {

        String methodName = "getUnitMasterByOrgId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            List<UnitMasterVO> unitMasterList = developService.getUnitMasterByOrgId(orgId, branch);

            responseObjectsMap.put("unitMasterList", unitMasterList);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
    
    //Uom Conversion
    
    @PutMapping("/createUpdateUomConversion")
	public ResponseEntity<ResponseDTO> createUpdateUomConversion(
			@RequestBody UomConversionDTO uomConversionDTO) {

		String methodName = "createUpdateUomConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> uomConversionVO = developService
					.createUpdateUomConversion(uomConversionDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					uomConversionVO.get("message"));
			responseObjectsMap.put("uomConversionVO",
					uomConversionVO.get("uomConversionVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

    @GetMapping("/getUomConversionById")
    public ResponseEntity<ResponseDTO> getUomConversionById(@RequestParam Long id) {

        String methodName = "getUomConversionById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            UomConversionVO uomConversionVO = developService.getUomConversionById(id).orElse(null);

            responseObjectsMap.put("uomConversionVO", uomConversionVO);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
    
    @GetMapping("/getUomConversionByOrgId")
    public ResponseEntity<ResponseDTO> getUomConversionByOrgId(@RequestParam Long orgId,
                                                               @RequestParam Long branch) {

        String methodName = "getUomConversionByOrgId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        String errorMsg = null;
        ResponseDTO responseDTO = null;

        try {

            List<UomConversionVO> uomConversionList = developService.getUomConversionByOrgId(orgId, branch);

            responseObjectsMap.put("uomConversionList", uomConversionList);

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
	//Grade Master
	
	@PutMapping("/createUpdateGradeMaster")
	public ResponseEntity<ResponseDTO> createUpdateGradeMaster(@RequestBody GradeMasterDTO gradeMasterDTO) {

		String methodName = "createUpdateGradeMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> gradeMasterVO = developService.createUpdateGradeMaster(gradeMasterDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, gradeMasterVO.get("message"));
			responseObjectsMap.put("gradeMasterVO", gradeMasterVO.get("gradeMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGradeMasterById")
	public ResponseEntity<ResponseDTO> getGradeMasterById(@RequestParam Long id) {

	    String methodName = "getGradeMasterById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        GradeMasterVO gradeMasterVO = developService.getGradeMasterById(id).orElse(null);

	        responseObjectsMap.put("gradeMasterVO", gradeMasterVO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGradeMasterByOrgId")
	public ResponseEntity<ResponseDTO> getGradeMasterByOrgId(@RequestParam Long orgId,
	                                                         @RequestParam Long branch) {

	    String methodName = "getGradeMasterByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        List<GradeMasterVO> gradeMasterList = developService.getGradeMasterByOrgId(orgId, branch);

	        responseObjectsMap.put("gradeMasterList", gradeMasterList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//GSTStateMaster
	
	@PutMapping("/createUpdateGSTStateMaster")
	public ResponseEntity<ResponseDTO> createUpdateGSTStateMaster(
	        @RequestBody GSTStateMasterDTO gstStateMasterDTO) {

	    String methodName = "createUpdateGSTStateMaster()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    try {

	        Map<String, Object> gstStateMasterVO =
	                developService.createUpdateGSTStateMaster(gstStateMasterDTO);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                gstStateMasterVO.get("message"));
	        responseObjectsMap.put("gstStateMasterVO",
	                gstStateMasterVO.get("gstStateMasterVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName, errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGSTStateMasterById")
	public ResponseEntity<ResponseDTO> getGSTStateMasterById(@RequestParam Long id) {

	    String methodName = "getGSTStateMasterById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        GSTStateMasterVO gstStateMasterVO = developService.getGSTStateMasterById(id).orElse(null);

	        responseObjectsMap.put("gstStateMasterVO", gstStateMasterVO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGSTStateMasterByOrgId")
	public ResponseEntity<ResponseDTO> getGSTStateMasterByOrgId(@RequestParam Long orgId,
	                                                            @RequestParam Long branch) {

	    String methodName = "getGSTStateMasterByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        List<GSTStateMasterVO> gstStateMasterList = developService.getGSTStateMasterByOrgId(orgId, branch);

	        responseObjectsMap.put("gstStateMasterList", gstStateMasterList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//DocumentTypeMaster
	
	
	// Document Type Master

	@PutMapping("/createUpdateDocumentTypeMaster")
	public ResponseEntity<ResponseDTO> createUpdateDocumentTypeMaster(
	        @RequestBody DocumentTypeMasterDTO documentTypeMasterDTO) {

	    String methodName = "createUpdateDocumentTypeMaster()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    try {

	        Map<String, Object> documentTypeMasterVO =
	                developService.createUpdateDocumentTypeMaster(documentTypeMasterDTO);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                documentTypeMasterVO.get("message"));
	        responseObjectsMap.put("documentTypeMasterVO",
	                documentTypeMasterVO.get("documentTypeMasterVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName, errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocumentTypeMasterById")
	public ResponseEntity<ResponseDTO> getDocumentTypeMasterById(@RequestParam Long id) {

	    String methodName = "getDocumentTypeMasterById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        DocumentTypeMasterVO documentTypeMasterVO = developService.getDocumentTypeMasterById(id).orElse(null);

	        responseObjectsMap.put("documentTypeMasterVO", documentTypeMasterVO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getDocumentTypeMasterByOrgId")
	public ResponseEntity<ResponseDTO> getDocumentTypeMasterByOrgId(@RequestParam Long orgId,
	                                                                @RequestParam Long branch) {

	    String methodName = "getDocumentTypeMasterByOrgId()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        List<DocumentTypeMasterVO> documentTypeMasterList =
	                developService.getDocumentTypeMasterByOrgId(orgId, branch);

	        responseObjectsMap.put("documentTypeMasterList", documentTypeMasterList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//documenttypemapping
	
	@PutMapping("/updateCreateDocumnentTypeMapping")
	public ResponseEntity<ResponseDTO> updateCreateDocumnentTypeMapping(
	        @RequestBody DocumnentTypeMappingDTO documentTypeMappingDTO) {

	    String methodName = "updateCreateDocumnentTypeMapping()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    try {

	        Map<String, Object> documentTypeMappingVO =
	        		developService.updateCreateDocumnentTypeMapping(documentTypeMappingDTO);

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
	
	@GetMapping("/getDocumnentTypeMappingById")
	public ResponseEntity<ResponseDTO> getDocumnentTypeMappingById(@RequestParam Long id) {

	    String methodName = "getDocumnentTypeMappingById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	    	DocumentTypeMappingVO documentTypeMappingVO =
	        		developService.getDocumnentTypeMappingById(id);

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
	
	




