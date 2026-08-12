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
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractDetailResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.SalesContractAmdDetailsDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDetailsDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesDeliverySchedulePlanDTO;
import com.efitops.basesetup.dto.SalesDeliverySchedulePlanResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.SalesContractAmdDetailsVO;
import com.efitops.basesetup.entity.SalesContractAmendmentVO;
import com.efitops.basesetup.entity.SalesDeliveryScheduleDetailsVO;
import com.efitops.basesetup.entity.SalesDeliverySchedulePlanVO;
import com.efitops.basesetup.entity.SalesDeliveryScheduleVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.SalesContractAmdDetailsRepo;
import com.efitops.basesetup.repository.SalesContractAmdRepo;
import com.efitops.basesetup.repository.SalesContractDetailsRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleDetailsRepo;
import com.efitops.basesetup.repository.SalesDeliverySchedulePlanRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

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
	
	@Autowired
	SalesContractAmdRepo salesContractAmendmentRepo;
	
	@Autowired
	SalesContractAmdDetailsRepo salesContractAmdDetailsRepo;
	
	@Autowired
    UnitMasterRepo 	unitMasterRepo;

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

	        if (dto.getItem() != null) {

	            ItemMasterVO item =
	                    itemMasterRepo.findById(dto.getItem())
	                    .orElseThrow(() ->
	                            new ApplicationException("Item Not Found"));

	            detail.setItem(item);
	        }
	        
	        if (dto.getUnit() != null) {

	            UnitMasterVO unit =
	                    unitMasterRepo.findById(dto.getUnit())
	                    .orElseThrow(() ->
	                            new ApplicationException("Unit Not Found"));

	            detail.setUnit(unit);
	        }
	       
	        detail.setOrderQty(dto.getOrderQty());
	        detail.setPendingQty(dto.getPendingQty());
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

