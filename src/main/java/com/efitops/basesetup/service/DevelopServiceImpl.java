package com.efitops.basesetup.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.efitops.basesetup.dto.DocumentTypeMasterDTO;
import com.efitops.basesetup.dto.DocumnentTypeMappingDTO;
import com.efitops.basesetup.dto.GSTStateMasterDTO;
import com.efitops.basesetup.dto.GradeMasterDTO;
import com.efitops.basesetup.dto.HsnDTO;
import com.efitops.basesetup.dto.UnitMasterDTO;
import com.efitops.basesetup.dto.UomConversionDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.DocumentTypeMasterVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.UomConversionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.DocumentTypeMasterRepo;
import com.efitops.basesetup.repository.DocumnentTypeMappingRepo;
import com.efitops.basesetup.repository.FinancialYearRepo;
import com.efitops.basesetup.repository.GSTStateMasterRepo;
import com.efitops.basesetup.repository.GradeMasterRepo;
import com.efitops.basesetup.repository.HsnRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.UnitMasterRepo;
import com.efitops.basesetup.repository.UomConversionRepo;


@Service
public class DevelopServiceImpl implements DevelopService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopServiceImpl.class);
	
@Autowired
private HsnRepo hsnRepo;

@Autowired
private ListOfValuesRepo listOfValuesRepo;

@Autowired
private UnitMasterRepo unitMasterRepo;

@Autowired
private UomConversionRepo uomConversionRepo;

@Autowired
private GradeMasterRepo gradeMasterRepo;

@Autowired
private BranchRepo branchRepo;

@Autowired
private GSTStateMasterRepo gstStateMasterRepo;

@Autowired
private DocumnentTypeMappingRepo documnentTypeMappingRepo;

@Autowired
private DocumentTypeMasterRepo documentTypeMasterRepo;

@Autowired
private DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

@Autowired
private FinancialYearRepo financialYearRepo;



@PersistenceContext
private EntityManager entityManager;

//HSN


@Override
public List<HsnVO> getHsnByOrgId(Long orgId,Long branch) {
    return hsnRepo.findByOrgId(orgId,branch);
}

@Override
public Optional<HsnVO> getHSNById(Long hsnId) {
    return hsnRepo.findById(hsnId);
}

@Override
@Transactional
public Map<String, Object> createUpdateHSN(HsnDTO hsnDTO)
        throws ApplicationException {

    HsnVO hsnVO;
    String message;
    HsnVO oldHSN = null;

    ListOfValuesVO category = listOfValuesRepo.findById(hsnDTO.getCategory())
            .orElseThrow(() ->
                    new ApplicationException(
                            "Category not found with id : " + hsnDTO.getCategory()));

    if (ObjectUtils.isEmpty(hsnDTO.getId())) {

        if (hsnRepo.existsByOrgIdAndCategoryAndHsnIgnoreCase(
                hsnDTO.getOrgId(),
                category,
                hsnDTO.getHsn())) {

            throw new ApplicationException(
                    "This HSN : " + hsnDTO.getHsn()
                            + " Already Exists in This Organization.");
        }

        hsnVO = new HsnVO();
        hsnVO.setCreatedBy(hsnDTO.getCreatedBy());
        hsnVO.setUpdatedBy(hsnDTO.getCreatedBy());

        message = "HSN Created Successfully";

    } else {

        oldHSN = hsnRepo.findById(hsnDTO.getId())
                .orElseThrow(() ->
                        new ApplicationException("HSN Master not found"));

        entityManager.detach(oldHSN);

        hsnVO = hsnRepo.findById(hsnDTO.getId())
                .orElseThrow(() ->
                        new ApplicationException(
                                "This Id Is Not Found : " + hsnDTO.getId()));

        hsnVO.setUpdatedBy(hsnDTO.getCreatedBy());

        if (!hsnVO.getHsn().equalsIgnoreCase(hsnDTO.getHsn())) {

            if (hsnRepo.existsByOrgIdAndCategoryAndHsnIgnoreCase(
                    hsnDTO.getOrgId(),
                    category,
                    hsnDTO.getHsn())) {

                throw new ApplicationException(
                        "This HSN : " + hsnDTO.getHsn()
                                + " Already Exists in This Organization.");
            }

            hsnVO.setHsn(hsnDTO.getHsn().toUpperCase());
        }

        if (hsnDTO.getDescription() != null) {

            if (hsnVO.getDescription() == null
                    || !hsnVO.getDescription().equalsIgnoreCase(hsnDTO.getDescription())) {

                hsnVO.setDescription(hsnDTO.getDescription().toUpperCase());
            }
        }

        message = "HSN Updated Successfully";
    }

    getHSNVOFromDTO(hsnVO, hsnDTO);

    hsnRepo.save(hsnVO);

    Map<String, Object> response = new HashMap<>();
    response.put("message", message);
    response.put("hsnVO", hsnVO);

    return response;
}

