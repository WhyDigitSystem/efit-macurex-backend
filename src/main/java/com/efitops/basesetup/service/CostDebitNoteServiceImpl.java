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

import com.efitops.basesetup.dto.CostDebitChargesDTO;
import com.efitops.basesetup.dto.CostDebitNoteDTO;
import com.efitops.basesetup.dto.CostDebitNoteTaxPrtculDTO;
import com.efitops.basesetup.dto.TdsCostInvoiceDTO;
import com.efitops.basesetup.entity.CostDebitChargesVO;
import com.efitops.basesetup.entity.CostDebitNoteGstVO;
import com.efitops.basesetup.entity.CostDebitNoteTaxPrtculVO;
import com.efitops.basesetup.entity.CostDebitNoteVO;
import com.efitops.basesetup.entity.CostInvoiceVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.TdsCostInvoiceVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.CostDebitChargesRepo;
import com.efitops.basesetup.repo.CostDebitNoteGstRepo;
import com.efitops.basesetup.repo.CostDebitNoteRepo;
import com.efitops.basesetup.repo.CostDebitNoteTaxPrtculRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;

@Service
public class CostDebitNoteServiceImpl implements CostDebitNoteService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CostDebitNoteServiceImpl.class);

	@Autowired
	CostDebitNoteRepo costDebitNoteRepo;

	@Autowired
	CostDebitChargesRepo costDebitChargesRepo;

	@Autowired
	CostDebitNoteGstRepo costDebitNoteGstRepo;

	@Autowired
	CostDebitNoteTaxPrtculRepo costDebitNoteTaxPrtculRepo;

	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public Map<String, Object> updateCreateCostDebitNote(@Valid CostDebitNoteDTO costDebitNoteDTO)
			throws ApplicationException {

		String message = null;
		String screenCode = "CDN";
		CostDebitNoteVO costDebitNoteVO;

		if (ObjectUtils.isEmpty(costDebitNoteDTO.getId())) {
			costDebitNoteVO = new CostDebitNoteVO();

			// GETDOCID API
			String docId = costDebitNoteRepo.getCostDebitNoteDocId(costDebitNoteDTO.getOrgId(),
					costDebitNoteDTO.getFinYear(), costDebitNoteDTO.getBranchCode(), screenCode);
			costDebitNoteVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(costDebitNoteDTO.getOrgId(),
							costDebitNoteDTO.getFinYear(), costDebitNoteDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			costDebitNoteVO.setCreatedBy(costDebitNoteDTO.getCreatedBy());
			costDebitNoteVO.setModifyBy(costDebitNoteDTO.getCreatedBy());

			message = "CostDebitNote Creation Successfully";

		} else {
			costDebitNoteVO = costDebitNoteRepo.findById(costDebitNoteDTO.getId()).orElseThrow(
					() -> new ApplicationException("Cost DebitNote Not Found with id: " + costDebitNoteDTO.getId()));
			costDebitNoteVO.setModifyBy(costDebitNoteDTO.getCreatedBy());

			message = "CostDebitNote Updation Successfully";
		}

		costDebitNoteVO = getCostDebitNoteVOFromCostDebitNoteDTO(costDebitNoteVO, costDebitNoteDTO);
		costDebitNoteRepo.save(costDebitNoteVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("costDebitNoteVO", costDebitNoteVO);
		return response;
	}

	private CostDebitNoteVO getCostDebitNoteVOFromCostDebitNoteDTO(CostDebitNoteVO costDebitNoteVO,
			@Valid CostDebitNoteDTO costDebitNoteDTO) {

		// Set individual fields from DTO to VO
		costDebitNoteVO.setBranch(costDebitNoteDTO.getBranch());
		costDebitNoteVO.setBranchCode(costDebitNoteDTO.getBranchCode());
		costDebitNoteVO.setCreatedBy(costDebitNoteDTO.getCreatedBy());
		costDebitNoteVO.setCancelRemarks(costDebitNoteDTO.getCancelRemarks());
		costDebitNoteVO.setFinYear(costDebitNoteDTO.getFinYear());
		costDebitNoteVO.setTaxExampt(costDebitNoteDTO.isTaxExampt());
		costDebitNoteVO.setIpNo(costDebitNoteDTO.getIpNo());
		costDebitNoteVO.setLatitude(costDebitNoteDTO.getLatitude());
		costDebitNoteVO.setOrgId(costDebitNoteDTO.getOrgId());
		costDebitNoteVO.setSubType(costDebitNoteDTO.getSubType());
		costDebitNoteVO.setProduct(costDebitNoteDTO.getProduct());
		costDebitNoteVO.setVohNo(costDebitNoteDTO.getVohNo());
		costDebitNoteVO.setVohDate(costDebitNoteDTO.getVohDate());
		costDebitNoteVO.setPartyType(costDebitNoteDTO.getPartyType());
		costDebitNoteVO.setSuppRefNo(costDebitNoteDTO.getSuppRefNo());
		costDebitNoteVO.setSuppDate(costDebitNoteDTO.getSuppDate());
		costDebitNoteVO.setPartyName(costDebitNoteDTO.getPartyName());
		costDebitNoteVO.setPartyCode(costDebitNoteDTO.getPartyCode());
		costDebitNoteVO.setCreditDays(costDebitNoteDTO.getCreditDays());
		costDebitNoteVO.setDueDate(costDebitNoteDTO.getDueDate());
		costDebitNoteVO.setAddress(costDebitNoteDTO.getAddress());
		costDebitNoteVO.setCurrency(costDebitNoteDTO.getCurrency());
		costDebitNoteVO.setExRate(costDebitNoteDTO.getExRate());
		costDebitNoteVO.setOtherInfo(costDebitNoteDTO.getOtherInfo());
		costDebitNoteVO.setRemarks(costDebitNoteDTO.getRemarks());
		costDebitNoteVO.setShipRefNo(costDebitNoteDTO.getShipRefNo());
		costDebitNoteVO.setStatus(costDebitNoteDTO.getStatus());
		costDebitNoteVO.setOrginBill(costDebitNoteDTO.getOrginBill());
		costDebitNoteVO.setGstType(costDebitNoteDTO.getGstType());
		costDebitNoteVO.setCurrentDate(costDebitNoteDTO.getCurrentDate());
		costDebitNoteVO.setCurrentDateValue(costDebitNoteDTO.getCurrentDateValue());

		// Deleting existing entries if updating
		if (costDebitNoteDTO.getId() != null) {
			List<CostDebitNoteGstVO> costDebitNoteGstVOs = costDebitNoteGstRepo.findByCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteGstRepo.deleteAll(costDebitNoteGstVOs);

			List<CostDebitNoteTaxPrtculVO> costDebitNoteTaxPrtculVOs = costDebitNoteTaxPrtculRepo
					.findByCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteTaxPrtculRepo.deleteAll(costDebitNoteTaxPrtculVOs);

			List<CostDebitChargesVO> costDebitChargesVOs = costDebitChargesRepo.findByCostDebitNoteVO(costDebitNoteVO);
			costDebitChargesRepo.deleteAll(costDebitChargesVOs);
		}


		BigDecimal totalChargeAmountLC = BigDecimal.ZERO;
		BigDecimal totalChargeAmountBC = BigDecimal.ZERO;
		BigDecimal totalTaxAmountLC = BigDecimal.ZERO;
		BigDecimal totalTaxAmountBC = BigDecimal.ZERO;
//		BigDecimal totalInvAmountLC = BigDecimal.ZERO;
//		BigDecimal totalInvAmountBC = BigDecimal.ZERO;
		BigDecimal sumLcAmount = BigDecimal.ZERO;

		// Adding Cost Debit Charges
		List<CostDebitChargesVO> costDebitChargesVOs = new ArrayList<>();
		for (CostDebitChargesDTO costDebitChargesDTO : costDebitNoteDTO.getCostDebitChargesDTO()) {
			CostDebitChargesVO costDebitChargesVO = new CostDebitChargesVO();

			costDebitChargesVO.setJobType(costDebitChargesDTO.getJobType());
			costDebitChargesVO.setJobNo(costDebitChargesDTO.getJobNo());
			costDebitChargesVO.setSubJobNo(costDebitChargesDTO.getSubJobNo());
			costDebitChargesVO.setHouseNo(costDebitChargesDTO.getHouseNo());
			costDebitChargesVO.setChargeCode(costDebitChargesDTO.getChargeCode());
			costDebitChargesVO.setGChargeCode(costDebitChargesDTO.getGChargeCode());
			costDebitChargesVO.setGSAC(costDebitChargesDTO.getGSAC());
			costDebitChargesVO.setChargeName(costDebitChargesDTO.getChargeName());
			costDebitChargesVO.setApplyOn(costDebitChargesDTO.getApplyOn());
			costDebitChargesVO.setCurrency(costDebitChargesDTO.getCurrency());
			costDebitChargesVO.setExRate(costDebitChargesDTO.getExRate());
			costDebitChargesVO.setRate(costDebitChargesDTO.getRate());
			costDebitChargesVO.setLedger(costDebitChargesDTO.getLedger());

			BigDecimal fcAmount;
			BigDecimal lcAmount;
			BigDecimal tlcAmount;
			BigDecimal billAmount;
			BigDecimal gstAmount;

			if (!costDebitChargesDTO.getCurrency().equals("INR")) {
				BigDecimal rate = costDebitChargesDTO.getRate(); // BigDecimal type is expected here
				BigDecimal qty = BigDecimal.valueOf(costDebitChargesDTO.getQty()); // Convert qty to BigDecimal

				fcAmount = rate.multiply(qty);

				costDebitChargesVO.setFcAmt(fcAmount);

			} else {
				fcAmount = BigDecimal.valueOf(0.00);
				costDebitChargesVO.setFcAmt(fcAmount);
			}

			BigDecimal exRate = costDebitChargesDTO.getExRate();// Assuming getExRate() returns BigDecimal
			BigDecimal qty = BigDecimal.valueOf(costDebitChargesDTO.getQty());
			BigDecimal rate = costDebitChargesDTO.getRate();
			lcAmount = exRate.multiply(qty.multiply(rate));
			costDebitChargesVO.setLcAmt(lcAmount);
			totalChargeAmountLC = totalChargeAmountLC.add(lcAmount);
			sumLcAmount = sumLcAmount.add(lcAmount);


			BigDecimal gstPercent = BigDecimal.valueOf(costDebitChargesDTO.getGstPercentage()); // Convert GSTPercent to
			// BigDecimal
			tlcAmount = lcAmount.multiply(gstPercent).divide(BigDecimal.valueOf(100));
			costDebitChargesVO.setTlcAmt(tlcAmount);

			billAmount = lcAmount.divide(exRate, RoundingMode.HALF_UP); // Ensure you specify a RoundingMode when
          // dividing
			costDebitChargesVO.setBillAmt(billAmount);
			totalChargeAmountBC = totalChargeAmountBC.add(billAmount);
			
			gstAmount = lcAmount.multiply(gstPercent).divide(BigDecimal.valueOf(100));
			costDebitChargesVO.setGst(gstAmount);
			totalTaxAmountLC = totalTaxAmountLC.add(gstAmount);
			totalTaxAmountBC = totalTaxAmountBC.add(gstAmount);
			
			costDebitChargesVO.setTaxPercentage(costDebitChargesDTO.getTaxPercentage());
			costDebitChargesVO.setGstPercentage(costDebitChargesDTO.getGstPercentage());
			costDebitChargesVO.setExampted(costDebitChargesDTO.isExampted());
			costDebitChargesVO.setTax(costDebitChargesDTO.isTax());

			costDebitChargesVO.setCostDebitNoteVO(costDebitNoteVO);
			costDebitChargesVOs.add(costDebitChargesVO);
			
		}
		
		// Adding Cost Debit Note Tax Particulars
		List<CostDebitNoteTaxPrtculVO> costDebitNoteTaxPrtculVOs = new ArrayList<>();
		for (CostDebitNoteTaxPrtculDTO costDebitNoteTaxPrtculDTO : costDebitNoteDTO.getCostDebitNoteTaxPrtculDTO()) {
			CostDebitNoteTaxPrtculVO costDebitNoteTaxPrtculVO = new CostDebitNoteTaxPrtculVO();

			costDebitNoteTaxPrtculVO.setTds(costDebitNoteTaxPrtculDTO.getTds());
			costDebitNoteTaxPrtculVO.setTdsPercentage(costDebitNoteTaxPrtculDTO.getTdsPercentage());
			costDebitNoteTaxPrtculVO.setSection(costDebitNoteTaxPrtculDTO.getSection());
			
			
			BigDecimal totTdsWhAmt = BigDecimal.ZERO;
			BigDecimal tdsWhPercent = costDebitNoteTaxPrtculDTO.getTdsPercentage();
			System.out.println("TOTAL LC AMOUNT IS :"+sumLcAmount);;
			totTdsWhAmt = sumLcAmount.multiply(tdsWhPercent.divide(BigDecimal.valueOf(100)));
			costDebitNoteTaxPrtculVO.setTotTDSAmt(totTdsWhAmt);
			

			costDebitNoteTaxPrtculVO.setCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteTaxPrtculVOs.add(costDebitNoteTaxPrtculVO);
		}
		costDebitNoteVO.setCostDebitNoteTaxPrtculVO(costDebitNoteTaxPrtculVOs);
		
		Map<String, BigDecimal> ledgerSumMap = new HashMap<>();
		for (CostDebitChargesVO detailsVO : costDebitChargesVOs) {
			String ledger = detailsVO.getLedger();
			BigDecimal lcAmount = detailsVO.getLcAmt();

			ledgerSumMap.put(ledger, ledgerSumMap.getOrDefault(ledger, BigDecimal.ZERO).add(lcAmount));
		}
		
		List<CostDebitNoteGstVO> costDebitNoteGstVOs = new ArrayList<>();
		for (Map.Entry<String, BigDecimal> entry : ledgerSumMap.entrySet()) {
			CostDebitNoteGstVO costDebitNoteGstVO = new CostDebitNoteGstVO();
			
			costDebitNoteGstVO.setChargeAcc(entry.getKey());
			costDebitNoteGstVO.setSubLodgerCode("None");
			costDebitNoteGstVO.setDBillAmt(BigDecimal.ZERO);
			costDebitNoteGstVO.setCrBillAmt(entry.getValue());
			costDebitNoteGstVO.setDBLCAmt(BigDecimal.ZERO);
			costDebitNoteGstVO.setCrLCAmt(entry.getValue());
//			costDebitNoteGstVO.setRemarks(costDebitNoteGstDTO.getRemarks());
			
			costDebitNoteGstVO.setCostDebitNoteVO(costDebitNoteVO);
			costDebitNoteGstVOs.add(costDebitNoteGstVO);
		}
		costDebitNoteVO.setCostDebitNoteGstVO(costDebitNoteGstVOs);
		costDebitNoteVO.setCostDebitChargesVO(costDebitChargesVOs);

		return costDebitNoteVO;
	}

	@Override
	public List<CostDebitNoteVO> getCostDebitNoteByOrgId(Long orgId) {

		return costDebitNoteRepo.getByCostDebitNoteByOrgId(orgId);
	}

	@Override
	public List<CostDebitNoteVO> getCostDebitNoteById(Long id) {
		return costDebitNoteRepo.getByCostDebitNoteById(id);
	}

	@Override
	public List<CostDebitNoteVO> getActiveCostDebitNote() {
		return costDebitNoteRepo.getActiveCostDebitNote();
	}

	@Override
	public CostDebitNoteVO getAllCostDebitNoteByDocId(Long orgId, String docId) {
		return costDebitNoteRepo.findAllCostDebitNoteByDocId(orgId, docId);
	}

	// 8778426636
	@Override
	public String getCostDebitNoteDocId(Long orgId, String finYear, String branch, String branchCode) {
		String ScreenCode = "CDN";
		String result = costDebitNoteRepo.getCostDebitNoteDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> chargeTypeDetailsForCostDebitNote(Long orgId) {
		Set<Object[]> getChargeTypeDetails = costDebitNoteRepo.getChareDetails(orgId);
		return getChargeType(getChargeTypeDetails); // Call the correct method
	}

	private List<Map<String, Object>> getChargeType(Set<Object[]> getCharge) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] grid : getCharge) {
			Map<String, Object> details = new HashMap<>();
			details.put("chargeType", grid[0] != null ? grid[0].toString() : "");
			details.put("chargeCode", grid[1] != null ? grid[1].toString() : "");
			details.put("govtSac", grid[2] != null ? grid[2].toString() : "");
			details.put("serviceAccountCode", grid[3] != null ? grid[3].toString() : ""); // Updated to match
																							// "serviceAccountCode"

			gridDetails.add(details);
		}
		return gridDetails;
	}

	@Override
	public List<Map<String, Object>> partyDetailsForCostDebitNote(Long orgId, String branch, String finYear) {
		// Fetch party details from the repository
		Set<Object[]> getPartyTypeDetails = costDebitNoteRepo.getParty(orgId, branch, finYear);
		// Return processed party details
		return getPartyDetails(getPartyTypeDetails);
	}

	private List<Map<String, Object>> getPartyDetails(Set<Object[]> getPartyInformation) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		// Iterate through the result set
		for (Object[] grid : getPartyInformation) {

			Map<String, Object> details = new HashMap<>(); 
			details.put("partyName", grid[0] != null ? grid[0].toString() : "");
			details.put("partyCode", grid[1] != null ? grid[1].toString() : "");
			details.put("partyType", grid[2] != null ? grid[2].toString() : "");
			details.put("addressType", grid[3] != null ? grid[3].toString() : "");

			gridDetails.add(details);

		}
		return gridDetails; // Return the list of party details
	}

	@Override
	public List<Map<String, Object>> getAllDocIdForCostInvoice(Long orgId) {
		Set<Object[]> getAllDocId = costDebitNoteRepo.getDocIdForCI(orgId);
		// Return processed party details
		return getAllCIDocId(getAllDocId);
	}

	private List<Map<String, Object>> getAllCIDocId(Set<Object[]> getDocIdInformation) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		// Iterate through the result set
		for (Object[] grid : getDocIdInformation) {

			Map<String, Object> details = new HashMap<>();
			details.put("docId", grid[0] != null ? grid[0].toString() : "");
			gridDetails.add(details);

		}
		return gridDetails; // Return the list of party details
	}

	@Override
	public List<Map<String, Object>> getCurrencyAndExrates(Long orgId) {
		Set<Object[]> currency = costDebitNoteRepo.getCurrencyAndExrateDetails(orgId);
		return getCurrency(currency);
	}

	private List<Map<String, Object>> getCurrency(Set<Object[]> currency) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : currency) {
			Map<String, Object> map = new HashMap<>();
			map.put("currency", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("currencyDescription", ch[1] != null ? ch[1].toString() : "");
			map.put("buyingExRate", ch[2] != null ? ch[2].toString() : "");
			map.put("sellingExRate", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<CostInvoiceVO> getOrginBillNoByParty(Long orgId, String party, String branchCode) {
		return costDebitNoteRepo.findOrginBillNoByParty(orgId,party,branchCode);
	}
	
	
}
