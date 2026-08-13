package com.efitops.basesetup.service;

import java.math.BigDecimal;
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

import com.efitops.basesetup.ResponseDTO.CustomerOtherSalesResponseDTO;
import com.efitops.basesetup.ResponseDTO.DespatchInstructionResponseDocIdDTO;
import com.efitops.basesetup.ResponseDTO.ItemMasterDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.LocationMasterResponseDTO;
import com.efitops.basesetup.ResponseDTO.RejectionInvoiceDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.RejectionInvoiceResponseDTO;
import com.efitops.basesetup.ResponseDTO.RejectionInvoiceTaxDetailsResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.dto.RejectionInvoiceDTO;
import com.efitops.basesetup.dto.RejectionInvoiceDetailsDTO;
import com.efitops.basesetup.dto.RejectionInvoiceTaxDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.DespatchInstructionVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.RejectionInvoiceDetailsVO;
import com.efitops.basesetup.entity.RejectionInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.RejectionInvoiceVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DespatchInstructionRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.LocationRepo;
import com.efitops.basesetup.repository.RejectionInvoiceDetailsRepo;
import com.efitops.basesetup.repository.RejectionInvoiceRepo;
import com.efitops.basesetup.repository.RejectionInvoiceTaxDetailsRepo;
import com.efitops.basesetup.repository.SalesOrderShortCloseRepo;

@Service
public class RejectionInvoiceServiceImpl implements RejectionInvoiceService {

    public static final Logger LOGGER = LoggerFactory.getLogger(RejectionInvoiceServiceImpl.class);

    @Autowired
    RejectionInvoiceRepo rejectionInvoiceRepo;

    @Autowired
    RejectionInvoiceTaxDetailsRepo rejectionInvoiceTaxDetailsRepo;

    @Autowired
    RejectionInvoiceDetailsRepo rejectionInvoiceDetailsRepo;

    @Autowired
    BranchRepo branchRepo;

    @Autowired
    LocationRepo locationRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ItemMasterRepo itemMasterRepo;

    @Autowired
    CurrencyRepo currencyRepo;

    @Autowired
    DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

    @Autowired
    SalesOrderShortCloseRepo salesOrderShortCloseRepo;

    @Autowired
    DespatchInstructionRepo despatchInstructionRepo;

    @Autowired
    AmountInWordsConverterService amountInWordsConverterService;

    @Override
    public RejectionInvoiceResponseDTO getRejectionInvoiceById(Long id) throws ApplicationException {

        RejectionInvoiceVO rejectionInvoiceVO = rejectionInvoiceRepo.getRejectionInvoiceById(id);

        if (rejectionInvoiceVO == null) {
            throw new ApplicationException("Rejection Invoice Not Found");
        }

        return buildRejectionInvoiceResponse(rejectionInvoiceVO);
    }

    @Override
    public List<RejectionInvoiceResponseDTO> getRejectionInvoiceByOrgId(Long orgId, Long branch)
            throws ApplicationException {

        List<RejectionInvoiceVO> quotationList = rejectionInvoiceRepo.getRejectionInvoiceByOrgId(orgId, branch);

        if (quotationList == null || quotationList.isEmpty()) {
            throw new ApplicationException("Rejection Invoice Not Found");
        }

        List<RejectionInvoiceResponseDTO> responseList = new ArrayList<>();

        for (RejectionInvoiceVO rejectionInvoiceVO : quotationList) {
            responseList.add(buildRejectionInvoiceResponse(rejectionInvoiceVO));
        }

        return responseList;
    }

