package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.LocalPurchaseOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseIndentResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseShortCloseResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.LocalPurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseBillDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseShortCloseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.PurchaseService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@CrossOrigin
@RestController
@RequestMapping("/api/purchaseservice")
public class PurchaseController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

	@Autowired
	PurchaseService purchaseService;

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	@PostMapping(value = "/createUpdatePurchaseIndent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdatePurchaseIndent(
			@RequestPart("purchaseIndent") PurchaseIndentDTO purchaseIndentDTO,

			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> purchaseIndentMap = purchaseService.createUpdatePurchaseIndent(purchaseIndentDTO,
					files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseIndentMap.get("message"));
			responseObjectsMap.put("purchaseIndentVO", purchaseIndentMap.get("purchaseIndentVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();
			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());

		}

		return ResponseEntity.ok(responseDTO);
	}

	// ==================================================================
	// PURCHASE CONTRACT — original paths: /api/purchaseContract/**
	// ==================================================================

//    @PostMapping(value = "/api/purchaseMaster/updateCreatePurchaseContract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ResponseDTO> updateCreatePurchaseContract(
//            @RequestPart("purchaseContractDTO") String purchaseContractJson,
//            @RequestPart(value = "files", required = false) MultipartFile[] files) {
//
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO;
//        try {
//            PurchaseContractDTO dto = objectMapper.readValue(purchaseContractJson, PurchaseContractDTO.class);
//            Map<String, Object> result = purchaseService.updateCreatePurchaseContract(dto, files);
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
//            responseObjectsMap.put("purchaseContractVO", result.get("purchaseContractVO"));
//            responseDTO = createServiceResponse(responseObjectsMap);
//        } catch (Exception e) {
//            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, "updateCreatePurchaseContract()", e.getMessage());
//            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract Save Failed", e.getMessage());
//        }
//        return ResponseEntity.ok(responseDTO);
//    }
//
//    @GetMapping("/api/purchaseMaster/getPurchaseContractById")
//    public ResponseEntity<ResponseDTO> getPurchaseContractById(@RequestParam Long id) {
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO;
//        try {
//            PurchaseContractResponseDTO result = purchaseService.getPurchaseContractById(id);
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Contract information retrieved successfully");
//            responseObjectsMap.put("purchaseContractVO", result);
//            responseDTO = createServiceResponse(responseObjectsMap);
//        } catch (Exception e) {
//            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract information retrieval failed", e.getMessage());
//        }
//        return ResponseEntity.ok(responseDTO);
//    }
//
//    @GetMapping("/api/purchaseMaster/getPurchaseContractByOrgId")
//    public ResponseEntity<ResponseDTO> getPurchaseContractByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO;
//        try {
//            List<PurchaseContractResponseDTO> result = purchaseService.getPurchaseContractByOrgId(orgId, branchId);
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Contract information retrieved successfully");
//            responseObjectsMap.put("purchaseContractVO", result);
//            responseDTO = createServiceResponse(responseObjectsMap);
//        } catch (Exception e) {
//            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract information retrieval failed", e.getMessage());
//        }
//        return ResponseEntity.ok(responseDTO);
//    }
//

	// ==================================================================
	// PURCHASE DELIVERY SCHEDULE — original paths: /api/purchaseDeliverySchedule/**
	// ==================================================================

