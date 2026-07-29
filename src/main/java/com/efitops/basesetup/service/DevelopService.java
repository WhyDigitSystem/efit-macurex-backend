package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DocumentTypeMappingDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DevelopService {

	Map<String, Object> updateCreateDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)
			throws ApplicationException;

	DocumentTypeMappingVO getDocumentTypeMappingById(Long id) throws ApplicationException;

	List<DocumentTypeMappingVO> getDocumnentTypeMappingByOrgId(Long orgId, Long branch) throws ApplicationException;

	//documenttypemapping

	
	
	
}
