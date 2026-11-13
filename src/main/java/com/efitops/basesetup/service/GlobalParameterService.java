package com.efitops.basesetup.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GlobalParameterDTO;
import com.efitops.basesetup.entity.GlobalParameterVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface GlobalParameterService {

	Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, Long userid);

	Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, Long userName);

	GlobalParameterVO updateGlobaParameter(GlobalParameterDTO globalParameterDTO) throws ApplicationException;


}
