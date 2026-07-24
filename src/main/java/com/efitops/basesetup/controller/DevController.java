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
import com.efitops.basesetup.dto.GSTRateMasterDTO;
import com.efitops.basesetup.dto.ListOfValuesDTO;
import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.service.TransportMasterService;



@CrossOrigin
@RestController
@RequestMapping("/api/dev")
public class DevController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DevController.class);
	@Autowired
	TransportMasterService transportMasterService;
	
	
	 
}
