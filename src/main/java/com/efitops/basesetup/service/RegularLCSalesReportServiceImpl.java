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
import com.efitops.basesetup.repository.AdvForStoresRepo;
import com.efitops.basesetup.repository.BillOfMaterialRepo;
import com.efitops.basesetup.repository.BomRepo;
import com.efitops.basesetup.repository.DocketInvoiceRepo;
import com.efitops.basesetup.repository.EngineeringChangeRecordRepo;
import com.efitops.basesetup.repository.GateInwardEntryRepo;
import com.efitops.basesetup.repository.InternalIndentRepo;
import com.efitops.basesetup.repository.IssuesRepo;
import com.efitops.basesetup.repository.JobOrderRepo;
import com.efitops.basesetup.repository.PhysicalStockReConcilationRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleRepo;
import com.efitops.basesetup.repository.SalesRejectionInvoiceRepo;
import com.efitops.basesetup.repository.SubContractingGRNDetailsRepo;
import com.efitops.basesetup.repository.SubContractingGRNRepo;
import com.efitops.basesetup.repository.SupplierRateContractRepo;

@Service
public class RegularLCSalesReportServiceImpl implements RegularLCSalesReportService {
	public static final Logger LOGGER = LoggerFactory.getLogger(RegularLCSalesReportServiceImpl.class);

	@Autowired
	private SalesRejectionInvoiceRepo salesRejectionInvoiceRepo;

	@Autowired
	private SalesDeliveryScheduleRepo salesDeliveryScheduleRepo;
	
	@Autowired
	private DocketInvoiceRepo docketInvoiceRepo;
	

	@Autowired
	private SalesContractRepo salesContractRepo;
	
	@Autowired
	private PhysicalStockReConcilationRepo physicalStockReConcilationRepo;
	
	@Autowired
	private IssuesRepo issuesRepo;
	
	@Autowired
	private InternalIndentRepo internalIndentRepo;
	
	@Autowired
	private SupplierRateContractRepo supplierRateContractRepo;
	
	@Autowired
	private JobOrderRepo jobOrderRepo;
	
	@Autowired
	private AdvForStoresRepo advForStoresRepo;
	
	@Autowired
	private SubContractingGRNRepo subContractingGRNRepo;
	
	@Autowired
	private BomRepo bomRepo;
	
	@Autowired
	private BillOfMaterialRepo billOfMaterialRepo;
	
	@Autowired
	private EngineeringChangeRecordRepo engineeringChangeRecordRepo;
	
	@Autowired
	private GateInwardEntryRepo gateInwardEntryRepo;
	
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
	
//	sales contract approval report
	
