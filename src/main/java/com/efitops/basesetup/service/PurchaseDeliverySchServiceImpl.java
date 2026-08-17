package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.ResponseDTO.CustomerResponse1DTO;
import com.efitops.basesetup.ResponseDTO.CustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleLineResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseDeliveryScheduleResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleDetailsDTO;
import com.efitops.basesetup.dto.PurchaseDeliveryScheduleLineDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleDetailsVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleLineVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.PurchaseDeliveryScheduleDetailsRepo;
import com.efitops.basesetup.repository.PurchaseDeliveryScheduleLineRepo;
import com.efitops.basesetup.repository.PurchaseDeliveryScheduleRepo;

@Service
public class PurchaseDeliverySchServiceImpl implements PurchaseDeliverySchService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterServiceImpl.class);

	@Autowired
	PurchaseDeliveryScheduleRepo purchaseDeliveryScheduleRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	PurchaseDeliveryScheduleDetailsRepo purchaseDeliveryScheduleDetailsRepo;

	@Autowired
	ItemMasterRepo itemRepo;

	@Autowired
	PurchaseDeliveryScheduleLineRepo purchaseDeliveryScheduleLineRepo;

	@Override
	@Transactional
	public Map<String, Object> updateCreatePurchaseDeliverySchedule(
			PurchaseDeliveryScheduleDTO purchaseDeliveryScheduleDTO) throws ApplicationException {

		PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO = new PurchaseDeliveryScheduleVO();

		String message;

		if (ObjectUtils.isNotEmpty(purchaseDeliveryScheduleDTO.getId())) {

			purchaseDeliveryScheduleVO = purchaseDeliveryScheduleRepo.findById(purchaseDeliveryScheduleDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Purchase Delivery Schedule Details"));

			purchaseDeliveryScheduleVO.setUpdatedBy(purchaseDeliveryScheduleDTO.getCreatedBy());

			message = "Purchase Delivery Schedule Updated Successfully";

		} else {

			purchaseDeliveryScheduleVO.setCreatedBy(purchaseDeliveryScheduleDTO.getCreatedBy());

			purchaseDeliveryScheduleVO.setUpdatedBy(purchaseDeliveryScheduleDTO.getCreatedBy());

			message = "Purchase Delivery Schedule Created Successfully";
		}

		createUpdatePurchaseDeliveryScheduleVO(purchaseDeliveryScheduleDTO, purchaseDeliveryScheduleVO);

		PurchaseDeliveryScheduleVO savedVO = purchaseDeliveryScheduleRepo.save(purchaseDeliveryScheduleVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("purchaseDeliveryScheduleVO", purchaseDeliveryScheduleResponse(savedVO));

		return response;
	}

	private void createUpdatePurchaseDeliveryScheduleVO(PurchaseDeliveryScheduleDTO dto,
			PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO) throws ApplicationException {

		purchaseDeliveryScheduleVO.setBelongsTo(dto.getBelongsTo());
		purchaseDeliveryScheduleVO.setSchStartDate(dto.getSchStartDate());
		purchaseDeliveryScheduleVO.setSchEndDate(dto.getSchEndDate());

		purchaseDeliveryScheduleVO.setFinancialYear(dto.getFinancialYear());
		purchaseDeliveryScheduleVO.setOrgId(dto.getOrgId());
		purchaseDeliveryScheduleVO.setActive(dto.isActive());
		purchaseDeliveryScheduleVO.setCancelRemarks(dto.getCancelRemarks());

		// =========================
		// Branch Mapping
		// =========================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			purchaseDeliveryScheduleVO.setBranch(branch);
		}

		// =========================
		// Supplier Mapping
		// =========================

		if (dto.getSupplier() != null && dto.getSupplier() != 0) {

			CustomerVO supplier = customerRepo.findById(dto.getSupplier())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));

			purchaseDeliveryScheduleVO.setSupplier(supplier);
		}

		// =========================
		// Purchase Order Mapping
		// =========================

		purchaseDeliveryScheduleVO.setPurchaseOrderNo(dto.getPurchaseOrderNo());
		purchaseDeliveryScheduleVO.setPurchaseOrderDate(dto.getPurchaseOrderDate());

		// ======================================
		// Delete Existing Details During Update
		// ======================================

		if (dto.getId() != null) {

			List<PurchaseDeliveryScheduleDetailsVO> oldDetails = purchaseDeliveryScheduleDetailsRepo
					.findByPurchaseDeliveryScheduleVO(purchaseDeliveryScheduleVO);

			for (PurchaseDeliveryScheduleDetailsVO detailVO : oldDetails) {

				List<PurchaseDeliveryScheduleLineVO> oldLines = purchaseDeliveryScheduleLineRepo
						.findByPurchaseDeliveryScheduleDetailsVO(detailVO);

				purchaseDeliveryScheduleLineRepo.deleteAll(oldLines);
			}

			purchaseDeliveryScheduleDetailsRepo.deleteAll(oldDetails);
		}

		// ======================================
		// Child Save - Details
		// ======================================
		List<PurchaseDeliveryScheduleDetailsVO> detailsList = new ArrayList<>();

		if (dto.getScheduleDetails() != null && !dto.getScheduleDetails().isEmpty()) {

			for (PurchaseDeliveryScheduleDetailsDTO detailDTO : dto.getScheduleDetails()) {

				PurchaseDeliveryScheduleDetailsVO detailVO = new PurchaseDeliveryScheduleDetailsVO();

				// Item Mapping
				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO item = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(item);

					if (item.getPrimaryUnit() != null) {
						detailVO.setPrimaryUnit(item.getPrimaryUnit());
					}
				}

				detailVO.setDemandQty(detailDTO.getDemandQty());
				detailVO.setAvailableStock(detailDTO.getAvailableStock());
				detailVO.setQty(detailDTO.getQty());
				detailVO.setTentativeQty(detailDTO.getTentativeQty());
				detailVO.setTentativeQtyNextMonth(detailDTO.getTentativeQtyNextMonth());
				detailVO.setRate(detailDTO.getRate());

				// Parent Mapping
				detailVO.setPurchaseDeliveryScheduleVO(purchaseDeliveryScheduleVO);

				// ======================================
				// Child Save - Schedule Line
				// ======================================
				List<PurchaseDeliveryScheduleLineVO> lineList = new ArrayList<>();

				if (detailDTO.getSchedule() != null && !detailDTO.getSchedule().isEmpty()) {

					for (PurchaseDeliveryScheduleLineDTO lineDTO : detailDTO.getSchedule()) {

						PurchaseDeliveryScheduleLineVO lineVO = new PurchaseDeliveryScheduleLineVO();

						lineVO.setPlanDate(lineDTO.getPlanDate());
						lineVO.setWeekNo(lineDTO.getWeekNo());
						lineVO.setScheduleQty(lineDTO.getScheduleQty());

						// Parent Mapping
						lineVO.setPurchaseDeliveryScheduleDetailsVO(detailVO);

						lineList.add(lineVO);
					}
				}

				detailVO.setPurchaseDeliveryScheduleLineVO(lineList);

				detailsList.add(detailVO);
			}

			purchaseDeliveryScheduleVO.setPurchaseDeliveryScheduleDetailsVO(detailsList);
		}
	}

	private PurchaseDeliveryScheduleResponseDTO purchaseDeliveryScheduleResponse(
			PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO) {

		PurchaseDeliveryScheduleResponseDTO responseDTO = new PurchaseDeliveryScheduleResponseDTO();

		responseDTO.setId(purchaseDeliveryScheduleVO.getId());
		responseDTO.setBelongsTo(purchaseDeliveryScheduleVO.getBelongsTo());
		responseDTO.setDocNo(purchaseDeliveryScheduleVO.getDocNo());
		responseDTO.setDocDate(purchaseDeliveryScheduleVO.getDocDate());
		responseDTO.setSchStartDate(purchaseDeliveryScheduleVO.getSchStartDate());
		responseDTO.setSchEndDate(purchaseDeliveryScheduleVO.getSchEndDate());
		responseDTO.setPurchaseOrderNo(purchaseDeliveryScheduleVO.getPurchaseOrderNo());
		responseDTO.setPurchaseOrderDate(purchaseDeliveryScheduleVO.getPurchaseOrderDate());
		responseDTO.setOrgId(purchaseDeliveryScheduleVO.getOrgId());
		responseDTO.setFinancialYear(purchaseDeliveryScheduleVO.getFinancialYear());
		responseDTO.setActive(purchaseDeliveryScheduleVO.getActive());
		responseDTO.setCancelRemarks(purchaseDeliveryScheduleVO.getCancelRemarks());
		responseDTO.setCreatedBy(purchaseDeliveryScheduleVO.getCreatedBy());
		responseDTO.setCreatedBy(purchaseDeliveryScheduleVO.getUpdatedBy());

		// =========================
		// Branch Response
		// =========================

		if (purchaseDeliveryScheduleVO.getBranch() != null) {

			BranchResponseDTO branchDTO = new BranchResponseDTO();

			branchDTO.setId(purchaseDeliveryScheduleVO.getBranch().getId());
			branchDTO.setBranchName(purchaseDeliveryScheduleVO.getBranch().getBranchName());

			responseDTO.setBranch(branchDTO);
		}

		// =========================
		// Supplier Response
		// =========================

		if (purchaseDeliveryScheduleVO.getSupplier() != null) {

			SupplierResponseDTO supplierDTO = new SupplierResponseDTO();

			supplierDTO.setId(purchaseDeliveryScheduleVO.getSupplier().getId());
			supplierDTO.setSupplierCode(purchaseDeliveryScheduleVO.getSupplier().getCustomerCode());
			supplierDTO.setSupplierName(purchaseDeliveryScheduleVO.getSupplier().getCustomerName());
			responseDTO.setSupplier(supplierDTO);
		}

		// =========================
		// Details Response
		// =========================

		List<PurchaseDeliveryScheduleDetailsResponseDTO> detailResponseList = new ArrayList<>();

		if (purchaseDeliveryScheduleVO.getPurchaseDeliveryScheduleDetailsVO() != null
				&& !purchaseDeliveryScheduleVO.getPurchaseDeliveryScheduleDetailsVO().isEmpty()) {

			for (PurchaseDeliveryScheduleDetailsVO detailVO : purchaseDeliveryScheduleVO
					.getPurchaseDeliveryScheduleDetailsVO()) {

				PurchaseDeliveryScheduleDetailsResponseDTO detailDTO = new PurchaseDeliveryScheduleDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());

				// Item Response

				if (detailVO.getItem() != null) {

					ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();

					itemDTO.setId(detailVO.getItem().getId());
					itemDTO.setItemCode(detailVO.getItem().getItemCode());
					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailDTO.setItem(itemDTO);
				}

				// Primary Unit Response

				if (detailVO.getPrimaryUnit() != null) {

					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

					unitDTO.setId(detailVO.getPrimaryUnit().getId());
					unitDTO.setUnitId(detailVO.getPrimaryUnit().getUnitId());
					unitDTO.setUnitDescription(detailVO.getPrimaryUnit().getDescription());

					detailDTO.setPrimaryUnit(unitDTO);
				}

				detailDTO.setDemandQty(detailVO.getDemandQty());
				detailDTO.setAvailableStock(detailVO.getAvailableStock());
				detailDTO.setQty(detailVO.getQty());
				detailDTO.setTentativeQty(detailVO.getTentativeQty());
				detailDTO.setTentativeQtyNextMonth(detailVO.getTentativeQtyNextMonth());
				detailDTO.setRate(detailVO.getRate());

				// =========================
				// Schedule Response
				// =========================

				List<PurchaseDeliveryScheduleLineResponseDTO> lineResponseList = new ArrayList<>();

				if (detailVO.getPurchaseDeliveryScheduleLineVO() != null
						&& !detailVO.getPurchaseDeliveryScheduleLineVO().isEmpty()) {

					for (PurchaseDeliveryScheduleLineVO lineVO : detailVO.getPurchaseDeliveryScheduleLineVO()) {

						PurchaseDeliveryScheduleLineResponseDTO lineDTO = new PurchaseDeliveryScheduleLineResponseDTO();

						lineDTO.setPlanDate(lineVO.getPlanDate());
						lineDTO.setWeekNo(lineVO.getWeekNo());
						lineDTO.setScheduleQty(lineVO.getScheduleQty());

						lineResponseList.add(lineDTO);
					}
				}

				detailDTO.setSchedule(lineResponseList);

				detailResponseList.add(detailDTO);
			}
		}

		responseDTO.setScheduleDetails(detailResponseList);

		return responseDTO;
	}

	@Override
	public PurchaseDeliveryScheduleResponseDTO getPurchaseDeliveryScheduleById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO = purchaseDeliveryScheduleRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Purchase Delivery Schedule Not Found"));

		return purchaseDeliveryScheduleResponse(purchaseDeliveryScheduleVO);
	}

	@Override
	public List<PurchaseDeliveryScheduleResponseDTO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<PurchaseDeliveryScheduleVO> purchaseDeliveryScheduleList = purchaseDeliveryScheduleRepo
				.getPurchaseDeliveryScheduleByOrgId(orgId, branch);

		if (purchaseDeliveryScheduleList.isEmpty()) {
			throw new ApplicationException("No Purchase Delivery Schedule Details Found");
		}

		List<PurchaseDeliveryScheduleResponseDTO> responseList = new ArrayList<>();

		for (PurchaseDeliveryScheduleVO purchaseDeliveryScheduleVO : purchaseDeliveryScheduleList) {

			responseList.add(purchaseDeliveryScheduleResponse(purchaseDeliveryScheduleVO));
		}

		return responseList;
	}

