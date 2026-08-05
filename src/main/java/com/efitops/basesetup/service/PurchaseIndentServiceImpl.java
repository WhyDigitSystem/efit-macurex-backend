package com.efitops.basesetup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.PurchaseIndentAttachmentResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseIndentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.PurchaseIndentResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.dto.PurchaseIndentDTO;
import com.efitops.basesetup.dto.PurchaseIndentDetailsDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DepartmentVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;
import com.efitops.basesetup.entity.ListOfValuesDetailsVO;
import com.efitops.basesetup.entity.PurchaseIndentAttachmentVO;
import com.efitops.basesetup.entity.PurchaseIndentDetailsVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.DepartmentRepo;
import com.efitops.basesetup.repository.EmployeeMasterRepo;
import com.efitops.basesetup.repository.ItemMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesDetailsRepo;
import com.efitops.basesetup.repository.PurchaseIndentAttachmentRepo;
import com.efitops.basesetup.repository.PurchaseIndentDetailsRepo;
import com.efitops.basesetup.repository.PurchaseIndentRepo;

@Service
public class PurchaseIndentServiceImpl implements PurchaseIndentService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PurchaseIndentServiceImpl.class);

    @Autowired
    private PurchaseIndentRepo purchaseIndentRepo;

    @Autowired
    private PurchaseIndentDetailsRepo purchaseIndentDetailsRepo;

    @Autowired
    private PurchaseIndentAttachmentRepo purchaseIndentAttachmentRepo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmployeeMasterRepo employeeMasterRepo;

    @Autowired
    private ListOfValuesDetailsRepo listOfValuesDetailsRepo;

    @Autowired
    private ItemMasterRepo itemMasterRepo;

    // Used only to build the full item payload for the response -
    // same method the item master screen itself uses.
    @Autowired
    private ItemMasterService itemMasterService;

    @Value("${purchaseindent.upload.path}")
    private String uploadPath;

    // ================== Purchase Indent ==================

    @Override
    @Transactional
    public Map<String, Object> createUpdatePurchaseIndent(PurchaseIndentDTO dto, MultipartFile[] files)
            throws ApplicationException {

        PurchaseIndentVO purchaseIndentVO;
        String message;

        // True only when we are updating an existing, already-managed entity
        // (fetched via findById in this same persistence context).
        //
        // NOTE: ObjectUtils.isNotEmpty(Long) is NOT safe here - isEmpty() only
        // special-cases CharSequence/Collection/Map/array types, so a Long
        // value of 0 is never treated as "empty". If the caller ever sends
        // id = 0 (common DTO/JSON default) for a NEW record, isNotEmpty()
        // still returns true and this would incorrectly take the update path
        // (findById(0) failing, or worse, silently colliding). Explicitly
        // check for both null and 0 so only a real, existing parent id
        // triggers the update branch; anything else (null or 0) is a create.
        boolean isUpdate = dto.getId() != null && dto.getId() != 0;

        if (isUpdate) {

            purchaseIndentVO = purchaseIndentRepo.findById(dto.getId())
                    .orElseThrow(() -> new ApplicationException("Invalid Purchase Indent Details"));

            // ----------------------------------------------------------------
            // Duplicate check (update): only re-validate if the caller actually
            // changed indentNo. AndIdNot excludes the current record itself so
            // it doesn't collide with its own unchanged value.
            // ----------------------------------------------------------------
            if (dto.getIndentNo() != null && !dto.getIndentNo().equalsIgnoreCase(purchaseIndentVO.getIndentNo())) {

                if (purchaseIndentRepo.existsByIndentNoAndOrgIdAndIdNot(dto.getIndentNo(), dto.getOrgId(),
                        dto.getId())) {

                    throw new ApplicationException(
                            "The Indent No : " + dto.getIndentNo() + " already exists in this Organization.");
                }

                purchaseIndentVO.setIndentNo(dto.getIndentNo());
            }

            purchaseIndentVO.setUpdatedBy(dto.getCreatedBy());

            message = "Purchase Indent Updated Successfully";

        } else {

            // ----------------------------------------------------------------
            // Duplicate check (create): if the caller supplied an indentNo,
            // honor it after validating uniqueness; otherwise fall back to the
            // existing auto-generation logic.
            // ----------------------------------------------------------------
            if (dto.getIndentNo() != null && !dto.getIndentNo().trim().isEmpty()) {

                if (purchaseIndentRepo.existsByIndentNoAndOrgId(dto.getIndentNo(), dto.getOrgId())) {

                    throw new ApplicationException(
                            "The Indent No : " + dto.getIndentNo() + " already exists in this Organization.");
                }

                purchaseIndentVO = new PurchaseIndentVO();
                purchaseIndentVO.setIndentNo(dto.getIndentNo());

            } else {

                purchaseIndentVO = new PurchaseIndentVO();
                purchaseIndentVO.setIndentNo(generateIndentNo(dto.getOrgId()));
            }

            purchaseIndentVO.setCreatedBy(dto.getCreatedBy());
            purchaseIndentVO.setUpdatedBy(dto.getCreatedBy());

            message = "Purchase Indent Created Successfully";
        }

        createUpdatePurchaseIndentVO(dto, purchaseIndentVO);

        // ------------------------------------------------------------------
        // IMPORTANT: purchaseIndentRepo.save(...) internally does:
        //   isNew(entity) ? entityManager.persist(entity) : entityManager.merge(entity)
        // Since an UPDATE entity already has a non-null id, save() would call
        // merge() even though purchaseIndentVO is ALREADY the managed instance
        // in this persistence context. Calling merge() on an entity that owns
        // a live, just-mutated orphanRemoval=true collection (details) is what
        // triggers:
        //   "A collection with cascade="all-delete-orphan" was no longer
        //    referenced by the owning entity instance"
        // - regardless of whether the collection was cleared+repopulated in
        // place or reassigned to a new List.
        //
        // Fix: only call save() (-> persist()) for genuinely new/transient
        // entities. For updates, the entity is already attached; just flush
        // the persistence context so generated child IDs are available for
        // the response, without ever invoking merge().
        // ------------------------------------------------------------------

        PurchaseIndentVO savedVO;

        if (isUpdate) {
            purchaseIndentRepo.flush();
            savedVO = purchaseIndentVO;
        } else {
            savedVO = purchaseIndentRepo.save(purchaseIndentVO);
        }

        // Save Attachments (multipart)
        saveAttachments(files, savedVO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("purchaseIndentVO", buildPurchaseIndentResponse(savedVO));

        return response;
    }

    private String generateIndentNo(Long orgId) {
        long count = purchaseIndentRepo.countByOrgId(orgId);
        return "PI" + String.format("%06d", count + 1);
    }

    private void createUpdatePurchaseIndentVO(PurchaseIndentDTO dto, PurchaseIndentVO purchaseIndentVO)
            throws ApplicationException {

        purchaseIndentVO.setIndentDate(dto.getIndentDate());
        purchaseIndentVO.setApproved(dto.isApproved());
        purchaseIndentVO.setRemarks(dto.getRemarks()); // Indent Summary - direct field
        purchaseIndentVO.setOrgId(dto.getOrgId());
        purchaseIndentVO.setActive(dto.isActive());
        purchaseIndentVO.setCancelRemarks(dto.getCancelRemarks());

        // Plant -> Branch
        if (dto.getPlant() != null && dto.getPlant() != 0) {

            BranchVO branch = branchRepo.findById(dto.getPlant())
                    .orElseThrow(() -> new ApplicationException("Plant (Branch) Not Found"));

            purchaseIndentVO.setPlant(branch);
        }

        // Belongs To -> ListOfValuesDetails
        if (dto.getBelongsTo() != null && dto.getBelongsTo() != 0) {

            ListOfValuesDetailsVO belongsTo = listOfValuesDetailsRepo.findById(dto.getBelongsTo())
                    .orElseThrow(() -> new ApplicationException("Belongs To Not Found"));

            purchaseIndentVO.setBelongsTo(belongsTo);
        }

        // Department
        if (dto.getDepartment() != null && dto.getDepartment() != 0) {

            DepartmentVO department = departmentRepo.findById(dto.getDepartment())
                    .orElseThrow(() -> new ApplicationException("Department Not Found"));

            purchaseIndentVO.setDepartment(department);
        }

        // Prepared By -> Employee
        EmployeeMasterVO preparedBy = null;

        if (dto.getPreparedBy() != null) {

            preparedBy = employeeMasterRepo.findById(dto.getPreparedBy())
                    .orElseThrow(() -> new ApplicationException("Prepared By Not Found"));
        }

        purchaseIndentVO.setPreparedBy(preparedBy);

        // By Whom -> Employee
        EmployeeMasterVO byWhom = null;

        if (dto.getByWhom() != null) {

            byWhom = employeeMasterRepo.findById(dto.getByWhom())
                    .orElseThrow(() -> new ApplicationException("By Whom Not Found"));
        }

        purchaseIndentVO.setByWhom(byWhom);

        // ------------------------------------------------------------------
        // Details Grid
        //
        // orphanRemoval = true collections must be mutated IN PLACE
        // (clear() + add()) on the managed entity's own List instance.
        // Never reassign the field with setDetails(newList) here, and never
        // manually deleteAll() the old rows via the repo - clear() already
        // marks them as orphans and Hibernate deletes them on flush.
        // ------------------------------------------------------------------

        purchaseIndentVO.getDetails().clear();

        if (dto.getDetails() != null) {

            for (PurchaseIndentDetailsDTO detailDTO : dto.getDetails()) {

                PurchaseIndentDetailsVO detailVO = new PurchaseIndentDetailsVO();

                // Item -> full ItemMasterVO reference; itemCode / description /
                // primaryUnit / purchaseUnit all come from this relation.
                if (detailDTO.getItem() != null && detailDTO.getItem() != 0) {

                    ItemMasterVO itemVO = itemMasterRepo.findById(detailDTO.getItem())
                            .orElseThrow(() -> new ApplicationException("Item Not Found"));

                    detailVO.setItem(itemVO);
                }

                detailVO.setQtyInPrimaryUnit(detailDTO.getQtyInPrimaryUnit());
                detailVO.setConversionFactor(detailDTO.getConversionFactor());
                detailVO.setQtyInPurchaseUnit(detailDTO.getQtyInPurchaseUnit());
                detailVO.setRequiredDate(detailDTO.getRequiredDate());
                detailVO.setPurpose(detailDTO.getPurpose());

                // Parent Mapping
                detailVO.setPurchaseIndentVO(purchaseIndentVO);

                purchaseIndentVO.getDetails().add(detailVO);
            }
        }
    }

    // ================== Attachments (Quotation-style) ==================

    private void saveAttachments(MultipartFile[] files, PurchaseIndentVO purchaseIndentVO)
            throws ApplicationException {

        if (files == null || files.length == 0) {
            return;
        }

        try {

            File folder = new File(uploadPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            List<PurchaseIndentAttachmentVO> attachmentList = new ArrayList<>();

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

                PurchaseIndentAttachmentVO attachment = new PurchaseIndentAttachmentVO();

                attachment.setPurchaseIndentVO(purchaseIndentVO);
                attachment.setName(originalFileName);
                attachment.setFileName(uniqueFileName);
                attachment.setFilePath(path.toString());
                attachment.setFileSize(file.getSize());
                attachment.setUploadOn(LocalDateTime.now());

                attachmentList.add(attachment);
            }

            List<PurchaseIndentAttachmentVO> savedAttachments = purchaseIndentAttachmentRepo.saveAll(attachmentList);

            // NOTE: append to the existing in-memory collection rather than
            // reassigning, so previously uploaded attachments (from earlier
            // create/update calls) are not lost when this response is built.
            if (purchaseIndentVO.getAttachments() == null) {
                purchaseIndentVO.setAttachments(new ArrayList<>());
            }

            purchaseIndentVO.getAttachments().addAll(savedAttachments);

        } catch (IOException e) {

            throw new ApplicationException("File Upload Failed : " + e.getMessage());
        }
    }

    // ================== Response Builder ==================

    private PurchaseIndentResponseDTO buildPurchaseIndentResponse(PurchaseIndentVO vo) {

        PurchaseIndentResponseDTO dto = new PurchaseIndentResponseDTO();

        dto.setId(vo.getId());
        dto.setIndentNo(vo.getIndentNo());
        dto.setIndentDate(vo.getIndentDate());
        dto.setApproved(vo.isApproved());
        dto.setRemarks(vo.getRemarks());
        dto.setOrgId(vo.getOrgId());
        dto.setCreatedBy(vo.getCreatedBy());
        dto.setUpdatedBy(vo.getUpdatedBy());
        dto.setActive(vo.getActiveStr());
        dto.setCancelRemarks(vo.getCancelRemarks());

        if (vo.getPlant() != null) {
            dto.setPlant(new BranchResponseDTO(vo.getPlant().getId(), vo.getPlant().getBranchCode(),
                    vo.getPlant().getBranchName()));
        }

        if (vo.getBelongsTo() != null) {
            dto.setBelongsTo(new ListOfVlauesDetailsResponseDTO(vo.getBelongsTo().getId(),
                    vo.getBelongsTo().getValueCode(), vo.getBelongsTo().getValueDescription()));
        }

        if (vo.getDepartment() != null) {
            DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();
            departmentDTO.setId(vo.getDepartment().getId());
            departmentDTO.setDepartmentCode(vo.getDepartment().getDepartmentCode());
            departmentDTO.setDepartmentName(vo.getDepartment().getDepartmentName());
            dto.setDepartment(departmentDTO);
        }

        if (vo.getPreparedBy() != null) {
            dto.setPreparedBy(new EmployeeResponseDTO(vo.getPreparedBy().getId(), vo.getPreparedBy().getEmployeeName()));
        }

        if (vo.getByWhom() != null) {
            dto.setByWhom(new EmployeeResponseDTO(vo.getByWhom().getId(), vo.getByWhom().getEmployeeName()));
        }

        // ---------- Item Details ----------
        List<PurchaseIndentDetailsResponseDTO> detailList = new ArrayList<>();

        if (vo.getDetails() != null) {

            for (PurchaseIndentDetailsVO detailVO : vo.getDetails()) {

                PurchaseIndentDetailsResponseDTO detailDTO = new PurchaseIndentDetailsResponseDTO();

                detailDTO.setId(detailVO.getId());

                if (detailVO.getItem() != null) {

                    // Full item master payload, purchase unit included automatically
                    // (exactly what /getItemMasterById returns)
                    try {
                        ItemMasterResponseDTO itemDTO = itemMasterService.getItemMasterById(detailVO.getItem().getId());
                        detailDTO.setItemMasterVO(itemDTO);
                    } catch (ApplicationException e) {
                        LOGGER.error("Unable to load item master for id {} : {}", detailVO.getItem().getId(),
                                e.getMessage());
                    }
                }

                detailDTO.setQtyInPrimaryUnit(detailVO.getQtyInPrimaryUnit());
                detailDTO.setConversionFactor(detailVO.getConversionFactor());
                detailDTO.setQtyInPurchaseUnit(detailVO.getQtyInPurchaseUnit());
                detailDTO.setRequiredDate(detailVO.getRequiredDate());
                detailDTO.setPurpose(detailVO.getPurpose());

                detailList.add(detailDTO);
            }
        }

        dto.setDetails(detailList);

        // ---------- Attachments ----------
        List<PurchaseIndentAttachmentResponseDTO> attachmentList = new ArrayList<>();

        if (vo.getAttachments() != null) {

            for (PurchaseIndentAttachmentVO attachmentVO : vo.getAttachments()) {

                PurchaseIndentAttachmentResponseDTO attachmentDTO = new PurchaseIndentAttachmentResponseDTO();

                attachmentDTO.setId(attachmentVO.getId());
                attachmentDTO.setName(attachmentVO.getName());
                attachmentDTO.setFileName(attachmentVO.getFileName());
                attachmentDTO.setFilePath(attachmentVO.getFilePath());
                attachmentDTO.setFileSize(attachmentVO.getFileSize());
                attachmentDTO.setUploadOn(attachmentVO.getUploadOn());

                attachmentList.add(attachmentDTO);
            }
        }

        dto.setAttachments(attachmentList);

        return dto;
    }

    @Override
    public PurchaseIndentResponseDTO getPurchaseIndentById(Long id) throws ApplicationException {

        PurchaseIndentVO vo = purchaseIndentRepo.findById(id)
                .orElseThrow(() -> new ApplicationException("Invalid Purchase Indent Details"));

        return buildPurchaseIndentResponse(vo);
    }

    @Override
    public List<PurchaseIndentResponseDTO> getPurchaseIndentByOrgId(Long orgId, Long branch)
            throws ApplicationException {

        List<PurchaseIndentVO> voList = purchaseIndentRepo.findByOrgIdAndPlant_Id(orgId, branch);

        if (voList.isEmpty()) {
            throw new ApplicationException("No Purchase Indent Details Found");
        }

        List<PurchaseIndentResponseDTO> responseList = new ArrayList<>();

        for (PurchaseIndentVO vo : voList) {
            responseList.add(buildPurchaseIndentResponse(vo));
        }

        return responseList;
    }
}