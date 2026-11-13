package com.efitops.basesetup.service;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.GlobalParameterDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.GlobalParameterVO;
import com.efitops.basesetup.entity.UserVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
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
	
	@Autowired
	BranchRepo branchRepo;

	@Override
	public Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, Long userid) {

		return globalParameterRepo.findGlobalParamByOrgIdAndUserName(orgid, userid);
	}

	@Override
	public Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, Long user) {

		return userBranchAccessRepo.findGlobalParametersBranchByUserName(orgid, user);
	}
	
	@Override
	public GlobalParameterVO updateGlobaParameter(GlobalParameterDTO dto)
	        throws ApplicationException {

	    UserVO userVO = userRepo.findByOrgIdAndId(dto.getOrgId(), dto.getUserId());

	    if (userVO == null) {
	        throw new ApplicationException("User not found");
	    }

	    BranchVO branchVO = branchRepo.findById(dto.getBranchId())
	            .orElseThrow(() -> new ApplicationException("Branch not found"));

	    GlobalParameterVO existingRecord = globalParameterRepo.findGlobalParam(
	            dto.getOrgId(), dto.getUserId());

	    if (existingRecord != null) {

	        existingRecord.setBranch(branchVO);
	        existingRecord.setFinancialYear(dto.getFinancialYear());

	        return globalParameterRepo.save(existingRecord);

	    } else {

	        GlobalParameterVO globalParameterVO = new GlobalParameterVO();
	        globalParameterVO.setOrgId(dto.getOrgId());
	        globalParameterVO.setUserId(dto.getUserId());
	        globalParameterVO.setBranch(branchVO);
	        globalParameterVO.setFinancialYear(dto.getFinancialYear());

	        return globalParameterRepo.save(globalParameterVO);
	    }
	}
}
