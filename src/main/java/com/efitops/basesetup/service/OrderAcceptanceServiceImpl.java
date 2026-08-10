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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.efitops.basesetup.ResponseDTO.GSTRateResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CustomerResponseGstDetailsDTO;
import com.efitops.basesetup.dto.ItemMasterResponseGstDetailsDTO;
import com.efitops.basesetup.dto.ItemMasterResponseTaxDTO;
import com.efitops.basesetup.dto.OrderAcceptanceDTO;
import com.efitops.basesetup.dto.OrderAcceptanceDetailsDTO;
import com.efitops.basesetup.dto.OrderAcceptanceDetailsResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceDocIdResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceFileUploadDetailsDTO;
import com.efitops.basesetup.dto.OrderAcceptanceItemDetailsResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceItemDropdownResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceResponseDTO;
import com.efitops.basesetup.dto.OrderAcceptanceTaxDetailsDTO;
import com.efitops.basesetup.dto.OrderAcceptanceTaxDetailsResponsDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDetailsDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesOrderShortCloseResponseDTO;
import com.efitops.basesetup.dto.ShortCloseItemResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.OrderAcceptanceDetailsVO;
import com.efitops.basesetup.entity.OrderAcceptanceFileUploadDetailsVO;
import com.efitops.basesetup.entity.OrderAcceptanceTaxDetailsVO;
import com.efitops.basesetup.entity.OrderAcceptanceVO;
import com.efitops.basesetup.entity.SalesOrderShortCloseDetailsVO;
import com.efitops.basesetup.entity.SalesOrderShortCloseVO;
import com.efitops.basesetup.entity.UnitMasterVO;
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

	@Autowired
	GstRateMasterRepo gstRateRepo;

	@Override
	public OrderAcceptanceResponseDTO getOrderAcceptanceById(Long id) throws ApplicationException {

		OrderAcceptanceVO orderAcceptanceVO = orderAcceptanceRepo.getOrderAcceptanceById(id);

		if (orderAcceptanceVO == null) {
			throw new ApplicationException("Order Not Found");
		}

		return buildOrderAcceptanceResponse(orderAcceptanceVO);
	}

	@Override
	public List<OrderAcceptanceResponseDTO> getOrderAcceptanceByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<OrderAcceptanceVO> quotationList = orderAcceptanceRepo.getQuotationByOrgId(orgId, branch);

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

		createUpdateOrderAcceptanceVOByOrderAcceptanceDTO(orderAcceptanceDTO, orderAcceptanceVO);

		orderAcceptanceVO = orderAcceptanceRepo.save(orderAcceptanceVO);

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

		if (orderAcceptanceDTO.getCustomer() != null && orderAcceptanceDTO.getCustomer() != 0) {

			CustomerVO customer = customerRepo.findById(orderAcceptanceDTO.getCustomer())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			orderAcceptanceVO.setCustomer(customer);
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

		orderAcceptanceVO.setOrgId(orderAcceptanceDTO.getOrgId());
		orderAcceptanceVO.setActive(orderAcceptanceDTO.isActive());

		orderAcceptanceVO.setOrgId(orderAcceptanceDTO.getOrgId());
		orderAcceptanceVO.setFinancialYear(orderAcceptanceDTO.getFinancialYear());
		orderAcceptanceVO.setGstApproval(orderAcceptanceDTO.getGstApproval());
		orderAcceptanceVO.setDocId(orderAcceptanceDTO.getDocId());

		if (orderAcceptanceDTO.getBranch() != null && orderAcceptanceDTO.getBranch() > 0) {

			BranchVO branch = branchRepo.findById(orderAcceptanceDTO.getBranch())
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

				UnitMasterVO unit = unitMasterRepo.findById(dto.getUnit())
						.orElseThrow(() -> new ApplicationException("Unit Not Found"));

				GSTRateMasterVO gstRateVO = gstRateRepo.findById(dto.getTaxPercentage())
						.orElseThrow(() -> new ApplicationException("GST Rate Not Found"));

				detailsVO.setTaxPercentage(gstRateVO);

				detailsVO.setLastInvoiceDate(dto.getLastInvoiceDate());

				detailsVO.setQuantity(dto.getQuantity());

				detailsVO.setUnit(unit);

				detailsVO.setQuantityRate(dto.getQuantityRate());

				detailsVO.setOrderRate(dto.getOrderRate());

				detailsVO.setDiscount(dto.getDiscount());

				detailsVO.setOrderAmount(dto.getQuantity().multiply(dto.getOrderRate()));

				detailsVO.setTaxType(dto.getTaxType());

				BigDecimal quantity = dto.getQuantity() == null ? BigDecimal.ZERO : dto.getQuantity();

				BigDecimal orderRate = dto.getOrderRate() == null ? BigDecimal.ZERO : dto.getOrderRate();

				BigDecimal discountPercentage = dto.getDiscount() == null ? BigDecimal.ZERO : dto.getDiscount();

				BigDecimal orderAmount = quantity.multiply(orderRate);

				BigDecimal discountAmount = orderAmount.multiply(discountPercentage).divide(BigDecimal.valueOf(100));

				BigDecimal amount = orderAmount.subtract(discountAmount);

				detailsVO.setDiscountAmount(discountAmount);
				detailsVO.setAmount(amount);

				if ("YES".equalsIgnoreCase(orderAcceptanceVO.getGstApproval())) {

					BigDecimal igstAmount = amount.multiply(gstRateVO.getIgst()).divide(BigDecimal.valueOf(100));

					detailsVO.setIgstRate(gstRateVO.getIgst());
					detailsVO.setCgstRate(BigDecimal.ZERO);
					detailsVO.setSgstRate(BigDecimal.ZERO);

					detailsVO.setIgstAmount(igstAmount);
					detailsVO.setCgstAmount(BigDecimal.ZERO);
					detailsVO.setSgstAmount(BigDecimal.ZERO);

				} else {

					BigDecimal cgstAmount = amount.multiply(gstRateVO.getCgst()).divide(BigDecimal.valueOf(100));

					BigDecimal sgstAmount = amount.multiply(gstRateVO.getSgst()).divide(BigDecimal.valueOf(100));

					detailsVO.setCgstRate(gstRateVO.getCgst());
					detailsVO.setSgstRate(gstRateVO.getSgst());
					detailsVO.setIgstRate(BigDecimal.ZERO);

					detailsVO.setCgstAmount(cgstAmount);
					detailsVO.setSgstAmount(sgstAmount);
					detailsVO.setIgstAmount(BigDecimal.ZERO);

				}

				detailsVO.setCurrencyName(dto.getCurrencyName());
				detailsVO.setOrderAcceptanceVO(orderAcceptanceVO);

				itemDetailsList.add(detailsVO);
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

			Path orderFolder = Paths.get(uploadPath, "orderAcceptance", orderAcceptanceVO.getId().toString());

			createDirectory(orderFolder);

			List<OrderAcceptanceFileUploadDetailsVO> attachmentList = new ArrayList<>();

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

				String fileName = originalName + "_" + orderAcceptanceVO.getId() + extension;

				Path filePath = orderFolder.resolve(fileName);

				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/orderAcceptance/viewFile/").toUriString();

				String relativePath = uploadPath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				String publicUrl = baseUrl + relativePath;

				OrderAcceptanceFileUploadDetailsVO attachment = new OrderAcceptanceFileUploadDetailsVO();

				attachment.setOrderAcceptanceVO(orderAcceptanceVO);
				attachment.setName(file.getOriginalFilename());
				attachment.setFileName(fileName);
				attachment.setFilePath(publicUrl);
				attachment.setFileSize(file.getSize());
				attachment.setContentType(file.getContentType());
				attachment.setUploadOn(LocalDateTime.now());

				attachmentList.add(attachment);
			}

			List<OrderAcceptanceFileUploadDetailsVO> saved = orderAcceptanceFileUploadDetailsRepo
					.saveAll(attachmentList);

			orderAcceptanceVO.setOrderAcceptanceFileUploadDetailsVO(saved);

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
	public ResponseEntity<byte[]> viewOrderAcceptanceFile(HttpServletRequest request) throws IOException {

		return serveFile(request, "/api/orderAcceptance/viewFile/", uploadPath);
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

	private OrderAcceptanceResponseDTO buildOrderAcceptanceResponse(OrderAcceptanceVO orderAcceptanceVO) {

		OrderAcceptanceResponseDTO responseDTO = new OrderAcceptanceResponseDTO();

		responseDTO.setId(orderAcceptanceVO.getId());
		responseDTO.setDocId(orderAcceptanceVO.getDocId());
		responseDTO.setDocDate(orderAcceptanceVO.getDocDate());
		responseDTO.setOrderNo(orderAcceptanceVO.getOrderNo());
		responseDTO.setBelongsTo(orderAcceptanceVO.getBelongsTo());
		responseDTO.setSoType(orderAcceptanceVO.getSoType());
		responseDTO.setWithQuotation(orderAcceptanceVO.getWithQuotation());
		responseDTO.setQuotationDate(orderAcceptanceVO.getQuotationDate());
		responseDTO.setQuotationNo(orderAcceptanceVO.getQuotationNo());
		responseDTO.setEnquiryNo(orderAcceptanceVO.getEnquiryNo());
		responseDTO.setEnquiryDate(orderAcceptanceVO.getEnquiryDate());
		responseDTO.setCustomerPurchaseOrderNo(orderAcceptanceVO.getCustomerPurchaseOrderNo());
		responseDTO.setCustomerPurchaseOrderDate(orderAcceptanceVO.getCustomerPurchaseOrderDate());
		responseDTO.setPostRate(orderAcceptanceVO.getPostRate());
		responseDTO.setCreatedBy(orderAcceptanceVO.getCreatedBy());
//		responseDTO.setActive(orderAcceptanceVO.isActive());
//		responseDTO.setCancel(orderAcceptanceVO.isCancel());
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

		if (orderAcceptanceVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(orderAcceptanceVO.getBranch().getId());
			branchDTO.setBranchCode(orderAcceptanceVO.getBranch().getBranchCode());
			branchDTO.setBranchName(orderAcceptanceVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		if (orderAcceptanceVO.getCustomer() != null) {

			CustomerResponseGstDetailsDTO customerDTO = new CustomerResponseGstDetailsDTO();

			customerDTO.setId(orderAcceptanceVO.getCustomer().getId());
			customerDTO.setCustomerName(orderAcceptanceVO.getCustomer().getCustomerName());
			customerDTO.setCustomerType(orderAcceptanceVO.getCustomer().getCustomerType());
			customerDTO.setCustomerGstNo(orderAcceptanceVO.getCustomer().getGstNo());

			customerDTO.setGstApproval(orderAcceptanceVO.getCustomer().isGstApplicable() ? "Yes" : "No");

			customerDTO.setCustomerGstNo(orderAcceptanceVO.getCustomer().getGstNo());

			responseDTO.setCustomerId(customerDTO);
		}

		List<OrderAcceptanceDetailsResponseDTO> detailsList = new ArrayList<>();

		if (orderAcceptanceVO.getOrderAcceptanceDetailsVO() != null) {

			for (OrderAcceptanceDetailsVO detailsVO : orderAcceptanceVO.getOrderAcceptanceDetailsVO()) {

				OrderAcceptanceDetailsResponseDTO detailsDTO = new OrderAcceptanceDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());

				if (detailsVO.getItem() != null) {
					detailsDTO.setItems(new ItemMasterResponseTaxDTO(detailsVO.getItem().getId(),
							detailsVO.getItem().getItemCode(), detailsVO.getItem().getItemDescription(),
							detailsVO.getItem().getHsnCode() != null ? detailsVO.getItem().getHsnCode().getHsn()
									: null));
				}

//				detailsDTO.setCustomerPartNo(detailsVO.getCustomerPartNo());

				if (detailsVO.getUnit() != null) {
					detailsDTO
							.setUnit(new UnitResponseDTO(detailsVO.getUnit().getId(), detailsVO.getUnit().getUnitId()));
				}

				if (detailsVO.getTaxPercentage() != null) {

					GSTRateResponseDTO gstDTO = new GSTRateResponseDTO();
					gstDTO.setId(detailsVO.getTaxPercentage().getId());
					gstDTO.setTaxPercentage(detailsVO.getTaxPercentage().getRate());

					detailsDTO.setTaxPercentage(gstDTO);
				} else {
					System.out.println("Tax Percentage is NULL");
				}

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
				detailsDTO.setCurrencyName(detailsVO.getCurrencyName());
				detailsDTO.setTaxType(detailsVO.getTaxType());

				detailsList.add(detailsDTO);
			}
		}

		responseDTO.setOrderAcceptanceDetailsResponseDTO(detailsList);

		List<OrderAcceptanceTaxDetailsResponsDTO> taxList = new ArrayList<>();

		if (orderAcceptanceVO.getOrderAcceptanceTaxDetailsVO() != null) {

			for (OrderAcceptanceTaxDetailsVO taxVO : orderAcceptanceVO.getOrderAcceptanceTaxDetailsVO()) {

				OrderAcceptanceTaxDetailsResponsDTO taxDTO = new OrderAcceptanceTaxDetailsResponsDTO();

				taxDTO.setId(taxVO.getId());
				taxDTO.setParticulars(taxVO.getParticulars());
				taxDTO.setAcceptedQtyAmount(taxVO.getAcceptedQtyAmount());
				taxDTO.setRevisedAmount(taxVO.getRevisedAmount());

				taxList.add(taxDTO);
			}
		}

		responseDTO.setOrderAcceptanceTaxDetailsResponsVO(taxList);

		List<OrderAcceptanceFileUploadDetailsDTO> fileList = new ArrayList<>();

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

				fileList.add(fileDTO);
			}
		}

		responseDTO.setOrderAcceptanceFileUploadDetailsDTO(fileList);

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
	public List<SalesOrderShortCloseResponseDTO> getSalesOrderShortCloseByOrgId(Long orgId, Long Branch)
			throws ApplicationException {

		List<SalesOrderShortCloseVO> quotationList = salesOrderShortCloseRepo.getSalesOrderShortCloseByOrgId(orgId,
				Branch);

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
	public Map<String, Object> createUpdateSalesOrderShort(SalesOrderShortCloseDTO salesOrderShortCloseDTO)
			throws ApplicationException {

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

		// Response
		SalesOrderShortCloseResponseDTO responseDTO = buildSalesOrderShortCloseResponse(salesOrderShortCloseVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("salesOrderShortCloseVO", responseDTO);

		return response;
	}

	private void createUpdateSalesOrderVOBySalesOrderDTO(SalesOrderShortCloseDTO salesOrderShortCloseDTO,
			SalesOrderShortCloseVO salesOrderShortCloseVO) throws ApplicationException {

		if (salesOrderShortCloseDTO.getCustomer() != null && salesOrderShortCloseDTO.getCustomer() != 0) {

			CustomerVO customer = customerRepo.findById(salesOrderShortCloseDTO.getCustomer())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			salesOrderShortCloseVO.setCustomer(customer);
		}

		if (salesOrderShortCloseDTO.getSaleOrderNo() != null && salesOrderShortCloseDTO.getSaleOrderNo() > 0) {

			OrderAcceptanceVO customer = orderAcceptanceRepo.findById(salesOrderShortCloseDTO.getSaleOrderNo())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			salesOrderShortCloseVO.setSaleOrderNo(customer);
		}

		salesOrderShortCloseVO.setDocId(salesOrderShortCloseDTO.getDocId());

		salesOrderShortCloseVO.setCancelRemarks(salesOrderShortCloseDTO.getCancelRemarks());

		salesOrderShortCloseVO.setOrgId(salesOrderShortCloseDTO.getOrgId());

		if (salesOrderShortCloseDTO.getBranch() != null && salesOrderShortCloseDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(salesOrderShortCloseDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			salesOrderShortCloseVO.setBranch(branch);
		}

		if (ObjectUtils.isNotEmpty(salesOrderShortCloseVO.getId())) {
			List<SalesOrderShortCloseDetailsVO> taxInvoiceDetailsVO1 = salesOrderShortCloseDetailsRepo
					.findBySalesOrderShortCloseVO(salesOrderShortCloseVO);
			salesOrderShortCloseDetailsRepo.deleteAll(taxInvoiceDetailsVO1);

		}

		List<SalesOrderShortCloseDetailsVO> itemDetailsList = new ArrayList<>();

		if (salesOrderShortCloseDTO.getSalesOrderShortCloseDetailsDTO() != null) {

			for (SalesOrderShortCloseDetailsDTO dto : salesOrderShortCloseDTO.getSalesOrderShortCloseDetailsDTO()) {

				SalesOrderShortCloseDetailsVO detailsVO = new SalesOrderShortCloseDetailsVO();

				detailsVO.setSalesOrderShortCloseVO(salesOrderShortCloseVO);

				if (dto.getItem() != null && dto.getItem() != 0) {

					ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailsVO.setItem(item);
				}

				detailsVO.setOrderQty(dto.getOrderQty());
				detailsVO.setSuppliedQty(dto.getSuppliedQty());

				BigDecimal pendingQty = dto.getOrderQty().subtract(dto.getSuppliedQty());

				detailsVO.setPendingQty(pendingQty);
				detailsVO.setRequiredQty(dto.getRequiredQty());
				detailsVO.setShortCloseQty(pendingQty.subtract(dto.getRequiredQty()));

				itemDetailsList.add(detailsVO);
			}
		}

		salesOrderShortCloseVO.setSalesOrderShortCloseDetailsVO(itemDetailsList);

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

		if (salesOrderShortCloseVO.getCustomer() != null) {

			CustomerResponseDetailsDTO customerDTO = new CustomerResponseDetailsDTO();

			customerDTO.setId(salesOrderShortCloseVO.getCustomer().getId());
			customerDTO.setCustomerName(salesOrderShortCloseVO.getCustomer().getCustomerName());
			customerDTO.setCustomerCode(salesOrderShortCloseVO.getCustomer().getCustomerCode());
			responseDTO.setCustomerId(customerDTO);
		}

		if (salesOrderShortCloseVO.getBranch() != null) {

			BranchResponseDTO branch = new BranchResponseDTO();

			branch.setId(salesOrderShortCloseVO.getBranch().getId());
			branch.setBranchCode(salesOrderShortCloseVO.getBranch().getBranchCode());
			branch.setBranchName(salesOrderShortCloseVO.getBranch().getBranchName());

			responseDTO.setBranch(branch);
		}

		List<SalesOrderShortCloseDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (salesOrderShortCloseVO.getSalesOrderShortCloseDetailsVO() != null
				&& !salesOrderShortCloseVO.getSalesOrderShortCloseDetailsVO().isEmpty()) {

			for (SalesOrderShortCloseDetailsVO detailsVO : salesOrderShortCloseVO.getSalesOrderShortCloseDetailsVO()) {

				SalesOrderShortCloseDetailsResponseDTO detailsDTO = new SalesOrderShortCloseDetailsResponseDTO();

				detailsDTO.setId(detailsVO.getId());

				if (detailsVO.getItem() != null) {

					ItemMasterResponseGstDetailsDTO itemDTO = new ItemMasterResponseGstDetailsDTO();

					itemDTO.setId(detailsVO.getItem().getId());
					itemDTO.setItemCode(detailsVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailsVO.getItem().getItemDescription());

					detailsDTO.setItem(itemDTO);
				}

				detailsDTO.setOrderQty(detailsVO.getOrderQty());
				detailsDTO.setSuppliedQty(detailsVO.getSuppliedQty());
				detailsDTO.setPendingQty(detailsVO.getPendingQty());
				detailsDTO.setRequiredQty(detailsVO.getRequiredQty());
				detailsDTO.setShortCloseQty(detailsVO.getShortCloseQty());

				detailsResponseList.add(detailsDTO);
			}
		}

		responseDTO.setSalesOrderShortCloseDetailsResponseDTO(detailsResponseList);

		return responseDTO;

	}

	@Override
	public List<OrderAcceptanceItemDropdownResponseDTO> getOrderAcceptanceItemDetails(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> itemList = orderAcceptanceRepo.getOrderAcceptanceItemDetails(orgId, branch);

		List<OrderAcceptanceItemDropdownResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {
			responseList.add(mapToFinishedGoodsResponseDTO(obj));
		}

		return responseList;
	}

	private OrderAcceptanceItemDropdownResponseDTO mapToFinishedGoodsResponseDTO(Object[] obj) {

		OrderAcceptanceItemDropdownResponseDTO dto = new OrderAcceptanceItemDropdownResponseDTO();

		dto.setItemId(((Number) obj[0]).longValue());
		dto.setItemCode((String) obj[1]);
		dto.setItemDescription((String) obj[2]);
		dto.setUnitId((String) obj[3]);
		dto.setMinimumSellPrice((BigDecimal) obj[4]);
		dto.setHsnCode((String) obj[5]);
		dto.setRate((BigDecimal) obj[6]);
		dto.setCgst((BigDecimal) obj[7]);
		dto.setSgst((BigDecimal) obj[8]);
		dto.setIgst((BigDecimal) obj[9]);
		dto.setUnitMasterId(((Number) obj[10]).longValue());
		dto.setGstRateMasterId(((Number) obj[11]).longValue());

		return dto;
	}

	@Override
	public List<ShortCloseItemResponseDTO> getSalesOrderItemDetails(Long orgId, Long branch, String docId)
			throws ApplicationException {

		List<Object[]> itemList = salesOrderShortCloseRepo.getSalesOrderItemDetails(orgId, branch, docId);

		List<ShortCloseItemResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {
			responseList.add(mapToFinishedGoodsResponseDTOs(obj));
		}

		return responseList;
	}

	private ShortCloseItemResponseDTO mapToFinishedGoodsResponseDTOs(Object[] obj) {

		ShortCloseItemResponseDTO dto = new ShortCloseItemResponseDTO();

		dto.setItemId(((Number) obj[0]).longValue());
		dto.setItemCode((String) obj[1]);
		dto.setItemDescription((String) obj[2]);

		return dto;
	}

	@Override
	public List<OrderAcceptanceDocIdResponseDTO> getOrderAcceptanceDocIdDetails(Long customer, String docId)
			throws ApplicationException {

		List<Object[]> itemList = salesOrderShortCloseRepo.getOrderAcceptanceDocIdDetails(customer, docId);

		List<OrderAcceptanceDocIdResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {
			responseList.add(mapToDocIdDTOs(obj));
		}

		return responseList;
	}

	private OrderAcceptanceDocIdResponseDTO mapToDocIdDTOs(Object[] obj) {

		OrderAcceptanceDocIdResponseDTO dto = new OrderAcceptanceDocIdResponseDTO();

		dto.setOrderAccptanceId(((Number) obj[0]).longValue());
		dto.setDocId((String) obj[1]);
		dto.setDocDate((LocalDate) obj[2]);

		return dto;
	}

	@Override
	public List<OrderAcceptanceItemDetailsResponseDTO> getOrderAcceptanceItemDetailsDetails(String docId)
			throws ApplicationException {

		List<Object[]> itemList = salesOrderShortCloseRepo.getOrderAcceptanceItemDetailsDetails(docId);

		List<OrderAcceptanceItemDetailsResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {
			responseList.add(mapToDocIdDTOss(obj));
		}

		return responseList;
	}

	private OrderAcceptanceItemDetailsResponseDTO mapToDocIdDTOss(Object[] obj) {

		OrderAcceptanceItemDetailsResponseDTO dto = new OrderAcceptanceItemDetailsResponseDTO();

		dto.setItemId(((Number) obj[0]).longValue());
		dto.setItemCode((String) obj[1]);
		dto.setItemDescitpion((String) obj[2]);
		dto.setOrderId(((Number) obj[3]).longValue());
		dto.setQuantity(((BigDecimal) obj[3]));
		return dto;
	}

}
