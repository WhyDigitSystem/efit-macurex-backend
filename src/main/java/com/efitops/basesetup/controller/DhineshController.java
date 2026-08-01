package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemDropdownResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesContractDTO;
import com.efitops.basesetup.service.DhineshService;

@CrossOrigin
@RestController
@RequestMapping("/api/dhinesh")
public class DhineshController extends BaseController{


	@Autowired
	DhineshService dhineshService;

	public static final Logger LOGGER = LoggerFactory.getLogger(DhineshController.class);
	
	
	@PostMapping("/createUpdateSalesContract")
	public ResponseEntity<ResponseDTO> createUpdateSalesContract(
	        @RequestBody SalesContractDTO salesContractDTO) {

	    String methodName = "createUpdateSalesContract()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    String errorMsg = null;
	    ResponseDTO responseDTO = null;

	    try {

	        Map<String, Object> createdSalesContractVO =
	        		dhineshService.createUpdateSalesContract(salesContractDTO);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                createdSalesContractVO.get("message"));

	        responseObjectsMap.put("salesContractVO",
	                createdSalesContractVO.get("salesContract"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap, errorMsg, errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getFinishedGoodsItems")
	public ResponseEntity<ResponseDTO> getFinishedGoodsItems(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getFinishedGoodsItems()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    List<SalesContractItemDropdownResponseDTO> itemList = new ArrayList<>();

	    try {

	        itemList = dhineshService.getFinishedGoodsItems(orgId, branch);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	    }

	    if (errorMsg == null || errorMsg.trim().isEmpty()) {

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                "Finished Goods Items fetched successfully");
	        responseObjectsMap.put("items", itemList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Finished Goods Items fetch failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getQuotationDropdown")
	public ResponseEntity<ResponseDTO> getQuotationDropdown(
	        @RequestParam String customerCode,
	        @RequestParam String ctype,
	        @RequestParam Long orgId,
	        @RequestParam Long branch,
	        @RequestParam(required = false, defaultValue = "") String oldQuotationNo,
	        @RequestParam(required = false, defaultValue = "0") Long recId) {

	    String methodName = "getQuotationDropdown()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;

	    List<QuotationDropdownResponseDTO> quotationList = new ArrayList<>();

	    try {

	        quotationList = dhineshService.getQuotationDropdown(
	                customerCode,
	                ctype,
	                orgId,
	                branch,
	                oldQuotationNo,
	                recId);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	    }

	    if (errorMsg == null || errorMsg.trim().isEmpty()) {

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Quotation details fetched successfully");

	        responseObjectsMap.put("quotations", quotationList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Quotation details fetch failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getCustomerDropdown")
    public ResponseEntity<ResponseDTO> getCustomerDropdown(
            @RequestParam String ctype,
            @RequestParam Long orgId,
            @RequestParam Long branch) {

        String methodName = "getCustomerDropdown()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        List<CustomerDropdownResponseDTO> customerList = new ArrayList<>();

        try {

            customerList = dhineshService.getCustomerDropdown(ctype, orgId, branch);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

        }

        if (errorMsg == null || errorMsg.trim().isEmpty()) {

            responseObjectsMap.put(
                    CommonConstant.STRING_MESSAGE,
                    "Customer Details fetched successfully");
            responseObjectsMap.put("customers", customerList);

            responseDTO = createServiceResponse(responseObjectsMap);

        } else {

            responseDTO = createServiceResponseError(
                    responseObjectsMap,
                    "Customer Details fetch failed",
                    errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
}
