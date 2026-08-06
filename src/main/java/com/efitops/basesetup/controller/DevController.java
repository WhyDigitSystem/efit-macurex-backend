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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.DespatchInstructionResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.DespatchInstructionDTO;
import com.efitops.basesetup.dto.DocketInvoiceDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.service.TransportMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/dev")
public class DevController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);
	@Autowired
	TransportMasterService transportMasterService;
	
	//customer complaint master
	@PutMapping(
	        value = "/updateCreateCustomerComplaint",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDTO> updateCreateCustomerComplaint(
	        @RequestPart("customerComplaint") CustomerComplaintDTO customerComplaintDTO,
	        @RequestPart(value = "images", required = false) MultipartFile[] images) {

	    String methodName = "updateCreateCustomerComplaint";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> responseMap =
	                transportMasterService.updateCreateCustomerComplaint(customerComplaintDTO, null);

	        responseObjectsMap.put(
	                CommonConstant.STRING_MESSAGE,
	                responseMap.get("message"));

	        responseObjectsMap.put(
	                "customerComplaintEntryVO",
	                responseMap.get("customerComplaintEntryVO"));

	        responseDTO = createServiceResponse(responseObjectsMap);

	    } catch (Exception e) {

	        LOGGER.error(
	                UserConstants.ERROR_MSG_METHOD_NAME,
	                methodName,
	                e.getMessage(),
	                e);

	        responseDTO = createServiceResponseError(
	                responseObjectsMap,
	                e.getMessage(),
	                e.getMessage());
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	    return ResponseEntity.ok(responseDTO);
	}
	 @GetMapping("/getCustomerComplaintById")
	 public ResponseEntity<ResponseDTO> getCustomerComplaintById(@RequestParam Long id) {

	     String methodName = "getCustomerComplaintById()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	    	 CustomerComplaintResponseDTO customerComplaintEntryVO = transportMasterService.getCustomerComplaintById(id);

	         responseObjectsMap.put("customerComplaintEntryVO", customerComplaintEntryVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getCustomerComplaintByOrgId")
	 public ResponseEntity<ResponseDTO> getCustomerComplaintByOrgId(@RequestParam Long orgId,@RequestParam Long branch) {

	     String methodName = "getCustomerComplaintByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         List<CustomerComplaintResponseDTO> customerComplaintEntryVO = transportMasterService.getCustomerComplaintByOrgId(orgId,branch);

	         responseObjectsMap.put("customerComplaintEntryVO", customerComplaintEntryVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 //dropdown for preparedby
	 
	 @GetMapping("/getPreparedBy")
	 public ResponseEntity<ResponseDTO> getPreparedBy(@RequestParam Long orgId,
	                                                  @RequestParam Long branch,
	                                                  @RequestParam Long departmentId) {

	     String methodName = "getPreparedBy()";

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     ResponseDTO responseDTO;

	     try {

	         Map<String, Object> preparedBy =
	                 transportMasterService.getPreparedBy(orgId, branch, departmentId);

	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                 "Prepared By Fetched Successfully");

	         responseObjectsMap.putAll(preparedBy);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         responseDTO = createServiceResponseError(
	                 responseObjectsMap,
	                 e.getMessage(),
	                 e.getMessage());
	     }

	     return ResponseEntity.ok(responseDTO);
	 }
	 

	 @GetMapping("/getCustomerComplaintItemDetails")
	 public ResponseEntity<ResponseDTO> getCustomerComplaintItemDetails(@RequestParam Long orgId,@RequestParam Long branch) {

	     String methodName = "getCustomerComplaintItemDetails()";

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     ResponseDTO responseDTO;

	     try {

	         Map<String, Object> itemDetails =
	                 transportMasterService.getCustomerComplaintItemDetails(orgId, branch);

	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                 "Item Details Fetched Successfully");

	         responseObjectsMap.put("itemDetails", itemDetails);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         responseDTO = createServiceResponseError(
	                 responseObjectsMap,
	                 e.getMessage(),
	                 e.getMessage());
	     }

	     return ResponseEntity.ok(responseDTO);
	 }
	
	// branch dropdown
	 @GetMapping("/getAllBranch")
	 public ResponseEntity<ResponseDTO> getAllBranch(@RequestParam Long orgId) {

	     String methodName = "getAllBranch()";

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     ResponseDTO responseDTO;

	     try {

	         Map<String, Object> branch =
	                 transportMasterService.getAllBranch(orgId);

	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                 "Branch Fetched Successfully");

	         responseObjectsMap.putAll(branch);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         responseDTO = createServiceResponseError(
	                 responseObjectsMap,
	                 e.getMessage(),
	                 e.getMessage());
	     }

	     return ResponseEntity.ok(responseDTO);
	 }
	
	 
	 @GetMapping("/getCustomerDetails")
	 public ResponseEntity<ResponseDTO> getCustomerDetails(@RequestParam Long orgId,
	                                                       @RequestParam Long branch) {

	     String methodName = "getCustomerDetails()";

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     ResponseDTO responseDTO;

	     try {

	         Map<String, Object> customerDetails =
	                 transportMasterService.getCustomerDetails(orgId, branch);

	         responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
	                 "Customer Details Fetched Successfully");

	         responseObjectsMap.putAll(customerDetails);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         responseDTO = createServiceResponseError(
	                 responseObjectsMap,
	                 e.getMessage(),
	                 e.getMessage());
	     }

	     return ResponseEntity.ok(responseDTO);
	 }
	 //sales contract amendment
	 
		@PostMapping("/updateCreateSalesContractAmendment")
		public ResponseEntity<ResponseDTO> updateCreateSalesContractAmendment(
				@RequestBody SalesContractAmendmentDTO salesContractAmendmentDTO) {

			String methodName = "updateCreateSalesContractAmendment()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			Map<String, Object> responseObjectsMap = new HashMap<>();
			String errorMsg = null;
			ResponseDTO responseDTO = null;

			try {

				Map<String, Object> responseMap = transportMasterService
						.updateCreateSalesContractAmendment(salesContractAmendmentDTO);

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));
				responseObjectsMap.put("salesContractAmendmentVO",
						responseMap.get("salesContractAmendmentVO"));

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

				salesContractAmendment = transportMasterService
						.getSalesContractAmendmentById(id);

			} catch (Exception e) {

				errorMsg = e.getMessage();

				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isBlank(errorMsg))  {

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"Sales Contract Amendment information retrieved successfully");

				responseObjectsMap.put("salesContractAmendment",
						salesContractAmendment);

				responseDTO = createServiceResponse(responseObjectsMap);

			} else {

				responseDTO = createServiceResponseError(responseObjectsMap,
						"Sales Contract Amendment information retrieval failed",
						errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/getSalesContractAmendmentByOrgId")
		public ResponseEntity<ResponseDTO> getSalesContractAmendmentByOrgId(@RequestParam Long orgId,@RequestParam Long branch) {

			String methodName = "getSalesContractAmendmentByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;

			List<SalesContractAmdResponseDTO> salesContractList = new ArrayList<>();

			try {

				salesContractList = transportMasterService
						.getSalesContractAmendmentByOrgId(orgId,branch);

			} catch (Exception e) {

				errorMsg = e.getMessage();

				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isBlank(errorMsg)) {

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"Sales Contract Amendment information retrieved successfully");

				responseObjectsMap.put("salesContractList",
						salesContractList);

				responseDTO = createServiceResponse(responseObjectsMap);

			} else {

				responseDTO = createServiceResponseError(responseObjectsMap,
						"Sales Contract Amendment information retrieval failed",
						errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}
		
		// dropdown for contractno
		
		@GetMapping("/getContractNoDropdown")
		public ResponseEntity<ResponseDTO> getContractNoDropdown() {

		    String methodName = "getContractNoDropdown";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap = transportMasterService.getContractNo();

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "contractList",
		                responseMap.get("contractList"));

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {

		        LOGGER.error(
		                UserConstants.ERROR_MSG_METHOD_NAME,
		                methodName,
		                e.getMessage(),
		                e);

		        responseDTO = createServiceResponseError(
		                responseObjectsMap,
		                e.getMessage(),
		                e.getMessage());
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		    return ResponseEntity.ok(responseDTO);
		}
		
		
		//Despatch Instruction
		
		@PostMapping("/updateCreateDespatchIntruction")
		public ResponseEntity<ResponseDTO> updateCreateDespatchIntruction(
				@RequestBody DespatchInstructionDTO despatchInstructionDTO) {

			String methodName = "updateCreateDespatchIntruction()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			Map<String, Object> responseObjectsMap = new HashMap<>();
			String errorMsg = null;
			ResponseDTO responseDTO = null;

			try {

				Map<String, Object> responseMap = transportMasterService.updateCreateDespatchInstruction(despatchInstructionDTO);

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));
				responseObjectsMap.put("despatchInstructionVO",
						responseMap.get("despatchInstructionVO"));

				responseDTO = createServiceResponse(responseObjectsMap);

			} catch (Exception e) {

				errorMsg = e.getMessage();

				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getDespatchIntructionById")
		public ResponseEntity<ResponseDTO> getDespatchIntructionById(@RequestParam Long id) {

			String methodName = "getDespatchIntructionById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;

			DespatchInstructionResponseDTO despatchInstructionResponseDTO = null;

			try {

				despatchInstructionResponseDTO = transportMasterService.getDespatchInstructionById(id);
			} catch (Exception e) {

				errorMsg = e.getMessage();

				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isBlank(errorMsg))  {

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"despatch instructions  retrieved successfully");

				responseObjectsMap.put("despatchInstructionResponseDTO",
						despatchInstructionResponseDTO);

				responseDTO = createServiceResponse(responseObjectsMap);

			} else {

				responseDTO = createServiceResponseError(responseObjectsMap,
						"Despatch Instructions retrieval failed",
						errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/getDespatchIntructionByOrgId")
		public ResponseEntity<ResponseDTO> getDespatchIntructionByOrgId(@RequestParam Long orgId,@RequestParam Long branch) {

			String methodName = "getDespatchIntructionByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;

			List<DespatchInstructionResponseDTO> despatchInstructionResponseDTO = new ArrayList<>();

			try {

				despatchInstructionResponseDTO = transportMasterService.getDespatchInstructionByOrgId(orgId, branch);

			} catch (Exception e) {

				errorMsg = e.getMessage();

				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isBlank(errorMsg)) {

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						" Despatch instructions retrieved successfully");

				responseObjectsMap.put("despatchInstructionResponseDTO",
						despatchInstructionResponseDTO);

				responseDTO = createServiceResponse(responseObjectsMap);

			} else {

				responseDTO = createServiceResponseError(responseObjectsMap,
						" Despatch instructions retrieval failed",
						errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);

	}
		
		// Docket Invoice 
		@PostMapping("/updateCreateDocketInvoice")
		public ResponseEntity<ResponseDTO> updateCreateDocketInvoice(
		        @RequestBody DocketInvoiceDTO docketInvoiceDTO) {

		    String methodName = "updateCreateDocketInvoice()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    String errorMsg = null;
		    ResponseDTO responseDTO = null;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.updateCreateDocketInvoice(docketInvoiceDTO);

		        responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put("docketInvoiceVO",
		                responseMap.get("docketInvoiceVO"));

		        responseDTO = createServiceResponse(responseObjectsMap);

		    } catch (Exception e) {

		        errorMsg = e.getMessage();

		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
		                methodName,
		                errorMsg);

		        responseDTO = createServiceResponseError(
		                responseObjectsMap,
		                errorMsg,
		                errorMsg);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		    return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getDocketInvoiceById")
		public ResponseEntity<ResponseDTO> getDocketInvoiceById(
		        @RequestParam Long id) {

		    String methodName = "getDocketInvoiceById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    String errorMsg = null;

		    Map<String, Object> responseObjectsMap = new HashMap<>();

		    ResponseDTO responseDTO = null;

		    DocketInvoiceResponseDTO docketInvoiceResponseDTO = null;

		    try {

		        docketInvoiceResponseDTO =
		                transportMasterService.getDocketInvoiceById(id);

		    } catch (Exception e) {

		        errorMsg = e.getMessage();

		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
		                methodName,
		                errorMsg);
		    }

		    if (StringUtils.isBlank(errorMsg)) {

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                "Docket Invoice retrieved successfully");

		        responseObjectsMap.put(
		                "docketInvoiceResponseDTO",
		                docketInvoiceResponseDTO);

		        responseDTO =
		                createServiceResponse(responseObjectsMap);

		    } else {

		        responseDTO =
		                createServiceResponseError(
		                        responseObjectsMap,
		                        "Docket Invoice retrieval failed",
		                        errorMsg);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		    return ResponseEntity.ok().body(responseDTO);

		}
		
		
		@GetMapping("/getDocketInvoiceByOrgId")
		public ResponseEntity<ResponseDTO> getDocketInvoiceByOrgId(
		        @RequestParam Long orgId,
		        @RequestParam Long branch) {

		    String methodName = "getDocketInvoiceByOrgId()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    String errorMsg = null;

		    Map<String, Object> responseObjectsMap = new HashMap<>();

		    ResponseDTO responseDTO = null;

		    List<DocketInvoiceResponseDTO> docketInvoiceResponseDTO =
		            new ArrayList<>();

		    try {

		        docketInvoiceResponseDTO =
		                transportMasterService.getDocketInvoiceByOrgId(
		                        orgId,
		                        branch);

		    } catch (Exception e) {

		        errorMsg = e.getMessage();

		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
		                methodName,
		                errorMsg);
		    }

		    if (StringUtils.isBlank(errorMsg)) {

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                "Docket Invoice retrieved successfully");

		        responseObjectsMap.put(
		                "docketInvoiceResponseDTO",
		                docketInvoiceResponseDTO);

		        responseDTO =
		                createServiceResponse(responseObjectsMap);

		    } else {

		        responseDTO =
		                createServiceResponseError(
		                        responseObjectsMap,
		                        "Docket Invoice retrieval failed",
		                        errorMsg);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		    return ResponseEntity.ok().body(responseDTO);

		}
}
		