//	        if (dto.getId() != null)
//	            plan.setId(dto.getId());

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
	            detailResponse.setOrderQty(
	                    detailVO.getOrderQty());
	            detailResponse.setPendingQty(
	                    detailVO.getPendingQty());
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
	public List<SalesDeliveryScheduleResponseDTO> getAllSalesDeliverySchedule(
	        Long orgId,
	        Long branch) throws ApplicationException {

	    List<SalesDeliveryScheduleVO> scheduleList =
	            salesDeliveryScheduleRepo.findByOrgIdAndBranch(orgId, branch);

	    List<SalesDeliveryScheduleResponseDTO> responseList =
	            new ArrayList<>();

	    for (SalesDeliveryScheduleVO scheduleVO : scheduleList) {
	        responseList.add(
	                buildSalesDeliveryScheduleResponse(scheduleVO)
	        );
	    }

	    return responseList;
	}

	@Override
	public Map<String, Object> getSalesDeliveryScheduleByItemDropdown(String docId,Long orgId, Long branch) throws ApplicationException {

		String methodName = "getItemDropdown";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		List<Object[]> list = salesContractDetailsRepo.getSalesDeliveryScheduleByItemDropdown(docId , orgId , branch);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemId", obj[0]);
			map.put("itemCode", obj[1]);
			map.put("itemDescription", obj[2]);
			map.put("unit", obj[3]);
			map.put("orderQty", obj[4]);
			map.put("itemId", obj[5]);
			map.put("unitId", obj[6]);

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
	
	
	// Sales Contract amendment

		@Override
		@Transactional
		public Map<String, Object> updateCreateSalesContractAmendment(
				SalesContractAmendmentDTO salesContractAmendmentDTO) throws ApplicationException {

			SalesContractAmendmentVO salesContractAmendmentVO = new SalesContractAmendmentVO();

			String message;

			if (salesContractAmendmentDTO.getId() != null) {

				salesContractAmendmentVO = salesContractAmendmentRepo.findById(salesContractAmendmentDTO.getId())
						.orElseThrow(() -> new ApplicationException("Invalid Sales Contract Amendment Details"));

				salesContractAmendmentVO.setUpdated_By(salesContractAmendmentDTO.getCreatedBy());

				message = "Sales Contract Amendment Updated Successfully";

			} else {

				salesContractAmendmentVO.setCreatedBy(salesContractAmendmentDTO.getCreatedBy());
				salesContractAmendmentVO.setUpdated_By(salesContractAmendmentDTO.getCreatedBy());

				message = "Sales Contract Amendment Created Successfully";
			}
			createUpdateSalesContractAmendmentVO(
			        salesContractAmendmentDTO,
			        salesContractAmendmentVO);

			SalesContractAmendmentVO savedSalesContractAmendment =
			        salesContractAmendmentRepo.save(salesContractAmendmentVO);

			Map<String, Object> response = new HashMap<>();
			response.put("message", message);
			response.put("salesContractAmendmentVO", salesContractResponseResponse(savedSalesContractAmendment));


			return response;
		}
		private SalesContractAmdResponseDTO salesContractResponseResponse(
				SalesContractAmendmentVO salesContractAmendmentVO) {

			SalesContractAmdResponseDTO responseDTO = new SalesContractAmdResponseDTO();

			responseDTO.setId(salesContractAmendmentVO.getId());
			responseDTO.setContractAmdNo(salesContractAmendmentVO.getDocId());
			responseDTO.setDate(salesContractAmendmentVO.getDocDate());
			responseDTO.setContractNo(salesContractAmendmentVO.getContractNo());
			responseDTO.setContractDate(salesContractAmendmentVO.getContractDate());
			responseDTO.setPartyPoAmdNo(salesContractAmendmentVO.getPartyPoAmdNo());
			responseDTO.setPartyPoAmdDate(salesContractAmendmentVO.getPartyPoAmdDate());
			responseDTO.setCustPoNo(salesContractAmendmentVO.getCustPoNo());
			responseDTO.setCustPoDate(salesContractAmendmentVO.getCustPoDate());
			responseDTO.setRevisionNo(salesContractAmendmentVO.getRevisionNo());
			responseDTO.setRemarks(salesContractAmendmentVO.getRemarks());

			if (salesContractAmendmentVO.getBranch() != null) {

				BranchResponseDTO branchDTO = new BranchResponseDTO();

				branchDTO.setId(salesContractAmendmentVO.getBranch().getId());
				branchDTO.setBranchName(salesContractAmendmentVO.getBranch().getBranchName());

				responseDTO.setBranch(branchDTO);
			}

			responseDTO.setOrgId(salesContractAmendmentVO.getOrgId());
			responseDTO.setCreatedBy(salesContractAmendmentVO.getCreatedBy());
			responseDTO.setCancelRemarks(salesContractAmendmentVO.getCancelRemarks());

			List<SalesContractDetailResponseDTO> detailResponseList = new ArrayList<>();

			if (salesContractAmendmentVO.getSalesContractAmdDetailsVO() != null
					&& !salesContractAmendmentVO.getSalesContractAmdDetailsVO().isEmpty()) {

				for (SalesContractAmdDetailsVO detailVO : salesContractAmendmentVO.getSalesContractAmdDetailsVO()) {

					SalesContractDetailResponseDTO detailDTO = new SalesContractDetailResponseDTO();

					detailDTO.setId(detailVO.getId());

					if (detailVO.getItem() != null) {

						ItemResponse1DTO itemDTO = new ItemResponse1DTO();

						itemDTO.setId(detailVO.getItem().getId());
						itemDTO.setItemCode(detailVO.getItem().getItemCode());
						itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

						detailDTO.setItem(itemDTO);
					}

					detailDTO.setOldRate(detailVO.getOldRate());
					detailDTO.setNewRate(detailVO.getNewRate());
					detailDTO.setValidFrom(detailVO.getValidFrom());
					detailDTO.setValidTo(detailVO.getValidTo());
					detailDTO.setNewValidDate(detailVO.getNewValidDate());

					detailResponseList.add(detailDTO);
				}
			}

			responseDTO.setSalesContractDetailResponseDTO(detailResponseList);

			return responseDTO;
		}
		private void createUpdateSalesContractAmendmentVO(
				SalesContractAmendmentDTO dto,
				SalesContractAmendmentVO salesContractAmendmentVO)
				throws ApplicationException {

			salesContractAmendmentVO.setContractNo(dto.getContractNo());
			salesContractAmendmentVO.setContractDate(dto.getContractDate());
			salesContractAmendmentVO.setPartyPoAmdNo(dto.getPartyPoAmdNo());
			salesContractAmendmentVO.setPartyPoAmdDate(dto.getPartyPoAmdDate());
			salesContractAmendmentVO.setContractNo(dto.getContractNo());
			salesContractAmendmentVO.setContractDate(dto.getContractDate());
			salesContractAmendmentVO.setCustPoNo(dto.getCustPoNo());
			salesContractAmendmentVO.setCustPoDate(dto.getCustPoDate());
			salesContractAmendmentVO.setRevisionNo(dto.getRevisionNo());
			salesContractAmendmentVO.setRemarks(dto.getRemarks());

			salesContractAmendmentVO.setOrgId(dto.getOrgId());
			salesContractAmendmentVO.setActive(dto.isActive());
			salesContractAmendmentVO.setCancelRemarks(dto.getCancelRemarks());

			if (dto.getBranch() != null && dto.getBranch() != 0) {

				BranchVO branch = branchRepo.findById(dto.getBranch())
						.orElseThrow(() -> new ApplicationException("Branch Not Found"));

				salesContractAmendmentVO.setBranch(branch);
			}

			//----------------------------------------------------
			// Delete old child records while updating
			//----------------------------------------------------

			if (dto.getId() != null) {

				List<SalesContractAmdDetailsVO> oldList = salesContractAmdDetailsRepo
						.findBySalesContractAmendmentVO(salesContractAmendmentVO);

				salesContractAmdDetailsRepo.deleteAll(oldList);
			}

			List<SalesContractAmdDetailsVO> detailList = new ArrayList<>();

			if (dto.getSalesContractAmdDetailsDTO() != null
					&& !dto.getSalesContractAmdDetailsDTO().isEmpty()) {

				for (SalesContractAmdDetailsDTO detailDTO : dto.getSalesContractAmdDetailsDTO()) {

					SalesContractAmdDetailsVO detailVO = new SalesContractAmdDetailsVO();

					if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

						ItemMasterVO item = itemMasterRepo.findById(detailDTO.getItem())
								.orElseThrow(() -> new ApplicationException("Item Not Found"));

						detailVO.setItem(item);
					}

					detailVO.setOldRate(detailDTO.getOldRate());
					detailVO.setNewRate(detailDTO.getNewRate());
					detailVO.setValidFrom(detailDTO.getValidFrom());
					detailVO.setValidTo(detailDTO.getValidTo());
					detailVO.setNewValidDate(detailDTO.getNewValidDate());

					// Parent Mapping
					detailVO.setSalesContractAmendmentVO(salesContractAmendmentVO);

					detailList.add(detailVO);
				}

				// Set child list to parent
				salesContractAmendmentVO.setSalesContractAmdDetailsVO(detailList);
			}
		}
		
		@Override
		public SalesContractAmdResponseDTO getSalesContractAmendmentById(Long id)
				throws ApplicationException {

			if (ObjectUtils.isEmpty(id)) {
				throw new ApplicationException("Invalid Id");
			}

			SalesContractAmendmentVO salesContractAmendmentVO = salesContractAmendmentRepo.findById(id)
					.orElseThrow(() -> new ApplicationException("Sales Contract Amendment Not Found"));

			return salesContractResponseResponse(salesContractAmendmentVO);
		}
		
		@Override
		public List<SalesContractAmdResponseDTO> getSalesContractAmendmentByOrgId(Long orgId,Long branch)
		        throws ApplicationException {

		    List<SalesContractAmendmentVO> salesContractAmendmentList =
		            salesContractAmendmentRepo.getSalesContractAmendmentByOrgId(orgId,branch);

		    if (salesContractAmendmentList.isEmpty()) {
		        throw new ApplicationException("No Sales Contract Amendment Details Found");
		    }

		    List<SalesContractAmdResponseDTO> responseList = new ArrayList<>();

		    for (SalesContractAmendmentVO salesContractAmendmentVO : salesContractAmendmentList) {

		        responseList.add(
		                salesContractResponseResponse(salesContractAmendmentVO));
		    }

		    return responseList;
		}
		
		//dropdown for sales Contract Amendment
		
		@Override
		public Map<String, Object> getSalesContractAmdContractNoDropdown(Long orgId, Long branch)
		        throws ApplicationException {

		    List<Object[]> contractList =
		            salesContractRepo.getSalesContractAmdContractNoDropdown(orgId, branch);

		    Map<String, Object> responseMap = new HashMap<>();
		    responseMap.put("message", "Contract No Dropdown Loaded Successfully");
		    responseMap.put("contractList", getContractDetails(contractList));

		    return responseMap;
		}
		
		private List<Map<String, Object>> getContractDetails(List<Object[]> contractList) {

		    List<Map<String, Object>> responseList = new ArrayList<>();

		    for (Object[] obj : contractList) {

		        Map<String, Object> map = new HashMap<>();

		        map.put("id", obj[0] != null ? obj[0] : "");
		        map.put("contractNo", obj[1] != null ? obj[1].toString() : "");
		        map.put("custPoNo", obj[2] != null ? obj[2].toString() : "");
		        map.put("custPoDate", obj[3] != null ? obj[3] : "");
		        map.put("contractDate", obj[4] != null ? obj[4] : "");

		        responseList.add(map);
		    }

		    return responseList;
		}
		
		@Override
		public Map<String, Object> getSalesContractAmdItemDropdown(String salesContractNo,
		                                           Long orgId,
		                                           Long branch)
		        throws ApplicationException {

		    String methodName = "getItemDropdown()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		    List<Object[]> itemList =
		            salesContractRepo.getSalesContractAmdItemDropdown(salesContractNo, orgId, branch);

		    Map<String, Object> responseMap = new HashMap<>();
		    responseMap.put("message", "Item Dropdown Loaded Successfully");
		    responseMap.put("itemList", getSalesContractAmdItemDropdown(itemList));

		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		    return responseMap;
		}
		
		private List<Map<String, Object>> getSalesContractAmdItemDropdown(List<Object[]> itemList) {

		    List<Map<String, Object>> responseList = new ArrayList<>();

		    for (Object[] obj : itemList) {

		        Map<String, Object> map = new HashMap<>();

		        map.put("itemId", obj[0]);
		        map.put("itemCode", obj[1]);
		        map.put("itemDescription", obj[2]);
		        map.put("newRate", obj[3]);

		        responseList.add(map);
		    }

		    return responseList;
		}
		
		@Override
		public Map<String, Object> getSalesContractAmdRevisionNo(String salesContractNo,
		                                         Long item,
		                                         Long orgId,
		                                         Long branch)
		        throws ApplicationException {

		    Integer revisionNo =
		            salesContractRepo.getSalesContractAmdRevisionNo(salesContractNo, item, orgId, branch);

		    Map<String, Object> responseMap = new HashMap<>();
		    responseMap.put("message", "Revision No Loaded Successfully");
		    responseMap.put("revisionNo", revisionNo);

		    return responseMap;
		}
		
}
