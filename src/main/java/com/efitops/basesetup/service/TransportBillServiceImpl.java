package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.efitops.basesetup.dto.BranchResponseDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.ResponseDTO.MappingBranchResponseDTO;
import com.efitops.basesetup.ResponseDTO.DocumentTypeResponseDTO;
import com.efitops.basesetup.ResponseDTO.TransportBillPaymentDetails2ResponseDTO;
import com.efitops.basesetup.ResponseDTO.TransportBillPaymentDetailsResponseDTO;
import com.efitops.basesetup.ResponseDTO.TransportBillResponseDTO;
import com.efitops.basesetup.ResponseDTO.TransportResponseDTO;
import com.efitops.basesetup.dto.TransportBillDTO;
import com.efitops.basesetup.dto.TransportBillPaymentDetails2DTO;
import com.efitops.basesetup.dto.TransportBillPaymentDetailsDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DocumentTypeMasterVO;
import com.efitops.basesetup.entity.TransportBillPaymentDetails2VO;
import com.efitops.basesetup.entity.TransportBillPaymentDetailsVO;
import com.efitops.basesetup.entity.TransportBillVO;
import com.efitops.basesetup.entity.TransportMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.DocumentTypeMasterRepo;
import com.efitops.basesetup.repository.TransportBillPaymentDetails2Repo;
import com.efitops.basesetup.repository.TransportBillPaymentDetailsRepo;
import com.efitops.basesetup.repository.TransportBillRepo;
import com.efitops.basesetup.repository.TransportRepo;

