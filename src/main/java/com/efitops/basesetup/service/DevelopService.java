package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DocumentTypeMasterDTO;
import com.efitops.basesetup.dto.DocumnentTypeMappingDTO;
import com.efitops.basesetup.dto.GSTStateMasterDTO;
import com.efitops.basesetup.dto.GradeMasterDTO;
import com.efitops.basesetup.dto.HsnDTO;
import com.efitops.basesetup.dto.UnitMasterDTO;
import com.efitops.basesetup.dto.UomConversionDTO;
import com.efitops.basesetup.entity.DocumentTypeMasterVO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.UomConversionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DevelopService {

	List<HsnVO> getHsnByOrgId(Long orgId, Long branch);

	Optional<HsnVO> getHSNById(Long hsnId);

	Map<String, Object> createUpdateHSN(HsnDTO hsnDTO) throws ApplicationException;
	
	// Unit Master
	
	List<UnitMasterVO> getUnitMasterByOrgId(Long orgId, Long branch);

	Optional<UnitMasterVO> getUnitMasterById(Long id);

	Map<String, Object> createUpdateUnitMaster(UnitMasterDTO unitMasterDTO)
	        throws ApplicationException;

	//uom
	
	Map<String, Object> createUpdateUomConversion(UomConversionDTO uomConversionDTO)
	       throws ApplicationException;


	List<UomConversionVO> getUomConversionByOrgId(Long orgId, Long branch);

	Optional<UomConversionVO> getUomConversionById(Long id);
	
	//grademaster

	Map<String, Object> createUpdateGradeMaster(GradeMasterDTO gradeMasterDTO)
			throws ApplicationException;



	List<GradeMasterVO> getGradeMasterByOrgId(Long orgId,Long branch);

	Optional<GradeMasterVO> getGradeMasterById(Long id);
	
	//GSTStateMaster
	
	List<GSTStateMasterVO> getGSTStateMasterByOrgId(Long orgId, Long branch);

	Optional<GSTStateMasterVO> getGSTStateMasterById(Long id);

	Map<String, Object> createUpdateGSTStateMaster(
	        GSTStateMasterDTO gstStateMasterDTO)
	        throws ApplicationException;
	
	
	//DocumentTypeMaster
	
	
	List<DocumentTypeMasterVO> getDocumentTypeMasterByOrgId(Long orgId, Long branch);

	Optional<DocumentTypeMasterVO> getDocumentTypeMasterById(Long id);

	Map<String, Object> createUpdateDocumentTypeMaster(
	        DocumentTypeMasterDTO documentTypeMasterDTO)
	        throws ApplicationException;
	

	//documenttypemapping

	Map<String, Object> updateCreateDocumnentTypeMapping(DocumnentTypeMappingDTO documnentTypeMappingDTO)
			throws ApplicationException;

	DocumentTypeMappingVO getDocumnentTypeMappingById(Long id);

	List<DocumentTypeMappingVO> getDocumnentTypeMappingByOrgId(Long orgId, Long branch);

	
	
	
	
	
	
	

	
	
}
