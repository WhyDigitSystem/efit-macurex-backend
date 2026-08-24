package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseOrderDeliveryScheduleShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.dto.PoType;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseOrderDeliveryScheduleShortCloseDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PurchaseServiceImport {

	Map<String, Object> createUpdatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, MultipartFile[] files)
			throws ApplicationException;

	ResponseEntity<byte[]> viewPurchaseOrderFile(HttpServletRequest request) throws IOException;

	List<PurchaseOrderResponseDTO> getPurchaseOrderByOrgId(Long orgId, Long branch) throws ApplicationException;

	List<Map<String, Object>> getItemDetailsResponsePurchaseLocal(Long orgId, Long branch);

	List<Map<String, Object>> getItemDetailsResponsePurchaseImport(Long orgId, Long branch);

	List<Map<String, Object>> getSupplierDetails(Long orgId, Long branch);

	String getPurchaseOrderDocId(Long orgId, String financialYear, String screenCode, PoType type);

	List<Map<String, Object>> getExchangeRateDetails(Long orgId, Long branch, Long currency);

	List<Map<String, Object>> getMutipleFactorAmount(Long orgId, Long primaryUnit, Long purchaseUnit);

	PurchaseOrderResponseDTO getPurchaseOrderById(Long id, PoType type) throws ApplicationException;

	List<Map<String, Object>> getIndentNoBasedLocal(Long orgId, String belongsTo, String type);

	List<Map<String, Object>> getIndentNoBasedImport(Long orgId, String type);

	List<Map<String, Object>> getHsnCodeDetails(Long orgId, Long branch, Long item, String type);

	PurchaseOrderDeliveryScheduleShortCloseResponseDTO getPurchaseOrderDeliveryScheduleShortCloseById(Long id)
			throws ApplicationException;

	List<PurchaseOrderDeliveryScheduleShortCloseResponseDTO> getPurchaseOrderDeliveryScheduleShortCloseByOrgId(
			Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdatePurchaseOrderDeliveryScheduleShortClose(
			PurchaseOrderDeliveryScheduleShortCloseDTO purchaseOrderDeliveryScheduleShortCloseDTO)
			throws ApplicationException;

	List<Map<String, Object>> getSupplierDetailsShortClose(Long orgId, Long branch);

	List<Map<String, Object>> getPurchaseOrderNobasedSchedule(Long orgId, Long branch, Long supplier);

	List<Map<String, Object>> getPurchaseOrderNobasedScheduleDetails(Long orgId, Long branch, Long supplier,
			String purchaseOrderNo);

	String getPurchaseOrderDeliveryScheduleShortCloseDocId(Long orgId, String financialYear);

}
