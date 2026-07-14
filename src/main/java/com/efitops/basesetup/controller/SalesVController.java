package com.efitops.basesetup.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesDTO;
import com.efitops.basesetup.dto.SalesInvoiceExportDTO;
import com.efitops.basesetup.entity.SalesInvoiceExportVO;
import com.efitops.basesetup.entity.SalesVO;
import com.efitops.basesetup.service.SalesVService;

@CrossOrigin
@RestController
@RequestMapping("/api/salesVController")
public class SalesVController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(SalesVController.class);

	@Autowired
	SalesVService salesVService;

	// Sales

	@GetMapping("/getAllSalesByOrgId")
	public ResponseEntity<ResponseDTO> getAllSalesByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getAllSalesByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SalesVO> salesVO = new ArrayList<>();
		try {
			salesVO = salesVService.getAllSalesByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales information get successfully ByOrgId");
			responseObjectsMap.put("salesVO", salesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Sales information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSalesById")
	public ResponseEntity<ResponseDTO> getSalesById(@RequestParam Long id) {
		String methodName = "getSalesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SalesVO salesVO = new SalesVO();
		try {
			salesVO = salesVService.getSalesById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales information get successfully By id");
			responseObjectsMap.put("salesVO", salesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Sales information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateSalesOrder")
	public ResponseEntity<ResponseDTO> updateCreateSalesOrder(@RequestBody SalesDTO salesDTO) {
		String methodName = "updateCreateSalesOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> salesVO = salesVService.updateCreateSalesOrder(salesDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, salesVO.get("message"));
			responseObjectsMap.put("salesVO", salesVO.get("salesVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesDocId")
	public ResponseEntity<ResponseDTO> getSalesDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getSalesDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = salesVService.getSalesDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalesDocId information retrieved successfully");
			responseObjectsMap.put("salesDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SalesDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByCustomerNameFromPartyMasterSalesOrder")
	public ResponseEntity<ResponseDTO> findByCustomerNameFromPartyMasterSalesOrder(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "findByCustomerNameFromPartyMasterSalesOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByCustomerNameFromPartyMasterSalesOrder(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve CustomerName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByShippingAddressFromPartyMaster")
	public ResponseEntity<ResponseDTO> findByShippingAddressFromPartyMaster(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String customerName) {
		String methodName = "findByShippingAddressFromPartyMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByShippingAddressFromPartyMaster(orgId, finYear, branchCode, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ShippingAddress Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ShippingAddress Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByCustomerPoNoFromWorkOrder")
	public ResponseEntity<ResponseDTO> findByCustomerPoNoFromWorkOrder(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String customerName) {
		String methodName = "findByCustomerPoNoFromWorkOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByCustomerPoNoFromWorkOrder(orgId, finYear, branchCode, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "customerPoNumber Details retrieved successfully");
			responseObjectsMap.put("workOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve customerPoNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByWorkOrderNo")
	public ResponseEntity<ResponseDTO> findByWorkOrderNo(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode, @RequestParam String customerPoNo) {
		String methodName = "findByWorkOrderNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByWorkOrderNo(orgId, finYear, branchCode, customerPoNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "WorkOrderVO Details retrieved successfully");
			responseObjectsMap.put("workOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve WorkOrderVO Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByContactPersonFromPartyMaster")
	public ResponseEntity<ResponseDTO> findByContactPersonFromPartyMaster(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String customerCode) {
		String methodName = "findByContactPersonFromPartyMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByContactPersonFromPartyMaster(orgId, finYear, branchCode, customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ContactPerson Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ContactPerson Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByInvoiceType")
	public ResponseEntity<ResponseDTO> findByInvoiceType(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode, String customerCode, @RequestParam String currency) {
		String methodName = "findByInvoiceType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByInvoiceType(orgId, finYear, branchCode, customerCode, currency);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InvoiceType Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve InvoiceType Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByTaxType")
	public ResponseEntity<ResponseDTO> findByTaxType(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String customerCode, @RequestParam String partyType) {
		String methodName = "findByTaxType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByTaxType(orgId, branchCode, customerCode, partyType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TaxType Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve TaxType Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByGstForSalesOrder")
	public ResponseEntity<ResponseDTO> findByGstForSalesOrder(@RequestParam Long orgId, @RequestParam String currency,
			@RequestParam String item, @RequestParam String taxType) {
		String methodName = "findByGstForSalesOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByGstForSalesOrder(orgId, currency, item, taxType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GST Details retrieved successfully");
			responseObjectsMap.put("GSTVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GST Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByPartNoAndDescFromWorkOrder")
	public ResponseEntity<ResponseDTO> findByPartNoAndDescFromWorkOrder(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String workOrderNo) {
		String methodName = "findByPartNoAndDescFromWorkOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByPartNoAndDescFromWorkOrder(orgId, finYear, branchCode, workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("workOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SALES INVOICE EXPORT

	@PutMapping("/updateCreateSalesInvoiceExport")
	public ResponseEntity<ResponseDTO> updateCreateSalesInvoiceExport(
			@Valid @RequestBody SalesInvoiceExportDTO salesInvoiceExportDTO) {
		String methodName = "updateCreateSalesInvoiceExport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> salesInvoiceExportVO = salesVService
					.updateCreateSalesInvoiceExport(salesInvoiceExportDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, salesInvoiceExportVO.get("message"));
			responseObjectsMap.put("salesInvoiceExportVO", salesInvoiceExportVO.get("salesInvoiceExportVO")); // Corrected
																												// key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesInvoiceExportDocId")
	public ResponseEntity<ResponseDTO> getSalesInvoiceExportDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getSalesInvoiceExportDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = salesVService.getSalesInvoiceExportDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sales Invoice Export DocId  information retrieved successfully");
			responseObjectsMap.put("dailyPatrolInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Sales Invoice Export DocId  information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesInvoiceExportById")
	public ResponseEntity<ResponseDTO> getSalesInvoiceExportById(@RequestParam Long id) {
		String methodName = "getSalesInvoiceExportById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SalesInvoiceExportVO salesInvoiceExportVO = new SalesInvoiceExportVO();
		try {
			salesInvoiceExportVO = salesVService.getSalesInvoiceExportById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesInvoiceExport information get successfully By id");
			responseObjectsMap.put("salesInvoiceExportVO", salesInvoiceExportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesInvoiceExport information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllSalesInvoiceExport")
	public ResponseEntity<ResponseDTO> getAllSalesInvoiceExport(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getAllSalesInvoiceExport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SalesInvoiceExportVO> salesVO = new ArrayList<SalesInvoiceExportVO>();
		try {
			salesVO = salesVService.getAllSalesInvoiceExport(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales Invoice Export information get successfully");
			responseObjectsMap.put("salesVO", salesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Sales Invoice Export information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByCustomerNameFromPartyMasterSalesInvoiceExport")
	public ResponseEntity<ResponseDTO> findByCustomerNameFromPartyMasterSalesInvoiceExport(@RequestParam Long orgId,@RequestParam String partyName) {
		String methodName = "findByCustomerNameFromPartyMasterSalesInvoiceExport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByCustomerNameFromPartyMasterSalesInvoiceExport(orgId,partyName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve CustomerName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByShippingFromPartySalesInvoiceExp")
	public ResponseEntity<ResponseDTO> findByShippingFromPartySalesInvoiceExp(@RequestParam Long orgId,
			@RequestParam String customerName) {
		String methodName = "findByShippingFromPartySalesInvoiceExp()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.findByShippingFromPartySalesInvoiceExp(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ShippingAddress Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ShippingAddress Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesOrderNumber")
	public ResponseEntity<ResponseDTO> getSalesOrderNumber(@RequestParam Long orgId,
			@RequestParam String customerName) {
		String methodName = "getSalesOrderNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.getSalesOrderNumber(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalesOrderNumber Details retrieved successfully");
			responseObjectsMap.put("salesOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SalesOrderNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getexportpackinglistNumber")
	public ResponseEntity<ResponseDTO> getexportpackinglistNumber(@RequestParam Long orgId,
			@RequestParam String customerName, @RequestParam String salesOrderNo) {
		String methodName = "getexportpackinglistNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.getexportpackinglistNumber(orgId, customerName, salesOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "exportPackingList Details retrieved successfully");
			responseObjectsMap.put("exportPackingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve exportPackingList Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoFromexportpackinglist")
	public ResponseEntity<ResponseDTO> getPartNoFromexportpackinglist(@RequestParam Long orgId,
			@RequestParam String salesOrderNo, @RequestParam String exportPackingListNo) {
		String methodName = "getPartNoFromexportpackinglist()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesVService.getPartNoFromexportpackinglist(orgId, salesOrderNo, exportPackingListNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InvoiceType Details retrieved successfully");
			responseObjectsMap.put("exportPackingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve InvoiceType Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getSalesOrderDetails")
	public ResponseEntity<ResponseDTO> getSalesOrderDetails(@RequestParam Long orgId,
			@RequestParam String customerName, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam String branchCode) {
		String methodName = "getSalesOrderDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> salesOrderDetails = new ArrayList<>();
		try {
			salesOrderDetails = salesVService.getSalesOrderDetails(orgId, customerName, fromDate, toDate,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalesOrderDetails get successfully By OrgId");
			responseObjectsMap.put("salesOrderDetails", salesOrderDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesOrderDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSalesOrderSummaryDetails")
	public ResponseEntity<ResponseDTO> getSalesOrderSummaryDetails(@RequestParam Long orgId, @RequestParam String customerName,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam String branchCode) {
		String methodName = "getSalesOrderSummaryDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> salesOrderSummaryDetails = new ArrayList<>();
		try {
			salesOrderSummaryDetails = salesVService.getSalesOrderSummaryDetails(orgId, customerName,
					fromDate, toDate, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesOrderSummaryDetails get successfully By OrgId");
			responseObjectsMap.put("salesOrderSummaryDetails", salesOrderSummaryDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesOrderSummaryDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@GetMapping("/getSalesInvoiceExportDetails")
	public ResponseEntity<ResponseDTO> getSalesInvoiceExportDetails(@RequestParam Long orgId,
			@RequestParam String customerName, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam String branchCode) {
		String methodName = "getSalesInvoiceExportDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> salesInvoiceExportDetails = new ArrayList<>();
		try {
			salesInvoiceExportDetails = salesVService.getSalesInvoiceExportDetails(orgId, customerName, fromDate, toDate,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalesInvoiceExportDetails get successfully By OrgId");
			responseObjectsMap.put("salesInvoiceExportDetails", salesInvoiceExportDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesInvoiceExportDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSalesInvoiceExportSummaryDetails")
	public ResponseEntity<ResponseDTO> getSalesInvoiceExportSummaryDetails(@RequestParam Long orgId, @RequestParam String customerName,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam String branchCode) {
		String methodName = "getSalesInvoiceExportSummaryDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> salesInvoiceExportSummaryDetails = new ArrayList<>();
		try {
			salesInvoiceExportSummaryDetails = salesVService.getSalesInvoiceExportSummaryDetails(orgId, customerName,
					fromDate, toDate, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"salesInvoiceExportSummaryDetails get successfully By OrgId");
			responseObjectsMap.put("salesInvoiceExportSummaryDetails", salesInvoiceExportSummaryDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"salesInvoiceExportSummaryDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
}
