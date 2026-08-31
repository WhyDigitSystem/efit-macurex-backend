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

import com.efitops.basesetup.ResponseDTO.IssuesResponseDTO;
import com.efitops.basesetup.ResponseDTO.OpenStockEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ParameterMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentContractDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentItemDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderAmendmentResponceDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.IssuesDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.OpenStockEntryDto;
import com.efitops.basesetup.dto.ParameterMasterDTO;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDto;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseOrderAmendmentDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentResponseDTO;
import com.efitops.basesetup.service.DevelopService;

@CrossOrigin
@RestController

@RequestMapping("/api/develop")
public class DevelopController extends BaseController {

	@Autowired
	private DevelopService developService;

	@PostMapping(value = "/updateCreateEnquiry", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> updateCreateEnquiry(@RequestPart("enquiryDTO") EnquiryDTO enquiryDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		String methodName = "updateCreateEnquiry";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = developService.updateCreateEnquiry(enquiryDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("enquiryVO", responseMap.get("enquiryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap,

					e.getMessage(), e.getMessage());
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

			Enquiry = developService.getEnquiryById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Enquiry information retrieved successfully");

			responseObjectsMap.put("enquiry", Enquiry);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, " Enquiry information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getEnquiryByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<EnquiryResponseDTO> enquiryList = new ArrayList<>();

		try {

			enquiryList = developService.getEnquiryByOrgId(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Enquiry information retrieved successfully");

			responseObjectsMap.put("enquiryList", enquiryList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, " Enquiry information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

////SALES RETURN
//
//	@PostMapping("/createUpdateSalesReturn")
//	public ResponseEntity updateCreateSalesReturn(@RequestBody SalesReturnDTO salesReturnDTO) {
//
//		String methodName = "createUpdateSalesReturn()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//
//		String errorMsg = null;
//
//		ResponseDTO responseDTO = null;
//
//		try {
//
//			Map<String, Object> responseMap = developService.createUpdateSalesReturn(salesReturnDTO);
//
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));
//
//			responseObjectsMap.put("salesReturnVO", responseMap.get("salesReturnVO"));
//
//			responseDTO = createServiceResponse(responseObjectsMap);
//
//		} catch (Exception e) {
//
//			errorMsg = e.getMessage();
//
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//
//			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	@GetMapping("/getSalesReturnById")
//	public ResponseEntity<ResponseDTO> getSalesReturnById(@RequestParam Long id) {
//
//		String methodName = "getSalesReturnById()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		String errorMsg = null;
//
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//
//		ResponseDTO responseDTO = null;
//
//		SalesReturnResponseDTO salesReturnResponseDTO = null;
//
//		try {
//
//			salesReturnResponseDTO = developService.getSalesReturnById(id);
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
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales Return retrieved successfully");
//
//			responseObjectsMap.put("salesReturnResponseDTO", salesReturnResponseDTO);
//
//			responseDTO = createServiceResponse(responseObjectsMap);
//
//		} else {
//
//			responseDTO = createServiceResponseError(responseObjectsMap, "Sales Return retrieval failed", errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	@GetMapping("/getSalesReturnByOrgId")
//	public ResponseEntity<ResponseDTO> getSalesReturnByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {
//
//		String methodName = "getSalesReturnByOrgId()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		String errorMsg = null;
//
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//
//		ResponseDTO responseDTO = null;
//
//		List<SalesReturnResponseDTO> salesReturnResponseDTO = new ArrayList<>();
//
//		try {
//
//			salesReturnResponseDTO = developService.getAllSalesReturn(orgId, branch);
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
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales Return retrieved successfully");
//
//			responseObjectsMap.put("salesReturnResponseDTO", salesReturnResponseDTO);
//
//			responseDTO = createServiceResponse(responseObjectsMap);
//
//		} else {
//
//			responseDTO = createServiceResponseError(responseObjectsMap, "Sales Return retrieval failed", errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//		return ResponseEntity.ok().body(responseDTO);
//	}

//salesorderamendment

	@PutMapping("/createUpdateSalesOrderAmendment")
	public ResponseEntity<ResponseDTO> createUpdateSalesOrderAmendment(
			@RequestBody SalesOrderAmendmentDTO salesOrderAmendmentDTO) {

		String methodName = "createUpdateSalesOrderAmendment";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> responseMap = developService.createUpdateSalesOrderAmendment(salesOrderAmendmentDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));

			responseObjectsMap.put("salesOrderAmendment", responseMap.get("salesOrderAmendmentVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage(), e);

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSalesOrderAmendmentById")
	public ResponseEntity<ResponseDTO> getSalesOrderAmendmentById(@RequestParam Long id) {

		String methodName = "getSalesOrderAmendmentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		SalesOrderAmendmentResponseDTO salesOrderAmendment = null;

		try {

			salesOrderAmendment = developService.getSalesOrderAmendmentById(id);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Order Amendment information retrieved successfully");

			responseObjectsMap.put("salesOrderAmendment", salesOrderAmendment);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Order Amendment information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesOrderAmendmentByOrgId")
	public ResponseEntity<ResponseDTO> getSalesOrderAmendmentByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getSalesOrderAmendmentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<SalesOrderAmendmentResponseDTO> salesOrderAmendmentList = new ArrayList<>();

		try {

			salesOrderAmendmentList = developService.getSalesOrderAmendmentByOrgId(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Order Amendment information retrieved successfully");

			responseObjectsMap.put("salesOrderAmendmentList", salesOrderAmendmentList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Order Amendment information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getOrderAcceptanceBySalesOrderAmendment")
	public ResponseEntity<ResponseDTO> getOrderAcceptanceBySalesOrderAmendment(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getOrderAcceptanceBySalesOrderAmendment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<Map<String, Object>> responseList = developService.getOrderAcceptanceBySalesOrderAmendment(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Order Acceptance List Fetched Successfully");

			responseObjectsMap.put("orderAcceptanceList", responseList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Order Acceptance List Fetch Failed",
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
	public ResponseEntity<ResponseDTO> getItemsDetailsbySalesOrderAmendment(@RequestParam String docId,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getOrderAcceptanceItemsWithAmendment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			List<Map<String, Object>> itemDetails = developService.getOrderAcceptanceItemsWithAmendment(docId, orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Order Acceptance Item Details Fetched Successfully");

			responseObjectsMap.put("itemDetails", itemDetails);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Order Acceptance Item Details Fetch Failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesOrderAmdRevisionNo")
	public ResponseEntity<ResponseDTO> getSalesOrderAmdRevisionNo(@RequestParam String salesOrderNo,
			@RequestParam Long item, @RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getSalesOrderAmdRevisionNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			Integer revisionNo = developService.getSalesOrderAmdRevisionNo(salesOrderNo, item, orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Revision No Loaded Successfully");

			responseObjectsMap.put("revisionNo", revisionNo);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Revision No Loading Failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

//Purchase contract amendment

	@PutMapping(value = "/createUpdatePurchaseContractAmendment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdatePurchaseContractAmendment(
			@RequestPart("purchaseContractAmendment") PurchaseContractAmendmentDto purchaseContractAmendmentDto,

			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> purchaseContractAmendmentMap = developService
					.createUpdatePurchaseContractAmendment(purchaseContractAmendmentDto, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseContractAmendmentMap.get("message"));

			responseObjectsMap.put("purchaseContractAmendmentVO",
					purchaseContractAmendmentMap.get("purchaseContractAmendmentVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}
// =========================
// Get By Id
// =========================

	@GetMapping("/getPurchaseContractAmendmentById")
	public ResponseEntity<ResponseDTO> getPurchaseContractAmendmentById(@RequestParam Long id) {

		String methodName = "getPurchaseContractAmendmentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			PurchaseContractAmendmentResponseDto response = developService.getPurchaseContractAmendmentById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Contract Amendment information retrieved successfully");

			responseObjectsMap.put("purchaseContractAmendmentResponseVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Purchase Contract Amendment information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

// =========================
// Get By Org Id
// =========================

	@GetMapping("/getPurchaseContractAmendmentByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseContractAmendmentByOrgId(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getPurchaseContractAmendmentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<PurchaseContractAmendmentResponseDto> response = developService
					.getPurchaseContractAmendmentByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Contract Amendment information retrieved successfully");

			responseObjectsMap.put("purchaseContractAmendmentResponseVO", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Purchase Contract Amendment information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

// =========================
// Contract No Dropdown
// =========================

	@GetMapping("/getContractNoDropdownforPurchaseContractAmendment")
	public ResponseEntity<ResponseDTO> getContractNoDropdownforPurchaseContractAmendment(@RequestParam Long orgId,
			@RequestParam Long branch, @RequestParam Long customerId) {

		String methodName = "getContractNoDropdownforPurchaseContractAmendment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			responseObjectsMap = developService.getContractNoDropdownforPurchaseContractAmendment(orgId, branch,
					customerId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Contract dropdown retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Contract dropdown retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}
//=========================
//Item Dropdown
//=========================

	@GetMapping("/getPurchaseContractAmendmentItemCodeDropdown")
	public ResponseEntity<ResponseDTO> getPurchaseContractAmendmentItemCodeDropdown(@RequestParam String docId,
			@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getPurchaseContractAmendmentItemCodeDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getPurchaseContractAmendmentItemCodeDropdown(docId, branch, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item Code retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Item Code retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPurchaseContractAmdRevisionNo")
	public ResponseEntity<ResponseDTO> getPurchaseContractAmdRevisionNo(@RequestParam String contractNo,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getPurchaseContractAmdRevisionNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Integer revisionNo = developService.getPurchaseContractAmdRevisionNo(contractNo, orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Revision No retrieved successfully");

			responseObjectsMap.put("revisionNo", revisionNo);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Revision No retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getEnquiryDocId")
	public ResponseEntity<ResponseDTO> getEnquiryDocId(@RequestParam Long orgId, @RequestParam String financialYear,
			@RequestParam String screenCode) {

		String methodName = "getEnquiryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = developService.getEnquiryDocId(orgId, financialYear, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Enquiry DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Proforma Invoice Enquiry",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//purchaseorderamendment

	@PostMapping(value = "/updateCreatePurchaseOrderAmendment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> updateCreatePurchaseOrderAmendment(

			@RequestPart("purchaseOrderAmendment") PurchaseOrderAmendmentDTO purchaseOrderAmendmentDTO,

			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> purchaseOrderAmendmentMap = developService
					.updateCreatePurchaseOrderAmendment(purchaseOrderAmendmentDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseOrderAmendmentMap.get("message"));

			responseObjectsMap.put("purchaseOrderAmendmentVO",
					purchaseOrderAmendmentMap.get("purchaseOrderAmendmentVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPurchaseOrderAmendmentById")
	public ResponseEntity<ResponseDTO> getPurchaseOrderAmendmentById(@RequestParam Long id) {

		String methodName = "getPurchaseOrderAmendmentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			PurchaseOrderAmendmentResponceDTO purchaseOrderAmendmentResponseDTO = developService
					.getPurchaseOrderAmendmentById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Order Amendment information retrieved successfully");

			responseObjectsMap.put("purchaseOrderAmendmentResponseVO", purchaseOrderAmendmentResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Purchase Order Amendment information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPurchaseOrderAmendmentByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderAmendmentByOrgId(@RequestParam Long orgId) {

		String methodName = "getPurchaseOrderAmendmentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			List<PurchaseOrderAmendmentResponceDTO> purchaseOrderAmendmentList = developService
					.getPurchaseOrderAmendmentByOrgId(orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Order Amendment information retrieved successfully");

			responseObjectsMap.put("purchaseOrderAmendmentResponseVO", purchaseOrderAmendmentList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Purchase Order Amendment information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPurchaseOrderAmdRevisionNo")

	public ResponseEntity<ResponseDTO> getPurchaseOrderAmdRevisionNo(@RequestParam String purchaseOrderNumber,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getPurchaseOrderAmdRevisionNo()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Integer revisionNo = developService.getPurchaseOrderAmendmentRevisionNo(purchaseOrderNumber, orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Revision No retrieved successfully");

			responseObjectsMap.put("revisionNo", revisionNo);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Revision No retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

//purchaseorderamendmentponumberdropdown

	@GetMapping("/getPurchaseOrderAmendmentforCustomer")
	public ResponseEntity<ResponseDTO> getPurchaseOrderAmendmentforCustomer(@RequestParam Long customer,
			@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getPurchaseOrderAmendmentforCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getPurchaseOrderAmendmentforCustomer(customer, branch, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Order retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Order retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getPurchaseOrderAmendmentItemCodeDropdown")

	public ResponseEntity<ResponseDTO> getPurchaseOrderAmendmentItemCodeDropdown(

			@RequestParam String docId,

			@RequestParam Long branch,

			@RequestParam Long orgId) {

		String methodName = "getPurchaseOrderAmendmentItemCodeDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getPurchaseOrderAmendmentItemCodeDropdown(docId, branch, orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item Code retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Item Code retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getCurrencyExchangeRateforPurchaseOrderAmendment")
	public ResponseEntity<ResponseDTO> getCurrencyExchangeRateforPurchaseOrderAmendment(@RequestParam Long customer,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getCurrencyExchangeRateforPurchaseOrderAmendment()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = developService.getCurrencyExchangeRateforPurchaseOrderAmendment(customer, orgId, branch);

			responseObjectsMap.put("currencyDetails", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		return ResponseEntity.ok(responseDTO);
	}

	// openstockentry

	@PutMapping("/createUpdateOpenStockEntry")
	public ResponseEntity<ResponseDTO> createUpdateOpenStockEntry(@RequestBody OpenStockEntryDto openStockEntryDto) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> openStockEntryMap = developService.createUpdateOpenStockEntry(openStockEntryDto);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, openStockEntryMap.get("message"));

			responseObjectsMap.put("openStockEntryVO", openStockEntryMap.get("openStockEntryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getOpenStockEntryById")
	public ResponseEntity<ResponseDTO> getOpenStockEntryById(@RequestParam Long id) {

		String methodName = "getOpenStockEntryById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			OpenStockEntryResponseDTO openStockEntryResponseDTO = developService.getOpenStockEntryById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Open Stock Entry information retrieved successfully");

			responseObjectsMap.put("openStockEntryVO", openStockEntryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Open Stock Entry information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getOpenStockEntryByOrgId")
	public ResponseEntity<ResponseDTO> getOpenStockEntryByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getOpenStockEntryByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<OpenStockEntryResponseDTO> openStockEntryResponseDTO = developService.getOpenStockEntryByOrgId(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Open Stock Entry information retrieved successfully");

			responseObjectsMap.put("openStockEntryResponseVO", openStockEntryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Open Stock Entry information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// itemcodedropdownforopenstockentry

	@GetMapping("/getOpenStockEntryItemCodeDropdown")
	public ResponseEntity<ResponseDTO> getOpenStockEntryItemCodeDropdown(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getOpenStockEntryItemCodeDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getOpenStockEntryItemCodeDropdown(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item Code retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Item Code retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getOpenStockEntryDocId")

	public ResponseEntity<ResponseDTO> getOpenStockEntryDocId(@RequestParam Long orgId,
			@RequestParam String financialYear, @RequestParam String screenCode) {

		String methodName = "getOpenStockEntryDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = developService.getOpenStockEntryDocId(orgId, financialYear, screenCode);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Open Stock Entry DocId information retrieved successfully");

			responseObjectsMap.put("openStockEntryDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Open Stock Entry DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	// Issues

	@PutMapping("/createUpdateIssues")

	public ResponseEntity<ResponseDTO> createUpdateIssues(

			@RequestBody IssuesDTO issuesDto) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> issuesMap = developService.createUpdateIssues(issuesDto);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, issuesMap.get("message"));

			responseObjectsMap.put("issuesVO", issuesMap.get("issuesVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIssuesById")
	public ResponseEntity<ResponseDTO> getIssuesById(@RequestParam Long id) {

		String methodName = "getIssuesById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			IssuesResponseDTO issuesResponseDTO = developService.getIssuesById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Issues information retrieved successfully");

			responseObjectsMap.put("issuesVO", issuesResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Issues information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getIssuesByOrgId")
	public ResponseEntity<ResponseDTO> getIssuesByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getIssuesByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<IssuesResponseDTO> issuesResponseDTO = developService.getIssuesByOrgId(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Issues information retrieved successfully");

			responseObjectsMap.put("issuesResponseVO", issuesResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Issues information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// issuesfromdropdown

	@GetMapping("/getIssueFromLocationDropdown")
	public ResponseEntity<ResponseDTO> getIssueFromLocationDropdown(@RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getIssueFromLocationDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getIssueFromLocationDropdown(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Issue From Location retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Issue From Location retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// Issuetodropdown

	@GetMapping("/getIssueToLocationDropdown")
	public ResponseEntity<ResponseDTO> getIssueToLocationDropdown(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam Long issueFrom) {

		String methodName = "getIssueToLocationDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getIssueToLocationDropdown(orgId, branch, issueFrom);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Issue To Location retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Issue To Location retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// issuesindentnumberdropdown

	@GetMapping("/getIssueIndentNoDropdown")
	public ResponseEntity<ResponseDTO> getIssueIndentNoDropdown(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getIssueIndentNoDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getIssueIndentNoDropdown(orgId, branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Indent Number retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Indent Number retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// issuesitemcodedropdown

	@GetMapping("/getIssueItemCodeDropdown")
	public ResponseEntity<ResponseDTO> getIssueItemCodeDropdown(@RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam String indentNo) {

		String methodName = "getIssueItemCodeDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getIssueItemCodeDropdown(orgId, branch, indentNo);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item Code retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Item Code retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// docidissues

	@GetMapping("/getIssuesDocId")
	public ResponseEntity<ResponseDTO> getIssuesDocId(@RequestParam Long orgId, @RequestParam String financialYear,
			@RequestParam String screenCode1) {

		String methodName = "getIssuesDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String docId = "";

		try {

			docId = developService.getIssuesDocId(orgId, financialYear);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Issues DocId information retrieved successfully");

			responseObjectsMap.put("issuesDocId", docId);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Issues DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	// ParameterMaster

	@PutMapping("/createUpdateParameterMaster")
	public ResponseEntity<ResponseDTO> createUpdateParameterMaster(@RequestBody ParameterMasterDTO parameterMasterDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> parameterMasterMap = developService.createUpdateParameterMaster(parameterMasterDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, parameterMasterMap.get("message"));

			responseObjectsMap.put("parameterMasterVO", parameterMasterMap.get("parameterMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getParameterMasterById")
	public ResponseEntity<ResponseDTO> getParameterMasterById(@RequestParam Long id) {

		String methodName = "getParameterMasterById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			ParameterMasterResponseDTO parameterMasterResponseDTO = developService.getParameterMasterById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Parameter Master information retrieved successfully");

			responseObjectsMap.put("parameterMasterVO", parameterMasterResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Parameter Master information retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getParameterMasterByOrgId")
	public ResponseEntity<ResponseDTO> getParameterMasterByOrgId(@RequestParam Long orgId) {

		String methodName = "getParameterMasterByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<ParameterMasterResponseDTO> parameterMasterResponseDTO = developService
					.getParameterMasterByOrgId(orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Parameter Master information retrieved successfully");

			responseObjectsMap.put("parameterMasterResponseVO", parameterMasterResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Parameter Master information retrieval failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// machine/instrumentmaster

	@PostMapping(value = "/createUpdateMachineMaster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateMachineMaster(

			@RequestPart("machineMaster") MachineMasterDTO machineMasterDTO,
//			@RequestBody MachineMasterDTO machineMasterDTO,

			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> machineMasterMap = developService.createUpdateMachineMaster(machineMasterDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, machineMasterMap.get("message"));

			responseObjectsMap.put("machineMasterVO", machineMasterMap.get("machineMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

}