    @Override
    @Transactional
    public Map<String, Object> createUpdateRejectionInvoice(RejectionInvoiceDTO rejectionInvoiceDTO)
            throws ApplicationException {
        String screenCode = "RI"; 
        RejectionInvoiceVO rejectionInvoiceVO = new RejectionInvoiceVO();
        String message;

        if (ObjectUtils.isNotEmpty(rejectionInvoiceDTO.getId())) {

            rejectionInvoiceVO = rejectionInvoiceRepo.findById(rejectionInvoiceDTO.getId())
                    .orElseThrow(() -> new ApplicationException("Rejection Invoice Not Found"));

            rejectionInvoiceVO.setUpdatedBy(rejectionInvoiceDTO.getCreatedBy());

            message = "Rejection Invoice Updated Successfully";

        } else {

            String docId = rejectionInvoiceRepo.getRejectionInvoiceDocId(rejectionInvoiceDTO.getOrgId(), screenCode);

            rejectionInvoiceVO.setDocId(docId);

            DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
                    .findByOrgIdScreenCode(rejectionInvoiceDTO.getOrgId(), screenCode);
            documentTypeMappingDetailsVO.setLastNo(documentTypeMappingDetailsVO.getLastNo() + 1);
            documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

            rejectionInvoiceVO.setCreatedBy(rejectionInvoiceDTO.getCreatedBy());
            rejectionInvoiceVO.setUpdatedBy(rejectionInvoiceDTO.getCreatedBy());

            message = "Rejection Invoice Created Successfully";
        }

        createUpdateRejectionInvoiceVOByRejectionInvoiceDTO(rejectionInvoiceDTO, rejectionInvoiceVO);

        rejectionInvoiceVO = rejectionInvoiceRepo.save(rejectionInvoiceVO);

        RejectionInvoiceResponseDTO responseDTO = buildRejectionInvoiceResponse(rejectionInvoiceVO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("rejectionInvoiceVO", responseDTO);

        return response;
    }

    private void createUpdateRejectionInvoiceVOByRejectionInvoiceDTO(RejectionInvoiceDTO rejectionInvoiceDTO,
            RejectionInvoiceVO rejectionInvoiceVO) throws ApplicationException {

        rejectionInvoiceVO.setBelongsTo(rejectionInvoiceDTO.getBelongsTo());

        if (rejectionInvoiceDTO.getCustomer() != null && rejectionInvoiceDTO.getCustomer() != 0) {

            CustomerVO customer = customerRepo.findById(rejectionInvoiceDTO.getCustomer())
                    .orElseThrow(() -> new ApplicationException("Party Not Found"));

            rejectionInvoiceVO.setCustomer(customer);
        }

        rejectionInvoiceVO.setId(rejectionInvoiceDTO.getId());

        rejectionInvoiceVO.setMonthYear(rejectionInvoiceDTO.getMonthYear());

        rejectionInvoiceVO.setBelongsTo(rejectionInvoiceDTO.getBelongsTo());

        rejectionInvoiceVO.setDocType(rejectionInvoiceDTO.getDocType());

        rejectionInvoiceVO.setStockPosting(rejectionInvoiceDTO.getStockPosting());

        rejectionInvoiceVO.setExcisable(rejectionInvoiceDTO.getExcisable());

        rejectionInvoiceVO.setVehicle(rejectionInvoiceDTO.getVehicle());

        rejectionInvoiceVO.setKanbanCardNo(rejectionInvoiceDTO.getKanbanCardNo());

        rejectionInvoiceVO.setInvoiceType(rejectionInvoiceDTO.getInvoiceType());

        rejectionInvoiceVO.setSchNo(rejectionInvoiceDTO.getSchNo());

        rejectionInvoiceVO.setSchDate(rejectionInvoiceDTO.getSchDate());

        rejectionInvoiceVO.setExchangeRate(rejectionInvoiceDTO.getExchangeRate());

        rejectionInvoiceVO.setTotalInsurance(rejectionInvoiceDTO.getTotalInsurance());

        rejectionInvoiceVO.setTotalFreight(rejectionInvoiceDTO.getTotalFreight());

        rejectionInvoiceVO.setModeOfTransport(rejectionInvoiceDTO.getModeOfTransport());

        rejectionInvoiceVO.setDeliveryTo(rejectionInvoiceDTO.getDeliveryTo());

        rejectionInvoiceVO.setPaymentTerms(rejectionInvoiceDTO.getPaymentTerms());

        rejectionInvoiceVO.setPurchaseOrder(rejectionInvoiceDTO.getPurchaseOrder());

        rejectionInvoiceVO.setPurchaseOrderDate(rejectionInvoiceDTO.getPurchaseOrderDate());

        rejectionInvoiceVO.setActive(rejectionInvoiceDTO.isActive());

        rejectionInvoiceVO.setCancelRemarks(rejectionInvoiceDTO.getCancelRemarks());

        rejectionInvoiceVO.setOrgId(rejectionInvoiceDTO.getOrgId());

        rejectionInvoiceVO.setFinancialYear(rejectionInvoiceDTO.getFinancialYear());

        rejectionInvoiceVO.setIsIgstApplicable(rejectionInvoiceDTO.getIsIgstApplicable());

//        rejectionInvoiceVO.setRejectionType(rejectionInvoiceDTO.getRejectionType());
//        
//        rejectionInvoiceVO.setReasonForRejection(rejectionInvoiceDTO.getReasonForRejection());
//        
//        rejectionInvoiceVO.setOriginalInvoiceNo(rejectionInvoiceDTO.getOriginalInvoiceNo());
//        
//        rejectionInvoiceVO.setOriginalInvoiceDate(rejectionInvoiceDTO.getOriginalInvoiceDate());

        if (rejectionInvoiceDTO.getBranch() != null && rejectionInvoiceDTO.getBranch() > 0) {

            BranchVO branch = branchRepo.findById(rejectionInvoiceDTO.getBranch())
                    .orElseThrow(() -> new ApplicationException("Branch Not Found"));

            rejectionInvoiceVO.setBranch(branch);
        }

        if (rejectionInvoiceDTO.getDiNo() != null && rejectionInvoiceDTO.getDiNo() > 0) {

            DespatchInstructionVO branch = despatchInstructionRepo.findById(rejectionInvoiceDTO.getDiNo())
                    .orElseThrow(() -> new ApplicationException("Despatch Not Found"));

            rejectionInvoiceVO.setDiNo(branch);
        }

        if (rejectionInvoiceDTO.getLocation() != null && rejectionInvoiceDTO.getLocation() > 0) {

            LocationVO branch = locationRepo.findById(rejectionInvoiceDTO.getLocation())
                    .orElseThrow(() -> new ApplicationException("Location Not Found"));

            rejectionInvoiceVO.setLocation(branch);
        }

        if (rejectionInvoiceDTO.getCurrency() != null && rejectionInvoiceDTO.getCurrency() > 0) {

            CurrencyVO branch = currencyRepo.findById(rejectionInvoiceDTO.getCurrency())
                    .orElseThrow(() -> new ApplicationException("Currency Not Found"));

            rejectionInvoiceVO.setCurrency(branch);
        }

        if (ObjectUtils.isNotEmpty(rejectionInvoiceVO.getId())) {

            List<RejectionInvoiceDetailsVO> rejectionInvoiceDetailsVO = rejectionInvoiceDetailsRepo
                    .findByRejectionInvoiceVO(rejectionInvoiceVO);

            rejectionInvoiceDetailsRepo.deleteAll(rejectionInvoiceDetailsVO);

            List<RejectionInvoiceTaxDetailsVO> rejectionInvoiceTaxDetailsVO = rejectionInvoiceTaxDetailsRepo
                    .findByRejectionInvoiceVO(rejectionInvoiceVO);

            rejectionInvoiceTaxDetailsRepo.deleteAll(rejectionInvoiceTaxDetailsVO);
        }

        BigDecimal finalAmount = BigDecimal.ZERO;
        BigDecimal assTotal = BigDecimal.ZERO;

        List<RejectionInvoiceDetailsVO> itemDetailsList = new ArrayList<>();

        if (rejectionInvoiceDTO.getRejectionInvoiceDetailsDTO() != null) {

            for (RejectionInvoiceDetailsDTO dto : rejectionInvoiceDTO.getRejectionInvoiceDetailsDTO()) {

                RejectionInvoiceDetailsVO detailsVO = new RejectionInvoiceDetailsVO();

                if (dto.getItem() != null && dto.getItem() != 0) {

                    ItemMasterVO item = itemMasterRepo.findById(dto.getItem())
                            .orElseThrow(() -> new ApplicationException("Item Code Not Found"));

                    detailsVO.setItem(item);
                }

                detailsVO.setTaxType(dto.getTaxType());

                detailsVO.setTariffNo(dto.getTariffNo());

                detailsVO.setStock(dto.getStock());

                detailsVO.setSalesOrderContractNo(dto.getSalesOrderContractNo());

                detailsVO.setQty(dto.getQty());

                detailsVO.setNoOfPackages(dto.getNoOfPackages());

                detailsVO.setPackageType(dto.getPackageType());

                detailsVO.setHsnCode(dto.getHsnCode());

                detailsVO.setOrderRate(dto.getOrderRate());

                detailsVO.setTaxPercentage(dto.getTaxPercentage());

                detailsVO.setRateInSelectedCurrency(dto.getRateInSelectedCurrency());

                BigDecimal quantity = dto.getQty() == null ? BigDecimal.ZERO : dto.getQty();

                BigDecimal amount = dto.getRateInSelectedCurrency() == null ? BigDecimal.ZERO
                        : dto.getRateInSelectedCurrency();

                BigDecimal orderAmount = quantity.multiply(amount);

                detailsVO.setAmtInSelectedCurrency(orderAmount);

                detailsVO.setAmountInRs(orderAmount);

                assTotal = assTotal.add(detailsVO.getAmountInRs());

                BigDecimal igstRate = BigDecimal.ZERO;
                BigDecimal cgstRate = BigDecimal.ZERO;
                BigDecimal sgstRate = BigDecimal.ZERO;

                BigDecimal igstAmount = BigDecimal.ZERO;
                BigDecimal cgstAmount = BigDecimal.ZERO;
                BigDecimal sgstAmount = BigDecimal.ZERO;

                if (rejectionInvoiceDTO.getIsIgstApplicable() != null
                        && rejectionInvoiceDTO.getIsIgstApplicable().equalsIgnoreCase("Yes")) {

                    igstRate = dto.getTaxPercentage() != null ? dto.getTaxPercentage() : BigDecimal.ZERO;

                    igstAmount = orderAmount.multiply(igstRate).divide(BigDecimal.valueOf(100));

                    cgstRate = BigDecimal.ZERO;
                    sgstRate = BigDecimal.ZERO;

                    cgstAmount = BigDecimal.ZERO;
                    sgstAmount = BigDecimal.ZERO;

                } else {

                    BigDecimal taxPercentage = dto.getTaxPercentage() != null ? dto.getTaxPercentage()
                            : BigDecimal.ZERO;

                    cgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

                    sgstRate = taxPercentage.divide(BigDecimal.valueOf(2));

                    cgstAmount = orderAmount.multiply(cgstRate).divide(BigDecimal.valueOf(100));

                    sgstAmount = orderAmount.multiply(sgstRate).divide(BigDecimal.valueOf(100));

                    igstRate = BigDecimal.ZERO;
                    igstAmount = BigDecimal.ZERO;
                }

                detailsVO.setIgstRate(igstRate);
                detailsVO.setCgstRate(cgstRate);
                detailsVO.setSgstRate(sgstRate);

                detailsVO.setIgstAmount(igstAmount);
                detailsVO.setCgstAmount(cgstAmount);
                detailsVO.setSgstAmount(sgstAmount);
                BigDecimal taxAmount = igstAmount.add(cgstAmount).add(sgstAmount);

                BigDecimal finalAmounts = orderAmount.add(taxAmount);

                finalAmount = finalAmount.add(finalAmounts);
                detailsVO.setRejectionInvoiceVO(rejectionInvoiceVO);

                itemDetailsList.add(detailsVO);
            }
        }

        rejectionInvoiceVO.setRejectionInvoiceDetailsVO(itemDetailsList);

        List<RejectionInvoiceTaxDetailsVO> taxList = new ArrayList<>();

        if (rejectionInvoiceDTO.getRejectionInvoiceTaxDetailsDTO() != null) {

            for (RejectionInvoiceTaxDetailsDTO dto : rejectionInvoiceDTO.getRejectionInvoiceTaxDetailsDTO()) {

                RejectionInvoiceTaxDetailsVO taxVO = new RejectionInvoiceTaxDetailsVO();

                taxVO.setRejectionInvoiceVO(rejectionInvoiceVO);

                taxVO.setParticulars(dto.getParticulars());

                taxVO.setAcceptedQtyAmount(dto.getAcceptedQtyAmount());

                taxVO.setRevisedAmount(dto.getRevisedAmount());

                taxList.add(taxVO);
            }
        }

        rejectionInvoiceVO.setNetAmount(finalAmount);

        rejectionInvoiceVO.setTotalAssVal(assTotal);

        rejectionInvoiceVO.setAmountInWords(amountInWordsConverterService.convert(rejectionInvoiceVO.getNetAmount()));

        rejectionInvoiceVO.setRejectionInvoiceTaxDetailsVO(taxList);

    }

    private RejectionInvoiceResponseDTO buildRejectionInvoiceResponse(RejectionInvoiceVO rejectionInvoiceVO) {

        RejectionInvoiceResponseDTO responseDTO = new RejectionInvoiceResponseDTO();

        responseDTO.setId(rejectionInvoiceVO.getId());

        responseDTO.setDocId(rejectionInvoiceVO.getDocId());

        responseDTO.setDocDate(rejectionInvoiceVO.getDocDate());

        responseDTO.setMonthYear(rejectionInvoiceVO.getMonthYear());

        responseDTO.setBelongsTo(rejectionInvoiceVO.getBelongsTo());

        responseDTO.setDocType(rejectionInvoiceVO.getDocType());

        responseDTO.setStockPosting(rejectionInvoiceVO.getStockPosting());

        responseDTO.setExcisable(rejectionInvoiceVO.getExcisable());

        responseDTO.setVehicle(rejectionInvoiceVO.getVehicle());

        responseDTO.setTimeOfIssue(rejectionInvoiceVO.getTimeOfIssue());

        responseDTO.setTimeOfIssueDate(rejectionInvoiceVO.getTimeOfIssueDate());

        responseDTO.setTimeOfRemoval(rejectionInvoiceVO.getTimeOfRemoval());

        responseDTO.setTimeOfRemovalDate(rejectionInvoiceVO.getTimeOfRemovalDate());

        responseDTO.setKanbanCardNo(rejectionInvoiceVO.getKanbanCardNo());

        responseDTO.setInvoiceType(rejectionInvoiceVO.getInvoiceType());

        responseDTO.setSchNo(rejectionInvoiceVO.getSchNo());

        responseDTO.setSchDate(rejectionInvoiceVO.getSchDate());

        responseDTO.setExchangeRate(rejectionInvoiceVO.getExchangeRate());

        responseDTO.setTotalInsurance(rejectionInvoiceVO.getTotalInsurance());

        responseDTO.setTotalFreight(rejectionInvoiceVO.getTotalFreight());

        responseDTO.setTotalAssVal(rejectionInvoiceVO.getTotalAssVal());

        responseDTO.setModeOfTransport(rejectionInvoiceVO.getModeOfTransport());

        responseDTO.setNetAmount(rejectionInvoiceVO.getNetAmount());

        responseDTO.setAmountInWords(rejectionInvoiceVO.getAmountInWords());

        responseDTO.setDeliveryTo(rejectionInvoiceVO.getDeliveryTo());

        responseDTO.setPaymentTerms(rejectionInvoiceVO.getPaymentTerms());

        responseDTO.setPurchaseOrder(rejectionInvoiceVO.getPurchaseOrder());

        responseDTO.setPurchaseOrderDate(rejectionInvoiceVO.getPurchaseOrderDate());

        responseDTO.setIsIgstApplicable(rejectionInvoiceVO.getIsIgstApplicable());

        responseDTO.setCreatedBy(rejectionInvoiceVO.getCreatedBy());

        responseDTO.setActive(rejectionInvoiceVO.getActive());

        responseDTO.setCancel(rejectionInvoiceVO.getCancel());

        responseDTO.setUpdatedBy(rejectionInvoiceVO.getUpdatedBy());

        responseDTO.setCancelRemarks(rejectionInvoiceVO.getCancelRemarks());

        responseDTO.setOrgId(rejectionInvoiceVO.getOrgId());

        responseDTO.setFinancialYear(rejectionInvoiceVO.getFinancialYear());

        responseDTO.setScreenName(rejectionInvoiceVO.getScreenName());

        responseDTO.setScreenCode(rejectionInvoiceVO.getScreenCode());
        
//        responseDTO.setRejectionType(rejectionInvoiceVO.getRejectionType());
//        
//        responseDTO.setReasonForRejection(rejectionInvoiceVO.getReasonForRejection());
//        
//        responseDTO.setOriginalInvoiceNo(rejectionInvoiceVO.getOriginalInvoiceNo());
//        
//        responseDTO.setOriginalInvoiceDate(rejectionInvoiceVO.getOriginalInvoiceDate());

        if (rejectionInvoiceVO.getBranch() != null) {

            BranchResponseDTO branchDTO = new BranchResponseDTO();

            branchDTO.setId(rejectionInvoiceVO.getBranch().getId());

            branchDTO.setBranchCode(rejectionInvoiceVO.getBranch().getBranchCode());

            branchDTO.setBranchName(rejectionInvoiceVO.getBranch().getBranchName());

            responseDTO.setBranch(branchDTO);
        }

        if (rejectionInvoiceVO.getCustomer() != null) {

            CustomerOtherSalesResponseDTO customerDTO = new CustomerOtherSalesResponseDTO();

            customerDTO.setId(rejectionInvoiceVO.getCustomer().getId());
            customerDTO.setCustomerName(rejectionInvoiceVO.getCustomer().getCustomerName());
            customerDTO.setCustomerCode(rejectionInvoiceVO.getCustomer().getCustomerCode());
            customerDTO.setCustomerGstNo(rejectionInvoiceVO.getCustomer().getGstNo());
            customerDTO.setGstApproval(rejectionInvoiceVO.getCustomer().isGstApplicable() ? "Yes" : "No");
            responseDTO.setCustomer(customerDTO);
        }

        if (rejectionInvoiceVO.getDiNo() != null) {

            DespatchInstructionResponseDocIdDTO customerDTO = new DespatchInstructionResponseDocIdDTO();

            customerDTO.setId(rejectionInvoiceVO.getDiNo().getId());
            customerDTO.setDocId(rejectionInvoiceVO.getDiNo().getDiNo());

            responseDTO.setDiNo(customerDTO);
        }

        if (rejectionInvoiceVO.getLocation() != null) {

            LocationMasterResponseDTO customerDTO = new LocationMasterResponseDTO();

            customerDTO.setId(rejectionInvoiceVO.getLocation().getId());
            customerDTO.setLocationName(rejectionInvoiceVO.getLocation().getLocationName());

            responseDTO.setLocation(customerDTO);
        }

        if (rejectionInvoiceVO.getCurrency() != null) {

            CurrencyResponseDTO customerDTO = new CurrencyResponseDTO();

            customerDTO.setId(rejectionInvoiceVO.getCurrency().getId());
            customerDTO.setCurrencyName(rejectionInvoiceVO.getCurrency().getCurrency());

            responseDTO.setCurrency(customerDTO);

        }

        List<RejectionInvoiceDetailsResponseDTO> detailsList = new ArrayList<>();

        if (rejectionInvoiceVO.getRejectionInvoiceDetailsVO() != null) {

            for (RejectionInvoiceDetailsVO detailsVO : rejectionInvoiceVO.getRejectionInvoiceDetailsVO()) {

                RejectionInvoiceDetailsResponseDTO detailsDTO = new RejectionInvoiceDetailsResponseDTO();

                detailsDTO.setId(detailsVO.getId());

                if (detailsVO.getItem() != null) {

                    ItemMasterDetailsResponseDTO itemMasterDetailsResponseDTO = new ItemMasterDetailsResponseDTO();

                    itemMasterDetailsResponseDTO.setId(detailsVO.getItem().getId());

                    itemMasterDetailsResponseDTO.setItemCode(detailsVO.getItem().getItemCode());

                    itemMasterDetailsResponseDTO.setItemDescription(detailsVO.getItem().getItemDescription());

                    itemMasterDetailsResponseDTO.setCustomerPoNo(detailsVO.getItem().getCustomerPartNo());

                    itemMasterDetailsResponseDTO.setHsnCode(detailsVO.getItem().getHsnCode().getHsn());

                    if (detailsVO.getItem().getPricingUnit() != null) {

                        UnitMasterResponseDTO unitDTO = new UnitMasterResponseDTO();

                        unitDTO.setId(detailsVO.getItem().getPricingUnit().getId());
                        unitDTO.setUnitId(detailsVO.getItem().getPricingUnit().getUnitId());
                        unitDTO.setUnitDescription(detailsVO.getItem().getPricingUnit().getUnitId());

                        itemMasterDetailsResponseDTO.setUnit(unitDTO);
                    }

                    detailsDTO.setItem(itemMasterDetailsResponseDTO);
                }

                detailsDTO.setTaxType(detailsVO.getTaxType());

                detailsDTO.setTaxPercentage(detailsVO.getTaxPercentage());

                detailsDTO.setTariffNo(detailsVO.getTariffNo());

                detailsDTO.setStock(detailsVO.getStock());

                detailsDTO.setSalesOrderContractNo(detailsVO.getSalesOrderContractNo());

                detailsDTO.setQty(detailsVO.getQty());

                detailsDTO.setNoOfPackages(detailsVO.getNoOfPackages());

                detailsDTO.setHsnCode(detailsVO.getHsnCode());

                detailsDTO.setPackageType(detailsVO.getPackageType());

                detailsDTO.setOrderRate(detailsVO.getOrderRate());

                detailsDTO.setRateInSelectedCurrency(detailsVO.getRateInSelectedCurrency());

                detailsDTO.setAmtInSelectedCurrency(detailsVO.getAmtInSelectedCurrency());

                detailsDTO.setAmountInRs(detailsVO.getAmountInRs());

                detailsDTO.setSgstRate(detailsVO.getSgstRate());

                detailsDTO.setSgstAmount(detailsVO.getSgstAmount());

                detailsDTO.setCgstRate(detailsVO.getCgstRate());

                detailsDTO.setCgstAmount(detailsVO.getCgstAmount());

                detailsDTO.setIgstRate(detailsVO.getIgstRate());

                detailsDTO.setIgstAmount(detailsVO.getIgstAmount());

                detailsList.add(detailsDTO);
            }
        }

        responseDTO.setRejectionInvoiceDetailsResponseDTO(detailsList);

        List<RejectionInvoiceTaxDetailsResponseDTO> taxList = new ArrayList<>();

        if (rejectionInvoiceVO.getRejectionInvoiceTaxDetailsVO() != null) {

            for (RejectionInvoiceTaxDetailsVO taxVO : rejectionInvoiceVO.getRejectionInvoiceTaxDetailsVO()) {

                RejectionInvoiceTaxDetailsResponseDTO taxDTO = new RejectionInvoiceTaxDetailsResponseDTO();

                taxDTO.setId(taxVO.getId());

                taxDTO.setParticulars(taxVO.getParticulars());

                taxDTO.setAcceptedQtyAmount(taxVO.getAcceptedQtyAmount());

                taxDTO.setRevisedAmount(taxVO.getRevisedAmount());

                taxList.add(taxDTO);
            }
        }

        responseDTO.setRejectionInvoiceTaxDetailsResponseDTO(taxList);

        return responseDTO;
    }

    @Override
    public String getRejectionInvoiceDocId(Long orgId, String screenCode) {
        String result = rejectionInvoiceRepo.getRejectionInvoiceDocId(orgId, screenCode);
        return result;
    }

    @Override
    public List<Map<String, Object>> getExchangeRate(Long orgId, Long currency) {
        Set<Object[]> chType = rejectionInvoiceRepo.getExchangeRate(orgId, currency);
        return getExchangeRate(chType);
    }

    private List<Map<String, Object>> getExchangeRate(Set<Object[]> chType) {
        List<Map<String, Object>> List1 = new ArrayList<>();
        for (Object[] ch : chType) {
            Map<String, Object> map = new HashMap<>();
            map.put("exchangeRate", ch[0] != null ? new BigDecimal(ch[0].toString()) : BigDecimal.ZERO);
            List1.add(map);
        }
        return List1;
    }

    @Override
    public List<Map<String, Object>> getTaxPercentage(Long orgId, Long hsn) {
        Set<Object[]> chType = rejectionInvoiceRepo.getTaxPercentage(orgId, hsn);
        return getTaxPercentage(chType);
    }

    private List<Map<String, Object>> getTaxPercentage(Set<Object[]> chType) {
        List<Map<String, Object>> List1 = new ArrayList<>();
        for (Object[] ch : chType) {
            Map<String, Object> map = new HashMap<>();
            map.put("taxPercentage", ch[0] != null ? new BigDecimal(ch[0].toString()) : BigDecimal.ZERO);
            List1.add(map);
        }
        return List1;
    }

    @Override
    public List<Map<String, Object>> getItemDetailsBasedDesPatch(Long orgId, Long branch, Long despatch) {
        Set<Object[]> chType = rejectionInvoiceRepo.getItemDetailsBasedDesPatch(orgId, branch, despatch);
        return getItemDetailsBasedDesPatch(chType);
    }

    private List<Map<String, Object>> getItemDetailsBasedDesPatch(Set<Object[]> chType) {

        List<Map<String, Object>> list = new ArrayList<>();

        for (Object[] ch : chType) {

            Map<String, Object> map = new HashMap<>();

            map.put("itemId", ch[0] != null ? ((Number) ch[0]).longValue() : null);
            map.put("itemCode", ch[1] != null ? ch[1].toString() : "");
            map.put("itemDescription", ch[2] != null ? ch[2].toString() : "");
            map.put("unitId", ch[3] != null ? ch[3].toString() : "");
            map.put("hsn", ch[4] != null ? ch[4].toString() : "");
            map.put("customerPartNo", ch[5] != null ? ch[5].toString() : "");
            map.put("descQty", ch[6] != null ? new BigDecimal(ch[6].toString()) : BigDecimal.ZERO);

            list.add(map);
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> getSalesOrderNo(Long customer) {
        Set<Object[]> chType = rejectionInvoiceRepo.getSalesOrderNo(customer);
        return getSalesOrderNo(chType);
    }

    private List<Map<String, Object>> getSalesOrderNo(Set<Object[]> chType) {

        List<Map<String, Object>> list = new ArrayList<>();

        for (Object[] ch : chType) {

            Map<String, Object> map = new HashMap<>();

            map.put("docId", ch[0] != null ? ch[0].toString() : "");
            map.put("docDate", ch[1] != null ? ch[1].toString() : "");
            map.put("id", ch[2] != null ? ch[2].toString() : "");
            list.add(map);
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> getOrderAmount(Long id, Long item) {
        Set<Object[]> chType = rejectionInvoiceRepo.getOrderAmount(id, item);
        return getOrderAmount(chType);
    }

    private List<Map<String, Object>> getOrderAmount(Set<Object[]> chType) {

        List<Map<String, Object>> list = new ArrayList<>();

        for (Object[] ch : chType) {

            Map<String, Object> map = new HashMap<>();

            map.put("rate", ch[1] != null ? new BigDecimal(ch[1].toString()) : BigDecimal.ZERO);

            list.add(map);
        }

        return list;
    }
}