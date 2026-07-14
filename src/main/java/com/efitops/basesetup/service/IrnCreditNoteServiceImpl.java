package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.IrnCreditNoteDTO;
import com.efitops.basesetup.dto.IrnCreditNoteDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.IrnCreditNoteDetailsVO;
import com.efitops.basesetup.entity.IrnCreditNoteGstVO;
import com.efitops.basesetup.entity.IrnCreditNoteVO;
import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.TaxInvoiceVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.IrnCreditNoteDetailsRepo;
import com.efitops.basesetup.repo.IrnCreditNoteGstRepo;
import com.efitops.basesetup.repo.IrnCreditNoteRepo;
import com.efitops.basesetup.repo.PartyMasterRepo;
import com.efitops.basesetup.repo.TaxInvoiceRepo;

@Service
public class IrnCreditNoteServiceImpl implements IrnCreditNoteService {
	public static final Logger LOGGER = LoggerFactory.getLogger(IrnCreditNoteServiceImpl.class);
	@Autowired
	IrnCreditNoteRepo irnCreditRepo;

	@Autowired
	IrnCreditNoteDetailsRepo irnCreditChargesRepo;

	@Autowired
	IrnCreditNoteGstRepo irnCreditGstRepo;
	
	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;
	
	@Autowired
	PartyMasterRepo partyMasterRepo;
	
