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
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.GrnDetailsDTO;
import com.efitops.basesetup.dto.GrnTaxDetailsDTO;
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

				} else {

					cgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

					sgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

					cgstAmount = amount.multiply(cgstRate).divide(BigDecimal.valueOf(100));

					sgstAmount = amount.multiply(sgstRate).divide(BigDecimal.valueOf(100));
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
					detailResponse.setItem(itemDTO);
				}

				if (detailVO.getPurchaseUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getPurchaseUnit().getId());
					unitDTO.setUnitId(detailVO.getPurchaseUnit().getUnitId());
					detailResponse.setPurchaseUnit(unitDTO);
				}

				if (detailVO.getPrimaryUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getPrimaryUnit().getId());
					unitDTO.setUnitId(detailVO.getPrimaryUnit().getUnitId());
					detailResponse.setPrimaryUnit(unitDTO);
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
			list.add(map);
		}

		return list;
	}

	@Override
	public String getGrnDocId(Long orgId, String financialYear, String screenCode) {
		String screenCode1 = "GRN";
		return grnRepo.getGrnDocId(orgId, financialYear, screenCode1);
	}
}