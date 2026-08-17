package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.*;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.*;
import com.efitops.basesetup.entity.*;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.*;

/*
 * =================================================================================================
 * PurchaseServiceImpl - Calculation & GET API Reference
 * =================================================================================================
 *
 * This service handles:
 * 1. Purchase Contract
 * 2. Purchase Delivery Schedule
 * 3. Purchase Bill
 * 4. Purchase Indent
 *
 * -------------------------------------------------------------------------------------------------
 * CALCULATIONS USED
 * -------------------------------------------------------------------------------------------------
 *
 * 1. TAX AMOUNT CALCULATION
 *    Method:
 *        calcAmount(BigDecimal base, BigDecimal percent)
 *
 *    Formula:
 *        Tax Amount = Base Amount × Tax Percentage / 100
 *
 *    Used in:
 *        - Purchase Contract Details
 *          sgstAmount = rateInCurrency × sgstRate / 100
 *          cgstAmount = rateInCurrency × cgstRate / 100
 *          igstAmount = rateInCurrency × igstRate / 100
 *
 *        - Purchase Bill Details
 *          sgstAmount = amount × sgstRate / 100
 *          cgstAmount = amount × cgstRate / 100
 *          igstAmount = amount × igstRate / 100
 *
 * -------------------------------------------------------------------------------------------------
 *
 * 2. PURCHASE BILL EXCHANGE RATE CALCULATION
 *    Method:
 *        buildPurchaseBillDetailsList()
 *
 *    exchangeRate:
 *        If exchange rate is null, BigDecimal.ONE (1) is used.
 *
 *    Formula:
 *        Rate In INR = Rate In Selected Currency × Exchange Rate
 *
 *    Code:
 *        rateInInr = rateInSelectedCurrency × exchangeRate
 *
 * -------------------------------------------------------------------------------------------------
 *
 * 3. PURCHASE BILL SHORTAGE QUANTITY
 *    Method:
 *        buildPurchaseBillDetailsList()
 *
 *    Formula:
 *        Shortage Qty = Challan Qty - Accepted Qty - Rejected Qty
 *
 *    If the result is negative, Shortage Qty is set to 0.
 *
 * -------------------------------------------------------------------------------------------------
 *
 * 4. PURCHASE BILL LANDED COST
 *    Method:
 *        buildPurchaseBillDetailsList()
 *
 *    Formula:
 *        Landed Cost Rate = Rate In INR + Apportioned Cost
 *
 * -------------------------------------------------------------------------------------------------
 *
 * 5. PURCHASE BILL BASIC AMOUNT
 *    Method:
 *        buildPurchaseBillDetailsList()
 *
 *    Formula:
 *        Amount = Accepted Qty × Rate In Selected Currency
 *
 * -------------------------------------------------------------------------------------------------
 *
 * 6. PURCHASE BILL AMOUNT INCLUDING ADDITIONAL DUTY
 *    Method:
 *        buildPurchaseBillDetailsList()
 *
 *    Formula:
 *        Amount In Selected Currency =
 *            Amount + Additional Duty
 *
 * -------------------------------------------------------------------------------------------------
 *
 * 7. PURCHASE BILL AMOUNT IN INR
 *    Method:
 *        buildPurchaseBillDetailsList()
 *
 *    Formula:
 *        Amount In INR =
 *            Amount In Selected Currency × Exchange Rate
 *
 * -------------------------------------------------------------------------------------------------
 *
 * 8. PURCHASE INDENT NUMBER GENERATION
 *    Method:
 *        generateIndentNo()
 *
 *    Formula / Format:
 *        PI + 6-digit sequential count
 *
 *    Example:
 *        PI000001
 *        PI000002
 *        PI000003
 *
 * -------------------------------------------------------------------------------------------------
 * GET OPERATIONS USED INTERNALLY
 * -------------------------------------------------------------------------------------------------
 *
 * The service also fetches related master data while creating/updating records:
 *
 * - branchRepo.findById()
 *       -> Fetch Branch / Plant
 *
 * - customerRepo.findById()
 *       -> Fetch Supplier / Customer
 *
 * - itemMasterRepo.findById()
 *       -> Fetch Item Master
 *
 * - hsnRepo.findById()
 *       -> Fetch HSN Code
 *
 * - taxDefinitionRepo.findById()
 *       -> Fetch Tax Definition
 *
 * - unitMasterRepo.findById()
 *       -> Fetch Unit
 *
 * - listOfValuesDetailsRepo.findById()
 *       -> Fetch LOV values such as Department, Tax Type, Currency,
 *          Dealer Type, Tax Code, Posting Category, ECC Type, etc.
 *
 * - departmentRepo.findById()
 *       -> Fetch Department for Purchase Indent
 *
 * - employeeMasterRepo.findById()
 *       -> Fetch Prepared By / By Whom employee
 *
 * - itemMasterService.getItemMasterById()
 *       -> Fetch complete Item Master details while building
 *          Purchase Indent response
 *
 * -------------------------------------------------------------------------------------------------
 * RESPONSE BUILDING
 * -------------------------------------------------------------------------------------------------
 *
 * -------------------------------------------------------------------------------------------------
 */


