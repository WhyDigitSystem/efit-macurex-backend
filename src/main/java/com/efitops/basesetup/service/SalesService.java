package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DeliveryChalanForFgDTO;
import com.efitops.basesetup.dto.SalesInvoiceLocalDTO;
import com.efitops.basesetup.dto.SalesReturnExportDTO;
import com.efitops.basesetup.dto.SalesReturnLocalDTO;
import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalVO;
import com.efitops.basesetup.entity.SalesReturnExportVO;
import com.efitops.basesetup.entity.SalesReturnLocalVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface SalesService {

	// DeliveryChalanForFg

	Map<String, Object> createUpdateDeliveryChalanForFg(DeliveryChalanForFgDTO deliveryChalanForFgDTO)
			throws ApplicationException;

	List<DeliveryChalanForFgVO> getAllDeliveryChalanForFgByOrgId(Long orgId,String finYear, String branchCode);

	DeliveryChalanForFgVO getDeliveryChalanForFgById(Long id);

	String getDeliveryChalanForFgDocId(Long orgId,String finYear, String branchCode);

	List<Map<String, Object>> getCustomerNameFromPartyMaster(Long orgId);

	List<Map<String, Object>> getSoNoFromSaleOrder(Long orgId, String customerName);

	List<Map<String, Object>> getItemNameFromSaleOrder(String customerName,String customerCode);

	// SalesInvoiceLocal

	Map<String, Object> createUpdateSalesInvoiceLocal(SalesInvoiceLocalDTO salesInvoiceLocalDTO)
			throws ApplicationException;

	List<SalesInvoiceLocalVO> getAllSalesInvoiceLocalByOrgId(Long orgId,String finYear, String branchCode);

	SalesInvoiceLocalVO getSalesInvoiceLocalById(Long id);

	String getSalesInvoiceLocalDocId(Long orgId,String finYear, String branchCode);

	List<Map<String, Object>> getpartyNameFromPartyMaster(Long orgId);

	List<Map<String, Object>> getShippingAddressFromPartyMaster(Long orgId);

	List<Map<String, Object>> getDocIdFromPackingList(Long orgId, String customerName);

	List<Map<String, Object>> getItemNameFromPackingList(Long orgId, String packingListNo, String customerName);

	// SalesReturnLocal

	Map<String, Object> createUpdateSalesReturnLocal(SalesReturnLocalDTO salesReturnLocalDTO)
			throws ApplicationException;

	List<SalesReturnLocalVO> getAllSalesReturnLocalByOrgId(Long orgId,String finYear, String branchCode);

	SalesReturnLocalVO getSalesReturnLocalById(Long id);

	String getSalesReturnLocalDocId(Long orgId,String finYear, String branchCode);

	List<Map<String, Object>> getSalesInvoiceNoFromSalesInvoice(Long orgId, String customerName);

	List<Map<String, Object>> getItemFromSalesInvoice(Long orgId, String customerName, String salesInvoiceLocalNo);

	// SalesReturnExport

	Map<String, Object> createUpdateSalesReturnExport(SalesReturnExportDTO salesReturnExportDTO)
			throws ApplicationException;

	List<SalesReturnExportVO> getAllSalesReturnExportByOrgId(Long orgId,String finYear, String branchCode);

	SalesReturnExportVO getSalesReturnExportById(Long id);

	String getSalesReturnExportDocId(Long orgId,String finYear, String branchCode);

	List<Map<String, Object>> getCustomerNameFromPartyMasterhExport(Long orgId);

	List<Map<String, Object>> getDocIdFromSalesInvoiceExport(Long orgId, String customerName);

	List<Map<String, Object>> getItemFromSalesInvoiceExport(Long orgId, String customerName,
			String salesInvoiceExportNo);

	List<Map<String, Object>> getItemDetailsforDCFGFromSaleOrder(Long orgId, String branchCode, String finYear,
			String salesOrderNo);

	List<Map<String, Object>> getPackListDetails(Long orgId, String branchCode, String customerName);

	List<Map<String, Object>> getItemPackListDetails(Long orgId, String branchCode, String customerName,
			String packlistNo);

	List<Map<String, Object>> getDeliveryChallanForFGReport(Long orgId, String fromDate, String toDate,String saleOrderNo);

	List<Map<String, Object>> getSalesInvoiceLocalDetails(Long orgId, String fromdate, String todate);

}
