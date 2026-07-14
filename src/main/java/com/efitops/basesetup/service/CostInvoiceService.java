package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.CostInvoiceDTO;
import com.efitops.basesetup.entity.CostInvoiceVO;
import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface CostInvoiceService {

	// CostInvoice

	List<CostInvoiceVO> getAllCostInvoiceByOrgId(Long orgId);

	Map<String, Object> updateCreateCostInvoice(@Valid CostInvoiceDTO costInvoiceDTO) throws ApplicationException;

	List<CostInvoiceVO> getAllCostInvoiceById(Long id);

	List<CostInvoiceVO> getCostInvoiceByActive();

	CostInvoiceVO getCostInvoiceByDocId(Long orgId, String docId);

	String getCostInvoiceDocId(Long orgId, String finYear, String branch, String branchCode);

	List<Map<String, Object>> getChargeType(Long orgId);

	List<Map<String, Object>> getChargeCodeByChargeType(Long orgId, String chargeType);

	List<Map<String, Object>> getCurrencyAndExratesForMatchingParties(Long orgId, String partyCode);

	List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType);

	List<Map<String, Object>> getPartyStateCodeDetails(Long orgId, Long id);

	List<Map<String, Object>> getPartyAddressDetails(Long orgId, Long id, String stateCode, String placeOfSupply);

	List<Map<String, Object>> getGstTypeDetails(Long orgId, String branchCode, String stateCode);

	List<Map<String, Object>> getPlaceOfSupplyDetails(Long orgId, Long id, String stateCode);

	List<Map<String, Object>> getJobNoFromTmsJobCard(Long orgId);

	List<Map<String, Object>> getChargeDetailsFromChargeType(Long orgId);

	// TDS Sub Table
	List<Map<String, Object>> getTdsDetailsFromPartyMasterSpecialTDS(Long orgId, String partyCode);

}
