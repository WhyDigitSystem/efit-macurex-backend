package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GradeMasterDTO;
import com.efitops.basesetup.dto.HsnDTO;
import com.efitops.basesetup.dto.UnitMasterDTO;
import com.efitops.basesetup.dto.UomConversionDTO;
import com.efitops.basesetup.entity.GradeMasterVO;
import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.UomConversionVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DevelopService {

	List<HsnVO> getAllHSN(Long orgId);

	Optional<HsnVO> getHSNById(Long hsnId);

	Map<String, Object> createUpdateHSN(HsnDTO hsnDTO) throws ApplicationException;
	
	// Unit Master
	
	List<UnitMasterVO> getAllUnitMaster(Long orgId);

	Optional<UnitMasterVO> getUnitMasterById(Long id);

	Map<String, Object> createUpdateUnitMaster(UnitMasterDTO unitMasterDTO)
	        throws ApplicationException;

	Map<String, Object> createUpdateUomConversion(UomConversionDTO uomConversionDTO)
	       throws ApplicationException;


	List<UomConversionVO> getAllUomConversion(Long orgId);

	Optional<UomConversionVO> getUomConversionById(Long id);

	Map<String, Object> createUpdateGradeMaster(GradeMasterDTO gradeMasterDTO)
			throws ApplicationException;



	List<GradeMasterVO> getAllGradeMaster(Long orgId);

	Optional<GradeMasterVO> getGradeMasterById(Long id);
	
	

	
}
