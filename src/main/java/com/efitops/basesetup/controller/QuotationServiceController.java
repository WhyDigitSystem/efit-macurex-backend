package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
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

	@PostMapping(value = "/createUpdateQuotation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateQuotation(@RequestPart("quotation") QuotationDTO quotationDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> quotationMap = quotationService.createUpdateQuotation(quotationDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, quotationMap.get("message"));

			responseObjectsMap.put("quotationVO", quotationMap.get("quotationVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getQuotationDocId")
	public ResponseEntity<ResponseDTO> getQuotationDocId(@RequestParam Long orgId,
			@RequestParam String financialYear, @RequestParam String screenCode) {

		String methodName = "getQuotationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = quotationService.getQuotationDocId(orgId, financialYear, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Quotation DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Proforma Quotation	 DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
