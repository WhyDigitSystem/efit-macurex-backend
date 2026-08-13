package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTRateResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateResponseDTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAttachResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractResponseTaxDetailsDTO;
import com.efitops.basesetup.ResponseDTO.SalesCustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.SalesContractDTO;
import com.efitops.basesetup.dto.SalesContractDetailsDTO;
import com.efitops.basesetup.dto.SalesContractTaxDetailsDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.SalesContractAttachVO;
import com.efitops.basesetup.entity.SalesContractDetailsVO;
import com.efitops.basesetup.entity.SalesContractTaxDetailsVO;
import com.efitops.basesetup.entity.SalesContractVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.GstRateMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.SalesContractAttachRepo;
import com.efitops.basesetup.repository.SalesContractDetailsRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.SalesContractTaxDetailsRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class DhineshServiceImpl implements DhineshService {

	public static final Logger LOGGER = LoggerFactory.getLogger(DhineshServiceImpl.class);

	@Autowired
	SalesContractRepo salesContractRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	SalesContractDetailsRepo salesContractDetailsRepo;

	@Autowired
	ItemMasterRepo itemMasterRepo;

	@Autowired
	UnitMasterRepo unitMasterRepo;

	@Autowired
	GstRateMasterRepo gstRateRepo;

	@Autowired
	ItemMasterRepo itemRepo;

	@Value("${sales.contract.upload.path}")
	private String uploadPath;

	@Autowired
	SalesContractAttachRepo salesContractAttachRepo;

	@Autowired
	ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	SalesContractTaxDetailsRepo salesContractTaxDetailsRepo;

	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesContract(SalesContractDTO dto, MultipartFile[] files)
			throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		String message;
		SalesContractVO salesContractVO;

		if (ObjectUtils.isEmpty(dto.getId())) {

			salesContractVO = new SalesContractVO();

			salesContractVO.setCreatedBy(dto.getCreatedBy());
			salesContractVO.setUpdatedBy(dto.getCreatedBy());

			message = "Sales Contract Created Successfully";

		} else {

			salesContractVO = salesContractRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("Sales Contract Not Found"));

			// Delete old details
//			salesContractDetailsRepo.deleteAll(salesContractDetailsRepo.findBySalesContract(salesContractVO));
			// Delete existing child records from DB
			salesContractDetailsRepo.deleteAll(
			        salesContractDetailsRepo.findBySalesContract(salesContractVO));

			salesContractTaxDetailsRepo.deleteAll(
			        salesContractTaxDetailsRepo.findBySalesContract(salesContractVO));

			salesContractAttachRepo.deleteAll(
			        salesContractAttachRepo.findBySalesContract(salesContractVO));

//			salesContractVO.getSalesContractDetailsVO().clear();
//			salesContractVO.getSalesContractTaxDetails().clear();
//			salesContractVO.getAttachments().clear();

			// Clear managed collections
			
			// Delete physical files only if a new file is uploaded
			if (files != null && files.length > 0 && files[0] != null && !files[0].isEmpty()) {

			    for (SalesContractAttachVO attach : salesContractVO.getAttachments()) {

			        if (attach.getPdfAttached() != null) {

			            File oldFile = new File(attach.getPdfAttached());

			            if (oldFile.exists()) {
			                oldFile.delete();
			            }
			        }
			    }

			    // Delete attachment records
			    salesContractAttachRepo.deleteAll(salesContractVO.getAttachments());

			    // Clear parent collection
			    salesContractVO.getAttachments().clear();
			}
			salesContractVO.getSalesContractDetailsVO().clear();
			salesContractVO.getSalesContractTaxDetails().clear();

			salesContractVO.setUpdatedBy(dto.getCreatedBy());

			message = "Sales Contract Updated Successfully";
		}

		// Header + Child Mapping
		getSalesContractVOFromDTO(dto, salesContractVO);

		// Save Header
		salesContractVO = salesContractRepo.saveAndFlush(salesContractVO);

		saveAttachments(files, salesContractVO);

		salesContractVO = salesContractRepo.findById(salesContractVO.getId())
		        .orElseThrow(() -> new ApplicationException("Sales Contract Not Found"));

		SalesContractResponseDTO responseDTO = convertToResponse(salesContractVO);

		response.put("message", message);
		response.put("salesContract", responseDTO);

		return response;
	}

	private void getSalesContractVOFromDTO(SalesContractDTO dto, SalesContractVO salesContractVO)
			throws ApplicationException {

		System.out.println("A");

		BranchVO branch = branchRepo.findById(dto.getBranch())
				.orElseThrow(() -> new ApplicationException("Branch Not Found"));

		System.out.println("B");

		CustomerVO customer = customerRepo.findById(dto.getCustomer())
				.orElseThrow(() -> new ApplicationException("Customer Not Found"));

		System.out.println("C");

		salesContractVO.setCustomerContractNo(dto.getCustomerContractNo());
		salesContractVO.setContractDate(dto.getContractDate());

		salesContractVO.setBranch(branch);

		salesContractVO.setBelongsTo(dto.getBelongsTo());
		salesContractVO.setContractType(dto.getContractType());
		salesContractVO.setWithQuotation(dto.getWithQuotation());
		salesContractVO.setInvoiceType(dto.getInvoiceType());

		salesContractVO.setCustomer(customer);

		salesContractVO.setQuotationNo(dto.getQuotationNo());
		salesContractVO.setQuotationDate(dto.getQuotationDate());

		salesContractVO.setCustomerPoNo(dto.getCustomerPoNo());
		salesContractVO.setCustomerPoDate(dto.getCustomerPoDate());

		salesContractVO.setEffectiveFrom(dto.getEffectiveFrom());
		salesContractVO.setEffectiveTo(dto.getEffectiveTo());

		salesContractVO.setPostRate(dto.getPostRate());

		salesContractVO.setOrgId(dto.getOrgId());
		salesContractVO.setFinancialYear(dto.getFinancialYear());

		salesContractVO.setCancelRemarks(dto.getCancelRemarks());
		salesContractVO.setActive(dto.isActive());

		salesContractVO.setTotalAmount(dto.getTotalAmount());
		salesContractVO.setAmountInWords(dto.getAmountInWords());
		salesContractVO.setPaymentTerms(dto.getPaymentTerms());
		salesContractVO.setPriceTerms(dto.getPriceTerms());
		salesContractVO.setTerms(dto.getTerms());
		salesContractVO.setNotes(dto.getNotes());

		List<SalesContractDetailsVO> detailList = new ArrayList<>();

		if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {

			for (SalesContractDetailsDTO child : dto.getDetails()) {

				SalesContractDetailsVO detailVO = new SalesContractDetailsVO();

				ItemMasterVO item = itemMasterRepo.findById(child.getItem())
						.orElseThrow(() -> new ApplicationException("Item Not Found"));

				UnitMasterVO unit = unitMasterRepo.findById(child.getUnit())
						.orElseThrow(() -> new ApplicationException("Unit Not Found"));

				GSTRateMasterVO gstRateVO = gstRateRepo.findById(child.getTaxPercentage())
						.orElseThrow(() -> new ApplicationException("GST Rate Not Found"));

				detailVO.setItem(item);
				detailVO.setTaxType(child.getTaxType());
				detailVO.setTaxPercentage(gstRateVO);
				detailVO.setGstRate(gstRateVO);

				detailVO.setUnit(unit);
				detailVO.setQuantity(child.getQuantity());
				detailVO.setQuotationRate(child.getQuotationRate());
				detailVO.setOrderRate(child.getOrderRate());
				detailVO.setEffectiveFrom(child.getEffectiveFrom());
				detailVO.setEffectiveTo(child.getEffectiveTo());
				salesContractVO.setIsIgstApplicable(dto.getIsIgstApplicable());
				detailVO.setDiscountPercentage(child.getDiscountPercentage());

				BigDecimal quantity = child.getQuantity() == null ? BigDecimal.ZERO : child.getQuantity();

				BigDecimal orderRate = child.getOrderRate() == null ? BigDecimal.ZERO : child.getOrderRate();

				BigDecimal discountPercentage = child.getDiscountPercentage() == null ? BigDecimal.ZERO
						: child.getDiscountPercentage();

				// Order Amount = Qty × Order Rate
				// Order Amount
				BigDecimal orderAmount = quantity.multiply(orderRate);

				// Discount Amount
				BigDecimal discountAmount = orderAmount.multiply(discountPercentage).divide(BigDecimal.valueOf(100));

				// Amount after Discount
				BigDecimal amount = orderAmount.subtract(discountAmount);

				detailVO.setDiscountAmount(discountAmount);
				detailVO.setAmount(amount);

//				BigDecimal finalAmount;

				if ("YES".equalsIgnoreCase(salesContractVO.getIsIgstApplicable())) {

					BigDecimal igstAmount = amount.multiply(gstRateVO.getIgst()).divide(BigDecimal.valueOf(100));

					// Rate
					detailVO.setIgstRate(gstRateVO.getIgst());
					detailVO.setCgstRate(BigDecimal.ZERO);
					detailVO.setSgstRate(BigDecimal.ZERO);

					// Amount
					detailVO.setIgstAmount(igstAmount);
					detailVO.setCgstAmount(BigDecimal.ZERO);
					detailVO.setSgstAmount(BigDecimal.ZERO);

//					finalAmount = amount.add(igstAmount);

				} else {

					BigDecimal cgstAmount = amount.multiply(gstRateVO.getCgst()).divide(BigDecimal.valueOf(100));

					BigDecimal sgstAmount = amount.multiply(gstRateVO.getSgst()).divide(BigDecimal.valueOf(100));

					// Rate
					detailVO.setCgstRate(gstRateVO.getCgst());
					detailVO.setSgstRate(gstRateVO.getSgst());
					detailVO.setIgstRate(BigDecimal.ZERO);

					// Amount
					detailVO.setCgstAmount(cgstAmount);
					detailVO.setSgstAmount(sgstAmount);
					detailVO.setIgstAmount(BigDecimal.ZERO);

//					finalAmount = amount.add(cgstAmount).add(sgstAmount);
				}

//				detailVO.setFinalAmount(finalAmount);

				detailVO.setCurrency(child.getCurrency());

				// Header mapping
				detailVO.setSalesContract(salesContractVO);

				detailList.add(detailVO);
			}
		}

//		salesContractVO.setSalesContractDetailsVO(detailList);
		salesContractVO.getSalesContractDetailsVO().clear();

		for (SalesContractDetailsVO detail : detailList) {
		    detail.setSalesContract(salesContractVO);
		    salesContractVO.getSalesContractDetailsVO().add(detail);
		}

		// ================= TAX DETAILS =================

		List<SalesContractTaxDetailsVO> taxDetailList = new ArrayList<>();

		if (dto.getSalesContractTaxDetailsDTO() != null && !dto.getSalesContractTaxDetailsDTO().isEmpty()) {

			for (SalesContractTaxDetailsDTO taxDTO : dto.getSalesContractTaxDetailsDTO()) {

				SalesContractTaxDetailsVO taxVO = new SalesContractTaxDetailsVO();

				ListOfValuesDetailsVO particulars = listOfValuesDetailsRepo.findById(taxDTO.getParticulars())
						.orElseThrow(() -> new ApplicationException("Particulars Not Found"));

				taxVO.setParticulars(particulars);

				taxVO.setAmount(taxDTO.getAmount());

				taxVO.setSalesContract(salesContractVO);

				taxDetailList.add(taxVO);
			}
		}

		salesContractVO.setSalesContractTaxDetails(taxDetailList);
	}

	private void saveAttachments(MultipartFile[] files, SalesContractVO salesContractVO)
	        throws ApplicationException {

	    if (files == null || files.length == 0) {
	        return;
	    }

	    try {

	        File folder = new File(uploadPath);

	        if (!folder.exists()) {
	            folder.mkdirs();
	        }

	        for (MultipartFile file : files) {

	            if (file == null || file.isEmpty()) {
	                continue;
	            }

	            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

	            Path path = Paths.get(uploadPath, fileName);

	            // Copy file and automatically close InputStream
	            try (InputStream inputStream = file.getInputStream()) {
	                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
	            }

	            SalesContractAttachVO attach = new SalesContractAttachVO();
	            attach.setSalesContract(salesContractVO);
	            attach.setPdfAttached(path.toString());

	            salesContractVO.getAttachments().add(attach);
	        }

	        salesContractRepo.saveAndFlush(salesContractVO);

	    } catch (IOException e) {
	        throw new ApplicationException("File upload failed : " + e.getMessage(), e);
	    }
	
	}

	private SalesContractResponseDTO convertToResponse(SalesContractVO vo) {

		SalesContractResponseDTO dto = new SalesContractResponseDTO();

		dto.setId(vo.getId());
		dto.setCustomerContractNo(vo.getCustomerContractNo());
		dto.setContractDate(vo.getContractDate());

		if (vo.getBranch() != null) {
			dto.setBranch(new BranchResponseDTO(vo.getBranch().getId(), vo.getBranch().getBranchCode(),
					vo.getBranch().getBranchName()));
		}

		if (vo.getCustomer() != null) {

			SalesCustomerResponseDTO customerDTO = new SalesCustomerResponseDTO();

			customerDTO.setCustomerId(vo.getCustomer().getId());
			customerDTO.setCustomerName(vo.getCustomer().getCustomerName());
			customerDTO.setCustomerType(vo.getCustomer().getCustomerType());

			if (vo.getCustomer().getGstState() != null) {
				customerDTO.setGstState(new GSTStateResponseDTO(vo.getCustomer().getGstState().getId(),
						vo.getCustomer().getGstState().getStateCode(), vo.getCustomer().getGstState().getStateName(),
						vo.getCustomer().getGstState().getGstStateId()));
			}

			customerDTO.setIgstApplicable(vo.getCustomer().isGstApplicable());
			customerDTO.setGstnNo(vo.getCustomer().getGstNo());

			dto.setCustomer(customerDTO);
		}

		dto.setBelongsTo(vo.getBelongsTo());
		dto.setContractType(vo.getContractType());
		dto.setWithQuotation(vo.getWithQuotation());
		dto.setInvoiceType(vo.getInvoiceType());

		dto.setQuotationNo(vo.getQuotationNo());
		dto.setQuotationDate(vo.getQuotationDate());

		dto.setCustomerPoNo(vo.getCustomerPoNo());
		dto.setCustomerPoDate(vo.getCustomerPoDate());

		dto.setEffectiveFrom(vo.getEffectiveFrom());
		dto.setEffectiveTo(vo.getEffectiveTo());

		dto.setPostRate(vo.getPostRate());

		dto.setOrgId(vo.getOrgId());
		dto.setFinancialYear(vo.getFinancialYear());

		dto.setCreatedBy(vo.getCreatedBy());
		dto.setUpdatedBy(vo.getUpdatedBy());

		dto.setCancelRemarks(vo.getCancelRemarks());
		dto.setActive(vo.isActive());

		dto.setTotalAmount(vo.getTotalAmount());
		dto.setAmountInWords(vo.getAmountInWords());
		dto.setPaymentTerms(vo.getPaymentTerms());
		dto.setPriceTerms(vo.getPriceTerms());
		dto.setTerms(vo.getTerms());
		dto.setNotes(vo.getNotes());

		// Details Mapping
		List<SalesContractDetailsResponseDTO> detailResponse = new ArrayList<>();

		if (vo.getSalesContractDetailsVO() != null) {

			for (SalesContractDetailsVO detail : vo.getSalesContractDetailsVO()) {

				SalesContractDetailsResponseDTO detailDTO = new SalesContractDetailsResponseDTO();

				detailDTO.setId(detail.getId());

				if (detail.getItem() != null) {
					detailDTO.setItem(new SalesContractItemResponseDTO(detail.getItem().getId(),
							detail.getItem().getItemCode(), detail.getItem().getItemDescription(),
							detail.getItem().getHsnCode() != null ? detail.getItem().getHsnCode().getHsn() : null,
							vo.getQuotationNo() != null ? vo.getCustomerPoNo() : null));
				}

				detailDTO.setTaxType(detail.getTaxType());
				if (detail.getTaxPercentage() != null) {

					GSTRateResponseDTO gstRateDTO = new GSTRateResponseDTO();

					gstRateDTO.setId(detail.getTaxPercentage().getId());
					gstRateDTO.setTaxPercentage(detail.getTaxPercentage().getRate()); // or getGstRate()

					detailDTO.setTaxPercentage(gstRateDTO);
				}
				if (detail.getUnit() != null) {
					detailDTO.setUnit(new UnitResponseDTO(detail.getUnit().getId(), detail.getUnit().getUnitId()));
				}

				detailDTO.setQuantity(detail.getQuantity());
				detailDTO.setQuotationRate(detail.getQuotationRate());
				detailDTO.setOrderRate(detail.getOrderRate());

				detailDTO.setDiscountPercentage(detail.getDiscountPercentage());
				detailDTO.setEffectiveFrom(detail.getEffectiveFrom());
				detailDTO.setEffectiveTo(detail.getEffectiveTo());

				detailDTO.setDiscountAmount(detail.getDiscountAmount());
				detailDTO.setAmount(detail.getAmount());
//				detailDTO.setFinalAmount(detail.getFinalAmount());

				detailDTO.setSgstRate(detail.getSgstRate());
				detailDTO.setSgstAmount(detail.getSgstAmount());

				detailDTO.setCgstRate(detail.getCgstRate());
				detailDTO.setCgstAmount(detail.getCgstAmount());

				detailDTO.setIgstRate(detail.getIgstRate());
				detailDTO.setIgstAmount(detail.getIgstAmount());

				detailDTO.setCurrency(detail.getCurrency());

				detailResponse.add(detailDTO);

			}
		}

		dto.setDetails(detailResponse);

		// Attachment Mapping
		List<SalesContractAttachResponseDTO> attachmentResponse = new ArrayList<>();

		if (vo.getAttachments() != null) {

			for (SalesContractAttachVO attachment : vo.getAttachments()) {

				SalesContractAttachResponseDTO attachmentDTO = new SalesContractAttachResponseDTO();

				attachmentDTO.setId(attachment.getId());
				attachmentDTO.setPdfAttached(attachment.getPdfAttached());

				attachmentResponse.add(attachmentDTO);
			}
		}

		dto.setAttachments(attachmentResponse);

		System.out.println("Upload Path : " + uploadPath);

		// ===================== Tax Details Mapping =====================

		List<SalesContractResponseTaxDetailsDTO> taxResponse = new ArrayList<>();

		if (vo.getSalesContractTaxDetails() != null) {

			for (SalesContractTaxDetailsVO tax : vo.getSalesContractTaxDetails()) {

				SalesContractResponseTaxDetailsDTO taxDTO = new SalesContractResponseTaxDetailsDTO();

				taxDTO.setId(tax.getId());

				if (tax.getParticulars() != null) {

					ListOfValuesDetailsResponseDTO particularsDTO = new ListOfValuesDetailsResponseDTO();

					particularsDTO.setId(tax.getParticulars().getId());
					particularsDTO.setCode(tax.getParticulars().getValueCode());
					particularsDTO.setDescription(tax.getParticulars().getValueDescription());

					taxDTO.setParticulars(particularsDTO);
				}

				taxDTO.setAmount(tax.getAmount());

				taxResponse.add(taxDTO);
			}
		}

		dto.setSalesContractTaxDetailsDTO(taxResponse);

		return dto;
	}

	// dropdown

	@Override
	public List<SalesContractItemDropdownResponseDTO> getFinishedGoodsItems(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> itemList = itemRepo.getFinishedGoodsItems(orgId, branch);

		List<SalesContractItemDropdownResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : itemList) {
			responseList.add(mapToFinishedGoodsResponseDTO(obj));
		}

		return responseList;
	}

	private SalesContractItemDropdownResponseDTO mapToFinishedGoodsResponseDTO(Object[] obj) {

		SalesContractItemDropdownResponseDTO dto = new SalesContractItemDropdownResponseDTO();

		dto.setItemId(((Number) obj[0]).longValue());
		dto.setItemCode((String) obj[1]);
		dto.setItemDescription((String) obj[2]);
		dto.setUnitId((String) obj[3]);
		dto.setMinimumSellPrice((BigDecimal) obj[4]);
		dto.setHsnCode((String) obj[5]);
		dto.setCustomerPartNo((String) obj[6]);
		dto.setRate((BigDecimal) obj[7]);
		dto.setCgst((BigDecimal) obj[8]);
		dto.setSgst((BigDecimal) obj[9]);
		dto.setIgst((BigDecimal) obj[10]);
		dto.setUnitMasterId(((Number) obj[11]).longValue());
		dto.setGstRateMasterId(((Number) obj[12]).longValue());

		return dto;
	}

	@Override
	public List<QuotationDropdownResponseDTO> getQuotationDropdown(String customerCode, String ctype, Long orgId,
			Long branch, String oldQuotationNo, Long recId) throws ApplicationException {

		List<Object[]> list = salesContractRepo.getQuotationDropdown(customerCode, ctype, orgId, branch, oldQuotationNo,
				recId);

		return convertToQuotationDropdownDTO(list);
	}

	private List<QuotationDropdownResponseDTO> convertToQuotationDropdownDTO(List<Object[]> list) {

		List<QuotationDropdownResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			QuotationDropdownResponseDTO dto = new QuotationDropdownResponseDTO();

			dto.setQuotationId(obj[0] != null ? ((Number) obj[0]).longValue() : null);

			dto.setQuotationNo(obj[1] != null ? obj[1].toString() : null);

			dto.setQuotationDate(obj[2] != null ? ((java.sql.Date) obj[2]).toLocalDate() : null);
			
			dto.setEnquiryNo(obj[3] != null ? obj[3].toString() : null);
			
			dto.setEnquiryDate(obj[4] != null ? ((java.sql.Date) obj[4]).toLocalDate() : null);


			responseList.add(dto);
		}

		return responseList;
	}

	@Override
	public List<CustomerDropdownResponseDTO> getCustomerDropdown(String ctype, Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> list = customerRepo.getCustomerDropdown(ctype, orgId, branch);

		return convertToCustomerDropdownDTO(list);
	}

	private List<CustomerDropdownResponseDTO> convertToCustomerDropdownDTO(List<Object[]> list) {

		List<CustomerDropdownResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			CustomerDropdownResponseDTO dto = new CustomerDropdownResponseDTO();

			dto.setCustomerId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
			dto.setCustomerCode(obj[1] != null ? obj[1].toString() : null);
			dto.setCustomerName(obj[2] != null ? obj[2].toString() : null);
			dto.setAddress(obj[3] != null ? obj[3].toString() : null);
			dto.setGstState(obj[4] != null ? obj[4].toString() : null);
			dto.setGstNo(obj[5] != null ? obj[5].toString() : null);
			dto.setIgstApplicable(obj[6] != null ? (Boolean) obj[6] : false);
			dto.setGstType(obj[7] != null ? obj[7].toString() : null);

			responseList.add(dto);
		}

		return responseList;
	}

	@Override
	public List<QuotationItemDropdownResponseDTO> getQuotationItemDropdown(String quotationNo, Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> list = salesContractRepo.getQuotationItemDropdown(quotationNo, orgId, branch);

		return convertToQuotationItemDropdownDTO(list);
	}

	private List<QuotationItemDropdownResponseDTO> convertToQuotationItemDropdownDTO(List<Object[]> list) {

		List<QuotationItemDropdownResponseDTO> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			QuotationItemDropdownResponseDTO dto = new QuotationItemDropdownResponseDTO();

			dto.setItemId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
			dto.setItemCode(obj[1] != null ? obj[1].toString() : null);
			dto.setItemDescription(obj[2] != null ? obj[2].toString() : null);
			dto.setHsnCode(obj[3] != null ? obj[3].toString() : null);
			dto.setCustomerPartNo(obj[4] != null ? obj[4].toString() : null);
			dto.setRate(obj[5] != null ? (BigDecimal) obj[5] : null);
			dto.setCgst(obj[6] != null ? (BigDecimal) obj[6] : null);
			dto.setSgst(obj[7] != null ? (BigDecimal) obj[7] : null);
			dto.setIgst(obj[8] != null ? (BigDecimal) obj[8] : null);
			dto.setUnitMasterId(obj[9] != null ? ((Number) obj[9]).longValue() : null);
			dto.setUnitId(obj[10] != null ? obj[10].toString() : null);
			dto.setGstRateMasterId(obj[11] != null ? ((Number) obj[11]).longValue() : null);

			responseList.add(dto);
		}

		return responseList;
	}
	
	@Override
	public SalesContractResponseDTO getSalesContractById(Long id)
	        throws ApplicationException {

	    SalesContractVO salesContractVO = salesContractRepo.findById(id)
	            .orElseThrow(() -> new ApplicationException("Sales Contract Not Found"));

	    return convertToResponse(salesContractVO);
	}
	
	@Override
	public List<SalesContractResponseDTO> getSalesContractByOrgIdAndBranch(
	        Long orgId,
	        Long branch)
	        throws ApplicationException {

	    List<SalesContractVO> salesContracts =
	            salesContractRepo.findByOrgIdAndBranch(orgId, branch);

	    List<SalesContractResponseDTO> responseList = new ArrayList<>();

	    for (SalesContractVO vo : salesContracts) {
	        responseList.add(convertToResponse(vo));
	    }

	    return responseList;
	}

}
