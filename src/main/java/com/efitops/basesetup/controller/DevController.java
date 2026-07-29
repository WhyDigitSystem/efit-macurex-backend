package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.efitops.basesetup.dto.DailyExchangeRateDTO;
import com.efitops.basesetup.dto.MappingOfPartyToAccDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.entity.MappingOfPartyToAccVO;
import com.efitops.basesetup.service.TransportMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/dev")
public class DevController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);
	@Autowired
	TransportMasterService transportMasterService;
	
	//Daily Exchange Rate
	 
	 @PutMapping("/updateCreateDailyExRate")
		public ResponseEntity<ResponseDTO> updateCreateDailyExRate(@RequestBody DailyExchangeRateDTO dailyExchangeRateDTO) {
			String methodName = "updateCreateDailyExRate()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> dailyExchangeRateVO = transportMasterService.updateCreateDailyExRate(dailyExchangeRateDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, dailyExchangeRateVO.get("message"));
				responseObjectsMap.put("dailyExchangeRateVO", dailyExchangeRateVO.get("dailyExchangeRateVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
	 @GetMapping("/getDailyExRateById")
	 public ResponseEntity<ResponseDTO> getDailyExRateById(@RequestParam Long id) {

	     String methodName = "getDailyExRateById()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	    	 DailyExchangeRateVO dailyExchangeRateVO = transportMasterService.getDailyExRateById(id);

	         responseObjectsMap.put("dailyExchangeRateVO", dailyExchangeRateVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	 
	 @GetMapping("/getDailyExRateByOrgId")
	 public ResponseEntity<ResponseDTO> getDailyExRateByOrgId(@RequestParam Long orgId,@RequestParam Long branch) {

	     String methodName = "getDailyExRateByOrgId()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         List<DailyExchangeRateVO> dailyExchangeRateVO = transportMasterService.getDailyExRateByOrgId(orgId,branch);

	         responseObjectsMap.put("dailyExchangeRateVO", dailyExchangeRateVO);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	
	//dropdown for currency filed
	 @GetMapping("/getCurrency")
	 public ResponseEntity<ResponseDTO> getCurrency(@RequestParam Long orgId) {

	     String methodName = "getCurrency()";
	     LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	     Map<String, Object> responseObjectsMap = new HashMap<>();
	     String errorMsg = null;
	     ResponseDTO responseDTO = null;

	     try {

	         responseObjectsMap = transportMasterService.getCurrency(orgId);

	         responseDTO = createServiceResponse(responseObjectsMap);

	     } catch (Exception e) {

	         errorMsg = e.getMessage();
	         LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);

	         responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	     }

	     LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

	     return ResponseEntity.ok().body(responseDTO);
	 }
	
}

