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
import com.efitops.basesetup.dto.HsnResponseImageDTO;
import com.efitops.basesetup.dto.ItemDrawingDTO;
import com.efitops.basesetup.dto.ItemMasterDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDTO;
import com.efitops.basesetup.dto.ListOfImageResponseDTO;
import com.efitops.basesetup.dto.ListOfImageResponseDetailsDTO;
import com.efitops.basesetup.dto.LocationImageDTO;
import com.efitops.basesetup.dto.PartyResponseDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ItemDrawingVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.ItemDrawingRepo;
import com.efitops.basesetup.repository.ItemInventoryRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ItemOthersRepo;
import com.efitops.basesetup.repository.ItemPurchaseRepo;
import com.efitops.basesetup.repository.ItemSalesRepo;
import com.efitops.basesetup.repository.ItemUnitsRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.LocationRepo;
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

	@Autowired
	HsnRepo hsnRepo;

	@Autowired
	LocationRepo locationRepo;

	@Autowired
	CustomerRepo customerRepo;

//		@Override
//		public ItemMasterVO getItemMasterById(Long id) {
//	
//			return itemMasterRepo.getItemMasterById(id);
//		}

	@Override
	public Map<String, Object> updateCreateItemMaster(ItemMasterDTO itemMasterDTO) throws ApplicationException {

		ItemMasterVO itemMasterVO = new ItemMasterVO();
		String message;
		if (ObjectUtils.isNotEmpty(itemMasterDTO.getId())) {

			itemMasterVO = itemMasterRepo.findById(itemMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Item not found"));

			if (!itemMasterVO.getItemCode().equals(itemMasterDTO.getItemCode())) {
				if (itemMasterRepo.existsByItemCodeAndOrg(itemMasterDTO.getItemCode(), itemMasterDTO.getOrg())) {

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

			if (itemMasterRepo.existsByItemCodeAndOrg(itemMasterDTO.getItemCode(), itemMasterDTO.getOrg())) {

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
		responseDTO.setCapitalOrInput(itemMasterVO.getCapitalOrInput());
//		responseDTO.setItemType(itemMasterVO.getItemType());

		if (itemMasterVO.getItemType() != null) {
			ListOfImageResponseDTO listOfValuesDTO = new ListOfImageResponseDTO();
			listOfValuesDTO.setId(itemMasterVO.getItemType().getId());
			listOfValuesDTO.setListCode(itemMasterVO.getItemType().getListCode());
			listOfValuesDTO.setListDescription(itemMasterVO.getItemType().getListDescription());

			List<ListOfImageResponseDetailsDTO> detailsList = new ArrayList<>();
			if (itemMasterVO.getItemType().getListOfValuesDetailsVO() != null) {
				for (ListOfValuesDetailsVO detailVO : itemMasterVO.getItemType().getListOfValuesDetailsVO()) {
					ListOfImageResponseDetailsDTO detailDTO = new ListOfImageResponseDetailsDTO();
					detailDTO.setValueCode(detailVO.getValueCode());
					detailDTO.setValueDescription(detailVO.getValueDescription());
					detailsList.add(detailDTO);
				}
			}
			listOfValuesDTO.setListOfImageResponseDetailsDTO(detailsList);

			responseDTO.setListOfValues(listOfValuesDTO);
			responseDTO.setListOfValuesId(itemMasterVO.getItemType().getId());
		}

//		responseDTO.setGrade(itemMasterVO.getGrade());

		if (itemMasterVO.getGrade() != null) {
			ListOfImageResponseDTO listOfValuesDTO = new ListOfImageResponseDTO();
			listOfValuesDTO.setId(itemMasterVO.getGrade().getId());
			listOfValuesDTO.setListCode(itemMasterVO.getGrade().getListCode());
			listOfValuesDTO.setListDescription(itemMasterVO.getGrade().getListDescription());

			List<ListOfImageResponseDetailsDTO> detailsList = new ArrayList<>();
			if (itemMasterVO.getGrade().getListOfValuesDetailsVO() != null) {
				for (ListOfValuesDetailsVO detailVO : itemMasterVO.getGrade().getListOfValuesDetailsVO()) {
					ListOfImageResponseDetailsDTO detailDTO = new ListOfImageResponseDetailsDTO();
					detailDTO.setValueCode(detailVO.getValueCode());
					detailDTO.setValueDescription(detailVO.getValueDescription());
					detailsList.add(detailDTO);
				}
			}
			listOfValuesDTO.setListOfImageResponseDetailsDTO(detailsList);

			responseDTO.setListOfGrade(listOfValuesDTO);
			responseDTO.setListOfValuesId(itemMasterVO.getGrade().getId());
		}

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

			responseDTO.setListOfGroupDetails(listOfValuesDTO);
			responseDTO.setListOfValuesId(itemMasterVO.getItemGroup().getId());
		}

		responseDTO.setItemCode(itemMasterVO.getItemCode());
//		responseDTO.setExciseTariffNo(itemMasterVO.getExciseTariffNo());

		if (itemMasterVO.getExciseTariffNo() != null) {
			ListOfImageResponseDTO listOfValuesDTO = new ListOfImageResponseDTO();
			listOfValuesDTO.setId(itemMasterVO.getExciseTariffNo().getId());
			listOfValuesDTO.setListCode(itemMasterVO.getExciseTariffNo().getListCode());
			listOfValuesDTO.setListDescription(itemMasterVO.getExciseTariffNo().getListDescription());

			List<ListOfImageResponseDetailsDTO> detailsList = new ArrayList<>();
			if (itemMasterVO.getExciseTariffNo().getListOfValuesDetailsVO() != null) {
				for (ListOfValuesDetailsVO detailVO : itemMasterVO.getExciseTariffNo().getListOfValuesDetailsVO()) {
					ListOfImageResponseDetailsDTO detailDTO = new ListOfImageResponseDetailsDTO();
					detailDTO.setValueCode(detailVO.getValueCode());
					detailDTO.setValueDescription(detailVO.getValueDescription());
					detailsList.add(detailDTO);
				}
			}
			listOfValuesDTO.setListOfImageResponseDetailsDTO(detailsList);

			responseDTO.setListOfValues(listOfValuesDTO);
			responseDTO.setListOfValuesId(itemMasterVO.getExciseTariffNo().getId());
		}

		responseDTO.setItemDescription(itemMasterVO.getItemDescription());
		responseDTO.setThickness(itemMasterVO.getThickness());
		responseDTO.setIsStock(itemMasterVO.getIsStock());
		responseDTO.setWidth(itemMasterVO.getWidth());
		responseDTO.setPrototype(itemMasterVO.getPrototype());
		responseDTO.setLength(itemMasterVO.getLength());
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
		responseDTO.setLotSize(itemMasterVO.getLotSize());
		responseDTO.setImportOrLocal(itemMasterVO.getImportOrLocal());
		responseDTO.setSaftyStockMsl(itemMasterVO.getSaftyStockMsl());
		responseDTO.setIsGrnRequired(itemMasterVO.getIsGrnRequired());
		responseDTO.setRawMaterialsMake(itemMasterVO.getRawMaterialsMake());

//		responseDTO.setHsnCode(itemMasterVO.getHsnCode());

		if (itemMasterVO.getHsnCode() != null) {
			HsnResponseImageDTO hsnResponseImageDTO = new HsnResponseImageDTO();
			hsnResponseImageDTO.setId(itemMasterVO.getHsnCode().getId());
			hsnResponseImageDTO.setHsnCode(itemMasterVO.getHsnCode().getHsn());
			responseDTO.setItemHsn(hsnResponseImageDTO);
		}

		responseDTO.setCreatedBy(itemMasterVO.getCreatedBy());
		responseDTO.setOrg(itemMasterVO.getOrg());
		responseDTO.setUpdatedBy(itemMasterVO.getUpdatedBy());
		responseDTO.setCancelRemarks(itemMasterVO.getCancelRemarks());

//		responseDTO.setPurchaseUnit(itemMasterVO.getPurchaseUnit());
//
//		responseDTO.setSellingUnit(itemMasterVO.getSellingUnit());
//		responseDTO.setPricingUnit(itemMasterVO.getPricingUnit());
//		responseDTO.setSecondaryUnit(itemMasterVO.getSecondaryUnit());

		if (itemMasterVO.getPurchaseUnit() != null) {
			PrimaryUnitImageDTO primaryUnitDTO = new PrimaryUnitImageDTO();
			primaryUnitDTO.setId(itemMasterVO.getPurchaseUnit().getId());
			primaryUnitDTO.setPrimaryUnit(itemMasterVO.getPurchaseUnit().getUnitId());
			responseDTO.setPurchaseUnit(primaryUnitDTO);
		}
		if (itemMasterVO.getSellingUnit() != null) {
			PrimaryUnitImageDTO primaryUnitDTO = new PrimaryUnitImageDTO();
			primaryUnitDTO.setId(itemMasterVO.getSellingUnit().getId());
			primaryUnitDTO.setPrimaryUnit(itemMasterVO.getSellingUnit().getUnitId());
			responseDTO.setSellingUnit(primaryUnitDTO);
		}
		if (itemMasterVO.getPricingUnit() != null) {
			PrimaryUnitImageDTO primaryUnitDTO = new PrimaryUnitImageDTO();
			primaryUnitDTO.setId(itemMasterVO.getPricingUnit().getId());
			primaryUnitDTO.setPrimaryUnit(itemMasterVO.getPricingUnit().getUnitId());
			responseDTO.setPricingUnit(primaryUnitDTO);
		}
		if (itemMasterVO.getSecondaryUnit() != null) {
			PrimaryUnitImageDTO primaryUnitDTO = new PrimaryUnitImageDTO();
			primaryUnitDTO.setId(itemMasterVO.getSecondaryUnit().getId());
			primaryUnitDTO.setPrimaryUnit(itemMasterVO.getSecondaryUnit().getUnitId());
			responseDTO.setSecondaryUnit(primaryUnitDTO);
		}

		responseDTO.setManufacturedOrBoughtout(itemMasterVO.getManufacturedOrBoughtout());

		if (itemMasterVO.getDefaultLocation() != null) {
			LocationImageDTO primaryUnitDTO = new LocationImageDTO();
			primaryUnitDTO.setId(itemMasterVO.getDefaultLocation().getId());
			primaryUnitDTO.setLocationName(itemMasterVO.getDefaultLocation().getLocationName());
			responseDTO.setLocationDefalutReponse(primaryUnitDTO);
		}

		if (itemMasterVO.getAlternativeLocation() != null) {
			LocationImageDTO primaryUnitDTO = new LocationImageDTO();
			primaryUnitDTO.setId(itemMasterVO.getAlternativeLocation().getId());
			primaryUnitDTO.setLocationName(itemMasterVO.getAlternativeLocation().getLocationName());
			responseDTO.setLocationAlterReponse(primaryUnitDTO);
		}

		responseDTO.setLeadTime(itemMasterVO.getLeadTime());
		responseDTO.setReorderLevel(itemMasterVO.getReorderLevel());
		responseDTO.setRackNo(itemMasterVO.getRackNo());
		responseDTO.setRowNo(itemMasterVO.getRowNo());
		responseDTO.setPosition(itemMasterVO.getPosition());
		responseDTO.setMinimumOrderQty(itemMasterVO.getMinimumOrderQty());
		responseDTO.setMaximumOrderQty(itemMasterVO.getMaximumOrderQty());
		responseDTO.setBinSize(itemMasterVO.getBinSize());
		responseDTO.setBinQty(itemMasterVO.getBinQty());

//		responseDTO.setDefaultSupplier(itemMasterVO.getDefaultSupplier());
//		responseDTO.setAlternativeSupplier(itemMasterVO.getAlternativeSupplier());

		if (itemMasterVO.getDefaultSupplier() == null || itemMasterVO.getDefaultSupplier() != null) {
			PartyResponseDTO primaryUnitDTO = new PartyResponseDTO();
			primaryUnitDTO.setId(itemMasterVO.getDefaultSupplier().getId());
			primaryUnitDTO.setPartyName(itemMasterVO.getDefaultSupplier().getCustomerName());
			responseDTO.setDefaultPartyResponse(primaryUnitDTO);
		}

		if (itemMasterVO.getAlternativeSupplier() == null || itemMasterVO.getAlternativeSupplier() != null) {
			PartyResponseDTO primaryUnitDTO = new PartyResponseDTO();
			primaryUnitDTO.setId(itemMasterVO.getAlternativeSupplier().getId());
			primaryUnitDTO.setPartyName(itemMasterVO.getAlternativeSupplier().getCustomerName());
			responseDTO.setAlterPartyResponse(primaryUnitDTO);
		}

		responseDTO.setLeadTime(itemMasterVO.getLeadTime());
		responseDTO.setPruchaseTalerance(itemMasterVO.getPruchaseTalerance());
		responseDTO.setRate(itemMasterVO.getRate());
		responseDTO.setDate(itemMasterVO.getDate());
		responseDTO.setLandedCostRate(itemMasterVO.getLandedCostRate());
		responseDTO.setToolOwner(itemMasterVO.getToolOwner());
		responseDTO.setToolNo(itemMasterVO.getToolNo());

		if (itemMasterVO.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(itemMasterVO.getBranch().getId());
			branchDTO.setBranchCode(itemMasterVO.getBranch().getBranchCode());
			branchDTO.setBranchName(itemMasterVO.getBranch().getBranchName());
			responseDTO.setBranch(branchDTO);

			responseDTO.setBranchId(itemMasterVO.getBranch().getId());
		}

		responseDTO.setIsItemBlockedForInvoicing(itemMasterVO.getIsItemBlockedForInvoicing());
		responseDTO.setMinSellPrice(itemMasterVO.getMinSellPrice());
		responseDTO.setSalesAccount(itemMasterVO.getSalesAccount());
		responseDTO.setLeadTimeToDispatch(itemMasterVO.getLeadTimeToDispatch());
		responseDTO.setCustomerPartNo(itemMasterVO.getCustomerPartNo());

		responseDTO.setTechSpec(itemMasterVO.getTechSpec());
		responseDTO.setSupplierPartNo(itemMasterVO.getSupplierPartNo());

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

		itemMasterVO.setOrg(itemMasterDTO.getOrg());
		itemMasterVO.setCreatedBy(itemMasterDTO.getCreatedBy());

		if (itemMasterDTO.getListOfValuesId() != null && itemMasterDTO.getListOfValuesId() != 0) {

			ListOfValuesVO listofvalues = listOfValuesRepo.findById(itemMasterDTO.getListOfValuesId())
					.orElseThrow(() -> new ApplicationException("ListOfValues Is Not Found"));

			itemMasterVO.setItemGroup(listofvalues);

		}
		itemMasterVO.setCapitalOrInput(itemMasterDTO.getCapitalOrInput());

		if (itemMasterDTO.getListOfValuesId() != null && itemMasterDTO.getListOfValuesId() != 0) {

			ListOfValuesVO listofvalues = listOfValuesRepo.findById(itemMasterDTO.getListOfValuesId())
					.orElseThrow(() -> new ApplicationException("ListOfValues Is Not Found"));

			itemMasterVO.setItemType(listofvalues);

		}

		if (itemMasterDTO.getGradeId() != null && itemMasterDTO.getGradeId() != 0) {

			ListOfValuesVO listofvalues = listOfValuesRepo.findById(itemMasterDTO.getGradeId())
					.orElseThrow(() -> new ApplicationException("ListOfValues Is Not Found"));

			itemMasterVO.setGrade(listofvalues);

		}
		itemMasterVO.setItemCode(itemMasterDTO.getItemCode());

		if (itemMasterDTO.getExciseTariffNoId() != null && itemMasterDTO.getExciseTariffNoId() != 0) {

			ListOfValuesVO listofvalues = listOfValuesRepo.findById(itemMasterDTO.getExciseTariffNoId())
					.orElseThrow(() -> new ApplicationException("ListOfValues Is Not Found"));

			itemMasterVO.setExciseTariffNo(listofvalues);

		}

		itemMasterVO.setItemDescription(itemMasterDTO.getItemDescription());

		itemMasterVO.setThickness(itemMasterDTO.getThickness());
		itemMasterVO.setSaftyStockMsl(itemMasterDTO.getSaftyStockMsl());
		itemMasterVO.setWidth(itemMasterDTO.getWidth());
		itemMasterVO.setPrototype(itemMasterDTO.getPrototype());
		itemMasterVO.setLength(itemMasterDTO.getLength());
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
		itemMasterVO.setLotSize(itemMasterDTO.getLotSize());
//		itemMasterVO.setShelfLifePart(itemMasterDTO.getShelfLifePart());
		itemMasterVO.setImportOrLocal(itemMasterDTO.getImportOrLocal());
		itemMasterVO.setSaftyStockMsl(itemMasterDTO.getSaftyStockMsl());
		itemMasterVO.setIsGrnRequired(itemMasterDTO.getIsGrnRequired());
		itemMasterVO.setRawMaterialsMake(itemMasterDTO.getRawMaterialsMake());

		if (itemMasterDTO.getHsnId() != null && itemMasterDTO.getHsnId() != 0) {

			HsnVO hsn = hsnRepo.findById(itemMasterDTO.getHsnId())
					.orElseThrow(() -> new ApplicationException("HsnCode Not Found"));

			itemMasterVO.setHsnCode(hsn);
		}

		itemMasterVO.setCreatedBy(itemMasterDTO.getCreatedBy());
		itemMasterVO.setUpdatedBy(itemMasterDTO.getUpdatedBy());
		itemMasterVO.setCancelRemarks(itemMasterDTO.getCancelRemarks());

		if (itemMasterDTO.getPurchaseUnitId() != null && itemMasterDTO.getPurchaseUnitId() != 0) {

			UnitMasterVO branch = unitMasterRepo.findById(itemMasterDTO.getPurchaseUnitId())
					.orElseThrow(() -> new ApplicationException("PurchaseUnit Not Found"));

			itemMasterVO.setPurchaseUnit(branch);
		}

		if (itemMasterDTO.getSellingUnitId() != null && itemMasterDTO.getSellingUnitId() != 0) {

			UnitMasterVO branch = unitMasterRepo.findById(itemMasterDTO.getSellingUnitId())
					.orElseThrow(() -> new ApplicationException("SellingUnit Not Found"));

			itemMasterVO.setSellingUnit(branch);
		}

		if (itemMasterDTO.getPricingUnitId() != null && itemMasterDTO.getPricingUnitId() != 0) {

			UnitMasterVO branch = unitMasterRepo.findById(itemMasterDTO.getPricingUnitId())
					.orElseThrow(() -> new ApplicationException("PricingUnit Not Found"));

			itemMasterVO.setPricingUnit(branch);
		}

		if (itemMasterDTO.getSecondaryUnitId() != null && itemMasterDTO.getSecondaryUnitId() != 0) {

			UnitMasterVO branch = unitMasterRepo.findById(itemMasterDTO.getSecondaryUnitId())
					.orElseThrow(() -> new ApplicationException("SecondaryUnit Not Found"));

			itemMasterVO.setSecondaryUnit(branch);
		}

		itemMasterVO.setManufacturedOrBoughtout(itemMasterDTO.getManufacturedOrBoughtout());

		if (itemMasterDTO.getDefaultLocationId() != null && itemMasterDTO.getDefaultLocationId() != 0) {

			LocationVO location = locationRepo.findById(itemMasterDTO.getDefaultLocationId())
					.orElseThrow(() -> new ApplicationException("DefaultLocation Not Found"));

			itemMasterVO.setDefaultLocation(location);
		}

		if (itemMasterDTO.getAlternativeLocationId() != null && itemMasterDTO.getAlternativeLocationId() != 0) {

			LocationVO location = locationRepo.findById(itemMasterDTO.getAlternativeLocationId())
					.orElseThrow(() -> new ApplicationException("tAlternativeLocation Not Found"));

			itemMasterVO.setAlternativeLocation(location);
		}

		itemMasterVO.setLeadTime(itemMasterDTO.getLeadTime());
		itemMasterVO.setReorderLevel(itemMasterDTO.getReorderLevel());
		itemMasterVO.setRackNo(itemMasterDTO.getRackNo());
		itemMasterVO.setRowNo(itemMasterDTO.getRowNo());
		itemMasterVO.setPosition(itemMasterDTO.getPosition());
		itemMasterVO.setMinimumOrderQty(itemMasterDTO.getMinimumOrderQty());
		itemMasterVO.setMaximumOrderQty(itemMasterDTO.getMaximumOrderQty());
		itemMasterVO.setBinSize(itemMasterDTO.getBinSize());
		itemMasterVO.setBinQty(itemMasterDTO.getBinQty());

		itemMasterVO.setLeadTime(itemMasterDTO.getLeadTime());
		itemMasterVO.setPruchaseTalerance(itemMasterDTO.getPruchaseTalerance());
		itemMasterVO.setRate(itemMasterDTO.getRate());
		itemMasterVO.setDate(itemMasterDTO.getDate());
		itemMasterVO.setLandedCostRate(itemMasterDTO.getLandedCostRate());
		itemMasterVO.setToolOwner(itemMasterDTO.getToolOwner());
		itemMasterVO.setToolNo(itemMasterDTO.getToolNo());

		if (itemMasterDTO.getAlternativeSupplierId() == null || itemMasterDTO.getAlternativeSupplierId() != null) {

			CustomerVO party = customerRepo.findById(itemMasterDTO.getAlternativeSupplierId())
					.orElseThrow(() -> new ApplicationException("AlternativeSupplier Not Found"));

			itemMasterVO.setAlternativeSupplier(party);
		}

		if (itemMasterDTO.getDefaultSupplierId() == null || itemMasterDTO.getDefaultSupplierId() != null) {

			CustomerVO party = customerRepo.findById(itemMasterDTO.getDefaultSupplierId())
					.orElseThrow(() -> new ApplicationException("DefaultSupplier Not Found"));

			itemMasterVO.setDefaultSupplier(party);
		}

		itemMasterVO.setIsItemBlockedForInvoicing(itemMasterDTO.getIsItemBlockedForInvoicing());
		itemMasterVO.setMinSellPrice(itemMasterDTO.getMinSellPrice());
		itemMasterVO.setSalesAccount(itemMasterDTO.getSalesAccount());
		itemMasterVO.setLeadTimeToDispatch(itemMasterDTO.getLeadTimeToDispatch());
		itemMasterVO.setCustomerPartNo(itemMasterDTO.getCustomerPartNo());

		itemMasterVO.setTechSpec(itemMasterDTO.getTechSpec());
		itemMasterVO.setSupplierPartNo(itemMasterDTO.getSupplierPartNo());

		if (itemMasterDTO.getBranchId() != null && itemMasterDTO.getBranchId() != 0) {

			BranchVO branch = branchRepo.findById(itemMasterDTO.getBranchId())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			itemMasterVO.setBranch(branch);
		}

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

	@Override
	public ItemMasterResponseDTO getItemMasterById(Long id) throws ApplicationException {
		
		ItemMasterVO itemMasterVO = itemMasterRepo.getItemMasterById(id);

		if (itemMasterVO == null) {
			throw new ApplicationException("Item Master Not Found");
		}

		return buildItemMasterResponse(itemMasterVO);
	}

	@Override
	public ItemMasterResponseDTO getItemMasterByOrgId(Long orgId, Long branchId) throws ApplicationException {

		ItemMasterVO itemMasterVO = itemMasterRepo.getItemMasterByOrgId(orgId,branchId);

		if (itemMasterVO == null) {
			throw new ApplicationException("Item Master Not Found");
		}

		return buildItemMasterResponse(itemMasterVO);
	}
}
