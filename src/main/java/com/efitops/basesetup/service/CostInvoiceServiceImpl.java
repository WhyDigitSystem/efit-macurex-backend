package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ChargerCostInvoiceDTO;
import com.efitops.basesetup.dto.CostInvoiceDTO;
import com.efitops.basesetup.dto.TdsCostInvoiceDTO;
import com.efitops.basesetup.entity.ChargerCostInvoiceVO;
import com.efitops.basesetup.entity.CostInvoiceVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.TdsCostInvoiceVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.ChargeTypeRequestRepo;
import com.efitops.basesetup.repo.ChargerCostInvoiceRepo;
import com.efitops.basesetup.repo.CostInvoiceRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.TdsCostInvoiceRepo;

@Service
public class CostInvoiceServiceImpl implements CostInvoiceService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CostInvoiceServiceImpl.class);

	@Autowired
	CostInvoiceRepo costInvoiceRepo;

	@Autowired
	TdsCostInvoiceRepo tdsCostInvoiceRepo;

	@Autowired
	ChargerCostInvoiceRepo chargerCostInvoiceRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	ChargeTypeRequestRepo chargeTypeRequestRepo;

	// costInvoice

	@Override
	public List<CostInvoiceVO> getAllCostInvoiceByOrgId(Long orgId) {
		List<CostInvoiceVO> costInvoiceVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  CostInvoice BY OrgId : {}", orgId);
			costInvoiceVO = costInvoiceRepo.getAllCostInvoiceByOrgId(orgId);
		} else {
			LOGGER.info("Successfully Received  CostInvoice For All OrgId.");
			costInvoiceVO = costInvoiceRepo.findAll();
		}
		return costInvoiceVO;
	}

	@Override
	public List<CostInvoiceVO> getAllCostInvoiceById(Long id) {
		List<CostInvoiceVO> costInvoiceVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  CostInvoice BY Id : {}", id);
			costInvoiceVO = costInvoiceRepo.getAllCostInvoiceById(id);
		} else {
			LOGGER.info("Successfully Received CostInvoice For All Id.");
			costInvoiceVO = costInvoiceRepo.findAll();
		}
		return costInvoiceVO;
	}

	@Override
	public List<CostInvoiceVO> getCostInvoiceByActive() {
		return costInvoiceRepo.findCostInvoiceByActive();
	}

	@Override
	public Map<String, Object> updateCreateCostInvoice(@Valid CostInvoiceDTO costInvoiceDTO)
			throws ApplicationException {
		String screenCode = "CI";
		CostInvoiceVO costInvoiceVO;
		String message = null;

		if (ObjectUtils.isEmpty(costInvoiceDTO.getId())) {

			costInvoiceVO = new CostInvoiceVO();

			// GETDOCID API
			String docId = costInvoiceRepo.getCostInvoiceDocId(costInvoiceDTO.getOrgId(), costInvoiceDTO.getFinYear(),
					costInvoiceDTO.getBranchCode(), screenCode);
			costInvoiceVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(costInvoiceDTO.getOrgId(),
							costInvoiceDTO.getFinYear(), costInvoiceDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			costInvoiceVO.setCreatedBy(costInvoiceDTO.getCreatedBy());
			costInvoiceVO.setUpdatedBy(costInvoiceDTO.getCreatedBy());

			message = "CostInvoice Creation Successfully";
		}

		else {
			costInvoiceVO = costInvoiceRepo.findById(costInvoiceDTO.getId()).orElseThrow(
					() -> new ApplicationException("Cost Invoice Not Found with id: " + costInvoiceDTO.getId()));
			costInvoiceVO.setUpdatedBy(costInvoiceDTO.getCreatedBy());

			message = "CostInvoice Updation Successfully";
		}

		costInvoiceVO = getCostInvoiceVOFromCostInvoiceDTO(costInvoiceVO, costInvoiceDTO);
		costInvoiceRepo.save(costInvoiceVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("costInvoiceVO", costInvoiceVO);
		return response;

	}

	private CostInvoiceVO getCostInvoiceVOFromCostInvoiceDTO(CostInvoiceVO costInvoiceVO,
			@Valid CostInvoiceDTO costInvoiceDTO) {

		costInvoiceVO.setMode(costInvoiceDTO.getMode());
		costInvoiceVO.setProduct(costInvoiceDTO.getProduct());
		costInvoiceVO.setPurVoucherNo(costInvoiceDTO.getPurVoucherNo());
		costInvoiceVO.setPurVoucherDate(costInvoiceDTO.getPurVoucherDate());
		costInvoiceVO.setCostInvoiceNo(costInvoiceDTO.getCostInvoiceNo());
		costInvoiceVO.setCostInvoiceDate(costInvoiceDTO.getCostInvoiceDate());
		costInvoiceVO.setSupplierBillNo(costInvoiceDTO.getSupplierBillNo());
		costInvoiceVO.setSupplierType(costInvoiceDTO.getSupplierType());
		costInvoiceVO.setSupplierCode(costInvoiceDTO.getSupplierCode());
		costInvoiceVO.setCreditDays(costInvoiceDTO.getCreditDays());
		costInvoiceVO.setDueDate(costInvoiceDTO.getDueDate());
		costInvoiceVO.setSupplierName(costInvoiceDTO.getSupplierName());
		costInvoiceVO.setSupplierPlace(costInvoiceDTO.getSupplierPlace());
		costInvoiceVO.setCurrency(costInvoiceDTO.getCurrency());
		costInvoiceVO.setExRate(costInvoiceDTO.getExRate());
		costInvoiceVO.setSupplierGstIn(costInvoiceDTO.getSupplierGstIn());
		costInvoiceVO.setSupplierGstInCode(costInvoiceDTO.getSupplierGstInCode());
		costInvoiceVO.setRemarks(costInvoiceDTO.getRemarks());
		costInvoiceVO.setAddress(costInvoiceDTO.getAddress());
		costInvoiceVO.setOtherInfo(costInvoiceDTO.getOtherInfo());
		costInvoiceVO.setShipperRefNo(costInvoiceDTO.getShipperRefNo());
		costInvoiceVO.setGstType(costInvoiceDTO.getGstType());
		costInvoiceVO.setOrgId(costInvoiceDTO.getOrgId());
		costInvoiceVO.setCreatedBy(costInvoiceDTO.getCreatedBy());
		costInvoiceVO.setCancelRemarks(costInvoiceDTO.getCancelRemarks());
		costInvoiceVO.setBranch(costInvoiceDTO.getBranch());
		costInvoiceVO.setBranchCode(costInvoiceDTO.getBranchCode());
		costInvoiceVO.setCustomer(costInvoiceDTO.getCustomer());
		costInvoiceVO.setClient(costInvoiceDTO.getClient());
		costInvoiceVO.setFinYear(costInvoiceDTO.getFinYear());
		costInvoiceVO.setIpNo(costInvoiceDTO.getIpNo());
		costInvoiceVO.setLatitude(costInvoiceDTO.getLatitude());
		costInvoiceVO.setPayment(costInvoiceDTO.getPayment());
		costInvoiceVO.setAccuralid(costInvoiceDTO.getAccuralid());
		costInvoiceVO.setUtrRef(costInvoiceDTO.getUtrRef());
		costInvoiceVO.setCostType(costInvoiceDTO.getCostType());

		if (costInvoiceDTO.getId() != null) {

			List<ChargerCostInvoiceVO> chargerCostInvoiceVOs = chargerCostInvoiceRepo
					.findByCostInvoiceVO(costInvoiceVO);
			chargerCostInvoiceRepo.deleteAll(chargerCostInvoiceVOs);

			List<TdsCostInvoiceVO> tdsCostInvoiceVOs = tdsCostInvoiceRepo.findByCostInvoiceVO(costInvoiceVO);
			tdsCostInvoiceRepo.deleteAll(tdsCostInvoiceVOs);

		}

		BigDecimal totChargesBillCurrAmt = BigDecimal.ZERO;
		BigDecimal totChargesLcAmt = BigDecimal.ZERO;
		BigDecimal actBillCurrAmt = BigDecimal.ZERO;
		BigDecimal actBillLcAmt = BigDecimal.ZERO;
		BigDecimal netBillCurrAmt = BigDecimal.ZERO;
		BigDecimal netBillLcAmt = BigDecimal.ZERO;
		BigDecimal roundOff = BigDecimal.ZERO;
		BigDecimal gstInputLcAmt = BigDecimal.ZERO;
//		SumOfLc Amount to set in TDS Table
		BigDecimal sumLcAmount = BigDecimal.ZERO;

		List<ChargerCostInvoiceVO> chargerCostInvoiceVOs = new ArrayList<>();
		for (ChargerCostInvoiceDTO chargerCostInvoiceDTO : costInvoiceDTO.getChargerCostInvoiceDTO()) {
			ChargerCostInvoiceVO chargerCostInvoiceVO = new ChargerCostInvoiceVO();

			chargerCostInvoiceVO.setRate(chargerCostInvoiceDTO.getRate());
			chargerCostInvoiceVO.setJobNo(chargerCostInvoiceDTO.getJobNo());
			chargerCostInvoiceVO.setChargeName(chargerCostInvoiceDTO.getChargeName());
			chargerCostInvoiceVO.setChargeCode(chargerCostInvoiceDTO.getChargeCode());
			chargerCostInvoiceVO.setChargeLedger(chargerCostInvoiceDTO.getChargeLedger());
			chargerCostInvoiceVO.setSac(chargerCostInvoiceDTO.getSac());
			chargerCostInvoiceVO.setCurrency(chargerCostInvoiceDTO.getCurrency());
			chargerCostInvoiceVO.setExRate(chargerCostInvoiceDTO.getExRate());
			chargerCostInvoiceVO.setGst(chargerCostInvoiceDTO.getGst());
			chargerCostInvoiceVO.setGovChargeCode(chargerCostInvoiceDTO.getGovChargeCode());
			chargerCostInvoiceVO.setExempted(chargerCostInvoiceDTO.getExempted());
			chargerCostInvoiceVO.setTaxable(chargerCostInvoiceDTO.getTaxable());

//			chargerCostInvoiceVO.setFcAmt(chargerCostInvoiceDTO.getFcAmt());
//			chargerCostInvoiceVO.setLcAmt(chargerCostInvoiceDTO.getLcAmt());
//			chargerCostInvoiceVO.setBillAmt(chargerCostInvoiceDTO.getBillAmt());
			BigDecimal fcAmount;
			BigDecimal lcAmount;
			BigDecimal tlcAmount;
			BigDecimal billAmount;
			BigDecimal gstAmount;

			if (!chargerCostInvoiceDTO.getCurrency().equals("INR")) {
				BigDecimal rate = chargerCostInvoiceDTO.getRate(); // BigDecimal type is expected here
				BigDecimal qty = BigDecimal.valueOf(chargerCostInvoiceDTO.getQty()); // Convert qty to BigDecimal

				fcAmount = rate.multiply(qty);

				chargerCostInvoiceVO.setFcAmt(fcAmount);

			} else {
				fcAmount = BigDecimal.valueOf(0.00);
				chargerCostInvoiceVO.setFcAmt(fcAmount);
			}

			BigDecimal exRate = chargerCostInvoiceDTO.getExRate();
			BigDecimal qty = BigDecimal.valueOf(chargerCostInvoiceDTO.getQty());
			BigDecimal rate = chargerCostInvoiceDTO.getRate();
			lcAmount = exRate.multiply(qty.multiply(rate));
			chargerCostInvoiceVO.setLcAmt(lcAmount);
			totChargesLcAmt = totChargesLcAmt.add(lcAmount);
			// TDS Purpose
			sumLcAmount = sumLcAmount.add(lcAmount);

			BigDecimal gstPercent = BigDecimal.valueOf(chargerCostInvoiceDTO.getGstPercent());
			tlcAmount = lcAmount.multiply(gstPercent).divide(BigDecimal.valueOf(100));
			chargerCostInvoiceVO.setTlcAmount(tlcAmount);

			billAmount = lcAmount.divide(exRate, RoundingMode.HALF_UP);

			chargerCostInvoiceVO.setBillAmt(billAmount);
			totChargesBillCurrAmt = totChargesBillCurrAmt.add(billAmount);

			gstAmount = lcAmount.multiply(gstPercent).divide(BigDecimal.valueOf(100));
			chargerCostInvoiceVO.setGstAmount(gstAmount);

			chargerCostInvoiceVO.setCostInvoiceVO(costInvoiceVO);
			chargerCostInvoiceVOs.add(chargerCostInvoiceVO);
		}

		Map<String, BigDecimal> ledgerSumMap = new HashMap<>();
		for (ChargerCostInvoiceVO detailsVO : chargerCostInvoiceVOs) {
			String ledger = detailsVO.getLedger();
			BigDecimal lcAmount = detailsVO.getLcAmt();

			ledgerSumMap.put(ledger, ledgerSumMap.getOrDefault(ledger, BigDecimal.ZERO).add(lcAmount));
		}

		// TDS table
		costInvoiceVO.setChargerCostInvoiceVO(chargerCostInvoiceVOs);

		List<TdsCostInvoiceVO> tdsCostInvoiceVOs = new ArrayList<>();
		for (TdsCostInvoiceDTO tdsCostInvoiceDTO : costInvoiceDTO.getTdsCostInvoiceDTO()) {
			TdsCostInvoiceVO tdsCostInvoiceVO = new TdsCostInvoiceVO();

			tdsCostInvoiceVO.setTdsWithHolding(tdsCostInvoiceDTO.getTdsWithHolding());
			tdsCostInvoiceVO.setTdsWithHoldingPer(tdsCostInvoiceDTO.getTdsWithHoldingPer());
			tdsCostInvoiceVO.setSection(tdsCostInvoiceDTO.getSection());
//			tdsCostInvoiceVO.setTotTdsWhAmnt(tdsCostInvoiceDTO.getTotTdsWhAmnt());

			BigDecimal totTdsWhAmt = BigDecimal.ZERO;
			BigDecimal tdsWhPercent = tdsCostInvoiceDTO.getTdsWithHoldingPer();
			System.out.println("TOTAL LC AMOUNT IS :"+sumLcAmount);;
			totTdsWhAmt = sumLcAmount.multiply(tdsWhPercent.divide(BigDecimal.valueOf(100)));
			tdsCostInvoiceVO.setTotTdsWhAmnt(totTdsWhAmt);
			
			tdsCostInvoiceVO.setCostInvoiceVO(costInvoiceVO);
			tdsCostInvoiceVOs.add(tdsCostInvoiceVO);
		}
		costInvoiceVO.setTdsCostInvoiceVO(tdsCostInvoiceVOs);

		// Summary Calculation

		return costInvoiceVO;

	}

	@Override
	public CostInvoiceVO getCostInvoiceByDocId(Long orgId, String docId) {
		return costInvoiceRepo.findAllCostInvoiceByDocId(orgId, docId);
	}

	@Override
	public String getCostInvoiceDocId(Long orgId, String finYear, String branch, String branchCode) {
		String ScreenCode = "CI";
		String result = costInvoiceRepo.getCostInvoiceDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getChargeType(Long orgId) {
		Set<Object[]> chType = costInvoiceRepo.getActiveChargType(orgId);
		return getChargeType(chType);
	}

	private List<Map<String, Object>> getChargeType(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("chargeType", ch[0].toString());
			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getChargeCodeByChargeType(Long orgId, String chargeType) {
		Set<Object[]> chCode = costInvoiceRepo.getActiveChargCodeByOrgIdAndChargeTypeIgnoreCase(orgId, chargeType);
		return getChargeCode(chCode);
	}

	private List<Map<String, Object>> getChargeCode(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("chargeCode", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("govChargeCode", ch[1] != null ? ch[1].toString() : "");
			map.put("chargeName", ch[2] != null ? ch[2].toString() : "");
			map.put("taxable", ch[3] != null ? ch[3].toString() : "");
			map.put("ccFeeApplicable", ch[4] != null ? ch[4].toString() : "");
			map.put("exempted", ch[5] != null ? ch[5].toString() : "");
			map.put("sac", ch[6] != null ? ch[6].toString() : "");
			map.put("GSTPercent", ch[7] != null ? ch[7].toString() : ""); // Handle as string, empty if null
			map.put("ledger", ch[8] != null ? ch[8].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getCurrencyAndExratesForMatchingParties(Long orgId, String partyCode) {
		Set<Object[]> currency = costInvoiceRepo.getCurrencyAndExratesForMatchingParties(orgId, partyCode);
		return getCurrency(currency);
	}

	private List<Map<String, Object>> getCurrency(Set<Object[]> currency) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : currency) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgId", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("date", ch[1] != null ? ch[1].toString() : "");
			map.put("month", ch[2] != null ? ch[2].toString() : "");
			map.put("currency", ch[3] != null ? ch[3].toString() : "");
			map.put("currencyDescripition", ch[4] != null ? ch[4].toString() : "");
			map.put("buyingExRate", ch[5] != null ? ch[5].toString() : "");
			map.put("sellingExRate", ch[6] != null ? ch[6].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType) {

		return costInvoiceRepo.findByOrgIdAndPartyTypeIgnoreCase(orgId, partyType);

	}

	@Override
	public List<Map<String, Object>> getPartyStateCodeDetails(Long orgId, Long id) {
		Set<Object[]> getStateDetails = costInvoiceRepo.getStateCodeDetails(orgId, id);
		return getState(getStateDetails);
	}

	private List<Map<String, Object>> getState(Set<Object[]> getStateDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getStateDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("stateCode", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("recipientGSTIN", ch[1] != null ? ch[1].toString() : "");
			map.put("stateNo", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartyAddressDetails(Long orgId, Long id, String stateCode,
			String placeOfSupply) {
		Set<Object[]> getAddressDetails = costInvoiceRepo.getAddressDetails(orgId, id, stateCode, placeOfSupply);
		return getAddress(getAddressDetails);
	}

	private List<Map<String, Object>> getAddress(Set<Object[]> getAddressDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getAddressDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("addressType", ch[0] != null ? ch[0].toString() : "");
			map.put("address", ch[1] != null ? ch[1].toString() : ""); // Empty string if null
			map.put("pinCode", ch[2] != null ? ch[2].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getGstTypeDetails(Long orgId, String branchCode, String stateCode) {
		Set<Object[]> getGSTTypeDetails = costInvoiceRepo.getGstType(orgId, branchCode, stateCode);
		return getGstType(getGSTTypeDetails);
	}

	private List<Map<String, Object>> getGstType(Set<Object[]> getGSTTypeDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getGSTTypeDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("gstType", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPlaceOfSupplyDetails(Long orgId, Long id, String stateCode) {
		Set<Object[]> getPOSDetails = costInvoiceRepo.getPlaceOfSupplyDetails(orgId, id, stateCode);
		return getPOS(getPOSDetails);
	}

	private List<Map<String, Object>> getPOS(Set<Object[]> getPOSDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getPOSDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("placeOfSupply", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getJobNoFromTmsJobCard(Long orgId) {
		Set<Object[]> getJobNo = costInvoiceRepo.getJobNoFromTmsJobCard(orgId);
		return getJobDetails(getJobNo);
	}

	private List<Map<String, Object>> getJobDetails(Set<Object[]> getJob) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : getJob) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobNo", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getChargeDetailsFromChargeType(Long orgId) {
		Set<Object[]> chDetails = chargeTypeRequestRepo.getActiveChargeDetailsFromChargeType(orgId);
		return getChargeDetails(chDetails);
	}

	private List<Map<String, Object>> getChargeDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("chargeCode", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("govChargeCode", ch[1] != null ? ch[1].toString() : "");
			map.put("chargeName", ch[2] != null ? ch[2].toString() : "");
			map.put("taxable", ch[3] != null ? ch[3].toString() : "");
			map.put("ccFeeApplicable", ch[4] != null ? ch[4].toString() : "");
			map.put("exempted", ch[5] != null ? ch[5].toString() : "");
			map.put("sac", ch[6] != null ? ch[6].toString() : "");
			map.put("GSTPercent", ch[7] != null ? ch[7].toString() : ""); // Handle as string, empty if null
			map.put("ledger", ch[8] != null ? ch[8].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getTdsDetailsFromPartyMasterSpecialTDS(Long orgId, String partyCode) {
		Set<Object[]> specialTds = costInvoiceRepo.findTdsDetailsFromPartyMasterSpecialTDS(orgId, partyCode);
		return getTds(specialTds);
	}

	private List<Map<String, Object>> getTds(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("section", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("tdsWhPercent", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;

	}

}
