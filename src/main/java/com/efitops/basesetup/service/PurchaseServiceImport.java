package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.dto.PoType;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
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

}
