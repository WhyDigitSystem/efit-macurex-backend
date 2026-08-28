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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.ResponseDTO.DocketInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesRejectionInvoiceResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.DocketInvoiceDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
import com.efitops.basesetup.dto.SalesRejectionInvoiceDTO;
import com.efitops.basesetup.dto.SalesReturnDTO;
import com.efitops.basesetup.service.TransactionService;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
public class TransactionController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterController.class);

	@Autowired
	TransactionService transactionService;

	@PutMapping("/createUpdateSalesDeliverySchedule")
	public ResponseEntity<ResponseDTO> createUpdateSalesDeliverySchedule(
			@RequestBody SalesDeliveryScheduleDTO salesDeliveryScheduleDTO) {

		String methodName = "createUpdateSalesDeliverySchedule";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = transactionService
					.createUpdateSalesDeliverySchedule(salesDeliveryScheduleDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("salesDeliverySchedule", responseMap.get("salesDeliverySchedule"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSalesDeliveryScheduleById")
	public ResponseEntity<ResponseDTO> getSalesDeliveryScheduleById(@RequestParam Long id) {

		String methodName = "getSalesDeliveryScheduleById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		SalesDeliveryScheduleResponseDTO salesDeliverySchedule = null;

		try {

			salesDeliverySchedule = transactionService.getSalesDeliveryScheduleById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Delivery Schedule information retrieved successfully");

			responseObjectsMap.put("salesDeliverySchedule", salesDeliverySchedule);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Delivery Schedule information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesDeliveryScheduleByOrgId")
	public ResponseEntity<ResponseDTO> getSalesDeliveryScheduleByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getSalesDeliveryScheduleByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<SalesDeliveryScheduleResponseDTO> salesDeliveryScheduleList = transactionService
					.getAllSalesDeliverySchedule(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Delivery Schedule information retrieved successfully");

			responseObjectsMap.put("salesDeliveryScheduleList", salesDeliveryScheduleList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Delivery Schedule information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	// contract dropdown

	@GetMapping("/getContractNoDropdown")
	public ResponseEntity<ResponseDTO> getContractNoDropdown(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getContractNoDropdown";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = transactionService.getContractNo(orgId, branch);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("contractList", responseMap.get("contractList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// item dropdown

	@GetMapping("/getItemDropdownBySalesDeliverySchedule")
	public ResponseEntity<ResponseDTO> getItemDropdown(@RequestParam String docId, @RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getSalesDeliveryScheduleByItemDropdown";

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = transactionService.getSalesDeliveryScheduleByItemDropdown(docId, orgId,
					branch);

			responseObjectsMap.put("message", responseMap.get("message"));
			responseObjectsMap.put("itemList", responseMap.get("itemList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getCustomerDetails")
	public ResponseEntity<ResponseDTO> getCustomerDetails(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getItemDropdown";

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = transactionService.getAllCustomerDetails(orgId, branch);

			responseObjectsMap.put("message", responseMap.get("message"));
			responseObjectsMap.put("customerDetails", responseMap.get("customerDetails"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	// sales contract amendment

	@PostMapping("/updateCreateSalesContractAmendment")
	public ResponseEntity<ResponseDTO> updateCreateSalesContractAmendment(
			@RequestBody SalesContractAmendmentDTO salesContractAmendmentDTO) {

		String methodName = "updateCreateSalesContractAmendment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = transactionService
					.updateCreateSalesContractAmendment(salesContractAmendmentDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));
			responseObjectsMap.put("salesContractAmendmentVO", responseMap.get("salesContractAmendmentVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesContractAmendmentById")
	public ResponseEntity<ResponseDTO> getSalesContractAmendmentById(@RequestParam Long id) {

		String methodName = "getSalesContractAmendmentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		SalesContractAmdResponseDTO salesContractAmendment = null;

		try {

			salesContractAmendment = transactionService.getSalesContractAmendmentById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Contract Amendment information retrieved successfully");

			responseObjectsMap.put("salesContractAmendment", salesContractAmendment);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Contract Amendment information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesContractAmendmentByOrgId")
	public ResponseEntity<ResponseDTO> getSalesContractAmendmentByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getSalesContractAmendmentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<SalesContractAmdResponseDTO> salesContractList = new ArrayList<>();

		try {

			salesContractList = transactionService.getSalesContractAmendmentByOrgId(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Contract Amendment information retrieved successfully");

			responseObjectsMap.put("salesContractList", salesContractList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Contract Amendment information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	// dropdown for contractno
	@GetMapping("/getSalesContractAmdContractNoDropdown")
	public ResponseEntity<ResponseDTO> getSalesContractAmdContractNoDropdown(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getSalesContractAmdContractNoDropdown";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = transactionService.getSalesContractAmdContractNoDropdown(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("contractList", responseMap.get("contractList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSalesContractAmdItemDropdown")
	public ResponseEntity<ResponseDTO> getSalesContractAmdItemDropdown(@RequestParam String salesContractNo,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getSalesContractAmdItemDropdown";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = transactionService.getSalesContractAmdItemDropdown(salesContractNo, orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("itemList", responseMap.get("itemList"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSalesContractAmdRevisionNo")
	public ResponseEntity<ResponseDTO> getRevisionNo(@RequestParam String salesContractNo, @RequestParam Long item,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getSalesContractAmdRevisionNo";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = transactionService.getSalesContractAmdRevisionNo(salesContractNo, item,
					orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("revisionNo", responseMap.get("revisionNo"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// Docket Invoice
	@PutMapping("/updateCreateDocketInvoice")
	public ResponseEntity<ResponseDTO> updateCreateDocketInvoice(@RequestBody DocketInvoiceDTO docketInvoiceDTO) {

		String methodName = "updateCreateDocketInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			Map<String, Object> responseMap = transactionService.updateCreateDocketInvoice(docketInvoiceDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("docketInvoiceVO", responseMap.get("docketInvoiceVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocketInvoiceById")
	public ResponseEntity<ResponseDTO> getDocketInvoiceById(@RequestParam Long id) {

		String methodName = "getDocketInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		DocketInvoiceResponseDTO docketInvoiceResponseDTO = null;

		try {

			docketInvoiceResponseDTO = transactionService.getDocketInvoiceById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Docket Invoice retrieved successfully");

			responseObjectsMap.put("docketInvoiceResponseDTO", docketInvoiceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Docket Invoice retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDocketInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getDocketInvoiceByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getDocketInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<DocketInvoiceResponseDTO> docketInvoiceResponseDTO = new ArrayList<>();

		try {

			docketInvoiceResponseDTO = transactionService.getDocketInvoiceByOrgId(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Docket Invoice retrieved successfully");

			responseObjectsMap.put("docketInvoiceResponseDTO", docketInvoiceResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Docket Invoice retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSalesDeliveryScheduleDocId")
	public ResponseEntity<ResponseDTO> getSalesDeliveryScheduleDocId(@RequestParam Long orgId,
			@RequestParam String financialYear, @RequestParam String screenCode) {

		String methodName = "getSalesDeliveryScheduleDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = transactionService.getSalesDeliveryScheduleDocId(orgId, financialYear, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesDeliveryScheduleDocId DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Proforma SalesDeliveryScheduleDocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocketInvoiceDocId")
	public ResponseEntity<ResponseDTO> getDocketInvoiceDocId(@RequestParam Long orgId,
			@RequestParam String financialYear, @RequestParam String screenCode) {

		String methodName = "getDocketInvoiceDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = transactionService.getDocketInvoiceDocId(orgId, financialYear, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Proforma docId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SalesRejectionInvoice api

	@PutMapping(value = "/createUpdateSalesRejectionInvoice")
	public ResponseEntity<ResponseDTO> createUpdateSalesRejectionInvoice(
			@RequestBody SalesRejectionInvoiceDTO salesRejectionInvoiceDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> salesRejectionInvoiceMap = transactionService
					.createUpdateSalesRejectionInvoice(salesRejectionInvoiceDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, salesRejectionInvoiceMap.get("message"));

			responseObjectsMap.put("salesRejectionInvoiceVO", salesRejectionInvoiceMap.get("salesRejectionInvoiceVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	// DespatchInstructionNoforSalesRejectionInv

	@GetMapping("/getDespatchInstructionNoforSalesRejectionInv")
	public ResponseEntity<ResponseDTO> getDespatchInstructionNoforSalesRejectionInv(@RequestParam Long customer,
			@RequestParam Long orgId, @RequestParam Long branch, @RequestParam String docType) {

		String methodName = "getDespatchInstructionNoforSalesRejectionInv()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = transactionService.getDespatchInstructionNoforSalesRejectionInv(customer, orgId, branch, docType);

			responseObjectsMap.put("despatchInstructions", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getCurrencyforSalesRejectionInv")
	public ResponseEntity<ResponseDTO> getCurrencyforSalesRejectionInv(@RequestParam Long customer,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getCurrencyforSalesRejectionInv()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = transactionService.getCurrencyforSalesRejectionInv(customer, orgId, branch);

			responseObjectsMap.put("currencyDetails", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getMonthYearForSalesRejectionInv")
	public ResponseEntity<ResponseDTO> getMonthYearForDropdown(@RequestParam String docId, @RequestParam Long branch,
			@RequestParam Long orgId) {

		String methodName = "getMonthYearForSalesRejectionInv()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = transactionService.getMonthYearForSalesRejectionInv(docId, branch, orgId);

			responseObjectsMap.put("monthYearDetails", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSalesRejectionInvoiceById")
	public ResponseEntity<ResponseDTO> getSalesRejectionInvoiceById(@RequestParam Long id) {

		String methodName = "getSalesRejectionInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		SalesRejectionInvoiceResponseDTO salesRejectionInvoice = null;

		try {

			salesRejectionInvoice = transactionService.getSalesRejectionInvoiceById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Rejection Invoice information retrieved successfully");

			responseObjectsMap.put("salesRejectionInvoice", salesRejectionInvoice);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Rejection Invoice information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesRejectionInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getSalesRejectionInvoiceByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getSalesRejectionInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<SalesRejectionInvoiceResponseDTO> salesRejectionInvoiceList = transactionService
					.getSalesRejectionInvoiceByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Rejection Invoice information retrieved successfully");

			responseObjectsMap.put("salesRejectionInvoiceList", salesRejectionInvoiceList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Rejection Invoice information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsforSalesRejectionInvoice")
	public ResponseEntity<ResponseDTO> getItemDetailsforSalesRejectionInvoice(
			@RequestParam String dispatchInstructiondocId, @RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getItemDetailsforSalesRejectionInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = transactionService.getItemDetailsforSalesRejectionInvoice(dispatchInstructiondocId, orgId, branch);

			responseObjectsMap.put("itemDetails", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getCustomerDetailsforSalesRejectionInvoice")
	public ResponseEntity<ResponseDTO> getCustomerDetailsforSalesRejectionInvoice(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getCustomerDetailsforSalesRejectionInvoice()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> customerDetails = new ArrayList<>();

		try {

			customerDetails = transactionService.getCustomerDetailsforSalesRejectionInvoice(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer details retrieved successfully");

			responseObjectsMap.put("customerDetails", customerDetails);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Customer details retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//	@GetMapping("/getSalesRejectionInvoiceDocId")
//	public ResponseEntity<ResponseDTO> getSalesRejectionInvoiceDocId(@RequestParam Long orgId,
//			@RequestParam String financialYear, @RequestParam String docType) {
//
//		String methodName = "getSalesRejectionInvoiceDocId()";
//
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		String docId = "";
//
//		try {
//
//			docId = transactionService.getSalesRejectionInvoiceDocId(orgId, financialYear, docType);
//
//		} catch (Exception e) {
//
//			errorMsg = e.getMessage();
//
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//
//		if (StringUtils.isBlank(errorMsg)) {
//
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DocId information retrieved successfully");
//
//			responseObjectsMap.put("invoiceDocId", docId);
//
//			responseDTO = createServiceResponse(responseObjectsMap);
//
//		} else {
//
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Failed to retrieve Sales Rejection Invoice docId", errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//		return ResponseEntity.ok().body(responseDTO);
//	}

	@PutMapping(value = "/createUpdateSalesReturn")
	public ResponseEntity<ResponseDTO> createUpdateSalesReturn(
	        @RequestBody SalesReturnDTO salesReturnDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> salesReturnMap =
	                transactionService.createUpdateSalesReturn(
	                        salesReturnDTO
	                );

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                salesReturnMap.get("message")
	        );

	        responseObjectsMap.put(
	                "salesReturnVO",
	                salesReturnMap.get("salesReturnVO")
	        );

	        responseDTO = createServiceResponse(
	                responseObjectsMap
	        );

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage()
	        );
	    }

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getSalesRejectionInvoiceDocId")
	public ResponseEntity<ResponseDTO> getSalesRejectionInvoiceDocId(
	        @RequestParam Long orgId,
	        @RequestParam String financialYear,
	        @RequestParam String docType) {

	    String methodName = "getSalesRejectionInvoiceDocId()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    String docId = "";

	    try {

	        docId = transactionService.getSalesRejectionInvoiceDocId(
	                orgId,
	                financialYear,
	                docType);

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
	                "DocId information retrieved successfully");

	        responseObjectsMap.put("invoiceDocId", docId);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } else {

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Failed to retrieve Sales Rejection Invoice docId",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok().body(responseDTO);
	}
}
