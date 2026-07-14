package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

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

import com.efitops.basesetup.dto.GrnDTO;
import com.efitops.basesetup.dto.GrnDetailsDTO;
import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.dto.SubContractGrnDTO;
import com.efitops.basesetup.dto.SubContractGrnDetailsDTO;
import com.efitops.basesetup.dto.ThirdPartyAttachmentDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDTO;
import com.efitops.basesetup.dto.ThirdPartyInspectionDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.GrnDetailsVO;
import com.efitops.basesetup.entity.GrnVO;
import com.efitops.basesetup.entity.PutawayVO;
import com.efitops.basesetup.entity.StockDetailsVO;
import com.efitops.basesetup.entity.SubContractGrnDetailsVO;
import com.efitops.basesetup.entity.SubContractGrnVO;
import com.efitops.basesetup.entity.ThirdPartyAttachmentVO;
import com.efitops.basesetup.entity.ThirdPartyAttachmentsVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionDetailsVO;
import com.efitops.basesetup.entity.ThirdPartyInspectionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.GrnDetailsRepo;
import com.efitops.basesetup.repo.GrnRepo;
import com.efitops.basesetup.repo.PurchaseOrderPendingRepo;
import com.efitops.basesetup.repo.RecieveFromSubcontractDetailsRepo;
import com.efitops.basesetup.repo.RecieveFromSubcontractRepo;
import com.efitops.basesetup.repo.StockDetailsRepo;
import com.efitops.basesetup.repo.SubContractGrnDetailsRepo;
import com.efitops.basesetup.repo.SubContractGrnRepo;
import com.efitops.basesetup.repo.ThirdPartyAttachmentRepo;
import com.efitops.basesetup.repo.ThirdPartyAttachmentsRepo;
import com.efitops.basesetup.repo.ThirdPartyImagesRepo;
import com.efitops.basesetup.repo.ThirdPartyInspectionDetailsRepo;
import com.efitops.basesetup.repo.ThirdPartyInspectionRepo;

@Service
public class GrnServiceImpl implements GrnService {
	public static final Logger LOGGER = LoggerFactory.getLogger(GrnServiceImpl.class);

	@Autowired
	GrnRepo grnRepo;

	@Autowired
	AmountInWordsConverterService amountInWordsConverterService;

	@Autowired
	GrnDetailsRepo grnDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	ThirdPartyInspectionRepo thirdPartyInspectionRepo;

	@Autowired
	ThirdPartyInspectionDetailsRepo thirdPartyInspectionDetailsRepo;

	@Autowired
	ThirdPartyAttachmentRepo thirdPartyAttachmentRepo;

	@Autowired
	RecieveFromSubcontractRepo recieveFromSubcontractRepo;

	@Autowired
	RecieveFromSubcontractDetailsRepo recieveFromSubcontractDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	PurchaseOrderPendingRepo purchaseOrderPendingRepo;

	@Autowired
	ThirdPartyImagesRepo thirdPartyImagesRepo;

	@Autowired
	SubContractGrnRepo subContractGrnRepo;

	@Autowired
	SubContractGrnDetailsRepo subContractGrnDetailsRepo;

	@Autowired
	ThirdPartyAttachmentsRepo thirdPartyAttachmentsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;

	@PersistenceContext
	private EntityManager entityManager;


	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public List<GrnVO> getGrnByOrgId(Long orgId, String finYear, String branchCode) {
		List<GrnVO> grnVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			grnVO = grnRepo.findGrnByOrgId(orgId, finYear, branchCode);
		}
		return grnVO;
	}

	@Override
	public List<GrnVO> getGrnById(Long id) {
		List<GrnVO> grnVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			grnVO = grnRepo.getGrnById(id);
		}
		return grnVO;
	}

