package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseIndentResponseDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface PurchaseIndentService {

    Map<String, Object> createUpdatePurchaseIndent(PurchaseIndentDTO dto, MultipartFile[] files)
            throws ApplicationException;

    PurchaseIndentResponseDTO getPurchaseIndentById(Long id) throws ApplicationException;

    List<PurchaseIndentResponseDTO> getPurchaseIndentByOrgId(Long orgId, Long branch) throws ApplicationException;
}