package com.efitops.basesetup.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.efitops.basesetup.dto.DocumentTypeMappingDTO;
import com.efitops.basesetup.dto.DocumentTypeMappingDetailsDTO;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingRepo;

import com.efitops.basesetup.repository.FinancialYearRepo;


@Service
public class DevelopServiceImpl implements DevelopService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopServiceImpl.class);
	

	@Autowired
	FinancialYearRepo financialYearRepo;

    @Autowired
    private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
    
    @Autowired
    private DocumentTypeMappingRepo documentTypeMappingRepo;
    
	@Autowired
	BranchRepo branchRepo;

@PersistenceContext
private EntityManager entityManager;


    
    //documenttypemapping
    
    
    @Override
    @Transactional
    public Map<String, Object> updateCreateDocumentTypeMapping(
    		DocumentTypeMappingDTO documentTypeMappingDTO)
            throws ApplicationException {

        Map<String, Object> response = new HashMap<>();
        String message = "";

        DocumentTypeMappingVO masterVO;

        BranchVO branchVO = branchRepo.findById(documentTypeMappingDTO.getBranch())
                .orElseThrow(() -> new ApplicationException(
                        "Branch not found with id : " + documentTypeMappingDTO.getBranch()));

        FinancialYearVO financialYearVO = financialYearRepo.findById(documentTypeMappingDTO.getFinancialYear())
                .orElseThrow(() -> new ApplicationException(
                        "Financial Year not found with id : " + documentTypeMappingDTO.getFinancialYear()));

        if (ObjectUtils.isEmpty(documentTypeMappingDTO.getId())) {

        	if (documentTypeMappingRepo.existsByBranch_IdAndFinancialYear_IdAndOrgId(
        	        documentTypeMappingDTO.getBranch(),
        	        documentTypeMappingDTO.getFinancialYear(),
        	        documentTypeMappingDTO.getOrgId())) {

                throw new ApplicationException("Document Type Mapping already exists.");
            }

            masterVO = new DocumentTypeMappingVO();

            masterVO.setCreatedBy(documentTypeMappingDTO.getCreatedBy());

            message = "Document Type Mapping Created Successfully";

        } else {

            masterVO = documentTypeMappingRepo.findById(documentTypeMappingDTO.getId())
                    .orElseThrow(() -> new ApplicationException("Document Type Mapping not found"));

            masterVO.setUpdatedBy(documentTypeMappingDTO.getCreatedBy());

            message = "Document Type Mapping Updated Successfully";
        }

        masterVO.setBranch(branchVO);
        masterVO.setFinancialYear(financialYearVO);
        masterVO.setOrgId(documentTypeMappingDTO.getOrgId());
        masterVO.setActive(documentTypeMappingDTO.isActive());
        masterVO.setCancelRemarks(documentTypeMappingDTO.getCancelRemarks());

        List<DocumentTypeMappingDetailsVO> details = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(documentTypeMappingDTO.getDetails())) {

            for (DocumentTypeMappingDetailsDTO dto : documentTypeMappingDTO.getDetails()) {

                DocumentTypeMappingDetailsVO detailVO;

                if (ObjectUtils.isEmpty(dto.getId()) || dto.getId() == 0) {

                    // Create new detail
                    detailVO = new DocumentTypeMappingDetailsVO();

                } else {

                    // Update existing detail
                    detailVO = documentTypeMappingDetailsRepo.findById(dto.getId())
                            .orElseThrow(() ->
                                    new ApplicationException(
                                            "Document Type Mapping Detail not found with id : " + dto.getId()));
                }
                detailVO.setScreenName(dto.getScreenName());
                detailVO.setScreenCode(dto.getScreenCode());
                detailVO.setDocCode(dto.getDocCode());
                detailVO.setPrefix(dto.getPrefix());
                detailVO.setActive(dto.isActive());

                detailVO.setDocumentTypeMappingMasterVO(masterVO);
                details.add(detailVO);
            }
        }

        masterVO.setDetails(details);

        documentTypeMappingRepo.save(masterVO);

        response.put("message", message);
        response.put("documentTypeMappingMasterVO", masterVO);

        return response;
    }

    @Override
    public DocumentTypeMappingVO getDocumentTypeMappingById(Long id)
            throws ApplicationException {

        return documentTypeMappingRepo.findById(id)
                .orElseThrow(() ->
                        new ApplicationException("Document Type Mapping not found with id : " + id));
    }

    @Override
    public List<DocumentTypeMappingVO> getDocumnentTypeMappingByOrgId(Long orgId, Long branch)
            throws ApplicationException {

        return documentTypeMappingRepo.findByOrgIdAndBranch(orgId, branch);
    }
	}





