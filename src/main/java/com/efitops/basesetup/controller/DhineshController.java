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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesContractDTO;
import com.efitops.basesetup.service.DhineshService;

@CrossOrigin
@RestController
@RequestMapping("/api/dhinesh")
public class DhineshController extends BaseController {

	@Autowired
	DhineshService dhineshService;

	public static final Logger LOGGER = LoggerFactory.getLogger(DhineshController.class);

	@PostMapping(value = "/createUpdateSalesContract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateSalesContract(
			@RequestPart("salesContract") SalesContractDTO salesContractDTO,
//	        @RequestBody SalesContractDTO salesContractDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		String methodName = "createUpdateSalesContract()";

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> createdSalesContractVO = dhineshService.createUpdateSalesContract(salesContractDTO,
					files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdSalesContractVO.get("message"));

			responseObjectsMap.put("salesContractVO", createdSalesContractVO.get("salesContract"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getFinishedGoodsItemsbySalesContract")
	public ResponseEntity<ResponseDTO> getFinishedGoodsItems(@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getFinishedGoodsItems()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<SalesContractItemDropdownResponseDTO> itemList = new ArrayList<>();

		try {

			itemList = dhineshService.getFinishedGoodsItems(orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

		}

		if (errorMsg == null || errorMsg.trim().isEmpty()) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Finished Goods Items fetched successfully");
			responseObjectsMap.put("items", itemList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Finished Goods Items fetch failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getQuotationDropdownbySalesContract")
	public ResponseEntity<ResponseDTO> getQuotationDropdown(@RequestParam String customerCode,
			@RequestParam String ctype, @RequestParam Long orgId, @RequestParam Long branch,
			@RequestParam(required = false, defaultValue = "") String oldQuotationNo,
			@RequestParam(required = false, defaultValue = "0") Long recId) {

		String methodName = "getQuotationDropdown()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<QuotationDropdownResponseDTO> quotationList = new ArrayList<>();

		try {

			quotationList = dhineshService.getQuotationDropdown(customerCode, ctype, orgId, branch, oldQuotationNo,
					recId);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

		}

		if (errorMsg == null || errorMsg.trim().isEmpty()) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation details fetched successfully");

			responseObjectsMap.put("quotations", quotationList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Quotation details fetch failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCustomerDropdownbySalesContract")
	public ResponseEntity<ResponseDTO> getCustomerDropdown(@RequestParam String ctype, @RequestParam Long orgId,
			@RequestParam Long branch) {

		String methodName = "getCustomerDropdown()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<CustomerDropdownResponseDTO> customerList = new ArrayList<>();

		try {

			customerList = dhineshService.getCustomerDropdown(ctype, orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

		}

		if (errorMsg == null || errorMsg.trim().isEmpty()) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer Details fetched successfully");
			responseObjectsMap.put("customers", customerList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Customer Details fetch failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getQuotationItemDropdownbySalesContract")
	public ResponseEntity<ResponseDTO> getQuotationItemDropdown(@RequestParam String quotationNo,
			@RequestParam Long orgId, @RequestParam Long branch) {

		String methodName = "getQuotationItemDropdown()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		List<QuotationItemDropdownResponseDTO> itemList = new ArrayList<>();

		try {

			itemList = dhineshService.getQuotationItemDropdown(quotationNo, orgId, branch);

		} catch (Exception e) {

			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (errorMsg == null || errorMsg.trim().isEmpty()) {

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Quotation Item Details fetched successfully");

			responseObjectsMap.put("items", itemList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} else {

			responseDTO = createServiceResponseError(responseObjectsMap, "Quotation Item Details fetch failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesContractById")
	public ResponseEntity<ResponseDTO> getSalesContractById(@RequestParam Long id) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			SalesContractResponseDTO salesContract = dhineshService.getSalesContractById(id);

			responseObjectsMap.put("salesContract", salesContract);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales Contract fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSalesContractByOrgIdAndBranch")
	public ResponseEntity<ResponseDTO> getSalesContractByOrgIdAndBranch(@RequestParam Long orgId,
			@RequestParam Long branch) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			List<SalesContractResponseDTO> salesContracts = dhineshService.getSalesContractByOrgIdAndBranch(orgId,
					branch);

			responseObjectsMap.put("salesContract", salesContracts);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales Contract List fetched successfully");

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return ResponseEntity.ok(responseDTO);
	}
	
	@GetMapping("/getSalesContractDocId")
	public ResponseEntity<ResponseDTO> getSalesContractDocId(@RequestParam Long orgId,
			@RequestParam String financialYear, @RequestParam String screenCode) {

		String methodName = "getQuotationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = dhineshService.getSalesContractDocId(orgId, financialYear, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesContract DocId information retrieved successfully");
			responseObjectsMap.put("invoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Proforma SalesContract	 DocId",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
