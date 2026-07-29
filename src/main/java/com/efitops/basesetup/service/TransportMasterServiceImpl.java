package com.efitops.basesetup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efitops.basesetup.dto.DailyExchangeRateDTO;
import com.efitops.basesetup.dto.ServiceAccMasterDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CurrencyVO;
import com.efitops.basesetup.entity.DailyExchangeRateVO;
import com.efitops.basesetup.entity.ServiceAccMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CurrencyRepo;
import com.efitops.basesetup.repository.DailyExchangeRateRepo;




@Service
public class TransportMasterServiceImpl implements TransportMasterService {
	
	@Autowired
	DailyExchangeRateRepo dailyExchangeRateRepo;
	
	@Autowired
	CurrencyRepo currencyRepo;
	
	@Autowired
	BranchRepo branchRepo;
	
	//Daily Exchange rate
	@Override
	@Transactional
	public Map<String, Object> updateCreateDailyExRate(@Valid DailyExchangeRateDTO dailyExchangeRateDTO)
			throws ApplicationException {

		DailyExchangeRateVO dailyExchangeRateVO = new DailyExchangeRateVO();
		String message;

		if (ObjectUtils.isNotEmpty(dailyExchangeRateDTO.getId())) {

		    dailyExchangeRateVO = dailyExchangeRateRepo.findById(dailyExchangeRateDTO.getId())
		            .orElseThrow(() -> new ApplicationException("Invalid Daily Exchange Rate Master Details"));

		    createUpdateDailyExchangeRateVOByDailyExchangeRateDTO(dailyExchangeRateDTO, dailyExchangeRateVO);

		    dailyExchangeRateVO.setUpdatedBy(dailyExchangeRateDTO.getCreatedBy());

		    message = "Daily Exchange Rate Master Updated Successfully";

		} else {

		    createUpdateDailyExchangeRateVOByDailyExchangeRateDTO(dailyExchangeRateDTO, dailyExchangeRateVO);

		    if (dailyExchangeRateDTO.getCurrency() != null && dailyExchangeRateDTO.getCurrency() != 0) {

				CurrencyVO currency = currencyRepo.findById(dailyExchangeRateDTO.getCurrency())
						.orElseThrow(() -> new ApplicationException("currency Not Found"));

				dailyExchangeRateVO.setCurrency(currency);
			}
		    dailyExchangeRateVO.setCreatedBy(dailyExchangeRateDTO.getCreatedBy());
		    dailyExchangeRateVO.setUpdatedBy(dailyExchangeRateDTO.getCreatedBy());
		    dailyExchangeRateVO.setEffectiveFrom(dailyExchangeRateDTO.getEffectiveFrom());
		    dailyExchangeRateVO.setBuyingExRate(dailyExchangeRateDTO.getBuyingExRate());
		    dailyExchangeRateVO.setSellingExRate(dailyExchangeRateDTO.getSellingExRate());
		    dailyExchangeRateVO.setMonth(dailyExchangeRateDTO.getMonth());
		    dailyExchangeRateVO.setYear(dailyExchangeRateDTO.getYear());
		    dailyExchangeRateVO.setActive(dailyExchangeRateDTO.isActive());
		    dailyExchangeRateVO.setCancelRemarks(dailyExchangeRateDTO.getCancelRemarks());
		    if (dailyExchangeRateDTO.getBranch() != null && dailyExchangeRateDTO.getBranch() != 0) {

				BranchVO branch = branchRepo.findById(dailyExchangeRateDTO.getBranch())
						.orElseThrow(() -> new ApplicationException("branch Not Found"));

				dailyExchangeRateVO.setBranch(branch);
			}



		    message = "Daily Exchange Rate Master Created Successfully";
		}
        dailyExchangeRateRepo.save(dailyExchangeRateVO);

		Map<String, Object> response = new HashMap<>();
		response.put("dailyExchangeRateVO", dailyExchangeRateVO);
		response.put("message", message);

		return response;
		
		}

	private void createUpdateDailyExchangeRateVOByDailyExchangeRateDTO(@Valid DailyExchangeRateDTO dailyExchangeRateDTO,
			DailyExchangeRateVO dailyExchangeRateVO) throws ApplicationException {

		if (dailyExchangeRateDTO.getCurrency() != null && dailyExchangeRateDTO.getCurrency() != 0) {

			CurrencyVO currency = currencyRepo.findById(dailyExchangeRateDTO.getCurrency())
					.orElseThrow(() -> new ApplicationException("currency Not Found"));

			dailyExchangeRateVO.setCurrency(currency);
		}
		dailyExchangeRateVO.setEffectiveFrom(dailyExchangeRateDTO.getEffectiveFrom());
		dailyExchangeRateVO.setBuyingExRate(dailyExchangeRateDTO.getBuyingExRate());
		dailyExchangeRateVO.setOrgId(dailyExchangeRateDTO.getOrgId());
		dailyExchangeRateVO.setSellingExRate(dailyExchangeRateDTO.getSellingExRate());
		dailyExchangeRateVO.setActive(dailyExchangeRateDTO.isActive());
		dailyExchangeRateVO.setCancelRemarks(dailyExchangeRateDTO.getCancelRemarks());
		dailyExchangeRateVO.setMonth(dailyExchangeRateDTO.getMonth());
		dailyExchangeRateVO.setYear(dailyExchangeRateDTO.getYear());
		dailyExchangeRateVO.setCreatedBy(dailyExchangeRateDTO.getCreatedBy());;
		if (dailyExchangeRateDTO.getBranch() != null && dailyExchangeRateDTO.getBranch() != 0) {

			BranchVO branch = branchRepo.findById(dailyExchangeRateDTO.getBranch())
					.orElseThrow(() -> new ApplicationException("branch Not Found"));

			dailyExchangeRateVO.setBranch(branch);
		}

	}

	@Override
	public DailyExchangeRateVO getDailyExRateById(Long id) throws ApplicationException {

		return dailyExchangeRateRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Invalid Daily Exchange Rate Master Details"));
	}

	@Override
	public List<DailyExchangeRateVO> getDailyExRateByOrgId(Long orgId, Long branch) throws ApplicationException {

		List<DailyExchangeRateVO> dailyExchangeRateVO = dailyExchangeRateRepo.findByOrgIdAndBranch(orgId, branch);

		if (dailyExchangeRateVO.isEmpty()) {
			throw new ApplicationException("No  Daily Exchange Master Details Found");
		}

		return dailyExchangeRateVO;
	}
	// dropdown for currency field
	
	@Override
	public Map<String, Object> getCurrency(Long orgId)
	        throws ApplicationException {

	    List<Object[]> currencyList = currencyRepo.getCurrency(orgId);

	    if (currencyList.isEmpty()) {
	        throw new ApplicationException("No Currency Found");
	    }

	    return getCurrencyResponse(currencyList);
	}
	private Map<String, Object> getCurrencyResponse(List<Object[]> currencyList) {

	    Map<String, Object> response = new HashMap<>();

	    List<Map<String, Object>> currencyDropdown = new ArrayList<>();

	    for (Object[] row : currencyList) {

	        Map<String, Object> map = new HashMap<>();
	        map.put("id", row[0]);
	        map.put("currency", row[1]);
	        map.put("mainCurrencySymbol", row[2]);

	        currencyDropdown.add(map);
	    }

	    response.put("currencyList", currencyDropdown);

	    return response;
	}

	
	

	  
}