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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.DeliveryChalanForFgDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalDTO;
import com.efitops.basesetup.dto.SalesReturnExportDTO;
import com.efitops.basesetup.dto.SalesReturnLocalDTO;
import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalVO;
import com.efitops.basesetup.entity.SalesReturnExportVO;
import com.efitops.basesetup.entity.SalesReturnLocalVO;
import com.efitops.basesetup.service.SalesService;

@RestController
@RequestMapping("/api/sales")
public class SalesServiceController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(SalesServiceController.class);

	@Autowired
	SalesService salesService;

	// DeliveryChalanForFg

	@GetMapping("/getAllDeliveryChalanForFgByOrgId")
	public ResponseEntity<ResponseDTO> getAllDeliveryChalanForFgByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllDeliveryChalanForFgByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DeliveryChalanForFgVO> deliveryChalanForFgVO = new ArrayList<>();
		try {
			deliveryChalanForFgVO = salesService.getAllDeliveryChalanForFgByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DeliveryChalanForFg information get successfully ByOrgId");
			responseObjectsMap.put("deliveryChalanForFgVO", deliveryChalanForFgVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DeliveryChalanForFg information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDeliveryChalanForFgById")
	public ResponseEntity<ResponseDTO> getDeliveryChalanForFgById(@RequestParam Long id) {
		String methodName = "getDeliveryChalanForFgById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DeliveryChalanForFgVO deliveryChalanForFgVO = new DeliveryChalanForFgVO();
		try {
			deliveryChalanForFgVO = salesService.getDeliveryChalanForFgById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DeliveryChalanForFg information get successfully By id");
			responseObjectsMap.put("deliveryChalanForFgVO", deliveryChalanForFgVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DeliveryChalanForFg information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
//
//	@PutMapping("/createUpdateDeliveryChalanForFg")
//	public ResponseEntity<ResponseDTO> createUpdateDeliveryChalanForFg(
//			@RequestBody DeliveryChalanForFgDTO deliveryChalanForFgDTO) {
//		String methodName = "createUpdateDeliveryChalanForFg()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		try {
//			Map<String, Object> deliveryChalanForFgVO = salesService
//					.createUpdateDeliveryChalanForFg(deliveryChalanForFgDTO);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, deliveryChalanForFgVO.get("message"));
//			responseObjectsMap.put("deliveryChalanForFgVO", deliveryChalanForFgVO.get("deliveryChalanForFgVO"));
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

	@PutMapping("/createUpdateDeliveryChalanForFg")
	public ResponseEntity<ResponseDTO> createUpdateDeliveryChalanForFg(
			@RequestBody DeliveryChalanForFgDTO deliveryChalanForFgDTO) {
		String methodName = "createUpdateDeliveryChalanForFg()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> deliveryChalanForFgVO = salesService
					.createUpdateDeliveryChalanForFg(deliveryChalanForFgDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, deliveryChalanForFgVO.get("message"));
			responseObjectsMap.put("deliveryChalanForFgVO", deliveryChalanForFgVO.get("deliveryChalanForFgVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeliveryChalanForFgDocId")
	public ResponseEntity<ResponseDTO> getDeliveryChalanForFgDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getDeliveryChalanForFgDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = salesService.getDeliveryChalanForFgDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DeliveryChalanForFgDocId information retrieved successfully");
			responseObjectsMap.put("deliveryChalanForFgDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DeliveryChalanForFgDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomerNameFromPartyMaster")
	public ResponseEntity<ResponseDTO> getCustomerNameFromPartyMaster(@RequestParam Long orgId) {
		String methodName = "getCustomerNameFromPartyMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getCustomerNameFromPartyMaster(orgId);
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

	@GetMapping("/getSoNoFromSaleOrder")
	public ResponseEntity<ResponseDTO> getSoNoFromSaleOrder(@RequestParam Long orgId,
			@RequestParam String customerName) {
		String methodName = "getSoNoFromSaleOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getSoNoFromSaleOrder(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SoNo Details retrieved successfully");
			responseObjectsMap.put("saleOderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SoNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemDetailsforDCFGFromSaleOrder")
	public ResponseEntity<ResponseDTO> getItemDetailsforDCFGFromSaleOrder(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String finYear, @RequestParam String salesOrderNo) {
		String methodName = "getItemDetailsforDCFGFromSaleOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getItemDetailsforDCFGFromSaleOrder(orgId, branchCode, finYear, salesOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item Details retrieved successfully");
			responseObjectsMap.put("itemVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SoNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemNameFromSaleOrder")
	public ResponseEntity<ResponseDTO> getItemNameFromSaleOrder(@RequestParam String customerName,
			@RequestParam String customerCode) {
		String methodName = "getItemNameFromSaleOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getItemNameFromSaleOrder(customerName, customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemName Details retrieved successfully");
			responseObjectsMap.put("saleOderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SalesInvoiceLocal

	@GetMapping("/getAllSalesInvoiceLocalByOrgId")
	public ResponseEntity<ResponseDTO> getAllSalesInvoiceLocalByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSalesInvoiceLocalByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SalesInvoiceLocalVO> salesInvoiceLocalVO = new ArrayList<>();
		try {
			salesInvoiceLocalVO = salesService.getAllSalesInvoiceLocalByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesInvoiceLocal information get successfully ByOrgId");
			responseObjectsMap.put("salesInvoiceLocalVO", salesInvoiceLocalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesInvoiceLocal information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSalesInvoiceLocalById")
	public ResponseEntity<ResponseDTO> getSalesInvoiceLocalById(@RequestParam Long id) {
		String methodName = "getSalesInvoiceLocalById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SalesInvoiceLocalVO salesInvoiceLocalVO = new SalesInvoiceLocalVO();
		try {
			salesInvoiceLocalVO = salesService.getSalesInvoiceLocalById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesInvoiceLocal information get successfully By id");
			responseObjectsMap.put("salesInvoiceLocalVO", salesInvoiceLocalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesInvoiceLocal information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateSalesInvoiceLocal")
	public ResponseEntity<ResponseDTO> createUpdateSalesInvoiceLocal(
			@RequestBody SalesInvoiceLocalDTO salesInvoiceLocalDTO) {
		String methodName = "createUpdateSalesInvoiceLocal()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> salesInvoiceLocalVO = salesService.createUpdateSalesInvoiceLocal(salesInvoiceLocalDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, salesInvoiceLocalVO.get("message"));
			responseObjectsMap.put("salesInvoiceLocalVO", salesInvoiceLocalVO.get("salesInvoiceLocalVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesInvoiceLocalDocId")
	public ResponseEntity<ResponseDTO> getSalesInvoiceLocalDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getSalesInvoiceLocalDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = salesService.getSalesInvoiceLocalDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesInvoiceLocalDocId information retrieved successfully");
			responseObjectsMap.put("salesInvoiceLocalDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SalesInvoiceLocalDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getpartyNameFromPartyMaster")
	public ResponseEntity<ResponseDTO> getpartyNameFromPartyMaster(@RequestParam Long orgId) {
		String methodName = "getpartyNameFromPartyMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getpartyNameFromPartyMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartyName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartyName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocIdFromPackingList")
	public ResponseEntity<ResponseDTO> getDocIdFromPackingList(@RequestParam Long orgId,
			@RequestParam String customerName) {
		String methodName = "getDocIdFromPackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getDocIdFromPackingList(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PackingListNo Details retrieved successfully");
			responseObjectsMap.put("packingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PackingListNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemNameFromPackingList")
	public ResponseEntity<ResponseDTO> getItemNameFromPackingList(@RequestParam Long orgId,
			@RequestParam String packingListNo, @RequestParam String customerName) {
		String methodName = "getItemNameFromPackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getItemNameFromPackingList(orgId, packingListNo, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemName Details retrieved successfully");
			responseObjectsMap.put("packingListVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getShippingAddressFromPartyMaster")
	public ResponseEntity<ResponseDTO> getShippingAddressFromPartyMaster(@RequestParam Long orgId) {
		String methodName = "getShippingAddressFromPartyMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getShippingAddressFromPartyMaster(orgId);
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

	// SalesReturnLocal

	@GetMapping("/getAllSalesReturnLocalByOrgId")
	public ResponseEntity<ResponseDTO> getAllSalesReturnLocalByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSalesReturnLocalByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SalesReturnLocalVO> salesReturnLocalVO = new ArrayList<>();
		try {
			salesReturnLocalVO = salesService.getAllSalesReturnLocalByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesReturnLocal information get successfully ByOrgId");
			responseObjectsMap.put("salesReturnLocalVO", salesReturnLocalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesReturnLocal information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSalesReturnLocalById")
	public ResponseEntity<ResponseDTO> getSalesReturnLocalById(@RequestParam Long id) {
		String methodName = "getSalesReturnLocalById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SalesReturnLocalVO salesReturnLocalVO = new SalesReturnLocalVO();
		try {
			salesReturnLocalVO = salesService.getSalesReturnLocalById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesReturnLocal information get successfully By id");
			responseObjectsMap.put("salesReturnLocalVO", salesReturnLocalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesReturnLocal information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateSalesReturnLocal")
	public ResponseEntity<ResponseDTO> createUpdateSalesReturnLocal(
			@RequestBody SalesReturnLocalDTO salesReturnLocalDTO) {
		String methodName = "createUpdateSalesReturnLocal()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> salesReturnLocalVO = salesService.createUpdateSalesReturnLocal(salesReturnLocalDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, salesReturnLocalVO.get("message"));
			responseObjectsMap.put("salesReturnLocalVO", salesReturnLocalVO.get("salesReturnLocalVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesReturnLocalDocId")
	public ResponseEntity<ResponseDTO> getSalesReturnLocalDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getSalesReturnLocalDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = salesService.getSalesReturnLocalDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesReturnLocalDocId information retrieved successfully");
			responseObjectsMap.put("salesReturnLocalDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SalesReturnLocalDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesInvoiceNoFromSalesInvoice")
	public ResponseEntity<ResponseDTO> getSalesInvoiceNoFromSalesInvoice(@RequestParam Long orgId,
			@RequestParam String customerName) {
		String methodName = "getSalesInvoiceNoFromSalesInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getSalesInvoiceNoFromSalesInvoice(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SalesInvoiceLocalNo Details retrieved successfully");
			responseObjectsMap.put("salesInvoiceLocalVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SalesInvoiceLocalNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemFromSalesInvoice")
	public ResponseEntity<ResponseDTO> getItemFromSalesInvoice(@RequestParam Long orgId,
			@RequestParam String customerName, @RequestParam String salesInvoiceLocalNo) {
		String methodName = "getItemNameFromPackingList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getItemFromSalesInvoice(orgId, customerName, salesInvoiceLocalNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemName Details retrieved successfully");
			responseObjectsMap.put("salesInvoiceLocalVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SalesReturnLocal

	@GetMapping("/getAllSalesReturnExportByOrgId")
	public ResponseEntity<ResponseDTO> getAllSalesReturnExportByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSalesReturnExportByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SalesReturnExportVO> salesReturnExportVO = new ArrayList<>();
		try {
			salesReturnExportVO = salesService.getAllSalesReturnExportByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesReturnExport information get successfully ByOrgId");
			responseObjectsMap.put("salesReturnExportVO", salesReturnExportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesReturnExport information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSalesReturnExportById")
	public ResponseEntity<ResponseDTO> getSalesReturnExportById(@RequestParam Long id) {
		String methodName = "getSalesReturnExportById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SalesReturnExportVO salesReturnExportVO = new SalesReturnExportVO();
		try {
			salesReturnExportVO = salesService.getSalesReturnExportById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesReturnExport information get successfully By id");
			responseObjectsMap.put("salesReturnExportVO", salesReturnExportVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SalesReturnExport information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateSalesReturnExport")
	public ResponseEntity<ResponseDTO> createUpdateSalesReturnExport(
			@RequestBody SalesReturnExportDTO salesReturnExportDTO) {
		String methodName = "createUpdateSalesReturnExport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> salesReturnExportVO = salesService.createUpdateSalesReturnExport(salesReturnExportDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, salesReturnExportVO.get("message"));
			responseObjectsMap.put("salesReturnExportVO", salesReturnExportVO.get("salesReturnExportVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesReturnExportDocId")
	public ResponseEntity<ResponseDTO> getSalesReturnExportDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getSalesReturnExportDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = salesService.getSalesReturnExportDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesReturnExportDocId information retrieved successfully");
			responseObjectsMap.put("salesReturnExportDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SalesReturnExportDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomerNameFromPartyMasterhExport")
	public ResponseEntity<ResponseDTO> getCustomerNameFromPartyMasterhExport(@RequestParam Long orgId) {
		String methodName = "getCustomerNameFromPartyMasterhExport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getCustomerNameFromPartyMasterhExport(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerName Details retrieved successfully");
			responseObjectsMap.put("partyMaster", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve CustomerName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocIdFromSalesInvoiceExport")
	public ResponseEntity<ResponseDTO> getDocIdFromSalesInvoiceExport(@RequestParam Long orgId,
			@RequestParam String customerName) {
		String methodName = "getDocIdFromSalesInvoiceExport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getDocIdFromSalesInvoiceExport(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesInvoiceExportNo Details retrieved successfully");
			responseObjectsMap.put("salesInvoiceExportVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SalesInvoiceExportNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemFromSalesInvoiceExport")
	public ResponseEntity<ResponseDTO> getItemFromSalesInvoiceExport(@RequestParam Long orgId,
			@RequestParam String customerName, @RequestParam String salesInvoiceLocalNo) {
		String methodName = "getItemFromSalesInvoiceExport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getItemFromSalesInvoiceExport(orgId, customerName, salesInvoiceLocalNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemName Details retrieved successfully");
			responseObjectsMap.put("salesInvoiceExportVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPackListDetails")
	public ResponseEntity<ResponseDTO> getPackListDetails(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String customerName) {
		String methodName = "getPackListDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getPackListDetails(orgId, branchCode, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PickListDetails retrieved successfully");
			responseObjectsMap.put("pickListDetailsVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PickListDetails",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemPackListDetails")
	public ResponseEntity<ResponseDTO> getItemPackListDetails(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String customerName, @RequestParam String packlistNo) {
		String methodName = "getItemPackListDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesService.getItemPackListDetails(orgId, branchCode, customerName, packlistNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemDetails retrieved successfully");
			responseObjectsMap.put("packListDetailsVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemDetails", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Report APIs

	@GetMapping("/getDeliveryChallanForFGReport")
	public ResponseEntity<ResponseDTO> getDeliveryChallanForFGReport(@RequestParam Long orgId,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String saleOrderNo) {

		String methodName = "getDeliveryChallanForFGReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> reportList = salesService.getDeliveryChallanForFGReport(orgId, fromDate, toDate,saleOrderNo);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan For FG Report retrieved successfully");
			responseObjectsMap.put("deliveryChallanForFGReport", reportList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Delivery Challan For FG Report", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSalesInvoiceLocalDetails")
	public ResponseEntity<ResponseDTO> getSalesInvoiceLocalDetails(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate) {
		String methodName = "getSalesInvoiceLocalDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> salesInvoiceLocalVO = new ArrayList<>();
		try {
			salesInvoiceLocalVO = salesService.getSalesInvoiceLocalDetails(orgId, fromdate, todate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales Invoice Local Details get successfully");
			responseObjectsMap.put("salesInvoiceLocalVO", salesInvoiceLocalVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Sales Invoice Local Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