private void getHSNVOFromDTO(HsnVO hsnVO,
                             HsnDTO hsnDTO)
        throws ApplicationException {

    hsnVO.setHsn(hsnDTO.getHsn().toUpperCase());

    if (hsnDTO.getDescription() != null) {
        hsnVO.setDescription(hsnDTO.getDescription().toUpperCase());
    }

    hsnVO.setActive(hsnDTO.isActive());
    hsnVO.setOrgId(hsnDTO.getOrgId());
    hsnVO.setCancelRemarks(hsnDTO.getCancelRemarks());

    if (hsnDTO.getCategory() != null && hsnDTO.getCategory() != 0) {

    ListOfValuesVO category = listOfValuesRepo.findById(hsnDTO.getCategory())
            .orElseThrow(() ->
                    new ApplicationException(
                            "Category not found with id : " + hsnDTO.getCategory()));

    hsnVO.setCategory(category);
    }
    if (hsnDTO.getBranch() != null && hsnDTO.getBranch() != 0) {

        BranchVO branch = branchRepo.findById(hsnDTO.getBranch())
                .orElseThrow(() ->
                        new ApplicationException("Branch Not Found"));

        hsnVO.setBranch(branch);
    }
}

//Unit Master

@Override
public List<UnitMasterVO> getUnitMasterByOrgId(Long orgId,Long branch) {
    return unitMasterRepo.findByOrgIdAndBranch(orgId,branch);
}

@Override
public Optional<UnitMasterVO> getUnitMasterById(Long id) {
    return unitMasterRepo.findById(id);
}

@Override
@Transactional
public Map<String, Object> createUpdateUnitMaster(UnitMasterDTO unitMasterDTO)
        throws ApplicationException {

    UnitMasterVO unitMasterVO;
    String message;
    UnitMasterVO oldUnitMaster = null;

    if (ObjectUtils.isEmpty(unitMasterDTO.getId())) {

        if (unitMasterRepo.existsByOrgIdAndUnitIdIgnoreCase(
                unitMasterDTO.getOrgId(),
                unitMasterDTO.getUnitId())) {

            throw new ApplicationException(
                    "This Unit Id : " + unitMasterDTO.getUnitId()
                            + " Already Exists in This Organization.");
        }

        unitMasterVO = new UnitMasterVO();
        unitMasterVO.setCreatedBy(unitMasterDTO.getCreatedBy());
        unitMasterVO.setUpdatedBy(unitMasterDTO.getCreatedBy());

        message = "Unit Master Created Successfully";

    } else {

        oldUnitMaster = unitMasterRepo.findById(unitMasterDTO.getId())
                .orElseThrow(() ->
                        new ApplicationException("Unit Master not found"));

        entityManager.detach(oldUnitMaster);

        unitMasterVO = unitMasterRepo.findById(unitMasterDTO.getId())
                .orElseThrow(() ->
                        new ApplicationException(
                                "This Id Is Not Found : " + unitMasterDTO.getId()));

        unitMasterVO.setUpdatedBy(unitMasterDTO.getCreatedBy());

        if (!unitMasterVO.getUnitId()
                .equalsIgnoreCase(unitMasterDTO.getUnitId())) {

            if (unitMasterRepo.existsByOrgIdAndUnitIdIgnoreCase(
                    unitMasterDTO.getOrgId(),
                    unitMasterDTO.getUnitId())) {

                throw new ApplicationException(
                        "This Unit Id : " + unitMasterDTO.getUnitId()
                                + " Already Exists in This Organization.");
            }

            unitMasterVO.setUnitId(unitMasterDTO.getUnitId().toUpperCase());
        }

        message = "Unit Master Updated Successfully";
    }

    getUnitMasterVOFromDTO(unitMasterVO, unitMasterDTO);

    unitMasterRepo.save(unitMasterVO);

    Map<String, Object> response = new HashMap<>();
    response.put("message", message);
    response.put("unitMasterVO", unitMasterVO);

    return response;
}