@Service
public class TransportBillServiceImpl implements TransportBillService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TransportBillServiceImpl.class);

    @Autowired
    private TransportBillRepo transportBillRepo;

    @Autowired
    private TransportBillPaymentDetailsRepo transportBillPaymentDetailsRepo;

    @Autowired
    private TransportBillPaymentDetails2Repo transportBillPaymentDetails2Repo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private DocumentTypeMasterRepo documentTypeMasterRepo;

    @Autowired
    private TransportRepo transportRepo;

    // ================== Transport Bill ==================

    @Override
    @Transactional
    public Map<String, Object> updateCreateTransportBill(TransportBillDTO dto) throws ApplicationException {

        TransportBillVO transportBillVO;
        String message;

        // True only when we are updating an existing, already-managed entity
        // (fetched via findById in this same persistence context).
        boolean isUpdate = ObjectUtils.isNotEmpty(dto.getId());

        if (isUpdate) {

            transportBillVO = transportBillRepo.findById(dto.getId())
                    .orElseThrow(() -> new ApplicationException("Invalid Transport Bill Details"));

            if (dto.getBillNo() != null && !dto.getBillNo().equalsIgnoreCase(transportBillVO.getBillNo())) {

                if (transportBillRepo.existsByBillNoAndOrgIdAndIdNot(dto.getBillNo(), dto.getOrgId(), dto.getId())) {

                    throw new ApplicationException(
                            "The Bill No : " + dto.getBillNo() + " already exists in this Organization.");
                }
            }

            if (dto.getDocNo() != null && !dto.getDocNo().equalsIgnoreCase(transportBillVO.getDocNo())) {

                if (transportBillRepo.existsByDocNoAndOrgIdAndIdNot(dto.getDocNo(), dto.getOrgId(), dto.getId())) {

                    throw new ApplicationException(
                            "The Doc No : " + dto.getDocNo() + " already exists in this Organization.");
                }
            }

            transportBillVO.setUpdatedBy(dto.getCreatedBy());

            message = "Transport Bill Updated Successfully";

        } else {

            if (dto.getBillNo() != null && transportBillRepo.existsByBillNoAndOrgId(dto.getBillNo(), dto.getOrgId())) {

                throw new ApplicationException(
                        "The Bill No : " + dto.getBillNo() + " already exists in this Organization.");
            }

            if (dto.getDocNo() != null && transportBillRepo.existsByDocNoAndOrgId(dto.getDocNo(), dto.getOrgId())) {

                throw new ApplicationException(
                        "The Doc No : " + dto.getDocNo() + " already exists in this Organization.");
            }

            transportBillVO = new TransportBillVO();
            transportBillVO.setCreatedBy(dto.getCreatedBy());
            transportBillVO.setUpdatedBy(dto.getCreatedBy());

            message = "Transport Bill Created Successfully";
        }

        createUpdateTransportBillVO(dto, transportBillVO);

        // ------------------------------------------------------------------
        // IMPORTANT: transportBillRepo.save(...) internally does:
        //   isNew(entity) ? entityManager.persist(entity) : entityManager.merge(entity)
        // Since an UPDATE entity already has a non-null id, save() would call
        // merge() even though transportBillVO is ALREADY the managed instance
        // in this persistence context. Calling merge() on an entity that owns
        // a live, just-mutated orphanRemoval=true collection is what triggers:
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

        TransportBillVO savedVO;

        if (isUpdate) {
            transportBillRepo.flush();
            savedVO = transportBillVO;
        } else {
            savedVO = transportBillRepo.save(transportBillVO);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("transportBillVO", buildTransportBillResponse(savedVO));

        return response;
    }

    private void createUpdateTransportBillVO(TransportBillDTO dto, TransportBillVO transportBillVO)
            throws ApplicationException {

        transportBillVO.setDocNo(dto.getDocNo());
        transportBillVO.setDocDate(dto.getDocDate());
        transportBillVO.setBillNo(dto.getBillNo());
        transportBillVO.setBillDate(dto.getBillDate());
        transportBillVO.setTotalAmount(dto.getTotalAmount());
        transportBillVO.setBillReceivedDate(dto.getBillReceivedDate());
        transportBillVO.setAccReceivedDate(dto.getAccReceivedDate());
        transportBillVO.setReceivedBy(dto.getReceivedBy());
        transportBillVO.setAccReceivedBy(dto.getAccReceivedBy());
        transportBillVO.setOrgId(dto.getOrgId());
        transportBillVO.setActive(dto.isActive());
        transportBillVO.setCancelRemarks(dto.getCancelRemarks());

        // Plant ID -> Branch
        if (dto.getBranch() != null && dto.getBranch() != 0) {

            BranchVO branch = branchRepo.findById(dto.getBranch())
                    .orElseThrow(() -> new ApplicationException("Plant (Branch) Not Found"));

            transportBillVO.setPlant(branch);
        }

        // Doc. No -> Document Type Master
        if (dto.getDocumentType() != null && dto.getDocumentType() != 0) {

            DocumentTypeMasterVO documentType = documentTypeMasterRepo.findById(dto.getDocumentType())
                    .orElseThrow(() -> new ApplicationException("Document Type Not Found"));

            transportBillVO.setDocumentType(documentType);
        }

        // Transport Name -> Transport Master
        if (dto.getTransportName() != null && dto.getTransportName() != 0) {

            TransportMasterVO transportMasterVO = transportRepo.findById(dto.getTransportName())
                    .orElseThrow(() -> new ApplicationException("Transport Not Found"));

            transportBillVO.setTransportName(transportMasterVO);
        }

        // ------------------------------------------------------------------
        // Payment Details Grid 1 / Grid 2
        //
        // orphanRemoval = true collections must be mutated IN PLACE
        // (clear() + add()) on the managed entity's own List instance.
        // Never reassign the field with setPaymentDetailsX(newList) here.
        // ------------------------------------------------------------------

        // Payment Details Grid 1
        transportBillVO.getPaymentDetails1().clear();

        if (dto.getPaymentDetails1() != null) {

            for (TransportBillPaymentDetailsDTO detailDTO : dto.getPaymentDetails1()) {

                TransportBillPaymentDetailsVO detailVO = new TransportBillPaymentDetailsVO();

                detailVO.setChequeRtgsNo(detailDTO.getChequeRtgsNo());
                detailVO.setChequeDate(detailDTO.getChequeDate());
                detailVO.setTotalAmount(detailDTO.getTotalAmount());
                detailVO.setPaidAmount(detailDTO.getPaidAmount());

                if (detailDTO.getTotalAmount() != null && detailDTO.getPaidAmount() != null) {
                    detailVO.setPendingAmount(
                            detailDTO.getTotalAmount().subtract(detailDTO.getPaidAmount()));
                } else {
                    detailVO.setPendingAmount(detailDTO.getPendingAmount());
                }

                // Parent Mapping
                detailVO.setTransportBillVO(transportBillVO);

                transportBillVO.getPaymentDetails1().add(detailVO);
            }
        }

        // Payment Details Grid 2
        transportBillVO.getPaymentDetails2().clear();

        if (dto.getPaymentDetails2() != null) {

            for (TransportBillPaymentDetails2DTO detailDTO : dto.getPaymentDetails2()) {

                TransportBillPaymentDetails2VO detailVO = new TransportBillPaymentDetails2VO();

                detailVO.setChequeRtgsNo(detailDTO.getChequeRtgsNo());
                detailVO.setChequeDate(detailDTO.getChequeDate());
                detailVO.setPendingAmount(detailDTO.getPendingAmount());
                detailVO.setPaidAmount(detailDTO.getPaidAmount());
                detailVO.setTotalPaidAmount(detailDTO.getTotalPaidAmount());

                // Parent Mapping
                detailVO.setTransportBillVO(transportBillVO);

                transportBillVO.getPaymentDetails2().add(detailVO);
            }
        }
    }

    private TransportBillResponseDTO buildTransportBillResponse(TransportBillVO vo) {

        TransportBillResponseDTO dto = new TransportBillResponseDTO();

        dto.setId(vo.getId());
        dto.setDocNo(vo.getDocNo());
        dto.setDocDate(vo.getDocDate());
        dto.setBillNo(vo.getBillNo());
        dto.setBillDate(vo.getBillDate());
        dto.setTotalAmount(vo.getTotalAmount());
        dto.setBillReceivedDate(vo.getBillReceivedDate());
        dto.setAccReceivedDate(vo.getAccReceivedDate());
        dto.setReceivedBy(vo.getReceivedBy());
        dto.setAccReceivedBy(vo.getAccReceivedBy());
        dto.setOrgId(vo.getOrgId());
        dto.setActive(vo.getActive());
        dto.setCancelRemarks(vo.getCancelRemarks());
        dto.setCreatedBy(vo.getCreatedBy());
        dto.setUpdatedBy(vo.getUpdatedBy());

        if (vo.getPlant() != null) {
            dto.setPlant(new BranchResponseDTO(vo.getPlant().getId(), vo.getPlant().getBranchCode(),
                    vo.getPlant().getBranchName()));
        }

        if (vo.getDocumentType() != null) {
            dto.setDocumentType(new DocumentTypeResponseDTO(vo.getDocumentType().getId(),
                    vo.getDocumentType().getCode(), vo.getDocumentType().getName()));
        }

        if (vo.getTransportName() != null) {
            dto.setTransportName(
                    new TransportResponseDTO(vo.getTransportName().getId(), vo.getTransportName().getTransportName()));
        }

        List<TransportBillPaymentDetailsResponseDTO> details1 = new ArrayList<>();

        if (vo.getPaymentDetails1() != null) {

            for (TransportBillPaymentDetailsVO detailVO : vo.getPaymentDetails1()) {

                TransportBillPaymentDetailsResponseDTO detailDTO = new TransportBillPaymentDetailsResponseDTO();

                detailDTO.setId(detailVO.getId());
                detailDTO.setChequeRtgsNo(detailVO.getChequeRtgsNo());
                detailDTO.setChequeDate(detailVO.getChequeDate());
                detailDTO.setTotalAmount(detailVO.getTotalAmount());
                detailDTO.setPaidAmount(detailVO.getPaidAmount());
                detailDTO.setPendingAmount(detailVO.getPendingAmount());

                details1.add(detailDTO);
            }
        }

        dto.setPaymentDetails1(details1);

        List<TransportBillPaymentDetails2ResponseDTO> details2 = new ArrayList<>();

        if (vo.getPaymentDetails2() != null) {

            for (TransportBillPaymentDetails2VO detailVO : vo.getPaymentDetails2()) {

                TransportBillPaymentDetails2ResponseDTO detailDTO = new TransportBillPaymentDetails2ResponseDTO();

                detailDTO.setId(detailVO.getId());
                detailDTO.setChequeRtgsNo(detailVO.getChequeRtgsNo());
                detailDTO.setChequeDate(detailVO.getChequeDate());
                detailDTO.setPendingAmount(detailVO.getPendingAmount());
                detailDTO.setPaidAmount(detailVO.getPaidAmount());
                detailDTO.setTotalPaidAmount(detailVO.getTotalPaidAmount());

                details2.add(detailDTO);
            }
        }

        dto.setPaymentDetails2(details2);

        return dto;
    }

    @Override
    public TransportBillResponseDTO getTransportBillById(Long id) throws ApplicationException {

        TransportBillVO vo = transportBillRepo.findById(id)
                .orElseThrow(() -> new ApplicationException("Invalid Transport Bill Details"));

        return buildTransportBillResponse(vo);
    }

    @Override
    public List<TransportBillResponseDTO> getTransportBillByOrgId(Long orgId, Long branch)
            throws ApplicationException {

        List<TransportBillVO> voList = transportBillRepo.findByOrgIdAndPlant_Id(orgId, branch);

        if (voList.isEmpty()) {
            throw new ApplicationException("No Transport Bill Details Found");
        }

        List<TransportBillResponseDTO> responseList = new ArrayList<>();

        for (TransportBillVO vo : voList) {
            responseList.add(buildTransportBillResponse(vo));
        }

        return responseList;
    }
}