//    @PostMapping("/api/purchaseMaster/updateCreatePurchaseDeliverySchedule")
//    public ResponseEntity<ResponseDTO> updateCreatePurchaseDeliverySchedule(@RequestBody PurchaseDeliveryScheduleDTO dto) {
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO;
//        try {
//            Map<String, Object> result = purchaseService.updateCreatePurchaseDeliverySchedule(dto);
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
//            responseObjectsMap.put("purchaseDeliveryScheduleVO", result.get("purchaseDeliveryScheduleVO"));
//            responseDTO = createServiceResponse(responseObjectsMap);
//        } catch (Exception e) {
//            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Delivery Schedule Save Failed", e.getMessage());
//        }
//        return ResponseEntity.ok(responseDTO);
//    }
//
//    @GetMapping("/api/purchaseMaster/getPurchaseDeliveryScheduleById")
//    public ResponseEntity<ResponseDTO> getPurchaseDeliveryScheduleById(@RequestParam Long id) {
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO;
//        try {
//            PurchaseDeliveryScheduleResponseDTO result = purchaseService.getPurchaseDeliveryScheduleById(id);
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Delivery Schedule information retrieved successfully");
//            responseObjectsMap.put("purchaseDeliveryScheduleVO", result);
//            responseDTO = createServiceResponse(responseObjectsMap);
//        } catch (Exception e) {
//            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Delivery Schedule information retrieval failed", e.getMessage());
//        }
//        return ResponseEntity.ok(responseDTO);
//    }
//
//    @GetMapping("/api/purchaseMaster/getPurchaseDeliveryScheduleByOrgId")
//    public ResponseEntity<ResponseDTO> getPurchaseDeliveryScheduleByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO;
//        try {
//            List<PurchaseDeliveryScheduleResponseDTO> result = purchaseService.getPurchaseDeliveryScheduleByOrgId(orgId, branchId);
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Delivery Schedule information retrieved successfully");
//            responseObjectsMap.put("purchaseDeliveryScheduleVO", result);
//            responseDTO = createServiceResponse(responseObjectsMap);
//        } catch (Exception e) {
//            responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Delivery Schedule information retrieval failed", e.getMessage());
//        }
//        return ResponseEntity.ok(responseDTO);
//    }

	// ==================================================================
	// PURCHASE BILL — original paths: /api/purchaseBill/**
	// ==================================================================

//	@PostMapping("/api/purchaseMaster/updateCreatePurchaseBill")
//	public ResponseEntity<ResponseDTO> updateCreatePurchaseBill(@RequestBody PurchaseBillDTO dto) {
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO;
//		try {
//			Map<String, Object> result = purchaseService.updateCreatePurchaseBill(dto);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
//			responseObjectsMap.put("purchaseBillVO", result.get("purchaseBillVO"));
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Bill Save Failed", e.getMessage());
//		}
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/api/purchaseMaster/getPurchaseBillById")
//	public ResponseEntity<ResponseDTO> getPurchaseBillById(@RequestParam Long id) {
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO;
//		try {
//			PurchaseBillResponseDTO result = purchaseService.getPurchaseBillById(id);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Bill information retrieved successfully");
//			responseObjectsMap.put("purchaseBillVO", result);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Bill information retrieval failed",
//					e.getMessage());
//		}
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/api/purchaseMaster/getPurchaseBillByOrgId")
//	public ResponseEntity<ResponseDTO> getPurchaseBillByOrgId(@RequestParam Long orgId, @RequestParam Long branchId) {
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO;
//		try {
//			List<PurchaseBillResponseDTO> result = purchaseService.getPurchaseBillByOrgId(orgId, branchId);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Bill information retrieved successfully");
//			responseObjectsMap.put("purchaseBillVO", result);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Bill information retrieval failed",
//					e.getMessage());
//		}
//		return ResponseEntity.ok(responseDTO);
//	}

	// ==================================================================
	// PURCHASE INDENT — original paths: /api/purchaseindent/**
	// ==================================================================
