package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import com.efitops.basesetup.ResponseDTO.QuotationIemFileUploadDetailsDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.QuotationDTO;
import com.efitops.basesetup.dto.QuotationItemDetailsDTO;
import com.efitops.basesetup.dto.QuotationItemDetailsResponseDTO;
import com.efitops.basesetup.dto.QuotationItemTaxDetailsDTO;
import com.efitops.basesetup.dto.QuotationResponseDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.QuotationIemFileUploadDetailsVO;
import com.efitops.basesetup.entity.QuotationItemDetailsVO;
import com.efitops.basesetup.entity.QuotationItemTaxDetailsVO;
import com.efitops.basesetup.entity.QuotationVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.QuotationIemFileUploadDetailsRepo;
import com.efitops.basesetup.repository.QuotationItemDetailsRepo;
import com.efitops.basesetup.repository.QuotationItemTaxDetailsRepo;
import com.efitops.basesetup.repository.QuotationRepo;
import com.efitops.basesetup.repository.TaxDefinitionRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class QuotationServiceImpl implements QuotationService {

	public static final Logger LOGGER = LoggerFactory.getLogger(QuotationServiceImpl.class);

	@Autowired
	QuotationRepo quotationRepo;

	@Autowired
	QuotationItemTaxDetailsRepo quotationItemTaxDetailsRepo;

	@Autowired
	QuotationItemDetailsRepo quotationItemDetailsRepo;

	@Autowired
	QuotationIemFileUploadDetailsRepo quotationIemFileUploadDetailsRepo;

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
	
	@Value("${server.base-url}")
	private String serverBaseUrl;

	

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	

//	@Override
//	public Map<String, Object> updateCreateQuotation(QuotationDTO quotationDTO) throws ApplicationException {
//
//		String screenCode = "QO";
//		QuotationVO quotationVO = new QuotationVO();
//		String message;
//		if (ObjectUtils.isNotEmpty(quotationDTO.getId())) {
//
//			quotationVO = quotationRepo.findById(quotationDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Quotation not found"));
//			quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());
//			createUpdateQuotationVOByQuotationDTO(quotationDTO, quotationVO);
//			message = "Quotation Updated Successfully";
//		} else {
//
////			String docId = quotationRepo.getQuotationByDocId(quotationDTO.getOrgId(), screenCode);
////
////			quotationVO.setDocId(docId);
////
//////						// GETDOCID LASTNO +1
////			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
////					.findByOrgIdScreenCode(quotationDTO.getOrgId(), screenCode);
////			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
////			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//			createUpdateQuotationVOByQuotationDTO(quotationDTO, quotationVO);
//			quotationVO.setCreatedBy(quotationDTO.getCreatedBy());
//			quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());
//			message = "Quotation Created Successfully";
//		}
//
//		QuotationVO savedItemMaster = quotationRepo.save(quotationVO);
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("message", message);
//		response.put("quotationVO", buildQuotationResponse(savedItemMaster));
//
//		return response;
//	}
//
//	private QuotationResponseDTO buildQuotationResponse(QuotationVO quotationVO) {
//		QuotationResponseDTO responseDTO = new QuotationResponseDTO();
//
//		responseDTO.setId(quotationVO.getId());
//		responseDTO.setUserCategory(quotationVO.getUserCategory());
//		responseDTO.setDocId(quotationVO.getDocId());
//		responseDTO.setDocDate(quotationVO.getDocDate());
//		responseDTO.setWithEnquiry(quotationVO.getWithEnquiry());
//		responseDTO.setPartyName(quotationVO.getPartyName());
//		responseDTO.setOldEnquryNo(quotationVO.getOldEnquryNo());
//		responseDTO.setEnquiryNo(quotationVO.getEnquiryNo());
//		responseDTO.setEnquiryDate(quotationVO.getEnquiryDate());
//		responseDTO.setEnquiryControl(quotationVO.getEnquiryControl());
//		responseDTO.setReason(quotationVO.getReason());
//		responseDTO.setPreparedBy(quotationVO.getPreparedBy());
//		responseDTO.setQuotationSerialNo(quotationVO.getQuotationSerialNo());
//		responseDTO.setCustomerEnquiryNo(quotationVO.getCustomerEnquiryNo());
//		responseDTO.setCustomerEnquiryDate(quotationVO.getCustomerEnquiryDate());
//		responseDTO.setEnqBasicId(quotationVO.getEnqBasicId());
//		responseDTO.setValidTill(quotationVO.getValidTill());
//		responseDTO.setKindAttention(quotationVO.getKindAttention());
//		responseDTO.setTaxBasicId(quotationVO.getTaxBasicId());
//
//		responseDTO.setCreatedBy(quotationVO.getCreatedBy());
//		responseDTO.setUpdatedBy(quotationVO.getUpdatedBy());
//		responseDTO.setCancelRemarks(quotationVO.getCancelRemarks());
//		responseDTO.setScreenName(quotationVO.getScreenName());
//		responseDTO.setScreenCode(quotationVO.getScreenCode());
//		responseDTO.setOrgId(quotationVO.getOrgId());
//		responseDTO.setFinancialYear(quotationVO.getFinancialYear());
//
//		responseDTO.setAmount(quotationVO.getAmount());
//		responseDTO.setFreight(quotationVO.getFreight());
//		responseDTO.setFreightBy(quotationVO.getFreightBy());
//		responseDTO.setTotalAmount(quotationVO.getTotalAmount());
//		responseDTO.setTerms(quotationVO.getTerms());
//		responseDTO.setRemarks(quotationVO.getRemarks());
//		responseDTO.setId(quotationVO.getId());
//
//		if (quotationVO.getPlantId() != null) {
//
//			BranchResponseDTO plantDTO = new BranchResponseDTO();
//			plantDTO.setId(quotationVO.getPlantId().getId());
//			plantDTO.setBranchCode(quotationVO.getPlantId().getBranchCode());
//			plantDTO.setBranchName(quotationVO.getPlantId().getBranchName());
//			responseDTO.setPlant(plantDTO);
//
//		}
//
//		if (quotationVO.getPartyId() != null) {
//			CustomerResponseDetailsDTO customerDTO = new CustomerResponseDetailsDTO();
//			customerDTO.setId(quotationVO.getPartyId().getId());
//			customerDTO.setCustomerName(quotationVO.getPartyId().getCustomerName());
//
//			responseDTO.setParty(customerDTO);
//			responseDTO.setPartyId(quotationVO.getPartyId().getId());
//		}
//
//		if (quotationVO.getTaxCode() != null) {
//
//			TaxDefinitionResponseDTO taxDTO = new TaxDefinitionResponseDTO();
//			taxDTO.setId(quotationVO.getTaxCode().getId());
//			taxDTO.setTaxName(quotationVO.getTaxCode().getTaxDescription());
//			responseDTO.setTaxDefinition(taxDTO);
//		}
//
//		if (quotationVO.getBranch() != null) {
//
//			BranchResponseDTO branchDTO = new BranchResponseDTO();
//			branchDTO.setId(quotationVO.getBranch().getId());
//			branchDTO.setBranchCode(quotationVO.getBranch().getBranchCode());
//			branchDTO.setBranchName(quotationVO.getBranch().getBranchName());
//			responseDTO.setBranch(branchDTO);
//		}
//
//		responseDTO.setCreatedBy(quotationVO.getCreatedBy());
//		responseDTO.setOrgId(quotationVO.getOrgId());
//		responseDTO.setUpdatedBy(quotationVO.getUpdatedBy());
//		responseDTO.setCancelRemarks(quotationVO.getCancelRemarks());
//
//		List<QuotationItemDetailsResponseDTO> quotationItemDetailsList = new ArrayList<>();
//
//		if (quotationVO.getQuotationItemDetailsVO() != null) {
//
//			for (QuotationItemDetailsVO itemVO : quotationVO.getQuotationItemDetailsVO()) {
//
//				QuotationItemDetailsResponseDTO itemDTO = new QuotationItemDetailsResponseDTO();
//
//				itemDTO.setId(itemVO.getId());
//
//				if (itemVO.getItemCode() != null) {
//
//					ItemMasterResponseDetailsDTO itemCodeDTO = new ItemMasterResponseDetailsDTO();
//					itemCodeDTO.setId(itemVO.getItemCode().getId());
//					itemCodeDTO.setItemCode(itemVO.getItemCode().getItemCode());
//					itemCodeDTO.setItemDescription(itemVO.getItemCode().getItemDescription());
//
//					itemDTO.setItemCodes(itemCodeDTO);
//				}
//
//				// Item Description
//				if (itemVO.getItemDescription() != null) {
//
//					ItemMasterResponseDetailsDTO itemDescDTO = new ItemMasterResponseDetailsDTO();
//					itemDescDTO.setId(itemVO.getItemDescription().getId());
//					itemDescDTO.setItemCode(itemVO.getItemDescription().getItemCode());
//					itemDescDTO.setItemDescription(itemVO.getItemDescription().getItemDescription());
//
//					itemDTO.setItemDescriptions(itemDescDTO);
//				}
//
//				// Unit
//				if (itemVO.getUnit() != null) {
//
//					UnitResponseDTO unitDTO = new UnitResponseDTO();
//					unitDTO.setId(itemVO.getUnit().getId());
//					unitDTO.setUnitId(itemVO.getUnit().getUnitId());
//
//					itemDTO.setUnitId(unitDTO);
//				}
//
//				// Currency
//				if (itemVO.getCurrencyName() != null) {
//
//					CurrencyResponseDTO currencyDTO = new CurrencyResponseDTO();
//					currencyDTO.setId(itemVO.getCurrencyName().getId());
//					currencyDTO.setCurrencyName(itemVO.getCurrencyName().getCurrency());
//
//					itemDTO.setCurrency(currencyDTO);
//				}
//
//				itemDTO.setTaxName(itemVO.getTaxName());
//				itemDTO.setTaxCode(itemVO.getTaxCode());
//
//				itemDTO.setQtyOffered(itemVO.getQtyOffered());
//				itemDTO.setMinPrice(itemVO.getMinPrice());
//				itemDTO.setEnquiryPrice(itemVO.getEnquiryPrice());
//				itemDTO.setBasicPrice(itemVO.getBasicPrice());
//
//				itemDTO.setDiscountPercentage(itemVO.getDiscountPercentage());
//				itemDTO.setDiscountAmount(itemVO.getDiscountAmount());
//
//				itemDTO.setLastRate(itemVO.getLastRate());
//				itemDTO.setLRate(itemVO.getLRate());
//
//				itemDTO.setQuotationAmount(itemVO.getQuotationAmount());
//
//				itemDTO.setEdPercentage(itemVO.getEdPercentage());
//				itemDTO.setEdValue(itemVO.getEdValue());
//
//				itemDTO.setEduPercentage(itemVO.getEduPercentage());
//				itemDTO.setEduVal(itemVO.getEduVal());
//
//				itemDTO.setVatPercentage(itemVO.getVatPercentage());
//				itemDTO.setVatValue(itemVO.getVatValue());
//
//				itemDTO.setQuotRatePiece(itemVO.getQuotRatePiece());
//
//				itemDTO.setAmount(itemVO.getAmount());
//
//				itemDTO.setDeliveryDate(itemVO.getDeliveryDate());
//
//				itemDTO.setCurrencySymbol(itemVO.getCurrencySymbol());
//
//				itemDTO.setEnqDetailId(itemVO.getEnqDetailId());
//
//				itemDTO.setOfferControl(itemVO.getOfferControl());
//
//				itemDTO.setEnquiryItem(itemVO.getEnquiryItem());
//
//				quotationItemDetailsList.add(itemDTO);
//			}
//		}
//
//		responseDTO.setQuotationItemDetailsResponseDTO(quotationItemDetailsList);
//
//		List<QuotationItemTaxDetailsDTO> quotationItemTaxDetailsList = new ArrayList<>();
//
//		if (quotationVO.getQuotationItemTaxDetailsVO() != null) {
//			for (QuotationItemTaxDetailsVO taxVO : quotationVO.getQuotationItemTaxDetailsVO()) {
//
//				QuotationItemTaxDetailsDTO taxDTO = new QuotationItemTaxDetailsDTO();
//
//				taxDTO.setParticulars(taxVO.getParticulars());
//				taxDTO.setAmount(taxVO.getAmount());
//
//				quotationItemTaxDetailsList.add(taxDTO);
//			}
//		}
//
//		responseDTO.setQuotationItemTaxDetailsDTO(quotationItemTaxDetailsList);
//
//		List<QuotationIemFileUploadDetailsDTO> quotationFileUploadList = new ArrayList<>();
//
//		if (quotationVO.getQuotationIemFileUploadDetailsVO() != null) {
//			for (QuotationIemFileUploadDetailsVO fileVO : quotationVO.getQuotationIemFileUploadDetailsVO()) {
//
//				QuotationIemFileUploadDetailsDTO fileDTO = new QuotationIemFileUploadDetailsDTO();
//
//				fileDTO.setId(fileVO.getId());
//				fileDTO.setName(fileVO.getName());
//
//				quotationFileUploadList.add(fileDTO);
//			}
//		}
//
//		responseDTO.setQuotationIemFileUploadDetailsDTO(quotationFileUploadList);
//
//		return responseDTO;
//	}
//
//	private void createUpdateQuotationVOByQuotationDTO(QuotationDTO quotationDTO, QuotationVO quotationVO)
//			throws ApplicationException {
//
//		quotationVO.setId(quotationDTO.getId());
//		quotationVO.setUserCategory(quotationDTO.getUserCategory());
//		quotationVO.setDocId(quotationDTO.getDocId());
//		quotationVO.setDocDate(quotationDTO.getDocDate());
//
//		if (quotationDTO.getPlantId() != null && quotationDTO.getPlantId() > 0) {
//
//			BranchVO plant = branchRepo.findById(quotationDTO.getPlantId())
//					.orElseThrow(() -> new ApplicationException("Plant Not Found"));
//
//			quotationVO.setPlantId(plant);
//		}
//
//		if (quotationDTO.getPartyId() != null && quotationDTO.getPartyId() > 0) {
//
//			CustomerVO customer = customerRepo.findById(quotationDTO.getPartyId())
//					.orElseThrow(() -> new ApplicationException("Party Not Found"));
//
//			quotationVO.setPartyId(customer);
//		}
//
//		quotationVO.setWithEnquiry(quotationDTO.getWithEnquiry());
//		quotationVO.setPartyName(quotationDTO.getPartyName());
//		quotationVO.setOldEnquryNo(quotationDTO.getOldEnquryNo());
//		quotationVO.setEnquiryNo(quotationDTO.getEnquiryNo());
//		quotationVO.setEnquiryDate(quotationDTO.getEnquiryDate());
//		quotationVO.setEnquiryControl(quotationDTO.getEnquiryControl());
//		quotationVO.setReason(quotationDTO.getReason());
//		quotationVO.setPreparedBy(quotationDTO.getPreparedBy());
//		quotationVO.setQuotationSerialNo(quotationDTO.getQuotationSerialNo());
//		quotationVO.setCustomerEnquiryNo(quotationDTO.getCustomerEnquiryNo());
//		quotationVO.setCustomerEnquiryDate(quotationDTO.getCustomerEnquiryDate());
//		quotationVO.setEnqBasicId(quotationDTO.getEnqBasicId());
//
//		quotationVO.setKindAttention(quotationDTO.getKindAttention());
//
//		// Tax Code
//		if (quotationDTO.getTaxCode() != null && quotationDTO.getTaxCode() > 0) {
//
//			TaxDefinitionVO taxDefinition = taxDefinitionRepo.findById(quotationDTO.getTaxCode())
//					.orElseThrow(() -> new ApplicationException("Tax Definition Not Found"));
//
//			quotationVO.setTaxCode(taxDefinition);
//		}
//
//		quotationVO.setTaxBasicId(quotationDTO.getTaxBasicId());
//
//		// Common Fields
//		quotationVO.setCreatedBy(quotationDTO.getCreatedBy());
//		quotationVO.setUpdatedBy(quotationDTO.getUpdatedBy());
//		quotationVO.setCancelRemarks(quotationDTO.getCancelRemarks());
//		quotationVO.setScreenName(quotationDTO.getScreenName());
//		quotationVO.setScreenCode(quotationDTO.getScreenCode());
//		quotationVO.setOrgId(quotationDTO.getOrgId());
//		quotationVO.setFinancialYear(quotationDTO.getFinancialYear());
//
//		// Branch
//		if (quotationDTO.getBranchId() != null && quotationDTO.getBranchId() > 0) {
//
//			BranchVO branch = branchRepo.findById(quotationDTO.getBranchId())
//					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
//
//			quotationVO.setBranch(branch);
//		}
//
//		quotationVO.setFreight(quotationDTO.getFreight());
//		quotationVO.setFreightBy(quotationDTO.getFreightBy());
//		quotationVO.setTerms(quotationDTO.getTerms());
//		quotationVO.setRemarks(quotationDTO.getRemarks());
//
//		if (ObjectUtils.isNotEmpty(quotationVO.getId())) {
//			List<QuotationItemDetailsVO> taxInvoiceDetailsVO1 = quotationItemDetailsRepo.findByQuotationVO(quotationVO);
//			quotationItemDetailsRepo.deleteAll(taxInvoiceDetailsVO1);
//
//			List<QuotationItemTaxDetailsVO> taxInvoiceDetailsVO2 = quotationItemTaxDetailsRepo
//					.findByQuotationVO(quotationVO);
//			quotationItemTaxDetailsRepo.deleteAll(taxInvoiceDetailsVO2);
//
//			List<QuotationIemFileUploadDetailsVO> taxInvoiceDetailsVO3 = quotationIemFileUploadDetailsRepo
//					.findByQuotationVO(quotationVO);
//			quotationIemFileUploadDetailsRepo.deleteAll(taxInvoiceDetailsVO3);
//		}
//
//		BigDecimal totalAmount = BigDecimal.ZERO;
//
//		List<QuotationItemDetailsVO> itemDetailsList = new ArrayList<>();
//
//		if (quotationDTO.getQuotationItemDetailsDTO() != null) {
//
//			for (QuotationItemDetailsDTO dto : quotationDTO.getQuotationItemDetailsDTO()) {
//
//				QuotationItemDetailsVO itemVO = new QuotationItemDetailsVO();
//
//				if (dto.getItemCodeId() != null && dto.getItemCodeId() != 0) {
//					ItemMasterVO itemCode = itemMasterRepo.findById(dto.getItemCodeId())
//							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));
//
//					itemVO.setItemCode(itemCode);
//				}
//
//				if (dto.getItemDescriptionId() != null && dto.getItemDescriptionId() != 0) {
//					ItemMasterVO itemDescription = itemMasterRepo.findById(dto.getItemDescriptionId())
//							.orElseThrow(() -> new ApplicationException("Item Description Not Found"));
//
//					itemVO.setItemDescription(itemDescription);
//				}
//
//				// Unit
//				if (dto.getUnitId() != null && dto.getUnitId() != 0) {
//					UnitMasterVO unit = unitMasterRepo.findById(dto.getUnitId())
//							.orElseThrow(() -> new ApplicationException("Unit Not Found"));
//
//					itemVO.setUnit(unit);
//				}
//
//				// Currency
//				if (dto.getCurrencyNameId() != null && dto.getCurrencyNameId() != 0) {
//					CurrencyVO currency = currencyRepo.findById(dto.getCurrencyNameId())
//							.orElseThrow(() -> new ApplicationException("Currency Not Found"));
//
//					itemVO.setCurrencyName(currency);
//				}
//
//				itemVO.setTaxName(dto.getTaxName());
//				itemVO.setTaxCode(dto.getTaxCode());
//
//				itemVO.setQtyOffered(dto.getQtyOffered());
//				itemVO.setMinPrice(dto.getMinPrice());
//				itemVO.setEnquiryPrice(dto.getEnquiryPrice());
//				itemVO.setBasicPrice(dto.getBasicPrice());
//
//				itemVO.setDiscountPercentage(dto.getDiscountPercentage());
//
//				itemVO.setDiscountAmount(
//						dto.getBasicPrice().multiply(dto.getDiscountPercentage()).divide(BigDecimal.valueOf(100)));
//
//				itemVO.setLastRate(dto.getBasicPrice().subtract(itemVO.getDiscountAmount()));
//				itemVO.setLRate(dto.getLRate());
//
//				itemVO.setQuotationAmount(itemVO.getLastRate().multiply(dto.getQtyOffered()));
//				totalAmount = totalAmount.add(itemVO.getQuotationAmount());
//
//				itemVO.setEdPercentage(dto.getEdPercentage());
//				itemVO.setEdValue(dto.getEdValue());
//
//				itemVO.setEduPercentage(dto.getEduPercentage());
//				itemVO.setEduVal(dto.getEduVal());
//
//				itemVO.setVatPercentage(dto.getVatPercentage());
//				itemVO.setVatValue(dto.getVatValue());
//
//				itemVO.setQuotRatePiece(dto.getQuotRatePiece());
//
//				itemVO.setAmount(dto.getAmount());
//
//				itemVO.setDeliveryDate(dto.getDeliveryDate());
//
//				itemVO.setCurrencySymbol(dto.getCurrencySymbol());
//
//				itemVO.setEnqDetailId(dto.getEnqDetailId());
//
//				itemVO.setOfferControl(dto.getOfferControl());
//
//				itemVO.setEnquiryItem(dto.getEnquiryItem());
//
//				itemVO.setQuotationVO(quotationVO);
//
//				itemDetailsList.add(itemVO);
//			}
//		}
//
//		quotationVO.setQuotationItemDetailsVO(itemDetailsList);
//
//		List<QuotationItemTaxDetailsVO> taxDetailsList = new ArrayList<>();
//
//		if (quotationDTO.getQuotationItemTaxDetailsDTO() != null) {
//
//			for (QuotationItemTaxDetailsDTO dto : quotationDTO.getQuotationItemTaxDetailsDTO()) {
//
//				QuotationItemTaxDetailsVO taxVO = new QuotationItemTaxDetailsVO();
//
//				taxVO.setQuotationVO(quotationVO);
//				taxVO.setParticulars(dto.getParticulars());
//				taxVO.setAmount(dto.getAmount());
//
//				taxDetailsList.add(taxVO);
//			}
//		}
//
//		quotationVO.setQuotationItemTaxDetailsVO(taxDetailsList);
//
//		List<QuotationIemFileUploadDetailsVO> fileList = new ArrayList<>();
//
//		if (quotationDTO.getQuotationIemFileUploadDetailsDTO() != null) {
//
//			for (QuotationIemFileUploadDetailsDTO dto : quotationDTO.getQuotationIemFileUploadDetailsDTO()) {
//
//				QuotationIemFileUploadDetailsVO fileVO = new QuotationIemFileUploadDetailsVO();
//
//				fileVO.setName(dto.getName());
//
//				fileVO.setQuotationVO(quotationVO);
//				fileList.add(fileVO);
//			}
//		}
//
//		quotationVO.setAmount(totalAmount);
//		quotationVO.setQuotationIemFileUploadDetailsVO(fileList);
//	}

//	@Override
//	@Transactional
//	public Map<String, Object> createUpdateQuotationImages(MultipartFile[] files, String docId, String screenName,
//			String module, List<String> fileNames) throws ApplicationException, IOException {
//
//		QuotationVO quotationVO = quotationRepo.findByDocId(docId);
//
//		if (quotationVO == null) {
//			throw new ApplicationException("Quotation Not Found");
//		}
//
//		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
//
//		createDirectory(docFolder);
//
//		List<QuotationIemFileUploadDetailsVO> oldDocs = quotationIemFileUploadDetailsRepo
//				.findByQuotationVO(quotationVO);
//
//		// Delete old physical files
//		for (QuotationIemFileUploadDetailsVO doc : oldDocs) {
//
//			deleteFileSafely(doc.getFilePath());
//
//		}
//
//		quotationIemFileUploadDetailsRepo.deleteAll(oldDocs);
//
//		if (quotationVO.getQuotationIemFileUploadDetailsVO() != null) {
//			quotationVO.getQuotationIemFileUploadDetailsVO().clear();
//		} else {
//			quotationVO.setQuotationIemFileUploadDetailsVO(new ArrayList<>());
//		}
//
//		replaceDocuments(quotationVO, files, docFolder, docId, module, screenName, fileNames);
//
//		quotationRepo.save(quotationVO);
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("quotationVO", quotationVO);
//
//		return response;
//	}
//
//	private void replaceDocuments(QuotationVO quotationVO, MultipartFile[] files, Path docFolder, String docId,
//			String module, String screenName, List<String> fileNames) throws IOException {
//
//		if (files == null || files.length == 0) {
//			return;
//		}
//
//		saveFiles(quotationVO, files, docFolder, docId, module, screenName, fileNames);
//
//	}
//
//	private void saveFiles(QuotationVO quotationVO, MultipartFile[] files, Path docFolder, String docId, String module,
//			String screenName, List<String> fileNames) throws IOException {
//
//		createDirectory(docFolder);
//
//		for (int i = 0; i < files.length; i++) {
//
//			MultipartFile file = files[i];
//
//			String originalName = file.getOriginalFilename();
//
//			if (originalName == null) {
//				originalName = "file";
//			}
//
//			String extension = "";
//
//			if (originalName.contains(".")) {
//
//				extension = originalName.substring(originalName.lastIndexOf("."));
//
//				originalName = originalName.substring(0, originalName.lastIndexOf("."));
//
//			}
//
//			String fileName = originalName + "_" + docId + extension;
//
//			Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
//
//			Path filePath = docFolder.resolve(fileName);
//
//			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//			// Create relative path
//			String relativePath = baseDir.relativize(filePath).toString().replace("\\", "/");
//
//			// Create URL
//			String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//					.path("/api/quotationservice/viewQuotationImages/").path(relativePath).toUriString();
//
//			QuotationIemFileUploadDetailsVO attach = new QuotationIemFileUploadDetailsVO();
//
//			attach.setQuotationVO(quotationVO);
//
//			attach.setFileName(fileName);
//
//			attach.setFilePath(filePath.toString()); // Physical path
//
//			attach.setFileUrl(fileUrl); // URL
//
//			attach.setFileSize(file.getSize());
//
//			attach.setUploadOn(LocalDateTime.now());
//
//			quotationVO.getQuotationIemFileUploadDetailsVO().add(attach);
//		}
//
//	}
//
//	private void deleteFileSafely(String path) {
//
//		try {
//
//			if (path == null || path.isEmpty()) {
//				return;
//			}
//
//			Path file = Paths.get(path);
//
//			if (Files.exists(file)) {
//				Files.delete(file);
//			}
//
//		} catch (Exception e) {
//
//			System.out.println("Unable to delete file : " + path);
//
//		}
//
//	}
//
//	private void createDirectory(Path path) throws IOException {
//
//		if (!Files.exists(path)) {
//
//			Files.createDirectories(path);
//
//		}
//
//	}

//	@Override
//	public ResponseEntity<byte[]> viewQuotationImages(HttpServletRequest request) throws IOException {
//
//		return serveFile(request, "/api/quotationservice/viewQuotationImages/", uploadBasePath);
//
//	}
//
////	@Override
////	public ResponseEntity<byte[]> viewQuotationImages(HttpServletRequest request) throws IOException {
////		return serveFile(request, "/api/quotationservice/viewQuotationImages/", uploadBasePath);
////	}
//
//	private ResponseEntity<byte[]> serveFile(HttpServletRequest request, String apiPrefix, String uploadBasePath)
//			throws IOException {
//
//		String uri = request.getRequestURI();
//
//		String relativePath = uri.replace(apiPrefix, "");
//
//		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);
//
//		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();
//
//		Path filePath = baseDir.resolve(relativePath).normalize();
//
//		if (!filePath.startsWith(baseDir)) {
//
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//
//		}
//
//		if (!Files.exists(filePath)) {
//
//			return ResponseEntity.notFound().build();
//
//		}
//
//		String contentType = Files.probeContentType(filePath);
//
//		if (contentType == null) {
//
//			contentType = "application/octet-stream";
//
//		}
//
//		byte[] bytes = Files.readAllBytes(filePath);
//
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "inline").body(bytes);
//
//	}

	@Override
	public QuotationResponseDTO getQuotationById(Long id) throws ApplicationException {

		QuotationVO quotationVO = quotationRepo.getQuotationById(id);

		if (quotationVO == null) {
			throw new ApplicationException("Quotation Not Found");
		}

		return buildQuotationResponse(quotationVO);
	}

	@Override
	public List<QuotationResponseDTO> getQuotationByOrgId(Long orgId, Long branchId) throws ApplicationException {

		List<QuotationVO> quotationList = quotationRepo.getQuotationByOrgId(orgId, branchId);

		if (quotationList == null || quotationList.isEmpty()) {
			throw new ApplicationException("Quotation Not Found");
		}

		List<QuotationResponseDTO> responseList = new ArrayList<>();

		for (QuotationVO quotationVO : quotationList) {
			responseList.add(buildQuotationResponse(quotationVO));
		}

		return responseList;
	}
	
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateQuotation(
	        QuotationDTO quotationDTO,
	        MultipartFile[] files) throws ApplicationException {

	    QuotationVO quotationVO;
	    String message;

	    if (ObjectUtils.isNotEmpty(quotationDTO.getId())) {

	        quotationVO = quotationRepo.findById(quotationDTO.getId())
	                .orElseThrow(() -> new ApplicationException("Quotation Not Found"));

	        quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());

	        message = "Quotation Updated Successfully";

	    } else {

	        quotationVO = new QuotationVO();

	        quotationVO.setCreatedBy(quotationDTO.getCreatedBy());
	        quotationVO.setUpdatedBy(quotationDTO.getCreatedBy());

	        message = "Quotation Created Successfully";
	    }

	    // Header + Child Mapping
	    createUpdateQuotationVOByQuotationDTO(quotationDTO, quotationVO);

	    // Save Header
	    quotationVO = quotationRepo.save(quotationVO);

	    // Save Attachments
	    saveAttachments(files, quotationVO);

	    // Response
	    QuotationResponseDTO responseDTO = buildQuotationResponse(quotationVO);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("quotationVO", responseDTO);

	    return response;
	}
	
	private void createUpdateQuotationVOByQuotationDTO(QuotationDTO quotationDTO, QuotationVO quotationVO)
			throws ApplicationException {

		quotationVO.setUserCategory(quotationDTO.getUserCategory());


		if (quotationDTO.getCustomer() != null && quotationDTO.getCustomer() > 0) {

			CustomerVO customer = customerRepo.findById(quotationDTO.getCustomer())
					.orElseThrow(() -> new ApplicationException("Party Not Found"));

			quotationVO.setCustomer(customer);
		}

		quotationVO.setWithEnquiry(quotationDTO.getWithEnquiry());
//		quotationVO.setPartyName(quotationDTO.getPartyName());
		quotationVO.setOldEnquryNo(quotationDTO.getOldEnquryNo());
		quotationVO.setEnquiryNo(quotationDTO.getEnquiryNo());
		quotationVO.setEnquiryDate(quotationDTO.getEnquiryDate());
		quotationVO.setEnquiryControl(quotationDTO.getEnquiryControl());
		quotationVO.setReason(quotationDTO.getReason());
		quotationVO.setPreparedBy(quotationDTO.getPreparedBy());
		quotationVO.setQuotationSerialNo(quotationDTO.getQuotationSerialNo());
		quotationVO.setCustomerEnquiryNo(quotationDTO.getCustomerEnquiryNo());
		quotationVO.setCustomerEnquiryDate(quotationDTO.getCustomerEnquiryDate());
		quotationVO.setEnqBasicId(quotationDTO.getEnqBasicId());
		quotationVO.setValidTill(quotationDTO.getValidTill());

		quotationVO.setAmount(quotationDTO.getAmount());
		quotationVO.setDate(quotationDTO.getDate());
		quotationVO.setTotalAmount(quotationDTO.getTotalAmount());

		
		quotationVO.setKindAttention(quotationDTO.getKindAttention());

// Common Fields
		quotationVO.setCreatedBy(quotationDTO.getCreatedBy());
		quotationVO.setUpdatedBy(quotationDTO.getUpdatedBy());
		quotationVO.setCancelRemarks(quotationDTO.getCancelRemarks());
		quotationVO.setScreenName(quotationDTO.getScreenName());
		quotationVO.setScreenCode(quotationDTO.getScreenCode());
		quotationVO.setOrgId(quotationDTO.getOrgId());
		quotationVO.setFinancialYear(quotationDTO.getFinancialYear());
		quotationVO.setActive(quotationDTO.isActive());

// Branch
		if (quotationDTO.getBranchId() != null && quotationDTO.getBranchId() > 0) {

			BranchVO branch = branchRepo.findById(quotationDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			quotationVO.setBranch(branch);
		}

		quotationVO.setFreight(quotationDTO.getFreight());
		quotationVO.setFreightBy(quotationDTO.getFreightBy());
		quotationVO.setTerms(quotationDTO.getTerms());
		quotationVO.setRemarks(quotationDTO.getRemarks());

		if (ObjectUtils.isNotEmpty(quotationVO.getId())) {
			List<QuotationItemDetailsVO> taxInvoiceDetailsVO1 = quotationItemDetailsRepo.findByQuotationVO(quotationVO);
			quotationItemDetailsRepo.deleteAll(taxInvoiceDetailsVO1);

			List<QuotationItemTaxDetailsVO> taxInvoiceDetailsVO2 = quotationItemTaxDetailsRepo
					.findByQuotationVO(quotationVO);
			quotationItemTaxDetailsRepo.deleteAll(taxInvoiceDetailsVO2);

			List<QuotationIemFileUploadDetailsVO> taxInvoiceDetailsVO3 = quotationIemFileUploadDetailsRepo
					.findByQuotationVO(quotationVO);
			quotationIemFileUploadDetailsRepo.deleteAll(taxInvoiceDetailsVO3);
		}

		BigDecimal totalAmount = BigDecimal.ZERO;

		List<QuotationItemDetailsVO> itemDetailsList = new ArrayList<>();

		if (quotationDTO.getQuotationItemDetailsDTO() != null) {

			for (QuotationItemDetailsDTO dto : quotationDTO.getQuotationItemDetailsDTO()) {

				QuotationItemDetailsVO itemVO = new QuotationItemDetailsVO();

				if (dto.getItem() != null && dto.getItem() != 0) {
					ItemMasterVO itemCode = itemMasterRepo.findById(dto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					itemVO.setItem(itemCode);
				}

				// Currency
				if (dto.getCurrencyName() != null && dto.getCurrencyName() != 0) {
					CurrencyVO currency = currencyRepo.findById(dto.getCurrencyName())
							.orElseThrow(() -> new ApplicationException("Currency Not Found"));

					itemVO.setCurrencyName(currency);
				}


				itemVO.setQtyOffered(dto.getQtyOffered());
				itemVO.setBasicPrice(dto.getBasicPrice());
				itemVO.setDiscountAmount(dto.getDiscountAmount());

				itemVO.setDiscountPercentage(dto.getDiscountPercentage());

				itemVO.setDiscountAmount(
						dto.getBasicPrice().multiply(dto.getDiscountPercentage()).divide(BigDecimal.valueOf(100)));


				itemVO.setQuotationAmount(dto.getQuotationAmount());

				itemVO.setDeliveryDate(dto.getDeliveryDate());

				itemVO.setQuotationVO(quotationVO);

				itemDetailsList.add(itemVO);
			}
		}

		quotationVO.setQuotationItemDetailsVO(itemDetailsList);

		List<QuotationItemTaxDetailsVO> taxDetailsList = new ArrayList<>();

		if (quotationDTO.getQuotationItemTaxDetailsDTO() != null) {

			for (QuotationItemTaxDetailsDTO dto : quotationDTO.getQuotationItemTaxDetailsDTO()) {

				QuotationItemTaxDetailsVO taxVO = new QuotationItemTaxDetailsVO();

				taxVO.setQuotationVO(quotationVO);
				taxVO.setParticulars(dto.getParticulars());
				taxVO.setAmount(dto.getAmount());

				taxDetailsList.add(taxVO);
			}
		}

		quotationVO.setQuotationItemTaxDetailsVO(taxDetailsList);

		quotationVO.setAmount(totalAmount);
//		quotationVO.setQuotationIemFileUploadDetailsVO(fileList);
	}
	
	@Value("${quotation.upload.path}")
	private String uploadPath;

	private void saveAttachments(MultipartFile[] files, QuotationVO quotationVO)
	        throws ApplicationException {

	    if (files == null || files.length == 0) {
	        return;
	    }

	    try {

	        File folder = new File(uploadPath);

	        if (!folder.exists()) {
	            folder.mkdirs();
	        }

	        List<QuotationIemFileUploadDetailsVO> attachmentList = new ArrayList<>();

	        for (MultipartFile file : files) {

	            if (file == null || file.isEmpty()) {
	                continue;
	            }

	            String originalFileName = file.getOriginalFilename();

	            String uniqueFileName =
	                    UUID.randomUUID() + "_" + originalFileName;

	            Path path = Paths.get(uploadPath, uniqueFileName);

	            try (InputStream inputStream = file.getInputStream()) {

	                Files.copy(inputStream,
	                        path,
	                        StandardCopyOption.REPLACE_EXISTING);
	            }

	            QuotationIemFileUploadDetailsVO attachment =
	                    new QuotationIemFileUploadDetailsVO();

	            attachment.setQuotationVO(quotationVO);

	            attachment.setName(originalFileName);

	            attachment.setFileName(uniqueFileName);

	            attachment.setFilePath(path.toString());
	            
	            attachment.setFileSize(file.getSize());

	            attachment.setUploadOn(LocalDateTime.now());

	            attachmentList.add(attachment);
	        }

	        List<QuotationIemFileUploadDetailsVO> savedAttachments =
	                quotationIemFileUploadDetailsRepo.saveAll(attachmentList);

	        quotationVO.setQuotationIemFileUploadDetailsVO(savedAttachments);

	    } catch (IOException e) {

	        throw new ApplicationException(
	                "File Upload Failed : " + e.getMessage());
	    }
	}
	
	private QuotationResponseDTO buildQuotationResponse(QuotationVO quotationVO) {
		QuotationResponseDTO responseDTO = new QuotationResponseDTO();

		responseDTO.setId(quotationVO.getId());
		responseDTO.setUserCategory(quotationVO.getUserCategory());
		responseDTO.setDocId(quotationVO.getDocId());
		responseDTO.setDocDate(quotationVO.getDocDate());
		responseDTO.setWithEnquiry(quotationVO.getWithEnquiry());
//		responseDTO.setPartyName(quotationVO.getPartyName());
		responseDTO.setOldEnquryNo(quotationVO.getOldEnquryNo());
		responseDTO.setEnquiryNo(quotationVO.getEnquiryNo());
		responseDTO.setEnquiryDate(quotationVO.getEnquiryDate());
		responseDTO.setEnquiryControl(quotationVO.getEnquiryControl());
		responseDTO.setReason(quotationVO.getReason());
		responseDTO.setPreparedBy(quotationVO.getPreparedBy());
		responseDTO.setQuotationSerialNo(quotationVO.getQuotationSerialNo());
		responseDTO.setCustomerEnquiryNo(quotationVO.getCustomerEnquiryNo());
		responseDTO.setCustomerEnquiryDate(quotationVO.getCustomerEnquiryDate());
		responseDTO.setEnqBasicId(quotationVO.getEnqBasicId());
		responseDTO.setValidTill(quotationVO.getValidTill());
		responseDTO.setKindAttention(quotationVO.getKindAttention());

		responseDTO.setCreatedBy(quotationVO.getCreatedBy());
		responseDTO.setUpdatedBy(quotationVO.getUpdatedBy());
		responseDTO.setCancelRemarks(quotationVO.getCancelRemarks());
		responseDTO.setScreenName(quotationVO.getScreenName());
		responseDTO.setScreenCode(quotationVO.getScreenCode());
		responseDTO.setOrgId(quotationVO.getOrgId());
		responseDTO.setFinancialYear(quotationVO.getFinancialYear());

		responseDTO.setAmount(quotationVO.getAmount());
		responseDTO.setFreight(quotationVO.getFreight());
		responseDTO.setFreightBy(quotationVO.getFreightBy());
		responseDTO.setTotalAmount(quotationVO.getTotalAmount());
		responseDTO.setTerms(quotationVO.getTerms());
		responseDTO.setRemarks(quotationVO.getRemarks());
		responseDTO.setId(quotationVO.getId());
		
		responseDTO.setAmount(quotationVO.getAmount());
		responseDTO.setDate(quotationVO.getDate());
		responseDTO.setTotalAmount(quotationVO.getTotalAmount());

		if (quotationVO.getCustomer() != null) {
			CustomerResponseDetailsDTO customerDTO = new CustomerResponseDetailsDTO();
			customerDTO.setId(quotationVO.getCustomer().getId());
			customerDTO.setCustomerName(quotationVO.getCustomer().getCustomerName());

			responseDTO.setCustomer(customerDTO);
			responseDTO.setPartyId(quotationVO.getCustomer().getId());
		}

		if (quotationVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(quotationVO.getBranch().getId());
			branchDTO.setBranchCode(quotationVO.getBranch().getBranchCode());
			branchDTO.setBranchName(quotationVO.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);
		}

		responseDTO.setCreatedBy(quotationVO.getCreatedBy());
		responseDTO.setOrgId(quotationVO.getOrgId());
		responseDTO.setUpdatedBy(quotationVO.getUpdatedBy());
		responseDTO.setCancelRemarks(quotationVO.getCancelRemarks());

		List<QuotationItemDetailsResponseDTO> quotationItemDetailsList = new ArrayList<>();

		if (quotationVO.getQuotationItemDetailsVO() != null) {

			for (QuotationItemDetailsVO itemVO : quotationVO.getQuotationItemDetailsVO()) {

				QuotationItemDetailsResponseDTO itemDTO = new QuotationItemDetailsResponseDTO();

				itemDTO.setId(itemVO.getId());

				if (itemVO.getItem() != null) {

				    ItemMasterResponseDetailsDTO itemCodeDTO = new ItemMasterResponseDetailsDTO();

				    itemCodeDTO.setId(itemVO.getItem().getId());
				    itemCodeDTO.setItemCode(itemVO.getItem().getItemCode());
				    itemCodeDTO.setItemDescription(itemVO.getItem().getItemDescription());

				    if (itemVO.getItem().getPrimaryUnit() != null) {

				        UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

				        unitDTO.setId(itemVO.getItem().getPrimaryUnit().getId());
				        unitDTO.setUnitId(itemVO.getItem().getPrimaryUnit().getUnitId());
				        unitDTO.setUnitDescription(itemVO.getItem().getPrimaryUnit().getDescription());

				        itemCodeDTO.setUnit(unitDTO);
				    }

				    itemDTO.setItemCodes(itemCodeDTO);
				}

				

				// Currency
				if (itemVO.getCurrencyName() != null) {

					CurrencyResponseDTO currencyDTO = new CurrencyResponseDTO();
					currencyDTO.setId(itemVO.getCurrencyName().getId());
					currencyDTO.setCurrencyName(itemVO.getCurrencyName().getCurrency());

					itemDTO.setCurrency(currencyDTO);
				}


				itemDTO.setQtyOffered(itemVO.getQtyOffered());
				itemDTO.setBasicPrice(itemVO.getBasicPrice());

				itemDTO.setDiscountPercentage(itemVO.getDiscountPercentage());
				itemDTO.setDiscountAmount(itemVO.getDiscountAmount());

				itemDTO.setQuotationAmount(itemVO.getQuotationAmount());

				itemDTO.setDeliveryDate(itemVO.getDeliveryDate());

				quotationItemDetailsList.add(itemDTO);
			}
		}

		responseDTO.setQuotationItemDetailsResponseDTO(quotationItemDetailsList);

		List<QuotationItemTaxDetailsDTO> quotationItemTaxDetailsList = new ArrayList<>();

		if (quotationVO.getQuotationItemTaxDetailsVO() != null) {
			for (QuotationItemTaxDetailsVO taxVO : quotationVO.getQuotationItemTaxDetailsVO()) {

				QuotationItemTaxDetailsDTO taxDTO = new QuotationItemTaxDetailsDTO();

				taxDTO.setId(taxVO.getId());
				taxDTO.setParticulars(taxVO.getParticulars());
				taxDTO.setAmount(taxVO.getAmount());

				quotationItemTaxDetailsList.add(taxDTO);
			}
		}

		responseDTO.setQuotationItemTaxDetailsDTO(quotationItemTaxDetailsList);

		// ================= Attachments =================

		List<QuotationIemFileUploadDetailsDTO> quotationFileUploadList = new ArrayList<>();

		if (quotationVO.getQuotationIemFileUploadDetailsVO() != null) {

		    for (QuotationIemFileUploadDetailsVO fileVO
		            : quotationVO.getQuotationIemFileUploadDetailsVO()) {

		        QuotationIemFileUploadDetailsDTO fileDTO =
		                new QuotationIemFileUploadDetailsDTO();

		        fileDTO.setId(fileVO.getId());

		        fileDTO.setName(fileVO.getName());

		        fileDTO.setFileName(fileVO.getFileName());
		        
		        String urlPath = uploadPath
		                .replace("C:/", "/")
		                .replace("\\", "/");

		        fileDTO.setFilePath(serverBaseUrl + urlPath + fileVO.getFileName());
		        
		        fileDTO.setFileSize(fileVO.getFileSize());

		        fileDTO.setUploadOn(fileVO.getUploadOn());

		        quotationFileUploadList.add(fileDTO);
		    }
		}

		responseDTO.setQuotationIemFileUploadDetailsDTO(quotationFileUploadList);

		return responseDTO;
	}

	
	
	
}
