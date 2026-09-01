package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SupplierRateContractDTO;
import com.efitops.basesetup.service.SubContractService;


@CrossOrigin
@RestController
@RequestMapping("/api/subContract")
public class SubContractController extends BaseController{


	@Autowired
	SubContractService subContractService;

	public static final Logger LOGGER = LoggerFactory.getLogger(SubContractController.class);

	@PostMapping("/createUpdateSupplierRateContract")
	public ResponseEntity<ResponseDTO> createUpdateSupplierRateContract(
	        @RequestBody SupplierRateContractDTO supplierRateContractDTO) {

	    String methodName = "createUpdateSupplierRateContract()";

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> createdSupplierRateContractVO =
	        		subContractService.createUpdateSupplierRateContract(
	                        supplierRateContractDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                createdSupplierRateContractVO.get("message")
	        );

	        responseObjectsMap.put(
	                "supplierRateContractVO",
	                createdSupplierRateContractVO.get("supplierRateContract")
	        );

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage()
	        );
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getCustomerForSupplierRateContract")
	public ResponseEntity<ResponseDTO> getCustomerForSupplierRateContract(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getCustomerForSupplierRateContract()";

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        List<Map<String, Object>> customerList =
	        		subContractService.getCustomerForSupplierRateContract(
	                        orgId, branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Customer Details Fetched Successfully");

	        responseObjectsMap.put(
	                "customerList",
	                customerList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getServiceForSupplierRateContract")
	public ResponseEntity<ResponseDTO> getServiceForSupplierRateContract(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getServiceForSupplierRateContract()";

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        List<Map<String, Object>> serviceList =
	        		subContractService.getServiceForSupplierRateContract(
	                        orgId, branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Service Details Fetched Successfully");

	        responseObjectsMap.put(
	                "serviceList",
	                serviceList);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getSupplierRateContractById")
	public ResponseEntity<ResponseDTO> getSupplierRateContractById(
	        @RequestParam Long id) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        SupplierRateContractResponseDTO supplierRateContract =
	        		subContractService.getSupplierRateContractById(id);

	        responseObjectsMap.put(
	                "supplierRateContract",
	                supplierRateContract);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Supplier Rate Contract fetched successfully");

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getSupplierRateContractByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getSupplierRateContractByOrgIdAndBranch(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<SupplierRateContractResponseDTO> supplierRateContracts =
	        		subContractService.getSupplierRateContractByOrgIdAndBranch(
	                        orgId, branch);

	        responseObjectsMap.put(
	                "supplierRateContract",
	                supplierRateContracts);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Supplier Rate Contract List fetched successfully");

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getSupplierRateContractDocId")
	public ResponseEntity<ResponseDTO> getSupplierRateContractDocId(
	        @RequestParam Long orgId,
	        @RequestParam String financialYear,
	        @RequestParam String screenCode) {

	    String methodName = "getSupplierRateContractDocId()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    String mapp = "";

	    try {

	        mapp = subContractService.getSupplierRateContractDocId(
	                orgId,
	                financialYear,
	                screenCode);

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
	                "Supplier Rate Contract DocId information retrieved successfully");

	        responseObjectsMap.put(
	                "supplierRateContractDocId",
	                mapp);

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed to retrieve Supplier Rate Contract DocId",
	                        errorMsg);
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSupplierRateContractItemDropdown")
	public ResponseEntity<ResponseDTO> getSupplierRateContractItemDropdown(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getSupplierRateContractItemDropdown()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<Map<String, Object>> itemDetails =
	        		subContractService.getSupplierRateContractItemDropdown(
	                        orgId, branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Supplier Rate Contract Item Dropdown fetched successfully");

	        responseObjectsMap.put("itemDetails", itemDetails);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}
}
