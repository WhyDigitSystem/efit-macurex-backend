package com.efitops.basesetup.service;

import java.util.List;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PurchaseShortCloseDTO;
import com.efitops.basesetup.dto.StockReConcilationDTO;
import com.efitops.basesetup.dto.WorkOrderShortCloseDTO;
import com.efitops.basesetup.entity.PurchaseShortCloseVO;
import com.efitops.basesetup.entity.StockReConcilationVO;
import com.efitops.basesetup.entity.WorkOrderShortCloseVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface StockReConcilationService {

	StockReConcilationVO getStockReConcilationById(Long id);

	List<StockReConcilationVO> getAllStockReConcilationByOrgId(Long orgId, String finYear, String branchCode);

	Map<String, Object> updateCreateStockReConcilation(@Valid StockReConcilationDTO stockReConcilationDTO)
			throws ApplicationException;

	String getStockReConcilationDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getItemNameAndDesc(Long orgId);

	// PurchaseStockClose

	List<PurchaseShortCloseVO> getPurchaseShortCloseByOrgId(Long orgId, String finYear, String branchCode);

	PurchaseShortCloseVO getPurchaseShortCloseById(Long id);

	Map<String, Object> updateCreatePurchaseShortClose(PurchaseShortCloseDTO purchaseShortCloseDTO)
			throws ApplicationException;

	String getPurchaseShortCloseDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getPurchaseOrderDetails(Long orgId, String poNo);

	List<Map<String, Object>> getItemDetailsFromPurchaseOrderDetails(Long orgId, String branchCode, String poNo);

	// WorkOrderStockCloseDetails

	Map<String, Object> createUpdateWorkOrderShortClose(WorkOrderShortCloseDTO workOrderShortCloseDTO)
			throws ApplicationException;

	WorkOrderShortCloseVO getWorkOrderShortCloseById(Long id);

	List<WorkOrderShortCloseVO> getAllWorkOrderShortCloseByOrgId(Long orgId, String finYear, String branchCode);

	String getWorkOrderShortCloseDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getWorkOrderNumber(Long orgId, String branchCode,
			String workOrderNo);

	List<Map<String, Object>> getWorkOrderDetails(Long orgId, String branchCode, String workOrderNo);

	WorkOrderShortCloseVO approveWorkOrderShortClose(Long orgId, Long id, String docId, String action, String actionBy)
			throws ApplicationException;

	PurchaseShortCloseVO approvePurchaseShortClose(Long orgId, Long id, String docId, String action, String actionBy)
			throws ApplicationException;

	List<Map<String, Object>> getPurchaseOrderDocId(Long orgId);

	List<Map<String, Object>> getWorkOrderDocId(Long orgId);

	List<Map<String, Object>> getPurchaseShortCloseReport(Long orgId, String branchCode, String fromDate,
			String toDate);

	List<Map<String, Object>> getWorkOrderShortCloseReport(Long orgId, String branchCode, String fromDate,
			String toDate);
}
