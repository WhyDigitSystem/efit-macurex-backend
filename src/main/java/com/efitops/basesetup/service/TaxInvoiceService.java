package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.TaxInvoiceDTO;
import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.TaxInvoiceVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface TaxInvoiceService {
	
//	TaxInvoice
	List<TaxInvoiceVO> getAllTaxInvoiceByFinYearAndOrgId(Long orgId, String finYear, String branchCode);

	Map<String, Object> updateCreateTaxInvoice(TaxInvoiceDTO taxInvoiceDTO) throws ApplicationException;

	TaxInvoiceVO getTaxInvoiceById(Long id);

	TaxInvoiceVO getTaxInvoiceByDocId(Long orgId, String docId);

	String getTaxInvoiceDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getChargeType(Long orgId);

	List<Map<String, Object>> getChargeCodeByChargeType(Long orgId, String chargeType);

	List<Map<String, Object>> getCurrencyAndExrates(Long orgId);

	List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType);

	List<Map<String, Object>> getPartyStateCodeDetails(Long orgId, Long id);

	List<Map<String, Object>> getPlaceOfSupplyDetails(Long orgId, Long id,String stateCode);

	List<Map<String, Object>> getPartyAddressDetails(Long orgId, Long id, String stateCode, String placeOfSupply);

	List<Map<String, Object>> getGstTypeDetails(Long orgId, String branchCode, String stateCode);

}
