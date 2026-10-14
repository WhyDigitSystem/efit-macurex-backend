package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.DocketInvoiceRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleRepo;
import com.efitops.basesetup.repository.SalesRejectionInvoiceRepo;

@Service
public class RegularLCSalesReportServiceImpl implements RegularLCSalesReportService {
	public static final Logger LOGGER = LoggerFactory.getLogger(RegularLCSalesReportServiceImpl.class);

	@Autowired
	private SalesRejectionInvoiceRepo salesRejectionInvoiceRepo;

	@Autowired
	private SalesDeliveryScheduleRepo salesDeliveryScheduleRepo;
	
	@Autowired
	private DocketInvoiceRepo docketInvoiceRepo;
	
	@Override
	public Map<String, Object> getRegularLCSalesReport(Long belongsTo, Long branch, Long orgId, String fromDate,
			String toDate) throws ApplicationException {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		List<Object[]> resultList = salesRejectionInvoiceRepo.getRegularLCSalesReport(belongsTo, branch, orgId,
				fromDate, toDate);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : resultList) {

			Map<String, Object> data = new HashMap<>();

			data.put("docid", obj[0]);
			data.put("docdt", obj[1]);
			data.put("cancel", obj[2]);
			data.put("cancelRemarks", obj[3]);
			data.put("partyid", obj[4]);
			data.put("partyname", obj[5]);
			data.put("pono", obj[6]);
			data.put("podt", obj[7]);
			data.put("itemid", obj[8]);
			data.put("itemdesc", obj[9]);
			data.put("cpart", obj[10]);
			data.put("unitid", obj[11]);
			data.put("qty", obj[12]);
			data.put("rate", obj[13]);
			data.put("amount", obj[14]);
			data.put("igst", obj[15]);
			data.put("sgst", obj[16]);
			data.put("cgst", obj[17]);
			data.put("tcs", obj[18]);
			data.put("grossamt", obj[19]);

			responseList.add(data);
		}

		responseObjectsMap.put("regularLCSalesReport", responseList);

		return responseObjectsMap;
	}

//	rejectioninvoice report

	@Override
	public Map<String, Object> getRejectionInvoiceReport(String fromDate, String toDate, Long branch, Long orgId)
			throws ApplicationException {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		List<Object[]> resultList = salesRejectionInvoiceRepo.getRejectionInvoiceReport(fromDate, toDate, branch,
				orgId);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : resultList) {

			Map<String, Object> data = new HashMap<>();

			data.put("docid", obj[0]);
			data.put("docdt", obj[1]);
			data.put("partyid", obj[2]);
			data.put("partyname", obj[3]);
			data.put("state", obj[4]);
			data.put("pono", obj[5]);
			data.put("podt", obj[6]);
			data.put("itemid", obj[7]);
			data.put("itemdesc", obj[8]);
			data.put("unitid", obj[9]);
			data.put("qty", obj[10]);
			data.put("rate", obj[11]);
			data.put("amount", obj[12]);
			data.put("vatval", obj[13]);

			responseList.add(data);
		}

		responseObjectsMap.put("rejectionInvoiceReport", responseList);

		return responseObjectsMap;
	}

	@Override
	public Map<String, Object> getRegularLCAppliancesReport(String fromDate, String toDate, Long branch, Long orgId)
			throws ApplicationException {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		List<Object[]> resultList = salesRejectionInvoiceRepo.getRegularLCAppliancesReport(fromDate, toDate, branch,
				orgId);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : resultList) {

			Map<String, Object> data = new HashMap<>();

			data.put("docid", obj[0]);
			data.put("docdt", obj[1]);
			data.put("cancel", obj[2]);
			data.put("cancelRemarks", obj[3]);
			data.put("partyid", obj[4]);
			data.put("partyname", obj[5]);
			data.put("pono", obj[6]);
			data.put("podt", obj[7]);
			data.put("pdino", obj[8]);
			data.put("cpart", obj[9]);
			data.put("itemid", obj[10]);
			data.put("itemdesc", obj[11]);
			data.put("unitid", obj[12]);
			data.put("qty", obj[13]);
			data.put("rate", obj[14]);
			data.put("amount", obj[15]);
			data.put("igst", obj[16]);
			data.put("sgst", obj[17]);
			data.put("cgst", obj[18]);
			data.put("tcs", obj[19]);
			data.put("grossamt", obj[20]);

			responseList.add(data);
		}

		responseObjectsMap.put("regularLCAppliancesReport", responseList);

		return responseObjectsMap;
	}

	@Override
	public Map<String, Object> getCustomerDetailsSales(String fromDate, String toDate, Long orgId, Long branch)
			throws ApplicationException {

		Map<String, Object> responseObjectsMap = new HashMap<>();

		List<Object[]> resultList = salesRejectionInvoiceRepo.getCustomerDetailsSales(fromDate, toDate, orgId, branch);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : resultList) {

			Map<String, Object> data = new HashMap<>();

			data.put("partyid", obj[0]);
			data.put("partyname", obj[1]);
			data.put("totamt", obj[2]);

			responseList.add(data);
		}

		responseObjectsMap.put("customerDetailsSales", responseList);

		return responseObjectsMap;
	}