//
//    @PostMapping(value = "/createUpdatePurchaseIndent",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ResponseDTO> createUpdatePurchaseIndent(
//            @RequestPart("purchaseIndent") PurchaseIndentDTO purchaseIndentDTO,
//            @RequestPart(value = "files", required = false) MultipartFile[] files) {
//
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//        ResponseDTO responseDTO;
//
//        try {
//
//            Map<String, Object> purchaseIndentMap = purchaseService.createUpdatePurchaseIndent(purchaseIndentDTO,files);
//
//            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseIndentMap.get("message"));
//
//            responseObjectsMap.put("purchaseIndentVO",purchaseIndentMap.get("purchaseIndentVO"));
//
//            responseDTO = createServiceResponse(responseObjectsMap);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            responseDTO = createServiceResponseError(
//                    responseObjectsMap,
//                    e.getMessage(),
//                    e.getMessage());
//        }
//
//        return ResponseEntity.ok(responseDTO);
//    }
//    
//   //get by id 
//    
	@GetMapping("/getPurchaseIndentById")
	public ResponseEntity<ResponseDTO> getPurchaseIndentById(@RequestParam Long id) {

		String methodName = "getPurchaseIndentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			PurchaseIndentResponseDTO purchaseIndentResponseDTO = purchaseService.getPurchaseIndentById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Indent information retrieved successfully");

			responseObjectsMap.put("purchaseIndentResponseVO", purchaseIndentResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Indent information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}
	// get byt orgid

	@GetMapping("/getPurchaseIndentByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseIndentByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getPurchaseIndentByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<PurchaseIndentResponseDTO> purchaseIndentResponseDTO = purchaseService.getPurchaseIndentByOrgId(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Indent information retrieved successfully");

			responseObjectsMap.put("purchaseIndentResponseVO", purchaseIndentResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Indent information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}
//	prepared by dropdown
	@GetMapping("/getPurchaseIndentPreparedByDropdown")
	public ResponseEntity<ResponseDTO> getPurchaseIndentPreparedByDropdown(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getPurchaseIndentPreparedByDropdown()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        List<Map<String, Object>> response =
	                purchaseService.getPurchaseIndentPreparedByDropdown(orgId, branch);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                "Prepared By dropdown retrieved successfully");
	        responseObjectsMap.put("preparedByDropdown", response);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Prepared By dropdown retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getPurchaseIndentItemDropdown")
	public ResponseEntity<ResponseDTO> getPurchaseIndentItemDropdown(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getPurchaseIndentItemDropdown()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        List<Map<String, Object>> response =
	                purchaseService.getPurchaseIndentItemDropdown(orgId, branch);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                "Item dropdown retrieved successfully");
	        responseObjectsMap.put("itemDropdown", response);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Item dropdown retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getPurchaseIndentConversionFactorDropdown")
	public ResponseEntity<ResponseDTO> getPurchaseIndentConversionFactorDropdown(
	        @RequestParam Long orgId,
	        @RequestParam Long branch,
	        @RequestParam Long fromUnit,
	        @RequestParam Long toUnit) {

	    String methodName = "getPurchaseIndentConversionFactorDropdown()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        List<Map<String, Object>> response =
	                purchaseService.getPurchaseIndentConversionFactorDropdown(orgId, branch,fromUnit,toUnit);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                "Conversion Factor dropdown retrieved successfully");
	        responseObjectsMap.put("conversionFactorDropdown", response);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Conversion Factor dropdown retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getPurchaseIndentDepartmentDropdown")
	public ResponseEntity<ResponseDTO> getPurchaseIndentDepartmentDropdown(
	        @RequestParam Long orgId) {

	    String methodName = "getPurchaseIndentDepartmentDropdown()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        List<Map<String, Object>> response =
	                purchaseService.getPurchaseIndentDepartmentDropdown(orgId);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                "Department dropdown retrieved successfully");
	        responseObjectsMap.put("departmentDropdown", response);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Department dropdown retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok(responseDTO);
	}
