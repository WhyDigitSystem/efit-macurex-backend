
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
import com.efitops.basesetup.ResponseDTO.StockTransferChallanResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.DespatchInstructionDTO;
import com.efitops.basesetup.dto.DocketInvoiceDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.dto.StockTransferChallanDTO;
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
//	        @RequestPart("customerComplaint") CustomerComplaintDTO customerComplaintDTO,
	        @RequestBody CustomerComplaintDTO customerComplaintDTO,
	        @RequestPart(value = "images", required = false) MultipartFile[] images) {

	    String methodName = "updateCreateCustomerComplaint";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        Map<String, Object> responseMap =
	                transportMasterService.updateCreateCustomerComplaint(customerComplaintDTO, images);

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
		
	
		//Stock Tranfer Challan
		@PostMapping("/updateCreateStockTransferChallan")
		public ResponseEntity<ResponseDTO> updateCreateStockTransferChallan(
		        @RequestBody StockTransferChallanDTO stockTransferChallanDTO) {

			String methodName = "updateCreateStockTransferChallan()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

			Map<String, Object> responseObjectsMap = new HashMap<>();
			String errorMsg = null;
			ResponseDTO responseDTO = null;

			try {

				Map<String, Object> responseMap = transportMasterService
						.updateCreateStockTransferChallan(stockTransferChallanDTO);

				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responseMap.get("message"));
				responseObjectsMap.put("stockTransferChallanVO",
						responseMap.get("stockTransferChallanVO"));

				responseDTO = createServiceResponse(responseObjectsMap);

			} catch (Exception e) {

				errorMsg = e.getMessage();

				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}

			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		@GetMapping("/getStockTransferChallanById")
		public ResponseEntity<ResponseDTO> getStockTransferChallanById(
		        @RequestParam Long id) {

		    String methodName = "getStockTransferChallanById()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    String errorMsg = null;

		    Map<String, Object> responseObjectsMap = new HashMap<>();

		    ResponseDTO responseDTO = null;

		    StockTransferChallanResponseDTO stockTransferChallanResponseDTO = null;

		    try {

		    	stockTransferChallanResponseDTO =
		                transportMasterService.getStockTransferChallanById(id);

		    } catch (Exception e) {

		        errorMsg = e.getMessage();

		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME,
		                methodName,
		                errorMsg);
		    }

		    if (StringUtils.isBlank(errorMsg)) {

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                " Stock Transfer retrieved successfully");

		        responseObjectsMap.put(
		                "stockTransferChallanResponseDTO",
		                stockTransferChallanResponseDTO);

		        responseDTO =
		                createServiceResponse(responseObjectsMap);

		    } else {

		        responseDTO =
		                createServiceResponseError(
		                        responseObjectsMap,
		                        " stock Transfer Challan retrieval failed",
		                        errorMsg);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		    return ResponseEntity.ok().body(responseDTO);

		}
		
		@GetMapping("/getStockTransferChallanByOrgId")
		public ResponseEntity<ResponseDTO> getStockTransferChallanByOrgId(
		        @RequestParam Long orgId,
		        @RequestParam Long branch) {

		    String methodName = "getStockTransferChallanByOrgId()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    String errorMsg = null;

		    Map<String, Object> responseObjectsMap = new HashMap<>();

		    ResponseDTO responseDTO = null;

		    List<StockTransferChallanResponseDTO> stockTransferChallanResponseDTO =
		            new ArrayList<>();

		    try {

		    	stockTransferChallanResponseDTO =
		                transportMasterService.getStockTransferChallanByOrgId(
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
		                " Stock Transfer Challan retrieved successfully");

		        responseObjectsMap.put(
		                "stockTransferChallanResponseDTO",
		                stockTransferChallanResponseDTO);

		        responseDTO =
		                createServiceResponse(responseObjectsMap);

		    } else {

		        responseDTO =
		                createServiceResponseError(
		                        responseObjectsMap,
		                        "Stock transfer challan retrieval failed",
		                        errorMsg);
		    }

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		    return ResponseEntity.ok().body(responseDTO);

		}
		//dropdown getstocktransfercustomer
		@GetMapping("/getCustomerForStockTransferChallan")
		public ResponseEntity<ResponseDTO> getCustomerForStockTransferChallan(@RequestParam Long branch,@RequestParam Long orgId) {

		    String methodName = "getCustomerForStockTransferChallan()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.getCustomerForStockTransferChallan(branch,orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "customerList",
		                responseMap.get("customerList"));

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
		//despatch instruction schedule no dropdown
		@GetMapping("/getScheduleNoDropdownForDespatchInstruction")
		public ResponseEntity<ResponseDTO> getScheduleNoDropdownForDespatchInstruction(
		        @RequestParam Long customer,
		        @RequestParam String monthYear,
		        @RequestParam Long branch,
		        @RequestParam Long orgId) {

		    String methodName = "getScheduleNoDropdownForDespatchInstruction()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.getScheduleNoDropdownForDespatchInstruction(
		                        customer,
		                        monthYear,
		                        branch,
		                        orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "scheduleBalanceList",
		                responseMap.get("scheduleBalanceList"));

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
		//despatch dropdown for customer
		@GetMapping("/getCustomerDropdownForDespatchInstructions")
		public ResponseEntity<ResponseDTO> getCustomerDropdownForDespatchInstructions(
				@RequestParam Long branch,
				 @RequestParam Long orgId) {

		    String methodName = "getCustomerDropdownForDespatchInstructions()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.getCustomerDropdownForDespatchInstructions(branch,orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "customerList",
		                responseMap.get("customerList"));

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
		//Despatch orderacceptanceandsalesContract Dropdown
		@GetMapping("/getOrderAndSalesContractDropdownFromDespatchInstruction")
		public ResponseEntity<ResponseDTO> getOrderAndSalesContractDropdownFromDespatchInstruction(
		        @RequestParam Long customerId,@RequestParam Long branch,@RequestParam Long orgId) {

		    String methodName = "getOrderAndSalesContractDropdownFromDespatchInstruction()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.getOrderAndSalesContractDropdownFromDespatchInstruction(customerId,branch,orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "salesContractList",
		                responseMap.get("salesContractList"));

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
		//despatch instruction items
		@GetMapping("/getItemsFromDespatchInstruction")
		public ResponseEntity<ResponseDTO> getItemsFromDespatchInstruction(@RequestParam Long item_type,@RequestParam Long branch,@RequestParam Long orgId) {

		    String methodName = "getItemsFromDespatchInstruction()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.getItemsFromDespatchInstruction(item_type,branch,orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "itemList",
		                responseMap.get("itemList"));

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
		
		//Despatch schedule month 
		@GetMapping("/getScheduleMonthForDespatchInstruction")
		public ResponseEntity<ResponseDTO> getScheduleMonthForDespatchInstruction(
		        @RequestParam Long item,
		        @RequestParam String dlvno,
		        @RequestParam Long branch,
		        @RequestParam Long orgId) {

		    String methodName = "getScheduleMonthForDespatchInstruction()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.getScheduleMonthForDespatchInstruction(item,dlvno, branch, orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "scheduleMonthList",
		                responseMap.get("scheduleMonthList"));

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
		
		//despatch instruction planned qty
		@GetMapping("/getPlannedQtyForDespatchInstruction")
		public ResponseEntity<ResponseDTO> getPlannedQtyForDespatchInstruction(
		        @RequestParam Long item,
		        @RequestParam Long branch,
		        @RequestParam Long orgId) {

		    String methodName = "getPlannedQtyForDespatchInstruction()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.getPlannedQtyForDespatchInstruction(
		                        item,
		                        branch,
		                        orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "plannedQty",
		                responseMap.get("plannedQty"));

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
		
		// despatch pending qty
		
