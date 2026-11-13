package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.ResponseDTO.CustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeDropdownResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.CustomerDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.PartyMasterService;




@RestController
@RequestMapping("/api/partyMaster")
@CrossOrigin(origins = "*")
public class PartyMasterController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterController.class);

    @Autowired
    private PartyMasterService partyMasterService;

    @PutMapping("/createUpdateCustomer")
    public ResponseEntity<ResponseDTO> createUpdateCustomer(
            @Valid @RequestBody CustomerDTO customerDTO) {

        String methodName = "createUpdateCustomer()";

        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;

        Map<String, Object> responseObjectsMap = new HashMap<>();

        ResponseDTO responseDTO = null;

        try {

            Map<String, Object> customerResponse =
                    partyMasterService.createUpdateCustomer(customerDTO);

            responseObjectsMap.put(
                    CommonConstant.STRING_MESSAGE,
                    customerResponse.get("message"));

            responseObjectsMap.put(
                    "customer",
                    customerResponse.get("customer"));

            responseDTO = createServiceResponse(responseObjectsMap);

            LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {

            errorMsg = e.getMessage();

            LOGGER.error(
                    UserConstants.ERROR_MSG_METHOD_NAME,
                    methodName,
                    errorMsg,
                    e);

            responseDTO = createServiceResponseError(
                    responseObjectsMap,
                    errorMsg,
                    errorMsg);

            LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseDTO);
        }
    }

    @GetMapping("/getCustomerById")
    public ResponseEntity<ResponseDTO> getCustomerById(
            @RequestParam Long id) {

        String methodName = "getCustomerById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        ResponseDTO responseDTO = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();

        CustomerResponseDTO customerResponseDTO = null;

        try {

            customerResponseDTO = partyMasterService.getCustomerById(id);

        } catch (Exception e) {

            errorMsg = e.getMessage();

            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
                    methodName,
                    errorMsg);
        }

        if (!StringUtils.hasText(errorMsg)) {

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
                    "Customer information retrieved successfully");

            responseObjectsMap.put("customer", customerResponseDTO);

            responseDTO = createServiceResponse(responseObjectsMap);

        } else {

            responseDTO = createServiceResponseError(
                    responseObjectsMap,
                    "Customer information retrieval failed",
                    errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
    
    @GetMapping("/getCustomerByOrgId")
    public ResponseEntity<ResponseDTO> getCustomerByOrgId(
            @RequestParam Long orgId,
            @RequestParam Long branch) {

        String methodName = "getCustomerByOrgId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        ResponseDTO responseDTO = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();

        try {

        	List<CustomerResponseDTO> customerList =
        	        partyMasterService.getCustomerByOrgIdAndBranch(orgId, branch);

        	responseObjectsMap.put("customerList", customerList);

            responseObjectsMap.put(
                    CommonConstant.STRING_MESSAGE,
                    "Customer list retrieved successfully");

            responseDTO = createServiceResponse(responseObjectsMap);

        } catch (Exception e) {

            errorMsg = e.getMessage();

            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
                    methodName,
                    errorMsg);

            responseDTO = createServiceResponseError(
                    responseObjectsMap,
                    "Customer retrieval failed",
                    errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok().body(responseDTO);
    }
    
    @GetMapping("/getPurchaseEmployees")
    public ResponseEntity<ResponseDTO> getPurchaseEmployees(
            @RequestParam Long orgId,
            @RequestParam Long branch) {

        String methodName = "getPurchaseEmployees()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;

        List<EmployeeDropdownResponseDTO> employeeList = new ArrayList<>();

        try {

            employeeList = partyMasterService.getPurchaseEmployees(orgId, branch);

        } catch (Exception e) {

            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        if (errorMsg == null || errorMsg.trim().isEmpty()) {

            responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
                    "Purchase Employees fetched successfully");
            responseObjectsMap.put("employees", employeeList);

            responseDTO = createServiceResponse(responseObjectsMap);

        } else {

            responseDTO = createServiceResponseError(
                    responseObjectsMap,
                    "Purchase Employees fetch failed",
                    errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return ResponseEntity.ok(responseDTO);
    }
    
    

}
