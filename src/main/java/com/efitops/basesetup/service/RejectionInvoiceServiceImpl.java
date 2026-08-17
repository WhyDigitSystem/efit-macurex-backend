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

import com.efitops.basesetup.ResponseDTO.BankResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.CustomerOtherSalesResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchInstructionResponseDocIdDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProformaInvoiceDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProformaInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProformaInvoiceTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.RejectionInvoiceDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.RejectionInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.RejectionInvoiceTaxDetailsResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.ProformaInvoiceDTO;
import com.efitops.basesetup.dto.ProformaInvoiceDetailsDTO;
import com.efitops.basesetup.dto.ProformaInvoiceTaxDetailsDTO;
import com.efitops.basesetup.dto.RejectionInvoiceDTO;
import com.efitops.basesetup.dto.RejectionInvoiceDetailsDTO;
import com.efitops.basesetup.dto.RejectionInvoiceTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BankDetailsVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DespatchInstructionVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.ProformaInvoiceDetailsVO;
import com.efitops.basesetup.entity.ProformaInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.ProformaInvoiceVO;
import com.efitops.basesetup.entity.RejectionInvoiceDetailsVO;
import com.efitops.basesetup.entity.RejectionInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.RejectionInvoiceVO;
import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BankDetailsRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DespatchInstructionRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.ProformaInvoiceDetailsRepo;
import com.efitops.basesetup.repository.ProformaInvoiceRepo;
import com.efitops.basesetup.repository.ProformaInvoiceTaxDetailsRepo;
import com.efitops.basesetup.repository.RejectionInvoiceDetailsRepo;
import com.efitops.basesetup.repository.RejectionInvoiceRepo;
import com.efitops.basesetup.repository.RejectionInvoiceTaxDetailsRepo;
import com.efitops.basesetup.repository.SalesOrderShortCloseRepo;
import com.efitops.basesetup.repository.TSBankRepo;

@Service
public class RejectionInvoiceServiceImpl implements RejectionInvoiceService {

	public static final Logger LOGGER = LoggerFactory.getLogger(RejectionInvoiceServiceImpl.class);

	@Autowired
	RejectionInvoiceRepo rejectionInvoiceRepo;

	@Autowired
	RejectionInvoiceTaxDetailsRepo rejectionInvoiceTaxDetailsRepo;

	@Autowired
	RejectionInvoiceDetailsRepo rejectionInvoiceDetailsRepo;

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

	@Autowired
	ProformaInvoiceRepo proformaInvoiceRepo;

	@Autowired
	ProformaInvoiceTaxDetailsRepo proformaInvoiceTaxDetailsRepo;

	@Autowired
	ProformaInvoiceDetailsRepo proformaInvoiceDetailsRepo;

	@Autowired
	TSBankRepo bankDetailsRepo;

	@Override
	public RejectionInvoiceResponseDTO getRejectionInvoiceById(Long id) throws ApplicationException {

		RejectionInvoiceVO rejectionInvoiceVO = rejectionInvoiceRepo.getRejectionInvoiceById(id);

		if (rejectionInvoiceVO == null) {
			throw new ApplicationException("Rejection Invoice Not Found");
		}

		return buildRejectionInvoiceResponse(rejectionInvoiceVO);
	}

	@Override
	public List<RejectionInvoiceResponseDTO> getRejectionInvoiceByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<RejectionInvoiceVO> quotationList = rejectionInvoiceRepo.getRejectionInvoiceByOrgId(orgId, branch);

		if (quotationList == null || quotationList.isEmpty()) {
			throw new ApplicationException("Rejection Invoice Not Found");
		}

		List<RejectionInvoiceResponseDTO> responseList = new ArrayList<>();

