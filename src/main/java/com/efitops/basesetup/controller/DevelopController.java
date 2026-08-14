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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentContractDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentItemDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentResponseDto;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDto;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentResponseDTO;
import com.efitops.basesetup.dto.SalesReturnDTO;
import com.efitops.basesetup.dto.SalesReturnResponseDTO;
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
            @RequestPart("enquiryDTO") EnquiryDTO enquiryDTO,
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





//SALES RETURN

@PostMapping("/createUpdateSalesReturn")
public ResponseEntity updateCreateSalesReturn(
        @RequestBody SalesReturnDTO salesReturnDTO) {

    String methodName = "createUpdateSalesReturn()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    Map<String, Object> responseObjectsMap = new HashMap<>();

    String errorMsg = null;

    ResponseDTO responseDTO = null;

    try {

        Map<String, Object> responseMap =
                developService.createUpdateSalesReturn(
                        salesReturnDTO);

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                responseMap.get("message"));

        responseObjectsMap.put(
                "salesReturnVO",
                responseMap.get("salesReturnVO"));

        responseDTO =
                createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        errorMsg = e.getMessage();

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                errorMsg);

        responseDTO =
                createServiceResponseError(
                        responseObjectsMap,
                        errorMsg,
                        errorMsg);
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok().body(responseDTO);
}
@GetMapping("/getSalesReturnById")
public ResponseEntity<ResponseDTO> getSalesReturnById(
        @RequestParam Long id) {

    String methodName = "getSalesReturnById()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    String errorMsg = null;

    Map<String, Object> responseObjectsMap =
            new HashMap<>();

    ResponseDTO responseDTO = null;

    SalesReturnResponseDTO salesReturnResponseDTO = null;

    try {

        salesReturnResponseDTO =
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
                "Sales Return retrieved successfully");

        responseObjectsMap.put(
                "salesReturnResponseDTO",
                salesReturnResponseDTO);

        responseDTO =
                createServiceResponse(responseObjectsMap);

    } else {

        responseDTO =
                createServiceResponseError(
                        responseObjectsMap,
                        "Sales Return retrieval failed",
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

    Map<String, Object> responseObjectsMap =
            new HashMap<>();

    ResponseDTO responseDTO = null;

    List<SalesReturnResponseDTO> salesReturnResponseDTO =
            new ArrayList<>();

    try {

        salesReturnResponseDTO =
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
                "Sales Return retrieved successfully");

        responseObjectsMap.put(
                "salesReturnResponseDTO",
                salesReturnResponseDTO);

        responseDTO =
                createServiceResponse(responseObjectsMap);

    } else {

        responseDTO =
                createServiceResponseError(
                        responseObjectsMap,
                        "Sales Return retrieval failed",
                        errorMsg);
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok().body(responseDTO);
}


//salesorderamendment


@PutMapping("/createUpdateSalesOrderAmendment")
public ResponseEntity<ResponseDTO> createUpdateSalesOrderAmendment(
      @RequestBody SalesOrderAmendmentDTO salesOrderAmendmentDTO) {

  String methodName = "createUpdateSalesOrderAmendment";
  LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  Map<String, Object> responseObjectsMap = new HashMap<>();
  ResponseDTO responseDTO;

  try {

      Map<String, Object> responseMap =
              developService.createUpdateSalesOrderAmendment(
                      salesOrderAmendmentDTO);

      responseObjectsMap.put(
              CommonConstant.STRING_MESSAGE,
              responseMap.get("message"));

      responseObjectsMap.put(
              "salesOrderAmendment",
              responseMap.get("salesOrderAmendmentVO"));

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


@GetMapping("/getSalesOrderAmendmentById")
public ResponseEntity<ResponseDTO> getSalesOrderAmendmentById(
      @RequestParam Long id) {

  String methodName = "getSalesOrderAmendmentById()";
  LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  String errorMsg = null;
  Map<String, Object> responseObjectsMap = new HashMap<>();
  ResponseDTO responseDTO = null;

  SalesOrderAmendmentResponseDTO salesOrderAmendment = null;

  try {

      salesOrderAmendment =
              developService.getSalesOrderAmendmentById(id);

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
              "Sales Order Amendment information retrieved successfully");

      responseObjectsMap.put(
              "salesOrderAmendment",
              salesOrderAmendment);

      responseDTO = createServiceResponse(responseObjectsMap);

  } else {

      responseDTO = createServiceResponseError(
              responseObjectsMap,
              "Sales Order Amendment information retrieval failed",
              errorMsg);
  }

  LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

  return ResponseEntity.ok().body(responseDTO);
}


@GetMapping("/getSalesOrderAmendmentByOrgId")
public ResponseEntity<ResponseDTO> getSalesOrderAmendmentByOrgId(
      @RequestParam Long orgId,
      @RequestParam Long branch) {

  String methodName = "getSalesOrderAmendmentByOrgId()";
  LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  String errorMsg = null;
  Map<String, Object> responseObjectsMap = new HashMap<>();
  ResponseDTO responseDTO = null;

  List<SalesOrderAmendmentResponseDTO> salesOrderAmendmentList =
          new ArrayList<>();

  try {

      salesOrderAmendmentList =
              developService.getSalesOrderAmendmentByOrgId(
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
              "Sales Order Amendment information retrieved successfully");

      responseObjectsMap.put(
              "salesOrderAmendmentList",
              salesOrderAmendmentList);

      responseDTO = createServiceResponse(responseObjectsMap);

  } else {

      responseDTO = createServiceResponseError(
              responseObjectsMap,
              "Sales Order Amendment information retrieval failed",
              errorMsg);
  }

  LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

  return ResponseEntity.ok().body(responseDTO);
}


@GetMapping("/getOrderAcceptanceBySalesOrderAmendment")
public ResponseEntity<ResponseDTO> getOrderAcceptanceBySalesOrderAmendment(
      @RequestParam Long orgId,
      @RequestParam Long branch) {

  String methodName = "getOrderAcceptanceBySalesOrderAmendment()";
  LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  String errorMsg = null;
  Map<String, Object> responseObjectsMap = new HashMap<>();
  ResponseDTO responseDTO;

  try {

      List<Map<String, Object>> responseList =
      		developService.getOrderAcceptanceBySalesOrderAmendment(
                      orgId, branch);

      responseObjectsMap.put(
              CommonConstant.STRING_MESSAGE,
              "Order Acceptance List Fetched Successfully");

      responseObjectsMap.put(
              "orderAcceptanceList",
              responseList);

      responseDTO = createServiceResponse(responseObjectsMap);

  } catch (Exception e) {

      errorMsg = e.getMessage();

      LOGGER.error(
              UserConstants.ERROR_MSG_METHOD_NAME,
              methodName,
              errorMsg);

      responseDTO = createServiceResponseError(
              responseObjectsMap,
              "Order Acceptance List Fetch Failed",
              errorMsg);
  }

  LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

  return ResponseEntity.ok().body(responseDTO);
}


//@GetMapping("/getItemDropdownBySalesOrderAmendment")
//public ResponseEntity<ResponseDTO> getItemDropdownBySalesOrderAmendment(
//      @RequestParam Long salesContractId,
//      @RequestParam Long orgId,
//      @RequestParam Long branch) {
//
//  String methodName = "getItemDropdownBySalesOrderAmendment";
//
//  LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//  Map<String, Object> responseObjectsMap = new HashMap<>();
//
//  ResponseDTO responseDTO;
//
//  try {
//
//      Map<String, Object> responseMap =
//      		developService.getItemDropdownBySalesOrderAmendment(
//      		        salesContractId,
//      		        orgId,
//      		        branch);
//
//      responseObjectsMap.put(
//              CommonConstant.STRING_MESSAGE,
//              responseMap.get("message"));
//
//      responseObjectsMap.put(
//              "itemList",
//              responseMap.get("itemList"));
//
//      responseDTO = createServiceResponse(responseObjectsMap);
//
//  } catch (Exception e) {
//
//      LOGGER.error(
//              UserConstants.ERROR_MSG_METHOD_NAME,
//              methodName,
//              e.getMessage(),
//              e);
//
//      responseDTO = createServiceResponseError(
//              responseObjectsMap,
//              e.getMessage(),
//              e.getMessage());
//  }
//
//  LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//  return ResponseEntity.ok(responseDTO);
//}

@GetMapping("/getItemsDetailsbySalesOrderAmendment")
public ResponseEntity<ResponseDTO> getItemsDetailsbySalesOrderAmendment(
      @RequestParam String docId,
      @RequestParam Long orgId,
      @RequestParam Long branch) {

  String methodName = "getOrderAcceptanceItemsWithAmendment()";
  LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  String errorMsg = null;
  Map<String, Object> responseObjectsMap = new HashMap<>();
  ResponseDTO responseDTO = null;

  try {

      List<Map<String, Object>> itemDetails =
      		developService.getOrderAcceptanceItemsWithAmendment(
                      docId,
                      orgId,
                      branch);

      responseObjectsMap.put(
              CommonConstant.STRING_MESSAGE,
              "Order Acceptance Item Details Fetched Successfully");

      responseObjectsMap.put(
              "itemDetails",
              itemDetails);

      responseDTO = createServiceResponse(responseObjectsMap);

  } catch (Exception e) {

      errorMsg = e.getMessage();

      LOGGER.error(
              UserConstants.ERROR_MSG_METHOD_NAME,
              methodName,
              errorMsg);
  }

  if (StringUtils.isBlank(errorMsg)) {

      responseDTO = createServiceResponse(responseObjectsMap);

  } else {

      responseDTO = createServiceResponseError(
              responseObjectsMap,
              "Order Acceptance Item Details Fetch Failed",
              errorMsg);
  }

  LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

  return ResponseEntity.ok().body(responseDTO);
}

@GetMapping("/getSalesOrderAmdRevisionNo")
public ResponseEntity<ResponseDTO> getSalesOrderAmdRevisionNo(
      @RequestParam String salesOrderNo,
      @RequestParam Long item,
      @RequestParam Long orgId,
      @RequestParam Long branch) {

  String methodName = "getSalesOrderAmdRevisionNo()";
  LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

  String errorMsg = null;
  Map<String, Object> responseObjectsMap = new HashMap<>();
  ResponseDTO responseDTO = null;

  try {

      Integer revisionNo =
      		developService.getSalesOrderAmdRevisionNo(
                      salesOrderNo,
                      item,
                      orgId,
                      branch);

      responseObjectsMap.put(
              CommonConstant.STRING_MESSAGE,
              "Revision No Loaded Successfully");

      responseObjectsMap.put(
              "revisionNo",
              revisionNo);

  } catch (Exception e) {

      errorMsg = e.getMessage();

      LOGGER.error(
              UserConstants.ERROR_MSG_METHOD_NAME,
              methodName,
              errorMsg);
  }

  if (StringUtils.isBlank(errorMsg)) {

      responseDTO = createServiceResponse(responseObjectsMap);

  } else {

      responseDTO = createServiceResponseError(
              responseObjectsMap,
              "Revision No Loading Failed",
              errorMsg);
  }

  LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

  return ResponseEntity.ok().body(responseDTO);
}

//Purchase contract amendment


@PutMapping(
        value = "/createUpdatePurchaseContractAmendment",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
)
public ResponseEntity<ResponseDTO> createUpdatePurchaseContractAmendment(
        @RequestPart("purchaseContractAmendment")
        PurchaseContractAmendmentDto purchaseContractAmendmentDto,

        @RequestPart(value = "files", required = false)
        MultipartFile[] files) {

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

        Map<String, Object> purchaseContractAmendmentMap =
                developService.createUpdatePurchaseContractAmendment(
                        purchaseContractAmendmentDto,
                        files);

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                purchaseContractAmendmentMap.get("message"));

        responseObjectsMap.put(
                "purchaseContractAmendmentVO",
                purchaseContractAmendmentMap.get(
                        "purchaseContractAmendmentVO"));

        responseDTO =
                createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        e.printStackTrace();

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                e.getMessage(),
                e.getMessage());
    }

    return ResponseEntity.ok(responseDTO);
}
// =========================
// Get By Id
// =========================