//	dropdown api for supllier

	@Override
	public Map<String, Object> getSupplierDropdown(Long branch, Long orgId) throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		List<Object[]> supplierList = customerRepo.getSupplierDropdownForPurchaseDeliverySchedule(branch, orgId);

		List<CustomerResponse1DTO> responseDTOList = new ArrayList<>();

		for (Object[] obj : supplierList) {

			CustomerResponse1DTO dto = new CustomerResponse1DTO();

			dto.setId(obj[0] != null ? ((Number) obj[0]).longValue() : 0L);

			dto.setCustomerCode(obj[1] != null ? (String) obj[1] : "");

			dto.setCustomerName(obj[2] != null ? (String) obj[2] : "");

			responseDTOList.add(dto);
		}

		responseMap.put("message", "Supplier List Fetched Successfully");

		responseMap.put("supplierList", responseDTOList);

		return responseMap;
	}
	
//	dropdown api for purchase unit in purchasedeliveryschedule
	
	@Override
	public Map<String, Object> getPurchaseUnitForPurchaseDeliverySchedule(Long item,Long branch,Long orgId )
	        throws ApplicationException {

	    Map<String, Object> responseMap = new HashMap<>();

	    List<Object[]> unitList =
	    		purchaseDeliveryScheduleRepo.getPurchaseUnitForPurchaseDeliverySchedule(item,branch,orgId);

	    List<UnitMasterResponseDTO> responseDTOList =
	            new ArrayList<>();

	    for (Object[] obj : unitList) {

	        UnitMasterResponseDTO dto =
	                new UnitMasterResponseDTO();

	        dto.setId(
	                obj[0] != null
	                        ? ((Number) obj[0]).longValue()
	                        : 0L);

	        dto.setUnitId(
	                obj[1] != null
	                        ? (String) obj[1]
	                        : "");

	        responseDTOList.add(dto);
	    }

	    responseMap.put(
	            "message",
	            "Purchase Unit Fetched Successfully");

	    responseMap.put(
	            "unitList",
	            responseDTOList);

	    return responseMap;
	}
	
	
}
