package com.efitops.basesetup.service;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.FgIssueToPackingDTO;
import com.efitops.basesetup.dto.FgIssueToPackingDetailsDTO;
import com.efitops.basesetup.dto.FgStockUpdateDetailsDTO;
import com.efitops.basesetup.dto.FinalFgPartStockUpdateDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.FgIssueToPackingDetailsVO;
import com.efitops.basesetup.entity.FgIssueToPackingVO;
import com.efitops.basesetup.entity.FgStockUpdateDetailsVO;
import com.efitops.basesetup.entity.FinalFgPartStockUpdateVO;
import com.efitops.basesetup.entity.StockDetailsVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repo.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repo.FgIssueToPackingDetailsRepo;
import com.efitops.basesetup.repo.FgIssueToPackingRepo;
import com.efitops.basesetup.repo.FgStockUpdateDetailsRepo;
import com.efitops.basesetup.repo.FinalFgPartStockUpdateRepo;
import com.efitops.basesetup.repo.StockDetailsRepo;

@Service
public class AssemblyServiceImpl implements AssemblyService {

	public static final Logger LOGGER = LoggerFactory.getLogger(AssemblyServiceImpl.class);

	@Autowired
	FinalFgPartStockUpdateRepo fgPartStockUpdateRepo;

	@Autowired
	FgStockUpdateDetailsRepo fgStockUpdateDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	FgIssueToPackingRepo fgIssueToPackingRepo;


	@Autowired
	FgIssueToPackingDetailsRepo fgIssueToPackingDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;
	
	@Autowired
	private CommonNotificationService commonNotificationService;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<FinalFgPartStockUpdateVO> getAllFgPartStockUpdateVOByOrgId(Long orgId) {
		List<FinalFgPartStockUpdateVO> finalFgPartStockUpdateVO = new ArrayList<>();
		finalFgPartStockUpdateVO = fgPartStockUpdateRepo.getAllFgPartStockUpdateVOByOrgId(orgId);

		return finalFgPartStockUpdateVO;
	}

	@Override
	public FinalFgPartStockUpdateVO getFgPartStockUpdateVOById(Long id) {
		FinalFgPartStockUpdateVO finalFgPartStockUpdateVO = new FinalFgPartStockUpdateVO();

		finalFgPartStockUpdateVO = fgPartStockUpdateRepo.getFgPartStockUpdateVOById(id);

		return finalFgPartStockUpdateVO;
	}

	@Override
	public String getFgPartStockUpdateDocId(Long orgId, String finYear, String branchCode) {
		String screenCode = "FGS";
		String result = fgPartStockUpdateRepo.getFgPartStockUpdateDocId(orgId, finYear, branchCode, screenCode);
		return result;
	}

