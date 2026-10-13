package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ReportService {

	Map<String, Object> getRegularSaleCustomerWiseCumlReport(String fromdate, String todate, Long orgId)
			throws ApplicationException;

	Map<String, Object> getDespatchInstructionReport(String fromdate, String todate, String division, Long orgId,
			Long branch) throws ApplicationException;

	Map<String, Object> getSalesRegisterAppliancesReport(String fromdate, String todate, Long orgId, Long branch)
			throws ApplicationException;

	List<Map<String, Object>> getCustomerSalesRegisterAppliancesReport(Long orgId, Long branch)
			throws ApplicationException;

	
	
	

}
