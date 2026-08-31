package com.efitops.basesetup.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ToolMasterDTO;
import com.efitops.basesetup.service.ToolMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/toolmaster")

public class ToolMasterController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);
	
	@Autowired
	ToolMasterService toolMasterService;
	
	@PostMapping(value = "/updateCreateToolMaster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseDTO updateCreateToolMaster(
			@RequestPart("toolMasterVO") ToolMasterDTO toolMasterDTO,
//			@RequestBody ToolMasterDTO toolMasterDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {

		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO;

		try {

			Map<String, Object> response = toolMasterService.updateCreateToolMaster(toolMasterDTO, files);

			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, response.get("message"));
			responseObjectsMap.put("toolMasterVO", response.get("toolMasterVO"));

			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			e.printStackTrace();

			responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
		}

		return responseDTO;
	}

}
