package com.efitops.basesetup.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.entity.GlobalParameterVO;

@Service
public interface GlobalParameterService {

	Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, Long userid);

	Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, String userName);

}
