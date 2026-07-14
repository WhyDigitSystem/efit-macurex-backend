package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.BomDTO;
import com.efitops.basesetup.dto.BomDetailsDTO;
import com.efitops.basesetup.dto.DepartmentDTO;
import com.efitops.basesetup.dto.DesignationDTO;
import com.efitops.basesetup.dto.EmployeeCommunicationDetailsDTO;
import com.efitops.basesetup.dto.EmployeeComplianceDetailsDTO;
import com.efitops.basesetup.dto.EmployeeDetailsDTO;
import com.efitops.basesetup.dto.EmployeeFinanceInformationDTO;
import com.efitops.basesetup.dto.EmployeeLoanDetailsDTO;
import com.efitops.basesetup.dto.EmployeeMasterDTO;
import com.efitops.basesetup.dto.EmployeePersonalDetailsDTO;
import com.efitops.basesetup.dto.GstDTO;
import com.efitops.basesetup.dto.ItemDTO;
import com.efitops.basesetup.dto.ItemInventoryDTO;
import com.efitops.basesetup.dto.ItemPriceSlabDTO;
import com.efitops.basesetup.dto.ItemTaxSlabDTO;
import com.efitops.basesetup.dto.ItemWiseProcessDetailsDTO;
import com.efitops.basesetup.dto.ItemWiseProcessMasterDTO;
import com.efitops.basesetup.dto.MaterialTypeDTO;
import com.efitops.basesetup.dto.MaterialTypeDetailsDTO;
import com.efitops.basesetup.dto.MeasuringInstrumentsDTO;
import com.efitops.basesetup.dto.ProcessMasterDTO;
import com.efitops.basesetup.dto.RackMasterDTO;
import com.efitops.basesetup.dto.ShiftBreakTimingDetailsDTO;
import com.efitops.basesetup.dto.ShiftDTO;
import com.efitops.basesetup.dto.ShiftDetailsDTO;
import com.efitops.basesetup.dto.UomDTO;
import com.efitops.basesetup.entity.BomDetailsVO;
import com.efitops.basesetup.entity.BomVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.DesignationVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.EmployeeCommunicationDetailsVO;
import com.efitops.basesetup.entity.EmployeeComplianceDetailsVO;
import com.efitops.basesetup.entity.EmployeeDetailsVO;
import com.efitops.basesetup.entity.EmployeeFinanceInformationVO;
import com.efitops.basesetup.entity.EmployeeLoanDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.EmployeePersonalDetailsVO;
import com.efitops.basesetup.entity.GstVO;
import com.efitops.basesetup.entity.ItemInventoryVO;
import com.efitops.basesetup.entity.ItemPriceSlabVO;
import com.efitops.basesetup.entity.ItemTaxSlabVO;
import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.ItemWiseProcessDetailsVO;
import com.efitops.basesetup.entity.ItemWiseProcessMasterVO;
import com.efitops.basesetup.entity.MaterialTypeDetailsVO;
import com.efitops.basesetup.entity.MaterialTypeVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;
import com.efitops.basesetup.entity.NotificationDesignationDetailsVO;
import com.efitops.basesetup.entity.NotificationDesignationVO;
import com.efitops.basesetup.entity.NotificationVO;
import com.efitops.basesetup.entity.ProcessMasterVO;
import com.efitops.basesetup.entity.RackMasterVO;
import com.efitops.basesetup.entity.ShiftBreakTimingDetailsVO;
import com.efitops.basesetup.entity.ShiftDetailsVO;
import com.efitops.basesetup.entity.ShiftVO;
import com.efitops.basesetup.entity.UomVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.BomDetailsRepo;
import com.efitops.basesetup.repo.BomRepo;
import com.efitops.basesetup.repo.DepartmentRepo;
import com.efitops.basesetup.repo.DesignationRepo;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.EmployeeCommunicationDetailsRepo;
import com.efitops.basesetup.repo.EmployeeComplianceDetailsRepo;
import com.efitops.basesetup.repo.EmployeeDetailsRepo;
import com.efitops.basesetup.repo.EmployeeFinanceInformationRepo;
import com.efitops.basesetup.repo.EmployeeLoanDetailsRepo;
import com.efitops.basesetup.repo.EmployeeMasterRepo;
import com.efitops.basesetup.repo.EmployeePersonalDetailsRepo;
import com.efitops.basesetup.repo.GstRepo;
import com.efitops.basesetup.repo.ItemInventoryRepo;
import com.efitops.basesetup.repo.ItemPriceSlabRepo;
import com.efitops.basesetup.repo.ItemRepo;
import com.efitops.basesetup.repo.ItemTaxSlabRepo;
import com.efitops.basesetup.repo.ItemWiseProcessDetailsRepo;
import com.efitops.basesetup.repo.ItemWiseProcessMasterRepo;
import com.efitops.basesetup.repo.MaterialTypeDetailRepo;
import com.efitops.basesetup.repo.MaterialTypeRepo;
import com.efitops.basesetup.repo.MeasuringInstrumentsRepo;
import com.efitops.basesetup.repo.NotificationDesignationDetailsRepo;
import com.efitops.basesetup.repo.NotificationDesignationRepo;
import com.efitops.basesetup.repo.NotificationRepo;
import com.efitops.basesetup.repo.ProcessMasterRepo;
import com.efitops.basesetup.repo.RackMasterRepo;
import com.efitops.basesetup.repo.ShiftBreakTimingDetailsRepo;
import com.efitops.basesetup.repo.ShiftDetailsRepo;
import com.efitops.basesetup.repo.ShiftRepo;
import com.efitops.basesetup.repo.UomRepo;
import com.efitops.basesetup.repo.UserRepo;

