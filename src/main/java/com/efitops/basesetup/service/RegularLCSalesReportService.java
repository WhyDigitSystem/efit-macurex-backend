package com.efitops.basesetup.service;

import java.util.Map;

import com.efitops.basesetup.exception.ApplicationException;

public interface RegularLCSalesReportService {

	Map<String, Object> getRegularLCSalesReport(Long belongsTo, Long branch, Long orgId, String fromDate, String toDate)
			throws ApplicationException;

	Map<String, Object> getRejectionInvoiceReport(String fromDate, String toDate, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> getRegularLCAppliancesReport(String fromDate, String toDate, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> getCustomerDetailsSales(String fromDate, String toDate, Long orgId, Long branch)
			throws ApplicationException;

	Map<String, Object> getScheduleVsSalesReport(String mon, Long under, Long branch, Long orgId)
			throws ApplicationException;

//	invoicedocketdetails
	Map<String, Object> getInvoiceDocketDetailsReport(String fromDate, String toDate, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> getDeliveryScheduleYearWiseReport(String year, String belongs, Long branch, Long orgId)
			throws ApplicationException;


	Map<String, Object> getSalesContractApprovalReport(String frdt, String endt, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> getPhysicalStockReconciliationReport(Long orgId, Long branch, String fromDate, String toDate)
			throws ApplicationException;

	Map<String, Object> getIssueReport(String fromDate, String toDate, String bel, Long branch, Long orgId)
			throws ApplicationException;

//	Map<String, Object> getInternalIndentReport(String fromDate, String toDate, String bel) throws ApplicationException;

	Map<String, Object> getInternalIndentReport(String fromDate, String toDate, String bel, Long branch, Long orgId)
			throws ApplicationException;

//	Map<String, Object> getSupplierRateContractReport(Long plant, String frdt, String todt) throws ApplicationException;

	Map<String, Object> getSupplierRateContractReport(Long branch, Long OrgId, String frdt, String todt)
			throws ApplicationException;

	Map<String, Object> getJobOrderStatusReport(Long branch, String fromDate, String toDate)
			throws ApplicationException;

	Map<String, Object> getJobOrderdetailsReport(String fromdt, String todate, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> getStoresAdviceReport(String fromDate, String toDate, Long branch, Long orgId)
			throws ApplicationException;

	Map<String, Object> getSubContractingGrnPendingListForInspection(String fromDate, String toDate, Long locId,
			Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getSubContractingGrnItemWiseReport(String fromDate, String toDate, Long plant, Long locId,
			Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getBomTreeReportLive(String itemCode, String branch, Long orgId) throws ApplicationException;

	Map<String, Object> getBomTreeAfterCorrectionReport(String itemCode, String branch, Long orgId)
			throws ApplicationException;


	
	Map<String, Object> getBomListFgReport(Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getEngineeringChangeRecordReport(Long branch, Long orgId) throws ApplicationException;

	Map<String, Object> getGateInwarddetails(Long branch, Long orgId, String startDate, String endDate)
			throws ApplicationException;

	Map<String, Object> getGateInwardModvatReport(Long branch, Long orgId, String startDate, String endDate)
			throws ApplicationException;

	Map<String, Object> getDeliveryScheduleWeekly(String monthYear, Long branch, Long orgId)
			throws ApplicationException;

}
