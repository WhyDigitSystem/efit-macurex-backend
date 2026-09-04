package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseCashDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseFileUploadDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeMasterResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemCategoryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseCloseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LmeResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderDeliveryScheduleShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderImportDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderLocalDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderLocalFileUploadDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderLocalTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.DirectPurchaseCashDetailsDTO;
import com.efitops.basesetup.dto.DirectPurchaseDTO;
import com.efitops.basesetup.dto.DirectPurchaseTaxDetailsDTO;
import com.efitops.basesetup.dto.PoType;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseOrderDeliveryScheduleShortCloseDTO;
import com.efitops.basesetup.dto.PurchaseOrderDeliveryScheduleShortCloseDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderImportDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderLocalDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderLocalFileUploadDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderLocalTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DirectPurchaseCashDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseFileUploadDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseTaxDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.PurchaseOrderDeliveryScheduleShortCloseDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderDeliveryScheduleShortCloseVO;
import com.efitops.basesetup.entity.PurchaseOrderImportDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderLocalDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderLocalFileUploadDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderLocalTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;
import com.efitops.basesetup.entity.StockTransferGrnFileUploadDetailsVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DirectPurchaseCashDetailsRepo;
import com.efitops.basesetup.repository.DirectPurchaseFileUploadDetailsRepo;
import com.efitops.basesetup.repository.DirectPurchaseRepo;
import com.efitops.basesetup.repository.DirectPurchaseTaxDetailsRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.LMERepo;
import com.efitops.basesetup.repository.PurchaseOrderDeliveryScheduleShortCloseDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderDeliveryScheduleShortCloseRepo;
import com.efitops.basesetup.repository.PurchaseOrderImportDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderLocalDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderLocalFileUploadDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderLocalTaxDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class PurchaseServiceImportImpl implements PurchaseServiceImport {

	public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImportImpl.class);

	@Autowired
	private PurchaseOrderRepo purchaseOrderRepo;

	@Autowired
	private PurchaseOrderLocalDetailsRepo purchaseOrderLocalDetailsRepo;

	@Autowired
	private PurchaseOrderLocalTaxDetailsRepo purchaseOrderLocalTaxDetailsRepo;

	@Autowired
	private PurchaseOrderLocalFileUploadDetailsRepo purchaseOrderLocalFileUploadDetailsRepo;

	@Autowired
	private PurchaseOrderImportDetailsRepo purchaseOrderImportDetailsRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CurrencyRepo currencyRepo;

	@Autowired
	private LMERepo lmeRepo;

	@Autowired
	private ItemMasterRepo itemMasterRepo;

	@Autowired
	private UnitMasterRepo unitMasterRepo;

	@Autowired
	private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	PurchaseOrderDeliveryScheduleShortCloseRepo purchaseOrderDeliveryScheduleShortCloseRepo;

	@Autowired
	PurchaseOrderDeliveryScheduleShortCloseDetailsRepo purchaseOrderDeliveryScheduleShortCloseDetailsRepo;

	@Autowired
	private DirectPurchaseRepo directPurchaseRepo;

	@Autowired
	private DirectPurchaseCashDetailsRepo directPurchaseCashDetailsRepo;

	@Autowired
	private DirectPurchaseTaxDetailsRepo directPurchaseTaxDetailsRepo;

	@Autowired
	private DirectPurchaseFileUploadDetailsRepo directPurchaseFileUploadDetailsRepo;

	@Autowired
	GSTStateMasterRepo gstStateMasterRepo;

	@Autowired
	EmployeeMasterRepo employeeMasterRepo;

	@Override
	public PurchaseOrderResponseDTO getPurchaseOrderById(Long id, PoType type) throws ApplicationException {

		Integer typeValue = type == PoType.Local ? 1 : 0;

		PurchaseOrderVO orderAcceptanceVO = purchaseOrderRepo.getPurchaseOrderById(id, typeValue);

		if (orderAcceptanceVO == null) {
			throw new ApplicationException("Order Not Found");
		}

		return buildPurchaseOrderLocalResponse(orderAcceptanceVO);
	}

	@Override
	public List<PurchaseOrderResponseDTO> getPurchaseOrderByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<PurchaseOrderVO> quotationList = purchaseOrderRepo.getPurchaseOrderByOrgId(orgId, branch);

		if (quotationList == null || quotationList.isEmpty()) {
			throw new ApplicationException("Quotation Not Found");
		}

		List<PurchaseOrderResponseDTO> responseList = new ArrayList<>();

		for (PurchaseOrderVO orderAcceptanceVO : quotationList) {
			responseList.add(buildPurchaseOrderLocalResponse(orderAcceptanceVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, MultipartFile[] files)
			throws ApplicationException {

		PurchaseOrderVO purchaseOrderVO;
		String message;

		if (ObjectUtils.isNotEmpty(purchaseOrderDTO.getId())) {
			purchaseOrderVO = purchaseOrderRepo.findById(purchaseOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("Purchase Order Not Found"));
			purchaseOrderVO.setUpdatedBy(purchaseOrderDTO.getCreatedBy());
			message = "Purchase Order Updated Successfully";
		} else {
			purchaseOrderVO = new PurchaseOrderVO();

			if (purchaseOrderDTO.getPoType() != null && purchaseOrderDTO.getPoType().equalsIgnoreCase("Import")) {

				String screenCode = "POI";
				String docId = purchaseOrderRepo.getPurchaseOrderImportDocId(purchaseOrderDTO.getOrgId(),
						purchaseOrderDTO.getFinancialYear(), screenCode);
				purchaseOrderVO.setDocId(docId);

				DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
						.findByOrgIdAndFinYearAndScreenCode(purchaseOrderDTO.getOrgId(),
								purchaseOrderDTO.getFinancialYear(), screenCode);

				if (documentTypeMappingDetailsVO == null) {
					throw new ApplicationException("Document Type Mapping Details Not Found");
				}

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			} else {
				String screenCode = "POL";
				String docId = purchaseOrderRepo.getPurchaseOrderLocalDocId(purchaseOrderDTO.getOrgId(),
						purchaseOrderDTO.getFinancialYear(), screenCode);
				purchaseOrderVO.setDocId(docId);

				DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
						.findByOrgIdAndFinYearAndScreenCode(purchaseOrderDTO.getOrgId(),
								purchaseOrderDTO.getFinancialYear(), screenCode);

				if (documentTypeMappingDetailsVO == null) {
					throw new ApplicationException("Document Type Mapping Details Not Found");
				}

				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			purchaseOrderVO.setCreatedBy(purchaseOrderDTO.getCreatedBy());
			purchaseOrderVO.setUpdatedBy(purchaseOrderDTO.getCreatedBy());
			message = "Purchase Order Created Successfully";
		}

		setPurchaseOrderLocalValues(purchaseOrderDTO, purchaseOrderVO);

		purchaseOrderVO = purchaseOrderRepo.save(purchaseOrderVO);

		saveAttachments(files, purchaseOrderVO);

		PurchaseOrderResponseDTO purchaseOrderLocalResponse = buildPurchaseOrderLocalResponse(purchaseOrderVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("purchaseOrderVO", purchaseOrderLocalResponse);

		return response;
	}

	private void setPurchaseOrderLocalValues(PurchaseOrderDTO dto, PurchaseOrderVO vo) throws ApplicationException {
		vo.setOrderPlacedDate(dto.getOrderPlacedDate());

		vo.setPoType(PoType.valueOf(dto.getPoType()));
		vo.setBelongsTo(dto.getBelongsTo());
		vo.setIsIgstApplicable(dto.getIsIgstApplicable());

		if (dto.getDepartment() != null && dto.getDepartment() >= 0) {

			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));

			vo.setDepartment(department);
		}

		vo.setItemType(dto.getItemType());

		vo.setFreightType(dto.getFreightType());
		vo.setPackingType(dto.getPackingType());
		vo.setInsurance(dto.getInsurance());
		vo.setFreight(dto.getFreight());
		vo.setModeOfDespatch(dto.getModeOfDespatch());
		vo.setPaymentTerms(dto.getPaymentTerms());
		vo.setDeliveryTerms(dto.getDeliveryTerms());
		vo.setNotes(dto.getNotes());

		vo.setPreparedBy(dto.getPreparedBy());
		vo.setCheckedBy(dto.getCheckedBy());
		vo.setAuthorisedBy(dto.getAuthorisedBy());

		vo.setIsReverseCharge(dto.getIsReverseCharge());
		vo.setIndentRequired(dto.getIndentRequired());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());
		vo.setTermsAndConditions(dto.getTermsAndConditions());
		vo.setRemarks(dto.getRemarks());

		vo.setShipMode(dto.getShipMode());
		vo.setExchangeRate(dto.getExchangeRate());
		vo.setPaymentTerms(dto.getPaymentTerms());

		vo.setPortOfLoading(dto.getPortOfLoading());
		vo.setIncoterm(dto.getIncoterm());
		vo.setForeCloseNo(dto.getForeCloseNo());

		vo.setCountryOfOrigin(dto.getCountryOfOrigin());

		vo.setPortOfDischarge(dto.getPortOfDischarge());

		vo.setFreightFc(dto.getFreightFc());
		vo.setTotalPoValueFc(dto.getTotalPoValueFc());
		vo.setBankCharges(dto.getBankCharges());
		vo.setPackingCharges(dto.getPackingCharges());
		vo.setSurCharges(dto.getSurCharges());
		vo.setTotalPoValueInr(dto.getTotalPoValueInr());
		vo.setAmountInWord(dto.getAmountInWord());
		vo.setPreparedBy(dto.getPreparedBy());
		vo.setCheckedBy(dto.getCheckedBy());
		vo.setAuthorisedBy(dto.getAuthorisedBy());

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
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

		if (dto.getLmeRate() != null && dto.getLmeRate() > 0) {
			LMEVO lme = lmeRepo.findById(dto.getLmeRate())
					.orElseThrow(() -> new ApplicationException("LME Rate Not Found"));
			vo.setLmeRate(lme);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {

			List<PurchaseOrderLocalDetailsVO> oldDetails = purchaseOrderLocalDetailsRepo.findByPurchaseOrderVO(vo);

			if (oldDetails != null && !oldDetails.isEmpty()) {
				purchaseOrderLocalDetailsRepo.deleteAll(oldDetails);
			}

			List<PurchaseOrderLocalTaxDetailsVO> oldTaxDetails = purchaseOrderLocalTaxDetailsRepo
					.findByPurchaseOrderVO(vo);

			if (oldTaxDetails != null && !oldTaxDetails.isEmpty()) {

				purchaseOrderLocalTaxDetailsRepo.deleteAll(oldTaxDetails);
			}

			List<PurchaseOrderLocalFileUploadDetailsVO> oldFileDetails = purchaseOrderLocalFileUploadDetailsRepo
					.findByPurchaseOrderVO(vo);

			if (oldFileDetails != null && !oldFileDetails.isEmpty()) {

				purchaseOrderLocalFileUploadDetailsRepo.deleteAll(oldFileDetails);
			}
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {

			List<PurchaseOrderImportDetailsVO> oldDetails = purchaseOrderImportDetailsRepo.findByPurchaseOrderVO(vo);

			purchaseOrderImportDetailsRepo.deleteAll(oldDetails);

		}

		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal finalAmount = BigDecimal.ZERO;

		List<PurchaseOrderLocalDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getPurchaseOrderLocalDetailsDTO() != null) {

			for (PurchaseOrderLocalDetailsDTO detailDTO : dto.getPurchaseOrderLocalDetailsDTO()) {

				PurchaseOrderLocalDetailsVO detailVO = new PurchaseOrderLocalDetailsVO();

				detailVO.setPurchaseOrderVO(vo);

				detailVO.setIndentNo(detailDTO.getIndentNo());

				detailVO.setIndentDate(detailDTO.getIndentDate());

				detailVO.setCustomerPartNo(detailDTO.getCustomerPartNo());

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
				detailVO.setIndentQty(detailDTO.getIndentQty());

				BigDecimal indentQty = detailDTO.getIndentQty() != null ? detailDTO.getIndentQty() : BigDecimal.ZERO;

				BigDecimal poQtyInPurchaseUnit = indentQty;

				Set<Object[]> multipleFactor = purchaseOrderRepo.getMutipleFactorAmount(dto.getOrgId(),
						detailDTO.getPrimaryUnit(), detailDTO.getPurchaseUnit());

				for (Object[] ledger : multipleFactor) {

					if (ledger[0] != null) {

						BigDecimal factor = ledger[0] instanceof BigDecimal ? (BigDecimal) ledger[0]
								: BigDecimal.valueOf(((Number) ledger[0]).doubleValue());

						poQtyInPurchaseUnit = factor.multiply(indentQty);
					}

					break;
				}

				detailVO.setPoQtyInPurchaseUnit(poQtyInPurchaseUnit);

				detailVO.setQtyInPrimaryUnit(indentQty);

				BigDecimal rateInInr = detailDTO.getRateInInr() != null ? detailDTO.getRateInInr() : BigDecimal.ZERO;

				detailVO.setRateInInr(rateInInr);

				BigDecimal discount = detailDTO.getDiscount() != null ? detailDTO.getDiscount() : BigDecimal.ZERO;

				detailVO.setDiscount(discount);

				BigDecimal orderAmount = poQtyInPurchaseUnit.multiply(rateInInr);

				BigDecimal discountAmount = orderAmount.multiply(discount).divide(BigDecimal.valueOf(100));

				BigDecimal amount = orderAmount.subtract(discountAmount);

				detailVO.setDiscountAmount(discountAmount);

				detailVO.setAmountInInr(amount);

				totalAmount = totalAmount.add(amount);

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

				detailVO.setTaxPercentage(taxPercentage);

				detailVO.setIgstRate(igstRate);

				detailVO.setCgstRate(cgstRate);

				detailVO.setSgstRate(sgstRate);

				detailVO.setIgstAmount(igstAmount);

				detailVO.setCgstAmount(cgstAmount);

				detailVO.setSgstAmount(sgstAmount);

				detailVO.setHsnCode(detailDTO.getHsnCode());

				detailVO.setTaxType(detailDTO.getTaxType());

				detailVO.setDeliveryDate(detailDTO.getDeliveryDate());

				BigDecimal taxAmount = igstAmount.add(cgstAmount).add(sgstAmount);

				BigDecimal finalAmounts = amount.add(taxAmount);

				finalAmount = finalAmount.add(finalAmounts);

				itemDetailsList.add(detailVO);
			}
		}

		vo.setPurchaseOrderLocalDetailsVO(itemDetailsList);

		List<PurchaseOrderLocalTaxDetailsVO> taxList = new ArrayList<>();

		if (dto.getPurchaseOrderLocalTaxDetailsDTO() != null) {

			for (PurchaseOrderLocalTaxDetailsDTO taxDTO : dto.getPurchaseOrderLocalTaxDetailsDTO()) {

				PurchaseOrderLocalTaxDetailsVO taxVO = new PurchaseOrderLocalTaxDetailsVO();

				taxVO.setParticulars(taxDTO.getParticulars());

				taxVO.setTax(taxDTO.getTax());

				taxVO.setAmount(taxDTO.getAmount());

				taxVO.setPurchaseOrderVO(vo);

				taxList.add(taxVO);
			}
		}

		vo.setPurchaseOrderLocalTaxDetailsVO(taxList);

		List<PurchaseOrderLocalFileUploadDetailsVO> fileUploadList = new ArrayList<>();

		if (dto.getPurchaseOrderLocalFileUploadDetailsDTO() != null) {

			for (PurchaseOrderLocalFileUploadDetailsDTO fileDTO : dto.getPurchaseOrderLocalFileUploadDetailsDTO()) {

				PurchaseOrderLocalFileUploadDetailsVO fileVO = new PurchaseOrderLocalFileUploadDetailsVO();

				fileVO.setPurchaseOrderVO(vo);

				fileVO.setName(fileDTO.getName());

				fileUploadList.add(fileVO);
			}
		}

		vo.setTotalAmount(finalAmount);
		vo.setAmountInWord(amountInWordsConverterService.convert(vo.getTotalAmount()));

		BigDecimal totalAmounValueFobInFc = BigDecimal.ZERO;

		List<PurchaseOrderImportDetailsVO> itemDetailsLists = new ArrayList<>();

		if (dto.getPurchaseOrderImportDetailsDTO() != null) {

			for (PurchaseOrderImportDetailsDTO detailDTO : dto.getPurchaseOrderImportDetailsDTO()) {

				PurchaseOrderImportDetailsVO detailVO = new PurchaseOrderImportDetailsVO();

				detailVO.setIndentNo(detailDTO.getIndentNo());

				detailVO.setIndentDate(detailDTO.getIndentDate());

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);
				}

				if (detailDTO.getUom() != null && detailDTO.getUom() != 0) {

					UnitMasterVO purchaseUnit = unitMasterRepo.findById(detailDTO.getUom())
							.orElseThrow(() -> new ApplicationException("Uom  Not Found"));

					detailVO.setUom(purchaseUnit);
				}

				detailVO.setIndentQty(detailDTO.getIndentQty());

				detailVO.setPoQty(detailVO.getIndentQty());

				detailVO.setOrderRate(detailDTO.getOrderRate());

//				detailVO.setFobRateFc(detailDTO.getOrderRate().divide(dto.getExchangeRate()));
				detailVO.setFobRateFc(detailDTO.getOrderRate().divide(dto.getExchangeRate(), 4, RoundingMode.HALF_UP));

				BigDecimal poQty = detailVO.getPoQty() != null ? detailVO.getPoQty() : BigDecimal.ZERO;

				BigDecimal amountInFb = detailDTO.getFobRateFc() != null ? detailDTO.getFobRateFc() : BigDecimal.ZERO;

				BigDecimal amountInFbValue = poQty.multiply(amountInFb);

				detailVO.setFobValueFc(amountInFbValue);

				totalAmounValueFobInFc = totalAmounValueFobInFc.add(detailVO.getFobValueFc());
				detailVO.setFobRateInr(detailDTO.getOrderRate());

				detailVO.setFobValueInr(detailDTO.getFobRateFc().multiply(dto.getExchangeRate()));

				detailVO.setFobValueInr(detailVO.getPoQty().multiply(detailVO.getFobRateInr()));

				detailVO.setHsnCode(detailDTO.getHsnCode());

				detailVO.setPurchaseOrderVO(vo);

				itemDetailsLists.add(detailVO);
			}
		}

		vo.setPurchaseOrderImportDetailsVO(itemDetailsLists);
	}

	private PurchaseOrderResponseDTO buildPurchaseOrderLocalResponse(PurchaseOrderVO vo) {

		PurchaseOrderResponseDTO responseDTO = new PurchaseOrderResponseDTO();
		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setOrderPlacedDate(vo.getOrderPlacedDate());
		responseDTO.setPoType(vo.getPoType() != null ? vo.getPoType().name() : null);
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setIsIgstApplicable(vo.getIsIgstApplicable());
		responseDTO.setIsReverseCharge(vo.getIsReverseCharge());

		responseDTO.setItemType(vo.getItemType());
		responseDTO.setFreightType(vo.getFreightType());
		responseDTO.setPackingType(vo.getPackingType());
		responseDTO.setInsurance(vo.getInsurance());
		responseDTO.setFreight(vo.getFreight());
		responseDTO.setModeOfDespatch(vo.getModeOfDespatch());
		responseDTO.setPaymentTerms(vo.getPaymentTerms());
		responseDTO.setDeliveryTerms(vo.getDeliveryTerms());
		responseDTO.setNotes(vo.getNotes());
		responseDTO.setPreparedBy(vo.getPreparedBy());
		responseDTO.setCheckedBy(vo.getCheckedBy());
		responseDTO.setAuthorisedBy(vo.getAuthorisedBy());
		responseDTO.setTotalAmount(vo.getTotalAmount());
		responseDTO.setAmountInWord(vo.getAmountInWord());

		if (vo.getDepartment() != null) {
			DepartmentResponseDTO deptDTO = new DepartmentResponseDTO();
			deptDTO.setId(vo.getDepartment().getId());

			deptDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());
			deptDTO.setDepartmentName(vo.getDepartment().getDepartmentName());
			responseDTO.setDepartment(deptDTO);
		}

		responseDTO.setIndentRequired(vo.getIndentRequired());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());
		responseDTO.setTermsAndConditions(vo.getTermsAndConditions());
		responseDTO.setRemarks(vo.getRemarks());

		if (vo.getCurrency() != null) {
			CurrencyResponseDTO currencyDTO = new CurrencyResponseDTO();
			currencyDTO.setId(vo.getCurrency().getId());
			currencyDTO.setCurrencyName(vo.getCurrency().getCurrency());
			responseDTO.setCurrency(currencyDTO);
		}

		if (vo.getLmeRate() != null) {
			LmeResponseDTO lmeDTO = new LmeResponseDTO();
			lmeDTO.setId(vo.getLmeRate().getId());
			lmeDTO.setLmeName(vo.getLmeRate().getLmeRate());
			responseDTO.setLmeRate(lmeDTO);
		}

		responseDTO.setShipMode(vo.getShipMode());
		responseDTO.setExchangeRate(vo.getExchangeRate());
		responseDTO.setPaymentTerms(vo.getPaymentTerms());
		responseDTO.setPortOfLoading(vo.getPortOfLoading());
		responseDTO.setIncoterm(vo.getIncoterm());
		responseDTO.setForeCloseNo(vo.getForeCloseNo());
		responseDTO.setCountryOfOrigin(vo.getCountryOfOrigin());
		responseDTO.setPortOfDischarge(vo.getPortOfDischarge());

		responseDTO.setTotalFobValueFc(vo.getTotalFobValueFc());
		responseDTO.setTotalFobValueInr(vo.getTotalFobValueInr());
		responseDTO.setFreightFc(vo.getFreightFc());
		responseDTO.setFreightInr(vo.getFreightInr());
		responseDTO.setInsuranceFc(vo.getInsuranceFc());
		responseDTO.setInsuranceInr(vo.getInsuranceInr());
		responseDTO.setOtherChargesFc(vo.getOtherChargesFc());
		responseDTO.setOtherChargesInr(vo.getOtherChargesInr());
		responseDTO.setTotalPoValueFc(vo.getTotalPoValueFc());
		responseDTO.setBankCharges(vo.getBankCharges());
		responseDTO.setPackingCharges(vo.getPackingCharges());
		responseDTO.setSurCharges(vo.getSurCharges());
		responseDTO.setTotalPoValueInr(vo.getTotalPoValueInr());
		responseDTO.setAmountInWord(vo.getAmountInWord());

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		if (vo.getSupplierCode() != null) {
			SupplierResponseDTO supplierDTO = new SupplierResponseDTO();
			supplierDTO.setId(vo.getSupplierCode().getId());
			supplierDTO.setSupplierName(vo.getSupplierCode().getCustomerName());
			supplierDTO.setSupplierCode(vo.getSupplierCode().getCustomerCode());
			supplierDTO.setAddress(vo.getSupplierCode().getAddress());
//	        supplierDTO.setSupplierRefNo(vo.getSupplierCode().getSu());
//	        supplierDTO.setSupplierRefDate(vo.getSupplierCode().getSupplierRefDate() != null ? 
//	                vo.getSupplierCode().getSupplierRefDate().toString() : null);
			supplierDTO.setGstNo(vo.getSupplierCode().getGstNo());
			supplierDTO.setGstApproval(vo.getSupplierCode().isGstApplicable() ? "Yes" : "No");
			supplierDTO.setGstSate(vo.getSupplierCode().getGstState().getStateName());

			responseDTO.setSupplierCode(supplierDTO);
		}

		List<PurchaseOrderLocalDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vo.getPurchaseOrderLocalDetailsVO() != null && !vo.getPurchaseOrderLocalDetailsVO().isEmpty()) {

			for (PurchaseOrderLocalDetailsVO detailVO : vo.getPurchaseOrderLocalDetailsVO()) {

				PurchaseOrderLocalDetailsResponseDTO detailResponse = new PurchaseOrderLocalDetailsResponseDTO();

				detailResponse.setId(detailVO.getId());
				detailResponse.setIndentNo(detailVO.getIndentNo());
				detailResponse.setIndentDate(detailVO.getIndentDate());
//				detailResponse.setCustomerPartNo(detailVO.getCustomerPartNo());
				detailResponse.setIndentQty(detailVO.getIndentQty());
				detailResponse.setPoQtyInPurchaseUnit(detailVO.getPoQtyInPurchaseUnit());
				detailResponse.setQtyInPrimaryUnit(detailVO.getQtyInPrimaryUnit());
				detailResponse.setRateInInr(detailVO.getRateInInr());
				detailResponse.setDiscount(detailVO.getDiscount());
				detailResponse.setDiscountAmount(detailVO.getDiscountAmount());
				detailResponse.setAmountInInr(detailVO.getAmountInInr());
				detailResponse.setDeliveryDate(detailVO.getDeliveryDate());
				detailResponse.setTaxPercentage(detailVO.getTaxPercentage());
//				detailResponse.setHsnCode(detailVO.getHsnCode());
				detailResponse.setTaxType(detailVO.getTaxType());
				detailResponse.setSgstRate(detailVO.getSgstRate());
				detailResponse.setSgstAmount(detailVO.getSgstAmount());
				detailResponse.setCgstRate(detailVO.getCgstRate());
				detailResponse.setCgstAmount(detailVO.getCgstAmount());
				detailResponse.setIgstRate(detailVO.getIgstRate());
				detailResponse.setIgstAmount(detailVO.getIgstAmount());

				if (detailVO.getItem() != null) {
					ItemMasterDetailsResponseDTO itemDTO = new ItemMasterDetailsResponseDTO();
					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());
					itemDTO.setHsnCode(detailVO.getItem().getHsnCode().getHsn());
					itemDTO.setCustomerPoNo(detailVO.getItem().getCustomerPartNo());
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
				detailsResponseList.add(detailResponse);
			}
		}

		responseDTO.setPurchaseOrderLocalDetailsResponseDTO(detailsResponseList);

		List<PurchaseOrderLocalTaxDetailsResponseDTO> taxResponseList = new ArrayList<>();

		if (vo.getPurchaseOrderLocalTaxDetailsVO() != null && !vo.getPurchaseOrderLocalTaxDetailsVO().isEmpty()) {

			for (PurchaseOrderLocalTaxDetailsVO taxVO : vo.getPurchaseOrderLocalTaxDetailsVO()) {

				PurchaseOrderLocalTaxDetailsResponseDTO taxResponse = new PurchaseOrderLocalTaxDetailsResponseDTO();

				taxResponse.setId(taxVO.getId());
				taxResponse.setParticulars(taxVO.getParticulars());
				taxResponse.setTax(taxVO.getTax());
				taxResponse.setAmount(taxVO.getAmount());

				taxResponseList.add(taxResponse);
			}
		}

		responseDTO.setPurchaseOrderLocalTaxDetailsResponseDTO(taxResponseList);

		List<PurchaseOrderLocalFileUploadDetailsResponseDTO> fileResponseList = new ArrayList<>();

		if (vo.getPurchaseOrderLocalFileUploadDetailsVO() != null
				&& !vo.getPurchaseOrderLocalFileUploadDetailsVO().isEmpty()) {

			for (PurchaseOrderLocalFileUploadDetailsVO fileVO : vo.getPurchaseOrderLocalFileUploadDetailsVO()) {

				PurchaseOrderLocalFileUploadDetailsResponseDTO fileResponse = new PurchaseOrderLocalFileUploadDetailsResponseDTO();

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

		responseDTO.setPurchaseOrderLocalFileUploadDetailsResponseDTO(fileResponseList);

		List<PurchaseOrderImportDetailsResponseDTO> importDetailsList = new ArrayList<>();

		if (vo.getPurchaseOrderImportDetailsVO() != null && !vo.getPurchaseOrderImportDetailsVO().isEmpty()) {

			for (PurchaseOrderImportDetailsVO importVO : vo.getPurchaseOrderImportDetailsVO()) {

				PurchaseOrderImportDetailsResponseDTO importResponse = new PurchaseOrderImportDetailsResponseDTO();

				importResponse.setId(importVO.getId());
				importResponse.setIndentNo(importVO.getIndentNo());
				importResponse.setIndentDate(importVO.getIndentDate());
				importResponse.setIndentQty(importVO.getIndentQty());
				importResponse.setPoQty(importVO.getPoQty());
				importResponse.setFobRateFc(importVO.getFobRateFc());
				importResponse.setFobValueFc(importVO.getFobValueFc());
				importResponse.setFobRateInr(importVO.getFobRateInr());
				importResponse.setOrderRate(importVO.getOrderRate());
				importResponse.setFobValueInr(importVO.getFobValueInr());
				importResponse.setHsnCode(importVO.getHsnCode());

				if (importVO.getItem() != null) {
					ItemMasterDetailsResponseDTO itemDTO = new ItemMasterDetailsResponseDTO();
					itemDTO.setId(importVO.getItem().getId());
					itemDTO.setItemCode(importVO.getItem().getItemCode());
					itemDTO.setItemDescription(importVO.getItem().getItemDescription());
					importResponse.setItem(itemDTO);
				}

				if (importVO.getUom() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(importVO.getUom().getId());
					unitDTO.setUnitId(importVO.getUom().getUnitId());
					importResponse.setUom(unitDTO);
				}

				importDetailsList.add(importResponse);
			}
		}

		responseDTO.setPurchaseOrderImportDetailsResponseDTO(importDetailsList);

		return responseDTO;

	}

	@Value("${purchase.order.upload.path}")
	private String uploadPath;

	private void saveAttachments(MultipartFile[] files, PurchaseOrderVO purchaseOrderVO) throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			Path purchaseOrderFolder = Paths.get(uploadPath, "purchaseOrder", purchaseOrderVO.getId().toString());

			createDirectory(purchaseOrderFolder);
			if (ObjectUtils.isNotEmpty(purchaseOrderVO.getId())) {

				List<PurchaseOrderLocalFileUploadDetailsVO> existingAttachments = purchaseOrderLocalFileUploadDetailsRepo
						.findByPurchaseOrderVO(purchaseOrderVO);

				if (existingAttachments != null && !existingAttachments.isEmpty()) {

					purchaseOrderLocalFileUploadDetailsRepo.deleteAll(existingAttachments);
				}
			}

			List<PurchaseOrderLocalFileUploadDetailsVO> attachmentList = new ArrayList<>();

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

				String fileName = originalName + "_" + purchaseOrderVO.getId() + extension;

				Path filePath = purchaseOrderFolder.resolve(fileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/purchaseOrder/viewFile/").toUriString();

				String relativePath = uploadPath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				String publicUrl = baseUrl + relativePath;

				PurchaseOrderLocalFileUploadDetailsVO attachment = new PurchaseOrderLocalFileUploadDetailsVO();

				attachment.setPurchaseOrderVO(purchaseOrderVO);

				attachment.setName(file.getOriginalFilename());

				attachment.setFileName(fileName);

				attachment.setFilePath(publicUrl);

				attachment.setFileSize(file.getSize());

				attachment.setContentType(file.getContentType());

				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			if (!attachmentList.isEmpty()) {

				List<PurchaseOrderLocalFileUploadDetailsVO> saved = purchaseOrderLocalFileUploadDetailsRepo
						.saveAll(attachmentList);

				purchaseOrderVO.setPurchaseOrderLocalFileUploadDetailsVO(saved);
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
	public ResponseEntity<byte[]> viewPurchaseOrderFile(HttpServletRequest request) throws IOException {

		return serveFile(request, "/api/purchaseOrder/viewFile/", uploadPath);
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

		// Security check
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
	public List<Map<String, Object>> getItemDetailsResponsePurchaseLocal(Long orgId, Long branch) {
		Set<Object[]> chType = purchaseOrderRepo.getItemDetailsResponsePurchaseLocal(orgId, branch);
		return getItemDetailsResponsePurchaseLocal(chType);
	}

	private List<Map<String, Object>> getItemDetailsResponsePurchaseLocal(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("unitId", ch[3] != null ? ch[3].toString() : "");
			map.put("hsn", ch[4] != null ? ch[4].toString() : "");
			map.put("customerPartNo", ch[5] != null ? ch[5].toString() : "");
			map.put("primaryUnit", ch[6] != null ? ((Number) ch[6]).longValue() : null);
			map.put("purchaseUnit", ch[7] != null ? ((Number) ch[7]).longValue() : null);

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsResponsePurchaseImport(Long orgId, Long branch) {
		Set<Object[]> chType = purchaseOrderRepo.getItemDetailsResponsePurchaseImport(orgId, branch);
		return getItemDetailsResponsePurchaseImport(chType);
	}

	private List<Map<String, Object>> getItemDetailsResponsePurchaseImport(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("unitId", ch[3] != null ? ch[3].toString() : "");
			map.put("hsn", ch[4] != null ? ch[4].toString() : "");
			map.put("customerPartNo", ch[5] != null ? ch[5].toString() : "");
			map.put("uom", ch[6] != null ? ((Number) ch[6]).longValue() : null);

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSupplierDetails(Long orgId, Long branch) {
		Set<Object[]> chType = purchaseOrderRepo.getSupplierDetails(orgId, branch);
		return getSupplierDetails(chType);
	}

	private List<Map<String, Object>> getSupplierDetails(Set<Object[]> chType) {

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
			map.put("stateCode", ch[8] != null ? ch[8].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public String getPurchaseOrderDocId(Long orgId, String financialYear, String screenCode, PoType type) {

		if (type == PoType.Import) {
			String screenCode1 = "POI";
			return purchaseOrderRepo.getPurchaseOrderImportDocId(orgId, financialYear, screenCode1);
		} else if (type == PoType.Local) {
			String screenCode1 = "POL";
			return purchaseOrderRepo.getPurchaseOrderLocalDocId(orgId, financialYear, screenCode1);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getExchangeRateDetails(Long orgId, Long branch, Long currency) {
		Set<Object[]> chType = purchaseOrderRepo.getExchangeRateDetails(orgId, branch, currency);
		return getExchangeRateDetails(chType);
	}

	private List<Map<String, Object>> getExchangeRateDetails(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("exchangeRate", ch[0] != null ? BigDecimal.valueOf(((Number) ch[0]).doubleValue()) : null);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getMutipleFactorAmount(Long orgId, Long primaryUnit, Long purchaseUnit) {
		Set<Object[]> chType = purchaseOrderRepo.getMutipleFactorAmount(orgId, primaryUnit, purchaseUnit);
		return getMutipleFactorAmount(chType);
	}

	private List<Map<String, Object>> getMutipleFactorAmount(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("poRate", ch[0] != null ? BigDecimal.valueOf(((Number) ch[0]).doubleValue()) : null);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getIndentNoBasedLocal(Long orgId, String belongsTo, String type) {
		Set<Object[]> chType = purchaseOrderRepo.getIndentNoBasedLocal(orgId, belongsTo, type);
		return getIndentNoBasedLocal(chType);
	}

	private List<Map<String, Object>> getIndentNoBasedLocal(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();

			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("indentBasicId", ch[1] != null ? ((Number) ch[1]).longValue() : null);

			map.put("docDate", ch[2] != null ? ch[2].toString() : "");

			map.put("itemId", ch[3] != null ? ch[3].toString() : "");

			map.put("itemDesc", ch[4] != null ? ch[4].toString() : "");

			map.put("unitId", ch[5] != null ? ch[5].toString() : "");

			map.put("qtyInPurchaseUnit", ch[6] != null ? new BigDecimal(ch[6].toString()) : BigDecimal.ZERO);

			map.put("pndQty", ch[7] != null ? new BigDecimal(ch[7].toString()) : BigDecimal.ZERO);

			map.put("qtyInPrimaryUnit", ch[8] != null ? new BigDecimal(ch[8].toString()) : BigDecimal.ZERO);

			map.put("requiredDate", ch[9] != null ? ch[9].toString() : "");

			map.put("indentDetailId", ch[10] != null ? ((Number) ch[10]).longValue() : null);

			map.put("itemMasterId", ch[11] != null ? ((Number) ch[11]).longValue() : null);

			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getIndentNoBasedImport(Long orgId, String type) {

		Set<Object[]> chType = purchaseOrderRepo.getIndentNoBasedImport(orgId, type);

		return getIndentNoBasedImport(chType);
	}

	private List<Map<String, Object>> getIndentNoBasedImport(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("docId", ch[1] != null ? ch[1].toString() : "");

			map.put("indentBasicId", ch[0] != null ? ((Number) ch[0]).longValue() : null);

			map.put("docDate", ch[2] != null ? ch[2].toString() : "");

			map.put("itemId", ch[3] != null ? ch[3].toString() : "");

			map.put("itemDesc", ch[4] != null ? ch[4].toString() : "");

			map.put("unitId", ch[5] != null ? ch[5].toString() : "");

			map.put("qtyInPurchaseUnit", ch[6] != null ? new BigDecimal(ch[6].toString()) : BigDecimal.ZERO);

			map.put("pndQty", ch[7] != null ? new BigDecimal(ch[7].toString()) : BigDecimal.ZERO);

			map.put("qtyInPrimaryUnit", ch[8] != null ? new BigDecimal(ch[8].toString()) : BigDecimal.ZERO);

			map.put("requiredDate", ch[9] != null ? ch[9].toString() : "");

			map.put("indentDetailId", ch[10] != null ? ((Number) ch[10]).longValue() : null);

			map.put("itemMasterId", ch[11] != null ? ((Number) ch[11]).longValue() : null);

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getHsnCodeDetails(Long orgId, Long branch, Long item, String type) {
		Set<Object[]> chType = purchaseOrderRepo.getHsnCodeDetails(orgId, branch, item, type);
		return getHsnCodeDetails(chType);
	}

	private List<Map<String, Object>> getHsnCodeDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("hsn", ch[0] != null ? ch[0].toString() : "");
			map.put("customerPartNo", ch[1] != null ? ch[1].toString() : "");
			list.add(map);
		}

		return list;
	}

	// PurchaseorderDelivaryScheduleshortCose

	@Override
	public PurchaseOrderDeliveryScheduleShortCloseResponseDTO getPurchaseOrderDeliveryScheduleShortCloseById(Long id)
			throws ApplicationException {

		PurchaseOrderDeliveryScheduleShortCloseVO purchaseOrderDeliveryScheduleShortCloseVO = purchaseOrderDeliveryScheduleShortCloseRepo
				.getPurchaseOrderDeliveryScheduleShortCloseById(id);

		if (purchaseOrderDeliveryScheduleShortCloseVO == null) {
			throw new ApplicationException("ShortClose  Not Found");
		}

		return buildProformaInvoiceResponse(purchaseOrderDeliveryScheduleShortCloseVO);
	}

	@Override
	public List<PurchaseOrderDeliveryScheduleShortCloseResponseDTO> getPurchaseOrderDeliveryScheduleShortCloseByOrgId(
			Long orgId, Long branch) throws ApplicationException {

		List<PurchaseOrderDeliveryScheduleShortCloseVO> quotationList = purchaseOrderDeliveryScheduleShortCloseRepo
				.getPurchaseOrderDeliveryScheduleShortCloseByOrgId(orgId, branch);

		if (quotationList == null || quotationList.isEmpty()) {
			throw new ApplicationException("Proforma Invoice Not Found");
		}

		List<PurchaseOrderDeliveryScheduleShortCloseResponseDTO> responseList = new ArrayList<>();

		for (PurchaseOrderDeliveryScheduleShortCloseVO purchaseOrderDeliveryScheduleShortCloseVO : quotationList) {
			responseList.add(buildProformaInvoiceResponse(purchaseOrderDeliveryScheduleShortCloseVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdatePurchaseOrderDeliveryScheduleShortClose(
			PurchaseOrderDeliveryScheduleShortCloseDTO purchaseOrderDeliveryScheduleShortCloseDTO)
			throws ApplicationException {
		String screenCode = "PODSSC";
		PurchaseOrderDeliveryScheduleShortCloseVO purchaseOrderDeliveryScheduleShortCloseVO = new PurchaseOrderDeliveryScheduleShortCloseVO();
		String message;

		if (ObjectUtils.isNotEmpty(purchaseOrderDeliveryScheduleShortCloseDTO.getId())) {

			purchaseOrderDeliveryScheduleShortCloseVO = purchaseOrderDeliveryScheduleShortCloseRepo
					.findById(purchaseOrderDeliveryScheduleShortCloseDTO.getId())
					.orElseThrow(() -> new ApplicationException("ShortClose Invoice Not Found"));

			purchaseOrderDeliveryScheduleShortCloseVO
					.setUpdatedBy(purchaseOrderDeliveryScheduleShortCloseDTO.getCreatedBy());

			message = "ShortClose Invoice Updated Successfully";

		} else {

			String docId = purchaseOrderDeliveryScheduleShortCloseRepo.getPurchaseOrderDeliveryScheduleShortCloseDocId(
					purchaseOrderDeliveryScheduleShortCloseDTO.getOrgId(),
					purchaseOrderDeliveryScheduleShortCloseDTO.getFinancialYear(), screenCode);

			purchaseOrderDeliveryScheduleShortCloseVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(purchaseOrderDeliveryScheduleShortCloseDTO.getOrgId(),
							purchaseOrderDeliveryScheduleShortCloseDTO.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			purchaseOrderDeliveryScheduleShortCloseVO
					.setCreatedBy(purchaseOrderDeliveryScheduleShortCloseDTO.getCreatedBy());
			purchaseOrderDeliveryScheduleShortCloseVO
					.setUpdatedBy(purchaseOrderDeliveryScheduleShortCloseDTO.getCreatedBy());

			message = "Proforma Invoice Created Successfully";
		}

		createUpdateResponse(purchaseOrderDeliveryScheduleShortCloseDTO, purchaseOrderDeliveryScheduleShortCloseVO);

		purchaseOrderDeliveryScheduleShortCloseVO = purchaseOrderDeliveryScheduleShortCloseRepo
				.save(purchaseOrderDeliveryScheduleShortCloseVO);

		PurchaseOrderDeliveryScheduleShortCloseResponseDTO responseDTO = buildProformaInvoiceResponse(
				purchaseOrderDeliveryScheduleShortCloseVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("purchaseOrderDeliveryScheduleShortCloseVO", responseDTO);

		return response;
	}

	private void createUpdateResponse(
			PurchaseOrderDeliveryScheduleShortCloseDTO purchaseOrderDeliveryScheduleShortCloseDTO,
			PurchaseOrderDeliveryScheduleShortCloseVO purchaseOrderDeliveryScheduleShortCloseVO)
			throws ApplicationException {

		purchaseOrderDeliveryScheduleShortCloseVO.setActive(purchaseOrderDeliveryScheduleShortCloseDTO.isActive());
		purchaseOrderDeliveryScheduleShortCloseVO
				.setCancelRemarks(purchaseOrderDeliveryScheduleShortCloseDTO.getCancelRemarks());
		purchaseOrderDeliveryScheduleShortCloseVO.setOrgId(purchaseOrderDeliveryScheduleShortCloseDTO.getOrgId());
		purchaseOrderDeliveryScheduleShortCloseVO
				.setFinancialYear(purchaseOrderDeliveryScheduleShortCloseDTO.getFinancialYear());
		purchaseOrderDeliveryScheduleShortCloseVO
				.setNarration(purchaseOrderDeliveryScheduleShortCloseDTO.getNarration());
		purchaseOrderDeliveryScheduleShortCloseVO
				.setBelongsTo(purchaseOrderDeliveryScheduleShortCloseDTO.getBelongsTo());

		if (purchaseOrderDeliveryScheduleShortCloseDTO.getSupplierCode() != null
				&& purchaseOrderDeliveryScheduleShortCloseDTO.getSupplierCode() != 0) {

			CustomerVO customer = customerRepo.findById(purchaseOrderDeliveryScheduleShortCloseDTO.getSupplierCode())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			purchaseOrderDeliveryScheduleShortCloseVO.setSupplierCode(customer);
		}

		purchaseOrderDeliveryScheduleShortCloseVO.setType(purchaseOrderDeliveryScheduleShortCloseDTO.getType());

		purchaseOrderDeliveryScheduleShortCloseVO
				.setPurchaseOrderScheduleNo(purchaseOrderDeliveryScheduleShortCloseDTO.getPurchaseOrderScheduleNo());

		purchaseOrderDeliveryScheduleShortCloseVO
				.setReferenceForShortClose(purchaseOrderDeliveryScheduleShortCloseDTO.getReferenceForShortClose());

		if (purchaseOrderDeliveryScheduleShortCloseDTO.getBranch() != null
				&& purchaseOrderDeliveryScheduleShortCloseDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(purchaseOrderDeliveryScheduleShortCloseDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			purchaseOrderDeliveryScheduleShortCloseVO.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(purchaseOrderDeliveryScheduleShortCloseVO.getId())) {

			List<PurchaseOrderDeliveryScheduleShortCloseDetailsVO> purchaseOrderDeliveryScheduleShortCloseDetailsVO = purchaseOrderDeliveryScheduleShortCloseDetailsRepo
					.findByPurchaseOrderDeliveryScheduleShortCloseVO(purchaseOrderDeliveryScheduleShortCloseVO);
			purchaseOrderDeliveryScheduleShortCloseDetailsRepo
					.deleteAll(purchaseOrderDeliveryScheduleShortCloseDetailsVO);

		}

		List<PurchaseOrderDeliveryScheduleShortCloseDetailsVO> itemDetailsList = new ArrayList<>();

		if (purchaseOrderDeliveryScheduleShortCloseDTO.getPurchaseOrderDeliveryScheduleShortCloseDetailsDTO() != null) {

			for (PurchaseOrderDeliveryScheduleShortCloseDetailsDTO dto : purchaseOrderDeliveryScheduleShortCloseDTO
					.getPurchaseOrderDeliveryScheduleShortCloseDetailsDTO()) {

				PurchaseOrderDeliveryScheduleShortCloseDetailsVO detailsVO = new PurchaseOrderDeliveryScheduleShortCloseDetailsVO();

				if (dto.getItem() != null && dto.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(item);
				}

				if (dto.getUnit() != null && dto.getUnit() != 0) {

					UnitMasterVO item = unitMasterRepo.findById(dto.getUnit())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setUnit(item);
				}

				detailsVO.setOrderedQty(dto.getOrderedQty());

				detailsVO.setSuppliedQty(dto.getSuppliedQty());

				detailsVO.setPendingQty(dto.getPendingQty());

				detailsVO.setShortCloseQty(dto.getShortCloseQty());

				detailsVO.setNewRequiredQty(
						dto.getSuppliedQty().subtract(dto.getPendingQty()).subtract(dto.getShortCloseQty()));

				detailsVO.setPurchaseOrderDeliveryScheduleShortCloseVO(purchaseOrderDeliveryScheduleShortCloseVO);

				itemDetailsList.add(detailsVO);
			}
		}

		purchaseOrderDeliveryScheduleShortCloseVO.setPurchaseOrderDeliveryScheduleShortCloseDetailsVO(itemDetailsList);

	}

	private PurchaseOrderDeliveryScheduleShortCloseResponseDTO buildProformaInvoiceResponse(
			PurchaseOrderDeliveryScheduleShortCloseVO purchaseOrderDeliveryScheduleShortCloseVO) {

		PurchaseOrderDeliveryScheduleShortCloseResponseDTO responseDTO = new PurchaseOrderDeliveryScheduleShortCloseResponseDTO();
		responseDTO.setId(purchaseOrderDeliveryScheduleShortCloseVO.getId());
		responseDTO.setDocId(purchaseOrderDeliveryScheduleShortCloseVO.getDocId());
		responseDTO.setDocDate(purchaseOrderDeliveryScheduleShortCloseVO.getDocDate());
		responseDTO.setBelongsTo(purchaseOrderDeliveryScheduleShortCloseVO.getBelongsTo());
		responseDTO.setType(purchaseOrderDeliveryScheduleShortCloseVO.getType());
		responseDTO.setPurchaseOrderScheduleNo(purchaseOrderDeliveryScheduleShortCloseVO.getPurchaseOrderScheduleNo());
		responseDTO.setReferenceForShortClose(purchaseOrderDeliveryScheduleShortCloseVO.getReferenceForShortClose());
		responseDTO.setCreatedBy(purchaseOrderDeliveryScheduleShortCloseVO.getCreatedBy());
		responseDTO.setNarration(purchaseOrderDeliveryScheduleShortCloseVO.getNarration());
		responseDTO.setActive(purchaseOrderDeliveryScheduleShortCloseVO.getActive());
		responseDTO.setCancel(purchaseOrderDeliveryScheduleShortCloseVO.getCancel());
		responseDTO.setUpdatedBy(purchaseOrderDeliveryScheduleShortCloseVO.getUpdatedBy());
		responseDTO.setCancelRemarks(purchaseOrderDeliveryScheduleShortCloseVO.getCancelRemarks());
		responseDTO.setScreenName(purchaseOrderDeliveryScheduleShortCloseVO.getScreenName());
		responseDTO.setScreenCode(purchaseOrderDeliveryScheduleShortCloseVO.getScreenCode());
		responseDTO.setOrgId(purchaseOrderDeliveryScheduleShortCloseVO.getOrgId());
		responseDTO.setFinancialYear(purchaseOrderDeliveryScheduleShortCloseVO.getFinancialYear());

		if (purchaseOrderDeliveryScheduleShortCloseVO.getSupplierCode() != null) {
			SupplierResponseDTO supplierDTO = new SupplierResponseDTO();
			supplierDTO.setId(purchaseOrderDeliveryScheduleShortCloseVO.getSupplierCode().getId());
			supplierDTO.setSupplierCode(purchaseOrderDeliveryScheduleShortCloseVO.getSupplierCode().getCustomerCode());
			supplierDTO.setSupplierName(purchaseOrderDeliveryScheduleShortCloseVO.getSupplierCode().getCustomerName());
			responseDTO.setSupplierCode(supplierDTO);
		}

		if (purchaseOrderDeliveryScheduleShortCloseVO.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(purchaseOrderDeliveryScheduleShortCloseVO.getBranch().getId());
			branchDTO.setBranchCode(purchaseOrderDeliveryScheduleShortCloseVO.getBranch().getBranchCode());
			branchDTO.setBranchName(purchaseOrderDeliveryScheduleShortCloseVO.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		// Set details list
		List<PurchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO> detailsList = new ArrayList<>();
		if (purchaseOrderDeliveryScheduleShortCloseVO.getPurchaseOrderDeliveryScheduleShortCloseDetailsVO() != null) {
			for (PurchaseOrderDeliveryScheduleShortCloseDetailsVO detailVO : purchaseOrderDeliveryScheduleShortCloseVO
					.getPurchaseOrderDeliveryScheduleShortCloseDetailsVO()) {
				PurchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO detailDTO = new PurchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO();
				detailDTO.setId(detailVO.getId());

				if (detailVO.getItem() != null) {
					ItemMasterDetailsResponseCloseDTO itemDTO = new ItemMasterDetailsResponseCloseDTO();
					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					if (detailVO.getItem().getPrimaryUnit() != null) {
						UnitMasterResponseDTO unit = new UnitMasterResponseDTO();
						unit.setId(detailVO.getItem().getPrimaryUnit().getId());
						unit.setUnitId(detailVO.getItem().getPrimaryUnit().getUnitId());
						itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					}

					if (detailVO.getItem().getPricingUnit() != null) {
						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();
						unitDTO.setId(detailVO.getItem().getPricingUnit().getId());
						unitDTO.setUnitId(detailVO.getItem().getPricingUnit().getUnitId());
						unitDTO.setUnitDescription(detailVO.getItem().getPricingUnit().getDescription());
						itemDTO.setUnit(unitDTO);
					}
					detailDTO.setItem(itemDTO);
				}

				detailDTO.setOrderedQty(detailVO.getOrderedQty());
				detailDTO.setSuppliedQty(detailVO.getSuppliedQty());
				detailDTO.setPendingQty(detailVO.getPendingQty());
				detailDTO.setNewRequiredQty(detailVO.getNewRequiredQty());
				detailDTO.setShortCloseQty(detailVO.getShortCloseQty());

				detailsList.add(detailDTO);
			}
		}
		responseDTO.setPurchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO(detailsList);

		return responseDTO;
	}

	@Override
	public String getPurchaseOrderDeliveryScheduleShortCloseDocId(Long orgId, String financialYear) {
		String screenCode1 = "PODSSC";
		String result = purchaseOrderDeliveryScheduleShortCloseRepo
				.getPurchaseOrderDeliveryScheduleShortCloseDocId(orgId, financialYear, screenCode1);
		return result;
	}

	@Override
	public List<Map<String, Object>> getSupplierDetailsShortClose(Long orgId, Long branch) {
		Set<Object[]> chType = purchaseOrderDeliveryScheduleShortCloseRepo.getSupplierDetailsShortClose(orgId, branch);
		return getSupplierDetailsShortClose(chType);
	}

	private List<Map<String, Object>> getSupplierDetailsShortClose(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("supplierId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("supplierName", ch[1] != null ? ch[1].toString() : "");
			map.put("supplierCode", ch[2] != null ? ch[2].toString() : "");

			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderNobasedSchedule(Long orgId, Long branch, Long supplier) {
		Set<Object[]> chType = purchaseOrderDeliveryScheduleShortCloseRepo.getPurchaseOrderNobasedSchedule(orgId,
				branch, supplier);
		return getPurchaseOrderNobasedSchedule(chType);
	}

	private List<Map<String, Object>> getPurchaseOrderNobasedSchedule(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("purchaseId", ch[2] != null ? ((Number) ch[2]).longValue() : null);

			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getPurchaseOrderNobasedScheduleDetails(Long orgId, Long branch, Long supplier,
			String purchaseOrderNo) {
		Set<Object[]> chType = purchaseOrderDeliveryScheduleShortCloseRepo.getPurchaseOrderNobasedScheduleDetails(orgId,
				branch, supplier, purchaseOrderNo);
		return getPurchaseOrderNobasedScheduleDetails(chType);
	}

	private List<Map<String, Object>> getPurchaseOrderNobasedScheduleDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("uom", ch[3] != null ? ((Number) ch[3]).longValue() : null);
			map.put("orderQty", ch[4] != null ? new BigDecimal(ch[4].toString()) : BigDecimal.ZERO);
			map.put("suppliedQty", ch[5] != null ? new BigDecimal(ch[5].toString()) : BigDecimal.ZERO);
			map.put("pendingQty", ch[6] != null ? new BigDecimal(ch[6].toString()) : BigDecimal.ZERO);
			map.put("unitDescription", ch[7] != null ? ch[7].toString() : "");
			list.add(map);
		}
		return list;
	}

	// Direct Purchase

	@Override
	public DirectPurchaseResponseDTO getDirectPurchaseById(Long id) throws ApplicationException {
		DirectPurchaseVO directPurchaseVO = directPurchaseRepo.getDirectPurchaseById(id);

		if (directPurchaseVO == null) {
			throw new ApplicationException("Direct Purchase Not Found");
		}

		return buildDirectPurchaseResponse(directPurchaseVO);
	}

	@Override
	public List<DirectPurchaseResponseDTO> getDirectPurchaseByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<DirectPurchaseVO> inwardInspectionList = directPurchaseRepo.getDirectPurchaseByOrgId(orgId, branch);
		if (inwardInspectionList == null || inwardInspectionList.isEmpty()) {
			throw new ApplicationException("Direct Purchase Not Found");
		}
		List<DirectPurchaseResponseDTO> responseList = new ArrayList<>();
		for (DirectPurchaseVO inwardInspectionVO : inwardInspectionList) {
			responseList.add(buildDirectPurchaseResponse(inwardInspectionVO));
		}
		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateDirectPurchase(DirectPurchaseDTO directPurchaseDTO, MultipartFile[] files)
			throws ApplicationException {
		DirectPurchaseVO directPurchaseVO;
		String message;

		if (ObjectUtils.isNotEmpty(directPurchaseDTO.getId())) {
			directPurchaseVO = directPurchaseRepo.findById(directPurchaseDTO.getId())
					.orElseThrow(() -> new ApplicationException("Direct Purchase Not Found"));

			directPurchaseVO.setUpdatedBy(directPurchaseDTO.getCreatedBy());
			message = "Direct Purchase Updated Successfully";
		} else {
			directPurchaseVO = new DirectPurchaseVO();
			String screenCode = "DP";
			String docId = directPurchaseRepo.getDirectPurchaseDocId(directPurchaseDTO.getOrgId(),
					directPurchaseDTO.getFinancialYear(), screenCode);
			directPurchaseVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(directPurchaseDTO.getOrgId(),
							directPurchaseDTO.getFinancialYear(), screenCode);
			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			directPurchaseVO.setCreatedBy(directPurchaseDTO.getCreatedBy());
			directPurchaseVO.setUpdatedBy(directPurchaseDTO.getCreatedBy());
			message = "Direct Purchase Created Successfully";
		}

		setDirectPurchaseValues(directPurchaseDTO, directPurchaseVO);

		directPurchaseVO = directPurchaseRepo.save(directPurchaseVO);
		saveDirectPurchaseAttachments(files, directPurchaseVO);

		DirectPurchaseResponseDTO directPurchaseResponse = buildDirectPurchaseResponse(directPurchaseVO);
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("directPurchaseVO", directPurchaseResponse);
		return response;
	}

	private void setDirectPurchaseValues(DirectPurchaseDTO dto, DirectPurchaseVO vo) throws ApplicationException {

		vo.setBelongsTo(dto.getBelongsTo());
		vo.setSupplierName(dto.getSupplierName());
		vo.setInvDate(dto.getInvDate());
		vo.setIsIgstApplicable(dto.getIsIgstApplicable());
		vo.setIssueTo(dto.getIssueTo());
		vo.setGstnNo(dto.getGstnNo());
		vo.setInvNo(dto.getInvNo());
		vo.setSuppType(dto.getSuppType());
		vo.setDealerType(dto.getDealerType());
		vo.setEccNoStNo(dto.getEccNoStNo());
		vo.setIsReverseCharge(dto.getIsReverseCharge());
		vo.setRemarks(dto.getRemarks());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		vo.setDiscount(dto.getDiscount());
		vo.setAfterDiscountTotalAmount(dto.getAfterDiscountTotalAmount());
		vo.setBasicAmount(dto.getBasicAmount());
		vo.setTotalAmount(dto.getTotalAmount());

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		if (dto.getGstState() != null && dto.getGstState() != 0) {

			GSTStateMasterVO gstState = gstStateMasterRepo.findById(dto.getGstState())
					.orElseThrow(() -> new ApplicationException("GST State Not Found"));

			vo.setGstState(gstState);
		}

		if (dto.getItemCategory() != null && dto.getItemCategory() != 0) {

			ItemMasterVO itemCategory = itemMasterRepo.findByItemType(dto.getItemCategory())
					.orElseThrow(() -> new ApplicationException("Item Category Not Found"));

			vo.setItemCategory(itemCategory);
		}

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {

			EmployeeMasterVO preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Employee Not Found"));

			vo.setPreparedBy(preparedBy);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {

			List<DirectPurchaseCashDetailsVO> oldCashDetails = directPurchaseCashDetailsRepo.findByDirectPurchaseVO(vo);

			directPurchaseCashDetailsRepo.deleteAll(oldCashDetails);

			List<DirectPurchaseTaxDetailsVO> oldTaxDetails = directPurchaseTaxDetailsRepo.findByDirectPurchaseVO(vo);

			directPurchaseTaxDetailsRepo.deleteAll(oldTaxDetails);
			
			List<DirectPurchaseFileUploadDetailsVO> direct = directPurchaseFileUploadDetailsRepo.findByDirectPurchaseVO(vo);

			directPurchaseFileUploadDetailsRepo.deleteAll(direct);

		}

		List<DirectPurchaseCashDetailsVO> cashDetailsList = new ArrayList<>();

		if (dto.getDirectPurchaseCashDetailsDTO() != null && !dto.getDirectPurchaseCashDetailsDTO().isEmpty()) {

			for (DirectPurchaseCashDetailsDTO detailDTO : dto.getDirectPurchaseCashDetailsDTO()) {

				DirectPurchaseCashDetailsVO detailVO = new DirectPurchaseCashDetailsVO();

				detailVO.setItemCode(detailDTO.getItemCode());
				detailVO.setItemDescription(detailDTO.getItemDescription());
				detailVO.setHsnCode(detailDTO.getHsnCode());
				detailVO.setTaxType(detailDTO.getTaxType());

				// Unit
				if (detailDTO.getUnit() != null && detailDTO.getUnit() > 0) {

					UnitMasterVO unit = unitMasterRepo.findById(detailDTO.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				detailVO.setDcQty(detailDTO.getDcQty());
				detailVO.setReceivedQty(detailDTO.getReceivedQty());
				detailVO.setRate(detailDTO.getRate());
				detailVO.setTax(detailDTO.getTax());

				BigDecimal quantity = detailDTO.getDcQty() != null ? detailDTO.getDcQty() : BigDecimal.ZERO;

				BigDecimal rate = detailDTO.getRate() != null ? detailDTO.getRate() : BigDecimal.ZERO;

				BigDecimal amount = quantity.multiply(rate);

				detailVO.setAmount(amount);

				BigDecimal taxPercentage = detailDTO.getTax() != null ? detailDTO.getTax() : BigDecimal.ZERO;

				BigDecimal igstRate = BigDecimal.ZERO;
				BigDecimal cgstRate = BigDecimal.ZERO;
				BigDecimal sgstRate = BigDecimal.ZERO;

				BigDecimal igstAmount = BigDecimal.ZERO;
				BigDecimal cgstAmount = BigDecimal.ZERO;
				BigDecimal sgstAmount = BigDecimal.ZERO;

				if ("Yes".equalsIgnoreCase(dto.getIsIgstApplicable())) {

					igstRate = taxPercentage;

					igstAmount = amount.multiply(igstRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

					detailVO.setIgstRate(igstRate);
					detailVO.setIgstAmount(igstAmount);

				} else {

					cgstRate = taxPercentage.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);

					sgstRate = taxPercentage.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);

					cgstAmount = amount.multiply(cgstRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

					sgstAmount = amount.multiply(sgstRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

					detailVO.setCgstRate(cgstRate);
					detailVO.setCgstAmount(cgstAmount);

					detailVO.setSgstRate(sgstRate);
					detailVO.setSgstAmount(sgstAmount);
				}

				detailVO.setDirectPurchaseVO(vo);

				cashDetailsList.add(detailVO);
			}
		}

		vo.setDirectPurchaseCashDetailsVO(cashDetailsList);

		List<DirectPurchaseTaxDetailsVO> taxList = new ArrayList<>();

		if (dto.getDirectPurchaseTaxDetailsDTO() != null && !dto.getDirectPurchaseTaxDetailsDTO().isEmpty()) {

			for (DirectPurchaseTaxDetailsDTO taxDTO : dto.getDirectPurchaseTaxDetailsDTO()) {

				DirectPurchaseTaxDetailsVO taxVO = new DirectPurchaseTaxDetailsVO();

				taxVO.setParticulars(taxDTO.getParticulars());
				taxVO.setTax(taxDTO.getTax());
				taxVO.setAcceptedQtyAmount(taxDTO.getAcceptedQtyAmount());
				taxVO.setRevisedAmount(taxDTO.getRevisedAmount());
				taxVO.setTaxId(taxDTO.getTaxId());

				taxVO.setDirectPurchaseVO(vo);

				taxList.add(taxVO);
			}
		}

		vo.setDirectPurchaseTaxDetailsVO(taxList);
	}

//	private void createDirectorys(Path path) throws IOException {
//		if (!Files.exists(path)) {
//			Files.createDirectories(path);
//		}
//	}	

//	private void saveAttachmentss(MultipartFile[] files, DirectPurchaseVO directPurchaseVO)
//			throws ApplicationException {
//		if (files == null || files.length == 0) {
//			return;
//		}
//
//		try {
//			Path inwardFolder = Paths.get(uploadPaths, "directpurchase", directPurchaseVO.getId().toString());
//			createDirectorys(inwardFolder);
//
//			if (ObjectUtils.isNotEmpty(directPurchaseVO.getId())) {
//				List<DirectPurchaseFileUploadDetailsVO> existingAttachments = directPurchaseFileUploadDetailsRepo
//						.findByDirectPurchaseVO(directPurchaseVO);
//				if (existingAttachments != null && !existingAttachments.isEmpty()) {
//					directPurchaseFileUploadDetailsRepo.deleteAll(existingAttachments);
//				}
//			}
//
//			List<DirectPurchaseFileUploadDetailsVO> attachmentList = new ArrayList<>();
//			for (MultipartFile file : files) {
//				if (file == null || file.isEmpty()) {
//					continue;
//				}
//
//				String originalName = file.getOriginalFilename();
//				if (originalName == null) {
//					originalName = "file";
//				}
//				originalName = originalName.replaceAll("\\s+", "_");
//
//				String extension = "";
//				if (originalName.contains(".")) {
//					extension = originalName.substring(originalName.lastIndexOf("."));
//					originalName = originalName.substring(0, originalName.lastIndexOf("."));
//				}
//
//				String fileName = originalName + "_" + directPurchaseVO.getId() + extension;
//				Path filePath = inwardFolder.resolve(fileName);
//
//				try (InputStream inputStream = file.getInputStream()) {
//					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//				}
//
//				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//						.path("/api/purchaseOrder/viewDirectPurchaseFile/").toUriString();
//				String relativePath = uploadPath.replace("\\", "/");
//				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");
//				String publicUrl = baseUrl + relativePath;
//
//				DirectPurchaseFileUploadDetailsVO attachment = new DirectPurchaseFileUploadDetailsVO();
//				attachment.setDirectPurchaseVO(directPurchaseVO);
//				attachment.setName(file.getOriginalFilename());
//				attachment.setFileName(fileName);
//				attachment.setFilePath(publicUrl);
//				attachment.setFileSize(file.getSize());
////				attachment.setContentType(file.getContentType());
////				attachment.setUploadOn(LocalDateTime.now());
//				attachmentList.add(attachment);
//			}
//
//			if (!attachmentList.isEmpty()) {
//				List<DirectPurchaseFileUploadDetailsVO> saved = directPurchaseFileUploadDetailsRepo
//						.saveAll(attachmentList);
//				directPurchaseVO.setDirectPurchaseFileUploadDetailsVO(saved);
//			}
//
//		} catch (IOException e) {
//			throw new ApplicationException("File Upload Failed : " + e.getMessage());
//		}
//	}
//
//	
//
//	@Override
//	public ResponseEntity<byte[]> viewDirectPurchaseFile(HttpServletRequest request) throws IOException {
//		return serveFiles(request, "/api/purchaseOrder/viewDirectPurchaseFile/", uploadPaths);
//	}
//
//	private ResponseEntity<byte[]> serveFiles(HttpServletRequest request, String apiPrefix, String uploadPaths)
//			throws IOException {
//
//		String uri = request.getRequestURI();
//		String relativePath = uri.replace(apiPrefix, "");
//		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);
//
//		if (relativePath.startsWith("uploads/")) {
//			relativePath = relativePath.substring("uploads/".length());
//		}
//
//		Path baseDir = Paths.get(uploadPaths).toAbsolutePath().normalize();
//		Path filePath = baseDir.resolve(relativePath).normalize();
//
//		if (!filePath.startsWith(baseDir)) {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//		}
//
//		if (!Files.exists(filePath)) {
//			return ResponseEntity.notFound().build();
//		}
//
//		String contentType = Files.probeContentType(filePath);
//		if (contentType == null) {
//			contentType = "application/octet-stream";
//		}
//
//		byte[] data = Files.readAllBytes(filePath);
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(data);
//	}

	@Value("${direction.purchase}")
	private String uploadPaths;

	private void saveDirectPurchaseAttachments(MultipartFile[] files, DirectPurchaseVO directPurchaseVO)
			throws ApplicationException {
		if (files == null || files.length == 0) {
			return;
		}

		try {
			Path stockTransferGrnFolder = Paths.get(uploadPaths, "directpurchase", directPurchaseVO.getId().toString());
			createDirectorys(stockTransferGrnFolder);

			if (ObjectUtils.isNotEmpty(directPurchaseVO.getId())) {
				List<DirectPurchaseFileUploadDetailsVO> existingAttachments = directPurchaseFileUploadDetailsRepo
						.findByDirectPurchaseVO(directPurchaseVO);
				if (existingAttachments != null && !existingAttachments.isEmpty()) {
					directPurchaseFileUploadDetailsRepo.deleteAll(existingAttachments);
				}
			}

			List<DirectPurchaseFileUploadDetailsVO> attachmentList = new ArrayList<>();

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

				String fileName = originalName + "_" + directPurchaseVO.getId() + extension;
				Path filePath = stockTransferGrnFolder.resolve(fileName);

				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/purchaseOrder/viewDirectPurchaseFile/").toUriString();

				String relativePath = uploadPaths.replace("\\", "/");
				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				String publicUrl = baseUrl + relativePath;

				DirectPurchaseFileUploadDetailsVO attachment = new DirectPurchaseFileUploadDetailsVO();
				attachment.setDirectPurchaseVO(directPurchaseVO);
				attachment.setName(file.getOriginalFilename());
				attachment.setFileName(fileName);
				attachment.setFilePath(publicUrl);
				attachment.setFileSize(file.getSize());
				attachment.setContentType(file.getContentType());
				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			if (!attachmentList.isEmpty()) {
				List<DirectPurchaseFileUploadDetailsVO> saved = directPurchaseFileUploadDetailsRepo
						.saveAll(attachmentList);
				directPurchaseVO.setDirectPurchaseFileUploadDetailsVO(saved);
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
	public ResponseEntity<byte[]> viewDirectPurchaseFile(HttpServletRequest request) throws IOException {
		return serveStockTransferFile(request, "/api/purchaseOrder/viewDirectPurchaseFile/", uploadPaths);
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
	public String getDirectPurchaseDocId(Long orgId, String financialYear) {
		String screenCode = "DP";
		return directPurchaseRepo.getDirectPurchaseDocId(orgId, financialYear, screenCode);
	}

	private DirectPurchaseResponseDTO buildDirectPurchaseResponse(DirectPurchaseVO vo) {

		DirectPurchaseResponseDTO responseDTO = new DirectPurchaseResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setSupplierName(vo.getSupplierName());
		responseDTO.setInvDate(vo.getInvDate());
		responseDTO.setIsIgstApplicable(vo.getIsIgstApplicable());
		responseDTO.setIssueTo(vo.getIssueTo());
		responseDTO.setGstnNo(vo.getGstnNo());
		responseDTO.setInvNo(vo.getInvNo());
		responseDTO.setSuppType(vo.getSuppType());
		responseDTO.setDealerType(vo.getDealerType());
		responseDTO.setEccNoStNo(vo.getEccNoStNo());
		responseDTO.setIsReverseCharge(vo.getIsReverseCharge());

		responseDTO.setBasicAmount(vo.getBasicAmount());
		responseDTO.setDiscount(vo.getDiscount());
		responseDTO.setAfterDiscountTotalAmount(vo.getAfterDiscountTotalAmount());
		responseDTO.setTotalAmount(vo.getTotalAmount());

		responseDTO.setRemarks(vo.getRemarks());

		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		if (vo.getGstState() != null) {
			GSTStateMasterResponseDTO gstStateDTO = new GSTStateMasterResponseDTO();
			gstStateDTO.setId(vo.getGstState().getId());
			gstStateDTO.setGstState(vo.getGstState().getStateName());
			gstStateDTO.setGstStateCode(vo.getGstState().getStateCode());
			responseDTO.setGstState(gstStateDTO);
		}

		if (vo.getItemCategory() != null) {
			ItemCategoryResponseDTO itemCategoryDTO = new ItemCategoryResponseDTO();
			itemCategoryDTO.setId(vo.getItemCategory().getId());
			itemCategoryDTO.setCategory(vo.getItemCategory().getItemCode());
			responseDTO.setItemCategory(itemCategoryDTO);
		}

		if (vo.getPreparedBy() != null) {
			EmployeeMasterResponseDetailsDTO preparedByDTO = new EmployeeMasterResponseDetailsDTO();
			preparedByDTO.setId(vo.getPreparedBy().getId());
			preparedByDTO.setEmployeeCode(vo.getPreparedBy().getEmployeeId());
			preparedByDTO.setEmployeeName(vo.getPreparedBy().getEmployeeName());
			responseDTO.setPreparedBy(preparedByDTO);
		}

		List<DirectPurchaseCashDetailsResponseDTO> cashDetailsList = new ArrayList<>();

		if (vo.getDirectPurchaseCashDetailsVO() != null && !vo.getDirectPurchaseCashDetailsVO().isEmpty()) {

			for (DirectPurchaseCashDetailsVO detailVO : vo.getDirectPurchaseCashDetailsVO()) {

				DirectPurchaseCashDetailsResponseDTO detailResponse = new DirectPurchaseCashDetailsResponseDTO();

				detailResponse.setId(detailVO.getId());
				detailResponse.setItemCode(detailVO.getItemCode());
				detailResponse.setItemDescription(detailVO.getItemDescription());
				detailResponse.setHsnCode(detailVO.getHsnCode());
				detailResponse.setTaxType(detailVO.getTaxType());
				detailResponse.setTaxPercentage(detailVO.getTax());

				if (detailVO.getUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailVO.getUnit().getId());
					unitDTO.setUnitId(detailVO.getUnit().getUnitId());
					detailResponse.setUnit(unitDTO);
				}

				detailResponse.setDcQty(detailVO.getDcQty());
				detailResponse.setReceivedQty(detailVO.getReceivedQty());
				detailResponse.setRate(detailVO.getRate());
				detailResponse.setAmount(detailVO.getAmount());

				detailResponse.setCgstRate(detailVO.getCgstRate());
				detailResponse.setCgstAmount(detailVO.getCgstAmount());
				detailResponse.setSgstRate(detailVO.getSgstRate());
				detailResponse.setSgstAmount(detailVO.getSgstAmount());
				detailResponse.setIgstRate(detailVO.getIgstRate());
				detailResponse.setIgstAmount(detailVO.getIgstAmount());

				cashDetailsList.add(detailResponse);
			}
		}

		responseDTO.setDirectPurchaseCashDetails(cashDetailsList);

		List<DirectPurchaseTaxDetailsResponseDTO> taxList = new ArrayList<>();

		if (vo.getDirectPurchaseTaxDetailsVO() != null && !vo.getDirectPurchaseTaxDetailsVO().isEmpty()) {

			for (DirectPurchaseTaxDetailsVO taxVO : vo.getDirectPurchaseTaxDetailsVO()) {

				DirectPurchaseTaxDetailsResponseDTO taxResponse = new DirectPurchaseTaxDetailsResponseDTO();

				taxResponse.setId(taxVO.getId());
				taxResponse.setParticulars(taxVO.getParticulars());
				taxResponse.setTax(taxVO.getTax());
				taxResponse.setAcceptedQtyAmount(taxVO.getAcceptedQtyAmount());
				taxResponse.setRevisedAmount(taxVO.getRevisedAmount());
				taxResponse.setTaxId(taxVO.getTaxId());

				taxList.add(taxResponse);
			}
		}

		responseDTO.setDirectPurchaseTaxDetails(taxList);

		List<DirectPurchaseFileUploadDetailsResponseDTO> fileList = new ArrayList<>();

		if (vo.getDirectPurchaseFileUploadDetailsVO() != null && !vo.getDirectPurchaseFileUploadDetailsVO().isEmpty()) {

			for (DirectPurchaseFileUploadDetailsVO fileVO : vo.getDirectPurchaseFileUploadDetailsVO()) {

				DirectPurchaseFileUploadDetailsResponseDTO fileResponse = new DirectPurchaseFileUploadDetailsResponseDTO();

				fileResponse.setId(fileVO.getId());
				fileResponse.setFileName(fileVO.getFileName());
				fileResponse.setFilePath(fileVO.getFilePath());
				fileResponse.setFileType(fileVO.getFileType());
				fileResponse.setFileSize(fileVO.getFileSize());

				fileList.add(fileResponse);
			}
		}

		responseDTO.setDirectPurchaseFileUploadDetails(fileList);

		return responseDTO;
	}

	@Override
	public List<Map<String, Object>> getIssueTo(Long orgId, Long branch) {
		Set<Object[]> chType = directPurchaseRepo.getIssueTo(orgId, branch);
		return getIssueTo(chType);
	}

	private List<Map<String, Object>> getIssueTo(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("issueTo", ch[0] != null ? ch[0].toString() : "");

			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getItemType(Long orgId, Long branch, Long itemType) {
		Set<Object[]> chType = directPurchaseRepo.getItemType(orgId, branch, itemType);
		return getItemType(chType);
	}

	private List<Map<String, Object>> getItemType(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");

			list.add(map);
		}
		return list;
	}
}
