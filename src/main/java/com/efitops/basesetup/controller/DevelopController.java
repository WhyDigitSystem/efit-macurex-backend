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

import com.efitops.basesetup.ResponseDTO.ControlPlanResponseDTO;
import com.efitops.basesetup.ResponseDTO.IssuesResponseDTO;
import com.efitops.basesetup.ResponseDTO.MachineMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.OpenStockEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ParameterMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProcessSheetCompRoutingResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentContractDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentItemDropdownResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseContractAmendmentResponseDto;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderAmendmentResponceDTO;
import com.efitops.basesetup.ResponseDTO.RootCauseAnalysisResponseDTO;
import com.efitops.basesetup.ResponseDTO.ToolCategoryResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ControlPlanDTO;
import com.efitops.basesetup.dto.EnquiryDTO;
import com.efitops.basesetup.dto.EnquiryResponseDTO;
import com.efitops.basesetup.dto.IssuesDTO;
import com.efitops.basesetup.dto.MachineMasterDTO;
import com.efitops.basesetup.dto.OpenStockEntryDto;
import com.efitops.basesetup.dto.ParameterMasterDTO;
import com.efitops.basesetup.dto.ProcessSheetCompRoutingDTO;
import com.efitops.basesetup.dto.PurchaseContractAmendmentDto;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseOrderAmendmentDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.RootCauseAnalysisDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentDTO;
import com.efitops.basesetup.dto.SalesOrderAmendmentResponseDTO;
import com.efitops.basesetup.dto.ToolCategoryDTO;
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

