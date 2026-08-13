package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.OtherSalesInvoiceResponseDTO;
import com.efitops.basesetup.dto.OtherSalesInvoiceDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface OtherSalesInvoiceService {

	OtherSalesInvoiceResponseDTO getOtherSalesInvoiceById(Long id) throws ApplicationException;

	List<OtherSalesInvoiceResponseDTO> getOtherSalesInvoiceByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> createUpdateOtherSalesInvoice(OtherSalesInvoiceDTO otherSalesInvoiceDTO)
			throws ApplicationException;

	String getOtherSalesInvoiceDocId(Long orgId, String screenCode);

	List<Map<String, Object>> getExchangeRate(Long orgId, Long currency);

	List<Map<String, Object>> getTaxPercentage(Long orgId, Long hsn);

	List<Map<String, Object>> getItemDetailsBasedDesPatch(Long orgId, Long branch, Long despatch);

	List<Map<String, Object>> getSalesOrderNo(Long customer);

}