private void getUnitMasterVOFromDTO(
        UnitMasterVO unitMasterVO,
        UnitMasterDTO unitMasterDTO) throws ApplicationException {

    unitMasterVO.setUnitId(unitMasterDTO.getUnitId().toUpperCase());
    unitMasterVO.setOrgId(unitMasterDTO.getOrgId());
    unitMasterVO.setActive(unitMasterDTO.isActive());
    unitMasterVO.setCancelRemarks(unitMasterDTO.getCancelRemarks());

    if (unitMasterDTO.getBranch() != null
            && unitMasterDTO.getBranch() != 0) {

        BranchVO branch = branchRepo.findById(unitMasterDTO.getBranch())
                .orElseThrow(() ->
                        new ApplicationException("Branch Not Found"));

        unitMasterVO.setBranch(branch);
    }
}
//Uom Conversion

@Override
public List<UomConversionVO> getUomConversionByOrgId(Long orgId, Long branch) {
	return uomConversionRepo.findByOrgIdAndBranch(orgId,branch);
}

@Override
public Optional<UomConversionVO> getUomConversionById(Long id) {
	return uomConversionRepo.findById(id);
}

@Override
@Transactional
public Map<String, Object> createUpdateUomConversion(UomConversionDTO uomConversionDTO)
		throws ApplicationException {

	UomConversionVO uomConversionVO;
	String message = null;
	UomConversionVO oldUomConversion = null;

	if (ObjectUtils.isEmpty(uomConversionDTO.getId())) {

		if (uomConversionRepo.existsByOrgIdAndFromUnitAndToUnit(
				uomConversionDTO.getOrgId(),
				uomConversionDTO.getFromUnit(),
				uomConversionDTO.getToUnit())) {

			String errorMessage = String.format(
					"This Conversion Already Exists in This Organization.");

			throw new ApplicationException(errorMessage);
		}

		uomConversionVO = new UomConversionVO();
		uomConversionVO.setCreatedBy(uomConversionDTO.getCreatedBy());
		uomConversionVO.setUpdatedBy(uomConversionDTO.getCreatedBy());

		message = "UOM Conversion Created Successfully";

	} else {

		oldUomConversion = uomConversionRepo.findById(uomConversionDTO.getId())
				.orElseThrow(() -> new ApplicationException("UOM Conversion not found"));

		entityManager.detach(oldUomConversion);

		uomConversionVO = uomConversionRepo.findById(uomConversionDTO.getId())
				.orElseThrow(() -> new ApplicationException(
						"This Id Is Not Found : " + uomConversionDTO.getId()));

		uomConversionVO.setUpdatedBy(uomConversionDTO.getCreatedBy());

		if (!uomConversionVO.getFromUnit().equals(uomConversionDTO.getFromUnit())
				|| !uomConversionVO.getToUnit().equals(uomConversionDTO.getToUnit())) {

			if (uomConversionRepo.existsByOrgIdAndFromUnitAndToUnit(
					uomConversionDTO.getOrgId(),
					uomConversionDTO.getFromUnit(),
					uomConversionDTO.getToUnit())) {

				String errorMessage = "This Conversion Already Exists in This Organization.";
				throw new ApplicationException(errorMessage);
			}

			uomConversionVO.setFromUnit(uomConversionDTO.getFromUnit());
			uomConversionVO.setToUnit(uomConversionDTO.getToUnit());
		}

		uomConversionVO.setMultiplicationFactor(
				uomConversionDTO.getMultiplicationFactor());

		message = "UOM Conversion Updated Successfully";
	}

	getUomConversionVOFromDTO(uomConversionVO, uomConversionDTO);

	uomConversionRepo.save(uomConversionVO);

	Map<String, Object> response = new HashMap<>();
	response.put("message", message);
	response.put("uomConversionVO", uomConversionVO);

	return response;
}

