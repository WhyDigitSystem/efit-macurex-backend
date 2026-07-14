package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.PartyTypeDTO;
import com.efitops.basesetup.entity.PartyTypeVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface PartyTypeService {

	// PartyType

	PartyTypeVO createUpdatePartyType(@Valid PartyTypeDTO partyTypeDTO) throws ApplicationException;

	List<PartyTypeVO> getAllPartyTypeByOrgId(Long orgid);

	List<PartyTypeVO> getPartyTypeById(Long id);

	List<Map<String, Object>> getPartyCodeByOrgIdAndPartyType(Long orgid, String partytype);

}
