package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SubContractGrnDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.SubContractGrnVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.repo.GrnRepo;
import com.efitops.basesetup.service.GrnService;

@CrossOrigin
@RestController
@RequestMapping("/api/grn")
public class GrnController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(GrnController.class);

	@Autowired
	GrnService grnService;

	@Autowired
	GrnRepo grnRepo;

	@GetMapping("/getGrnByOrgId")
	public ResponseEntity<ResponseDTO> getGrnByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getGrnByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GrnVO> grnVO = new ArrayList<>();
		try {
			grnVO = grnService.getGrnByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn information get successfully ByOrgId");
			responseObjectsMap.put("grnVO", grnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGrnById")
	public ResponseEntity<ResponseDTO> getItemById(@RequestParam Long id) {
		String methodName = "getGrnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GrnVO> grnVO = new ArrayList<>();
		try {
			grnVO = grnService.getGrnById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item information get successfully By Id");
			responseObjectsMap.put("grnVO", grnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Item information receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateGrn")
	public ResponseEntity<ResponseDTO> updateCreateGrn(@RequestBody GrnDTO grnDTO) {
		String methodName = "updateCreateGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> grnVO = grnService.updateCreateGrn(grnDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, grnVO.get("message"));
			responseObjectsMap.put("grnVO", grnVO.get("grnVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnDocId")
	public ResponseEntity<ResponseDTO> getGrnDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getGrnDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = grnService.getGrnDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnDocId information retrieved successfully");
			responseObjectsMap.put("grnDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getInwardNoForGRN")
	public ResponseEntity<ResponseDTO> getInwardNoForGRN(@RequestParam(required = false) Long orgId) {
		String methodName = "getInwardNoForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> inwardforgrn = new ArrayList<>();
		try {
			inwardforgrn = grnService.getInwardNoForGRN(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" Inward for Grn information get successfully By OrgId");
			responseObjectsMap.put("inwardforgrn", inwardforgrn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Inward for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSupplierAddressForGRN")
	public ResponseEntity<ResponseDTO> getSupplierAddressForGRN(@RequestParam(required = false) Long orgId,
			String supplierName) {
		String methodName = "getSupplierAddressForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> supplieraddressforgrn = new ArrayList<>();
		try {
			supplieraddressforgrn = grnService.getSupplierAddressForGRN(orgId, supplierName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" supplieraddress for Grn information get successfully By OrgId");
			responseObjectsMap.put("supplieraddressforgrn", supplieraddressforgrn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"supplieraddress For Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGrnavlstock")
	public ResponseEntity<ResponseDTO> getGrnavlstock(@RequestParam(required = false) Long orgId, String itemCode) {
		String methodName = "getGrnavlstock()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockavl = new ArrayList<>();
		try {
			stockavl = grnService.getGrnavlstock(orgId, itemCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" available stock for Grn information get successfully By OrgId");
			responseObjectsMap.put("stockavl", stockavl);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"available stock For Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSGSTandCGSTForGRN")
	public ResponseEntity<ResponseDTO> getSGSTandCGSTForGRN(@RequestParam(required = false) Long orgId, String taxType,
			String gstType) {
		String methodName = "getSGSTandCGSTForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cgstandsgst = new ArrayList<>();
		try {
			cgstandsgst = grnService.getSGSTandCGSTForGRN(orgId, taxType, gstType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" CGST and SGST for Grn information get successfully By OrgId");
			responseObjectsMap.put("cgstandsgst", cgstandsgst);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CGST and SGST for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIGSTForGRN")
	public ResponseEntity<ResponseDTO> getIGSTForGRN(@RequestParam(required = false) Long orgId, String taxType,
			String gstType) {
		String methodName = "getIGSTForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> igst = new ArrayList<>();
		try {
			igst = grnService.getIGSTForGRN(orgId, taxType, gstType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" IGST for Grn information get successfully By OrgId");
			responseObjectsMap.put("igst", igst);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IGST for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getItemForGRN")
	public ResponseEntity<ResponseDTO> getItemForGRN(@RequestParam(required = false) Long orgId, String inwardNo) {
		String methodName = "getItemForGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> itemforgrn = new ArrayList<>();
		try {
			itemforgrn = grnService.getItemForGRN(orgId, inwardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" itemforgrn for Grn information get successfully By OrgId");
			responseObjectsMap.put("itemforgrn", itemforgrn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"itemforgrn for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getThirdPartyInspectionByOrgId")
	public ResponseEntity<ResponseDTO> getThirdPartyInspectionByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getThirdPartyInspectionByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
		try {
			thirdPartyInspectionVO = grnService.getThirdPartyInspByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Third Party information get successfully ByOrgId");
			responseObjectsMap.put("thirdPartyInspectionVO", thirdPartyInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Third Party information information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/findGRNForThirdPartyInspDetails")
	public ResponseEntity<ResponseDTO> findGRNForThirdPartyInspDetails(@RequestParam(required = false) Long orgId) {
		String methodName = "findGRNForThirdPartyInspDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chCode = new ArrayList<>();
		try {
			chCode = grnService.findGRNForThirdPartyInspDetails(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" GRNForThirdPartyInsp for Grn information get successfully By OrgId");
			responseObjectsMap.put("chCode", chCode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GRNForThirdPartyInsp for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getThirdPartyDetailsForThirdPartyInsp")
	public ResponseEntity<ResponseDTO> getThirdPartyDetailsForThirdPartyInsp(
			@RequestParam(required = false) Long orgId) {
		String methodName = "getThirdPartyDetailsForThirdPartyInsp()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> chCode = new ArrayList<>();
		try {
			chCode = grnService.getThirdPartyDetailsForThirdPartyInsp(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" ThirdPartyDetails for Grn information get successfully By OrgId");
			responseObjectsMap.put("chCode", chCode);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ThirdPartyDetails for Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getThirdPartyInspectionById")
	public ResponseEntity<ResponseDTO> getThirdPartyInspectionById(@RequestParam Long id) {
		String methodName = "getThirdPartyInspectionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
		try {
			thirdPartyInspectionVO = grnService.getThirdPartyInspById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ThirdPartyInspection information get successfully By Id");
			responseObjectsMap.put("thirdPartyInspectionVO", thirdPartyInspectionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ThirdPartyInspection information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateThirdPartyInsp")
	public ResponseEntity<ResponseDTO> updateCreateThirdPartyInsp(
			@RequestBody ThirdPartyInspectionDTO thirdPartyInspectionDTO) {
		String methodName = "updateCreateThirdPartyInsp()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> thirdPartyInspectionVO = grnService.updateCreateThirdPartyInsp(thirdPartyInspectionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, thirdPartyInspectionVO.get("message"));
			responseObjectsMap.put("thirdPartyInspectionVO", thirdPartyInspectionVO.get("thirdPartyInspectionVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getThirdPartyInspectionDocId")
	public ResponseEntity<ResponseDTO> getThirdPartyInspectionDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getThirdPartyInspectionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = grnService.getThirdPartyInspectionDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"ThirdPartyInspectionDocId information retrieved successfully");
			responseObjectsMap.put("thirdPartyInspectionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ThirdPartyInspectionDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//	@PostMapping(value = "/uploadFileForThirdPartyInspection", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<Map<String, Object>> uploadImages(@RequestParam("thirdPartyId") Long thirdPartyId,
//			@RequestParam("attachmentId") Long attachmentId, @RequestParam("files") List<MultipartFile> files)
//			throws IOException {
//
//		ThirdPartyAttachmentDTO dto = new ThirdPartyAttachmentDTO();
//		dto.setAttachmentId(attachmentId);
//		dto.setFiles(files.toArray(new MultipartFile[0]));
//
//		return ResponseEntity.ok(grnService.uploadFileForThirdPartyInspection(thirdPartyId, dto));
//	}

	@GetMapping("/getAvailableStock")
	public ResponseEntity<ResponseDTO> getAvailableStock(@RequestParam(required = false) Long orgId,
			@RequestParam String branchCode, @RequestParam String location, @RequestParam String itemCode) {
		String methodName = "getAvailableStock()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockavl = new ArrayList<>();
		try {
			stockavl = grnService.getAvailableStock(orgId, branchCode, location, itemCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" available stock for Grn information get successfully By OrgId");
			responseObjectsMap.put("stockavl", stockavl);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"available stock For Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGrnItemDetails")
	public ResponseEntity<ResponseDTO> getGrnItemDetails(@RequestParam Long orgId, @RequestParam String grnNo) {
		String methodName = "getGrnItemDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> grnDetails = new ArrayList<>();
		try {
			grnDetails = grnService.getGrnItemDetails(orgId, grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnDetails  information get successfully");
			responseObjectsMap.put("grnDetails", grnDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GrnDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getRemainingBalanceQty")
	public ResponseEntity<ResponseDTO> getRemainingBalanceQty(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String purchaseOrderNo, @RequestParam String itemCode) {
		String methodName = "getRemainingBalanceQty()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> remainingQty = new ArrayList<>();
		try {
			remainingQty = grnService.getRemainingBalanceQty(orgId, branchCode, purchaseOrderNo, itemCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"RemainingQty for Grn information get successfully By OrgId");
			responseObjectsMap.put("remainingQty", remainingQty);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"RemainingQty stock For Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllShowsAvalibaleqty")
	public ResponseEntity<ResponseDTO> getAllShowsAvalibaleqty(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String location, @RequestParam String itemCode) {
		String methodName = "getAllShowsAvalibaleqty()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> avalibaleQty = new ArrayList<>();
		try {
			avalibaleQty = grnService.getAllShowsAvalibaleqty(orgId, branchCode, location, itemCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"RemainingQty for Grn information get successfully By OrgId");
			responseObjectsMap.put("avalibaleQty", avalibaleQty);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"RemainingQty stock For Grn information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGrnDetails")
	public ResponseEntity<ResponseDTO> getGrnDetails(@RequestParam Long orgId, @RequestParam String supplierName,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam String branchCode) {
		String methodName = "getGrnDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> grnDetails = new ArrayList<>();
		try {
			grnDetails = grnService.getGrnDetails(orgId, supplierName, fromDate, toDate, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnDetails get successfully By OrgId");
			responseObjectsMap.put("grnDetails", grnDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GrnDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getGrnSummaryDetails")
	public ResponseEntity<ResponseDTO> getGrnSummaryDetails(@RequestParam Long orgId, @RequestParam String supplierName,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam String branchCode) {
		String methodName = "getGrnSummaryDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> grnSummaryDetails = new ArrayList<>();
		try {
			grnSummaryDetails = grnService.getGrnSummaryDetails(orgId, supplierName, fromDate, toDate, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnSummaryDetails get successfully By OrgId");
			responseObjectsMap.put("grnSummaryDetails", grnSummaryDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"GrnSummaryDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getThirdPartyInspectionReport")
	public ResponseEntity<ResponseDTO> getThirdPartyInspectionReport(@RequestParam Long orgId,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam String partyName) {

		String methodName = "getThirdPartyInspectionReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> reportList = grnService.getThirdPartyInspectionReport(orgId, fromDate, toDate,
					partyName);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Third Party Inspection Report retrieved successfully");
			responseObjectsMap.put("thirdPartyInspectionReport", reportList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Third Party Inspection Report", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSupplierName")
	public ResponseEntity<ResponseDTO> getSupplierName(@RequestParam Long orgId) {

		String methodName = "getSupplierName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> reportList = grnService.getSupplierName(orgId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SupplierName retrieved successfully");
			responseObjectsMap.put("thirdPartyInspectionReport", reportList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SupplierName",
					e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	// SubContractGrnVO

	@GetMapping("/getAllSubContractGrnByOrgId")
	public ResponseEntity<ResponseDTO> getAllSubContractGrnByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSubContractGrnByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractGrnVO> subContractGrnVO = new ArrayList<>();
		try {
			subContractGrnVO = grnService.getAllSubContractGrnByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractGrn information get successfully ByOrgId");
			responseObjectsMap.put("subContractGrnVO", subContractGrnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "SubContractGrn receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractGrnById")
	public ResponseEntity<ResponseDTO> getSubContractGrnById(@RequestParam Long id) {
		String methodName = "getSubContractById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SubContractGrnVO subContractGrnVO = new SubContractGrnVO();
		try {
			subContractGrnVO = grnService.getSubContractGrnById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SubContractGrn information get successfully By Id");
			responseObjectsMap.put("subContractGrnVO", subContractGrnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "SubContractGrn receive failed By Id",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateSubContractGrn")
	public ResponseEntity<ResponseDTO> updateCreateSubContractGrn(@RequestBody SubContractGrnDTO subContractGrnDTO) {
		String methodName = "updateCreateSubContractGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> subContractGrnVO = grnService.updateCreateSubContractGrn(subContractGrnDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, subContractGrnVO.get("message"));
			responseObjectsMap.put("subContractGrnVO", subContractGrnVO.get("subContractGrnVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractGrnDocId")
	public ResponseEntity<ResponseDTO> getSubContractGrnDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getSubContractGrnDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = grnService.getSubContractGrnDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractGrnDocId information retrieved successfully");
			responseObjectsMap.put("subContractGrnDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractGrnDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobWorkOutOrderFromSubContractDetails")
	public ResponseEntity<ResponseDTO> getJobWorkOutOrderFromSubContractDetails(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String JobWorkOutOrder) {
		String methodName = "getJobWorkOutOrderFromSubContractDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> jobWorkOutOrderDetails = new ArrayList<>();
		try {
			jobWorkOutOrderDetails = grnService.getJobWorkOutOrderFromSubContractDetails(orgId, branchCode,
					JobWorkOutOrder);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobWorkOutOrderDetails get successfully");
			responseObjectsMap.put("jobWorkOutOrderDetails", jobWorkOutOrderDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "JobWorkOutOrderDetails  receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobWorkOutOrderFromSubContractItemDetails")
	public ResponseEntity<ResponseDTO> getJobWorkOutOrderFromSubContractItemDetails(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String jobWorkOutOrderNumber) {
		String methodName = "getJobWorkOutOrderFromSubContractItemDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dcFromSubContractItemDetails = new ArrayList<>();
		try {
			dcFromSubContractItemDetails = grnService.getJobWorkOutOrderFromSubContractItemDetails(orgId, branchCode,
					jobWorkOutOrderNumber);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DcFromSubContractItemDetails get successfully");
			responseObjectsMap.put("dcFromSubContractItemDetails", dcFromSubContractItemDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DcFromSubContractItemDetails  receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobWorkOutOrderDocId")
	public ResponseEntity<ResponseDTO> getJobWorkOutOrderDocId(@RequestParam Long orgId) {
		String methodName = "getJobWorkOutOrderDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> jobWorkOutOrderDocId = new ArrayList<>();
		try {
			jobWorkOutOrderDocId = grnService.getJobWorkOutOrderDocId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DcFromSubContractDocId get successfully");
			responseObjectsMap.put("jobWorkOutOrderDocId", jobWorkOutOrderDocId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DcFromSubContractDocId Details receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getThirdPartyNamesFromPartyMaster")
	public ResponseEntity<ResponseDTO> getThirdPartyNamesFromPartyMaster(@RequestParam Long orgId) {
		String methodName = "getThirdPartyNamesFromPartyMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> partyName = new ArrayList<>();
		try {
			partyName = grnService.getThirdPartyNamesFromPartyMaster(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "partyName get successfully");
			responseObjectsMap.put("partyName", partyName);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "partyName Details receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractGrnDetails")
	public ResponseEntity<ResponseDTO> getSubContractGrnDetails(@RequestParam Long orgId,
			@RequestParam String subContractName, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam String branchCode) {
		String methodName = "getSubContractGrnDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> subContractGrnDetails = new ArrayList<>();
		try {
			subContractGrnDetails = grnService.getSubContractGrnDetails(orgId, subContractName, fromDate, toDate,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SubContractGrnDetails get successfully By OrgId");
			responseObjectsMap.put("subContractGrnDetails", subContractGrnDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractGrnDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractGrnSummaryDetails")
	public ResponseEntity<ResponseDTO> getSubContractGrnSummaryDetails(@RequestParam Long orgId,
			@RequestParam String subContractName, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam String branchCode) {
		String methodName = "getSubContractGrnSummaryDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> subContractGrnSummaryDetails = new ArrayList<>();
		try {
			subContractGrnSummaryDetails = grnService.getSubContractGrnSummaryDetails(orgId, subContractName, fromDate,
					toDate, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"subContractGrnSummaryDetails get successfully By OrgId");
			responseObjectsMap.put("subContractGrnSummaryDetails", subContractGrnSummaryDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"subContractGrnSummaryDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

//	@GetMapping("/viewThirdPartyImage")
//	public ResponseEntity<?> viewThirdPartyImage(@RequestParam Long imageId) {
//
//		String methodName = "viewThirdPartyImage()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO;
//		String errorMsg = null;
//
//		try {
//
//			byte[] img = grnService.viewThirdPartyImage(imageId);
//
//			if (img == null) {
//				return ResponseEntity.notFound().build();
//			}
//
//			// Get Dynamic Content Type
//			String contentType = grnService.getImageFileType(imageId);
//
//			if (contentType == null) {
//				contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//			}
//
//			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(img);
//
//		} catch (Exception e) {
//
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//
//			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
//
//			return ResponseEntity.badRequest().body(responseDTO);
//		}
//	}

	@PostMapping(value = "/createUpdateThirdPartyImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateThirdPartyImages(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module, @RequestParam List<String> itemId) {

		String methodName = "createUpdateThirdPartyImages()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = grnService.createUpdateThirdPartyImages(files, docId, screenName,
					module, itemId);

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

	@GetMapping("/viewFileThirdPartyImages/**")
	public ResponseEntity<byte[]> viewFileThirdPartyImages(HttpServletRequest request) throws IOException {
		return grnService.viewFileThirdPartyImages(request);
	}

	@GetMapping("/getThirdPartyReportDetailsImages/{id}")
	public ResponseEntity<List<ImageResponseDTO>> getThirdPartyReportDetailsImages(@PathVariable Long id)
			throws Exception {

		List<ImageResponseDTO> response = grnService.getThirdPartyReportDetailsImages(id);

		return ResponseEntity.ok(response);
	}
}
