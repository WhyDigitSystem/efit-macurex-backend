package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.ResponseDTO.CustomerOtherSalesResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseInwardDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseReturnDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseReturnResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseReturnTaxDetailsResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.PurchaseReturnDTO;
import com.efitops.basesetup.dto.PurchaseReturnDetailsDTO;
import com.efitops.basesetup.dto.PurchaseReturnTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.PurchaseReturnDetailsVO;
import com.efitops.basesetup.entity.PurchaseReturnTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.PurchaseReturnDetailsRepo;
import com.efitops.basesetup.repository.PurchaseReturnRepo;
import com.efitops.basesetup.repository.PurchaseReturnTaxDetailsRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseReturnServiceImpl.class);

	@Autowired
	private PurchaseReturnRepo purchaseReturnRepo;

	@Autowired
	private PurchaseReturnDetailsRepo purchaseReturnDetailsRepo;

	@Autowired
	private PurchaseReturnTaxDetailsRepo purchaseReturnTaxDetailsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	private AmountInWordsConverterService amountInWordsConverterService;

	@Override
	public PurchaseReturnResponseDTO getPurchaseReturnById(Long id) throws ApplicationException {

		PurchaseReturnVO purchaseReturnVO = purchaseReturnRepo.getPurchaseReturnById(id);

		if (purchaseReturnVO == null) {
			throw new ApplicationException("Purchase Return Not Found");
		}

		return buildPurchaseReturnResponse(purchaseReturnVO);
	}

	@Override
	public List<PurchaseReturnResponseDTO> getPurchaseReturnByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<PurchaseReturnVO> purchaseReturnList = purchaseReturnRepo.getPurchaseReturnByOrgId(orgId, branch);

		if (purchaseReturnList == null || purchaseReturnList.isEmpty()) {
			throw new ApplicationException("Purchase Return Not Found");
		}

		List<PurchaseReturnResponseDTO> responseList = new ArrayList<>();

		for (PurchaseReturnVO purchaseReturnVO : purchaseReturnList) {
			responseList.add(buildPurchaseReturnResponse(purchaseReturnVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdatePurchaseReturn(PurchaseReturnDTO purchaseReturnDTO)
			throws ApplicationException {
		String screenCode = "PR";
		PurchaseReturnVO purchaseReturnVO = new PurchaseReturnVO();
		String message;

		if (ObjectUtils.isNotEmpty(purchaseReturnDTO.getId())) {

			purchaseReturnVO = purchaseReturnRepo.findById(purchaseReturnDTO.getId())
					.orElseThrow(() -> new ApplicationException("Purchase Return Not Found"));

			purchaseReturnVO.setUpdatedBy(purchaseReturnDTO.getCreatedBy());

			message = "Purchase Return Updated Successfully";

		} else {

			String docId = purchaseReturnRepo.getPurchaseReturnDocId(purchaseReturnDTO.getOrgId(),
					purchaseReturnDTO.getFinancialYear(), screenCode);

			purchaseReturnVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(purchaseReturnDTO.getOrgId(),
							purchaseReturnDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO != null) {
				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			purchaseReturnVO.setCreatedBy(purchaseReturnDTO.getCreatedBy());
			purchaseReturnVO.setUpdatedBy(purchaseReturnDTO.getCreatedBy());

			message = "Purchase Return Created Successfully";
		}

		createUpdatePurchaseReturnVOByPurchaseReturnDTO(purchaseReturnDTO, purchaseReturnVO);

		purchaseReturnVO = purchaseReturnRepo.save(purchaseReturnVO);

		PurchaseReturnResponseDTO responseDTO = buildPurchaseReturnResponse(purchaseReturnVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("purchaseReturnVO", responseDTO);

		return response;
	}

	private void createUpdatePurchaseReturnVOByPurchaseReturnDTO(PurchaseReturnDTO dto, PurchaseReturnVO vo)
			throws ApplicationException {
		vo.setBelongsTo(dto.getBelongsTo());
		vo.setGrnNo(dto.getGrnNo());
		vo.setGrnDate(dto.getGrnDate());
		vo.setTotalFreight(dto.getTotalFreight());
		vo.setIsIgstAppl(dto.getIsIgstAppl());
		vo.setExcisable(dto.getExcisable());
		vo.setVendorDcNo(dto.getVendorDcNo());
		vo.setExchangeRate(dto.getExchangeRate());
		vo.setDealerType(dto.getDealerType());
		vo.setPurchaseorderNumber(dto.getPurchaseorderNumber());
		vo.setPurchaseorderType(dto.getPurchaseorderType());
		vo.setPurchaseorderDate(dto.getPurchaseorderDate());
		vo.setIsReverseChrg(dto.getIsReverseChrg());
		vo.setVoucherPostingDate(dto.getVoucherPostingDate());
		vo.setDutyPerUnit(dto.getDutyPerUnit());
		vo.setModvatCopyReceived(dto.getModvatCopyReceived());
		vo.setSupplierDcInvNo(dto.getSupplierDcInvNo());
		vo.setSupplierDcInvDate(dto.getSupplierDcInvDate());
		vo.setEntryTaxApplicable(dto.getEntryTaxApplicable());
		vo.setNarration(dto.getNarration());
		vo.setPaymentTerms(dto.getPaymentTerms());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getSupplier() != null && dto.getSupplier() != 0) {
			CustomerVO supplier = customerRepo.findById(dto.getSupplier())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));
			vo.setSupplier(supplier);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<PurchaseReturnDetailsVO> purchaseReturnDetailsVO = purchaseReturnDetailsRepo
					.findByPurchaseReturnVO(vo);

			purchaseReturnDetailsRepo.deleteAll(purchaseReturnDetailsVO);

			List<PurchaseReturnTaxDetailsVO> purchaseReturnTaxDetailsVO = purchaseReturnTaxDetailsRepo
					.findByPurchaseReturnVO(vo);
			purchaseReturnTaxDetailsRepo.deleteAll(purchaseReturnTaxDetailsVO);
		}

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalQty = BigDecimal.ZERO;
		BigDecimal basicValue = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		List<PurchaseReturnDetailsVO> detailsList = new ArrayList<>();

		if (dto.getPurchaseReturnDetailsDTO() != null) {
			for (PurchaseReturnDetailsDTO detailDTO : dto.getPurchaseReturnDetailsDTO()) {

				PurchaseReturnDetailsVO detailVO = new PurchaseReturnDetailsVO();

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));
					detailVO.setItem(item);
				}

				if (detailDTO.getUnit() != null && detailDTO.getUnit() != 0) {
					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));
					detailVO.setUnit(unit);
				}

				detailVO.setHsnSacCode(detailDTO.getHsnSacCode());
				detailVO.setTaxType(detailDTO.getTaxType());
				detailVO.setTaxPercentage(detailDTO.getTaxPercentage());
				detailVO.setTariffNo(detailDTO.getTariffNo());
				detailVO.setExciseToPost(detailDTO.getExciseToPost());
				detailVO.setChallanQty(detailDTO.getChallanQty());
				detailVO.setGrnReceivedQty(detailDTO.getGrnReceivedQty());
				detailVO.setAcceptedQty(detailDTO.getAcceptedQty());
				detailVO.setRejectedQty(detailDTO.getRejectedQty());
				detailVO.setShortageQty(detailDTO.getChallanQty().subtract(detailDTO.getGrnReceivedQty()));
				detailVO.setPoRate(detailDTO.getPoRate());
				detailVO.setRateInInr(detailDTO.getRateInInr());

				totalQty = totalQty.add(detailVO.getGrnReceivedQty());
				detailVO.setApportionedCost(detailDTO.getApportionedCost());
				detailVO.setLandedCostRate(detailDTO.getLandedCostRate());
				detailVO.setAdditionalDuty(detailDTO.getAdditionalDuty());
				detailVO.setAmountInInr(detailDTO.getChallanQty().multiply(detailDTO.getRateInInr()));

				BigDecimal quantity = detailDTO.getChallanQty() == null ? BigDecimal.ZERO : detailDTO.getChallanQty();

				BigDecimal amount = detailDTO.getRateInInr() == null ? BigDecimal.ZERO : detailDTO.getRateInInr();

				BigDecimal orderAmount = quantity.multiply(amount);

				detailVO.setRateInSelectedCurrency(detailDTO.getRateInSelectedCurrency());

				detailVO.setAmount(orderAmount);

				detailVO.setAmountInSelectedCurrency(detailVO.getRateInSelectedCurrency().multiply(quantity));

				BigDecimal igstRate = BigDecimal.ZERO;
				BigDecimal cgstRate = BigDecimal.ZERO;
				BigDecimal sgstRate = BigDecimal.ZERO;

				BigDecimal igstAmount = BigDecimal.ZERO;
				BigDecimal cgstAmount = BigDecimal.ZERO;
				BigDecimal sgstAmount = BigDecimal.ZERO;

				if (dto.getIsIgstAppl() != null && dto.getIsIgstAppl().equalsIgnoreCase("Yes")) {

					igstRate = detailDTO.getTaxPercentage() != null ? detailDTO.getTaxPercentage() : BigDecimal.ZERO;

					igstAmount = orderAmount.multiply(igstRate).divide(BigDecimal.valueOf(100));

					cgstRate = BigDecimal.ZERO;
					sgstRate = BigDecimal.ZERO;

					cgstAmount = BigDecimal.ZERO;
					sgstAmount = BigDecimal.ZERO;

				} else {

					BigDecimal taxPercentage = detailDTO.getTaxPercentage() != null ? detailDTO.getTaxPercentage()
							: BigDecimal.ZERO;

					cgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

					sgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

					cgstAmount = orderAmount.multiply(cgstRate).divide(BigDecimal.valueOf(100));

					sgstAmount = orderAmount.multiply(sgstRate).divide(BigDecimal.valueOf(100));

					igstRate = BigDecimal.ZERO;
					igstAmount = BigDecimal.ZERO;
				}

				detailVO.setIgstRate(igstRate);
				detailVO.setCgstRate(cgstRate);
				detailVO.setSgstRate(sgstRate);

				detailVO.setIgstAmount(igstAmount);
				detailVO.setCgstAmount(cgstAmount);
				detailVO.setSgstAmount(sgstAmount);
				totalTaxAmount = totalTaxAmount.add(igstAmount).add(cgstAmount).add(sgstAmount);
				totalAmount = totalAmount.add(detailVO.getAmount());
				detailVO.setPurchaseReturnVO(vo);
				detailsList.add(detailVO);
			}
		}

		vo.setPurchaseReturnDetailsVO(detailsList);

		List<PurchaseReturnTaxDetailsVO> taxList = new ArrayList<>();

		if (dto.getPurchaseReturnDetailsDTO() != null) {
			for (PurchaseReturnTaxDetailsDTO taxDTO : dto.getPurchaseReturnTaxDetailsDTO()) {

				PurchaseReturnTaxDetailsVO taxVO = new PurchaseReturnTaxDetailsVO();

				taxVO.setParticulars(taxDTO.getParticulars());
				taxVO.setTax(taxDTO.getTax());
				taxVO.setAcceptedQtyAmount(taxDTO.getAcceptedQtyAmount());
				taxVO.setRevisedAmount(taxDTO.getRevisedAmount());
				taxVO.setPurchaseReturnVO(vo);

				taxList.add(taxVO);
			}
		}

		vo.setPurchaseReturnTaxDetailsVO(taxList);
		vo.setTotalQty(totalQty);
		vo.setTotalAmount(totalAmount);
		vo.setBasicValue(basicValue);
		vo.setAmountInWords(amountInWordsConverterService.convert(vo.getTotalAmount()));

	}

	private PurchaseReturnResponseDTO buildPurchaseReturnResponse(PurchaseReturnVO vo) {

		PurchaseReturnResponseDTO responseDTO = new PurchaseReturnResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setGrnNo(vo.getGrnNo());
		responseDTO.setGrnDate(vo.getGrnDate());
		responseDTO.setIsIgstAppl(vo.getIsIgstAppl());
		responseDTO.setExcisable(vo.getExcisable());
		responseDTO.setVendorDcNo(vo.getVendorDcNo());
		responseDTO.setExchangeRate(vo.getExchangeRate());
		responseDTO.setDealerType(vo.getDealerType());

		responseDTO.setPurchaseorderNumber(vo.getPurchaseorderNumber());
		responseDTO.setPurchaseorderType(vo.getPurchaseorderType());
		responseDTO.setPurchaseorderDate(vo.getPurchaseorderDate());

		responseDTO.setIsReverseChrg(vo.getIsReverseChrg());
		responseDTO.setVoucherPostingDate(vo.getVoucherPostingDate());
		responseDTO.setDutyPerUnit(vo.getDutyPerUnit());

		responseDTO.setModvatCopyReceived(vo.getModvatCopyReceived());

		responseDTO.setSupplierDcInvNo(vo.getSupplierDcInvNo());
		responseDTO.setSupplierDcInvDate(vo.getSupplierDcInvDate());

		responseDTO.setAmountInWords(vo.getAmountInWords());
		responseDTO.setEntryTaxApplicable(vo.getEntryTaxApplicable());

		responseDTO.setNarration(vo.getNarration());
		responseDTO.setPaymentTerms(vo.getPaymentTerms());

		// Totals
		responseDTO.setTotalFreight(vo.getTotalFreight());
		responseDTO.setTotalQty(vo.getTotalQty());
		responseDTO.setBasicValue(vo.getBasicValue());
		responseDTO.setTotalAmount(vo.getTotalAmount());

		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());

		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());

		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setScreenName(vo.getScreenName());

		if (vo.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
			responseDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranchCode(vo.getBranch().getBranchCode());
		}

		if (vo.getSupplier() != null) {

			CustomerOtherSalesResponseDTO customerDTO = new CustomerOtherSalesResponseDTO();

			customerDTO.setId(vo.getSupplier().getId());
			customerDTO.setCustomerName(vo.getSupplier().getCustomerName());
			customerDTO.setCustomerCode(vo.getSupplier().getCustomerCode());
			customerDTO.setCustomerGstNo(vo.getSupplier().getGstNo());

			customerDTO.setGstApproval(vo.getSupplier().isGstApplicable() ? "Yes" : "No");

//			if (vo.getSupplier().getGstState() != null) {
//				customerDTO.setState(vo.getSupplier().getGstState().getStateName());
//			}

			responseDTO.setSupplier(customerDTO);
		}

		List<PurchaseReturnDetailsResponseDTO> detailsList = new ArrayList<>();

		if (vo.getPurchaseReturnDetailsVO() != null) {

			for (PurchaseReturnDetailsVO detailVO : vo.getPurchaseReturnDetailsVO()) {

				PurchaseReturnDetailsResponseDTO detailDTO = new PurchaseReturnDetailsResponseDTO();

				if (detailVO.getItem() != null) {

					ItemMasterDetailsResponseInwardDTO itemDTO = new ItemMasterDetailsResponseInwardDTO();

					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailDTO.setItem(itemDTO);
				}

				if (detailVO.getUnit() != null) {

					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

					unitDTO.setId(detailVO.getUnit().getId());
					unitDTO.setUnitId(detailVO.getUnit().getUnitId());
					detailDTO.setUnit(unitDTO);
				}

				detailDTO.setId(detailVO.getId());
				detailDTO.setHsnSacCode(detailVO.getHsnSacCode());

				detailDTO.setTaxType(detailVO.getTaxType());

				detailDTO.setTaxPercentage(detailVO.getTaxPercentage());

				detailDTO.setTariffNo(detailVO.getTariffNo());

				detailDTO.setExciseToPost(detailVO.getExciseToPost());

				detailDTO.setChallanQty(detailVO.getChallanQty());

				detailDTO.setGrnReceivedQty(detailVO.getGrnReceivedQty());

				detailDTO.setAcceptedQty(detailVO.getAcceptedQty());

				detailDTO.setRejectedQty(detailVO.getRejectedQty());

				detailDTO.setShortageQty(detailVO.getShortageQty());

				detailDTO.setPoRate(detailVO.getPoRate());

				detailDTO.setRateInInr(detailVO.getRateInInr());

				detailDTO.setRateInSelectedCurrency(detailVO.getRateInSelectedCurrency());

				detailDTO.setApportionedCost(detailVO.getApportionedCost());

				detailDTO.setLandedCostRate(detailVO.getLandedCostRate());

				detailDTO.setAmount(detailVO.getAmount());

				detailDTO.setAmountInSelectedCurrency(detailVO.getAmountInSelectedCurrency());

				detailDTO.setAdditionalDuty(detailVO.getAdditionalDuty());

				detailDTO.setAmountInInr(detailVO.getAmountInInr());

				detailDTO.setSgstRate(detailVO.getSgstRate());

				detailDTO.setSgstAmount(detailVO.getSgstAmount());

				detailDTO.setCgstRate(detailVO.getCgstRate());

				detailDTO.setCgstAmount(detailVO.getCgstAmount());

				detailDTO.setIgstRate(detailVO.getIgstRate());

				detailDTO.setIgstAmount(detailVO.getIgstAmount());

				detailsList.add(detailDTO);
			}
		}

		responseDTO.setPurchaseReturnDetailsResponseDTO(detailsList);

		List<PurchaseReturnTaxDetailsResponseDTO> taxList = new ArrayList<>();

		if (vo.getPurchaseReturnTaxDetailsVO() != null) {

			for (PurchaseReturnTaxDetailsVO taxVO : vo.getPurchaseReturnTaxDetailsVO()) {

				PurchaseReturnTaxDetailsResponseDTO taxDTO = new PurchaseReturnTaxDetailsResponseDTO();
				taxDTO.setId(taxVO.getId());
				taxDTO.setParticulars(taxVO.getParticulars());
				taxDTO.setTax(taxVO.getTax());
				taxDTO.setAcceptedQtyAmount(taxVO.getAcceptedQtyAmount());
				taxDTO.setRevisedAmount(taxVO.getRevisedAmount());

				taxList.add(taxDTO);
			}
		}

		responseDTO.setPurchaseReturnTaxDetailsResponseDTO(taxList);

		return responseDTO;
	}

	@Override
	public String getPurchaseReturnDocId(Long orgId, String financialYear) {
		String screenCode = "PR";
		String result = purchaseReturnRepo.getPurchaseReturnDocId(orgId, financialYear, screenCode);
		return result;
	}
}