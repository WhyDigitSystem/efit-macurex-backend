package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.RejectionInvoiceResponseDTO;
import com.efitops.basesetup.dto.RejectionInvoiceDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface RejectionInvoiceService {

	RejectionInvoiceResponseDTO getRejectionInvoiceById(Long id) throws ApplicationException;

	List<RejectionInvoiceResponseDTO> getRejectionInvoiceByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateRejectionInvoice(RejectionInvoiceDTO rejectionInvoiceDTO)
			throws ApplicationException;

	String getRejectionInvoiceDocId(Long orgId, String screenCode);

	List<Map<String, Object>> getExchangeRate(Long orgId, Long currency);

	List<Map<String, Object>> getTaxPercentage(Long orgId, Long hsn);

	List<Map<String, Object>> getItemDetailsBasedDesPatch(Long orgId, Long branch, Long despatch);

	List<Map<String, Object>> getSalesOrderNo(Long customer);

	List<Map<String, Object>> getOrderAmount(Long id, Long item);

}
