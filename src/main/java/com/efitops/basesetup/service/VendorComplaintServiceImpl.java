package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.VendorComplaintEntryResponseDTO;
import com.efitops.basesetup.dto.VendorComplaintDetailsDTO;
import com.efitops.basesetup.dto.VendorComplaintEntryDTO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.VendorComplaintDetailsVO;
import com.efitops.basesetup.entity.VendorComplaintEntryVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.VendorComplaintDetailsRepo;
import com.efitops.basesetup.repository.VendorComplaintEntryRepo;

@Service

public class VendorComplaintServiceImpl implements VendorComplaintService {

	@Autowired
	VendorComplaintEntryRepo vendorComplaintEntryRepo;

	@Autowired
	VendorComplaintDetailsRepo vendorComplaintDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ItemMasterRepo itemRepo;

	@Override
	@Transactional
	public Map<String, Object> updateCreateVendorComplaintEntry(VendorComplaintEntryDTO vendorComplaintEntryDTO)
			throws ApplicationException {

		String screenCode = "VCE";
		VendorComplaintEntryVO vendorComplaintEntryVO = new VendorComplaintEntryVO();
		String message;

		if (ObjectUtils.isNotEmpty(vendorComplaintEntryDTO.getId())) {

			vendorComplaintEntryVO = vendorComplaintEntryRepo.findById(vendorComplaintEntryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Vendor Complaint Entry Details"));

			vendorComplaintEntryVO.setUpdatedBy(vendorComplaintEntryDTO.getCreatedBy());

			message = "Vendor Complaint Entry Updated Successfully";

		} else {

			String docId = vendorComplaintEntryRepo.getVendorComplaintEntryDocId(vendorComplaintEntryDTO.getOrgId(),
					vendorComplaintEntryDTO.getFinancialYear(), screenCode);

			if (StringUtils.isBlank(docId)) {
				throw new ApplicationException("Vendor Complaint Entry DocId Not Found");
			}

			vendorComplaintEntryVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdScreenCode(vendorComplaintEntryDTO.getOrgId(), screenCode);

			if (documentTypeMappingDetailsVO == null) {
				throw new ApplicationException("Document Type Mapping Details Not Found");
			}

			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);

			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			vendorComplaintEntryVO.setCreatedBy(vendorComplaintEntryDTO.getCreatedBy());

			vendorComplaintEntryVO.setUpdatedBy(vendorComplaintEntryDTO.getCreatedBy());

			message = "Vendor Complaint Entry Created Successfully";
		}

		createUpdateVendorComplaintEntryVO(vendorComplaintEntryDTO, vendorComplaintEntryVO);

		VendorComplaintEntryVO savedVO = vendorComplaintEntryRepo.save(vendorComplaintEntryVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("vendorComplaintEntryVO", vendorComplaintEntryResponse(savedVO));

		return response;
	}

	private void createUpdateVendorComplaintEntryVO(VendorComplaintEntryDTO dto,
			VendorComplaintEntryVO vendorComplaintEntryVO) throws ApplicationException {

		vendorComplaintEntryVO.setDocDate(dto.getDocDate());
		vendorComplaintEntryVO.setRemarks(dto.getRemarks());
		vendorComplaintEntryVO.setActive(dto.isActive());
		vendorComplaintEntryVO.setOrgId(dto.getOrgId());
		vendorComplaintEntryVO.setCancelRemarks(dto.getCancelRemarks());
		vendorComplaintEntryVO.setFinancialYear(dto.getFinancialYear());

		if (dto.getFgItem() != null && dto.getFgItem() != 0) {

			ItemMasterVO fgItem = itemRepo.findById(dto.getFgItem())
					.orElseThrow(() -> new ApplicationException("FG Item Not Found"));

			vendorComplaintEntryVO.setFgItem(fgItem);
		}

		if (dto.getSupplier() != null && dto.getSupplier() != 0) {

			CustomerVO supplier = customerRepo.findById(dto.getSupplier())
					.orElseThrow(() -> new ApplicationException("Supplier Not Found"));

			vendorComplaintEntryVO.setSupplier(supplier);
		}

		if (dto.getId() != null) {

			List<VendorComplaintDetailsVO> oldDetails = vendorComplaintDetailsRepo
					.findByVendorComplaintEntryVO(vendorComplaintEntryVO);

			if (!oldDetails.isEmpty()) {
				vendorComplaintDetailsRepo.deleteAll(oldDetails);
			}
		}

		List<VendorComplaintDetailsVO> detailsList = new ArrayList<>();

		if (dto.getVendorComplaintDetailsDTO() != null && !dto.getVendorComplaintDetailsDTO().isEmpty()) {

			for (VendorComplaintDetailsDTO detailDTO : dto.getVendorComplaintDetailsDTO()) {

				VendorComplaintDetailsVO detailVO = new VendorComplaintDetailsVO();

				if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

					ItemMasterVO itemVO = itemRepo.findById(detailDTO.getItem())
							.orElseThrow(() -> new ApplicationException("Item Not Found"));

					detailVO.setItem(itemVO);
				}

				detailVO.setReason(detailDTO.getReason());

				detailVO.setVendorComplaintEntryVO(vendorComplaintEntryVO);

				detailsList.add(detailVO);
			}
		}

		vendorComplaintEntryVO.setVendorComplaintDetailsVO(detailsList);
	}

