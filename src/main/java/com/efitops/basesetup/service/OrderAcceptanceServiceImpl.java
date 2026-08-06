package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.CustomerResponseGstDetailsDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.OrderAcceptanceDTO;
import com.efitops.basesetup.dto.OrderAcceptanceDetailsDTO;
import com.efitops.basesetup.dto.OrderAcceptanceDetailsResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceFileUploadDetailsDTO;
import com.efitops.basesetup.dto.OrderAcceptanceResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceTaxDetailsDTO;
import com.efitops.basesetup.dto.OrderAcceptanceTaxDetailsResponsDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDetailsDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseFileDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseResponseDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.OrderAcceptanceDetailsVO;
import com.efitops.basesetup.entity.OrderAcceptanceFileUploadDetailsVO;
import com.efitops.basesetup.entity.OrderAcceptanceTaxDetailsVO;
import com.efitops.basesetup.entity.OrderAcceptanceVO;
import com.efitops.basesetup.entity.SalesOrderShortCloseDetailsVO;
import com.efitops.basesetup.entity.SalesOrderShortCloseFileDetailsVO;
import com.efitops.basesetup.entity.SalesOrderShortCloseVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.GstRateMasterRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.OrderAcceptanceDetailsRepo;
import com.efitops.basesetup.repository.OrderAcceptanceFileUploadDetailsRepo;
import com.efitops.basesetup.repository.OrderAcceptanceRepo;
import com.efitops.basesetup.repository.OrderAcceptanceTaxDetailsRepo;
import com.efitops.basesetup.repository.SalesOrderShortCloseDetailsRepo;
import com.efitops.basesetup.repository.SalesOrderShortCloseFileDetailsRepo;
import com.efitops.basesetup.repository.SalesOrderShortCloseRepo;
import com.efitops.basesetup.repository.TaxDefinitionRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class OrderAcceptanceServiceImpl implements OrderAcceptanceService {

	public static final Logger LOGGER = LoggerFactory.getLogger(OrderAcceptanceServiceImpl.class);

	@Autowired
	OrderAcceptanceRepo orderAcceptanceRepo;

	@Autowired
	OrderAcceptanceTaxDetailsRepo orderAcceptanceTaxDetailsRepo;

	@Autowired
	OrderAcceptanceDetailsRepo orderAcceptanceDetailsRepo;

	@Autowired
	OrderAcceptanceFileUploadDetailsRepo orderAcceptanceFileUploadDetailsRepo;

	@Value("${file.upload-dirs}")
	private String uploadBasePath;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	ListOfValuesRepo listOfValuesRepo;

	@Autowired
	UnitMasterRepo unitMasterRepo;

	@Autowired
	HsnRepo hsnRepo;

	@Autowired
	LocationRepo locationRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	TaxDefinitionRepo taxDefinitionRepo;

	@Autowired
	ItemMasterRepo itemMasterRepo;

	@Autowired
	CurrencyRepo currencyRepo;

	@Autowired
	GstRateMasterRepo gstRateMasterRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	SalesOrderShortCloseRepo salesOrderShortCloseRepo;

	@Autowired
	SalesOrderShortCloseDetailsRepo salesOrderShortCloseDetailsRepo;

	@Autowired
	SalesOrderShortCloseFileDetailsRepo salesOrderShortCloseFileDetailsRepo;

	@Override
	public OrderAcceptanceResponseDTO getOrderAcceptanceById(Long id) throws ApplicationException {

		OrderAcceptanceVO orderAcceptanceVO = orderAcceptanceRepo.getOrderAcceptanceById(id);

		if (orderAcceptanceVO == null) {
			throw new ApplicationException("Order Not Found");
		}

		return buildOrderAcceptanceResponse(orderAcceptanceVO);
	}

	@Override
	public List<OrderAcceptanceResponseDTO> getOrderAcceptanceByOrgId(Long orgId, Long branchId)
			throws ApplicationException {

		List<OrderAcceptanceVO> quotationList = orderAcceptanceRepo.getQuotationByOrgId(orgId, branchId);

		if (quotationList == null || quotationList.isEmpty()) {
			throw new ApplicationException("Quotation Not Found");
		}

		List<OrderAcceptanceResponseDTO> responseList = new ArrayList<>();

		for (OrderAcceptanceVO orderAcceptanceVO : quotationList) {
			responseList.add(buildOrderAcceptanceResponse(orderAcceptanceVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateOrderAcceptance(OrderAcceptanceDTO orderAcceptanceDTO, MultipartFile[] files)
			throws ApplicationException {

		OrderAcceptanceVO orderAcceptanceVO;
		String message;

		if (ObjectUtils.isNotEmpty(orderAcceptanceDTO.getId())) {

			orderAcceptanceVO = orderAcceptanceRepo.findById(orderAcceptanceDTO.getId())
					.orElseThrow(() -> new ApplicationException("OrderAcceptance Not Found"));

			orderAcceptanceVO.setUpdatedBy(orderAcceptanceDTO.getCreatedBy());

			message = "OrderAcceptance Updated Successfully";

		} else {

			orderAcceptanceVO = new OrderAcceptanceVO();

			orderAcceptanceVO.setCreatedBy(orderAcceptanceDTO.getCreatedBy());
			orderAcceptanceVO.setUpdatedBy(orderAcceptanceDTO.getCreatedBy());

			message = "OrderAcceptance Created Successfully";
		}

		// Header + Child Mapping
		createUpdateOrderAcceptanceVOByOrderAcceptanceDTO(orderAcceptanceDTO, orderAcceptanceVO);

		// Save Header
		orderAcceptanceVO = orderAcceptanceRepo.save(orderAcceptanceVO);

		// Save Attachments
		saveAttachments(files, orderAcceptanceVO);

		// Response
		OrderAcceptanceResponseDTO responseDTO = buildOrderAcceptanceResponse(orderAcceptanceVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("orderAcceptanceVO", responseDTO);

		return response;
	}

	private void createUpdateOrderAcceptanceVOByOrderAcceptanceDTO(OrderAcceptanceDTO orderAcceptanceDTO,
			OrderAcceptanceVO orderAcceptanceVO) throws ApplicationException {

		orderAcceptanceVO.setBelongsTo(orderAcceptanceDTO.getBelongsTo());

		if (orderAcceptanceDTO.getCustomerId() != null && orderAcceptanceDTO.getCustomerId() > 0) {

			CustomerVO customer = customerRepo.findById(orderAcceptanceDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			orderAcceptanceVO.setCustomerId(customer);
		}

		orderAcceptanceVO.setWithQuotation(orderAcceptanceDTO.getWithQuotation());

		orderAcceptanceVO.setId(orderAcceptanceDTO.getId());
		orderAcceptanceVO.setOrderNo(orderAcceptanceDTO.getOrderNo());

		orderAcceptanceVO.setBelongsTo(orderAcceptanceDTO.getBelongsTo());
		orderAcceptanceVO.setSoType(orderAcceptanceDTO.getSoType());
		orderAcceptanceVO.setWithQuotation(orderAcceptanceDTO.getWithQuotation());

		orderAcceptanceVO.setQuotationDate(orderAcceptanceDTO.getQuotationDate());
		orderAcceptanceVO.setQuotationNo(orderAcceptanceDTO.getQuotationNo());

		orderAcceptanceVO.setEnquiryNo(orderAcceptanceDTO.getEnquiryNo());
		orderAcceptanceVO.setEnquiryDate(orderAcceptanceDTO.getEnquiryDate());

		orderAcceptanceVO.setCustomerPurchaseOrderNo(orderAcceptanceDTO.getCustomerPurchaseOrderNo());
		orderAcceptanceVO.setCustomerPurchaseOrderDate(orderAcceptanceDTO.getCustomerPurchaseOrderDate());

		orderAcceptanceVO.setPostRate(orderAcceptanceDTO.getPostRate());

		orderAcceptanceVO.setCreatedBy(orderAcceptanceDTO.getCreatedBy());
		orderAcceptanceVO.setUpdatedBy(orderAcceptanceDTO.getUpdatedBy());
		orderAcceptanceVO.setCancelRemarks(orderAcceptanceDTO.getCancelRemarks());

		orderAcceptanceVO.setOrgId(orderAcceptanceDTO.getOrgId());

		orderAcceptanceVO.setDestination(orderAcceptanceDTO.getDestination());
		orderAcceptanceVO.setModeOfTransport(orderAcceptanceDTO.getModeOfTransport());

		orderAcceptanceVO.setGrossalue(orderAcceptanceDTO.getGrossalue());
		orderAcceptanceVO.setFreight(orderAcceptanceDTO.getFreight());

		orderAcceptanceVO.setDeliveryTerms(orderAcceptanceDTO.getDeliveryTerms());
		orderAcceptanceVO.setPaymentTerms(orderAcceptanceDTO.getPaymentTerms());

		orderAcceptanceVO.setSpecification(orderAcceptanceDTO.getSpecification());
		orderAcceptanceVO.setNote(orderAcceptanceDTO.getNote());

		orderAcceptanceVO.setCreatedBy(orderAcceptanceDTO.getCreatedBy());
		orderAcceptanceVO.setUpdatedBy(orderAcceptanceDTO.getUpdatedBy());
		orderAcceptanceVO.setOrgId(orderAcceptanceDTO.getOrgId());
		orderAcceptanceVO.setFinancialYear(orderAcceptanceDTO.getFinancialYear());

		orderAcceptanceVO.setGstApproval(orderAcceptanceDTO.getGstApproval());

		if (orderAcceptanceDTO.getBranchId() != null && orderAcceptanceDTO.getBranchId() > 0) {

			BranchVO branch = branchRepo.findById(orderAcceptanceDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			orderAcceptanceVO.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(orderAcceptanceVO.getId())) {
			List<OrderAcceptanceDetailsVO> taxInvoiceDetailsVO1 = orderAcceptanceDetailsRepo
					.findByOrderAcceptanceVO(orderAcceptanceVO);
			orderAcceptanceDetailsRepo.deleteAll(taxInvoiceDetailsVO1);

			List<OrderAcceptanceTaxDetailsVO> taxInvoiceDetailsVO2 = orderAcceptanceTaxDetailsRepo
					.findByOrderAcceptanceVO(orderAcceptanceVO);
			orderAcceptanceTaxDetailsRepo.deleteAll(taxInvoiceDetailsVO2);

			List<OrderAcceptanceFileUploadDetailsVO> taxInvoiceDetailsVO3 = orderAcceptanceFileUploadDetailsRepo
					.findByOrderAcceptanceVO(orderAcceptanceVO);
			orderAcceptanceFileUploadDetailsRepo.deleteAll(taxInvoiceDetailsVO3);
		}

		BigDecimal totalAmount = BigDecimal.ZERO;

		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		BigDecimal totalDiscountAmount = BigDecimal.ZERO;

		List<OrderAcceptanceDetailsVO> itemDetailsList = new ArrayList<>();

		if (orderAcceptanceDTO.getOrderAcceptanceDetailsDTO() != null) {

			for (OrderAcceptanceDetailsDTO dto : orderAcceptanceDTO.getOrderAcceptanceDetailsDTO()) {

				OrderAcceptanceDetailsVO detailsVO = new OrderAcceptanceDetailsVO();

				if (dto.getItem() != null && dto.getItem() != 0) {
					ItemMasterVO itemCode = itemMasterRepo.findById(dto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(itemCode);
				}

				if (dto.getCurrencyNameId() != null && dto.getCurrencyNameId() != 0) {
					CurrencyVO currency = currencyRepo.findById(dto.getCurrencyNameId())
							.orElseThrow(() -> new ApplicationException("Currency Not Found"));

					detailsVO.setCurrencyName(currency);
				}

				detailsVO.setLastInvoiceDate(dto.getLastInvoiceDate());

				detailsVO.setQuantity(dto.getQuantity());

				detailsVO.setQuantityRate(dto.getQuantityRate());

				detailsVO.setOrderRate(dto.getOrderRate());

				detailsVO.setDiscount(dto.getDiscount());

				detailsVO.setOrderAmount(dto.getQuantity().multiply(dto.getOrderRate()));

				BigDecimal discountAmount = detailsVO.getOrderAmount().multiply(dto.getDiscount())
						.divide(BigDecimal.valueOf(100));

				detailsVO.setDiscountAmount(discountAmount);

				totalDiscountAmount = totalDiscountAmount.add(discountAmount);

				detailsVO.setAmount(detailsVO.getOrderAmount().subtract(discountAmount));

				BigDecimal taxPercentage = dto.getTaxPercentage() == null ? BigDecimal.ZERO : dto.getTaxPercentage();

				detailsVO.setTaxPercentage(taxPercentage);

				if ("Yes".equalsIgnoreCase(orderAcceptanceDTO.getGstApproval())) {

					BigDecimal igstAmount = detailsVO.getAmount().multiply(taxPercentage)
							.divide(BigDecimal.valueOf(100));

					detailsVO.setIgstRate(taxPercentage);
					detailsVO.setIgstAmount(igstAmount);

				} else if ("No".equalsIgnoreCase(orderAcceptanceDTO.getGstApproval())) {

					BigDecimal halfTax = taxPercentage.divide(BigDecimal.valueOf(2));

					BigDecimal cgstAmount = detailsVO.getAmount().multiply(halfTax).divide(BigDecimal.valueOf(100));

					BigDecimal sgstAmount = detailsVO.getAmount().multiply(halfTax).divide(BigDecimal.valueOf(100));

					detailsVO.setCgstRate(halfTax);
					detailsVO.setCgstAmount(cgstAmount);

					detailsVO.setSgstRate(halfTax);
					detailsVO.setSgstAmount(sgstAmount);

				} else {
					detailsVO.setIgstRate(BigDecimal.ZERO);
					detailsVO.setIgstAmount(BigDecimal.ZERO);

					detailsVO.setCgstRate(BigDecimal.ZERO);
					detailsVO.setCgstAmount(BigDecimal.ZERO);

					detailsVO.setSgstRate(BigDecimal.ZERO);
					detailsVO.setSgstAmount(BigDecimal.ZERO);
				}

				// Total Tax
				BigDecimal totalItemTax = detailsVO.getIgstAmount().add(detailsVO.getCgstAmount())
						.add(detailsVO.getSgstAmount());

				totalTaxAmount = totalTaxAmount.add(totalItemTax);
				detailsVO.setTotalAmount(detailsVO.getAmount().add(totalItemTax));

				totalAmount = totalAmount.add(detailsVO.getTotalAmount());

			}
		}
		orderAcceptanceVO.setOrderAcceptanceDetailsVO(itemDetailsList);

		List<OrderAcceptanceTaxDetailsVO> taxList = new ArrayList<>();

		if (orderAcceptanceDTO.getOrderAcceptanceTaxDetailsDTO() != null) {

			for (OrderAcceptanceTaxDetailsDTO dto : orderAcceptanceDTO.getOrderAcceptanceTaxDetailsDTO()) {

				OrderAcceptanceTaxDetailsVO taxVO = new OrderAcceptanceTaxDetailsVO();

				taxVO.setOrderAcceptanceVO(orderAcceptanceVO);

				taxVO.setParticulars(dto.getParticulars());
				taxVO.setAcceptedQtyAmount(dto.getAcceptedQtyAmount());
				taxVO.setRevisedAmount(dto.getRevisedAmount());

				taxList.add(taxVO);
			}
		}

		orderAcceptanceVO.setTaxableAmount(totalAmount);

		orderAcceptanceVO.setTotalTaxAmount(totalTaxAmount);

		orderAcceptanceVO.setTotalDiscountAmount(totalDiscountAmount);

		orderAcceptanceVO.setOrderAcceptanceTaxDetailsVO(taxList);

	}

	@Value("${order.upload.path}")
	private String uploadPath;

	private void saveAttachments(MultipartFile[] files, OrderAcceptanceVO orderAcceptanceVO)
			throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			File folder = new File(uploadPath);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			List<OrderAcceptanceFileUploadDetailsVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalFileName = file.getOriginalFilename();

				String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

				Path path = Paths.get(uploadPath, uniqueFileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}

				OrderAcceptanceFileUploadDetailsVO attachment = new OrderAcceptanceFileUploadDetailsVO();

				attachment.setOrderAcceptanceVO(orderAcceptanceVO);

				attachment.setName(originalFileName);

				attachment.setFileName(uniqueFileName);

				attachment.setFilePath(path.toString());

				attachment.setFileSize(file.getSize());

				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			List<OrderAcceptanceFileUploadDetailsVO> savedAttachments = orderAcceptanceFileUploadDetailsRepo
					.saveAll(attachmentList);

			orderAcceptanceVO.setOrderAcceptanceFileUploadDetailsVO(savedAttachments);

		} catch (IOException e) {

			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private OrderAcceptanceResponseDTO buildOrderAcceptanceResponse(OrderAcceptanceVO orderAcceptanceVO) {

		OrderAcceptanceResponseDTO responseDTO = new OrderAcceptanceResponseDTO();

		responseDTO.setId(orderAcceptanceVO.getId());
		responseDTO.setDocId(orderAcceptanceVO.getDocId());
		responseDTO.setDocDate(orderAcceptanceVO.getDocDate());

		responseDTO.setOrderNo(orderAcceptanceVO.getOrderNo());
		responseDTO.setBelongsTo(orderAcceptanceVO.getBelongsTo());
		responseDTO.setSoType(orderAcceptanceVO.getSoType());
		responseDTO.setWithQuotation(orderAcceptanceVO.getWithQuotation());

		responseDTO.setQuotationNo(orderAcceptanceVO.getQuotationNo());
		responseDTO.setQuotationDate(orderAcceptanceVO.getQuotationDate());

		responseDTO.setEnquiryNo(orderAcceptanceVO.getEnquiryNo());
		responseDTO.setEnquiryDate(orderAcceptanceVO.getEnquiryDate());

		responseDTO.setCustomerPurchaseOrderNo(orderAcceptanceVO.getCustomerPurchaseOrderNo());
		responseDTO.setCustomerPurchaseOrderDate(orderAcceptanceVO.getCustomerPurchaseOrderDate());

		responseDTO.setPostRate(orderAcceptanceVO.getPostRate());

		responseDTO.setCreatedBy(orderAcceptanceVO.getCreatedBy());
		responseDTO.setUpdatedBy(orderAcceptanceVO.getUpdatedBy());

		responseDTO.setCancelRemarks(orderAcceptanceVO.getCancelRemarks());

		responseDTO.setOrgId(orderAcceptanceVO.getOrgId());
		responseDTO.setFinancialYear(orderAcceptanceVO.getFinancialYear());

		responseDTO.setDestination(orderAcceptanceVO.getDestination());
		responseDTO.setModeOfTransport(orderAcceptanceVO.getModeOfTransport());
		responseDTO.setGrossalue(orderAcceptanceVO.getGrossalue());
		responseDTO.setFreight(orderAcceptanceVO.getFreight());

		responseDTO.setDeliveryTerms(orderAcceptanceVO.getDeliveryTerms());
		responseDTO.setPaymentTerms(orderAcceptanceVO.getPaymentTerms());
		responseDTO.setSpecification(orderAcceptanceVO.getSpecification());
		responseDTO.setNote(orderAcceptanceVO.getNote());
		responseDTO.setGstApproval(orderAcceptanceVO.getGstApproval());

		if (orderAcceptanceVO.getCustomerId() != null) {

			CustomerResponseGstDetailsDTO customerDTO = new CustomerResponseGstDetailsDTO();

			customerDTO.setId(orderAcceptanceVO.getCustomerId().getId());
			customerDTO.setCustomerName(orderAcceptanceVO.getCustomerId().getCustomerName());
			customerDTO.setCustomerGstNo(orderAcceptanceVO.getCustomerId().getGstNo());
			customerDTO.setGstApproval(orderAcceptanceVO.getCustomerId().isRegistered());
			responseDTO.setCustomerId(customerDTO);
		}

		if (orderAcceptanceVO.getBranch() != null) {

			BranchVO branch = new BranchVO();

			branch.setId(orderAcceptanceVO.getBranch().getId());
			branch.setBranchCode(orderAcceptanceVO.getBranch().getBranchCode());
			branch.setBranchName(orderAcceptanceVO.getBranch().getBranchName());

			responseDTO.setBranch(branch);
		}

		List<OrderAcceptanceDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (orderAcceptanceVO.getOrderAcceptanceDetailsVO() != null) {

			for (OrderAcceptanceDetailsVO detailsVO : orderAcceptanceVO.getOrderAcceptanceDetailsVO()) {

				OrderAcceptanceDetailsResponseDTO detailsDTO = new OrderAcceptanceDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());

				if (detailsVO.getItem() != null) {

					ItemMasterResponseDetailsDTO itemCodeDTO = new ItemMasterResponseDetailsDTO();

					itemCodeDTO.setId(detailsVO.getItem().getId());
					itemCodeDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemCodeDTO.setItemDescription(detailsVO.getItem().getItemDescription());

					if (detailsVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

						unitDTO.setId(detailsVO.getItem().getPrimaryUnit().getId());
						unitDTO.setUnitId(detailsVO.getItem().getPrimaryUnit().getUnitId());
						unitDTO.setUnitDescription(detailsVO.getItem().getPrimaryUnit().getDescription());

						itemCodeDTO.setUnit(unitDTO);
					}

					detailsDTO.setItems(itemCodeDTO);
				}

				detailsDTO.setCustomerPartNo(detailsVO.getCustomerPartNo());

				detailsDTO.setLastInvoiceDate(detailsVO.getLastInvoiceDate());

				detailsDTO.setQuantity(detailsVO.getQuantity());
				detailsDTO.setQuantityRate(detailsVO.getQuantityRate());
				detailsDTO.setOrderRate(detailsVO.getOrderRate());

				detailsDTO.setDiscount(detailsVO.getDiscount());

				detailsDTO.setAmount(detailsVO.getAmount());

				detailsDTO.setSgstRate(detailsVO.getSgstRate());
				detailsDTO.setSgstAmount(detailsVO.getSgstAmount());

				detailsDTO.setCgstRate(detailsVO.getCgstRate());
				detailsDTO.setCgstAmount(detailsVO.getCgstAmount());

				detailsDTO.setIgstRate(detailsVO.getIgstRate());
				detailsDTO.setIgstAmount(detailsVO.getIgstAmount());

				if (detailsVO.getCurrencyName() != null) {

					CurrencyResponseDTO currencyDTO = new CurrencyResponseDTO();

					currencyDTO.setId(detailsVO.getCurrencyName().getId());
					currencyDTO.setCurrencyName(detailsVO.getCurrencyName().getCurrency());

					detailsDTO.setCurrencyName(currencyDTO);
				}

				detailsResponseList.add(detailsDTO);
			}
		}

		responseDTO.setOrderAcceptanceDetailsResponseDTO(detailsResponseList);

		// ================ Tax Details ===================

		List<OrderAcceptanceTaxDetailsResponsDTO> taxResponseList = new ArrayList<>();

		if (orderAcceptanceVO.getOrderAcceptanceTaxDetailsVO() != null) {

			for (OrderAcceptanceTaxDetailsVO taxVO : orderAcceptanceVO.getOrderAcceptanceTaxDetailsVO()) {

				OrderAcceptanceTaxDetailsResponsDTO taxDTO = new OrderAcceptanceTaxDetailsResponsDTO();

				taxDTO.setId(taxVO.getId());
				taxDTO.setParticulars(taxVO.getParticulars());
				taxDTO.setAcceptedQtyAmount(taxVO.getAcceptedQtyAmount());
				taxDTO.setRevisedAmount(taxVO.getRevisedAmount());

				taxResponseList.add(taxDTO);
			}
		}

		responseDTO.setOrderAcceptanceTaxDetailsResponsVO(taxResponseList);

		// ================ File Upload ===================

		List<OrderAcceptanceFileUploadDetailsDTO> fileResponseList = new ArrayList<>();

		if (orderAcceptanceVO.getOrderAcceptanceFileUploadDetailsVO() != null) {

			for (OrderAcceptanceFileUploadDetailsVO fileVO : orderAcceptanceVO
					.getOrderAcceptanceFileUploadDetailsVO()) {

				OrderAcceptanceFileUploadDetailsDTO fileDTO = new OrderAcceptanceFileUploadDetailsDTO();

				fileDTO.setId(fileVO.getId());
				fileDTO.setName(fileVO.getName());
				fileDTO.setFileName(fileVO.getFileName());
				fileDTO.setFilePath(fileVO.getFilePath());
				fileDTO.setFileSize(fileVO.getFileSize());
				fileDTO.setUploadOn(fileVO.getUploadOn());

				fileResponseList.add(fileDTO);
			}
		}

		responseDTO.setOrderAcceptanceFileUploadDetailsDTO(fileResponseList);

		return responseDTO;
	}

	// Sales Order

	@Override
	public SalesOrderShortCloseResponseDTO getSalesOrderShortCloseById(Long id) throws ApplicationException {

		SalesOrderShortCloseVO salesOrderShortCloseVO = salesOrderShortCloseRepo.getSalesOrderShortCloseById(id);

		if (salesOrderShortCloseVO == null) {
			throw new ApplicationException("Order Not Found");
		}

		return buildSalesOrderShortCloseResponse(salesOrderShortCloseVO);
	}

	@Override
	public List<SalesOrderShortCloseResponseDTO> getSalesOrderShortCloseByOrgId(Long orgId, Long branchId)
			throws ApplicationException {

		List<SalesOrderShortCloseVO> quotationList = salesOrderShortCloseRepo.getSalesOrderShortCloseByOrgId(orgId,
				branchId);

		if (quotationList == null || quotationList.isEmpty()) {
			throw new ApplicationException("Quotation Not Found");
		}

		List<SalesOrderShortCloseResponseDTO> responseList = new ArrayList<>();

		for (SalesOrderShortCloseVO salesOrderShortCloseVO : quotationList) {
			responseList.add(buildSalesOrderShortCloseResponse(salesOrderShortCloseVO));
		}

		return responseList;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesOrderShort(SalesOrderShortCloseDTO salesOrderShortCloseDTO,
			MultipartFile[] files) throws ApplicationException {

		SalesOrderShortCloseVO salesOrderShortCloseVO;
		String message;

		if (ObjectUtils.isNotEmpty(salesOrderShortCloseDTO.getId())) {

			salesOrderShortCloseVO = salesOrderShortCloseRepo.findById(salesOrderShortCloseDTO.getId())
					.orElseThrow(() -> new ApplicationException("OrderAcceptance Not Found"));

			salesOrderShortCloseVO.setUpdatedBy(salesOrderShortCloseDTO.getCreatedBy());

			message = "OrderAcceptance Updated Successfully";

		} else {

			salesOrderShortCloseVO = new SalesOrderShortCloseVO();

			salesOrderShortCloseVO.setCreatedBy(salesOrderShortCloseDTO.getCreatedBy());
			salesOrderShortCloseVO.setUpdatedBy(salesOrderShortCloseDTO.getCreatedBy());

			message = "OrderAcceptance Created Successfully";
		}

		createUpdateSalesOrderVOBySalesOrderDTO(salesOrderShortCloseDTO, salesOrderShortCloseVO);

		// Save Header
		salesOrderShortCloseVO = salesOrderShortCloseRepo.save(salesOrderShortCloseVO);

		// Save Attachments
		saveAttachmentss(files, salesOrderShortCloseVO);

		// Response
		SalesOrderShortCloseResponseDTO responseDTO = buildSalesOrderShortCloseResponse(salesOrderShortCloseVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("orderAcceptanceVO", responseDTO);

		return response;
	}

	private void createUpdateSalesOrderVOBySalesOrderDTO(SalesOrderShortCloseDTO salesOrderShortCloseDTO,
			SalesOrderShortCloseVO salesOrderShortCloseVO) throws ApplicationException {

		if (salesOrderShortCloseDTO.getCustomerId() != null && salesOrderShortCloseDTO.getCustomerId() > 0) {

			CustomerVO customer = customerRepo.findById(salesOrderShortCloseDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			salesOrderShortCloseVO.setCustomerId(customer);
		}

		salesOrderShortCloseVO.setDocId(salesOrderShortCloseDTO.getDocId());

		salesOrderShortCloseVO.setCreatedBy(salesOrderShortCloseDTO.getCreatedBy());
		salesOrderShortCloseVO.setUpdatedBy(salesOrderShortCloseDTO.getCreatedBy());
		salesOrderShortCloseVO.setCancelRemarks(salesOrderShortCloseDTO.getCancelRemarks());

		salesOrderShortCloseVO.setOrgId(salesOrderShortCloseDTO.getOrgId());

		if (salesOrderShortCloseDTO.getBranchId() != null && salesOrderShortCloseDTO.getBranchId() > 0) {

			BranchVO branch = branchRepo.findById(salesOrderShortCloseDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			salesOrderShortCloseVO.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(salesOrderShortCloseVO.getId())) {
			List<SalesOrderShortCloseDetailsVO> taxInvoiceDetailsVO1 = salesOrderShortCloseDetailsRepo
					.findBySalesOrderShortCloseVO(salesOrderShortCloseVO);
			salesOrderShortCloseDetailsRepo.deleteAll(taxInvoiceDetailsVO1);

			List<SalesOrderShortCloseFileDetailsVO> taxInvoiceDetailsVO3 = salesOrderShortCloseFileDetailsRepo
					.findBySalesOrderShortCloseVO(salesOrderShortCloseVO);
			salesOrderShortCloseFileDetailsRepo.deleteAll(taxInvoiceDetailsVO3);
		}

		List<SalesOrderShortCloseDetailsVO> itemDetailsList = new ArrayList<>();

		if (salesOrderShortCloseDTO.getSalesOrderShortCloseDetailsDTO() != null) {

			for (SalesOrderShortCloseDetailsDTO dto : salesOrderShortCloseDTO.getSalesOrderShortCloseDetailsDTO()) {

				SalesOrderShortCloseDetailsVO detailsVO = new SalesOrderShortCloseDetailsVO();

				if (dto.getItemId() != null && dto.getItemId() != 0) {
					ItemMasterVO itemCode = itemMasterRepo.findById(dto.getItemId())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailsVO.setItem(itemCode);
				}

				detailsVO.setOrderQty(dto.getOrderQty());

				detailsVO.setSuppliedQty(dto.getSuppliedQty());

				detailsVO.setPendingQty(dto.getOrderQty().subtract(dto.getSuppliedQty()));

				detailsVO.setRequiredQty(dto.getRequiredQty());

				detailsVO.setPendingQty(detailsVO.getPendingQty().subtract(dto.getRequiredQty()));

			}
		}
		salesOrderShortCloseVO.setSalesOrderShortCloseDetailsVO(itemDetailsList);

	}

	@Value("${short.upload.path}")
	private String uploadPaths;

	private void saveAttachmentss(MultipartFile[] files, SalesOrderShortCloseVO orderAcceptanceVO)
			throws ApplicationException {

		if (files == null || files.length == 0) {
			return;
		}

		try {

			File folder = new File(uploadPath);

			if (!folder.exists()) {
				folder.mkdirs();
			}

			List<SalesOrderShortCloseFileDetailsVO> attachmentList = new ArrayList<>();

			for (MultipartFile file : files) {

				if (file == null || file.isEmpty()) {
					continue;
				}

				String originalFileName = file.getOriginalFilename();

				String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

				Path path = Paths.get(uploadPath, uniqueFileName);

				try (InputStream inputStream = file.getInputStream()) {

					Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
				}

				SalesOrderShortCloseFileDetailsVO attachment = new SalesOrderShortCloseFileDetailsVO();

				attachment.setSalesOrderShortCloseVO(orderAcceptanceVO);

				attachment.setName(originalFileName);

				attachment.setFileName(uniqueFileName);

				attachment.setFilePath(path.toString());

				attachment.setFileSize(file.getSize());

				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			List<SalesOrderShortCloseFileDetailsVO> savedAttachments = salesOrderShortCloseFileDetailsRepo
					.saveAll(attachmentList);

			orderAcceptanceVO.setSalesOrderShortCloseFileDetailsVO(savedAttachments);

		} catch (IOException e) {

			throw new ApplicationException("File Upload Failed : " + e.getMessage());
		}
	}

	private SalesOrderShortCloseResponseDTO buildSalesOrderShortCloseResponse(
			SalesOrderShortCloseVO salesOrderShortCloseVO) {

		SalesOrderShortCloseResponseDTO responseDTO = new SalesOrderShortCloseResponseDTO();

		responseDTO.setId(salesOrderShortCloseVO.getId());
		responseDTO.setDocId(salesOrderShortCloseVO.getDocId());
		responseDTO.setDocDate(LocalDate.now());

		responseDTO.setCreatedBy(salesOrderShortCloseVO.getCreatedBy());
		responseDTO.setUpdatedBy(salesOrderShortCloseVO.getUpdatedBy());

		responseDTO.setCancelRemarks(salesOrderShortCloseVO.getCancelRemarks());

		responseDTO.setOrgId(salesOrderShortCloseVO.getOrgId());
		responseDTO.setFinancialYear(salesOrderShortCloseVO.getFinancialYear());

		if (salesOrderShortCloseVO.getCustomerId() != null) {

			CustomerResponseDetailsDTO customerDTO = new CustomerResponseDetailsDTO();

			customerDTO.setId(salesOrderShortCloseVO.getCustomerId().getId());
			customerDTO.setCustomerName(salesOrderShortCloseVO.getCustomerId().getCustomerName());
			customerDTO.setCustomerCode(salesOrderShortCloseVO.getCustomerId().getCustomerCode());
			responseDTO.setCustomerId(customerDTO);
		}

		if (salesOrderShortCloseVO.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(salesOrderShortCloseVO.getBranch().getId());
			branch.setBranchCode(salesOrderShortCloseVO.getBranch().getBranchCode());
			branch.setBranchName(salesOrderShortCloseVO.getBranch().getBranchName());

			responseDTO.setBranchId(branch);
		}

		List<SalesOrderShortCloseDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (salesOrderShortCloseVO.getSalesOrderShortCloseDetailsVO() != null) {

			for (SalesOrderShortCloseDetailsVO detailsVO : salesOrderShortCloseVO.getSalesOrderShortCloseDetailsVO()) {

				SalesOrderShortCloseDetailsResponseDTO detailsDTO = new SalesOrderShortCloseDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());

				if (detailsVO.getItem() != null) {

					ItemMasterResponseDTO itemCodeDTO = new ItemMasterResponseDTO();

					itemCodeDTO.setId(detailsVO.getItem().getId());
					itemCodeDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemCodeDTO.setItemDescription(detailsVO.getItem().getItemDescription());

					detailsDTO.setItem(itemCodeDTO);
				}

				detailsDTO.setPendingQty(detailsVO.getPendingQty());

				detailsDTO.setOrderQty(detailsVO.getOrderQty());
				detailsDTO.setSuppliedQty(detailsVO.getSuppliedQty());
				detailsDTO.setPendingQty(detailsVO.getPendingQty());
				detailsDTO.setRequiredQty(detailsVO.getRequiredQty());
				detailsDTO.setShortCloseQty(detailsVO.getShortCloseQty());

				detailsResponseList.add(detailsDTO);
			}
		}

		responseDTO.setSalesOrderShortCloseDetailsResponseDTO(detailsResponseList);

		List<SalesOrderShortCloseFileDetailsResponseDTO> fileResponseList = new ArrayList<>();

		if (salesOrderShortCloseVO.getSalesOrderShortCloseFileDetailsVO() != null) {

			for (SalesOrderShortCloseFileDetailsVO fileVO : salesOrderShortCloseVO
					.getSalesOrderShortCloseFileDetailsVO()) {

				SalesOrderShortCloseFileDetailsResponseDTO fileDTO = new SalesOrderShortCloseFileDetailsResponseDTO();

				fileDTO.setId(fileVO.getId());
				fileDTO.setName(fileVO.getName());
				fileDTO.setFileName(fileVO.getFileName());
				fileDTO.setFilePath(fileVO.getFilePath());
				fileDTO.setFileSize(fileVO.getFileSize());
				fileDTO.setUploadOn(fileVO.getUploadOn());

				fileResponseList.add(fileDTO);
			}
		}

		responseDTO.setSalesOrderShortCloseFileDetailsResponseDTO(fileResponseList);

		return responseDTO;

	}

}
