package com.efitops.basesetup.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	
	@PostMapping(value = "/createUpdateQuotation",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateQuotation(
	        @RequestPart("quotation") QuotationDTO quotationDTO,
	        @RequestPart(value = "files", required = false) MultipartFile[] files) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> quotationMap =
	                quotationService.createUpdateQuotation(quotationDTO, files);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                quotationMap.get("message"));

	        responseObjectsMap.put(
	                "quotationVO",
	                quotationMap.get("quotationVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@Value("${quotation.upload.path}")
	private String uploadPath;
	
	@Value("${server.base-url}")
	private String serverBaseUrl;
	
	@GetMapping("/getFileUrl")
	public ResponseEntity<ResponseDTO> getFileUrl(@RequestParam String fileName) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    try {

	        Path filePath = Paths.get(uploadPath).resolve(fileName).normalize();

	        if (!Files.exists(filePath)) {

	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(createServiceResponseError(
	                            responseObjectsMap,
	                            "File not found",
	                            "File not found"));
	        }

	        Map<String, Object> fileDetails = new HashMap<>();

	        fileDetails.put("fileName", fileName);
	        fileDetails.put("filePath", filePath.toString());
	        fileDetails.put("fileUrl",
	                serverBaseUrl + "/api/quotationservice/file/" + fileName);

	        responseObjectsMap.put("message", "File found");
	        responseObjectsMap.put("fileDetails", fileDetails);

	        return ResponseEntity.ok(createServiceResponse(responseObjectsMap));

	    } catch (Exception e) {

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed to get file",
	                        e.getMessage()));
	    }
	}
}
