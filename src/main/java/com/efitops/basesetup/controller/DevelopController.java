package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.service.DevelopService;

@CrossOrigin
@RestController
@RequestMapping("/api/develop")
public class DevelopController extends BaseController  {

    @Autowired
    private DevelopService developService;

    
    
	@PostMapping("/updateCreateEnquiry")
	public ResponseEntity<ResponseDTO> updateCreateEnquiry(
			@RequestBody EnquiryDTO enquiryDTO) {

		String methodName = "updateCreateEnquiry";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = developService
					.updateCreateEnquiry(enquiryDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));
			responseObjectsMap.put("enquiryVO",
					responseMap.get("enquiryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getEnquiryById")
	public ResponseEntity<ResponseDTO> getEnquiryById(@RequestParam Long id) {

		String methodName = "getEnquiryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		EnquiryVO Enquiry = null;

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

		List<EnquiryVO> enquiryList = new ArrayList<>();

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

}
	