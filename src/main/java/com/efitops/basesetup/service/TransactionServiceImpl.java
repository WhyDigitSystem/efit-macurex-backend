package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDetailsDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesDeliverySchedulePlanDTO;
import com.efitops.basesetup.dto.SalesDeliverySchedulePlanResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.SalesContractDetailsVO;
import com.efitops.basesetup.entity.SalesContractVO;
import com.efitops.basesetup.entity.SalesDeliveryScheduleDetailsVO;
import com.efitops.basesetup.entity.SalesDeliverySchedulePlanVO;
import com.efitops.basesetup.entity.SalesDeliveryScheduleVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.SalesContractDetailsRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleDetailsRepo;
import com.efitops.basesetup.repository.SalesDeliverySchedulePlanRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleRepo;

@Service
public class TransactionServiceImpl implements TransactionService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	SalesContractRepo salesContractRepo;

	@Autowired
	SalesDeliveryScheduleRepo salesDeliveryScheduleRepo;

	@Autowired
	SalesDeliveryScheduleDetailsRepo salesDeliveryScheduleDetailsRepo;

	@Autowired
	SalesDeliverySchedulePlanRepo salesDeliverySchedulePlanRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	SalesContractDetailsRepo salesContractDetailsRepo;

	@Autowired
	ItemMasterRepo itemMasterRepo;

	// salesdeliveryschedule

	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesDeliverySchedule(SalesDeliveryScheduleDTO salesDeliveryScheduleDTO)
			throws ApplicationException {

		Map<String, Object> response = new HashMap<>();

		String message;

		SalesDeliveryScheduleVO salesDeliveryScheduleVO;

		if (ObjectUtils.isEmpty(salesDeliveryScheduleDTO.getId())) {

			salesDeliveryScheduleVO = new SalesDeliveryScheduleVO();

			salesDeliveryScheduleVO.setCreatedBy(salesDeliveryScheduleDTO.getCreatedBy());

			salesDeliveryScheduleVO.setUpdatedBy(salesDeliveryScheduleDTO.getCreatedBy());

			message = "Sales Delivery Schedule Created Successfully";

		} else {

			salesDeliveryScheduleVO = salesDeliveryScheduleRepo.findById(salesDeliveryScheduleDTO.getId())
					.orElseThrow(() -> new ApplicationException("Sales Delivery Schedule Not Found"));

			salesDeliveryScheduleVO.setUpdatedBy(salesDeliveryScheduleDTO.getCreatedBy());

			if (salesDeliveryScheduleDTO.getId() != null) {

			    salesDeliveryScheduleVO = salesDeliveryScheduleRepo.findById(salesDeliveryScheduleDTO.getId())
			            .orElseThrow(() -> new ApplicationException("Sales Delivery Schedule Not Found"));

			    // Delete all plans first
			    for (SalesDeliveryScheduleDetailsVO detail : salesDeliveryScheduleVO.getDetails()) {
			        salesDeliverySchedulePlanRepo.deleteAll(detail.getDeliverySchedules());
			    }

			    // Delete all details
			    salesDeliveryScheduleDetailsRepo.deleteAll(salesDeliveryScheduleVO.getDetails());

			    salesDeliveryScheduleVO.getDetails().clear();
			}
			
			message = "Sales Delivery Schedule Updated Successfully";
		}

		// Map Header + Details + Delivery Plans
		createUpdateSalesDeliveryScheduleVOByDTO(salesDeliveryScheduleDTO, salesDeliveryScheduleVO);

		// Cascade saves everything
		salesDeliveryScheduleVO = salesDeliveryScheduleRepo.save(salesDeliveryScheduleVO);

		SalesDeliveryScheduleResponseDTO responseDTO = buildSalesDeliveryScheduleResponse(salesDeliveryScheduleVO);

		response.put("message", message);
		response.put("salesDeliverySchedule", responseDTO);

		return response;
	}

	private void createUpdateSalesDeliveryScheduleVOByDTO(
	        SalesDeliveryScheduleDTO dto,
	        SalesDeliveryScheduleVO vo) throws ApplicationException {

	    // Header
//	    vo.setDlvNo(dto.getDlvNo());
//	    vo.setDlvDate(dto.getDlvDate());
	    vo.setMonthOfSchedule(dto.getMonthOfSchedule());
	    vo.setBelongsTo(dto.getBelongsTo());
	    vo.setMonthYear(dto.getMonthYear());
	    vo.setRemarks(dto.getRemarks());
	    vo.setOrgId(dto.getOrgId());
	    vo.setFinancialYear(dto.getFinancialYear());
	    vo.setCancelRemarks(dto.getCancelRemarks());

	    if (dto.getActive() != null)
	        vo.setActive(dto.getActive());

	    // Branch
	    if (dto.getBranch() != null) {
	        BranchVO branch = branchRepo.findById(dto.getBranch())
	                .orElseThrow(() -> new ApplicationException("Branch Not Found"));
	        vo.setBranch(branch);
	    }

	    // Customer
	    if (dto.getCustomer() != null) {
	        CustomerVO customer = customerRepo.findById(dto.getCustomer())
	                .orElseThrow(() -> new ApplicationException("Customer Not Found"));
	        vo.setCustomer(customer);
	    }

	    // Detail Mapping
	    vo.setDetails(createDetails(dto.getDetails(), vo));
	}
	
	private List<SalesDeliveryScheduleDetailsVO> createDetails(
	        List<SalesDeliveryScheduleDetailsDTO> detailDTOs,
	        SalesDeliveryScheduleVO header)
	        throws ApplicationException {

	    List<SalesDeliveryScheduleDetailsVO> details = new ArrayList<>();

	    if (detailDTOs == null)
	        return details;

	    for (SalesDeliveryScheduleDetailsDTO dto : detailDTOs) {

	        SalesDeliveryScheduleDetailsVO detail =
	                new SalesDeliveryScheduleDetailsVO();
//
//	        if (dto.getSalesContractId() != null) {
//
//	            SalesContractVO salesContract =
//	                    salesContractRepo.findById(dto.getSalesContractId())
//	                    .orElseThrow(() ->
//	                            new ApplicationException("Sales Contract Not Found"));
//
//	            detail.setSalesContract(salesContract);
//	        }
//
//	        if (dto.getSalesContractDetailsId() != null) {
//
//	            SalesContractDetailsVO contractDetails =
//	                    salesContractDetailsRepo
//	                    .findById(dto.getSalesContractDetailsId())
//	                    .orElseThrow(() ->
//	                            new ApplicationException(
//	                                    "Sales Contract Detail Not Found"));
//
//	            detail.setSalesContractDetails(contractDetails);
//	        }

	        if (dto.getItemId() != null) {

	            ItemMasterVO item =
	                    itemMasterRepo.findById(dto.getItemId())
	                    .orElseThrow(() ->
	                            new ApplicationException("Item Not Found"));

	            detail.setItem(item);
	        }

	        detail.setActualPlannedQty(dto.getActualPlannedQty());
	        detail.setSoNoContractNo(dto.getSoNoContractNo());
	        detail.setInvoiceType(dto.getInvoiceType());

	        detail.setSalesDeliverySchedule(header);

	        // Delivery Schedule
	        detail.setDeliverySchedules(
	                createPlans(dto.getDeliverySchedules(), detail));

	        details.add(detail);
	    }

	    return details;
	}
	private List<SalesDeliverySchedulePlanVO> createPlans(
	        List<SalesDeliverySchedulePlanDTO> planDTOs,
	        SalesDeliveryScheduleDetailsVO detail) {

	    List<SalesDeliverySchedulePlanVO> plans =
	            new ArrayList<>();

	    if (planDTOs == null)
	        return plans;

	    for (SalesDeliverySchedulePlanDTO dto : planDTOs) {

	        SalesDeliverySchedulePlanVO plan =
	                new SalesDeliverySchedulePlanVO();

	        if (dto.getId() != null)
	            plan.setId(dto.getId());

	        plan.setDayNo(dto.getDayNo());
	        plan.setDeliveryDate(dto.getDeliveryDate());
	        plan.setWeekNo(dto.getWeekNo());
	        plan.setDayName(dto.getDayName());
	        plan.setDeliveryQty(dto.getDeliveryQty());

	        plan.setSalesDeliveryScheduleDetails(detail);

	        plans.add(plan);
	    }

	    return plans;
	}

	private SalesDeliveryScheduleResponseDTO buildSalesDeliveryScheduleResponse(
	        SalesDeliveryScheduleVO vo) {

	    SalesDeliveryScheduleResponseDTO response =
	            new SalesDeliveryScheduleResponseDTO();

	    //================ Header =================

	    response.setId(vo.getId());
	    response.setDlvNo(vo.getDlvNo());
	    response.setDlvDate(vo.getDlvDate());

	    response.setMonthOfSchedule(vo.getMonthOfSchedule());
	    response.setBelongsTo(vo.getBelongsTo());
	    response.setMonthYear(vo.getMonthYear());
	    response.setRemarks(vo.getRemarks());

	    response.setOrgId(vo.getOrgId());
	    response.setFinancialYear(vo.getFinancialYear());

	    response.setCreatedBy(vo.getCreatedBy());
	    response.setUpdatedBy(vo.getUpdatedBy());

	    response.setCancelRemarks(vo.getCancelRemarks());

	    response.setActive(vo.getActive());
	    response.setCancel(vo.getCancel());

	    response.setScreenCode(vo.getScreenCode());
	    response.setScreenName(vo.getScreenName());
	    if (vo.getBranch() != null) {

	        BranchResponseDTO branch = new BranchResponseDTO();

	        branch.setId(vo.getBranch().getId());
	        branch.setBranchCode(vo.getBranch().getBranchCode());
	        branch.setBranchName(vo.getBranch().getBranchName());

	        response.setBranch(branch);
	    }
	    if (vo.getCustomer() != null) {

	    	CustomerDropdownResponseDTO customer =
	                new CustomerDropdownResponseDTO();

	        customer.setCustomerId(vo.getCustomer().getId());
	        customer.setCustomerCode(
	                vo.getCustomer().getCustomerCode());

	        customer.setCustomerName(
	                vo.getCustomer().getCustomerName());

	        response.setCustomer(customer);
	    }
	    
	  //================ Details =================

	    List<SalesDeliveryScheduleDetailsResponseDTO> detailsResponse =
	            new ArrayList<>();

	    if (vo.getDetails() != null) {

	        for (SalesDeliveryScheduleDetailsVO detailVO : vo.getDetails()) {

	            SalesDeliveryScheduleDetailsResponseDTO detailResponse =
	                    new SalesDeliveryScheduleDetailsResponseDTO();

	            detailResponse.setId(detailVO.getId());

	            //================ Contract =================

//	            if (detailVO.getSalesContract() != null) {
//
//	                detailResponse.setContractNo(
//	                        detailVO.getSalesContract().getCustomerContractNo());
//
//	                detailResponse.setInvoiceType(
//	                        detailVO.getSalesContract().getInvoiceType());
//	            }
//
//	            //================ Qty =================
//
//	            if (detailVO.getSalesContractDetails() != null
//	                    && detailVO.getSalesContractDetails().getQuantity() != null) {
//
//	                Double qty = detailVO.getSalesContractDetails()
//	                        .getQuantity()
//	                        .doubleValue();
//
//	                detailResponse.setOrderQty(qty);
//
//	                // Temporary
//	                detailResponse.setPendingQty(qty);
//	            }

	            detailResponse.setSoNocontractNo(detailVO.getSoNoContractNo());
	            
	            detailResponse.setInvoiceType(detailVO.getInvoiceType());
	            
	            detailResponse.setActualPlannedQty(
	                    detailVO.getActualPlannedQty());

	            //================ Item =================

	            if (detailVO.getItem() != null) {

	                ItemResponseDTO item = new ItemResponseDTO();

	                item.setId(detailVO.getItem().getId());

	                item.setItemCode(detailVO.getItem().getItemCode());

	                item.setItemDescription(
	                        detailVO.getItem().getItemDescription());

	                if (detailVO.getItem().getPrimaryUnit() != null) {

	                    UnitResponseDTO unit = new UnitResponseDTO();

	                    unit.setId(
	                            detailVO.getItem().getPrimaryUnit().getId());

	                    unit.setUnitId(
	                            detailVO.getItem()
	                                    .getPrimaryUnit()
	                                    .getDescription());

	                    item.setUnit(unit);
	                }

	                detailResponse.setItem(item);
	            }

	            //================ Delivery Schedule =================

	            List<SalesDeliverySchedulePlanResponseDTO> planResponseList =
	                    new ArrayList<>();

	            if (detailVO.getDeliverySchedules() != null) {

	                for (SalesDeliverySchedulePlanVO planVO
	                        : detailVO.getDeliverySchedules()) {

	                    SalesDeliverySchedulePlanResponseDTO planResponse =
	                            new SalesDeliverySchedulePlanResponseDTO();

	                    planResponse.setId(planVO.getId());
	                    planResponse.setDayNo(planVO.getDayNo());
	                    planResponse.setDeliveryDate(planVO.getDeliveryDate());
	                    planResponse.setWeekNo(planVO.getWeekNo());
	                    planResponse.setDayName(planVO.getDayName());
	                    planResponse.setDeliveryQty(planVO.getDeliveryQty());

	                    planResponseList.add(planResponse);
	                }
	            }

	            detailResponse.setDeliverySchedules(planResponseList);

	            detailsResponse.add(detailResponse);
	        }
	    }

	    response.setDetails(detailsResponse);

	    return response;
	}

	@Override
	public SalesDeliveryScheduleResponseDTO getSalesDeliveryScheduleById(Long id) throws ApplicationException {

		SalesDeliveryScheduleVO salesDeliveryScheduleVO = salesDeliveryScheduleRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Sales Delivery Schedule Not Found"));

		return buildSalesDeliveryScheduleResponse(salesDeliveryScheduleVO);
	}

	@Override
	public List<SalesDeliveryScheduleResponseDTO> getAllSalesDeliverySchedule(Long orgId, Long branchId)
			throws ApplicationException {

		List<SalesDeliveryScheduleVO> scheduleList = salesDeliveryScheduleRepo.findByOrgIdAndBranch(orgId, branchId);

		List<SalesDeliveryScheduleResponseDTO> responseList = new ArrayList<>();

		for (SalesDeliveryScheduleVO scheduleVO : scheduleList) {

			responseList.add(buildSalesDeliveryScheduleResponse(scheduleVO));
		}

		return responseList;
	}

	@Override
	public Map<String, Object> getItemDropdown(String docId) throws ApplicationException {

		String methodName = "getItemDropdown";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		List<Object[]> list = salesContractDetailsRepo.getItemDropdown(docId);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemId", obj[0]);
			map.put("itemCode", obj[1]);
			map.put("itemDescription", obj[2]);
			map.put("unit", obj[3]);
			map.put("orderQty", obj[4]);

			responseList.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Item Dropdown Loaded Successfully");
		response.put("itemList", responseList);

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return response;
	}

	@Override
	public Map<String, Object> getContractNo(Long orgId, Long branch) throws ApplicationException {

		String methodName = "getContractNo";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		List<Map<String, Object>> responseList = new ArrayList<>();

		List<Map<String, Object>> contractList = salesContractRepo.getDocIdAndInvoiceType(orgId, branch);

		for (Map<String, Object> data : contractList) {

			Map<String, Object> map = new HashMap<>();

			map.put("contractNo", data.get("docId"));
			map.put("invoiceType", data.get("invoiceType"));

			responseList.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Contract No Dropdown Loaded Successfully");
		response.put("contractList", responseList);

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return response;
	}

	@Override
	public Map<String, Object> getAllCustomerDetails(Long orgId, Long branch) throws ApplicationException {

		Map<String, Object> map = new HashMap<>();

		List<Map<String, Object>> customerList = customerRepo.getAllCustomerDetails(orgId, branch);

		map.put("customerDetails", customerList);

		return map;
	}

}
