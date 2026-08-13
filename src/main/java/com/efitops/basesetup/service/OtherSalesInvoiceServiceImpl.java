package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.CustomerOtherSalesResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchInstructionResponseDocIdDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.OtherSalesInvoiceDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.OtherSalesInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.OtherSalesInvoiceTaxDetailsResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.OtherSalesInvoiceDTO;
import com.efitops.basesetup.dto.OtherSalesInvoiceDetailsDTO;
import com.efitops.basesetup.dto.OtherSalesInvoiceTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DespatchInstructionVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.OtherSalesInvoiceDetailsVO;
import com.efitops.basesetup.entity.OtherSalesInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.OtherSalesInvoiceVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DespatchInstructionRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.OtherSalesInvoiceDetailsRepo;
import com.efitops.basesetup.repository.OtherSalesInvoiceRepo;
import com.efitops.basesetup.repository.OtherSalesInvoiceTaxDetailsRepo;
import com.efitops.basesetup.repository.SalesOrderShortCloseRepo;

@Service
public class OtherSalesInvoiceServiceImpl implements OtherSalesInvoiceService {

	public static final Logger LOGGER = LoggerFactory.getLogger(OtherSalesInvoiceServiceImpl.class);

	@Autowired
	OtherSalesInvoiceRepo otherSalesInvoiceRepo;

	@Autowired
	OtherSalesInvoiceTaxDetailsRepo otherSalesInvoiceTaxDetailsRepo;

	@Autowired
	OtherSalesInvoiceDetailsRepo otherSalesInvoiceDetailsRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	LocationRepo locationRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ItemMasterRepo itemMasterRepo;

	@Autowired
	CurrencyRepo currencyRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	SalesOrderShortCloseRepo salesOrderShortCloseRepo;

	@Autowired
	DespatchInstructionRepo despatchInstructionRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Override
	public OtherSalesInvoiceResponseDTO getOtherSalesInvoiceById(Long id) throws ApplicationException {

		OtherSalesInvoiceVO otherSalesInvoiceVO = otherSalesInvoiceRepo.getOtherSalesInvoiceById(id);

		if (otherSalesInvoiceVO == null) {
			throw new ApplicationException("Other Not Found");
		}

		return buildOtherSalesInvoiceResponse(otherSalesInvoiceVO);
	}

	@Override
	public List<OtherSalesInvoiceResponseDTO> getOtherSalesInvoiceByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<OtherSalesInvoiceVO> quotationList = otherSalesInvoiceRepo.getOtherSalesInvoiceByOrgId(orgId, branch);

		if (quotationList == null || quotationList.isEmpty()) {
			throw new ApplicationException("OtherSales Not Found");
		}

		List<OtherSalesInvoiceResponseDTO> responseList = new ArrayList<>();

