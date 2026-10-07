package com.efitops.basesetup.service;

import java.util.Map;

import com.efitops.basesetup.exception.ApplicationException;

public interface RegularLCSalesReportService {

	Map<String, Object> getRegularLCSalesReport(Long belongsTo, Long branch, Long orgId, String fromDate, String toDate)
			throws ApplicationException;

}
