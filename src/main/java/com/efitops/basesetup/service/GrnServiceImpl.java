package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.efitops.basesetup.ResponseDTO.GrnDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.GrnFileUploadDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.GrnResponseDTO;
import com.efitops.basesetup.ResponseDTO.GrnTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseStockDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferGrnDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferGrnFileUploadDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferGrnResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.GrnDetailsDTO;
import com.efitops.basesetup.dto.GrnTaxDetailsDTO;
import com.efitops.basesetup.dto.StockTransferGrnDTO;
import com.efitops.basesetup.dto.StockTransferGrnDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.GrnDetailsVO;
import com.efitops.basesetup.entity.GrnFileUploadDetailsVO;
import com.efitops.basesetup.entity.GrnTaxDetailsVO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.StockTransferGrnDetailsVO;
import com.efitops.basesetup.entity.StockTransferGrnFileUploadDetailsVO;
import com.efitops.basesetup.entity.StockTransferGrnVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.GrnDetailsRepo;
import com.efitops.basesetup.repository.GrnFileUploadDetailsRepo;
import com.efitops.basesetup.repository.GrnRepo;
import com.efitops.basesetup.repository.GrnTaxDetailsRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.StockTransferGrnDetailsRepo;
import com.efitops.basesetup.repository.StockTransferGrnFileUploadDetailsRepo;
import com.efitops.basesetup.repository.StockTransferGrnRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class GrnServiceImpl implements GrnService {

	public static final Logger LOGGER = LoggerFactory.getLogger(GrnServiceImpl.class);

	@Autowired
	private GrnRepo grnRepo;

	@Autowired
	private GrnDetailsRepo grnDetailsRepo;

	@Autowired
	private GrnTaxDetailsRepo grnTaxDetailsRepo;

	@Autowired
	private GrnFileUploadDetailsRepo grnFileUploadDetailsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CurrencyRepo currencyRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	private StockTransferGrnRepo stockTransferGrnRepo;

	@Autowired
	private StockTransferGrnDetailsRepo stockTransferGrnDetailsRepo;

	@Autowired
	private StockTransferGrnFileUploadDetailsRepo stockTransferGrnFileUploadDetailsRepo;

	@Override
	public GrnResponseDTO getGrnById(Long id) throws ApplicationException {
		GrnVO grnVO = grnRepo.getGrnById(id);

		if (grnVO == null) {
			throw new ApplicationException("GRN Not Found");
		}

		return buildGrnResponse(grnVO);
	}

	@Override
	public List<GrnResponseDTO> getGrnByOrgId(Long orgId, Long branch) throws ApplicationException {
		List<GrnVO> grnList = grnRepo.getGrnByOrgId(orgId, branch);

		if (grnList == null || grnList.isEmpty()) {
			throw new ApplicationException("GRN Not Found");
		}

		List<GrnResponseDTO> responseList = new ArrayList<>();

		for (GrnVO grnVO : grnList) {
			responseList.add(buildGrnResponse(grnVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateGrn(GrnDTO grnDTO, MultipartFile[] files) throws ApplicationException {
		GrnVO grnVO;
		String message;
		if (ObjectUtils.isNotEmpty(grnDTO.getId())) {
			grnVO = grnRepo.findById(grnDTO.getId()).orElseThrow(() -> new ApplicationException("GRN Not Found"));
			grnVO.setUpdatedBy(grnDTO.getCreatedBy());
			message = "GRN Updated Successfully";
		} else {
			grnVO = new GrnVO();
			String screenCode = "GRN";
			String docId = grnRepo.getGrnDocId(grnDTO.getOrgId(), grnDTO.getFinancialYear(), screenCode);
			grnVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(grnDTO.getOrgId(), grnDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			grnVO.setCreatedBy(grnDTO.getCreatedBy());
			grnVO.setUpdatedBy(grnDTO.getCreatedBy());
			message = "GRN Created Successfully";
		}

		setGrnValues(grnDTO, grnVO);

		grnVO = grnRepo.save(grnVO);

		saveAttachments(files, grnVO);

		GrnResponseDTO grnResponse = buildGrnResponse(grnVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("grnVO", grnResponse);

		return response;
	}

	private void setGrnValues(GrnDTO dto, GrnVO vo) throws ApplicationException {
		vo.setBelongsTo(dto.getBelongsTo());
		vo.setIsIgstApplicable(dto.getIsIgstApplicable());
		vo.setIsReverseCharge(dto.getIsReverseCharge());
		vo.setGatePassNo(dto.getGatePassNo());
		vo.setPoNo(dto.getPoNo());
		vo.setDealerType(dto.getDealerType());
		vo.setScheduleNo(dto.getScheduleNo());
		vo.setScheduleDate(dto.getScheduleDate());
		vo.setScheduleStartDate(dto.getScheduleStartDate());
		vo.setScheduleEndDate(dto.getScheduleEndDate());
		vo.setExchangeRate(dto.getExchangeRate());
		vo.setGrossAmount(dto.getGrossAmount());
		vo.setModvatCopyReceived(dto.getModvatCopyReceived());
		vo.setTotalQtyInKg(dto.getTotalQtyInKg());
		vo.setPartyDcNo(dto.getPartyDcNo());
		vo.setDiscount(dto.getDiscount());
		vo.setSupplierDcDate(dto.getSupplierDcDate());
		vo.setActive(dto.isActive());
		vo.setCancel(dto.isCancel());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());
		vo.setTotalAmountTax(dto.getTotalAmountTax());
		vo.setInvoiceSentOn(dto.getInvoiceSentOn());
		vo.setRemarks(dto.getRemarks());

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (dto.getLocation() != null && dto.getLocation() != 0) {
			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));
			vo.setLocation(location);
		}

		if (dto.getSupplierCode() != null && dto.getSupplierCode() != 0) {
			CustomerVO supplier = customerRepo.findById(dto.getSupplierCode())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));
			vo.setSupplierCode(supplier);
		}

		if (dto.getCurrency() != null && dto.getCurrency() > 0) {
			CurrencyVO currency = currencyRepo.findById(dto.getCurrency())
					.orElseThrow(() -> new ApplicationException("Currency Not Found"));
			vo.setCurrency(currency);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<GrnDetailsVO> oldDetails = grnDetailsRepo.findByGrnVO(vo);
			grnDetailsRepo.deleteAll(oldDetails);

			List<GrnTaxDetailsVO> oldTaxDetails = grnTaxDetailsRepo.findByGrnVO(vo);

			grnTaxDetailsRepo.deleteAll(oldTaxDetails);

			List<GrnFileUploadDetailsVO> oldFileDetails = grnFileUploadDetailsRepo.findByGrnVO(vo);
			grnFileUploadDetailsRepo.deleteAll(oldFileDetails);
		}

		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal basicAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		List<GrnDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getGrnDetailsDTO() != null) {
			for (GrnDetailsDTO detailDTO : dto.getGrnDetailsDTO()) {
				GrnDetailsVO detailVO = new GrnDetailsVO();

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));
					detailVO.setItem(item);
				}

				if (detailDTO.getPurchaseUnit() != null && detailDTO.getPurchaseUnit() > 0) {
					UnitMasterVO purchaseUnit = unitMasterRepo.findById(detailDTO.getPurchaseUnit())
							.orElseThrow(() -> new ApplicationException("Purchase Unit Not Found"));
					detailVO.setPurchaseUnit(purchaseUnit);
				}

				if (detailDTO.getPrimaryUnit() != null && detailDTO.getPrimaryUnit() > 0) {
					UnitMasterVO primaryUnit = unitMasterRepo.findById(detailDTO.getPrimaryUnit())
							.orElseThrow(() -> new ApplicationException("Primary Unit Not Found"));
					detailVO.setPrimaryUnit(primaryUnit);
				}

				if (detailDTO.getPoUnit() != null && detailDTO.getPoUnit() > 0) {
					UnitMasterVO poUnit = unitMasterRepo.findById(detailDTO.getPoUnit())
							.orElseThrow(() -> new ApplicationException("PO Unit Not Found"));
					detailVO.setPoUnit(poUnit);
				}

				if (detailDTO.getReceivedUnit() != null && detailDTO.getReceivedUnit() > 0) {
					UnitMasterVO receivedUnit = unitMasterRepo.findById(detailDTO.getReceivedUnit())
							.orElseThrow(() -> new ApplicationException("Received Unit Not Found"));
					detailVO.setReceivedUnit(receivedUnit);
				}

				if (detailDTO.getAccUnit() != null && detailDTO.getAccUnit() > 0) {
					UnitMasterVO accUnit = unitMasterRepo.findById(detailDTO.getAccUnit())
							.orElseThrow(() -> new ApplicationException("Accepted Unit Not Found"));
					detailVO.setAccUnit(accUnit);
				}

				detailVO.setStock(detailDTO.getStock());
				detailVO.setPurchaseTolerance(detailDTO.getPurchaseTolerance());
				detailVO.setInspectionable(detailDTO.getInspectionable());
				detailVO.setManufacturedDate(detailDTO.getManufacturedDate());
				detailVO.setPoRate(detailDTO.getPoRate());
				detailVO.setPoQty(detailDTO.getPoQty());
				detailVO.setReceivedQty(detailDTO.getReceivedQty());
				detailVO.setStoreStock(detailDTO.getStoreStock());

				detailVO.setPendingQty(detailDTO.getPoQty().subtract(detailDTO.getReceivedQty()));

				Set<Object[]> multipleFactor = grnRepo.getConversionFactorAmount(dto.getOrgId(), detailDTO.getPoQty(),
						detailDTO.getReceivedQty());
				BigDecimal factor = BigDecimal.ONE;

				for (Object[] ledger : multipleFactor) {

					if (ledger[0] != null) {
						factor = ledger[0] instanceof BigDecimal ? (BigDecimal) ledger[0]
								: BigDecimal.valueOf(((Number) ledger[0]).doubleValue());
					}
					break;
				}

				detailVO.setConversionFactor(factor);

//				BigDecimal poQtyInPurchaseUnit = BigDecimal.ONE;
//
//				Set<Object[]> multipleFactor = grnRepo.getConversionFactorAmount(dto.getOrgId(), detailDTO.getPoQty(),
//						detailDTO.getReceivedQty());
//
//				for (Object[] ledger : multipleFactor) {
//
//					if (ledger[0] != null) {
//
//						BigDecimal factor = ledger[0] instanceof BigDecimal ? (BigDecimal) ledger[0]
//								: BigDecimal.valueOf(((Number) ledger[0]).doubleValue());
//
//						poQtyInPurchaseUnit = factor.multiply(detailDTO.getReceivedQty());
//						detailVO.setConversionFactor(poQtyInPurchaseUnit);
//					}
//
//					break;
//				}

				detailVO.setRecQtyInPrimaryUnit(detailVO.getReceivedQty());
				detailVO.setAcceptQty(detailDTO.getAcceptQty());

				detailVO.setAccQtyInPrimaryUnit(detailVO.getAcceptQty());

				detailVO.setChallanQty(detailDTO.getChallanQty());

				detailVO.setRejectQty(detailDTO.getReceivedQty().subtract(detailDTO.getAcceptQty()));

				detailVO.setRejQtyInPrimaryUnit(detailVO.getRejectQty());

				detailVO.setExcessQty(detailDTO.getExcessQty());
				detailVO.setItemMaxQty(detailDTO.getItemMaxQty());

				BigDecimal rate = detailDTO.getPoQty() != null ? detailDTO.getPoQty() : BigDecimal.ZERO;

				BigDecimal qty = detailDTO.getAcceptQty() != null ? detailDTO.getAcceptQty() : BigDecimal.ZERO;

				BigDecimal amount = rate.multiply(qty);

				detailVO.setAmount(amount);

				basicAmount = basicAmount.add(amount);

				detailVO.setTaxPercentage(detailDTO.getTaxPercentage());
				detailVO.setHsnCode(detailDTO.getHsnCode());
				detailVO.setTaxType(detailDTO.getTaxType());
				detailVO.setInsurance(detailDTO.getInsurance());
				detailVO.setBankchrg(detailDTO.getBankchrg());
				detailVO.setLcost(detailDTO.getLcost());
				detailVO.setLandedCostRate(detailDTO.getLandedCostRate());
				detailVO.setApportionedCost(detailDTO.getApportionedCost());

				BigDecimal taxPercentage = detailDTO.getTaxPercentage() != null ? detailDTO.getTaxPercentage()
						: BigDecimal.ZERO;

				BigDecimal igstRate = BigDecimal.ZERO;

				BigDecimal cgstRate = BigDecimal.ZERO;

				BigDecimal sgstRate = BigDecimal.ZERO;

				BigDecimal igstAmount = BigDecimal.ZERO;

				BigDecimal cgstAmount = BigDecimal.ZERO;

				BigDecimal sgstAmount = BigDecimal.ZERO;

				if (dto.getIsIgstApplicable() != null && dto.getIsIgstApplicable().equalsIgnoreCase("Yes")) {

					igstRate = taxPercentage;

					igstAmount = amount.multiply(igstRate).divide(BigDecimal.valueOf(100));
					detailVO.setIgstAmount(igstAmount);
					detailVO.setIgstRate(igstRate);

				} else {

					cgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

					sgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

					cgstAmount = amount.multiply(cgstRate).divide(BigDecimal.valueOf(100));

					sgstAmount = amount.multiply(sgstRate).divide(BigDecimal.valueOf(100));

					detailVO.setCgstAmount(cgstAmount);
					detailVO.setCgstRate(cgstRate);
					detailVO.setSgstAmount(sgstAmount);
					detailVO.setSgstRate(sgstRate);
				}

				totalTaxAmount = totalTaxAmount.add(igstAmount).add(cgstAmount).add(sgstAmount);

				detailVO.setLandedValue(totalTaxAmount.add(detailDTO.getApportionedCost()).add(detailDTO.getInsurance())
						.add(detailDTO.getHandCharge()).add(detailDTO.getLcost()).add(detailDTO.getLandedCostRate())
						.add(basicAmount));
//				detailVO.setApportionedCost(apportionedCost);

				netAmount = basicAmount.add(totalTaxAmount);

				detailVO.setGrnVO(vo);

				itemDetailsList.add(detailVO);
			}
		}

		vo.setGrnDetailsVO(itemDetailsList);

		List<GrnTaxDetailsVO> taxList = new ArrayList<>();

		if (dto.getGrnTaxDetailsDTO() != null) {
			for (GrnTaxDetailsDTO taxDTO : dto.getGrnTaxDetailsDTO()) {
				GrnTaxDetailsVO taxVO = new GrnTaxDetailsVO();
				taxVO.setParticulars(taxDTO.getParticulars());
				taxVO.setTax(taxDTO.getTax());
				taxVO.setTaxVal(taxDTO.getTaxVal());
				taxVO.setTaxAmount(taxDTO.getTaxAmount());
				taxVO.setGrnVO(vo);
				taxList.add(taxVO);
			}
		}

		vo.setGrnTaxDetailsVO(taxList);
		vo.setBasicAmount(basicAmount);
		vo.setTotalAmountTax(totalTaxAmount);
		vo.setNetAmount(netAmount);
	}

	private GrnResponseDTO buildGrnResponse(GrnVO vo) {
		GrnResponseDTO responseDTO = new GrnResponseDTO();
		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setIsIgstApplicable(vo.getIsIgstApplicable());
		responseDTO.setIsReverseCharge(vo.getIsReverseCharge());
		responseDTO.setGatePassNo(vo.getGatePassNo());
		responseDTO.setPoNo(vo.getPoNo());
		responseDTO.setDealerType(vo.getDealerType());
		responseDTO.setScheduleNo(vo.getScheduleNo());
		responseDTO.setScheduleDate(vo.getScheduleDate());
		responseDTO.setScheduleStartDate(vo.getScheduleStartDate());
		responseDTO.setScheduleEndDate(vo.getScheduleEndDate());
		responseDTO.setExchangeRate(vo.getExchangeRate());
		responseDTO.setGrnClearTime(vo.getGrnClearTime());
		responseDTO.setGrossAmount(vo.getGrossAmount());
		responseDTO.setModvatCopyReceived(vo.getModvatCopyReceived());
		responseDTO.setTotalQtyInKg(vo.getTotalQtyInKg());
		responseDTO.setPartyDcNo(vo.getPartyDcNo());
		responseDTO.setDiscount(vo.getDiscount());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setSupplierDcDate(vo.getSupplierDcDate());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());
		responseDTO.setNetAmount(vo.getNetAmount());
		responseDTO.setTotalAmountTax(vo.getTotalAmountTax());
		responseDTO.setBasicAmount(vo.getBasicAmount());
		responseDTO.setInvoiceSentOn(vo.getInvoiceSentOn());
		responseDTO.setRemarks(vo.getRemarks());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		if (vo.getLocation() != null) {
			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();
			locationDTO.setId(vo.getLocation().getId());
			locationDTO.setLocationName(vo.getLocation().getLocationName());
			responseDTO.setLocation(locationDTO);
		}

		if (vo.getSupplierCode() != null) {
			SupplierResponseDTO supplierDTO = new SupplierResponseDTO();
			supplierDTO.setId(vo.getSupplierCode().getId());
			supplierDTO.setSupplierName(vo.getSupplierCode().getCustomerName());
			supplierDTO.setSupplierCode(vo.getSupplierCode().getCustomerCode());
			supplierDTO.setAddress(vo.getSupplierCode().getAddress());
			supplierDTO.setGstNo(vo.getSupplierCode().getGstNo());
			supplierDTO.setGstApproval(vo.getSupplierCode().isGstApplicable() ? "Yes" : "No");
			if (vo.getSupplierCode().getGstState() != null) {
				supplierDTO.setGstSate(vo.getSupplierCode().getGstState().getStateName());
			}
			responseDTO.setSupplierCode(supplierDTO);
		}

		if (vo.getCurrency() != null) {
			CurrencyResponseDTO currencyDTO = new CurrencyResponseDTO();
			currencyDTO.setId(vo.getCurrency().getId());
			currencyDTO.setCurrencyName(vo.getCurrency().getCurrency());
			responseDTO.setCurrency(currencyDTO);
		}

		List<GrnDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vo.getGrnDetailsVO() != null && !vo.getGrnDetailsVO().isEmpty()) {
			for (GrnDetailsVO detailVO : vo.getGrnDetailsVO()) {
				GrnDetailsResponseDTO detailResponse = new GrnDetailsResponseDTO();

				detailResponse.setId(detailVO.getId());
				detailResponse.setStock(detailVO.getStock());
				detailResponse.setPurchaseTolerance(detailVO.getPurchaseTolerance());
				detailResponse.setInspectionable(detailVO.getInspectionable());
				detailResponse.setManufacturedDate(detailVO.getManufacturedDate());
				detailResponse.setPoRate(detailVO.getPoRate());
				detailResponse.setPoQty(detailVO.getPoQty());
				detailResponse.setReceivedQty(detailVO.getReceivedQty());
				detailResponse.setStoreStock(detailVO.getStoreStock());
				detailResponse.setPendingQty(detailVO.getPendingQty());
				detailResponse.setConversionFactor(detailVO.getConversionFactor());
				detailResponse.setRecQtyInPrimaryUnit(detailVO.getRecQtyInPrimaryUnit());
				detailResponse.setAcceptQty(detailVO.getAcceptQty());
				detailResponse.setAccQtyInPrimaryUnit(detailVO.getAccQtyInPrimaryUnit());
				detailResponse.setRejectQty(detailVO.getRejectQty());
				detailResponse.setRejQtyInPrimaryUnit(detailVO.getRejQtyInPrimaryUnit());
				detailResponse.setExcessQty(detailVO.getExcessQty());
				detailResponse.setItemMaxQty(detailVO.getItemMaxQty());
				detailResponse.setTaxPercentage(detailVO.getTaxPercentage());
				detailResponse.setAmount(detailVO.getAmount());
				detailResponse.setHsnCode(detailVO.getHsnCode());
				detailResponse.setTaxType(detailVO.getTaxType());
				detailResponse.setSgstRate(detailVO.getSgstRate());
				detailResponse.setSgstAmount(detailVO.getSgstAmount());
				detailResponse.setCgstRate(detailVO.getCgstRate());
				detailResponse.setCgstAmount(detailVO.getCgstAmount());
				detailResponse.setIgstRate(detailVO.getIgstRate());
				detailResponse.setIgstAmount(detailVO.getIgstAmount());
				detailResponse.setApportionedCost(detailVO.getApportionedCost());
				detailResponse.setInsurance(detailVO.getInsurance());
				detailResponse.setBankchrg(detailVO.getBankchrg());
				detailResponse.setLcost(detailVO.getLcost());
				detailResponse.setLandedCostRate(detailVO.getLandedCostRate());
				detailResponse.setLandedValue(detailVO.getLandedValue());

				if (detailVO.getItem() != null) {
					ItemMasterDetailsResponseDTO itemDTO = new ItemMasterDetailsResponseDTO();
					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());
					if (detailVO.getItem().getHsnCode() != null) {
						itemDTO.setHsnCode(detailVO.getItem().getHsnCode().getHsn());
					}
					if (detailVO.getItem() != null && detailVO.getItem().getPurchaseUnit() != null) {

						UnitMasterResponseDTO purchaseUnitDTO = new UnitMasterResponseDTO();

						purchaseUnitDTO.setId(detailVO.getItem().getPurchaseUnit().getId());

						purchaseUnitDTO.setUnitId(detailVO.getItem().getPurchaseUnit().getUnitId());

						purchaseUnitDTO.setUnitDescription(detailVO.getItem().getPurchaseUnit().getDescription());

						itemDTO.setPurchaseUnit(purchaseUnitDTO);
					}

					if (detailVO.getItem() != null && detailVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO purchaseUnitDTO = new UnitMasterResponseDTO();

						purchaseUnitDTO.setId(detailVO.getItem().getPrimaryUnit().getId());

						purchaseUnitDTO.setUnitId(detailVO.getItem().getPrimaryUnit().getUnitId());

						purchaseUnitDTO.setUnitDescription(detailVO.getItem().getPrimaryUnit().getDescription());

						itemDTO.setPrimaryUnit(purchaseUnitDTO);
					}

					detailResponse.setItem(itemDTO);
				}

//				if (detailVO.getPurchaseUnit() != null) {
//					UnitResponseDTO unitDTO = new UnitResponseDTO();
//					unitDTO.setId(detailVO.getPurchaseUnit().getId());
//					unitDTO.setUnitId(detailVO.getPurchaseUnit().getUnitId());
//					detailResponse.setPurchaseUnit(unitDTO);
//				}
//
//				if (detailVO.getPrimaryUnit() != null) {
//					UnitResponseDTO unitDTO = new UnitResponseDTO();
//					unitDTO.setId(detailVO.getPrimaryUnit().getId());
//					unitDTO.setUnitId(detailVO.getPrimaryUnit().getUnitId());
//					detailResponse.setPrimaryUnit(unitDTO);
//				}

				if (detailVO.getPoUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getPoUnit().getId());
					unitDTO.setUnitId(detailVO.getPoUnit().getUnitId());
					detailResponse.setPoUnit(unitDTO);
				}

				if (detailVO.getReceivedUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getReceivedUnit().getId());
					unitDTO.setUnitId(detailVO.getReceivedUnit().getUnitId());
					detailResponse.setReceivedUnit(unitDTO);
				}

				if (detailVO.getAccUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getAccUnit().getId());
					unitDTO.setUnitId(detailVO.getAccUnit().getUnitId());
					detailResponse.setAccUnit(unitDTO);
				}

				detailsResponseList.add(detailResponse);
			}
		}

		responseDTO.setGrnDetailsResponseDTO(detailsResponseList);

		List<GrnTaxDetailsResponseDTO> taxResponseList = new ArrayList<>();

		if (vo.getGrnTaxDetailsVO() != null && !vo.getGrnTaxDetailsVO().isEmpty()) {
			for (GrnTaxDetailsVO taxVO : vo.getGrnTaxDetailsVO()) {
				GrnTaxDetailsResponseDTO taxResponse = new GrnTaxDetailsResponseDTO();
				taxResponse.setId(taxVO.getId());
				taxResponse.setParticulars(taxVO.getParticulars());
				taxResponse.setTax(taxVO.getTax());
				taxResponse.setTaxVal(taxVO.getTaxVal());
				taxResponse.setTaxAmount(taxVO.getTaxAmount());
				taxResponseList.add(taxResponse);
			}
		}

		responseDTO.setGrnTaxDetailsResponseDTO(taxResponseList);

		List<GrnFileUploadDetailsResponseDTO> fileResponseList = new ArrayList<>();

		if (vo.getGrnFileUploadDetailsVO() != null && !vo.getGrnFileUploadDetailsVO().isEmpty()) {
			for (GrnFileUploadDetailsVO fileVO : vo.getGrnFileUploadDetailsVO()) {
				GrnFileUploadDetailsResponseDTO fileResponse = new GrnFileUploadDetailsResponseDTO();
				fileResponse.setId(fileVO.getId());
				fileResponse.setName(fileVO.getName());
				fileResponse.setFileName(fileVO.getFileName());
				fileResponse.setFilePath(fileVO.getFilePath());
				fileResponse.setFileSize(fileVO.getFileSize());
				fileResponse.setContentType(fileVO.getContentType());
				fileResponse.setUploadOn(fileVO.getUploadOn());
				fileResponseList.add(fileResponse);
			}
		}

		responseDTO.setGrnFileUploadDetailsResponseDTO(fileResponseList);

		return responseDTO;
	}

	@Value("${grn.upload.path}")
	private String uploadPath;

	private void saveAttachments(MultipartFile[] files, GrnVO grnVO) throws ApplicationException {
		if (files == null || files.length == 0) {
			return;
		}

		try {
			Path grnFolder = Paths.get(uploadPath, "grn", grnVO.getId().toString());
			createDirectory(grnFolder);

			if (ObjectUtils.isNotEmpty(grnVO.getId())) {
				List<GrnFileUploadDetailsVO> existingAttachments = grnFileUploadDetailsRepo.findByGrnVO(grnVO);
				if (existingAttachments != null && !existingAttachments.isEmpty()) {
					grnFileUploadDetailsRepo.deleteAll(existingAttachments);
				}
			}

			List<GrnFileUploadDetailsVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {
				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "file";
				}

				originalName = originalName.replaceAll("\\s+", "_");
				String extension = "";

				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + grnVO.getId() + extension;
				Path filePath = grnFolder.resolve(fileName);

				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/grn/viewFile/")
						.toUriString();

				String relativePath = uploadPath.replace("\\", "/");
				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				String publicUrl = baseUrl + relativePath;

				GrnFileUploadDetailsVO attachment = new GrnFileUploadDetailsVO();
				attachment.setGrnVO(grnVO);
				attachment.setName(file.getOriginalFilename());
				attachment.setFileName(fileName);
				attachment.setFilePath(publicUrl);
				attachment.setFileSize(file.getSize());
				attachment.setContentType(file.getContentType());
				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			if (!attachmentList.isEmpty()) {
				List<GrnFileUploadDetailsVO> saved = grnFileUploadDetailsRepo.saveAll(attachmentList);
				grnVO.setGrnFileUploadDetailsVO(saved);
			}

		} catch (IOException e) {
			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private void createDirectory(Path path) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewGrnFile(HttpServletRequest request) throws IOException {
		return serveFile(request, "/api/grn/viewFile/", uploadPath);
	}

	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
			throws IOException {
		String uri = request.getRequestURI();
		String relativePath = uri.replace(apiPrefix, "");
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
		Path filePath = baseDir.resolve(relativePath).normalize();

		if (!filePath.startsWith(baseDir)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		if (!Files.exists(filePath)) {
			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	@Override
	public List<Map<String, Object>> getSupplierDetailsForGrn(Long orgId, Long branch) {
		Set<Object[]> chType = grnRepo.getSupplierDetailsForGrn(orgId, branch);
		return getSupplierDetailsResponse(chType);
	}

	private List<Map<String, Object>> getSupplierDetailsResponse(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("supplierId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("supplierName", ch[1] != null ? ch[1].toString() : "");
			map.put("supplierCode", ch[2] != null ? ch[2].toString() : "");
			map.put("address", ch[3] != null ? ch[3].toString() : "");
			map.put("pinCode", ch[4] != null ? ch[4].toString() : "");
			map.put("gstNo", ch[5] != null ? ch[5].toString() : "");
			map.put("stateName", ch[6] != null ? ch[6].toString() : "");
			map.put("isRegistered", ch[7] != null ? ch[7].toString() : "");
			map.put("country", ch[8] != null ? ch[8].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public String getGrnDocId(Long orgId, String financialYear, String screenCode) {
		String screenCode1 = "GRN";
		return grnRepo.getGrnDocId(orgId, financialYear, screenCode1);
	}

	@Override
	public List<Map<String, Object>> getGatePassDocIdDetails(Long orgId, Long branch, Long supplierCode) {
		Set<Object[]> chType = grnRepo.getGatePassDocIdDetails(orgId, branch, supplierCode);
		return getGatePassDocIdDetails(chType);
	}

	private List<Map<String, Object>> getGatePassDocIdDetails(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("gatePassId", ch[2] != null ? ((Number) ch[2]).longValue() : null);
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderNoBasedDocId(Long orgId, Long branch, Long supplierCode,
			String gatePass) {
		Set<Object[]> chType = grnRepo.getPurchaseOrderNoBasedDocId(orgId, branch, supplierCode, gatePass);
		return getPurchaseOrderNoBasedDocId(chType);
	}

	private List<Map<String, Object>> getPurchaseOrderNoBasedDocId(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("gatePassId", ch[2] != null ? ((Number) ch[2]).longValue() : null);
			map.put("Id", ch[3] != null ? ((Number) ch[3]).longValue() : null);
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getScheduleDocIdDetails(Long orgId, String purchaseOrderNo, String date,
			String gatePass) {
		Set<Object[]> chType = grnRepo.getScheduleDocIdDetails(orgId, purchaseOrderNo, date, gatePass);
		return getScheduleDocIdDetails(chType);
	}

	private List<Map<String, Object>> getScheduleDocIdDetails(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("scheduleId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("docId", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			map.put("startDate", ch[3] != null ? ch[3].toString() : "");
			map.put("endDate", ch[4] != null ? ch[4].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getPoNmberBasedItemDetails(Long orgId, Long branch, String purchaseOrderNo) {
		Set<Object[]> chType = grnRepo.getPoNmberBasedItemDetails(orgId, branch, purchaseOrderNo);
		return getPoNmberBasedItemDetails(chType);
	}

	private List<Map<String, Object>> getPoNmberBasedItemDetails(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("hsn", ch[4] != null ? ch[4].toString() : "");
			map.put("primaryUnit", ch[5] != null ? ((Number) ch[5]).longValue() : null);
			map.put("rate", ch[6] != null ? ch[6].toString() : "");
			list.add(map);
		}

		return list;
	}

	// StockTransferGrn

	@Override
	public StockTransferGrnResponseDTO getStockTransferGrnById(Long id) throws ApplicationException {
		StockTransferGrnVO stockTransferGrnVO = stockTransferGrnRepo.getStockTransferGrnById(id);

		if (stockTransferGrnVO == null) {
			throw new ApplicationException("Stock Transfer GRN Not Found");
		}

		return buildStockTransferGrnResponse(stockTransferGrnVO);
	}

	@Override
	public List<StockTransferGrnResponseDTO> getStockTransferGrnByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<StockTransferGrnVO> stockTransferGrnList = stockTransferGrnRepo.getStockTransferGrnByOrgId(orgId, branch);

		if (stockTransferGrnList == null || stockTransferGrnList.isEmpty()) {
			throw new ApplicationException("Stock Transfer GRN Not Found");
		}

		List<StockTransferGrnResponseDTO> responseList = new ArrayList<>();

		for (StockTransferGrnVO stockTransferGrnVO : stockTransferGrnList) {
			responseList.add(buildStockTransferGrnResponse(stockTransferGrnVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateStockTransferGrn(StockTransferGrnDTO stockTransferGrnDTO,
			MultipartFile[] files) throws ApplicationException {
		StockTransferGrnVO stockTransferGrnVO;
		String message;
		if (ObjectUtils.isNotEmpty(stockTransferGrnDTO.getId())) {
			stockTransferGrnVO = stockTransferGrnRepo.findById(stockTransferGrnDTO.getId())
					.orElseThrow(() -> new ApplicationException("Stock Transfer GRN Not Found"));
			stockTransferGrnVO.setUpdatedBy(stockTransferGrnDTO.getCreatedBy());
			message = "Stock Transfer GRN Updated Successfully";
		} else {
			stockTransferGrnVO = new StockTransferGrnVO();
			String screenCode = "STG";
			String docId = stockTransferGrnRepo.getStockTransferGrnDocId(stockTransferGrnDTO.getOrgId(),
					stockTransferGrnDTO.getFinancialYear(), screenCode);
			stockTransferGrnVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(stockTransferGrnDTO.getOrgId(),
							stockTransferGrnDTO.getFinancialYear(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			stockTransferGrnVO.setCreatedBy(stockTransferGrnDTO.getCreatedBy());
			stockTransferGrnVO.setUpdatedBy(stockTransferGrnDTO.getCreatedBy());
			message = "Stock Transfer GRN Created Successfully";
		}

		setStockTransferGrnValues(stockTransferGrnDTO, stockTransferGrnVO);

		stockTransferGrnVO = stockTransferGrnRepo.save(stockTransferGrnVO);

		saveStockTransferAttachments(files, stockTransferGrnVO);

		StockTransferGrnResponseDTO stockTransferGrnResponse = buildStockTransferGrnResponse(stockTransferGrnVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("stockTransferGrnVO", stockTransferGrnResponse);

		return response;
	}

	private void setStockTransferGrnValues(StockTransferGrnDTO dto, StockTransferGrnVO vo) throws ApplicationException {
		vo.setBelongsTo(dto.getBelongsTo());
		vo.setIsIgstApplicable(dto.getIsIgstApplicable());
		vo.setIsReverseCharge(dto.getIsReverseCharge());
		vo.setGatePassNo(dto.getGatePassNo());
		vo.setPoNo(dto.getPoNo());
		vo.setDealerType(dto.getDealerType());
		vo.setScheduleNo(dto.getScheduleNo());
		vo.setScheduleDate(dto.getScheduleDate());
		vo.setScheduleStartDate(dto.getScheduleStartDate());
		vo.setScheduleEndDate(dto.getScheduleEndDate());
		vo.setExchangeRate(dto.getExchangeRate());
		vo.setGrossAmount(dto.getGrossAmount());
		vo.setModvatCopyReceived(dto.getModvatCopyReceived());
		vo.setTotalQtyInKg(dto.getTotalQtyInKg());
		vo.setPartyDcNo(dto.getPartyDcNo());
		vo.setDiscount(dto.getDiscount());
		vo.setSupplierDcDate(dto.getSupplierDcDate());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());
		vo.setTotalAmountTax(dto.getTotalAmountTax());
		vo.setInvoiceSentOn(dto.getInvoiceSentOn());
		vo.setRemarks(dto.getRemarks());

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (dto.getLocation() != null && dto.getLocation() != 0) {
			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));
			vo.setLocation(location);
		}

		if (dto.getSupplierCode() != null && dto.getSupplierCode() != 0) {
			CustomerVO supplier = customerRepo.findById(dto.getSupplierCode())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));
			vo.setSupplierCode(supplier);
		}

		if (dto.getCurrency() != null && dto.getCurrency() > 0) {
			CurrencyVO currency = currencyRepo.findById(dto.getCurrency())
					.orElseThrow(() -> new ApplicationException("Currency Not Found"));
			vo.setCurrency(currency);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<StockTransferGrnDetailsVO> oldDetails = stockTransferGrnDetailsRepo.findByStockTransferGrnVO(vo);
			stockTransferGrnDetailsRepo.deleteAll(oldDetails);

			List<StockTransferGrnFileUploadDetailsVO> oldFileDetails = stockTransferGrnFileUploadDetailsRepo
					.findByStockTransferGrnVO(vo);
			stockTransferGrnFileUploadDetailsRepo.deleteAll(oldFileDetails);
		}

		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal basicAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		List<StockTransferGrnDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getStockTransferGrnDetailsDTO() != null) {
			for (StockTransferGrnDetailsDTO detailDTO : dto.getStockTransferGrnDetailsDTO()) {
				StockTransferGrnDetailsVO detailVO = new StockTransferGrnDetailsVO();

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));
					detailVO.setItem(item);
				}

				if (detailDTO.getPoUnit() != null && detailDTO.getPoUnit() > 0) {
					UnitMasterVO purchaseUnit = unitMasterRepo.findById(detailDTO.getPoUnit())
							.orElseThrow(() -> new ApplicationException("Purchase Unit Not Found"));
					detailVO.setPoUnit(purchaseUnit);
				}

				if (detailDTO.getPrimaryUnit() != null && detailDTO.getPrimaryUnit() > 0) {
					UnitMasterVO primaryUnit = unitMasterRepo.findById(detailDTO.getPrimaryUnit())
							.orElseThrow(() -> new ApplicationException("Primary Unit Not Found"));
					detailVO.setPrimaryUnit(primaryUnit);
				}

				if (detailDTO.getReceivedUnit() != null && detailDTO.getReceivedUnit() > 0) {
					UnitMasterVO receivedUnit = unitMasterRepo.findById(detailDTO.getReceivedUnit())
							.orElseThrow(() -> new ApplicationException("Received Unit Not Found"));
					detailVO.setReceivedUnit(receivedUnit);
				}

				if (detailDTO.getAccUnit() != null && detailDTO.getAccUnit() > 0) {
					UnitMasterVO accUnit = unitMasterRepo.findById(detailDTO.getAccUnit())
							.orElseThrow(() -> new ApplicationException("Accepted Unit Not Found"));
					detailVO.setAccUnit(accUnit);
				}

				detailVO.setStock(detailDTO.getStock());
				detailVO.setPurchaseTolerance(detailDTO.getPurchaseTolerance());
				detailVO.setInspectionable(detailDTO.getInspectionable());
				detailVO.setPoRate(detailDTO.getPoRate());
				detailVO.setPoQty(detailDTO.getPoQty());
				detailVO.setReceivedQty(detailDTO.getReceivedQty());
				detailVO.setStoreStock(detailDTO.getStoreStock());
				detailVO.setPendingQty(detailDTO.getPoQty().subtract(detailDTO.getReceivedQty()));

				Set<Object[]> multipleFactor = stockTransferGrnRepo.getConversionFactorAmount(dto.getOrgId(),
						detailDTO.getPoQty(), detailDTO.getReceivedQty());
				BigDecimal factor = BigDecimal.ONE;

				for (Object[] ledger : multipleFactor) {
					if (ledger[0] != null) {
						factor = ledger[0] instanceof BigDecimal ? (BigDecimal) ledger[0]
								: BigDecimal.valueOf(((Number) ledger[0]).doubleValue());
					}
					break;
				}

				detailVO.setConversionFactor(factor);
				detailVO.setRecQtyInPrimaryUnit(detailVO.getReceivedQty());
				detailVO.setAcceptQty(detailDTO.getAcceptQty());
				detailVO.setAccQtyInPrimaryUnit(detailVO.getAcceptQty());
				detailVO.setRejectQty(detailDTO.getReceivedQty().subtract(detailDTO.getAcceptQty()));
				detailVO.setRejQtyInPrimaryUnit(detailVO.getRejectQty());
				detailVO.setExcessQty(detailDTO.getExcessQty());
				detailVO.setItemMaxQty(detailDTO.getItemMaxQty());

				BigDecimal rate = detailDTO.getPoQty() != null ? detailDTO.getPoQty() : BigDecimal.ZERO;
				BigDecimal qty = detailDTO.getAcceptQty() != null ? detailDTO.getAcceptQty() : BigDecimal.ZERO;
				BigDecimal amount = rate.multiply(qty);

				detailVO.setAmount(amount);
				basicAmount = basicAmount.add(amount);

//				detailVO.setTaxPercentage(detailDTO.getTaxPercentage());
//	            detailVO.setHsnCode(detailDTO.getHsnCode());
//	            detailVO.setTaxType(detailDTO.getTaxType());
				detailVO.setInsurance(detailDTO.getInsurance());
				detailVO.setBankchrg(detailDTO.getBankchrg());
				detailVO.setLcost(detailDTO.getLcost());
				detailVO.setLandedCostRate(detailDTO.getLandedCostRate());
				detailVO.setHandCharge(detailDTO.getHandCharge());
				detailVO.setApportionedCost(detailDTO.getApportionedCost());
				detailVO.setLandedValue(totalTaxAmount.add(detailDTO.getApportionedCost()).add(detailDTO.getInsurance())
						.add(detailDTO.getHandCharge()).add(detailDTO.getLcost()).add(detailDTO.getLandedCostRate())
						.add(basicAmount));

				netAmount = basicAmount.add(totalTaxAmount);

				detailVO.setStockTransferGrnVO(vo);

				itemDetailsList.add(detailVO);
			}
		}
		vo.setStockTransferGrnDetailsVO(itemDetailsList);
		vo.setBasicAmount(basicAmount);
		vo.setTotalAmountTax(totalTaxAmount);
		vo.setNetAmount(netAmount);
	}

	private StockTransferGrnResponseDTO buildStockTransferGrnResponse(StockTransferGrnVO vo) {
		StockTransferGrnResponseDTO responseDTO = new StockTransferGrnResponseDTO();
		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setIsIgstApplicable(vo.getIsIgstApplicable());
		responseDTO.setIsReverseCharge(vo.getIsReverseCharge());
		responseDTO.setGatePassNo(vo.getGatePassNo());
		responseDTO.setPoNo(vo.getPoNo());
		responseDTO.setDealerType(vo.getDealerType());
		responseDTO.setScheduleNo(vo.getScheduleNo());
		responseDTO.setScheduleDate(vo.getScheduleDate());
		responseDTO.setScheduleStartDate(vo.getScheduleStartDate());
		responseDTO.setScheduleEndDate(vo.getScheduleEndDate());
		responseDTO.setExchangeRate(vo.getExchangeRate());
		responseDTO.setGrnClearTime(vo.getGrnClearTime());
		responseDTO.setGrossAmount(vo.getGrossAmount());
		responseDTO.setModvatCopyReceived(vo.getModvatCopyReceived());
		responseDTO.setTotalQtyInKg(vo.getTotalQtyInKg());
		responseDTO.setPartyDcNo(vo.getPartyDcNo());
		responseDTO.setDiscount(vo.getDiscount());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setSupplierDcDate(vo.getSupplierDcDate());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());
		responseDTO.setNetAmount(vo.getNetAmount());
		responseDTO.setTotalAmountTax(vo.getTotalAmountTax());
		responseDTO.setBasicAmount(vo.getBasicAmount());
		responseDTO.setInvoiceSentOn(vo.getInvoiceSentOn());
		responseDTO.setRemarks(vo.getRemarks());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		if (vo.getLocation() != null) {
			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();
			locationDTO.setId(vo.getLocation().getId());
			locationDTO.setLocationName(vo.getLocation().getLocationName());
			responseDTO.setLocation(locationDTO);
		}

		if (vo.getSupplierCode() != null) {
			SupplierResponseDTO supplierDTO = new SupplierResponseDTO();
			supplierDTO.setId(vo.getSupplierCode().getId());
			supplierDTO.setSupplierName(vo.getSupplierCode().getCustomerName());
			supplierDTO.setSupplierCode(vo.getSupplierCode().getCustomerCode());
			supplierDTO.setAddress(vo.getSupplierCode().getAddress());
			supplierDTO.setGstNo(vo.getSupplierCode().getGstNo());
			supplierDTO.setGstApproval(vo.getSupplierCode().isGstApplicable() ? "Yes" : "No");
			if (vo.getSupplierCode().getGstState() != null) {
				supplierDTO.setGstSate(vo.getSupplierCode().getGstState().getStateName());
			}
			responseDTO.setSupplierCode(supplierDTO);
		}

		if (vo.getCurrency() != null) {
			CurrencyResponseDTO currencyDTO = new CurrencyResponseDTO();
			currencyDTO.setId(vo.getCurrency().getId());
			currencyDTO.setCurrencyName(vo.getCurrency().getCurrency());
			responseDTO.setCurrency(currencyDTO);
		}

		List<StockTransferGrnDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vo.getStockTransferGrnDetailsVO() != null && !vo.getStockTransferGrnDetailsVO().isEmpty()) {
			for (StockTransferGrnDetailsVO detailVO : vo.getStockTransferGrnDetailsVO()) {
				StockTransferGrnDetailsResponseDTO detailResponse = new StockTransferGrnDetailsResponseDTO();

				detailResponse.setId(detailVO.getId());
				detailResponse.setStock(detailVO.getStock());
				detailResponse.setPurchaseTolerance(detailVO.getPurchaseTolerance());
				detailResponse.setInspectionable(detailVO.getInspectionable());
				detailResponse.setPoRate(detailVO.getPoRate());
				detailResponse.setPoQty(detailVO.getPoQty());
				detailResponse.setReceivedQty(detailVO.getReceivedQty());
				detailResponse.setStoreStock(detailVO.getStoreStock());
				detailResponse.setPendingQty(detailVO.getPendingQty());
				detailResponse.setConversionFactor(detailVO.getConversionFactor());
				detailResponse.setRecQtyInPrimaryUnit(detailVO.getRecQtyInPrimaryUnit());
				detailResponse.setAcceptQty(detailVO.getAcceptQty());
				detailResponse.setAccQtyInPrimaryUnit(detailVO.getAccQtyInPrimaryUnit());
				detailResponse.setRejectQty(detailVO.getRejectQty());
				detailResponse.setRejQtyInPrimaryUnit(detailVO.getRejQtyInPrimaryUnit());
				detailResponse.setExcessQty(detailVO.getExcessQty());
				detailResponse.setItemMaxQty(detailVO.getItemMaxQty());
				detailResponse.setTaxPercentage(detailVO.getTaxPercentage());
				detailResponse.setAmount(detailVO.getAmount());

				detailResponse.setApportionedCost(detailVO.getApportionedCost());
				detailResponse.setInsurance(detailVO.getInsurance());
				detailResponse.setBankchrg(detailVO.getBankchrg());
				detailResponse.setLcost(detailVO.getLcost());
				detailResponse.setLandedCostRate(detailVO.getLandedCostRate());
				detailResponse.setLandedValue(detailVO.getLandedValue());
				detailResponse.setHandCharge(detailVO.getHandCharge());

				if (detailVO.getItem() != null) {
					ItemMasterDetailsResponseStockDTO itemDTO = new ItemMasterDetailsResponseStockDTO();
					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());
					if (detailVO.getItem().getHsnCode() != null) {
						itemDTO.setHsnCode(detailVO.getItem().getHsnCode().getHsn());
					}
					if (detailVO.getItem() != null && detailVO.getItem().getPurchaseUnit() != null) {
						UnitMasterResponseDTO purchaseUnitDTO = new UnitMasterResponseDTO();
						purchaseUnitDTO.setId(detailVO.getItem().getPurchaseUnit().getId());
						purchaseUnitDTO.setUnitId(detailVO.getItem().getPurchaseUnit().getUnitId());
						purchaseUnitDTO.setUnitDescription(detailVO.getItem().getPurchaseUnit().getDescription());
						itemDTO.setPurchaseUnit(purchaseUnitDTO);
					}

					if (detailVO.getItem() != null && detailVO.getItem().getPrimaryUnit() != null) {
						UnitMasterResponseDTO purchaseUnitDTO = new UnitMasterResponseDTO();
						purchaseUnitDTO.setId(detailVO.getItem().getPrimaryUnit().getId());
						purchaseUnitDTO.setUnitId(detailVO.getItem().getPrimaryUnit().getUnitId());
						purchaseUnitDTO.setUnitDescription(detailVO.getItem().getPrimaryUnit().getDescription());
						itemDTO.setPrimaryUnit(purchaseUnitDTO);
					}

					detailResponse.setItem(itemDTO);
				}

				if (detailVO.getPoUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getPoUnit().getId());
					unitDTO.setUnitId(detailVO.getPoUnit().getUnitId());
					detailResponse.setPoUnit(unitDTO);
				}

				if (detailVO.getReceivedUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getReceivedUnit().getId());
					unitDTO.setUnitId(detailVO.getReceivedUnit().getUnitId());
					detailResponse.setReceivedUnit(unitDTO);
				}

				if (detailVO.getAccUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getAccUnit().getId());
					unitDTO.setUnitId(detailVO.getAccUnit().getUnitId());
					detailResponse.setAccUnit(unitDTO);
				}

				detailsResponseList.add(detailResponse);
			}
		}

		responseDTO.setStockTransferGrnDetailsResponseDTO(detailsResponseList);

		List<StockTransferGrnFileUploadDetailsResponseDTO> fileResponseList = new ArrayList<>();

		if (vo.getStockTransferGrnFileUploadDetailsVO() != null
				&& !vo.getStockTransferGrnFileUploadDetailsVO().isEmpty()) {
			for (StockTransferGrnFileUploadDetailsVO fileVO : vo.getStockTransferGrnFileUploadDetailsVO()) {
				StockTransferGrnFileUploadDetailsResponseDTO fileResponse = new StockTransferGrnFileUploadDetailsResponseDTO();
				fileResponse.setId(fileVO.getId());
				fileResponse.setName(fileVO.getName());
				fileResponse.setFileName(fileVO.getFileName());
				fileResponse.setFilePath(fileVO.getFilePath());
				fileResponse.setFileSize(fileVO.getFileSize());
				fileResponse.setContentType(fileVO.getContentType());
				fileResponse.setUploadOn(fileVO.getUploadOn());
				fileResponseList.add(fileResponse);
			}
		}

		responseDTO.setStockTransferGrnFileUploadDetailsResponseDTO(fileResponseList);

		return responseDTO;
	}

	@Value("${stock.transfer.grn.upload.path}")
	private String stockTransferUploadPath;

	private void saveStockTransferAttachments(MultipartFile[] files, StockTransferGrnVO stockTransferGrnVO)
			throws ApplicationException {
		if (files == null || files.length == 0) {
			return;
		}

		try {
			Path stockTransferGrnFolder = Paths.get(stockTransferUploadPath, "stocktransfergrn",
					stockTransferGrnVO.getId().toString());
			createDirectorys(stockTransferGrnFolder);

			if (ObjectUtils.isNotEmpty(stockTransferGrnVO.getId())) {
				List<StockTransferGrnFileUploadDetailsVO> existingAttachments = stockTransferGrnFileUploadDetailsRepo
						.findByStockTransferGrnVO(stockTransferGrnVO);
				if (existingAttachments != null && !existingAttachments.isEmpty()) {
					stockTransferGrnFileUploadDetailsRepo.deleteAll(existingAttachments);
				}
			}

			List<StockTransferGrnFileUploadDetailsVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {
				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "file";
				}

				originalName = originalName.replaceAll("\\s+", "_");
				String extension = "";

				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + stockTransferGrnVO.getId() + extension;
				Path filePath = stockTransferGrnFolder.resolve(fileName);

				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/grn/viewStockTransferGrnFile/").toUriString();

				String relativePath = stockTransferUploadPath.replace("\\", "/");
				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				String publicUrl = baseUrl + relativePath;

				StockTransferGrnFileUploadDetailsVO attachment = new StockTransferGrnFileUploadDetailsVO();
				attachment.setStockTransferGrnVO(stockTransferGrnVO);
				attachment.setName(file.getOriginalFilename());
				attachment.setFileName(fileName);
				attachment.setFilePath(publicUrl);
				attachment.setFileSize(file.getSize());
				attachment.setContentType(file.getContentType());
				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			if (!attachmentList.isEmpty()) {
				List<StockTransferGrnFileUploadDetailsVO> saved = stockTransferGrnFileUploadDetailsRepo
						.saveAll(attachmentList);
				stockTransferGrnVO.setStockTransferGrnFileUploadDetailsVO(saved);
			}

		} catch (IOException e) {
			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private void createDirectorys(Path path) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewStockTransferGrnFile(HttpServletRequest request) throws IOException {
		return serveStockTransferFile(request, "/api/grn/viewStockTransferGrnFile/", stockTransferUploadPath);
	}

	private ResponseEntity<byte[]> serveStockTransferFile(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException {
		String uri = request.getRequestURI();
		String relativePath = uri.replace(apiPrefix, "");
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		if (relativePath.startsWith("uploads/")) {
			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
		Path filePath = baseDir.resolve(relativePath).normalize();

		if (!filePath.startsWith(baseDir)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		if (!Files.exists(filePath)) {
			return ResponseEntity.notFound().build();
		}

		String contentType = Files.probeContentType(filePath);
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		byte[] data = Files.readAllBytes(filePath);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
	}

	@Override
	public String getStockTransferGrnDocId(Long orgId, String financialYear) {
		String screenCode = "STG";
		return stockTransferGrnRepo.getStockTransferGrnDocId(orgId, financialYear, screenCode);
	}

	@Override
	public List<Map<String, Object>> getGatePassDocIdDetailsForStockTransfer(Long orgId, Long branch,
			Long supplierCode) {
		Set<Object[]> chType = stockTransferGrnRepo.getGatePassDocIdDetailsForStockTransfer(orgId, branch,
				supplierCode);
		return getGatePassDocIdDetailsForStockTransfer(chType);
	}

	private List<Map<String, Object>> getGatePassDocIdDetailsForStockTransfer(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("gatePassId", ch[2] != null ? ((Number) ch[2]).longValue() : null);
			map.put("invoiceNumber", ch[3] != null ? ch[3].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderNumberStockTransfer(Long orgId, Long branch, Long supplierCode) {
		Set<Object[]> supplierDetails = stockTransferGrnRepo.getPurchaseOrderNumberStockTransfer(orgId, branch,
				supplierCode);
		return getPurchaseOrderNumberStockTransfer(supplierDetails);
	}

	private List<Map<String, Object>> getPurchaseOrderNumberStockTransfer(Set<Object[]> supplierDetails) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : supplierDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getScheduleDocIdStockTransfer(Long orgId, Long branch, Long supplierCode,
			String purchaseOrderNo) {
		Set<Object[]> supplierDetails = stockTransferGrnRepo.getScheduleDocIdStockTransfer(orgId, branch, supplierCode,
				purchaseOrderNo);
		return getScheduleDocIdStockTransfer(supplierDetails);
	}

	private List<Map<String, Object>> getScheduleDocIdStockTransfer(Set<Object[]> supplierDetails) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : supplierDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("scheduleStartDate", ch[2] != null ? ch[2].toString() : "");
			map.put("scheduleEndDate", ch[3] != null ? ch[3].toString() : "");
			map.put("purchaseDeliveryScheduleBasicId", ch[4] != null ? ((Number) ch[4]).longValue() : null);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsForStockTransfer(Long orgId, Long branch, String purchaseOrderNo) {
		Set<Object[]> supplierDetails = stockTransferGrnRepo.getItemDetailsForStockTransfer(orgId, branch,
				purchaseOrderNo);
		return getItemDetailsForStockTransfer(supplierDetails);
	}

	private List<Map<String, Object>> getItemDetailsForStockTransfer(Set<Object[]> supplierDetails) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : supplierDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("unitmasterId", ch[3] != null ? ((Number) ch[3]).longValue() : null);
			map.put("unitId", ch[4] != null ? ((Number) ch[4]).longValue() : null);
			map.put("inspection", ch[5] != null ? ((Number) ch[5]).longValue() : null);

			if ("100%".equalsIgnoreCase(ch[6] != null ? ch[6].toString() : "")) {

				map.put("inspectionDescription", "Yes");

			} else if ("Sample".equalsIgnoreCase(ch[6] != null ? ch[6].toString() : "")
					|| "Not Required".equalsIgnoreCase(ch[6] != null ? ch[6].toString() : "")) {

				map.put("inspectionDescription", "No");

			} else {

				map.put("inspectionDescription", "");
			}
			map.put("qtyInPrimaryUnit", ch[7] != null ? new BigDecimal(ch[7].toString()) : null);

			map.put("rateInInr", ch[8] != null ? new BigDecimal(ch[8].toString()) : null);
			list.add(map);
		}
		return list;
	}

	
	
	@Override
	public List<Map<String, Object>> getLocationDetails(Long orgId, Long branch) {
		Set<Object[]> supplierDetails = stockTransferGrnRepo.getLocationDetails(orgId, branch);
		return getLocationDetails(supplierDetails);
	}

	private List<Map<String, Object>> getLocationDetails(Set<Object[]> supplierDetails) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : supplierDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("locationId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("locationName", ch[1] != null ? ch[1].toString() : "");
			map.put("locationType", ch[2] != null ? ch[2].toString() : "");
			map.put("locationDetails", ch[3] != null ? ((Number) ch[3]).longValue() : null);
			list.add(map);
		}
		return list;
	}
}