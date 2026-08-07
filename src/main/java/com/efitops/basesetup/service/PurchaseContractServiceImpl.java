package com.efitops.basesetup.service;

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

import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseContractDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseContractTaxDetailsResponseDTO;
import com.efitops.basesetup.common.CommonConstant;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;
import com.efitops.basesetup.dto.HsnResponseImageDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;
import com.efitops.basesetup.dto.PurchaseContractAttachmentDTO;
import com.efitops.basesetup.dto.PurchaseContractDTO;
import com.efitops.basesetup.dto.PurchaseContractDetailsDTO;
import com.efitops.basesetup.dto.PurchaseContractTaxDetailsDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractAttachmentVO;
import com.efitops.basesetup.entity.PurchaseContractDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.PurchaseContractAttachmentRepo;
import com.efitops.basesetup.repository.PurchaseContractDetailsRepo;
import com.efitops.basesetup.repository.PurchaseContractRepo;
import com.efitops.basesetup.repository.PurchaseContractTaxDetailsRepo;
import com.efitops.basesetup.repository.TaxDefinitionRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;

@Service
public class PurchaseContractServiceImpl implements PurchaseContractService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseContractServiceImpl.class);

    private static final String SCREEN_CODE = "PC";
    private static final String INDIA = "INDIA";

    @Autowired
    PurchaseContractRepo purchaseContractRepo;

    @Autowired
    PurchaseContractDetailsRepo purchaseContractDetailsRepo;

    @Autowired
    PurchaseContractTaxDetailsRepo purchaseContractTaxDetailsRepo;

    @Autowired
    PurchaseContractAttachmentRepo purchaseContractAttachmentRepo;

    @Autowired
    BranchRepo branchRepo;

    @Autowired
    ListOfValuesDetailsRepo listOfValuesDetailsRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ItemMasterRepo itemMasterRepo;

    @Autowired
    HsnRepo hsnRepo;

    @Autowired
    TaxDefinitionRepo taxDefinitionRepo;

    @Autowired
    UnitMasterRepo unitMasterRepo;

    @Autowired
    DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

    // same style as QuotationServiceImpl's uploadPath, add
    // "purchasecontract.upload.path=<some dir>" to application.properties
    @Value("${purchasecontract.upload.path}")
    private String uploadPath;

    // ==================================================================
    // CREATE / UPDATE
    // ==================================================================
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateCreatePurchaseContract(PurchaseContractDTO dto, MultipartFile[] files)
            throws ApplicationException {

        String methodName = "updateCreatePurchaseContract()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        PurchaseContractVO purchaseContractVO;
        String message;

        // True only when we are updating an existing, already-managed entity
        // (fetched via findById in this same persistence context).
        boolean isUpdate = ObjectUtils.isNotEmpty(dto.getId());

        if (isUpdate) {

            purchaseContractVO = purchaseContractRepo.findById(dto.getId())
                    .orElseThrow(() -> new ApplicationException("Purchase Contract Not Found"));

            purchaseContractVO.setUpdatedBy(dto.getCreatedBy());

            message = "Purchase Contract Updated Successfully";

        } else {

            purchaseContractVO = new PurchaseContractVO();

            purchaseContractVO.setCreatedBy(dto.getCreatedBy());
            purchaseContractVO.setUpdatedBy(dto.getCreatedBy());

            // Contract No -> generated once, on create, exactly like
            // EfitMasterServiceImpl.getDepartmentDocId / getEmployeeByDocId pattern
            String docId = purchaseContractRepo.getPurchaseContractDocId(
                    dto.getOrgId(),
                    SCREEN_CODE
            );
            purchaseContractVO.setContractNo(docId);

            message = "Purchase Contract Created Successfully";
        }

        createUpdatePurchaseContractVOFromDTO(dto, purchaseContractVO);
        // check Duplicate Purchase Contract LINE
        createUpdatePurchaseContractVOFromDTO(dto, purchaseContractVO);

        if (!isUpdate) {
            checkDuplicatePurchaseContract(purchaseContractVO, isUpdate);
        }

        if (isUpdate) {
            purchaseContractRepo.flush();
        } else {
            purchaseContractVO = purchaseContractRepo.save(purchaseContractVO);
        }

        if (isUpdate) {
            deleteExistingAttachments(purchaseContractVO);
        }

        saveAttachments(files, purchaseContractVO, dto.getCreatedBy());

        PurchaseContractResponseDTO responseDTO = buildPurchaseContractResponse(purchaseContractVO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("purchaseContractVO", responseDTO);

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

        return response;
    }

    private void createUpdatePurchaseContractVOFromDTO(PurchaseContractDTO dto, PurchaseContractVO vo)
            throws ApplicationException {

        // Plant ID -> Branch
        if (dto.getBranch() != null && dto.getBranch() != 0) {

            BranchVO branch = branchRepo.findById(dto.getBranch())
                    .orElseThrow(() -> new ApplicationException("Branch Not Found"));

            vo.setPlant(branch);
        }

        vo.setContractDate(dto.getContractDate());

        // Department -> List Of Values Details
        if (dto.getDepartment() != null && dto.getDepartment() != 0) {

            ListOfValuesDetailsVO department = listOfValuesDetailsRepo.findById(dto.getDepartment())
                    .orElseThrow(() -> new ApplicationException("Department Not Found"));

            vo.setDepartment(department);
        }

        // Supplier Code / Supplier Name -> Party (Customer) master
        if (dto.getSupplier() != null && dto.getSupplier() != 0) {

            CustomerVO supplier = customerRepo.findById(dto.getSupplier())
                    .orElseThrow(() -> new ApplicationException("Supplier Not Found"));

            vo.setSupplier(supplier);

            // GST State -> auto pulled from supplier (party master); ignore whatever client sent
            GSTStateMasterVO gstState = supplier.getGstState();
            vo.setGstState(gstState);

            // Is IGST Applicable / P.O Type -> derived from supplier's country (via GST State -> Country)
            boolean isIndia = gstState != null && gstState.getStateName() != null
                    && gstState.getStateName().toUpperCase().contains(INDIA);

            // If supplier stores its own country independent of GST state, prefer that field instead —
            // this checks the Customer's country name when present.
            if (supplier.getCountry() != null && supplier.getCountry().getCountryName() != null) {
                isIndia = supplier.getCountry().getCountryName().toUpperCase().contains(INDIA);
            }

            vo.setIsIgstAppl(!isIndia); // IGST applies for inter-state / import movement, not for local intra-state
            vo.setPoType(isIndia ? "LOCAL" : "IMPORT");
        }

        vo.setSupplierRefNo(dto.getSupplierRefNo());
        vo.setRefDate(dto.getRefDate());
        vo.setValidFrom(dto.getValidFrom());
        vo.setValidTo(dto.getValidTo());

        // -------- Charges Summary --------
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

        // ------------------------------------------------------------------
        // orphanRemoval = true collections (details, tax details) must be
        // mutated IN PLACE (clear() + add()) on the managed entity's own
        // List instance. Never delete the old rows via a repo.deleteAll()
        // call and then reassign the field with a setter — that swaps in a
        // brand-new List, disconnecting Hibernate's managed PersistentBag
        // from its owner, which is what triggers:
        //   "A collection with cascade="all-delete-orphan" was no longer
        //    referenced by the owning entity instance"
        // ------------------------------------------------------------------

        buildDetailsList(dto, vo);
        buildTaxDetailsList(dto, vo);
    }
    private void checkDuplicatePurchaseContract(PurchaseContractVO vo, boolean isUpdate) throws ApplicationException {

        if (vo.getSupplierRefNo() == null || vo.getSupplierRefNo().trim().isEmpty()) {
            return;
        }

        boolean duplicateExists = isUpdate
                ? purchaseContractRepo.existsBySupplierRefNoAndOrgIdAndIdNot(
                vo.getSupplierRefNo(), vo.getOrgId(), vo.getId())
                : purchaseContractRepo.existsBySupplierRefNoAndOrgId(
                vo.getSupplierRefNo(), vo.getOrgId());

        if (duplicateExists) {
            throw new ApplicationException(
                    "Duplicate Purchase Contract: Supplier Ref No '" + vo.getSupplierRefNo()
                            + "' already exists");
        }
    }
    // "1-Contract Details" grid
    private void buildDetailsList(PurchaseContractDTO dto, PurchaseContractVO vo)
            throws ApplicationException {

        vo.getPurchaseContractDetailsVO().clear();

        if (dto.getDetails() == null) {
            return;
        }

        for (PurchaseContractDetailsDTO line : dto.getDetails()) {

            PurchaseContractDetailsVO detailVO = new PurchaseContractDetailsVO();

            // Item Code -> Item Description also comes from this same ItemMaster record
            if (line.getItemId() != null && line.getItemId() != 0) {

                ItemMasterVO item = itemMasterRepo.findById(line.getItemId())
                        .orElseThrow(() -> new ApplicationException("Item Not Found"));

                detailVO.setItem(item);

                // HSN/SAC Code -> auto pulled from item unless the user explicitly picked one
                if (line.getHsnId() != null && line.getHsnId() != 0) {

                    HsnVO hsn = hsnRepo.findById(line.getHsnId())
                            .orElseThrow(() -> new ApplicationException("HSN Not Found"));
                    detailVO.setHsnCode(hsn);

                } else if (item.getHsnCode() != null) {
                    detailVO.setHsnCode(item.getHsnCode());
                }

                // Unit -> auto pulled from item's primary unit unless overridden
                if (line.getUnitId() != null && line.getUnitId() != 0) {

                    UnitMasterVO unit = unitMasterRepo.findById(line.getUnitId())
                            .orElseThrow(() -> new ApplicationException("Unit Not Found"));
                    detailVO.setUnit(unit);

                } else if (item.getPrimaryUnit() != null) {
                    detailVO.setUnit(item.getPrimaryUnit());
                }
            }

            // Tax Type -> List Of Values
            if (line.getTaxType() != null && line.getTaxType() != 0) {

                ListOfValuesDetailsVO taxType = listOfValuesDetailsRepo.findById(line.getTaxType())
                        .orElseThrow(() -> new ApplicationException("Tax Type Not Found"));
                detailVO.setTaxType(taxType);
            }

            // Tax (%) -> auto pulled from TaxDefinition unless the user typed a value directly
            if (line.getTaxDefinition() != null && line.getTaxDefinition() != 0) {

                TaxDefinitionVO taxDefinition = taxDefinitionRepo.findById(line.getTaxDefinition())
                        .orElseThrow(() -> new ApplicationException("Tax Definition Not Found"));
                detailVO.setTaxDefinition(taxDefinition);
            }
            detailVO.setTaxPercent(line.getTaxPercent());

            detailVO.setRateInCurrency(line.getRateInCurrency());

            // SGST / CGST / IGST rates -> entered by user; amounts -> calculated here
            detailVO.setSgstRate(line.getSgstRate());
            detailVO.setSgstAmount(calcAmount(line.getRateInCurrency(), line.getSgstRate()));

            detailVO.setCgstRate(line.getCgstRate());
            detailVO.setCgstAmount(calcAmount(line.getRateInCurrency(), line.getCgstRate()));

            detailVO.setIgstRate(line.getIgstRate());
            detailVO.setIgstAmount(calcAmount(line.getRateInCurrency(), line.getIgstRate()));

            // Valid From / Valid To -> default to header validity when not entered on the line
            detailVO.setValidFrom(line.getValidFrom() != null ? line.getValidFrom() : dto.getValidFrom());
            detailVO.setValidTo(line.getValidTo() != null ? line.getValidTo() : dto.getValidTo());

            // Parent mapping
            detailVO.setPurchaseContractVO(vo);

            vo.getPurchaseContractDetailsVO().add(detailVO);
        }
    }

    private BigDecimal calcAmount(BigDecimal rate, BigDecimal percent) {

        if (rate == null || percent == null) {
            return BigDecimal.ZERO;
        }
        return rate.multiply(percent).divide(BigDecimal.valueOf(100));
    }

    // "2-Tax Details" grid — plain entry, no lookups
    private void buildTaxDetailsList(PurchaseContractDTO dto, PurchaseContractVO vo) {

        vo.getPurchaseContractTaxDetailsVO().clear();

        if (dto.getTaxDetails() == null) {
            return;
        }

        for (PurchaseContractTaxDetailsDTO taxDTO : dto.getTaxDetails()) {

            PurchaseContractTaxDetailsVO taxVO = new PurchaseContractTaxDetailsVO();

            taxVO.setParticulars(taxDTO.getParticulars());
            taxVO.setTaxPercent(taxDTO.getTaxPercent());
            taxVO.setAmount(taxDTO.getAmount());

            // Parent mapping
            taxVO.setPurchaseContractVO(vo);

            vo.getPurchaseContractTaxDetailsVO().add(taxVO);
        }
    }

    // "4-Quotation Attachment" — files saved to disk, exactly like QuotationServiceImpl.saveAttachments
    private void saveAttachments(MultipartFile[] files, PurchaseContractVO vo, Long createdBy)
            throws ApplicationException {

        if (files == null || files.length == 0) {
            return;
        }

        try {

            Path folder = Paths.get(uploadPath);

            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            List<PurchaseContractAttachmentVO> attachmentList = new ArrayList<>();

            for (MultipartFile file : files) {

                if (file == null || file.isEmpty()) {
                    continue;
                }

                String originalFileName = file.getOriginalFilename();

                String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

                Path path = Paths.get(uploadPath, uniqueFileName);

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                }

                PurchaseContractAttachmentVO attachment = new PurchaseContractAttachmentVO();

                // Parent mapping
                attachment.setPurchaseContractVO(vo);

                attachment.setName(originalFileName);
                attachment.setFileName(uniqueFileName);
                attachment.setFilePath(path.toString());
                attachment.setFileSize(file.getSize());
                attachment.setUploadOn(LocalDateTime.now());

                attachmentList.add(attachment);
            }

            // Do not replace Hibernate managed collection
            // because PurchaseContractVO has orphanRemoval = true
            vo.getPurchaseContractAttachmentVO().addAll(attachmentList);

        } catch (IOException e) {

            throw new ApplicationException("File Upload Failed : " + e.getMessage());
        }
    }
    private void deleteExistingAttachments(PurchaseContractVO vo) {

        if (vo.getPurchaseContractAttachmentVO() == null || vo.getPurchaseContractAttachmentVO().isEmpty()) {
            return;
        }

        // snapshot first — we're about to clear/delete the live managed collection
        List<PurchaseContractAttachmentVO> existing = new ArrayList<>(vo.getPurchaseContractAttachmentVO());

        for (PurchaseContractAttachmentVO attachment : existing) {
            try {
                if (attachment.getFilePath() != null) {
                    Files.deleteIfExists(Paths.get(attachment.getFilePath()));
                }
            } catch (IOException e) {
                // don't fail the whole update just because a stale file is missing/locked on disk
                LOGGER.error("Failed to delete attachment file from disk: {} - {}",
                        attachment.getFilePath(), e.getMessage());
            }
        }

        purchaseContractAttachmentRepo.deleteAll(existing);
        purchaseContractAttachmentRepo.flush();

        vo.getPurchaseContractAttachmentVO().clear();
    }
    // ==================================================================
    // READ
    // ==================================================================
    @Override
    public PurchaseContractResponseDTO getPurchaseContractById(Long id) throws ApplicationException {

        PurchaseContractVO vo = purchaseContractRepo.getPurchaseContractById(id);

        if (vo == null) {
            throw new ApplicationException("Purchase Contract Not Found");
        }

        return buildPurchaseContractResponse(vo);
    }

    @Override
    public List<PurchaseContractResponseDTO> getPurchaseContractByOrgId(Long orgId, Long branchId)
            throws ApplicationException {

        List<PurchaseContractVO> list = purchaseContractRepo.getPurchaseContractByOrgId(orgId, branchId);

        if (list == null || list.isEmpty()) {
            throw new ApplicationException("Purchase Contract Not Found");
        }

        List<PurchaseContractResponseDTO> responseList = new ArrayList<>();

        for (PurchaseContractVO vo : list) {
            responseList.add(buildPurchaseContractResponse(vo));
        }

        return responseList;
    }

    @Override
    public String getPurchaseContractDocId(Long orgId, String finYear, Long branch) {
        return purchaseContractRepo.getPurchaseContractDocId(orgId, SCREEN_CODE);
    }

    // ==================================================================
    // VO -> ResponseDTO
    // ==================================================================
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
            dto.setGstState(new com.efitops.basesetup.ResponseDTO.GSTStateResponseDTO(vo.getGstState().getId(),
                    vo.getGstState().getStateCode(), vo.getGstState().getStateName(),
                    vo.getGstState().getGstStateId()));
        }

        dto.setValidFrom(vo.getValidFrom());
        dto.setValidTo(vo.getValidTo());
        dto.setIsIgstAppl(vo.getIsIgstAppl());
        dto.setPoType(vo.getPoType());

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

                if (d.getTaxDefinition() != null) {
                    line.setTaxName(d.getTaxDefinition().getTaxDescription());
                }
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
                attachDTO.setId(a.getId());
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