package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.SalesDTO;
import com.efitops.basesetup.dto.SalesInvoiceExportDTO;
import com.efitops.basesetup.entity.SalesInvoiceExportVO;
import com.efitops.basesetup.entity.SalesVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface SalesVService {

	// SALESORDER

	Map<String, Object> updateCreateSalesOrder(SalesDTO salesDTO) throws ApplicationException;

	List<SalesVO> getAllSalesByOrgId(Long orgId, String finYear, String branchCode);

	SalesVO getSalesById(Long id);

	String getSalesDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> findByCustomerNameFromPartyMasterSalesOrder(Long orgId, String finYear,
			String branchCode);

	List<Map<String, Object>> findByShippingAddressFromPartyMaster(Long orgId, String finYear, String branchCode,
			String customerName);

	List<Map<String, Object>> findByCustomerPoNoFromWorkOrder(Long orgId, String finYear, String branchCode,
			String customerName);

	List<Map<String, Object>> findByWorkOrderNo(Long orgId, String finYear, String branchCode, String customerPoNo);

	List<Map<String, Object>> findByContactPersonFromPartyMaster(Long orgId, String finYear, String branchCode,
			String customerCode);

	List<Map<String, Object>> findByInvoiceType(Long orgId, String finYear, String branchCode, String customerCode,
			String currency);

	List<Map<String, Object>> findByPartNoAndDescFromWorkOrder(Long orgId, String finYear, String branchCode,
			String workOrderNo);

	List<Map<String, Object>> findByTaxType(Long orgId, String branchCode, String customerCode, String partyType);

	// SALESINVOICEEXPORT

	Map<String, Object> updateCreateSalesInvoiceExport(SalesInvoiceExportDTO salesInvoiceExportDTO)
			throws ApplicationException;

	String getSalesInvoiceExportDocId(Long orgId, String finYear, String branchCode);

	SalesInvoiceExportVO getSalesInvoiceExportById(Long id);

	List<SalesInvoiceExportVO> getAllSalesInvoiceExport(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> findByCustomerNameFromPartyMasterSalesInvoiceExport(Long orgId, String partyName);

	List<Map<String, Object>> findByShippingFromPartySalesInvoiceExp(Long orgId, String customerName);

	List<Map<String, Object>> getSalesOrderNumber(Long orgId, String customerName);

	List<Map<String, Object>> getexportpackinglistNumber(Long orgId, String customerName, String salesOrderNo);

	List<Map<String, Object>> getPartNoFromexportpackinglist(Long orgId, String salesOrderNo,
			String exportPackingListNo);

	List<Map<String, Object>> findByGstForSalesOrder(Long orgId, String currency, String item, String taxType);

	List<Map<String, Object>> getSalesOrderDetails(Long orgId, String customerName, String fromDate, String toDate,
			String branchCode);

	List<Map<String, Object>> getSalesOrderSummaryDetails(Long orgId, String customerName, String fromDate,
			String toDate, String branchCode);

	List<Map<String, Object>> getSalesInvoiceExportDetails(Long orgId, String customerName, String fromDate,
			String toDate, String branchCode);

	List<Map<String, Object>> getSalesInvoiceExportSummaryDetails(Long orgId, String customerName, String fromDate,
			String toDate, String branchCode);

}
