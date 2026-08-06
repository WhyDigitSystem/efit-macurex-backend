package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
import com.efitops.basesetup.dto.SalesReturnDTO;
import com.efitops.basesetup.dto.SalesReturnResponseDTO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.service.DevelopService;

@CrossOrigin
@RestController
@RequestMapping("/api/develop")
public class DevelopController extends BaseController  {

    @Autowired
    private DevelopService developService;

    
    
    @PostMapping(
            value = "/updateCreateEnquiry",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> updateCreateEnquiry(
            @RequestPart("enquiry") EnquiryDTO enquiryDTO,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        String methodName = "updateCreateEnquiry";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {

            Map<String, Object> responseMap =
                    developService.updateCreateEnquiry(enquiryDTO, files);

            responseObjectsMap.put(
                    CommonConstant.STRING_MESSAGE,
                    responseMap.get("message"));

            responseObjectsMap.put(
                    "enquiryVO",
                    responseMap.get("enquiryVO"));

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            LOGGER.error(
                    UserConstants.ERROR_MSG_METHOD_NAME,
                    methodName,
                    e.getMessage(),
                    e);

            responseDTO = createServiceResponseError(
                    responseObjectsMap,
                    
                    e.getMessage(),
                    e.getMessage());
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok(responseDTO);
    }
    
    
	@GetMapping("/getEnquiryById")
	public ResponseEntity<ResponseDTO> getEnquiryById(@RequestParam Long id) {

		String methodName = "getEnquiryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		EnquiryResponseDTO Enquiry = null;

		try {

			Enquiry = developService
					.getEnquiryById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg))  {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Enquiry information retrieved successfully");

			responseObjectsMap.put("enquiry",
					Enquiry);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					" Enquiry information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getEnquiryByOrgId(@RequestParam Long orgId,@RequestParam Long branch) {

		String methodName = "getEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<EnquiryResponseDTO> enquiryList = new ArrayList<>();

		try {

			enquiryList = developService
					.getEnquiryByOrgId(orgId,branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Enquiry information retrieved successfully");

			responseObjectsMap.put("enquiryList",
					enquiryList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					" Enquiry information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}



//salesdeliveryschedule

@PostMapping("/createUpdateSalesDeliverySchedule")
public ResponseEntity<ResponseDTO> createUpdateSalesDeliverySchedule(
        @RequestBody SalesDeliveryScheduleDTO salesDeliveryScheduleDTO) {

    String methodName = "createUpdateSalesDeliverySchedule";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

        Map<String, Object> responseMap =
                developService.createUpdateSalesDeliverySchedule(
                        salesDeliveryScheduleDTO);

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                responseMap.get("message"));

        responseObjectsMap.put(
                "salesDeliverySchedule",
                responseMap.get("salesDeliverySchedule"));

        responseDTO = createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                e.getMessage(),
                e);

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                e.getMessage(),
                e.getMessage());
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok(responseDTO);
}

@GetMapping("/getSalesDeliveryScheduleById")
public ResponseEntity<ResponseDTO> getSalesDeliveryScheduleById(
        @RequestParam Long id) {

    String methodName = "getSalesDeliveryScheduleById()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    String errorMsg = null;
    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO = null;

    SalesDeliveryScheduleResponseDTO salesDeliverySchedule = null;

    try {

        salesDeliverySchedule =
                developService.getSalesDeliveryScheduleById(id);

    } catch (Exception e) {

        errorMsg = e.getMessage();

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                errorMsg);
    }

    if (StringUtils.isBlank(errorMsg)) {

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                "Sales Delivery Schedule information retrieved successfully");

        responseObjectsMap.put(
                "salesDeliverySchedule",
                salesDeliverySchedule);

        responseDTO = createServiceResponse(responseObjectsMap);

    } else {

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Sales Delivery Schedule information retrieval failed",
                errorMsg);
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok().body(responseDTO);
}

@GetMapping("/getSalesDeliveryScheduleByOrgId")
public ResponseEntity<ResponseDTO> getSalesDeliveryScheduleByOrgId(
        @RequestParam Long orgId,
        @RequestParam Long branch) {

    String methodName = "getSalesDeliveryScheduleByOrgId()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    String errorMsg = null;
    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO = null;

    List<SalesDeliveryScheduleResponseDTO> salesDeliveryScheduleList =
            new ArrayList<>();

    try {

        salesDeliveryScheduleList =
                developService.getAllSalesDeliverySchedule(
                        orgId,
                        branch);

    } catch (Exception e) {

        errorMsg = e.getMessage();

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                errorMsg);
    }

    if (StringUtils.isBlank(errorMsg)) {

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                "Sales Delivery Schedule information retrieved successfully");

        responseObjectsMap.put(
                "salesDeliveryScheduleList",
                salesDeliveryScheduleList);

        responseDTO = createServiceResponse(responseObjectsMap);

    } else {

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Sales Delivery Schedule information retrieval failed",
                errorMsg);
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok().body(responseDTO);
}

