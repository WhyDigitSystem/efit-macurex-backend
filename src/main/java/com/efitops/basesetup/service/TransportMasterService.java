package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.efitops.basesetup.dto.DailyExchangeRateDTO;
import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.exception.ApplicationException;


public interface TransportMasterService {

	//dailyexrate
	Map<String, Object> updateCreateDailyExRate(@Valid DailyExchangeRateDTO dailyExchangeRateDTO)
			throws ApplicationException;

	DailyExchangeRateVO getDailyExRateById(Long id) throws ApplicationException;

	List<DailyExchangeRateVO> getDailyExRateByOrgId(Long orgId, Long branch) throws ApplicationException;

	Map<String, Object> getCurrency(Long orgId) throws ApplicationException;

	
	

	
	 


}
