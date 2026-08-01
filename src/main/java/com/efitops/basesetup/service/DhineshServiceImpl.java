package com.efitops.basesetup.service;

import java.math.BigDecimal;
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
import com.efitops.basesetup.ResponseDTO.GSTRateResponseDTO;
import com.efitops.basesetup.ResponseDTO.GSTStateResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.QuotationItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemDropdownResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractItemResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.SalesCustomerResponseDTO;
import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.SalesContractDTO;
import com.efitops.basesetup.dto.SalesContractDetailsDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.GSTRateMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.SalesContractDetailsVO;
import com.efitops.basesetup.entity.SalesContractVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.GstRateMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.SalesContractDetailsRepo;
import com.efitops.basesetup.repository.SalesContractRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;


@Service
public class DhineshServiceImpl implements DhineshService{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DhineshServiceImpl.class);

	@Autowired
	SalesContractRepo salesContractRepo;
	
	@Autowired
	BranchRepo branchRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	SalesContractDetailsRepo salesContractDetailsRepo;
	
	@Autowired
	ItemMasterRepo itemMasterRepo;
	
	@Autowired
	UnitMasterRepo unitMasterRepo;
	
	@Autowired
	GstRateMasterRepo gstRateRepo;
	
	@Autowired
	ItemMasterRepo itemRepo;
	
	@Override
	@Transactional
	public Map<String, Object> createUpdateSalesContract(SalesContractDTO dto) throws ApplicationException {

	    Map<String, Object> response = new HashMap<>();

	    String message;

	    SalesContractVO salesContractVO;

	    if (ObjectUtils.isEmpty(dto.getId())) {

	        salesContractVO = new SalesContractVO();

	        salesContractVO.setCreatedBy(dto.getCreatedBy());
	        salesContractVO.setUpdatedBy(dto.getCreatedBy());

	        message = "Sales Contract Created Successfully";

	    } else {

	        salesContractVO = salesContractRepo.findById(dto.getId())
	                .orElseThrow(() -> new ApplicationException("Sales Contract Not Found"));

	        List<SalesContractDetailsVO> oldDetails =
	                salesContractDetailsRepo.findBySalesContract(salesContractVO);

	        salesContractDetailsRepo.deleteAll(oldDetails);
	        
	        salesContractVO.setUpdatedBy(dto.getCreatedBy());

	        message = "Sales Contract Updated Successfully";
	    }

	    getSalesContractVOFromDTO(dto, salesContractVO);

	    salesContractVO = salesContractRepo.save(salesContractVO);

	    SalesContractResponseDTO responseDTO = convertToResponse(salesContractVO);

	    response.put("message", message);
	    response.put("salesContract", responseDTO);

	    return response;
	}
	
	private void getSalesContractVOFromDTO(SalesContractDTO dto, SalesContractVO salesContractVO)
	        throws ApplicationException {

	    BranchVO branch = branchRepo.findById(dto.getBranch())
	            .orElseThrow(() -> new ApplicationException("Branch Not Found"));

	    CustomerVO customer = customerRepo.findById(dto.getCustomer())
	            .orElseThrow(() -> new ApplicationException("Customer Not Found"));

	    salesContractVO.setCustomerContractNo(dto.getCustomerContractNo());
	    salesContractVO.setContractDate(dto.getContractDate());

	    salesContractVO.setBranch(branch);

	    salesContractVO.setBelongsTo(dto.getBelongsTo());
	    salesContractVO.setContractType(dto.getContractType());
	    salesContractVO.setWithQuotation(dto.getWithQuotation());
	    salesContractVO.setInvoiceType(dto.getInvoiceType());

	    salesContractVO.setCustomer(customer);

	    salesContractVO.setQuotationNo(dto.getQuotationNo());
	    salesContractVO.setQuotationDate(dto.getQuotationDate());

	    salesContractVO.setCustomerPoNo(dto.getCustomerPoNo());
	    salesContractVO.setCustomerPoDate(dto.getCustomerPoDate());

	    salesContractVO.setEffectiveFrom(dto.getEffectiveFrom());
	    salesContractVO.setEffectiveTo(dto.getEffectiveTo());

	    salesContractVO.setPostRate(dto.getPostRate());

	    salesContractVO.setOrgId(dto.getOrgId());
	    salesContractVO.setFinancialYear(dto.getFinancialYear());

	    salesContractVO.setCancelRemarks(dto.getCancelRemarks());
	    salesContractVO.setActive(dto.isActive());
	    
	    List<SalesContractDetailsVO> detailList = new ArrayList<>();

	    if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {

	        for (SalesContractDetailsDTO child : dto.getDetails()) {

	            SalesContractDetailsVO detailVO = new SalesContractDetailsVO();

	            ItemMasterVO item = itemMasterRepo.findById(child.getItem())
	                    .orElseThrow(() -> new ApplicationException("Item Not Found"));

	            UnitMasterVO unit = unitMasterRepo.findById(child.getUnit())
	                    .orElseThrow(() -> new ApplicationException("Unit Not Found"));
	            
	            GSTRateMasterVO gstRateVO = gstRateRepo.findById(child.getTaxPercentage())
	                    .orElseThrow(() -> new ApplicationException("GST Rate Not Found"));

	            detailVO.setItem(item);
	            detailVO.setTaxType(child.getTaxType());
	            detailVO.setTaxPercentage(gstRateVO);
	            detailVO.setGstRate(gstRateVO);

	            detailVO.setUnit(unit);
	            detailVO.setQuantity(child.getQuantity());
	            detailVO.setQuotationRate(child.getQuotationRate());
	            detailVO.setOrderRate(child.getOrderRate());
	            detailVO.setEffectiveFrom(child.getEffectiveFrom());
	            detailVO.setEffectiveTo(child.getEffectiveTo());

	            detailVO.setDiscountPercentage(child.getDiscountPercentage());

	            BigDecimal quantity = child.getQuantity() == null
	                    ? BigDecimal.ZERO
	                    : child.getQuantity();

	            BigDecimal orderRate = child.getOrderRate() == null
	                    ? BigDecimal.ZERO
	                    : child.getOrderRate();

	            BigDecimal discountPercentage = child.getDiscountPercentage() == null
	                    ? BigDecimal.ZERO
	                    : child.getDiscountPercentage();

	            // Order Amount = Qty × Order Rate
	         // Order Amount
	            BigDecimal orderAmount = quantity.multiply(orderRate);

	            // Discount Amount
	            BigDecimal discountAmount = orderAmount
	                    .multiply(discountPercentage)
	                    .divide(BigDecimal.valueOf(100));

	            // Amount after Discount
	            BigDecimal amount = orderAmount.subtract(discountAmount);

	            detailVO.setDiscountAmount(discountAmount);
	            detailVO.setAmount(amount);

	            BigDecimal finalAmount;

	            if (Boolean.TRUE.equals(salesContractVO.getIsIgstApplicable())) {

	                BigDecimal igstAmount = amount
	                        .multiply(gstRateVO.getIgst())
	                        .divide(BigDecimal.valueOf(100));

	                detailVO.setIgstAmount(igstAmount);
	                detailVO.setCgstAmount(BigDecimal.ZERO);
	                detailVO.setSgstAmount(BigDecimal.ZERO);

	                // Final Amount
	                finalAmount = amount.subtract(igstAmount);

	            } else {

	                BigDecimal cgstAmount = amount
	                        .multiply(gstRateVO.getCgst())
	                        .divide(BigDecimal.valueOf(100));

	                BigDecimal sgstAmount = amount
	                        .multiply(gstRateVO.getSgst())
	                        .divide(BigDecimal.valueOf(100));

	                detailVO.setCgstAmount(cgstAmount);
	                detailVO.setSgstAmount(sgstAmount);
	                detailVO.setIgstAmount(BigDecimal.ZERO);

	                // Final Amount
	                finalAmount = amount.subtract(cgstAmount.add(sgstAmount));
	            }

	            detailVO.setFinalAmount(finalAmount);

	            detailVO.setCurrency(child.getCurrency());
	            
	            // Header mapping
	            detailVO.setSalesContract(salesContractVO);

	            detailList.add(detailVO);
	        }
	    }

	    salesContractVO.setSalesContractDetailsVO(detailList);
	}
	
	private SalesContractResponseDTO convertToResponse(SalesContractVO vo) {

	    SalesContractResponseDTO dto = new SalesContractResponseDTO();

	    dto.setId(vo.getId());
	    dto.setCustomerContractNo(vo.getCustomerContractNo());
	    dto.setContractDate(vo.getContractDate());

	    if (vo.getBranch() != null) {
	        dto.setBranch(new BranchResponseDTO(
	                vo.getBranch().getId(),
	                vo.getBranch().getBranchCode(),
	                vo.getBranch().getBranchName()));
	    }

	    if (vo.getCustomer() != null) {

	        SalesCustomerResponseDTO customerDTO = new SalesCustomerResponseDTO();

	        customerDTO.setCustomerId(vo.getCustomer().getId());
	        customerDTO.setCustomerName(vo.getCustomer().getCustomerName());
	        customerDTO.setCustomerType(vo.getCustomer().getCustomerType());

	        if (vo.getCustomer().getGstState() != null) {
	            customerDTO.setGstState(
	                new GSTStateResponseDTO(
	                    vo.getCustomer().getGstState().getId(),
	                    vo.getCustomer().getGstState().getStateCode(),
	                    vo.getCustomer().getGstState().getStateName(),
	                    vo.getCustomer().getGstState().getGstStateId()
	                )
	            );
	        }

	        customerDTO.setIgstApplicable(vo.getCustomer().isGstApplicable());
	        customerDTO.setGstnNo(vo.getCustomer().getGstNo());

	        dto.setCustomer(customerDTO);
	    }

	    dto.setBelongsTo(vo.getBelongsTo());
	    dto.setContractType(vo.getContractType());
	    dto.setWithQuotation(vo.getWithQuotation());
	    dto.setInvoiceType(vo.getInvoiceType());

	    dto.setQuotationNo(vo.getQuotationNo());
	    dto.setQuotationDate(vo.getQuotationDate());

	    dto.setCustomerPoNo(vo.getCustomerPoNo());
	    dto.setCustomerPoDate(vo.getCustomerPoDate());

	    dto.setEffectiveFrom(vo.getEffectiveFrom());
	    dto.setEffectiveTo(vo.getEffectiveTo());

	    dto.setPostRate(vo.getPostRate());

	    dto.setOrgId(vo.getOrgId());
	    dto.setFinancialYear(vo.getFinancialYear());

	    dto.setCreatedBy(vo.getCreatedBy());
	    dto.setUpdatedBy(vo.getUpdatedBy());

	    dto.setCancelRemarks(vo.getCancelRemarks());
	    dto.setActive(vo.isActive());
	    
	    
	    // Details Mapping
	    List<SalesContractDetailsResponseDTO> detailResponse = new ArrayList<>();

	    if (vo.getSalesContractDetailsVO() != null) {

	        for (SalesContractDetailsVO detail : vo.getSalesContractDetailsVO()) {

	            SalesContractDetailsResponseDTO detailDTO = new SalesContractDetailsResponseDTO();

	            detailDTO.setId(detail.getId());

	            if (detail.getItem() != null) {
	            	detailDTO.setItem(new SalesContractItemResponseDTO(
	            	        detail.getItem().getId(),
	            	        detail.getItem().getItemCode(),
	            	        detail.getItem().getItemDescription(),
	            	        detail.getItem().getHsnCode() != null
	                        ? detail.getItem().getHsnCode().getHsn()
	                        : null,
	            	        vo.getQuotationNo() != null ? vo.getCustomerPoNo() : null
	            	));
	            }

	            detailDTO.setTaxType(detail.getTaxType());
	            if (detail.getTaxPercentage() != null) {

	                GSTRateResponseDTO gstRateDTO = new GSTRateResponseDTO();

	                gstRateDTO.setId(detail.getTaxPercentage().getId());
	                gstRateDTO.setTaxPercentage(detail.getTaxPercentage().getRate()); // or getGstRate()

	                detailDTO.setTaxPercentage(gstRateDTO);
	            }
	            if (detail.getUnit() != null) {
	                detailDTO.setUnit(new UnitResponseDTO(
	                        detail.getUnit().getId(),
	                        detail.getUnit().getUnitId()));
	            }

	            detailDTO.setQuantity(detail.getQuantity());
	            detailDTO.setQuotationRate(detail.getQuotationRate());
	            detailDTO.setOrderRate(detail.getOrderRate());

	            detailDTO.setDiscountPercentage(detail.getDiscountPercentage());
	            detailDTO.setEffectiveFrom(detail.getEffectiveFrom());
	            detailDTO.setEffectiveTo(detail.getEffectiveTo());

	            detailDTO.setDiscountAmount(detail.getDiscountAmount());
	            detailDTO.setAmount(detail.getAmount());
	            detailDTO.setFinalAmount(detail.getFinalAmount());

	            detailDTO.setSgstRate(detail.getSgstRate());
	            detailDTO.setSgstAmount(detail.getSgstAmount());

	            detailDTO.setCgstRate(detail.getCgstRate());
	            detailDTO.setCgstAmount(detail.getCgstAmount());

	            detailDTO.setIgstRate(detail.getIgstRate());
	            detailDTO.setIgstAmount(detail.getIgstAmount());

	            detailDTO.setCurrency(detail.getCurrency());

	            detailResponse.add(detailDTO);
	        }
	    }

	    dto.setDetails(detailResponse);

	    return dto;
	}
	
	
	//dropdown
	
	
	@Override
	public List<SalesContractItemDropdownResponseDTO> getFinishedGoodsItems(Long orgId, Long branch)
	        throws ApplicationException {

	    List<Object[]> itemList = itemRepo.getFinishedGoodsItems(orgId, branch);

	    List<SalesContractItemDropdownResponseDTO> responseList = new ArrayList<>();

	    for (Object[] obj : itemList) {
	        responseList.add(mapToFinishedGoodsResponseDTO(obj));
	    }

	    return responseList;
	}
	
	private SalesContractItemDropdownResponseDTO mapToFinishedGoodsResponseDTO(Object[] obj) {

	    SalesContractItemDropdownResponseDTO dto = new SalesContractItemDropdownResponseDTO();

	    dto.setItemId(((Number) obj[0]).longValue());
	    dto.setItemCode((String) obj[1]);
	    dto.setItemDescription((String) obj[2]);
	    dto.setUnitId((String) obj[3]);
	    dto.setMinimumSellPrice((BigDecimal) obj[4]);
	    dto.setHsnCode((String) obj[5]);
	    dto.setCustomerPartNo((String) obj[6]);

	    return dto;
	}
	
	
	@Override
	public List<QuotationDropdownResponseDTO> getQuotationDropdown(
	        String customerCode,
	        String ctype,
	        Long orgId,
	        Long branch,
	        String oldQuotationNo,
	        Long recId) throws ApplicationException {

	    List<Object[]> list = salesContractRepo.getQuotationDropdown(
	            customerCode,
	            ctype,
	            orgId,
	            branch,
	            oldQuotationNo,
	            recId);

	    return convertToQuotationDropdownDTO(list);
	}
	
	private List<QuotationDropdownResponseDTO> convertToQuotationDropdownDTO(List<Object[]> list) {

	    List<QuotationDropdownResponseDTO> responseList = new ArrayList<>();

	    for (Object[] obj : list) {

	        QuotationDropdownResponseDTO dto = new QuotationDropdownResponseDTO();

	        dto.setQuotationId(
	                obj[0] != null ? ((Number) obj[0]).longValue() : null);

	        dto.setQuotationNo(
	                obj[1] != null ? obj[1].toString() : null);

	        dto.setQuotationDate(
	                obj[2] != null ? ((java.sql.Date) obj[2]).toLocalDate() : null);

	        responseList.add(dto);
	    }

	    return responseList;
	}
	
	@Override
	public List<CustomerDropdownResponseDTO> getCustomerDropdown(String ctype, Long orgId, Long branch)
	        throws ApplicationException {

	    List<Object[]> list = customerRepo.getCustomerDropdown(ctype, orgId, branch);

	    return convertToCustomerDropdownDTO(list);
	}

	private List<CustomerDropdownResponseDTO> convertToCustomerDropdownDTO(List<Object[]> list) {

	    List<CustomerDropdownResponseDTO> responseList = new ArrayList<>();

	    for (Object[] obj : list) {

	        CustomerDropdownResponseDTO dto = new CustomerDropdownResponseDTO();

	        dto.setCustomerId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
	        dto.setCustomerCode(obj[1] != null ? obj[1].toString() : null);
	        dto.setCustomerName(obj[2] != null ? obj[2].toString() : null);
	        dto.setAddress(obj[3] != null ? obj[3].toString() : null);
	        dto.setGstState(obj[4] != null ? obj[4].toString() : null);
	        dto.setGstNo(obj[5] != null ? obj[5].toString() : null);
	        dto.setIgstApplicable(obj[6] != null ? (Boolean) obj[6] : false);
	        dto.setGstType(obj[7] != null ? obj[7].toString() : null);

	        responseList.add(dto);
	    }

	    return responseList;
	}
	
	
	@Override
	public List<QuotationItemDropdownResponseDTO> getQuotationItemDropdown(
	        String quotationNo,
	        Long orgId,
	        Long branch) throws ApplicationException {

	    List<Object[]> list = salesContractRepo.getQuotationItemDropdown(
	            quotationNo,
	            orgId,
	            branch);

	    return convertToQuotationItemDropdownDTO(list);
	}
	
	private List<QuotationItemDropdownResponseDTO> convertToQuotationItemDropdownDTO(List<Object[]> list) {

	    List<QuotationItemDropdownResponseDTO> responseList = new ArrayList<>();

	    for (Object[] obj : list) {

	        QuotationItemDropdownResponseDTO dto = new QuotationItemDropdownResponseDTO();

	        dto.setItemId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
	        dto.setItemCode(obj[1] != null ? obj[1].toString() : null);
	        dto.setItemDescription(obj[2] != null ? obj[2].toString() : null);
	        dto.setHsnCode(obj[3] != null ? obj[3].toString() : null);
	        dto.setCustomerPartNo(obj[4] != null ? obj[4].toString() : null);

	        responseList.add(dto);
	    }

	    return responseList;
	}
	

}