//contract dropdown

@GetMapping("/getContractNoDropdown")
public ResponseEntity<ResponseDTO> getContractNoDropdown() {

    String methodName = "getContractNoDropdown";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

    	Map<String, Object> responseMap =
    	        developService.getContractNo();
        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                responseMap.get("message"));

        responseObjectsMap.put(
                "contractList",
                responseMap.get("contractList"));

        responseDTO = createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                e.getMessage(),
                e);

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                e.getMessage(),
                e.getMessage());
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok(responseDTO);
}


//item dropdown

@GetMapping("/getItemDropdown")
public ResponseEntity<ResponseDTO> getItemDropdown(
        @PathVariable Long salesContractId) {

    String methodName = "getItemDropdown";

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

        Map<String, Object> responseMap =
                developService.getItemDropdown(salesContractId);

        responseObjectsMap.put("message", responseMap.get("message"));
        responseObjectsMap.put("itemList", responseMap.get("itemList"));

        responseDTO = createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                e.getMessage(),
                e.getMessage());
    }

    return ResponseEntity.ok(responseDTO);
}

//SALES RETURN

@PostMapping("/createUpdateSalesReturn")
public ResponseEntity<ResponseDTO> createUpdateSalesReturn(
        @RequestBody SalesReturnDTO salesReturnDTO) {

    String methodName = "createUpdateSalesReturn";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

        Map<String, Object> responseMap =
                developService.createUpdateSalesReturn(
                        salesReturnDTO);

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                responseMap.get("message"));

        responseObjectsMap.put(
                "salesReturn",
                responseMap.get("salesReturn"));

        responseDTO = createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                e.getMessage(),
                e);

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                e.getMessage(),
                e.getMessage());
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok(responseDTO);
}

@GetMapping("/getSalesReturnById")
public ResponseEntity<ResponseDTO> getSalesReturnById(
        @RequestParam Long id) {

    String methodName = "getSalesReturnById()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    String errorMsg = null;
    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO = null;

    SalesReturnResponseDTO salesReturn = null;

    try {

        salesReturn =
                developService.getSalesReturnById(id);

    } catch (Exception e) {

        errorMsg = e.getMessage();

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                errorMsg);
    }

    if (StringUtils.isBlank(errorMsg)) {

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                "Sales Return information retrieved successfully");

        responseObjectsMap.put(
                "salesReturn",
                salesReturn);

        responseDTO = createServiceResponse(responseObjectsMap);

    } else {

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Sales Return information retrieval failed",
                errorMsg);
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok().body(responseDTO);
}

@GetMapping("/getSalesReturnByOrgId")
public ResponseEntity<ResponseDTO> getSalesReturnByOrgId(
        @RequestParam Long orgId,
        @RequestParam Long branch) {

    String methodName = "getSalesReturnByOrgId()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    String errorMsg = null;
    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO = null;

    List<SalesReturnResponseDTO> salesReturnList =
            new ArrayList<>();

    try {

        salesReturnList =
                developService.getAllSalesReturn(
                        orgId,
                        branch);

    } catch (Exception e) {

        errorMsg = e.getMessage();

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                errorMsg);
    }

    if (StringUtils.isBlank(errorMsg)) {

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                "Sales Return information retrieved successfully");

        responseObjectsMap.put(
                "salesReturnList",
                salesReturnList);

        responseDTO = createServiceResponse(responseObjectsMap);

    } else {

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Sales Return information retrieval failed",
                errorMsg);
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok().body(responseDTO);
}
}
	