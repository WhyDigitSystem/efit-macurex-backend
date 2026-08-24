package com.efitops.basesetup.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.efitops.basesetup.ResponseDTO.GateInwardEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseBillResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.PurchaseBillDTO;
import com.efitops.basesetup.dto.PurchaseContractDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.service.PurchaseDeliverySchService;

@CrossOrigin
@RestController
@RequestMapping("/api/purchasedeliveryschedule")

public class PurchaseDeliverySchController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseDeliverySchController.class);

	@Autowired
	PurchaseDeliverySchService purchaseDeliverySchService;

	@PutMapping("/updateCreatePurchaseDeliverySchedule")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseDeliverySchedule(
			@RequestBody PurchaseDeliveryScheduleDTO purchaseDeliveryScheduleDTO) {

		String methodName = "updateCreatePurchaseDeliverySchedule()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = purchaseDeliverySchService
					.updateCreatePurchaseDeliverySchedule(purchaseDeliveryScheduleDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("purchaseDeliveryScheduleVO", responseMap.get("purchaseDeliveryScheduleVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseDeliveryScheduleById")
	public ResponseEntity<ResponseDTO> getPurchaseDeliveryScheduleById(@RequestParam Long id) {

		String methodName = "getPurchaseDeliveryScheduleById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			PurchaseDeliveryScheduleResponseDTO purchaseDeliveryScheduleResponseDTO = purchaseDeliverySchService
					.getPurchaseDeliveryScheduleById(id);

			responseObjectsMap.put("purchaseDeliveryScheduleVO", purchaseDeliveryScheduleResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseDeliveryScheduleByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseDeliveryScheduleByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getPurchaseDeliveryScheduleByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			List<PurchaseDeliveryScheduleResponseDTO> responseList = purchaseDeliverySchService
					.getPurchaseDeliveryScheduleByOrgId(orgId, branch);

			responseObjectsMap.put("purchaseDeliveryScheduleVO", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	// supplier dropdown
	@GetMapping("/getSupplierDropdownForPurchaseDeliverySchedule")
	public ResponseEntity<ResponseDTO> getSupplierDropdownForPurchaseDeliverySchedule(@RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getSupplierDropdownForPurchaseDeliverySchedule()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = purchaseDeliverySchService
					.getSupplierDropdownForPurchaseDeliverySchedule(branch, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("supplierList", responseMap.get("supplierList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}
	
//	item dropdown fro purchasedeliveryschedule
	@GetMapping("/getItemsForPurchaseDeliverySchedule")
	public ResponseEntity<Map<String, Object>> getItemsForPurchaseDeliverySchedule(
	        @RequestParam String purchasecontractnumber,
	        @RequestParam Long customer,
	        @RequestParam Long branch,
	        @RequestParam Long orgId) throws ApplicationException {

	    String methodName = "getItemsForPurchaseDeliverySchedule()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseMap = purchaseDeliverySchService
	            .getItemsForPurchaseDeliverySchedule(
	                    purchasecontractnumber,
	                    customer,
	                    branch,
	                    orgId);

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseMap);
	}
//	Purchase order number dropdown
	@GetMapping("/getPurchaseOrderNumberForPurchaseDeliverySchedule")
	public ResponseEntity<Map<String, Object>> getPurchaseOrderNumberForPurchaseDeliverySchedule(
	        @RequestParam Long custid,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate docdt,
	        @RequestParam Long branch,
	        @RequestParam Long orgId) throws ApplicationException {

	    String methodName = "getPurchaseOrderNumberForPurchaseDeliverySchedule()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseMap =
	            purchaseDeliverySchService
	                    .getPurchaseOrderNumberForPurchaseDeliverySchedule(
	                            custid,
	                            docdt,
	                            branch,
	                            orgId);

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseMap);
	}

//	Gate Inward Entry
	@PutMapping("/updateCreateGateInwardEntry")
	public ResponseEntity<ResponseDTO> updateCreateGateInwardEntry(@RequestBody GateInwardEntryDTO gateInwardEntryDTO) {

		String methodName = "updateCreateGateInwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = purchaseDeliverySchService
					.updateCreateGateInwardEntry(gateInwardEntryDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("gateInwardEntryVO", responseMap.get("gateInwardEntryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGateInwardEntryById")
	public ResponseEntity<ResponseDTO> getGateInwardEntryById(@RequestParam Long id) {

		String methodName = "getGateInwardEntryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			GateInwardEntryResponseDTO gateInwardEntryResponseDTO = purchaseDeliverySchService
					.getGateInwardEntryById(id);

			responseObjectsMap.put("gateInwardEntryVO", gateInwardEntryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGateInwardEntryByOrgId")
	public ResponseEntity<ResponseDTO> getGateInwardEntryByOrgId(@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getGateInwardEntryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			List<GateInwardEntryResponseDTO> responseList = purchaseDeliverySchService.getGateInwardEntryByOrgId(branch,
					orgId);

			responseObjectsMap.put("gateInwardEntryVO", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	customername dropdown
	@GetMapping("/getCustomerNameDropdownForGateInwardEntry")
	public ResponseEntity<ResponseDTO> getCustomerNameDropdownForGateInwardEntry(@RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getCustomerNameDropdownForGateInwardEntry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> customerList = purchaseDeliverySchService
					.getCustomerNameDropdownForGateInwardEntry(branch, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Details Fetched Successfully");

			responseObjectsMap.put("customerList", customerList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

//	purchase Contract
	@PostMapping(value = "/updateCreatePurchaseContract",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> updateCreatePurchaseContract(

	        @RequestPart("purchaseContractVO") PurchaseContractDTO purchaseContractDTO,

	        @RequestPart(value = "files", required = false)
	        MultipartFile[] files) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> response =
	                purchaseDeliverySchService.updateCreatePurchaseContract(
	                        purchaseContractDTO,
	                        files);

	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                response.get("message"));
	        responseObjectsMap.put("purchaseContractVO",
	                response.get("purchaseContractVO"));

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
	@GetMapping("/getPurchaseContractById")
	public ResponseEntity<ResponseDTO> getPurchaseContractById(@RequestParam Long id) {

		String methodName = "getPurchaseContractById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			PurchaseContractResponseDTO purchaseContractResponseDTO = purchaseDeliverySchService
					.getPurchaseContractById(id);

			responseObjectsMap.put("purchaseContractVO", purchaseContractResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseContractByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseContractByOrgId(@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getPurchaseContractByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			List<PurchaseContractResponseDTO> responseList = purchaseDeliverySchService
					.getPurchaseContractByOrgId(branch, orgId);

			responseObjectsMap.put("purchaseContractVO", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	// dropdown api for supplier

	@GetMapping("/getSupplierDropdownForPurchaseContract")
	public ResponseEntity<ResponseDTO> getSupplierDropdownForPurchaseContract(@RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getSupplierDropdownForPurchaseContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> supplierList = purchaseDeliverySchService
					.getSupplierDropdownForPurchaseContract(branch, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier Details Fetched Successfully");

			responseObjectsMap.put("supplierList", supplierList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

//	prepared by ,checkedby and authorized by 

	@GetMapping("/getEmployeeDropdownPurchaseContract")
	public ResponseEntity<ResponseDTO> getEmployeeDropdownPurchaseContract(@RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getEmployeeDropdownPurchaseContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> employeeList = purchaseDeliverySchService
					.getEmployeeDropdownPurchaseContract(branch, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee Details Fetched Successfully");

			responseObjectsMap.put("employeeList", employeeList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}
// purchase contract item dropdown
	@GetMapping("/getPurchaseContractItems")
	public ResponseEntity<ResponseDTO> getPurchaseContractItems(
	        @RequestParam Long supplier,
	        @RequestParam Long branch,
	        @RequestParam Long orgId) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> response = purchaseDeliverySchService.getPurchaseContractItems(
	                supplier,
	                branch,
	                orgId);

	        responseObjectsMap.put("itemList", response.get("itemList"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {
	        e.printStackTrace();
	        responseDTO = createServiceResponseError(responseObjectsMap,
	                e.getMessage(), e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
//	purchase bill
	
	@PutMapping("/createUpdatePurchaseBill")
	public ResponseEntity<ResponseDTO> createUpdatePurchaseBill(
	        @RequestBody PurchaseBillDTO purchaseBillDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> purchaseBillMap =
	                purchaseDeliverySchService.createUpdatePurchaseBill(
	                        purchaseBillDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                purchaseBillMap.get("message"));

	        responseObjectsMap.put(
	                "purchaseBillVO",
	                purchaseBillMap.get("purchaseBillVO"));

	        responseDTO = createServiceResponse(
	                responseObjectsMap);

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
//	supllier dropdown fro purchase bill
	@GetMapping("/getSuppliersForPurchaseBill")
	public ResponseEntity<ResponseDTO> getSuppliersForPurchaseBill(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> supplierMap =
	                purchaseDeliverySchService.getSuppliersForPurchaseBill(
	                        orgId,
	                        branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Supplier List Fetched Successfully");

	        responseObjectsMap.put(
	                "supplierList",
	                supplierMap.get("supplierList"));

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
	@GetMapping("/getPurchaseBillById")
	public ResponseEntity<ResponseDTO> getPurchaseBillById(
	        @RequestParam Long id) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        PurchaseBillResponseDTO purchaseBillResponseDTO =
	                purchaseDeliverySchService.getPurchaseBillById(id);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Purchase Bill Fetched Successfully");

	        responseObjectsMap.put(
	                "purchaseBillVO",
	                purchaseBillResponseDTO);

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
	@GetMapping("/getPurchaseBillByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseBillByOrgId(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        List<PurchaseBillResponseDTO> purchaseBillList =
	                purchaseDeliverySchService
	                        .getPurchaseBillByOrgId(orgId, branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Purchase Bill Details Fetched Successfully");

	        responseObjectsMap.put(
	                "purchaseBillList",
	                purchaseBillList);

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
}
