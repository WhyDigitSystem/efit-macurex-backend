package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PurchaseServiceImport {

	Map<String, Object> createUpdatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, MultipartFile[] files)
			throws ApplicationException;

	ResponseEntity<byte[]> viewPurchaseOrderFile(HttpServletRequest request) throws IOException;

	PurchaseOrderResponseDTO getPurchaseOrderById(Long id, String type) throws ApplicationException;

	List<PurchaseOrderResponseDTO> getPurchaseOrderByOrgId(Long orgId, Long branch) throws ApplicationException;

}
