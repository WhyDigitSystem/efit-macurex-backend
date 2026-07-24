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
import com.efitops.basesetup.dto.GradeMasterDTO;
import com.efitops.basesetup.dto.HsnDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.UnitMasterDTO;
import com.efitops.basesetup.dto.UomConversionDTO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.service.DevelopService;

@CrossOrigin
@RestController
@RequestMapping("/api/develop")
public class  DevelopController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopController.class);

    @Autowired
    private DevelopService developService;
    
  
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


	@GetMapping("/getHSNById/{id}")
    public ResponseEntity<?> getHSNById(@PathVariable Long id)
            throws ApplicationException {

        return ResponseEntity.ok(
                developService.getHSNById(id));
    }

    @GetMapping("/getHSNByOrgId/{orgId}")
    public ResponseEntity<?> getHSNByOrgId(@PathVariable Long orgId)
            throws ApplicationException {

        return ResponseEntity.ok(
                developService.getAllHSN(orgId));
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

    @GetMapping("/getUnitMasterById/{id}")
    public ResponseEntity<?> getUnitMasterById(@PathVariable Long id)
            throws ApplicationException {

        return ResponseEntity.ok(developService.getUnitMasterById(id));
    }

    @GetMapping("/getUnitMasterByOrgId/{orgId}")
    public ResponseEntity<?> getUnitMasterByOrgId(@PathVariable Long orgId)
            throws ApplicationException {

        return ResponseEntity.ok(developService.getAllUnitMaster(orgId));
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

	@GetMapping("/getUomConversionById/{id}")
	public ResponseEntity<?> getUomConversionById(@PathVariable Long id)
			throws ApplicationException {

		return ResponseEntity.ok(
				developService.getUomConversionById(id));
	}

	@GetMapping("/getUomConversionByOrgId/{orgId}")
	public ResponseEntity<?> getUomConversionByOrgId(@PathVariable Long orgId)
			throws ApplicationException {

		return ResponseEntity.ok(
				developService.getAllUomConversion(orgId));
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

	@GetMapping("/getGradeMasterById/{id}")
	public ResponseEntity<?> getGradeMasterById(@PathVariable Long id)
			throws ApplicationException {

		return ResponseEntity.ok(
				developService.getGradeMasterById(id));
	}

	@GetMapping("/getGradeMasterByOrgId/{orgId}")
	public ResponseEntity<?> getGradeMasterByOrgId(@PathVariable Long orgId)
			throws ApplicationException {

		return ResponseEntity.ok(
				developService.getAllGradeMaster(orgId));
	}  
	
	}




