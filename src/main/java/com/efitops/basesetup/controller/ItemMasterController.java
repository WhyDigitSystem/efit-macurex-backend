package com.efitops.basesetup.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.common.UserConstants;
import com.efitops.basesetup.dto.ItemMasterDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.service.ItemMasterService;

@RestController
@RequestMapping("/api/itemMaster")
public class ItemMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(ItemMasterController.class);

	@Autowired
	ItemMasterService itemMasterService;

	// TaxInvoice

	@GetMapping("/getItemMasterById")
	public ResponseEntity<ResponseDTO> getItemMasterById(@RequestParam Long id) {
		String methodName = "getItemMasterById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ItemMasterVO itemMasterVO = new ItemMasterVO();
		try {
			itemMasterVO = itemMasterService.getItemMasterById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemMaster information get successfully By id");
			responseObjectsMap.put("itemMasterVO", itemMasterVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ItemMaster information receive failedByOrgId",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCreateItemMaster")
	public ResponseEntity<ResponseDTO> updateCreateItemMaster(@RequestBody ItemMasterDTO itemMasterDTO) {
		String methodName = "updateCreateItemMaster()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> itemMasterVO = itemMasterService.updateCreateItemMaster(itemMasterDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, itemMasterVO.get("message"));
			responseObjectsMap.put("itemMasterVO", itemMasterVO.get("itemMasterVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/uploadImageItemMasterDetails")
	public ResponseEntity<ResponseDTO> uploadImageItemMasterDetails(@RequestParam List<MultipartFile> files,
			@RequestParam Long itemMasterId, @RequestParam List<Long> itemDrawingId) {

		String methodName = "uploadImageItemMasterDetails()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		Map<String, Object> responseObjectsMap = new HashMap<>();

		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = itemMasterService.uploadImageItemMasterDetails(files, itemMasterId,
					itemDrawingId);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ItemImage Uploaded Successfully");

			responseObjectsMap.put("response", response);

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			responseDTO = createServiceResponseError(responseObjectsMap, "ItemImage  Upload Failed", e.getMessage());
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/viewFile/**")
	public ResponseEntity<byte[]> viewItemMasterImages(HttpServletRequest request) throws IOException {

		return itemMasterService.viewItemMasterImages(request);
	}

}