		for (RejectionInvoiceVO rejectionInvoiceVO : quotationList) {
			responseList.add(buildRejectionInvoiceResponse(rejectionInvoiceVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateRejectionInvoice(RejectionInvoiceDTO rejectionInvoiceDTO)
			throws ApplicationException {
		String screenCode = "RI";
		RejectionInvoiceVO rejectionInvoiceVO = new RejectionInvoiceVO();
		String message;

		if (ObjectUtils.isNotEmpty(rejectionInvoiceDTO.getId())) {

			rejectionInvoiceVO = rejectionInvoiceRepo.findById(rejectionInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("Rejection Invoice Not Found"));

			rejectionInvoiceVO.setUpdatedBy(rejectionInvoiceDTO.getCreatedBy());

			message = "Rejection Invoice Updated Successfully";

		} else {

			String docId = rejectionInvoiceRepo.getRejectionInvoiceDocId(rejectionInvoiceDTO.getOrgId(), screenCode);

			rejectionInvoiceVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(rejectionInvoiceDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			rejectionInvoiceVO.setCreatedBy(rejectionInvoiceDTO.getCreatedBy());
			rejectionInvoiceVO.setUpdatedBy(rejectionInvoiceDTO.getCreatedBy());

			message = "Rejection Invoice Created Successfully";
		}

		createUpdateRejectionInvoiceVOByRejectionInvoiceDTO(rejectionInvoiceDTO, rejectionInvoiceVO);

		rejectionInvoiceVO = rejectionInvoiceRepo.save(rejectionInvoiceVO);

		RejectionInvoiceResponseDTO responseDTO = buildRejectionInvoiceResponse(rejectionInvoiceVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("rejectionInvoiceVO", responseDTO);

		return response;
	}

	private void createUpdateRejectionInvoiceVOByRejectionInvoiceDTO(RejectionInvoiceDTO rejectionInvoiceDTO,
			RejectionInvoiceVO rejectionInvoiceVO) throws ApplicationException {

		rejectionInvoiceVO.setBelongsTo(rejectionInvoiceDTO.getBelongsTo());

		if (rejectionInvoiceDTO.getCustomer() != null && rejectionInvoiceDTO.getCustomer() != 0) {

			CustomerVO customer = customerRepo.findById(rejectionInvoiceDTO.getCustomer())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			rejectionInvoiceVO.setCustomer(customer);
		}

		rejectionInvoiceVO.setId(rejectionInvoiceDTO.getId());

		rejectionInvoiceVO.setMonthYear(rejectionInvoiceDTO.getMonthYear());

		rejectionInvoiceVO.setBelongsTo(rejectionInvoiceDTO.getBelongsTo());

		rejectionInvoiceVO.setDocType(rejectionInvoiceDTO.getDocType());

		rejectionInvoiceVO.setStockPosting(rejectionInvoiceDTO.getStockPosting());

		rejectionInvoiceVO.setExcisable(rejectionInvoiceDTO.getExcisable());

		rejectionInvoiceVO.setVehicle(rejectionInvoiceDTO.getVehicle());

		rejectionInvoiceVO.setKanbanCardNo(rejectionInvoiceDTO.getKanbanCardNo());

		rejectionInvoiceVO.setInvoiceType(rejectionInvoiceDTO.getInvoiceType());

		rejectionInvoiceVO.setSchNo(rejectionInvoiceDTO.getSchNo());

		rejectionInvoiceVO.setSchDate(rejectionInvoiceDTO.getSchDate());

		rejectionInvoiceVO.setExchangeRate(rejectionInvoiceDTO.getExchangeRate());

		rejectionInvoiceVO.setTotalInsurance(rejectionInvoiceDTO.getTotalInsurance());

		rejectionInvoiceVO.setTotalFreight(rejectionInvoiceDTO.getTotalFreight());

		rejectionInvoiceVO.setModeOfTransport(rejectionInvoiceDTO.getModeOfTransport());

		rejectionInvoiceVO.setDeliveryTo(rejectionInvoiceDTO.getDeliveryTo());

		rejectionInvoiceVO.setPaymentTerms(rejectionInvoiceDTO.getPaymentTerms());

		rejectionInvoiceVO.setPurchaseOrder(rejectionInvoiceDTO.getPurchaseOrder());

		rejectionInvoiceVO.setPurchaseOrderDate(rejectionInvoiceDTO.getPurchaseOrderDate());

		rejectionInvoiceVO.setActive(rejectionInvoiceDTO.isActive());

		rejectionInvoiceVO.setCancelRemarks(rejectionInvoiceDTO.getCancelRemarks());

		rejectionInvoiceVO.setOrgId(rejectionInvoiceDTO.getOrgId());

		rejectionInvoiceVO.setFinancialYear(rejectionInvoiceDTO.getFinancialYear());

		rejectionInvoiceVO.setIsIgstApplicable(rejectionInvoiceDTO.getIsIgstApplicable());

		if (rejectionInvoiceDTO.getBranch() != null && rejectionInvoiceDTO.getBranch() > 0) {

			BranchVO branch = branchRepo.findById(rejectionInvoiceDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			rejectionInvoiceVO.setBranch(branch);
		}

		if (rejectionInvoiceDTO.getDiNo() != null && rejectionInvoiceDTO.getDiNo() > 0) {

			DespatchInstructionVO branch = despatchInstructionRepo.findById(rejectionInvoiceDTO.getDiNo())
					.orElseThrow(() -> new ApplicationException("Despatch Not Found"));

			rejectionInvoiceVO.setDiNo(branch);
		}

		if (rejectionInvoiceDTO.getLocation() != null && rejectionInvoiceDTO.getLocation() > 0) {

			LocationVO branch = locationRepo.findById(rejectionInvoiceDTO.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			rejectionInvoiceVO.setLocation(branch);
		}

		if (rejectionInvoiceDTO.getCurrency() != null && rejectionInvoiceDTO.getCurrency() > 0) {

			CurrencyVO branch = currencyRepo.findById(rejectionInvoiceDTO.getCurrency())
					.orElseThrow(() -> new ApplicationException("Currency Not Found"));

			rejectionInvoiceVO.setCurrency(branch);
		}

		if (ObjectUtils.isNotEmpty(rejectionInvoiceVO.getId())) {

			List<RejectionInvoiceDetailsVO> rejectionInvoiceDetailsVO = rejectionInvoiceDetailsRepo
					.findByRejectionInvoiceVO(rejectionInvoiceVO);

			rejectionInvoiceDetailsRepo.deleteAll(rejectionInvoiceDetailsVO);

			List<RejectionInvoiceTaxDetailsVO> rejectionInvoiceTaxDetailsVO = rejectionInvoiceTaxDetailsRepo
					.findByRejectionInvoiceVO(rejectionInvoiceVO);

			rejectionInvoiceTaxDetailsRepo.deleteAll(rejectionInvoiceTaxDetailsVO);
		}

		BigDecimal finalAmount = BigDecimal.ZERO;
		BigDecimal assTotal = BigDecimal.ZERO;

		List<RejectionInvoiceDetailsVO> itemDetailsList = new ArrayList<>();

		if (rejectionInvoiceDTO.getRejectionInvoiceDetailsDTO() != null) {

			for (RejectionInvoiceDetailsDTO dto : rejectionInvoiceDTO.getRejectionInvoiceDetailsDTO()) {

				RejectionInvoiceDetailsVO detailsVO = new RejectionInvoiceDetailsVO();

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

				if (rejectionInvoiceDTO.getIsIgstApplicable() != null
						&& rejectionInvoiceDTO.getIsIgstApplicable().equalsIgnoreCase("Yes")) {

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
				detailsVO.setRejectionInvoiceVO(rejectionInvoiceVO);

				itemDetailsList.add(detailsVO);
			}
		}

		rejectionInvoiceVO.setRejectionInvoiceDetailsVO(itemDetailsList);

		List<RejectionInvoiceTaxDetailsVO> taxList = new ArrayList<>();

		if (rejectionInvoiceDTO.getRejectionInvoiceTaxDetailsDTO() != null) {

			for (RejectionInvoiceTaxDetailsDTO dto : rejectionInvoiceDTO.getRejectionInvoiceTaxDetailsDTO()) {

				RejectionInvoiceTaxDetailsVO taxVO = new RejectionInvoiceTaxDetailsVO();

				taxVO.setRejectionInvoiceVO(rejectionInvoiceVO);

				taxVO.setParticulars(dto.getParticulars());

				taxVO.setAcceptedQtyAmount(dto.getAcceptedQtyAmount());

				taxVO.setRevisedAmount(dto.getRevisedAmount());

				taxList.add(taxVO);
			}
		}

		rejectionInvoiceVO.setNetAmount(finalAmount);

		rejectionInvoiceVO.setTotalAssVal(assTotal);

		rejectionInvoiceVO.setAmountInWords(amountInWordsConverterService.convert(rejectionInvoiceVO.getNetAmount()));

		rejectionInvoiceVO.setRejectionInvoiceTaxDetailsVO(taxList);

	}

	private RejectionInvoiceResponseDTO buildRejectionInvoiceResponse(RejectionInvoiceVO rejectionInvoiceVO) {

		RejectionInvoiceResponseDTO responseDTO = new RejectionInvoiceResponseDTO();

		responseDTO.setId(rejectionInvoiceVO.getId());

		responseDTO.setDocId(rejectionInvoiceVO.getDocId());

		responseDTO.setDocDate(rejectionInvoiceVO.getDocDate());

		responseDTO.setMonthYear(rejectionInvoiceVO.getMonthYear());

		responseDTO.setBelongsTo(rejectionInvoiceVO.getBelongsTo());

		responseDTO.setDocType(rejectionInvoiceVO.getDocType());

		responseDTO.setStockPosting(rejectionInvoiceVO.getStockPosting());

		responseDTO.setExcisable(rejectionInvoiceVO.getExcisable());

		responseDTO.setVehicle(rejectionInvoiceVO.getVehicle());

		responseDTO.setTimeOfIssue(rejectionInvoiceVO.getTimeOfIssue());

		responseDTO.setTimeOfIssueDate(rejectionInvoiceVO.getTimeOfIssueDate());

		responseDTO.setTimeOfRemoval(rejectionInvoiceVO.getTimeOfRemoval());

		responseDTO.setTimeOfRemovalDate(rejectionInvoiceVO.getTimeOfRemovalDate());

		responseDTO.setKanbanCardNo(rejectionInvoiceVO.getKanbanCardNo());

		responseDTO.setInvoiceType(rejectionInvoiceVO.getInvoiceType());

		responseDTO.setSchNo(rejectionInvoiceVO.getSchNo());

		responseDTO.setSchDate(rejectionInvoiceVO.getSchDate());

		responseDTO.setExchangeRate(rejectionInvoiceVO.getExchangeRate());

		responseDTO.setTotalInsurance(rejectionInvoiceVO.getTotalInsurance());

		responseDTO.setTotalFreight(rejectionInvoiceVO.getTotalFreight());

		responseDTO.setTotalAssVal(rejectionInvoiceVO.getTotalAssVal());

		responseDTO.setModeOfTransport(rejectionInvoiceVO.getModeOfTransport());

		responseDTO.setNetAmount(rejectionInvoiceVO.getNetAmount());

		responseDTO.setAmountInWords(rejectionInvoiceVO.getAmountInWords());

		responseDTO.setDeliveryTo(rejectionInvoiceVO.getDeliveryTo());

		responseDTO.setPaymentTerms(rejectionInvoiceVO.getPaymentTerms());

		responseDTO.setPurchaseOrder(rejectionInvoiceVO.getPurchaseOrder());

		responseDTO.setPurchaseOrderDate(rejectionInvoiceVO.getPurchaseOrderDate());

		responseDTO.setIsIgstApplicable(rejectionInvoiceVO.getIsIgstApplicable());

		responseDTO.setCreatedBy(rejectionInvoiceVO.getCreatedBy());

		responseDTO.setActive(rejectionInvoiceVO.getActive());

		responseDTO.setCancel(rejectionInvoiceVO.getCancel());

		responseDTO.setUpdatedBy(rejectionInvoiceVO.getUpdatedBy());

		responseDTO.setCancelRemarks(rejectionInvoiceVO.getCancelRemarks());

		responseDTO.setOrgId(rejectionInvoiceVO.getOrgId());

		responseDTO.setFinancialYear(rejectionInvoiceVO.getFinancialYear());

		responseDTO.setScreenName(rejectionInvoiceVO.getScreenName());

		responseDTO.setScreenCode(rejectionInvoiceVO.getScreenCode());

//        responseDTO.setRejectionType(rejectionInvoiceVO.getRejectionType());
//        
//        responseDTO.setReasonForRejection(rejectionInvoiceVO.getReasonForRejection());
//        
//        responseDTO.setOriginalInvoiceNo(rejectionInvoiceVO.getOriginalInvoiceNo());
//        
//        responseDTO.setOriginalInvoiceDate(rejectionInvoiceVO.getOriginalInvoiceDate());

		if (rejectionInvoiceVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(rejectionInvoiceVO.getBranch().getId());

			branchDTO.setBranchCode(rejectionInvoiceVO.getBranch().getBranchCode());

			branchDTO.setBranchName(rejectionInvoiceVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		if (rejectionInvoiceVO.getCustomer() != null) {

			CustomerOtherSalesResponseDTO customerDTO = new CustomerOtherSalesResponseDTO();

			customerDTO.setId(rejectionInvoiceVO.getCustomer().getId());
			customerDTO.setCustomerName(rejectionInvoiceVO.getCustomer().getCustomerName());
			customerDTO.setCustomerCode(rejectionInvoiceVO.getCustomer().getCustomerCode());
			customerDTO.setCustomerGstNo(rejectionInvoiceVO.getCustomer().getGstNo());
			customerDTO.setGstApproval(rejectionInvoiceVO.getCustomer().isGstApplicable() ? "Yes" : "No");
			responseDTO.setCustomer(customerDTO);
		}

		if (rejectionInvoiceVO.getDiNo() != null) {

			DespatchInstructionResponseDocIdDTO customerDTO = new DespatchInstructionResponseDocIdDTO();

			customerDTO.setId(rejectionInvoiceVO.getDiNo().getId());
			customerDTO.setDocId(rejectionInvoiceVO.getDiNo().getDocId());

			responseDTO.setDiNo(customerDTO);
		}

		if (rejectionInvoiceVO.getLocation() != null) {

			LocationMasterResponseDTO customerDTO = new LocationMasterResponseDTO();

			customerDTO.setId(rejectionInvoiceVO.getLocation().getId());
			customerDTO.setLocationName(rejectionInvoiceVO.getLocation().getLocationName());

			responseDTO.setLocation(customerDTO);
		}

		if (rejectionInvoiceVO.getCurrency() != null) {

			CurrencyResponseDTO customerDTO = new CurrencyResponseDTO();

			customerDTO.setId(rejectionInvoiceVO.getCurrency().getId());
			customerDTO.setCurrencyName(rejectionInvoiceVO.getCurrency().getCurrency());

			responseDTO.setCurrency(customerDTO);

		}

		List<RejectionInvoiceDetailsResponseDTO> detailsList = new ArrayList<>();

		if (rejectionInvoiceVO.getRejectionInvoiceDetailsVO() != null) {

			for (RejectionInvoiceDetailsVO detailsVO : rejectionInvoiceVO.getRejectionInvoiceDetailsVO()) {

				RejectionInvoiceDetailsResponseDTO detailsDTO = new RejectionInvoiceDetailsResponseDTO();

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

		responseDTO.setRejectionInvoiceDetailsResponseDTO(detailsList);

		List<RejectionInvoiceTaxDetailsResponseDTO> taxList = new ArrayList<>();

		if (rejectionInvoiceVO.getRejectionInvoiceTaxDetailsVO() != null) {

			for (RejectionInvoiceTaxDetailsVO taxVO : rejectionInvoiceVO.getRejectionInvoiceTaxDetailsVO()) {

				RejectionInvoiceTaxDetailsResponseDTO taxDTO = new RejectionInvoiceTaxDetailsResponseDTO();

				taxDTO.setId(taxVO.getId());

				taxDTO.setParticulars(taxVO.getParticulars());

				taxDTO.setAcceptedQtyAmount(taxVO.getAcceptedQtyAmount());

				taxDTO.setRevisedAmount(taxVO.getRevisedAmount());

				taxList.add(taxDTO);
			}
		}

		responseDTO.setRejectionInvoiceTaxDetailsResponseDTO(taxList);

		return responseDTO;
	}

	@Override
	public String getRejectionInvoiceDocId(Long orgId, String screenCode) {
		String result = rejectionInvoiceRepo.getRejectionInvoiceDocId(orgId, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getTaxValue(Long orgId, Long hsn) {
		Set<Object[]> chType = proformaInvoiceRepo.getTaxValue(orgId, hsn);
		return getTaxPercentage(chType);
	}

	private List<Map<String, Object>> getTaxPercentage(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("taxPercentage", ch[0] != null ? new BigDecimal(ch[0].toString()) : BigDecimal.ZERO);
			map.put("igst", ch[1] != null ? new BigDecimal(ch[1].toString()) : BigDecimal.ZERO);
			map.put("sgst", ch[2] != null ? new BigDecimal(ch[2].toString()) : BigDecimal.ZERO);
			map.put("cgst", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);
			List1.add(map);
		}
		return List1;
	}

	// ProformaInvoice

	@Override
	public ProformaInvoiceResponseDTO getProformaInvoiceById(Long id) throws ApplicationException {

		ProformaInvoiceVO proformaInvoiceVO = proformaInvoiceRepo.getProformaInvoiceById(id);

		if (proformaInvoiceVO == null) {
			throw new ApplicationException("Proforma Invoice Not Found");
		}

		return buildProformaInvoiceResponse(proformaInvoiceVO);
	}

	@Override
	public List<ProformaInvoiceResponseDTO> getProformaInvoiceByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<ProformaInvoiceVO> quotationList = proformaInvoiceRepo.getProformaInvoiceByOrgId(orgId, branch);

		if (quotationList == null || quotationList.isEmpty()) {
			throw new ApplicationException("Proforma Invoice Not Found");
		}

		List<ProformaInvoiceResponseDTO> responseList = new ArrayList<>();

		for (ProformaInvoiceVO proformaInvoiceVO : quotationList) {
			responseList.add(buildProformaInvoiceResponse(proformaInvoiceVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateProformaInvoice(ProformaInvoiceDTO proformaInvoiceDTO)
			throws ApplicationException {
		String screenCode = "PI";
		ProformaInvoiceVO proformaInvoiceVO = new ProformaInvoiceVO();
		String message;

		if (ObjectUtils.isNotEmpty(proformaInvoiceDTO.getId())) {

			proformaInvoiceVO = proformaInvoiceRepo.findById(proformaInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("Proforma Invoice Not Found"));

			proformaInvoiceVO.setUpdatedBy(proformaInvoiceDTO.getCreatedBy());

			message = "Proforma Invoice Updated Successfully";

		} else {

			String docId = proformaInvoiceRepo.getProformaInvoiceDocId(proformaInvoiceDTO.getOrgId(),
					proformaInvoiceDTO.getFinancialYear(), screenCode);

			proformaInvoiceVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(proformaInvoiceDTO.getOrgId(),
							proformaInvoiceDTO.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			proformaInvoiceVO.setCreatedBy(proformaInvoiceDTO.getCreatedBy());
			proformaInvoiceVO.setUpdatedBy(proformaInvoiceDTO.getCreatedBy());

			message = "Proforma Invoice Created Successfully";
		}

		createUpdateProformaInvoiceVOByProformaInvoiceDTO(proformaInvoiceDTO, proformaInvoiceVO);

		proformaInvoiceVO = proformaInvoiceRepo.save(proformaInvoiceVO);

		ProformaInvoiceResponseDTO responseDTO = buildProformaInvoiceResponse(proformaInvoiceVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("proformaInvoiceVO", responseDTO);

		return response;
	}

	private void createUpdateProformaInvoiceVOByProformaInvoiceDTO(ProformaInvoiceDTO proformaInvoiceDTO,
			ProformaInvoiceVO proformaInvoiceVO) throws ApplicationException {

//		proformaInvoiceVO.setBelongsTo(proformaInvoiceDTO.getBelongsTo());

		if (proformaInvoiceDTO.getCustomer() != null && proformaInvoiceDTO.getCustomer() != 0) {

			CustomerVO customer = customerRepo.findById(proformaInvoiceDTO.getCustomer())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			proformaInvoiceVO.setCustomer(customer);
		}

//		proformaInvoiceVO.setId(proformaInvoiceDTO.getId());

		proformaInvoiceVO.setBelongsTo(proformaInvoiceDTO.getBelongsTo());

		proformaInvoiceVO.setInsurance(proformaInvoiceDTO.getInsurance());

		proformaInvoiceVO.setFreight(proformaInvoiceDTO.getFreight());

		proformaInvoiceVO.setModeOfTransport(proformaInvoiceDTO.getModeOfTransport());

		proformaInvoiceVO.setDeliveryTo(proformaInvoiceDTO.getDeliveryTo());

		proformaInvoiceVO.setPaymentTerms(proformaInvoiceDTO.getPaymentTerms());

		proformaInvoiceVO.setPurchaseOrderNo(proformaInvoiceDTO.getPurchaseOrderNo());

		proformaInvoiceVO.setPurchaseOrderDate(proformaInvoiceDTO.getPurchaseOrderDate());

		proformaInvoiceVO.setRefNo(proformaInvoiceDTO.getRefNo());

		proformaInvoiceVO.setRefDate(proformaInvoiceDTO.getRefDate());

		proformaInvoiceVO.setActive(proformaInvoiceDTO.isActive());

		proformaInvoiceVO.setCancelRemarks(proformaInvoiceDTO.getCancelRemarks());

		proformaInvoiceVO.setOrgId(proformaInvoiceDTO.getOrgId());

		proformaInvoiceVO.setFinancialYear(proformaInvoiceDTO.getFinancialYear());

		proformaInvoiceVO.setKindAttention(proformaInvoiceDTO.getKindAttention());
		proformaInvoiceVO.setDesignation(proformaInvoiceDTO.getDesignation());
		proformaInvoiceVO.setNoOfPkg(proformaInvoiceDTO.getNoOfPkg());
		proformaInvoiceVO.setPkgType(proformaInvoiceDTO.getPkgType());
		proformaInvoiceVO.setRateOfDuty(proformaInvoiceDTO.getRateOfDuty());
		proformaInvoiceVO.setTariffNo(proformaInvoiceDTO.getTariffNo());

		proformaInvoiceVO.setNarration(proformaInvoiceDTO.getNarration());

		proformaInvoiceVO.setPaymentPercentage(proformaInvoiceDTO.getPaymentPercentage());

		if (proformaInvoiceDTO.getBranch() != null && proformaInvoiceDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(proformaInvoiceDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			proformaInvoiceVO.setBranch(branch);
		}

		if (proformaInvoiceDTO.getLocation() != null && proformaInvoiceDTO.getLocation() != 0) {

			LocationVO branch = locationRepo.findById(proformaInvoiceDTO.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			proformaInvoiceVO.setLocation(branch);
		}

		if (proformaInvoiceDTO.getBankName() != null && proformaInvoiceDTO.getBankName() != 0) {

			TSBankVO bankDetailsVO = bankDetailsRepo.findById(proformaInvoiceDTO.getBankName())
					.orElseThrow(() -> new ApplicationException("BankName Not Found"));

			proformaInvoiceVO.setBankName(bankDetailsVO);
		}

		if (ObjectUtils.isNotEmpty(proformaInvoiceVO.getId())) {

			List<ProformaInvoiceDetailsVO> proformaInvoiceDetailsVO = proformaInvoiceDetailsRepo
					.findByProformaInvoiceVO(proformaInvoiceVO);

			proformaInvoiceDetailsRepo.deleteAll(proformaInvoiceDetailsVO);

			List<ProformaInvoiceTaxDetailsVO> proformaInvoiceTaxDetailsVO = proformaInvoiceTaxDetailsRepo
					.findByProformaInvoiceVO(proformaInvoiceVO);

			proformaInvoiceTaxDetailsRepo.deleteAll(proformaInvoiceTaxDetailsVO);
		}

		BigDecimal finalAmount = BigDecimal.ZERO;
		BigDecimal assTotal = BigDecimal.ZERO;

		List<ProformaInvoiceDetailsVO> itemDetailsList = new ArrayList<>();

		if (proformaInvoiceDTO.getProformaInvoiceDetailsDTO() != null) {

			for (ProformaInvoiceDetailsDTO dto : proformaInvoiceDTO.getProformaInvoiceDetailsDTO()) {

				ProformaInvoiceDetailsVO detailsVO = new ProformaInvoiceDetailsVO();

				if (dto.getItem() != null && dto.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(item);
				}

				detailsVO.setDespatchQty(dto.getDespatchQty());

				detailsVO.setTaxType(dto.getTaxType());

				detailsVO.setHsnCode(dto.getHsnCode());

				detailsVO.setOrderRate(dto.getOrderRate());

				detailsVO.setTaxPercentage(dto.getTaxPercentage());

				BigDecimal quantity = dto.getDespatchQty() == null ? BigDecimal.ZERO : dto.getDespatchQty();

				BigDecimal amount = dto.getOrderRate() == null ? BigDecimal.ZERO : dto.getOrderRate();

				BigDecimal orderAmount = quantity.multiply(amount);

				detailsVO.setAmount(orderAmount);

				assTotal = assTotal.add(detailsVO.getAmount());

				BigDecimal igstRate = BigDecimal.ZERO;
				BigDecimal cgstRate = BigDecimal.ZERO;
				BigDecimal sgstRate = BigDecimal.ZERO;

				BigDecimal igstAmount = BigDecimal.ZERO;
				BigDecimal cgstAmount = BigDecimal.ZERO;
				BigDecimal sgstAmount = BigDecimal.ZERO;

				if (proformaInvoiceDTO.getIsIgstApplicable() != null
						&& proformaInvoiceDTO.getIsIgstApplicable().equalsIgnoreCase("Yes")) {

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
				detailsVO.setProformaInvoiceVO(proformaInvoiceVO);

				itemDetailsList.add(detailsVO);
			}
		}

		proformaInvoiceVO.setProformaInvoiceDetailsVO(itemDetailsList);

		List<ProformaInvoiceTaxDetailsVO> taxList = new ArrayList<>();

		if (proformaInvoiceDTO.getProformaInvoiceTaxDetailsDTO() != null) {

			for (ProformaInvoiceTaxDetailsDTO dto : proformaInvoiceDTO.getProformaInvoiceTaxDetailsDTO()) {

				ProformaInvoiceTaxDetailsVO taxVO = new ProformaInvoiceTaxDetailsVO();

				taxVO.setProformaInvoiceVO(proformaInvoiceVO);

				taxVO.setParticulars(dto.getParticulars());

				taxVO.setAmount(dto.getAmount());

				taxList.add(taxVO);
			}
		}

		proformaInvoiceVO.setProformaInvoiceTaxDetailsVO(taxList);

		proformaInvoiceVO.setBasicValue(assTotal);

		proformaInvoiceVO.setGrossAmount(finalAmount);

		proformaInvoiceVO.setAmountInWords(amountInWordsConverterService.convert(proformaInvoiceVO.getGrossAmount()));

	}

	private ProformaInvoiceResponseDTO buildProformaInvoiceResponse(ProformaInvoiceVO proformaInvoiceVO) {

		ProformaInvoiceResponseDTO responseDTO = new ProformaInvoiceResponseDTO();

		responseDTO.setId(proformaInvoiceVO.getId());
		responseDTO.setDocId(proformaInvoiceVO.getDocId());
		responseDTO.setDocDate(proformaInvoiceVO.getDocDate());
		responseDTO.setBelongsTo(proformaInvoiceVO.getBelongsTo());
		responseDTO.setPurchaseOrderNo(proformaInvoiceVO.getPurchaseOrderNo());
		responseDTO.setPurchaseOrderDate(proformaInvoiceVO.getPurchaseOrderDate());
		responseDTO.setRefNo(proformaInvoiceVO.getRefNo());
		responseDTO.setRefDate(proformaInvoiceVO.getRefDate());
		responseDTO.setKindAttention(proformaInvoiceVO.getKindAttention());
		responseDTO.setDesignation(proformaInvoiceVO.getDesignation());
		responseDTO.setTimeOfIssue(proformaInvoiceVO.getTimeOfIssue());
		responseDTO.setTimeOfIssueDate(proformaInvoiceVO.getTimeOfIssueDate());
		responseDTO.setInsurance(proformaInvoiceVO.getInsurance());
		responseDTO.setFreight(proformaInvoiceVO.getFreight());
		responseDTO.setNoOfPkg(proformaInvoiceVO.getNoOfPkg());
		responseDTO.setPkgType(proformaInvoiceVO.getPkgType());
		responseDTO.setRateOfDuty(proformaInvoiceVO.getRateOfDuty());
		responseDTO.setTariffNo(proformaInvoiceVO.getTariffNo());
		responseDTO.setBasicValue(proformaInvoiceVO.getBasicValue());
		responseDTO.setGrossAmount(proformaInvoiceVO.getGrossAmount());
		responseDTO.setModeOfTransport(proformaInvoiceVO.getModeOfTransport());
		responseDTO.setAmountInWords(proformaInvoiceVO.getAmountInWords());
		responseDTO.setDeliveryTo(proformaInvoiceVO.getDeliveryTo());
		responseDTO.setPaymentTerms(proformaInvoiceVO.getPaymentTerms());
		responseDTO.setPaymentPercentage(proformaInvoiceVO.getPaymentPercentage());
		responseDTO.setNarration(proformaInvoiceVO.getNarration());
		responseDTO.setCreatedBy(proformaInvoiceVO.getCreatedBy());
		responseDTO.setCreatedBy(proformaInvoiceVO.getCreatedBy());
		responseDTO.setActive(proformaInvoiceVO.getActive());
		responseDTO.setCancel(proformaInvoiceVO.getCancel());
		responseDTO.setUpdatedBy(proformaInvoiceVO.getUpdatedBy());
		responseDTO.setCancelRemarks(proformaInvoiceVO.getCancelRemarks());
		responseDTO.setOrgId(proformaInvoiceVO.getOrgId());
		responseDTO.setKindAttention(proformaInvoiceVO.getKindAttention());
		responseDTO.setDesignation(proformaInvoiceVO.getDesignation());
		responseDTO.setNoOfPkg(proformaInvoiceVO.getNoOfPkg());
		responseDTO.setPkgType(proformaInvoiceVO.getPkgType());
		responseDTO.setRateOfDuty(proformaInvoiceVO.getRateOfDuty());
		responseDTO.setTariffNo(proformaInvoiceVO.getTariffNo());

		responseDTO.setNarration(proformaInvoiceVO.getNarration());
		responseDTO.setScreenName(proformaInvoiceVO.getScreenName());
		responseDTO.setScreenCode(proformaInvoiceVO.getScreenCode());
		responseDTO.setFinancialYear(proformaInvoiceVO.getFinancialYear());
		responseDTO.setPaymentPercentage(proformaInvoiceVO.getPaymentPercentage());
		responseDTO.setUpdatedBy(proformaInvoiceVO.getUpdatedBy());

		if (proformaInvoiceVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(proformaInvoiceVO.getBranch().getId());

			branchDTO.setBranchCode(proformaInvoiceVO.getBranch().getBranchCode());

			branchDTO.setBranchName(proformaInvoiceVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		if (proformaInvoiceVO.getCustomer() != null) {

			CustomerOtherSalesResponseDTO customerDTO = new CustomerOtherSalesResponseDTO();

			customerDTO.setId(proformaInvoiceVO.getCustomer().getId());
			customerDTO.setCustomerName(proformaInvoiceVO.getCustomer().getCustomerName());
			customerDTO.setCustomerCode(proformaInvoiceVO.getCustomer().getCustomerCode());
			customerDTO.setCustomerGstNo(proformaInvoiceVO.getCustomer().getGstNo());
			customerDTO.setGstApproval(proformaInvoiceVO.getCustomer().isGstApplicable() ? "Yes" : "No");
			customerDTO.setState(proformaInvoiceVO.getCustomer().getGstState().getStateName());
			responseDTO.setCustomer(customerDTO);
		}

		if (proformaInvoiceVO.getLocation() != null) {

			LocationMasterResponseDTO customerDTO = new LocationMasterResponseDTO();

			customerDTO.setId(proformaInvoiceVO.getLocation().getId());
			customerDTO.setLocationName(proformaInvoiceVO.getLocation().getLocationName());

			responseDTO.setLocation(customerDTO);
		}

		if (proformaInvoiceVO.getBankName() != null) {

			BankResponseDetailsDTO customerDTO = new BankResponseDetailsDTO();

			customerDTO.setId(proformaInvoiceVO.getBankName().getId());
			customerDTO.setBankName(proformaInvoiceVO.getBankName().getBank());
			responseDTO.setBankName(customerDTO);

		}

		List<ProformaInvoiceDetailsResponseDTO> detailsList = new ArrayList<>();

		if (proformaInvoiceVO.getProformaInvoiceDetailsVO() != null) {

			for (ProformaInvoiceDetailsVO detailsVO : proformaInvoiceVO.getProformaInvoiceDetailsVO()) {

				ProformaInvoiceDetailsResponseDTO detailsDTO = new ProformaInvoiceDetailsResponseDTO();

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

					detailsDTO.setItem(itemMasterDetailsResponseDTO);
				}

				detailsDTO.setTaxPercentage(detailsVO.getTaxPercentage());

				detailsDTO.setTaxType(detailsVO.getTaxType());

				detailsDTO.setHsnCode(detailsVO.getHsnCode());

				detailsDTO.setDespatchQty(detailsVO.getDespatchQty());

				detailsDTO.setAmount(detailsVO.getAmount());

				detailsDTO.setOrderRate(detailsVO.getOrderRate());

				detailsDTO.setSgstRate(detailsVO.getSgstRate());

				detailsDTO.setSgstAmount(detailsVO.getSgstAmount());

				detailsDTO.setCgstRate(detailsVO.getCgstRate());

				detailsDTO.setCgstAmount(detailsVO.getCgstAmount());

				detailsDTO.setIgstRate(detailsVO.getIgstRate());

				detailsDTO.setIgstAmount(detailsVO.getIgstAmount());

				detailsList.add(detailsDTO);
			}
		}

		responseDTO.setProformaInvoiceDetailsResponseDTO(detailsList);

		List<ProformaInvoiceTaxDetailsResponseDTO> taxList = new ArrayList<>();

		if (proformaInvoiceVO.getProformaInvoiceTaxDetailsVO() != null
				&& !proformaInvoiceVO.getProformaInvoiceTaxDetailsVO().isEmpty()) {

			for (ProformaInvoiceTaxDetailsVO taxVO : proformaInvoiceVO.getProformaInvoiceTaxDetailsVO()) {

				ProformaInvoiceTaxDetailsResponseDTO taxDTO = new ProformaInvoiceTaxDetailsResponseDTO();

				taxDTO.setId(taxVO.getId());
				taxDTO.setParticulars(taxVO.getParticulars());
				taxDTO.setAmount(taxVO.getAmount());

				taxList.add(taxDTO);
			}
		}

		responseDTO.setProformaInvoiceTaxDetailsResponseDTO(taxList);

		return responseDTO;
	}

	@Override
	public String getProformaInvoiceDocId(Long orgId, String financialYear, String screenCode) {
		String screenCode1 = "PI";
		String result = proformaInvoiceRepo.getProformaInvoiceDocId(orgId, financialYear, screenCode1);
		return result;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsResponse(Long orgId, Long branch) {
		Set<Object[]> chType = proformaInvoiceRepo.getItemDetailsResponse(orgId, branch);
		return getItemDetailsResponse(chType);
	}

	private List<Map<String, Object>> getItemDetailsResponse(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("unitId", ch[3] != null ? ch[3].toString() : "");
			map.put("hsn", ch[4] != null ? ch[4].toString() : "");
			map.put("customerPartNo", ch[5] != null ? ch[5].toString() : "");

			list.add(map);
		}

		return list;
	}

//	@Override
//	public List<Map<String, Object>> getGstState(Long orgId, Long customer) {
//		Set<Object[]> chType = proformaInvoiceRepo.getGstState(orgId, customer);
//		return getGstState(chType);
//	}
//
//	private List<Map<String, Object>> getGstState(Set<Object[]> chType) {
//
//		List<Map<String, Object>> list = new ArrayList<>();
//
//		for (Object[] ch : chType) {
//
//			Map<String, Object> map = new HashMap<>();
//			map.put("stateName", ch[0] != null ? ch[0].toString() : "");
//			map.put("stateCode", ch[1] != null ? ch[1].toString() : "");
//
//			list.add(map);
//		}
//
//		return list;
//	}

}
