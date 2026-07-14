package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GateInwardEntryDTO;
import com.efitops.basesetup.dto.GateOutwardEntryDTO;
import com.efitops.basesetup.entity.GateInwardEntryVO;
import com.efitops.basesetup.entity.GateOutwardEntryVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface InwardOutwardService {

	// GateInwardEntry
	List<GateInwardEntryVO> getGateInwardEntryByOrgId(Long orgId, String finYear, String branchCode);

	List<GateInwardEntryVO> getGateInwardEntryById(Long id);

	Map<String, Object> updateCreateGateInwardEntry(GateInwardEntryDTO gateInwardEntryDTO) throws ApplicationException;

	String getGateInwardEntryDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getPurchaseOrderNoForGateInward(Long orgId, String supplierCode);

	List<Map<String, Object>> getItemDetailsForGateInwardEntry(Long orgId, String purchaseOrderNo);

	// GateOutwardEntry
	List<GateOutwardEntryVO> getAllGateOutwardEntryByOrgId(Long orgId, String finYear, String branchCode);

	GateOutwardEntryVO getGateOutwardEntryById(Long id);

	Map<String, Object> updateCreateGateOutwardEntry(GateOutwardEntryDTO gateOutwardEntryDTO)
			throws ApplicationException;

	List<Map<String, Object>> getCustomerNameAndCodeFromGateOutwardEntry(Long orgId);

	List<Map<String, Object>> getDeliveryChallanNoForGateOutwardEntry(Long orgId, String customerName, String type);

	List<Map<String, Object>> getInvoiceNoForGateOutwardEntry(Long orgId, String dcNo, String type);

	List<Map<String, Object>> getItemDetailsForGateOutwardEntry(Long orgId, String invNo);

	String getGateOutwardEntryDocId(Long orgId, String finYear, String branchCode);

	List<Map<String, Object>> getEmployeeNameDetails(Long orgId, String branchCode);

	List<Map<String, Object>> getDeliveryChallanDetails(Long orgId, String branchCode, String type);

	List<Map<String, Object>> getInvoiceDetails(Long orgId, String branchCode, String deliveryChallanNo);

	List<Map<String, Object>> getGateOutwardEntryReport(Long orgId, String fromDate, String toDate);

	List<Map<String, Object>> getGateInwardReport(Long orgId, String branchCode, String supplierName, String fromDate,
			String toDate);

}