	private VendorComplaintEntryResponseDTO vendorComplaintEntryResponse(
			VendorComplaintEntryVO vendorComplaintEntryVO) {

		VendorComplaintEntryResponseDTO responseDTO = new VendorComplaintEntryResponseDTO();

		responseDTO.setId(vendorComplaintEntryVO.getId());
		responseDTO.setDocId(vendorComplaintEntryVO.getDocId());
		responseDTO.setDocDate(vendorComplaintEntryVO.getDocDate());
		responseDTO.setActive(vendorComplaintEntryVO.isActive());
		responseDTO.setOrgId(vendorComplaintEntryVO.getOrgId());
		responseDTO.setCreatedBy(vendorComplaintEntryVO.getCreatedBy());
		responseDTO.setCancelRemarks(vendorComplaintEntryVO.getCancelRemarks());
		responseDTO.setRemarks(vendorComplaintEntryVO.getRemarks());
		responseDTO.setFinancialYear(vendorComplaintEntryVO.getFinancialYear());

		if (vendorComplaintEntryVO.getFgItem() != null) {

			ItemResponse1DTO itemDTO = new ItemResponse1DTO();

			itemDTO.setId(vendorComplaintEntryVO.getFgItem().getId());

			itemDTO.setItemCode(vendorComplaintEntryVO.getFgItem().getItemCode());

			itemDTO.setItemDescription(vendorComplaintEntryVO.getFgItem().getItemDescription());

			responseDTO.setFgItem(itemDTO);
		}

		if (vendorComplaintEntryVO.getSupplier() != null) {

			responseDTO.setSupplier(vendorComplaintEntryVO.getSupplier().getId());
		}

		List<VendorComplaintDetailsResponseDTO> detailsResponseList = new ArrayList<>();

		if (vendorComplaintEntryVO.getVendorComplaintDetailsVO() != null
				&& !vendorComplaintEntryVO.getVendorComplaintDetailsVO().isEmpty()) {

			for (VendorComplaintDetailsVO detailVO : vendorComplaintEntryVO.getVendorComplaintDetailsVO()) {

				VendorComplaintDetailsResponseDTO detailResponseDTO = new VendorComplaintDetailsResponseDTO();

				detailResponseDTO.setReason(detailVO.getReason());

				if (detailVO.getItem() != null) {

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getItem().getId());

					itemDTO.setItemCode(detailVO.getItem().getItemCode());

					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					detailResponseDTO.setItem(itemDTO);
				}

				detailsResponseList.add(detailResponseDTO);
			}
		}

		responseDTO.setVendorComplaintDetailsResponseDTO(detailsResponseList);

		return responseDTO;
	}

	@Override
	public VendorComplaintEntryResponseDTO getVendorComplaintEntryById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		VendorComplaintEntryVO vendorComplaintEntryVO = vendorComplaintEntryRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Vendor Complaint Entry Not Found"));

		return vendorComplaintEntryResponse(vendorComplaintEntryVO);
	}

	@Override
	public List<VendorComplaintEntryResponseDTO> getVendorComplaintEntryByOrgId(Long orgId)
			throws ApplicationException {

		List<VendorComplaintEntryVO> vendorComplaintEntryList = vendorComplaintEntryRepo
				.getVendorComplaintEntryByOrgId(orgId);

		if (vendorComplaintEntryList.isEmpty()) {
			throw new ApplicationException("No Vendor Complaint Entry Details Found");
		}

		List<VendorComplaintEntryResponseDTO> responseList = new ArrayList<>();

		for (VendorComplaintEntryVO vendorComplaintEntryVO : vendorComplaintEntryList) {

			responseList.add(vendorComplaintEntryResponse(vendorComplaintEntryVO));
		}

		return responseList;
	}

	@Override
	public String getVendorComplaintEntryDocId(Long orgId, String financialYear) {

		String screenCode = "VCE";

		String result = vendorComplaintEntryRepo.getVendorComplaintEntryDocId(orgId, financialYear, screenCode);

		return result;
	}

	@Override
	public Map<String, Object> getFgItemDropdownForVendorComplaintEntry(Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> result = vendorComplaintEntryRepo.getFgItemDropdownForVendorComplaintEntry(branch, orgId);

		if (result.isEmpty()) {
			throw new ApplicationException("No FG Item Details Found");
		}

		List<Map<String, Object>> itemList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> itemMap = new HashMap<>();

			// =========================
			// Item Details
			// =========================

			itemMap.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			itemMap.put("name", obj[1] != null ? obj[1].toString() : null);

			itemList.add(itemMap);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("itemList", itemList);

		return response;
	}

//	itemdropdown for the vendor complaint entry
	@Override
	public Map<String, Object> getItemDropdownForVendorComplaintEntry(Long supplier, Long branch, Long orgId)
			throws ApplicationException {

		List<Object[]> result = vendorComplaintEntryRepo.getItemDropdownForVendorComplaintEntry(supplier, branch,
				orgId);

		if (result.isEmpty()) {
			throw new ApplicationException("No Item Details Found");
		}

		List<Map<String, Object>> itemList = new ArrayList<>();

		for (Object[] obj : result) {

			Map<String, Object> itemMap = new HashMap<>();

			// =========================
			// Item Details
			// =========================

			itemMap.put("id", obj[0] != null ? ((Number) obj[0]).longValue() : null);

			itemMap.put("itemCode", obj[1] != null ? obj[1].toString() : null);

			itemMap.put("itemDescription", obj[2] != null ? obj[2].toString() : null);

			itemMap.put("qty", obj[3] != null ? ((Number) obj[3]).doubleValue() : null);

			itemList.add(itemMap);
		}

		Map<String, Object> response = new HashMap<>();

		response.put("itemList", itemList);

		return response;
	}
}
