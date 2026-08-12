package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.dto.PurchaseContractDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface PurchaseContractService {

    Map<String, Object> updateCreatePurchaseContract(PurchaseContractDTO purchaseContractDTO, MultipartFile[] files)
            throws ApplicationException;

    PurchaseContractResponseDTO getPurchaseContractById(Long id) throws ApplicationException;

    List<PurchaseContractResponseDTO> getPurchaseContractByOrgId(Long orgId, Long branchId)
            throws ApplicationException;

    String getPurchaseContractDocId(Long orgId, String finYear, Long branch);
}