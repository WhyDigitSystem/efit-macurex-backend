package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.InwardInspectionResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.InwardInspectionDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.InwardInspectionService;

@RestController
@RequestMapping("/api/inwardinspection")
public class InwardInspectionController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardInspectionController.class);

	@Autowired
	private InwardInspectionService inwardInspectionService;

	@GetMapping("/getInwardInspectionByOrgId")
	public ResponseEntity<ResponseDTO> getInwardInspectionByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {
		String methodName = "getInwardInspectionByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<InwardInspectionResponseDTO> inwardInspectionList = inwardInspectionService
					.getInwardInspectionByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Inward Inspection retrieved successfully");
			responseObjectsMap.put("inwardInspectionVO", inwardInspectionList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Inward Inspection retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getInwardInspectionById")
	public ResponseEntity<ResponseDTO> getInwardInspectionById(@RequestParam Long id) {
		String methodName = "getInwardInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			InwardInspectionResponseDTO inwardInspectionResponse = inwardInspectionService.getInwardInspectionById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Inward Inspection retrieved successfully");
			responseObjectsMap.put("inwardInspectionVO", inwardInspectionResponse);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Inward Inspection retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdateInwardInspection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateInwardInspection(
           @RequestPart("inwardInspection") InwardInspectionDTO inwardInspectionDTO,
//			@RequestBody InwardInspectionDTO inwardInspectionDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		String methodName = "createUpdateInwardInspection()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			Map<String, Object> inwardInspectionMap = inwardInspectionService
					.createUpdateInwardInspection(inwardInspectionDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, inwardInspectionMap.get("message"));
			responseObjectsMap.put("inwardInspectionVO", inwardInspectionMap.get("inwardInspectionVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Inward Inspection creation/update failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getInwardInspectionDocId")
	public ResponseEntity<ResponseDTO> getInwardInspectionDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {
		String methodName = "getInwardInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			String docId = inwardInspectionService.getInwardInspectionDocId(orgId, financialYear);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document ID retrieved successfully");
			responseObjectsMap.put("docId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Document ID retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/viewFile/**")
	public ResponseEntity<byte[]> viewInwardInspectionFile(HttpServletRequest request) {
		String methodName = "viewInwardInspectionFile()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		try {
			return inwardInspectionService.viewInwardInspectionFile(request);

		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
			return ResponseEntity.status(500).build();
		}
	}

//    @GetMapping("/getSupplierDetailsForInwardInspection")
//  public ResponseEntity<ResponseDTO> getSupplierDetailsForInwardInspection(@RequestParam Long orgId, @RequestParam Long branch) {
//      String methodName = "getSupplierDetailsForInwardInspection()";
//      LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//      Map<String, Object> responseObjectsMap = new HashMap<>();
//      ResponseDTO responseDTO;
//
//      try {
//          List<Map<String, Object>> supplierDetails = inwardInspectionService.getSupplierDetailsForInwardInspection(orgId, branch);
//
//          responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier details retrieved successfully");
//          responseObjectsMap.put("supplierDetails", supplierDetails);
//
//          responseDTO = createServiceResponse(responseObjectsMap);
//
//      } catch (Exception e) {
//          LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
//
//          responseDTO = createServiceResponseError(responseObjectsMap, "Supplier details retrieval failed", e.getMessage());
//      }
//
//      LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//      return ResponseEntity.ok(responseDTO);
//  }
}