//		@GetMapping("/getDespatchPendingQty")
//		public ResponseEntity<ResponseDTO> getDespatchPendingQty(
//		        @RequestParam Long itemId,
//		        @RequestParam String month,
//		        @RequestParam Long branch,
//		        @RequestParam Long orgId,
//		        @RequestParam Long customerId) {
//
//		    String methodName = "getDespatchPendingQty()";
//		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//		    Map<String, Object> responseObjectsMap = new HashMap<>();
//		    ResponseDTO responseDTO;
//
//		    try {
//
//		        Map<String, Object> responseMap =
//		                transportMasterService.getDespatchPendingQty(
//		                        itemId,
//		                        month,
//		                        branch,
//		                        orgId,
//		                        customerId);
//
//		        responseObjectsMap.put(
//		                CommonConstant.STRING_MESSAGE,
//		                responseMap.get("message"));
//
//		        responseObjectsMap.put(
//		                "pendingQtyList",
//		                responseMap.get("pendingQtyList"));
//
//		        responseDTO = createServiceResponse(responseObjectsMap);
//
//		    } catch (Exception e) {
//
//		        LOGGER.error(
//		                UserConstants.ERROR_MSG_METHOD_NAME,
//		                methodName,
//		                e.getMessage(),
//		                e);
//
//		        responseDTO = createServiceResponseError(
//		                responseObjectsMap,
//		                e.getMessage(),
//		                e.getMessage());
//		    }
//
//		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//
//		    return ResponseEntity.ok(responseDTO);
//		}
		
		
//   fillgrid api for despatch instruction 
		
		@GetMapping("/getFillGridItemsForDespatchInstruction")
		public ResponseEntity<ResponseDTO> getFillGridItemsForDespatchInstruction(
		        @RequestParam Long customerId,
		        @RequestParam Long sdvBasicId,
		        @RequestParam Long branch,
		        @RequestParam Long orgId) {

		    String methodName = "getFillGridItemsForDespatchInstruction()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        List<Map<String, Object>> itemList =
		                transportMasterService.getFillGridItemsForDespatchInstruction(
		                        customerId,
		                        sdvBasicId,
		                        branch,
		                        orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                "Despatch Instruction Grid Fetched Successfully");

		        responseObjectsMap.put(
		                "itemList",
		                itemList);

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
		
		//item drop down for stocktransfer challan
		@GetMapping("/getItemsForStockTransferChallan")
		public ResponseEntity<ResponseDTO> getItemsForStockTransferChallan(
		        @RequestParam Long branch,
		        @RequestParam Long orgId) {

		    String methodName = "getItemsForStockTransferChallan()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO;

		    try {

		        Map<String, Object> responseMap =
		                transportMasterService.getItemsForStockTransferChallan(
		                        branch,
		                        orgId);

		        responseObjectsMap.put(
		                CommonConstant.STRING_MESSAGE,
		                responseMap.get("message"));

		        responseObjectsMap.put(
		                "itemList",
		                responseMap.get("itemList"));

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
}


		


