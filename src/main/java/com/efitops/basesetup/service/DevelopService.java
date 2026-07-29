package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DocumentTypeMappingDTO;
import com.efitops.basesetup.dto.SalesZoneMasterDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.SalesZoneMasterVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DevelopService {
	
	//documenttypemapping


	Map<String, Object> updateCreateDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)
			throws ApplicationException;

	DocumentTypeMappingVO getDocumentTypeMappingById(Long id) throws ApplicationException;

	List<DocumentTypeMappingVO> getDocumnentTypeMappingByOrgId(Long orgId, Long branch) throws ApplicationException;

	//saleszonemaster
	
	Map<String, Object> createUpdateSalesZoneMaster(SalesZoneMasterDTO salesZoneMasterDTO)
	        throws ApplicationException;


	Optional<SalesZoneMasterVO> getSalesZoneMasterById(Long id);

	List<SalesZoneMasterVO> getSalesZoneMasterByOrgId(Long orgId, Long branch);
	
	
	
}