@Service
public class EfitMasterServiceImpl implements EfitMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	ItemRepo itemRepo;
	@Autowired
	ItemInventoryRepo itemInventoryRepo;
	@Autowired
	ItemPriceSlabRepo itemPriceSlabRepo;
	@Autowired
	ItemTaxSlabRepo itemTaxSlabRepo;

	@Autowired
	MeasuringInstrumentsRepo measuringInstrumentsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	ItemWiseProcessMasterRepo itemWiseProcessMasterRepo;

	@Autowired
	ItemWiseProcessDetailsRepo itemWiseProcessDetailsRepo;

	@Autowired
	DepartmentRepo departmentRepo;

	@Autowired
	GstRepo gstRepo;

	@Autowired
	ProcessMasterRepo processMasterRepo;

	@Autowired
	MaterialTypeRepo materialTypeRepo;

	@Autowired
	MaterialTypeDetailRepo materialTypeDetailRepo;

	@Autowired
	DesignationRepo designationrepo;

	@Autowired
	UomRepo uomrepo;

	@Autowired
	ShiftRepo shiftRepo;

	@Autowired
	ShiftDetailsRepo shiftDetailsRepo;

	@Autowired
	ShiftRepo shiftrepo;

	@Autowired
	RackMasterRepo rackMasterRepo;

	@Autowired
	BomRepo bomRepo;

	@Autowired
	BomDetailsRepo bomDetailsRepo;

	@Autowired
	ShiftBreakTimingDetailsRepo shiftBreakTimingDetailsRepo;

	@Autowired
	EmployeeMasterRepo employeeMasterRepo;

	@Autowired
	EmployeeDetailsRepo employeeDetailsRepo;

	@Autowired
	EmployeePersonalDetailsRepo employeePersonalRepo;

	@Autowired
	EmployeeCommunicationDetailsRepo employeeCommunicationRepo;

	@Autowired
	EmployeeComplianceDetailsRepo employeeComplianceRepo;

	@Autowired
	EmployeeFinanceInformationRepo employeeFinanceRepo;

	@Autowired
	EmployeeLoanDetailsRepo employeeLoanRepo;

	@Autowired
	NotificationRepo notificationRepo;

	@Autowired
	NotificationDesignationRepo notificationDesignationRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	NotificationDesignationDetailsRepo notificationDesignationDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ItemVO> getItemByOrgId(Long orgId) {

		return itemRepo.findItemByOrgId(orgId);

	}

	@Override
	public List<ItemVO> getItemById(Long id) {
		List<ItemVO> itemVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Item BY Id : {}", id);
			itemVO = itemRepo.findItemById(id);
		}
		return itemVO;
	}

	@Override
	public Map<String, Object> updateCreateItemMaster(@Valid ItemDTO itemDTO) throws ApplicationException {
		String message;

		ItemVO itemVO = new ItemVO();

		ItemVO oldItem = null;
		
		if (itemDTO.getId() != null) {
			
			oldItem = itemRepo.findById(itemDTO.getId())
		            .orElseThrow(() -> new ApplicationException("Item master not found"));

		    oldItem.getItemPriceSlabVO().size();
		    oldItem.getItemInventoryVO().size();
		    oldItem.getItemTaxSlabVO().size();;
		    entityManager.detach(oldItem); // detach snapshot
			
			// Fetch existing ItemVO for update
			itemVO = itemRepo.findById(itemDTO.getId())
					.orElseThrow(() -> new ApplicationException("Item master not found"));
			itemVO.setUpdatedBy(itemDTO.getCreatedBy());
			
			if (!itemVO.getItemName().equalsIgnoreCase(itemDTO.getItemName())) {
				if (itemRepo.existsByItemNameAndOrgId(itemDTO.getItemName(), itemDTO.getOrgId())) {
					String errorMessage = String.format("This ItemName: %s Already Exists in This Organization",
							itemDTO.getItemName());
					throw new ApplicationException(errorMessage);
				}
				itemVO.setItemName(itemDTO.getItemName().toUpperCase());
			}
			
			message = "Item Master Updated Successfully";

		} else {

			if (itemRepo.existsByItemNameAndOrgId(itemDTO.getItemName(), itemDTO.getOrgId())) {
				String errorMessage = String.format("This ItemName: %s Already Exists in This Organization",
						itemDTO.getItemName());
				throw new ApplicationException(errorMessage);
			}
			// Create new ItemVO
			itemVO.setCreatedBy(itemDTO.getCreatedBy());
			itemVO.setUpdatedBy(itemDTO.getCreatedBy());
			message = "Item Master Created Successfully";
		}

		createUpdateItemMasterVOByItemMasterDTO(itemDTO, itemVO);
		itemVO = itemRepo.save(itemVO);
		commonNotificationService.generateNotification(itemVO.getScreenCode(), itemVO.getId(), oldItem, itemVO);	

//		createUpdateItemMasterNotification(itemVO, itemDTO);
//		if (itemDTO.getId() == null)
//		{
//			createUpdateItemMasterNotification(itemVO);
//		}
		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("itemVO", itemVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateItemMasterVOByItemMasterDTO(@Valid ItemDTO itemDTO, ItemVO itemVO) {
		itemVO.setItemName(itemDTO.getItemName());
		itemVO.setMaterialGroup(itemDTO.getMaterialGroup());
		itemVO.setMaterialType(itemDTO.getMaterialType());
		itemVO.setItemType(itemDTO.getItemType());
		itemVO.setMaterialSubGroup(itemDTO.getMaterialSubGroup());
		itemVO.setItemDesc(itemDTO.getItemDesc());
		itemVO.setPrimaryUnit(itemDTO.getPrimaryUnit());
		itemVO.setHsnCode(itemDTO.getHsnCode());
		itemVO.setNeedqcapproval(itemDTO.getNeedQcApproval());
		itemVO.setInspection(itemDTO.getInspection());
		itemVO.setInstrumentSeqCode(itemDTO.getInstrumentSeqCode());
		itemVO.setOrgId(itemDTO.getOrgId());
		itemVO.setActive(itemDTO.isActive());

		if (itemVO.getId() != null) {
		List<ItemTaxSlabVO> itemTaxSlabVOslist = itemTaxSlabRepo.findByItemVO(itemVO);
		itemTaxSlabRepo.deleteAll(itemTaxSlabVOslist);

		List<ItemInventoryVO> itemInventoryVOslist = itemInventoryRepo.findByItemVO(itemVO);
		itemInventoryRepo.deleteAll(itemInventoryVOslist);

		List<ItemPriceSlabVO> itemPriceSlabVOslist = itemPriceSlabRepo.findByItemVO(itemVO);
		itemPriceSlabRepo.deleteAll(itemPriceSlabVOslist);
		
		}
		// Handling ItemInventoryVO
		List<ItemInventoryVO> itemInventoryVOs = new ArrayList<>();
		for (ItemInventoryDTO itemInventoryDTO : itemDTO.getItemInventoryDTO()) {
			ItemInventoryVO itemInventoryVO = new ItemInventoryVO();
			itemInventoryVO.setImportLocal(itemInventoryDTO.getImportLocal());
			itemInventoryVO.setStockLocation(itemInventoryDTO.getStockLocation());
			itemInventoryVO.setMinOrderQuantity(itemInventoryDTO.getMinOrderQuantity());
			itemInventoryVO.setReOrderLevel(itemInventoryDTO.getReOrderLevel());
			itemInventoryVO.setItemVO(itemVO); // Set the reference in child entity
			itemInventoryVOs.add(itemInventoryVO);
		}
		itemVO.setItemInventoryVO(itemInventoryVOs);

		// Handling ItemPriceSlabVO
		List<ItemPriceSlabVO> itemPriceSlabVOs = new ArrayList<>();
		for (ItemPriceSlabDTO itemPriceSlabDTO : itemDTO.getItemPriceSlabDTO()) {
			ItemPriceSlabVO itemPriceSlabVO = new ItemPriceSlabVO();
			itemPriceSlabVO.setPrice(itemPriceSlabDTO.getPrice());
			itemPriceSlabVO.setPriceEffectiveFrom(itemPriceSlabDTO.getPriceEffectiveFrom());
			itemPriceSlabVO.setItemVO(itemVO); // Set the reference in child entity
			itemPriceSlabVOs.add(itemPriceSlabVO);
		}
		itemVO.setItemPriceSlabVO(itemPriceSlabVOs);

		// Handling ItemTaxSlabVO
		List<ItemTaxSlabVO> itemTaxSlabVOs = new ArrayList<>();
		for (ItemTaxSlabDTO itemTaxSlabDTO : itemDTO.getItemTaxSlabDTO()) {
			ItemTaxSlabVO itemTaxSlabVO = new ItemTaxSlabVO();
			itemTaxSlabVO.setTaxSlab(itemTaxSlabDTO.getTaxSlab());
			itemTaxSlabVO.setTaxEffectiveFrom(itemTaxSlabDTO.getTaxEffectiveFrom());
			itemTaxSlabVO.setItemVO(itemVO); // Set the reference in child entity
			itemTaxSlabVOs.add(itemTaxSlabVO);
		}
		itemVO.setItemTaxSlabVO(itemTaxSlabVOs);
	}

//	private void createUpdateItemMasterNotification(ItemVO itemVO, ItemDTO itemDTO) {
//	private void createUpdateItemMasterNotification(ItemVO itemVO,ItemDTO itemDTO) {
//
//		String msg;
//		if(itemDTO.getId() != null) {
//			 msg = " Item is Updated that Item : " + itemVO.getItemName();
//		}else
//		{
//			 msg = "New Item is Created that Item : " + itemVO.getItemName();
//		}
//
//	    NotificationDesignationDetailsVO detailsVO =
//	        notificationDesignationDetailsRepo.findByScreenCode(itemVO.getScreenCode());
//
//	    if (detailsVO == null) {
//	        throw new RuntimeException("No record found for screenCode: " + itemVO.getScreenCode());
//	    }
//
//	    NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();
//
//	    String codes = headerVO.getDesignationcode();
//	    String names = headerVO.getDesignationname();
//
//	    List<String> codeList = Arrays.asList(codes.split(","));
//	    List<String> nameList = Arrays.asList(names.split(","));
//
//	    if (codeList.size() != nameList.size()) {
//	        throw new RuntimeException("Mismatch in designation data");
//	    }
//
//	    // Step 1: Get employees
//	    List<EmployeeMasterVO> employees =
//	        employeeMasterRepo.findByDesignationIn(nameList);
//
//	    // Step 2: Get employeeCodes
//	    List<String> employeeCodes = employees.stream()
//	            .map(EmployeeMasterVO::getEmployeeCode)
//	            .toList();
//
//	    // Step 3: Get userIds
//	    List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//	    if (userIds == null || userIds.isEmpty()) {
//	        throw new RuntimeException("No users found for given employee codes");
//	    }
//
//	    // ✅ Step 4: Save notification for each user
//	    for (Long userId : userIds) {
//
//	        NotificationVO n = new NotificationVO();
//
//	        n.setUserid(userId);
//	        n.setMessage(msg);
//	        n.setNotificationType(itemVO.getScreenName());
//
//	        notificationRepo.save(n);
//	    }
//	}
//		String msg;
//		if (itemDTO.getId() != null) {
//			msg = " Item is Updated that Item : " + itemVO.getItemName();
//		} else {
//			msg = "New Item is Created that Item : " + itemVO.getItemName();
//		}
//
//		NotificationDesignationDetailsVO detailsVO = notificationDesignationDetailsRepo
//				.findByScreenCode(itemVO.getScreenCode());
//
//		if (detailsVO == null) {
//			throw new RuntimeException("No record found for screenCode: " + itemVO.getScreenCode());
//		}
//
//		NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();
//
//		String codes = headerVO.getDesignationcode();
//		String names = headerVO.getDesignationname();
//
//		List<String> codeList = Arrays.asList(codes.split(","));
//		List<String> nameList = Arrays.asList(names.split(","));
//
//		if (codeList.size() != nameList.size()) {
//			throw new RuntimeException("Mismatch in designation data");
//		}
//
//		// Step 1: Get employees
//		List<EmployeeMasterVO> employees = employeeMasterRepo.findByDesignationIn(nameList);
//
//		// Step 2: Get employeeCodes
//		List<String> employeeCodes = employees.stream().map(EmployeeMasterVO::getEmployeeCode).toList();
//
//		// Step 3: Get userIds
//		List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//		if (userIds == null || userIds.isEmpty()) {
//			throw new RuntimeException("No users found for given employee codes");
//		}
//
//		// ✅ Step 4: Save notification for each user
//		for (Long userId : userIds) {
//
//			NotificationVO n = new NotificationVO();
//
//			n.setUserid(userId);
//			n.setMessage(msg);
//			n.setNotificationType(itemVO.getScreenName());
//
//			notificationRepo.save(n);
//		}
//	}

	@Override
	@Transactional
	public List<Map<String, Object>> getPrimaryCodeFromUomMaster(Long orgId) {

		Set<Object[]> result = itemRepo.findPrimaryCodeFromUomMaster(orgId);
		return getPrimaryCodeFromUomMaster(result);
	}

	private List<Map<String, Object>> getPrimaryCodeFromUomMaster(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("primaryUnit", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getStockLocationForItemMaster(Long orgId) {

		Set<Object[]> result = itemRepo.findStockLocationForItemMaster(orgId);
		return getStockLocationForItemMaster(result);
	}

	private List<Map<String, Object>> getStockLocationForItemMaster(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("stockLocation", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getTaxSlabFromGst(Long orgId) {

		Set<Object[]> result = itemRepo.findTaxSlabFromGst(orgId);
		return getTaxSlabFromGst(result);
	}

	private List<Map<String, Object>> getTaxSlabFromGst(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("taxSlab", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getMaterialGroupFromMaterialType(Long orgId, String materialType) {

		Set<Object[]> result = itemRepo.findMaterialGroupFromMaterialType(orgId, materialType);
		return getMaterialGroupFromMaterialType(result);
	}

	private List<Map<String, Object>> getMaterialGroupFromMaterialType(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("materialGroup", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getMaterialSubGroupFromMaterialType(Long orgId, String materialType,
			String materialGroup) {

		Set<Object[]> result = itemRepo.findMaterialSubGroupFromMaterialType(orgId, materialType, materialGroup);
		return getMaterialSubGroupFromMaterialType(result);
	}

	private List<Map<String, Object>> getMaterialSubGroupFromMaterialType(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("materialSubGroup", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getMaterialTypeForItemMaster(Long orgId) {

		Set<Object[]> result = itemRepo.findMaterialTypeForItemMaster(orgId);
		return getMaterialTypeForItemMaster(result);
	}

	private List<Map<String, Object>> getMaterialTypeForItemMaster(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("materialType", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	// MeasuringInstrument

	@Override
	public List<MeasuringInstrumentsVO> getMeasuringInstrumentByOrgId(Long orgId, String branchCode) {
		List<MeasuringInstrumentsVO> measuringInstrumentsVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received MeasuringInstrument BY OrgId : {}", orgId);
			measuringInstrumentsVO = measuringInstrumentsRepo.findMeasuringInstrumentsByOrgId(orgId, branchCode);
		}
		return measuringInstrumentsVO;
	}

	@Override
	public List<MeasuringInstrumentsVO> getMeasuringInstrumentById(Long id) {
		List<MeasuringInstrumentsVO> measuringInstrumentsVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received MeasuringInstrument BY Id : {}", id);
			measuringInstrumentsVO = measuringInstrumentsRepo.findMeasuringInstrumentsById(id);
		}
		return measuringInstrumentsVO;
	}

	@Override
	public Map<String, Object> updateCreateMeasuringInstruments(@Valid MeasuringInstrumentsDTO measuringInstrumentsDTO)
			throws ApplicationException {
		String message;
		MeasuringInstrumentsVO measuringInstrumentsVO = new MeasuringInstrumentsVO();
		String screenCode = "MI";

		if (measuringInstrumentsDTO.getId() != null) {

			measuringInstrumentsVO = measuringInstrumentsRepo.findById(measuringInstrumentsDTO.getId())
					.orElseThrow(() -> new ApplicationException("MeasuringInstrument not found"));

//			if (!measuringInstrumentsVO.getInstrumentName()
//					.equalsIgnoreCase(measuringInstrumentsDTO.getInstrumentName())) {
//				if (measuringInstrumentsRepo.existsByInstrumentNameAndOrgId(measuringInstrumentsDTO.getInstrumentName(),
//						measuringInstrumentsDTO.getOrgId())) {
//					String errorMessage = String.format("The InstrumentName: %s  already exists This Organization.",
//							measuringInstrumentsDTO.getInstrumentName());
//					throw new ApplicationException(errorMessage);
//				}
//			}

//			if (!measuringInstrumentsVO.getInstrumentCode()
//					.equalsIgnoreCase(measuringInstrumentsDTO.getInstrumentCode())) {
//				if (measuringInstrumentsRepo.existsByInstrumentCodeAndOrgId(measuringInstrumentsDTO.getInstrumentCode(),
//						measuringInstrumentsDTO.getOrgId())) {
//					String errorMessage = String.format("The InstrumentCode: %s  already exists This Organization.",
//							measuringInstrumentsDTO.getInstrumentCode());
//					throw new ApplicationException(errorMessage);
//				}
//			}

			boolean nameChanged = !measuringInstrumentsVO.getInstrumentName()
					.equalsIgnoreCase(measuringInstrumentsDTO.getInstrumentName());

			boolean codeChanged = !measuringInstrumentsVO.getInstrumentCode()
					.equalsIgnoreCase(measuringInstrumentsDTO.getInstrumentCode());

			if (nameChanged || codeChanged) {
				if (measuringInstrumentsRepo
						.existsByInstrumentNameIgnoreCaseAndInstrumentCodeIgnoreCaseAndOrgIdAndIdNot(
								measuringInstrumentsDTO.getInstrumentName(),
								measuringInstrumentsDTO.getInstrumentCode(), measuringInstrumentsDTO.getOrgId(),
								measuringInstrumentsDTO.getId())) {

					throw new ApplicationException(
							"Instrument Code already exists for this Instrument Name in this Organization");
				}
			}
			measuringInstrumentsVO.setUpdatedBy(measuringInstrumentsDTO.getCreatedBy());
			createUpdateMeasuringInstrumentVOByMeasuringInstrumentDTO(measuringInstrumentsDTO, measuringInstrumentsVO);
			message = "MeasuringInstrument Updated Successfully";
		} else {

			// GETDOCID API
			String docId = measuringInstrumentsRepo.getMeasuringInstrumentsDocId(measuringInstrumentsDTO.getOrgId(),
					measuringInstrumentsDTO.getFinYear(), measuringInstrumentsDTO.getBranchCode(), screenCode);

			measuringInstrumentsVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(measuringInstrumentsDTO.getOrgId(),
							measuringInstrumentsDTO.getFinYear(), measuringInstrumentsDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

//			if (measuringInstrumentsRepo.existsByInstrumentNameAndOrgId(measuringInstrumentsDTO.getInstrumentName(),
//					measuringInstrumentsDTO.getOrgId())) {
//				String errorMessage = String.format("The InstrumentName: %s  already exists This Organization.",
//						measuringInstrumentsDTO.getInstrumentName());
//				throw new ApplicationException(errorMessage);
//			}
//
//			if (measuringInstrumentsRepo.existsByInstrumentCodeAndOrgId(measuringInstrumentsDTO.getInstrumentCode(),
//					measuringInstrumentsDTO.getOrgId())) {
//				String errorMessage = String.format("The InstrumentCode: %s  already exists This Organization.",
//						measuringInstrumentsDTO.getInstrumentCode());
//				throw new ApplicationException(errorMessage);
//			}

			if (measuringInstrumentsRepo.existsByInstrumentNameIgnoreCaseAndInstrumentCodeIgnoreCaseAndOrgId(
					measuringInstrumentsDTO.getInstrumentName(), measuringInstrumentsDTO.getInstrumentCode(),
					measuringInstrumentsDTO.getOrgId())) {

				throw new ApplicationException(
						"Instrument Code already exists for this Instrument Name in this Organization");
			}

			measuringInstrumentsVO.setCreatedBy(measuringInstrumentsDTO.getCreatedBy());
			measuringInstrumentsVO.setUpdatedBy(measuringInstrumentsDTO.getCreatedBy());
			createUpdateMeasuringInstrumentVOByMeasuringInstrumentDTO(measuringInstrumentsDTO, measuringInstrumentsVO);
			message = "MeasuringInstrument Created Successfully";
		}

		// Save the ItemVO
		measuringInstrumentsRepo.save(measuringInstrumentsVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("measuringInstrumentsVO", measuringInstrumentsVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateMeasuringInstrumentVOByMeasuringInstrumentDTO(
			@Valid MeasuringInstrumentsDTO measuringInstrumentsDTO, MeasuringInstrumentsVO measuringInstrumentsVO) {
		measuringInstrumentsVO.setInstrumentName(measuringInstrumentsDTO.getInstrumentName());
		measuringInstrumentsVO.setInstrumentCode(measuringInstrumentsDTO.getInstrumentCode());
		measuringInstrumentsVO.setInstrumentDesc(measuringInstrumentsDTO.getInstrumentDesc());
		measuringInstrumentsVO.setLeastCount(measuringInstrumentsDTO.getLeastCount());
		measuringInstrumentsVO.setCalibrationFrequence(measuringInstrumentsDTO.getCalibrationFrequence());
		measuringInstrumentsVO.setColourCode(measuringInstrumentsDTO.getColourCode());
		measuringInstrumentsVO.setRanges(measuringInstrumentsDTO.getRanges());
		measuringInstrumentsVO.setRemarks(measuringInstrumentsDTO.getRemarks());
		measuringInstrumentsVO.setOrgId(measuringInstrumentsDTO.getOrgId());
		measuringInstrumentsVO.setFinYear(measuringInstrumentsDTO.getFinYear());
		measuringInstrumentsVO.setBranch(measuringInstrumentsDTO.getBranch());
		measuringInstrumentsVO.setBranchCode(measuringInstrumentsDTO.getBranchCode());
	}

	@Override
	public String getMeasuringInstrumentsDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "MI";
		String result = measuringInstrumentsRepo.getMeasuringInstrumentsDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getInstrumentNameFromItemMaster(Long orgId) {

		Set<Object[]> result = measuringInstrumentsRepo.findInstrumentNameFromItemMaster(orgId);
		return getInstrumentNameFromItemMaster(result);
	}

	private List<Map<String, Object>> getInstrumentNameFromItemMaster(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("item", fs[0] != null ? fs[0].toString() : "");
			part.put("itemDesc", fs[1] != null ? fs[1].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	// ItemWiseProcessMasterVO

	@Override
	public List<ItemWiseProcessMasterVO> getItemWiseProcessMasterByOrgId(Long orgId, String branchCode) {
		List<ItemWiseProcessMasterVO> itemWiseProcessMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ItemWiseProcessMaster BY OrgId : {}", orgId);
			itemWiseProcessMasterVO = itemWiseProcessMasterRepo.findItemWiseProcessMasterByOrgId(orgId, branchCode);
		}
		return itemWiseProcessMasterVO;
	}

	@Override
	public List<ItemWiseProcessMasterVO> getItemWiseProcessMasterById(Long id) {
		List<ItemWiseProcessMasterVO> itemWiseProcessMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ItemWiseProcessMaster BY Id : {}", id);
			itemWiseProcessMasterVO = itemWiseProcessMasterRepo.findItemWiseProcessMasterById(id);
		}
		return itemWiseProcessMasterVO;
	}

	@Override
	public Map<String, Object> updateCreateItemWiseProcessMaster(
			@Valid ItemWiseProcessMasterDTO itemWiseProcessMasterDTO) throws ApplicationException {
		String message;
		ItemWiseProcessMasterVO itemWiseProcessMasterVO = new ItemWiseProcessMasterVO();
		String screenCode = "IPM";
		ItemWiseProcessMasterVO oldItemWiseProcessMaster = null;
		if (itemWiseProcessMasterDTO.getId() != null) {

			oldItemWiseProcessMaster = itemWiseProcessMasterRepo.findById(itemWiseProcessMasterDTO.getId())
		            .orElseThrow(() -> new ApplicationException("ItemWiseProcessMaster master not found"));

			oldItemWiseProcessMaster.getItemWiseProcessDetailsVO().size();
		    entityManager.detach(oldItemWiseProcessMaster); // detach snapshot
		    
			itemWiseProcessMasterVO = itemWiseProcessMasterRepo.findById(itemWiseProcessMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("ItemWiseProcessMaster not found"));

			itemWiseProcessMasterVO.setUpdatedBy(itemWiseProcessMasterDTO.getCreatedBy());
			createUpdateProcessMasterVOByProcessMasterDTO(itemWiseProcessMasterDTO, itemWiseProcessMasterVO);
			message = "ItemWiseProcessMaster Updated Successfully";
		} else {

			String docId = itemWiseProcessMasterRepo.getItemWiseProcessMasterDocId(itemWiseProcessMasterDTO.getOrgId(),
					itemWiseProcessMasterDTO.getFinYear(), itemWiseProcessMasterDTO.getBranchCode(), screenCode);
			itemWiseProcessMasterVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(itemWiseProcessMasterDTO.getOrgId(),
							itemWiseProcessMasterDTO.getFinYear(), itemWiseProcessMasterDTO.getBranchCode(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			itemWiseProcessMasterVO.setCreatedBy(itemWiseProcessMasterDTO.getCreatedBy());
			itemWiseProcessMasterVO.setUpdatedBy(itemWiseProcessMasterDTO.getCreatedBy());
			createUpdateProcessMasterVOByProcessMasterDTO(itemWiseProcessMasterDTO, itemWiseProcessMasterVO);
			message = "ItemWiseProcessMaster Created Successfully";
		}

		itemWiseProcessMasterRepo.save(itemWiseProcessMasterVO);
		commonNotificationService.generateNotification(itemWiseProcessMasterVO.getScreenCode(), itemWiseProcessMasterVO.getId(), oldItemWiseProcessMaster, itemWiseProcessMasterVO);	

//		createUpdateITEMWISEPROCESSMasterNotification(itemWiseProcessMasterVO, itemWiseProcessMasterDTO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("itemWiseProcessMasterVO", itemWiseProcessMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateProcessMasterVOByProcessMasterDTO(@Valid ItemWiseProcessMasterDTO itemWiseProcessMasterDTO,
			ItemWiseProcessMasterVO itemWiseProcessMasterVO) {
		itemWiseProcessMasterVO.setProcessType(itemWiseProcessMasterDTO.getProcessType());
		itemWiseProcessMasterVO.setItem(itemWiseProcessMasterDTO.getItem());
		itemWiseProcessMasterVO.setItemDesc(itemWiseProcessMasterDTO.getItemDesc());
		itemWiseProcessMasterVO.setOrgId(itemWiseProcessMasterDTO.getOrgId());
		itemWiseProcessMasterVO.setFinYear(itemWiseProcessMasterDTO.getFinYear());
		itemWiseProcessMasterVO.setBranch(itemWiseProcessMasterDTO.getBranch());
		itemWiseProcessMasterVO.setBranchCode(itemWiseProcessMasterDTO.getBranchCode());

		if (itemWiseProcessMasterDTO.getId() != null) {
			List<ItemWiseProcessDetailsVO> itemWiseProcessDetailsVOs = itemWiseProcessDetailsRepo
					.findByItemWiseProcessMasterVO(itemWiseProcessMasterVO);
			itemWiseProcessDetailsRepo.deleteAll(itemWiseProcessDetailsVOs);
		}
		// Handling ItemPriceSlabVO
		List<ItemWiseProcessDetailsVO> itemWiseProcessDetailsVOs = new ArrayList<>();
		for (ItemWiseProcessDetailsDTO itemWiseProcessDetailsDTO : itemWiseProcessMasterDTO
				.getItemWiseProcessDetailsDTO()) {
			ItemWiseProcessDetailsVO itemWiseProcessDetailsVO = new ItemWiseProcessDetailsVO();
			itemWiseProcessDetailsVO.setProcessName(itemWiseProcessDetailsDTO.getProcessName());
			itemWiseProcessDetailsVO.setItemWiseProcessMasterVO(itemWiseProcessMasterVO); // Set the reference in child
																							// entity
			itemWiseProcessDetailsVOs.add(itemWiseProcessDetailsVO);
		}
		itemWiseProcessMasterVO.setItemWiseProcessDetailsVO(itemWiseProcessDetailsVOs);

	}

//	private void createUpdateITEMWISEPROCESSMasterNotification(ItemWiseProcessMasterVO itemWiseProcessMasterVO,
//			ItemWiseProcessMasterDTO itemWiseProcessMasterDTO) {
//
//		String msg;
//		if (itemWiseProcessMasterDTO.getId() != null) {
//			msg = " ITEMWISEPROCESS is Updated that Item : " + itemWiseProcessMasterVO.getItem();
//		} else {
//			msg = "New ITEMWISEPROCESS is Created that Item  : " + itemWiseProcessMasterVO.getItem();
//		}
//
//		NotificationDesignationDetailsVO detailsVO = notificationDesignationDetailsRepo
//				.findByScreenCode(itemWiseProcessMasterVO.getScreenCode());
//
//		if (detailsVO == null) {
//			throw new RuntimeException("No record found for screenCode: " + itemWiseProcessMasterVO.getScreenCode());
//		}
//
//		NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();
//
//		String codes = headerVO.getDesignationcode();
//		String names = headerVO.getDesignationname();
//
//		List<String> codeList = Arrays.asList(codes.split(","));
//		List<String> nameList = Arrays.asList(names.split(","));
//
//		if (codeList.size() != nameList.size()) {
//			throw new RuntimeException("Mismatch in designation data");
//		}
//
//		// Step 1: Get employees
//		List<EmployeeMasterVO> employees = employeeMasterRepo.findByDesignationIn(nameList);
//
//		// Step 2: Get employeeCodes
//		List<String> employeeCodes = employees.stream().map(EmployeeMasterVO::getEmployeeCode).toList();
//
//		// Step 3: Get userIds
//		List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//		if (userIds == null || userIds.isEmpty()) {
//			throw new RuntimeException("No users found for given employee codes");
//		}
//
//		// ✅ Step 4: Save notification for each user
//		for (Long userId : userIds) {
//
//			NotificationVO n = new NotificationVO();
//
//			n.setUserid(userId);
//			n.setMessage(msg);
//			n.setNotificationType(itemWiseProcessMasterVO.getScreenName());
//
//			notificationRepo.save(n);
//		}
//	}

	@Override
	public String getItemWiseProcessMasterDocId(Long orgId, String finyear, String branchCode) {
		String screenCode = "IPM";
		String result = itemWiseProcessMasterRepo.getItemWiseProcessMasterDocId(orgId, finyear, branchCode, screenCode);
		return result;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getItemAndItemDescforItemWiseProcess(Long orgId) {

		Set<Object[]> result = itemWiseProcessMasterRepo.findItemAndItemDescforItemWiseProcess(orgId);
		return getItemAndItemDescforItemWiseProcess(result);
	}

	private List<Map<String, Object>> getItemAndItemDescforItemWiseProcess(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("itemName", fs[0] != null ? fs[0].toString() : "");
			part.put("itemDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("id", fs[2] != null ? Integer.parseInt(fs[2].toString()) : 0);

			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getProcessNameFromItemWiseProcess(Long orgId) {

		Set<Object[]> result = itemWiseProcessMasterRepo.findProcessNameFromItemWiseProcess(orgId);
		return getProcessNameFromItemWiseProcess(result);
	}

	private List<Map<String, Object>> getProcessNameFromItemWiseProcess(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("processName", fs[0] != null ? fs[0].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	// Department
	@Override
	public Map<String, Object> createUpdateDepartment(DepartmentDTO departmentDTO) throws ApplicationException {
		DepartmentVO departmentVO = new DepartmentVO();
		String message;
		String screenCode = "DEPT";
		if (ObjectUtils.isNotEmpty(departmentDTO.getId())) {
			departmentVO = departmentRepo.findById(departmentDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Department details"));

			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			if (!departmentVO.getDepartmentName().equalsIgnoreCase(departmentDTO.getDepartmentName())) {
				if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("The DepartmentName: %s already exists in This Organization.",
							departmentDTO.getDepartmentName());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
			}

			if (!departmentVO.getDepartmentCode().equalsIgnoreCase(departmentDTO.getDepartmentCode())) {
				if (departmentRepo.existsByDepartmentCodeAndOrgId(departmentDTO.getDepartmentCode(),
						departmentDTO.getOrgId())) {
					String errorMessage = String.format("The DepartmentCode: %s already exists in This Organization.",
							departmentDTO.getDepartmentCode());
					throw new ApplicationException(errorMessage);
				}
				departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
			}
			message = "Department Updated Successfully";
		} else {

			if (departmentRepo.existsByDepartmentNameAndOrgId(departmentDTO.getDepartmentName(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentName : %s already exists in This Organization.",
						departmentDTO.getDepartmentName());
				throw new ApplicationException(errorMessage);
			}
			if (departmentRepo.existsByDepartmentCodeAndOrgId(departmentDTO.getDepartmentCode(),
					departmentDTO.getOrgId())) {
				String errorMessage = String.format("The DepartmentCode: %s already exists in This Organization.",
						departmentDTO.getDepartmentCode());
				throw new ApplicationException(errorMessage);
			}
			String docId = departmentRepo.getDepartmentDocId(departmentDTO.getOrgId(), departmentDTO.getFinYear(),
					departmentDTO.getBranchCode(), screenCode);
			departmentVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(departmentDTO.getOrgId(),
							departmentDTO.getFinYear(), departmentDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
			departmentVO.setUpdatedBy(departmentDTO.getCreatedBy());
			message = "Department Created Successfully";
		}

		createUpdateDepartmentVOByDepartmentDTO(departmentDTO, departmentVO);
		departmentRepo.save(departmentVO);
		Map<String, Object> response = new HashMap<>();
		response.put("departmentVO", departmentVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDepartmentVOByDepartmentDTO(DepartmentDTO departmentDTO, DepartmentVO departmentVO) {
		departmentVO.setDepartmentName(departmentDTO.getDepartmentName().toUpperCase());
		departmentVO.setDepartmentCode(departmentDTO.getDepartmentCode().toUpperCase());
		departmentVO.setOrgId(departmentDTO.getOrgId());
		departmentVO.setFinYear(departmentDTO.getFinYear());
		departmentVO.setBranch(departmentDTO.getBranch());
		departmentVO.setBranchCode(departmentDTO.getBranchCode());
		departmentVO.setCreatedBy(departmentDTO.getCreatedBy());
		departmentVO.setActive(departmentDTO.isActive());

	}

	@Override
	public String getDepartmentDocId(Long orgId, String finyear, String branchCode) {
		String screenCode = "DEPT";
		String result = departmentRepo.getDepartmentDocId(orgId, finyear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<DepartmentVO> getAllDepartmentByOrgId(Long orgId, String branchCode) {
		List<DepartmentVO> departmentVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Department BY OrgId : {}", orgId);
			departmentVO = departmentRepo.getAllDepartmentByOrgId(orgId, branchCode);
		}
		return departmentVO;
	}

	@Override
	public List<DepartmentVO> getDepartmentById(Long id) {
		List<DepartmentVO> departmentVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Department BY Id : {}", id);
			departmentVO = departmentRepo.getDepartmentById(id);
		}
		return departmentVO;
	}

	// GST

	@Override
	public Map<String, Object> createUpdateGst(GstDTO gstDTO) throws ApplicationException {
		GstVO gstVO = new GstVO();
		String message;
		if (ObjectUtils.isNotEmpty(gstDTO.getId())) {
			gstVO = gstRepo.findById(gstDTO.getId()).orElseThrow(() -> new ApplicationException("Invalid Gst details"));
			message = "Gst Updated Successfully";
			gstVO.setUpdatedBy(gstDTO.getCreatedBy());
			if (!gstVO.getGstSlab().equalsIgnoreCase(gstDTO.getGstSlab())) {
				if (gstRepo.existsByGstSlabAndOrgId(gstDTO.getGstSlab(), gstDTO.getOrgId())) {
					String errorMessage = String.format("The GstSlab : %s already exists in This Organization.",
							gstDTO.getGstSlab());
					throw new ApplicationException(errorMessage);
				}
				gstVO.setGstSlab(gstDTO.getGstSlab().toUpperCase());
			}

		} else {

			if (gstRepo.existsByGstSlabAndOrgId(gstDTO.getGstSlab(), gstDTO.getOrgId())) {
				String errorMessage = String.format("The GstSlab : %s already exists in This Organization.",
						gstDTO.getGstSlab());
				throw new ApplicationException(errorMessage);
			}
			gstVO.setCreatedBy(gstDTO.getCreatedBy());
			gstVO.setUpdatedBy(gstDTO.getCreatedBy());

			message = "Gst Created Successfully";
		}
		createUpdateGstVOByGstDTO(gstDTO, gstVO);
		gstRepo.save(gstVO);
		Map<String, Object> response = new HashMap<>();
		response.put("gstVO", gstVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGstVOByGstDTO(GstDTO gstDTO, GstVO gstVO) {
		gstVO.setGstSlab(gstDTO.getGstSlab().toUpperCase());
		gstVO.setGstPercentage(gstDTO.getGstPercentage());
		gstVO.setIgstPercentage(gstDTO.getIgstPercentage());
		gstVO.setCgstPercentage(gstDTO.getCgstPercentage());
		gstVO.setSgstPercentage(gstDTO.getSgstPercentage());
		gstVO.setOrgId(gstDTO.getOrgId());
		gstVO.setCreatedBy(gstDTO.getCreatedBy());
		gstVO.setActive(gstDTO.isActive());
	}

	@Override
	public List<GstVO> getAllGstByOrgId(Long orgId) {
		List<GstVO> gstVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  Gst BY OrgId : {}", orgId);
			gstVO = gstRepo.getAllGstByOrgId(orgId);
		}
		return gstVO;
	}

	@Override
	public List<GstVO> getGstById(Long id) {
		List<GstVO> gstVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  Gst BY Id : {}", id);
			gstVO = gstRepo.getGstById(id);
		}
		return gstVO;
	}

	// ProcessMaster

	@Override
	public Map<String, Object> createUpdateProcessMaster(ProcessMasterDTO processMasterDTO)
			throws ApplicationException {
		ProcessMasterVO processMasterVO = new ProcessMasterVO();
		String message;
		String screenCode = "PM";
		if (ObjectUtils.isNotEmpty(processMasterDTO.getId())) {
			processMasterVO = processMasterRepo.findById(processMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid ProcessMaster details"));
			message = "ProcessMaster Updated Successfully";
			processMasterVO.setUpdatedBy(processMasterDTO.getCreatedBy());
			String existingName = processMasterVO.getProcessName().replaceAll("\\s+", "").trim();
			String newName = processMasterDTO.getProcessName().replaceAll("\\s+", "").trim();

			if (!existingName.equalsIgnoreCase(newName)) {

				if (processMasterRepo.existsByProcessNameAndOrgId(
						processMasterDTO.getProcessName().replaceAll("\\s+", "").trim(), processMasterDTO.getOrgId())) {

					String errorMessage = String.format("The ProcessName : %s already exists in This Organization.",
							processMasterDTO.getProcessName().trim());

					throw new ApplicationException(errorMessage);
				}

				processMasterVO
						.setProcessName(processMasterDTO.getProcessName().replaceAll("\\s+", "").trim().toUpperCase());
			}
		} else {
			String processName = processMasterDTO.getProcessName().replaceAll("\\s+", "").trim().toUpperCase();

			if (processMasterRepo.existsByProcessNameAndOrgId(processName, processMasterDTO.getOrgId())) {

				String errorMessage = String.format("The ProcessName : %s already exists in This Organization.",
						processMasterDTO.getProcessName());

				throw new ApplicationException(errorMessage);
			}

			String docId = processMasterRepo.getProcessMasterDocId(processMasterDTO.getOrgId(),
					processMasterDTO.getFinYear(), processMasterDTO.getBranchCode(), screenCode);
			processMasterVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(processMasterDTO.getOrgId(),
							processMasterDTO.getFinYear(), processMasterDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			processMasterVO.setCreatedBy(processMasterDTO.getCreatedBy());
			processMasterVO.setUpdatedBy(processMasterDTO.getCreatedBy());

			message = "ProcessMaster Created Successfully";
		}
		createUpdatedProcessMasterVOFromProcessMasterDTO(processMasterDTO, processMasterVO);
		processMasterRepo.save(processMasterVO);
		Map<String, Object> response = new HashMap<>();
		response.put("processMasterVO", processMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedProcessMasterVOFromProcessMasterDTO(ProcessMasterDTO processMasterDTO,
			ProcessMasterVO processMasterVO) {
		processMasterVO.setProcessName(processMasterDTO.getProcessName().toUpperCase());
		processMasterVO.setCreatedBy(processMasterDTO.getCreatedBy());
		processMasterVO.setOrgId(processMasterDTO.getOrgId());
		processMasterVO.setActive(processMasterDTO.isActive());
		processMasterVO.setBranch(processMasterDTO.getBranch());
		processMasterVO.setBranchCode(processMasterDTO.getBranchCode());
		processMasterVO.setFinYear(processMasterDTO.getFinYear());

	}

	@Override
	public String getProcessMasterDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "PM";
		String result = processMasterRepo.getProcessMasterDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<ProcessMasterVO> getAllProcessMasterByOrgId(Long orgId, String branchCode) {
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  ProcessMaster BY OrgId : {}", orgId);
			processMasterVO = processMasterRepo.getAllProcessMasterByOrgId(orgId, branchCode);
		}
		return processMasterVO;
	}

	@Override
	public List<ProcessMasterVO> getProcessMasterById(Long id) {
		List<ProcessMasterVO> processMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  ProcessMaster BY Id : {}", id);
			processMasterVO = processMasterRepo.getProcessMasterById(id);
		}
		return processMasterVO;
	}

	// Material Type

	@Override
	public Map<String, Object> createUpdateMaterialType(MaterialTypeDTO materialTypeDTO) throws ApplicationException {
		MaterialTypeVO materialTypeVO = new MaterialTypeVO();
		String message;
		if (ObjectUtils.isNotEmpty(materialTypeDTO.getId())) {
			materialTypeVO = materialTypeRepo.findById(materialTypeDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid MaterialType Details"));
			materialTypeVO.setUpdatedBy(materialTypeDTO.getCreatedBy());

//			if (!materialTypeVO.getItemGroup().equalsIgnoreCase(materialTypeDTO.getItemGroup())) {
//				if (materialTypeRepo.existsByItemGroupAndOrgId(materialTypeDTO.getItemGroup(),
//						materialTypeDTO.getOrgId())) {
//					String errorMessage = String.format("The ItemGroup: %s already exists in this Organization!",
//							materialTypeDTO.getItemGroup());
//					throw new ApplicationException(errorMessage);
//				}
//				materialTypeVO.setItemGroup(materialTypeDTO.getItemGroup().toUpperCase());
//			}
			message = "MaterialType Updated Successfully";
		} else {
//			if (materialTypeRepo.existsByItemGroupAndOrgId(materialTypeDTO.getItemGroup(),
//					materialTypeDTO.getOrgId())) {
//				String errorMessage = String.format("The ItemGroup: %s already exists in this Organization!",
//						materialTypeDTO.getItemGroup());
//				throw new ApplicationException(errorMessage);
//			}
			materialTypeVO.setCreatedBy(materialTypeDTO.getCreatedBy());
			materialTypeVO.setUpdatedBy(materialTypeDTO.getCreatedBy());
			message = "MaterialType Created Successfully";
		}

		getMaterialTypeVOFromMaterialTypeDTO(materialTypeDTO, materialTypeVO);
		materialTypeRepo.save(materialTypeVO);

		Map<String, Object> response = new HashMap<>();
		response.put("materialTypeVO", materialTypeVO);
		response.put("message", message);
		return response;
	}

	private void getMaterialTypeVOFromMaterialTypeDTO(MaterialTypeDTO materialTypeDTO, MaterialTypeVO materialTypeVO)
			throws ApplicationException {

		materialTypeVO.setMaterialType(materialTypeDTO.getMaterialType().toUpperCase());
		materialTypeVO.setItemGroup(materialTypeDTO.getItemGroup().toUpperCase());
		materialTypeVO.setOrgId(materialTypeDTO.getOrgId());
		materialTypeVO.setCreatedBy(materialTypeDTO.getCreatedBy());

		boolean isCreate = ObjectUtils.isEmpty(materialTypeDTO.getId());

		List<MaterialTypeDetailsVO> materialTypeDetailsVOs = new ArrayList<>();

		for (MaterialTypeDetailsDTO dto : materialTypeDTO.getMaterialTypeDetailDTO()) {

			String itemSubGroup = dto.getItemSubGroup().toUpperCase();

			// ✅ DUPLICATE CHECK
			if (isCreate) {
				// CREATE → full DB check
				if (materialTypeDetailRepo
						.existsByMaterialTypeVO_OrgIdAndMaterialTypeVO_MaterialTypeAndMaterialTypeVO_ItemGroupAndItemSubGroupIgnoreCase(
								materialTypeDTO.getOrgId(), materialTypeDTO.getMaterialType().toUpperCase(),
								materialTypeDTO.getItemGroup().toUpperCase(), itemSubGroup)) {

					throw new ApplicationException("Duplicate ItemSubGroup not allowed: " + itemSubGroup);
				}
			} else {
				// UPDATE → exclude same PARENT (MaterialTypeVO) ID
				if (materialTypeDetailRepo
						.existsByMaterialTypeVO_OrgIdAndMaterialTypeVO_MaterialTypeAndMaterialTypeVO_ItemGroupAndItemSubGroupIgnoreCaseAndMaterialTypeVO_IdNot(
								materialTypeDTO.getOrgId(), materialTypeDTO.getMaterialType().toUpperCase(),
								materialTypeDTO.getItemGroup().toUpperCase(), itemSubGroup, materialTypeDTO.getId() // ✅
																													// parent
																													// id
						)) {

					throw new ApplicationException("Duplicate ItemSubGroup not allowed: " + itemSubGroup);
				}

			}

			MaterialTypeDetailsVO vo = new MaterialTypeDetailsVO();
			vo.setItemSubGroup(itemSubGroup);
			vo.setMaterialTypeVO(materialTypeVO);

			materialTypeDetailsVOs.add(vo);
		}

		// 🔁 Update case: remove old details after validation
		if (!isCreate) {
			List<MaterialTypeDetailsVO> oldDetails = materialTypeDetailRepo.findByMaterialTypeVO(materialTypeVO);
			materialTypeDetailRepo.deleteAll(oldDetails);
		}

		materialTypeVO.setMaterialTypeDetailsVO(materialTypeDetailsVOs);
	}

	@Override
	public List<MaterialTypeVO> getAllMaterialTypeByOrgId(Long orgId) {
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  MaterialType BY OrgId : {}", orgId);
			materialTypeVO = materialTypeRepo.getAllMaterialTypeByOrgId(orgId);
		}
		return materialTypeVO;
	}

	@Override
	public List<MaterialTypeVO> getMaterialTypeById(Long id) {
		List<MaterialTypeVO> materialTypeVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  MaterialType BY Id : {}", id);
			materialTypeVO = materialTypeRepo.getMaterialTypeById(id);
		}
		return materialTypeVO;
	}

	@Override
	public List<DesignationVO> getDesignationByOrgId(Long orgId, String branchCode) {
		List<DesignationVO> designationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received ArapAdjustments BY OrgId : {}", orgId);
			designationVO = designationrepo.getDesignationByOrgId(orgId, branchCode);
		}
		return designationVO;
	}

	@Override
	public List<DesignationVO> getDesignationById(Long id) {
		List<DesignationVO> designationVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received ArapAdjustments BY Id : {}", id);
			designationVO = designationrepo.getDesignationById(id);
		}
		return designationVO;
	}

	@Override
	public Map<String, Object> updateCreateDesignation(@Valid DesignationDTO designationDTO)
			throws ApplicationException {
		String screenCode = "DSG";
		DesignationVO designationVO = new DesignationVO();
		String message;
		if (ObjectUtils.isNotEmpty(designationDTO.getId())) {
			designationVO = designationrepo.findById(designationDTO.getId())
					.orElseThrow(() -> new ApplicationException("Designation not found"));

			designationVO.setUpdatedBy(designationDTO.getCreatedBy());
			if (!designationVO.getDesignation().equalsIgnoreCase(designationDTO.getDesignation())) {
				if (designationrepo.existsByDesignationAndOrgId(designationDTO.getDesignation(),
						designationDTO.getOrgId())) {
					String errorMessage = String.format("The Designation: %s already exists This Organization.",
							designationDTO.getDesignation());
					throw new ApplicationException(errorMessage);
				}
				designationVO.setDesignation(designationDTO.getDesignation().toUpperCase());
			}
			if (!designationVO.getDesignationCode().equalsIgnoreCase(designationDTO.getDesignationCode())) {
				if (designationrepo.existsByDesignationCodeAndOrgId(designationDTO.getDesignationCode(),
						designationDTO.getOrgId())) {
					String errorMessage = String.format("The DesignationCode: %s already exists This Organization.",
							designationDTO.getDesignationCode());
					throw new ApplicationException(errorMessage);
				}
				designationVO.setDesignationCode(designationDTO.getDesignationCode().toUpperCase());

			}
			message = "Designation  Updated Successfully";
		} else {
			createUpdateDesignationVOByDesignationDTO(designationDTO, designationVO);
			String docId = designationrepo.getDesignationDocId(designationDTO.getOrgId(), designationDTO.getFinYear(),
					designationDTO.getBranchCode(), screenCode);
			designationVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(designationDTO.getOrgId(),
							designationDTO.getFinYear(), designationDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			if (designationrepo.existsByDesignationAndDesignationCodeAndOrgId(designationDTO.getDesignation(),
					designationDTO.getDesignationCode(), designationDTO.getOrgId())) {
				String errorMessage = String.format(
						"The Designation: %s and DesignationCode: %s already exists This Organization.",
						designationDTO.getDesignation(), designationDTO.getDesignationCode());
				throw new ApplicationException(errorMessage);
			}

			if (designationrepo.existsByDesignationAndOrgId(designationDTO.getDesignation(),
					designationDTO.getOrgId())) {
				String errorMessage = String.format("The Designation: %s already exists This Organization.",
						designationDTO.getDesignation());
				throw new ApplicationException(errorMessage);
			}

			if (designationrepo.existsByDesignationCodeAndOrgId(designationDTO.getDesignationCode(),
					designationDTO.getOrgId())) {
				String errorMessage = String.format("The DesignationCode: %s already exists This Organization.",
						designationDTO.getDesignationCode());
				throw new ApplicationException(errorMessage);
			}

			designationVO.setCreatedBy(designationDTO.getCreatedBy());
			designationVO.setUpdatedBy(designationDTO.getCreatedBy());
			message = "Designation Created Successfully";
		}

		designationrepo.save(designationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("designationVO", designationVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDesignationVOByDesignationDTO(@Valid DesignationDTO designationDTO,
			DesignationVO designationVO) throws ApplicationException {
		designationVO.setDesignation(designationDTO.getDesignation().toUpperCase());
		designationVO.setDesignationCode(designationDTO.getDesignationCode().toUpperCase());
		designationVO.setOrgId(designationDTO.getOrgId());
		designationVO.setFinYear(designationDTO.getFinYear());
		designationVO.setBranch(designationDTO.getBranch());
		designationVO.setBranchCode(designationDTO.getBranchCode());
		designationVO.setActive(designationDTO.isActive());

	}

	@Override
	public String getDesignationDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "DSG";
		String result = designationrepo.getDesignationDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public List<UomVO> getUomByOrgId(Long orgId) {
		List<UomVO> uomVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Uom BY OrgId : {}", orgId);
			uomVO = uomrepo.getUomByOrgId(orgId);
		}
		return uomVO;
	}

	@Override
	public List<UomVO> getUomById(Long id) {
		List<UomVO> uomVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Uom BY Id : {}", id);
			uomVO = uomrepo.getUomById(id);
		}
		return uomVO;
	}

	@Override
	public Map<String, Object> updateCreateUom(@Valid UomDTO uomDTO) throws ApplicationException {
		String screenCode = "D";
		UomVO uomVO = new UomVO();
		String message;
		if (ObjectUtils.isNotEmpty(uomDTO.getId())) {
			uomVO = uomrepo.findById(uomDTO.getId()).orElseThrow(() -> new ApplicationException("Uom not found"));

			if (!uomVO.getUomCode().toUpperCase().equalsIgnoreCase(uomDTO.getUomCode().toUpperCase())) {
				if (uomrepo.existsByUomCodeAndOrgId(uomDTO.getUomCode(), uomDTO.getOrgId())) {
					String errorMessage = String.format("This UomCode: %s Already Exists in This Organization",
							uomDTO.getUomCode());
					throw new ApplicationException(errorMessage);
				}
				uomVO.setUomCode(uomDTO.getUomCode().toUpperCase());
			}

			if (!uomVO.getUomDesc().toUpperCase().equalsIgnoreCase(uomDTO.getUomDesc().toUpperCase())) {
				if (uomrepo.existsByUomDescAndOrgId(uomDTO.getUomDesc(), uomDTO.getOrgId())) {
					String errorMessage = String.format("This UomCode: %s Already Exists in This Organization",
							uomDTO.getUomDesc());
					throw new ApplicationException(errorMessage);
				}
				uomVO.setUomCode(uomDTO.getUomDesc().toUpperCase());
			}

			uomVO.setUpdatedBy(uomDTO.getCreatedBy());
			createUpdateUomVOByUomDTO(uomDTO, uomVO);
			message = "Uom  Updated Successfully";
		} else {

			if (uomrepo.existsByUomCodeAndOrgId(uomDTO.getUomCode(), uomDTO.getOrgId())) {
				String errorMessage = String.format("The UomCode: %s  already exists This Organization.",
						uomDTO.getUomCode());
				throw new ApplicationException(errorMessage);
			}

			if (uomrepo.existsByUomDescAndOrgId(uomDTO.getUomDesc(), uomDTO.getOrgId())) {
				String errorMessage = String.format("The UomDesc: %s  already exists This Organization.",
						uomDTO.getUomCode());
				throw new ApplicationException(errorMessage);
			}

			uomVO.setCreatedBy(uomDTO.getCreatedBy());
			uomVO.setUpdatedBy(uomDTO.getCreatedBy());
			createUpdateUomVOByUomDTO(uomDTO, uomVO);
			message = "Uom Created Successfully";
		}

		uomrepo.save(uomVO);
		Map<String, Object> response = new HashMap<>();
		response.put("uomVO", uomVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateUomVOByUomDTO(@Valid UomDTO uomDTO, UomVO uomVO) throws ApplicationException {
		uomVO.setUomCode(uomDTO.getUomCode().toUpperCase());
		uomVO.setUomDesc(uomDTO.getUomDesc());
		uomVO.setOrgId(uomDTO.getOrgId());
		uomVO.setActive(uomDTO.isActive());

	}
	// shift master

	@Override
	public List<ShiftVO> getShiftByOrgId(Long orgId) {
		List<ShiftVO> shiftVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received Uom BY OrgId : {}", orgId);
			shiftVO = shiftRepo.getShiftByOrgId(orgId);
		}
		return shiftVO;
	}

	@Override
	public List<ShiftVO> getShiftById(Long id) {
		List<ShiftVO> shiftVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received Shift BY Id : {}", id);
			shiftVO = shiftrepo.getShiftById(id);
		}
		return shiftVO;
	}

	@Override
	public Map<String, Object> updateCreateShift(ShiftDTO shiftdto) throws ApplicationException {
		String screenCode = "D";
		ShiftVO shiftVO = new ShiftVO();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		String message;
		if (ObjectUtils.isNotEmpty(shiftdto.getId())) {
			shiftVO = shiftrepo.findById(shiftdto.getId())
					.orElseThrow(() -> new ApplicationException("SHIFT not found"));

			if (!shiftVO.getShiftName().equalsIgnoreCase(shiftdto.getShiftName())) {
				if (shiftrepo.existsByShiftCodeAndOrgId(shiftdto.getShiftName(), shiftdto.getOrgId())) {
					String errorMessage = String.format("The ShiftName: %s  already exists This Organization.",
							shiftdto.getShiftName());
					throw new ApplicationException(errorMessage);
				}
			}

			List<ShiftDetailsVO> shiftDetailsVOs = shiftDetailsRepo.findByShiftVO(shiftVO);
			shiftDetailsRepo.deleteAll(shiftDetailsVOs);

			List<ShiftBreakTimingDetailsVO> ShiftBreakTimingDetailsVOs = shiftBreakTimingDetailsRepo
					.findByshiftVO(shiftVO);
			shiftBreakTimingDetailsRepo.deleteAll(ShiftBreakTimingDetailsVOs);

			shiftVO.setUpdatedBy(shiftdto.getCreatedBy());
			createUpdateShiftVOByShiftDTO(shiftdto, shiftVO);
			message = "Shift  Updated Successfully";
		} else {

			if (shiftrepo.existsByShiftNameAndOrgId(shiftdto.getShiftName(), shiftdto.getOrgId())) {
				String errorMessage = String.format("The Shift: %s  already exists This Organization.",
						shiftdto.getShiftName());
				throw new ApplicationException(errorMessage);
			}
			shiftVO.setCreatedBy(shiftdto.getCreatedBy());
			shiftVO.setUpdatedBy(shiftdto.getCreatedBy());
			createUpdateShiftVOByShiftDTO(shiftdto, shiftVO);
			message = "Shift Created Successfully";
		}

		shiftrepo.save(shiftVO);
		Map<String, Object> response = new HashMap<>();
		response.put("shiftVO", shiftVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateShiftVOByShiftDTO(@Valid ShiftDTO shiftDTO, ShiftVO shiftVO) throws ApplicationException {

		if (shiftDTO.getFromHour() != null) {
			shiftVO.setFromHour(shiftDTO.getFromHour().toLocalTime());
		}
		if (shiftDTO.getToHour() != null) {
			shiftVO.setToHour(shiftDTO.getToHour().toLocalTime());
		}

		shiftVO.setTiming(shiftDTO.getTiming());

		shiftVO.setShiftName(shiftDTO.getShiftName());
		shiftVO.setShiftType(shiftDTO.getShiftType());
		shiftVO.setShiftCode(shiftDTO.getShiftCode());
		shiftVO.setOrgId(shiftDTO.getOrgId());
		shiftVO.setActive(shiftDTO.isActive());

		// Map shift details
		List<ShiftDetailsVO> shiftDetailsVOs = new ArrayList<>();
		for (ShiftDetailsDTO shiftDetailsDTO : shiftDTO.getShiftDetailsDTO()) {
			ShiftDetailsVO shiftDetailsVO = new ShiftDetailsVO();

			if (shiftDetailsDTO.getTimingInHours() != null) {
				shiftDetailsVO.setTimingInHours(shiftDetailsDTO.getTimingInHours());
			}

			// Set the reference in the child entity
			shiftDetailsVO.setShiftVO(shiftVO);
			shiftDetailsVOs.add(shiftDetailsVO);
		}
		shiftVO.setShiftDetailsVO(shiftDetailsVOs);

		List<ShiftBreakTimingDetailsVO> shiftBreakTimingDetailsVOs = new ArrayList<>();

		for (ShiftBreakTimingDetailsDTO shiftBreakTimingDetailsDTO : shiftDTO.getShiftBreakTimingDetailsDTO()) {

			ShiftBreakTimingDetailsVO shiftBreakTimingDetailsVO = new ShiftBreakTimingDetailsVO();

			shiftBreakTimingDetailsVO.setBreakCategory(shiftBreakTimingDetailsDTO.getBreakCategory());
			shiftBreakTimingDetailsVO.setBreakTimings(shiftBreakTimingDetailsDTO.getBreakTimings());
			shiftBreakTimingDetailsVO.setShiftVO(shiftVO);
			shiftBreakTimingDetailsVOs.add(shiftBreakTimingDetailsVO);
		}
		shiftVO.setShiftBreakTimingDetailsVO(shiftBreakTimingDetailsVOs);
	}

	@Override
	public List<RackMasterVO> getRackMasterByOrgId(Long orgId) {
		List<RackMasterVO> rackMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received RackMaster BY OrgId : {}", orgId);
			rackMasterVO = rackMasterRepo.getRackMasterByOrgId(orgId);
		}
		return rackMasterVO;
	}

	@Override
	public List<RackMasterVO> getRackMasterById(Long id) {
		List<RackMasterVO> rackMasterVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received RackMaster BY Id : {}", id);
			rackMasterVO = rackMasterRepo.getRackMasterById(id);
		}
		return rackMasterVO;
	}

	@Override
	public Map<String, Object> updateCreateRackMaster(@Valid RackMasterDTO rackMasterDTO) throws ApplicationException {
		String message;

		RackMasterVO rackMasterVO = new RackMasterVO();

		if (rackMasterDTO.getId() != null) {

			rackMasterVO = rackMasterRepo.findById(rackMasterDTO.getId())
					.orElseThrow(() -> new ApplicationException("RackMaster master not found"));
			rackMasterVO.setUpdatedBy(rackMasterDTO.getCreatedBy());
			createUpdateRackMasterVOByRackMasterDTO(rackMasterDTO, rackMasterVO);
			message = "RackMaster Updated Successfully";

		} else {

			rackMasterVO.setCreatedBy(rackMasterDTO.getCreatedBy());
			rackMasterVO.setUpdatedBy(rackMasterDTO.getCreatedBy());
			createUpdateRackMasterVOByRackMasterDTO(rackMasterDTO, rackMasterVO);
			message = "RackMaster Created Successfully";
		}

		rackMasterRepo.save(rackMasterVO);

		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("rackMasterVO", rackMasterVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateRackMasterVOByRackMasterDTO(@Valid RackMasterDTO rackMasterDTO,
			RackMasterVO rackMasterVO) {
		rackMasterVO.setRackNo(rackMasterDTO.getRackNo());
		rackMasterVO.setRackLocation(rackMasterDTO.getRackLocation());
		rackMasterVO.setOrgId(rackMasterDTO.getOrgId());
		rackMasterVO.setActive(rackMasterDTO.isActive());

	}

	// Bom Master

	@Override
	public Map<String, Object> createUpdateBom(BomDTO bomDTO) throws ApplicationException {
		BomVO bomVO = new BomVO();
		BomVO oldBom = null;
		String message = null;
		String screenCode = "BOM";
		if (ObjectUtils.isNotEmpty(bomDTO.getId())) {
			
			oldBom = bomRepo.findById(bomDTO.getId())
		            .orElseThrow(() -> new ApplicationException("BOM master not found"));

			oldBom.getBomDetailsVO().size(); // load
		    entityManager.detach(oldBom); // detach snapshot
			
			bomVO = bomRepo.findById(bomDTO.getId())
					.orElseThrow(() -> new ApplicationException("BOM  detailsNot Found with id: " + bomDTO.getId()));

			List<BomDetailsVO> bomDetailsVO1 = bomDetailsRepo.findByBomVO(bomVO);
			bomDetailsRepo.deleteAll(bomDetailsVO1);

			message = "jobWorkOut Updated Successfully";
			bomVO.setUpdatedBy(bomDTO.getCreatedBy());

		} else {

			String docId = bomRepo.getBomDocId(bomDTO.getOrgId(), bomDTO.getFinYear(), bomDTO.getBranchCode(),
					screenCode);
			bomVO.setDocid(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(bomDTO.getOrgId(), bomDTO.getFinYear(),
							bomDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			bomVO.setCreatedBy(bomDTO.getCreatedBy());
			bomVO.setUpdatedBy(bomDTO.getCreatedBy());

			message = "Bom Created Successfully";
		}
		
		createUpdatedBomVOFromBomDTO(bomDTO, bomVO);

		commonNotificationService.generateNotification(bomVO.getScreenCode(), bomVO.getId(), oldBom, bomVO);

		bomRepo.save(bomVO);
//		createUpdateBOMMasterNotification(bomVO, bomDTO);
//		createUpdateBOMMasterNotification( bomVO , bomDTO);

		Map<String, Object> response = new HashMap<>();
		response.put("bomVO", bomVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatedBomVOFromBomDTO(BomDTO bomDTO, BomVO bomVO) throws ApplicationException {
		bomVO.setProductCode(bomDTO.getProductCode());
		bomVO.setProductName(bomDTO.getProductName());
		bomVO.setProductType(bomDTO.getProductType());
		bomVO.setQty(bomDTO.getQty());
		bomVO.setUom(bomDTO.getUom());
		bomVO.setActive(bomDTO.isActive());
		bomVO.setFinYear(bomDTO.getFinYear());
		bomVO.setBranch(bomDTO.getBranch());
		bomVO.setBranchCode(bomDTO.getBranchCode());

		bomVO.setRevision(bomDTO.isRevision());
		bomVO.setCurrent(bomDTO.isCurrent());
		bomVO.setOrgId(bomDTO.getOrgId());

		List<BomDetailsVO> bomDetailsVOs = new ArrayList<>();
		for (BomDetailsDTO bomDetailsDTO : bomDTO.getBomDetailsDTO()) {
			BomDetailsVO bomDetailsVO = new BomDetailsVO();
			bomDetailsVO.setItemCode(bomDetailsDTO.getItemCode());
			bomDetailsVO.setItemDesc(bomDetailsDTO.getItemDesc());
			bomDetailsVO.setItemType(bomDetailsDTO.getItemType());
			if (bomDetailsDTO.getQty().compareTo(BigDecimal.ZERO) <= 0) {
				throw new ApplicationException("Qty must be greater than zero.");
			} else {
				bomDetailsVO.setQty(bomDetailsDTO.getQty());
			}
			bomDetailsVO.setUom(bomDetailsDTO.getUom());
			bomDetailsVO.setBomVO(bomVO);
			bomDetailsVOs.add(bomDetailsVO);
		}
		bomVO.setBomDetailsVO(bomDetailsVOs);
	}

//	private void createUpdateBOMMasterNotification(BomVO bomVO, BomDTO bomDTO) {
//
//		String msg;
//		if (bomDTO.getId() != null) {
//			msg = " BOM is Updated that Product Name : " + bomVO.getProductName();
//		} else {
//			msg = "New BOM is Created that Product Name  : " + bomVO.getProductName();
//		}
//
//		NotificationDesignationDetailsVO detailsVO = notificationDesignationDetailsRepo
//				.findByScreenCode(bomVO.getScreenCode());
//
//		if (detailsVO == null) {
//			throw new RuntimeException("No record found for screenCode: " + bomVO.getScreenCode());
//		}
//
//		NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();
//
//		String codes = headerVO.getDesignationcode();
//		String names = headerVO.getDesignationname();
//
//		List<String> codeList = Arrays.asList(codes.split(","));
//		List<String> nameList = Arrays.asList(names.split(","));
//
//		if (codeList.size() != nameList.size()) {
//			throw new RuntimeException("Mismatch in designation data");
//		}
//
//		// Step 1: Get employees
//		List<EmployeeMasterVO> employees = employeeMasterRepo.findByDesignationIn(nameList);
//
//		// Step 2: Get employeeCodes
//		List<String> employeeCodes = employees.stream().map(EmployeeMasterVO::getEmployeeCode).toList();
//
//		// Step 3: Get userIds
//		List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//		if (userIds == null || userIds.isEmpty()) {
//			throw new RuntimeException("No users found for given employee codes");
//		}
//
//		// ✅ Step 4: Save notification for each user
//		for (Long userId : userIds) {
//
//			NotificationVO n = new NotificationVO();
//
//			n.setUserid(userId);
//			n.setMessage(msg);
//			n.setNotificationType(bomVO.getScreenName());
//
//			notificationRepo.save(n);
//		}
//	}

//	private void createUpdateBOMMasterNotification(BomVO bomVO ,BomDTO bomDTO) {
//
//		String msg;
//		if(bomDTO.getId() != null) {
//			 msg = " BOM is Updated that Product Name : " + bomVO.getProductName();
//		}else
//		{
//			 msg = "New BOM is Created that Product Name  : " + bomVO.getProductName();
//		}
//
//	    NotificationDesignationDetailsVO detailsVO =
//	        notificationDesignationDetailsRepo.findByScreenCode(bomVO.getScreenCode());
//
//	    if (detailsVO == null) {
//	        throw new RuntimeException("No record found for screenCode: " + bomVO.getScreenCode());
//	    }
//
//	    NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();
//
//	    String codes = headerVO.getDesignationcode();
//	    String names = headerVO.getDesignationname();
//
//	    List<String> codeList = Arrays.asList(codes.split(","));
//	    List<String> nameList = Arrays.asList(names.split(","));
//
//	    if (codeList.size() != nameList.size()) {
//	        throw new RuntimeException("Mismatch in designation data");
//	    }
//
//	    // Step 1: Get employees
//	    List<EmployeeMasterVO> employees =
//	        employeeMasterRepo.findByDesignationIn(nameList);
//
//	    // Step 2: Get employeeCodes
//	    List<String> employeeCodes = employees.stream()
//	            .map(EmployeeMasterVO::getEmployeeCode)
//	            .toList();
//
//	    // Step 3: Get userIds
//	    List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//	    if (userIds == null || userIds.isEmpty()) {
//	        throw new RuntimeException("No users found for given employee codes");
//	    }
//
//	    // ✅ Step 4: Save notification for each user
//	    for (Long userId : userIds) {
//
//	        NotificationVO n = new NotificationVO();
//
//	        n.setUserid(userId);
//	        n.setMessage(msg);
//	        n.setNotificationType(bomVO.getScreenName());
//
//	        notificationRepo.save(n);
//	    }
//	}
	
	@Override
	public String getBomDocId(Long orgId, String finYear, String branchCode) {
		String ScreenCode = "BOM";
		String result = bomRepo.getBomDocId(orgId, finYear, branchCode, ScreenCode);
		return result;
	}

	@Override
	public List<BomVO> getAllBomOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return bomRepo.getAllBomByOrgId(orgId, branchCode);
	}

	@Override
	public List<BomVO> getAllBomId(Long id) {
		// TODO Auto-generated method stub
		return bomRepo.getBomById(id);
	}

	@Override
	public List<Map<String, Object>> getFGSFGPartDetailsForBOM(Long orgId, String productType) {
		Set<Object[]> FgSfg = bomRepo.findFGSFGPartDetails(orgId, productType);
		return getFGSFGPartDetailsForBOM(FgSfg);
	}

	private List<Map<String, Object>> getFGSFGPartDetailsForBOM(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryunit", ch[2] != null ? ch[2].toString() : "");

			List1.add(map);
		}
		return List1;

	}

	@Override
	public List<Map<String, Object>> getSFGItemDetailsForBOM(Long orgId) {
		Set<Object[]> SfgItem = bomRepo.findSFGItemDetails(orgId);
		return getSFGItemDetailsForBOM(SfgItem);
	}

	private List<Map<String, Object>> getSFGItemDetailsForBOM(Set<Object[]> chCode) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chCode) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemname", ch[0] != null ? ch[0].toString() : ""); // Empty string if null
			map.put("itemdesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryunit", ch[2] != null ? ch[2].toString() : "");
			map.put("itemtype", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;

	}

	// EmployeeMaster

	@Override
	@Transactional
	public Map<String, Object> updateCreateEmployeeMaster(EmployeeMasterDTO dto) throws Exception {

		EmployeeMasterVO vo;
		String message;

		EmployeeMasterVO oldEmployee = null;
		// ---------------- UPDATE --------------------
		if (ObjectUtils.isNotEmpty(dto.getId())) {
			
			oldEmployee = employeeMasterRepo.findById(dto.getId())
		            .orElseThrow(() -> new ApplicationException("Employee master not found"));
			
//			initializeAll(oldEmployee);

			
			oldEmployee.getEmployeeDetailsVO();
			oldEmployee.getEmployeePersonalDetailsVO();
			oldEmployee.getEmployeeCommunicationDetailsVO();
			oldEmployee.getEmployeeComplianceDetailsVO();
			oldEmployee.getEmployeeFinanceInformationVO().size();
			oldEmployee.getEmployeeLoanDetailsVO().size();
			oldEmployee.getDocuments().size();
		    entityManager.detach(oldEmployee); // detach snapshot

//			Hibernate.initialize(oldEmployee.getEmployeeDetailsVO());
//			Hibernate.initialize(oldEmployee.getEmployeePersonalDetailsVO());
//			Hibernate.initialize(oldEmployee.getEmployeeCommunicationDetailsVO());
//			Hibernate.initialize(oldEmployee.getEmployeeComplianceDetailsVO());
//
//			Hibernate.initialize(oldEmployee.getEmployeeFinanceInformationVO());
//			Hibernate.initialize(oldEmployee.getEmployeeLoanDetailsVO());
//			Hibernate.initialize(oldEmployee.getDocuments());


			vo = employeeMasterRepo.findById(dto.getId())
					.orElseThrow(() -> new ApplicationException("EmployeeMaster Not Found with id: " + dto.getId()));

			vo.setUpdatedBy(dto.getCreatedBy());
			message = "EmployeeMaster Updated Successfully";

			// Update master fields
			mapEmployeeDTOtoVO(dto, vo);

			// Save master first
			vo = employeeMasterRepo.save(vo);

			// ================= Delete One-to-Many Children ====================
			employeeFinanceRepo.deleteByEmployeeMasterVO(vo);
			employeeFinanceRepo.flush();

			employeeLoanRepo.deleteByEmployeeMasterVO(vo);
			employeeLoanRepo.flush();

		} else {
			// ---------------- CREATE --------------------
			vo = new EmployeeMasterVO();
			vo.setCreatedBy(dto.getCreatedBy());
			vo.setUpdatedBy(dto.getCreatedBy());
			message = "EmployeeMaster Created Successfully";

			mapEmployeeDTOtoVO(dto, vo);

			vo = employeeMasterRepo.save(vo);
		}

		// =====================================================================
		// ========================= ONE-TO-ONE UPDATE ==========================
		// =====================================================================

		// ------------ Employee Details (One-to-One) ------------
		if (dto.getEmployeeDetailsDTO() != null) {

			EmployeeDetailsVO details = employeeDetailsRepo.findByEmployeeMasterVO(vo);

			if (details == null) {
				details = new EmployeeDetailsVO();
				details.setEmployeeMasterVO(vo);
			}

			EmployeeDetailsDTO d = dto.getEmployeeDetailsDTO();
			details.setEmployeeType(d.getEmployeeType());
			details.setDepartment(d.getDepartment());
			details.setDateOfJoining(d.getDateOfJoining());
			if (dto.getDateOfBirth() != null && d.getDateOfJoining() != null
					&& d.getDateOfJoining().isBefore(dto.getDateOfBirth())) {

				throw new ApplicationException("Joining date cannot be earlier than Date of Birth.");
			}

			details.setDateOfLeaving(d.getDateOfLeaving());
			details.setDesignation(d.getDesignation());
			details.setJobLocation(d.getJobLocation());
			details.setMinimumWageCategory(d.getMinimumWageCategory());
			details.setPayCategory(d.getPayCategory());
			details.setPtState(d.getPtState());
			details.setCountry(d.getCountry());

			employeeDetailsRepo.save(details);
		}

		// ------------ Employee Personal Details (One-to-One) ------------
		if (dto.getEmployeePersonalDetailsDTO() != null) {

			EmployeePersonalDetailsVO personal = employeePersonalRepo.findByEmployeeMasterVO(vo);

			if (personal == null) {
				personal = new EmployeePersonalDetailsVO();
				personal.setEmployeeMasterVO(vo);
			}

			EmployeePersonalDetailsDTO p = dto.getEmployeePersonalDetailsDTO();
			personal.setBirthPlace(p.getBirthPlace());
			personal.setReligion(p.getReligion());
			personal.setPassportNo(p.getPassportNo());
			personal.setHomeState(p.getHomeState());
			personal.setNationality(p.getNationality());
			personal.setExpiryDate(p.getExpiryDate());
			personal.setCountryOfOrigin(p.getCountryOfOrigin());
			personal.setPlaceOfIssue(p.getPlaceOfIssue());

			employeePersonalRepo.save(personal);
		}

		// ------------ Employee Communication Details (One-to-One) ------------
		if (dto.getEmployeeCommunicationDetailsDTO() != null) {

			EmployeeCommunicationDetailsVO comm = employeeCommunicationRepo.findByEmployeeMasterVO(vo);

			if (comm == null) {
				comm = new EmployeeCommunicationDetailsVO();
				comm.setEmployeeMasterVO(vo);
			}

			EmployeeCommunicationDetailsDTO c = dto.getEmployeeCommunicationDetailsDTO();
			comm.setAddress(c.getAddress());
			comm.setContactNumber(c.getContactNumber());
			comm.setEmailId(c.getEmailId());
			String email = dto.getEmployeeCommunicationDetailsDTO().getEmailId();

			if (email != null && !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
				throw new ApplicationException("Please enter a valid email address");
			}
			comm.setCity(c.getCity());
			comm.setState(c.getState());
			comm.setCountry(c.getCountry());

			employeeCommunicationRepo.save(comm);
		}

		// ------------ Employee Compliance Details (One-to-One) ------------
		if (dto.getEmployeeComplianceDetailsDTO() != null) {

			EmployeeComplianceDetailsVO comp = employeeComplianceRepo.findByEmployeeMasterVO(vo);

			if (comp == null) {
				comp = new EmployeeComplianceDetailsVO();
				comp.setEmployeeMasterVO(vo);
			}

			EmployeeComplianceDetailsDTO cm = dto.getEmployeeComplianceDetailsDTO();

			comp.setEsiNo(cm.getEsiNo());
			String esiNo = cm.getEsiNo();

			if (esiNo != null && !esiNo.matches("\\d{10}")) {
				throw new ApplicationException("ESI must be 10 digits");
			}

			comp.setUanNo(cm.getUanNo());
			String uanNo = cm.getUanNo();

			if (uanNo != null && !uanNo.matches("\\d{12}")) {
				throw new ApplicationException("UAN must be 12 digits");
			}

			comp.setPt(cm.isPt());
			comp.setInsuranceNumber(cm.getInsuranceNumber());
			comp.setPfNumber(cm.getPfNumber());
			String pfNo = cm.getPfNumber();

			if (pfNo != null && !pfNo.matches("^[A-Za-z0-9]+$")) {
				throw new ApplicationException("Invalid PF Number");
			}

			comp.setEsi(cm.isEsi());

			employeeComplianceRepo.save(comp);
		}

		// =====================================================================
		// ======================== ONE-TO-MANY CREATE ==========================
		// =====================================================================

		// ------------ Finance (One-to-Many) ------------
		if (dto.getEmployeeFinanceInformationDTO() != null && !dto.getEmployeeFinanceInformationDTO().isEmpty()) {

			List<EmployeeFinanceInformationVO> finList = new ArrayList<>();

			for (EmployeeFinanceInformationDTO f : dto.getEmployeeFinanceInformationDTO()) {
				EmployeeFinanceInformationVO fin = new EmployeeFinanceInformationVO();

				fin.setModeOfPayment(f.getModeOfPayment());
				fin.setAccountNumber(f.getAccountNumber());
				fin.setIfscCode(f.getIfscCode());
				fin.setBankName(f.getBankName());
				fin.setBankBranchName(f.getBankBranchName());
				fin.setPayBill(f.getPayBill());
				fin.setDate(f.getDate());
				fin.setEmployeeMasterVO(vo);

				finList.add(fin);
			}

			employeeFinanceRepo.saveAll(finList);
		}

		// ------------ Loan (One-to-Many) ------------
		if (dto.getEmployeeLoanDetailsDTO() != null && !dto.getEmployeeLoanDetailsDTO().isEmpty()) {

			List<EmployeeLoanDetailsVO> loanList = new ArrayList<>();

			for (EmployeeLoanDetailsDTO l : dto.getEmployeeLoanDetailsDTO()) {

				EmployeeLoanDetailsVO loan = new EmployeeLoanDetailsVO();

				loan.setFinYear(l.getFinYear());
				loan.setOpeningBalance(l.getOpeningBalance());
				loan.setJanuary(l.getJanuary());
				loan.setFebruary(l.getFebruary());
				loan.setMarch(l.getMarch());
				loan.setApril(l.getApril());
				loan.setMay(l.getMay());
				loan.setJune(l.getJune());
				loan.setJuly(l.getJuly());
				loan.setAugust(l.getAugust());
				loan.setSeptember(l.getSeptember());
				loan.setOctober(l.getOctober());
				loan.setNovember(l.getNovember());
				loan.setDecember(l.getDecember());
				loan.setEmployeeMasterVO(vo);

				loanList.add(loan);
			}

			employeeLoanRepo.saveAll(loanList);
		}

		// =====================================================================
		// ============================== RESPONSE =============================
		// =====================================================================

		EmployeeMasterVO saved = employeeMasterRepo.findById(vo.getId()).get();

		
//		createUpdateEmployeeMasterNotification( vo , dto);

		createUpdateEmployeeMasterNotification(vo, dto);

		saved.setEmployeeDetailsVO(employeeDetailsRepo.findByEmployeeMasterVO(saved));
		saved.setEmployeePersonalDetailsVO(employeePersonalRepo.findByEmployeeMasterVO(saved));
		saved.setEmployeeCommunicationDetailsVO(employeeCommunicationRepo.findByEmployeeMasterVO(saved));
		saved.setEmployeeComplianceDetailsVO(employeeComplianceRepo.findByEmployeeMasterVO(saved));

		saved.setEmployeeFinanceInformationVO(employeeFinanceRepo.findByEmployeeMasterVO(saved));
		saved.setEmployeeLoanDetailsVO(employeeLoanRepo.findByEmployeeMasterVO(saved));
		
//		commonNotificationService.generateNotification(saved.getScreenCode(), saved.getId(), oldEmployee, saved);


		Map<String, Object> response = new HashMap<>();
		response.put("employeeMasterVO", saved);
		response.put("message", message);

		return response;
	}

	private void mapEmployeeDTOtoVO(EmployeeMasterDTO dto, EmployeeMasterVO vo) throws ApplicationException {

		// Prevent duplicate Employee Code
		if (dto.getEmployeeCode() != null) {

			boolean exists = employeeMasterRepo.existsByEmployeeCodeAndOrgId(dto.getEmployeeCode(), dto.getOrgId());

			if (exists && (vo.getId() == null || !dto.getEmployeeCode().equalsIgnoreCase(vo.getEmployeeCode()))) {

				throw new ApplicationException("Employee Code already exists.");
			}
		}

		vo.setEmployeeCode(dto.getEmployeeCode());

		// MAIN TABLE
		vo.setEmployeeCode(dto.getEmployeeCode());
		vo.setFirstName(dto.getFirstName());
		vo.setLastName(dto.getLastName());
		vo.setEmployeeName(dto.getEmployeeName());
		vo.setFatherName(dto.getFatherName());
		vo.setGender(dto.getGender());
		vo.setBloodGroup(dto.getBloodGroup());
		if (!dto.getBloodGroup().matches("^(A|B|AB|O)[+-]$")) {
			throw new ApplicationException("Invalid Blood Group");
		}
		vo.setSalutation(dto.getSalutation());
		String aadhaar = dto.getAadhaarNo();

		if (aadhaar != null && aadhaar.matches("\\d{12}")) {
			vo.setAadhaarNo(aadhaar);
		} else {
			throw new ApplicationException("Aadhaar number must be exactly 12 digits");
		}
		vo.setDateOfBirth(dto.getDateOfBirth());

		if (dto.getDateOfBirth() != null && dto.getDateOfBirth().isAfter(LocalDate.now())) {
			throw new ApplicationException("Date of Birth cannot be a future date");
		}

		vo.setDateOfBirth(dto.getDateOfBirth());
		vo.setMaritalStatus(dto.getMaritalStatus());
		vo.setOrgId(dto.getOrgId());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setFinYear(dto.getFinYear());
		vo.setActive(dto.isActive());

	}

	private void createUpdateEmployeeMasterNotification(EmployeeMasterVO employeeMasterVO,
			EmployeeMasterDTO employeeMasterDTO) {

		String msg;
		if (employeeMasterDTO.getId() != null) {
			msg = " employeeMaster is Updated that Employee : " + employeeMasterVO.getEmployeeName();
		} else {
			msg = "New employeeMaster is Created that Employee  : " + employeeMasterVO.getEmployeeName();
		}

		NotificationDesignationDetailsVO detailsVO = notificationDesignationDetailsRepo
				.findByScreenCode(employeeMasterVO.getScreenCode());

		if (detailsVO == null) {
			throw new RuntimeException("No record found for screenCode: " + employeeMasterVO.getScreenCode());
		}

		NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();

		String codes = headerVO.getDesignationcode();
		String names = headerVO.getDesignationname();

		List<String> codeList = Arrays.asList(codes.split(","));
		List<String> nameList = Arrays.asList(names.split(","));

		if (codeList.size() != nameList.size()) {
			throw new RuntimeException("Mismatch in designation data");
		}

		// Step 1: Get employees
		List<EmployeeMasterVO> employees = employeeMasterRepo.findByDesignationIn(nameList);

		// Step 2: Get employeeCodes
		List<String> employeeCodes = employees.stream().map(EmployeeMasterVO::getEmployeeCode).toList();

		// Step 3: Get userIds
		List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);

		if (userIds == null || userIds.isEmpty()) {
			throw new RuntimeException("No users found for given employee codes");
		}

		// ✅ Step 4: Save notification for each user
		for (Long userId : userIds) {

			NotificationVO n = new NotificationVO();

			n.setUserid(userId);
			n.setMessage(msg);
			n.setNotificationType(employeeMasterVO.getScreenName());

			notificationRepo.save(n);
		}
	}
	
//	private void createUpdateEmployeeMasterNotification(EmployeeMasterVO employeeMasterVO ,EmployeeMasterDTO employeeMasterDTO) {
//
//		String msg;
//		if(employeeMasterDTO.getId() != null) {
//			 msg = " employeeMaster is Updated that Employee : " + employeeMasterVO.getEmployeeName();
//		}else
//		{
//			 msg = "New employeeMaster is Created that Employee  : " + employeeMasterVO.getEmployeeName();
//		}
//
//	    NotificationDesignationDetailsVO detailsVO =
//	        notificationDesignationDetailsRepo.findByScreenCode(employeeMasterVO.getScreenCode());
//
//	    if (detailsVO == null) {
//	        throw new RuntimeException("No record found for screenCode: " + employeeMasterVO.getScreenCode());
//	    }
//
//	    NotificationDesignationVO headerVO = detailsVO.getNotificationDesignationVO();
//
//	    String codes = headerVO.getDesignationcode();
//	    String names = headerVO.getDesignationname();
//
//	    List<String> codeList = Arrays.asList(codes.split(","));
//	    List<String> nameList = Arrays.asList(names.split(","));
//
//	    if (codeList.size() != nameList.size()) {
//	        throw new RuntimeException("Mismatch in designation data");
//	    }
//
//	    // Step 1: Get employees
//	    List<EmployeeMasterVO> employees =
//	        employeeMasterRepo.findByDesignationIn(nameList);
//
//	    // Step 2: Get employeeCodes
//	    List<String> employeeCodes = employees.stream()
//	            .map(EmployeeMasterVO::getEmployeeCode)
//	            .toList();
//
//	    // Step 3: Get userIds
//	    List<Long> userIds = userRepo.findUserIdsByEmployeeCodes(employeeCodes);
//
//	    if (userIds == null || userIds.isEmpty()) {
//	        throw new RuntimeException("No users found for given employee codes");
//	    }
//
//	    // ✅ Step 4: Save notification for each user
//	    for (Long userId : userIds) {
//
//	        NotificationVO n = new NotificationVO();
//
//	        n.setUserid(userId);
//	        n.setMessage(msg);
//	        n.setNotificationType(employeeMasterVO.getScreenName());
//
//	        notificationRepo.save(n);
//	    }
//	}

	@Override
	public List<EmployeeMasterVO> getAllEmployeeMasterByOrgId(Long orgId, String branchCode) {
		// TODO Auto-generated method stub
		return employeeMasterRepo.getAllEmployeeMasterByOrgId(orgId, branchCode);
	}

	@Override
	public List<EmployeeMasterVO> getEmployeeMasterById(Long id) {
		// TODO Auto-generated method stub
		return employeeMasterRepo.getEmployeeMasterById(id);
	}

}