	@Override
	public Map<String, Object> getSalesContractApprovalReport(
	        String frdt, String endt, Long branch, Long orgId) throws ApplicationException {

	    List<Object[]> salesContractList = salesContractRepo
	            .getSalesContractApprovalReport(frdt, endt, branch, orgId);

	    List<Map<String, Object>> reportList = new ArrayList<>();

	    for (Object[] obj : salesContractList) {
	        Map<String, Object> reportMap = new HashMap<>();

	        reportMap.put("ORDRECDNO", obj[0]);
	        reportMap.put("ORDRECDDT", obj[1]);
	        reportMap.put("partyid", obj[2]);
	        reportMap.put("partyname", obj[3]);
	        reportMap.put("approval", obj[4]);
	        reportMap.put("schagrbasicid", obj[5]);
	        reportMap.put("note", obj[6]);

	        reportList.add(reportMap);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    responseObjectsMap.put("salesContractApprovalReport", reportList);

	    return responseObjectsMap;
	}
	
//	physical stock reconcilation
	
	@Override
	public Map<String, Object> getPhysicalStockReconciliationReport(
	        Long orgId, Long branch, String fromDate, String toDate)
	        throws ApplicationException {

	    List<Object[]> physicalStockList = physicalStockReConcilationRepo
	            .getPhysicalStockReconciliationReport(
	                    orgId, branch, fromDate, toDate);

	    List<Map<String, Object>> reportList = new ArrayList<>();

	    for (Object[] obj : physicalStockList) {
	        Map<String, Object> reportMap = new HashMap<>();

	        reportMap.put("phystheadid", obj[0]);
	        reportMap.put("reconid", obj[1]);
	        reportMap.put("recondt", obj[2]);
	        reportMap.put("appprod", obj[3]);
	        reportMap.put("ttime", obj[4]);
	        reportMap.put("PREBY", obj[5]);

	        reportList.add(reportMap);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    responseObjectsMap.put("physicalStockReconciliationReport", reportList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getIssueReport(
	        String fromDate, String toDate, String bel,
	        Long branch, Long orgId) throws ApplicationException {

	    List<Object[]> issueList = issuesRepo.getIssueReport(
	            fromDate, toDate, bel, branch, orgId);

	    List<Map<String, Object>> reportList = new ArrayList<>();

	    for (Object[] obj : issueList) {
	        Map<String, Object> reportMap = new HashMap<>();

	        reportMap.put("LOCID", obj[0]);
	        reportMap.put("issueto", obj[1]);
	        reportMap.put("ISSNO", obj[2]);
	        reportMap.put("UNDER", obj[3]);
	        reportMap.put("ISSDT", obj[4]);
	        reportMap.put("ITEMID", obj[5]);
	        reportMap.put("ITEMDESC", obj[6]);
	        reportMap.put("ISSQTY", obj[7]);
	        reportMap.put("LPORATE", obj[8]);
	        reportMap.put("Value", obj[9]);

	        reportList.add(reportMap);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    responseObjectsMap.put("issueReport", reportList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getInternalIndentReport(
	        String fromDate, String toDate, String bel,Long branch,Long orgId)
	        throws ApplicationException {

	    List<Object[]> indentList = internalIndentRepo
	            .getInternalIndentReport(fromDate, toDate, bel,branch,orgId);

	    List<Map<String, Object>> reportList = new ArrayList<>();

	    for (Object[] obj : indentList) {
	        Map<String, Object> reportMap = new HashMap<>();

	        reportMap.put("DEPTNAME", obj[0]);
	        reportMap.put("DOCID", obj[1]);
	        reportMap.put("indtime", obj[2]);
	        reportMap.put("DOCDATE", obj[3]);
	        reportMap.put("PREBY", obj[4]);
	        reportMap.put("ITEMID", obj[5]);
	        reportMap.put("ITEMDESC", obj[6]);
	        reportMap.put("QTY", obj[7]);
	        reportMap.put("DELIVERYDATE", obj[8]);

	        reportList.add(reportMap);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    responseObjectsMap.put("internalIndentReport", reportList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getSupplierRateContractReport(
	        Long branch,Long orgId, String frdt, String todt) throws ApplicationException {

	    List<Object[]> supplierRateContractList =
	            supplierRateContractRepo.getSupplierRateContractReport(
	                    branch,orgId, frdt, todt);

	    List<Map<String, Object>> reportList = new ArrayList<>();

	    for (Object[] obj : supplierRateContractList) {
	        Map<String, Object> reportMap = new HashMap<>();

	        reportMap.put("CONTNO", obj[0]);
	        reportMap.put("CONTDT", obj[1]);
	        reportMap.put("PARTYNAME", obj[2]);
	        reportMap.put("CONFOR", obj[3]);
	        reportMap.put("AMOUNT", obj[4]);
	        reportMap.put("validfr", obj[5]);
	        reportMap.put("validto", obj[6]);
	        reportMap.put("taxdesc", obj[7]);
	        reportMap.put("ITEMID", obj[8]);
	        reportMap.put("ITEMDESC", obj[9]);

	        reportList.add(reportMap);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    responseObjectsMap.put("supplierRateContractReport", reportList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getJobOrderStatusReport(
	        Long branch, String fromDate, String toDate) throws ApplicationException {

	    List<Object[]> jobOrderStatusList =
	            jobOrderRepo.getJobOrderStatusReport(branch, fromDate, toDate);

	    List<Map<String, Object>> reportList = new ArrayList<>();

	    for (Object[] obj : jobOrderStatusList) {
	        Map<String, Object> reportMap = new HashMap<>();

	        reportMap.put("wkorderbasicid", obj[0]);
	        reportMap.put("WONO", obj[1]);
	        reportMap.put("WODT", obj[2]);
	        reportMap.put("PARTYNAME", obj[3]);
	        reportMap.put("contno", obj[4]);
	        reportMap.put("INCOMINGID", obj[5]);
	        reportMap.put("unitid", obj[6]);
	        reportMap.put("grnrecqty", obj[7]);
	        reportMap.put("jobqty", obj[8]);
	        reportMap.put("PendQty", obj[9]);
	        reportMap.put("partyid", obj[10]);

	        reportList.add(reportMap);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    responseObjectsMap.put("jobOrderStatusReport", reportList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getJobOrderdetailsReport(
	        String fromdt,
	        String todate,
	        Long branch,
	        Long orgId) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> jobOrderDetailsList =
	            jobOrderRepo.getJobOrderdetailsReport(
	                    fromdt, todate, branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : jobOrderDetailsList) {
	        Map<String, Object> map = new HashMap<>();

	        map.put("WONO", obj[0]);
	        map.put("WODT", obj[1]);
	        map.put("PARTYNAME", obj[2]);
	        map.put("INCOMINGID", obj[3]);
	        map.put("unitid", obj[4]);
	        map.put("partyid", obj[5]);
	        map.put("itemid", obj[6]);
	        map.put("itemdesc", obj[7]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("jobOrderDetailsReport", responseList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getStoresAdviceReport(
	        String fromDate,
	        String toDate,
	        Long branch,
	        Long orgId) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> storesAdviceList =
	            advForStoresRepo.getStoresAdviceReport(
	                    fromDate, toDate, branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : storesAdviceList) {
	        Map<String, Object> map = new HashMap<>();

	        map.put("docno", obj[0]);
	        map.put("docdt", obj[1]);
	        map.put("partyid", obj[2]);
	        map.put("partyname", obj[3]);
	        map.put("itemid", obj[4]);
	        map.put("itemdesc", obj[5]);
	        map.put("issqty", obj[6]);
	        map.put("TIM", obj[7]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("storesAdviceReport", responseList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getSubContractingGrnPendingListForInspection(
	        String fromDate,
	        String toDate,
	        Long locId,
	        Long branch,
	        Long orgId) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> grnPendingList =
	            subContractingGRNRepo.getSubContractingGrnPendingListForInspection(
	                    fromDate, toDate, locId, branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : grnPendingList) {
	        Map<String, Object> map = new HashMap<>();

	        map.put("GRNNO", obj[0]);
	        map.put("GRNDT", obj[1]);
	        map.put("stime", obj[2]);
	        map.put("ITEMID", obj[3]);
	        map.put("ITEMDESC", obj[4]);
	        map.put("RECQTY", obj[5]);
	        map.put("RATE", obj[6]);
	        map.put("accqty", obj[7]);
	        map.put("rejqty", obj[8]);
	        map.put("prate", obj[9]);
	        map.put("PARTYID", obj[10]);
	        map.put("PARTYNAME", obj[11]);
	        map.put("supdcno", obj[12]);
	        map.put("subgrnBASICID", obj[13]);
	        map.put("PAty", obj[14]);
	        map.put("GRNID", obj[15]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("subContractingGrnPendingListForInspection", responseList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getSubContractingGrnItemWiseReport(
	        String fromDate,
	        String toDate,
	        Long plant,
	        Long locId,
	        Long branch,
	        Long orgId) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> grnItemWiseList =
	            subContractingGRNRepo.getSubContractingGrnItemWiseReport(
	                    fromDate, toDate, plant, locId, branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : grnItemWiseList) {
	        Map<String, Object> map = new HashMap<>();

	        map.put("customer", obj[0]);
	        map.put("GRNNO", obj[1]);
	        map.put("GRNDT", obj[2]);
	        map.put("grnno1", obj[3]);
	        map.put("time", obj[4]);
	        map.put("supdcno", obj[5]);
	        map.put("supdcdt", obj[6]);
	        map.put("PARTYNAME", obj[7]);
	        map.put("ITEMDESC", obj[8]);
	        map.put("UNITID", obj[9]);
	        map.put("BATCHQTY", obj[10]);
	        map.put("RECQTY", obj[11]);
	        map.put("ACCQTY", obj[12]);
	        map.put("REJQTY", obj[13]);
	        map.put("itemid", obj[14]);
	        map.put("prate", obj[15]);
	        map.put("jormcost", obj[16]);
	        map.put("partyid", obj[17]);
	        map.put("buycode", obj[18]);
	        map.put("remarks", obj[19]);
	        map.put("SUBGRNBASICid", obj[20]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("subContractGrnItemWiseReport", responseList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getBomTreeReportLive(
	        String itemCode, String branch, Long orgId) throws ApplicationException {

	    List<Object[]> bomTreeReportList =
	            bomRepo.getBomTreeReportLive(itemCode, branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : bomTreeReportList) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("bombasicid", obj[0]);
	        map.put("parentid", obj[1]);
	        map.put("pitemid", obj[2]);
	        map.put("pitemdesc", obj[3]);
	        map.put("ptype", obj[4]);
	        map.put("rmid", obj[5]);
	        map.put("itemid", obj[6]);
	        map.put("ManB", obj[7]);
	        map.put("qty", obj[8]);
	        map.put("first_parent_id", obj[9]);
	        map.put("first_parent_name", obj[10]);
	        map.put("first_parent_item", obj[11]);
	        map.put("levelno", obj[12]);
	        map.put("orderno", obj[13]);
	        map.put("itemtree", obj[14]);
	        map.put("itemdesc", obj[15]);
	        map.put("itemtype", obj[16]);
	        map.put("unitid", obj[17]);
	        map.put("cpartno", obj[18]);
	        map.put("bomid", obj[19]);
	        map.put("wef", obj[20]);

	        responseList.add(map);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    responseObjectsMap.put("bomTreeReportLive", responseList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getBomTreeAfterCorrectionReport(
	        String itemCode, String branch, Long orgId) throws ApplicationException {

	    List<Object[]> bomTreeList = bomRepo.getBomTreeAfterCorrectionReport(
	            itemCode, branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] obj : bomTreeList) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("bombasicid", obj[0]);
	        map.put("parentid", obj[1]);
	        map.put("pitemid", obj[2]);
	        map.put("pitemdesc", obj[3]);
	        map.put("ptype", obj[4]);
	        map.put("rmid", obj[5]);
	        map.put("itemid", obj[6]);
	        map.put("ManB", obj[7]);
	        map.put("qty", obj[8]);
	        map.put("first_parent_id", obj[9]);
	        map.put("first_parent_name", obj[10]);
	        map.put("first_parent_item", obj[11]);
	        map.put("levelno", obj[12]);
	        map.put("orderno", obj[13]);
	        map.put("itemtree", obj[14]);
	        map.put("itemdesc", obj[15]);
	        map.put("itemtype", obj[16]);
	        map.put("unitid", obj[17]);
	        map.put("cpartno", obj[18]);
	        map.put("wef", obj[19]);
	        map.put("revno", obj[20]);

	        responseList.add(map);
	    }

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    responseObjectsMap.put("bomTreeAfterCorrectionReport", responseList);

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getBomListFgReport(
	        Long branch, Long orgId) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> bomList = billOfMaterialRepo.getBomListFgReport(
	            branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] row : bomList) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("docid", row[0]);
	        map.put("docdt", row[1]);
	        map.put("itemid", row[2]);
	        map.put("itemdesc", row[3]);
	        map.put("revno", row[4]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("bomListFgReport", responseList);
	    responseObjectsMap.put("message", "BOM List FG report fetched successfully");

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getEngineeringChangeRecordReport(
	        Long branch, Long orgId) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> resultList = engineeringChangeRecordRepo
	            .getEngineeringChangeRecordReport(branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] row : resultList) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("branch", row[0]);
	        map.put("docId", row[1]);
	        map.put("approved", row[2]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("engineeringChangeRecordList", responseList);
	    responseObjectsMap.put("message", "Engineering Change Record report fetched successfully");

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getGateInwarddetails(
	        Long branch, Long orgId, String startDate, String endDate)
	        throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> resultList = gateInwardEntryRepo.getGateInwarddetails(
	            branch, orgId, startDate, endDate);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] row : resultList) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("gpno", row[0]);
	        map.put("gpdate", row[1]);
	        map.put("partyid", row[2]);
	        map.put("supdcno", row[3]);
	        map.put("supdcdt", row[4]);
	        map.put("partyname", row[5]);
	        map.put("itemid", row[6]);
	        map.put("itemdesc", row[7]);
	        map.put("unitid", row[8]);
	        map.put("gpqty", row[9]);
	        map.put("invctr", row[10]);
	        map.put("rhh", row[11]);
	        map.put("pono", row[12]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("gateInwarddetails", responseList);
	    responseObjectsMap.put("message", "Gate Inward Details fetched successfully");

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getGateInwardModvatReport(
	        Long branch, Long orgId, String startDate, String endDate)
	        throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> resultList = gateInwardEntryRepo.getGateInwardModvatReport(
	            branch, orgId, startDate, endDate);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] row : resultList) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("gpno", row[0]);
	        map.put("gpdate", row[1]);
	        map.put("partyid", row[2]);
	        map.put("modvat", row[3]);
	        map.put("supdcno", row[4]);
	        map.put("supdcdt", row[5]);
	        map.put("partyname", row[6]);
	        map.put("itemid", row[7]);
	        map.put("itemdesc", row[8]);
	        map.put("unitid", row[9]);
	        map.put("gpqty", row[10]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("gateInwardModvatReport", responseList);
	    responseObjectsMap.put("message", "Gate Inward Modvat report fetched successfully");

	    return responseObjectsMap;
	}
	
	@Override
	public Map<String, Object> getDeliveryScheduleWeekly(
	        String monthYear, Long branch, Long orgId) throws ApplicationException {

	    Map<String, Object> responseObjectsMap = new HashMap<>();

	    List<Object[]> resultList = salesDeliveryScheduleRepo.getDeliveryScheduleWeekly(
	            monthYear, branch, orgId);

	    List<Map<String, Object>> responseList = new ArrayList<>();

	    for (Object[] row : resultList) {

	        Map<String, Object> map = new HashMap<>();

	        map.put("itemid", row[0]);
	        map.put("itemdesc", row[1]);
	        map.put("divnos", row[2]);
	        map.put("divdt", row[3]);
	        map.put("partyid", row[4]);
	        map.put("partyname", row[5]);

	        map.put("d1", row[6]);
	        map.put("d2", row[7]);
	        map.put("d3", row[8]);
	        map.put("d4", row[9]);
	        map.put("d5", row[10]);
	        map.put("d6", row[11]);
	        map.put("d7", row[12]);
	        map.put("d8", row[13]);
	        map.put("d9", row[14]);
	        map.put("d10", row[15]);
	        map.put("d11", row[16]);
	        map.put("d12", row[17]);
	        map.put("d13", row[18]);
	        map.put("d14", row[19]);
	        map.put("d15", row[20]);
	        map.put("d16", row[21]);
	        map.put("d17", row[22]);
	        map.put("d18", row[23]);
	        map.put("d19", row[24]);
	        map.put("d20", row[25]);
	        map.put("d21", row[26]);
	        map.put("d22", row[27]);
	        map.put("d23", row[28]);
	        map.put("d24", row[29]);
	        map.put("d25", row[30]);
	        map.put("d26", row[31]);
	        map.put("d27", row[32]);
	        map.put("d28", row[33]);
	        map.put("d29", row[34]);
	        map.put("d30", row[35]);
	        map.put("d31", row[36]);

	        responseList.add(map);
	    }

	    responseObjectsMap.put("deliveryScheduleWeekly", responseList);
	    responseObjectsMap.put("message", "Delivery Schedule Weekly report fetched successfully");

	    return responseObjectsMap;
	}
}