private void getUomConversionVOFromDTO(
        UomConversionVO uomConversionVO,
        UomConversionDTO uomConversionDTO) throws ApplicationException {

	uomConversionVO.setFromUnit(uomConversionDTO.getFromUnit());
	uomConversionVO.setToUnit(uomConversionDTO.getToUnit());
	uomConversionVO.setMultiplicationFactor(
			uomConversionDTO.getMultiplicationFactor());

	uomConversionVO.setOrgId(uomConversionDTO.getOrgId());
	uomConversionVO.setActive(uomConversionDTO.isActive());
	uomConversionVO.setCancelRemarks(uomConversionDTO.getCancelRemarks());
	
	if (uomConversionDTO.getBranch() != null && uomConversionDTO.getBranch() != 0) {

	    BranchVO branch = branchRepo.findById(uomConversionDTO.getBranch())
	            .orElseThrow(() ->
	                    new ApplicationException("Branch Not Found"));

	    uomConversionVO.setBranch(branch);
	}
	
}


//Grade Master


@Override
public List<GradeMasterVO> getGradeMasterByOrgId(Long orgId,Long branch) {
 return gradeMasterRepo.findByOrgIdAndBranch(orgId,branch);
}

@Override
public Optional<GradeMasterVO> getGradeMasterById(Long id) {
 return gradeMasterRepo.findById(id);
}

@Override
@Transactional
public Map<String, Object> createUpdateGradeMaster(GradeMasterDTO gradeMasterDTO)
     throws ApplicationException {

 GradeMasterVO gradeMasterVO;
 String message = null;
 GradeMasterVO oldGradeMaster = null;

 if (ObjectUtils.isEmpty(gradeMasterDTO.getId())) {

     if (gradeMasterRepo.existsByOrgIdAndGradeCodeIgnoreCase(
             gradeMasterDTO.getOrgId(),
             gradeMasterDTO.getGradeCode())) {

         String errorMessage = String.format(
                 "This Grade Code : %s Already Exists in This Organization.",
                 gradeMasterDTO.getGradeCode());

         throw new ApplicationException(errorMessage);
     }

     gradeMasterVO = new GradeMasterVO();
     gradeMasterVO.setCreatedBy(gradeMasterDTO.getCreatedBy());
     gradeMasterVO.setUpdatedBy(gradeMasterDTO.getCreatedBy());

     message = "Grade Master Created Successfully";

 } else {

     oldGradeMaster = gradeMasterRepo.findById(gradeMasterDTO.getId())
             .orElseThrow(() -> new ApplicationException("Grade Master not found"));

     entityManager.detach(oldGradeMaster);

     gradeMasterVO = gradeMasterRepo.findById(gradeMasterDTO.getId())
             .orElseThrow(() -> new ApplicationException(
                     "This Id Is Not Found : " + gradeMasterDTO.getId()));

     gradeMasterVO.setUpdatedBy(gradeMasterDTO.getCreatedBy());

     if (!gradeMasterVO.getGradeCode().equalsIgnoreCase(gradeMasterDTO.getGradeCode())) {

         if (gradeMasterRepo.existsByOrgIdAndGradeCodeIgnoreCase(
                 gradeMasterDTO.getOrgId(),
                 gradeMasterDTO.getGradeCode())) {

             String errorMessage = String.format(
                     "This Grade Code : %s Already Exists in This Organization.",
                     gradeMasterDTO.getGradeCode());

             throw new ApplicationException(errorMessage);
         }

         gradeMasterVO.setGradeCode(gradeMasterDTO.getGradeCode().toUpperCase());
     }

     if (!gradeMasterVO.getGradeDescription()
             .equalsIgnoreCase(gradeMasterDTO.getGradeDescription())) {

         gradeMasterVO.setGradeDescription(
                 gradeMasterDTO.getGradeDescription().toUpperCase());
     }

     if (gradeMasterDTO.getRemarks() != null) {
         gradeMasterVO.setRemarks(
                 gradeMasterDTO.getRemarks().toUpperCase());
     }

     message = "Grade Master Updated Successfully";
 }

 getGradeMasterVOFromDTO(gradeMasterVO, gradeMasterDTO);

 gradeMasterRepo.save(gradeMasterVO);

 Map<String, Object> response = new HashMap<>();
 response.put("message", message);
 response.put("gradeMasterVO", gradeMasterVO);

 return response;
}

