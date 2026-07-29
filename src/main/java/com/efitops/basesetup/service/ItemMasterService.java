package com.efitops.basesetup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.ItemMasterDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDTO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface ItemMasterService {

	ItemMasterResponseDTO getItemMasterById(Long id) throws ApplicationException;

	Map<String, Object> updateCreateItemMaster(ItemMasterDTO itemMasterDTO) throws ApplicationException;

	Map<String, Object> uploadImageItemMasterDetails(List<MultipartFile> files, Long itemMasterId,
			List<Long> itemDrawingId) throws ApplicationException, IOException;

	ResponseEntity<byte[]> viewItemMasterImages(HttpServletRequest request) throws IOException;

	ItemMasterResponseDTO getItemMasterByOrgId(Long orgId, Long branchId) throws ApplicationException;


}
