package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PackingListDTO;
import com.efitops.basesetup.dto.PackingListDetailsDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ExportPackingListVO;
import com.efitops.basesetup.entity.PackingListDetailsVO;
import com.efitops.basesetup.entity.PackingListVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.PackingListDetailsRepo;
import com.efitops.basesetup.repo.PackingListRepo;

@Service
public class PackingListServiceImpl implements PackingListService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PackingListServiceImpl.class);

	@Autowired
	PackingListRepo packingListRepo;

	@Autowired
	PackingListDetailsRepo packingListDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public List<PackingListVO> getAllPackingListByOrgId(Long orgId, String finYear, String branchCode) {
		List<PackingListVO> packingListVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orgId)) {
			LOGGER.info("Successfully Received  PackingList BY OrgId : {}", orgId);
			packingListVO = packingListRepo.getAllPackingListByOrgId(orgId, finYear, branchCode);
		}
		return packingListVO;
	}

	@Override
	public PackingListVO getPackingListById(Long id) {

		return packingListRepo.getAllPackingListById(id);
	}

	@Override
	public Map<String, Object> createUpdatePackingList(PackingListDTO packingListDTO) throws ApplicationException {
		PackingListVO packingListVO = new PackingListVO();
		String message;
		String screenCode = "PL";
		PackingListVO oldPackingList  = null;

		
		if (ObjectUtils.isNotEmpty(packingListDTO.getId())) {
			
			oldPackingList = packingListRepo.findById(packingListDTO.getId())
		            .orElseThrow(() -> new ApplicationException("packingList  not found"));

			oldPackingList.getPackingListDetailsVO().size();// load
	
			
		    entityManager.detach(oldPackingList); // detach snapshot
			
			packingListVO = packingListRepo.findById(packingListDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid PackingList details"));
			message = "PackingList Updated Successfully";
			packingListVO.setUpdatedBy(packingListDTO.getCreatedBy());

		} else {
			String docId = packingListRepo.getPackingListDocId(packingListDTO.getOrgId(), packingListDTO.getFinYear(),
					packingListDTO.getBranchCode(), screenCode);
			packingListVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(packingListDTO.getOrgId(),
							packingListDTO.getFinYear(), packingListDTO.getBranchCode(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			packingListVO.setCreatedBy(packingListDTO.getCreatedBy());
			packingListVO.setUpdatedBy(packingListDTO.getCreatedBy());

			message = "PackingList Created Successfully";
		}
		createUpdatePackingListVOByPackingListDTO(packingListDTO, packingListVO);
		packingListRepo.save(packingListVO);
		commonNotificationService.generateNotification(packingListVO.getScreenCode(), packingListVO.getId(), oldPackingList, packingListVO);

		Map<String, Object> response = new HashMap<>();
		response.put("packingListVO", packingListVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatePackingListVOByPackingListDTO(PackingListDTO packingListDTO, PackingListVO packingListVO) {

		packingListVO.setOrgId(packingListDTO.getOrgId());
		packingListVO.setNarration(packingListDTO.getNarration());
		packingListVO.setCustomerName(packingListDTO.getCustomerName());
		packingListVO.setCustomerAddress(packingListDTO.getCustomerAddress());
		packingListVO.setSalesOrderNo(packingListDTO.getSalesOrderNo());
		packingListVO.setSalesOrderDate(packingListDTO.getSalesOrderDate());
		packingListVO.setSupplyDate(packingListDTO.getSupplyDate());
		packingListVO.setDeliveryPlace(packingListDTO.getDeliveryPlace());
		packingListVO.setNoOfPackage(packingListDTO.getNoOfPackage());
		packingListVO.setVendorCode(packingListDTO.getVendorCode());
		packingListVO.setBranch(packingListDTO.getBranch());
		packingListVO.setBranchCode(packingListDTO.getBranchCode());
		packingListVO.setFinYear(packingListDTO.getFinYear());

		BigDecimal totalQty = BigDecimal.ZERO;
		BigDecimal totalWeight = BigDecimal.ZERO;

		if (ObjectUtils.isNotEmpty(packingListVO.getId())) {
			List<PackingListDetailsVO> packingListDetailsVO1 = packingListDetailsRepo
					.findByPackingListVO(packingListVO);
			packingListDetailsRepo.deleteAll(packingListDetailsVO1);
		}

		List<PackingListDetailsVO> packingListDetailsVOs = new ArrayList<>();
		for (PackingListDetailsDTO packingListDetailsDTO : packingListDTO.getPackingListDetailsDTO()) {
			PackingListDetailsVO packingListDetailsVO = new PackingListDetailsVO();
			packingListDetailsVO.setPartNo(packingListDetailsDTO.getPartNo());
			packingListDetailsVO.setPartDesc(packingListDetailsDTO.getPartDesc());
			packingListDetailsVO.setQty(packingListDetailsDTO.getQty());
			totalQty = totalQty.add(packingListDetailsVO.getQty());
			packingListDetailsVO.setWeight(packingListDetailsDTO.getWeight());
			totalWeight = totalWeight.add(packingListDetailsVO.getWeight());
			packingListDetailsVO.setUnit(packingListDetailsDTO.getUnit());
			packingListDetailsVO.setRemarks(packingListDetailsDTO.getRemarks());
			packingListDetailsVO.setPoQty(packingListDetailsDTO.getPoQty());
			packingListDetailsVO.setSalesOrderNo(packingListDetailsDTO.getSalesOrderNo());

			packingListDetailsVO.setPackingListVO(packingListVO);
			packingListDetailsVOs.add(packingListDetailsVO);
		}
		packingListVO.setTotalQty(totalQty);
		packingListVO.setTotalCrossWeight(totalWeight);
		packingListVO.setPackingListDetailsVO(packingListDetailsVOs);
	}

	@Override
	public String getPackingListDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "PL";
		return packingListRepo.getPackingListDocId(orgId, finYear, branchCode, screenCode);
	}

	@Override
	public List<Map<String, Object>> getCustomerNameFromPartyMasterPacking(Long orgId) {
		Set<Object[]> chType = packingListRepo.getCustomerNameFromPartyMasterPacking(orgId);
		return getCustomerNameFromParty(chType);
	}

	private List<Map<String, Object>> getCustomerNameFromParty(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partyName", ch[0] != null ? ch[0].toString() : "");
			map.put("partyCode", ch[1] != null ? ch[1].toString() : "");
			map.put("partyAddress", ch[2] != null ? ch[2].toString() : "");
			map.put("city", ch[3] != null ? ch[3].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getDocIdFromSalesOrderNo(Long orgId, String customerName) {
		Set<Object[]> chType = packingListRepo.getDocIdFromSalesOrderNo(orgId, customerName);
		return getDocIdFromSales(chType);
	}

	private List<Map<String, Object>> getDocIdFromSales(Set<Object[]> chType) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("salesOrderNo", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNoFromSalesOrder(Long orgId, String salesOrderNo) {

		// 1️⃣ Split comma-separated sales orders
		List<String> salesOrderList = Arrays.stream(salesOrderNo.split(",")).map(String::trim)
				.collect(Collectors.toList());

		// 2️⃣ Call repo ONCE with IN clause
		Set<Object[]> chType = packingListRepo.getPartNoFromSalesOrder(orgId, salesOrderList);

		return getPartNoFromSales(chType);
	}

	private List<Map<String, Object>> getPartNoFromSales(Set<Object[]> chType) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Object[] ch : chType) {
			Map<String, Object> map = new HashMap<>();
			map.put("partNo", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryUnit", ch[2] != null ? ch[2].toString() : "");
			map.put("salesOrderNo", ch[3] != null ? ch[3].toString() : "");
			map.put("poQty", ch[4] != null ? ch[4].toString() : "");

			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getPackingListDetails(Long orgId, String fromdate, String todate,
			String customername, String salesorderno) {
		Set<Object[]> packingListDetails = packingListRepo.getPackingListDetails(orgId, fromdate, todate, customername,
				salesorderno);
		return getPackingListDetails(packingListDetails);
	}

	private List<Map<String, Object>> getPackingListDetails(Set<Object[]> packingListDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : packingListDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("packinglistid", ch[0] != null ? ch[0].toString() : "");
			map.put("orgid", ch[1] != null ? ch[1].toString() : "");
			map.put("docid", ch[2] != null ? ch[2].toString() : "");
			map.put("docdate", ch[3] != null ? ch[3].toString() : "");
			map.put("customername", ch[4] != null ? ch[4].toString() : "");
			map.put("customeraddress", ch[5] != null ? ch[5].toString() : "");
			map.put("salesorderno", ch[6] != null ? ch[6].toString() : "");
			map.put("salesorderdate", ch[7] != null ? ch[7].toString() : "");
			map.put("deliveryplace", ch[8] != null ? ch[8].toString() : "");
			map.put("noofpckages", ch[9] != null ? ch[9].toString() : "");

			List1.add(map);
		}

		return List1;
	}

}