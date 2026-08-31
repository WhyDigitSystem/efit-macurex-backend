package com.efitops.basesetup.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.efitops.basesetup.dto.ToolMasterDTO;
import com.efitops.basesetup.exception.ApplicationException;

public interface ToolMasterService {

	Map<String, Object> updateCreateToolMaster(ToolMasterDTO toolMasterDTO, MultipartFile[] files)
			throws ApplicationException;

}
