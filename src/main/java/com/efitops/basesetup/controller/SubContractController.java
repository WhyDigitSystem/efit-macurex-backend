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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.JobOrderAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.DeliveryChallanSubcontractingDTO;
import com.efitops.basesetup.dto.JobOrderAmendmentDTO;
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleDTO;
import com.efitops.basesetup.dto.SupplierRateContractDTO;
import com.efitops.basesetup.service.SubContractService;

@CrossOrigin
@RestController
@RequestMapping("/api/subContract")
public class SubContractController extends BaseController {

	@Autowired
	SubContractService subContractService;

	public static final Logger LOGGER = LoggerFactory.getLogger(SubContractController.class);

	@PutMapping("/createUpdateSupplierRateContract")
	public ResponseEntity<ResponseDTO> createUpdateSupplierRateContract(
			@RequestBody SupplierRateContractDTO supplierRateContractDTO) {

		String methodName = "createUpdateSupplierRateContract()";

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> createdSupplierRateContractVO = subContractService
					.createUpdateSupplierRateContract(supplierRateContractDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdSupplierRateContractVO.get("message"));

			responseObjectsMap.put("supplierRateContractVO", createdSupplierRateContractVO.get("supplierRateContract"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getCustomerForSupplierRateContract")
	public ResponseEntity<ResponseDTO> getCustomerForSupplierRateContract(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getCustomerForSupplierRateContract()";

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> customerList = subContractService.getCustomerForSupplierRateContract(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Details Fetched Successfully");

			responseObjectsMap.put("customerList", customerList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getServiceForSupplierRateContract")
	public ResponseEntity<ResponseDTO> getServiceForSupplierRateContract(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getServiceForSupplierRateContract()";

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> serviceList = subContractService.getServiceForSupplierRateContract(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Service Details Fetched Successfully");

			responseObjectsMap.put("serviceList", serviceList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractById")
	public ResponseEntity<ResponseDTO> getSupplierRateContractById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			SupplierRateContractResponseDTO supplierRateContract = subContractService.getSupplierRateContractById(id);

			responseObjectsMap.put("supplierRateContract", supplierRateContract);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier Rate Contract fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getSupplierRateContractByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<SupplierRateContractResponseDTO> supplierRateContracts = subContractService
					.getSupplierRateContractByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("supplierRateContract", supplierRateContracts);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier Rate Contract List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractDocId")
	public ResponseEntity<ResponseDTO> getSupplierRateContractDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getSupplierRateContractDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = subContractService.getSupplierRateContractDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Rate Contract DocId information retrieved successfully");

			responseObjectsMap.put("supplierRateContractDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Supplier Rate Contract DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSupplierRateContractItemDropdown")
	public ResponseEntity<ResponseDTO> getSupplierRateContractItemDropdown(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getSupplierRateContractItemDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> itemDetails = subContractService.getSupplierRateContractItemDropdown(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Rate Contract Item Dropdown fetched successfully");

			responseObjectsMap.put("itemDetails", itemDetails);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// JobOrder

	@PutMapping(value = "/createUpdateJobOrder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateJobOrder(
			@RequestPart("jobOrderDTO") JobOrderDTO jobOrderDTO,
//	        @RequestBody JobOrderDTO jobOrderDTO,

			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> jobOrderMap = subContractService.createUpdateJobOrder(jobOrderDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, jobOrderMap.get("message"));

			responseObjectsMap.put("jobOrderVO", jobOrderMap.get("jobOrderVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractforJobOrder")
	public ResponseEntity<ResponseDTO> getSupplierRateContractforJobOrder(@RequestParam Long customer,
			@RequestParam Long orgId, @RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		String methodName = "getSupplierRateContractforJobOrder()";

		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> supplierRateContractList = subContractService
					.getSupplierRateContractDropdown(customer, orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier Rate Contract fetched successfully");

			responseObjectsMap.put("supplierRateContractDropdown", supplierRateContractList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractItemDetailsForJobOrder")
	public ResponseEntity<ResponseDTO> getSupplierRateContractItemDetails(@RequestParam String contractNo,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getSupplierRateContractItemDetailsForJobOrder()";

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> result = subContractService
					.getSupplierRateContractItemDetailsForJobOrder(contractNo, orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Rate Contract Item Details fetched successfully");

			responseObjectsMap.put("supplierRateContractItemDetails", result);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderById")
	public ResponseEntity<ResponseDTO> getJobOrderById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			JobOrderResponseDTO jobOrder = subContractService.getJobOrderById(id);

			responseObjectsMap.put("jobOrder", jobOrder);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getJobOrderByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<JobOrderResponseDTO> jobOrders = subContractService.getJobOrderByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("jobOrder", jobOrders);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderDocId")
	public ResponseEntity<ResponseDTO> getJobOrderDocId(@RequestParam Long orgId, @RequestParam String financialYear) {

		String methodName = "getJobOrderDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = subContractService.getJobOrderDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order DocId information retrieved successfully");

			responseObjectsMap.put("jobOrderDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Job Order DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping(value = "/createUpdateJobOrderAmendment")
	public ResponseEntity<ResponseDTO> createUpdateJobOrderAmendment(
	        @RequestBody
	        JobOrderAmendmentDTO jobOrderAmendmentDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> jobOrderAmendmentMap =
	        		subContractService
	                        .createUpdateJobOrderAmendment(
	                                jobOrderAmendmentDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                jobOrderAmendmentMap.get("message"));

	        responseObjectsMap.put(
	                "jobOrderAmendment",
	                jobOrderAmendmentMap.get("jobOrderAmendment"));

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
	
	@GetMapping("/getJobOrderNoAndDateForJobOrderAmd")
	public ResponseEntity<ResponseDTO> getJobOrderNoAndDateForJobOrderAmd(
	        @RequestParam Long branch,
	        @RequestParam Long orgId,
	        @RequestParam Long customer) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<Map<String, Object>> jobOrderList =
	                subContractService.getJobOrderNoAndDateForJobOrderAmd(
	                        branch, orgId, customer);

	        responseObjectsMap.put("jobOrderList", jobOrderList);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Job Order No and Date fetched successfully");

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
	@GetMapping("/getNextRevisionNoForJobOrderAmd")
	public ResponseEntity<ResponseDTO> getNextRevisionNoForJobOrderAmd(
	        @RequestParam String jobOrderNo,
	        @RequestParam Long branch,
	        @RequestParam Long orgId) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Integer revisionNo = subContractService.getNextRevisionNoForJobOrderAmd(
	                jobOrderNo, branch, orgId);

	        responseObjectsMap.put("revisionNo", revisionNo);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Revision No fetched successfully");

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getJobOrderItemDetailsForJobOrderAmd")
	public ResponseEntity<ResponseDTO> getJobOrderItemDetailsForJobOrderAmd(
	        @RequestParam String jobOrderNo,
	        @RequestParam Long branch,
	        @RequestParam Long orgId,
	        @RequestParam Long customer) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<Map<String, Object>> jobOrderItemDetails =
	                subContractService.getJobOrderItemDetailsForJobOrderAmd(
	                        jobOrderNo, branch, orgId, customer);

	        responseObjectsMap.put(
	                "jobOrderItemDetails",
	                jobOrderItemDetails);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Job Order Item Details fetched successfully");

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
	
	@GetMapping("/getJobOrderAmendmentById")
	public ResponseEntity<ResponseDTO> getJobOrderAmendmentById(@RequestParam Long id) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        JobOrderAmendmentResponseDTO jobOrderAmendment =
	                subContractService.getJobOrderAmendmentById(id);

	        responseObjectsMap.put(
	                "jobOrderAmendment",
	                jobOrderAmendment);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Job Order Amendment fetched successfully");

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getJobOrderAmendmentByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getJobOrderAmendmentByOrgIdAndBranch(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<JobOrderAmendmentResponseDTO> jobOrderAmendments =
	                subContractService.getJobOrderAmendmentByOrgIdAndBranch(
	                        orgId, branch);

	        responseObjectsMap.put(
	                "jobOrderAmendment",
	                jobOrderAmendments);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Job Order Amendment List fetched successfully");

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getJobOrderAmendmentDocId")
	public ResponseEntity<ResponseDTO> getJobOrderAmendmentDocId(
	        @RequestParam Long orgId,
	        @RequestParam String financialYear) {

	    String methodName = "getJobOrderAmendmentDocId()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    String mapp = "";

	    try {

	        mapp = subContractService.getJobOrderAmendmentDocId(
	                orgId,
	                financialYear);

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
	                "Job Order Amendment DocId information retrieved successfully");

	        responseObjectsMap.put(
	                "jobOrderAmendmentDocId",
	                mapp);

	        responseDTO = createServiceResponse(
	                responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to retrieve Job Order Amendment DocId",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PutMapping(value = "/createUpdateDeliveryChallanSubcontracting")
			public ResponseEntity<ResponseDTO> createUpdateDeliveryChallanSubcontracting(
	        @RequestBody
	        DeliveryChallanSubcontractingDTO deliveryChallanSubcontractingDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> deliveryChallanSubcontractingMap =
	        		subContractService
	                        .createUpdateDeliveryChallanSubcontracting(
	                                deliveryChallanSubcontractingDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                deliveryChallanSubcontractingMap.get("message"));

	        responseObjectsMap.put(
	                "deliveryChallanSubcontracting",
	                deliveryChallanSubcontractingMap.get(
	                        "deliveryChallanSubcontracting"));

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        e.getMessage(),
	                        e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getLocationForDeliverChallanSubContract")
	public ResponseEntity<ResponseDTO> getLocationForDeliverChallanSubContract(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getLocationForDeliverChallanSubContract()";

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        List<Map<String, Object>> locationList =
	        		subContractService
	                        .getLocationForDeliverChallanSubContract(
	                                orgId,
	                                branch);

	        responseObjectsMap.put(
	                "locationList",
	                locationList);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Sub Contract Location List fetched successfully");

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        e.getMessage(),
	                        e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getItemDetailsforDeliveryChallanSubContract")
	public ResponseEntity<ResponseDTO> getItemDetailsforDeliveryChallanSubContract(
	        @RequestParam String jobOrderNo,
	        @RequestParam Long branch,
	        @RequestParam Long orgId,
	        @RequestParam Long vendor) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<Map<String, Object>> itemDetails =
	                subContractService.getItemDetailsforDeliveryChallanSubContract(
	                        jobOrderNo,
	                        branch,
	                        orgId,
	                        vendor);

	        responseObjectsMap.put(
	                "itemDetails",
	                itemDetails);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Item Details fetched successfully");

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
	
	//SubContractSupplySchedule
	
	
//	@PutMapping(value = "/createUpdateSubContractSupplySchedule")
//	public ResponseEntity<ResponseDTO> createUpdateSubContractSupplySchedule(
//	        @RequestBody SubContractSupplyScheduleDTO subContractSupplyScheduleDTO) {
//
//	    Map<String, Object> responseObjectsMap = new HashMap<>();
//	    ResponseDTO responseDTO;
//
//	    try {
//
//	        Map<String, Object> subContractSupplyScheduleMap =
//	        		subContractService.createUpdateSubContractSupplySchedule(
//	                        subContractSupplyScheduleDTO);
//
//	        responseObjectsMap.put(
//	                CommonConstant.STRING_MESSAGE,
//	                subContractSupplyScheduleMap.get("message"));
//
//	        responseObjectsMap.put(
//	                "subContractSupplyScheduleVO",
//	                subContractSupplyScheduleMap.get("subContractSupplyScheduleVO"));
//
//	        responseDTO = createServiceResponse(responseObjectsMap);
//
//	    } catch (Exception e) {
//
//	        e.printStackTrace();
//
//	        responseDTO = createServiceResponseError(
//	                responseObjectsMap,
//	                e.getMessage(),
//	                e.getMessage());
//	    }
//
//	    return ResponseEntity.ok(responseDTO);
//	}
}