private void getGradeMasterVOFromDTO(GradeMasterVO gradeMasterVO,
     GradeMasterDTO gradeMasterDTO) throws ApplicationException {

 gradeMasterVO.setGradeCode(gradeMasterDTO.getGradeCode().toUpperCase());
 gradeMasterVO.setGradeDescription(
         gradeMasterDTO.getGradeDescription().toUpperCase());

 if (gradeMasterDTO.getRemarks() != null) {
     gradeMasterVO.setRemarks(
             gradeMasterDTO.getRemarks().toUpperCase());
 }

 gradeMasterVO.setActive(gradeMasterDTO.isActive());
 gradeMasterVO.setOrgId(gradeMasterDTO.getOrgId());
 gradeMasterVO.setCancelRemarks(gradeMasterDTO.getCancelRemarks());
 
 if (gradeMasterDTO.getBranch() != null && gradeMasterDTO.getBranch() != 0) {

	    BranchVO branch = branchRepo.findById(gradeMasterDTO.getBranch())
	            .orElseThrow(() ->
	                    new ApplicationException("Branch Not Found"));

	    gradeMasterVO.setBranch(branch);
   }
}

//GSTStateMaster

@Override
public List<GSTStateMasterVO> getGSTStateMasterByOrgId(Long orgId,Long branch) {
    return gstStateMasterRepo.findByGSTStateMasterByOrgId(orgId,branch);
}

@Override
public Optional<GSTStateMasterVO> getGSTStateMasterById(Long id) {
    return gstStateMasterRepo.findById(id);
}

