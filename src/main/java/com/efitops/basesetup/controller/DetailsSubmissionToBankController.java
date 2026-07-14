package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.DetailsSubmissionToBankDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankDetailsVO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.service.DetailsSubmissionToBankService;

@RestController
@RequestMapping("/api/detailsSubmissionToBank")
public class DetailsSubmissionToBankController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(DetailsSubmissionToBankController.class);

	@Autowired
	DetailsSubmissionToBankService detailsSubmissionToBankService;

	@GetMapping("/getAllDetailsSubmissionToBankByOrgId")
	public ResponseEntity<ResponseDTO> getAllDetailsSubmissionToBankByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getAllDetailsSubmissionToBankByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DetailsSubmissionToBankVO> bankVO = new ArrayList<>();
		try {
			bankVO = detailsSubmissionToBankService.getAllDetailsSubmissionToBankByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bank Details get successfully By OrgId");
			responseObjectsMap.put("bankDetailsVO", bankVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bank Details receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDetailsSubmissionToBankById")
	public ResponseEntity<ResponseDTO> getDetailsSubmissionToBankById(@RequestParam Long id) {
		String methodName = "getDetailsSubmissionToBankById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DetailsSubmissionToBankVO> bankVO = new ArrayList<>();
		try {
			bankVO = detailsSubmissionToBankService.getDetailsSubmissionToBankById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bank Details get successfully By id");
			responseObjectsMap.put("bankDetailsVO", bankVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bank Details receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateDetailsSubmissionToBank")
	public ResponseEntity<ResponseDTO> updateCreateDetailsSubmissionToBank(
	        @RequestBody DetailsSubmissionToBankDTO bankDTO) {

	    String methodName = "updateCreateDetailsSubmissionToBank()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        Map<String, Object> result =
	                detailsSubmissionToBankService.createUpdateDetailsSubmissionToBank(bankDTO);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
	        responseObjectsMap.put(
	                "detailsSubmissionToBankVO",
	                result.get("detailsSubmissionToBankVO")
	        );

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (ApplicationException ae) {
	        LOGGER.error("Business error in {}", methodName, ae);
	        responseDTO = createServiceResponseError(
	                responseObjectsMap, ae.getMessage(), ae.getMessage());

	    } catch (Exception e) {
	        LOGGER.error("System error in {}", methodName, e);
	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Error while submitting bank details",
	                "Error while submitting bank details");
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDetailsSubmissionToBankByDocId")
	public ResponseEntity<ResponseDTO> getDetailsSubmissionToBankByDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getDetailsSubmissionToBankByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String bankVO = null;
		try {
			bankVO = detailsSubmissionToBankService.getDetailsSubmissionToBankDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bank Details information get successfully By docid");
			responseObjectsMap.put("bankDetailsVO", bankVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Bank Detail information receive failed By docid", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadAttachementsInBloob")
	public ResponseEntity<ResponseDTO> uploadAttachementsInBloob(@RequestParam("files") List<MultipartFile> files,
			@RequestParam("id") List<Long> id) {

		String methodName = "uploadAttachementsInBloob()";
		LOGGER.debug("Starting method: " + methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {
			if (files.size() != id.size()) {
				throw new IllegalArgumentException("Files and IDs must have the same size.");
			}

			// Call your service to handle files and ids
			List<DetailsSubmissionToBankDetailsVO> masterVO = detailsSubmissionToBankService
					.uploadAttachmentsInBloob(files, id);

			responseObjectsMap.put("message", "Bank Details Attachments successfully uploaded.");
			responseObjectsMap.put("bankDetailVO", masterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable to upload attachments", methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Bank Details Attachments upload failed",
					errorMsg);
		}

		LOGGER.debug("Ending method: " + methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	//Reports APIs
	
	@GetMapping("/getDetailsSubmissionToBankReport")
    public ResponseEntity<ResponseDTO> getDetailsSubmissionToBankReport(
            @RequestParam Long orgId,
            @RequestParam String fromDate,
            @RequestParam String toDate) {

        String methodName = "getDetailsSubmissionToBankReport()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            List<Map<String, Object>> reportList =
                    detailsSubmissionToBankService
                            .getDetailsSubmissionToBankReport(
                                    orgId, fromDate, toDate
                            );

            responseObjectsMap.put(
                    CommonConstant.STRING_MESSAGE,
                    "Details Submission To Bank Report retrieved successfully"
            );
            responseObjectsMap.put(
                    "detailsSubmissionToBankReport",
                    reportList
            );

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {
            responseDTO = createServiceResponseError(
                    responseObjectsMap,
                    "Failed to retrieve Details Submission To Bank Report",
                    e.getMessage()
            );
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok(responseDTO);
    }

}
