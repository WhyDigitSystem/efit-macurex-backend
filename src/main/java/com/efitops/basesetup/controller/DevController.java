package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.CustomerComplaintDTO;
import com.efitops.basesetup.dto.CustomerComplaintResponseDTO;
import com.efitops.basesetup.dto.DailyExchangeRateDTO;
import com.efitops.basesetup.dto.MappingOfPartyToAccDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.CustomerComplaintEntryVO;
import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.entity.MappingOfPartyToAccVO;
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
	
	 @PutMapping(value = "/updateCreateCustomerComplaint",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ResponseEntity<ResponseDTO> updateCreateCustomerComplaint(
		        @ModelAttribute CustomerComplaintDTO customerComplaintDTO) { 
			String methodName = "updateCreateCustomerComplaint()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> customerComplaintEntryVO = transportMasterService.updateCreateCustomerComplaint(customerComplaintDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, customerComplaintEntryVO.get("message"));
				responseObjectsMap.put("customerComplaintEntryVO", customerComplaintEntryVO.get("customerComplaintEntryVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
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
	 public ResponseEntity<?> getPreparedBy(
	         @RequestParam Long departmentId)
	         throws ApplicationException {

	     return ResponseEntity.ok(
	    		 transportMasterService.getPreparedBy(departmentId));
	 }
	 
	 //drop down for item
	 @GetMapping("/getItem")
	 public ResponseEntity<ResponseDTO> getItemDropdown() throws ApplicationException {

	     ResponseDTO responseDTO = new ResponseDTO();
	     responseDTO.setStatus(true);
	     responseDTO.setStatusFlag("Ok");
	     responseDTO.setParamObjectsMap(transportMasterService.getItem());

	     return ResponseEntity.ok(responseDTO);
	 }
	 
	 @GetMapping("/getItemDetails/{itemId}")
	 public ResponseEntity<ResponseDTO> getItemDetails(@RequestParam Long itemId)
	         throws ApplicationException {

	     ResponseDTO responseDTO = new ResponseDTO();
	     responseDTO.setStatus(true);
	     responseDTO.setStatusFlag("Ok");
	     responseDTO.setParamObjectsMap(transportMasterService.getItemDetails(itemId));

	     return ResponseEntity.ok(responseDTO);
	 }
	
	// branch dropdown
	 @GetMapping("/getBranch")
	 public ResponseEntity<ResponseDTO> getBranch() throws ApplicationException {

	     ResponseDTO responseDTO = new ResponseDTO();

	     responseDTO.setStatus(true);
	     responseDTO.setStatusFlag("Ok");
	     responseDTO.setParamObjectsMap(transportMasterService.getBranch());

	     return ResponseEntity.ok(responseDTO);
	 }
	 
	 //belongs to
	 
	 @GetMapping("/getBelongsTo")
	 public ResponseEntity<ResponseDTO> getTypeDropdown() throws ApplicationException {

	     ResponseDTO responseDTO = new ResponseDTO();
	     responseDTO.setStatus(true);
	     responseDTO.setStatusFlag("Ok");
	     responseDTO.setParamObjectsMap(transportMasterService.getTypeDropdown());

	     return ResponseEntity.ok(responseDTO);
	 }
	 
	 //department 
	 @GetMapping("/getDepartment")
	 public ResponseEntity<ResponseDTO> getDepartment() throws ApplicationException {

	     ResponseDTO responseDTO = new ResponseDTO();
	     responseDTO.setStatus(true);
	     responseDTO.setStatusFlag("Ok");
	     responseDTO.setParamObjectsMap(transportMasterService.getDepartment());

	     return ResponseEntity.ok(responseDTO);
	 }
	 
	 //Customer drowpdown
	 @GetMapping("/getCustomer")
	 public ResponseEntity<ResponseDTO> getCustomer() throws ApplicationException {

	     ResponseDTO responseDTO = new ResponseDTO();

	     responseDTO.setStatus(true);
	     responseDTO.setStatusFlag("Ok");
	     responseDTO.setParamObjectsMap(transportMasterService.getCustomer());

	     return ResponseEntity.ok(responseDTO);
	 }
	 
	 @GetMapping("/getCustomerDetails/{customerId}")
	 public ResponseEntity<ResponseDTO> getCustomerDetails(
	         @RequestParam String customerId)
	         throws ApplicationException {

	     ResponseDTO responseDTO = new ResponseDTO();

	     responseDTO.setStatus(true);
	     responseDTO.setStatusFlag("Ok");
	     responseDTO.setParamObjectsMap(
	    		 transportMasterService.getCustomerDetails(customerId));

	     return ResponseEntity.ok(responseDTO);
	 }
}