@Override
@Transactional
public Map<String, Object> createUpdateGSTStateMaster(GSTStateMasterDTO gstStateMasterDTO)
        throws ApplicationException {

    GSTStateMasterVO gstStateMasterVO;
    String message;
    GSTStateMasterVO oldGSTStateMaster = null;

    if (ObjectUtils.isEmpty(gstStateMasterDTO.getId())) {

        if (gstStateMasterRepo.existsByOrgIdAndStateCodeIgnoreCase(
                gstStateMasterDTO.getOrgId(),
                gstStateMasterDTO.getStateCode())) {

            String errorMessage = String.format(
                    "This State Code : %s Already Exists in This Organization.",
                    gstStateMasterDTO.getStateCode());

            throw new ApplicationException(errorMessage);
        }

        gstStateMasterVO = new GSTStateMasterVO();
        gstStateMasterVO.setCreatedBy(gstStateMasterDTO.getCreatedBy());
        gstStateMasterVO.setUpdatedBy(gstStateMasterDTO.getCreatedBy());

        message = "GST State Master Created Successfully";

    } else {

        oldGSTStateMaster = gstStateMasterRepo.findById(gstStateMasterDTO.getId())
                .orElseThrow(() ->
                        new ApplicationException("GST State Master not found"));

        entityManager.detach(oldGSTStateMaster);

        gstStateMasterVO = gstStateMasterRepo.findById(gstStateMasterDTO.getId())
                .orElseThrow(() ->
                        new ApplicationException(
                                "This Id Is Not Found : " + gstStateMasterDTO.getId()));

        gstStateMasterVO.setUpdatedBy(gstStateMasterDTO.getCreatedBy());

        if (!gstStateMasterVO.getStateCode()
                .equalsIgnoreCase(gstStateMasterDTO.getStateCode())) {

            if (gstStateMasterRepo.existsByOrgIdAndStateCodeIgnoreCase(
                    gstStateMasterDTO.getOrgId(),
                    gstStateMasterDTO.getStateCode())) {

                String errorMessage = String.format(
                        "This State Code : %s Already Exists in This Organization.",
                        gstStateMasterDTO.getStateCode());

                throw new ApplicationException(errorMessage);
            }

            gstStateMasterVO.setStateCode(
                    gstStateMasterDTO.getStateCode().toUpperCase());
        }

        if (!gstStateMasterVO.getStateName()
                .equalsIgnoreCase(gstStateMasterDTO.getStateName())) {

            gstStateMasterVO.setStateName(
                    gstStateMasterDTO.getStateName().toUpperCase());
        }

        if (!gstStateMasterVO.getGstStateId()
                .equalsIgnoreCase(gstStateMasterDTO.getGstStateId())) {

            gstStateMasterVO.setGstStateId(
                    gstStateMasterDTO.getGstStateId().toUpperCase());
        }

        message = "GST State Master Updated Successfully";
    }

    getGSTStateMasterVOFromDTO(gstStateMasterVO, gstStateMasterDTO);

    gstStateMasterRepo.save(gstStateMasterVO);

    Map<String, Object> response = new HashMap<>();
    response.put("message", message);
    response.put("gstStateMasterVO", gstStateMasterVO);

    return response;
}

private void getGSTStateMasterVOFromDTO(
        GSTStateMasterVO gstStateMasterVO,
        GSTStateMasterDTO gstStateMasterDTO)
        throws ApplicationException {

    gstStateMasterVO.setStateCode(
            gstStateMasterDTO.getStateCode().toUpperCase());

    gstStateMasterVO.setStateName(
            gstStateMasterDTO.getStateName().toUpperCase());

    gstStateMasterVO.setGstStateId(
            gstStateMasterDTO.getGstStateId().toUpperCase());

    gstStateMasterVO.setOrgId(gstStateMasterDTO.getOrgId());
    gstStateMasterVO.setActive(gstStateMasterDTO.isActive());
    gstStateMasterVO.setCancelRemarks(
            gstStateMasterDTO.getCancelRemarks());

    if (gstStateMasterDTO.getBranch() != null
            && gstStateMasterDTO.getBranch() != 0) {

        BranchVO branch = branchRepo.findById(gstStateMasterDTO.getBranch())
                .orElseThrow(() ->
                        new ApplicationException("Branch Not Found"));

        gstStateMasterVO.setBranch(branch);
    }
}


//DocumentTypeMaster

@Override
public List<DocumentTypeMasterVO> getDocumentTypeMasterByOrgId(Long orgId,Long branch) {
    return documentTypeMasterRepo.findByOrgIdAndBranch(orgId,branch);
}

@Override
public Optional<DocumentTypeMasterVO> getDocumentTypeMasterById(Long id) {
    return documentTypeMasterRepo.findById(id);
}

