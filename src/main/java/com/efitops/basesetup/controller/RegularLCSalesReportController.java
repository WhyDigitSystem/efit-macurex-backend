package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.RegularLCSalesReportService;

@RestController
@RequestMapping("/api/regularLCSalesReport")
public class RegularLCSalesReportController extends BaseController {
	
	@Autowired
	RegularLCSalesReportService regularLCSalesReportService;

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImportController.class);

	@GetMapping("/getRegularLCSalesReport")
	public ResponseEntity<ResponseDTO> getRegularLCSalesReport(@RequestParam Long belongsTo, @RequestParam Long branch,
			@RequestParam Long orgId, @RequestParam String fromDate, @RequestParam String toDate) {

		String methodName = "getRegularLCSalesReport()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String errorMsg = null;

		try {

			responseObjectsMap = regularLCSalesReportService.getRegularLCSalesReport(belongsTo, branch, orgId, fromDate,
					toDate);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			errorMsg = e.getMessage();

			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);

			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

}
