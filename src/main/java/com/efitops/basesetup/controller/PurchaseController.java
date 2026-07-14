package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.PurchaseEnquiryDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseInvoiceDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseQuotationDTO;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.entity.PurchaseInvoiceVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;
import com.efitops.basesetup.entity.PurchaseQuotationAttachmentVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.service.PurchaseService;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

	@Autowired
	PurchaseService purchaseService;

	@PutMapping("/updateCreatePurchaseIndent")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseIndent(
			@Valid @RequestBody PurchaseIndentDTO purchaseIndentDTO) {
		String methodName = "updateCreatePurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> purchaseIndentVO = purchaseService.updateCreatePurchaseIndent(purchaseIndentDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseIndentVO.get("message"));
			responseObjectsMap.put("purchaseIndentVO", purchaseIndentVO.get("purchaseIndentVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllPurchaseIndentByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseIndentByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllPurchaseIndentByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseIndentVO> purchaseIndentVOs = new ArrayList<>();
		try {
			purchaseIndentVOs = purchaseService.getAllPurchaseIndentByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"purchaseService information get successfully By OrgId");
			responseObjectsMap.put("purchaseIndentVO", purchaseIndentVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseService information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseIndentById")
	public ResponseEntity<ResponseDTO> getPurchaseIndentById(@RequestParam Long id) {
		String methodName = "getPurchaseIndentById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<PurchaseIndentVO> purchaseIndentVO = null;
		try {
			purchaseIndentVO = purchaseService.getPurchaseIndentById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "purchaseService information get successfully By Id");
			responseObjectsMap.put("purchaseIndentVO", purchaseIndentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseService information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getCustomerNameForPurchaseIndent")
	public ResponseEntity<ResponseDTO> getCustomerNameForPurchaseIndent(@RequestParam Long orgId) {
		String methodName = "getCustomerNameForPurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> customerNameList = new ArrayList<>();
		try {
			customerNameList = purchaseService.getCustomerNameForPurchaseIndent(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"CustomerName for PurchaseIndent information get successfully By OrgId");
			responseObjectsMap.put("customerNameList", customerNameList);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CustomerName for PurchaseIndent information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getpurchaseindentavlstock")
	public ResponseEntity<ResponseDTO> getpurchaseindentavlstock(@RequestParam Long orgId, String item) {
		String methodName = "getpurchaseindentavlstock()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> avlstock = new ArrayList<>();
		try {
			avlstock = purchaseService.getpurchaseindentavlstock(orgId, item);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"available stock for PurchaseIndent information get successfully By OrgId");
			responseObjectsMap.put("avlstock", avlstock);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"avlstock for PurchaseIndent information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIndentType")
	public ResponseEntity<ResponseDTO> getIndentType(@RequestParam Long orgId) {
		String methodName = "getCustomerNameForPurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> indentType = new ArrayList<>();
		try {
			indentType = purchaseService.getIndentType(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IndentType for PurchaseIndent  information get successfully");
			responseObjectsMap.put("indentType", indentType);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IndentType for PurchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDepartmentForPurchase")
	public ResponseEntity<ResponseDTO> getDepartmentForPurchase(@RequestParam Long orgId) {
		String methodName = "getDepartmentForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> department = new ArrayList<>();
		try {
			department = purchaseService.getDepartmentForPurchase(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Department for PurchaseIndent information get successfully");
			responseObjectsMap.put("department", department);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Department for PurchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getRequestedByForPurchase")
	public ResponseEntity<ResponseDTO> getRequestedByForPurchase(@RequestParam Long orgId) {
		String methodName = "getRequestedByForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> department = new ArrayList<>();
		try {
			department = purchaseService.getRequestedByForPurchase(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"RequestedBy for PurchaseIndent information get successfully");
			responseObjectsMap.put("RequestedBy", department);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"RequestedBy for PurchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getVerifiedByForPurchase")
	public ResponseEntity<ResponseDTO> getVerifiedByForPurchase(@RequestParam Long orgId) {
		String methodName = "getVerifiedByForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> Employee = new ArrayList<>();
		try {
			Employee = purchaseService.getVerifiedByForPurchase(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"VerifiedBy for PurchaseIndent information get successfully");
			responseObjectsMap.put("VerifiedBy", Employee);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"VerifiedBy for PurchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getBomItemDetailsForPurchase")
	public ResponseEntity<ResponseDTO> getBomItemDetailsForPurchase(@RequestParam Long orgId,
			@RequestParam(required = false) String fgPart) {
		String methodName = "getBomItemDetailsForPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemDetails = new ArrayList<>();
		try {
			itemDetails = purchaseService.getBomItemDetailsForPurchase(orgId, fgPart);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Item for purchaseIndent information get successfully");
			responseObjectsMap.put("itemDetails", itemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Item for purchaseIndent information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getpurchaseIndentDocId")
	public ResponseEntity<ResponseDTO> getpurchaseIndentDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getpurchaseIndentDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getpurchaseIndentDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"purchaseIndent DocId information retrieved successfully");
			responseObjectsMap.put("purchaseIndentDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve purchaseIndent DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWorkOrderNoForPurchaseIndent")
	public ResponseEntity<ResponseDTO> getWorkOrderNoForPurchaseIndent(@RequestParam Long orgId,
			@RequestParam String customerCode) {
		String methodName = "getWorkOrderNoForPurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderNo = new ArrayList<>();
		try {
			workOrderNo = purchaseService.getWorkOrderNoForPurchaseIndent(orgId, customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"WorkOrderNo for PurchaseIndent information get successfully By OrgId");
			responseObjectsMap.put("workOrderNo", workOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"WorkOrderNo for PurchaseIndent information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getWorkOrderDetailsForPurchaseIndent")
	public ResponseEntity<ResponseDTO> getWorkOrderDetailsForPurchaseIndent(@RequestParam Long orgId,
			@RequestParam String workOrderNo) {
		String methodName = "getWorkOrderDetailsForPurchaseIndent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderDtls = new ArrayList<>();
		try {
			workOrderDtls = purchaseService.getWorkOrderDetailsForPurchaseIndent(orgId, workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"workOrderDtls for PurchaseIndent information get successfully By OrgId");
			responseObjectsMap.put("workOrderDtls", workOrderDtls);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"workOrderDtls for PurchaseIndent information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	// PurchaseEnquiry

	@PutMapping("/updateCreatePurchaseEnquiry")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseEnquiry(
			@Valid @RequestBody PurchaseEnquiryDTO purchaseEnquiryDTO) {
		String methodName = "updateCreatePurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> purchaseEnquiryVO = purchaseService.updateCreatePurchaseEnquiry(purchaseEnquiryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseEnquiryVO.get("message"));
			responseObjectsMap.put("purchaseEnquiryVO", purchaseEnquiryVO.get("purchaseEnquiryVO")); // Corrected key
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllPurchaseEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseEnquiryByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllPurchaseEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseEnquiryVO> purchaseEnquiryVO = new ArrayList<>();
		try {
			purchaseEnquiryVO = purchaseService.getAllPurchaseEnquiryByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"purchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("purchaseEnquiryVO", purchaseEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllPurchaseEnquiryById")
	public ResponseEntity<ResponseDTO> getAllPurchaseEnquiryById(@RequestParam Long id) {
		String methodName = "getAllPurchaseEnquiryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<PurchaseEnquiryVO> purchaseEnquiryVO = null;
		try {
			purchaseEnquiryVO = purchaseService.getAllPurchaseEnquiryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseEnquiry information get successfully By id");
			responseObjectsMap.put("purchaseEnquiryVO", purchaseEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseEnquiry information receive failed By id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseEnquiryByDocId")
	public ResponseEntity<ResponseDTO> getPurchaseEnquiryByDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getPurchaseEnquiryByDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getPurchaseEnquiryByDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseEnquiry DocId information retrieved successfully");
			responseObjectsMap.put("purchaseEnquiryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseEnquiry DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSupplierNameForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getSupplierNameForPurchaseEnquiry(@RequestParam Long orgId) {
		String methodName = "getSupplierNameForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> supplierNameList = new ArrayList<>();
		try {
			supplierNameList = purchaseService.getSupplierNameForPurchaseEnquiry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SupplierName for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("SupplierNameList", supplierNameList);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SupplierName for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getContactPersonDetailsForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getContactPersonDetailsForPurchaseEnquiry(@RequestParam Long orgId,
			@RequestParam String supplierCode) {
		String methodName = "getContactPersonDetailsForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> ContactPersonDetails = new ArrayList<>();
		try {
			ContactPersonDetails = purchaseService.getContactPersonDetailsForPurchaseEnquiry(orgId, supplierCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ContactPersonDetails for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("purchaseEnquiryVO", ContactPersonDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ContactPersonDetails for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseIndentNoForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getPurchaseIndentNoForPurchaseEnquiry(@RequestParam Long orgId,
			@RequestParam String customerCode, @RequestParam String workOrderNo) {
		String methodName = "getPurchaseIndentNoForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseIndentNo = new ArrayList<>();
		try {
			purchaseIndentNo = purchaseService.getPurchaseIndentNoForPurchaseEnquiry(orgId, customerCode, workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseIndentNo for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("purchaseIndentNo", purchaseIndentNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseIndentNo for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemDetailsForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getItemDetailsForPurchaseEnquiry(@RequestParam Long orgId,
			@RequestParam String purchaseIndentNo, @RequestParam String fgItem) {
		String methodName = "getItemDetailsForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemDetails = new ArrayList<>();
		try {
			itemDetails = purchaseService.getItemDetailsForPurchaseEnquiry(orgId, purchaseIndentNo, fgItem);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ItemDetails for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("itemDetails", itemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ItemDetails for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getWorkOrderNoForPurchaseEnquiry")
	public ResponseEntity<ResponseDTO> getWorkOrderNoForPurchaseEnquiry(@RequestParam Long orgId,
			@RequestParam String customerCode) {
		String methodName = "getWorkOrderNoForPurchaseEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderNo = new ArrayList<>();
		try {
			workOrderNo = purchaseService.getWorkOrderNoForPurchaseEnquiry(orgId, customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"WorkOrderNo for PurchaseEnquiry information get successfully By OrgId");
			responseObjectsMap.put("workOrderNo", workOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"WorkOrderNo for PurchaseEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

//	@GetMapping("/getWorkOrderDetailsForPurchaseEnquiry")
//	public ResponseEntity<ResponseDTO> getWorkOrderDetailsForPurchaseEnquiry(@RequestParam Long orgId,@RequestParam String workOrderNo) {
//		String methodName = "getWorkOrderDetailsForPurchaseEnquiry()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<Map<String, Object>> workOrderDtls = new ArrayList<>();
//		try {
//			workOrderDtls = purchaseService.getWorkOrderDetailsForPurchaseEnquiry(orgId,workOrderNo);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "workOrderDtls for PurchaseEnquiry information get successfully By OrgId");
//			responseObjectsMap.put("workOrderDtls", workOrderDtls);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"workOrderDtls for PurchaseEnquiry information receive failed By OrgId", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//
//	}
//	
	// PurchaseQuotation

	@GetMapping("/getAllPurchaseQuotationByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseQuotationByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllPurchaseQuotationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseQuotationVO> purchaseQuotationVO = new ArrayList<>();
		try {
			purchaseQuotationVO = purchaseService.getAllPurchaseQuotationByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"purchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("purchaseQuotationVO", purchaseQuotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseQuotationById")
	public ResponseEntity<ResponseDTO> getPurchaseQuotationById(@RequestParam Long id) {
		String methodName = "getPurchaseQuotationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<PurchaseQuotationVO> purchaseQuotationVO = null;
		try {
			purchaseQuotationVO = purchaseService.getPurchaseQuotationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"purchaseQuotation information get successfully By Id");
			responseObjectsMap.put("purchaseQuotationVO", purchaseQuotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"purchaseQuotation information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreatePurchaseQuotation")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseQuotation(
			@Valid @RequestBody PurchaseQuotationDTO purchaseQuotationDTO) {
		String methodName = "updateCreatePurchaseQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			Map<String, Object> purchaseQuotationVO = purchaseService
					.updateCreatePurchaseQuotation(purchaseQuotationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseQuotationVO.get("message"));
			responseObjectsMap.put("purchaseQuotationVO", purchaseQuotationVO.get("purchaseQuotationVO")); // Corrected
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

	@GetMapping("/getpurchaseQuotationDocId")
	public ResponseEntity<ResponseDTO> getpurchaseQuotationDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getpurchaseQuotationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getpurchaseQuotationDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"purchaseQuotation DocId information retrieved successfully");
			responseObjectsMap.put("purchaseQuotationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve purchaseQuotation DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseEnquiryNoForPurchaseQuotation")
	public ResponseEntity<ResponseDTO> getPurchaseEnquiryNoForPurchaseQuotation(@RequestParam Long orgId,
			@RequestParam String customerCode, @RequestParam String workOrderNo) {
		String methodName = "getPurchaseEnquiryNoForPurchaseQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseEnquiryNo = new ArrayList<>();
		try {
			purchaseEnquiryNo = purchaseService.getPurchaseEnquiryNoForPurchaseQuotation(orgId, customerCode,
					workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseEnquiryNo for PurchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("purchaseEnquiryNo", purchaseEnquiryNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseEnquiryNo for PurchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemDetailsForPurchaseQuotation")
	public ResponseEntity<ResponseDTO> getItemDetailsForPurchaseQuotation(@RequestParam Long orgId,
			@RequestParam String purchaseEnquiryNo) {
		String methodName = "getItemDetailsForPurchaseQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemDetails = new ArrayList<>();
		try {
			itemDetails = purchaseService.getItemDetailsForPurchaseQuotation(orgId, purchaseEnquiryNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ItemDetails for PurchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("itemDetails", itemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ItemDetails for PurchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PostMapping("/uploadPurchaseQuatationAttachementsInBloob")
	public ResponseEntity<ResponseDTO> uploadPurchaseQuatationAttachementsInBloob(
			@RequestParam("file") MultipartFile file, @RequestParam Long id) {
		String methodName = "uploadPurchaseQuatationAttachementsInBloob()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PurchaseQuotationAttachmentVO purchaseQuotationAttachmentVO = null;
		try {
			purchaseQuotationAttachmentVO = purchaseService.uploadPurchaseQuatationAttachementsInBloob(file, id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error("Unable To Upload attachements", methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseQuatation Attachments Successfully Upload");
			responseObjectsMap.put("purchaseQuotationAttachmentVO", purchaseQuotationAttachmentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Attachments Upload Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getWorkOrderNoForPurchaseQuotation")
	public ResponseEntity<ResponseDTO> getWorkOrderNoForPurchaseQuotation(@RequestParam Long orgId,
			@RequestParam String customerCode) {
		String methodName = "getWorkOrderNoForPurchaseQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderNo = new ArrayList<>();
		try {
			workOrderNo = purchaseService.getWorkOrderNoForPurchaseQuotation(orgId, customerCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"WorkOrderNo for PurchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("workOrderNo", workOrderNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"WorkOrderNo for PurchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getWorkOrderDetailsForPurchaseQuotation")
	public ResponseEntity<ResponseDTO> getWorkOrderDetailsForPurchaseQuotation(@RequestParam Long orgId,
			@RequestParam String workOrderNo) {
		String methodName = "getWorkOrderDetailsForPurchaseQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> workOrderDtls = new ArrayList<>();
		try {
			workOrderDtls = purchaseService.getWorkOrderDetailsForPurchaseQuotation(orgId, workOrderNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"workOrderDtls for PurchaseQuotation information get successfully By OrgId");
			responseObjectsMap.put("workOrderDtls", workOrderDtls);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"workOrderDtls for PurchaseQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/findByTaxCode")
	public ResponseEntity<ResponseDTO> findByTaxCode(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String supplierCode, @RequestParam String partyType) {
		String methodName = "findByTaxCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.findByTaxCode(orgId, branchCode, supplierCode, partyType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "TaxCode Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve TaxCode Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByUnitForPurchaseQuatation")
	public ResponseEntity<ResponseDTO> findByUnitForPurchaseQuatation(@RequestParam Long orgId,
			@RequestParam String itemName) {
		String methodName = "findByUnitForPurchaseQuatation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.findByUnitForPurchaseQuatation(orgId, itemName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit Details retrieved successfully");
			responseObjectsMap.put("itemVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Unit Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// PurchaseReturn

	@GetMapping("/getAllPurchaseReturnByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseReturnByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, String branchCode) {
		String methodName = "getAllPurchaseReturnByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseReturnVO> purchaseReturnVO = new ArrayList<>();
		try {
			purchaseReturnVO = purchaseService.getAllPurchaseReturnByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseReturn information get successfully ByOrgId");
			responseObjectsMap.put("purchaseReturnVO", purchaseReturnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseReturn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseReturnById")
	public ResponseEntity<ResponseDTO> getPurchaseReturnById(@RequestParam Long id) {
		String methodName = "getPurchaseReturnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PurchaseReturnVO purchaseReturnVO = new PurchaseReturnVO();
		try {
			purchaseReturnVO = purchaseService.getPurchaseReturnById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseReturn information get successfully By id");
			responseObjectsMap.put("purchaseReturnVO", purchaseReturnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseReturn information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdatePurchaseReturn")
	public ResponseEntity<ResponseDTO> createUpdatePurchaseReturn(@RequestBody PurchaseReturnDTO purchaseReturnDTO) {
		String methodName = "createUpdatePurchaseReturn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> purchaseReturnVO = purchaseService.createUpdatePurchaseReturn(purchaseReturnDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseReturnVO.get("message"));
			responseObjectsMap.put("purchaseReturnVO", purchaseReturnVO.get("purchaseReturnVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseReturnDocId")
	public ResponseEntity<ResponseDTO> getPurchaseReturnDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getPurchaseReturnDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getPurchaseReturnDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"purchaseReturnDocId information retrieved successfully");
			responseObjectsMap.put("purchaseReturnDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve purchaseReturnDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseInvoiceNumberFromPurchaseInvoice")
	public ResponseEntity<ResponseDTO> getPurchaseInvoiceNumberFromPurchaseInvoice(@RequestParam Long orgId,
			@RequestParam String supplierCode) {
		String methodName = "getPurchaseInvoiceNumberFromPurchaseInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getPurchaseInvoiceNumberFromPurchaseInvoice(orgId, supplierCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseInvoiceNumber Details retrieved successfully");
			responseObjectsMap.put("purchaseInvoiceVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseInvoiceNumber Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getLocationFromStockLocation")
	public ResponseEntity<ResponseDTO> getLocationFromStockLocation(@RequestParam Long orgId) {
		String methodName = "getLocationFromStockLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getLocationFromStockLocation(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "location Details retrieved successfully");
			responseObjectsMap.put("stockLocationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve location Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemCodeAndItemDescFromPurchsaeInvoice")
	public ResponseEntity<ResponseDTO> getItemCodeAndItemDescFromPurchsaeInvoice(@RequestParam Long orgId,
			@RequestParam String purchaseInvoiceNo) {
		String methodName = "getItemCodeAndItemDescFromPurchsaeInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getItemCodeAndItemDescFromPurchsaeInvoice(orgId, purchaseInvoiceNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemCode Details retrieved successfully");
			responseObjectsMap.put("purchaseInvoiceVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemCode Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// PurchaseInvoice

	@GetMapping("/getAllPurchaseInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getAllPurchaseInvoiceByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllPurchaseInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseInvoiceVO> purchaseInvoiceVO = new ArrayList<>();
		try {
			purchaseInvoiceVO = purchaseService.getAllPurchaseInvoiceByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseInvoice information get successfully ByOrgId");
			responseObjectsMap.put("purchaseInvoiceVO", purchaseInvoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseInvoice information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseInvoiceById")
	public ResponseEntity<ResponseDTO> getPurchaseInvoiceById(@RequestParam Long id) {
		String methodName = "getPurchaseInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PurchaseInvoiceVO purchaseInvoiceVO = new PurchaseInvoiceVO();
		try {
			purchaseInvoiceVO = purchaseService.getPurchaseInvoiceById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseInvoice information get successfully By id");
			responseObjectsMap.put("purchaseInvoiceVO", purchaseInvoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseInvoice information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdatePurchaseInvoice")
	public ResponseEntity<ResponseDTO> createUpdatePurchaseInvoice(@RequestBody PurchaseInvoiceDTO purchaseInvoiceDTO) {
		String methodName = "createUpdatePurchaseInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> purchaseInvoiceVO = purchaseService.createUpdatePurchaseInvoice(purchaseInvoiceDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseInvoiceVO.get("message"));
			responseObjectsMap.put("purchaseInvoiceVO", purchaseInvoiceVO.get("purchaseInvoiceVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseInvoiceDocId")
	public ResponseEntity<ResponseDTO> getPurchaseInvoiceDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getPurchaseInvoiceDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getPurchaseInvoiceDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseInvoiceDocId information retrieved successfully");
			responseObjectsMap.put("purchaseInvoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseInvoiceDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderPoNumber")
	public ResponseEntity<ResponseDTO> getPurchaseOrderPoNumber(@RequestParam Long orgId,
			@RequestParam String supplierCode) {
		String methodName = "getPurchaseOrderPoNumber()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getPurchaseOrderPoNumber(orgId, supplierCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PoNo Details retrieved successfully");
			responseObjectsMap.put("purchaseOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PoNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoAndGrnDateFromGrnDetails")
	public ResponseEntity<ResponseDTO> getGrnNoAndGrnDateFromGrnDetails(@RequestParam Long orgId,
			@RequestParam String poNo) {
		String methodName = "getGrnNoAndGrnDateFromGrnDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getGrnNoAndGrnDateFromGrnDetails(orgId, poNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnNo Details retrieved successfully");
			responseObjectsMap.put("grnVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemCodeAndItemDescFromGrn")
	public ResponseEntity<ResponseDTO> getItemCodeAndItemDescFromGrn(@RequestParam Long orgId,
			@RequestParam String grnNo) {
		String methodName = "getItemCodeAndItemDescFromGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getItemCodeAndItemDescFromGrn(orgId, grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemCode Details retrieved successfully");
			responseObjectsMap.put("grnVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ItemCode Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPoDetailsId")
	public ResponseEntity<ResponseDTO> getPoDetailsId(@RequestParam String docId, @RequestParam String item,
			@RequestParam Long orgId) {
		String methodName = "getPoDetailsId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getPoDetailsId(docId, item, orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PoDetailsId Details retrieved successfully");
			responseObjectsMap.put("purchaseOrderDetailsVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PoDetailsId Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Purchase Order

	@GetMapping("/getPurchaseOrderByOrgId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getPurchaseOrderByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseOrderVO> purchaseOrderVO = new ArrayList<>();
		try {
			purchaseOrderVO = purchaseService.getPurchaseOrderByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Order information get successfully ByOrgId");
			responseObjectsMap.put("purchaseOrderVO", purchaseOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Purchase Order  information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderById")
	public ResponseEntity<ResponseDTO> getPurchaseOrderById(@RequestParam Long id) {
		String methodName = "getPurchaseOrderById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PurchaseOrderVO> purchaseOrderVO = new ArrayList<>();
		try {
			purchaseOrderVO = purchaseService.getPurchaseOrderById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Order information get successfully By Id");
			responseObjectsMap.put("purchaseOrderVO", purchaseOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Purchase Order information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseOrderDocId")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getPurchaseOrderDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getPurchaseOrderDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Purchase Order DocId information retrieved successfully");
			responseObjectsMap.put("poNo", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Purchase Order DocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreatePurchaseOrder")
	public ResponseEntity<ResponseDTO> updateCreatePurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
		String methodName = "updateCreatePurchaseOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> purchaseOrderVO = purchaseService.updateCreatePurchaseOrder(purchaseOrderDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, purchaseOrderVO.get("message"));
			responseObjectsMap.put("purchaseOrderVO", purchaseOrderVO.get("purchaseOrderVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/findByIgstAndSgstPercentageForPurchaseQrder")
	public ResponseEntity<ResponseDTO> findByIgstAndSgstPercentageForPurchaseQrder(@RequestParam Long orgId,
			@RequestParam String taxType, @RequestParam String taxCode) {
		String methodName = "findByIgstAndSgstPercentageForPurchaseQrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.findByIgstAndSgstPercentageForPurchaseQrder(orgId, taxType, taxCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Gst Details retrieved successfully");
			responseObjectsMap.put("gstVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Unit Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSupplierAddressForPurchaseOrder")
	public ResponseEntity<ResponseDTO> getSupplierAddressForPurchaseOrder(@RequestParam(required = false) Long orgId,
			String supplierName) {
		String methodName = "getSupplierAddressForPurchaseOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chCode = new ArrayList<>();
		try {
			chCode = purchaseService.getSupplierAddressForPurchaseOrder(orgId, supplierName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" supplier contact and Address information get successfully By OrgId");
			responseObjectsMap.put("chCode", chCode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"supplier contact and Address information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseIndentForPurchaseOrder")
	public ResponseEntity<ResponseDTO> getPurchaseIndentForPurchaseOrder(@RequestParam Long orgId,
			@RequestParam(required = false) String customerCode, @RequestParam(required = false) String workorderno,
			@RequestParam String basedOn) {

		String methodName = "getPurchaseIndentForPurchaseOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> chCode = purchaseService.getPurchaseIndentForPurchaseOrder(orgId, customerCode,
					workorderno, basedOn);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Purchase Indent information retrieved successfully");
			responseObjectsMap.put("chCode", chCode);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			LOGGER.error("Error in {}", methodName, e);

			responseDTO = createServiceResponseError(responseObjectsMap, "Purchase Indent information receive failed",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getQuotationForPurchaseOrder")
	public ResponseEntity<ResponseDTO> getQuotationForPurchaseOrder(@RequestParam(required = false) Long orgId,
			String customerCode, String workorderno, String basedOn) {
		String methodName = "getQuotationForPurchaseOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chCode = new ArrayList<>();
		try {
			chCode = purchaseService.getQuotationForPurchaseOrder(orgId, customerCode, workorderno, basedOn);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " Quotation information get successfully By OrgId");
			responseObjectsMap.put("chCode", chCode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Quotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemForPurchaseOrder")
	public ResponseEntity<ResponseDTO> getItemForPurchaseOrder(@RequestParam Long orgId,
			@RequestParam(required = false) String purchaseIndentNo,
			@RequestParam(required = false) String quotationNo) {
		String methodName = "getItemForPurchaseOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chCode = new ArrayList<>();
		try {
			chCode = purchaseService.getItemForPurchaseOrder(orgId, purchaseIndentNo, quotationNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " PO Item information get successfully By OrgId");
			responseObjectsMap.put("chCode", chCode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PO Item information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseIndentReport")
	public ResponseEntity<ResponseDTO> getPurchaseIndentReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String customerName, @RequestParam String status,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		String methodName = "getPurchaseIndentReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getPurchaseIndentReport(orgId, branchCode, customerName, status, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseIndentReport Details retrieved successfully");
			responseObjectsMap.put("quotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseIndentReport Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseEnquiryReport")
	public ResponseEntity<ResponseDTO> getPurchaseEnquiryReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String supplierName, @RequestParam String status,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		String methodName = "getPurchaseEnquiryReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getPurchaseEnquiryReport(orgId, branchCode, supplierName, status, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseEnquiryReport Details retrieved successfully");
			responseObjectsMap.put("quotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseIndentReport Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseReturnReport")
	public ResponseEntity<ResponseDTO> getPurchaseReturnReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String finYear, @RequestParam String supplierName,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		String methodName = "getPurchaseReturnReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getPurchaseReturnReport(orgId, branchCode, finYear, supplierName, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseReturnReport Details retrieved successfully");
			responseObjectsMap.put("quotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseReturnReport Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderDetails")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDetails(@RequestParam Long orgId,
			@RequestParam String supplierName, @RequestParam String status) {
		String methodName = "getPurchaseOrderDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseOrderVO = new ArrayList<>();
		try {
			purchaseOrderVO = purchaseService.getPurchaseOrderDetails(orgId, supplierName, status);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseOrder Details get successfully");
			responseObjectsMap.put("purchaseOrderVO", purchaseOrderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PurchaseOrder Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseEnquiryIdIteration")
	public ResponseEntity<ResponseDTO> getPurchaseEnquiryIdIteration(@RequestParam Long orgId,
			@RequestParam String clientName, @RequestParam String enquiryNo) {

		String methodName = "getPurchaseEnquiryIdIteration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = purchaseService.getPurchaseEnquiryIdIteration(orgId, clientName, enquiryNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "IterationId information retrieved successfully");
			responseObjectsMap.put("iterationId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve IterationId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseQuotationDetailsReport")
	public ResponseEntity<ResponseDTO> getPurchaseQuotationDetailsReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String supplierName,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		String methodName = "getPurchaseQuotationDetailsReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = purchaseService.getPurchaseQuotationDetailsReport(orgId, branchCode, supplierName, fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseQuotationDetails retrieved successfully");
			responseObjectsMap.put("purchaseQuotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PurchaseQuotationDetails ",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseInvoiceDetails")
	public ResponseEntity<ResponseDTO> getPurchaseInvoiceDetails(@RequestParam Long orgId,
			@RequestParam String supplierName, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam String branchCode) {
		String methodName = "getPurchaseInvoiceDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseInvoiceDetails = new ArrayList<>();
		try {
			purchaseInvoiceDetails = purchaseService.getPurchaseInvoiceDetails(orgId, supplierName, fromDate, toDate,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PurchaseInvoiceDetails get successfully By OrgId");
			responseObjectsMap.put("purchaseInvoiceDetails", purchaseInvoiceDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseInvoiceDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPurchaseInvoiceSummaryDetails")
	public ResponseEntity<ResponseDTO> getGrnSummaryDetails(@RequestParam Long orgId, @RequestParam String supplierName,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam String branchCode) {
		String methodName = "getPurchaseInvoiceSummaryDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> purchaseInvoiceSummaryDetails = new ArrayList<>();
		try {
			purchaseInvoiceSummaryDetails = purchaseService.getPurchaseInvoiceSummaryDetails(orgId, supplierName,
					fromDate, toDate, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PurchaseInvoiceSummaryDetails get successfully By OrgId");
			responseObjectsMap.put("purchaseInvoiceSummaryDetails", purchaseInvoiceSummaryDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PurchaseInvoiceSummaryDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	@PostMapping(value = "/createUpdatePurchaseImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdatePurchaseImages(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module, @RequestParam List<String> fileNames) {

		String methodName = "createUpdatePurchaseImages()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = purchaseService.createUpdatePurchaseImages(files, docId, screenName,
					module, fileNames);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("thirdPartyInspectionVO", serviceResponse.get("thirdPartyInspectionVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/viewPurchaseQuotationImages/**")
	public ResponseEntity<byte[]> viewPurchaseQuotationImages(HttpServletRequest request) throws IOException {
		return purchaseService.viewPurchaseQuotationImages(request);
	}


}