@Override
@Transactional
public Map<String, Object> createUpdateDocumentTypeMaster(
        DocumentTypeMasterDTO documentTypeMasterDTO)
        throws ApplicationException {
  
    DocumentTypeMasterVO documentTypeMasterVO;
    String message;
    DocumentTypeMasterVO oldDocumentTypeMaster = null;

    if (ObjectUtils.isEmpty(documentTypeMasterDTO.getId())) {

        if (documentTypeMasterRepo.existsByOrgIdAndCodeIgnoreCase(
                documentTypeMasterDTO.getOrgId(),
                documentTypeMasterDTO.getCode())) {

            throw new ApplicationException(
                    "Document Type Code : " + documentTypeMasterDTO.getCode()
                            + " Already Exists in This Organization.");
        }

        documentTypeMasterVO = new DocumentTypeMasterVO();
        documentTypeMasterVO.setCreatedBy(documentTypeMasterDTO.getCreatedBy());
        documentTypeMasterVO.setUpdatedBy(documentTypeMasterDTO.getCreatedBy());

        message = "Document Type Master Created Successfully";

    } else {

        oldDocumentTypeMaster = documentTypeMasterRepo.findById(documentTypeMasterDTO.getId())
                .orElseThrow(() ->
                        new ApplicationException("Document Type Master Not Found"));

        entityManager.detach(oldDocumentTypeMaster);

        documentTypeMasterVO = documentTypeMasterRepo.findById(documentTypeMasterDTO.getId())
                .orElseThrow(() ->
                        new ApplicationException(
                                "This Id Is Not Found : " + documentTypeMasterDTO.getId()));

        documentTypeMasterVO.setUpdatedBy(documentTypeMasterDTO.getCreatedBy());

        if (!documentTypeMasterVO.getCode()
                .equalsIgnoreCase(documentTypeMasterDTO.getCode())) {

            if (documentTypeMasterRepo.existsByOrgIdAndCodeIgnoreCase(
                    documentTypeMasterDTO.getOrgId(),
                    documentTypeMasterDTO.getCode())) {

                throw new ApplicationException(
                        "Document Type Code : " + documentTypeMasterDTO.getCode()
                                + " Already Exists in This Organization.");
            }

            documentTypeMasterVO.setCode(
                    documentTypeMasterDTO.getCode().toUpperCase());
        }

        message = "Document Type Master Updated Successfully";
    }

    getDocumentTypeMasterVOFromDTO(
            documentTypeMasterVO,
            documentTypeMasterDTO);

    documentTypeMasterRepo.save(documentTypeMasterVO);

    Map<String, Object> response = new HashMap<>();
    response.put("message", message);
    response.put("documentTypeMasterVO", documentTypeMasterVO);

    return response;
}

private void getDocumentTypeMasterVOFromDTO(
        DocumentTypeMasterVO documentTypeMasterVO,
        DocumentTypeMasterDTO documentTypeMasterDTO)
        throws ApplicationException {

    documentTypeMasterVO.setCode(
            documentTypeMasterDTO.getCode().toUpperCase());

    if (documentTypeMasterDTO.getName() != null) {
        documentTypeMasterVO.setName(
                documentTypeMasterDTO.getName().toUpperCase());
    }

    if (documentTypeMasterDTO.getDes() != null) {
        documentTypeMasterVO.setDescription(
                documentTypeMasterDTO.getDescription().toUpperCase());
    }

    if (documentTypeMasterDTO.getDocCode() != null) {
        documentTypeMasterVO.setDocCode(
                documentTypeMasterDTO.getDocCode().toUpperCase());
    }

    documentTypeMasterVO.setOrgId(documentTypeMasterDTO.getOrgId());
    documentTypeMasterVO.setActive(documentTypeMasterDTO.isActive());
    documentTypeMasterVO.setCancelRemarks(documentTypeMasterDTO.getCancelRemarks());

    if (documentTypeMasterDTO.getBranch() != null
            && documentTypeMasterDTO.getBranch() != 0) {

        BranchVO branch = branchRepo.findById(documentTypeMasterDTO.getBranch())
                .orElseThrow(() ->
                        new ApplicationException("Branch Not Found"));

        documentTypeMasterVO.setBranch(branch);
    }
}
    
    
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
            masterVO.getCommonDate().setCreatedDate(LocalDateTime.now());

            message = "Document Type Mapping Created Successfully";

        } else {

            masterVO = documnentTypeMappingRepo.findById(documnentTypeMappingDTO.getId())
                    .orElseThrow(() -> new ApplicationException("Document Type Mapping not found"));

            masterVO.setUpdatedBy(documnentTypeMappingDTO.getCreatedBy());
            masterVO.getCommonDate().setUpdatedDate(LocalDateTime.now());

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




