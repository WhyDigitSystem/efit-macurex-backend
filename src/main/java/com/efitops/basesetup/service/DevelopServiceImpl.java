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

import com.efitops.basesetup.dto.DocumentTypeMappingDetailsDTO;
import com.efitops.basesetup.dto.DocumnentTypeMappingDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.DocumnentTypeMappingRepo;
import com.efitops.basesetup.repository.FinancialYearRepo;


@Service
public class DevelopServiceImpl implements DevelopService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopServiceImpl.class);
	

	@Autowired
	FinancialYearRepo financialYearRepo;

    @Autowired
    private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
    
    @Autowired
    private DocumnentTypeMappingRepo documnentTypeMappingRepo;
    
	@Autowired
	BranchRepo branchRepo;

@PersistenceContext
private EntityManager entityManager;


    
    //documenttypemapping
    
    
    @Override
    @Transactional
    public Map<String, Object> updateCreateDocumnentTypeMapping(
    		DocumnentTypeMappingDTO documnentTypeMappingDTO)
            throws ApplicationException {

        Map<String, Object> response = new HashMap<>();
        String message = "";

        DocumentTypeMappingVO masterVO;

        BranchVO branchVO = branchRepo.findById(documnentTypeMappingDTO.getBranch())
                .orElseThrow(() -> new ApplicationException(
                        "Branch not found with id : " + documnentTypeMappingDTO.getBranch()));

        FinancialYearVO financialYearVO = financialYearRepo.findById(documnentTypeMappingDTO.getFinancialYear())
                .orElseThrow(() -> new ApplicationException(
                        "Financial Year not found with id : " + documnentTypeMappingDTO.getFinancialYear()));

        if (ObjectUtils.isEmpty(documnentTypeMappingDTO.getId())) {

        	if (documnentTypeMappingRepo.existsByBranch_IdAndFinancialYear_IdAndOrgId(
        	        documnentTypeMappingDTO.getBranch(),
        	        documnentTypeMappingDTO.getFinancialYear(),
        	        documnentTypeMappingDTO.getOrgId())) {

                throw new ApplicationException("Document Type Mapping already exists.");
            }

            masterVO = new DocumentTypeMappingVO();

            masterVO.setCreatedBy(documnentTypeMappingDTO.getCreatedBy());

            message = "Document Type Mapping Created Successfully";

        } else {

            masterVO = documnentTypeMappingRepo.findById(documnentTypeMappingDTO.getId())
                    .orElseThrow(() -> new ApplicationException("Document Type Mapping not found"));

            masterVO.setUpdatedBy(documnentTypeMappingDTO.getCreatedBy());

            message = "Document Type Mapping Updated Successfully";
        }

        masterVO.setBranch(branchVO);
        masterVO.setFinancialYear(financialYearVO);
        masterVO.setOrgId(documnentTypeMappingDTO.getOrgId());
        masterVO.setActive(documnentTypeMappingDTO.isActive());
        masterVO.setCancelRemarks(documnentTypeMappingDTO.getCancelRemarks());

        List<DocumentTypeMappingDetailsVO> details = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(documnentTypeMappingDTO.getDetails())) {

            for (DocumentTypeMappingDetailsDTO dto : documnentTypeMappingDTO.getDetails()) {

                DocumentTypeMappingDetailsVO detailVO;

                if (ObjectUtils.isEmpty(dto.getId())) {

                    detailVO = documentTypeMappingDetailsRepo.findById(dto.getId())
                            .orElse(new DocumentTypeMappingDetailsVO());

                } else {

                    detailVO = new DocumentTypeMappingDetailsVO();
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

        documnentTypeMappingRepo.save(masterVO);

        response.put("message", message);
        response.put("documentTypeMappingMasterVO", masterVO);

        return response;
    }

	@Override
	public DocumentTypeMappingVO getDocumnentTypeMappingById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentTypeMappingVO> getDocumnentTypeMappingByOrgId(Long orgId, Long branch) {
		// TODO Auto-generated method stub
		return null;
	}
}