@GetMapping("/getPurchaseContractAmendmentById")
public ResponseEntity<ResponseDTO> getPurchaseContractAmendmentById(
        @RequestParam Long id) {

    String methodName = "getPurchaseContractAmendmentById()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

        PurchaseContractAmendmentResponseDto response =
                developService.getPurchaseContractAmendmentById(id);

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                "Purchase Contract Amendment information retrieved successfully");

        responseObjectsMap.put(
                "purchaseContractAmendmentResponseVO",
                response);

        responseDTO = createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                e.getMessage());

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Purchase Contract Amendment information retrieval failed",
                e.getMessage());
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok(responseDTO);
}

// =========================
// Get By Org Id
// =========================

@GetMapping("/getPurchaseContractAmendmentByOrgId")
public ResponseEntity<ResponseDTO> getPurchaseContractAmendmentByOrgId(
        @RequestParam Long orgId,
        @RequestParam Long branch) {

    String methodName = "getPurchaseContractAmendmentByOrgId()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

        List<PurchaseContractAmendmentResponseDto> response =
                developService.getPurchaseContractAmendmentByOrgId(
                        orgId,
                        branch);

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                "Purchase Contract Amendment information retrieved successfully");

        responseObjectsMap.put(
                "purchaseContractAmendmentResponseVO",
                response);

        responseDTO = createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                e.getMessage());

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Purchase Contract Amendment information retrieval failed",
                e.getMessage());
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok(responseDTO);
}

