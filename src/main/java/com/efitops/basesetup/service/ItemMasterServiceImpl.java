package com.efitops.basesetup.service;

import java.io.IOException;
import java.io.InputStream;
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

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ItemDrawingDTO;
import com.efitops.basesetup.dto.ItemInventoryDTO;
import com.efitops.basesetup.dto.ItemMasterDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDTO;
import com.efitops.basesetup.dto.ItemOthersDTO;
import com.efitops.basesetup.dto.ItemPurchaseDTO;
import com.efitops.basesetup.dto.ItemSalesDTO;
import com.efitops.basesetup.dto.ItemUnitsDTO;
import com.efitops.basesetup.dto.ListOfImageResponseDTO;
import com.efitops.basesetup.dto.ListOfImageResponseDetailsDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.ItemDrawingVO;
import com.efitops.basesetup.entity.ItemInventoryVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ItemOthersVO;
import com.efitops.basesetup.entity.ItemPurchaseVO;
import com.efitops.basesetup.entity.ItemSalesVO;
import com.efitops.basesetup.entity.ItemUnitsVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.ItemDrawingRepo;
import com.efitops.basesetup.repository.ItemInventoryRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ItemOthersRepo;
import com.efitops.basesetup.repository.ItemPurchaseRepo;
import com.efitops.basesetup.repository.ItemSalesRepo;
import com.efitops.basesetup.repository.ItemUnitsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class ItemMasterServiceImpl implements ItemMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ItemMasterServiceImpl.class);

	@Autowired
	ItemMasterRepo itemMasterRepo;

	@Autowired
	ItemInventoryRepo itemInventoryRepo;

	@Autowired
	ItemUnitsRepo itemUnitsRepo;

	@Autowired
	ItemPurchaseRepo itemPurchaseRepo;

	@Autowired
	ItemSalesRepo itemSalesRepo;

	@Autowired
	ItemOthersRepo itemOthersRepo;

	@Autowired
	ItemDrawingRepo itemDrawingRepo;

	@Value("${file.upload-dirs}")
	private String uploadBasePath;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	ListOfValuesRepo listOfValuesRepo;

	@Autowired
	UnitMasterRepo unitMasterRepo;

	@Override
	public ItemMasterVO getItemMasterById(Long id) {

		return itemMasterRepo.getItemMasterById(id);
	}

	@Override
	public Map<String, Object> updateCreateItemMaster(ItemMasterDTO itemMasterDTO) throws ApplicationException {

		ItemMasterVO itemMasterVO = new ItemMasterVO();
		String message;
		if (ObjectUtils.isNotEmpty(itemMasterDTO.getId())) {

			itemMasterVO = itemMasterRepo.findById(itemMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Item not found"));

			if (!itemMasterVO.getItemCode().equals(itemMasterDTO.getItemCode())) {
				if (itemMasterRepo.existsByItemCodeAndOrgId(itemMasterDTO.getItemCode(), itemMasterDTO.getOrgId())) {

					String errorMessage = String.format("This ItemCode: %s already exists for this organization.",
							itemMasterDTO.getItemCode());
					throw new ApplicationException(errorMessage);
				}
				itemMasterVO.setItemCode(itemMasterDTO.getItemCode());
			}

			itemMasterVO.setUpdatedBy(itemMasterDTO.getCreatedBy());
			createUpdateItemMasterVOByItemMasterDTO(itemMasterDTO, itemMasterVO);
			message = "Item Updated Successfully";
		} else {

			if (itemMasterRepo.existsByItemCodeAndOrgId(itemMasterDTO.getItemCode(), itemMasterDTO.getOrgId())) {

				String errorMessage = String.format("This ItemCode: %s already exists for this organization.",
						itemMasterDTO.getItemCode());
				throw new ApplicationException(errorMessage);
			}

			createUpdateItemMasterVOByItemMasterDTO(itemMasterDTO, itemMasterVO);
			itemMasterVO.setCreatedBy(itemMasterDTO.getCreatedBy());
			itemMasterVO.setUpdatedBy(itemMasterDTO.getCreatedBy());
			message = "Item Created Successfully";
		}

		ItemMasterVO savedItemMaster = itemMasterRepo.save(itemMasterVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("itemMasterVO", buildItemMasterResponse(savedItemMaster));

		return response;
	}

	private ItemMasterResponseDTO buildItemMasterResponse(ItemMasterVO itemMasterVO) {
		ItemMasterResponseDTO responseDTO = new ItemMasterResponseDTO();

		responseDTO.setId(itemMasterVO.getId());
		responseDTO.setCapital(itemMasterVO.getCapital());
		responseDTO.setItemType(itemMasterVO.getItemType());
		responseDTO.setGrade(itemMasterVO.getGrade());

		if (itemMasterVO.getItemGroup() != null) {
			ListOfImageResponseDTO listOfValuesDTO = new ListOfImageResponseDTO();
			listOfValuesDTO.setId(itemMasterVO.getItemGroup().getId());
			listOfValuesDTO.setListCode(itemMasterVO.getItemGroup().getListCode());
			listOfValuesDTO.setListDescription(itemMasterVO.getItemGroup().getListDescription());

			List<ListOfImageResponseDetailsDTO> detailsList = new ArrayList<>();
			if (itemMasterVO.getItemGroup().getListOfValuesDetailsVO() != null) {
				for (ListOfValuesDetailsVO detailVO : itemMasterVO.getItemGroup().getListOfValuesDetailsVO()) {
					ListOfImageResponseDetailsDTO detailDTO = new ListOfImageResponseDetailsDTO();
					detailDTO.setValueCode(detailVO.getValueCode());
					detailDTO.setValueDescription(detailVO.getValueDescription());
					detailsList.add(detailDTO);
				}
			}
			listOfValuesDTO.setListOfImageResponseDetailsDTO(detailsList);

			responseDTO.setListOfValues(listOfValuesDTO);
			responseDTO.setListOfValuesId(itemMasterVO.getItemGroup().getId());
		}

		responseDTO.setItemCode(itemMasterVO.getItemCode());
		responseDTO.setExciseTariffNo(itemMasterVO.getExciseTariffNo());
		responseDTO.setItemDescription(itemMasterVO.getItemDescription());
		responseDTO.setThickness(itemMasterVO.getThickness());
		responseDTO.setStock(itemMasterVO.getStock());
		responseDTO.setWidth(itemMasterVO.getWidth());
		responseDTO.setProtoType(itemMasterVO.getProtoType());
		responseDTO.setLenth(itemMasterVO.getLenth());
		responseDTO.setPsw(itemMasterVO.getPsw());
		responseDTO.setWeight(itemMasterVO.getWeight());
		responseDTO.setNeedQcApproval(itemMasterVO.getNeedQcApproval());
//		responseDTO.setPrimaryUnit(itemMasterVO.getPrimaryUnit());

		if (itemMasterVO.getPrimaryUnit() != null) {
			PrimaryUnitImageDTO primaryUnitDTO = new PrimaryUnitImageDTO();
			primaryUnitDTO.setId(itemMasterVO.getPrimaryUnit().getId());
			primaryUnitDTO.setPrimaryUnit(itemMasterVO.getPrimaryUnit().getUnitId());
			responseDTO.setPrimaryUnits(primaryUnitDTO);
		}
		responseDTO.setInspection(itemMasterVO.getInspection());
		responseDTO.setAbcGrade(itemMasterVO.getAbcGrade());
		responseDTO.setDrawingNo(itemMasterVO.getDrawingNo());
		responseDTO.setExcisbleItem(itemMasterVO.getExcisbleItem());
		responseDTO.setLotSize(itemMasterVO.getLotSize());
		responseDTO.setShelfLifePart(itemMasterVO.getShelfLifePart());
		responseDTO.setImportLocal(itemMasterVO.getImportLocal());
		responseDTO.setSafetyStock(itemMasterVO.getSafetyStock());
		responseDTO.setGrnRequired(itemMasterVO.getGrnRequired());
		responseDTO.setRowmaterials(itemMasterVO.getRowmaterials());
		responseDTO.setHsnSacCode(itemMasterVO.getHsnSacCode());
		responseDTO.setCreatedBy(itemMasterVO.getCreatedBy());
		responseDTO.setOrgId(itemMasterVO.getOrgId());
		responseDTO.setUpdatedBy(itemMasterVO.getUpdatedBy());
		responseDTO.setCancelRemarks(itemMasterVO.getCancelRemarks());

		List<ItemUnitsDTO> unitsResponseList = new ArrayList<>();
		if (itemMasterVO.getItemUnitsVO() != null) {
			for (ItemUnitsVO unitsVO : itemMasterVO.getItemUnitsVO()) {
				ItemUnitsDTO unitsDTO = new ItemUnitsDTO();
				unitsDTO.setId(unitsVO.getId());
				unitsDTO.setPurchaseUnit(unitsVO.getPurchaseUnit());
				unitsDTO.setSellingUnit(unitsVO.getSellingUnit());
				unitsDTO.setPricingUnit(unitsVO.getPricingUnit());
				unitsDTO.setSecondaryUnit(unitsVO.getSecondaryUnit());
				unitsResponseList.add(unitsDTO);
			}
		}
		responseDTO.setItemUnitsDTO(unitsResponseList);

		List<ItemInventoryDTO> inventoryResponseList = new ArrayList<>();
		if (itemMasterVO.getItemInventoryVO() != null) {
			for (ItemInventoryVO inventoryVO : itemMasterVO.getItemInventoryVO()) {
				ItemInventoryDTO inventoryDTO = new ItemInventoryDTO();
				inventoryDTO.setId(inventoryVO.getId());
				inventoryDTO.setManufacured(inventoryVO.getManufacured());
				inventoryDTO.setDefaultLocation(inventoryVO.getDefaultLocation());
				inventoryDTO.setAlternateLocation(inventoryVO.getAlternateLocation());
				inventoryDTO.setLeadTime(inventoryVO.getLeadTime());
				inventoryDTO.setReorderLevel(inventoryVO.getReorderLevel());
				inventoryDTO.setRackNo(inventoryVO.getRackNo());
				inventoryDTO.setRowNo(inventoryVO.getRowNo());
				inventoryDTO.setPosition(inventoryVO.getPosition());
				inventoryDTO.setMinimumOrderQty(inventoryVO.getMinimumOrderQty());
				inventoryDTO.setMaximumOrderQty(inventoryVO.getMaximumOrderQty());
				inventoryDTO.setBinSize(inventoryVO.getBinSize());
				inventoryDTO.setBinQty(inventoryVO.getBinQty());
				inventoryResponseList.add(inventoryDTO);
			}
		}
		responseDTO.setItemInventoryDTO(inventoryResponseList);

		List<ItemPurchaseDTO> purchaseResponseList = new ArrayList<>();
		if (itemMasterVO.getItemPurchaseVO() != null) {
			for (ItemPurchaseVO purchaseVO : itemMasterVO.getItemPurchaseVO()) {
				ItemPurchaseDTO purchaseDTO = new ItemPurchaseDTO();
				purchaseDTO.setId(purchaseVO.getId());
				purchaseDTO.setDefaultSupplier(purchaseVO.getDefaultSupplier());
				purchaseDTO.setAlternateSupplier(purchaseVO.getAlternateSupplier());
				purchaseDTO.setLeadTime(purchaseVO.getLeadTime());
				purchaseDTO.setPurchaseTolerance(purchaseVO.getPurchaseTolerance());
				purchaseDTO.setRate(purchaseVO.getRate());
				purchaseDTO.setDate(purchaseVO.getDate());
				purchaseDTO.setLandedCostRate(purchaseVO.getLandedCostRate());
				purchaseDTO.setToolOwner(purchaseVO.getToolOwner());
				purchaseDTO.setToolNo(purchaseVO.getToolNo());

				if (purchaseVO.getBranch() != null) {
					BranchResponseDTO branchDTO = new BranchResponseDTO();
					branchDTO.setId(purchaseVO.getBranch().getId());
					branchDTO.setBranchCode(purchaseVO.getBranch().getBranchCode());
					branchDTO.setBranchName(purchaseVO.getBranch().getBranchName());
					purchaseDTO.setBranch(branchDTO);

					purchaseDTO.setBranchId(purchaseVO.getBranch().getId());
				}

				purchaseResponseList.add(purchaseDTO);
			}
		}
		responseDTO.setItemPurchaseDTO(purchaseResponseList);

		List<ItemSalesDTO> salesResponseList = new ArrayList<>();
		if (itemMasterVO.getItemSalesVO() != null) {
			for (ItemSalesVO salesVO : itemMasterVO.getItemSalesVO()) {
				ItemSalesDTO salesDTO = new ItemSalesDTO();
				salesDTO.setId(salesVO.getId());
				salesDTO.setItemBlocked(salesVO.getItemBlocked());
				salesDTO.setMinimumSellingPrice(salesVO.getMinimumSellingPrice());
				salesDTO.setSalesAccount(salesVO.getSalesAccount());
				salesDTO.setLeadTimeToDespatch(salesVO.getLeadTimeToDespatch());
				salesDTO.setCustomerPartNo(salesVO.getCustomerPartNo());
				salesResponseList.add(salesDTO);
			}
		}
		responseDTO.setItemSalesDTO(salesResponseList);

		List<ItemOthersDTO> othersResponseList = new ArrayList<>();
		if (itemMasterVO.getItemOthersVO() != null) {
			for (ItemOthersVO othersVO : itemMasterVO.getItemOthersVO()) {
				ItemOthersDTO othersDTO = new ItemOthersDTO();
				othersDTO.setId(othersVO.getId());
				othersDTO.setTechnicalSpecification(othersVO.getTechnicalSpecification());
				othersDTO.setSupplierPartNo(othersVO.getSupplierPartNo());
				othersResponseList.add(othersDTO);
			}
		}
		responseDTO.setItemOthersDTO(othersResponseList);

		List<ItemDrawingDTO> drawingResponseList = new ArrayList<>();
		if (itemMasterVO.getItemDrawingVO() != null) {
			for (ItemDrawingVO drawingVO : itemMasterVO.getItemDrawingVO()) {
				ItemDrawingDTO drawingDTO = new ItemDrawingDTO();
				drawingDTO.setId(drawingVO.getId());
				drawingDTO.setName(drawingVO.getName());
				drawingResponseList.add(drawingDTO);
			}
		}
		responseDTO.setItemDrawingDTO(drawingResponseList);

		return responseDTO;
	}

	private void createUpdateItemMasterVOByItemMasterDTO(ItemMasterDTO itemMasterDTO, ItemMasterVO itemMasterVO)
			throws ApplicationException {

		itemMasterVO.setOrgId(itemMasterDTO.getOrgId());
		itemMasterVO.setCreatedBy(itemMasterDTO.getCreatedBy());

		if (itemMasterDTO.getListOfValuesId() != null && itemMasterDTO.getListOfValuesId() != 0) {

			ListOfValuesVO listofvalues = listOfValuesRepo.findById(itemMasterDTO.getListOfValuesId())
					.orElseThrow(() -> new ApplicationException("ListOfValues Is Not Found"));

			itemMasterVO.setItemGroup(listofvalues);

		}
		itemMasterVO.setCapital(itemMasterDTO.getCapital());
		itemMasterVO.setItemType(itemMasterDTO.getItemType());
		itemMasterVO.setGrade(itemMasterDTO.getGrade());
		itemMasterVO.setItemCode(itemMasterDTO.getItemCode());
		itemMasterVO.setExciseTariffNo(itemMasterDTO.getExciseTariffNo());
		itemMasterVO.setItemDescription(itemMasterDTO.getItemDescription());

		itemMasterVO.setThickness(itemMasterDTO.getThickness());
		itemMasterVO.setStock(itemMasterDTO.getStock());
		itemMasterVO.setWidth(itemMasterDTO.getWidth());
		itemMasterVO.setProtoType(itemMasterDTO.getProtoType());
		itemMasterVO.setLenth(itemMasterDTO.getLenth());
		itemMasterVO.setPsw(itemMasterDTO.getPsw());
		itemMasterVO.setWeight(itemMasterDTO.getWeight());
		itemMasterVO.setNeedQcApproval(itemMasterDTO.getNeedQcApproval());

		if (itemMasterDTO.getPrimaryUnitId() != null && itemMasterDTO.getPrimaryUnitId() != 0) {

			UnitMasterVO branch = unitMasterRepo.findById(itemMasterDTO.getPrimaryUnitId())
					.orElseThrow(() -> new ApplicationException("UnitMaster Not Found"));

			itemMasterVO.setPrimaryUnit(branch);
		}
		itemMasterVO.setInspection(itemMasterDTO.getInspection());
		itemMasterVO.setAbcGrade(itemMasterDTO.getAbcGrade());
		itemMasterVO.setDrawingNo(itemMasterDTO.getDrawingNo());
		itemMasterVO.setExcisbleItem(itemMasterDTO.getExcisbleItem());
		itemMasterVO.setLotSize(itemMasterDTO.getLotSize());
		itemMasterVO.setShelfLifePart(itemMasterDTO.getShelfLifePart());
		itemMasterVO.setImportLocal(itemMasterDTO.getImportLocal());
		itemMasterVO.setSafetyStock(itemMasterDTO.getSafetyStock());
		itemMasterVO.setGrnRequired(itemMasterDTO.getGrnRequired());
		itemMasterVO.setRowmaterials(itemMasterDTO.getRowmaterials());
		itemMasterVO.setHsnSacCode(itemMasterDTO.getHsnSacCode());

		itemMasterVO.setCreatedBy(itemMasterDTO.getCreatedBy());
		itemMasterVO.setOrgId(itemMasterDTO.getOrgId());
		itemMasterVO.setUpdatedBy(itemMasterDTO.getUpdatedBy());
		itemMasterVO.setCancelRemarks(itemMasterDTO.getCancelRemarks());

		// Delete existing child records if updating
		if (ObjectUtils.isNotEmpty(itemMasterVO.getId())) {

			List<ItemUnitsVO> itemUnits = itemUnitsRepo.findByItemMasterVO(itemMasterVO);
			itemUnitsRepo.deleteAll(itemUnits);

			List<ItemInventoryVO> itemInventory = itemInventoryRepo.findByItemMasterVO(itemMasterVO);
			itemInventoryRepo.deleteAll(itemInventory);

			List<ItemPurchaseVO> itemPurchase = itemPurchaseRepo.findByItemMasterVO(itemMasterVO);
			itemPurchaseRepo.deleteAll(itemPurchase);

			List<ItemSalesVO> itemSales = itemSalesRepo.findByItemMasterVO(itemMasterVO);
			itemSalesRepo.deleteAll(itemSales);

			List<ItemOthersVO> itemOthers = itemOthersRepo.findByItemMasterVO(itemMasterVO);
			itemOthersRepo.deleteAll(itemOthers);

			List<ItemDrawingVO> itemDrawing = itemDrawingRepo.findByItemMasterVO(itemMasterVO);
			itemDrawingRepo.deleteAll(itemDrawing);
		}

		List<ItemUnitsVO> itemUnitsVOs = new ArrayList<>();
		if (itemMasterDTO.getItemUnitsDTO() != null) {
			for (ItemUnitsDTO dto : itemMasterDTO.getItemUnitsDTO()) {

				ItemUnitsVO vo = new ItemUnitsVO();

				vo.setPurchaseUnit(dto.getPurchaseUnit());
				vo.setSellingUnit(dto.getSellingUnit());
				vo.setPricingUnit(dto.getPricingUnit());
				vo.setSecondaryUnit(dto.getSecondaryUnit());

				vo.setItemMasterVO(itemMasterVO);

				itemUnitsVOs.add(vo);
			}
		}
		itemMasterVO.setItemUnitsVO(itemUnitsVOs);

		// Set ItemInventory
		List<ItemInventoryVO> itemInventoryVOs = new ArrayList<>();
		if (itemMasterDTO.getItemInventoryDTO() != null) {
			for (ItemInventoryDTO dto : itemMasterDTO.getItemInventoryDTO()) {

				ItemInventoryVO vo = new ItemInventoryVO();

				vo.setManufacured(dto.getManufacured());
				vo.setDefaultLocation(dto.getDefaultLocation());
				vo.setAlternateLocation(dto.getAlternateLocation());
				vo.setLeadTime(dto.getLeadTime());
				vo.setReorderLevel(dto.getReorderLevel());
				vo.setRackNo(dto.getRackNo());
				vo.setRowNo(dto.getRowNo());
				vo.setPosition(dto.getPosition());
				vo.setMinimumOrderQty(dto.getMinimumOrderQty());
				vo.setMaximumOrderQty(dto.getMaximumOrderQty());
				vo.setBinSize(dto.getBinSize());
				vo.setBinQty(dto.getBinQty());

				vo.setItemMasterVO(itemMasterVO);

				itemInventoryVOs.add(vo);
			}
		}
		itemMasterVO.setItemInventoryVO(itemInventoryVOs);

		// Set ItemPurchase - Store branch reference
		List<ItemPurchaseVO> itemPurchaseVOs = new ArrayList<>();
		if (itemMasterDTO.getItemPurchaseDTO() != null) {
			for (ItemPurchaseDTO dto : itemMasterDTO.getItemPurchaseDTO()) {

				ItemPurchaseVO vo = new ItemPurchaseVO();

				vo.setDefaultSupplier(dto.getDefaultSupplier());
				vo.setAlternateSupplier(dto.getAlternateSupplier());
				vo.setLeadTime(dto.getLeadTime());
				vo.setPurchaseTolerance(dto.getPurchaseTolerance());
				vo.setRate(dto.getRate());
				vo.setDate(dto.getDate());
				vo.setLandedCostRate(dto.getLandedCostRate());
				vo.setToolOwner(dto.getToolOwner());
				vo.setToolNo(dto.getToolNo());

				if (dto.getBranchId() != null && dto.getBranchId() != 0) {

					BranchVO branch = branchRepo.findById(dto.getBranchId())
							.orElseThrow(() -> new ApplicationException("Branch Not Found"));

					vo.setBranch(branch);
				}

				vo.setItemMasterVO(itemMasterVO);

				itemPurchaseVOs.add(vo);
			}
		}
		itemMasterVO.setItemPurchaseVO(itemPurchaseVOs);

		// Set ItemSales
		List<ItemSalesVO> itemSalesVOs = new ArrayList<>();
		if (itemMasterDTO.getItemSalesDTO() != null) {
			for (ItemSalesDTO dto : itemMasterDTO.getItemSalesDTO()) {

				ItemSalesVO vo = new ItemSalesVO();

				vo.setItemBlocked(dto.getItemBlocked());
				vo.setMinimumSellingPrice(dto.getMinimumSellingPrice());
				vo.setSalesAccount(dto.getSalesAccount());
				vo.setLeadTimeToDespatch(dto.getLeadTimeToDespatch());
				vo.setCustomerPartNo(dto.getCustomerPartNo());

				vo.setItemMasterVO(itemMasterVO);

				itemSalesVOs.add(vo);
			}
		}
		itemMasterVO.setItemSalesVO(itemSalesVOs);

		// Set ItemOthers
		List<ItemOthersVO> itemOthersVOs = new ArrayList<>();
		if (itemMasterDTO.getItemOthersDTO() != null) {
			for (ItemOthersDTO dto : itemMasterDTO.getItemOthersDTO()) {

				ItemOthersVO vo = new ItemOthersVO();

				vo.setTechnicalSpecification(dto.getTechnicalSpecification());
				vo.setSupplierPartNo(dto.getSupplierPartNo());

				vo.setItemMasterVO(itemMasterVO);

				itemOthersVOs.add(vo);
			}
		}
		itemMasterVO.setItemOthersVO(itemOthersVOs);

		// Set ItemDrawing
		List<ItemDrawingVO> itemDrawingVOs = new ArrayList<>();
		if (itemMasterDTO.getItemDrawingDTO() != null) {
			for (ItemDrawingDTO dto : itemMasterDTO.getItemDrawingDTO()) {

				ItemDrawingVO vo = new ItemDrawingVO();

				vo.setName(dto.getName());

				vo.setItemMasterVO(itemMasterVO);

				itemDrawingVOs.add(vo);
			}
		}
		itemMasterVO.setItemDrawingVO(itemDrawingVOs);

	}

	@Override
	@Transactional
	public Map<String, Object> uploadImageItemMasterDetails(List<MultipartFile> files, Long itemMasterId,
			List<Long> itemDrawingId) throws ApplicationException, IOException {

		if (files == null || files.isEmpty()) {

			throw new RuntimeException("Files are required");
		}

		if (files.size() != itemDrawingId.size()) {

			throw new IllegalArgumentException("Mismatch between files and detail IDs.");
		}

		ItemMasterVO investmentDeclarationVO = itemMasterRepo.findById(itemMasterId)
				.orElseThrow(() -> new RuntimeException("Item not found"));

		// BASE FOLDER
		Path declarationFolder = Paths.get(uploadBasePath, "investmentfiles", itemMasterId.toString());

		createDirectoryInvestment(declarationFolder);

		for (int i = 0; i < files.size(); i++) {

			MultipartFile file = files.get(i);

			Long detailId = itemDrawingId.get(i);

			ItemDrawingVO detail = itemDrawingRepo.findById(detailId)
					.orElseThrow(() -> new RuntimeException("Details not found ID : " + detailId));

			// VALIDATION
			if (!detail.getItemMasterVO().getId().equals(investmentDeclarationVO.getId())) {

				throw new IllegalArgumentException("Detail ID " + detailId + " does not belong to declaration.");
			}

			// DELETE OLD FILE
			if (detail.getFilePath() != null && !detail.getFilePath().isEmpty()) {

				deleteFileSafelyInvestment(detail.getFilePath());
			}

			// ORIGINAL FILE NAME
			String originalName = file.getOriginalFilename();

			if (originalName == null) {
				originalName = "file";
			}

			// REMOVE SPACES
			originalName = originalName.replaceAll("\\s+", "_");

			// EXTENSION
			String extension = "";

			if (originalName.contains(".")) {

				extension = originalName.substring(originalName.lastIndexOf("."));

				originalName = originalName.substring(0, originalName.lastIndexOf("."));
			}

			// NEW FILE NAME
			String fileName = originalName + "_" + detailId + extension;

			// FINAL FILE PATH
			Path filePath = declarationFolder.resolve(fileName);

			// SAVE FILE
			try (InputStream inputStream = file.getInputStream()) {

				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}

			// BASE URL
			String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/itemMaster/viewFile/")
					.toUriString();

			// RELATIVE PATH
			String relativePath = uploadBasePath.replace("\\", "/");

			relativePath = filePath.toString().replace("\\", "/").replace(relativePath + "/", "");

			// PUBLIC URL
			String publicUrl = baseUrl + relativePath;

			// SAVE DB
			detail.setFileName(fileName);

			detail.setFilePath(publicUrl);

			detail.setFileSize(file.getSize());

			detail.setContentType(file.getContentType());

			detail.setUploadOn(LocalDateTime.now());

			itemDrawingRepo.save(detail);

			System.out.println("FILE SAVED : " + filePath.toAbsolutePath());

			System.out.println("PUBLIC URL : " + publicUrl);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("investmentDeclarationVO", investmentDeclarationVO);

		return response;
	}

	private void deleteFileSafelyInvestment(String fileUrl) {

		try {

			String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

			String relativePath = fileUrl.replace(baseUrl + "/api/itemMaster/viewFile/", "");

			Path filePath = Paths.get(uploadBasePath, relativePath);

			if (Files.exists(filePath)) {

				Files.delete(filePath);

				System.out.println("Old file deleted : " + filePath);
			}

		} catch (Exception e) {

			System.err.println("Unable to delete file : " + fileUrl);
		}
	}

	private void createDirectoryInvestment(Path path) throws IOException {

		if (!Files.exists(path)) {

			Files.createDirectories(path);
		}
	}

	@Override
	public ResponseEntity<byte[]> viewItemMasterImages(HttpServletRequest request) throws IOException {

		return serveFileInvestment(request, "/api/itemMaster/viewFile/", uploadBasePath);
	}

	private ResponseEntity<byte[]> serveFileInvestment(HttpServletRequest request, String apiPrefix,
			String uploadBasePath) throws IOException {

		String uri = request.getRequestURI();

		// REMOVE API PREFIX
		String relativePath = uri.replace(apiPrefix, "");

		// URL DECODE
		relativePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);

		// REMOVE uploads/
		if (relativePath.startsWith("uploads/")) {

			relativePath = relativePath.substring("uploads/".length());
		}

		Path baseDir = Paths.get(uploadBasePath).toAbsolutePath().normalize();

		Path filePath = baseDir.resolve(relativePath).normalize();

		// SECURITY CHECK
		if (!filePath.startsWith(baseDir)) {

			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		// FILE EXISTS
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
}