@Service
public class PurchaseServiceImpl implements PurchaseService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    private static final String SCREEN_CODE_PC = "PC";
    private static final String SCREEN_CODE_PDS = "PDS";
    private static final String SCREEN_CODE_PB = "PB";
    private static final String INDIA = "INDIA";
    private static final String SCREEN_CODE_SC = "SC";
    private static final String SCREEN_CODE_LPO = "LPO";

    @Autowired LocalPurchaseOrderRepo localPurchaseOrderRepo;
    @Autowired LocalPurchaseOrderDetailsRepo localPurchaseOrderDetailsRepo;
    @Autowired LocalPurchaseOrderTaxDetailsRepo localPurchaseOrderTaxDetailsRepo;
    @Autowired LocalPurchaseOrderAttachmentRepo localPurchaseOrderAttachmentRepo;
    @Autowired PurchaseIndentDetailsRepo purchaseIndentDetailsRepo; // already present above for Purchase Indent

    @Value("${localpurchaseorder.upload.path}")
    private String localPurchaseOrderUploadPath;
    @Autowired PurchaseShortCloseRepo purchaseShortCloseRepo;
    @Autowired PurchaseShortCloseDetailsRepo purchaseShortCloseDetailsRepo;
    // ---------- repos used across Purchase Contract / Delivery Schedule / Bill ----------
    @Autowired PurchaseContractRepo purchaseContractRepo;
    @Autowired PurchaseContractDetailsRepo purchaseContractDetailsRepo;
    @Autowired PurchaseContractTaxDetailsRepo purchaseContractTaxDetailsRepo;
    @Autowired PurchaseContractAttachmentRepo purchaseContractAttachmentRepo;

    @Autowired PurchaseDeliveryScheduleRepo purchaseDeliveryScheduleRepo;
    @Autowired PurchaseDeliveryScheduleDetailsRepo purchaseDeliveryScheduleDetailsRepo;
    @Autowired PurchaseDeliveryScheduleLineRepo purchaseDeliveryScheduleLineRepo;

    @Autowired PurchaseBillRepo purchaseBillRepo;
    @Autowired PurchaseBillDetailsRepo purchaseBillDetailsRepo;
    @Autowired PurchaseBillTaxGridRepo purchaseBillTaxGridRepo;
    
    
    @Autowired
    private UomConversionRepo uomConversionRepo;
    
    

    // ---------- repos used by Purchase Indent (unchanged from your PurchaseIndentServiceImpl) ----------
    @Autowired PurchaseIndentRepo purchaseIndentRepo;

    @Autowired PurchaseIndentAttachmentRepo purchaseIndentAttachmentRepo;
    @Autowired DepartmentRepo departmentRepo;
    @Autowired EmployeeMasterRepo employeeMasterRepo;
    @Autowired ItemMasterService itemMasterService;

    // ---------- shared master repos (same instances every module already used) ----------
    @Autowired BranchRepo branchRepo;
    @Autowired CustomerRepo customerRepo;
    @Autowired ItemMasterRepo itemMasterRepo;
    @Autowired HsnRepo hsnRepo;
    @Autowired TaxDefinitionRepo taxDefinitionRepo;
    @Autowired UnitMasterRepo unitMasterRepo;
    @Autowired ListOfValuesDetailsRepo listOfValuesDetailsRepo;

    @Value("${purchasecontract.upload.path}")
    private String purchaseContractUploadPath;

    @Value("${purchaseindent.upload.path}")
    private String purchaseIndentUploadPath;

    private BigDecimal calcAmount(BigDecimal base, BigDecimal percent) {
        if (base == null || percent == null) return BigDecimal.ZERO;
        return base.multiply(percent).divide(BigDecimal.valueOf(100));
    }

    private ListOfValuesDetailsVO resolveLov(Long id) throws ApplicationException {
        if (id == null || id == 0) return null;
        return listOfValuesDetailsRepo.findById(id)
                .orElseThrow(() -> new ApplicationException("List Of Values entry Not Found: " + id));
    }

    // ==================================================================
    // ============================ PURCHASE CONTRACT ==================
    // (repos/logic identical to your PurchaseContractServiceImpl — bug #1,2,3 fixed)
    // ==================================================================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateCreatePurchaseContract(PurchaseContractDTO dto, MultipartFile[] files)
            throws ApplicationException {

        PurchaseContractVO vo;
        String message;
        boolean isUpdate = ObjectUtils.isNotEmpty(dto.getId());

        if (isUpdate) {
            vo = purchaseContractRepo.findById(dto.getId())
                    .orElseThrow(() -> new ApplicationException("Purchase Contract Not Found"));
            vo.setUpdatedBy(dto.getCreatedBy());
            message = "Purchase Contract Updated Successfully";
        } else {
            vo = new PurchaseContractVO();
            vo.setCreatedBy(dto.getCreatedBy());
            vo.setUpdatedBy(dto.getCreatedBy());
            vo.setContractNo(purchaseContractRepo.getPurchaseContractDocId(dto.getOrgId(), SCREEN_CODE_PC));
            message = "Purchase Contract Created Successfully";
        }

        // FIX #1: was called twice in your pasted code - now called once
        createUpdatePurchaseContractVOFromDTO(dto, vo);

        if (!isUpdate) {
            checkDuplicatePurchaseContract(vo);
        }

        if (isUpdate) {
            purchaseContractRepo.flush();
        } else {
            vo = purchaseContractRepo.save(vo);
        }

        if (isUpdate) {
            deleteExistingAttachments(vo);
        }
        saveContractAttachments(files, vo);

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("purchaseContractVO", buildPurchaseContractResponse(vo));
        return response;
    }

    // FIX: single-arg version (isUpdate param dropped since it's only ever called on !isUpdate)
    private void checkDuplicatePurchaseContract(PurchaseContractVO vo) throws ApplicationException {
        if (vo.getSupplierRefNo() == null || vo.getSupplierRefNo().trim().isEmpty()) return;
        if (purchaseContractRepo.existsBySupplierRefNoAndOrgId(vo.getSupplierRefNo(), vo.getOrgId())) {
            throw new ApplicationException("Duplicate Purchase Contract: Supplier Ref No '" + vo.getSupplierRefNo() + "' already exists");
        }
    }

    private void createUpdatePurchaseContractVOFromDTO(PurchaseContractDTO dto, PurchaseContractVO vo) throws ApplicationException {

        if (dto.getBranch() != null && dto.getBranch() != 0) {
            vo.setPlant(branchRepo.findById(dto.getBranch()).orElseThrow(() -> new ApplicationException("Branch Not Found")));
        }
        vo.setContractDate(dto.getContractDate());

        if (dto.getDepartment() != null && dto.getDepartment() != 0) {
            vo.setDepartment(resolveLov(dto.getDepartment()));
        }

        if (dto.getSupplier() != null && dto.getSupplier() != 0) {
            CustomerVO supplier = customerRepo.findById(dto.getSupplier())
                    .orElseThrow(() -> new ApplicationException("Supplier Not Found"));
            vo.setSupplier(supplier);

            GSTStateMasterVO gstState = supplier.getGstState();
            vo.setGstState(gstState);

            boolean isIndia = gstState != null && gstState.getStateName() != null
                    && gstState.getStateName().toUpperCase().contains(INDIA);
            if (supplier.getCountry() != null && supplier.getCountry().getCountryName() != null) {
                isIndia = supplier.getCountry().getCountryName().toUpperCase().contains(INDIA);
            }
            vo.setIsIgstAppl(!isIndia);
            vo.setPoType(isIndia ? "LOCAL" : "IMPORT");
        }

        vo.setSupplierRefNo(dto.getSupplierRefNo());
        vo.setRefDate(dto.getRefDate());
        vo.setValidFrom(dto.getValidFrom());
        vo.setValidTo(dto.getValidTo());

        vo.setModeOfDespatch(dto.getModeOfDespatch());
        vo.setPaymentTerms(dto.getPaymentTerms());
        vo.setDelivery(dto.getDelivery());
        vo.setFreightType(dto.getFreightType());
        vo.setPackingType(dto.getPackingType());
        vo.setInsuranceAmount(dto.getInsuranceAmount());
        vo.setBank(dto.getBank());
        vo.setAccounts(dto.getAccounts());
        vo.setSwiftCode(dto.getSwiftCode());
        vo.setCheckedBy(dto.getCheckedBy());
        vo.setPreparedBy(dto.getPreparedBy());
        vo.setAuthorisedBy(dto.getAuthorisedBy());
        vo.setFreightForwarder(dto.getFreightForwarder());
        vo.setNotes(dto.getNotes());
        vo.setTermsConditions(dto.getTermsConditions());

        vo.setOrgId(dto.getOrgId());
        vo.setFinancialYear(dto.getFinancialYear());
        vo.setActive(dto.isActive());
        vo.setCancelRemarks(dto.getCancelRemarks());

        buildPurchaseContractDetailsList(dto, vo);
        buildPurchaseContractTaxDetailsList(dto, vo);
    }

    private void buildPurchaseContractDetailsList(PurchaseContractDTO dto, PurchaseContractVO vo) throws ApplicationException {

        if (vo.getId() != null && vo.getPurchaseContractDetailsVO() != null && !vo.getPurchaseContractDetailsVO().isEmpty()) {
            purchaseContractDetailsRepo.deleteAll(new ArrayList<>(vo.getPurchaseContractDetailsVO()));
            purchaseContractDetailsRepo.flush();
        }
        vo.getPurchaseContractDetailsVO().clear();
        if (dto.getDetails() == null) return;

        for (PurchaseContractDetailsDTO line : dto.getDetails()) {

            PurchaseContractDetailsVO detailVO = new PurchaseContractDetailsVO();

            if (line.getItemId() != null && line.getItemId() != 0) {
                ItemMasterVO item = itemMasterRepo.findById(line.getItemId()).orElseThrow(() -> new ApplicationException("Item Not Found"));
                detailVO.setItem(item);

                if (line.getHsnId() != null && line.getHsnId() != 0) {
                    detailVO.setHsnCode(hsnRepo.findById(line.getHsnId()).orElseThrow(() -> new ApplicationException("HSN Not Found")));
                } else if (item.getHsnCode() != null) {
                    detailVO.setHsnCode(item.getHsnCode());
                }

                if (line.getUnitId() != null && line.getUnitId() != 0) {
                    detailVO.setUnit(unitMasterRepo.findById(line.getUnitId()).orElseThrow(() -> new ApplicationException("Unit Not Found")));
                } else if (item.getPrimaryUnit() != null) {
                    detailVO.setUnit(item.getPrimaryUnit());
                }
            }

            if (line.getTaxType() != null && line.getTaxType() != 0) detailVO.setTaxType(resolveLov(line.getTaxType()));
            if (line.getTaxDefinition() != null && line.getTaxDefinition() != 0) {
                detailVO.setTaxDefinition(taxDefinitionRepo.findById(line.getTaxDefinition())
                        .orElseThrow(() -> new ApplicationException("Tax Definition Not Found")));
            }
            detailVO.setTaxPercent(line.getTaxPercent());
            detailVO.setRateInCurrency(line.getRateInCurrency());

            detailVO.setSgstRate(line.getSgstRate());
            detailVO.setSgstAmount(calcAmount(line.getRateInCurrency(), line.getSgstRate()));
            detailVO.setCgstRate(line.getCgstRate());
            detailVO.setCgstAmount(calcAmount(line.getRateInCurrency(), line.getCgstRate()));
            detailVO.setIgstRate(line.getIgstRate());
            detailVO.setIgstAmount(calcAmount(line.getRateInCurrency(), line.getIgstRate()));

            detailVO.setValidFrom(line.getValidFrom() != null ? line.getValidFrom() : dto.getValidFrom());
            detailVO.setValidTo(line.getValidTo() != null ? line.getValidTo() : dto.getValidTo());

            detailVO.setPurchaseContractVO(vo);
            vo.getPurchaseContractDetailsVO().add(detailVO);
        }
    }

    private void buildPurchaseContractTaxDetailsList(PurchaseContractDTO dto, PurchaseContractVO vo) {

        if (vo.getId() != null && vo.getPurchaseContractTaxDetailsVO() != null && !vo.getPurchaseContractTaxDetailsVO().isEmpty()) {
            purchaseContractTaxDetailsRepo.deleteAll(new ArrayList<>(vo.getPurchaseContractTaxDetailsVO()));
            purchaseContractTaxDetailsRepo.flush();
        }
        vo.getPurchaseContractTaxDetailsVO().clear();
        if (dto.getTaxDetails() == null) return;

        for (PurchaseContractTaxDetailsDTO taxDTO : dto.getTaxDetails()) {
            PurchaseContractTaxDetailsVO taxVO = new PurchaseContractTaxDetailsVO();
            taxVO.setParticulars(taxDTO.getParticulars());
            taxVO.setTaxPercent(taxDTO.getTaxPercent());
            taxVO.setAmount(taxDTO.getAmount());
            taxVO.setPurchaseContractVO(vo);
            vo.getPurchaseContractTaxDetailsVO().add(taxVO);
        }
    }

    private void deleteExistingAttachments(PurchaseContractVO vo) {
        if (vo.getPurchaseContractAttachmentVO() == null || vo.getPurchaseContractAttachmentVO().isEmpty()) return;

        List<PurchaseContractAttachmentVO> existing = new ArrayList<>(vo.getPurchaseContractAttachmentVO());
        for (PurchaseContractAttachmentVO a : existing) {
            try {
                if (a.getFilePath() != null) Files.deleteIfExists(Paths.get(a.getFilePath()));
            } catch (IOException e) {
                LOGGER.error("Failed to delete attachment file from disk: {} - {}", a.getFilePath(), e.getMessage());
            }
        }
        purchaseContractAttachmentRepo.deleteAll(existing);
        purchaseContractAttachmentRepo.flush();
        vo.getPurchaseContractAttachmentVO().clear();
    }

    private void saveContractAttachments(MultipartFile[] files, PurchaseContractVO vo) throws ApplicationException {
        if (files == null || files.length == 0) return;

        try {
            Path folder = Paths.get(purchaseContractUploadPath);
            if (!Files.exists(folder)) Files.createDirectories(folder);

            List<PurchaseContractAttachmentVO> attachmentList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file == null || file.isEmpty()) continue;

                String originalFileName = file.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
                Path path = Paths.get(purchaseContractUploadPath, uniqueFileName);

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                }

                PurchaseContractAttachmentVO attachment = new PurchaseContractAttachmentVO();
                attachment.setPurchaseContractVO(vo);
                attachment.setName(originalFileName);
                attachment.setFileName(uniqueFileName);
                attachment.setFilePath(path.toString());
                attachment.setFileSize(file.getSize());
                attachment.setUploadOn(LocalDateTime.now());
                attachmentList.add(attachment);
            }
            vo.getPurchaseContractAttachmentVO().addAll(attachmentList);
        } catch (IOException e) {
            throw new ApplicationException("File Upload Failed : " + e.getMessage());
        }
    }

    @Override
    public PurchaseContractResponseDTO getPurchaseContractById(Long id) throws ApplicationException {
        PurchaseContractVO vo = purchaseContractRepo.getPurchaseContractById(id);
        if (vo == null) throw new ApplicationException("Purchase Contract Not Found");
        return buildPurchaseContractResponse(vo);
    }

    @Override
    public List<PurchaseContractResponseDTO> getPurchaseContractByOrgId(Long orgId, Long branchId) throws ApplicationException {
        List<PurchaseContractVO> list = purchaseContractRepo.getPurchaseContractByOrgId(orgId, branchId);
        if (list == null || list.isEmpty()) throw new ApplicationException("Purchase Contract Not Found");
        List<PurchaseContractResponseDTO> responseList = new ArrayList<>();
        for (PurchaseContractVO vo : list) responseList.add(buildPurchaseContractResponse(vo));
        return responseList;
    }

    @Override
    public String getPurchaseContractDocId(Long orgId, String finYear, Long branch) {
        return purchaseContractRepo.getPurchaseContractDocId(orgId, SCREEN_CODE_PC);
    }

    private PurchaseContractResponseDTO buildPurchaseContractResponse(PurchaseContractVO vo) {

        PurchaseContractResponseDTO dto = new PurchaseContractResponseDTO();
        dto.setId(vo.getId());

        if (vo.getPlant() != null) {
            BranchResponseDTO plantDTO = new BranchResponseDTO();
            plantDTO.setId(vo.getPlant().getId());
            plantDTO.setBranchCode(vo.getPlant().getBranchCode());
            plantDTO.setBranchName(vo.getPlant().getBranchName());
            dto.setPlant(plantDTO);
        }

        dto.setContractNo(vo.getContractNo());
        dto.setContractDate(vo.getContractDate());

        if (vo.getDepartment() != null) {
            dto.setDepartment(new ListOfVlauesDetailsResponseDTO(vo.getDepartment().getId(),
                    vo.getDepartment().getValueCode(), vo.getDepartment().getValueDescription()));
        }

        if (vo.getSupplier() != null) {
            CustomerResponseDetailsDTO supplierDTO = new CustomerResponseDetailsDTO();
            supplierDTO.setId(vo.getSupplier().getId());
            supplierDTO.setCustomerName(vo.getSupplier().getCustomerName());
            dto.setSupplier(supplierDTO);
        }

        dto.setSupplierRefNo(vo.getSupplierRefNo());
        dto.setRefDate(vo.getRefDate());

        if (vo.getGstState() != null) {
            dto.setGstState(new GSTStateResponseDTO(vo.getGstState().getId(), vo.getGstState().getStateCode(),
                    vo.getGstState().getStateName(), vo.getGstState().getGstStateId()));
        }

        dto.setValidFrom(vo.getValidFrom());
        dto.setValidTo(vo.getValidTo());
        dto.setIsIgstAppl(vo.getIsIgstAppl());
        dto.setPoType(vo.getPoType());  // FIX #2: was missing in your pasted code

        dto.setModeOfDespatch(vo.getModeOfDespatch());
        dto.setPaymentTerms(vo.getPaymentTerms());
        dto.setDelivery(vo.getDelivery());
        dto.setFreightType(vo.getFreightType());
        dto.setPackingType(vo.getPackingType());
        dto.setInsuranceAmount(vo.getInsuranceAmount());
        dto.setBank(vo.getBank());
        dto.setAccounts(vo.getAccounts());
        dto.setSwiftCode(vo.getSwiftCode());
        dto.setCheckedBy(vo.getCheckedBy());
        dto.setPreparedBy(vo.getPreparedBy());
        dto.setAuthorisedBy(vo.getAuthorisedBy());
        dto.setFreightForwarder(vo.getFreightForwarder());
        dto.setNotes(vo.getNotes());
        dto.setTermsConditions(vo.getTermsConditions());

        dto.setOrgId(vo.getOrgId());
        dto.setFinancialYear(vo.getFinancialYear());
        dto.setActive(vo.getActive());
        dto.setCancelRemarks(vo.getCancelRemarks());
        dto.setCreatedBy(vo.getCreatedBy());
        dto.setUpdatedBy(vo.getUpdatedBy());

        List<PurchaseContractDetailsResponseDTO> detailsList = new ArrayList<>();
        if (vo.getPurchaseContractDetailsVO() != null) {
            for (PurchaseContractDetailsVO d : vo.getPurchaseContractDetailsVO()) {

                PurchaseContractDetailsResponseDTO line = new PurchaseContractDetailsResponseDTO();
                line.setId(d.getId());

                if (d.getItem() != null) {
                    ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();
                    itemDTO.setId(d.getItem().getId());
                    itemDTO.setItemCode(d.getItem().getItemCode());
                    itemDTO.setItemDescription(d.getItem().getItemDescription());
                    line.setItemCode(itemDTO);
                }
                if (d.getHsnCode() != null) {
                    HsnResponseImageDTO hsnDTO = new HsnResponseImageDTO();
                    hsnDTO.setId(d.getHsnCode().getId());
                    hsnDTO.setHsnCode(d.getHsnCode().getHsn());
                    line.setHsnCode(hsnDTO);
                }
                if (d.getTaxType() != null) {
                    line.setTaxType(new ListOfVlauesDetailsResponseDTO(d.getTaxType().getId(),
                            d.getTaxType().getValueCode(), d.getTaxType().getValueDescription()));
                }
                if (d.getTaxDefinition() != null) line.setTaxName(d.getTaxDefinition().getTaxDescription());
                line.setTaxPercent(d.getTaxPercent());

                if (d.getUnit() != null) {
                    PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();
                    unitDTO.setId(d.getUnit().getId());
                    unitDTO.setPrimaryUnit(d.getUnit().getUnitId());
                    line.setUnit(unitDTO);
                }

                line.setRateInCurrency(d.getRateInCurrency());
                line.setSgstRate(d.getSgstRate());
                line.setSgstAmount(d.getSgstAmount());
                line.setCgstRate(d.getCgstRate());
                line.setCgstAmount(d.getCgstAmount());
                line.setIgstRate(d.getIgstRate());
                line.setIgstAmount(d.getIgstAmount());
                line.setValidFrom(d.getValidFrom());
                line.setValidTo(d.getValidTo());

                detailsList.add(line);
            }
        }
        dto.setDetails(detailsList);

        List<PurchaseContractTaxDetailsResponseDTO> taxList = new ArrayList<>();
        if (vo.getPurchaseContractTaxDetailsVO() != null) {
            for (PurchaseContractTaxDetailsVO t : vo.getPurchaseContractTaxDetailsVO()) {
                PurchaseContractTaxDetailsResponseDTO taxDTO = new PurchaseContractTaxDetailsResponseDTO();
                taxDTO.setId(t.getId());
                taxDTO.setParticulars(t.getParticulars());
                taxDTO.setTaxPercent(t.getTaxPercent());
                taxDTO.setAmount(t.getAmount());
                taxList.add(taxDTO);
            }
        }
        dto.setTaxDetails(taxList);

        List<PurchaseContractAttachmentDTO> attachmentList = new ArrayList<>();
        if (vo.getPurchaseContractAttachmentVO() != null) {
            for (PurchaseContractAttachmentVO a : vo.getPurchaseContractAttachmentVO()) {
                PurchaseContractAttachmentDTO attachDTO = new PurchaseContractAttachmentDTO();

                attachDTO.setName(a.getName());
                attachDTO.setFileName(a.getFileName());
                attachDTO.setFilePath(a.getFilePath());
                attachDTO.setFileSize(a.getFileSize());
                attachDTO.setUploadOn(a.getUploadOn());
                attachmentList.add(attachDTO);
            }
        }
        dto.setAttachments(attachmentList);

        return dto;
    }

    // ==================================================================
    // ===================== PURCHASE DELIVERY SCHEDULE ================
    // FIX #4: converted from poType/poId + resolvePoSelection() to a direct
    // purchaseContractId field, per the earlier decision to drop the PO-type flag.
    // ==================================================================

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Map<String, Object> updateCreatePurchaseDeliverySchedule(PurchaseDeliveryScheduleDTO dto) throws ApplicationException {
//
//        PurchaseDeliveryScheduleVO vo;
//        String message;
//        boolean isUpdate = ObjectUtils.isNotEmpty(dto.getId());
//
//        if (isUpdate) {
//            vo = purchaseDeliveryScheduleRepo.findById(dto.getId())
//                    .orElseThrow(() -> new ApplicationException("Purchase Delivery Schedule Not Found"));
//            vo.setUpdatedBy(dto.getCreatedBy());
//            message = "Purchase Delivery Schedule Updated Successfully";
//        } else {
//            vo = new PurchaseDeliveryScheduleVO();
//            vo.setCreatedBy(dto.getCreatedBy());
//            vo.setUpdatedBy(dto.getCreatedBy());
//            vo.setDocNo(purchaseDeliveryScheduleRepo.getPurchaseDeliveryScheduleDocId(dto.getOrgId(), SCREEN_CODE_PDS));
//            message = "Purchase Delivery Schedule Created Successfully";
//        }
//
//        createUpdatePurchaseDeliveryScheduleVOFromDTO(dto, vo);
//
//        if (isUpdate) {
//            purchaseDeliveryScheduleRepo.flush();
//        } else {
//            vo = purchaseDeliveryScheduleRepo.save(vo);
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", message);
//        response.put("purchaseDeliveryScheduleVO", buildPurchaseDeliveryScheduleResponse(vo));
//        return response;
//    }
//
//    private void createUpdatePurchaseDeliveryScheduleVOFromDTO(PurchaseDeliveryScheduleDTO dto, PurchaseDeliveryScheduleVO vo) throws ApplicationException {
//
//        if (dto.getBranch() != null && dto.getBranch() != 0) {
//            vo.setPlant(branchRepo.findById(dto.getBranch()).orElseThrow(() -> new ApplicationException("Branch Not Found")));
//        }
//        vo.setBelongsTo(dto.getBelongsTo());
//        vo.setDocDate(dto.getDocDate());
//        vo.setSchStartDate(dto.getSchStartDate());
//        vo.setSchEndDate(dto.getSchEndDate());
//
//        // PO No. -> resolved from whichever source table poType points to.
//        // Snapshotted here so this record doesn't silently change if the source PO is edited later.
//        if (dto.getPoType() != null && dto.getPoId() != null) {
//
//            switch (dto.getPoType()) {
//
//                case "LOCAL_PURCHASE_ORDER":
//                    LocalPurchaseOrderVO lpo = localPurchaseOrderRepo.findById(dto.getPoId())
//                            .orElseThrow(() -> new ApplicationException("Local Purchase Order Not Found"));
//                    vo.setPoType(dto.getPoType());
//                    vo.setPoId(lpo.getId());
//                    vo.setPoNo(lpo.getPoNo());
//                    vo.setPoDate(lpo.getPoDate());
//                    break;
//
//                case "PURCHASE_CONTRACT":
//                    PurchaseContractVO contract = purchaseContractRepo.findById(dto.getPoId())
//                            .orElseThrow(() -> new ApplicationException("Purchase Contract Not Found"));
//                    vo.setPoType(dto.getPoType());
//                    vo.setPoId(contract.getId());
//                    vo.setPoNo(contract.getContractNo());
//                    vo.setPoDate(contract.getContractDate());
//                    break;
//
//                default:
//                    throw new ApplicationException("Unsupported PO Type: " + dto.getPoType());
//            }
//
//        } else {
//            vo.setPoType(null);
//            vo.setPoId(null);
//            vo.setPoNo(null);
//            vo.setPoDate(null);
//        }
//
//        vo.setPreparedBy(dto.getPreparedBy());
//        vo.setNote(dto.getNote());
//        vo.setOrgId(dto.getOrgId());
//        vo.setFinancialYear(dto.getFinancialYear());
//        vo.setActive(dto.isActive());
//        vo.setCancelRemarks(dto.getCancelRemarks());
//
//        buildScheduleDetailsList(dto, vo);
//        buildScheduleList(dto, vo);
//    }
//
//    private void buildScheduleDetailsList(PurchaseDeliveryScheduleDTO dto, PurchaseDeliveryScheduleVO vo) throws ApplicationException {
//
//        if (vo.getId() != null && vo.getPurchaseDeliveryScheduleDetailsVO() != null && !vo.getPurchaseDeliveryScheduleDetailsVO().isEmpty()) {
//            purchaseDeliveryScheduleDetailsRepo.deleteAll(new ArrayList<>(vo.getPurchaseDeliveryScheduleDetailsVO()));
//            purchaseDeliveryScheduleDetailsRepo.flush();
//        }
//        vo.getPurchaseDeliveryScheduleDetailsVO().clear();
//        if (dto.getScheduleDetails() == null) return;
//
//        for (PurchaseDeliveryScheduleDetailsDTO line : dto.getScheduleDetails()) {
//
//            PurchaseDeliveryScheduleDetailsVO detailVO = new PurchaseDeliveryScheduleDetailsVO();
//
//            if (line.getItemId() != null && line.getItemId() != 0) {
//                ItemMasterVO item = itemMasterRepo.findById(line.getItemId()).orElseThrow(() -> new ApplicationException("Item Not Found"));
//                detailVO.setItem(item);
//                if (item.getPrimaryUnit() != null) detailVO.setPrimaryUnit(item.getPrimaryUnit());
//                // purchaseUnit/demandQty/availableStock/qty still pending confirmed ItemMasterVO getter names
//            }
//
//            detailVO.setTentativeQty(line.getTentativeQty());
//            detailVO.setTentativeQtyNextMonth(line.getTentativeQtyNextMonth());
//            detailVO.setRate(line.getRate());
//            detailVO.setPurchaseDeliveryScheduleVO(vo);
//            vo.getPurchaseDeliveryScheduleDetailsVO().add(detailVO);
//        }
//    }
//
//    private void buildScheduleList(PurchaseDeliveryScheduleDTO dto, PurchaseDeliveryScheduleVO vo) {
//
//        if (vo.getId() != null && vo.getPurchaseDeliveryScheduleLineVO() != null && !vo.getPurchaseDeliveryScheduleLineVO().isEmpty()) {
//            purchaseDeliveryScheduleLineRepo.deleteAll(new ArrayList<>(vo.getPurchaseDeliveryScheduleLineVO()));
//            purchaseDeliveryScheduleLineRepo.flush();
//        }
//        vo.getPurchaseDeliveryScheduleLineVO().clear();
//        if (dto.getSchedule() == null) return;
//
//        for (PurchaseDeliveryScheduleLineDTO line : dto.getSchedule()) {
//            PurchaseDeliveryScheduleLineVO lineVO = new PurchaseDeliveryScheduleLineVO();
//            lineVO.setPlanDate(line.getPlanDate());
//            lineVO.setWeekNo(line.getWeekNo());
//            lineVO.setScheduleQty(line.getScheduleQty());
//            lineVO.setPurchaseDeliveryScheduleVO(vo);
//            vo.getPurchaseDeliveryScheduleLineVO().add(lineVO);
//        }
//    }
//
//    @Override
//    public PurchaseDeliveryScheduleResponseDTO getPurchaseDeliveryScheduleById(Long id) throws ApplicationException {
//        PurchaseDeliveryScheduleVO vo = purchaseDeliveryScheduleRepo.getPurchaseDeliveryScheduleById(id);
//        if (vo == null) throw new ApplicationException("Purchase Delivery Schedule Not Found");
//        return buildPurchaseDeliveryScheduleResponse(vo);
//    }
//
//    @Override
//    public List<PurchaseDeliveryScheduleResponseDTO> getPurchaseDeliveryScheduleByOrgId(Long orgId, Long branchId) throws ApplicationException {
//        List<PurchaseDeliveryScheduleVO> list = purchaseDeliveryScheduleRepo.getPurchaseDeliveryScheduleByOrgId(orgId, branchId);
//        if (list == null || list.isEmpty()) throw new ApplicationException("Purchase Delivery Schedule Not Found");
//        List<PurchaseDeliveryScheduleResponseDTO> responseList = new ArrayList<>();
//        for (PurchaseDeliveryScheduleVO vo : list) responseList.add(buildPurchaseDeliveryScheduleResponse(vo));
//        return responseList;
//    }
//
//    @Override
//    public String getPurchaseDeliveryScheduleDocId(Long orgId, String finYear, Long branch) {
//        return purchaseDeliveryScheduleRepo.getPurchaseDeliveryScheduleDocId(orgId, SCREEN_CODE_PDS);
//    }
//
//    private PurchaseDeliveryScheduleResponseDTO buildPurchaseDeliveryScheduleResponse(PurchaseDeliveryScheduleVO vo) {
//
//        PurchaseDeliveryScheduleResponseDTO dto = new PurchaseDeliveryScheduleResponseDTO();
//        dto.setId(vo.getId());
//
//        if (vo.getPlant() != null) {
//            BranchResponseDTO plantDTO = new BranchResponseDTO();
//            plantDTO.setId(vo.getPlant().getId());
//            plantDTO.setBranchCode(vo.getPlant().getBranchCode());
//            plantDTO.setBranchName(vo.getPlant().getBranchName());
//            dto.setPlant(plantDTO);
//        }
//
//        dto.setBelongsTo(vo.getBelongsTo());
//        dto.setDocNo(vo.getDocNo());
//        dto.setDocDate(vo.getDocDate());
//        dto.setSchStartDate(vo.getSchStartDate());
//        dto.setSchEndDate(vo.getSchEndDate());
//
//        if (vo.getSupplier() != null) {
//            CustomerResponseDetailsDTO supplierDTO = new CustomerResponseDetailsDTO();
//            supplierDTO.setId(vo.getSupplier().getId());
//            supplierDTO.setCustomerName(vo.getSupplier().getCustomerName());
//            dto.setSupplier(supplierDTO);
//        }
//
//        dto.setLocalPurchaseOrderId(vo.getLocalPurchaseOrder() != null ? vo.getLocalPurchaseOrder().getId() : null);
//        dto.setPoNo(vo.getPoNo());
//        dto.setPoDate(vo.getPoDate());
//
//        dto.setPreparedBy(vo.getPreparedBy());
//        dto.setNote(vo.getNote());
//        dto.setOrgId(vo.getOrgId());
//        dto.setFinancialYear(vo.getFinancialYear());
//        dto.setActive(vo.getActive());
//        dto.setCancelRemarks(vo.getCancelRemarks());
//        dto.setCreatedBy(vo.getCreatedBy());
//        dto.setUpdatedBy(vo.getUpdatedBy());
//
//        List<PurchaseDeliveryScheduleDetailsResponseDTO> detailsList = new ArrayList<>();
//        if (vo.getPurchaseDeliveryScheduleDetailsVO() != null) {
//            for (PurchaseDeliveryScheduleDetailsVO d : vo.getPurchaseDeliveryScheduleDetailsVO()) {
//
//                PurchaseDeliveryScheduleDetailsResponseDTO line = new PurchaseDeliveryScheduleDetailsResponseDTO();
//                line.setId(d.getId());
//
//                if (d.getItem() != null) {
//                    ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();
//                    itemDTO.setId(d.getItem().getId());
//                    itemDTO.setItemCode(d.getItem().getItemCode());
//                    itemDTO.setItemDescription(d.getItem().getItemDescription());
//                    line.setItemCode(itemDTO);
//                }
//                if (d.getPrimaryUnit() != null) {
//                    PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();
//                    unitDTO.setId(d.getPrimaryUnit().getId());
//                    unitDTO.setPrimaryUnit(d.getPrimaryUnit().getUnitId());
//                    line.setPrimaryUnit(unitDTO);
//                }
//                if (d.getPurchaseUnit() != null) {
//                    PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();
//                    unitDTO.setId(d.getPurchaseUnit().getId());
//                    unitDTO.setPrimaryUnit(d.getPurchaseUnit().getUnitId());
//                    line.setPurchaseUnit(unitDTO);
//                }
//                line.setDemandQty(d.getDemandQty());
//                line.setAvailableStock(d.getAvailableStock());
//                line.setQty(d.getQty());
//                line.setTentativeQty(d.getTentativeQty());
//                line.setTentativeQtyNextMonth(d.getTentativeQtyNextMonth());
//                line.setRate(d.getRate());
//
//                detailsList.add(line);
//            }
//        }
//        dto.setScheduleDetails(detailsList);
//
//        List<PurchaseDeliveryScheduleLineResponseDTO> scheduleList = new ArrayList<>();
//        if (vo.getPurchaseDeliveryScheduleLineVO() != null) {
//            for (PurchaseDeliveryScheduleLineVO l : vo.getPurchaseDeliveryScheduleLineVO()) {
//                PurchaseDeliveryScheduleLineResponseDTO line = new PurchaseDeliveryScheduleLineResponseDTO();
//                line.setId(l.getId());
//                line.setPlanDate(l.getPlanDate());
//                line.setWeekNo(l.getWeekNo());
//                line.setScheduleQty(l.getScheduleQty());
//                scheduleList.add(line);
//            }
//        }
//        dto.setSchedule(scheduleList);
//
//        return dto;
//    }

    // ==================================================================
    // ============================ PURCHASE BILL =======================
    // FIX #4 (same as PDS): poType/poId -> purchaseContractId direct FK
    // ==================================================================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateCreatePurchaseBill(PurchaseBillDTO dto) throws ApplicationException {

        PurchaseBillVO vo;
        String message;
        boolean isUpdate = ObjectUtils.isNotEmpty(dto.getId());

        if (isUpdate) {
            vo = purchaseBillRepo.findById(dto.getId()).orElseThrow(() -> new ApplicationException("Purchase Bill Not Found"));
            vo.setUpdatedBy(dto.getCreatedBy());
            message = "Purchase Bill Updated Successfully";
        } else {
            vo = new PurchaseBillVO();
            vo.setCreatedBy(dto.getCreatedBy());
            vo.setUpdatedBy(dto.getCreatedBy());
            vo.setPbNo(purchaseBillRepo.getPurchaseBillDocId(dto.getOrgId(), SCREEN_CODE_PB));
            message = "Purchase Bill Created Successfully";
        }

        createUpdatePurchaseBillVOFromDTO(dto, vo);

        if (isUpdate) {
            purchaseBillRepo.flush();
        } else {
            vo = purchaseBillRepo.save(vo);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("purchaseBillVO", buildPurchaseBillResponse(vo));
        return response;
    }

    private void createUpdatePurchaseBillVOFromDTO(PurchaseBillDTO dto, PurchaseBillVO vo) throws ApplicationException {

        if (dto.getBranch() != null && dto.getBranch() != 0) {
            vo.setPlant(branchRepo.findById(dto.getBranch()).orElseThrow(() -> new ApplicationException("Branch Not Found")));
        }
        vo.setBelongsTo(dto.getBelongsTo());
        vo.setPbDate(dto.getPbDate());

        if (dto.getSupplier() != null && dto.getSupplier() != 0) {
            CustomerVO supplier = customerRepo.findById(dto.getSupplier()).orElseThrow(() -> new ApplicationException("Supplier Not Found"));
            vo.setSupplier(supplier);

            GSTStateMasterVO gstState = supplier.getGstState();
            vo.setGstState(gstState);

            boolean isIndia = gstState != null && gstState.getStateName() != null
                    && gstState.getStateName().toUpperCase().contains(INDIA);
            if (supplier.getCountry() != null && supplier.getCountry().getCountryName() != null) {
                isIndia = supplier.getCountry().getCountryName().toUpperCase().contains(INDIA);
            }
            vo.setIsIgstAppl(!isIndia);
            // vo.setGstnNo(supplier.getGstnNo());  // still pending confirmed getter name on CustomerVO
        }

        vo.setGrnNo(dto.getGrnNo());
        vo.setGrnDate(dto.getGrnDate());
        vo.setExcisable(dto.getExcisable());
        vo.setCurrency(resolveLov(dto.getCurrency()));
        vo.setVendorDcNo(dto.getVendorDcNo());
        vo.setExchangeRate(dto.getExchangeRate());
        vo.setDealerType(resolveLov(dto.getDealerType()));
        vo.setTaxCode(resolveLov(dto.getTaxCode()));

        if (dto.getLocalPurchaseOrderId() != null) {
            LocalPurchaseOrderVO lpo = localPurchaseOrderRepo.findById(dto.getLocalPurchaseOrderId())
                    .orElseThrow(() -> new ApplicationException("Local Purchase Order Not Found"));
            vo.setLocalPurchaseOrder(lpo);
            vo.setPoNo(lpo.getPoNo());
            vo.setPoDate(lpo.getPoDate());
        } else {
            vo.setLocalPurchaseOrder(null);
            vo.setPoNo(null);
            vo.setPoDate(null);
        }

        vo.setIsReverseChrg(dto.getIsReverseChrg());
        vo.setVoucherPostingDate(dto.getVoucherPostingDate());
        vo.setDate(dto.getDate());
        vo.setDutyPerUnit(dto.getDutyPerUnit());
        vo.setPostingCategory(resolveLov(dto.getPostingCategory()));
        vo.setModvatCopyReceived(dto.getModvatCopyReceived());
        vo.setEccType(resolveLov(dto.getEccType()));
        vo.setSupplierDcInvNo(dto.getSupplierDcInvNo());
        vo.setSupplierDcInvDate(dto.getSupplierDcInvDate());

        vo.setTotalFreight(dto.getTotalFreight());
        vo.setTotalQty(dto.getTotalQty());
        vo.setBasicValue(dto.getBasicValue());
        vo.setTotalAmount(dto.getTotalAmount());
        vo.setAmountInWords(dto.getAmountInWords());
        vo.setEntryTaxApplicable(dto.getEntryTaxApplicable());
        vo.setNarration(dto.getNarration());
        vo.setPaymentTerms(dto.getPaymentTerms());

        vo.setOrgId(dto.getOrgId());
        vo.setFinancialYear(dto.getFinancialYear());
        vo.setActive(dto.isActive());
        vo.setCancelRemarks(dto.getCancelRemarks());

        buildPurchaseBillDetailsList(dto, vo);
        buildPurchaseBillTaxGridList(dto, vo);
    }

    // "1-Purchase Detail" grid — with calculations
    private void buildPurchaseBillDetailsList(PurchaseBillDTO dto, PurchaseBillVO vo) throws ApplicationException {

        if (vo.getId() != null && vo.getPurchaseBillDetailsVO() != null && !vo.getPurchaseBillDetailsVO().isEmpty()) {
            purchaseBillDetailsRepo.deleteAll(new ArrayList<>(vo.getPurchaseBillDetailsVO()));
            purchaseBillDetailsRepo.flush();
        }
        vo.getPurchaseBillDetailsVO().clear();
        if (dto.getPurchaseDetails() == null) return;

        BigDecimal exchangeRate = dto.getExchangeRate() != null ? dto.getExchangeRate() : BigDecimal.ONE;

        for (PurchaseBillDetailsDTO line : dto.getPurchaseDetails()) {

            PurchaseBillDetailsVO detailVO = new PurchaseBillDetailsVO();

            if (line.getItemId() != null && line.getItemId() != 0) {
                ItemMasterVO item = itemMasterRepo.findById(line.getItemId()).orElseThrow(() -> new ApplicationException("Item Not Found"));
                detailVO.setItem(item);
                if (item.getHsnCode() != null) detailVO.setHsnCode(item.getHsnCode());
                if (item.getPrimaryUnit() != null) detailVO.setUnit(item.getPrimaryUnit());
            }

            if (line.getTaxType() != null && line.getTaxType() != 0) detailVO.setTaxType(resolveLov(line.getTaxType()));
            detailVO.setTaxPercent(line.getTaxPercent());
            detailVO.setTariffNo(line.getTariffNo());
            detailVO.setExciseToPost(line.getExciseToPost());
            detailVO.setChallanQty(line.getChallanQty());

            if (line.getUnitId() != null && line.getUnitId() != 0) {
                detailVO.setUnit(unitMasterRepo.findById(line.getUnitId()).orElseThrow(() -> new ApplicationException("Unit Not Found")));
            }

            detailVO.setGrnReceivedQty(line.getGrnReceivedQty());
            detailVO.setAcceptedQty(line.getAcceptedQty());
            detailVO.setRejectedQty(line.getRejectedQty());

            BigDecimal challanQty = line.getChallanQty() != null ? line.getChallanQty() : BigDecimal.ZERO;
            BigDecimal acceptedQty = line.getAcceptedQty() != null ? line.getAcceptedQty() : BigDecimal.ZERO;
            BigDecimal rejectedQty = line.getRejectedQty() != null ? line.getRejectedQty() : BigDecimal.ZERO;
            BigDecimal shortageQty = challanQty.subtract(acceptedQty).subtract(rejectedQty);
            if (shortageQty.compareTo(BigDecimal.ZERO) < 0) shortageQty = BigDecimal.ZERO;
            detailVO.setShortageQty(shortageQty);

            detailVO.setPoRate(line.getPoRate());

            BigDecimal rateInSelectedCurrency = line.getRateInSelectedCurrency() != null ? line.getRateInSelectedCurrency() : BigDecimal.ZERO;
            BigDecimal rateInInr = rateInSelectedCurrency.multiply(exchangeRate);
            detailVO.setRateInInr(rateInInr);
            detailVO.setRateInSelectedCurrency(rateInSelectedCurrency);

            BigDecimal apportionedCost = line.getApportionedCost() != null ? line.getApportionedCost() : BigDecimal.ZERO;
            detailVO.setApportionedCost(apportionedCost);
            detailVO.setLandedCostRate(rateInInr.add(apportionedCost));

            BigDecimal amount = acceptedQty.multiply(rateInSelectedCurrency);
            detailVO.setAmount(amount);

            BigDecimal additionalDuty = line.getAdditionalDuty() != null ? line.getAdditionalDuty() : BigDecimal.ZERO;
            detailVO.setAdditionalDuty(additionalDuty);

            BigDecimal amountInSelectedCurrency = amount.add(additionalDuty);
            detailVO.setAmountInSelectedCurrency(amountInSelectedCurrency);
            detailVO.setAmountInInr(amountInSelectedCurrency.multiply(exchangeRate));

            detailVO.setSgstRate(line.getSgstRate());
            detailVO.setSgstAmount(calcAmount(amount, line.getSgstRate()));
            detailVO.setCgstRate(line.getCgstRate());
            detailVO.setCgstAmount(calcAmount(amount, line.getCgstRate()));
            detailVO.setIgstRate(line.getIgstRate());
            detailVO.setIgstAmount(calcAmount(amount, line.getIgstRate()));

            detailVO.setPurchaseBillVO(vo);
            vo.getPurchaseBillDetailsVO().add(detailVO);
        }
    }

    private void buildPurchaseBillTaxGridList(PurchaseBillDTO dto, PurchaseBillVO vo) throws ApplicationException {

        if (vo.getId() != null && vo.getPurchaseBillTaxGridVO() != null && !vo.getPurchaseBillTaxGridVO().isEmpty()) {
            purchaseBillTaxGridRepo.deleteAll(new ArrayList<>(vo.getPurchaseBillTaxGridVO()));
            purchaseBillTaxGridRepo.flush();
        }
        vo.getPurchaseBillTaxGridVO().clear();
        if (dto.getTaxGrid() == null) return;

        for (PurchaseBillTaxGridDTO taxDTO : dto.getTaxGrid()) {
            PurchaseBillTaxGridVO taxVO = new PurchaseBillTaxGridVO();
            taxVO.setParticulars(taxDTO.getParticulars());
            taxVO.setTaxPercent(taxDTO.getTaxPercent());
            taxVO.setAcceptedQtyAmount(taxDTO.getAcceptedQtyAmount());
            taxVO.setRevisedAmount(taxDTO.getRevisedAmount());
            taxVO.setLedgerAccount(resolveLov(taxDTO.getLedgerAccount()));
            taxVO.setDbCr(taxDTO.getDbCr());
            taxVO.setDbAmt(taxDTO.getDbAmt());
            taxVO.setCrAmt(taxDTO.getCrAmt());
            taxVO.setPostToFinanceAc(taxDTO.getPostToFinanceAc());
            taxVO.setPurchaseBillVO(vo);
            vo.getPurchaseBillTaxGridVO().add(taxVO);
        }
    }

    @Override
    public PurchaseBillResponseDTO getPurchaseBillById(Long id) throws ApplicationException {
        PurchaseBillVO vo = purchaseBillRepo.getPurchaseBillById(id);
        if (vo == null) throw new ApplicationException("Purchase Bill Not Found");
        return buildPurchaseBillResponse(vo);
    }

    @Override
    public List<PurchaseBillResponseDTO> getPurchaseBillByOrgId(Long orgId, Long branchId) throws ApplicationException {
        List<PurchaseBillVO> list = purchaseBillRepo.getPurchaseBillByOrgId(orgId, branchId);
        if (list == null || list.isEmpty()) throw new ApplicationException("Purchase Bill Not Found");
        List<PurchaseBillResponseDTO> responseList = new ArrayList<>();
        for (PurchaseBillVO vo : list) responseList.add(buildPurchaseBillResponse(vo));
        return responseList;
    }

    @Override
    public String getPurchaseBillDocId(Long orgId, String finYear, Long branch) {
        return purchaseBillRepo.getPurchaseBillDocId(orgId, SCREEN_CODE_PB);
    }

    private ListOfVlauesDetailsResponseDTO toLovDTO(ListOfValuesDetailsVO lov) {
        if (lov == null) return null;
        return new ListOfVlauesDetailsResponseDTO(lov.getId(), lov.getValueCode(), lov.getValueDescription());
    }

    private PurchaseBillResponseDTO buildPurchaseBillResponse(PurchaseBillVO vo) {

        PurchaseBillResponseDTO dto = new PurchaseBillResponseDTO();
        dto.setId(vo.getId());

        if (vo.getPlant() != null) {
            BranchResponseDTO plantDTO = new BranchResponseDTO();
            plantDTO.setId(vo.getPlant().getId());
            plantDTO.setBranchCode(vo.getPlant().getBranchCode());
            plantDTO.setBranchName(vo.getPlant().getBranchName());
            dto.setPlant(plantDTO);
        }

        dto.setPbNo(vo.getPbNo());
        dto.setBelongsTo(vo.getBelongsTo());
        dto.setPbDate(vo.getPbDate());

        if (vo.getSupplier() != null) {
            CustomerResponseDetailsDTO supplierDTO = new CustomerResponseDetailsDTO();
            supplierDTO.setId(vo.getSupplier().getId());
            supplierDTO.setCustomerName(vo.getSupplier().getCustomerName());
            dto.setSupplier(supplierDTO);
        }

        if (vo.getGstState() != null) {
            dto.setGstState(new GSTStateResponseDTO(vo.getGstState().getId(), vo.getGstState().getStateCode(),
                    vo.getGstState().getStateName(), vo.getGstState().getGstStateId()));
        }

        dto.setGrnNo(vo.getGrnNo());
        dto.setGrnDate(vo.getGrnDate());
        dto.setIsIgstAppl(vo.getIsIgstAppl());
        dto.setExcisable(vo.getExcisable());
        dto.setCurrency(toLovDTO(vo.getCurrency()));
        dto.setGstnNo(vo.getGstnNo());
        dto.setVendorDcNo(vo.getVendorDcNo());
        dto.setExchangeRate(vo.getExchangeRate());
        dto.setDealerType(toLovDTO(vo.getDealerType()));
        dto.setTaxCode(toLovDTO(vo.getTaxCode()));

        dto.setPoNo(vo.getPoNo());
        dto.setPoDate(vo.getPoDate());
        dto.setIsReverseChrg(vo.getIsReverseChrg());
        dto.setVoucherPostingDate(vo.getVoucherPostingDate());
        dto.setDate(vo.getDate());
        dto.setDutyPerUnit(vo.getDutyPerUnit());
        dto.setPostingCategory(toLovDTO(vo.getPostingCategory()));
        dto.setModvatCopyReceived(vo.getModvatCopyReceived());
        dto.setEccType(toLovDTO(vo.getEccType()));
        dto.setSupplierDcInvNo(vo.getSupplierDcInvNo());
        dto.setSupplierDcInvDate(vo.getSupplierDcInvDate());

        dto.setTotalFreight(vo.getTotalFreight());
        dto.setTotalQty(vo.getTotalQty());
        dto.setBasicValue(vo.getBasicValue());
        dto.setTotalAmount(vo.getTotalAmount());
        dto.setAmountInWords(vo.getAmountInWords());
        dto.setEntryTaxApplicable(vo.getEntryTaxApplicable());
        dto.setNarration(vo.getNarration());
        dto.setPaymentTerms(vo.getPaymentTerms());

        dto.setOrgId(vo.getOrgId());
        dto.setFinancialYear(vo.getFinancialYear());
        dto.setActive(vo.getActive());
        dto.setCancelRemarks(vo.getCancelRemarks());
        dto.setCreatedBy(vo.getCreatedBy());
        dto.setUpdatedBy(vo.getUpdatedBy());

        List<PurchaseBillDetailsResponseDTO> detailsList = new ArrayList<>();
        if (vo.getPurchaseBillDetailsVO() != null) {
            for (PurchaseBillDetailsVO d : vo.getPurchaseBillDetailsVO()) {

                PurchaseBillDetailsResponseDTO line = new PurchaseBillDetailsResponseDTO();
                line.setId(d.getId());

                if (d.getItem() != null) {
                    ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();
                    itemDTO.setId(d.getItem().getId());
                    itemDTO.setItemCode(d.getItem().getItemCode());
                    itemDTO.setItemDescription(d.getItem().getItemDescription());
                    line.setItemCode(itemDTO);
                }
                if (d.getHsnCode() != null) {
                    HsnResponseImageDTO hsnDTO = new HsnResponseImageDTO();
                    hsnDTO.setId(d.getHsnCode().getId());
                    hsnDTO.setHsnCode(d.getHsnCode().getHsn());
                    line.setHsnCode(hsnDTO);
                }
                line.setTaxType(toLovDTO(d.getTaxType()));
                line.setTaxPercent(d.getTaxPercent());
                line.setTariffNo(d.getTariffNo());
                line.setExciseToPost(d.getExciseToPost());
                line.setChallanQty(d.getChallanQty());

                if (d.getUnit() != null) {
                    PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();
                    unitDTO.setId(d.getUnit().getId());
                    unitDTO.setPrimaryUnit(d.getUnit().getUnitId());
                    line.setUnit(unitDTO);
                }

                line.setGrnReceivedQty(d.getGrnReceivedQty());
                line.setAcceptedQty(d.getAcceptedQty());
                line.setRejectedQty(d.getRejectedQty());
                line.setShortageQty(d.getShortageQty());
                line.setPoRate(d.getPoRate());
                line.setRateInInr(d.getRateInInr());
                line.setRateInSelectedCurrency(d.getRateInSelectedCurrency());
                line.setApportionedCost(d.getApportionedCost());
                line.setLandedCostRate(d.getLandedCostRate());
                line.setAmount(d.getAmount());
                line.setAmountInSelectedCurrency(d.getAmountInSelectedCurrency());
                line.setAdditionalDuty(d.getAdditionalDuty());
                line.setAmountInInr(d.getAmountInInr());
                line.setSgstRate(d.getSgstRate());
                line.setSgstAmount(d.getSgstAmount());
                line.setCgstRate(d.getCgstRate());
                line.setCgstAmount(d.getCgstAmount());
                line.setIgstRate(d.getIgstRate());
                line.setIgstAmount(d.getIgstAmount());

                detailsList.add(line);
            }
        }
        dto.setPurchaseDetails(detailsList);

        List<PurchaseBillTaxGridResponseDTO> taxList = new ArrayList<>();
        if (vo.getPurchaseBillTaxGridVO() != null) {
            for (PurchaseBillTaxGridVO t : vo.getPurchaseBillTaxGridVO()) {
                PurchaseBillTaxGridResponseDTO taxDTO = new PurchaseBillTaxGridResponseDTO();
                taxDTO.setId(t.getId());
                taxDTO.setParticulars(t.getParticulars());
                taxDTO.setTaxPercent(t.getTaxPercent());
                taxDTO.setAcceptedQtyAmount(t.getAcceptedQtyAmount());
                taxDTO.setRevisedAmount(t.getRevisedAmount());
                taxDTO.setLedgerAccount(toLovDTO(t.getLedgerAccount()));
                taxDTO.setDbCr(t.getDbCr());
                taxDTO.setDbAmt(t.getDbAmt());
                taxDTO.setCrAmt(t.getCrAmt());
                taxDTO.setPostToFinanceAc(t.getPostToFinanceAc());
                taxList.add(taxDTO);
            }
        }
        dto.setTaxGrid(taxList);

        return dto;
    }

    
    // ============================ PURCHASE INDENT =====================
   

    @Override
    @Transactional
    public Map<String, Object> createUpdatePurchaseIndent(
            PurchaseIndentDTO purchaseIndentDTO)
            throws ApplicationException {

        Map<String, Object> response = new HashMap<>();

        PurchaseIndentVO purchaseIndentVO = null;

        if (purchaseIndentDTO.getId() != null) {

            purchaseIndentVO = purchaseIndentRepo.findById(
                    purchaseIndentDTO.getId())
                    .orElseThrow(() ->
                            new ApplicationException("Purchase Indent not found"));

            purchaseIndentDetailsRepo.deleteByPurchaseIndentVO(
                    purchaseIndentVO);

            purchaseIndentAttachmentRepo.deleteByPurchaseIndentVO(
                    purchaseIndentVO);

        } else {

            purchaseIndentVO = new PurchaseIndentVO();

        }

        // ==========================
        // Header Mapping
        // ==========================

        purchaseIndentVO.setDocId(
                purchaseIndentDTO.getDocId());

        purchaseIndentVO.setBelongsTo(
                purchaseIndentDTO.getBelongsTo());

        purchaseIndentVO.setDocDate(
                purchaseIndentDTO.getDocDate());

        purchaseIndentVO.setApproved(
                purchaseIndentDTO.isApproved());

        purchaseIndentVO.setRemarks(
                purchaseIndentDTO.getRemarks());

        purchaseIndentVO.setOrgId(
                purchaseIndentDTO.getOrgId());

        purchaseIndentVO.setCreatedBy(
                purchaseIndentDTO.getCreatedBy());

        purchaseIndentVO.setUpdatedBy(
                purchaseIndentDTO.getUpdatedBy());

        purchaseIndentVO.setActive(
                purchaseIndentDTO.isActive());

        purchaseIndentVO.setCancel(
                purchaseIndentDTO.isCancel());

        purchaseIndentVO.setCancelRemarks(
                purchaseIndentDTO.getCancelRemarks());

        // ==========================
        // Branch
        // ==========================

        if (purchaseIndentDTO.getBranch() != null) {

            BranchVO branchVO =
                    branchRepo.findById(
                            purchaseIndentDTO.getBranch())
                            .orElseThrow(() ->
                                    new ApplicationException("Branch not found"));

            purchaseIndentVO.setBranch(branchVO);
        }

        // ==========================
        // Department
        // ==========================

        if (purchaseIndentDTO.getDepartment() != null) {

            DepartmentVO departmentVO =
                    departmentRepo.findById(
                            purchaseIndentDTO.getDepartment())
                            .orElseThrow(() ->
                                    new ApplicationException("Department not found"));

            purchaseIndentVO.setDepartment(departmentVO);
        }

        // ==========================
        // Prepared By
        // ==========================

        if (purchaseIndentDTO.getPreparedBy() != null) {

            EmployeeMasterVO preparedBy =
                    employeeMasterRepo.findById(
                            purchaseIndentDTO.getPreparedBy())
                            .orElseThrow(() ->
                                    new ApplicationException("Employee not found"));

            purchaseIndentVO.setPreparedBy(preparedBy);
        }

        // ==========================
        // By Whom
        // ==========================

        if (purchaseIndentDTO.getByWhom() != null) {

            EmployeeMasterVO byWhom =
                    employeeMasterRepo.findById(
                            purchaseIndentDTO.getByWhom())
                            .orElseThrow(() ->
                                    new ApplicationException("Employee not found"));

            purchaseIndentVO.setByWhom(byWhom);
        }

        // ==========================
        // Save Header
        // ==========================

        purchaseIndentVO =
                purchaseIndentRepo.save(purchaseIndentVO);
        
     // ==========================
     // Details Save
     // ==========================

     List<PurchaseIndentDetailsVO> detailsList = new ArrayList<>();

     if (purchaseIndentDTO.getDetails() != null) {

         for (PurchaseIndentDetailsDTO detailDTO : purchaseIndentDTO.getDetails()) {

             PurchaseIndentDetailsVO detailsVO = new PurchaseIndentDetailsVO();

             // Parent Mapping
             detailsVO.setPurchaseIndentVO(purchaseIndentVO);

             // Item
             if (detailDTO.getItem() != null) {

                 ItemMasterVO itemVO =
                         itemMasterRepo.findById(detailDTO.getItem())
                         .orElseThrow(() ->
                         new ApplicationException("Item not found"));

                 detailsVO.setItem(itemVO);
             }

             // Qty in Primary Unit
             detailsVO.setQtyInPrimaryUnit(
                     detailDTO.getQtyInPrimaryUnit());

             // Conversion Factor
             if (detailDTO.getConversionFactor() != null) {

                 UomConversionVO conversionVO =
                         uomConversionRepo.findById(
                                 detailDTO.getConversionFactor())
                         .orElseThrow(() ->
                         new ApplicationException("Conversion Factor not found"));

                 detailsVO.setConversionFactor(conversionVO);
             }

             // Qty in Purchase Unit
             detailsVO.setQtyInPurchaseUnit(
                     detailDTO.getQtyInPurchaseUnit());

             // Required Date
             detailsVO.setRequiredDate(
                     detailDTO.getRequiredDate());

             // Purpose
             detailsVO.setPurpose(
                     detailDTO.getPurpose());

             detailsList.add(detailsVO);
         }

         purchaseIndentDetailsRepo.saveAll(detailsList);
     }
         
      // ==========================
      // Attachment Save
      // ==========================

      List<PurchaseIndentAttachmentVO> attachmentList = new ArrayList<>();

      if (purchaseIndentDTO.getAttachments() != null) {

          for (PurchaseIndentAttachmentDTO attachmentDTO : purchaseIndentDTO.getAttachments()) {

              PurchaseIndentAttachmentVO attachmentVO =
                      new PurchaseIndentAttachmentVO();

              attachmentVO.setPurchaseIndentVO(purchaseIndentVO);

              attachmentVO.setName(
                      attachmentDTO.getName());

              attachmentVO.setFileName(
                      attachmentDTO.getFileName());

              attachmentVO.setFilePath(
                      attachmentDTO.getFilePath());

              attachmentVO.setFileSize(
                      attachmentDTO.getFileSize());

              attachmentVO.setUploadOn(
                      attachmentDTO.getUploadOn());

              attachmentList.add(attachmentVO);
          }

          purchaseIndentAttachmentRepo.saveAll(attachmentList);
      }


   // ==========================
   // Response
   // ==========================

   String message;

   if (purchaseIndentDTO.getId() == null) {
       message = "Purchase Indent Created Successfully";
   } else {
       message = "Purchase Indent Updated Successfully";
   }

   PurchaseIndentResponseDTO responseDTO =
           purchaseIndentResponse(purchaseIndentVO);

   response.put("message", message);
   response.put("purchaseIndentVO", responseDTO);

   return response;
   
    }
    
    private PurchaseIndentResponseDTO purchaseIndentResponse(
            PurchaseIndentVO purchaseIndentVO)
            throws ApplicationException {

        PurchaseIndentResponseDTO responseDTO =
                new PurchaseIndentResponseDTO();

        // =========================
        // Header
        // =========================

        responseDTO.setId(purchaseIndentVO.getId());

        responseDTO.setDocId(
                purchaseIndentVO.getDocId());

        responseDTO.setBelongsTo(
                purchaseIndentVO.getBelongsTo());

        responseDTO.setDocDate(
                purchaseIndentVO.getDocDate());

        responseDTO.setApproved(
                purchaseIndentVO.isApproved());

        responseDTO.setRemarks(
                purchaseIndentVO.getRemarks());

        responseDTO.setOrgId(
                purchaseIndentVO.getOrgId());

        responseDTO.setCreatedBy(
                purchaseIndentVO.getCreatedBy());

        responseDTO.setUpdatedBy(
                purchaseIndentVO.getUpdatedBy());

        responseDTO.setCancelRemarks(
                purchaseIndentVO.getCancelRemarks());

        responseDTO.setActive(
                purchaseIndentVO.isActive());

        responseDTO.setCancel(
                purchaseIndentVO.isCancel());

        responseDTO.setScreenName(
                purchaseIndentVO.getScreenName());

        responseDTO.setScreenCode(
                purchaseIndentVO.getScreenCode());

        // =========================
        // Branch
        // =========================

        if (purchaseIndentVO.getBranch() != null) {

            BranchResponseDTO branchResponse =
                    new BranchResponseDTO();

            branchResponse.setId(
                    purchaseIndentVO.getBranch().getId());

            branchResponse.setBranchName(
                    purchaseIndentVO.getBranch().getBranchName());

            responseDTO.setBranch(branchResponse);
        }

        // =========================
        // Department
        // =========================

        if (purchaseIndentVO.getDepartment() != null) {

            DepartmentResponseDTO departmentResponse =
                    new DepartmentResponseDTO();

            departmentResponse.setId(
                    purchaseIndentVO.getDepartment().getId());

            departmentResponse.setDepartmentName(
                    purchaseIndentVO.getDepartment().getDepartmentName());

            responseDTO.setDepartment(departmentResponse);
        }

        // =========================
        // Prepared By
        // =========================

        if (purchaseIndentVO.getPreparedBy() != null) {

            EmployeeResponseDTO employeeResponse =
                    new EmployeeResponseDTO();

            employeeResponse.setId(
                    purchaseIndentVO.getPreparedBy().getId());

            employeeResponse.setEmployeeName(
                    purchaseIndentVO.getPreparedBy().getEmployeeName());

            responseDTO.setPreparedBy(employeeResponse);
        }

        // =========================
        // By Whom
        // =========================

        if (purchaseIndentVO.getByWhom() != null) {

            EmployeeResponseDTO employeeResponse =
                    new EmployeeResponseDTO();

            employeeResponse.setId(
                    purchaseIndentVO.getByWhom().getId());

            employeeResponse.setEmployeeName(
                    purchaseIndentVO.getByWhom().getEmployeeName());

            responseDTO.setByWhom(employeeResponse);
        }

        // =========================
        // Details
        // =========================

        List<PurchaseIndentDetailsResponseDTO> detailsResponse =
                new ArrayList<>();

        if (purchaseIndentVO.getDetails() != null) {

            for (PurchaseIndentDetailsVO detailVO :
                    purchaseIndentVO.getDetails()) {

                PurchaseIndentDetailsResponseDTO detailResponse =
                        new PurchaseIndentDetailsResponseDTO();

                detailResponse.setId(detailVO.getId());

                // Item
                if (detailVO.getItem() != null) {

                    PurchaseIndentItemResponseDTO itemResponse =
                            new PurchaseIndentItemResponseDTO();

                    itemResponse.setId(detailVO.getItem().getId());
                    itemResponse.setItemCode(detailVO.getItem().getItemCode());
                    itemResponse.setItemDescription(detailVO.getItem().getItemDescription());

                    if (detailVO.getItem().getPrimaryUnit() != null) {
                        itemResponse.setPrimaryUnit(
                                detailVO.getItem().getPrimaryUnit().getUnitId());
                    }

                    if (detailVO.getItem().getPurchaseUnit() != null) {
                        itemResponse.setPurchaseUnit(
                                detailVO.getItem().getPurchaseUnit().getUnitId());
                    }
                    
                    detailResponse.setItem(itemResponse);
                    
                }
                // Conversion Factor
                if (detailVO.getConversionFactor() != null) {

                	UomConversionResponseDTO conversionResponse =
                	        new UomConversionResponseDTO();

                	conversionResponse.setId(
                	        detailVO.getConversionFactor().getId());

                	conversionResponse.setMultiplicationFactor(
                	        detailVO.getConversionFactor().getMultiplicationFactor());
                    detailResponse.setConversionFactor(conversionResponse);
                }

                detailResponse.setQtyInPrimaryUnit(
                        detailVO.getQtyInPrimaryUnit());

                detailResponse.setQtyInPurchaseUnit(
                        detailVO.getQtyInPurchaseUnit());

                detailResponse.setRequiredDate(
                        detailVO.getRequiredDate());

                detailResponse.setPurpose(
                        detailVO.getPurpose());

                detailsResponse.add(detailResponse);
            }
        }

        responseDTO.setDetails(detailsResponse);

        // =========================
        // Attachments
        // =========================

        List<PurchaseIndentAttachmentResponseDTO> attachmentResponse =
                new ArrayList<>();

        if (purchaseIndentVO.getAttachments() != null) {

            for (PurchaseIndentAttachmentVO attachment :
                    purchaseIndentVO.getAttachments()) {

                PurchaseIndentAttachmentResponseDTO dto =
                        new PurchaseIndentAttachmentResponseDTO();

                dto.setId(attachment.getId());
                dto.setName(attachment.getName());
                dto.setFileName(attachment.getFileName());
                dto.setFilePath(attachment.getFilePath());
                dto.setFileSize(attachment.getFileSize());
                dto.setUploadOn(attachment.getUploadOn());

                attachmentResponse.add(dto);
            }
        }

        responseDTO.setAttachments(attachmentResponse);

        return responseDTO;
    }
    
    @Override
    public PurchaseIndentResponseDTO getPurchaseIndentById(Long id)
            throws ApplicationException {

        PurchaseIndentVO purchaseIndentVO = purchaseIndentRepo.findById(id)
                .orElseThrow(() ->
                        new ApplicationException("Purchase Indent Not Found"));

        return purchaseIndentResponse(purchaseIndentVO);
    }
    
    
    @Override
    public List<PurchaseIndentResponseDTO> getPurchaseIndentByOrgId(
            Long orgId,
            Long branch)
            throws ApplicationException {

        List<PurchaseIndentVO> purchaseIndentVOList =
                purchaseIndentRepo.findByOrgId(orgId, branch);

        List<PurchaseIndentResponseDTO> responseList =
                new ArrayList<>();

        for (PurchaseIndentVO purchaseIndentVO : purchaseIndentVOList) {

            responseList.add(
                    purchaseIndentResponse(purchaseIndentVO));
        }

        return responseList;
    }
    

// ==================================================================
    // ===================== PURCHASE SHORT CLOSE =======================
    // PO/Del.Sch.No link is commented out - Local Purchase/PO module not built yet.
    // pendingQty / shortCloseQty are always server-calculated, never trusted from client.
    // ==================================================================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateCreatePurchaseShortClose(PurchaseShortCloseDTO dto) throws ApplicationException {

        PurchaseShortCloseVO vo;
        String message;
        boolean isUpdate = ObjectUtils.isNotEmpty(dto.getId());

        if (isUpdate) {
            vo = purchaseShortCloseRepo.findById(dto.getId())
                    .orElseThrow(() -> new ApplicationException("Purchase Short Close Not Found"));
            vo.setUpdatedBy(dto.getCreatedBy());
            message = "Purchase Short Close Updated Successfully";
        } else {
            vo = new PurchaseShortCloseVO();
            vo.setCreatedBy(dto.getCreatedBy());
            vo.setUpdatedBy(dto.getCreatedBy());
            vo.setShortCloseNo(purchaseShortCloseRepo.getPurchaseShortCloseDocId(dto.getOrgId(), SCREEN_CODE_SC));
            message = "Purchase Short Close Created Successfully";
        }

        createUpdatePurchaseShortCloseVOFromDTO(dto, vo);

        if (isUpdate) {
            purchaseShortCloseRepo.flush();
        } else {
            vo = purchaseShortCloseRepo.save(vo);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("purchaseShortCloseVO", buildPurchaseShortCloseResponse(vo));
        return response;
    }

    private void createUpdatePurchaseShortCloseVOFromDTO(PurchaseShortCloseDTO dto, PurchaseShortCloseVO vo) throws ApplicationException {

        if (dto.getBranch() != null && dto.getBranch() != 0) {
            vo.setPlant(branchRepo.findById(dto.getBranch()).orElseThrow(() -> new ApplicationException("Branch Not Found")));
        }
        vo.setBelongsTo(dto.getBelongsTo());
        vo.setShortCloseDate(dto.getShortCloseDate());
        vo.setType(dto.getType());

        if (dto.getSupplier() != null && dto.getSupplier() != 0) {
            vo.setSupplier(customerRepo.findById(dto.getSupplier()).orElseThrow(() -> new ApplicationException("Supplier Not Found")));
        }

        if (dto.getLocalPurchaseOrderId() != null) {
            LocalPurchaseOrderVO lpo = localPurchaseOrderRepo.findById(dto.getLocalPurchaseOrderId())
                    .orElseThrow(() -> new ApplicationException("Local Purchase Order Not Found"));
            vo.setLocalPurchaseOrder(lpo);
            vo.setPoNo(lpo.getPoNo());
            vo.setPoDate(lpo.getPoDate());
        } else {
            vo.setLocalPurchaseOrder(null);
            vo.setPoNo(null);
            vo.setPoDate(null);
        }

        vo.setReferenceForShortClose(dto.getReferenceForShortClose());

        vo.setOrgId(dto.getOrgId());
        vo.setFinancialYear(dto.getFinancialYear());
        vo.setActive(dto.isActive());
        vo.setCancelRemarks(dto.getCancelRemarks());

        buildShortCloseDetailsList(dto, vo);
    }

    // "1-Order Closed Detail" grid — with calculations
    private void buildShortCloseDetailsList(PurchaseShortCloseDTO dto, PurchaseShortCloseVO vo) throws ApplicationException {

        if (vo.getId() != null && vo.getPurchaseShortCloseDetailsVO() != null && !vo.getPurchaseShortCloseDetailsVO().isEmpty()) {
            purchaseShortCloseDetailsRepo.deleteAll(new ArrayList<>(vo.getPurchaseShortCloseDetailsVO()));
            purchaseShortCloseDetailsRepo.flush();
        }
        vo.getPurchaseShortCloseDetailsVO().clear();
        if (dto.getDetails() == null) return;

        for (PurchaseShortCloseDetailsDTO line : dto.getDetails()) {

            PurchaseShortCloseDetailsVO detailVO = new PurchaseShortCloseDetailsVO();

            if (line.getItemId() != null && line.getItemId() != 0) {
                ItemMasterVO item = itemMasterRepo.findById(line.getItemId()).orElseThrow(() -> new ApplicationException("Item Not Found"));
                detailVO.setItem(item);

                if (line.getUnitId() != null && line.getUnitId() != 0) {
                    detailVO.setUnit(unitMasterRepo.findById(line.getUnitId()).orElseThrow(() -> new ApplicationException("Unit Not Found")));
                } else if (item.getPrimaryUnit() != null) {
                    detailVO.setUnit(item.getPrimaryUnit());
                }
            }

            // ** PENDING: once Local Purchase/PO module exists, orderedQty/suppliedQty
            // should be looked up here by matching vo's PO id against that module's item
            // lines for this item, instead of being taken from the client.
            BigDecimal orderedQty = line.getOrderedQty() != null ? line.getOrderedQty() : BigDecimal.ZERO;
            BigDecimal suppliedQty = line.getSuppliedQty() != null ? line.getSuppliedQty() : BigDecimal.ZERO;
            detailVO.setOrderedQty(orderedQty);
            detailVO.setSuppliedQty(suppliedQty);

            BigDecimal pendingQty = orderedQty.subtract(suppliedQty);
            if (pendingQty.compareTo(BigDecimal.ZERO) < 0) pendingQty = BigDecimal.ZERO;
            detailVO.setPendingQty(pendingQty);

            BigDecimal newRequiredQty = line.getNewRequiredQty() != null ? line.getNewRequiredQty() : BigDecimal.ZERO;
            detailVO.setNewRequiredQty(newRequiredQty);

            BigDecimal shortCloseQty = pendingQty.subtract(newRequiredQty);
            if (shortCloseQty.compareTo(BigDecimal.ZERO) < 0) shortCloseQty = BigDecimal.ZERO;
            detailVO.setShortCloseQty(shortCloseQty);

            detailVO.setPurchaseShortCloseVO(vo);
            vo.getPurchaseShortCloseDetailsVO().add(detailVO);
        }
    }

    @Override
    public PurchaseShortCloseResponseDTO getPurchaseShortCloseById(Long id) throws ApplicationException {
        PurchaseShortCloseVO vo = purchaseShortCloseRepo.getPurchaseShortCloseById(id);
        if (vo == null) throw new ApplicationException("Purchase Short Close Not Found");
        return buildPurchaseShortCloseResponse(vo);
    }

    @Override
    public List<PurchaseShortCloseResponseDTO> getPurchaseShortCloseByOrgId(Long orgId, Long branchId) throws ApplicationException {
        List<PurchaseShortCloseVO> list = purchaseShortCloseRepo.getPurchaseShortCloseByOrgId(orgId, branchId);
        if (list == null || list.isEmpty()) throw new ApplicationException("Purchase Short Close Not Found");
        List<PurchaseShortCloseResponseDTO> responseList = new ArrayList<>();
        for (PurchaseShortCloseVO vo : list) responseList.add(buildPurchaseShortCloseResponse(vo));
        return responseList;
    }

    @Override
    public String getPurchaseShortCloseDocId(Long orgId, String finYear, Long branch) {
        return purchaseShortCloseRepo.getPurchaseShortCloseDocId(orgId, SCREEN_CODE_SC);
    }

    private PurchaseShortCloseResponseDTO buildPurchaseShortCloseResponse(PurchaseShortCloseVO vo) {

        PurchaseShortCloseResponseDTO dto = new PurchaseShortCloseResponseDTO();
        dto.setId(vo.getId());

        if (vo.getPlant() != null) {
            BranchResponseDTO plantDTO = new BranchResponseDTO();
            plantDTO.setId(vo.getPlant().getId());
            plantDTO.setBranchCode(vo.getPlant().getBranchCode());
            plantDTO.setBranchName(vo.getPlant().getBranchName());
            dto.setPlant(plantDTO);
        }

        dto.setShortCloseNo(vo.getShortCloseNo());
        dto.setBelongsTo(vo.getBelongsTo());
        dto.setShortCloseDate(vo.getShortCloseDate());
        dto.setType(vo.getType());

        if (vo.getSupplier() != null) {
            CustomerResponseDetailsDTO supplierDTO = new CustomerResponseDetailsDTO();
            supplierDTO.setId(vo.getSupplier().getId());
            supplierDTO.setCustomerName(vo.getSupplier().getCustomerName());
            dto.setSupplier(supplierDTO);
        }

        dto.setLocalPurchaseOrderId(vo.getLocalPurchaseOrder() != null ? vo.getLocalPurchaseOrder().getId() : null);
        dto.setPoNo(vo.getPoNo());
        dto.setPoDate(vo.getPoDate());

        dto.setReferenceForShortClose(vo.getReferenceForShortClose());

        dto.setOrgId(vo.getOrgId());
        dto.setFinancialYear(vo.getFinancialYear());
        dto.setActive(vo.getActive());
        dto.setCancelRemarks(vo.getCancelRemarks());
        dto.setCreatedBy(vo.getCreatedBy());
        dto.setUpdatedBy(vo.getUpdatedBy());

        List<PurchaseShortCloseDetailsResponseDTO> detailsList = new ArrayList<>();
        if (vo.getPurchaseShortCloseDetailsVO() != null) {
            for (PurchaseShortCloseDetailsVO d : vo.getPurchaseShortCloseDetailsVO()) {

                PurchaseShortCloseDetailsResponseDTO line = new PurchaseShortCloseDetailsResponseDTO();
                line.setId(d.getId());

                if (d.getItem() != null) {
                    ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();
                    itemDTO.setId(d.getItem().getId());
                    itemDTO.setItemCode(d.getItem().getItemCode());
                    itemDTO.setItemDescription(d.getItem().getItemDescription());
                    line.setItemCode(itemDTO);
                }
                if (d.getUnit() != null) {
                    PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();
                    unitDTO.setId(d.getUnit().getId());
                    unitDTO.setPrimaryUnit(d.getUnit().getUnitId());
                    line.setUnit(unitDTO);
                }

                line.setOrderedQty(d.getOrderedQty());
                line.setSuppliedQty(d.getSuppliedQty());
                line.setPendingQty(d.getPendingQty());
                line.setNewRequiredQty(d.getNewRequiredQty());
                line.setShortCloseQty(d.getShortCloseQty());

                detailsList.add(line);
            }
        }
        dto.setDetails(detailsList);

        return dto;
    }

    // ==================================================================
    // ========================= LOCAL PURCHASE ORDER ===================
    // Same structure as Purchase Contract (branch/dept/supplier/GST resolution,
    // tax details grid, attachment upload). PO Detail grid additionally links
    // to Purchase Indent lines and calculates Pending Indent Qty.
    // ==================================================================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateCreateLocalPurchaseOrder(LocalPurchaseOrderDTO dto, MultipartFile[] files)
            throws ApplicationException {

        LocalPurchaseOrderVO vo;
        String message;
        boolean isUpdate = ObjectUtils.isNotEmpty(dto.getId());

        if (isUpdate) {
            vo = localPurchaseOrderRepo.findById(dto.getId())
                    .orElseThrow(() -> new ApplicationException("Local Purchase Order Not Found"));
            vo.setUpdatedBy(dto.getCreatedBy());
            message = "Local Purchase Order Updated Successfully";
        } else {
            vo = new LocalPurchaseOrderVO();
            vo.setCreatedBy(dto.getCreatedBy());
            vo.setUpdatedBy(dto.getCreatedBy());
            vo.setPoNo(localPurchaseOrderRepo.getLocalPurchaseOrderDocId(dto.getOrgId(), SCREEN_CODE_LPO));
            message = "Local Purchase Order Created Successfully";
        }

        createUpdateLocalPurchaseOrderVOFromDTO(dto, vo);

        if (isUpdate) {
            localPurchaseOrderRepo.flush();
        } else {
            vo = localPurchaseOrderRepo.save(vo);
        }

        if (isUpdate) {
            deleteExistingLpoAttachments(vo);
        }
        saveLpoAttachments(files, vo);

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("localPurchaseOrderVO", buildLocalPurchaseOrderResponse(vo));
        return response;
    }

    private void createUpdateLocalPurchaseOrderVOFromDTO(LocalPurchaseOrderDTO dto, LocalPurchaseOrderVO vo) throws ApplicationException {

        if (dto.getBranch() != null && dto.getBranch() != 0) {
            vo.setPlant(branchRepo.findById(dto.getBranch()).orElseThrow(() -> new ApplicationException("Branch Not Found")));
        }
        vo.setBelongsTo(dto.getBelongsTo());
        vo.setPoDate(dto.getPoDate());

        if (dto.getDepartment() != null && dto.getDepartment() != 0) {
            vo.setDepartment(resolveLov(dto.getDepartment()));
        }

        if (dto.getSupplier() != null && dto.getSupplier() != 0) {
            CustomerVO supplier = customerRepo.findById(dto.getSupplier())
                    .orElseThrow(() -> new ApplicationException("Supplier Not Found"));
            vo.setSupplier(supplier);

            GSTStateMasterVO gstState = supplier.getGstState();
            vo.setGstState(gstState);

            boolean isIndia = gstState != null && gstState.getStateName() != null
                    && gstState.getStateName().toUpperCase().contains(INDIA);
            if (supplier.getCountry() != null && supplier.getCountry().getCountryName() != null) {
                isIndia = supplier.getCountry().getCountryName().toUpperCase().contains(INDIA);
            }
            vo.setIsIgstAppl(!isIndia);
            // vo.setGstnNo(supplier.getGstnNo()); // still pending confirmed getter name on CustomerVO, same as Purchase Bill
        }

        vo.setSupplierRefNo(dto.getSupplierRefNo());
        vo.setAddress(dto.getAddress());
        vo.setSuppRefDt(dto.getSuppRefDt());

        vo.setTaxCode(resolveLov(dto.getTaxCode()));
        vo.setIsReverseChrg(dto.getIsReverseChrg());
        vo.setItemType(dto.getItemType());
        vo.setIndentRequired(dto.getIndentRequired());
        vo.setDealerType(resolveLov(dto.getDealerType()));

        vo.setFreightType(dto.getFreightType());
        vo.setPackingType(dto.getPackingType());
        vo.setInsurance(dto.getInsurance());
        vo.setFreight(dto.getFreight());
        vo.setTotalAmount(dto.getTotalAmount());
        vo.setModeOfDespatch(dto.getModeOfDespatch());
        vo.setPaymentTerms(dto.getPaymentTerms());
        vo.setDeliveryTerms(dto.getDeliveryTerms());
        vo.setAmountInWords(dto.getAmountInWords());
        vo.setRemarks(dto.getRemarks());
        vo.setNotes(dto.getNotes());

        if (dto.getPreparedBy() != null) {
            vo.setPreparedBy(employeeMasterRepo.findById(dto.getPreparedBy()).orElseThrow(() -> new ApplicationException("Prepared By Not Found")));
        }
        if (dto.getCheckedBy() != null) {
            vo.setCheckedBy(employeeMasterRepo.findById(dto.getCheckedBy()).orElseThrow(() -> new ApplicationException("Checked By Not Found")));
        }
        if (dto.getAuthorisedBy() != null) {
            vo.setAuthorisedBy(employeeMasterRepo.findById(dto.getAuthorisedBy()).orElseThrow(() -> new ApplicationException("Authorised By Not Found")));
        }

        vo.setOrgId(dto.getOrgId());
        vo.setFinancialYear(dto.getFinancialYear());
        vo.setActive(dto.isActive());
        vo.setCancelRemarks(dto.getCancelRemarks());

        buildLpoDetailsList(dto, vo);
        buildLpoTaxDetailsList(dto, vo);
    }

    // "1-PO Detail" grid — with calculations, including Pending Indent Qty
    private void buildLpoDetailsList(LocalPurchaseOrderDTO dto, LocalPurchaseOrderVO vo) throws ApplicationException {

        if (vo.getId() != null && vo.getLocalPurchaseOrderDetailsVO() != null && !vo.getLocalPurchaseOrderDetailsVO().isEmpty()) {
            localPurchaseOrderDetailsRepo.deleteAll(new ArrayList<>(vo.getLocalPurchaseOrderDetailsVO()));
            localPurchaseOrderDetailsRepo.flush();
        }
        vo.getLocalPurchaseOrderDetailsVO().clear();
        if (dto.getDetails() == null) return;

        for (LocalPurchaseOrderDetailsDTO line : dto.getDetails()) {

            LocalPurchaseOrderDetailsVO detailVO = new LocalPurchaseOrderDetailsVO();

            if (line.getItemId() != null && line.getItemId() != 0) {
                ItemMasterVO item = itemMasterRepo.findById(line.getItemId()).orElseThrow(() -> new ApplicationException("Item Not Found"));
                detailVO.setItem(item);

                if (line.getHsnId() != null && line.getHsnId() != 0) {
                    detailVO.setHsnCode(hsnRepo.findById(line.getHsnId()).orElseThrow(() -> new ApplicationException("HSN Not Found")));
                } else if (item.getHsnCode() != null) {
                    detailVO.setHsnCode(item.getHsnCode());
                }

                if (line.getPrimaryUnitId() != null && line.getPrimaryUnitId() != 0) {
                    detailVO.setPrimaryUnit(unitMasterRepo.findById(line.getPrimaryUnitId()).orElseThrow(() -> new ApplicationException("Primary Unit Not Found")));
                } else if (item.getPrimaryUnit() != null) {
                    detailVO.setPrimaryUnit(item.getPrimaryUnit());
                }
            }

            // ** NEEDS CONFIRMATION - customerPartNo: using client value; swap to item.getCustomerPartNo()
            // once that field is confirmed to exist on ItemMasterVO
            detailVO.setCustomerPartNo(line.getCustomerPartNo());

            if (line.getTaxType() != null && line.getTaxType() != 0) detailVO.setTaxType(resolveLov(line.getTaxType()));
            detailVO.setTaxPercent(line.getTaxPercent());

            if (line.getPurchaseUnitId() != null && line.getPurchaseUnitId() != 0) {
                detailVO.setPurchaseUnit(unitMasterRepo.findById(line.getPurchaseUnitId()).orElseThrow(() -> new ApplicationException("Purchase Unit Not Found")));
            }

            BigDecimal conversionFactor = line.getConversionFactor() != null ? line.getConversionFactor() : BigDecimal.ONE;
            detailVO.setConversionFactor(conversionFactor);

            // ---- Indent linkage: Indent No / Indent Date / Indent Qty / Pending Indent Qty ----
            if (line.getIndentDetailId() != null) {

                PurchaseIndentDetailsVO indentDetail = purchaseIndentDetailsRepo.findById(line.getIndentDetailId())
                        .orElseThrow(() -> new ApplicationException("Indent Line Not Found: " + line.getIndentDetailId()));
                detailVO.setIndentDetails(indentDetail);

                BigDecimal indentQty = indentDetail.getQtyInPurchaseUnit() != null
                        ? indentDetail.getQtyInPurchaseUnit() : BigDecimal.ZERO;
                detailVO.setIndentQty(indentQty);

                // Sum already placed on OTHER local purchase orders against this same indent line
                BigDecimal alreadyOrdered = localPurchaseOrderDetailsRepo
                        .getAlreadyOrderedQtyForIndentLine(line.getIndentDetailId(), vo.getId());
                if (alreadyOrdered == null) alreadyOrdered = BigDecimal.ZERO;

                BigDecimal pendingIndentQty = indentQty.subtract(alreadyOrdered);
                if (pendingIndentQty.compareTo(BigDecimal.ZERO) < 0) pendingIndentQty = BigDecimal.ZERO;
                detailVO.setPendingIndentQty(pendingIndentQty);
            }

            BigDecimal poQtyInPurchaseUnit = line.getPoQtyInPurchaseUnit() != null ? line.getPoQtyInPurchaseUnit() : BigDecimal.ZERO;
            detailVO.setPoQtyInPurchaseUnit(poQtyInPurchaseUnit);
            detailVO.setQtyInPrimaryUnit(poQtyInPurchaseUnit.multiply(conversionFactor));

            BigDecimal rateInInr = line.getRateInInr() != null ? line.getRateInInr() : BigDecimal.ZERO;
            detailVO.setRateInInr(rateInInr);

            BigDecimal baseAmount = poQtyInPurchaseUnit.multiply(rateInInr);

            BigDecimal discountPercent = line.getDiscountPercent() != null ? line.getDiscountPercent() : BigDecimal.ZERO;
            detailVO.setDiscountPercent(discountPercent);

            BigDecimal discountAmountInr = calcAmount(baseAmount, discountPercent);
            detailVO.setDiscountAmountInr(discountAmountInr);

            BigDecimal amountInInr = baseAmount.subtract(discountAmountInr);
            detailVO.setAmountInInr(amountInInr);

            detailVO.setDeliveryDate(line.getDeliveryDate());

            detailVO.setSgstRate(line.getSgstRate());
            detailVO.setSgstAmount(calcAmount(amountInInr, line.getSgstRate()));
            detailVO.setCgstRate(line.getCgstRate());
            detailVO.setCgstAmount(calcAmount(amountInInr, line.getCgstRate()));
            detailVO.setIgstRate(line.getIgstRate());
            detailVO.setIgstAmount(calcAmount(amountInInr, line.getIgstRate()));

            detailVO.setLocalPurchaseOrderVO(vo);
            vo.getLocalPurchaseOrderDetailsVO().add(detailVO);
        }
    }

    private void buildLpoTaxDetailsList(LocalPurchaseOrderDTO dto, LocalPurchaseOrderVO vo) {

        if (vo.getId() != null && vo.getLocalPurchaseOrderTaxDetailsVO() != null && !vo.getLocalPurchaseOrderTaxDetailsVO().isEmpty()) {
            localPurchaseOrderTaxDetailsRepo.deleteAll(new ArrayList<>(vo.getLocalPurchaseOrderTaxDetailsVO()));
            localPurchaseOrderTaxDetailsRepo.flush();
        }
        vo.getLocalPurchaseOrderTaxDetailsVO().clear();
        if (dto.getTaxDetails() == null) return;

        for (LocalPurchaseOrderTaxDetailsDTO taxDTO : dto.getTaxDetails()) {
            LocalPurchaseOrderTaxDetailsVO taxVO = new LocalPurchaseOrderTaxDetailsVO();
            taxVO.setParticulars(taxDTO.getParticulars());
            taxVO.setTaxPercent(taxDTO.getTaxPercent());
            taxVO.setAmount(taxDTO.getAmount());
            taxVO.setLocalPurchaseOrderVO(vo);
            vo.getLocalPurchaseOrderTaxDetailsVO().add(taxVO);
        }
    }

    private void deleteExistingLpoAttachments(LocalPurchaseOrderVO vo) {
        if (vo.getLocalPurchaseOrderAttachmentVO() == null || vo.getLocalPurchaseOrderAttachmentVO().isEmpty()) return;

        List<LocalPurchaseOrderAttachmentVO> existing = new ArrayList<>(vo.getLocalPurchaseOrderAttachmentVO());
        for (LocalPurchaseOrderAttachmentVO a : existing) {
            try {
                if (a.getFilePath() != null) Files.deleteIfExists(Paths.get(a.getFilePath()));
            } catch (IOException e) {
                LOGGER.error("Failed to delete attachment file from disk: {} - {}", a.getFilePath(), e.getMessage());
            }
        }
        localPurchaseOrderAttachmentRepo.deleteAll(existing);
        localPurchaseOrderAttachmentRepo.flush();
        vo.getLocalPurchaseOrderAttachmentVO().clear();
    }

    private void saveLpoAttachments(MultipartFile[] files, LocalPurchaseOrderVO vo) throws ApplicationException {
        if (files == null || files.length == 0) return;

        try {
            Path folder = Paths.get(localPurchaseOrderUploadPath);
            if (!Files.exists(folder)) Files.createDirectories(folder);

            List<LocalPurchaseOrderAttachmentVO> attachmentList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file == null || file.isEmpty()) continue;

                String originalFileName = file.getOriginalFilename();
                String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
                Path path = Paths.get(localPurchaseOrderUploadPath, uniqueFileName);

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                }

                LocalPurchaseOrderAttachmentVO attachment = new LocalPurchaseOrderAttachmentVO();
                attachment.setLocalPurchaseOrderVO(vo);
                attachment.setName(originalFileName);
                attachment.setFileName(uniqueFileName);
                attachment.setFilePath(path.toString());
                attachment.setFileSize(file.getSize());
                attachment.setUploadOn(LocalDateTime.now());
                attachmentList.add(attachment);
            }
            vo.getLocalPurchaseOrderAttachmentVO().addAll(attachmentList);
        } catch (IOException e) {
            throw new ApplicationException("File Upload Failed : " + e.getMessage());
        }
    }

    @Override
    public LocalPurchaseOrderResponseDTO getLocalPurchaseOrderById(Long id) throws ApplicationException {
        LocalPurchaseOrderVO vo = localPurchaseOrderRepo.getLocalPurchaseOrderById(id);
        if (vo == null) throw new ApplicationException("Local Purchase Order Not Found");
        return buildLocalPurchaseOrderResponse(vo);
    }

    @Override
    public List<LocalPurchaseOrderResponseDTO> getLocalPurchaseOrderByOrgId(Long orgId, Long branchId) throws ApplicationException {
        List<LocalPurchaseOrderVO> list = localPurchaseOrderRepo.getLocalPurchaseOrderByOrgId(orgId, branchId);
        if (list == null || list.isEmpty()) throw new ApplicationException("Local Purchase Order Not Found");
        List<LocalPurchaseOrderResponseDTO> responseList = new ArrayList<>();
        for (LocalPurchaseOrderVO vo : list) responseList.add(buildLocalPurchaseOrderResponse(vo));
        return responseList;
    }

    @Override
    public String getLocalPurchaseOrderDocId(Long orgId, String finYear, Long branch) {
        return localPurchaseOrderRepo.getLocalPurchaseOrderDocId(orgId, SCREEN_CODE_LPO);
    }

    private LocalPurchaseOrderResponseDTO buildLocalPurchaseOrderResponse(LocalPurchaseOrderVO vo) {

        LocalPurchaseOrderResponseDTO dto = new LocalPurchaseOrderResponseDTO();
        dto.setId(vo.getId());

        if (vo.getPlant() != null) {
            BranchResponseDTO plantDTO = new BranchResponseDTO();
            plantDTO.setId(vo.getPlant().getId());
            plantDTO.setBranchCode(vo.getPlant().getBranchCode());
            plantDTO.setBranchName(vo.getPlant().getBranchName());
            dto.setPlant(plantDTO);
        }

        dto.setPoNo(vo.getPoNo());
        dto.setBelongsTo(vo.getBelongsTo());
        dto.setPoDate(vo.getPoDate());
        dto.setDepartment(toLovDTO(vo.getDepartment()));

        if (vo.getSupplier() != null) {
            CustomerResponseDetailsDTO supplierDTO = new CustomerResponseDetailsDTO();
            supplierDTO.setId(vo.getSupplier().getId());
            supplierDTO.setCustomerName(vo.getSupplier().getCustomerName());
            dto.setSupplier(supplierDTO);
        }

        if (vo.getGstState() != null) {
            dto.setGstState(new GSTStateResponseDTO(vo.getGstState().getId(), vo.getGstState().getStateCode(),
                    vo.getGstState().getStateName(), vo.getGstState().getGstStateId()));
        }

        dto.setSupplierRefNo(vo.getSupplierRefNo());
        dto.setAddress(vo.getAddress());
        dto.setIsIgstAppl(vo.getIsIgstAppl());
        dto.setSuppRefDt(vo.getSuppRefDt());
        dto.setGstnNo(vo.getGstnNo());

        dto.setTaxCode(toLovDTO(vo.getTaxCode()));
        dto.setIsReverseChrg(vo.getIsReverseChrg());
        dto.setItemType(vo.getItemType());
        dto.setIndentRequired(vo.getIndentRequired());
        dto.setDealerType(toLovDTO(vo.getDealerType()));

        dto.setFreightType(vo.getFreightType());
        dto.setPackingType(vo.getPackingType());
        dto.setInsurance(vo.getInsurance());
        dto.setFreight(vo.getFreight());
        dto.setTotalAmount(vo.getTotalAmount());
        dto.setModeOfDespatch(vo.getModeOfDespatch());
        dto.setPaymentTerms(vo.getPaymentTerms());
        dto.setDeliveryTerms(vo.getDeliveryTerms());
        dto.setAmountInWords(vo.getAmountInWords());
        dto.setRemarks(vo.getRemarks());
        dto.setNotes(vo.getNotes());

//        if (vo.getPreparedBy() != null) dto.setPreparedBy(new EmployeeResponseDTO(vo.getPreparedBy().getId(), vo.getPreparedBy().getEmployeeName()));
//        if (vo.getCheckedBy() != null) dto.setCheckedBy(new EmployeeResponseDTO(vo.getCheckedBy().getId(), vo.getCheckedBy().getEmployeeName()));
//        if (vo.getAuthorisedBy() != null) dto.setAuthorisedBy(new EmployeeResponseDTO(vo.getAuthorisedBy().getId(), vo.getAuthorisedBy().getEmployeeName()));

        dto.setOrgId(vo.getOrgId());
        dto.setFinancialYear(vo.getFinancialYear());
        dto.setActive(vo.getActive());
        dto.setCancelRemarks(vo.getCancelRemarks());
        dto.setCreatedBy(vo.getCreatedBy());
        dto.setUpdatedBy(vo.getUpdatedBy());

        List<LocalPurchaseOrderDetailsResponseDTO> detailsList = new ArrayList<>();
        if (vo.getLocalPurchaseOrderDetailsVO() != null) {
            for (LocalPurchaseOrderDetailsVO d : vo.getLocalPurchaseOrderDetailsVO()) {

                LocalPurchaseOrderDetailsResponseDTO line = new LocalPurchaseOrderDetailsResponseDTO();
                line.setId(d.getId());

//                if (d.getIndentDetails() != null) {
//                    line.setIndentDetailId(d.getIndentDetails().getId());
//                    if (d.getIndentDetails().getPurchaseIndentVO() != null) {
//                        line.setIndentNo(d.getIndentDetails().getPurchaseIndentVO().getIndentNo());
//                        line.setIndentDate(d.getIndentDetails().getPurchaseIndentVO().getIndentDate());
//                    }
//                }

                if (d.getItem() != null) {
                    ItemMasterResponseDetailsDTO itemDTO = new ItemMasterResponseDetailsDTO();
                    itemDTO.setId(d.getItem().getId());
                    itemDTO.setItemCode(d.getItem().getItemCode());
                    itemDTO.setItemDescription(d.getItem().getItemDescription());
                    line.setItemCode(itemDTO);
                }
                line.setCustomerPartNo(d.getCustomerPartNo());

                if (d.getHsnCode() != null) {
                    HsnResponseImageDTO hsnDTO = new HsnResponseImageDTO();
                    hsnDTO.setId(d.getHsnCode().getId());
                    hsnDTO.setHsnCode(d.getHsnCode().getHsn());
                    line.setHsnCode(hsnDTO);
                }
                line.setTaxType(toLovDTO(d.getTaxType()));
                line.setTaxPercent(d.getTaxPercent());

                if (d.getPurchaseUnit() != null) {
                    PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();
                    unitDTO.setId(d.getPurchaseUnit().getId());
                    unitDTO.setPrimaryUnit(d.getPurchaseUnit().getUnitId());
                    line.setPurchaseUnit(unitDTO);
                }
                if (d.getPrimaryUnit() != null) {
                    PrimaryUnitImageDTO unitDTO = new PrimaryUnitImageDTO();
                    unitDTO.setId(d.getPrimaryUnit().getId());
                    unitDTO.setPrimaryUnit(d.getPrimaryUnit().getUnitId());
                    line.setPrimaryUnit(unitDTO);
                }
                line.setConversionFactor(d.getConversionFactor());

                line.setIndentQty(d.getIndentQty());
                line.setPendingIndentQty(d.getPendingIndentQty());

                line.setPoQtyInPurchaseUnit(d.getPoQtyInPurchaseUnit());
                line.setQtyInPrimaryUnit(d.getQtyInPrimaryUnit());

                line.setRateInInr(d.getRateInInr());
                line.setDiscountPercent(d.getDiscountPercent());
                line.setDiscountAmountInr(d.getDiscountAmountInr());
                line.setAmountInInr(d.getAmountInInr());
                line.setDeliveryDate(d.getDeliveryDate());

                line.setSgstRate(d.getSgstRate());
                line.setSgstAmount(d.getSgstAmount());
                line.setCgstRate(d.getCgstRate());
                line.setCgstAmount(d.getCgstAmount());
                line.setIgstRate(d.getIgstRate());
                line.setIgstAmount(d.getIgstAmount());

                detailsList.add(line);
            }
        }
        dto.setDetails(detailsList);

        List<LocalPurchaseOrderTaxDetailsResponseDTO> taxList = new ArrayList<>();
        if (vo.getLocalPurchaseOrderTaxDetailsVO() != null) {
            for (LocalPurchaseOrderTaxDetailsVO t : vo.getLocalPurchaseOrderTaxDetailsVO()) {
                LocalPurchaseOrderTaxDetailsResponseDTO taxDTO = new LocalPurchaseOrderTaxDetailsResponseDTO();
                taxDTO.setId(t.getId());
                taxDTO.setParticulars(t.getParticulars());
                taxDTO.setTaxPercent(t.getTaxPercent());
                taxDTO.setAmount(t.getAmount());
                taxList.add(taxDTO);
            }
        }
        dto.setTaxDetails(taxList);

        List<LocalPurchaseOrderAttachmentDTO> attachmentList = new ArrayList<>();
        if (vo.getLocalPurchaseOrderAttachmentVO() != null) {
            for (LocalPurchaseOrderAttachmentVO a : vo.getLocalPurchaseOrderAttachmentVO()) {
                LocalPurchaseOrderAttachmentDTO attachDTO = new LocalPurchaseOrderAttachmentDTO();
                attachDTO.setName(a.getName());
                attachDTO.setFileName(a.getFileName());
                attachDTO.setFilePath(a.getFilePath());
                attachDTO.setFileSize(a.getFileSize());
                attachDTO.setUploadOn(a.getUploadOn());
                attachmentList.add(attachDTO);
            }
        }
        dto.setAttachments(attachmentList);

        return dto;
    }
}