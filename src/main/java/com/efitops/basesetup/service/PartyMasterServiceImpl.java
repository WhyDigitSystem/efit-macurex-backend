package com.efitops.basesetup.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.BranchResponseDTO;
import com.efitops.basesetup.ResponseDTO.LogisticsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PartyCategoryResponseDTO;
import com.efitops.basesetup.ResponseDTO.PartyMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.SupplierCategoryResponseDTO;
import com.efitops.basesetup.dto.PartyMasterDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.PartyMasterRepo;
import com.efitops.basesetup.repository.TransportRepo;

@Service
public class PartyMasterServiceImpl implements PartyMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PartyMasterServiceImpl.class);

//	@Autowired
//	PartyMasterRepo partyMasterRepo;
//	
//	@Autowired
//	ListOfValuesRepo listOfValuesRepo;
//	
//	@Autowired
//	BranchRepo branchRepo;
//
//	@Autowired
//	TransportRepo transportMasterRepo;
//	
//	@Override
//	@Transactional
//	public Map<String, Object> createUpdatePartyMaster(PartyMasterDTO partyMasterDTO) throws ApplicationException {
//
//		PartyMasterVO partyMasterVO = new PartyMasterVO();
//		String message;
//
//		if (partyMasterDTO.getId() != null) {
//
//			partyMasterVO = partyMasterRepo.findById(partyMasterDTO.getId())
//					.orElseThrow(() -> new ApplicationException("Invalid Party Master Details"));
//
//			partyMasterVO.setUpdatedBy(partyMasterDTO.getCreatedBy());
//
//			message = "Party Master Updated Successfully";
//
//		} else {
//
//			partyMasterVO.setCreatedBy(partyMasterDTO.getCreatedBy());
//			partyMasterVO.setUpdatedBy(partyMasterDTO.getCreatedBy());
//
//			message = "Party Master Created Successfully";
//		}
//
//		createUpdatePartyMasterVO(partyMasterDTO, partyMasterVO);
//
//		partyMasterVO = partyMasterRepo.save(partyMasterVO);
//
//		PartyMasterResponseDTO responseDTO = mapPartyMasterVOToResponseDTO(partyMasterVO);
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("partyMaster", responseDTO);
//		response.put("message", message);
//
//		return response;
//	}
//
//	private void createUpdatePartyMasterVO(PartyMasterDTO dto, PartyMasterVO partyMasterVO)
//			throws ApplicationException {
//
//		if (dto.getPartyCategory() != null && dto.getPartyCategory() != 0) {
//
//			ListOfValuesVO category = listOfValuesRepo.findById(dto.getPartyCategory())
//					.orElseThrow(() -> new ApplicationException("Party Category Not Found"));
//
//			partyMasterVO.setPartyCategory(category);
//		}
//
//		if (dto.getPartyCategory1() != null && dto.getPartyCategory1() != 0) {
//
//			ListOfValuesVO category1 = listOfValuesRepo.findById(dto.getPartyCategory1())
//					.orElseThrow(() -> new ApplicationException("Party Category1 Not Found"));
//
//			partyMasterVO.setPartyCategory1(category1);
//		}
//
//		if (dto.getPartyCategory2() != null && dto.getPartyCategory2() != 0) {
//
//			ListOfValuesVO category2 = listOfValuesRepo.findById(dto.getPartyCategory2())
//					.orElseThrow(() -> new ApplicationException("Party Category2 Not Found"));
//
//			partyMasterVO.setPartyCategory2(category2);
//		}
//
//		if (dto.getSupplierCategory() != null && dto.getSupplierCategory() != 0) {
//
//			ListOfValuesVO supplierCategory = listOfValuesRepo.findById(dto.getSupplierCategory())
//					.orElseThrow(() -> new ApplicationException("Supplier Category Not Found"));
//
//			partyMasterVO.setSupplierCategory(supplierCategory);
//		}
//
//		if (dto.getBranch() != null && dto.getBranch() != 0) {
//
//			BranchVO branch = branchRepo.findById(dto.getBranch())
//					.orElseThrow(() -> new ApplicationException("Branch Not Found"));
//
//			partyMasterVO.setBranch(branch);
//		}
//
//		if (dto.getLogistics() != null && dto.getLogistics() != 0) {
//
//			TransportMasterVO transport = transportMasterRepo.findById(dto.getLogistics())
//					.orElseThrow(() -> new ApplicationException("Transport Master Not Found"));
//
//			partyMasterVO.setLogistics(transport);
//		}
//
//		partyMasterVO.setRegistered(dto.isRegistered());
//		partyMasterVO.setSalutation(dto.getSalutation());
//		partyMasterVO.setPartyType(dto.getPartyType());
//		partyMasterVO.setVendorId(dto.getVendorId());
//		partyMasterVO.setPartyName(dto.getPartyName().toUpperCase());
//		partyMasterVO.setIsGroupCompany(dto.getIsGroupCompany());
//		partyMasterVO.setZone(dto.getZone());
//		partyMasterVO.setVendorCode(dto.getVendorCode());
//		partyMasterVO.setGroupName(dto.getGroupName());
//		partyMasterVO.setLegalName(dto.getLegalName());
//		partyMasterVO.setTradeName(dto.getTradeName());
//		partyMasterVO.setBelongsTo(dto.getBelongsTo());
//		partyMasterVO.setPartyCreditLimit(dto.getPartyCreditLimit());
//		partyMasterVO.setPartyCreditPeriod(dto.getPartyCreditPeriod());
//		partyMasterVO.setExcisable(dto.isExcisable());
//		partyMasterVO.setGstType(dto.getGstType());
//		partyMasterVO.setGstNo(dto.getGstNo());
//		partyMasterVO.setIgstApplicable(dto.isIgstApplicable());
//		partyMasterVO.setDate(dto.getDate());
//		partyMasterVO.setOrgId(dto.getOrgId());
//		partyMasterVO.setFinYear(dto.getFinYear());
//		partyMasterVO.setActive(dto.isActive());
//		partyMasterVO.setCancelRemarks(dto.getCancelRemarks());
//	}
//	
//	private PartyMasterResponseDTO mapPartyMasterVOToResponseDTO(PartyMasterVO vo) {
//
//	    PartyMasterResponseDTO dto = new PartyMasterResponseDTO();
//
//	    dto.setId(vo.getId());
//
//	    // Party Category
//	    if (vo.getPartyCategory() != null) {
//	        PartyCategoryResponseDTO partyCategory = new PartyCategoryResponseDTO();
//	        partyCategory.setId(vo.getPartyCategory().getId());
//	        partyCategory.setCode(vo.getPartyCategory().getListCode());
//	        partyCategory.setDescription(vo.getPartyCategory().getListDescription());
//	        dto.setPartyCategory(partyCategory);
//	    }
//
//	    // Party Category 1
//	    if (vo.getPartyCategory1() != null) {
//	        PartyCategoryResponseDTO partyCategory1 = new PartyCategoryResponseDTO();
//	        partyCategory1.setId(vo.getPartyCategory1().getId());
//	        partyCategory1.setCode(vo.getPartyCategory1().getListCode());
//	        partyCategory1.setDescription(vo.getPartyCategory1().getListDescription());
//	        dto.setPartyCategory1(partyCategory1);
//	    }
//
//	    // Party Category 2
//	    if (vo.getPartyCategory2() != null) {
//	        PartyCategoryResponseDTO partyCategory2 = new PartyCategoryResponseDTO();
//	        partyCategory2.setId(vo.getPartyCategory2().getId());
//	        partyCategory2.setCode(vo.getPartyCategory2().getListCode());
//	        partyCategory2.setDescription(vo.getPartyCategory2().getListDescription());
//	        dto.setPartyCategory2(partyCategory2);
//	    }
//
//	    // Supplier Category
//	    if (vo.getSupplierCategory() != null) {
//	        SupplierCategoryResponseDTO supplierCategory = new SupplierCategoryResponseDTO();
//	        supplierCategory.setId(vo.getSupplierCategory().getId());
//	        supplierCategory.setCode(vo.getSupplierCategory().getListCode());
//	        supplierCategory.setDescription(vo.getSupplierCategory().getListDescription());
//	        dto.setSupplierCategory(supplierCategory);
//	    }
//
//	    // Branch
//	    if (vo.getBranch() != null) {
//	        BranchResponseDTO branch = new BranchResponseDTO();
//	        branch.setId(vo.getBranch().getId());
//	        branch.setBranchCode(vo.getBranch().getBranchCode());
//	        branch.setBranchName(vo.getBranch().getBranchName());
//	        dto.setBranch(branch);
//	    }
//
//	    // Logistics
//	    if (vo.getLogistics() != null) {
//	        LogisticsResponseDTO logistics = new LogisticsResponseDTO();
//	        logistics.setId(vo.getLogistics().getId());
//	        logistics.setTransportName(vo.getLogistics().getTransportName());
//	        dto.setLogistics(logistics);
//	    }
//
//	    dto.setRegistered(vo.isRegistered());
//	    dto.setSalutation(vo.getSalutation());
//	    dto.setPartyType(vo.getPartyType());
//	    dto.setVendorId(vo.getVendorId());
//	    dto.setPartyName(vo.getPartyName());
//	    dto.setIsGroupCompany(vo.getIsGroupCompany());
//	    dto.setZone(vo.getZone());
//	    dto.setVendorCode(vo.getVendorCode());
//	    dto.setGroupName(vo.getGroupName());
//	    dto.setLegalName(vo.getLegalName());
//	    dto.setTradeName(vo.getTradeName());
//	    dto.setBelongsTo(vo.getBelongsTo());
//	    dto.setPartyCreditLimit(vo.getPartyCreditLimit());
//	    dto.setPartyCreditPeriod(vo.getPartyCreditPeriod());
//	    dto.setExcisable(vo.isExcisable());
//	    dto.setGstType(vo.getGstType());
//	    dto.setGstNo(vo.getGstNo());
//	    dto.setIgstApplicable(vo.isIgstApplicable());
//	    dto.setDate(vo.getDate());
//	    dto.setOrgId(vo.getOrgId());
//	    dto.setCreatedBy(vo.getCreatedBy());
//	    dto.setUpdatedBy(vo.getUpdatedBy());
//	    dto.setActive(vo.isActive());
//	    dto.setCancelRemarks(vo.getCancelRemarks());
//
//	    return dto;
//	}
//
//	@Override
//	public Object getPartyMasterByOrgId(Long orgId, Long branch) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public PartyMasterVO getPartyMasterById(Long id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
