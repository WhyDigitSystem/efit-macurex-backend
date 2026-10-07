package com.efitops.basesetup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efitops.basesetup.service.ReportService;

@CrossOrigin
@RestController

@RequestMapping("/api/report")

public class ReportController extends BaseController {
	
	@Autowired
	private ReportService reportService;
	
	

}
