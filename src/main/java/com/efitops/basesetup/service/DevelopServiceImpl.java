package com.efitops.basesetup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.efitops.basesetup.dto.GradeMasterDTO;
import com.efitops.basesetup.dto.HsnDTO;
import com.efitops.basesetup.dto.UnitMasterDTO;
import com.efitops.basesetup.dto.UomConversionDTO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.UomConversionVO;
import com.efitops.basesetup.exception.ApplicationException;
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

@PersistenceContext
private EntityManager entityManager;


@Override
public List<HsnVO> getAllHSN(Long orgId) {
    return hsnRepo.findByOrgId(orgId);
}

@Override
public Optional<HsnVO> getHSNById(Long hsnId) {
    return hsnRepo.findById(hsnId);
}

@Override
@Transactional
public Map<String, Object> createUpdateHSN(HsnDTO hsnDTO) throws ApplicationException {

    HsnVO hsnVO;
    String message = null;
    HsnVO oldHSN = null;

    ListOfValuesVO listOfValuesVO = listOfValuesRepo.findById(hsnDTO.getListofvalues())
            .orElseThrow(() -> new ApplicationException(
                    "Category not found with id : " + hsnDTO.getListofvalues()));

    if (ObjectUtils.isEmpty(hsnDTO.getId())) {

        if (hsnRepo.existsByOrgIdAndListofvaluesAndHsnIgnoreCase(
                hsnDTO.getOrgId(), listOfValuesVO, hsnDTO.getHsn())) {

            String errorMessage = String.format(
                    "This HSN : %s Already Exists in This Organization.",
                    hsnDTO.getHsn());

            throw new ApplicationException(errorMessage);
        }

        hsnVO = new HsnVO();
        hsnVO.setCreatedBy(hsnDTO.getCreatedBy());
        hsnVO.setUpdatedBy(hsnDTO.getCreatedBy());

        message = "HSN Created Successfully";

    } else {

        oldHSN = hsnRepo.findById(hsnDTO.getId())
                .orElseThrow(() -> new ApplicationException("HSN Master not found"));

        entityManager.detach(oldHSN);

        hsnVO = hsnRepo.findById(hsnDTO.getId())
                .orElseThrow(() -> new ApplicationException(
                        "This Id Is Not Found : " + hsnDTO.getId()));

        hsnVO.setUpdatedBy(hsnDTO.getCreatedBy());

        if (!hsnVO.getHsn().equalsIgnoreCase(hsnDTO.getHsn())) {

            if (hsnRepo.existsByOrgIdAndListofvaluesAndHsnIgnoreCase(
                    hsnDTO.getOrgId(), listOfValuesVO, hsnDTO.getHsn())) {

            	 
                String errorMessage = String.format(
                        "This HSN : %s Already Exists in This Organization.",
                        hsnDTO.getHsn());

                throw new ApplicationException(errorMessage);
            }

            hsnVO.setHsn(hsnDTO.getHsn().toUpperCase());
        }

        if (!hsnVO.getDescription().equalsIgnoreCase(hsnDTO.getDescription())) {
            hsnVO.setDescription(hsnDTO.getDescription().toUpperCase());
        }

        message = "HSN Updated Successfully";
    }

    getHSNVOFromHSNDTO(hsnVO, hsnDTO);

    hsnRepo.save(hsnVO);

    Map<String, Object> response = new HashMap<>();
    response.put("message", message);
    response.put("hsnVO", hsnVO);

    return response;
}

private void getHSNVOFromHSNDTO(HsnVO hsnVO, HsnDTO hsnDTO)
        throws ApplicationException {

    hsnVO.setHsn(hsnDTO.getHsn().toUpperCase());

    if (hsnDTO.getDescription() != null) {
        hsnVO.setDescription(hsnDTO.getDescription().toUpperCase());
    }

    hsnVO.setActive(hsnDTO.isActive());

    ListOfValuesVO listOfValuesVO = listOfValuesRepo.findById(hsnDTO.getListofvalues())
            .orElseThrow(() -> new ApplicationException(
                    "Category not found with id : " + hsnDTO.getListofvalues()));

    hsnVO.setListofvalues(listOfValuesVO);
    hsnVO.setOrgId(hsnDTO.getOrgId());
    hsnVO.setCancelRemarks(hsnDTO.getCancelRemarks());
}

//Unit Master

@Override
public List<UnitMasterVO> getAllUnitMaster(Long orgId) {
	return unitMasterRepo.findByOrgId(orgId);
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
	String message = null;
	UnitMasterVO oldUnitMaster = null;

	if (ObjectUtils.isEmpty(unitMasterDTO.getId())) {

		if (unitMasterRepo.existsByOrgIdAndUnitIdIgnoreCase(
				unitMasterDTO.getOrgId(),
				unitMasterDTO.getUnitId())) {

			String errorMessage = String.format(
					"This Unit Id : %s Already Exists in This Organization.",
					unitMasterDTO.getUnitId());

			throw new ApplicationException(errorMessage);
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

		if (!unitMasterVO.getUnitId().equalsIgnoreCase(unitMasterDTO.getUnitId())) {

			if (unitMasterRepo.existsByOrgIdAndUnitIdIgnoreCase(
					unitMasterDTO.getOrgId(),
					unitMasterDTO.getUnitId())) {

				String errorMessage = String.format(
						"This Unit Id : %s Already Exists in This Organization.",
						unitMasterDTO.getUnitId());

				throw new ApplicationException(errorMessage);
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

private void getUnitMasterVOFromDTO(UnitMasterVO unitMasterVO,
		UnitMasterDTO unitMasterDTO) {

	unitMasterVO.setUnitId(unitMasterDTO.getUnitId().toUpperCase());
	unitMasterVO.setActive(unitMasterDTO.isActive());
	unitMasterVO.setOrgId(unitMasterDTO.getOrgId());
	unitMasterVO.setCancelRemarks(unitMasterDTO.getCancelRemarks());
	unitMasterVO.setBranch(unitMasterDTO.getBranch());
	unitMasterVO.setBranchCode(unitMasterDTO.getBranchCode());
}


//Uom Conversion

@Override
public List<UomConversionVO> getAllUomConversion(Long orgId) {
	return uomConversionRepo.findByOrgId(orgId);
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
		UomConversionDTO uomConversionDTO) {

	uomConversionVO.setFromUnit(uomConversionDTO.getFromUnit());
	uomConversionVO.setToUnit(uomConversionDTO.getToUnit());
	uomConversionVO.setMultiplicationFactor(
			uomConversionDTO.getMultiplicationFactor());

	uomConversionVO.setOrgId(uomConversionDTO.getOrgId());
	uomConversionVO.setActive(uomConversionDTO.isActive());
	uomConversionVO.setCancelRemarks(uomConversionDTO.getCancelRemarks());
	
}


//Grade Master


@Override
public List<GradeMasterVO> getAllGradeMaster(Long orgId) {
 return gradeMasterRepo.findByOrgId(orgId);
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
     GradeMasterDTO gradeMasterDTO) {

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
 gradeMasterVO.setBranch(gradeMasterDTO.getBranch());
 gradeMasterVO.setBranchCode(gradeMasterDTO.getBranchCode());
}

}