//    
//    
//    
////    @GetMapping("/getPurchaseIndentDocId")
//    public ResponseEntity<ResponseDTO> getPurchaseIndentDocId(
//            @RequestParam Long orgId,
//            @RequestParam String financialYear,
//            @RequestParam String screenCode) {
//
//        String methodName = "getPurchaseIndentDocId()";
//        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//        String errorMsg = null;
//
//        Map<String, Object> responseObjectsMap = new HashMap<>();
//
//        ResponseDTO responseDTO = null;
//
//        String docId = "";
//
//        try {
//
//            docId = purchaseService.getPurchaseIndentDocId(
//                    orgId,
//                    financialYear,
//                    screenCode);
//
//        } catch (Exception e) {
//
//            errorMsg = e.getMessage();
//
//            LOGGER.error(
//                    UserConstants.ERROR_MSG_METHOD_NAME,
//                    methodName,
//                    errorMsg);
//        }
//
//        if (StringUtils.isBlank(errorMsg)) {
//
//            responseObjectsMap.put(
//                    CommonConstant.STRING_MESSAGE,
//                    "Purchase Indent DocId information retrieved successfully");
//
//            responseObjectsMap.put(
//                    "purchaseIndentDocId",
//                    docId);
//
//            responseDTO = createServiceResponse(responseObjectsMap);
//
//        } else {
//
//            responseDTO = createServiceResponseError(
//                    responseObjectsMap,
//                    "Failed to retrieve Purchase Indent DocId",
//                    errorMsg);
//        }
//
//        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//        return ResponseEntity.ok().body(responseDTO);
//    }
//    
//    
//    
//   //dropdowns
//   
//   
//   
//    @GetMapping("/purchaseIndentDepartmentDropdown")
//    public List<PurchaseIndentDepartmentDropdownResponseDTO>
//            getPurchaseIndentDepartmentDropdown(
//            @RequestParam Long orgId,
//            @RequestParam Long branch)
//            throws ApplicationException {
//
//        return purchaseService.getPurchaseIndentDepartmentDropdown(orgId, branch);
//    }
//    
//    //purchaseindentpreparedbydropdown
//    
//    
//    @GetMapping("/api/purchaseMaster/purchaseIndentPreparedByDropdown")
//    public List<PurchaseIndentPreparedByDropdownResponseDTO>
//    getPurchaseIndentPreparedByDropdown(
//            @RequestParam Long orgId,
//            @RequestParam Long branch)
//            throws ApplicationException {
//
//        return purchaseService
//                .getPurchaseIndentPreparedByDropdown(orgId, branch);
//    }
//    
//    
//    //purchaseindentbywhomedropdown
//    
//    
//    @GetMapping("/api/purchaseMaster/purchaseIndentByWhomDropdown")
//    public ResponseEntity<List<PurchaseIndentByWhomDropdownResponseDTO>>
//    getPurchaseIndentByWhomDropdown(
//            @RequestParam Long orgId,
//            @RequestParam Long branch) throws ApplicationException {
//
//        return ResponseEntity.ok(
//                purchaseService.getPurchaseIndentByWhomDropdown(orgId, branch));
//    }
//    
//    
//    //purchaseindentitemcodedropdown
//    
//    
//    @GetMapping("/api/purchaseMaster/purchaseIndentItemDropdown")
//    public ResponseEntity<List<PurchaseIndentItemDropdownResponseDTO>>
//    getPurchaseIndentItemDropdown(
//            @RequestParam Long orgId,
//            @RequestParam Long branch)
//            throws ApplicationException {
//
//        return ResponseEntity.ok(
//                purchaseService.getPurchaseIndentItemDropdown(orgId, branch));
//    }
//    
//    
//    //purchaseindentconversionfactordropdown
//    
//    
//    @GetMapping("/api/purchaseMaster/purchaseIndentConversionFactorDropdown")
//    public ResponseEntity<List<PurchaseIndentConversionFactorDropdownResponseDTO>>
//    getPurchaseIndentConversionFactorDropdown(
//            @RequestParam Long orgId,
//            @RequestParam Long branch)
//            throws ApplicationException {
//
//        return ResponseEntity.ok(
//                purchaseService.getPurchaseIndentConversionFactorDropdown(orgId, branch));
//    }

	// ==================================================================
	// PURCHASE SHORT CLOSE — /api/purchaseShortClose/**
	// ==================================================================

	@PostMapping("/api/purchaseMaster/updateCreatePurchaseShortClose")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseShortClose(@RequestBody PurchaseShortCloseDTO dto) {
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		try {
			Map<String, Object> result = purchaseService.updateCreatePurchaseShortClose(dto);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
			responseObjectsMap.put("purchaseShortCloseVO", result.get("purchaseShortCloseVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Short Close Save Failed",
					e.getMessage());
		}
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/api/purchaseMaster/getPurchaseShortCloseById")
	public ResponseEntity<ResponseDTO> getPurchaseShortCloseById(@RequestParam Long id) {
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		try {
			PurchaseShortCloseResponseDTO result = purchaseService.getPurchaseShortCloseById(id);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Short Close information retrieved successfully");
			responseObjectsMap.put("purchaseShortCloseVO", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Purchase Short Close information retrieval failed", e.getMessage());
		}
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/api/purchaseMaster/getPurchaseShortCloseByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseShortCloseByOrgId(@RequestParam Long orgId,
			@RequestParam Long branchId) {
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;
		try {
			List<PurchaseShortCloseResponseDTO> result = purchaseService.getPurchaseShortCloseByOrgId(orgId, branchId);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Short Close information retrieved successfully");
			responseObjectsMap.put("purchaseShortCloseVO", result);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Purchase Short Close information retrieval failed", e.getMessage());
		}
		return ResponseEntity.ok(responseDTO);
	}
	// ==================================================================
	// LOCAL PURCHASE ORDER — /api/localPurchaseOrder/**
	// ==================================================================

//	@PostMapping(value = "/api/purchaseMaster/updateCreateLocalPurchaseOrder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<ResponseDTO> updateCreateLocalPurchaseOrder(
//			@RequestPart("localPurchaseOrderDTO") String localPurchaseOrderJson,
//			@RequestPart(value = "files", required = false) MultipartFile[] files) {
//
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO;
//		try {
//			LocalPurchaseOrderDTO dto = objectMapper.readValue(localPurchaseOrderJson, LocalPurchaseOrderDTO.class);
//			Map<String, Object> result = purchaseService.updateCreateLocalPurchaseOrder(dto, files);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, result.get("message"));
//			responseObjectsMap.put("localPurchaseOrderVO", result.get("localPurchaseOrderVO"));
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, "updateCreateLocalPurchaseOrder()", e.getMessage());
//			responseDTO = createServiceResponseError(responseObjectsMap, "Local Purchase Order Save Failed",
//					e.getMessage());
//		}
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/api/purchaseMaster/getLocalPurchaseOrderById")
//	public ResponseEntity<ResponseDTO> getLocalPurchaseOrderById(@RequestParam Long id) {
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO;
//		try {
//			LocalPurchaseOrderResponseDTO result = purchaseService.getLocalPurchaseOrderById(id);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"Local Purchase Order information retrieved successfully");
//			responseObjectsMap.put("localPurchaseOrderVO", result);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Local Purchase Order information retrieval failed", e.getMessage());
//		}
//		return ResponseEntity.ok(responseDTO);
//	}
//
//	@GetMapping("/api/purchaseMaster/getLocalPurchaseOrderByOrgId")
//	public ResponseEntity<ResponseDTO> getLocalPurchaseOrderByOrgId(@RequestParam Long orgId,
//			@RequestParam Long branchId) {
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO;
//		try {
//			List<LocalPurchaseOrderResponseDTO> result = purchaseService.getLocalPurchaseOrderByOrgId(orgId, branchId);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"Local Purchase Order information retrieved successfully");
//			responseObjectsMap.put("localPurchaseOrderVO", result);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Local Purchase Order information retrieval failed", e.getMessage());
//		}
//		return ResponseEntity.ok(responseDTO);
//	}
}