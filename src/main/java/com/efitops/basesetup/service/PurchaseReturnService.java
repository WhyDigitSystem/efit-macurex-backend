package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.PurchaseReturnResponseDTO;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface PurchaseReturnService {

	PurchaseReturnResponseDTO getPurchaseReturnById(Long id) throws ApplicationException;

	Map<String, Object> createUpdatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO) throws ApplicationException;

	String getPurchaseReturnDocId(Long orgId, String financialYear);

	List<PurchaseReturnResponseDTO> getPurchaseReturnByOrgId(Long orgId, Long branch) throws ApplicationException;
}