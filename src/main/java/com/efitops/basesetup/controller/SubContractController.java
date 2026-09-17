package com.efitops.basesetup.controller;

import java.util.ArrayList;
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

import com.efitops.basesetup.ResponseDTO.AdvForStoresResponseDTO;
import com.efitops.basesetup.ResponseDTO.BomCorrectionRequestNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanCapitalItemsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanCumGatePassResponseDTO;
import com.efitops.basesetup.ResponseDTO.DeliveryChallanSubcontractingResponseDTO;
import com.efitops.basesetup.ResponseDTO.InspectionRequisitionNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.JobOrderShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialPlanningResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionScheduleForNextThreeMonthResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractSupplyScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.SubContractingGRNResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractAmendmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierRateContractResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.AdvForStoresDTO;
import com.efitops.basesetup.dto.BomCorrectionRequestNoteDTO;
import com.efitops.basesetup.dto.DeliveryChallanCapitalItemsDTO;
import com.efitops.basesetup.dto.DeliveryChallanCumGatePassDTO;
import com.efitops.basesetup.dto.DeliveryChallanSubcontractingDTO;
import com.efitops.basesetup.dto.InspectionRequisitionNoteDTO;
import com.efitops.basesetup.dto.JobOrderAmendmentDTO;
import com.efitops.basesetup.dto.JobOrderDTO;
import com.efitops.basesetup.dto.JobOrderShortCloseDTO;
import com.efitops.basesetup.dto.MaterialPlanningDTO;
import com.efitops.basesetup.dto.ProductionScheduleForNextThreeMonthDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SubContractSupplyScheduleDTO;
import com.efitops.basesetup.dto.SubContractingGRNDTO;
import com.efitops.basesetup.dto.SupplierRateContractAmendmentDTO;
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
	public ResponseEntity<ResponseDTO> createUpdateJobOrder(@RequestPart("jobOrderDTO") JobOrderDTO jobOrderDTO,
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
			@RequestBody JobOrderAmendmentDTO jobOrderAmendmentDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> jobOrderAmendmentMap = subContractService
					.createUpdateJobOrderAmendment(jobOrderAmendmentDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, jobOrderAmendmentMap.get("message"));

			responseObjectsMap.put("jobOrderAmendment", jobOrderAmendmentMap.get("jobOrderAmendment"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderNoAndDateForJobOrderAmd")
	public ResponseEntity<ResponseDTO> getJobOrderNoAndDateForJobOrderAmd(@RequestParam Long branch,
			@RequestParam Long orgId, @RequestParam Long customer) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> jobOrderList = subContractService.getJobOrderNoAndDateForJobOrderAmd(branch,
					orgId, customer);

			responseObjectsMap.put("jobOrderList", jobOrderList);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order No and Date fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getNextRevisionNoForJobOrderAmd")
	public ResponseEntity<ResponseDTO> getNextRevisionNoForJobOrderAmd(@RequestParam String jobOrderNo,
			@RequestParam Long branch, @RequestParam Long orgId) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Integer revisionNo = subContractService.getNextRevisionNoForJobOrderAmd(jobOrderNo, branch, orgId);

			responseObjectsMap.put("revisionNo", revisionNo);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Revision No fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderItemDetailsForJobOrderAmd")
	public ResponseEntity<ResponseDTO> getJobOrderItemDetailsForJobOrderAmd(@RequestParam String jobOrderNo,
			@RequestParam Long branch, @RequestParam Long orgId, @RequestParam Long customer) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> jobOrderItemDetails = subContractService
					.getJobOrderItemDetailsForJobOrderAmd(jobOrderNo, branch, orgId, customer);

			responseObjectsMap.put("jobOrderItemDetails", jobOrderItemDetails);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order Item Details fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderAmendmentById")
	public ResponseEntity<ResponseDTO> getJobOrderAmendmentById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			JobOrderAmendmentResponseDTO jobOrderAmendment = subContractService.getJobOrderAmendmentById(id);

			responseObjectsMap.put("jobOrderAmendment", jobOrderAmendment);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order Amendment fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderAmendmentByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getJobOrderAmendmentByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<JobOrderAmendmentResponseDTO> jobOrderAmendments = subContractService
					.getJobOrderAmendmentByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("jobOrderAmendment", jobOrderAmendments);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order Amendment List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderAmendmentDocId")
	public ResponseEntity<ResponseDTO> getJobOrderAmendmentDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getJobOrderAmendmentDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = subContractService.getJobOrderAmendmentDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Job Order Amendment DocId information retrieved successfully");

			responseObjectsMap.put("jobOrderAmendmentDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Job Order Amendment DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeliveryChallanSubcontractingById")
	public ResponseEntity<ResponseDTO> getDeliveryChallanSubcontractingById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			DeliveryChallanSubcontractingResponseDTO response = subContractService
					.getDeliveryChallanSubcontractingById(id);

			responseObjectsMap.put("deliveryChallanSubcontracting", response);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan For Sub Contracting Fetched Successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAllDeliveryChallanSubcontractingByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getAllDeliveryChallanSubcontractingByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<DeliveryChallanSubcontractingResponseDTO> response = subContractService
					.getAllDeliveryChallanSubcontractingByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("deliveryChallanSubcontracting", response);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan For Sub Contracting Fetched Successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDeliveryChallanSubcontractingDocId")
	public ResponseEntity<ResponseDTO> getDeliveryChallanSubcontractingDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			String docId = subContractService.getDeliveryChallanSubcontractingDocId(orgId, financialYear);

			responseObjectsMap.put("docId", docId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document ID Fetched Successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping(value = "/createUpdateDeliveryChallanSubcontracting")
	public ResponseEntity<ResponseDTO> createUpdateDeliveryChallanSubcontracting(
			@RequestBody DeliveryChallanSubcontractingDTO deliveryChallanSubcontractingDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> deliveryChallanSubcontractingMap = subContractService
					.createUpdateDeliveryChallanSubcontracting(deliveryChallanSubcontractingDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, deliveryChallanSubcontractingMap.get("message"));

			responseObjectsMap.put("deliveryChallanSubcontracting",
					deliveryChallanSubcontractingMap.get("deliveryChallanSubcontracting"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getLocationForDeliverChallanSubContract")
	public ResponseEntity<ResponseDTO> getLocationForDeliverChallanSubContract(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getLocationForDeliverChallanSubContract()";

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> locationList = subContractService.getLocationForDeliverChallanSubContract(orgId,
					branch);

			responseObjectsMap.put("locationList", locationList);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sub Contract Location List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getItemDetailsforDeliveryChallanSubContract")
	public ResponseEntity<ResponseDTO> getItemDetailsforDeliveryChallanSubContract(@RequestParam String jobOrderNo,
			@RequestParam Long branch, @RequestParam Long orgId, @RequestParam Long vendor) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> itemDetails = subContractService
					.getItemDetailsforDeliveryChallanSubContract(jobOrderNo, branch, orgId, vendor);

			responseObjectsMap.put("itemDetails", itemDetails);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item Details fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	// SubContractSupplySchedule

	@PutMapping(value = "/createUpdateSubContractSupplySchedule")
	public ResponseEntity<ResponseDTO> createUpdateSubContractSupplySchedule(
			@RequestBody SubContractSupplyScheduleDTO subContractSupplyScheduleDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> subContractSupplyScheduleMap = subContractService
					.createUpdateSubContractSupplySchedule(subContractSupplyScheduleDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, subContractSupplyScheduleMap.get("message"));

			responseObjectsMap.put("subContractSupplyScheduleVO",
					subContractSupplyScheduleMap.get("subContractSupplyScheduleVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderNoAndDateForSubContractSupplySch")
	public ResponseEntity<ResponseDTO> getJobOrderNoAndDateForSubContractSupplySch(@RequestParam Long branch,
			@RequestParam Long orgId, @RequestParam String contractNo) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> jobOrderList = subContractService
					.getJobOrderNoAndDateForSubContractSupplySch(branch, orgId, contractNo);

			responseObjectsMap.put("jobOrderList", jobOrderList);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order No and Date fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSubContractSupplyScheduleById")
	public ResponseEntity<ResponseDTO> getSubContractSupplyScheduleById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			SubContractSupplyScheduleResponseDTO subContractSupplySchedule = subContractService
					.getSubContractSupplyScheduleById(id);

			responseObjectsMap.put("subContractSupplySchedule", subContractSupplySchedule);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sub Contract Supply Schedule fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSubContractSupplyScheduleByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getSubContractSupplyScheduleByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<SubContractSupplyScheduleResponseDTO> subContractSupplySchedules = subContractService
					.getSubContractSupplyScheduleByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("subContractSupplySchedule", subContractSupplySchedules);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sub Contract Supply Schedule List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSubContractSupplyScheduleDocId")
	public ResponseEntity<ResponseDTO> getSubContractSupplyScheduleDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getSubContractSupplyScheduleDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = subContractService.getSubContractSupplyScheduleDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sub Contract Supply Schedule DocId information retrieved successfully");

			responseObjectsMap.put("subContractSupplyScheduleDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Sub Contract Supply Schedule DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateSupplierRateContractAmendment")
	public ResponseEntity<ResponseDTO> createUpdateSupplierRateContractAmendment(
			@RequestBody SupplierRateContractAmendmentDTO supplierRateContractAmendmentDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> supplierRateContractAmendmentMap = subContractService
					.createUpdateSupplierRateContractAmendment(supplierRateContractAmendmentDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, supplierRateContractAmendmentMap.get("message"));

			responseObjectsMap.put("supplierRateContractAmendmentVO",
					supplierRateContractAmendmentMap.get("supplierRateContractAmendmentVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractAmendmentById")
	public ResponseEntity<ResponseDTO> getSupplierRateContractAmendmentById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			SupplierRateContractAmendmentResponseDTO supplierRateContractAmendment = subContractService
					.getSupplierRateContractAmendmentById(id);

			responseObjectsMap.put("supplierRateContractAmendment", supplierRateContractAmendment);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Rate Contract Amendment fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractAmendmentByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getSupplierRateContractAmendmentByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<SupplierRateContractAmendmentResponseDTO> supplierRateContractAmendments = subContractService
					.getSupplierRateContractAmendmentByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("supplierRateContractAmendment", supplierRateContractAmendments);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Rate Contract Amendment List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractAmendmentDocId")
	public ResponseEntity<ResponseDTO> getSupplierRateContractAmendmentDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getSupplierRateContractAmendmentDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = subContractService.getSupplierRateContractAmendmentDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Rate Contract Amendment DocId information retrieved successfully");

			responseObjectsMap.put("supplierRateContractAmendmentDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Supplier Rate Contract Amendment DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getLatestSupplierRateContractAmendmentContractNoDetails")
	public ResponseEntity<ResponseDTO> getRevisionNoDetailsForSupplierRateContractAmd(@RequestParam String contractNo,
			@RequestParam Long orgId, @RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> details = subContractService
					.getRevisionNoDetailsForSupplierRateContractAmd(contractNo, orgId, branch);

			responseObjectsMap.put("contractDetails", details);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Contract details fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierRateContractItemDetailsForSRCAmd")
	public ResponseEntity<ResponseDTO> getSupplierRateContractAmendmentItemDetails(@RequestParam String contractNo,
			@RequestParam Long orgId, @RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> details = subContractService
					.getSupplierRateContractItemDetailsForSRCAmd(contractNo, orgId, branch);

			responseObjectsMap.put("supplierRateContractAmendmentItemDetails", details);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Supplier Rate Contract Amendment Item Details fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@PutMapping("/createUpdateProductionScheduleForNextThreeMonth")
	public ResponseEntity<ResponseDTO> createUpdateProductionScheduleForNextThreeMonth(
			@RequestBody ProductionScheduleForNextThreeMonthDTO productionScheduleDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> productionScheduleMap = subContractService
					.createUpdateProductionScheduleForNextThreeMonth(productionScheduleDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, productionScheduleMap.get("message"));

			responseObjectsMap.put("productionScheduleForNextThreeMonth",
					productionScheduleMap.get("productionScheduleForNextThreeMonth"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getProductionScheduleForNextThreeMonthById")
	public ResponseEntity<ResponseDTO> getProductionScheduleForNextThreeMonthById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			ProductionScheduleForNextThreeMonthResponseDTO productionScheduleResponseDTO = subContractService
					.getProductionScheduleForNextThreeMonthById(id);

			responseObjectsMap.put("productionScheduleForNextThreeMonth", productionScheduleResponseDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Schedule For Next Three Month Fetched Successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAllProductionScheduleForNextThreeMonthByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getAllProductionScheduleForNextThreeMonthByOrgIdAndBranch(
			@RequestParam Long orgId, @RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<ProductionScheduleForNextThreeMonthResponseDTO> productionScheduleList = subContractService
					.getAllProductionScheduleForNextThreeMonthByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("productionScheduleForNextThreeMonth", productionScheduleList);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Production Schedule For Next Three Month Fetched Successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	// DeliveryChallanCumGatePass

	@PutMapping("/createUpdateDeliveryChallanCumGatePass")

	public ResponseEntity<ResponseDTO> createUpdateDeliveryChallanCumGatePass(
			@RequestBody DeliveryChallanCumGatePassDTO deliveryChallanCumGatePassDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> deliveryChallanCumGatePassMap = subContractService
					.createUpdateDeliveryChallanCumGatePass(deliveryChallanCumGatePassDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, deliveryChallanCumGatePassMap.get("message"));

			responseObjectsMap.put("deliveryChallanCumGatePassVO",
					deliveryChallanCumGatePassMap.get("deliveryChallanCumGatePassVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDeliveryChallanCumGatePassDetails")
	public ResponseEntity<ResponseDTO> getDeliveryChallanCumGatePassDetails(@RequestParam String jobOrderNo,
			@RequestParam Long branch, @RequestParam Long orgId, @RequestParam Long customer) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> deliveryChallanCumGatePassDetails = subContractService
					.getDeliveryChallanCumGatePassDetails(jobOrderNo, branch, orgId, customer);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan Cum Gate Pass Details fetched successfully");

			responseObjectsMap.put("deliveryChallanCumGatePassDetails", deliveryChallanCumGatePassDetails);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDeliveryChallanCumGatePassById")
	public ResponseEntity<ResponseDTO> getDeliveryChallanCumGatePassById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			DeliveryChallanCumGatePassResponseDTO deliveryChallanCumGatePass = subContractService
					.getDeliveryChallanCumGatePassById(id);

			responseObjectsMap.put("deliveryChallanCumGatePass", deliveryChallanCumGatePass);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan Cum Gate Pass fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDeliveryChallanCumGatePassByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getDeliveryChallanCumGatePassByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<DeliveryChallanCumGatePassResponseDTO> deliveryChallanCumGatePassList = subContractService
					.getDeliveryChallanCumGatePassByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("deliveryChallanCumGatePass", deliveryChallanCumGatePassList);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan Cum Gate Pass List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getDeliveryChallanCumGatePassDocId")
	public ResponseEntity<ResponseDTO> getDeliveryChallanCumGatePassDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getDeliveryChallanCumGatePassDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String docId = "";

		try {

			docId = subContractService.getDeliveryChallanCumGatePassDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan Cum Gate Pass DocId information retrieved successfully");

			responseObjectsMap.put("deliveryChallanCumGatePassDocId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Delivery Challan Cum Gate Pass DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateAdvForStores")
	public ResponseEntity<ResponseDTO> createUpdateAdvForStores(@RequestBody AdvForStoresDTO advForStoresDTO) {

		String methodName = "createUpdateAdvForStores()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> advForStoresMap = subContractService.createUpdateAdvForStores(advForStoresDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, advForStoresMap.get("message"));

			responseObjectsMap.put("advForStores", advForStoresMap.get("advForStores"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getLatestBomDropdown")
	public ResponseEntity<ResponseDTO> getLatestBomDropdown(@RequestParam Long itemId, @RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getLatestBomDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> bomDetails = subContractService.getLatestBomDropdown(itemId, orgId, branch);

			responseObjectsMap.put("bomDetails", bomDetails);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BOM Dropdown fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBomDetailsByDocId")
	public ResponseEntity<ResponseDTO> getBomDetailsByDocId(@RequestParam String docId, @RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getBomDetailsByDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> bomDetails = subContractService.getBomDetailsByDocId(docId, orgId, branch);

			responseObjectsMap.put("bomDetails", bomDetails);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BOM Details fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFGAndSFGItems")
	public ResponseEntity<ResponseDTO> getFGAndSFGItems(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getFGAndSFGItems()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> itemDetails = subContractService.getFGAndSFGItems(orgId, branch);

			responseObjectsMap.put("itemDetails", itemDetails);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FG / SFG Items retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FG / SFG Items", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAdvForStoresById")
	public ResponseEntity<ResponseDTO> getAdvForStoresById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			AdvForStoresResponseDTO advForStores = subContractService.getAdvForStoresById(id);

			responseObjectsMap.put("advForStores", advForStores);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ADV For Stores fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAdvForStoresByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getAdvForStoresByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<AdvForStoresResponseDTO> advForStores = subContractService.getAdvForStoresByOrgIdAndBranch(orgId,
					branch);

			responseObjectsMap.put("advForStores", advForStores);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ADV For Stores List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getAdvForStoresDocId")
	public ResponseEntity<ResponseDTO> getAdvForStoresDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getAdvForStoresDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = subContractService.getAdvForStoresDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ADV For Stores DocId information retrieved successfully");

			responseObjectsMap.put("advForStoresDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ADV For Stores DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping(value = "/createUpdateJobOrderShortClose", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateJobOrderShortClose(
			@RequestBody JobOrderShortCloseDTO jobOrderShortCloseDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> jobOrderShortCloseMap = subContractService
					.createUpdateJobOrderShortClose(jobOrderShortCloseDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, jobOrderShortCloseMap.get("message"));

			responseObjectsMap.put("jobOrderShortCloseVO", jobOrderShortCloseMap.get("jobOrderShortCloseVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getTotalSuppliedQtyforJobOrderClose")
	public ResponseEntity<ResponseDTO> getTotalSuppliedQtyforJobOrderClose(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam String jobOrderNo, @RequestParam Long item) {

		String methodName = "getTotalSuppliedQtyforJobOrderClose()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> issueQty = subContractService.getTotalSuppliedQtyforJobOrderClose(orgId, branch,
					jobOrderNo, item);

			responseObjectsMap.put("issueQty", issueQty);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Issue Qty retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Issue Qty", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobOrderShortCloseById")
	public ResponseEntity<ResponseDTO> getJobOrderShortCloseById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			JobOrderShortCloseResponseDTO jobOrderShortClose = subContractService.getJobOrderShortCloseById(id);

			responseObjectsMap.put("jobOrderShortClose", jobOrderShortClose);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order Short Close fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderShortCloseByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getJobOrderShortCloseByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<JobOrderShortCloseResponseDTO> jobOrderShortClose = subContractService
					.getJobOrderShortCloseByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("jobOrderShortClose", jobOrderShortClose);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Job Order Short Close List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobOrderShortCloseDocId")
	public ResponseEntity<ResponseDTO> getJobOrderShortCloseDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getJobOrderShortCloseDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = subContractService.getJobOrderShortCloseDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Job Order Short Close DocId information retrieved successfully");

			responseObjectsMap.put("jobOrderShortCloseDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Job Order Short Close DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeliveryChallanCapitalItemsById")
	public ResponseEntity<ResponseDTO> getDeliveryChallanCapitalItemsById(@RequestParam Long id) {

		String methodName = "getDeliveryChallanCapitalItemsById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			DeliveryChallanCapitalItemsResponseDTO deliveryChallanCapitalItems = subContractService
					.getDeliveryChallanCapitalItemsById(id);

			responseObjectsMap.put("deliveryChallanCapitalItems", deliveryChallanCapitalItems);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan Capital Items fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to fetch Delivery Challan Capital Items", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeliveryChallanCapitalItemsByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getDeliveryChallanCapitalItemsByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getDeliveryChallanCapitalItemsByOrgIdAndBranch()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			List<DeliveryChallanCapitalItemsResponseDTO> deliveryChallanCapitalItems = subContractService
					.getDeliveryChallanCapitalItemsByOrgIdAndBranch(orgId, branch);

			responseObjectsMap.put("deliveryChallanCapitalItems", deliveryChallanCapitalItems);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan Capital Items List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to fetch Delivery Challan Capital Items List", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeliveryChallanCapitalItemsDocId")
	public ResponseEntity<ResponseDTO> getDeliveryChallanCapitalItemsDocId(@RequestParam Long orgId,
			@RequestParam String financialYear) {

		String methodName = "getDeliveryChallanCapitalItemsDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		String docId = "";

		try {

			docId = subContractService.getDeliveryChallanCapitalItemsDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan Capital Items DocId information retrieved successfully");

			responseObjectsMap.put("deliveryChallanCapitalItemsDocId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Delivery Challan Capital Items DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping(value = "/createUpdateDeliveryChallanCapitalItems")
	public ResponseEntity<ResponseDTO> createUpdateDeliveryChallanCapitalItems(
			@RequestBody DeliveryChallanCapitalItemsDTO dto) {

		String methodName = "createUpdateDeliveryChallanCapitalItems()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = subContractService.createUpdateDeliveryChallanCapitalItems(dto);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("deliveryChallanCapitalItemsVO", responseMap.get("deliveryChallanCapitalItemsVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to create/update Delivery Challan Capital Items", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping(value = "/createUpdateSubContractingGRN")
	public ResponseEntity<ResponseDTO> createUpdateSubContractingGRN(@RequestBody SubContractingGRNDTO dto) {

		String methodName = "createUpdateSubContractingGRN()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = subContractService.createUpdateSubContractingGRN(dto);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("subContractingGRNVO", responseMap.get("subContractingGRNVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to create/update Sub Contracting GRN",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGateInwardEntryforSubContractingGRN")
	public ResponseEntity<ResponseDTO> getGateInwardEntryDropdown(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam Long customer) {

		String methodName = "getGateInwardEntryforSubContractingGRN()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = subContractService.getGateInwardEntryDropdown(orgId, branch, customer);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Gate Inward Entry information retrieved successfully");

			responseObjectsMap.put("GateInwardEntryVO", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Gate Inward Entry information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubcontractSupplyScheduleforSubContractingGRN")
	public ResponseEntity<ResponseDTO> getSubcontractSupplyScheduleforSubContractingGRN(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long customer) {

		String methodName = "getSubcontractSupplyScheduleforSubContractingGRN()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = subContractService.getSubcontractSupplyScheduleforSubContractingGRN(orgId, branch, customer);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Subcontract Supply Schedule information retrieved successfully");

			responseObjectsMap.put("SubcontractSupplyScheduleVO", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Subcontract Supply Schedule information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsForSubContractingGRN")
	public ResponseEntity<ResponseDTO> getItemDetailsForSubContractingGRN(@RequestParam String scheduleNo,
			@RequestParam Long orgId, @RequestParam Long branch, @RequestParam Long customer) {

		String methodName = "getItemDetailsForSubContractingGRN()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = subContractService.getItemDetailsForSubContractingGRN(scheduleNo, orgId, branch, customer);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Subcontract Supply Schedule Item information retrieved successfully");

			responseObjectsMap.put("SubcontractSupplyScheduleItemVO", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Subcontract Supply Schedule Item information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBomItemDetailsforSubContractingGRN")
	public ResponseEntity<ResponseDTO> getBomItemDetails(
	        @RequestParam Long orgId,
	        @RequestParam Long branch,
	        @RequestParam Long itemId) {

	    String methodName = "getBomItemDetailsforSubContractingGRN()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO = null;

	    List<Map<String, Object>> mov =
	            new ArrayList<>();

	    try {

	        mov = subContractService.getBomItemDetailsforSubContractingGRN(
	                orgId,
	                branch,
	                itemId);

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
	                "BOM Item Details information retrieved successfully");

	        responseObjectsMap.put(
	                "BomItemDetailsVO",
	                mov);

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } else {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed to retrieve BOM Item Details information",
	                        errorMsg);
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSubContractingGRNById")
	public ResponseEntity<ResponseDTO> getSubContractingGRNById(
	        @RequestParam Long id) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        SubContractingGRNResponseDTO subContractingGRN =
	                subContractService.getSubContractingGRNById(id);

	        responseObjectsMap.put(
	                "subContractingGRN",
	                subContractingGRN);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Sub Contracting GRN fetched successfully");

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        e.getMessage(),
	                        e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getSubContractingGRNByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getSubContractingGRNByOrgIdAndBranch(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<SubContractingGRNResponseDTO> subContractingGRNList =
	                subContractService.getSubContractingGRNByOrgIdAndBranch(
	                        orgId,
	                        branch);

	        responseObjectsMap.put(
	                "subContractingGRN",
	                subContractingGRNList);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Sub Contracting GRN List fetched successfully");

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        e.getMessage(),
	                        e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getSubContractingGRNDocId")
	public ResponseEntity<ResponseDTO> getSubContractingGRNDocId(
	        @RequestParam Long orgId,
	        @RequestParam String financialYear) {

	    String methodName = "getSubContractingGRNDocId()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO = null;

	    String docId = "";

	    try {

	        docId =
	                subContractService.getSubContractingGRNDocId(
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
	                "Sub Contracting GRN DocId information retrieved successfully");

	        responseObjectsMap.put(
	                "subContractingGRNDocId",
	                docId);

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } else {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed to retrieve Sub Contracting GRN DocId",
	                        errorMsg);
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}

	
	@PutMapping(value = "/createUpdateMaterialPlanning")
	public ResponseEntity<ResponseDTO> createUpdateMaterialPlanning(
	        @RequestBody MaterialPlanningDTO materialPlanningDTO) {

	    String methodName = "createUpdateMaterialPlanning()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    try {

	        Map<String, Object> responseMap =
	        		subContractService.createUpdateMaterialPlanning(
	                        materialPlanningDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                responseMap.get("message"));

	        responseObjectsMap.put(
	                "materialPlanningVO",
	                responseMap.get("materialPlanningVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to create/update Material Planning",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	

	
	@GetMapping("/getMaterialPlanningById")
	public ResponseEntity<ResponseDTO> getMaterialPlanningById(
	        @RequestParam Long id) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        MaterialPlanningResponseDTO materialPlanning =
	        		subContractService.getMaterialPlanningById(id);

	        responseObjectsMap.put(
	                "materialPlanning",
	                materialPlanning);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Material Planning fetched successfully");

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getMaterialPlanningByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getMaterialPlanningByOrgIdAndBranch(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<MaterialPlanningResponseDTO> materialPlanningList =
	        		subContractService
	                        .getMaterialPlanningByOrgIdAndBranch(
	                                orgId,
	                                branch);

	        responseObjectsMap.put(
	                "materialPlanning",
	                materialPlanningList);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Material Planning List fetched successfully");

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getMaterialPlanningDocId")
	public ResponseEntity<ResponseDTO> getMaterialPlanningDocId(
	        @RequestParam Long orgId,
	        @RequestParam String financialYear) {

	    String methodName = "getMaterialPlanningDocId()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    String docId = "";

	    try {

	        docId = subContractService
	                .getMaterialPlanningDocId(
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
	                "Material Planning DocId information retrieved successfully");

	        responseObjectsMap.put(
	                "materialPlanningDocId",
	                docId);

	        responseDTO = createServiceResponse(
	                responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to retrieve Material Planning DocId",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PutMapping(value = "/createUpdateBomCorrectionRequestNote")
	public ResponseEntity<ResponseDTO> createUpdateBomCorrectionRequestNote(
	        @RequestBody BomCorrectionRequestNoteDTO dto) {

	    String methodName = "createUpdateBomCorrectionRequestNote()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    try {

	        Map<String, Object> responseMap =
	        		subContractService
	                        .createUpdateBomCorrectionRequestNote(dto);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                responseMap.get("message"));

	        responseObjectsMap.put(
	                "bomCorrectionRequestNoteVO",
	                responseMap.get("bomCorrectionRequestNoteVO"));

	        responseDTO = createServiceResponse(
	                responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to create/update BOM Correction Request Note",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBomCorrectionRequestNoteById")
	public ResponseEntity<ResponseDTO> getBomCorrectionRequestNoteById(
	        @RequestParam Long id) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        BomCorrectionRequestNoteResponseDTO bomCorrectionRequestNote =
	        		subContractService
	                        .getBomCorrectionRequestNoteById(id);

	        responseObjectsMap.put(
	                "bomCorrectionRequestNote",
	                bomCorrectionRequestNote);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "BOM Correction Request Note fetched successfully");

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getBomCorrectionRequestNoteByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getBomCorrectionRequestNoteByOrgIdAndBranch(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<BomCorrectionRequestNoteResponseDTO>
	                bomCorrectionRequestNoteList =
	                		subContractService
	                        .getBomCorrectionRequestNoteByOrgIdAndBranch(
	                                orgId,
	                                branch);

	        responseObjectsMap.put(
	                "bomCorrectionRequestNote",
	                bomCorrectionRequestNoteList);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "BOM Correction Request Note List fetched successfully");

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getBomCorrectionRequestNoteDocId")
	public ResponseEntity<ResponseDTO> getBomCorrectionRequestNoteDocId(
	        @RequestParam Long orgId,
	        @RequestParam String financialYear) {

	    String methodName = "getBomCorrectionRequestNoteDocId()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    String docId = "";

	    try {

	        docId = subContractService
	                .getBomCorrectionRequestNoteDocId(
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
	                "BOM Correction Request Note DocId information retrieved successfully");

	        responseObjectsMap.put(
	                "bomCorrectionRequestNoteDocId",
	                docId);

	        responseDTO = createServiceResponse(
	                responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to retrieve BOM Correction Request Note DocId",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getFGItemsforBOMCorrectionRequestNote")
	public ResponseEntity<ResponseDTO> getFGItemsforBOMCorrectionRequestNote(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getFGItemsforBOMCorrectionRequestNote()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> itemDetails = subContractService.getFGItemsforBOMCorrectionRequestNote(orgId, branch);

			responseObjectsMap.put("itemDetails", itemDetails);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "FG / SFG Items retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FG / SFG Items", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAllItemsNotFGforBOMCorrectionRequestNote")
	public ResponseEntity<ResponseDTO> getAllItemsNotFGforBOMCorrectionRequestNote(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getAllItemsNotFGforBOMCorrectionRequestNote()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> itemDetails = subContractService.getAllItemsNotFGforBOMCorrectionRequestNote(orgId, branch);

			responseObjectsMap.put("itemDetails", itemDetails);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " Items retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve FG / SFG Items", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getEmployeesByDepartmentforBOMCorrectionRequestNote")
	public ResponseEntity<ResponseDTO> getEmployeesByDepartment(
	        @RequestParam Long orgId,
	        @RequestParam Long branch,
	        @RequestParam String department) {

	    String methodName = "getEmployeesByDepartmentforBOMCorrectionRequestNote()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    List<Map<String, Object>> mov = new ArrayList<>();

	    try {

	        mov = subContractService.getEmployeesByDepartmentforBOMCorrectionRequestNote(
	                orgId,
	                branch,
	                department);

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
	                "Employee information retrieved successfully");

	        responseObjectsMap.put(
	                "employeeList",
	                mov);

	        responseDTO = createServiceResponse(
	                responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to retrieve Employee information",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PutMapping("/createUpdateInspectionRequisitionNote")
	public ResponseEntity<ResponseDTO> createUpdateInspectionRequisitionNote(
	        @RequestBody InspectionRequisitionNoteDTO inspectionRequisitionNoteDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> response =
	        		subContractService
	                        .createUpdateInspectionRequisitionNote(
	                                inspectionRequisitionNoteDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                response.get("message"));

	        responseObjectsMap.put(
	                "inspectionRequisitionNoteVO",
	                response.get("inspectionRequisitionNoteVO"));

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
	
	@GetMapping("/getInspectionRequisitionNoteById")
	public ResponseEntity<ResponseDTO> getInspectionRequisitionNoteById(
	        @RequestParam Long id) {

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        InspectionRequisitionNoteResponseDTO response =
	        		subContractService
	                        .getInspectionRequisitionNoteById(id);

	        responseObjectsMap.put(
	                "inspectionRequisitionNote",
	                response);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Inspection Requisition Note fetched successfully");

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        e.getMessage(),
	                        e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getInspectionRequisitionNoteByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO>
	getInspectionRequisitionNoteByOrgIdAndBranch(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        List<InspectionRequisitionNoteResponseDTO> response =
	        		subContractService
	                        .getInspectionRequisitionNoteByOrgIdAndBranch(
	                                orgId, branch);

	        responseObjectsMap.put(
	                "inspectionRequisitionNoteList",
	                response);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Inspection Requisition Note information retrieved successfully");

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        e.getMessage(),
	                        e.getMessage());
	    }

	    return ResponseEntity.ok(responseDTO);
	}
}
