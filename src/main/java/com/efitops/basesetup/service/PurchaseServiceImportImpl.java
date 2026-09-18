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

import com.efitops.basesetup.ResponseDTO.BillOfMaterialDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.BillOfMaterialResDTO;
import com.efitops.basesetup.ResponseDTO.BillOfMaterialResponseDTO;
import com.efitops.basesetup.ResponseDTO.BomFgResponseDTO;
import com.efitops.basesetup.ResponseDTO.CompRouteNoResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.ConsumptionEntryDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ConsumptionEntryResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseCashDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseFileUploadDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseResponseDTO;
import com.efitops.basesetup.ResponseDTO.DirectPurchaseTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.EmployeeMasterResponseDetailsDTO;
import com.efitops.basesetup.ResponseDTO.FGTransferSlipDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.FgTransferSlipResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemCategoryResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseCloseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseImportDTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesResponseDTO;
import com.efitops.basesetup.ResponseDTO.LmeResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialIndentForProductionDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialIndentForProductionResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialTransferReturnNoteDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.MaterialTransferReturnNoteResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionScheduleOrderDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionScheduleOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionTransferSlipDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ProductionTransferSlipResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderDeliveryScheduleShortCloseResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderImportDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderLocalDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderLocalFileUploadDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderLocalTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseOrderResponseDTO;
import com.efitops.basesetup.ResponseDTO.RmConsumptionEntryDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.ScheduleDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.StockTransferResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BillOfMaterialDTO;
import com.efitops.basesetup.dto.BillOfMaterialDetailsDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ConsumptionEntryDTO;
import com.efitops.basesetup.dto.ConsumptionEntryDetailsDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.CustomerResponseGstDetailsDTO;
import com.efitops.basesetup.dto.DirectPurchaseCashDetailsDTO;
import com.efitops.basesetup.dto.DirectPurchaseDTO;
import com.efitops.basesetup.dto.DirectPurchaseTaxDetailsDTO;
import com.efitops.basesetup.dto.EmployeeMasterDetailsReponseDTO;
import com.efitops.basesetup.dto.FGTransferSlipDetailsDTO;
import com.efitops.basesetup.dto.FgTransferSlipDTO;
import com.efitops.basesetup.dto.MaterialIndentForProductionDTO;
import com.efitops.basesetup.dto.MaterialIndentForProductionDetailsDTO;
import com.efitops.basesetup.dto.MaterialTransferReturnNoteDTO;
import com.efitops.basesetup.dto.MaterialTransferReturnNoteDetailsDTO;
import com.efitops.basesetup.dto.PoType;
import com.efitops.basesetup.dto.ProductionScheduleOrderDTO;
import com.efitops.basesetup.dto.ProductionScheduleOrderDetailsDTO;
import com.efitops.basesetup.dto.ProductionTransferSlipDTO;
import com.efitops.basesetup.dto.ProductionTransferSlipDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderDTO;
import com.efitops.basesetup.dto.PurchaseOrderDeliveryScheduleShortCloseDTO;
import com.efitops.basesetup.dto.PurchaseOrderDeliveryScheduleShortCloseDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderImportDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderLocalDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderLocalFileUploadDetailsDTO;
import com.efitops.basesetup.dto.PurchaseOrderLocalTaxDetailsDTO;
import com.efitops.basesetup.dto.RmConsumptionEntryDetailsDTO;
import com.efitops.basesetup.dto.ScheduleDetailsDTO;
import com.efitops.basesetup.dto.StockTransferDTO;
import com.efitops.basesetup.dto.StockTransferDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BillOfMaterialDetailsVO;
import com.efitops.basesetup.entity.BillOfMaterialVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.ConsumptionEntryDetailsVO;
import com.efitops.basesetup.entity.ConsumptionEntryVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DirectPurchaseCashDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseFileUploadDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseTaxDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.FGTransferSlipDetailsVO;
import com.efitops.basesetup.entity.FgTransferSlipVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.LMEVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.MaterialIndentForProductionDetailsVO;
import com.efitops.basesetup.entity.MaterialIndentForProductionVO;
import com.efitops.basesetup.entity.MaterialTransferReturnNoteDetailsVO;
import com.efitops.basesetup.entity.MaterialTransferReturnNoteVO;
import com.efitops.basesetup.entity.ProcessSheetCompRoutingVO;
import com.efitops.basesetup.entity.ProductionScheduleOrderDetailsVO;
import com.efitops.basesetup.entity.ProductionScheduleOrderVO;
import com.efitops.basesetup.entity.ProductionTransferSlipDetailsVO;
import com.efitops.basesetup.entity.ProductionTransferSlipVO;
import com.efitops.basesetup.entity.PurchaseOrderDeliveryScheduleShortCloseDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderDeliveryScheduleShortCloseVO;
import com.efitops.basesetup.entity.PurchaseOrderImportDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderLocalDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderLocalFileUploadDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderLocalTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;
import com.efitops.basesetup.entity.RmConsumptionEntryDetailsVO;
import com.efitops.basesetup.entity.ScheduleDetailsVO;
import com.efitops.basesetup.entity.StockTransferDetailsVO;
import com.efitops.basesetup.entity.StockTransferVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BillOfMaterialDetailsRepo;
import com.efitops.basesetup.repository.BillOfMaterialRepo;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.ConsumptionEntryDetailsRepo;
import com.efitops.basesetup.repository.ConsumptionEntryRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.DirectPurchaseCashDetailsRepo;
import com.efitops.basesetup.repository.DirectPurchaseFileUploadDetailsRepo;
import com.efitops.basesetup.repository.DirectPurchaseRepo;
import com.efitops.basesetup.repository.DirectPurchaseTaxDetailsRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.FGTransferSlipDetailsRepo;
import com.efitops.basesetup.repository.FgTransferSlipRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.LMERepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.MaterialIndentForProductionDetailsRepo;
import com.efitops.basesetup.repository.MaterialIndentForProductionRepo;
import com.efitops.basesetup.repository.MaterialTransferReturnNoteDetailsRepository;
import com.efitops.basesetup.repository.MaterialTransferReturnNoteRepository;
import com.efitops.basesetup.repository.ProcessSheetCompRoutingRepo;
import com.efitops.basesetup.repository.ProductionScheduleOrderDetailsRepo;
import com.efitops.basesetup.repository.ProductionScheduleOrderRepo;
import com.efitops.basesetup.repository.ProductionTransferSlipDetailsRepo;
import com.efitops.basesetup.repository.ProductionTransferSlipRepo;
import com.efitops.basesetup.repository.PurchaseOrderDeliveryScheduleShortCloseDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderDeliveryScheduleShortCloseRepo;
import com.efitops.basesetup.repository.PurchaseOrderImportDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderLocalDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderLocalFileUploadDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderLocalTaxDetailsRepo;
import com.efitops.basesetup.repository.PurchaseOrderRepo;
import com.efitops.basesetup.repository.RmConsumptionEntryDetailsRepo;
import com.efitops.basesetup.repository.ScheduleDetailsRepo;
import com.efitops.basesetup.repository.StockTransferDetailsRepo;
import com.efitops.basesetup.repository.StockTransferRepo;
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

	@Autowired
	StockTransferRepo stockTransferRepo;

	@Autowired
	LocationRepo locationRepo;

	@Autowired
	StockTransferDetailsRepo stockTransferDetailsRepo;

	@Autowired
	private ProductionScheduleOrderRepo productionScheduleOrderRepo;

	@Autowired
	private ProductionScheduleOrderDetailsRepo productionScheduleOrderDetailsRepo;
	@Autowired
	private ScheduleDetailsRepo scheduleDetailsRepo;

	@Autowired
	private BillOfMaterialRepo billOfMaterialRepo;
	@Autowired
	private BillOfMaterialDetailsRepo billOfMaterialDetailsRepo;

	@Autowired
	private ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	ProcessSheetCompRoutingRepo processSheetCompRoutingRepo;

	@Autowired
	private MaterialIndentForProductionRepo materialIndentForProductionRepo;

	@Autowired
	private MaterialIndentForProductionDetailsRepo materialIndentForProductionDetailsRepo;

	@Autowired
	private ProductionTransferSlipRepo productionTransferSlipRepo;

	@Autowired
	private ProductionTransferSlipDetailsRepo productionTransferSlipDetailsRepo;

	@Autowired
	private FgTransferSlipRepo fgTransferSlipRepo;

	@Autowired
	private FGTransferSlipDetailsRepo fgTransferSlipDetailsRepo;

	@Autowired
	private ConsumptionEntryRepo consumptionEntryRepo;

	@Autowired
	private ConsumptionEntryDetailsRepo consumptionEntryDetailsRepo;

	@Autowired
	private RmConsumptionEntryDetailsRepo rmConsumptionEntryDetailsRepo;

	@Autowired
	private MaterialTransferReturnNoteRepository materialTransferReturnNoteRepo;
	@Autowired
	private MaterialTransferReturnNoteDetailsRepository materialTransferReturnNoteDetailsRepo;

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

			List<DirectPurchaseFileUploadDetailsVO> direct = directPurchaseFileUploadDetailsRepo
					.findByDirectPurchaseVO(vo);

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

	// StockTransferResponseDTO

	@Override
	public StockTransferResponseDTO getStockTransferById(Long id) throws ApplicationException {

		StockTransferVO stockTransferVO = stockTransferRepo.getStockTransferById(id);

		if (stockTransferVO == null) {
			throw new ApplicationException("Stock Transfer Not Found");
		}

		return buildStockTransferResponse(stockTransferVO);
	}

	@Override
	public List<StockTransferResponseDTO> getStockTransferByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<StockTransferVO> stockTransferList = stockTransferRepo.getStockTransferByOrgId(orgId, branch);

		if (stockTransferList == null || stockTransferList.isEmpty()) {
			throw new ApplicationException("Stock Transfer Not Found");
		}

		List<StockTransferResponseDTO> responseList = new ArrayList<>();

		for (StockTransferVO stockTransferVO : stockTransferList) {
			responseList.add(buildStockTransferResponse(stockTransferVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateStockTransfer(StockTransferDTO stockTransferDTO)
			throws ApplicationException {
		String screenCode = "STR";
		StockTransferVO stockTransferVO = new StockTransferVO();
		String message;

		if (ObjectUtils.isNotEmpty(stockTransferDTO.getId())) {

			stockTransferVO = stockTransferRepo.findById(stockTransferDTO.getId())
					.orElseThrow(() -> new ApplicationException("Stock Transfer Not Found"));

			stockTransferVO.setUpdatedBy(stockTransferDTO.getCreatedBy());

			message = "Stock Transfer Updated Successfully";

		} else {

			String docId = stockTransferRepo.getStockTransferDocId(stockTransferDTO.getOrgId(),
					stockTransferDTO.getFinancialYear(), screenCode);

			stockTransferVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(stockTransferDTO.getOrgId(),
							stockTransferDTO.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			stockTransferVO.setCreatedBy(stockTransferDTO.getCreatedBy());
			stockTransferVO.setUpdatedBy(stockTransferDTO.getCreatedBy());

			message = "Stock Transfer Created Successfully";
		}

		createUpdateStockTransferVOByStockTransferDTO(stockTransferDTO, stockTransferVO);

		stockTransferVO = stockTransferRepo.save(stockTransferVO);

		StockTransferResponseDTO responseDTO = buildStockTransferResponse(stockTransferVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("stockTransferVO", responseDTO);

		return response;
	}

	private void createUpdateStockTransferVOByStockTransferDTO(StockTransferDTO stockTransferDTO,
			StockTransferVO stockTransferVO) throws ApplicationException {

		stockTransferVO.setBelongsTo(stockTransferDTO.getBelongsTo());
		stockTransferVO.setReason(stockTransferDTO.getReason());
		stockTransferVO.setActive(stockTransferDTO.isActive());
		stockTransferVO.setCancelRemarks(stockTransferDTO.getCancelRemarks());
		stockTransferVO.setOrgId(stockTransferDTO.getOrgId());
		stockTransferVO.setFinancialYear(stockTransferDTO.getFinancialYear());
		stockTransferVO.setNarration(stockTransferDTO.getNarration());

		if (stockTransferDTO.getFromLocation() != null && stockTransferDTO.getFromLocation() != 0) {

			LocationVO fromLocation = locationRepo.findById(stockTransferDTO.getFromLocation())
					.orElseThrow(() -> new ApplicationException("From Location Not Found"));

			stockTransferVO.setFromLocation(fromLocation);
		}

		if (stockTransferDTO.getToLocation() != null && stockTransferDTO.getToLocation() != 0) {

			LocationVO toLocation = locationRepo.findById(stockTransferDTO.getToLocation())
					.orElseThrow(() -> new ApplicationException("To Location Not Found"));

			stockTransferVO.setToLocation(toLocation);
		}

		if (stockTransferDTO.getBranch() != null && stockTransferDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(stockTransferDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			stockTransferVO.setBranch(branch);
		}

		if (stockTransferDTO.getToBranch() != null && stockTransferDTO.getToBranch() != 0) {

			BranchVO toBranch = branchRepo.findById(stockTransferDTO.getToBranch())
					.orElseThrow(() -> new ApplicationException("To Branch Not Found"));

			stockTransferVO.setToBranch(toBranch);
		}

		if (ObjectUtils.isNotEmpty(stockTransferVO.getId())) {

			List<StockTransferDetailsVO> stockTransferDetailsVO = stockTransferDetailsRepo
					.findByStockTransferVO(stockTransferVO);

			stockTransferDetailsRepo.deleteAll(stockTransferDetailsVO);
		}

		List<StockTransferDetailsVO> itemDetailsList = new ArrayList<>();

		if (stockTransferDTO.getStockTransferDetailsDTO() != null) {

			for (StockTransferDetailsDTO dto : stockTransferDTO.getStockTransferDetailsDTO()) {

				StockTransferDetailsVO detailsVO = new StockTransferDetailsVO();

				if (dto.getItem() != null && dto.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(item);
				}

				if (dto.getUnit() != null && dto.getUnit() != 0) {

					UnitMasterVO item = unitMasterRepo.findById(dto.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Code Not Found"));

					detailsVO.setUnit(item);
				}

				detailsVO.setAvailableQty(dto.getAvailableQty());
				detailsVO.setQty(dto.getQty());
				detailsVO.setRate(dto.getRate());
				detailsVO.setAmount(dto.getQty().multiply(dto.getRate()));
				detailsVO.setStockTransferVO(stockTransferVO);

				itemDetailsList.add(detailsVO);
			}
		}

		stockTransferVO.setStockTransferDetailsVO(itemDetailsList);
	}

	private StockTransferResponseDTO buildStockTransferResponse(StockTransferVO stockTransferVO) {

		StockTransferResponseDTO responseDTO = new StockTransferResponseDTO();

		responseDTO.setId(stockTransferVO.getId());
		responseDTO.setDocId(stockTransferVO.getDocId());
		responseDTO.setDocDate(stockTransferVO.getDocDate());
		responseDTO.setBelongsTo(stockTransferVO.getBelongsTo());
		responseDTO.setReason(stockTransferVO.getReason());
		responseDTO.setActive(stockTransferVO.getActive());
		responseDTO.setCancel(stockTransferVO.getCancel());
		responseDTO.setUpdatedBy(stockTransferVO.getUpdatedBy());
		responseDTO.setCancelRemarks(stockTransferVO.getCancelRemarks());
		responseDTO.setOrgId(stockTransferVO.getOrgId());
		responseDTO.setScreenName(stockTransferVO.getScreenName());
		responseDTO.setScreenCode(stockTransferVO.getScreenCode());
		responseDTO.setFinancialYear(stockTransferVO.getFinancialYear());
		responseDTO.setNarration(stockTransferVO.getNarration());
		responseDTO.setCreatedBy(stockTransferVO.getCreatedBy());
		responseDTO.setDocDate(stockTransferVO.getDocDate());

		if (stockTransferVO.getFromLocation() != null) {

			LocationMasterResponseDTO fromLocationDTO = new LocationMasterResponseDTO();
			fromLocationDTO.setId(stockTransferVO.getFromLocation().getId());
			fromLocationDTO.setLocationName(stockTransferVO.getFromLocation().getLocationName());
			responseDTO.setFromLocation(fromLocationDTO);
		}

		if (stockTransferVO.getToLocation() != null) {

			LocationMasterResponseDTO toLocationDTO = new LocationMasterResponseDTO();
			toLocationDTO.setId(stockTransferVO.getToLocation().getId());
			toLocationDTO.setLocationName(stockTransferVO.getToLocation().getLocationName());
			responseDTO.setToLocation(toLocationDTO);
		}

		if (stockTransferVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(stockTransferVO.getBranch().getId());
			branchDTO.setBranchCode(stockTransferVO.getBranch().getBranchCode());
			branchDTO.setBranchName(stockTransferVO.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		if (stockTransferVO.getToBranch() != null) {

			BranchResponseDTO toBranchDTO = new BranchResponseDTO();
			toBranchDTO.setId(stockTransferVO.getToBranch().getId());
			toBranchDTO.setBranchCode(stockTransferVO.getToBranch().getBranchCode());
			toBranchDTO.setBranchName(stockTransferVO.getToBranch().getBranchName());
			responseDTO.setToBranch(toBranchDTO);
		}

		List<StockTransferDetailsResponseDTO> detailsList = new ArrayList<>();

		if (stockTransferVO.getStockTransferDetailsVO() != null) {

			for (StockTransferDetailsVO detailsVO : stockTransferVO.getStockTransferDetailsVO()) {

				StockTransferDetailsResponseDTO detailsDTO = new StockTransferDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setAvailableQty(detailsVO.getAvailableQty());
				detailsDTO.setQty(detailsVO.getQty());
				detailsDTO.setRate(detailsVO.getRate());

				if (detailsVO.getUnit() != null) {
					UnitMasterResponseDTO itemMasterDetailsResponseDTO = new UnitMasterResponseDTO();
					itemMasterDetailsResponseDTO.setId(detailsVO.getUnit().getId());
					itemMasterDetailsResponseDTO.setUnitId(detailsVO.getUnit().getUnitId());
					itemMasterDetailsResponseDTO.setUnitDescription(detailsVO.getUnit().getDescription());
					detailsDTO.setUnit(itemMasterDetailsResponseDTO);
				}

				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemMasterDetailsResponseDTO = new ItemMasterDetailsResponseImportDTO();
					itemMasterDetailsResponseDTO.setId(detailsVO.getItem().getId());
					itemMasterDetailsResponseDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemMasterDetailsResponseDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItem(itemMasterDetailsResponseDTO);
				}

				detailsList.add(detailsDTO);
			}
		}

		responseDTO.setStockTransferDetailsResponseDTO(detailsList);

		return responseDTO;
	}

	@Override
	public String getStockTransferDocId(Long orgId, String financialYear) {

		String screenCode = "STR";

		return stockTransferRepo.getStockTransferDocId(orgId, financialYear, screenCode);
	}

	@Override
	public List<Map<String, Object>> getStockTransferItemDetails(Long orgId, Long branch) {
		Set<Object[]> chType = stockTransferRepo.getStockTransferItemDetails(orgId, branch);
		return getStockTransferItemDetails(chType);
	}

	private List<Map<String, Object>> getStockTransferItemDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("unitId", ch[3] != null ? ch[3].toString() : "");
			map.put("unitmasterId", ch[4] != null ? ((Number) ch[4]).longValue() : null);
			map.put("locationId", ch[5] != null ? ((Number) ch[5]).longValue() : null);

			list.add(map);
		}

		return list;
	}

	// ProductionScheduleOrderVO

	@Override
	@Transactional
	public Map<String, Object> createUpdateProductionScheduleOrder(
			ProductionScheduleOrderDTO productionScheduleOrderDTO) throws ApplicationException {

		String screenCode = "PSO";
		ProductionScheduleOrderVO productionScheduleOrderVO = new ProductionScheduleOrderVO();
		String message;

		if (ObjectUtils.isNotEmpty(productionScheduleOrderDTO.getId())) {

			productionScheduleOrderVO = productionScheduleOrderRepo.findById(productionScheduleOrderDTO.getId())
					.orElseThrow(() -> new ApplicationException("Production Schedule Order Not Found"));

			productionScheduleOrderVO.setUpdatedBy(productionScheduleOrderDTO.getCreatedBy());

			message = "Production Schedule Order Updated Successfully";

		} else {

			String docId = productionScheduleOrderRepo.getProductionScheduleOrderDocId(
					productionScheduleOrderDTO.getOrgId(), productionScheduleOrderDTO.getFinancialYear(), screenCode);

			productionScheduleOrderVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(productionScheduleOrderDTO.getOrgId(),
							productionScheduleOrderDTO.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			productionScheduleOrderVO.setCreatedBy(productionScheduleOrderDTO.getCreatedBy());
			productionScheduleOrderVO.setUpdatedBy(productionScheduleOrderDTO.getCreatedBy());

			message = "Production Schedule Order Created Successfully";
		}

		createUpdateProductionScheduleOrderVOByProductionScheduleOrderDTO(productionScheduleOrderDTO,
				productionScheduleOrderVO);

		productionScheduleOrderVO = productionScheduleOrderRepo.save(productionScheduleOrderVO);

		ProductionScheduleOrderResponseDTO responseDTO = buildProductionScheduleOrderResponse(
				productionScheduleOrderVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("productionScheduleOrderVO", responseDTO);

		return response;
	}

	private void createUpdateProductionScheduleOrderVOByProductionScheduleOrderDTO(ProductionScheduleOrderDTO dto,
			ProductionScheduleOrderVO vo) throws ApplicationException {

		vo.setOrderType(dto.getOrderType());
		vo.setLcPoNo(dto.getLcPoNo());
		vo.setLcPoDate(dto.getLcPoDate());
		vo.setScheduleStartDate(dto.getScheduleStartDate());
		vo.setScheduleEndDate(dto.getScheduleEndDate());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setBatchQty(dto.getBatchQty());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getFgItem() != null && dto.getFgItem() >= 0) {

			ItemMasterVO fgItem = itemMasterRepo.findById(dto.getFgItem())
					.orElseThrow(() -> new ApplicationException("FG Item Not Found"));

			vo.setFgItem(fgItem);
		}

		if (dto.getCompRouteNo() != null && dto.getCompRouteNo() >= 0) {

			ProcessSheetCompRoutingVO fgItem = processSheetCompRoutingRepo.findById(dto.getCompRouteNo())
					.orElseThrow(() -> new ApplicationException("CompRouteNo Not Found"));

			vo.setCompRouteNo(fgItem);
		}

		if (dto.getBom() != null && dto.getBom() >= 0) {

			BillOfMaterialVO fgItem = billOfMaterialRepo.findById(dto.getBom())
					.orElseThrow(() -> new ApplicationException("Bom is not Found"));

			vo.setBom(fgItem);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {

			List<ProductionScheduleOrderDetailsVO> existingDetails = productionScheduleOrderDetailsRepo
					.findByProductionScheduleOrderVO(vo);

			productionScheduleOrderDetailsRepo.deleteAll(existingDetails);

			List<ScheduleDetailsVO> existingSchedules = scheduleDetailsRepo.findByProductionScheduleOrderVO(vo);

			scheduleDetailsRepo.deleteAll(existingSchedules);
		}

		BigDecimal totalQty = BigDecimal.ZERO;
		// ---------- Production Details ----------
		List<ProductionScheduleOrderDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getProductionScheduleOrderDetailsDTO() != null) {

			for (ProductionScheduleOrderDetailsDTO d : dto.getProductionScheduleOrderDetailsDTO()) {

				ProductionScheduleOrderDetailsVO detailsVO = new ProductionScheduleOrderDetailsVO();

				if (d.getItem() != null && d.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(item);
				}

				if (d.getUnit() != null && d.getUnit() != 0) {

					UnitMasterVO unit = unitMasterRepo.findById(d.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Code Not Found"));

					detailsVO.setUnit(unit);
				}

				if (d.getScrapUnit() != null && d.getScrapUnit() != 0) {

					UnitMasterVO scrapUnit = unitMasterRepo.findById(d.getScrapUnit())
							.orElseThrow(() -> new ApplicationException("Scrap Unit Code Not Found"));

					detailsVO.setScrapUnit(scrapUnit);
				}

				detailsVO.setBomQty(d.getBomQty());
				BigDecimal batchQty = dto.getBatchQty();
				detailsVO.setQtyRequired(d.getBomQty().multiply(batchQty));
				totalQty = totalQty.add(detailsVO.getQtyRequired());
				detailsVO.setScrapQty(d.getScrapQty());
				detailsVO.setProductionScheduleOrderVO(vo);

				itemDetailsList.add(detailsVO);
			}
		}

		vo.setProductionScheduleOrderDetailsVO(itemDetailsList);

		// ---------- Schedule Details ----------
		List<ScheduleDetailsVO> scheduleList = new ArrayList<>();

		if (dto.getScheduleDetailsDTO() != null) {

			for (ScheduleDetailsDTO s : dto.getScheduleDetailsDTO()) {

				ScheduleDetailsVO scheduleVO = new ScheduleDetailsVO();

				scheduleVO.setScheduleDate(s.getScheduleDate());
				scheduleVO.setQty(s.getQty());
				scheduleVO.setQtyRequired(s.getRemarks());
				scheduleVO.setProductionScheduleOrderVO(vo);

				scheduleList.add(scheduleVO);
			}
		}
		vo.setScheduleDetailsVO(scheduleList);

		vo.setTotalQty(totalQty);
	}

	private ProductionScheduleOrderResponseDTO buildProductionScheduleOrderResponse(ProductionScheduleOrderVO vo) {

		// ---------- Parent Response DTO ----------
		ProductionScheduleOrderResponseDTO responseDTO = new ProductionScheduleOrderResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setOrderType(vo.getOrderType());
		responseDTO.setLcPoNo(vo.getLcPoNo());
		responseDTO.setLcPoDate(vo.getLcPoDate());
		responseDTO.setBatchQty(vo.getBatchQty());

		if (vo.getFgItem() != null) {
			ItemMasterDetailsResponseImportDTO fgItemDTO = new ItemMasterDetailsResponseImportDTO();
			fgItemDTO.setId(vo.getFgItem().getId());
			fgItemDTO.setItemCode(vo.getFgItem().getItemCode());
			fgItemDTO.setItemDescription(vo.getFgItem().getItemDescription());
			responseDTO.setFgItem(fgItemDTO);
		}

		if (vo.getCompRouteNo() != null) {
			CompRouteNoResponseDetailsDTO fgItemDTO = new CompRouteNoResponseDetailsDTO();
			fgItemDTO.setId(vo.getCompRouteNo().getId());
			fgItemDTO.setDocId(vo.getCompRouteNo().getDocId());
			fgItemDTO.setDocDate(vo.getCompRouteNo().getDocDate());
			responseDTO.setCompRouteNo(fgItemDTO);
		}

		if (vo.getBom() != null) {
			BillOfMaterialResDTO fgItemDTO = new BillOfMaterialResDTO();
			fgItemDTO.setId(vo.getBom().getId());
			fgItemDTO.setDocId(vo.getBom().getDocId());
			fgItemDTO.setDocDate(vo.getBom().getDocDate());
			responseDTO.setBom(fgItemDTO);
		}

		responseDTO.setScheduleStartDate(vo.getScheduleStartDate());
		responseDTO.setScheduleEndDate(vo.getScheduleEndDate());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		// ---------- Branch ----------
		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		// ---------- Production Schedule Order Details ----------
		List<ProductionScheduleOrderDetailsResponseDTO> detailsList = new ArrayList<>();

		if (vo.getProductionScheduleOrderDetailsVO() != null) {

			for (ProductionScheduleOrderDetailsVO detailsVO : vo.getProductionScheduleOrderDetailsVO()) {

				ProductionScheduleOrderDetailsResponseDTO detailsDTO = new ProductionScheduleOrderDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setBomQty(detailsVO.getBomQty());
				detailsDTO.setQtyRequired(detailsVO.getQtyRequired());
				detailsDTO.setScrapQty(detailsVO.getScrapQty());

				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemMasterDetailsResponseDTO = new ItemMasterDetailsResponseImportDTO();
					itemMasterDetailsResponseDTO.setId(detailsVO.getItem().getId());
					itemMasterDetailsResponseDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemMasterDetailsResponseDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItem(itemMasterDetailsResponseDTO);
				}

				if (detailsVO.getUnit() != null) {
					UnitMasterResponseDTO unitMasterResponseDTO = new UnitMasterResponseDTO();
					unitMasterResponseDTO.setId(detailsVO.getUnit().getId());
					unitMasterResponseDTO.setUnitId(detailsVO.getUnit().getUnitId());
					unitMasterResponseDTO.setUnitDescription(detailsVO.getUnit().getDescription());
					detailsDTO.setUnit(unitMasterResponseDTO);
				}

				if (detailsVO.getScrapUnit() != null) {
					UnitMasterResponseDTO scrapUnitResponseDTO = new UnitMasterResponseDTO();
					scrapUnitResponseDTO.setId(detailsVO.getScrapUnit().getId());
					scrapUnitResponseDTO.setUnitId(detailsVO.getScrapUnit().getUnitId());
					scrapUnitResponseDTO.setUnitDescription(detailsVO.getScrapUnit().getDescription());
					detailsDTO.setScrapUnit(scrapUnitResponseDTO);
				}

				detailsList.add(detailsDTO);
			}
		}

		responseDTO.setProductionScheduleOrderDetailsResponseDTO(detailsList);

		// ---------- Schedule Details ----------
		List<ScheduleDetailsResponseDTO> scheduleList = new ArrayList<>();

		if (vo.getScheduleDetailsVO() != null) {

			for (ScheduleDetailsVO scheduleVO : vo.getScheduleDetailsVO()) {

				ScheduleDetailsResponseDTO scheduleDTO = new ScheduleDetailsResponseDTO();

				scheduleDTO.setId(scheduleVO.getId());
				scheduleDTO.setScheduleDate(scheduleVO.getScheduleDate());
				scheduleDTO.setQty(scheduleVO.getQty());
				scheduleDTO.setRemarks(scheduleVO.getQtyRequired()); // see entity fix note below

				scheduleList.add(scheduleDTO);
			}
		}

		responseDTO.setScheduleDetailsResponseDTO(scheduleList);

		return responseDTO;
	}

	@Override
	public String getProductionScheduleOrderDocId(Long orgId, String financialYear) {

		String screenCode = "PSO";

		return productionScheduleOrderRepo.getProductionScheduleOrderDocId(orgId, financialYear, screenCode);
	}

	@Override
	public ProductionScheduleOrderResponseDTO getProductionScheduleOrderById(Long id) throws ApplicationException {

		ProductionScheduleOrderVO stockTransferVO = productionScheduleOrderRepo.getProductionScheduleOrderById(id);

		if (stockTransferVO == null) {
			throw new ApplicationException("Order Not Found");
		}

		return buildProductionScheduleOrderResponse(stockTransferVO);
	}

	@Override
	public List<ProductionScheduleOrderResponseDTO> getProductionScheduleOrderByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<ProductionScheduleOrderVO> stockTransferList = productionScheduleOrderRepo
				.getProductionScheduleOrderByOrgId(orgId, branch);

		if (stockTransferList == null || stockTransferList.isEmpty()) {
			throw new ApplicationException("Stock Transfer Not Found");
		}

		List<ProductionScheduleOrderResponseDTO> responseList = new ArrayList<>();

		for (ProductionScheduleOrderVO stockTransferVO : stockTransferList) {
			responseList.add(buildProductionScheduleOrderResponse(stockTransferVO));
		}

		return responseList;
	}

	// Bom

	@Override
	@Transactional
	public Map<String, Object> createUpdateBillOfMaterial(BillOfMaterialDTO billOfMaterialDTO)
			throws ApplicationException {

		String screenCode = "BOM";
		BillOfMaterialVO billOfMaterialVO = new BillOfMaterialVO();
		String message;

		if (ObjectUtils.isNotEmpty(billOfMaterialDTO.getId())) {

			billOfMaterialVO = billOfMaterialRepo.findById(billOfMaterialDTO.getId())
					.orElseThrow(() -> new ApplicationException("Bill Of Material Not Found"));

			billOfMaterialVO.setUpdatedBy(billOfMaterialDTO.getCreatedBy());

			message = "Bill Of Material Updated Successfully";

		} else {

			String docId = billOfMaterialRepo.getBillOfMaterialDocId(billOfMaterialDTO.getOrgId(),
					billOfMaterialDTO.getFinancialYear(), screenCode);

			billOfMaterialVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(billOfMaterialDTO.getOrgId(),
							billOfMaterialDTO.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			billOfMaterialVO.setCreatedBy(billOfMaterialDTO.getCreatedBy());
			billOfMaterialVO.setUpdatedBy(billOfMaterialDTO.getCreatedBy());

			message = "Bill Of Material Created Successfully";
		}

		createUpdateBillOfMaterialVOByBillOfMaterialDTO(billOfMaterialDTO, billOfMaterialVO);

		billOfMaterialVO = billOfMaterialRepo.save(billOfMaterialVO);

		BillOfMaterialResponseDTO responseDTO = buildBillOfMaterialResponse(billOfMaterialVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("billOfMaterialVO", responseDTO);

		return response;
	}

	private void createUpdateBillOfMaterialVOByBillOfMaterialDTO(BillOfMaterialDTO dto, BillOfMaterialVO vo)
			throws ApplicationException {

		vo.setTypeOfItem(dto.getTypeOfItem());

		vo.setSpecifications(dto.getSpecifications());

		vo.setFillDetailsOf(dto.getFillDetailsOf());

		vo.setWef(dto.getWef());

		if (dto.getId() != null) {

			vo.setRevisionNo(vo.getRevisionNo() + 1);

		} else {

			vo.setRevisionNo(1);
		}

		vo.setFgReferenceToProfit(dto.getFgReferenceToProfit());

		vo.setManufacturing(dto.getManufacturing());

		vo.setRemarks(dto.getRemarks());

		vo.setActive(dto.isActive());

		vo.setCancel(dto.isCancel());

		vo.setCancelRemarks(dto.getCancelRemarks());

		vo.setCreatedBy(dto.getCreatedBy());

		vo.setOrgId(dto.getOrgId());

		if (dto.getTypeOfBom() != null && dto.getTypeOfBom() >= 0) {

			ListOfValuesDetailsVO typeOfBom = listOfValuesDetailsRepo.findById(dto.getTypeOfBom())
					.orElseThrow(() -> new ApplicationException("Type Of BOM Not Found"));

			vo.setTypeOfBom(typeOfBom);
		}

		if (dto.getFgItem() != null && dto.getFgItem() >= 0) {

			ItemMasterVO fgItem = itemMasterRepo.findById(dto.getFgItem())
					.orElseThrow(() -> new ApplicationException("FG Item Not Found"));

			vo.setFgItem(fgItem);
		}

		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			vo.setBranch(branch);
		}

		if (dto.getFillDetailsOfItem() != null && dto.getFillDetailsOfItem() != 0) {

			ItemMasterVO fillDetailsOfItem = itemMasterRepo.findById(dto.getFillDetailsOfItem())
					.orElseThrow(() -> new ApplicationException("Fill Details Of Item Not Found"));

			vo.setFillDetailsOfItem(fillDetailsOfItem);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {

			List<BillOfMaterialDetailsVO> existingDetails = billOfMaterialDetailsRepo.findByBillOfMaterialVO(vo);

			billOfMaterialDetailsRepo.deleteAll(existingDetails);
		}

		List<BillOfMaterialDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getBillOfMaterialDetailsDTO() != null) {

			for (BillOfMaterialDetailsDTO d : dto.getBillOfMaterialDetailsDTO()) {

				BillOfMaterialDetailsVO detailsVO = new BillOfMaterialDetailsVO();

				if (d.getItem() != null && d.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(item);
				}

				if (d.getUom() != null && d.getUom() != 0) {

					UnitMasterVO unit = unitMasterRepo.findById(d.getUom())
							.orElseThrow(() -> new ApplicationException("UOM Not Found"));

					detailsVO.setUom(unit);
				}

				if (d.getItem() != null && d.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(item);
				}

				if (d.getUom() != null && d.getUom() != 0) {

					UnitMasterVO unit = unitMasterRepo.findById(d.getUom())
							.orElseThrow(() -> new ApplicationException("UOM Not Found"));

					detailsVO.setUom(unit);
				}

				if (d.getScrapUnit() != null && d.getScrapUnit() != 0) {

					UnitMasterVO scrapUnit = unitMasterRepo.findById(d.getScrapUnit())
							.orElseThrow(() -> new ApplicationException("Scrap Unit Not Found"));

					detailsVO.setScrapUnit(scrapUnit);
				}

				detailsVO.setSfgBomRefNo(d.getSfgBomRefNo());

				detailsVO.setItemType(d.getItemType());

				detailsVO.setWeight(d.getWeight());

				detailsVO.setQty(d.getQty());

				detailsVO.setManbou(d.getManbou());

				detailsVO.setSfgBomRefDate(d.getSfgBomRefDate());

				detailsVO.setScrapQty(d.getScrapQty());

				detailsVO.setScrapItem(d.getScrapItem());

				detailsVO.setBillOfMaterialVO(vo);

				itemDetailsList.add(detailsVO);
			}
		}

		vo.setBillOfMaterialDetailsVO(itemDetailsList);
	}

	private BillOfMaterialResponseDTO buildBillOfMaterialResponse(BillOfMaterialVO vo) {

		BillOfMaterialResponseDTO responseDTO = new BillOfMaterialResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setTypeOfItem(vo.getTypeOfItem());
		responseDTO.setRevisionNo(vo.getRevisionNo());
		responseDTO.setSpecifications(vo.getSpecifications());
		responseDTO.setFillDetailsOf(vo.getFillDetailsOf());
		responseDTO.setWef(vo.getWef());
		responseDTO.setFgReferenceToProfit(vo.getFgReferenceToProfit());
		responseDTO.setManufacturing(vo.getManufacturing());
		responseDTO.setRemarks(vo.getRemarks());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		if (vo.getFgItem() != null) {
			ItemMasterDetailsResponseImportDTO fgItemDTO = new ItemMasterDetailsResponseImportDTO();
			fgItemDTO.setId(vo.getFgItem().getId());
			fgItemDTO.setItemCode(vo.getFgItem().getItemCode());
			fgItemDTO.setItemDescription(vo.getFgItem().getItemDescription());
			responseDTO.setFgItem(fgItemDTO);
		}

		if (vo.getTypeOfBom() != null) {
			ListOfValuesResponseDTO fgItemDTO = new ListOfValuesResponseDTO();
			fgItemDTO.setId(vo.getTypeOfBom().getId());
			fgItemDTO.setListCode(vo.getTypeOfBom().getValueCode());
			fgItemDTO.setListDescription(vo.getTypeOfBom().getValueDescription());
			responseDTO.setTypeOfBom(fgItemDTO);
		}

		if (vo.getFillDetailsOfItem() != null) {
			ItemMasterDetailsResponseImportDTO fillDetailsOfItemDTO = new ItemMasterDetailsResponseImportDTO();
			fillDetailsOfItemDTO.setId(vo.getFillDetailsOfItem().getId());
			fillDetailsOfItemDTO.setItemCode(vo.getFillDetailsOfItem().getItemCode());
			fillDetailsOfItemDTO.setItemDescription(vo.getFillDetailsOfItem().getItemDescription());
			responseDTO.setFillDetailsOfItem(fillDetailsOfItemDTO);
		}

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		// ---------- Material Details ----------
		List<BillOfMaterialDetailsResponseDTO> detailsList = new ArrayList<>();

		if (vo.getBillOfMaterialDetailsVO() != null) {

			for (BillOfMaterialDetailsVO detailsVO : vo.getBillOfMaterialDetailsVO()) {

				BillOfMaterialDetailsResponseDTO detailsDTO = new BillOfMaterialDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setItemType(detailsVO.getItemType());
				detailsDTO.setWeight(detailsVO.getWeight());
				detailsDTO.setQty(detailsVO.getQty());
				detailsDTO.setManbou(detailsVO.getManbou());
				detailsDTO.setSfgBomRefDate(detailsVO.getSfgBomRefDate());
				detailsDTO.setScrapQty(detailsVO.getScrapQty());
				detailsDTO.setScrapItem(detailsVO.getScrapItem());

				// ---------- Item ----------
				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
					itemDTO.setId(detailsVO.getItem().getId());
					itemDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItem(itemDTO);
				}

				// ---------- UOM ----------
				if (detailsVO.getUom() != null) {
					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();
					unitDTO.setId(detailsVO.getUom().getId());
					unitDTO.setUnitId(detailsVO.getUom().getUnitId());
					unitDTO.setUnitDescription(detailsVO.getUom().getDescription());
					detailsDTO.setUom(unitDTO);
				}

				// ---------- Scrap Unit ----------
				if (detailsVO.getScrapUnit() != null) {
					UnitMasterResponseDTO scrapUnitDTO = new UnitMasterResponseDTO();
					scrapUnitDTO.setId(detailsVO.getScrapUnit().getId());
					scrapUnitDTO.setUnitId(detailsVO.getScrapUnit().getUnitId());
					scrapUnitDTO.setUnitDescription(detailsVO.getScrapUnit().getDescription());
					detailsDTO.setScrapUnit(scrapUnitDTO);
				}

				// ---------- SFG BOM Reference No ----------
				if (detailsVO.getSfgBomRefNo() != null) {
					detailsDTO.setSfgBomRefNo(detailsVO.getSfgBomRefNo());
				}

				detailsList.add(detailsDTO);
			}
		}

		responseDTO.setBillOfMaterialDetailsResponseDTO(detailsList);

		return responseDTO;
	}

	@Override
	public String getBillOfMaterialDocId(Long orgId, String financialYear) {

		String screenCode = "BOM";

		return billOfMaterialRepo.getBillOfMaterialDocId(orgId, financialYear, screenCode);
	}

	@Override
	public BillOfMaterialResponseDTO getBillOfMaterialById(Long id) throws ApplicationException {

		BillOfMaterialVO billOfMaterialVO = billOfMaterialRepo.getBillOfMaterialById(id);

		if (billOfMaterialVO == null) {
			throw new ApplicationException("Bill Of Material Not Found");
		}

		return buildBillOfMaterialResponse(billOfMaterialVO);
	}

	@Override
	public List<BillOfMaterialResponseDTO> getBillOfMaterialByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<BillOfMaterialVO> billOfMaterialList = billOfMaterialRepo.getBillOfMaterialByOrgId(orgId, branch);

		if (billOfMaterialList == null || billOfMaterialList.isEmpty()) {
			throw new ApplicationException("Bill Of Material Not Found");
		}

		List<BillOfMaterialResponseDTO> responseList = new ArrayList<>();

		for (BillOfMaterialVO billOfMaterialVO : billOfMaterialList) {
			responseList.add(buildBillOfMaterialResponse(billOfMaterialVO));
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getFgAndSfgItemDetails(Long orgId, Long branch, String type) {
		Set<Object[]> chType = billOfMaterialRepo.getFgAndSfgItemDetails(orgId, branch, type);
		return getFgAndSfgItemDetails(chType);
	}

	private List<Map<String, Object>> getFgAndSfgItemDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("profit", ch[3] != null ? ch[3].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getGridDetailsFromBom(Long orgId, Long branch) {
		Set<Object[]> chType = billOfMaterialRepo.getGridDetailsFromBom(orgId, branch);
		return getGridDetailsFromBom(chType);
	}

	private List<Map<String, Object>> getGridDetailsFromBom(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("itemType", ch[3] != null ? ch[3].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getScrapDetailsItem(Long orgId, Long branch) {
		Set<Object[]> chType = billOfMaterialRepo.getScrapDetailsItem(orgId, branch);
		return getScrapDetailsItem(chType);
	}

	private List<Map<String, Object>> getScrapDetailsItem(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("unit", ch[3] != null ? ((Number) ch[3]).longValue() : null);
			map.put("unitDescription", ch[4] != null ? ch[4].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSfGDocIdAndDetails(Long orgId, Long branch) {
		Set<Object[]> chType = billOfMaterialRepo.getSfGDocIdAndDetails(orgId, branch);
		return getSfGDocIdAndDetails(chType);
	}

	private List<Map<String, Object>> getSfGDocIdAndDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getFillDetailsOf(Long orgId, Long branch, Long fgItem) {
		Set<Object[]> chType = billOfMaterialRepo.getFillDetailsOf(orgId, branch, fgItem);
		return getFillDetailsOf(chType);
	}

	private List<Map<String, Object>> getFillDetailsOf(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getFgAndSfgItemDetailsFromProduction(Long orgId, Long branch) {
		Set<Object[]> chType = productionScheduleOrderRepo.getFgAndSfgItemDetailsFromProduction(orgId, branch);
		return getFgAndSfgItemDetailsFromProduction(chType);
	}

	private List<Map<String, Object>> getFgAndSfgItemDetailsFromProduction(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getFgAndSfgItemDetailsFromProductionDetails(Long orgId, Long branch, Long bom) {
		Set<Object[]> chType = productionScheduleOrderRepo.getFgAndSfgItemDetailsFromProductionDetails(orgId, branch,
				bom);
		return getFgAndSfgItemDetailsFromProductionDetails(chType);
	}

	private List<Map<String, Object>> getFgAndSfgItemDetailsFromProductionDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("itemType", ch[3] != null ? ch[3].toString() : "");
			list.add(map);
		}

		return list;
	}

	//

	@Override
	@Transactional
	public Map<String, Object> createUpdateMaterialIndentForProduction(MaterialIndentForProductionDTO dto)
			throws ApplicationException {

		String screenCode = "MIP";
		MaterialIndentForProductionVO vo = new MaterialIndentForProductionVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {

			vo = materialIndentForProductionRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Material Indent For Production Not Found"));

			vo.setUpdatedBy(dto.getCreatedBy());

			message = "Material Indent For Production Updated Successfully";

		} else {

			String docId = materialIndentForProductionRepo.getMaterialIndentForProductionDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);

			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());

			message = "Material Indent For Production Created Successfully";
		}

		createUpdateMaterialIndentForProductionVOByDTO(dto, vo);

		vo = materialIndentForProductionRepo.save(vo);

		MaterialIndentForProductionResponseDTO responseDTO = buildMaterialIndentForProductionResponse(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("materialIndentForProductionVO", responseDTO);

		return response;
	}

	private void createUpdateMaterialIndentForProductionVOByDTO(MaterialIndentForProductionDTO dto,
			MaterialIndentForProductionVO vo) throws ApplicationException {

		vo.setSchOrderNo(dto.getSchOrderNo());
		vo.setBelongsTo(dto.getBelongsTo());
		vo.setSchQty(dto.getSchQty());
		vo.setScheduledDate(dto.getScheduledDate());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setCreatedBy(dto.getCreatedBy());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getDepartment() != null && dto.getDepartment() != 0) {
			DepartmentVO department = departmentRepo.findById(dto.getDepartment())
					.orElseThrow(() -> new ApplicationException("Department Not Found"));
			vo.setDepartment(department);
		}

		if (dto.getFgItem() != null && dto.getFgItem() != 0) {
			ItemMasterVO fgItem = itemMasterRepo.findById(dto.getFgItem())
					.orElseThrow(() -> new ApplicationException("FG Item Not Found"));
			vo.setFgItem(fgItem);
		}

		if (dto.getToLocation() != null && dto.getToLocation() != 0) {
			LocationVO toLocation = locationRepo.findById(dto.getToLocation())
					.orElseThrow(() -> new ApplicationException("To Location Not Found"));
			vo.setToLocation(toLocation);
		}

		if (dto.getFromLocation() != null && dto.getFromLocation() != 0) {
			LocationVO fromLocation = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("From Location Not Found"));
			vo.setFromLocation(fromLocation);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {
			EmployeeMasterVO branch = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("PreparedBy Not Found"));
			vo.setPreparedBy(branch);
		}

		if (dto.getAuthorisedBy() != null && dto.getAuthorisedBy() != 0) {
			EmployeeMasterVO branch = employeeMasterRepo.findById(dto.getAuthorisedBy())
					.orElseThrow(() -> new ApplicationException("AuthorisedBy Not Found"));
			vo.setAuthorisedBy(branch);
		}

		// Delete existing details if updating
		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<MaterialIndentForProductionDetailsVO> existingDetails = materialIndentForProductionDetailsRepo
					.findByMaterialIndentForProductionVO(vo);
			materialIndentForProductionDetailsRepo.deleteAll(existingDetails);
		}

		List<MaterialIndentForProductionDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getMaterialIndentForProductionDetailsDTO() != null) {

			for (MaterialIndentForProductionDetailsDTO d : dto.getMaterialIndentForProductionDetailsDTO()) {

				MaterialIndentForProductionDetailsVO detailsVO = new MaterialIndentForProductionDetailsVO();

				detailsVO.setSchQty(d.getSchQty());
				detailsVO.setStockAvailable(d.getStockAvailable());
				detailsVO.setRequiredQty(d.getRequiredQty());

				if (d.getItem() != null && d.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));
					detailsVO.setItem(item);
				}

				if (d.getUnit() != null && d.getUnit() != 0) {
					UnitMasterVO item = unitMasterRepo.findById(d.getUnit())
							.orElseThrow(() -> new ApplicationException("Unti Not Found"));
					detailsVO.setUnit(item);
				}

				detailsVO.setMaterialIndentForProductionVO(vo);

				itemDetailsList.add(detailsVO);
			}
		}

		vo.setMaterialIndentForProductionDetailsVO(itemDetailsList);
	}

	private MaterialIndentForProductionResponseDTO buildMaterialIndentForProductionResponse(
			MaterialIndentForProductionVO vo) {

		MaterialIndentForProductionResponseDTO responseDTO = new MaterialIndentForProductionResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setSchOrderNo(vo.getSchOrderNo());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setSchQty(vo.getSchQty());
		responseDTO.setScheduledDate(vo.getScheduledDate());
		responseDTO.setIndentTime(vo.getIndentTime());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());
		responseDTO.setRemarks(vo.getRemarks());
		responseDTO.setApprovedBy(vo.getApprovedBy());

		// Department
		if (vo.getDepartment() != null) {
			DepartmentResponseDTO deptDTO = new DepartmentResponseDTO();
			deptDTO.setId(vo.getDepartment().getId());
			deptDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());
			deptDTO.setDepartmentName(vo.getDepartment().getDepartmentName());
			responseDTO.setDepartment(deptDTO);
		}

		// FG Item
		if (vo.getFgItem() != null) {
			ItemMasterDetailsResponseImportDTO fgItemDTO = new ItemMasterDetailsResponseImportDTO();
			fgItemDTO.setId(vo.getFgItem().getId());
			fgItemDTO.setItemCode(vo.getFgItem().getItemCode());
			fgItemDTO.setItemDescription(vo.getFgItem().getItemDescription());
			responseDTO.setFgItem(fgItemDTO);
		}

		// To Location
		if (vo.getToLocation() != null) {
			LocationMasterResponseDTO toLocationDTO = new LocationMasterResponseDTO();
			toLocationDTO.setId(vo.getToLocation().getId());
			toLocationDTO.setLocationName(vo.getToLocation().getLocationName());
			responseDTO.setToLocation(toLocationDTO);
		}

		if (vo.getFromLocation() != null) {
			LocationMasterResponseDTO fromLocationDTO = new LocationMasterResponseDTO();
			fromLocationDTO.setId(vo.getFromLocation().getId());
			fromLocationDTO.setLocationName(vo.getFromLocation().getLocationName());
			responseDTO.setFromLocation(fromLocationDTO);
		}

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}
		if (vo.getPreparedBy() != null) {
			EmployeeMasterResponseDetailsDTO branchDTO = new EmployeeMasterResponseDetailsDTO();
			branchDTO.setId(vo.getPreparedBy().getId());
			branchDTO.setEmployeeCode(vo.getPreparedBy().getEmployeeId());
			branchDTO.setEmployeeName(vo.getPreparedBy().getEmployeeName());
			responseDTO.setPreparedBy(branchDTO);
		}

		if (vo.getAuthorisedBy() != null) {
			EmployeeMasterResponseDetailsDTO branchDTO = new EmployeeMasterResponseDetailsDTO();
			branchDTO.setId(vo.getAuthorisedBy().getId());
			branchDTO.setEmployeeCode(vo.getAuthorisedBy().getEmployeeId());
			branchDTO.setEmployeeName(vo.getAuthorisedBy().getEmployeeName());
			responseDTO.setAuthorisedBy(branchDTO);
		}

		List<MaterialIndentForProductionDetailsResponseDTO> detailsList = new ArrayList<>();

		if (vo.getMaterialIndentForProductionDetailsVO() != null) {

			for (MaterialIndentForProductionDetailsVO detailsVO : vo.getMaterialIndentForProductionDetailsVO()) {

				MaterialIndentForProductionDetailsResponseDTO detailsDTO = new MaterialIndentForProductionDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setSchQty(detailsVO.getSchQty());
				detailsDTO.setStockAvailable(detailsVO.getStockAvailable());
				detailsDTO.setRequiredQty(detailsVO.getRequiredQty());

				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
					itemDTO.setId(detailsVO.getItem().getId());
					itemDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItemCode(itemDTO);
				}

				if (detailsVO.getUnit() != null) {
					UnitResponseDTO itemDTO = new UnitResponseDTO();
					itemDTO.setId(detailsVO.getUnit().getId());
					itemDTO.setUnitId(detailsVO.getUnit().getUnitId());
					detailsDTO.setUnit(itemDTO);
				}

				detailsList.add(detailsDTO);
			}
		}

		responseDTO.setMaterialIndentForProductionDetailsResponseDTO(detailsList);

		return responseDTO;
	}

	@Override
	public String getMaterialIndentForProductionDocId(Long orgId, String financialYear) {
		String screenCode = "MIP";
		return materialIndentForProductionRepo.getMaterialIndentForProductionDocId(orgId, financialYear, screenCode);
	}

	@Override
	public MaterialIndentForProductionResponseDTO getMaterialIndentForProductionById(Long id)
			throws ApplicationException {
		MaterialIndentForProductionVO vo = materialIndentForProductionRepo.getMaterialIndentForProductionById(id);

		if (vo == null) {
			throw new ApplicationException("Material Indent For Production Not Found");
		}

		return buildMaterialIndentForProductionResponse(vo);
	}

	@Override
	public List<MaterialIndentForProductionResponseDTO> getMaterialIndentForProductionByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<MaterialIndentForProductionVO> list = materialIndentForProductionRepo
				.getMaterialIndentForProductionByOrgId(orgId, branch);

		if (list == null || list.isEmpty()) {
			throw new ApplicationException("Material Indent For Production Not Found");
		}

		List<MaterialIndentForProductionResponseDTO> responseList = new ArrayList<>();

		for (MaterialIndentForProductionVO vo : list) {
			responseList.add(buildMaterialIndentForProductionResponse(vo));
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getFgAndSfgItemDetailsFromMaterial(Long orgId, Long branch) {
		Set<Object[]> chType = materialIndentForProductionRepo.getFgAndSfgItemDetailsFromMaterial(orgId, branch);
		return getFgAndSfgItemDetailsFromMaterial(chType);
	}

	private List<Map<String, Object>> getFgAndSfgItemDetailsFromMaterial(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("fgItemId", ch[2] != null ? ((Number) ch[2]).longValue() : null);
			map.put("itemCode", ch[3] != null ? ch[3].toString() : "");
			map.put("itemDescription", ch[4] != null ? ch[4].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getFgAndSfgItemDetailsFromMaterialDetails(Long orgId, Long branch, Long fgItem) {
		Set<Object[]> chType = materialIndentForProductionRepo.getFgAndSfgItemDetailsFromMaterialDetails(orgId, branch,
				fgItem);
		return getFgAndSfgItemDetailsFromMaterialDetails(chType);
	}

	private List<Map<String, Object>> getFgAndSfgItemDetailsFromMaterialDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);
			list.add(map);
		}

		return list;
	}

	// PurchaseService

	@Override
	@Transactional
	public Map<String, Object> createUpdateProductionTransferSlip(ProductionTransferSlipDTO dto)
			throws ApplicationException {
		String screenCode = "PTS";
		ProductionTransferSlipVO vo = new ProductionTransferSlipVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {
			vo = productionTransferSlipRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Production Transfer Slip Not Found"));
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Production Transfer Slip Updated Successfully";
		} else {
			String docId = productionTransferSlipRepo.getProductionTransferSlipDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);
			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Production Transfer Slip Created Successfully";
		}

		createUpdateProductionTransferSlipVOByDTO(dto, vo);
		vo = productionTransferSlipRepo.save(vo);

		ProductionTransferSlipResponseDTO responseDTO = buildProductionTransferSlipResponse(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("productionTransferSlipVO", responseDTO);

		return response;
	}

	private void createUpdateProductionTransferSlipVOByDTO(ProductionTransferSlipDTO dto, ProductionTransferSlipVO vo)
			throws ApplicationException {

		vo.setBelongsTo(dto.getBelongsTo());
		vo.setSfgDescription(dto.getSfgDescription());
		vo.setSchOrderNo(dto.getSchOrderNo());
		vo.setSchDates(dto.getSchDates());
		vo.setAlterInputItem(dto.getAlterInputItem());
		vo.setItemType(dto.getItemType());
		vo.setIssueQty(dto.getIssueQty());
		vo.setUnit(dto.getUnit());
		vo.setValue(dto.getValue());
		vo.setRate(dto.getRate());
		vo.setRemarks(dto.getRemarks());
		vo.setTotalValue(dto.getTotalValue());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getFromLocation() != null && dto.getFromLocation() != 0) {
			LocationVO fromLocation = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("From Location Not Found"));
			vo.setFromLocation(fromLocation);
		}

		if (dto.getToLocation() != null && dto.getToLocation() != 0) {
			LocationVO toLocation = locationRepo.findById(dto.getToLocation())
					.orElseThrow(() -> new ApplicationException("To Location Not Found"));
			vo.setToLocation(toLocation);
		}

		if (dto.getScrapToLocation() != null && dto.getScrapToLocation() != 0) {
			LocationVO scrapToLocation = locationRepo.findById(dto.getScrapToLocation())
					.orElseThrow(() -> new ApplicationException("Scrap To Location Not Found"));
			vo.setScrapToLocation(scrapToLocation);
		}

		if (dto.getFgPartNo() != null && dto.getFgPartNo() != 0) {
			ItemMasterVO fgItem = itemMasterRepo.findById(dto.getFgPartNo())
					.orElseThrow(() -> new ApplicationException("FG Part No Not Found"));
			vo.setFgPartNo(fgItem);
		}

		if (dto.getSfgPartNo() != null && dto.getSfgPartNo() != 0) {
			ItemMasterVO sfgItem = itemMasterRepo.findById(dto.getSfgPartNo())
					.orElseThrow(() -> new ApplicationException("SFG Part No Not Found"));
			vo.setSfgPartNo(sfgItem);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (dto.getBom() != null && dto.getBom() != 0) {
			BillOfMaterialVO branch = billOfMaterialRepo.findById(dto.getBom())
					.orElseThrow(() -> new ApplicationException("Bom Not Found"));
			vo.setBom(branch);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<ProductionTransferSlipDetailsVO> existingDetails = productionTransferSlipDetailsRepo
					.findByProductionTransferSlipVO(vo);
			productionTransferSlipDetailsRepo.deleteAll(existingDetails);
		}

		List<ProductionTransferSlipDetailsVO> itemDetailsList = new ArrayList<>();

		BigDecimal totalQty = BigDecimal.ZERO;

		if (dto.getProductionTransferSlipDetailsDTO() != null) {
			for (ProductionTransferSlipDetailsDTO d : dto.getProductionTransferSlipDetailsDTO()) {
				ProductionTransferSlipDetailsVO detailsVO = new ProductionTransferSlipDetailsVO();
				detailsVO.setStock(d.getStock());
				detailsVO.setBomQty(d.getBomQty());
				BigDecimal qty = dto.getIssueQty();
				detailsVO.setInputQty(qty.multiply(d.getBomQty()));

				detailsVO.setRate(d.getRate());
				detailsVO.setValue(d.getRate().multiply(d.getBomQty()));
				totalQty = totalQty.add(detailsVO.getValue());

				detailsVO.setScrapQty(d.getScrapQty());
				detailsVO.setScrapTotal(d.getScrapQty());

				if (d.getItem() != null && d.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("Input Item Code Not Found"));
					detailsVO.setItem(item);
				}

				if (d.getPrimaryUnit() != null && d.getPrimaryUnit() != 0) {
					UnitMasterVO unit = unitMasterRepo.findById(d.getPrimaryUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));
					detailsVO.setPrimaryUnit(unit);
				}

				if (d.getScrap() != null && d.getScrap() != 0) {
					ListOfValuesDetailsVO item = listOfValuesDetailsRepo.findById(d.getScrap())
							.orElseThrow(() -> new ApplicationException("Scrap Not Found"));
					detailsVO.setScrap(item);
				}

				detailsVO.setProductionTransferSlipVO(vo);
				itemDetailsList.add(detailsVO);
			}
		}
		vo.setProductionTransferSlipDetailsVO(itemDetailsList);
		vo.setTotalValue(totalQty);

	}

	private ProductionTransferSlipResponseDTO buildProductionTransferSlipResponse(ProductionTransferSlipVO vo) {
		ProductionTransferSlipResponseDTO responseDTO = new ProductionTransferSlipResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setSfgDescription(vo.getSfgDescription());
		responseDTO.setSchOrderNo(vo.getSchOrderNo());
		responseDTO.setSchDates(vo.getSchDates());
		responseDTO.setAlterInputItem(vo.getAlterInputItem());
		responseDTO.setItemType(vo.getItemType());
		responseDTO.setIssueQty(vo.getIssueQty());
		responseDTO.setUnit(vo.getUnit());
		responseDTO.setValue(vo.getValue());
		responseDTO.setRate(vo.getRate());
		responseDTO.setTotalValue(vo.getTotalValue());
		responseDTO.setRemarks(vo.getRemarks());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		if (vo.getFromLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getFromLocation().getId());
			locDto.setLocationName(vo.getFromLocation().getLocationName());
			responseDTO.setFromLocation(locDto);
		}

		if (vo.getBom() != null) {
			BomFgResponseDTO locDto = new BomFgResponseDTO();
			locDto.setId(vo.getBom().getId());
			locDto.setDocId(vo.getBom().getDocId());
			locDto.setDocDate(vo.getBom().getDocDate());
			responseDTO.setBom(locDto);
		}

		if (vo.getToLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getToLocation().getId());
			locDto.setLocationName(vo.getToLocation().getLocationName());
			responseDTO.setToLocation(locDto);
		}

		if (vo.getScrapToLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getScrapToLocation().getId());
			locDto.setLocationName(vo.getScrapToLocation().getLocationName());
			responseDTO.setScrapToLocation(locDto);
		}

		if (vo.getFgPartNo() != null) {
			ItemMasterDetailsResponseImportDTO itemDto = new ItemMasterDetailsResponseImportDTO();
			itemDto.setId(vo.getFgPartNo().getId());
			itemDto.setItemCode(vo.getFgPartNo().getItemCode());
			itemDto.setItemDescription(vo.getFgPartNo().getItemDescription());
			responseDTO.setFgPartNo(itemDto);
		}

		if (vo.getSfgPartNo() != null) {
			ItemMasterDetailsResponseImportDTO itemDto = new ItemMasterDetailsResponseImportDTO();
			itemDto.setId(vo.getSfgPartNo().getId());
			itemDto.setItemCode(vo.getSfgPartNo().getItemCode());
			itemDto.setItemDescription(vo.getSfgPartNo().getItemDescription());
			responseDTO.setSfgPartNo(itemDto);
		}

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		List<ProductionTransferSlipDetailsResponseDTO> detailsList = new ArrayList<>();
		if (vo.getProductionTransferSlipDetailsVO() != null) {
			for (ProductionTransferSlipDetailsVO detailsVO : vo.getProductionTransferSlipDetailsVO()) {
				ProductionTransferSlipDetailsResponseDTO detailsDTO = new ProductionTransferSlipDetailsResponseDTO();
				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setStock(detailsVO.getStock());
				detailsDTO.setBomQty(detailsVO.getBomQty());
				detailsDTO.setInputQty(detailsVO.getInputQty());
				detailsDTO.setRate(detailsVO.getRate());
				detailsDTO.setValue(detailsVO.getValue());

				if (detailsVO.getScrap() != null) {
					ListOfValuesResponseDTO fgItemDTO = new ListOfValuesResponseDTO();
					fgItemDTO.setId(detailsVO.getScrap().getId());
					fgItemDTO.setListCode(detailsVO.getScrap().getValueCode());
					fgItemDTO.setListDescription(detailsVO.getScrap().getValueDescription());
					detailsDTO.setScrap(fgItemDTO);
				}

				detailsDTO.setScrapQty(detailsVO.getScrapQty());
				detailsDTO.setScrapTotal(detailsVO.getScrapTotal());

				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
					itemDTO.setId(detailsVO.getItem().getId());
					itemDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItem(itemDTO);
				}

				if (detailsVO.getPrimaryUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailsVO.getPrimaryUnit().getId());
					unitDTO.setUnitId(detailsVO.getPrimaryUnit().getUnitId());
					detailsDTO.setPrimaryUnit(unitDTO);
				}

				detailsList.add(detailsDTO);
			}
		}
		responseDTO.setProductionTransferSlipDetailsResponseDTO(detailsList);
		return responseDTO;
	}

	@Override
	public String getProductionTransferSlipDocId(Long orgId, String financialYear) {
		String screenCode = "PTS";
		return productionTransferSlipRepo.getProductionTransferSlipDocId(orgId, financialYear, screenCode);
	}

	@Override
	public ProductionTransferSlipResponseDTO getProductionTransferSlipById(Long id) throws ApplicationException {
		ProductionTransferSlipVO vo = productionTransferSlipRepo.getProductionTransferSlipById(id);
		if (vo == null) {
			throw new ApplicationException("Production Transfer Slip Not Found");
		}
		return buildProductionTransferSlipResponse(vo);
	}

	@Override
	public List<ProductionTransferSlipResponseDTO> getProductionTransferSlipByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<ProductionTransferSlipVO> list = productionTransferSlipRepo.getProductionTransferSlipByOrgId(orgId,
				branch);
		if (list == null || list.isEmpty()) {
			throw new ApplicationException("Production Transfer Slip Not Found");
		}
		List<ProductionTransferSlipResponseDTO> responseList = new ArrayList<>();
		for (ProductionTransferSlipVO vo : list) {
			responseList.add(buildProductionTransferSlipResponse(vo));
		}
		return responseList;
	}

	@Override
	public List<Map<String, Object>> getFgPartNoDetails(Long orgId, Long branch) {
		Set<Object[]> chType = productionTransferSlipRepo.getFgPartNoDetails(orgId, branch);
		return getFgPartNoDetails(chType);
	}

	private List<Map<String, Object>> getFgPartNoDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSfgPartNoDetails(Long orgId, Long branch) {
		Set<Object[]> chType = productionTransferSlipRepo.getSfgPartNoDetails(orgId, branch);
		return getSfgPartNoDetails(chType);
	}

	private List<Map<String, Object>> getSfgPartNoDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSchNoFromTransferSlip(Long orgId, Long branch, Long fgItem, Long sfgItem) {
		Set<Object[]> chType = productionTransferSlipRepo.getSchNoFromTransferSlip(orgId, branch, fgItem, sfgItem);
		return getSchNoFromTransferSlip(chType);
	}

	private List<Map<String, Object>> getSchNoFromTransferSlip(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getBomNoFromTransferSlip(Long orgId, Long branch, Long fgItem, Long sfgItem) {
		Set<Object[]> chType = productionTransferSlipRepo.getBomNoFromTransferSlip(orgId, branch, fgItem, sfgItem);
		return getBomNoFromTransferSlip(chType);
	}

	private List<Map<String, Object>> getBomNoFromTransferSlip(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("bomId", ch[2] != null ? ((Number) ch[2]).longValue() : null);
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getBomNoFromTransferSlipDetails(Long orgId, Long branch, Long bom) {
		Set<Object[]> chType = productionTransferSlipRepo.getBomNoFromTransferSlipDetails(orgId, branch, bom);
		return getBomNoFromTransferSlipDetails(chType);
	}

	private List<Map<String, Object>> getBomNoFromTransferSlipDetails(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("itemType", ch[3] != null ? ch[3].toString() : "");
			map.put("qty", ch[4] != null ? new BigDecimal(ch[4].toString()) : BigDecimal.ZERO);
			list.add(map);
		}

		return list;
	}

	//

	@Override
	@Transactional
	public Map<String, Object> createUpdateFgTransferSlip(FgTransferSlipDTO dto) throws ApplicationException {
		String screenCode = "FGTS";
		FgTransferSlipVO vo = new FgTransferSlipVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {
			vo = fgTransferSlipRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("FG Transfer Slip Not Found"));
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "FG Transfer Slip Updated Successfully";
		} else {
			String docId = fgTransferSlipRepo.getFgTransferSlipDocId(dto.getOrgId(), dto.getFinancialYear(),
					screenCode);
			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "FG Transfer Slip Created Successfully";
		}

		createUpdateFgTransferSlipVOByDTO(dto, vo);
		vo = fgTransferSlipRepo.save(vo);

		FgTransferSlipResponseDTO responseDTO = buildFgTransferSlipResponse(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("fgTransferSlipVO", responseDTO);

		return response;
	}

	private void createUpdateFgTransferSlipVOByDTO(FgTransferSlipDTO dto, FgTransferSlipVO vo)
			throws ApplicationException {

		vo.setBelongsTo(dto.getBelongsTo());
		vo.setTransferNo(dto.getTransferNo());
		vo.setTransferDate(dto.getTransferDate());
		vo.setScheduleNo(dto.getScheduleNo());
		vo.setScheduleDate(dto.getScheduleDate());
		vo.setScheduledQty(dto.getScheduledQty());
		vo.setQtyForInspection(dto.getQtyForInspection());
		vo.setRate(dto.getRate());
		vo.setRemarks(dto.getRemarks());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getBom() != null && dto.getBom() != 0) {
			BillOfMaterialVO branch = billOfMaterialRepo.findById(dto.getBom())
					.orElseThrow(() -> new ApplicationException("Bom Not Found"));
			vo.setBom(branch);
		}

		if (dto.getFromLocation() != null && dto.getFromLocation() != 0) {
			LocationVO fromLocation = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("From Location Not Found"));
			vo.setFromLocation(fromLocation);
		}

		if (dto.getToLocation() != null && dto.getToLocation() != 0) {
			LocationVO toLocation = locationRepo.findById(dto.getToLocation())
					.orElseThrow(() -> new ApplicationException("To Location Not Found"));
			vo.setToLocation(toLocation);
		}

		if (dto.getScrapLocation() != null && dto.getScrapLocation() != 0) {
			LocationVO scrapLocation = locationRepo.findById(dto.getScrapLocation())
					.orElseThrow(() -> new ApplicationException("Scrap Location Not Found"));
			vo.setScrapLocation(scrapLocation);
		}

		if (dto.getFgItem() != null && dto.getFgItem() != 0) {
			ItemMasterVO fgItem = itemMasterRepo.findById(dto.getFgItem())
					.orElseThrow(() -> new ApplicationException("FG Item Not Found"));
			vo.setFgItem(fgItem);
		}

		if (dto.getCustomer() != null && dto.getCustomer() != 0) {
			CustomerVO customer = customerRepo.findById(dto.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));
			vo.setCustomer(customer);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<FGTransferSlipDetailsVO> existingDetails = fgTransferSlipDetailsRepo.findByFgTransferSlipVO(vo);
			fgTransferSlipDetailsRepo.deleteAll(existingDetails);
		}

		BigDecimal totalQty = BigDecimal.ZERO;

		List<FGTransferSlipDetailsVO> itemDetailsList = new ArrayList<>();

		if (dto.getFgTransferSlipDetailsDTO() != null) {
			for (FGTransferSlipDetailsDTO d : dto.getFgTransferSlipDetailsDTO()) {
				FGTransferSlipDetailsVO detailsVO = new FGTransferSlipDetailsVO();
				detailsVO.setBomQty(d.getBomQty());
				detailsVO.setAvailableStock(d.getAvailableStock());
				BigDecimal qty = dto.getScheduledQty();
				detailsVO.setConsumptionAsPerBom(qty.multiply(d.getBomQty()));
				detailsVO.setWastageQty(d.getWastageQty());
				detailsVO.setConsumedQty(detailsVO.getConsumptionAsPerBom());
				totalQty = totalQty.add(detailsVO.getConsumedQty());
				detailsVO.setRate(d.getRate());
				detailsVO.setValue(d.getRate().multiply(detailsVO.getConsumedQty()));

				detailsVO.setScrapQty(d.getScrapQty());
				detailsVO.setScrapTotal(d.getScrapQty());

				if (d.getItem() != null && d.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));
					detailsVO.setItem(item);
				}

				if (d.getUnit() != null && d.getUnit() != 0) {
					UnitMasterVO unit = unitMasterRepo.findById(d.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));
					detailsVO.setUnit(unit);
				}

				if (d.getScrapUnit() != null && d.getScrapUnit() != 0) {
					UnitMasterVO scrapUnit = unitMasterRepo.findById(d.getScrapUnit())
							.orElseThrow(() -> new ApplicationException("Scrap Unit Not Found"));
					detailsVO.setScrapUnit(scrapUnit);
				}

				if (d.getScrap() != null && d.getScrap() != 0) {
					ListOfValuesDetailsVO item = listOfValuesDetailsRepo.findById(d.getScrap())
							.orElseThrow(() -> new ApplicationException("Scrap Not Found"));
					detailsVO.setScrap(item);
				}

				detailsVO.setFgTransferSlipVO(vo);
				itemDetailsList.add(detailsVO);
			}
		}
		vo.setFgTransferSlipDetailsVO(itemDetailsList);
		vo.setTotalQty(totalQty);
	}

	private FgTransferSlipResponseDTO buildFgTransferSlipResponse(FgTransferSlipVO vo) {
		FgTransferSlipResponseDTO responseDTO = new FgTransferSlipResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setTransferNo(vo.getTransferNo());
		responseDTO.setTransferDate(vo.getTransferDate());
		responseDTO.setScheduleNo(vo.getScheduleNo());
		responseDTO.setScheduleDate(vo.getScheduleDate());
		responseDTO.setScheduledQty(vo.getScheduledQty());
		responseDTO.setQtyForInspection(vo.getQtyForInspection());
		responseDTO.setRate(vo.getRate());
		responseDTO.setTotalQty(vo.getTotalQty());
		responseDTO.setRemarks(vo.getRemarks());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		if (vo.getBom() != null) {
			BomFgResponseDTO locDto = new BomFgResponseDTO();
			locDto.setId(vo.getBom().getId());
			locDto.setDocId(vo.getBom().getDocId());
			locDto.setDocDate(vo.getBom().getDocDate());
			responseDTO.setBom(locDto);
		}

		if (vo.getFromLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getFromLocation().getId());
			locDto.setLocationName(vo.getFromLocation().getLocationName());
			responseDTO.setFromLocation(locDto);
		}

		if (vo.getToLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getToLocation().getId());
			locDto.setLocationName(vo.getToLocation().getLocationName());
			responseDTO.setToLocation(locDto);
		}

		if (vo.getScrapLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getScrapLocation().getId());
			locDto.setLocationName(vo.getScrapLocation().getLocationName());
			responseDTO.setScrapLocation(locDto);
		}

		if (vo.getFgItem() != null) {
			ItemMasterDetailsResponseImportDTO itemDto = new ItemMasterDetailsResponseImportDTO();
			itemDto.setId(vo.getFgItem().getId());
			itemDto.setItemCode(vo.getFgItem().getItemCode());
			itemDto.setItemDescription(vo.getFgItem().getItemDescription());
			responseDTO.setFgItem(itemDto);
		}

		if (vo.getCustomer() != null) {
			CustomerResponseGstDetailsDTO customerDTO = new CustomerResponseGstDetailsDTO();
			customerDTO.setId(vo.getCustomer().getId());
			customerDTO.setCustomerCode(vo.getCustomer().getCustomerCode());
			customerDTO.setCustomerName(vo.getCustomer().getCustomerName());
			responseDTO.setCustomer(customerDTO);
		}

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		List<FGTransferSlipDetailsResponseDTO> detailsList = new ArrayList<>();
		if (vo.getFgTransferSlipDetailsVO() != null) {
			for (FGTransferSlipDetailsVO detailsVO : vo.getFgTransferSlipDetailsVO()) {
				FGTransferSlipDetailsResponseDTO detailsDTO = new FGTransferSlipDetailsResponseDTO();
				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setBomQty(detailsVO.getBomQty());
				detailsDTO.setAvailableStock(detailsVO.getAvailableStock());
				detailsDTO.setConsumptionAsPerBom(detailsVO.getConsumptionAsPerBom());
				detailsDTO.setWastageQty(detailsVO.getWastageQty());
				detailsDTO.setConsumedQty(detailsVO.getConsumedQty());
				detailsDTO.setRate(detailsVO.getRate());
				detailsDTO.setValue(detailsVO.getValue());
				detailsDTO.setScrapQty(detailsVO.getScrapQty());
				detailsDTO.setScrapTotal(detailsVO.getScrapTotal());

				if (detailsVO.getScrap() != null) {
					ListOfValuesResponseDTO fgItemDTO = new ListOfValuesResponseDTO();
					fgItemDTO.setId(detailsVO.getScrap().getId());
					fgItemDTO.setListCode(detailsVO.getScrap().getValueCode());
					fgItemDTO.setListDescription(detailsVO.getScrap().getValueDescription());
					detailsDTO.setScrap(fgItemDTO);
				}

				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
					itemDTO.setId(detailsVO.getItem().getId());
					itemDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItem(itemDTO);
				}

				if (detailsVO.getUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailsVO.getUnit().getId());
					unitDTO.setUnitId(detailsVO.getUnit().getUnitId());
					detailsDTO.setUnit(unitDTO);
				}

				if (detailsVO.getScrapUnit() != null) {
					UnitResponseDTO scrapUnitDTO = new UnitResponseDTO();
					scrapUnitDTO.setId(detailsVO.getScrapUnit().getId());
					scrapUnitDTO.setUnitId(detailsVO.getScrapUnit().getUnitId());
					detailsDTO.setScrapUnit(scrapUnitDTO);
				}

				detailsList.add(detailsDTO);
			}
		}
		responseDTO.setFgTransferSlipDetailsResponseDTO(detailsList);
		return responseDTO;
	}

	@Override
	public String getFgTransferSlipDocId(Long orgId, String financialYear) {
		String screenCode = "FGTS";
		return fgTransferSlipRepo.getFgTransferSlipDocId(orgId, financialYear, screenCode);
	}

	@Override
	public FgTransferSlipResponseDTO getFgTransferSlipById(Long id) throws ApplicationException {
		FgTransferSlipVO vo = fgTransferSlipRepo.getFgTransferSlipById(id);
		if (vo == null) {
			throw new ApplicationException("FG Transfer Slip Not Found");
		}
		return buildFgTransferSlipResponse(vo);
	}

	@Override
	public List<FgTransferSlipResponseDTO> getFgTransferSlipByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<FgTransferSlipVO> list = fgTransferSlipRepo.getFgTransferSlipByOrgId(orgId, branch);
		if (list == null || list.isEmpty()) {
			throw new ApplicationException("FG Transfer Slip Not Found");
		}
		List<FgTransferSlipResponseDTO> responseList = new ArrayList<>();
		for (FgTransferSlipVO vo : list) {
			responseList.add(buildFgTransferSlipResponse(vo));
		}
		return responseList;
	}

	@Override
	public List<Map<String, Object>> getBomFromFgTransferSlip(Long orgId, Long branch, Long fgItem) {
		Set<Object[]> chType = fgTransferSlipRepo.getBomFromFgTransferSlip(orgId, branch, fgItem);
		return getBomFromFgTransferSlip(chType);
	}

	private List<Map<String, Object>> getBomFromFgTransferSlip(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("bomId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("docId", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSchNoFromFgTransferSlip(Long orgId, Long branch) {
		Set<Object[]> chType = fgTransferSlipRepo.getSchNoFromFgTransferSlip(orgId, branch);
		return getSchNoFromFgTransferSlip(chType);
	}

	private List<Map<String, Object>> getSchNoFromFgTransferSlip(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			map.put("docDate", ch[1] != null ? ch[1].toString() : "");
			map.put("scheduledQty", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getCustomersDetailsFromTransferSlip(Long orgId, Long branch) {
		Set<Object[]> chType = fgTransferSlipRepo.getCustomersDetailsFromTransferSlip(orgId, branch);
		return getCustomersDetailsFromTransferSlip(chType);
	}

	private List<Map<String, Object>> getCustomersDetailsFromTransferSlip(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("customerId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("customerName", ch[1] != null ? ch[1].toString() : "");
			map.put("customerCode", ch[2] != null ? ch[2].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getBomDetailsFromFgTransferSlip(Long orgId, Long branch, Long bom) {
		Set<Object[]> chType = fgTransferSlipRepo.getBomDetailsFromFgTransferSlip(orgId, branch, bom);
		return getBomDetailsFromFgTransferSlip(chType);
	}

	private List<Map<String, Object>> getBomDetailsFromFgTransferSlip(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);
			map.put("unitId", ch[4] != null ? ((Number) ch[4]).longValue() : null);
			map.put("unitDescription", ch[5] != null ? ch[5].toString() : "");
			list.add(map);
		}

		return list;
	}

	// cons

	@Override
	@Transactional
	public Map<String, Object> createUpdateConsumptionEntry(ConsumptionEntryDTO dto) throws ApplicationException {
		String screenCode = "CE";
		ConsumptionEntryVO vo = new ConsumptionEntryVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {
			vo = consumptionEntryRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Consumption Entry Not Found"));
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Consumption Entry Updated Successfully";
		} else {
			String docId = consumptionEntryRepo.getConsumptionEntryDocId(dto.getOrgId(), dto.getFinancialYear(),
					screenCode);
			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Consumption Entry Created Successfully";
		}

		createUpdateConsumptionEntryVOByDTO(dto, vo);
		vo = consumptionEntryRepo.save(vo);

		ConsumptionEntryResponseDTO responseDTO = buildConsumptionEntryResponse(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("consumptionEntryVO", responseDTO);

		return response;
	}

	private void createUpdateConsumptionEntryVOByDTO(ConsumptionEntryDTO dto, ConsumptionEntryVO vo)
			throws ApplicationException {

		vo.setType(dto.getType());
		vo.setFromDate(dto.getFromDate());
		vo.setToDate(dto.getToDate());
		vo.setConsumption(dto.getConsumption());
		vo.setNarration(dto.getNarration());
		vo.setActive(dto.isActive());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getEntryType() != null && dto.getEntryType() != 0) {
			ListOfValuesDetailsVO location = listOfValuesDetailsRepo.findById(dto.getEntryType())
					.orElseThrow(() -> new ApplicationException("List Pf Values Not Found"));
			vo.setEntryType(location);
		}

		if (dto.getLocation() != null && dto.getLocation() != 0) {
			LocationVO location = locationRepo.findById(dto.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));
			vo.setLocation(location);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<ConsumptionEntryDetailsVO> existingDetails = consumptionEntryDetailsRepo.findByConsumptionEntryVO(vo);
			consumptionEntryDetailsRepo.deleteAll(existingDetails);

			List<RmConsumptionEntryDetailsVO> existingRmDetails = rmConsumptionEntryDetailsRepo
					.findByConsumptionEntryVO(vo);
			rmConsumptionEntryDetailsRepo.deleteAll(existingRmDetails);
		}

		List<ConsumptionEntryDetailsVO> itemDetailsList = new ArrayList<>();
		if (dto.getConsumptionEntryDetailsDTO() != null) {
			for (ConsumptionEntryDetailsDTO d : dto.getConsumptionEntryDetailsDTO()) {
				ConsumptionEntryDetailsVO detailsVO = new ConsumptionEntryDetailsVO();
				detailsVO.setConsumedQty(d.getConsumedQty());

				if (d.getItem() != null && d.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));
					detailsVO.setItem(item);
				}

				if (d.getUnit() != null && d.getUnit() != 0) {
					UnitMasterVO unit = unitMasterRepo.findById(d.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));
					detailsVO.setUnit(unit);
				}

				detailsVO.setConsumptionEntryVO(vo);
				itemDetailsList.add(detailsVO);
			}
		}
		vo.setConsumptionEntryDetailsVO(itemDetailsList);

		List<RmConsumptionEntryDetailsVO> rmItemDetailsList = new ArrayList<>();
		if (dto.getRmConsumptionEntryDetailsDTO() != null) {
			for (RmConsumptionEntryDetailsDTO d : dto.getRmConsumptionEntryDetailsDTO()) {
				RmConsumptionEntryDetailsVO detailsVO = new RmConsumptionEntryDetailsVO();
				detailsVO.setConsumptionAsPerBomQty(d.getConsumptionAsPerBomQty());
				detailsVO.setAvailableStock(d.getAvailableStock());

				BigDecimal consumedQty = d.getConsumedQty() != null ? d.getConsumedQty() : BigDecimal.ZERO;
				detailsVO.setActualConsumedQty(d.getConsumptionAsPerBomQty().multiply(consumedQty));
				detailsVO.setWastageQty(d.getWastageQty());
				detailsVO.setScrapQty(d.getScrapQty());
				BigDecimal total = d.getScrapQty().add(d.getWastageQty()).add(detailsVO.getActualConsumedQty());

				detailsVO.setTotalConsumedQty(total);
				detailsVO.setRate(d.getRate());

				detailsVO.setAmount(d.getRate().multiply(detailsVO.getTotalConsumedQty()));

				if (d.getItem() != null && d.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("RM Item Not Found"));
					detailsVO.setItem(item);
				}

				if (d.getUnit() != null && d.getUnit() != 0) {
					UnitMasterVO unit = unitMasterRepo.findById(d.getUnit())
							.orElseThrow(() -> new ApplicationException("RM Unit Not Found"));
					detailsVO.setUnit(unit);
				}

				detailsVO.setConsumptionEntryVO(vo);
				rmItemDetailsList.add(detailsVO);
			}
		}
		vo.setRmConsumptionEntryDetailsVO(rmItemDetailsList);
	}

	private ConsumptionEntryResponseDTO buildConsumptionEntryResponse(ConsumptionEntryVO vo) {
		ConsumptionEntryResponseDTO responseDTO = new ConsumptionEntryResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setType(vo.getType());
		responseDTO.setFromDate(vo.getFromDate());
		responseDTO.setToDate(vo.getToDate());
		responseDTO.setConsumption(vo.getConsumption());
		responseDTO.setNarration(vo.getNarration());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		if (vo.getEntryType() != null) {
			ListOfValuesResponseDTO fgItemDTO = new ListOfValuesResponseDTO();
			fgItemDTO.setId(vo.getEntryType().getId());
			fgItemDTO.setListCode(vo.getEntryType().getValueCode());
			fgItemDTO.setListDescription(vo.getEntryType().getValueDescription());
			responseDTO.setEntryType(fgItemDTO);
		}

		if (vo.getLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getLocation().getId());
			locDto.setLocationName(vo.getLocation().getLocationName());
			responseDTO.setLocation(locDto);
		}

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		List<ConsumptionEntryDetailsResponseDTO> detailsList = new ArrayList<>();
		if (vo.getConsumptionEntryDetailsVO() != null) {
			for (ConsumptionEntryDetailsVO detailsVO : vo.getConsumptionEntryDetailsVO()) {
				ConsumptionEntryDetailsResponseDTO detailsDTO = new ConsumptionEntryDetailsResponseDTO();
				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setConsumedQty(detailsVO.getConsumedQty());

				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
					itemDTO.setId(detailsVO.getItem().getId());
					itemDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItem(itemDTO);
				}

				if (detailsVO.getUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailsVO.getUnit().getId());
					unitDTO.setUnitId(detailsVO.getUnit().getUnitId());
					detailsDTO.setUnit(unitDTO);
				}

				detailsList.add(detailsDTO);
			}
		}
		responseDTO.setConsumptionEntryDetailsResponseDTO(detailsList);

		List<RmConsumptionEntryDetailsResponseDTO> rmDetailsList = new ArrayList<>();
		if (vo.getRmConsumptionEntryDetailsVO() != null) {
			for (RmConsumptionEntryDetailsVO detailsVO : vo.getRmConsumptionEntryDetailsVO()) {
				RmConsumptionEntryDetailsResponseDTO detailsDTO = new RmConsumptionEntryDetailsResponseDTO();
				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setConsumptionAsPerBomQty(detailsVO.getConsumptionAsPerBomQty());
				detailsDTO.setAvailableStock(detailsVO.getAvailableStock());
				detailsDTO.setActualConsumedQty(detailsVO.getActualConsumedQty());
				detailsDTO.setWastageQty(detailsVO.getWastageQty());
				detailsDTO.setScrapQty(detailsVO.getScrapQty());
				detailsDTO.setTotalConsumedQty(detailsVO.getTotalConsumedQty());
				detailsDTO.setRate(detailsVO.getRate());
				detailsDTO.setAmount(detailsVO.getAmount());

				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
					itemDTO.setId(detailsVO.getItem().getId());
					itemDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItem(itemDTO);
				}

				if (detailsVO.getUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailsVO.getUnit().getId());
					unitDTO.setUnitId(detailsVO.getUnit().getUnitId());
					detailsDTO.setUnit(unitDTO);
				}

				rmDetailsList.add(detailsDTO);
			}
		}
		responseDTO.setRmConsumptionEntryDetailsResponseDTO(rmDetailsList);

		return responseDTO;
	}

	@Override
	public String getConsumptionEntryDocId(Long orgId, String financialYear) {
		String screenCode = "CE";
		return consumptionEntryRepo.getConsumptionEntryDocId(orgId, financialYear, screenCode);
	}

	@Override
	public ConsumptionEntryResponseDTO getConsumptionEntryById(Long id) throws ApplicationException {
		ConsumptionEntryVO vo = consumptionEntryRepo.getConsumptionEntryById(id);
		if (vo == null) {
			throw new ApplicationException("Consumption Entry Not Found");
		}
		return buildConsumptionEntryResponse(vo);
	}

	@Override
	public List<ConsumptionEntryResponseDTO> getConsumptionEntryByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<ConsumptionEntryVO> list = consumptionEntryRepo.getConsumptionEntryByOrgId(orgId, branch);
		if (list == null || list.isEmpty()) {
			throw new ApplicationException("Consumption Entry Not Found");
		}
		List<ConsumptionEntryResponseDTO> responseList = new ArrayList<>();
		for (ConsumptionEntryVO vo : list) {
			responseList.add(buildConsumptionEntryResponse(vo));
		}
		return responseList;
	}

	@Override
	public List<Map<String, Object>> getFgAndSfgItemDetailsConsumptionEntry(Long orgId, Long branch) {
		Set<Object[]> chType = consumptionEntryRepo.getFgAndSfgItemDetailsConsumptionEntry(orgId, branch);
		return getFgAndSfgItemDetailsConsumptionEntry(chType);
	}

	private List<Map<String, Object>> getFgAndSfgItemDetailsConsumptionEntry(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("unitId", ch[3] != null ? ((Number) ch[3]).longValue() : null);
			map.put("unitDescription", ch[4] != null ? ch[4].toString() : "");
			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getRawMaterialConsumptionEntry(Long orgId, Long branch, Long fgItem) {
		Set<Object[]> chType = consumptionEntryRepo.getRawMaterialConsumptionEntry(orgId, branch, fgItem);
		return getRawMaterialConsumptionEntry(chType);
	}

	private List<Map<String, Object>> getRawMaterialConsumptionEntry(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();
			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
			map.put("bomQty", ch[3] != null ? new BigDecimal(ch[3].toString()) : BigDecimal.ZERO);
			map.put("scrapQty", ch[4] != null ? new BigDecimal(ch[4].toString()) : BigDecimal.ZERO);
			map.put("unitId", ch[5] != null ? ((Number) ch[5]).longValue() : null);
			map.put("unitDescription", ch[6] != null ? ch[6].toString() : "");
			list.add(map);
		}

		return list;
	}

	// Mater

	@Override
	@Transactional
	public Map<String, Object> createUpdateMaterialTransferReturnNote(MaterialTransferReturnNoteDTO dto)
			throws ApplicationException {
		String screenCode = "MTRN";
		MaterialTransferReturnNoteVO vo = new MaterialTransferReturnNoteVO();
		String message;

		if (ObjectUtils.isNotEmpty(dto.getId())) {
			vo = materialTransferReturnNoteRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Material Transfer Return Note Not Found"));
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Material Transfer Return Note Updated Successfully";
		} else {
			String docId = materialTransferReturnNoteRepo.getMaterialTransferReturnNoteDocId(dto.getOrgId(),
					dto.getFinancialYear(), screenCode);
			vo.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(dto.getOrgId(), dto.getFinancialYear(), screenCode);
			if (documentTypeMappingDetailsVO != null) {
				documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "Material Transfer Return Note Created Successfully";
		}

		createUpdateMaterialTransferReturnNoteVOByDTO(dto, vo);

		vo = materialTransferReturnNoteRepo.save(vo);

		MaterialTransferReturnNoteResponseDTO responseDTO = buildMaterialTransferReturnNoteResponse(vo);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("materialTransferReturnNoteVO", responseDTO);

		return response;
	}

	private void createUpdateMaterialTransferReturnNoteVOByDTO(MaterialTransferReturnNoteDTO dto,
			MaterialTransferReturnNoteVO vo) throws ApplicationException {
		vo.setBelongsTo(dto.getBelongsTo());
		vo.setType(dto.getType());
		vo.setSchOrderNo(dto.getSchOrderNo());
		vo.setApprovedByPm(dto.getApprovedByPm());
		vo.setApprovedByQc(dto.getApprovedByQc());
		vo.setApprovedByStores(dto.getApprovedByStores());
		vo.setNarration(dto.getNarration());
		vo.setActive(dto.isActive());
		vo.setCancel(dto.isCancel());
		vo.setCancelRemarks(dto.getCancelRemarks());
		vo.setOrgId(dto.getOrgId());
		vo.setFinancialYear(dto.getFinancialYear());

		if (dto.getFromLocation() != null && dto.getFromLocation() != 0) {
			LocationVO location = locationRepo.findById(dto.getFromLocation())
					.orElseThrow(() -> new ApplicationException("From Location Not Found"));
			vo.setFromLocation(location);
		}

		if (dto.getToLocation() != null && dto.getToLocation() != 0) {
			LocationVO location = locationRepo.findById(dto.getToLocation())
					.orElseThrow(() -> new ApplicationException("To Location Not Found"));
			vo.setToLocation(location);
		}

		if (dto.getFgItem() != null && dto.getFgItem() != 0) {
			ItemMasterVO item = itemMasterRepo.findById(dto.getFgItem())
					.orElseThrow(() -> new ApplicationException("FG/SFG Item Not Found"));
			vo.setFgItem(item);
		}

		if (dto.getPreparedBy() != null && dto.getPreparedBy() != 0) {
			EmployeeMasterVO employee = employeeMasterRepo.findById(dto.getPreparedBy())
					.orElseThrow(() -> new ApplicationException("Prepared By Employee Not Found"));
			vo.setPreparedBy(employee);
		}

		if (dto.getBranch() != null && dto.getBranch() != 0) {
			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
			vo.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(vo.getId())) {
			List<MaterialTransferReturnNoteDetailsVO> existingDetails = materialTransferReturnNoteDetailsRepo
					.findByMaterialTransferReturnNoteVO(vo);
			if (existingDetails != null && !existingDetails.isEmpty()) {
				materialTransferReturnNoteDetailsRepo.deleteAll(existingDetails);
			}
		}

		List<MaterialTransferReturnNoteDetailsVO> itemDetailsList = new ArrayList<>();
		BigDecimal totalValue = BigDecimal.ZERO;

		if (dto.getMaterialTransferReturnNoteDetailsDTO() != null) {
			for (MaterialTransferReturnNoteDetailsDTO d : dto.getMaterialTransferReturnNoteDetailsDTO()) {
				MaterialTransferReturnNoteDetailsVO detailsVO = new MaterialTransferReturnNoteDetailsVO();

				detailsVO.setAvailableQty(d.getAvailableQty());
				detailsVO.setQty(d.getQty());
				detailsVO.setRate(d.getRate());

				BigDecimal calculatedValue = BigDecimal.ZERO;
				calculatedValue = d.getQty().multiply(d.getRate());

				detailsVO.setValue(calculatedValue);
				totalValue = totalValue.add(calculatedValue);

				detailsVO.setReasonForRejectionTransfer(d.getReasonForRejectionTransfer());

				if (d.getItem() != null && d.getItem() != 0) {
					ItemMasterVO item = itemMasterRepo.findById(d.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found in Details"));
					detailsVO.setItem(item);
				}

				if (d.getUnit() != null && d.getUnit() != 0) {
					UnitMasterVO unit = unitMasterRepo.findById(d.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));
					detailsVO.setUnit(unit);
				}

				if (d.getSupplier() != null && d.getSupplier() != 0) {
					CustomerVO supplier = customerRepo.findById(d.getSupplier())
							.orElseThrow(() -> new ApplicationException("Supplier Not Found"));
					detailsVO.setSupplier(supplier);
				}

				detailsVO.setMaterialTransferReturnNoteVO(vo);
				itemDetailsList.add(detailsVO);
			}
		}
		vo.setItemDetails(itemDetailsList);
		vo.setTotalValue(totalValue);
	}

	private MaterialTransferReturnNoteResponseDTO buildMaterialTransferReturnNoteResponse(
			MaterialTransferReturnNoteVO vo) {
		MaterialTransferReturnNoteResponseDTO responseDTO = new MaterialTransferReturnNoteResponseDTO();

		responseDTO.setId(vo.getId());
		responseDTO.setDocId(vo.getDocId());
		responseDTO.setDocDate(vo.getDocDate());
		responseDTO.setBelongsTo(vo.getBelongsTo());
		responseDTO.setType(vo.getType());
		responseDTO.setSchOrderNo(vo.getSchOrderNo());
		responseDTO.setTime(vo.getTime());
		responseDTO.setTotalValue(vo.getTotalValue());
		responseDTO.setApprovedByPm(vo.getApprovedByPm());
		responseDTO.setApprovedByQc(vo.getApprovedByQc());
		responseDTO.setApprovedByStores(vo.getApprovedByStores());
		responseDTO.setNarration(vo.getNarration());
		responseDTO.setCreatedBy(vo.getCreatedBy());
		responseDTO.setUpdatedBy(vo.getUpdatedBy());
		responseDTO.setActive(vo.getActive());
		responseDTO.setCancel(vo.getCancel());
		responseDTO.setCancelRemarks(vo.getCancelRemarks());
		responseDTO.setScreenName(vo.getScreenName());
		responseDTO.setScreenCode(vo.getScreenCode());
		responseDTO.setOrgId(vo.getOrgId());
		responseDTO.setFinancialYear(vo.getFinancialYear());

		if (vo.getFromLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getFromLocation().getId());
			locDto.setLocationName(vo.getFromLocation().getLocationName());
			responseDTO.setFromLocation(locDto);
		}

		if (vo.getPreparedBy() != null) {
			EmployeeMasterDetailsReponseDTO locDto = new EmployeeMasterDetailsReponseDTO();
			locDto.setId(vo.getPreparedBy().getId());
			locDto.setEmployeeName(vo.getPreparedBy().getEmployeeName());
			responseDTO.setPreparedBy(locDto);
		}

		if (vo.getToLocation() != null) {
			LocationMasterResponseDTO locDto = new LocationMasterResponseDTO();
			locDto.setId(vo.getToLocation().getId());
			locDto.setLocationName(vo.getToLocation().getLocationName());
			responseDTO.setToLocation(locDto);
		}

		if (vo.getFgItem() != null) {
			ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
			itemDTO.setId(vo.getFgItem().getId());
			itemDTO.setItemCode(vo.getFgItem().getItemCode());
			itemDTO.setItemDescription(vo.getFgItem().getItemDescription());
			responseDTO.setFgItem(itemDTO);
		}

		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		// Details List Mapping
		List<MaterialTransferReturnNoteDetailsResponseDTO> detailsList = new ArrayList<>();
		if (vo.getItemDetails() != null) {
			for (MaterialTransferReturnNoteDetailsVO detailsVO : vo.getItemDetails()) {
				MaterialTransferReturnNoteDetailsResponseDTO detailsDTO = new MaterialTransferReturnNoteDetailsResponseDTO();
				detailsDTO.setId(detailsVO.getId());
				detailsDTO.setAvailableQty(detailsVO.getAvailableQty());
				detailsDTO.setQty(detailsVO.getQty());
				detailsDTO.setRate(detailsVO.getRate());
				detailsDTO.setValue(detailsVO.getValue());
				detailsDTO.setReasonForRejectionTransfer(detailsVO.getReasonForRejectionTransfer());

				if (detailsVO.getItem() != null) {
					ItemMasterDetailsResponseImportDTO itemDTO = new ItemMasterDetailsResponseImportDTO();
					itemDTO.setId(detailsVO.getItem().getId());
					itemDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());
					detailsDTO.setItem(itemDTO);
				}

				if (detailsVO.getUnit() != null) {
					UnitResponseDTO unitDTO = new UnitResponseDTO();
					unitDTO.setId(detailsVO.getUnit().getId());
					unitDTO.setUnitId(detailsVO.getUnit().getUnitId());
					detailsDTO.setUnit(unitDTO);
				}

				if (detailsVO.getSupplier() != null) {
					CustomerResponseGstDetailsDTO custDto = new CustomerResponseGstDetailsDTO();
					custDto.setId(detailsVO.getSupplier().getId());
					custDto.setCustomerName(detailsVO.getSupplier().getCustomerName());
					detailsDTO.setSupplier(custDto);
				}

				detailsList.add(detailsDTO);
			}
		}
		responseDTO.setItemDetails(detailsList);

		return responseDTO;
	}

	@Override
	public String getMaterialTransferReturnNoteDocId(Long orgId, String financialYear) throws ApplicationException {
		String screenCode = "MTRN";
		return materialTransferReturnNoteRepo.getMaterialTransferReturnNoteDocId(orgId, financialYear, screenCode);
	}

	@Override
	public MaterialTransferReturnNoteResponseDTO getMaterialTransferReturnNoteById(Long id)
			throws ApplicationException {
		MaterialTransferReturnNoteVO vo = materialTransferReturnNoteRepo.getMaterialTransferReturnNoteById(id);
		if (vo == null) {
			throw new ApplicationException("Material Transfer Return Note Not Found");
		}
		return buildMaterialTransferReturnNoteResponse(vo);
	}

	@Override
	public List<MaterialTransferReturnNoteResponseDTO> getMaterialTransferReturnNoteByOrgId(Long orgId, Long branch)
			throws ApplicationException {
		List<MaterialTransferReturnNoteVO> list = materialTransferReturnNoteRepo
				.getMaterialTransferReturnNoteByOrgId(orgId, branch);
		if (list == null || list.isEmpty()) {
			throw new ApplicationException("Material Transfer Return Note Not Found");
		}
		List<MaterialTransferReturnNoteResponseDTO> responseList = new ArrayList<>();
		for (MaterialTransferReturnNoteVO vo : list) {
			responseList.add(buildMaterialTransferReturnNoteResponse(vo));
		}
		return responseList;
	}

	@Override
	public List<Map<String, Object>> getFgAndSfgFromMaterialTransferReturnNote(Long orgId, Long branch) {

		Set<Object[]> chType = materialTransferReturnNoteRepo.getFgAndSfgFromMaterialTransferReturnNote(orgId, branch);

		return getFgAndSfgFromMaterialTransferReturnNote(chType);
	}

	private List<Map<String, Object>> getFgAndSfgFromMaterialTransferReturnNote(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);

			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");

			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSchNoFromMaterialTransferReturnNote(Long orgId, Long branch) {

		Set<Object[]> chType = materialTransferReturnNoteRepo.getSchNoFromMaterialTransferReturnNote(orgId, branch);

		return getSchNoFromMaterialTransferReturnNote(chType);
	}

	private List<Map<String, Object>> getSchNoFromMaterialTransferReturnNote(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("docId", ch[0] != null ? ch[0].toString() : "");

			map.put("docDate", ch[1] != null ? ch[1].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSchNoItemDetailsFromMaterialTransferReturnNote(Long orgId, Long branch,
			String schNo) {

		Set<Object[]> chType = materialTransferReturnNoteRepo.etSchNoItemDetailsFromMaterialTransferReturnNote(orgId,
				branch, schNo);

		return getSchNoItemDetailsFromMaterialTransferReturnNote(chType);
	}

	private List<Map<String, Object>> getSchNoItemDetailsFromMaterialTransferReturnNote(Set<Object[]> chType) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : chType) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);

			map.put("itemCode", ch[1] != null ? ch[1].toString() : "");

			map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");

			map.put("unitMasterId", ch[3] != null ? ((Number) ch[3]).longValue() : null);

			map.put("unitMasterDescription", ch[4] != null ? ch[4].toString() : "");

			map.put("qtyRequired", ch[5] != null ? new BigDecimal(ch[5].toString()) : BigDecimal.ZERO);

			list.add(map);
		}

		return list;
	}
}