	@Autowired
	TaxInvoiceRepo taxInvoiceRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public List<IrnCreditNoteVO> getAllIrnCreditByOrgId(Long orgId) {
		List<IrnCreditNoteVO> irnCreditVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  IrnCredit BY OrgId : {}", orgId);
			irnCreditVO = irnCreditRepo.getAllIrnCreditByOrgId(orgId);
		} 
		return irnCreditVO;
	}

	@Override
	public List<IrnCreditNoteVO> getAllIrnCreditById(Long id) {
		List<IrnCreditNoteVO> irnCreditVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  IrnCredit BY Id : {}", id);
			irnCreditVO = irnCreditRepo.getAllIrnCreditById(id);
		} 
		return irnCreditVO;
	}

	@Override
	public Map<String, Object> updateCreateIrnCreditNote(IrnCreditNoteDTO irnCreditNoteDTO) throws ApplicationException {
		String screenCode = "ICN";
		IrnCreditNoteVO irnCreditNoteVO = new IrnCreditNoteVO();
		String message;
		if (ObjectUtils.isNotEmpty(irnCreditNoteDTO.getId())) {
			irnCreditNoteVO = irnCreditRepo.findById(irnCreditNoteDTO.getId())
					.orElseThrow(() -> new ApplicationException("IRN Credit Note not found"));

			irnCreditNoteVO.setModifiedBy(irnCreditNoteDTO.getCreatedBy());
			createUpdateIrnCreditNoteVOByIrnCreditNoteDTO(irnCreditNoteDTO, irnCreditNoteVO);
			message = "IRN Credit Note Updated Successfully";
		} else {
			// GETDOCID API
			String docId = irnCreditRepo.getIrnCreditDocId(irnCreditNoteVO.getOrgId(), irnCreditNoteVO.getFinYear(),
					irnCreditNoteVO.getBranchCode(), screenCode);
			irnCreditNoteVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(irnCreditNoteVO.getOrgId(),
							irnCreditNoteVO.getFinYear(), irnCreditNoteVO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			irnCreditNoteVO.setCreatedBy(irnCreditNoteDTO.getCreatedBy());
			irnCreditNoteVO.setModifiedBy(irnCreditNoteDTO.getCreatedBy());
			createUpdateIrnCreditNoteVOByIrnCreditNoteDTO(irnCreditNoteDTO, irnCreditNoteVO);
			message = "Tax Invoice Created Successfully";
		}

		irnCreditRepo.save(irnCreditNoteVO);
		Map<String, Object> response = new HashMap<>();
		response.put("irnCreditNoteVO", irnCreditNoteVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateIrnCreditNoteVOByIrnCreditNoteDTO(IrnCreditNoteDTO irnCreditNoteDTO, IrnCreditNoteVO irnCreditNoteVO) {

		// Map fields from DTO to VO
		irnCreditNoteVO.setOrgId(irnCreditNoteDTO.getOrgId());
	    irnCreditNoteVO.setBranch(irnCreditNoteDTO.getBranch());
	    irnCreditNoteVO.setBranchCode(irnCreditNoteDTO.getBranchCode());
	    irnCreditNoteVO.setFinYear(irnCreditNoteDTO.getFinYear());
	    irnCreditNoteVO.setCreatedBy(irnCreditNoteDTO.getCreatedBy());
	    irnCreditNoteVO.setBizType(irnCreditNoteDTO.getBizType());
	    irnCreditNoteVO.setBizMode(irnCreditNoteDTO.getBizMode());
	    irnCreditNoteVO.setPartyName(irnCreditNoteDTO.getPartyName());
	    irnCreditNoteVO.setPartyCode(irnCreditNoteDTO.getPartyCode());
	    irnCreditNoteVO.setPartyType(irnCreditNoteDTO.getPartyType());
	    irnCreditNoteVO.setStateNo(irnCreditNoteDTO.getStateNo());
	    irnCreditNoteVO.setStateCode(irnCreditNoteDTO.getStateCode());
	    irnCreditNoteVO.setRecipientGSTIN(irnCreditNoteDTO.getRecipientGSTIN());
	    irnCreditNoteVO.setPlaceOfSupply(irnCreditNoteDTO.getPlaceOfSupply());
	    irnCreditNoteVO.setAddressType(irnCreditNoteDTO.getAddressType());
	    irnCreditNoteVO.setAddress(irnCreditNoteDTO.getAddress());
	    irnCreditNoteVO.setPinCode(irnCreditNoteDTO.getPinCode());
	    irnCreditNoteVO.setStatus(irnCreditNoteDTO.getStatus());
	    irnCreditNoteVO.setGstType(irnCreditNoteDTO.getGstType());
	    irnCreditNoteVO.setOriginBillNo(irnCreditNoteDTO.getOriginBillNo());
	    irnCreditNoteVO.setVoucherNo(irnCreditNoteDTO.getVoucherNo());
	    irnCreditNoteVO.setVoucherDate(irnCreditNoteDTO.getVoucherDate());
	    irnCreditNoteVO.setSupplierRefNo(irnCreditNoteDTO.getSupplierRefNo());
	    irnCreditNoteVO.setSupplierRefDate(irnCreditNoteDTO.getSupplierRefDate());
	    irnCreditNoteVO.setBillCurr(irnCreditNoteDTO.getBillCurr());
	    irnCreditNoteVO.setBillCurrRate(irnCreditNoteDTO.getBillCurrRate());
	    irnCreditNoteVO.setExAmount(irnCreditNoteDTO.getExAmount());
	    irnCreditNoteVO.setCreditDays(irnCreditNoteDTO.getCreditDays());
	    irnCreditNoteVO.setShipperRefNo(irnCreditNoteDTO.getShipperRefNo());
	    irnCreditNoteVO.setBillMonth(irnCreditNoteDTO.getBillMonth());
	    irnCreditNoteVO.setSalesType(irnCreditNoteDTO.getSalesType());
	    irnCreditNoteVO.setCreditRemarks(irnCreditNoteDTO.getCreditRemarks());
	    
		if (ObjectUtils.isNotEmpty(irnCreditNoteVO.getId())) {
			List<IrnCreditNoteDetailsVO> irnCreditNoteDetailsVO1 = irnCreditChargesRepo.findByIrnCreditNoteVO(irnCreditNoteVO);
			irnCreditChargesRepo.deleteAll(irnCreditNoteDetailsVO1);
		}
		BigDecimal totalChargeAmountLC = BigDecimal.ZERO;
		BigDecimal totalChargeAmountBC = BigDecimal.ZERO;
		BigDecimal totalTaxAmountLC = BigDecimal.ZERO;
		BigDecimal totalTaxAmountBC = BigDecimal.ZERO;
		BigDecimal totalInvAmountLC = BigDecimal.ZERO;
		BigDecimal totalInvAmountBC = BigDecimal.ZERO;

		List<IrnCreditNoteDetailsVO> irnCreditNoteDetailsVOs = new ArrayList<>();
		for (IrnCreditNoteDetailsDTO irnCreditNoteDetailsDTO : irnCreditNoteDTO.getIrnCreditNoteDetailsDTO()) {

			IrnCreditNoteDetailsVO irnCreditNoteDetailsVO = new IrnCreditNoteDetailsVO();
			irnCreditNoteDetailsVO.setChargeType(irnCreditNoteDetailsDTO.getChargeType());
			irnCreditNoteDetailsVO.setChargeCode(irnCreditNoteDetailsDTO.getChargeCode());
			irnCreditNoteDetailsVO.setGovChargeCode(irnCreditNoteDetailsDTO.getGovChargeCode());
			irnCreditNoteDetailsVO.setLedger(irnCreditNoteDetailsDTO.getLedger());
			irnCreditNoteDetailsVO.setChargeName(irnCreditNoteDetailsDTO.getChargeName());
			irnCreditNoteDetailsVO.setTaxable(irnCreditNoteDetailsDTO.getTaxable());
			irnCreditNoteDetailsVO.setQty(irnCreditNoteDetailsDTO.getQty());
			irnCreditNoteDetailsVO.setRate(irnCreditNoteDetailsDTO.getRate());
			irnCreditNoteDetailsVO.setCurrency(irnCreditNoteDetailsDTO.getCurrency());
			irnCreditNoteDetailsVO.setExRate(irnCreditNoteDetailsDTO.getExRate());
			irnCreditNoteDetailsVO.setExempted(irnCreditNoteDetailsDTO.getExempted());
			irnCreditNoteDetailsVO.setSac(irnCreditNoteDetailsDTO.getSac());
			irnCreditNoteDetailsVO.setGSTPercent(irnCreditNoteDetailsDTO.getGSTPercent());

			BigDecimal fcAmount;
			BigDecimal lcAmount;
			BigDecimal tlcAmount;
			BigDecimal billAmount;
			BigDecimal gstAmount;

			if (!irnCreditNoteDetailsDTO.getCurrency().equals("INR")) {
				BigDecimal rate = irnCreditNoteDetailsDTO.getRate(); // BigDecimal type is expected here
				BigDecimal qty = BigDecimal.valueOf(irnCreditNoteDetailsDTO.getQty()); // Convert qty to BigDecimal

				fcAmount = rate.multiply(qty);

				irnCreditNoteDetailsVO.setFcAmount(fcAmount);

			} else {
				fcAmount = BigDecimal.valueOf(0.00);
				irnCreditNoteDetailsVO.setFcAmount(fcAmount);
			}

			BigDecimal exRate = irnCreditNoteDetailsDTO.getExRate();// Assuming getExRate() returns BigDecimal
			BigDecimal qty = BigDecimal.valueOf(irnCreditNoteDetailsDTO.getQty());
			BigDecimal rate = irnCreditNoteDetailsDTO.getRate();
			lcAmount = exRate.multiply(qty.multiply(rate));
			irnCreditNoteDetailsVO.setLcAmount(lcAmount);
			totalChargeAmountLC = totalChargeAmountLC.add(lcAmount);

			BigDecimal gstPercent = BigDecimal.valueOf(irnCreditNoteDetailsDTO.getGSTPercent()); // Convert GSTPercent to
																								// BigDecimal
			tlcAmount = lcAmount.multiply(gstPercent).divide(BigDecimal.valueOf(100));
			irnCreditNoteDetailsVO.setTlcAmount(tlcAmount);

			billAmount = lcAmount.divide(exRate, RoundingMode.HALF_UP); // Ensure you specify a RoundingMode when
																		// dividing
			irnCreditNoteDetailsVO.setBillAmount(billAmount);
			totalChargeAmountBC = totalChargeAmountBC.add(billAmount);

			gstAmount = lcAmount.multiply(gstPercent).divide(BigDecimal.valueOf(100));
			irnCreditNoteDetailsVO.setGstAmount(gstAmount);
			totalTaxAmountLC = totalTaxAmountLC.add(gstAmount);
			totalTaxAmountBC = totalTaxAmountBC.add(gstAmount);

			irnCreditNoteDetailsVO.setIrnCreditNoteVO(irnCreditNoteVO);
			irnCreditNoteDetailsVOs.add(irnCreditNoteDetailsVO);
		}

		Map<String, BigDecimal> ledgerSumMap = new HashMap<>();
		for (IrnCreditNoteDetailsVO detailsVO : irnCreditNoteDetailsVOs) {
			String ledger = detailsVO.getLedger();
			BigDecimal lcAmount = detailsVO.getLcAmount();

			ledgerSumMap.put(ledger, ledgerSumMap.getOrDefault(ledger, BigDecimal.ZERO).add(lcAmount));
		}
		if (ObjectUtils.isNotEmpty(irnCreditNoteVO.getId())) {
			List<IrnCreditNoteGstVO> irnCreditNoteGstVO = irnCreditGstRepo.findByIrnCreditNoteVO(irnCreditNoteVO);
			irnCreditGstRepo.deleteAll(irnCreditNoteGstVO);
		}
		List<IrnCreditNoteGstVO> irnCreditNoteGstVOList = new ArrayList<>();
		for (Map.Entry<String, BigDecimal> entry : ledgerSumMap.entrySet()) {
			IrnCreditNoteGstVO irnCreditNoteGstVO = new IrnCreditNoteGstVO();
			irnCreditNoteGstVO.setGstChargeAcc(entry.getKey());
			irnCreditNoteGstVO.setGstCrLcAmount(entry.getValue());
			irnCreditNoteGstVO.setGstDbBillAmount(BigDecimal.ZERO);
			irnCreditNoteGstVO.setGstDbLcAmount(BigDecimal.ZERO);
			irnCreditNoteGstVO.setGstSubledgerCode("None");
			irnCreditNoteGstVO.setGstCrBillAmount(entry.getValue());
			irnCreditNoteGstVO.setIrnCreditNoteVO(irnCreditNoteVO);
			irnCreditNoteGstVOList.add(irnCreditNoteGstVO);
		}
		irnCreditNoteVO.setIrnCreditNoteGstVO(irnCreditNoteGstVOList);

		totalInvAmountLC = totalChargeAmountLC.add(totalTaxAmountLC);
		totalInvAmountBC = totalChargeAmountBC.add(totalTaxAmountBC);

		irnCreditNoteVO.setTotalChargeAmountLc(totalChargeAmountLC);
		irnCreditNoteVO.setTotalChargeAmountBc(totalChargeAmountBC);
		irnCreditNoteVO.setTotalTaxAmountLc(totalTaxAmountLC);
		irnCreditNoteVO.setTotalTaxAmountBc(totalTaxAmountBC);

		BigDecimal originalTotalInvAmountLC = totalChargeAmountLC.add(totalTaxAmountLC);
		BigDecimal roundedTotalInvAmountLC = totalInvAmountLC.setScale(0, RoundingMode.HALF_UP);
		BigDecimal roundOffAmountLC = roundedTotalInvAmountLC.subtract(originalTotalInvAmountLC);
		irnCreditNoteVO.setTotalInvAmountLc(roundedTotalInvAmountLC);
		irnCreditNoteVO.setAmountInWords(amountInWordsConverterService.convert(irnCreditNoteVO.getTotalInvAmountLc().longValue()));
		irnCreditNoteVO.setRoundOffAmountLc(roundOffAmountLC);

		BigDecimal roundedTotalInvAmountBC = totalInvAmountBC.setScale(0, RoundingMode.HALF_UP);
		irnCreditNoteVO.setTotalInvAmountBc(roundedTotalInvAmountBC);

		irnCreditNoteVO.setIrnCreditNoteDetailsVO(irnCreditNoteDetailsVOs);

	}

	@Override
	public String getIrnCreditNoteDocId(Long orgId, String finYear, String branch, String branchCode) {
		String ScreenCode = "ICN";
		String result = irnCreditRepo.getIrnCreditDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}
	
	@Override
	public List<PartyMasterVO> getAllPartyByPartyType(Long orgId, String partyType) {
		
		return partyMasterRepo.findByOrgIdAndPartyTypeIgnoreCase(orgId,partyType);

	}

	@Override
	public List<TaxInvoiceVO> getOriginBillNofromTaxInvoiceByParty(Long orgId, String party,String branchCode) {
		return taxInvoiceRepo.findPartyInvoiceDetails(orgId,party,branchCode);
	}

}
