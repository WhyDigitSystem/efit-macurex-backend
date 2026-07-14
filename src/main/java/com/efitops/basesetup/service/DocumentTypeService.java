package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.DocumentTypeDTO;
import com.efitops.basesetup.dto.DocumentTypeMappingDTO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.DocumentTypeVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface DocumentTypeService {
	
	// Document Type

	Map<String, Object> createUpdateDocumentType(DocumentTypeDTO documentTypeDTO) throws ApplicationException;

	DocumentTypeVO getDocumentTypeById(Long id) throws ApplicationException;

	List<DocumentTypeVO> getAllDocumentTypeByOrgId(Long orgId);
	
	// Document type mapping

	List<Map<String, Object>> getPendingDocumentTypeMapping(Long orgId, String branch, String branchCode,
			String finYear, String finYearIdentifier);

	Map<String, Object> createDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)
			throws ApplicationException;

	List<DocumentTypeMappingVO> getAllDocumentTypeMapping(Long orgId);

	DocumentTypeMappingVO getDocumentTypeMappingById(Long id) throws ApplicationException;

}
