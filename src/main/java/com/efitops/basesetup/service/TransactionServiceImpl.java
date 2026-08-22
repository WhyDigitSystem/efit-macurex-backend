package com.efitops.basesetup.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.CustomerDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.CustomerShipingResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceDetResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocketInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.ItemResponse1DTO;
import com.efitops.basesetup.ResponseDTO.ItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.ListOfValuesDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractAmdResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractDetailResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesRejectionInvoiceDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesRejectionInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesRejectionInvoiceTaxDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.TransportResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.DocketInvoiceDTO;
import com.efitops.basesetup.dto.DocketInvoiceDetailsDTO;
import com.efitops.basesetup.dto.SalesContractAmdDetailsDTO;
import com.efitops.basesetup.dto.SalesContractAmendmentDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDetailsDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleDetailsResponseDTO;
import com.efitops.basesetup.dto.SalesDeliverySchedulePlanDTO;
import com.efitops.basesetup.dto.SalesDeliverySchedulePlanResponseDTO;
import com.efitops.basesetup.dto.SalesDeliveryScheduleResponseDTO;
import com.efitops.basesetup.dto.SalesRejectionInvoiceDTO;
import com.efitops.basesetup.dto.SalesRejectionInvoiceDetailsDTO;
import com.efitops.basesetup.dto.SalesRejectionInvoiceTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerShippingDetailsVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DocketInvoiceDetailsVO;
import com.efitops.basesetup.entity.DocketInvoiceVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.SalesContractAmdDetailsVO;
import com.efitops.basesetup.entity.SalesContractAmendmentVO;
import com.efitops.basesetup.entity.SalesDeliveryScheduleDetailsVO;
import com.efitops.basesetup.entity.SalesDeliverySchedulePlanVO;
import com.efitops.basesetup.entity.SalesDeliveryScheduleVO;
import com.efitops.basesetup.entity.SalesRejectionInvoiceDetailsVO;
import com.efitops.basesetup.entity.SalesRejectionInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.SalesRejectionInvoiceVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DespatchInstructionRepo;
import com.efitops.basesetup.repository.DocketInvoiceDetRepo;
import com.efitops.basesetup.repository.DocketInvoiceRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.SalesContractAmdDetailsRepo;
import com.efitops.basesetup.repository.SalesContractAmdRepo;
import com.efitops.basesetup.repository.SalesContractDetailsRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleDetailsRepo;
import com.efitops.basesetup.repository.SalesDeliverySchedulePlanRepo;
import com.efitops.basesetup.repository.SalesDeliveryScheduleRepo;
import com.efitops.basesetup.repository.SalesRejectionInvoiceDetailsRepo;
import com.efitops.basesetup.repository.SalesRejectionInvoiceRepo;
import com.efitops.basesetup.repository.SalesRejectionInvoiceTaxDetailsRepo;
import com.efitops.basesetup.repository.TransportRepo;
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
	UnitMasterRepo unitMasterRepo;

	@Autowired
	private DocketInvoiceRepo docketInvoiceRepo;

	@Autowired
	private DocketInvoiceDetRepo docketInvoiceDetRepo;

	@Autowired
	TransportRepo transportRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	SalesRejectionInvoiceRepo salesRejectionInvoiceRepo;

	@Autowired
	SalesRejectionInvoiceTaxDetailsRepo salesRejectionInvoiceTaxDetailsRepo;

	@Autowired
	SalesRejectionInvoiceDetailsRepo salesRejectionInvoiceDetailsRepo;

	@Autowired
	LocationRepo locationRepo;

	@Autowired
	ListOfValuesDetailsRepo listOfValuesDetailsRepo;

	@Autowired
	CurrencyRepo currencyRepo;

	@Autowired
	DespatchInstructionRepo despatchInstructionRepo;

	// salesdeliveryschedule

	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesDeliverySchedule(SalesDeliveryScheduleDTO salesDeliveryScheduleDTO)
			throws ApplicationException {
		String screenCode = "SDS";

		Map<String, Object> response = new HashMap<>();

		String message;

		SalesDeliveryScheduleVO salesDeliveryScheduleVO;

		if (ObjectUtils.isEmpty(salesDeliveryScheduleDTO.getId())) {

			salesDeliveryScheduleVO = new SalesDeliveryScheduleVO();

			String docId = salesDeliveryScheduleRepo.getSalesDeliveryScheduleDocId(salesDeliveryScheduleDTO.getOrgId(),
					salesDeliveryScheduleDTO.getFinancialYear(), screenCode);

			salesDeliveryScheduleVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(salesDeliveryScheduleDTO.getOrgId(),
							salesDeliveryScheduleDTO.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

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

		createUpdateSalesDeliveryScheduleVOByDTO(salesDeliveryScheduleDTO, salesDeliveryScheduleVO);

		// Cascade saves everything
		salesDeliveryScheduleVO = salesDeliveryScheduleRepo.save(salesDeliveryScheduleVO);

		SalesDeliveryScheduleResponseDTO responseDTO = buildSalesDeliveryScheduleResponse(salesDeliveryScheduleVO);

		response.put("message", message);
		response.put("salesDeliverySchedule", responseDTO);

		return response;
	}

	private void createUpdateSalesDeliveryScheduleVOByDTO(SalesDeliveryScheduleDTO dto, SalesDeliveryScheduleVO vo)
			throws ApplicationException {

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

	private List<SalesDeliveryScheduleDetailsVO> createDetails(List<SalesDeliveryScheduleDetailsDTO> detailDTOs,
			SalesDeliveryScheduleVO header) throws ApplicationException {

		List<SalesDeliveryScheduleDetailsVO> details = new ArrayList<>();

		if (detailDTOs == null)
			return details;

		for (SalesDeliveryScheduleDetailsDTO dto : detailDTOs) {

			SalesDeliveryScheduleDetailsVO detail = new SalesDeliveryScheduleDetailsVO();
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

				ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
						.orElseThrow(() -> new ApplicationException("Item Not Found"));

				detail.setItem(item);
			}

			if (dto.getUnit() != null) {

				UnitMasterVO unit = unitMasterRepo.findById(dto.getUnit())
						.orElseThrow(() -> new ApplicationException("Unit Not Found"));

				detail.setUnit(unit);
			}

			detail.setOrderQty(dto.getOrderQty());
			detail.setPendingQty(dto.getPendingQty());
			detail.setActualPlannedQty(dto.getActualPlannedQty());
			detail.setSoNoContractNo(dto.getSoNoContractNo());
			detail.setInvoiceType(dto.getInvoiceType());

			detail.setSalesDeliverySchedule(header);

			// Delivery Schedule
			detail.setDeliverySchedules(createPlans(dto.getDeliverySchedules(), detail));

			details.add(detail);
		}

		return details;
	}

	private List<SalesDeliverySchedulePlanVO> createPlans(List<SalesDeliverySchedulePlanDTO> planDTOs,
			SalesDeliveryScheduleDetailsVO detail) {

		List<SalesDeliverySchedulePlanVO> plans = new ArrayList<>();

		if (planDTOs == null)
			return plans;

		for (SalesDeliverySchedulePlanDTO dto : planDTOs) {

			SalesDeliverySchedulePlanVO plan = new SalesDeliverySchedulePlanVO();

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

	private SalesDeliveryScheduleResponseDTO buildSalesDeliveryScheduleResponse(SalesDeliveryScheduleVO vo) {

		SalesDeliveryScheduleResponseDTO response = new SalesDeliveryScheduleResponseDTO();

		// ================ Header =================

		response.setId(vo.getId());
		response.setDocId(vo.getDocId());
		response.setDocDate(vo.getDocDate());

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

			CustomerDropdownResponseDTO customer = new CustomerDropdownResponseDTO();

			customer.setCustomerId(vo.getCustomer().getId());
			customer.setCustomerCode(vo.getCustomer().getCustomerCode());

			customer.setCustomerName(vo.getCustomer().getCustomerName());

			response.setCustomer(customer);
		}

		// ================ Details =================

		List<SalesDeliveryScheduleDetailsResponseDTO> detailsResponse = new ArrayList<>();

		if (vo.getDetails() != null) {

			for (SalesDeliveryScheduleDetailsVO detailVO : vo.getDetails()) {

				SalesDeliveryScheduleDetailsResponseDTO detailResponse = new SalesDeliveryScheduleDetailsResponseDTO();

				detailResponse.setId(detailVO.getId());

				// ================ Contract =================

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
				detailResponse.setOrderQty(detailVO.getOrderQty());
				detailResponse.setPendingQty(detailVO.getPendingQty());
				detailResponse.setActualPlannedQty(detailVO.getActualPlannedQty());

				// ================ Item =================

				if (detailVO.getItem() != null) {

					ItemResponseDTO item = new ItemResponseDTO();

					item.setId(detailVO.getItem().getId());

					item.setItemCode(detailVO.getItem().getItemCode());

					item.setItemDescription(detailVO.getItem().getItemDescription());

					if (detailVO.getItem().getPrimaryUnit() != null) {

						UnitResponseDTO unit = new UnitResponseDTO();

						unit.setId(detailVO.getItem().getPrimaryUnit().getId());

						unit.setUnitId(detailVO.getItem().getPrimaryUnit().getDescription());

						item.setUnit(unit);
					}

					detailResponse.setItem(item);
				}

				// ================ Delivery Schedule =================

				List<SalesDeliverySchedulePlanResponseDTO> planResponseList = new ArrayList<>();

				if (detailVO.getDeliverySchedules() != null) {

					for (SalesDeliverySchedulePlanVO planVO : detailVO.getDeliverySchedules()) {

						SalesDeliverySchedulePlanResponseDTO planResponse = new SalesDeliverySchedulePlanResponseDTO();

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
	public List<SalesDeliveryScheduleResponseDTO> getAllSalesDeliverySchedule(Long orgId, Long branch)
			throws ApplicationException {

		List<SalesDeliveryScheduleVO> scheduleList = salesDeliveryScheduleRepo.findByOrgIdAndBranch(orgId, branch);

		List<SalesDeliveryScheduleResponseDTO> responseList = new ArrayList<>();

		for (SalesDeliveryScheduleVO scheduleVO : scheduleList) {
			responseList.add(buildSalesDeliveryScheduleResponse(scheduleVO));
		}

		return responseList;
	}

	@Override
	public Map<String, Object> getSalesDeliveryScheduleByItemDropdown(String docId, Long orgId, Long branch)
			throws ApplicationException {

		String methodName = "getItemDropdown";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		List<Object[]> list = salesContractDetailsRepo.getSalesDeliveryScheduleByItemDropdown(docId, orgId, branch);

		List<Map<String, Object>> responseList = new ArrayList<>();

		for (Object[] obj : list) {

			Map<String, Object> map = new HashMap<>();

			map.put("itemId", obj[0] != null ? obj[0] : null);
			map.put("itemCode", obj[1] != null ? obj[1] : null);
			map.put("itemDescription", obj[2] != null ? obj[2] : null);
			map.put("unit", obj[3] != null ? obj[3] : null);
			map.put("orderQty", obj[4] != null ? obj[4] : null);
			map.put("itemId", obj[5] != null ? obj[5] : null);
			map.put("unitId", obj[6] != null ? obj[6] : null);

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
	public Map<String, Object> updateCreateSalesContractAmendment(SalesContractAmendmentDTO salesContractAmendmentDTO)
			throws ApplicationException {

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
		createUpdateSalesContractAmendmentVO(salesContractAmendmentDTO, salesContractAmendmentVO);

		SalesContractAmendmentVO savedSalesContractAmendment = salesContractAmendmentRepo
				.save(salesContractAmendmentVO);

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

	private void createUpdateSalesContractAmendmentVO(SalesContractAmendmentDTO dto,
			SalesContractAmendmentVO salesContractAmendmentVO) throws ApplicationException {

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

		// ----------------------------------------------------
		// Delete old child records while updating
		// ----------------------------------------------------

		if (dto.getId() != null) {

			List<SalesContractAmdDetailsVO> oldList = salesContractAmdDetailsRepo
					.findBySalesContractAmendmentVO(salesContractAmendmentVO);

			salesContractAmdDetailsRepo.deleteAll(oldList);
		}

		List<SalesContractAmdDetailsVO> detailList = new ArrayList<>();

		if (dto.getSalesContractAmdDetailsDTO() != null && !dto.getSalesContractAmdDetailsDTO().isEmpty()) {

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
	public SalesContractAmdResponseDTO getSalesContractAmendmentById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		SalesContractAmendmentVO salesContractAmendmentVO = salesContractAmendmentRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Sales Contract Amendment Not Found"));

		return salesContractResponseResponse(salesContractAmendmentVO);
	}

	@Override
	public List<SalesContractAmdResponseDTO> getSalesContractAmendmentByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<SalesContractAmendmentVO> salesContractAmendmentList = salesContractAmendmentRepo
				.getSalesContractAmendmentByOrgId(orgId, branch);

		if (salesContractAmendmentList.isEmpty()) {
			throw new ApplicationException("No Sales Contract Amendment Details Found");
		}

		List<SalesContractAmdResponseDTO> responseList = new ArrayList<>();

		for (SalesContractAmendmentVO salesContractAmendmentVO : salesContractAmendmentList) {

			responseList.add(salesContractResponseResponse(salesContractAmendmentVO));
		}

		return responseList;
	}

	// dropdown for sales Contract Amendment

	@Override
	public Map<String, Object> getSalesContractAmdContractNoDropdown(Long orgId, Long branch)
			throws ApplicationException {

		List<Object[]> contractList = salesContractRepo.getSalesContractAmdContractNoDropdown(orgId, branch);

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
	public Map<String, Object> getSalesContractAmdItemDropdown(String salesContractNo, Long orgId, Long branch)
			throws ApplicationException {

		String methodName = "getItemDropdown()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		List<Object[]> itemList = salesContractRepo.getSalesContractAmdItemDropdown(salesContractNo, orgId, branch);

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
			map.put("oldRate", obj[3]);

			responseList.add(map);
		}

		return responseList;
	}

	@Override
	public Map<String, Object> getSalesContractAmdRevisionNo(String salesContractNo, Long item, Long orgId, Long branch)
			throws ApplicationException {

		Integer revisionNo = salesContractRepo.getSalesContractAmdRevisionNo(salesContractNo, item, orgId, branch);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("message", "Revision No Loaded Successfully");
		responseMap.put("revisionNo", revisionNo);

		return responseMap;
	}
	// Docket Invoice

	@Override
	@Transactional
	public Map<String, Object> updateCreateDocketInvoice(DocketInvoiceDTO docketInvoiceDTO)
			throws ApplicationException {
		String screenCode = "DID";

		DocketInvoiceVO docketInvoiceVO = new DocketInvoiceVO();

		String message;

		if (ObjectUtils.isNotEmpty(docketInvoiceDTO.getId())) {

			docketInvoiceVO = docketInvoiceRepo.findById(docketInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Docket Invoice Details"));

			docketInvoiceVO.setUpdatedBy(docketInvoiceDTO.getCreatedBy());

			message = "Docket Invoice Updated Successfully";

		} else {

			String docId = docketInvoiceRepo.getDocketInvoiceDocId(docketInvoiceDTO.getOrgId(),
					docketInvoiceDTO.getFinancialYear(), screenCode);

			docketInvoiceVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(docketInvoiceDTO.getOrgId(),
							docketInvoiceDTO.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			docketInvoiceVO.setCreatedBy(docketInvoiceDTO.getCreatedBy());

			docketInvoiceVO.setUpdatedBy(docketInvoiceDTO.getCreatedBy());

			message = "Docket Invoice Created Successfully";
		}

		createUpdateDocketInvoiceVO(docketInvoiceDTO, docketInvoiceVO);

		DocketInvoiceVO savedDocketInvoice = docketInvoiceRepo.save(docketInvoiceVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);

		response.put("docketInvoiceVO", docketInvoiceResponse(savedDocketInvoice));

		return response;
	}

	private DocketInvoiceResponseDTO docketInvoiceResponse(DocketInvoiceVO docketInvoiceVO) {

		DocketInvoiceResponseDTO responseDTO = new DocketInvoiceResponseDTO();

		responseDTO.setId(docketInvoiceVO.getId());
		responseDTO.setDocId(docketInvoiceVO.getDocId());
		responseDTO.setDocDate(docketInvoiceVO.getDocDate());
		responseDTO.setBillNo(docketInvoiceVO.getBillNo());
		responseDTO.setBillDate(docketInvoiceVO.getBillDate());
		responseDTO.setTotalAmount(docketInvoiceVO.getTotalAmount());
		responseDTO.setOrgId(docketInvoiceVO.getOrgId());
		responseDTO.setActive(docketInvoiceVO.getActive());
		responseDTO.setCreatedBy(docketInvoiceVO.getCreatedBy());
		responseDTO.setCancelRemarks(docketInvoiceVO.getCancelRemarks());

		responseDTO.setFinancialYear(docketInvoiceVO.getFinancialYear());

		// =========================
		// Branch Response
		// =========================

		if (docketInvoiceVO.getBranch() != null) {

			BranchResponseDTO branchResponseDTO = new BranchResponseDTO();

			branchResponseDTO.setId(docketInvoiceVO.getBranch().getId());
			branchResponseDTO.setBranchName(docketInvoiceVO.getBranch().getBranchName());

			responseDTO.setBranch(branchResponseDTO);
		}

		// =========================
		// Transport Response
		// =========================

		if (docketInvoiceVO.getTransport() != null) {

			TransportResponseDTO transportResponseDTO = new TransportResponseDTO();

			transportResponseDTO.setId(docketInvoiceVO.getTransport().getId());
			transportResponseDTO.setTransportName(docketInvoiceVO.getTransport().getTransportName());

			responseDTO.setTransport(transportResponseDTO);
		}

		// =========================
		// Child Response
		// =========================

		List<DocketInvoiceDetResponseDTO> detailResponseList = new ArrayList<>();

		if (docketInvoiceVO.getDetails() != null && !docketInvoiceVO.getDetails().isEmpty()) {

			for (DocketInvoiceDetailsVO detailVO : docketInvoiceVO.getDetails()) {

				DocketInvoiceDetResponseDTO detailDTO = new DocketInvoiceDetResponseDTO();

				detailDTO.setDocketNo(detailVO.getDocketNo());
				detailDTO.setDocketDate(detailVO.getDocketDate());
				detailDTO.setInvoiceNo(detailVO.getInvoiceNo());
				detailDTO.setNoOfQty(detailVO.getNoOfQty());
				detailDTO.setWeight(detailVO.getWeight());
				detailDTO.setTotalValue(detailVO.getTotalValue());
				detailDTO.setCumulativeValue(detailVO.getCumulativeValue());
				detailDTO.setMode(detailVO.getMode());

				detailResponseList.add(detailDTO);
			}
		}

		responseDTO.setDocketInvoiceDetResponseDTO(detailResponseList);

		return responseDTO;
	}

	private void createUpdateDocketInvoiceVO(DocketInvoiceDTO dto, DocketInvoiceVO docketInvoiceVO)
			throws ApplicationException {

		docketInvoiceVO.setBillNo(dto.getBillNo());
		docketInvoiceVO.setBillDate(dto.getBillDate());
		docketInvoiceVO.setTotalAmount(dto.getTotalAmount());

		docketInvoiceVO.setOrgId(dto.getOrgId());
		docketInvoiceVO.setActive(dto.isActive());
		docketInvoiceVO.setCancelRemarks(dto.getCancelRemarks());
		docketInvoiceVO.setFinancialYear(dto.getFinancialYear());

		// =========================
		// Branch Mapping
		// =========================

		if (dto.getBranch() != null && dto.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dto.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			docketInvoiceVO.setBranch(branch);
		}

		// =========================
		// Transport Mapping
		// =========================

		if (dto.getTransport() != null && dto.getTransport() != 0) {

			TransportMasterVO transport = transportRepo.findById(dto.getTransport())
					.orElseThrow(() -> new ApplicationException("Transport Not Found"));

			docketInvoiceVO.setTransport(transport);
		}

		// ======================================
		// Delete Existing Child During Update
		// ======================================

		if (dto.getId() != null) {

			List<DocketInvoiceDetailsVO> oldList = docketInvoiceDetRepo.findByDocketInvoiceVO(docketInvoiceVO);

			docketInvoiceDetRepo.deleteAll(oldList);
		}

		// ======================================
		// Child Save
		// ======================================

		List<DocketInvoiceDetailsVO> detailList = new ArrayList<>();

		if (dto.getDocketInvoiceDetailsDTO() != null && !dto.getDocketInvoiceDetailsDTO().isEmpty()) {

			for (DocketInvoiceDetailsDTO detailDTO : dto.getDocketInvoiceDetailsDTO()) {

				DocketInvoiceDetailsVO detailVO = new DocketInvoiceDetailsVO();

				detailVO.setDocketNo(detailDTO.getDocketNo());
				detailVO.setDocketDate(detailDTO.getDocketDate());
				detailVO.setInvoiceNo(detailDTO.getInvoiceNo());
				detailVO.setNoOfQty(detailDTO.getNoOfQty());
				detailVO.setWeight(detailDTO.getWeight());
				detailVO.setTotalValue(detailDTO.getTotalValue());
				detailVO.setCumulativeValue(detailDTO.getCumulativeValue());
				detailVO.setMode(detailDTO.getMode());

				// Parent Mapping
				detailVO.setDocketInvoiceVO(docketInvoiceVO);

				detailList.add(detailVO);
			}

			docketInvoiceVO.setDetails(detailList);
		}

	}

	@Override
	public DocketInvoiceResponseDTO getDocketInvoiceById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		DocketInvoiceVO docketInvoiceVO = docketInvoiceRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Docket Invoice Not Found"));

		return docketInvoiceResponse(docketInvoiceVO);
	}

	@Override
	public List<DocketInvoiceResponseDTO> getDocketInvoiceByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<DocketInvoiceVO> docketInvoiceList = docketInvoiceRepo.getDocketInvoiceByOrgId(orgId, branch);

		if (docketInvoiceList.isEmpty()) {
			throw new ApplicationException("No Docket Invoice Details Found");
		}

		List<DocketInvoiceResponseDTO> responseList = new ArrayList<>();

		for (DocketInvoiceVO docketInvoiceVO : docketInvoiceList) {

			responseList.add(docketInvoiceResponse(docketInvoiceVO));
		}

		return responseList;
	}

	@Override
	public String getSalesDeliveryScheduleDocId(Long orgId, String financialYear, String screenCode) {
		String screenCode1 = "SDS";
		String result = salesDeliveryScheduleRepo.getSalesDeliveryScheduleDocId(orgId, financialYear, screenCode1);
		return result;
	}

	@Override
	public String getDocketInvoiceDocId(Long orgId, String financialYear, String screenCode) {
		String screenCode1 = "DID";
		String result = docketInvoiceRepo.getDocketInvoiceDocId(orgId, financialYear, screenCode1);
		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesRejectionInvoice(SalesRejectionInvoiceDTO salesRejectionInvoiceDTO)
			throws ApplicationException {

		Map<String, Object> responseMap = new HashMap<>();

		SalesRejectionInvoiceVO salesRejectionInvoiceVO;
		String message;
		String screenCode;

		if ("Other Sales Invoice".equals(salesRejectionInvoiceDTO.getDocType())) {
			screenCode = "SOI";
		} else if ("Invoice".equals(salesRejectionInvoiceDTO.getDocType())) {
			screenCode = "DCI";
		} else if ("Rejection".equals(salesRejectionInvoiceDTO.getDocType())) {
			screenCode = "RI";
		} else {
			throw new ApplicationException("Invalid Document Type");
		}

		if (ObjectUtils.isNotEmpty(salesRejectionInvoiceDTO.getId())) {

			salesRejectionInvoiceVO = salesRejectionInvoiceRepo.findById(salesRejectionInvoiceDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid Sales Rejection Invoice Details"));

			List<SalesRejectionInvoiceDetailsVO> oldDetails = salesRejectionInvoiceDetailsRepo
					.findBySalesRejectionInvoiceVO(salesRejectionInvoiceVO);

			salesRejectionInvoiceDetailsRepo.deleteAll(oldDetails);

			List<SalesRejectionInvoiceTaxDetailsVO> oldTaxDetails = salesRejectionInvoiceTaxDetailsRepo
					.findBySalesRejectionInvoiceVO(salesRejectionInvoiceVO);

			salesRejectionInvoiceTaxDetailsRepo.deleteAll(oldTaxDetails);

			salesRejectionInvoiceVO.setCreatedBy(salesRejectionInvoiceDTO.getCreatedBy());

			message = "Sales Rejection Invoice Updated Successfully";

		} else {

			salesRejectionInvoiceVO = new SalesRejectionInvoiceVO();

			String docId = salesRejectionInvoiceRepo.getSalesRejectionInvoiceDocId(salesRejectionInvoiceDTO.getOrgId(),
					salesRejectionInvoiceDTO.getFinancialYear(), screenCode);

			salesRejectionInvoiceVO.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndScreenCode(salesRejectionInvoiceDTO.getOrgId(),
							salesRejectionInvoiceDTO.getFinancialYear(), screenCode);
			documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			salesRejectionInvoiceVO.setCreatedBy(salesRejectionInvoiceDTO.getCreatedBy());
			salesRejectionInvoiceVO.setUpdatedBy(salesRejectionInvoiceDTO.getCreatedBy());

			message = "Sales Rejection Invoice Saved Successfully";
		}

		// Header + Child Mapping
		createUpdateSalesRejectionInvoiceVOBySalesRejectionInvoiceDTO(salesRejectionInvoiceDTO,
				salesRejectionInvoiceVO);

		// Save Header
		salesRejectionInvoiceVO = salesRejectionInvoiceRepo.save(salesRejectionInvoiceVO);

		// Response
		SalesRejectionInvoiceResponseDTO responseDTO = buildSalesRejectionInvoiceResponse(salesRejectionInvoiceVO);

		Map<String, Object> response = new HashMap<>();

		response.put("message", message);
		response.put("salesRejectionInvoiceVO", responseDTO);

		return response;
	}

	private void createUpdateSalesRejectionInvoiceVOBySalesRejectionInvoiceDTO(
			SalesRejectionInvoiceDTO salesRejectionInvoiceDTO, SalesRejectionInvoiceVO salesRejectionInvoiceVO)
			throws ApplicationException {

		// =========================================================
		// COMMON HEADER FIELDS
		// =========================================================

		salesRejectionInvoiceVO.setVehicle(salesRejectionInvoiceDTO.getVehicle());

		salesRejectionInvoiceVO.setDocType(salesRejectionInvoiceDTO.getDocType());

		salesRejectionInvoiceVO.setIgstAppl(salesRejectionInvoiceDTO.isIgstApplicable());

		salesRejectionInvoiceVO.setTimeOfIssue(salesRejectionInvoiceDTO.getTimeOfIssue());

		salesRejectionInvoiceVO.setDateOfIssue(salesRejectionInvoiceDTO.getDateOfIssue());

		salesRejectionInvoiceVO.setScheduleNo(salesRejectionInvoiceDTO.getScheduleNo());

		salesRejectionInvoiceVO.setDispatchInstructionNo(salesRejectionInvoiceDTO.getDispatchInstructionNo());

		salesRejectionInvoiceVO.setFinancialYear(salesRejectionInvoiceDTO.getFinancialYear());

		salesRejectionInvoiceVO.setTimeOfRemoval(salesRejectionInvoiceDTO.getTimeOfRemoval());

		salesRejectionInvoiceVO.setDateOfRemoval(salesRejectionInvoiceDTO.getDateOfRemoval());

		salesRejectionInvoiceVO.setScheduleDate(salesRejectionInvoiceDTO.getScheduleDate());

		salesRejectionInvoiceVO.setDispatchInstructionDate(salesRejectionInvoiceDTO.getDispatchInstructionDate());

		salesRejectionInvoiceVO.setExchangeRate(salesRejectionInvoiceDTO.getExchangeRate());

		salesRejectionInvoiceVO.setMonthYear(salesRejectionInvoiceDTO.getMonthYear());

		salesRejectionInvoiceVO.setKanbanCardNo(salesRejectionInvoiceDTO.getKanbanCardNo());

		salesRejectionInvoiceVO.setExcisable(salesRejectionInvoiceDTO.isExcisable());

		salesRejectionInvoiceVO.setStockPosting(salesRejectionInvoiceDTO.isStockPosting());

		// =========================================================
		// REJECTION INVOICE FIELDS
		// =========================================================

		salesRejectionInvoiceVO.setRefNo(salesRejectionInvoiceDTO.getRefNo());

		salesRejectionInvoiceVO.setRefDate(salesRejectionInvoiceDTO.getRefDate());

		salesRejectionInvoiceVO.setSupplierInvoiceNo(salesRejectionInvoiceDTO.getSupplierInvoiceNo());

		// =========================================================
		// COMMON HEADER FIELDS
		// =========================================================

		salesRejectionInvoiceVO.setTotalInsurance(salesRejectionInvoiceDTO.getTotalInsurance());

		salesRejectionInvoiceVO.setTotalFreight(salesRejectionInvoiceDTO.getTotalFreight());

		salesRejectionInvoiceVO.setTotalAssVal(salesRejectionInvoiceDTO.getTotalAssVal());

		salesRejectionInvoiceVO.setModeOfTransport(salesRejectionInvoiceDTO.getModeOfTransport());

		salesRejectionInvoiceVO.setNetAmount(salesRejectionInvoiceDTO.getNetAmount());

		salesRejectionInvoiceVO.setAmountInWords(salesRejectionInvoiceDTO.getAmountInWords());

		salesRejectionInvoiceVO.setDeliveryTo(salesRejectionInvoiceDTO.getDeliveryTo());

		salesRejectionInvoiceVO.setPaymentTerms(salesRejectionInvoiceDTO.getPaymentTerms());

		salesRejectionInvoiceVO.setPurchaseOrder(salesRejectionInvoiceDTO.getPurchaseOrder());

		salesRejectionInvoiceVO.setPurchaseOrderDate(salesRejectionInvoiceDTO.getPurchaseOrderDate());

		salesRejectionInvoiceVO.setNarration(salesRejectionInvoiceDTO.getNarration());

		// =========================================================
		// DC CUM INVOICE SPECIFIC FIELDS
		// =========================================================

		salesRejectionInvoiceVO.setTcsAmount(salesRejectionInvoiceDTO.getTcsAmount());

		salesRejectionInvoiceVO.setNetWeight(salesRejectionInvoiceDTO.getNetWeight());

		salesRejectionInvoiceVO.setGrossWeight(salesRejectionInvoiceDTO.getGrossWeight());

		// =========================================================
		// COMMON / AUDIT
		// =========================================================

		salesRejectionInvoiceVO.setOrgId(salesRejectionInvoiceDTO.getOrgId());

		salesRejectionInvoiceVO.setActive(salesRejectionInvoiceDTO.isActive());

		salesRejectionInvoiceVO.setCancelRemarks(salesRejectionInvoiceDTO.getCancelRemarks());

		salesRejectionInvoiceVO.setBelongsTo(salesRejectionInvoiceDTO.getBelongsTo());

		// =========================================================
		// BRANCH
		// =========================================================

		if (salesRejectionInvoiceDTO.getBranch() != null && salesRejectionInvoiceDTO.getBranch() > 0) {

			BranchVO branch = branchRepo.findById(salesRejectionInvoiceDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("Branch Not Found"));

			salesRejectionInvoiceVO.setBranch(branch);
		}

		// =========================================================
		// LOCATION
		// =========================================================

		if (salesRejectionInvoiceDTO.getLocation() != null && salesRejectionInvoiceDTO.getLocation() > 0) {

			LocationVO location = locationRepo.findById(salesRejectionInvoiceDTO.getLocation())
					.orElseThrow(() -> new ApplicationException("Location Not Found"));

			salesRejectionInvoiceVO.setLocation(location);
		}

		// =========================================================
		// BELONGS TO
		// =========================================================

	

		// =========================================================
		// CUSTOMER
		// =========================================================

		if (salesRejectionInvoiceDTO.getCustomer() != null && salesRejectionInvoiceDTO.getCustomer() > 0) {

			CustomerVO customer = customerRepo.findById(salesRejectionInvoiceDTO.getCustomer())
					.orElseThrow(() -> new ApplicationException("Customer Not Found"));

			salesRejectionInvoiceVO.setCustomer(customer);
		}

		// =========================================================
		// CURRENCY
		// =========================================================

		if (salesRejectionInvoiceDTO.getCurrency() != null && salesRejectionInvoiceDTO.getCurrency() > 0) {

			CurrencyVO currency = currencyRepo.findById(salesRejectionInvoiceDTO.getCurrency())
					.orElseThrow(() -> new ApplicationException("Currency Not Found"));

			salesRejectionInvoiceVO.setCurrency(currency);
		}

		// =========================================================
		// UPDATE - DELETE OLD CHILD DETAILS
		// =========================================================

		if (ObjectUtils.isNotEmpty(salesRejectionInvoiceVO.getId())) {

			List<SalesRejectionInvoiceDetailsVO> oldDetails = salesRejectionInvoiceDetailsRepo
					.findBySalesRejectionInvoiceVO(salesRejectionInvoiceVO);

			salesRejectionInvoiceDetailsRepo.deleteAll(oldDetails);

			List<SalesRejectionInvoiceTaxDetailsVO> oldTaxDetails = salesRejectionInvoiceTaxDetailsRepo
					.findBySalesRejectionInvoiceVO(salesRejectionInvoiceVO);

			salesRejectionInvoiceTaxDetailsRepo.deleteAll(oldTaxDetails);
		}

		// =========================================================
		// DETAILS
		// =========================================================

		List<SalesRejectionInvoiceDetailsVO> detailsList = new ArrayList<>();

		if (salesRejectionInvoiceDTO.getSalesRejectionInvoiceDetailsDTO() != null) {

			for (SalesRejectionInvoiceDetailsDTO dto : salesRejectionInvoiceDTO.getSalesRejectionInvoiceDetailsDTO()) {

				SalesRejectionInvoiceDetailsVO detailVO = new SalesRejectionInvoiceDetailsVO();

				// Item
				if (dto.getItem() != null && dto.getItem() > 0) {

					ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
							.orElseThrow(() -> new ApplicationException("Item Code Not Found"));

					detailVO.setItem(item);
				}

				// Unit
				if (dto.getUnit() != null && dto.getUnit() > 0) {

					UnitMasterVO unit = unitMasterRepo.findById(dto.getUnit())
							.orElseThrow(() -> new ApplicationException("Unit Not Found"));

					detailVO.setUnit(unit);
				}

				detailVO.setHsnSacCode(dto.getHsnSacCode());

				detailVO.setTaxType(dto.getTaxType());
				detailVO.setNewRate(dto.getNewRate());

				detailVO.setTaxPercentage(dto.getTaxPercentage());

				detailVO.setCustomerPartNo(dto.getCustomerPartNo());

				detailVO.setStock(dto.getStock());

				detailVO.setSalesOrderContractNo(dto.getSalesOrderContractNo());

				detailVO.setDespatchQty(dto.getDespatchQty());

				BigDecimal newRate = dto.getNewRate() != null ? dto.getNewRate() : BigDecimal.ZERO;

				BigDecimal despatchQty = dto.getDespatchQty() != null ? dto.getDespatchQty() : BigDecimal.ZERO;

				BigDecimal exchangeRate = salesRejectionInvoiceDTO.getExchangeRate() != null
						? new BigDecimal(salesRejectionInvoiceDTO.getExchangeRate())
						: BigDecimal.ONE;

				// Rate In Selected Currency
				BigDecimal rateInSelectedCurrency = BigDecimal.ZERO;

				if (exchangeRate.compareTo(BigDecimal.ZERO) > 0) {
					rateInSelectedCurrency = newRate.divide(exchangeRate, 2, RoundingMode.HALF_UP);
				}

				// Amount In Selected Currency
				BigDecimal amountInSelectedCurrency = despatchQty.multiply(rateInSelectedCurrency).setScale(2,
						RoundingMode.HALF_UP);

				// Base Amount In Rs
				BigDecimal amountInRs = newRate.multiply(despatchQty).setScale(2, RoundingMode.HALF_UP);

				// Tax Calculation
//				BigDecimal amountInRs = BigDecimal.ZERO;

				if (salesRejectionInvoiceDTO.isIgstApplicable()) {

					// IGST
					BigDecimal igstRate = dto.getIgstRate() != null ? dto.getIgstRate() : BigDecimal.ZERO;

					BigDecimal igstAmount = amountInRs.multiply(igstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					detailVO.setIgstRate(igstRate);
					detailVO.setIgstAmount(igstAmount);

					detailVO.setCgstRate(BigDecimal.ZERO);
					detailVO.setCgstAmount(BigDecimal.ZERO);

					detailVO.setSgstRate(BigDecimal.ZERO);
					detailVO.setSgstAmount(BigDecimal.ZERO);

				} else {

					// CGST
					BigDecimal cgstRate = dto.getCgstRate() != null ? dto.getCgstRate() : BigDecimal.ZERO;

					BigDecimal cgstAmount = amountInRs.multiply(cgstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					// SGST
					BigDecimal sgstRate = dto.getSgstRate() != null ? dto.getSgstRate() : BigDecimal.ZERO;

					BigDecimal sgstAmount = amountInRs.multiply(sgstRate).divide(BigDecimal.valueOf(100), 2,
							RoundingMode.HALF_UP);

					detailVO.setCgstRate(cgstRate);
					detailVO.setCgstAmount(cgstAmount);

					detailVO.setSgstRate(sgstRate);
					detailVO.setSgstAmount(sgstAmount);

					detailVO.setIgstRate(BigDecimal.ZERO);
					detailVO.setIgstAmount(BigDecimal.ZERO);

				}

//				// Final Amount In Rs = Base + Tax
//				BigDecimal amountInRs = baseAmountInRs
//				        .add(taxAmountInRs)
//				        .setScale(2, RoundingMode.HALF_UP);

				detailVO.setRateInSelectedCurrency(rateInSelectedCurrency);
				detailVO.setAmountInSelectedCurrency(amountInSelectedCurrency);
				detailVO.setAmountInRs(amountInRs);
				// Parent
				detailVO.setSalesRejectionInvoiceVO(salesRejectionInvoiceVO);

				detailsList.add(detailVO);
			}
		}

		salesRejectionInvoiceVO.setDetails(detailsList);

		// =========================================================
		// TAX DETAILS
		// =========================================================

		List<SalesRejectionInvoiceTaxDetailsVO> taxDetailsList = new ArrayList<>();

		if (salesRejectionInvoiceDTO.getSalesRejectionInvoiceTaxDetailsDTO() != null) {

			for (SalesRejectionInvoiceTaxDetailsDTO dto : salesRejectionInvoiceDTO
					.getSalesRejectionInvoiceTaxDetailsDTO()) {

				SalesRejectionInvoiceTaxDetailsVO taxVO = new SalesRejectionInvoiceTaxDetailsVO();

				// Particulars
				if (dto.getParticulars() != null && dto.getParticulars() > 0) {

					ListOfValuesDetailsVO particulars = listOfValuesDetailsRepo.findById(dto.getParticulars())
							.orElseThrow(() -> new ApplicationException("Particulars Not Found"));

					taxVO.setParticulars(particulars);
				}

				taxVO.setGlAccountName(dto.getGlAccountName());

				taxVO.setAcceptedQtyAmount(dto.getAcceptedQtyAmount());

				taxVO.setRevisedAmount(dto.getRevisedAmount());

				taxVO.setAmount(dto.getAmount());

				// Parent
				taxVO.setSalesRejectionInvoiceVO(salesRejectionInvoiceVO);

				taxDetailsList.add(taxVO);
			}
		}

		salesRejectionInvoiceVO.setTaxDetails(taxDetailsList);
	}

	private SalesRejectionInvoiceResponseDTO buildSalesRejectionInvoiceResponse(SalesRejectionInvoiceVO vo) {

		SalesRejectionInvoiceResponseDTO dto = new SalesRejectionInvoiceResponseDTO();

		// =========================================================
		// HEADER
		// =========================================================

		dto.setId(vo.getId());
		dto.setDocId(vo.getDocId());
		dto.setDocDate(vo.getDocDate());
		dto.setBelongsTo(vo.getBelongsTo());

		// Branch
		if (vo.getBranch() != null) {
			BranchResponseDTO branchDTO = new BranchResponseDTO();
			branchDTO.setId(vo.getBranch().getId());
			branchDTO.setBranchCode(vo.getBranch().getBranchCode());
			branchDTO.setBranchName(vo.getBranch().getBranchName());
			dto.setBranch(branchDTO);
		}

		// Location
		if (vo.getLocation() != null) {
			LocationMasterResponseDTO locationDTO = new LocationMasterResponseDTO();

			locationDTO.setId(vo.getLocation().getId());
			locationDTO.setLocationName(vo.getLocation().getLocationName());

			dto.setLocation(locationDTO);
		}

		// Belongs To
		

		dto.setVehicle(vo.getVehicle());
		dto.setDocType(vo.getDocType());
		dto.setIgstAppl(vo.isIgstAppl());
		dto.setTimeOfIssue(vo.getTimeOfIssue());
		dto.setDateOfIssue(vo.getDateOfIssue());

		// Customer
		if (vo.getCustomer() != null) {

			CustomerShipingResponseDTO customerDTO = new CustomerShipingResponseDTO();

			customerDTO.setCustomerId(vo.getCustomer().getId());
			customerDTO.setCustomerCode(vo.getCustomer().getCustomerCode());
			customerDTO.setCustomerName(vo.getCustomer().getCustomerName());

			// Shipping Details
			if (vo.getCustomer().getCustomerShippingDetails() != null
					&& !vo.getCustomer().getCustomerShippingDetails().isEmpty()) {

				CustomerShippingDetailsVO shippingDetails = vo.getCustomer().getCustomerShippingDetails().get(0);

				customerDTO.setShippingAddress(shippingDetails.getShippingAddress());

				if (shippingDetails.getShippingCity() != null) {
					customerDTO.setShippingCity(shippingDetails.getShippingCity().getCityName());
				}

				customerDTO.setShippingPincode(shippingDetails.getShippingPincode());
			}

			if (vo.getCustomer().getGstState() != null) {
				customerDTO.setGstState(vo.getCustomer().getGstState().getStateName());
			}

			customerDTO.setGstNo(vo.getCustomer().getGstNo());

			customerDTO.setIgstApplicable(vo.getCustomer().isGstApplicable());

			customerDTO.setGstType(vo.getCustomer().getGstType());

			dto.setCustomer(customerDTO);
		}

		// Currency
		if (vo.getCurrency() != null) {
			CurrencyResponseDTO currencyDTO = new CurrencyResponseDTO();

			currencyDTO.setId(vo.getCurrency().getId());
			currencyDTO.setCurrencyName(vo.getCurrency().getCurrency());

			dto.setCurrency(currencyDTO);
		}

		dto.setScheduleNo(vo.getScheduleNo());
		dto.setDispatchInstructionNo(vo.getDispatchInstructionNo());
		dto.setTimeOfRemoval(vo.getTimeOfRemoval());
		dto.setDateOfRemoval(vo.getDateOfRemoval());
		dto.setScheduleDate(vo.getScheduleDate());
		dto.setDispatchInstructionDate(vo.getDispatchInstructionDate());
		dto.setExchangeRate(vo.getExchangeRate());
		dto.setMonthYear(vo.getMonthYear());
		dto.setKanbanCardNo(vo.getKanbanCardNo());
		dto.setExcisable(vo.isExcisable());
		dto.setStockPosting(vo.isStockPosting());

		// =========================================================
		// REJECTION INVOICE FIELDS
		// =========================================================

		dto.setRefNo(vo.getRefNo());
		dto.setRefDate(vo.getRefDate());
		dto.setSupplierInvoiceNo(vo.getSupplierInvoiceNo());

		// =========================================================
		// COMMON HEADER FIELDS
		// =========================================================

		dto.setTotalInsurance(vo.getTotalInsurance());
		dto.setTotalFreight(vo.getTotalFreight());
		dto.setTotalAssVal(vo.getTotalAssVal());
		dto.setModeOfTransport(vo.getModeOfTransport());
		dto.setNetAmount(vo.getNetAmount());
		dto.setAmountInWords(vo.getAmountInWords());
		dto.setDeliveryTo(vo.getDeliveryTo());
		dto.setPaymentTerms(vo.getPaymentTerms());
		dto.setPurchaseOrder(vo.getPurchaseOrder());
		dto.setPurchaseOrderDate(vo.getPurchaseOrderDate());
		dto.setNarration(vo.getNarration());

		// =========================================================
		// DC CUM INVOICE SPECIFIC
		// =========================================================

		dto.setTcsAmount(vo.getTcsAmount());
		dto.setNetWeight(vo.getNetWeight());
		dto.setGrossWeight(vo.getGrossWeight());

		// =========================================================
		// AUDIT
		// =========================================================

		dto.setCreatedBy(vo.getCreatedBy());
		dto.setOrgId(vo.getOrgId());
		dto.setActive(vo.isActive());
		dto.setCancelRemarks(vo.getCancelRemarks());
		dto.setFinancialYear(vo.getFinancialYear());

		// =========================================================
		// DETAILS CHILD
		// =========================================================

		List<SalesRejectionInvoiceDetailsResponseDTO> detailsList = new ArrayList<>();

		if (vo.getDetails() != null) {

			for (SalesRejectionInvoiceDetailsVO detailVO : vo.getDetails()) {

				SalesRejectionInvoiceDetailsResponseDTO detailDTO = new SalesRejectionInvoiceDetailsResponseDTO();

				detailDTO.setId(detailVO.getId());

				// Item
				if (detailVO.getItem() != null) {

					ItemResponse1DTO itemDTO = new ItemResponse1DTO();

					itemDTO.setId(detailVO.getItem().getId());

					itemDTO.setItemCode(detailVO.getItem().getItemCode());

					itemDTO.setItemDescription(detailVO.getItem().getItemDescription());

					// Item Unit
					if (detailVO.getItem().getPrimaryUnit() != null) {

						UnitMasterResponseDTO itemUnitDTO = new UnitMasterResponseDTO();

						itemUnitDTO.setId(detailVO.getItem().getPrimaryUnit().getId());

						itemUnitDTO.setUnitId(detailVO.getItem().getPrimaryUnit().getUnitId());

						itemUnitDTO.setUnitDescription(detailVO.getItem().getPrimaryUnit().getDescription());

						itemDTO.setUnit(itemUnitDTO);
					}

					detailDTO.setItem(itemDTO);
				}

				detailDTO.setHsnSacCode(detailVO.getHsnSacCode());

				detailDTO.setNewRate(detailVO.getNewRate());

				detailDTO.setTaxType(detailVO.getTaxType());

				detailDTO.setTaxPercentage(detailVO.getTaxPercentage());

				detailDTO.setCustomerPartNo(detailVO.getCustomerPartNo());

				// Unit
				if (detailVO.getUnit() != null) {

					UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

					unitDTO.setId(detailVO.getUnit().getId());

					unitDTO.setUnitId(detailVO.getUnit().getUnitId());

					unitDTO.setUnitDescription(detailVO.getUnit().getDescription());

					detailDTO.setUnit(unitDTO);
				}

				detailDTO.setStock(detailVO.getStock());

				detailDTO.setSalesOrderContractNo(detailVO.getSalesOrderContractNo());

				detailDTO.setDespatchQty(detailVO.getDespatchQty());

				detailDTO.setRateInSelectedCurrency(detailVO.getRateInSelectedCurrency());

				detailDTO.setAmountInSelectedCurrency(detailVO.getAmountInSelectedCurrency());

				detailDTO.setAmountInRs(detailVO.getAmountInRs());

				detailDTO.setSgstRate(detailVO.getSgstRate());

				detailDTO.setSgstAmount(detailVO.getSgstAmount());

				detailDTO.setCgstRate(detailVO.getCgstRate());

				detailDTO.setCgstAmount(detailVO.getCgstAmount());

				detailDTO.setIgstRate(detailVO.getIgstRate());

				detailDTO.setIgstAmount(detailVO.getIgstAmount());

				detailsList.add(detailDTO);
			}
		}

		dto.setSalesRejectionInvoiceDetails(detailsList);

		// =========================================================
		// TAX DETAILS CHILD
		// =========================================================

		List<SalesRejectionInvoiceTaxDetailsResponseDTO> taxDetailsList = new ArrayList<>();

		if (vo.getTaxDetails() != null) {

			for (SalesRejectionInvoiceTaxDetailsVO taxVO : vo.getTaxDetails()) {

				SalesRejectionInvoiceTaxDetailsResponseDTO taxDTO = new SalesRejectionInvoiceTaxDetailsResponseDTO();

				taxDTO.setId(taxVO.getId());

				// Particulars
				if (taxVO.getParticulars() != null) {

					ListOfValuesDetailsResponseDTO particularsDTO = new ListOfValuesDetailsResponseDTO();

					particularsDTO.setId(taxVO.getParticulars().getId());

					particularsDTO.setCode(taxVO.getParticulars().getValueCode());

					particularsDTO.setDescription(taxVO.getParticulars().getValueDescription());

					taxDTO.setParticulars(particularsDTO);
				}

				taxDTO.setGlAccountName(taxVO.getGlAccountName());

				taxDTO.setAcceptedQtyAmount(taxVO.getAcceptedQtyAmount());

				taxDTO.setRevisedAmount(taxVO.getRevisedAmount());

				taxDTO.setAmount(taxVO.getAmount());

				taxDetailsList.add(taxDTO);
			}
		}

		dto.setSalesRejectionInvoiceTaxDetails(taxDetailsList);

		return dto;
	}

	// DespatchInstructiondropdown

	@Override
	public List<Map<String, Object>> getDespatchInstructionNoforSalesRejectionInv(Long customer, Long orgId,
			Long branch, String docType) {

		List<Object[]> result = despatchInstructionRepo.getDespatchInstructionNoforSalesRejectionInv(customer, orgId,
				branch, docType);

		return getDespatchInstructionNoforSalesRejection(result);
	}

	private List<Map<String, Object>> getDespatchInstructionNoforSalesRejection(List<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("despatchInstructionNo", fs[0] != null ? fs[0].toString() : null);

			part.put("despatchInstructionDate", fs[1] != null ? fs[1] : null);

			part.put("scheduleNo", fs[2] != null ? fs[2].toString() : null);

			part.put("scheduleDate", fs[3] != null ? fs[3] : null);

			details.add(part);
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getCurrencyforSalesRejectionInv(Long customer, Long orgId, Long branch) {

		Set<Object[]> result = currencyRepo.getCurrencyforSalesRejectionInv(customer, orgId, branch);

		return getCurrencyforSalesRejectionInv(result);
	}

	private List<Map<String, Object>> getCurrencyforSalesRejectionInv(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("currencyId", fs[0] != null ? Long.valueOf(fs[0].toString()) : null);

			part.put("currency", fs[1] != null ? fs[1].toString() : null);

			part.put("exchangeRate", fs[2] != null ? new BigDecimal(fs[2].toString()) : BigDecimal.ZERO);

			details.add(part);
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getMonthYearForSalesRejectionInv(String docId, Long branch, Long orgId) {

		Set<Object[]> result = salesDeliveryScheduleRepo.getMonthYearForSalesRejectionInv(docId, branch, orgId);

		return getMonthYearDetails(result);
	}

	private List<Map<String, Object>> getMonthYearDetails(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("monthYear", fs[0] != null ? fs[0].toString() : null);

			details.add(part);
		}

		return details;
	}

	@Override
	public SalesRejectionInvoiceResponseDTO getSalesRejectionInvoiceById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Id");
		}

		SalesRejectionInvoiceVO salesRejectionInvoiceVO = salesRejectionInvoiceRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Sales Rejection Invoice Not Found"));

		return buildSalesRejectionInvoiceResponse(salesRejectionInvoiceVO);
	}

	@Override
	public List<SalesRejectionInvoiceResponseDTO> getSalesRejectionInvoiceByOrgId(Long orgId, Long branch)
			throws ApplicationException {

		List<SalesRejectionInvoiceVO> salesRejectionInvoiceList = salesRejectionInvoiceRepo
				.getSalesRejectionInvoiceByOrgId(orgId, branch);

		if (salesRejectionInvoiceList.isEmpty()) {
			throw new ApplicationException("No Sales Rejection Invoice Details Found");
		}

		List<SalesRejectionInvoiceResponseDTO> responseList = new ArrayList<>();

		for (SalesRejectionInvoiceVO salesRejectionInvoiceVO : salesRejectionInvoiceList) {
			responseList.add(buildSalesRejectionInvoiceResponse(salesRejectionInvoiceVO));
		}

		return responseList;
	}

	@Override
	public List<Map<String, Object>> getItemDetailsforSalesRejectionInvoice(String docId, Long orgId, Long branch) {

		Set<Object[]> result = despatchInstructionRepo.getItemDetailsforSalesRejectionInvoice(docId, orgId, branch);

		return getItemDetailsforSalesRejectionInvoice(result);
	}

	private List<Map<String, Object>> getItemDetailsforSalesRejectionInvoice(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("itemId", fs[0] != null ? Long.valueOf(fs[0].toString()) : null);

			part.put("itemCode", fs[1] != null ? fs[1].toString() : "");

			part.put("itemDescription", fs[2] != null ? fs[2].toString() : "");

			part.put("hsn", fs[3] != null ? fs[3].toString() : "");

			part.put("customerPartNo", fs[4] != null ? fs[4].toString() : "");

			part.put("unitMasterId", fs[5] != null ? Long.valueOf(fs[5].toString()) : null);

			part.put("unitId", fs[6] != null ? fs[6].toString() : "");

			part.put("gstRateMasterId", fs[7] != null ? Long.valueOf(fs[7].toString()) : null);

			part.put("cgst", fs[8] != null ? new BigDecimal(fs[8].toString()) : BigDecimal.ZERO);

			part.put("sgst", fs[9] != null ? new BigDecimal(fs[9].toString()) : BigDecimal.ZERO);

			part.put("igst", fs[10] != null ? new BigDecimal(fs[10].toString()) : BigDecimal.ZERO);

			part.put("salesOrderContractNo", fs[11] != null ? fs[11].toString() : "");

			part.put("despatchQty", fs[12] != null ? new BigDecimal(fs[12].toString()) : BigDecimal.ZERO);

			part.put("newRate", fs[13] != null ? new BigDecimal(fs[13].toString()) : BigDecimal.ZERO);

			details.add(part);
		}

		return details;
	}

	@Override
	public List<Map<String, Object>> getCustomerDetailsforSalesRejectionInvoice(Long orgId, Long branch)
			throws ApplicationException {

		Set<Object[]> result = customerRepo.getCustomerDetailsforSalesRejectionInvoice(orgId, branch);

		return getCustomerDetailsforSalesRejectionInvoiceResponse(result);
	}

	private List<Map<String, Object>> getCustomerDetailsforSalesRejectionInvoiceResponse(Set<Object[]> result) {

		List<Map<String, Object>> details = new ArrayList<>();

		for (Object[] fs : result) {

			Map<String, Object> part = new HashMap<>();

			part.put("customerId", fs[0] != null ? Long.valueOf(fs[0].toString()) : null);

			part.put("customerCode", fs[1] != null ? fs[1].toString() : "");

			part.put("customerName", fs[2] != null ? fs[2].toString() : "");

			part.put("gstState", fs[3] != null ? fs[3].toString() : "");

			part.put("gstNo", fs[4] != null ? fs[4].toString() : "");

			part.put("igstApplicable", fs[5] != null ? Boolean.valueOf(fs[5].toString()) : false);

			part.put("gstType", fs[6] != null ? fs[6].toString() : "");

			part.put("shippingAddress", fs[7] != null ? fs[7].toString() : "");

			part.put("shippingCity", fs[8] != null ? fs[8].toString() : "");

			part.put("shippingPincode", fs[9] != null ? fs[9].toString() : "");

			details.add(part);
		}

		return details;
	}

	@Override
	public String getSalesRejectionInvoiceDocId(Long orgId, String financialYear, String docType)
			throws ApplicationException {

		String screenCode;

		if ("Other Sales Invoice".equals(docType)) {
			screenCode = "SOI";
		} else if ("Invoice".equals(docType)) {
			screenCode = "DCI";
		} else if ("Rejection".equals(docType)) {
			screenCode = "RI";
		} else {
			throw new ApplicationException("Invalid Document Type");
		}

		return salesRejectionInvoiceRepo.getSalesRejectionInvoiceDocId(orgId, financialYear, screenCode);
	}
}