//	@Override
//	public Map<String, Object> updateCreateGrn(GrnDTO grndto) throws ApplicationException {
//		GrnVO grnVO = new GrnVO();
//		String message;
//		String screenCode = "GRN";
//
//		if (ObjectUtils.isNotEmpty(grndto.getId())) {
//			grnVO = grnRepo.findById(grndto.getId()).orElseThrow(() -> new ApplicationException("Invalid GRN details"));
//			message = "GRN Updated Successfully";
//			createUpdateGrnVOByGrnDTO(grndto, grnVO);
//			grnVO.setUpdatedBy(grndto.getCreatedBy());
//
//		} else {
//			String docId = grnRepo.getGrnDocId(grndto.getOrgId(), grndto.getFinYear(), grndto.getBranchCode(),
//					screenCode);
//			grnVO.setGrnNo(docId);
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(grndto.getOrgId(), grndto.getFinYear(),
//							grndto.getBranchCode(), screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//			createUpdateGrnVOByGrnDTO(grndto, grnVO);
//			grnVO.setCreatedBy(grndto.getCreatedBy());
//			grnVO.setUpdatedBy(grndto.getCreatedBy());
//			message = "GRN Created Successfully";	
//		}
//
//		GrnVO savedGrnVO = grnRepo.save(grnVO);
//
//		List<GrnDetailsVO> grnDetailsListVO = savedGrnVO.getGrnDetailsVO();
//		if (grnDetailsListVO != null && !grnDetailsListVO.isEmpty()) {
//			if ("CONFIRM".equalsIgnoreCase(savedGrnVO.getStatus())) {
//				for (GrnDetailsVO detailsVO : grnDetailsListVO) {
//					StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
//					stockDetailsVOFrom.setOrgId(savedGrnVO.getOrgId());
//					stockDetailsVOFrom.setDocId(savedGrnVO.getGrnNo());
//					stockDetailsVOFrom.setDocDate(savedGrnVO.getGrnDate());
//					stockDetailsVOFrom.setRefNo(savedGrnVO.getId());
//					stockDetailsVOFrom.setLocation(savedGrnVO.getLocation());
//					stockDetailsVOFrom.setRefDate(savedGrnVO.getGrnDate());
//					stockDetailsVOFrom.setQty(detailsVO.getAcceptQty());
//					stockDetailsVOFrom.setUpdatedBy(savedGrnVO.getUpdatedBy());
//					stockDetailsVOFrom.setPartno(detailsVO.getItemCode());
//					stockDetailsVOFrom.setPartDesc(detailsVO.getItemDesc());
//					stockDetailsVOFrom.setCustomer(savedGrnVO.getSupplierName());
//					stockDetailsVOFrom.setSourceId(savedGrnVO.getId());
//					stockDetailsVOFrom.setRecQty(detailsVO.getAcceptQty());
//					stockDetailsVOFrom.setRate(detailsVO.getPoRate());
//					stockDetailsVOFrom.setAmount(savedGrnVO.getNetAmount());
//					stockDetailsVOFrom.setStatus(detailsVO.getStatus());
//					stockDetailsRepo.save(stockDetailsVOFrom);
//				}
//			}
//		}
//		
//		
//		List<GrnDetailsVO> grnDetailsVOs = savedGrnVO.getGrnDetailsVO();
//
//		Long sourceId = savedGrnVO.getId();
//		if (sourceId != null) { // Null check for safety
//		    List<PurchaseOrderPendingVO> purchaseOrderPendingList = purchaseOrderPendingRepo.findBySourceId(sourceId);
//		    
//		    if (!purchaseOrderPendingList.isEmpty()) { // Check if records exist
//		        purchaseOrderPendingRepo.deleteAll(purchaseOrderPendingList);
//		    }
//		}
//		if (grnDetailsVOs != null && !grnDetailsVOs.isEmpty()) {
//
//		for (GrnDetailsVO grnDetailsVO : grnDetailsVOs) {
//
//			PurchaseOrderPendingVO purchaseOrderPendingVO = new PurchaseOrderPendingVO();
//			purchaseOrderPendingVO.setPoNo(savedGrnVO.getPoNo());
////			purchaseOrderPendingVO.setWorkOrderNo(savedGrnVO.getWorkOrderNo());
//			purchaseOrderPendingVO.setSupplierName(savedGrnVO.getSupplierName());
//			purchaseOrderPendingVO.setSupplierCode(savedGrnVO.getSupplierCode());
//			purchaseOrderPendingVO.setItem(grnDetailsVO.getItemCode());
//			purchaseOrderPendingVO.setItemDesc(grnDetailsVO.getItemDesc());
//			purchaseOrderPendingVO.setQty(grnDetailsVO.getRecievedQty());
//			purchaseOrderPendingVO.setTaxType(grnDetailsVO.getTaxType());
////			purchaseOrderPendingVO.setuom(purchaseOrderDetailsVO.getUom());
//			purchaseOrderPendingVO.setPrice(grnDetailsVO.getPoRate());
//			purchaseOrderPendingVO.setAmount(grnDetailsVO.getAmount());
//			purchaseOrderPendingVO.setTaxValue(grnDetailsVO.getTaxValue());
//			purchaseOrderPendingVO.setLandedValue(grnDetailsVO.getLandedValue());
//			purchaseOrderPendingVO.setPlusOrMinus("M");
//			purchaseOrderPendingVO.setSourceId(savedGrnVO.getId());
//
//
//			purchaseOrderPendingVO.setFinYear(savedGrnVO.getFinYear());
//			purchaseOrderPendingVO.setCreatedBy(savedGrnVO.getCreatedBy());
//			purchaseOrderPendingVO.setUpdatedBy(savedGrnVO.getUpdatedBy());
//			purchaseOrderPendingVO.setBranch(savedGrnVO.getBranch());
//			purchaseOrderPendingVO.setBranchCode(savedGrnVO.getBranchCode());
//			purchaseOrderPendingVO.setOrgId(savedGrnVO.getOrgId());
//			purchaseOrderPendingRepo.save(purchaseOrderPendingVO);
//		}}
//		
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("grnVO", grnVO);
//		response.put("message", message);
//		return response;
//	}
//
//	private void createUpdateGrnVOByGrnDTO(@Valid GrnDTO grndto, GrnVO grnVO) throws ApplicationException {
//		grnVO.setInwardNo(grndto.getInwardNo());
//		grnVO.setLocation(grndto.getLocation());
//		grnVO.setGstNo(grndto.getGstNo());
//		grnVO.setPoNo(grndto.getPoNo());
//		grnVO.setOrgId(grndto.getOrgId());
//		grnVO.setBranch(grndto.getBranch());
//		grnVO.setBranchCode(grndto.getBranchCode());
//		grnVO.setFinYear(grndto.getFinYear());
//		grnVO.setGstType(grndto.getGstType());
////		grnVO.setAdress(grndto.getAdress());
//		grnVO.setCurrency(grndto.getCurrency());
//		grnVO.setExchangeRate(grndto.getExchangeRate());
//		grnVO.setGrnClearTime(grndto.getGrnClearTime());
//		grnVO.setInvDcNo(grndto.getInvDcNo());
//		grnVO.setInvDcDate(grndto.getInvDcDate());
//		grnVO.setCustomer(grndto.getCustomer());
//		grnVO.setStatus(grndto.getStatus());
//		grnVO.setAddress(grndto.getAddress());
//		grnVO.setSupplierName(grndto.getSupplierName());
//		grnVO.setSupplierCode(grndto.getSupplierCode());
//
//		BigDecimal grossAmount = BigDecimal.ZERO;
//		BigDecimal netAmount = BigDecimal.ZERO;
//		BigDecimal totalTaxAmount = BigDecimal.ZERO;
//
//		if (ObjectUtils.isNotEmpty(grnVO.getId())) {
//			List<GrnDetailsVO> grnDetailsVo1 = grnDetailsRepo.findByGrnVO(grnVO);
//			grnDetailsRepo.deleteAll(grnDetailsVo1);
//		}
//
//		List<GrnDetailsVO> grnDetailsVOs = new ArrayList<>();
//		for (GrnDetailsDTO grnDetailsDTO : grndto.getGrnDetailsDTO()) {
//			GrnDetailsVO grnDetailsVO = new GrnDetailsVO();
//			grnDetailsVO.setItemCode(grnDetailsDTO.getItemCode());
//			grnDetailsVO.setItemDesc(grnDetailsDTO.getItemDesc());
//			grnDetailsVO.setHsnSacCode(grnDetailsDTO.getHsnSacCode());
//			grnDetailsVO.setTaxType(grnDetailsDTO.getTaxType());
//			grnDetailsVO.setPrimaryUnit(grnDetailsDTO.getPrimaryUnit());
//			grnDetailsVO.setAcceptQty(grnDetailsDTO.getAcceptQty());
//			grnDetailsVO.setPoRate(grnDetailsDTO.getPoRate());
//			grnDetailsVO.setChallanQty(grnDetailsDTO.getChallanQty());
//			grnDetailsVO.setExcessQty(grnDetailsDTO.getExcessQty());
//			grnDetailsVO.setRecievedQty(grnDetailsDTO.getRecievedQty());
//			grnDetailsVO.setRejectQty(grnDetailsDTO.getRejectQty());
//			grnDetailsVO.setIgst(grnDetailsDTO.getIgst());
//			grnDetailsVO.setInspectionable(grnDetailsDTO.getInspectionable());
//			grnDetailsVO.setSgst(grnDetailsDTO.getSgst());
//			grnDetailsVO.setStock(grnDetailsDTO.getStock());
//			grnDetailsVO.setCgst(grnDetailsDTO.getCgst());
//			grnDetailsVO.setOrderQty(grnDetailsDTO.getOrderQty());
//
//			BigDecimal amountSet = grnDetailsDTO.getPoRate().multiply(grnDetailsDTO.getAcceptQty());
//			grnDetailsVO.setAmount(amountSet);
//			grossAmount = grossAmount.add(grnDetailsVO.getAmount());
//			grnDetailsVO.setPendingQty(grnDetailsDTO.getOrderQty().subtract(grnDetailsDTO.getChallanQty()));
//
//			BigDecimal sgstAmount = grnDetailsDTO.getSgst().multiply(grnDetailsVO.getAmount())
//					.divide(BigDecimal.valueOf(100));
//			BigDecimal cgstAmount = grnDetailsDTO.getCgst().multiply(grnDetailsVO.getAmount())
//					.divide(BigDecimal.valueOf(100));
//			BigDecimal igstAmount = grnDetailsDTO.getIgst().multiply(grnDetailsVO.getAmount())
//					.divide(BigDecimal.valueOf(100));
//
//			BigDecimal taxAmount = sgstAmount.add(cgstAmount).add(igstAmount);
//			grnDetailsVO.setTaxValue(taxAmount);
//			totalTaxAmount = totalTaxAmount.add(grnDetailsVO.getTaxValue());
//
//			BigDecimal landedValues = grnDetailsVO.getAmount().add(grnDetailsVO.getTaxValue());
//			grnDetailsVO.setLandedValue(landedValues);
//			netAmount = netAmount.add(grnDetailsVO.getLandedValue());
//
//			grnDetailsVO.setGrnVO(grnVO);
//			grnDetailsVOs.add(grnDetailsVO);
//		}
//
//		grnVO.setGrossAmount(grossAmount);
//		grnVO.setNetAmount(netAmount);
//		grnVO.setTotalAmountTax(totalTaxAmount);
//		grnVO.setGrnDetailsVO(grnDetailsVOs);
//	}

	@Override
	public Map<String, Object> updateCreateGrn(GrnDTO grndto) throws ApplicationException {
		GrnVO grnVO = new GrnVO();
		String message;
		String screenCode = "GRN";
		GrnVO oldGrn = null;
		
		if (ObjectUtils.isNotEmpty(grndto.getId())) {
			
			
			oldGrn = grnRepo.findById(grndto.getId())
					.orElseThrow(() -> new ApplicationException("GRN not found"));

			oldGrn.getGrnDetailsVO().size(); // load

			entityManager.detach(oldGrn); // detach snapshot
			grnVO = grnRepo.findById(grndto.getId()).orElseThrow(() -> new ApplicationException("Invalid GRN details"));
			message = "GRN Updated Successfully";
			createUpdateGrnVOByGrnDTO(grndto, grnVO);
			grnVO.setUpdatedBy(grndto.getCreatedBy());

		} else {
			String docId = grnRepo.getGrnDocId(grndto.getOrgId(), grndto.getFinYear(), grndto.getBranchCode(),
					screenCode);
			grnVO.setGrnNo(docId);
			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(grndto.getOrgId(), grndto.getFinYear(),
							grndto.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			createUpdateGrnVOByGrnDTO(grndto, grnVO);
			grnVO.setCreatedBy(grndto.getCreatedBy());
			grnVO.setUpdatedBy(grndto.getCreatedBy());

			GrnVO savedGrnVO = grnRepo.save(grnVO);
			List<GrnDetailsVO> grnDetailsListVO = savedGrnVO.getGrnDetailsVO();

			if (grnDetailsListVO != null && !grnDetailsListVO.isEmpty()) {

				for (GrnDetailsVO detailsVO : grnDetailsListVO) {
					StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();

					stockDetailsVOFrom.setOrgId(savedGrnVO.getOrgId());
					stockDetailsVOFrom.setDocId(savedGrnVO.getGrnNo());
					stockDetailsVOFrom.setDocDate(savedGrnVO.getGrnDate());
					stockDetailsVOFrom.setRefNo(savedGrnVO.getId());
					stockDetailsVOFrom.setBranch(savedGrnVO.getBranch());
					stockDetailsVOFrom.setBranchCode(savedGrnVO.getBranchCode());
					stockDetailsVOFrom.setLocation(savedGrnVO.getLocation());
					stockDetailsVOFrom.setFinYear(savedGrnVO.getFinYear());
					stockDetailsVOFrom.setRefDate(savedGrnVO.getGrnDate());

					stockDetailsVOFrom.setQty(detailsVO.getChallanQty());
					stockDetailsVOFrom.setRecQty(detailsVO.getAcceptQty());
					stockDetailsVOFrom.setRate(detailsVO.getPoRate());

					stockDetailsVOFrom.setPartno(detailsVO.getItemCode());
					stockDetailsVOFrom.setPartDesc(detailsVO.getItemDesc());
					stockDetailsVOFrom.setActive(true);
					stockDetailsVOFrom.setCancel(false);
					stockDetailsVOFrom.setCustomer(savedGrnVO.getSupplierName());
					stockDetailsVOFrom.setSourceId(savedGrnVO.getId());
					stockDetailsVOFrom.setSourceScreenName(savedGrnVO.getScreenName());
					stockDetailsVOFrom.setSourceScreenCode(savedGrnVO.getScreenCode());
					stockDetailsVOFrom.setPlusOrMinus("p");
					stockDetailsVOFrom.setAmount(detailsVO.getLandedValue());
					stockDetailsVOFrom.setStatus("CONFIRM");

					stockDetailsVOFrom.setCreatedBy(savedGrnVO.getCreatedBy());
					stockDetailsVOFrom.setUpdatedBy(savedGrnVO.getUpdatedBy());

					stockDetailsRepo.save(stockDetailsVOFrom);
				}
			}

			message = "GRN Created Successfully";
		}

		commonNotificationService.generateNotification(grnVO.getScreenCode(), grnVO.getId(), oldGrn,
				grnVO);
		
		Map<String, Object> response = new HashMap<>();
		response.put("grnVO", grnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGrnVOByGrnDTO(@Valid GrnDTO grndto, GrnVO grnVO) throws ApplicationException {
		grnVO.setInwardNo(grndto.getInwardNo());
		grnVO.setLocation(grndto.getLocation());
		grnVO.setGstNo(grndto.getGstNo());
		grnVO.setPoNo(grndto.getPoNo());
		grnVO.setOrgId(grndto.getOrgId());
		grnVO.setBranch(grndto.getBranch());
		grnVO.setBranchCode(grndto.getBranchCode());
		grnVO.setFinYear(grndto.getFinYear());
		grnVO.setGstType(grndto.getGstType());
//		grnVO.setAdress(grndto.getAdress());
		grnVO.setCurrency(grndto.getCurrency());
		grnVO.setExchangeRate(grndto.getExchangeRate());
		grnVO.setGrnClearTime(grndto.getGrnClearTime());
		grnVO.setInvDcNo(grndto.getInvDcNo());
		grnVO.setInvDcDate(grndto.getInvDcDate());
		grnVO.setCustomer(grndto.getCustomer());
		grnVO.setStatus(grndto.getStatus());
		grnVO.setAddress(grndto.getAddress());
		grnVO.setSupplierName(grndto.getSupplierName());
		grnVO.setSupplierCode(grndto.getSupplierCode());

		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(grnVO.getId())) {
			List<GrnDetailsVO> grnDetailsVo1 = grnDetailsRepo.findByGrnVO(grnVO);
			grnDetailsRepo.deleteAll(grnDetailsVo1);
		}

		List<GrnDetailsVO> grnDetailsVOs = new ArrayList<>();
		for (GrnDetailsDTO grnDetailsDTO : grndto.getGrnDetailsDTO()) {
			GrnDetailsVO grnDetailsVO = new GrnDetailsVO();
			grnDetailsVO.setItemCode(grnDetailsDTO.getItemCode());
			grnDetailsVO.setItemDesc(grnDetailsDTO.getItemDesc());
			grnDetailsVO.setHsnSacCode(grnDetailsDTO.getHsnSacCode());
			grnDetailsVO.setTaxType(grnDetailsDTO.getTaxType());
			grnDetailsVO.setPrimaryUnit(grnDetailsDTO.getPrimaryUnit());
			if (grnDetailsDTO.getAcceptQty() == null || grnDetailsDTO.getAcceptQty().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("AcceptQty must be greater than zero.");
			}
			grnDetailsVO.setAcceptQty(grnDetailsDTO.getAcceptQty());
			grnDetailsVO.setPoRate(grnDetailsDTO.getPoRate());
			grnDetailsVO.setChallanQty(grnDetailsDTO.getChallanQty());
			grnDetailsVO.setExcessQty(grnDetailsDTO.getExcessQty());
			grnDetailsVO.setRecievedQty(grnDetailsDTO.getRecievedQty());
			grnDetailsVO.setRejectQty(grnDetailsDTO.getRejectQty());
			grnDetailsVO.setIgst(grnDetailsDTO.getIgst());
			grnDetailsVO.setInspectionable(grnDetailsDTO.getInspectionable());
			grnDetailsVO.setSgst(grnDetailsDTO.getSgst());
			grnDetailsVO.setStock(grnDetailsDTO.getStock());
			grnDetailsVO.setCgst(grnDetailsDTO.getCgst());
			grnDetailsVO.setOrderQty(grnDetailsDTO.getOrderQty());
			BigDecimal amountSet = grnDetailsDTO.getPoRate().multiply(grnDetailsDTO.getAcceptQty());
			grnDetailsVO.setAmount(amountSet);
			grossAmount = grossAmount.add(grnDetailsVO.getAmount());
			grnDetailsVO.setPendingQty(grnDetailsDTO.getOrderQty().subtract(grnDetailsDTO.getAcceptQty()));

			BigDecimal sgstAmount = grnDetailsDTO.getSgst().multiply(grnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal cgstAmount = grnDetailsDTO.getCgst().multiply(grnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal igstAmount = grnDetailsDTO.getIgst().multiply(grnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));

			BigDecimal taxAmount = sgstAmount.add(cgstAmount).add(igstAmount);
			grnDetailsVO.setTaxValue(taxAmount);
			totalTaxAmount = totalTaxAmount.add(grnDetailsVO.getTaxValue());

			BigDecimal landedValues = grnDetailsVO.getAmount().add(grnDetailsVO.getTaxValue());
			grnDetailsVO.setLandedValue(landedValues);
			netAmount = netAmount.add(grnDetailsVO.getLandedValue());

			grnDetailsVO.setGrnVO(grnVO);
			grnDetailsVOs.add(grnDetailsVO);
		}

		grnVO.setGrossAmount(grossAmount);
		grnVO.setNetAmount(netAmount);
		grnVO.setTotalAmountTax(totalTaxAmount);
		grnVO.setGrnDetailsVO(grnDetailsVOs);
	}

	@Override
	public String getGrnDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "GRN";
		String result = grnRepo.getGrnDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getInwardNoForGRN(Long orgId) {
		Set<Object[]> address = grnRepo.findInwardNoForGRNDetails(orgId);
		return getInwardNoForGRN(address);
	}

	private List<Map<String, Object>> getInwardNoForGRN(Set<Object[]> chCode) {
		List<Map<String, Object>> inward = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("docid", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("ponumber", ch[1] != null ? ch[1].toString() : "");
			map.put("suppliername", ch[2] != null ? ch[2].toString() : "");
			map.put("vehicleno", ch[3] != null ? ch[3].toString() : "");
			map.put("invoiceno", ch[4] != null ? ch[4].toString() : "");
			map.put("invoicedate", ch[5] != null ? ch[5].toString() : "");
			map.put("currency", ch[6] != null ? ch[6].toString() : "");
			map.put("gstin", ch[7] != null ? ch[7].toString() : "");
			map.put("supplierCode", ch[8] != null ? ch[8].toString() : "");
			map.put("sellingPrice", ch[9] != null ? ch[9].toString() : "");
			inward.add(map);
		}
		return inward;
	}

	@Override
	public List<Map<String, Object>> getItemForGRN(Long orgId, String InwardNo) {
		Set<Object[]> grnitem = grnRepo.findItemForGRNDetails(orgId, InwardNo);
		return getItemForGRN(grnitem);
	}

	private List<Map<String, Object>> getItemForGRN(Set<Object[]> chCode) {
		List<Map<String, Object>> itemgrn = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("inwardqty", ch[2] != null ? ch[2].toString() : "");
			map.put("invoiceqty", ch[3] != null ? ch[3].toString() : "");
			map.put("poqty", ch[4] != null ? ch[4].toString() : "");
//			map.put("uom", ch[5] != null ? ch[5].toString() : "");
			map.put("hsncode", ch[5] != null ? ch[5].toString() : "");
			map.put("inspection", ch[6] != null ? ch[6].toString() : "");
			map.put("needqcapproval", ch[7] != null ? ch[7].toString() : "");
			map.put("price", ch[8] != null ? ch[8].toString() : "");
			map.put("taxslab", ch[9] != null ? ch[9].toString() : "");
			map.put("uom", ch[10] != null ? ch[10].toString() : "");
			map.put("poBalanceQty", ch[11] != null ? ch[11].toString() : "");

			itemgrn.add(map);
		}
		return itemgrn;
	}

	@Override
	public List<Map<String, Object>> findGRNForThirdPartyInspDetails(Long orgId) {
		Set<Object[]> grnthirdpartyinsp = thirdPartyInspectionRepo.findGRNForThirdPartyInspDetails(orgId);
		return getGRNForThirdPartyInsp(grnthirdpartyinsp);
	}

	private List<Map<String, Object>> getGRNForThirdPartyInsp(Set<Object[]> grnthirdpartyinsp) {
		List<Map<String, Object>> grnthirdpaty = new ArrayList<>();
		for (Object[] ch : grnthirdpartyinsp) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnno", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("inwardno", ch[1] != null ? ch[1].toString() : "");
			map.put("customer", ch[2] != null ? ch[2].toString() : "");
			map.put("suppliername", ch[3] != null ? ch[3].toString() : "");
			map.put("workOrderNo", ch[4] != null ? ch[4].toString() : "");
			map.put("pono", ch[5] != null ? ch[5].toString() : "");

			grnthirdpaty.add(map);
		}
		return grnthirdpaty;
	}

	// ThirdPartyInspection

	@Override
	public List<ThirdPartyInspectionVO> getThirdPartyInspByOrgId(Long orgId, String finYear, String branchCode) {
		List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Item BY OrgId : {}", orgId);
			thirdPartyInspectionVO = thirdPartyInspectionRepo.findThirdPartyInspectionOrgId(orgId, finYear, branchCode);
		}
		return thirdPartyInspectionVO;
	}

	@Override
	public List<ThirdPartyInspectionVO> getThirdPartyInspById(Long id) {
		List<ThirdPartyInspectionVO> thirdPartyInspectionVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			thirdPartyInspectionVO = thirdPartyInspectionRepo.getThirdPartyInspectionById(id);
		}
		return thirdPartyInspectionVO;
	}

	@Override
	public Map<String, Object> updateCreateThirdPartyInsp(ThirdPartyInspectionDTO thirdPartyInspectionDTO)
			throws ApplicationException {

		ThirdPartyInspectionVO thirdPartyInspectionVO;
		String message;
		String screenCode = "TPI";
		ThirdPartyInspectionVO oldThirdPartyInspection = null;
		
		if (ObjectUtils.isNotEmpty(thirdPartyInspectionDTO.getId())) {
			oldThirdPartyInspection = thirdPartyInspectionRepo.findById(thirdPartyInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Putaway not found"));

			oldThirdPartyInspection.getThirdPartyInspectionDetailsVO().size(); // load

			entityManager.detach(oldThirdPartyInspection); // detach snapshot


			thirdPartyInspectionVO = thirdPartyInspectionRepo.findById(thirdPartyInspectionDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Thirdparty Inspection details"));

			message = "Thirdparty Inspection Updated Successfully";
			thirdPartyInspectionVO.setUpdatedBy(thirdPartyInspectionDTO.getCreatedBy());

		} else {

			thirdPartyInspectionVO = new ThirdPartyInspectionVO();

			String docId = thirdPartyInspectionRepo.getThirdPartyInspectionDocId(thirdPartyInspectionDTO.getOrgId(),
					thirdPartyInspectionDTO.getFinYear(), thirdPartyInspectionDTO.getBranchCode(), screenCode);

			thirdPartyInspectionVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(thirdPartyInspectionDTO.getOrgId(),
							thirdPartyInspectionDTO.getFinYear(), thirdPartyInspectionDTO.getBranchCode(), screenCode);

			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			thirdPartyInspectionVO.setCreatedBy(thirdPartyInspectionDTO.getCreatedBy());
			thirdPartyInspectionVO.setUpdatedBy(thirdPartyInspectionDTO.getCreatedBy());

			message = "Thirdparty Inspection Created Successfully";
		}

		createUpdateThirdPartyInspectionVOByThirdPartyInspectionDTO(thirdPartyInspectionDTO, thirdPartyInspectionVO);

		thirdPartyInspectionRepo.save(thirdPartyInspectionVO);
		commonNotificationService.generateNotification(thirdPartyInspectionVO.getScreenCode(), thirdPartyInspectionVO.getId(), oldThirdPartyInspection,
				thirdPartyInspectionVO);

		Map<String, Object> response = new HashMap<>();
		response.put("thirdPartyInspectionVO", thirdPartyInspectionVO);
		response.put("message", message);

		return response;
	}

	private void createUpdateThirdPartyInspectionVOByThirdPartyInspectionDTO(ThirdPartyInspectionDTO dto,
			ThirdPartyInspectionVO vo) throws ApplicationException {

		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setFinYear(dto.getFinYear());
		vo.setGrnNo(dto.getGrnNo());
		vo.setWorkOrderNo(dto.getWorkOrderNo());
		vo.setPoNo(dto.getPoNo());
		vo.setCustomerName(dto.getCustomerName());
		vo.setSupplierName(dto.getSupplierName());
		vo.setThirdPartyDetails(dto.getThirdPartyDetails());
		vo.setThirdPartyAddress(dto.getThirdPartyAddress());

		if (vo.getThirdPartyInspectionDetailsVO() == null) {
			vo.setThirdPartyInspectionDetailsVO(new ArrayList<>());
		} else {
			vo.getThirdPartyInspectionDetailsVO().clear();
		}

		for (ThirdPartyInspectionDetailsDTO detailsDTO : dto.getThirdPartyInspectionDetailsDTO()) {

			ThirdPartyInspectionDetailsVO detailsVO = new ThirdPartyInspectionDetailsVO();

			detailsVO.setItemId(detailsDTO.getItemId());
			detailsVO.setItemDesc(detailsDTO.getItemDesc());
			detailsVO.setInspectionType(detailsDTO.getInspectionType());
			detailsVO.setCertificateNo(detailsDTO.getCertificateNo());
			detailsVO.setRemarks(detailsDTO.getRemarks());
			detailsVO.setStatus(detailsDTO.getStatus());
			detailsVO.setThirdPartyInspectionVO(vo);

			vo.getThirdPartyInspectionDetailsVO().add(detailsVO);
		}

		/* ---------------- Attachments ---------------- */

		if (vo.getThirdPartyAttachmentVO() == null) {
			vo.setThirdPartyAttachmentVO(new ArrayList<>());
		} else {
			vo.getThirdPartyAttachmentVO().clear();
		}

		for (ThirdPartyAttachmentDTO attachmentDTO : dto.getThirdPartyAttachmentDTO()) {

			ThirdPartyAttachmentVO attachmentVO = new ThirdPartyAttachmentVO();

			attachmentVO.setItemId(attachmentDTO.getItemId());
			attachmentVO.setThirdPartyInspectionVO(vo);

			vo.getThirdPartyAttachmentVO().add(attachmentVO);
		}
	}

	@Override
	public String getThirdPartyInspectionDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "TPI";
		String result = thirdPartyInspectionRepo.getThirdPartyInspectionDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

//	@Transactional
//	@Override
//	public Map<String, Object> uploadFileForThirdPartyInspection(Long thirdPartyId,
//			ThirdPartyAttachmentDTO attachmentDTO) throws IOException {
//
//		if (attachmentDTO.getAttachmentId() == null)
//			throw new RuntimeException("attachmentId is required");
//
//		if (attachmentDTO.getFiles() == null || attachmentDTO.getFiles().length == 0)
//			throw new RuntimeException("No files provided");
//
//		ThirdPartyInspectionVO inspection = thirdPartyInspectionRepo.findById(thirdPartyId)
//				.orElseThrow(() -> new RuntimeException("Inspection not found"));
//
//		ThirdPartyAttachmentVO attachment = thirdPartyAttachmentRepo.findById(attachmentDTO.getAttachmentId())
//				.orElseThrow(() -> new RuntimeException("Attachment not found"));
//
//		if (!attachment.getThirdPartyInspectionVO().getId().equals(thirdPartyId))
//			throw new RuntimeException("Attachment does not belong to this inspection");
//
//		List<ThirdPartyImagesVO> oldImages = new ArrayList<>(attachment.getThirdPartyImagesVO());
//
//		for (ThirdPartyImagesVO img : oldImages) {
//			if (img.getFilePath() != null) {
//				Files.deleteIfExists(Paths.get(img.getFilePath()));
//			}
//		}
//
//		thirdPartyImagesRepo.deleteAll(oldImages);
//		attachment.getThirdPartyImagesVO().clear();
//
//		Path baseDir = Paths.get(uploadDir).resolve(attachment.getItemId());
//
//		Files.createDirectories(baseDir);
//
//		List<ThirdPartyImagesVO> newImages = new ArrayList<>();
//
//		for (MultipartFile file : attachmentDTO.getFiles()) {
//			if (file.isEmpty())
//				continue;
//
//			String safeName = System.currentTimeMillis() + "_"
//					+ file.getOriginalFilename().replaceAll("[\\\\/:*?\"<>|]", "_");
//
//			Path filePath = baseDir.resolve(safeName);
//
//			try (InputStream in = file.getInputStream()) {
//				Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
//			}
//
//			ThirdPartyImagesVO image = new ThirdPartyImagesVO();
//			image.setFileName(safeName);
//			image.setFilePath(filePath.toString());
//			image.setThirdPartyAttachmentVO(attachment);
//
//			newImages.add(image);
//		}
//
//		attachment.getThirdPartyImagesVO().addAll(newImages);
//		thirdPartyAttachmentRepo.saveAndFlush(attachment);
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("thirdPartyInspectionId", thirdPartyId);
//		response.put("attachmentId", attachment.getId());
//		response.put("uploadedCount", newImages.size());
//		response.put("status", "File Upload Successfully");
//
//		return response;
//	}

	@Override
	public List<Map<String, Object>> getSupplierAddressForGRN(Long orgId, String supplierName) {
		Set<Object[]> address = grnRepo.findSupplierAddressDetails

		(orgId, supplierName);
		return getSupplierAddressForGRN(address);
	}

	private List<Map<String, Object>> getSupplierAddressForGRN(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("full_address", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("stategstin", ch[1] != null ? ch[1].toString() : "");
			map.put("state", ch[2] != null ? ch[2].toString() : "");
			map.put("pinCode", ch[3] != null ? ch[3].toString() : "");
			map.put("city", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSGSTandCGSTForGRN(Long orgId, String taxType, String gstType) {
		Set<Object[]> chType = grnRepo.findgetSGSTandCGSTForGRN(orgId, taxType, gstType);
		return getCGST(chType);
	}

	private List<Map<String, Object>> getCGST(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("sgstpercentage", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getIGSTForGRN(Long orgId, String taxType, String gstType) {
		Set<Object[]> chType = grnRepo.findgetIGSTForGRN(orgId, taxType, gstType);
		return getIGST(chType);
	}

	private List<Map<String, Object>> getIGST(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("igstpercentage", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getThirdPartyDetailsForThirdPartyInsp(Long orgId) {
		Set<Object[]> chType = thirdPartyInspectionRepo.findgetThirdPartyDetailsForThirdPartyInsp(orgId);
		return getThirdPartyDetailsForThirdPartyInsp(chType);
	}

	private List<Map<String, Object>> getThirdPartyDetailsForThirdPartyInsp(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partyName", ch[0] != null ? ch[0].toString() : "");
			map.put("address", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getGrnavlstock(Long orgId, String itemCode) {
		Set<Object[]> stock = grnRepo.findGrnAvlStock(orgId, itemCode);
		return getGrnavlstock(stock);
	}

	private List<Map<String, Object>> getGrnavlstock(Set<Object[]> chCode) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("stock", ch[0] != null ? ch[0].toString() : ""); // Empty string if null

			stock.add(map);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getAvailableStock(Long orgId, String branchCode, String location,
			String itemCode) {
		Set<Object[]> stock = grnRepo.getAvailableStock(orgId, branchCode, location, itemCode);
		return getAvailableStock(stock);
	}

	private List<Map<String, Object>> getAvailableStock(Set<Object[]> chCode) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("availableStock", ch[0] != null ? ch[0].toString() : "");
			stock.add(map);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getGrnItemDetails(Long orgId, String grnNo) {
		Set<Object[]> grnitem = grnRepo.getGrnItemDetails(orgId, grnNo);
		return getGrnItemDetails(grnitem);
	}

	private List<Map<String, Object>> getGrnItemDetails(Set<Object[]> chCode) {
		List<Map<String, Object>> itemgrn = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("orderqty", ch[2] != null ? ch[2].toString() : "");
			map.put("recievedqty", ch[3] != null ? ch[3].toString() : "");
			map.put("porate", ch[4] != null ? ch[4].toString() : "");
//			map.put("uom", ch[5] != null ? ch[5].toString() : "");
			map.put("hsncode", ch[5] != null ? ch[5].toString() : "");
			map.put("inspection", ch[6] != null ? ch[6].toString() : "");
			map.put("needqcapproval", ch[7] != null ? ch[7].toString() : "");
			map.put("price", ch[8] != null ? ch[8].toString() : "");
			map.put("taxslab", ch[9] != null ? ch[9].toString() : "");
			map.put("uom", ch[10] != null ? ch[10].toString() : "");
			map.put("inwardNo", ch[11] != null ? ch[11].toString() : "");

			itemgrn.add(map);
		}
		return itemgrn;
	}

	@Override
	public List<Map<String, Object>> getRemainingBalanceQty(Long orgId, String branchCode, String purchaseOrderNo,
			String itemCode) {
		Set<Object[]> grnitem = grnRepo.getRemainingBalanceQty(orgId, branchCode, purchaseOrderNo, itemCode);
		return getRemainingBalanceQty(grnitem);
	}

	private List<Map<String, Object>> getRemainingBalanceQty(Set<Object[]> chCode) {
		List<Map<String, Object>> itemgrn = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("purchaseOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("orderQty", ch[3] != null ? ch[3].toString() : "");
			map.put("rejectQty", ch[4] != null ? ch[4].toString() : "");
			map.put("recievedqty", ch[5] != null ? ch[5].toString() : "");
			map.put("remainingQty", ch[6] != null ? ch[6].toString() : "");
			itemgrn.add(map);
		}
		return itemgrn;
	}

	@Override
	public List<Map<String, Object>> getAllShowsAvalibaleqty(Long orgId, String branchCode, String location,
			String itemCode) {
		Set<Object[]> grnitem = grnRepo.getAllShowsAvalibaleqty(orgId, branchCode, location, itemCode);
		return getAllShowsAvalibaleqty(grnitem);
	}

	private List<Map<String, Object>> getAllShowsAvalibaleqty(Set<Object[]> chCode) {
		List<Map<String, Object>> itemgrn = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("avalibaleQty", ch[0] != null ? ch[0].toString() : "");
			itemgrn.add(map);
		}
		return itemgrn;
	}

	@Override
	public List<Map<String, Object>> getGrnDetails(Long orgId, String supplierName, String fromDate, String toDate,
			String branchCode) {
		Set<Object[]> chType = grnRepo.getGrnDetails(orgId, supplierName, fromDate, toDate, branchCode);
		return getGrnDetails(chType);
	}

	private List<Map<String, Object>> getGrnDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnNo", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("grnDate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("invDcNo", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("inwardNo", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("gstNo", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("location", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("poNo", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("supplierName", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("supplierCode", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("status", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("itemCode", ch[10] != null ? ch[10].toString() : ""); // 5
			map.put("itemDesc", ch[11] != null ? ch[11].toString() : ""); // 6
			map.put("primaryUnit", ch[12] != null ? ch[12].toString() : ""); // 7
			map.put("poRate", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 10
			map.put("acceptQty", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 11
			map.put("amount", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 12
			map.put("igst", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 13
			map.put("taxValue", ch[17] != null ? new BigDecimal(ch[17].toString()) : BigDecimal.ZERO); // 19
			map.put("totalAmount", ch[18] != null ? new BigDecimal(ch[18].toString()) : BigDecimal.ZERO);
			map.put("grnId", ch[19] != null ? ch[19].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getGrnSummaryDetails(Long orgId, String supplierName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = grnRepo.getGrnSummaryDetails(orgId, supplierName, fromDate, toDate, branchCode);
		return getGrnSummaryDetails(chType);
	}

	private List<Map<String, Object>> getGrnSummaryDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("grnNo", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("grnDate", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("invDcNo", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("inwardNo", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("gstNo", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("location", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("poNo", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("supplierName", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("supplierCode", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("status", ch[9] != null ? ch[9].toString() : "");
			map.put("gstType", ch[10] != null ? ch[10].toString() : "");
			map.put("grossAmount", ch[11] != null ? new BigDecimal(ch[11].toString()) : BigDecimal.ZERO);
			map.put("totalTaxAmount", ch[12] != null ? new BigDecimal(ch[12].toString()) : BigDecimal.ZERO);
			map.put("netAmount", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO);
			map.put("igst", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO);
			map.put("cgst", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO);
			map.put("sgst", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO);
			map.put("approvestatus", ch[17] != null ? ch[17].toString() : "");
			map.put("grnId", ch[18] != null ? ch[18].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getThirdPartyInspectionReport(Long orgId, String fromDate, String toDate,
			String partyName) {

		Set<Object[]> reportData = thirdPartyInspectionRepo.getThirdPartyInspectionReport(orgId, fromDate, toDate,
				partyName);

		return mapThirdPartyInspectionReport(reportData);
	}

	private List<Map<String, Object>> mapThirdPartyInspectionReport(Set<Object[]> reportData) {

		List<Map<String, Object>> list = new ArrayList<>();

		for (Object[] ch : reportData) {

			Map<String, Object> map = new HashMap<>();

			map.put("inspectionId", ch[0] != null ? ch[0].toString() : "");
			map.put("inspectionNo", ch[1] != null ? ch[1].toString() : "");
			map.put("inspectionDate", ch[2] != null ? ch[2].toString() : "");
			map.put("grnNo", ch[3] != null ? ch[3].toString() : "");
			map.put("workOrderNo", ch[4] != null ? ch[4].toString() : "");
			map.put("poNo", ch[5] != null ? ch[5].toString() : "");
			map.put("customerName", ch[6] != null ? ch[6].toString() : "");
			map.put("supplierName", ch[7] != null ? ch[7].toString() : "");
			map.put("thirdPartyName", ch[8] != null ? ch[8].toString() : "");

			map.put("itemId", ch[9] != null ? ch[9].toString() : "");
			map.put("itemParticular", ch[10] != null ? ch[10].toString() : "");
			map.put("inspectionType", ch[11] != null ? ch[11].toString() : "");
			map.put("certificateNo", ch[12] != null ? ch[12].toString() : "");
			map.put("status", ch[13] != null ? ch[13].toString() : "");
			map.put("remarks", ch[14] != null ? ch[14].toString() : "");

			list.add(map);
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> getSupplierName(Long orgId) {
		Set<Object[]> chType = grnRepo.getSupplierName(orgId);
		return getSupplierName(chType);
	}

	private List<Map<String, Object>> getSupplierName(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partyname", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("partycode", ch[1] != null ? ch[1].toString() : ""); // 1
			List1.add(map);
		}
		return List1;
	}

	// SubContractGrn

	@Override
	public List<SubContractGrnVO> getAllSubContractGrnByOrgId(Long orgId, String finYear, String branchCode) {

		return subContractGrnRepo.getAllSubContractGrnByOrgId(orgId, finYear, branchCode);

	}

	@Override
	public SubContractGrnVO getSubContractGrnById(Long id) {

		return subContractGrnRepo.getSubContractGrnById(id);

	}

	@Override
	public Map<String, Object> updateCreateSubContractGrn(SubContractGrnDTO subContractGrnDTO)
			throws ApplicationException {
		SubContractGrnVO subContractGrnVO = new SubContractGrnVO();
		String message;
		String screenCode = "SGRN";

		if (ObjectUtils.isNotEmpty(subContractGrnDTO.getId())) {
			subContractGrnVO = subContractGrnRepo.findById(subContractGrnDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid GRN details"));
			message = "GRN Updated Successfully";
			createUpdateSubContractGrnVOBySubContractGrnDTO(subContractGrnDTO, subContractGrnVO);
			subContractGrnVO.setUpdatedBy(subContractGrnDTO.getCreatedBy());

		} else {
			String docId = subContractGrnRepo.getSubContractGrnDocId(subContractGrnDTO.getOrgId(),
					subContractGrnDTO.getFinYear(), subContractGrnDTO.getBranchCode(), screenCode);
			subContractGrnVO.setDocId(docId);
			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(subContractGrnDTO.getOrgId(),
							subContractGrnDTO.getFinYear(), subContractGrnDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			createUpdateSubContractGrnVOBySubContractGrnDTO(subContractGrnDTO, subContractGrnVO);
			subContractGrnVO.setCreatedBy(subContractGrnDTO.getCreatedBy());
			subContractGrnVO.setUpdatedBy(subContractGrnDTO.getCreatedBy());
			message = "GRN Created Successfully";
		}

		subContractGrnRepo.save(subContractGrnVO);
		Map<String, Object> response = new HashMap<>();
		response.put("subContractGrnVO", subContractGrnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSubContractGrnVOBySubContractGrnDTO(@Valid SubContractGrnDTO subContractGrnDTO,
			SubContractGrnVO subContractGrnVO) throws ApplicationException {
		subContractGrnVO.setDcNo(subContractGrnDTO.getDcNo());
		subContractGrnVO.setGstNo(subContractGrnDTO.getGstNo());
		subContractGrnVO.setRouteCardNo(subContractGrnDTO.getRouteCardNo());
		subContractGrnVO.setPoNo(subContractGrnDTO.getPoNo());
		subContractGrnVO.setOrgId(subContractGrnDTO.getOrgId());
		subContractGrnVO.setBranch(subContractGrnDTO.getBranch());
		subContractGrnVO.setBranchCode(subContractGrnDTO.getBranchCode());
		subContractGrnVO.setFinYear(subContractGrnDTO.getFinYear());
		subContractGrnVO.setGstType(subContractGrnDTO.getGstType());
		subContractGrnVO.setCurrency(subContractGrnDTO.getCurrency());
		subContractGrnVO.setScIssueNo(subContractGrnDTO.getScIssueNo());
		subContractGrnVO.setSubContractorName(subContractGrnDTO.getSubContractorName());
		subContractGrnVO.setSubContractorAddress(subContractGrnDTO.getSubContractorAddress());
		subContractGrnVO.setSubContractorCode(subContractGrnDTO.getSubContractorCode());
		subContractGrnVO.setJobWorkOutOrderDocId(subContractGrnDTO.getJobWorkOutOrderDocId());

		BigDecimal grossAmount = BigDecimal.ZERO;
		BigDecimal netAmount = BigDecimal.ZERO;
		BigDecimal totalTaxAmount = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(subContractGrnDTO.getId())) {
			List<SubContractGrnDetailsVO> subContractGrnDetailsVO1 = subContractGrnDetailsRepo
					.findBySubContractGrnVO(subContractGrnVO);
			subContractGrnDetailsRepo.deleteAll(subContractGrnDetailsVO1);
		}
		List<SubContractGrnDetailsVO> subContractGrnDetailsVOs = new ArrayList<>();

		for (SubContractGrnDetailsDTO subContractGrnDetailsDTO : subContractGrnDTO.getSubContractGrnDetailsDTO()) {
			SubContractGrnDetailsVO subContractGrnDetailsVO = new SubContractGrnDetailsVO();
			subContractGrnDetailsVO.setItemCode(subContractGrnDetailsDTO.getItemCode());
			subContractGrnDetailsVO.setItemDesc(subContractGrnDetailsDTO.getItemDesc());
			subContractGrnDetailsVO.setTaxType(subContractGrnDetailsDTO.getTaxType());
			subContractGrnDetailsVO.setPrimaryUnit(subContractGrnDetailsDTO.getPrimaryUnit());
			validateQty(subContractGrnDetailsDTO.getAcceptQty(), "Accept Qty");
			subContractGrnDetailsVO.setAcceptQty(subContractGrnDetailsDTO.getAcceptQty());

			validateQty(subContractGrnDetailsDTO.getPoRate(), "PO Rate");
			subContractGrnDetailsVO.setPoRate(subContractGrnDetailsDTO.getPoRate());

			validateQty(subContractGrnDetailsDTO.getRecievedQty(), "Received Qty");
			subContractGrnDetailsVO.setRecievedQty(subContractGrnDetailsDTO.getRecievedQty());

			validateQty(subContractGrnDetailsDTO.getRejectQty(), "Reject Qty");
			subContractGrnDetailsVO.setRejectQty(subContractGrnDetailsDTO.getRejectQty());
			subContractGrnDetailsVO.setIgst(subContractGrnDetailsDTO.getIgst());
			subContractGrnDetailsVO.setSgst(subContractGrnDetailsDTO.getSgst());
			subContractGrnDetailsVO.setCgst(subContractGrnDetailsDTO.getCgst());
			subContractGrnDetailsVO.setQty(subContractGrnDetailsDTO.getQty());
			BigDecimal amountSet = subContractGrnDetailsDTO.getPoRate().multiply(subContractGrnDetailsDTO.getQty());
			subContractGrnDetailsVO.setAmount(amountSet);
			grossAmount = grossAmount.add(subContractGrnDetailsVO.getAmount());
			subContractGrnDetailsVO.setPendingQty(
					subContractGrnDetailsDTO.getAcceptQty().subtract(subContractGrnDetailsDTO.getRecievedQty()));

			BigDecimal sgstAmount = subContractGrnDetailsDTO.getSgst().multiply(subContractGrnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal cgstAmount = subContractGrnDetailsDTO.getCgst().multiply(subContractGrnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));
			BigDecimal igstAmount = subContractGrnDetailsDTO.getIgst().multiply(subContractGrnDetailsVO.getAmount())
					.divide(BigDecimal.valueOf(100));

			BigDecimal taxAmount = sgstAmount.add(cgstAmount).add(igstAmount);
			subContractGrnDetailsVO.setTaxValue(taxAmount);
			totalTaxAmount = totalTaxAmount.add(subContractGrnDetailsVO.getTaxValue());

			BigDecimal landedValues = subContractGrnDetailsVO.getAmount().add(subContractGrnDetailsVO.getTaxValue());
			subContractGrnDetailsVO.setLandedValue(landedValues);
			netAmount = netAmount.add(subContractGrnDetailsVO.getLandedValue());

			subContractGrnDetailsVO.setSubContractGrnVO(subContractGrnVO);
			subContractGrnDetailsVOs.add(subContractGrnDetailsVO);
		}

		subContractGrnVO.setGrossAmount(grossAmount);
		subContractGrnVO.setNetAmount(netAmount);
		subContractGrnVO.setTotalAmountTax(totalTaxAmount);
		subContractGrnVO.setSubContractGrnDetailsVO(subContractGrnDetailsVOs);
	}

	private void validateQty(BigDecimal qty, String fieldName) throws ApplicationException {
		if (qty == null || qty.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ApplicationException(fieldName + " must be greater than zero.");
		}
	}

	@Override
	public String getSubContractGrnDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "SGRN";
		String result = subContractGrnRepo.getSubContractGrnDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutOrderFromSubContractDetails(Long orgId, String branchCode,
			String jobWorkOutOrderNumber) {
		Set<Object[]> chType = subContractGrnRepo.getJobWorkOutOrderFromSubContractDetails(orgId, branchCode,
				jobWorkOutOrderNumber);
		return getJobWorkOutOrderFromSubContractDetails(chType);
	}

	private List<Map<String, Object>> getJobWorkOutOrderFromSubContractDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("jobWorkOrderNo", ch[0] != null ? ch[0].toString() : "");
			map.put("subContractName", ch[1] != null ? ch[1].toString() : "");
			map.put("subContractCode", ch[2] != null ? ch[2].toString() : "");
			map.put("dcNo", ch[3] != null ? ch[3].toString() : "");
			map.put("dispatchThrough", ch[4] != null ? ch[4].toString() : "");
			map.put("poNo", ch[5] != null ? ch[5].toString() : "");
			map.put("routecardNo", ch[6] != null ? ch[6].toString() : "");
			map.put("taxType", ch[7] != null ? ch[7].toString() : "");
			map.put("scIssueNo", ch[8] != null ? ch[8].toString() : "");
			map.put("gstNo", ch[9] != null ? ch[9].toString() : "");
			map.put("subContractorAddress", ch[10] != null ? ch[10].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutOrderFromSubContractItemDetails(Long orgId, String branchCode,
			String jobWorkOutOrderNumber) {
		Set<Object[]> chType = subContractGrnRepo.getJobWorkOutOrderFromSubContractItemDetails(orgId, branchCode,
				jobWorkOutOrderNumber);
		return getJobWorkOutOrderFromSubContractItemDetails(chType);
	}

	private List<Map<String, Object>> getJobWorkOutOrderFromSubContractItemDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("process", ch[2] != null ? ch[2].toString() : "");
			map.put("qty", ch[3] != null ? ch[3].toString() : "");
			map.put("unit", ch[4] != null ? ch[4].toString() : "");
			map.put("rate", ch[5] != null ? ch[5].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getJobWorkOutOrderDocId(Long orgId) {
		Set<Object[]> chType = subContractGrnRepo.getJobWorkOutOrderDocId(orgId);
		return getJobWorkOutOrderDocId(chType);
	}

	private List<Map<String, Object>> getJobWorkOutOrderDocId(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("docId", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getThirdPartyNamesFromPartyMaster(Long orgId) {
		Set<Object[]> chType = thirdPartyInspectionRepo.getThirdPartyNamesFromPartyMaster(orgId);
		return getThirdPartyNamesFromPartyMaster(chType);
	}

	private List<Map<String, Object>> getThirdPartyNamesFromPartyMaster(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partyName", ch[0] != null ? ch[0].toString() : ""); // 0

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractGrnDetails(Long orgId, String subContractName, String fromDate,
			String toDate, String branchCode) {
		Set<Object[]> chType = subContractGrnRepo.getSubContractGrnDetails(orgId, subContractName, fromDate, toDate,
				branchCode);
		return getSubContractGrnDetails(chType);
	}

	private List<Map<String, Object>> getSubContractGrnDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("dcNo", ch[0] != null ? ch[0].toString() : "");
			map.put("docId", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			map.put("poNo", ch[3] != null ? ch[3].toString() : "");
			map.put("gstNo", ch[4] != null ? ch[4].toString() : "");
			map.put("gstType", ch[5] != null ? ch[5].toString() : "");
			map.put("jobWorkOutOrderDocId", ch[6] != null ? ch[6].toString() : "");
			map.put("currency", ch[7] != null ? ch[7].toString() : "");
			map.put("routeCardNo", ch[8] != null ? ch[8].toString() : "");
			map.put("subContractorAddress", ch[9] != null ? ch[9].toString() : "");
			map.put("subContractorCode", ch[10] != null ? ch[10].toString() : "");
			map.put("subContractorName", ch[11] != null ? ch[11].toString() : "");
			map.put("itemCode", ch[12] != null ? ch[12].toString() : "");
			map.put("itemDesc", ch[13] != null ? ch[13].toString() : "");
			map.put("primaryUnit", ch[14] != null ? ch[14].toString() : "");
			map.put("qty", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO);
			map.put("poRate", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO);
			map.put("taxType", ch[17] != null ? ch[17].toString() : "");
			map.put("taxValue", ch[18] != null ? new BigDecimal(ch[18].toString()) : BigDecimal.ZERO);
			map.put("amount", ch[19] != null ? new BigDecimal(ch[19].toString()) : BigDecimal.ZERO);
			map.put("totalAmount", ch[20] != null ? new BigDecimal(ch[20].toString()) : BigDecimal.ZERO);

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getSubContractGrnSummaryDetails(Long orgId, String subContractName,
			String fromDate, String toDate, String branchCode) {
		Set<Object[]> chType = subContractGrnRepo.getSubContractGrnSummaryDetails(orgId, subContractName, fromDate,
				toDate, branchCode);
		return getSubContractGrnSummaryDetails(chType);
	}

	private List<Map<String, Object>> getSubContractGrnSummaryDetails(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("dcNo", ch[0] != null ? ch[0].toString() : ""); // 0
			map.put("docId", ch[1] != null ? ch[1].toString() : ""); // 1
			map.put("docDate", ch[2] != null ? ch[2].toString() : ""); // 2
			map.put("gstNo", ch[3] != null ? ch[3].toString() : ""); // 3
			map.put("gstType", ch[4] != null ? ch[4].toString() : ""); // 4
			map.put("jobWorkOutOrderDocId", ch[5] != null ? ch[5].toString() : ""); // 5
			map.put("poNo", ch[6] != null ? ch[6].toString() : ""); // 6
			map.put("routeCardNo", ch[7] != null ? ch[7].toString() : ""); // 7
			map.put("subContractorAddress", ch[8] != null ? ch[8].toString() : ""); // 8
			map.put("subContractorCode", ch[9] != null ? ch[9].toString() : ""); // 9
			map.put("subContractorName", ch[10] != null ? ch[10].toString() : ""); // 10

			map.put("grossAmount", ch[11] != null ? new BigDecimal(ch[11].toString()) : BigDecimal.ZERO); // 11
			map.put("totalTaxAmount", ch[12] != null ? new BigDecimal(ch[12].toString()) : BigDecimal.ZERO); // 12
			map.put("netAmount", ch[13] != null ? new BigDecimal(ch[13].toString()) : BigDecimal.ZERO); // 13
			map.put("igst", ch[14] != null ? new BigDecimal(ch[14].toString()) : BigDecimal.ZERO); // 14
			map.put("cgst", ch[15] != null ? new BigDecimal(ch[15].toString()) : BigDecimal.ZERO); // 15
			map.put("sgst", ch[16] != null ? new BigDecimal(ch[16].toString()) : BigDecimal.ZERO); // 16

			List1.add(map);
		}
		return List1;
	}

	@Value("${file.upload.path}")
	private String uploadBasePath;

	@Override
	@Transactional
	public Map<String, Object> createUpdateThirdPartyImages(MultipartFile[] files, String docId, String screenName,
			String module, List<String> itemIds) throws ApplicationException, IOException {

		ThirdPartyInspectionVO inspection = thirdPartyInspectionRepo.findByDocId(docId);

		inspection = thirdPartyInspectionRepo.save(inspection);

		// Create folder
		Path docFolder = Paths.get(uploadBasePath, module, screenName, docId);
		createDirectory(docFolder);

		// Delete old DB attachments
		List<ThirdPartyAttachmentsVO> oldDocs = thirdPartyAttachmentsRepo.findByThirdPartyInspectionVO(inspection);

		thirdPartyAttachmentsRepo.deleteAll(oldDocs);

		if (inspection.getDocuments() != null) {
			inspection.getDocuments().clear();
		} else {
			inspection.setDocuments(new ArrayList<>());
		}

		// Delete old physical files
		for (ThirdPartyAttachmentsVO doc : oldDocs) {
			deleteFileSafely(doc.getFilePath());
		}

		// Save new files
		replaceDocuments(inspection, files, docFolder, docId, itemIds);

		Map<String, Object> response = new HashMap<>();
		response.put("thirdPartyInspectionVO", inspection);

		return response;
	}

	private void replaceDocuments(ThirdPartyInspectionVO inspection, MultipartFile[] files, Path docFolder,
			String docId, List<String> itemIds) throws IOException {

		if (files == null || files.length == 0) {
			return;
		}

		saveFiles(inspection, files, docFolder, docId, itemIds);
	}

	private void saveFiles(ThirdPartyInspectionVO inspection, MultipartFile[] files, Path docFolder, String docId,
			List<String> itemIds) throws IOException {

		try {

			createDirectory(docFolder);

			for (int i = 0; i < files.length; i++) {

				MultipartFile file = files[i];

				String itemId = null;
				if (itemIds != null && itemIds.size() > i) {
					itemId = itemIds.get(i);
				}

				String originalName = file.getOriginalFilename();
				if (originalName == null) {
					originalName = "file";
				}

				String extension = "";
				if (originalName.contains(".")) {
					extension = originalName.substring(originalName.lastIndexOf("."));
					originalName = originalName.substring(0, originalName.lastIndexOf("."));
				}

				String fileName = originalName + "_" + docId + extension;

				Path filePath = docFolder.resolve(fileName);

				try (InputStream is = file.getInputStream()) {
					Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
				}

				String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/api/grn/viewFileThirdPartyImages/").toUriString();

				String relativePath = uploadBasePath.replace("\\", "/");

				relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

				ThirdPartyAttachmentsVO attach = new ThirdPartyAttachmentsVO();
				attach.setThirdPartyInspectionVO(inspection);
				attach.setFilename(fileName);
				attach.setFilePath(baseUrl + relativePath);
				attach.setFileype(file.getContentType());
				attach.setFilesize(file.getSize());
				attach.setItemId(itemId);
				attach.setUploadOn(LocalDateTime.now());

				inspection.getDocuments().add(attach);
			}

		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	private void deleteFileSafely(String path) {
		try {
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				Files.delete(filePath);
			}
		} catch (Exception e) {
			System.err.println("Unable to delete file: " + path);
		}
	}

	private void createDirectory(Path path) throws IOException {
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewFileThirdPartyImages(HttpServletRequest request) throws IOException {
		return serveFile(request, "/api/grn/viewFileThirdPartyImages/", uploadBasePath);
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
	public List<ImageResponseDTO> getThirdPartyReportDetailsImages(Long id) throws Exception {

		ThirdPartyInspectionVO record = thirdPartyInspectionRepo
				.getAllThirdPartyInspectionById(id);

		if (record == null) {
			throw new RuntimeException("Record not found");
		}

		List<ThirdPartyAttachmentsVO> docs = record.getDocuments();

		if (docs == null || docs.isEmpty()) {
			throw new RuntimeException("No attachments found");
		}
		List<ImageResponseDTO> responseList = new ArrayList<>();

		for (ThirdPartyAttachmentsVO attachment : docs) {

			String fileUrl = attachment.getFilePath().replace(" ", "%20");

			InputStream inputStream = new URL(fileUrl).openStream();

			byte[] bytes = inputStream.readAllBytes();

			String base64 = Base64.getEncoder().encodeToString(bytes);

			ImageResponseDTO dto = new ImageResponseDTO();
			dto.setFileName(attachment.getFilename());
			dto.setProfileImage(base64); // only base64 (like you asked)

			responseList.add(dto);
		}

		return responseList;
	}

}
