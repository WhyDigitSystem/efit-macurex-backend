package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ItemMasterDTO;
import com.efitops.basesetup.dto.QuotationDTO;
import com.efitops.basesetup.dto.QuotationResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.QuotationService;

@RestController
@RequestMapping("/api/quotationservice")
public class QuotationServiceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(QuotationServiceController.class);

	@Autowired
	QuotationService quotationService;

	@GetMapping("/getQuotationById")
	public ResponseEntity<ResponseDTO> getQuotationById(@RequestParam Long id) {

		String methodName = "getQuotationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			QuotationResponseDTO quotationResponseDTO = quotationService.getQuotationById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation information retrieved successfully");

			responseObjectsMap.put("quotationResponseVO", quotationResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Quotation information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getQuotationByOrgId")
	public ResponseEntity<ResponseDTO> getQuotationByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {

		String methodName = "getQuotationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<QuotationResponseDTO> quotationResponseDTO = quotationService.getQuotationByOrgId(orgId, branchId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation information retrieved successfully");

			responseObjectsMap.put("quotationResponseVO", quotationResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Quotation information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@PostMapping(value = "/createUpdateQuotationImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateQuotationImages(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module, @RequestParam List<String> fileNames) {

		String methodName = "createUpdateQuotationImages()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = quotationService.createUpdateQuotationImages(files, docId, screenName,
					module, fileNames);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("quotationImages", serviceResponse.get("quotationImagesVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewQuotationImages/**")
	public ResponseEntity<byte[]> viewQuotationImages(HttpServletRequest request) throws IOException {
		return quotationService.viewQuotationImages(request);
	}

	@PutMapping("/updateCreateQuotation")
	public ResponseEntity<ResponseDTO> updateCreateQuotation(@RequestBody QuotationDTO quotationDTO) {
		String methodName = "updateCreateQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> quotationVO = quotationService.updateCreateQuotation(quotationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, quotationVO.get("message"));
			responseObjectsMap.put("quotationVO", quotationVO.get("quotationVO"));
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
