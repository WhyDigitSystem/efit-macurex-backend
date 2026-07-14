package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.efitops.basesetup.dto.DcForSubContractDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.IssueToSubContractorDTO;
import com.efitops.basesetup.dto.JobWorkOutDTO;
import com.efitops.basesetup.dto.RecieveFromSubcontractDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SubContractEnquiryDTO;
import com.efitops.basesetup.dto.SubContractInvoiceDTO;
import com.efitops.basesetup.dto.SubContractQuotationDTO;
import com.efitops.basesetup.entity.DcForSubContractVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;
import com.efitops.basesetup.entity.JobWorkOutVO;
import com.efitops.basesetup.entity.RecieveFromSubcontractVO;
import com.efitops.basesetup.entity.SubContractEnquiryVO;
import com.efitops.basesetup.entity.SubContractInvoiceVO;
import com.efitops.basesetup.entity.SubContractQuotationVO;
import com.efitops.basesetup.service.IssueToSubContractorService;

@CrossOrigin
@RestController
@RequestMapping("/api/issuetosubcontractor")
public class IssueToSubContractorController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(IssueToSubContractorController.class);

	@Autowired
	IssueToSubContractorService issueToSubContractorService;

	// IssueToSubContractor

	@GetMapping("/getAllIssueToSubContractorByOrgId")
	public ResponseEntity<ResponseDTO> getAllIssueToSubContractorByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllIssueToSubContractorByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueToSubContractorVO> issueToSubContractorVO = new ArrayList<>();
		try {
			issueToSubContractorVO = issueToSubContractorService.getAllIssueToSubContractorByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractor information get successfully ByOrgId");
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IssueToSubContractor information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIssueToSubContractorById")
	public ResponseEntity<ResponseDTO> getIssueToSubContractorById(@RequestParam Long id) {
		String methodName = "getIssueToSubContractorById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<IssueToSubContractorVO> issueToSubContractorVO = new ArrayList<>();
		try {
			issueToSubContractorVO = issueToSubContractorService.getIssueToSubContractorById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractor information get successfully By Id");
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"IssueToSubContractor information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateIssueToSubContractor")
	public ResponseEntity<ResponseDTO> createUpdateIssueToSubContractor(
			@Valid @RequestBody IssueToSubContractorDTO issueToSubContractorDTO) {
		String methodName = "createUpdateIssueToSubContractor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> issueToSubContractorVO = issueToSubContractorService
					.createUpdateIssueToSubContractor(issueToSubContractorDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, issueToSubContractorVO.get("message"));
			responseObjectsMap.put("issueToSubContractorVO", issueToSubContractorVO.get("issueToSubContractorVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getIssueToSubContractorDocId")
	public ResponseEntity<ResponseDTO> getIssueToSubContractorDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getIssueToSubContractorDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getIssueToSubContractorDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"IssueToSubContractorDocId information retrieved successfully");
			responseObjectsMap.put("issueToSubContractorDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve IssueToSubContractorDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRouteCardNoAndItemNo")
	public ResponseEntity<ResponseDTO> getRouteCardNoAndItemNo(@RequestParam Long orgId) {
		String methodName = "getRouteCardNoAndItemNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getRouteCardNoAndItemNo(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCardNo Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RouteCardNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDepartmentName")
	public ResponseEntity<ResponseDTO> getDepartmentName(@RequestParam Long orgId) {
		String methodName = "getDepartmentName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getDepartmentName(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DepartmentName Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DepartmentName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getProcessNameFormItemWiseProcess")
	public ResponseEntity<ResponseDTO> getProcessNameFormItemWiseProcess(@RequestParam Long orgId,
			@RequestParam String item) {
		String methodName = "getProcessNameFormItemWiseProcess()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getProcessNameFormItemWiseProcess(orgId, item);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ProcessName Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ProcessName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// DcForSubContractor

	@GetMapping("/getDcforSCByOrgId")
	public ResponseEntity<ResponseDTO> getDcforSCByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getDcforSCByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		try {
			dcForSubContractVO = issueToSubContractorService.getDcforSCByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DcForSubContract information get successfully ByOrgId");
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DcForSubContract information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getDcforSCById")
	public ResponseEntity<ResponseDTO> getDcforSCById(@RequestParam Long id) {
		String methodName = "getDcforSCById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DcForSubContractVO> dcForSubContractVO = new ArrayList<>();
		try {
			dcForSubContractVO = issueToSubContractorService.getDcforSCById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Dc For SubContract information get successfully By Id");
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Dc For SubContract information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getIssueSCNoForDcForSubContract")
	public ResponseEntity<ResponseDTO> getIssueSCNoForDcForSubContract(@RequestParam(required = false) Long orgId) {
		String methodName = "getIssueSCNoForDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> issuescno = new ArrayList<>();
		try {
			issuescno = issueToSubContractorService.getIssueSCNoForDcForSubContracto(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CustomerName information get successfully By OrgId");
			responseObjectsMap.put("issuescno", issuescno);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CustomerName information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAddressForDcForSubContract")
	public ResponseEntity<ResponseDTO> getAddressForDcForSubContract(@RequestParam(required = false) Long orgId,
			@RequestParam String customerName) {
		String methodName = "getAddressForDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> address = new ArrayList<>();
		try {
			address = issueToSubContractorService.getAddressForDcForSubContract(orgId, customerName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Address information get successfully By OrgId");
			responseObjectsMap.put("address", address);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Address information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateDcForSubContract")
	public ResponseEntity<ResponseDTO> updateCreateDcForSubContract(
			@Valid @RequestBody DcForSubContractDTO dcForSubContractDTO) {
		String methodName = "updateCreateDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> dcForSubContractVO = issueToSubContractorService
					.updateCreateDcForSubContract(dcForSubContractDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, dcForSubContractVO.get("message"));
			responseObjectsMap.put("dcForSubContractVO", dcForSubContractVO.get("dcForSubContractVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDcForSubContractDocId")
	public ResponseEntity<ResponseDTO> getDcForSubContractDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getDcForSubContractDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getDcForSubContractDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnDocId information retrieved successfully");
			responseObjectsMap.put("dcForSubContractDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnDocId information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractorName")
	public ResponseEntity<ResponseDTO> getSubContractorName(@RequestParam Long orgId) {
		String methodName = "getSubContractorName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractorName(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "partyName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve partyName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItenNameAndDescFromIssue")
	public ResponseEntity<ResponseDTO> getItenNameAndDescFromIssue(@RequestParam Long orgId,
			@RequestParam String scIssueNo) {
		String methodName = "getItenNameAndDescFromIssue()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getItenNameAndDescFromIssue(orgId, scIssueNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Item Details retrieved successfully");
			responseObjectsMap.put("issuetoSubContractorVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Item Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SubContractEnquiry

	@GetMapping("/getAllSubContractEnquiryByOrgId")
	public ResponseEntity<ResponseDTO> getAllSubContractEnquiryByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSubContractEnquiryByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractEnquiryVO> subContractEnquiryVO = new ArrayList<>();
		try {
			subContractEnquiryVO = issueToSubContractorService.getAllSubContractEnquiryByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiry information get successfully ByOrgId");
			responseObjectsMap.put("subContractEnquiryVO", subContractEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractEnquiryById")
	public ResponseEntity<ResponseDTO> getSubContractEnquiryById(@RequestParam Long id) {
		String methodName = "getSubContractEnquiryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractEnquiryVO> subContractEnquiryVO = new ArrayList<>();
		try {
			subContractEnquiryVO = issueToSubContractorService.getSubContractEnquiryById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiry information get successfully By Id");
			responseObjectsMap.put("issueToSubContractorVO", subContractEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractEnquiry information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateSubContractEnquiry")
	public ResponseEntity<ResponseDTO> createUpdateSubContractEnquiry(
			@Valid @RequestBody SubContractEnquiryDTO subContractEnquiryDTO) {
		String methodName = "createUpdateSubContractEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> subContractEnquiryVO = issueToSubContractorService
					.createUpdateSubContractEnquiry(subContractEnquiryDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, subContractEnquiryVO.get("message"));
			responseObjectsMap.put("subContractEnquiryVO", subContractEnquiryVO.get("subContractEnquiryVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractEnquiryDocId")
	public ResponseEntity<ResponseDTO> getSubContractEnquiryDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getSubContractEnquiryDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getSubContractEnquiryDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiryDocId information retrieved successfully");
			responseObjectsMap.put("subContractEnquiryDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractEnquiryDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractCustomerNameAndCode")
	public ResponseEntity<ResponseDTO> getSubContractCustomerNameAndCode(@RequestParam Long orgId) {
		String methodName = "getSubContractCustomerNameAndCode()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractCustomerNameAndCode(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractContactNameAndNo")
	public ResponseEntity<ResponseDTO> getSubContractContactNameAndNo(@RequestParam Long orgId,
			@RequestParam String subContractorName) {
		String methodName = "getSubContractContactNameAndNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractContactNameAndNo(orgId, subContractorName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ContactName Details retrieved successfully");
			responseObjectsMap.put("partyMasterVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ContactName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractPartNoAndDescription")
	public ResponseEntity<ResponseDTO> getSubContractPartNoAndDescription(@RequestParam Long orgId,
			@RequestParam String scIssueNo) {
		String methodName = "getSubContractPartNoAndDescription()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractPartNoAndDescription(orgId, scIssueNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("issuetoSubContractorVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubRouteCardNo")
	public ResponseEntity<ResponseDTO> getSubRouteCardNo(@RequestParam Long orgId) {
		String methodName = "getSubRouteCardNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubRouteCardNo(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "RouteCard Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve RouteCard Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getScIssueNoFormSubContract")
	public ResponseEntity<ResponseDTO> getScIssueNoFormSubContract(@RequestParam Long orgId,
			@RequestParam String routeCardNo) {
		String methodName = "getScIssueNoFormSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getScIssueNoFormSubContract(orgId, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ScIssueNo Details retrieved successfully");
			responseObjectsMap.put("routeCardVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve ScIssueNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SubContractorQuotation

	@GetMapping("/getAllSubContractQuotationByOrgId")
	public ResponseEntity<ResponseDTO> getAllSubContractQuotationByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSubContractQuotationByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractQuotationVO> subContractQuotationVO = new ArrayList<>();
		try {
			subContractQuotationVO = issueToSubContractorService.getAllSubContractQuotationByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractQuotation information get successfully ByOrgId");
			responseObjectsMap.put("subContractQuotationVO", subContractQuotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractQuotation information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractQuotationById")
	public ResponseEntity<ResponseDTO> getSubContractQuotationById(@RequestParam Long id) {
		String methodName = "getSubContractQuotationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractQuotationVO> subContractQuotationVO = new ArrayList<>();
		try {
			subContractQuotationVO = issueToSubContractorService.getSubContractQuotationById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractQuotation information get successfully By Id");
			responseObjectsMap.put("subContractQuotationVO", subContractQuotationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractQuotation information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateSubContractQuotation")
	public ResponseEntity<ResponseDTO> createUpdateSubContractQuotation(
			@Valid @RequestBody SubContractQuotationDTO subContractQuotationDTO) {
		String methodName = "createUpdateSubContractQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> subContractQuotationVO = issueToSubContractorService
					.createUpdateSubContractQuotation(subContractQuotationDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, subContractQuotationVO.get("message"));
			responseObjectsMap.put("subContractQuotationVO", subContractQuotationVO.get("subContractQuotationVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractQuotationDocId")
	public ResponseEntity<ResponseDTO> getSubContractQuotationDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getSubContractQuotationDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getSubContractQuotationDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractQuotationDocId information retrieved successfully");
			responseObjectsMap.put("subContractQuotationDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractQuotationDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getEnquiryNoFromSubContractEnquiry")
	public ResponseEntity<ResponseDTO> getEnquiryNoFromSubContractEnquiry(@RequestParam Long orgId) {
		String methodName = "getEnquiryNoFromSubContractEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getEnquiryNoFromSubContractEnquiry(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "EnquirayNo Details retrieved successfully");
			responseObjectsMap.put("subContractEnquiryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve EnquirayNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDocDateFromSubEnquiry")
	public ResponseEntity<ResponseDTO> getDocDateFromSubEnquiry(@RequestParam Long orgId, @RequestParam String docId) {
		String methodName = "getDocDateFromSubEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getDocDateFromSubEnquiry(orgId, docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SubContractorName Details retrieved successfully");
			responseObjectsMap.put("subContractEnquiryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve SubContractorName Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoPartDescFromSubEnquiry")
	public ResponseEntity<ResponseDTO> getPartNoPartDescFromSubEnquiry(@RequestParam Long orgId,
			@RequestParam String docId) {
		String methodName = "getPartNoPartDescFromSubEnquiry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getPartNoPartDescFromSubEnquiry(orgId, docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Part Details retrieved successfully");
			responseObjectsMap.put("subContractEnquiryVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Part Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// SubContractInvoice

	@GetMapping("/getAllSubContractInvoiceByOrgId")
	public ResponseEntity<ResponseDTO> getAllSubContractInvoiceByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getAllSubContractInvoiceByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractInvoiceVO> subContractInvoiceVO = new ArrayList<>();
		try {
			subContractInvoiceVO = issueToSubContractorService.getAllSubContractInvoiceByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractInvoice information get successfully ByOrgId");
			responseObjectsMap.put("subContractInvoiceVO", subContractInvoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractInvoice information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractInvoiceById")
	public ResponseEntity<ResponseDTO> getSubContractInvoiceById(@RequestParam Long id) {
		String methodName = "getSubContractInvoiceById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SubContractInvoiceVO> subContractInvoiceVO = new ArrayList<>();
		try {
			subContractInvoiceVO = issueToSubContractorService.getSubContractInvoiceById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractInvoice information get successfully By Id");
			responseObjectsMap.put("subContractInvoiceVO", subContractInvoiceVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractInvoice information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateSubContractInvoice")
	public ResponseEntity<ResponseDTO> createUpdateSubContractInvoice(
			@Valid @RequestBody SubContractInvoiceDTO subContractInvoiceDTO) {
		String methodName = "createUpdateSubContractInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> subContractInvoiceVO = issueToSubContractorService
					.createUpdateSubContractInvoice(subContractInvoiceDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, subContractInvoiceVO.get("message"));
			responseObjectsMap.put("subContractInvoiceVO", subContractInvoiceVO.get("subContractInvoiceVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractInvoiceDocId")
	public ResponseEntity<ResponseDTO> getSubContractInvoiceDocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getSubContractInvoiceDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getSubContractInvoiceDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractInvoiceDocId information retrieved successfully");
			responseObjectsMap.put("subContractInvoiceDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractInvoiceDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobWorkOutOrderNo")
	public ResponseEntity<ResponseDTO> getJobWorkOutOrderNo(@RequestParam Long orgId) {
		String methodName = "getJobWorkOutOrderNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getJobWorkOutOrderNo(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobWorkOutOrder Details retrieved successfully");
			responseObjectsMap.put("jobWorkOutOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve JobWorkOutOrder Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobWorkOutOrderFromPartNoAndDesc")
	public ResponseEntity<ResponseDTO> getJobWorkOutOrderFromPartNoAndDesc(@RequestParam Long orgId,
			@RequestParam String docId) {
		String methodName = "getJobWorkOutOrderFromPartNoAndDesc()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getJobWorkOutOrderFromPartNoAndDesc(orgId, docId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details retrieved successfully");
			responseObjectsMap.put("jobWorkOutOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNo Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// JobWorkOut

	@GetMapping("/getAllJobWorkOutByOrgId")
	public ResponseEntity<ResponseDTO> getAllJobWorkOutByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {
		String methodName = "getAllJobWorkOutByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobWorkOutVO> jobWorkOutVO = new ArrayList<>();
		try {
			jobWorkOutVO = issueToSubContractorService.getAllJobWorkOutByOrgId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiry information get successfully ByOrgId");
			responseObjectsMap.put("jobWorkOutVO", jobWorkOutVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractEnquiry information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getAllJobWorkOutById")
	public ResponseEntity<ResponseDTO> getAllJobWorkOutById(@RequestParam Long id) {
		String methodName = "getAllJobWorkOutById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<JobWorkOutVO> jobWorkOutVO = new ArrayList<>();
		try {
			jobWorkOutVO = issueToSubContractorService.getAllJobWorkOutById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractEnquiry information get successfully By Id");
			responseObjectsMap.put("jobWorkOutVO", jobWorkOutVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SubContractEnquiry information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/createUpdateJobWorkOut")
	public ResponseEntity<ResponseDTO> createUpdateJobWorkOut(@Valid @RequestBody JobWorkOutDTO jobWorkOutDTO) {
		String methodName = "createUpdateJobWorkOut()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> jobWorkOutVO = issueToSubContractorService.createUpdateJobWorkOut(jobWorkOutDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, jobWorkOutVO.get("message"));
			responseObjectsMap.put("jobWorkOutVO", jobWorkOutVO.get("jobWorkOutVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobWorkOutDocId")
	public ResponseEntity<ResponseDTO> getJobWorkOutDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode) {

		String methodName = "getJobWorkOutDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getJobWorkOutDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobWorkOutDocId information retrieved successfully");
			responseObjectsMap.put("jobWorkOutDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve JobWorkOutDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDCNumberFromDcForSubContract")
	public ResponseEntity<ResponseDTO> getDCNumberFromDcForSubContract(@RequestParam(required = false) Long orgId) {
		String methodName = "getDCNumberFromDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> dcforsub = new ArrayList<>();
		try {
			dcforsub = issueToSubContractorService.getDCNumberFromDcForSubContract(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"dc for subcontract information get successfully By OrgId");
			responseObjectsMap.put("dcforsub", dcforsub);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Address information receive failed By OrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getPoNumberFromPurchase")
	public ResponseEntity<ResponseDTO> getPoNumberFromPurchase(@RequestParam Long orgId,
			@RequestParam String routeCardNo) {
		String methodName = "getPoNumberFromPurchase()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getPoNumberFromPurchase(orgId, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PoNumnber Details retrieved successfully");
			responseObjectsMap.put("purchaseOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PoNumnber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getQuotationNumberFromSubContract")
	public ResponseEntity<ResponseDTO> getQuotationNumberFromSubContract(@RequestParam Long orgId,
			@RequestParam String routeCardNo) {
		String methodName = "getQuotationNumberFromSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getQuotationNumberFromSubContract(orgId, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "QuotationNumber Details retrieved successfully");
			responseObjectsMap.put("subContractQuotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve QuotationNumber Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getItemAndItemDescFromDcForSubContract")
	public ResponseEntity<ResponseDTO> getItemAndItemDescFromDcForSubContract(@RequestParam Long orgId,
			@RequestParam String dcNumber, @RequestParam String routeCardNo) {
		String methodName = "getItemAndItemDescFromDcForSubContract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getItemAndItemDescFromDcForSubContract(orgId, dcNumber, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Part Details retrieved successfully");
			responseObjectsMap.put("dcFromSubContractVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Part Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// RecieveFromSubContract

	@GetMapping("/getRecieveFromSubcontractByOrgId")
	public ResponseEntity<ResponseDTO> getRecieveFromSubcontractByOrgId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getRecieveFromSubcontractByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RecieveFromSubcontractVO> recieveFromSubcontractVO = new ArrayList<>();
		try {
			recieveFromSubcontractVO = issueToSubContractorService.getRecieveFromSubcontractByOrgId(orgId, finYear,
					branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Recieve From Subcontract information get successfully ByOrgId");
			responseObjectsMap.put("recieveFromSubcontractVO", recieveFromSubcontractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Recieve From Subcontract information receive failedByOrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getRecieveFromSubcontractById")
	public ResponseEntity<ResponseDTO> getRecieveFromSubcontractById(@RequestParam Long id) {
		String methodName = "getRecieveFromSubcontractById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RecieveFromSubcontractVO> recieveFromSubcontractVO = new ArrayList<>();
		try {
			recieveFromSubcontractVO = issueToSubContractorService.getRecieveFromSubcontractById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Recieve From Subcontract information get successfully By Id");
			responseObjectsMap.put("recieveFromSubcontractVO", recieveFromSubcontractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Recieve From Subcontract information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/updateCreateRecieveFromSubcontract")
	public ResponseEntity<ResponseDTO> updateCreateRecieveFromSubcontract(
			@Valid @RequestBody RecieveFromSubcontractDTO recieveFromSubcontractDTO) {
		String methodName = "updateCreateRecieveFromSubcontract()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> recieveFromSubcontractVO = issueToSubContractorService
					.updateCreateRecieveFromSubcontract(recieveFromSubcontractDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, recieveFromSubcontractVO.get("message"));
			responseObjectsMap.put("recieveFromSubcontractVO",
					recieveFromSubcontractVO.get("recieveFromSubcontractVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRecieveFromSubcontractVODocId")
	public ResponseEntity<ResponseDTO> getRecieveFromSubcontractVODocId(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {

		String methodName = "getRecieveFromSubcontractVODocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getRecieveFromSubcontractDocId(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"RecieveFromSubcontractVODocId information retrieved successfully");
			responseObjectsMap.put("recieveFromSubcontractVODocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve RecieveFromSubcontractVODocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// JobWorkOutOrder

	@GetMapping("/getDcSubContractorDocIdForJobWorkOutOrder")
	public ResponseEntity<ResponseDTO> getDcSubContractorDocIdForJobWorkOutOrder(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode) {
		String methodName = "getDcSubContractorDocIdForWorkJobOutOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getDcSubContractorDocIdForJobWorkOutOrder(orgId, finYear, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" DcSubContractorDocId Details retrieved successfully");
			responseObjectsMap.put("dcSubContractorVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DcSubContractorDocId Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPurchaseOrderDocIdForJobWorkOutOrder")
	public ResponseEntity<ResponseDTO> getPurchaseOrderDocIdForJobWorkOutOrder(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String routeCardNo) {
		String methodName = "getPurchaseOrderDocIdForJobWorkOutOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getPurchaseOrderDocIdForJobWorkOutOrder(orgId, finYear, branchCode,
					routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " PurchaseOrderDocId Details retrieved successfully");
			responseObjectsMap.put("purchaseOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PurchaseOrderDocId Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractQuotationDocIdForJobWorkOutOrder")
	public ResponseEntity<ResponseDTO> getSubContractQuotationDocIdForJobWorkOutOrder(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String routeCardNo) {
		String methodName = "getSubContractQuotationDocIdForJobWorkOutOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractQuotationDocIdForJobWorkOutOrder(orgId, finYear,
					branchCode, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" SubContractQuotation DocId Details retrieved successfully");
			responseObjectsMap.put("subContractQuotationVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractQuotation DocId Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// dropdown

	@GetMapping("/getIssueNoForReceiveFromSubContractor")
	public ResponseEntity<ResponseDTO> getIssueNoForReceiveFromSubContractor(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String routeCardNo) {
		String methodName = "getIssueNoForReceiveFromSubContractor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getIssueNoForReceiveFromSubContractor(orgId, finYear, branchCode,
					routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " IssueNo Details retrieved successfully");
			responseObjectsMap.put("issueToSubContractorVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve IssueNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getJobWorkOutOrderNoForReceiveFromSubContractor")
	public ResponseEntity<ResponseDTO> getJobWorkOutOrderNoForReceiveFromSubContractor(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String routeCardNo) {
		String methodName = "getJobWorkOutOrderNoForReceiveFromSubContractor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getJobWorkOutOrderNoForReceiveFromSubContractor(orgId, finYear,
					branchCode, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, " JobWorkOutOrderNo Details retrieved successfully");
			responseObjectsMap.put("jobWorkOutOrderVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve JobWorkOutOrderNo Details",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNameAndPartDescForReceiveFromSubContractor")
	public ResponseEntity<ResponseDTO> getPartNameAndPartDescForReceiveFromSubContractor(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String routeCardNo,
			@RequestParam String issueNo) {
		String methodName = "getPartNameAndPartDescForReceiveFromSubContractor()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getPartNameAndPartDescForReceiveFromSubContractor(orgId, finYear,
					branchCode, routeCardNo, issueNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					" issueToSubContractor Details retrieved successfully");
			responseObjectsMap.put("issueToSubContractorVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve issueToSubContractor Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRateFromSubContractQuotation")
	public ResponseEntity<ResponseDTO> getRateFromSubContractQuotation(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode,
			@RequestParam String subContractQuotationDocId, @RequestParam String routeCardNo,
			@RequestParam String part) {
		String methodName = "getRateFromSubContractQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getRateFromSubContractQuotation(orgId, finYear, branchCode,
					subContractQuotationDocId, routeCardNo, part);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "rate Details retrieved successfully");
			responseObjectsMap.put("issueToSubContractorVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve rate Details", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractEnquiryIdIteration")
	public ResponseEntity<ResponseDTO> getSubContractEnquiryIdIteration(@RequestParam Long orgId,
			@RequestParam String clientName, @RequestParam String enquiryNo) {

		String methodName = "getSubContractEnquiryIdIteration()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = issueToSubContractorService.getSubContractEnquiryIdIteration(orgId, clientName, enquiryNo);
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

	@GetMapping("/getIssueToSubContractorDetails")
	public ResponseEntity<ResponseDTO> getIssueToSubContractorDetails(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam String status, @RequestParam String routeCardNo) {
		String methodName = "getIssueToSubContractorDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> issueToSubContractVO = new ArrayList<>();
		try {
			issueToSubContractVO = issueToSubContractorService.getIssueToSubContractorDetails(orgId, fromdate, todate,
					status, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Issue To SubContract Details get successfully");
			responseObjectsMap.put("issueToSubContractVO", issueToSubContractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, " Issue To SubContract Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSubContractEnquiryDetails")
	public ResponseEntity<ResponseDTO> getSubContractEnquiryDetails(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam String subContractorName) {
		String methodName = "getSubContractEnquiryDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> subContractEnquiryVO = new ArrayList<>();
		try {
			subContractEnquiryVO = issueToSubContractorService.getSubContractEnquiryDetails(orgId, fromdate, todate,
					subContractorName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sub Contract Enquiry Details get successfully");
			responseObjectsMap.put("subContractEnquiryVO", subContractEnquiryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, " Sub Contract Enquiry Details receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRecieveFromSubContractDetails")
	public ResponseEntity<ResponseDTO> getRecieveFromSubContractDetails(@RequestParam Long orgId,
			@RequestParam(required = false) String fromdate, @RequestParam(required = false) String todate,
			@RequestParam String status, @RequestParam String routeCardNo) {
		String methodName = "getRecieveFromSubContractDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> recieveFromSubcontractVO = new ArrayList<>();
		try {
			recieveFromSubcontractVO = issueToSubContractorService.getRecieveFromSubContractDetails(orgId, fromdate,
					todate, status, routeCardNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Recieve From Sub Contract Details get successfully");
			responseObjectsMap.put("recieveFromSubcontractVO", recieveFromSubcontractVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					" Recieve From Sub Contract Details receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	// Reports

	@GetMapping("/getDeliveryChallanSubContractorReport")
	public ResponseEntity<ResponseDTO> getDeliveryChallanSubContractorReport(@RequestParam Long orgId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String routeCardNo) {

		String methodName = "getDeliveryChallanSubContractorReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> reportList = issueToSubContractorService
					.getDeliveryChallanSubContractorReport(orgId, fromDate, toDate, routeCardNo);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Delivery Challan Sub Contractor Report retrieved successfully");
			responseObjectsMap.put("deliveryChallanSubContractorReport", reportList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Delivery Challan Sub Contractor Report", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getSubContractorInvoiceReport")
	public ResponseEntity<ResponseDTO> getSubContractorInvoiceReport(@RequestParam Long orgId,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String routeCardNo) {

		String methodName = "getSubContractorInvoiceReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			List<Map<String, Object>> reportList = issueToSubContractorService.getSubContractorInvoiceReport(orgId,
					fromDate, toDate, routeCardNo);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Sub Contractor Invoice Report retrieved successfully");
			responseObjectsMap.put("subContractorInvoiceReport", reportList);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Sub Contractor Invoice Report", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

	@GetMapping("/getJobWorkOutDetails")
	public ResponseEntity<ResponseDTO> getJobWorkOutDetails(@RequestParam Long orgId,
			@RequestParam String contractorName, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam String branchCode) {
		String methodName = "getJobWorkOutDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> jobWorkOutDetails = new ArrayList<>();
		try {
			jobWorkOutDetails = issueToSubContractorService.getJobWorkOutDetails(orgId, contractorName, fromDate,
					toDate, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "JobWorkOutDetails get successfully By OrgId");
			responseObjectsMap.put("jobWorkOutDetails", jobWorkOutDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"JobWorkOutDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getJobWorkOutSummaryDetails")
	public ResponseEntity<ResponseDTO> getJobWorkOutSummaryDetails(@RequestParam Long orgId,
			@RequestParam String contractorName, @RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate, @RequestParam String branchCode) {
		String methodName = "getJobWorkOutSummaryDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> jobWorkOutSummaryDetails = new ArrayList<>();
		try {
			jobWorkOutSummaryDetails = issueToSubContractorService.getJobWorkOutSummaryDetails(orgId, contractorName,
					fromDate, toDate, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "jobWorkOutSummaryDetails get successfully By OrgId");
			responseObjectsMap.put("jobWorkOutSummaryDetails", jobWorkOutSummaryDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"jobWorkOutSummaryDetails information receive failed By OrgId", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@GetMapping("/getSubContractQuotationDetailsReport")
	public ResponseEntity<ResponseDTO> getSubContractQuotationDetailsReport(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String subContractName,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate) {
		String methodName = "getSubContractQuotationDetailsReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = issueToSubContractorService.getSubContractQuotationDetailsReport(orgId, branchCode, subContractName,
					fromDate, toDate);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SubContractQuotationDetailsReport retrieved successfully");
			responseObjectsMap.put("SubContractQuotationDetailsReport", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SubContractQuotationDetailsReport ", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PostMapping(value = "/createUpdateSubContractQuotation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> createUpdateSubContractQuotation(
			@RequestPart(value = "files", required = false) MultipartFile[] files, @RequestParam String docId,
			@RequestParam String screenName, @RequestParam String module) {

		String methodName = "createUpdateSubContractQuotation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> serviceResponse = issueToSubContractorService.createUpdateSubContractQuotation(files, docId,
					screenName, module);

			responseMap.put("message", serviceResponse.get("message"));
			responseMap.put("inprocessInspectionVO", serviceResponse.get("inprocessInspectionVO"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);

		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

	@GetMapping("/files/**")
	public ResponseEntity<byte[]> viewFile(HttpServletRequest request) throws IOException {
		return issueToSubContractorService.viewFile(request);
	}
	
	@GetMapping("/getSubContractQuotationImages/{id}")
	public ResponseEntity<List<ImageResponseDTO>> getSubContractQuotationImages(@PathVariable Long id) throws Exception {

		List<ImageResponseDTO> response = issueToSubContractorService.getSubContractQuotationImages(id);

		return ResponseEntity.ok(response);
	}

}