// =========================
// Contract No Dropdown
// =========================

@GetMapping("/getContractNoDropdownforPurchaseContractAmendment")
public ResponseEntity<ResponseDTO>
getContractNoDropdownforPurchaseContractAmendment(
        @RequestParam Long orgId,
        @RequestParam Long branch) {

    String methodName =
            "getContractNoDropdownforPurchaseContractAmendment()";

    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

        List<PurchaseContractAmendmentContractDropdownResponseDto> response =
                developService
                .getContractNoDropdownforPurchaseContractAmendment(
                        orgId,
                        branch);

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                "Purchase Contract dropdown retrieved successfully");

        responseObjectsMap.put(
                "purchaseContractDropdownResponseVO",
                response);

        responseDTO = createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                e.getMessage());

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Purchase Contract dropdown retrieval failed",
                e.getMessage());
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok(responseDTO);
}


//=========================
//Item Dropdown
//=========================

@GetMapping("/getItemDropdownForPurchaseContractAmendment")
public ResponseEntity<ResponseDTO> getItemDropdownForPurchaseContractAmendment(
     @RequestParam Long contractId) {

 String methodName = "getItemDropdownForPurchaseContractAmendment()";
 LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

 Map<String, Object> responseObjectsMap = new HashMap<>();
 ResponseDTO responseDTO;

 try {

     List<PurchaseContractAmendmentItemDropdownResponseDto> response =
             developService.getItemDropdownForPurchaseContractAmendment(
                     contractId);

     responseObjectsMap.put(
             CommonConstant.STRING_MESSAGE,
             "Item dropdown retrieved successfully");

     responseObjectsMap.put(
             "purchaseContractAmendmentItemDropdownResponseVO",
             response);

     responseDTO = createServiceResponse(responseObjectsMap);

 } catch (Exception e) {

     LOGGER.error(
             UserConstants.ERROR_MSG_METHOD_NAME,
             methodName,
             e.getMessage());

     responseDTO = createServiceResponseError(
             responseObjectsMap,
             "Item dropdown retrieval failed",
             e.getMessage());
 }

 LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

 return ResponseEntity.ok(responseDTO);
}


@GetMapping("/getPurchaseContractAmdRevisionNo")
public ResponseEntity<ResponseDTO> getPurchaseContractAmdRevisionNo(
        @RequestParam String contractNo,
        @RequestParam Long orgId,
        @RequestParam Long branch) {

    String methodName = "getPurchaseContractAmdRevisionNo()";
    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

    Map<String, Object> responseObjectsMap = new HashMap<>();
    ResponseDTO responseDTO;

    try {

        Integer revisionNo = developService.getPurchaseContractAmdRevisionNo(
                contractNo,
                orgId,
                branch);

        responseObjectsMap.put(
                CommonConstant.STRING_MESSAGE,
                "Revision No retrieved successfully");

        responseObjectsMap.put("revisionNo", revisionNo);

        responseDTO = createServiceResponse(responseObjectsMap);

    } catch (Exception e) {

        LOGGER.error(
                UserConstants.ERROR_MSG_METHOD_NAME,
                methodName,
                e.getMessage());

        responseDTO = createServiceResponseError(
                responseObjectsMap,
                "Revision No retrieval failed",
                e.getMessage());
    }

    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

    return ResponseEntity.ok(responseDTO);
}
}