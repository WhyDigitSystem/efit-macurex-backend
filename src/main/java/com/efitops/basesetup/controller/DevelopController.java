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
import com.efitops.basesetup.dto.DocumentTypeMappingDTO;

import com.efitops.basesetup.dto.ResponseDTO;
import com.efitops.basesetup.dto.SalesZoneMasterDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.SalesZoneMasterVO;
import com.efitops.basesetup.service.DevelopService;



@CrossOrigin
@RestController
@RequestMapping("/api/develop")
public class  DevelopController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopController.class);

    @Autowired
    private DevelopService developService;
    
    
  
}
	
	