	@Override
	public Map<String, Object> updateCreateFgPartStockUpdate(@Valid FinalFgPartStockUpdateDTO finalFgPartStockUpdateDTO)
			throws ApplicationException {
		String message;
		String screenCode = "FGS";
		FinalFgPartStockUpdateVO oldFinalFgPartStockUpdate    = null;

		
		FinalFgPartStockUpdateVO finalFgPartStockUpdateVO = new FinalFgPartStockUpdateVO();

		if (finalFgPartStockUpdateDTO.getId() != null) {
			
			oldFinalFgPartStockUpdate = fgPartStockUpdateRepo.findById(finalFgPartStockUpdateDTO.getId())
		            .orElseThrow(() -> new ApplicationException("finalFgPartStockUpdate not found"));

			oldFinalFgPartStockUpdate.getFgStockUpdateDetailsVO().size(); // load
			
		    entityManager.detach(oldFinalFgPartStockUpdate); // detach snapshot
			
		    finalFgPartStockUpdateVO = fgPartStockUpdateRepo.findById(finalFgPartStockUpdateDTO.getId())
					.orElseThrow(() -> new ApplicationException("PickList not found"));
			finalFgPartStockUpdateVO.setUpdatedBy(finalFgPartStockUpdateDTO.getCreatedBy());
			createUpdateFinalFgPartStockUpdateVOByFinalFgPartStockUpdateDTO(finalFgPartStockUpdateDTO,
					finalFgPartStockUpdateVO);
			message = "PickList Updated Successfully";

		} else {

			// GETDOCID API
			String docId = fgPartStockUpdateRepo.getFgPartStockUpdateDocId(finalFgPartStockUpdateDTO.getOrgId(),
					finalFgPartStockUpdateDTO.getFinYear(), finalFgPartStockUpdateDTO.getBranchCode(), screenCode);
			finalFgPartStockUpdateVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndScreenCode(finalFgPartStockUpdateDTO.getOrgId(),
							finalFgPartStockUpdateDTO.getFinYear(), finalFgPartStockUpdateDTO.getBranchCode(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			finalFgPartStockUpdateVO.setCreatedBy(finalFgPartStockUpdateDTO.getCreatedBy());
			finalFgPartStockUpdateVO.setUpdatedBy(finalFgPartStockUpdateDTO.getCreatedBy());
			createUpdateFinalFgPartStockUpdateVOByFinalFgPartStockUpdateDTO(finalFgPartStockUpdateDTO,
					finalFgPartStockUpdateVO);
			message = "PickList Created Successfully";
		}

		FinalFgPartStockUpdateVO savedPicked = fgPartStockUpdateRepo.save(finalFgPartStockUpdateVO);
		StockDetailsVO stockDetailsVO = new StockDetailsVO();
		stockDetailsVO.setOrgId(savedPicked.getOrgId());
		stockDetailsVO.setStockDate(savedPicked.getDocDate());
		stockDetailsVO.setDocId(savedPicked.getDocId());
		stockDetailsVO.setDocDate(savedPicked.getDocDate());
		stockDetailsVO.setRefDate(savedPicked.getDocDate());
		stockDetailsVO.setRefNo(savedPicked.getId());
		stockDetailsVO.setSourceId(savedPicked.getId());
		stockDetailsVO.setSourceScreenCode(savedPicked.getScreenCode());
		stockDetailsVO.setSourceScreenName(savedPicked.getScreenName());
		stockDetailsVO.setPlusOrMinus("p");
		stockDetailsVO.setQty(savedPicked.getQty().multiply(BigDecimal.valueOf(1)));
		stockDetailsVO.setLocation(savedPicked.getToLocation());
		stockDetailsVO.setPartno(savedPicked.getPart());
		stockDetailsVO.setPartDesc(savedPicked.getPartDesc());
		stockDetailsVO.setActive(true);
		stockDetailsVO.setCancel(false);
		stockDetailsVO.setStatus(savedPicked.getStatus());
		stockDetailsVO.setCreatedBy(savedPicked.getCreatedBy());
		stockDetailsVO.setUpdatedBy(savedPicked.getUpdatedBy());
		stockDetailsVO.setBranch(savedPicked.getBranch());
		stockDetailsVO.setBranchCode(savedPicked.getBranchCode());
		stockDetailsVO.setFinYear(savedPicked.getFinYear());
		stockDetailsVO.setAmount(savedPicked.getAmount());
		stockDetailsVO.setRate(savedPicked.getRate());
		stockDetailsRepo.save(stockDetailsVO);

		for (FgStockUpdateDetailsVO detail : savedPicked.getFgStockUpdateDetailsVO()) {
			StockDetailsVO stockDetailsVO1 = new StockDetailsVO();
			stockDetailsVO1.setOrgId(savedPicked.getOrgId());
			stockDetailsVO1.setStockDate(savedPicked.getDocDate());
			stockDetailsVO1.setDocId(savedPicked.getDocId());
			stockDetailsVO1.setDocDate(savedPicked.getDocDate());
			stockDetailsVO1.setRefDate(savedPicked.getDocDate());
			stockDetailsVO1.setRefNo(savedPicked.getId());
			stockDetailsVO1.setSourceId(savedPicked.getId());
			stockDetailsVO1.setSourceScreenCode(savedPicked.getScreenCode());
			stockDetailsVO1.setSourceScreenName(savedPicked.getScreenName());
			stockDetailsVO1.setPlusOrMinus("m");
			stockDetailsVO1.setQty(detail.getQty().multiply(BigDecimal.valueOf(-1)));
			stockDetailsVO1.setLocation(savedPicked.getFromLocation());
			stockDetailsVO1.setPartno(detail.getPart());
			stockDetailsVO1.setPartDesc(detail.getPartDesc());
			stockDetailsVO1.setAmount(detail.getAmount());
			stockDetailsVO1.setRate(detail.getRate());
			stockDetailsVO1.setActive(true);
			stockDetailsVO1.setCancel(false);
			stockDetailsVO1.setStatus(savedPicked.getStatus());
			stockDetailsVO1.setCreatedBy(savedPicked.getCreatedBy());
			stockDetailsVO1.setUpdatedBy(savedPicked.getUpdatedBy());
			stockDetailsVO1.setBranch(savedPicked.getBranch());
			stockDetailsVO1.setBranchCode(savedPicked.getBranchCode());
			stockDetailsVO1.setFinYear(savedPicked.getFinYear());
			stockDetailsRepo.save(stockDetailsVO1);
		}
		
		commonNotificationService.generateNotification(finalFgPartStockUpdateVO.getScreenCode(), finalFgPartStockUpdateVO.getId(), oldFinalFgPartStockUpdate, finalFgPartStockUpdateVO);
		Map<String, Object> response = new HashMap<>();
		response.put("finalFgPartStockUpdateVO", finalFgPartStockUpdateVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateFinalFgPartStockUpdateVOByFinalFgPartStockUpdateDTO(
			@Valid FinalFgPartStockUpdateDTO finalFgPartStockUpdateDTO,
			FinalFgPartStockUpdateVO finalFgPartStockUpdateVO) {
		finalFgPartStockUpdateVO.setRouteCardNo(finalFgPartStockUpdateDTO.getRouteCardNo());
		finalFgPartStockUpdateVO.setWorkOrderNo(finalFgPartStockUpdateDTO.getWorkOrderNo());
		finalFgPartStockUpdateVO.setFromLocation(finalFgPartStockUpdateDTO.getFromLocation());
		finalFgPartStockUpdateVO.setToLocation(finalFgPartStockUpdateDTO.getToLocation());
		finalFgPartStockUpdateVO.setNarration(finalFgPartStockUpdateDTO.getNarration());

		finalFgPartStockUpdateVO.setPart(finalFgPartStockUpdateDTO.getPart());
		finalFgPartStockUpdateVO.setPartDesc(finalFgPartStockUpdateDTO.getPartDesc());
		finalFgPartStockUpdateVO.setUnit(finalFgPartStockUpdateDTO.getUnit());
		finalFgPartStockUpdateVO.setQty(finalFgPartStockUpdateDTO.getQty());
		finalFgPartStockUpdateVO.setStatus(finalFgPartStockUpdateDTO.getStatus());
		finalFgPartStockUpdateVO.setRate(finalFgPartStockUpdateDTO.getRate());
		BigDecimal qtyBigDecimal = finalFgPartStockUpdateDTO.getQty();
		BigDecimal amount = qtyBigDecimal.multiply(finalFgPartStockUpdateDTO.getRate());
		finalFgPartStockUpdateVO.setAmount(amount);
		finalFgPartStockUpdateVO.setOrgId(finalFgPartStockUpdateDTO.getOrgId());
		finalFgPartStockUpdateVO.setStatus(finalFgPartStockUpdateDTO.getStatus());
		finalFgPartStockUpdateVO.setBranchCode(finalFgPartStockUpdateDTO.getBranchCode());
		finalFgPartStockUpdateVO.setBranch(finalFgPartStockUpdateDTO.getBranch());
		finalFgPartStockUpdateVO.setFinYear(finalFgPartStockUpdateDTO.getFinYear());

		if (ObjectUtils.isNotEmpty(finalFgPartStockUpdateDTO.getId())) {
			List<FgStockUpdateDetailsVO> fgStockUpdateDetailsVOs = fgStockUpdateDetailsRepo
					.findByFinalFgPartStockUpdateVO(finalFgPartStockUpdateVO);
			fgStockUpdateDetailsRepo.deleteAll(fgStockUpdateDetailsVOs);
		}

		List<FgStockUpdateDetailsVO> fgStockUpdateDetailsVOs = new ArrayList<>();
		for (FgStockUpdateDetailsDTO fgStockUpdateDetailsDTO : finalFgPartStockUpdateDTO.getFgStockUpdateDetailsDTO()) {
			FgStockUpdateDetailsVO fgStockUpdateDetailsVO = new FgStockUpdateDetailsVO();
			fgStockUpdateDetailsVO.setPart(fgStockUpdateDetailsDTO.getPart());
			fgStockUpdateDetailsVO.setPartDesc(fgStockUpdateDetailsDTO.getPartDesc());
			fgStockUpdateDetailsVO.setUnit(fgStockUpdateDetailsDTO.getUnit());
			if (fgStockUpdateDetailsDTO != null && fgStockUpdateDetailsDTO.getAvailableQty() != null
					&& fgStockUpdateDetailsDTO.getQty() != null
					&& fgStockUpdateDetailsDTO.getAvailableQty().compareTo(fgStockUpdateDetailsDTO.getQty()) > 0) {

				fgStockUpdateDetailsVO.setQty(fgStockUpdateDetailsDTO.getQty());
				fgStockUpdateDetailsVO.setRate(fgStockUpdateDetailsDTO.getRate());
			} else {
				throw new ApplicationContextException("Invalid quantity values");
			}

			fgStockUpdateDetailsVO.setAvailableQty(fgStockUpdateDetailsDTO.getAvailableQty());
			fgStockUpdateDetailsVO.setAmount(fgStockUpdateDetailsVO.getQty().multiply(finalFgPartStockUpdateVO.getQty())
					.multiply(fgStockUpdateDetailsVO.getRate()));
			fgStockUpdateDetailsVO.setActualQty(fgStockUpdateDetailsDTO.getActualQty());
			fgStockUpdateDetailsVO.setFinalFgPartStockUpdateVO(finalFgPartStockUpdateVO);
			fgStockUpdateDetailsVOs.add(fgStockUpdateDetailsVO);
		}
		finalFgPartStockUpdateVO.setFgStockUpdateDetailsVO(fgStockUpdateDetailsVOs);

	}

	@Override
	public List<Map<String, Object>> getRouteCardEntryNoFromFgPartStockUpdate(Long orgId) {
		Set<Object[]> fgPartStockUpdate = fgPartStockUpdateRepo.findRouteCardEntryNoFromFgPartStockUpdate(orgId);
		return getRouteCardEntryNoFromFgPartStockUpdate(fgPartStockUpdate);
	}

	private List<Map<String, Object>> getRouteCardEntryNoFromFgPartStockUpdate(Set<Object[]> fgPartStockUpdate) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : fgPartStockUpdate) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardEntryNo", ch[0] != null ? ch[0].toString() : "");
			map.put("woNo", ch[1] != null ? ch[1].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getRouteCardEntryDetailsFromFgPartStockUpdate(Long orgId,
			String routeCardEntryNo) {
		Set<Object[]> fgPartStockUpdate = fgPartStockUpdateRepo.findRouteCardEntryDetailsFromFgPartStockUpdate(orgId,
				routeCardEntryNo);
		return getRouteCardEntryDetailsFromFgPartStockUpdate(fgPartStockUpdate);
	}

	private List<Map<String, Object>> getRouteCardEntryDetailsFromFgPartStockUpdate(Set<Object[]> fgPartStockUpdate) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : fgPartStockUpdate) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("qty", ch[2] != null ? ch[2].toString() : "");
			map.put("unit", ch[3] != null ? ch[3].toString() : "");
			map.put("rate", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsFromFgPartStockUpdate(Long orgId, String fgPartName) {
		Set<Object[]> fgPartStockUpdate = fgPartStockUpdateRepo.findItemDetailsFromFgPartStockUpdate(orgId, fgPartName);
		return getItemDetailsFromFgPartStockUpdate(fgPartStockUpdate);
	}

	private List<Map<String, Object>> getItemDetailsFromFgPartStockUpdate(Set<Object[]> fgPartStockUpdate) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : fgPartStockUpdate) {
			Map<String, Object> map = new HashMap<>();
			map.put("part", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("qty", ch[2] != null ? ch[2].toString() : "");
			map.put("unit", ch[3] != null ? ch[3].toString() : "");
//			map.put("rate", ch[4] != null ? ch[4].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPriceDetails(Long orgId, String itemName) {
		Set<Object[]> fgPartStockUpdate = fgPartStockUpdateRepo.getPriceDetails(orgId, itemName);
		return getPriceDetails(fgPartStockUpdate);
	}

	private List<Map<String, Object>> getPriceDetails(Set<Object[]> fgPartStockUpdate) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : fgPartStockUpdate) {
			Map<String, Object> map = new HashMap<>();
			map.put("rate", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<FgIssueToPackingVO> getAllFgIssueToPackingVOByOrgId(Long orgId) {
		List<FgIssueToPackingVO> fgIssueToPackingVO = new ArrayList<>();
		fgIssueToPackingVO = fgIssueToPackingRepo.getAllFgIssueToPackingVOByOrgId(orgId);

		return fgIssueToPackingVO;
	}

	@Override
	public FgIssueToPackingVO getFgIssueToPackingVOById(Long id) {
		FgIssueToPackingVO fgIssueToPackingVO = new FgIssueToPackingVO();

		fgIssueToPackingVO = fgIssueToPackingRepo.getFgIssueToPackingVOById(id);

		return fgIssueToPackingVO;
	}

	@Override
	public String getFgIssueToPackingDocId(Long orgId) {
		String screenCode = "FIP";
		String result = fgIssueToPackingRepo.getFgIssueToPackingDocId(orgId, screenCode);
		return result;
	}

	@Override
	public Map<String, Object> updateCreateFgIssueToPacking(@Valid FgIssueToPackingDTO fgIssueToPackingDTO)
			throws ApplicationException {
		String message;
		String screenCode = "FIP";
		FgIssueToPackingVO oldFgIssueToPacking    = null;

		FgIssueToPackingVO fgIssueToPackingVO = new FgIssueToPackingVO();

		if (fgIssueToPackingDTO.getId() != null) {
			oldFgIssueToPacking = fgIssueToPackingRepo.findById(fgIssueToPackingDTO.getId())
		            .orElseThrow(() -> new ApplicationException("fgIssueToPacking not found"));

			oldFgIssueToPacking.getFgIssueToPackingDetailsVO().size(); // load
			
		    entityManager.detach(oldFgIssueToPacking); // detach snapshot
			
		    // Fetch existing ItemVO for update
			fgIssueToPackingVO = fgIssueToPackingRepo.findById(fgIssueToPackingDTO.getId())
					.orElseThrow(() -> new ApplicationException("FgIssueToPacking master not found"));
			fgIssueToPackingVO.setUpdatedBy(fgIssueToPackingDTO.getCreatedBy());

			createUpdateFgIssueToPackingVOByFgIssueToPackingDTO(fgIssueToPackingDTO, fgIssueToPackingVO);
			message = "FgIssueToPacking Master Updated Successfully";

			List<FgIssueToPackingDetailsVO> fgIssueToPackingDetailsVOs = fgIssueToPackingDetailsRepo
					.findByFgIssueToPackingVO(fgIssueToPackingVO);
			fgIssueToPackingDetailsRepo.deleteAll(fgIssueToPackingDetailsVOs);

		} else {

			// GETDOCID API
			String docId = fgIssueToPackingRepo.getFgIssueToPackingDocId(fgIssueToPackingDTO.getOrgId(), screenCode);

			fgIssueToPackingVO.setDocId(docId);

//        							// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndScreenCode(fgIssueToPackingDTO.getOrgId(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			// Create new ItemVO
			fgIssueToPackingVO.setCreatedBy(fgIssueToPackingDTO.getCreatedBy());
			fgIssueToPackingVO.setUpdatedBy(fgIssueToPackingDTO.getCreatedBy());
			createUpdateFgIssueToPackingVOByFgIssueToPackingDTO(fgIssueToPackingDTO, fgIssueToPackingVO);
			message = "FgIssueToPacking Master Created Successfully";
		}

		// Save the ItemVO
		fgIssueToPackingRepo.save(fgIssueToPackingVO);
		
		commonNotificationService.generateNotification(fgIssueToPackingVO.getScreenCode(), fgIssueToPackingVO.getId(), oldFgIssueToPacking, fgIssueToPackingVO);


		// Prepare response
		Map<String, Object> response = new HashMap<>();
		response.put("fgIssueToPackingVO", fgIssueToPackingVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateFgIssueToPackingVOByFgIssueToPackingDTO(@Valid FgIssueToPackingDTO fgIssueToPackingDTO,
			FgIssueToPackingVO fgIssueToPackingVO) {
		fgIssueToPackingVO.setFromDept(fgIssueToPackingDTO.getFromDept());
		fgIssueToPackingVO.setToDept(fgIssueToPackingDTO.getToDept());
		fgIssueToPackingVO.setRouteCardNo(fgIssueToPackingDTO.getRouteCardNo());
		fgIssueToPackingVO.setOrgId(fgIssueToPackingDTO.getOrgId());
		fgIssueToPackingVO.setApprovedBy(fgIssueToPackingDTO.getApprovedBy());
		fgIssueToPackingVO.setRemarks(fgIssueToPackingDTO.getRemarks());
		fgIssueToPackingVO.setNarration(fgIssueToPackingDTO.getNarration());

		List<FgIssueToPackingDetailsVO> fgIssueToPackingDetailsVOs = new ArrayList<>();
		for (FgIssueToPackingDetailsDTO fgIssueToPackingDetailsDTO : fgIssueToPackingDTO
				.getFgIssueToPackingDetailsDTO()) {
			FgIssueToPackingDetailsVO fgIssueToPackingDetailsVO = new FgIssueToPackingDetailsVO();
			fgIssueToPackingDetailsVO.setPartName(fgIssueToPackingDetailsDTO.getPartName());
			fgIssueToPackingDetailsVO.setPartDesc(fgIssueToPackingDetailsDTO.getPartDesc());
			fgIssueToPackingDetailsVO.setTotalQty(fgIssueToPackingDetailsDTO.getTotalQty());
			fgIssueToPackingDetailsVO.setIssueQty(fgIssueToPackingDetailsDTO.getIssueQty());

			fgIssueToPackingDetailsVO.setFgIssueToPackingVO(fgIssueToPackingVO);
			fgIssueToPackingDetailsVOs.add(fgIssueToPackingDetailsVO);
		}
		fgIssueToPackingVO.setFgIssueToPackingDetailsVO(fgIssueToPackingDetailsVOs);

	}

	@Override
	public List<Map<String, Object>> getDeptfromFgIssueToPacking(Long orgId) {
		Set<Object[]> dept = fgIssueToPackingRepo.findDeptfromFgIssueToPacking(orgId);
		return getDeptfromFgIssueToPacking(dept);
	}

	private List<Map<String, Object>> getDeptfromFgIssueToPacking(Set<Object[]> dept) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : dept) {
			Map<String, Object> map = new HashMap<>();
			map.put("dept", ch[0] != null ? ch[0].toString() : "");
			List1.add(map);
		}

		return List1;
	}

	@Override
	public List<Map<String, Object>> getRouteCardEntryNoFromFgIssueToPacking(Long orgId) {
		Set<Object[]> routeCardEntryNo = fgIssueToPackingRepo.findRouteCardEntryNoFromFgIssueToPacking(orgId);
		return getRouteCardEntryNoFromFgIssueToPacking(routeCardEntryNo);
	}

	private List<Map<String, Object>> getRouteCardEntryNoFromFgIssueToPacking(Set<Object[]> routeCardEntryNo) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : routeCardEntryNo) {
			Map<String, Object> map = new HashMap<>();
			map.put("routeCardEntryNo", ch[0] != null ? ch[0].toString() : "");

			List1.add(map);
		}
		return List1;
	}

	@Override
	public List<Map<String, Object>> getPartNameAndDesc(Long orgId) {
		Set<Object[]> itemDetails = fgPartStockUpdateRepo.getPartNameAndDesc(orgId);
		return getPartNameAndDesc(itemDetails);
	}

	private List<Map<String, Object>> getPartNameAndDesc(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgId", ch[0] != null ? ch[0].toString() : "");
			map.put("itemName", ch[1] != null ? ch[1].toString() : "");
			map.put("itemDesc", ch[2] != null ? ch[2].toString() : "");
			map.put("itemNameAndDesc", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;
	}
	
	@Override
	public List<Map<String, Object>> getFinalFgPartStockUpdateReport(Long orgId, String fromDate,String toDate,String partName) {
		Set<Object[]> itemDetails = fgPartStockUpdateRepo.getFinalFgPartStockUpdateReport( orgId,  fromDate, toDate, partName);
		return getFinalFgPartStockUpdateReport(itemDetails);
	}

	private List<Map<String, Object>> getFinalFgPartStockUpdateReport(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgId", ch[0] != null ? ch[0].toString() : "");
			map.put("finalFgPartStockUpdateId", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			map.put("routeCardNo", ch[3] != null ? ch[3].toString() : "");
			map.put("workorderNo", ch[4] != null ? ch[4].toString() : "");
			map.put("tolocation", ch[5] != null ? ch[5].toString() : "");
			map.put("part", ch[6] != null ? ch[6].toString() : "");
			map.put("partdesc", ch[7] != null ? ch[7].toString() : "");
			map.put("qty", ch[8] != null ? ch[8].toString() : "");
			map.put("unit", ch[9] != null ? ch[9].toString() : "");
			map.put("partname", ch[10] != null ? ch[10].toString() : "");
			map.put("docId", ch[11] != null ? ch[11].toString() : "");
			List1.add(map);
		}
		return List1;
	}
	
	
	@Override
	public List<Map<String, Object>> getFgIssueToPackingReport(Long orgId, String fromDate,String toDate,String routeCardNo) {
		Set<Object[]> itemDetails = fgIssueToPackingRepo.getFgIssueToPackingReport( orgId,  fromDate, toDate, routeCardNo);
		return getFgIssueToPackingReport(itemDetails);
	}

	private List<Map<String, Object>> getFgIssueToPackingReport(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("orgId", ch[0] != null ? ch[0].toString() : "");
			map.put("fgissuetopackingid", ch[1] != null ? ch[1].toString() : "");
			map.put("docDate", ch[2] != null ? ch[2].toString() : "");
			map.put("fromdept", ch[3] != null ? ch[3].toString() : "");
			map.put("todept", ch[4] != null ? ch[4].toString() : "");
			map.put("routecardno", ch[5] != null ? ch[5].toString() : "");
			map.put("partname", ch[6] != null ? ch[6].toString() : "");
			map.put("partdesc", ch[7] != null ? ch[7].toString() : "");
			map.put("issueqty", ch[8] != null ? ch[8].toString() : "");
			map.put("docId", ch[9] != null ? ch[9].toString() : "");
			List1.add(map);
		}
		return List1;
	}
	
	
	@Override
	public List<Map<String, Object>> getItemDetailsFromFgIssueToPacking(Long orgId, String routeCardEntryNo) {
		Set<Object[]> itemDetails = fgIssueToPackingRepo.findItemDetailsFromFgIssueToPacking(orgId, routeCardEntryNo);
		return getItemDetailsFromFgIssueToPacking(itemDetails);
	}

	private List<Map<String, Object>> getItemDetailsFromFgIssueToPacking(Set<Object[]> itemDetails) {
		List<Map<String, Object>> List1 = new ArrayList<>();
		for (Object[] ch : itemDetails) {
			Map<String, Object> map = new HashMap<>();
			map.put("partName", ch[0] != null ? ch[0].toString() : "");
			map.put("partDesc", ch[1] != null ? ch[1].toString() : "");
			map.put("primaryUnit", ch[2] != null ? ch[2].toString() : "");
			map.put("totalqty", ch[3] != null ? ch[3].toString() : "");

			List1.add(map);
		}
		return List1;
	}
}
