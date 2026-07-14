package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.service.DeleteStockService;

@CrossOrigin
@RestController
@RequestMapping("/api/deletestock")
public class DeleteStockController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(DeleteStockController.class);

	@Autowired
	DeleteStockService deleteStockService;

	@PutMapping("grnCancelApprove")
	public ResponseEntity<ResponseDTO> grnCancelApprove(@RequestParam Long orgId, @RequestParam Long id,
			@RequestParam String docId, @RequestParam String action, @RequestParam String actionBy,
			@RequestParam String supplierName) {

		String methodName = "grnCancelApprove()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {
			deleteStockService.grnCancelApprove(orgId, id, docId, action, actionBy, supplierName);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GRN Cancelled Successfully");
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, e.getMessage());
			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok(responseDTO);
	}

}
