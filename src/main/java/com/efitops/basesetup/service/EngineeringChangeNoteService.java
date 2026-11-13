package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.ResponseDTO.EngineeringChangeNoteResponseDTO;
import com.efitops.basesetup.dto.EngineeringChangeNoteDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface EngineeringChangeNoteService {

	EngineeringChangeNoteResponseDTO getEngineeringChangeNoteById(Long id) throws ApplicationException;

	List<EngineeringChangeNoteResponseDTO> getEngineeringChangeNoteByOrgId(Long orgId, Long branch)
			throws ApplicationException;

	Map<String, Object> createUpdateEngineeringChangeNote(EngineeringChangeNoteDTO engineeringChangeNoteDTO,
			MultipartFile[] drawingFiles, MultipartFile[] bomFiles) throws ApplicationException;

	ResponseEntity<byte[]> viewDrawingFile(HttpServletRequest request) throws IOException;

	ResponseEntity<byte[]> viewBomFile(HttpServletRequest request) throws IOException;

	String getEngineeringChangeNoteDocId(Long orgId, String financialYear);

}