		for (OtherSalesInvoiceVO otherSalesInvoiceVO : quotationList) {
			responseList.add(buildOtherSalesInvoiceResponse(otherSalesInvoiceVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateOtherSalesInvoice(OtherSalesInvoiceDTO otherSalesInvoiceDTO)
			throws ApplicationException {
		String screenCode = "OSI";
		OtherSalesInvoiceVO otherSalesInvoiceVO = new OtherSalesInvoiceVO();
		String message;

		if (ObjectUtils.isNotEmpty(otherSalesInvoiceDTO.getId())) {

			otherSalesInvoiceVO = otherSalesInvoiceRepo.findById(otherSalesInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("OrderAcceptance Not Found"));

			otherSalesInvoiceVO.setUpdatedBy(otherSalesInvoiceDTO.getCreatedBy());

			message = "Other Updated Successfully";

		} else {

			String docId = otherSalesInvoiceRepo.getOtherSalesInvoiceDocId(otherSalesInvoiceDTO.getOrgId(), screenCode);

			otherSalesInvoiceVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(otherSalesInvoiceDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			otherSalesInvoiceVO.setCreatedBy(otherSalesInvoiceDTO.getCreatedBy());
			otherSalesInvoiceVO.setUpdatedBy(otherSalesInvoiceDTO.getCreatedBy());

			message = "Other Created Successfully";
		}

		createUpdateOtherSalesInvoiceVOByOtherSalesInvoiceDTO(otherSalesInvoiceDTO, otherSalesInvoiceVO);

		otherSalesInvoiceVO = otherSalesInvoiceRepo.save(otherSalesInvoiceVO);

		OtherSalesInvoiceResponseDTO responseDTO = buildOtherSalesInvoiceResponse(otherSalesInvoiceVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("otherSalesInvoiceVO", responseDTO);

		return response;
	}

	private void createUpdateOtherSalesInvoiceVOByOtherSalesInvoiceDTO(OtherSalesInvoiceDTO otherSalesInvoiceDTO,
			OtherSalesInvoiceVO otherSalesInvoiceVO) throws ApplicationException {

		otherSalesInvoiceVO.setBelongsTo(otherSalesInvoiceDTO.getBelongsTo());

		if (otherSalesInvoiceDTO.getCustomer() != null && otherSalesInvoiceDTO.getCustomer() != 0) {

			CustomerVO customer = customerRepo.findById(otherSalesInvoiceDTO.getCustomer())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			otherSalesInvoiceVO.setCustomer(customer);
		}

		otherSalesInvoiceVO.setId(otherSalesInvoiceDTO.getId());

		otherSalesInvoiceVO.setMonthYear(otherSalesInvoiceDTO.getMonthYear());

		otherSalesInvoiceVO.setBelongsTo(otherSalesInvoiceDTO.getBelongsTo());

		otherSalesInvoiceVO.setDocType(otherSalesInvoiceDTO.getDocType());

		otherSalesInvoiceVO.setStockPosting(otherSalesInvoiceDTO.getStockPosting());

		otherSalesInvoiceVO.setExcisable(otherSalesInvoiceDTO.getExcisable());

		otherSalesInvoiceVO.setVehicle(otherSalesInvoiceDTO.getVehicle());

		otherSalesInvoiceVO.setKanbanCardNo(otherSalesInvoiceDTO.getKanbanCardNo());

		otherSalesInvoiceVO.setInvoiceType(otherSalesInvoiceDTO.getInvoiceType());

		otherSalesInvoiceVO.setSchNo(otherSalesInvoiceDTO.getSchNo());

		otherSalesInvoiceVO.setSchDate(otherSalesInvoiceDTO.getSchDate());

		otherSalesInvoiceVO.setExchangeRate(otherSalesInvoiceDTO.getExchangeRate());

		otherSalesInvoiceVO.setTotalInsurance(otherSalesInvoiceDTO.getTotalInsurance());

		otherSalesInvoiceVO.setTotalFreight(otherSalesInvoiceDTO.getTotalFreight());

		otherSalesInvoiceVO.setModeOfTransport(otherSalesInvoiceDTO.getModeOfTransport());

		otherSalesInvoiceVO.setDeliveryTo(otherSalesInvoiceDTO.getDeliveryTo());

		otherSalesInvoiceVO.setPaymentTerms(otherSalesInvoiceDTO.getPaymentTerms());

		otherSalesInvoiceVO.setPurchaseOrder(otherSalesInvoiceDTO.getPurchaseOrder());

		otherSalesInvoiceVO.setPurchaseOrderDate(otherSalesInvoiceDTO.getPurchaseOrderDate());

		otherSalesInvoiceVO.setActive(otherSalesInvoiceDTO.isActive());

		otherSalesInvoiceVO.setCancelRemarks(otherSalesInvoiceDTO.getCancelRemarks());

		otherSalesInvoiceVO.setOrgId(otherSalesInvoiceDTO.getOrgId());

		otherSalesInvoiceVO.setFinancialYear(otherSalesInvoiceDTO.getFinancialYear());

		otherSalesInvoiceVO.setIsIgstApplicable(otherSalesInvoiceDTO.getIsIgstApplicable());
		

		if (otherSalesInvoiceDTO.getBranch() != null && otherSalesInvoiceDTO.getBranch() > 0) {

			BranchVO branch = branchRepo.findById(otherSalesInvoiceDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			otherSalesInvoiceVO.setBranch(branch);
		}

		if (otherSalesInvoiceDTO.getDiNo() != null && otherSalesInvoiceDTO.getDiNo() > 0) {

			DespatchInstructionVO branch = despatchInstructionRepo.findById(otherSalesInvoiceDTO.getDiNo())
					.orElseThrow(() -> new ApplicationException("Despatch Not Found"));

			otherSalesInvoiceVO.setDiNo(branch);
		}

		if (otherSalesInvoiceDTO.getLocation() != null && otherSalesInvoiceDTO.getLocation() > 0) {

			LocationVO branch = locationRepo.findById(otherSalesInvoiceDTO.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			otherSalesInvoiceVO.setLocation(branch);
		}

		if (otherSalesInvoiceDTO.getCurrency() != null && otherSalesInvoiceDTO.getCurrency() > 0) {

			CurrencyVO branch = currencyRepo.findById(otherSalesInvoiceDTO.getLocation())
					.orElseThrow(() -> new ApplicationException("Currency Not Found"));

			otherSalesInvoiceVO.setCurrency(branch);
		}

		if (ObjectUtils.isNotEmpty(otherSalesInvoiceVO.getId())) {

			List<OtherSalesInvoiceDetailsVO> otherSalesInvoiceDetailsVO = otherSalesInvoiceDetailsRepo
					.findByOtherSalesInvoiceVO(otherSalesInvoiceVO);

			otherSalesInvoiceDetailsRepo.deleteAll(otherSalesInvoiceDetailsVO);

			List<OtherSalesInvoiceTaxDetailsVO> otherSalesInvoiceTaxDetailsVO = otherSalesInvoiceTaxDetailsRepo
					.findByOtherSalesInvoiceVO(otherSalesInvoiceVO);

			otherSalesInvoiceTaxDetailsRepo.deleteAll(otherSalesInvoiceTaxDetailsVO);
		}

		BigDecimal finalAmount = BigDecimal.ZERO;
		BigDecimal assTotal = BigDecimal.ZERO;

		List<OtherSalesInvoiceDetailsVO> itemDetailsList = new ArrayList<>();

		if (otherSalesInvoiceDTO.getOtherSalesInvoiceDetailsDTO() != null) {

			for (OtherSalesInvoiceDetailsDTO dto : otherSalesInvoiceDTO.getOtherSalesInvoiceDetailsDTO()) {

				OtherSalesInvoiceDetailsVO detailsVO = new OtherSalesInvoiceDetailsVO();

				if (dto.getItem() != null && dto.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(item);
				}

				detailsVO.setTaxType(dto.getTaxType());

				detailsVO.setTariffNo(dto.getTariffNo());

				detailsVO.setStock(dto.getStock());

				detailsVO.setSalesOrderContractNo(dto.getSalesOrderContractNo());

				detailsVO.setQty(dto.getQty());

				detailsVO.setNoOfPackages(dto.getNoOfPackages());

				detailsVO.setPackageType(dto.getPackageType());

				detailsVO.setHsnCode(dto.getHsnCode());

				detailsVO.setOrderRate(dto.getOrderRate());

				detailsVO.setTaxPercentage(dto.getTaxPercentage());

				detailsVO.setRateInSelectedCurrency(dto.getRateInSelectedCurrency());

				BigDecimal quantity = dto.getQty() == null ? BigDecimal.ZERO : dto.getQty();

				BigDecimal amount = dto.getRateInSelectedCurrency() == null ? BigDecimal.ZERO
						: dto.getRateInSelectedCurrency();

				BigDecimal orderAmount = quantity.multiply(amount);

				detailsVO.setAmtInSelectedCurrency(orderAmount);

				detailsVO.setAmountInRs(orderAmount);

				assTotal = assTotal.add(detailsVO.getAmountInRs());

				BigDecimal igstRate = BigDecimal.ZERO;
				BigDecimal cgstRate = BigDecimal.ZERO;
				BigDecimal sgstRate = BigDecimal.ZERO;

				BigDecimal igstAmount = BigDecimal.ZERO;
				BigDecimal cgstAmount = BigDecimal.ZERO;
				BigDecimal sgstAmount = BigDecimal.ZERO;

				if (otherSalesInvoiceDTO.getIsIgstApplicable() != null
						&& otherSalesInvoiceDTO.getIsIgstApplicable().equalsIgnoreCase("Yes")) {

					igstRate = dto.getTaxPercentage() != null ? dto.getTaxPercentage() : BigDecimal.ZERO;

					igstAmount = orderAmount.multiply(igstRate).divide(BigDecimal.valueOf(100));

					cgstRate = BigDecimal.ZERO;
					sgstRate = BigDecimal.ZERO;

					cgstAmount = BigDecimal.ZERO;
					sgstAmount = BigDecimal.ZERO;

				} else {

					BigDecimal taxPercentage = dto.getTaxPercentage() != null ? dto.getTaxPercentage()
							: BigDecimal.ZERO;

					cgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

					sgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

					cgstAmount = orderAmount.multiply(cgstRate).divide(BigDecimal.valueOf(100));

					sgstAmount = orderAmount.multiply(sgstRate).divide(BigDecimal.valueOf(100));

					igstRate = BigDecimal.ZERO;
					igstAmount = BigDecimal.ZERO;
				}

				detailsVO.setIgstRate(igstRate);
				detailsVO.setCgstRate(cgstRate);
				detailsVO.setSgstRate(sgstRate);

				detailsVO.setIgstAmount(igstAmount);
				detailsVO.setCgstAmount(cgstAmount);
				detailsVO.setSgstAmount(sgstAmount);
				BigDecimal taxAmount = igstAmount.add(cgstAmount).add(sgstAmount);

				BigDecimal finalAmounts = orderAmount.add(taxAmount);

				finalAmount = finalAmount.add(finalAmounts);
				detailsVO.setOtherSalesInvoiceVO(otherSalesInvoiceVO);

				itemDetailsList.add(detailsVO);
			}
		}

		otherSalesInvoiceVO.setOtherSalesInvoiceDetailsVO(itemDetailsList);

		List<OtherSalesInvoiceTaxDetailsVO> taxList = new ArrayList<>();

		if (otherSalesInvoiceDTO.getOtherSalesInvoiceTaxDetailsDTO() != null) {

			for (OtherSalesInvoiceTaxDetailsDTO dto : otherSalesInvoiceDTO.getOtherSalesInvoiceTaxDetailsDTO()) {

				OtherSalesInvoiceTaxDetailsVO taxVO = new OtherSalesInvoiceTaxDetailsVO();

				taxVO.setOtherSalesInvoiceVO(otherSalesInvoiceVO);

				taxVO.setParticulars(dto.getParticulars());

				taxVO.setAcceptedQtyAmount(dto.getAcceptedQtyAmount());

				taxVO.setRevisedAmount(dto.getRevisedAmount());

				taxList.add(taxVO);
			}
		}

		otherSalesInvoiceVO.setNetAmount(finalAmount);

		otherSalesInvoiceVO.setTotalAssVal(assTotal);

		otherSalesInvoiceVO.setAmountInWords(amountInWordsConverterService.convert(otherSalesInvoiceVO.getNetAmount()));

		otherSalesInvoiceVO.setOtherSalesInvoiceTaxDetailsVO(taxList);

	}

	private OtherSalesInvoiceResponseDTO buildOtherSalesInvoiceResponse(OtherSalesInvoiceVO otherSalesInvoiceVO) {

		OtherSalesInvoiceResponseDTO responseDTO = new OtherSalesInvoiceResponseDTO();

		responseDTO.setId(otherSalesInvoiceVO.getId());

		responseDTO.setDocId(otherSalesInvoiceVO.getDocId());

		responseDTO.setDocDate(otherSalesInvoiceVO.getDocDate());

		responseDTO.setMonthYear(otherSalesInvoiceVO.getMonthYear());

		responseDTO.setBelongsTo(otherSalesInvoiceVO.getBelongsTo());

		responseDTO.setDocType(otherSalesInvoiceVO.getDocType());

		responseDTO.setStockPosting(otherSalesInvoiceVO.getStockPosting());

		responseDTO.setExcisable(otherSalesInvoiceVO.getExcisable());

		responseDTO.setVehicle(otherSalesInvoiceVO.getVehicle());

		responseDTO.setTimeOfIssue(otherSalesInvoiceVO.getTimeOfIssue());

		responseDTO.setTimeOfIssueDate(otherSalesInvoiceVO.getTimeOfIssueDate());

		responseDTO.setTimeOfRemoval(otherSalesInvoiceVO.getTimeOfRemoval());

		responseDTO.setTimeOfRemovalDate(otherSalesInvoiceVO.getTimeOfRemovalDate());

		responseDTO.setKanbanCardNo(otherSalesInvoiceVO.getKanbanCardNo());

		responseDTO.setInvoiceType(otherSalesInvoiceVO.getInvoiceType());

		responseDTO.setSchNo(otherSalesInvoiceVO.getSchNo());

		responseDTO.setSchDate(otherSalesInvoiceVO.getSchDate());

		responseDTO.setExchangeRate(otherSalesInvoiceVO.getExchangeRate());

		responseDTO.setTotalInsurance(otherSalesInvoiceVO.getTotalInsurance());

		responseDTO.setTotalFreight(otherSalesInvoiceVO.getTotalFreight());

		responseDTO.setTotalAssVal(otherSalesInvoiceVO.getTotalAssVal());

		responseDTO.setModeOfTransport(otherSalesInvoiceVO.getModeOfTransport());

		responseDTO.setNetAmount(otherSalesInvoiceVO.getNetAmount());

		responseDTO.setAmountInWords(otherSalesInvoiceVO.getAmountInWords());

		responseDTO.setDeliveryTo(otherSalesInvoiceVO.getDeliveryTo());

		responseDTO.setPaymentTerms(otherSalesInvoiceVO.getPaymentTerms());

		responseDTO.setPurchaseOrder(otherSalesInvoiceVO.getPurchaseOrder());

		responseDTO.setPurchaseOrderDate(otherSalesInvoiceVO.getPurchaseOrderDate());

		responseDTO.setIsIgstApplicable(otherSalesInvoiceVO.getIsIgstApplicable());

		responseDTO.setCreatedBy(otherSalesInvoiceVO.getCreatedBy());

		responseDTO.setActive(otherSalesInvoiceVO.getActive());

		responseDTO.setCancel(otherSalesInvoiceVO.getCancel());

		responseDTO.setUpdatedBy(otherSalesInvoiceVO.getUpdatedBy());

		responseDTO.setCancelRemarks(otherSalesInvoiceVO.getCancelRemarks());

		responseDTO.setOrgId(otherSalesInvoiceVO.getOrgId());

		responseDTO.setFinancialYear(otherSalesInvoiceVO.getFinancialYear());

		responseDTO.setScreenName(otherSalesInvoiceVO.getScreenName());

		responseDTO.setScreenCode(otherSalesInvoiceVO.getScreenCode());

		if (otherSalesInvoiceVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(otherSalesInvoiceVO.getBranch().getId());

			branchDTO.setBranchCode(otherSalesInvoiceVO.getBranch().getBranchCode());

			branchDTO.setBranchName(otherSalesInvoiceVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		if (otherSalesInvoiceVO.getCustomer() != null) {

			CustomerOtherSalesResponseDTO customerDTO = new CustomerOtherSalesResponseDTO();

			customerDTO.setId(otherSalesInvoiceVO.getCustomer().getId());
			customerDTO.setCustomerName(otherSalesInvoiceVO.getCustomer().getCustomerName());
			customerDTO.setCustomerCode(otherSalesInvoiceVO.getCustomer().getCustomerCode());
			customerDTO.setCustomerGstNo(otherSalesInvoiceVO.getCustomer().getGstNo());
			customerDTO.setGstApproval(otherSalesInvoiceVO.getCustomer().isGstApplicable() ? "Yes" : "No");
			responseDTO.setCustomer(customerDTO);
		}

		if (otherSalesInvoiceVO.getDiNo() != null) {

			DespatchInstructionResponseDocIdDTO customerDTO = new DespatchInstructionResponseDocIdDTO();

			customerDTO.setId(otherSalesInvoiceVO.getDiNo().getId());
			customerDTO.setDocId(otherSalesInvoiceVO.getDiNo().getDocId());

			responseDTO.setDiNo(customerDTO);
		}

		if (otherSalesInvoiceVO.getLocation() != null) {

			LocationMasterResponseDTO customerDTO = new LocationMasterResponseDTO();

			customerDTO.setId(otherSalesInvoiceVO.getLocation().getId());
			customerDTO.setLocationName(otherSalesInvoiceVO.getLocation().getLocationName());

			responseDTO.setLocation(customerDTO);
		}

		if (otherSalesInvoiceVO.getCurrency() != null) {

			CurrencyResponseDTO customerDTO = new CurrencyResponseDTO();

			customerDTO.setId(otherSalesInvoiceVO.getCurrency().getId());
			customerDTO.setCurrencyName(otherSalesInvoiceVO.getCurrency().getCurrency());

			responseDTO.setCurrency(customerDTO);

		}

		List<OtherSalesInvoiceDetailsResponseDTO> detailsList = new ArrayList<>();

		if (otherSalesInvoiceVO.getOtherSalesInvoiceDetailsVO() != null) {

			for (OtherSalesInvoiceDetailsVO detailsVO : otherSalesInvoiceVO.getOtherSalesInvoiceDetailsVO()) {

				OtherSalesInvoiceDetailsResponseDTO detailsDTO = new OtherSalesInvoiceDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());

				if (detailsVO.getItem() != null) {

					ItemMasterDetailsResponseDTO itemMasterDetailsResponseDTO = new ItemMasterDetailsResponseDTO();

					itemMasterDetailsResponseDTO.setId(detailsVO.getItem().getId());

					itemMasterDetailsResponseDTO.setItemCode(detailsVO.getItem().getItemCode());

					itemMasterDetailsResponseDTO.setItemDescription(detailsVO.getItem().getItemDescription());

					itemMasterDetailsResponseDTO.setCustomerPoNo(detailsVO.getItem().getCustomerPartNo());

					itemMasterDetailsResponseDTO.setHsnCode(detailsVO.getItem().getHsnCode().getHsn());

					if (detailsVO.getItem().getPricingUnit() != null) {

						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

						unitDTO.setId(detailsVO.getItem().getPricingUnit().getId());
						unitDTO.setUnitId(detailsVO.getItem().getPricingUnit().getUnitId());
						unitDTO.setUnitDescription(detailsVO.getItem().getPricingUnit().getUnitId());

						itemMasterDetailsResponseDTO.setUnit(unitDTO);
					}

					if (detailsVO.getItem().getPricingUnit() != null) {

						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

						unitDTO.setId(detailsVO.getItem().getPricingUnit().getId());
						unitDTO.setUnitId(detailsVO.getItem().getPricingUnit().getUnitId());
						unitDTO.setUnitDescription(detailsVO.getItem().getPricingUnit().getUnitId());

						itemMasterDetailsResponseDTO.setUnit(unitDTO);
					}

					detailsDTO.setItem(itemMasterDetailsResponseDTO);
				}

				detailsDTO.setTaxType(detailsVO.getTaxType());

				detailsDTO.setTaxPercentage(detailsVO.getTaxPercentage());

				detailsDTO.setTariffNo(detailsVO.getTariffNo());

				detailsDTO.setStock(detailsVO.getStock());

				detailsDTO.setSalesOrderContractNo(detailsVO.getSalesOrderContractNo());

				detailsDTO.setQty(detailsVO.getQty());

				detailsDTO.setNoOfPackages(detailsVO.getNoOfPackages());

				detailsDTO.setHsnCode(detailsVO.getHsnCode());

				detailsDTO.setPackageType(detailsVO.getPackageType());

				detailsDTO.setOrderRate(detailsVO.getOrderRate());

				detailsDTO.setRateInSelectedCurrency(detailsVO.getRateInSelectedCurrency());

				detailsDTO.setAmtInSelectedCurrency(detailsVO.getAmtInSelectedCurrency());

				detailsDTO.setAmountInRs(detailsVO.getAmountInRs());

				detailsDTO.setSgstRate(detailsVO.getSgstRate());

				detailsDTO.setSgstAmount(detailsVO.getSgstAmount());

				detailsDTO.setCgstRate(detailsVO.getCgstRate());

				detailsDTO.setCgstAmount(detailsVO.getCgstAmount());

				detailsDTO.setIgstRate(detailsVO.getIgstRate());

				detailsDTO.setIgstAmount(detailsVO.getIgstAmount());

				detailsList.add(detailsDTO);
			}
		}

		responseDTO.setOtherSalesInvoiceDetailsResponseDTO(detailsList);

		List<OtherSalesInvoiceTaxDetailsResponseDTO> taxList = new ArrayList<>();

		if (otherSalesInvoiceVO.getOtherSalesInvoiceTaxDetailsVO() != null) {

			for (OtherSalesInvoiceTaxDetailsVO taxVO : otherSalesInvoiceVO.getOtherSalesInvoiceTaxDetailsVO()) {

				OtherSalesInvoiceTaxDetailsResponseDTO taxDTO = new OtherSalesInvoiceTaxDetailsResponseDTO();

				taxDTO.setId(taxVO.getId());

				taxDTO.setParticulars(taxVO.getParticulars());

				taxDTO.setAcceptedQtyAmount(taxVO.getAcceptedQtyAmount());

				taxDTO.setRevisedAmount(taxVO.getRevisedAmount());

				taxList.add(taxDTO);
			}
		}

		responseDTO.setOtherSalesInvoiceTaxDetailsResponseDTO(taxList);

		return responseDTO;
	}

	@Override
	public String getOtherSalesInvoiceDocId(Long orgId, String screenCode) {
		String result = otherSalesInvoiceRepo.getOtherSalesInvoiceDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getExchangeRate(Long orgId, Long currency) {
		Set<Object[]> chType = otherSalesInvoiceRepo.getExchangeRate(orgId, currency);
		return getExchangeRate(chType);
	}

	private List<Map<String, Object>> getExchangeRate(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("exchangeRate", ch[0] != null ? new BigDecimal(ch[0].toString()) : BigDecimal.ZERO);
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getTaxPercentage(Long orgId, Long hsn) {
		Set<Object[]> chType = otherSalesInvoiceRepo.getTaxPercentage(orgId, hsn);
		return getTaxPercentage(chType);
	}

	private List<Map<String, Object>> getTaxPercentage(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("taxPercentage", ch[0] != null ? new BigDecimal(ch[0].toString()) : BigDecimal.ZERO);
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsBasedDesPatch(Long orgId, Long branch, Long despatch) {
		Set<Object[]> chType = otherSalesInvoiceRepo.getItemDetailsBasedDesPatch(orgId, branch, despatch);
		return getItemDetailsBasedDesPatch(chType);
	}

	private List<Map<String, Object>> getItemDetailsBasedDesPatch(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("unitId", ch[3] != null ? ch[3].toString() : "");
			map.put("hsn", ch[4] != null ? ch[4].toString() : "");
			map.put("customerPartNo", ch[5] != null ? ch[5].toString() : "");
			map.put("descQty", ch[6] != null ? new BigDecimal(ch[6].toString()) : BigDecimal.ZERO);

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSalesOrderNo(Long customer) {
		Set<Object[]> chType = otherSalesInvoiceRepo.getSalesOrderNo(customer);
		return getSalesOrderNo(chType);
	}

	private List<Map<String, Object>> getSalesOrderNo(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("id", ch[2] != null ? ch[2].toString() : "");
			list.add(map);
		}

		return list;
	}

}
