package com.efitops.basesetup.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.entity.GlobalParameterVO;
import com.efitops.basesetup.repository.FinancialYearRepo;
import com.efitops.basesetup.repository.GlobalParameterRepo;
import com.efitops.basesetup.repository.UserBranchAccessRepo;
import com.efitops.basesetup.repository.UserRepo;

@Service
public class GlobalParameterServiceImpl implements GlobalParameterService{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(GlobalParameterServiceImpl.class);

	@Autowired
	GlobalParameterRepo globalParameterRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserBranchAccessRepo userBranchAccessRepo;


	@Autowired
	FinancialYearRepo financialRepo;

	@Override
	public Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, Long userid) {

		return globalParameterRepo.findGlobalParamByOrgIdAndUserName(orgid, userid);
	}

	@Override
	public Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, String userName) {

		return userBranchAccessRepo.findGlobalParametersBranchByUserName(orgid, userName);
	}
}
