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

	//documenttypemapping

		Map<String, Object> updateCreateDocumnentTypeMapping(DocumnentTypeMappingDTO documnentTypeMappingDTO)
				throws ApplicationException;

		DocumentTypeMappingVO getDocumnentTypeMappingById(Long id);

		List<DocumentTypeMappingVO> getDocumnentTypeMappingByOrgId(Long orgId, Long branch);

		
		
	
	
	

	
	
}