//	schedule vs sales
	@Override
	public Map<String, Object> getScheduleVsSalesReport(String mon, Long under, Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> resultList = salesDeliveryScheduleRepo.getScheduleVsSalesReport(mon, under, branch, orgId);

		List<Map<String, Object>> reportList = new ArrayList<>();

		for (Object[] obj : resultList) {
			Map<String, Object> map = new HashMap<>();

			map.put("partyid", obj[0]);
			map.put("partyname", obj[1]);
			map.put("itemid", obj[2]);
			map.put("itemdesc", obj[3]);
			map.put("cpartno", obj[4]);
			map.put("pLqty", obj[5]);
			map.put("sqty", obj[6]);
			map.put("PQTY", obj[7]);
			map.put("shvalue", obj[8]);
			map.put("sales", obj[9]);
			map.put("RATE", obj[10]);

			reportList.add(map);
		}

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("scheduleVsSalesReport", reportList);

		return responseMap;
	}
	
//	invoice/docket details
	@Override
	public Map<String, Object> getInvoiceDocketDetailsReport(
	        String fromDate, String toDate, Long branch, Long orgId)
	        throws ApplicationException {

	    List<Object[]> resultList = docketInvoiceRepo
	            .getInvoiceDocketDetailsReport(fromDate, toDate, branch, orgId);

	    List<Map<String, Object>> reportList = new ArrayList<>();

	    for (Object[] obj : resultList) {
	        Map<String, Object> map = new HashMap<>();

	        map.put("transmastid", obj[0]);
	        map.put("transid", obj[1]);
	        map.put("dono", obj[2]);
	        map.put("docdate", obj[3]);
	        map.put("billno", obj[4]);
	        map.put("billdt", obj[5]);
	        map.put("totamt", obj[6]);

	        reportList.add(map);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    responseObjectsMap.put("invoiceDocketDetailsReport", reportList);

	    return responseObjectsMap;
	}
	
//	delivery schedule year wise
	@Override
	public Map<String, Object> getDeliveryScheduleYearWiseReport(
	        String year, String belongs, Long branch, Long orgId) throws ApplicationException {

	    List<Object[]> deliveryScheduleList = salesDeliveryScheduleRepo
	            .getDeliveryScheduleYearWiseReport(year, belongs, branch, orgId);

	    List<Map<String, Object>> reportList = new ArrayList<>();

	    for (Object[] obj : deliveryScheduleList) {
	        Map<String, Object> reportMap = new HashMap<>();

	        reportMap.put("ITEMID", obj[0]);
	        reportMap.put("ITEMDESCRIPTION", obj[1]);
	        reportMap.put("JAN", obj[2]);
	        reportMap.put("FEB", obj[3]);
	        reportMap.put("MAR", obj[4]);
	        reportMap.put("APR", obj[5]);
	        reportMap.put("MAY", obj[6]);
	        reportMap.put("JUN", obj[7]);
	        reportMap.put("JUL", obj[8]);
	        reportMap.put("AUG", obj[9]);
	        reportMap.put("SEP", obj[10]);
	        reportMap.put("OCT", obj[11]);
	        reportMap.put("NOV", obj[12]);
	        reportMap.put("DECE", obj[13]);

	        reportList.add(reportMap);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    responseObjectsMap.put("deliveryScheduleYearWiseReport", reportList);

	    return responseObjectsMap;
	}
}
