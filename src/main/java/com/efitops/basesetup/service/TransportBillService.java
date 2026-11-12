package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import com.efitops.basesetup.ResponseDTO.TransportBillResponseDTO;
import com.efitops.basesetup.dto.TransportBillDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface TransportBillService {

    Map<String, Object> updateCreateTransportBill(TransportBillDTO transportBillDTO) throws ApplicationException;

    TransportBillResponseDTO getTransportBillById(Long id) throws ApplicationException;

    List<TransportBillResponseDTO> getTransportBillByOrgId(Long orgId, Long branch) throws ApplicationException;

	String getTransportBillDocId(Long orgId, String financialYear, String screenCode);
}