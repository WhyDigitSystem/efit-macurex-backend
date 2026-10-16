package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.DespatchInstructionRepo;
import com.efitops.basesetup.repository.SalesRejectionInvoiceRepo;



@Service
public class ReportServiceImpl implements ReportService  {
	
	@Autowired
	private SalesRejectionInvoiceRepo salesRejectionInvoiceRepo;
	
	
	@Autowired
	private DespatchInstructionRepo despatchInstructionRepo;
	

	@Override
	public Map<String, Object> getRegularSaleCustomerWiseCumlReport(
	        String fromdate,
	        String todate,
	        Long orgId) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> regularSaleCustomerWiseCuml =
	            salesRejectionInvoiceRepo.getRegularSaleCustomerWiseCumlReport(
	                    fromdate,
	                    todate,
	                    orgId);

	    responseObjectsMap.put(
	            "regularSaleCustomerWiseCuml",
	            regularSaleCustomerWiseCuml);

	    responseObjectsMap.put(
	            "message",
	            "Regular Sale Customer Wise Cumulative Report Retrieved Successfully");

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getDespatchInstructionReport(
	        String fromdate,
	        String todate,
	        String division,
	        Long orgId,
	        Long branch) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> despatchReport =
	    		despatchInstructionRepo.getDespatchInstructionReport(
	                    fromdate,
	                    todate,
	                    division,
	                    orgId,
	                    branch);

	    responseObjectsMap.put(
	            "despatchReport",
	            despatchReport);

	    responseObjectsMap.put(
	            "message",
	            "Despatch Report Retrieved Successfully");

	    return responseObjectsMap;
	}
	
	
	//SalesRegisterAppliancesReport
	
	
	@Override
	public Map<String, Object> getSalesRegisterAppliancesReport(
	        String fromdate,
	        String todate,
	        Long orgId,
	        Long branch) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> salesRegisterReport =
	            salesRejectionInvoiceRepo.getSalesRegisterAppliancesReport(
	                    fromdate,
	                    todate,
	                    orgId,
	                    branch);

	    responseObjectsMap.put(
	            "salesRegisterReport",
	            salesRegisterReport);

	    responseObjectsMap.put(
	            "message",
	            "Sales Register Report Retrieved Successfully");

	    return responseObjectsMap;
	}
	
	//getSalesRegisterAppliancesReportMultiselectquery

	
	@Override
	public List<Map<String, Object>> getCustomerSalesRegisterAppliancesReport(
	        Long orgId,
	        Long branch) throws ApplicationException {

	    List<Object[]> customerList =
	    		salesRejectionInvoiceRepo.getCustomerSalesRegisterAppliancesReport(
	                    orgId,
	                    branch);

	    if (customerList == null || customerList.isEmpty()) {
	        throw new ApplicationException("Customer Not Found");
	    }

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : customerList) {

	        Map<String, Object> response = new HashMap<>();

	        response.put("id", obj[0]);
	        response.put("name", obj[1]);
	        

	        responseList.add(response);
	    }

	    return responseList;
	}
}