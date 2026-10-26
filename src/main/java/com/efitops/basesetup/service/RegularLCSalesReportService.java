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

}