//			@RequestPart("purchaseOrderAmendment") PurchaseOrderAmendmentDTO purchaseOrderAmendmentDTO,
			@RequestBody PurchaseOrderAmendmentDTO purchaseOrderAmendmentDTO,

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
	public ResponseEntity<ResponseDTO> getPurchaseOrderAmendmentItemCodeDropdown(@RequestParam String docId,
			@RequestParam Long branch, @RequestParam Long orgId) {

		String methodName = "getPurchaseOrderAmendmentItemCodeDropdown()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> itemList = new ArrayList<>();

		try {

			itemList = developService.getPurchaseOrderAmendmentItemCodeDropdown(docId, branch, orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information retrieved successfully");

			responseObjectsMap.put("itemCodeDropdown", itemList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Item information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCurrencyExchangeRateforPurchaseOrderAmendment")
	public ResponseEntity<ResponseDTO> getCurrencyExchangeRateforPurchaseOrderAmendment(@RequestParam String docId,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getCurrencyExchangeRateforPurchaseOrderAmendment()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> mov = new ArrayList<>();

		try {

			mov = developService.getCurrencyExchangeRateforPurchaseOrderAmendment(docId, orgId, branch);

			responseObjectsMap.put("currencyDetails", mov);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		return ResponseEntity.ok(responseDTO);
	}

	// DOCID

	@GetMapping("/getPurchaseOrderAmendmentDocId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderAmendmentDocId(@RequestParam Long orgId,
			@RequestParam String financialYear, @RequestParam String screenCode) {

		String methodName = "getPurchaseOrderAmendmentDocId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		String mapp = "";

		try {

			mapp = developService.getPurchaseOrderAmendmentDocId(orgId, financialYear, screenCode);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Order Amendment DocId information retrieved successfully");

			responseObjectsMap.put("purchaseOrderAmendmentDocId", mapp);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Purchase Order Amendment DocId", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
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

	@PostMapping(value = "/updateCreateMachineMaster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> updateCreateMachineMaster(

        	@RequestPart("machineMasterDTO") MachineMasterDTO machineMasterDTO,
//			@RequestBody() MachineMasterDTO machineMasterDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files,
	        @RequestPart(value = "images", required = false) MultipartFile[] images) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = developService.updateCreateMachineMaster(machineMasterDTO, files,images);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));

			responseObjectsMap.put("machineMasterVO", response.get("machineMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

//	@GetMapping("/getMachineMasterById")
//	public ResponseEntity<ResponseDTO> getMachineMasterById(@RequestParam Long id) {
//
//		String methodName = "getMachineMasterById()";
//
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		String errorMsg = null;
//
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//
//		ResponseDTO responseDTO = null;
//
//		try {
//
//			MachineMasterResponseDTO machineMasterResponseDTO = developService.getMachineMasterById(id);
//
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Machine Master information retrieved successfully");
//
//			responseObjectsMap.put("machineMasterVO", machineMasterResponseDTO);
//
//			responseDTO = createServiceResponse(responseObjectsMap);
//
//		} catch (Exception e) {
//
//			errorMsg = e.getMessage();
//
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//
//			responseDTO = createServiceResponseError(responseObjectsMap, "Machine Master information retrieval failed",
//					errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//		return ResponseEntity.ok(responseDTO);
//	}

	@GetMapping("/getMachineMasterById")
	public ResponseEntity<ResponseDTO> getMachineMasterById(@RequestParam Long id) {

		String methodName = "getMachineMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		String errorMsg = null;
		ResponseDTO responseDTO = null;

		try {

			MachineMasterResponseDTO machineMasterResponseDTO = developService.getMachineMasterById(id);

			responseObjectsMap.put("machineMasterVO", machineMasterResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getMachineMasterByOrgId")
	public ResponseEntity<ResponseDTO> getMachineMasterByOrgId(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getMachineMasterByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<MachineMasterResponseDTO> machineMasterResponseDTO = developService.getMachineMasterByOrgId(orgId,
					branch);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Machine Master information retrieved successfully");

			responseObjectsMap.put("machineMasterResponseVO", machineMasterResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Machine Master information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// machine/instrumentcategory

	@GetMapping("/getToolCategoryforMachineMaster")
	public ResponseEntity<ResponseDTO> getToolCategoryforMachineMaster(@RequestParam Long orgId,
			@RequestParam String applicableFor) {

		String methodName = "getToolCategoryforMachineMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {

			responseObjectsMap = developService.getToolCategoryforMachineMaster(orgId, applicableFor);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tool Category retrieved successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Tool Category retrieval failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// TOOL CATEGORY

	@PutMapping("/createUpdateToolCategory")
	public ResponseEntity<ResponseDTO> createUpdateToolCategory(@RequestBody ToolCategoryDTO toolCategoryDTO) {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> toolCategoryMap = developService.createUpdateToolCategory(toolCategoryDTO);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, toolCategoryMap.get("message"));

			responseObjectsMap.put("toolCategoryVO", toolCategoryMap.get("toolCategoryVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getToolCategoryById")
	public ResponseEntity<ResponseDTO> getToolCategoryById(@RequestParam Long id) {

		String methodName = "getToolCategoryById()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		try {

			ToolCategoryResponseDTO toolCategoryResponseDTO = developService.getToolCategoryById(id);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tool Category information retrieved successfully");

			responseObjectsMap.put("toolCategoryVO", toolCategoryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Tool Category information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getToolCategoryByOrgId")
	public ResponseEntity<ResponseDTO> getToolCategoryByOrgId(@RequestParam Long orgId) {

		String methodName = "getToolCategoryByOrgId()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			List<ToolCategoryResponseDTO> toolCategoryResponseDTO = developService.getToolCategoryByOrgId(orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Tool Category information retrieved successfully");

			responseObjectsMap.put("toolCategoryResponseVO", toolCategoryResponseDTO);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());

			responseDTO = createServiceResponseError(responseObjectsMap, "Tool Category information retrieval failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok(responseDTO);
	}

	// purchase order amendment dropdown
	@GetMapping("/getPurchaseOrderDropdownForPurchaseOrderAmendment")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDropdownForPurchaseOrderAmendment(@RequestParam Long branch,
			@RequestParam Long customerId, @RequestParam Long orgId) {

		String methodName = "getPurchaseOrderDropdownForPurchaseOrderAmendment()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO = null;

		List<Map<String, Object>> purchaseOrderList = new ArrayList<>();

		try {

			purchaseOrderList = developService.getPurchaseOrderDropdownForPurchaseOrderAmendment(branch,
					customerId, orgId);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Order information retrieved successfully");

			responseObjectsMap.put("purchaseOrderDropdown", purchaseOrderList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Purchase Order information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//process sheet comp routing
	
	
	
	@PutMapping("/updateCreateProcessSheetCompRouting")

	public ResponseEntity<ResponseDTO> updateCreateProcessSheetCompRouting(
	        @RequestBody ProcessSheetCompRoutingDTO processSheetCompRoutingDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> processSheetCompRoutingMap =
	                developService.updateCreateProcessSheetCompRouting(
	                        processSheetCompRoutingDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                processSheetCompRoutingMap.get("message"));

	        responseObjectsMap.put(
	                "processSheetCompRoutingVO",
	                processSheetCompRoutingMap.get("processSheetCompRoutingVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//orgid
	
	
	@GetMapping("/getProcessSheetCompRoutingByOrgId")
	public ResponseEntity<ResponseDTO> getProcessSheetCompRoutingByOrgId(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getProcessSheetCompRoutingByOrgId()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        List<ProcessSheetCompRoutingResponseDTO>
	                processSheetCompRoutingResponseDTO =
	                developService.getProcessSheetCompRoutingByOrgId(
	                        orgId, branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Process Sheet Comp Routing information retrieved successfully");

	        responseObjectsMap.put(
	                "processSheetCompRoutingResponseVO",
	                processSheetCompRoutingResponseDTO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Process Sheet Comp Routing information retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getProcessSheetCompRoutingById")
	public ResponseEntity<ResponseDTO> getProcessSheetCompRoutingById(
	        @RequestParam Long id) {

	    String methodName = "getProcessSheetCompRoutingById()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        ProcessSheetCompRoutingResponseDTO
	                processSheetCompRoutingResponseDTO =
	                developService.getProcessSheetCompRoutingById(id);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Process Sheet Comp Routing information retrieved successfully");

	        responseObjectsMap.put(
	                "processSheetCompRoutingResponseVO",
	                processSheetCompRoutingResponseDTO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Process Sheet Comp Routing information retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	
	// FG/SFG Item Code Dropdown
	
	
	@GetMapping("/getFgSfgItemCodeDropdownforProcessSheetCompRouting")
	public ResponseEntity<ResponseDTO> getFgSfgItemCodeDropdownforProcessSheetCompRouting(
	        @RequestParam Long orgId,
	        @RequestParam Long branch,
	        @RequestParam Long itemType) {

	    String methodName = "getFgSfgItemCodeDropdown()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> fgSfgItemCodeResponse =
	                developService.getFgSfgItemCodeDropdownforProcessSheetCompRouting(
	                        orgId,
	                        branch,
	                        itemType);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "FG/SFG Item Code information retrieved successfully");

	        responseObjectsMap.put(
	                "itemCodeList",
	                fgSfgItemCodeResponse.get("itemCodeList"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "FG/SFG Item Code information retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	//LocationDropdownforProcessSheetCompRouting
	
	
	@GetMapping("/getLocationDropdownforProcessSheetCompRouting")
	public ResponseEntity<ResponseDTO> getLocationDropdownforProcessSheetCompRouting(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getLocationDropdownforProcessSheetCompRouting()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> locationResponse =
	                developService.getLocationDropdownforProcessSheetCompRouting(
	                        orgId,
	                        branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Location information retrieved successfully");

	        responseObjectsMap.put(
	                "locationList",
	                locationResponse.get("locationList"));

	        responseDTO =
	                createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Location information retrieval failed",
	                        e.getMessage());
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	//getOperationDropdownforProcessSheetCompRouting
	
	
	@GetMapping("/getOperationDropdownforProcessSheetCompRouting")
	public ResponseEntity<ResponseDTO> getOperationDropdownforProcessSheetCompRouting(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName =
	            "getOperationDropdownforProcessSheetCompRouting()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> operationResponse =
	                developService
	                        .getOperationDropdownforProcessSheetCompRouting(
	                                orgId,
	                                branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Operation information retrieved successfully");

	        responseObjectsMap.put(
	                "operationList",
	                operationResponse.get("operationList"));

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Operation information retrieval failed",
	                        e.getMessage());
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	//RootCauseAnalysis
	
	
	@PutMapping("/updateCreateRootCauseAnalysis")

	public ResponseEntity<ResponseDTO> updateCreateRootCauseAnalysis(
	        @RequestBody RootCauseAnalysisDTO rootCauseAnalysisDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> rootCauseAnalysisMap =
	                developService.updateCreateRootCauseAnalysis(
	                        rootCauseAnalysisDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                rootCauseAnalysisMap.get("message"));

	        responseObjectsMap.put(
	                "rootCauseAnalysisVO",
	                rootCauseAnalysisMap.get("rootCauseAnalysisVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	//orgid
	
	
	
	@GetMapping("/getRootCauseAnalysisByOrgId")
	public ResponseEntity<ResponseDTO> getRootCauseAnalysisByOrgId(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getRootCauseAnalysisByOrgId()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        List<RootCauseAnalysisResponseDTO>
	                rootCauseAnalysisResponseDTO =
	                developService.getRootCauseAnalysisByOrgId(
	                        orgId,
	                        branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Root Cause Analysis information retrieved successfully");

	        responseObjectsMap.put(
	                "rootCauseAnalysisResponseVO",
	                rootCauseAnalysisResponseDTO);

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Root Cause Analysis information retrieval failed",
	                        e.getMessage());
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	//byid
	
	
	@GetMapping("/getRootCauseAnalysisById")
	public ResponseEntity<ResponseDTO> getRootCauseAnalysisById(
	        @RequestParam Long id) {

	    String methodName = "getRootCauseAnalysisById()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        RootCauseAnalysisResponseDTO
	                rootCauseAnalysisResponseDTO =
	                developService.getRootCauseAnalysisById(id);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Root Cause Analysis information retrieved successfully");

	        responseObjectsMap.put(
	                "rootCauseAnalysisResponseVO",
	                rootCauseAnalysisResponseDTO);

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Root Cause Analysis information retrieval failed",
	                        e.getMessage());
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
//DROP DOWN
	
	
	@GetMapping("/getCustomerComplaintDropDownForRootCauseAnalysis")
	public ResponseEntity<ResponseDTO> getCustomerComplaintDropDownForRootCauseAnalysis(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName =
	            "getCustomerComplaintDropDownForRootCauseAnalysis()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> complaintResponse =
	                developService
	                        .getCustomerComplaintDropDownForRootCauseAnalysis(
	                                orgId,
	                                branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Customer complaint information retrieved successfully");

	        responseObjectsMap.put(
	                "complaintList",
	                complaintResponse.get("complaintList"));

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Customer complaint information retrieval failed",
	                        e.getMessage());
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	//drop down
	
	
	@GetMapping("/getItemDropdownForRootCauseAnalysis")
	public ResponseEntity<ResponseDTO> getItemDropdownForRootCauseAnalysis(
	        @RequestParam String compino,
	        @RequestParam Long branch,
	        @RequestParam Long orgId) {

	    String methodName =
	            "getItemDropdownForRootCauseAnalysis()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> itemResponse =
	                developService
	                        .getItemDropdownForRootCauseAnalysis(
	                                compino,
	                                branch,
	                                orgId);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Item information retrieved successfully");

	        responseObjectsMap.put(
	                "itemList",
	                itemResponse.get("itemList"));

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Item information retrieval failed",
	                        e.getMessage());
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getRootCauseAnalysisDocId")

	public ResponseEntity<ResponseDTO> getRootCauseAnalysisDocId(
	        @RequestParam Long orgId,
	        @RequestParam String financialYear) {

	    String methodName = "getRootCauseAnalysisDocId()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO = null;

	    String mapp = "";

	    try {

	        mapp = developService
	                .getRootCauseAnalysisDocId(
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
	                "Root Cause Analysis DocId information retrieved successfully");

	        responseObjectsMap.put(
	                "rootCauseAnalysisDocId",
	                mapp);

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } else {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed to retrieve Root Cause Analysis DocId",
	                        errorMsg);
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity
	            .ok()
	            .body(responseDTO);
	}
	
	
	//createupdatecontrolplan
	
	
	@PutMapping("/createUpdateControlPlan")

	public ResponseEntity<ResponseDTO> createUpdateControlPlan(
	        @RequestBody ControlPlanDTO controlPlanDTO) {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> controlPlanMap =
	                developService.createUpdateControlPlan(controlPlanDTO);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                controlPlanMap.get("message"));

	        responseObjectsMap.put(
	                "controlPlanVO",
	                controlPlanMap.get("controlPlanVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        e.printStackTrace();

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getControlPlanByOrgId")

	public ResponseEntity<ResponseDTO> getControlPlanByOrgId(
	        @RequestParam Long orgId,
	        @RequestParam Long branch) {

	    String methodName = "getControlPlanByOrgId()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        List<ControlPlanResponseDTO> controlPlanResponseDTO =
	                developService.getControlPlanByOrgId(
	                        orgId,
	                        branch);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Control Plan information retrieved successfully");

	        responseObjectsMap.put(
	                "controlPlanResponseVO",
	                controlPlanResponseDTO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Control Plan information retrieval failed",
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getControlPlanById")

	public ResponseEntity<ResponseDTO> getControlPlanById(
	        @RequestParam Long id) {

	    String methodName = "getControlPlanById()";

	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    ResponseDTO responseDTO = null;

	    try {

	        ControlPlanResponseDTO controlPlanResponseDTO =
	                developService.getControlPlanById(id);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Control Plan information retrieved successfully");

	        responseObjectsMap.put(
	                "controlPlanVO",
	                controlPlanResponseDTO);

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        errorMsg = e.getMessage();

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                errorMsg);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                "Control Plan information retrieval failed",
	                errorMsg);
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	//controlplan dropdown
	
	
	@GetMapping("/getcontrolplandropdownforMachineFixtureDropdown")
	public ResponseEntity<ResponseDTO> getcontrolplandropdownforMachineFixtureDropdown(
	        @RequestParam Long branch,
	        @RequestParam Long orgId) {

	    String methodName = "getcontrolplandropdownforMachineFixtureDropdown()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> machineFixtureResponse =
	                developService.getcontrolplandropdownforMachineFixtureDropdown(
	                        branch,
	                        orgId);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Machine Fixture information retrieved successfully");

	        responseObjectsMap.put(
	                "machineFixtureList",
	                machineFixtureResponse.get("machineFixtureList"));

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Machine Fixture information retrieval failed",
	                        e.getMessage());
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getControlPlanDropdownitemcode")
	public ResponseEntity<ResponseDTO> getControlPlanDropdownitemcode(
	       
	        @RequestParam Long branch,
	        @RequestParam Long orgId) {

	    String methodName = "getControlPlanDropdownitemcode()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> controlPlanResponse =
	                developService.getControlPlanDropdownitemcode(
	                       
	                        branch,
	                        orgId);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                "Control Plan information retrieved successfully");

	        responseObjectsMap.put(
	                "controlPlanList",
	                controlPlanResponse.get("controlPlanList"));

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage());

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Control Plan information retrieval failed",
	                        e.getMessage());
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	
	
	@GetMapping("/getControlPlanDocId")
	public ResponseEntity<ResponseDTO> getControlPlanDocId(
	        @RequestParam Long orgId,
	        @RequestParam String financialYear) {

	    String methodName = "getControlPlanDocId()";

	    LOGGER.debug(
	            CommonConstant.STARTING_METHOD,
	            methodName);

	    String errorMsg = null;

	    Map<String, Object> responseObjectsMap =
	            new HashMap<>();

	    ResponseDTO responseDTO = null;

	    String mapp = "";

	    try {

	        mapp = developService
	                .getControlPlanDocId(
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
	                "Control Plan DocId information retrieved successfully");

	        responseObjectsMap.put(
	                "controlPlanDocId",
	                mapp);

	        responseDTO =
	                createServiceResponse(
	                        responseObjectsMap);

	    } else {

	        responseDTO =
	                createServiceResponseError(
	                        responseObjectsMap,
	                        "Failed to retrieve Control Plan DocId",
	                        errorMsg);
	    }

	    LOGGER.debug(
	            CommonConstant.ENDING_METHOD,
	            methodName);

	    return ResponseEntity
	            .ok()
	            .body(responseDTO);
	}
	
	
	
	
	
	
	
	
}