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

import com.efitops.basesetup.dto.HolidayMasterDTO;
import com.efitops.basesetup.dto.HolidayMasterDetailsDTO;
import com.efitops.basesetup.dto.MappingDetailsDTO;
import com.efitops.basesetup.dto.MappingOfPartyToAccDTO;
import com.efitops.basesetup.dto.TSBankDTO;
import com.efitops.basesetup.dto.TaxDefinitionDTO;
import com.efitops.basesetup.dto.TaxDefinitionDetailsDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.HolidayMasterDetailsVO;
import com.efitops.basesetup.entity.HolidayMasterVO;
import com.efitops.basesetup.entity.ListOfValuesVO;
import com.efitops.basesetup.entity.MappingDetailsVO;
import com.efitops.basesetup.entity.MappingOfPartyToAccVO;
import com.efitops.basesetup.entity.TSBankVO;
import com.efitops.basesetup.entity.TaxDefinitionDetailsVO;
import com.efitops.basesetup.entity.TaxDefinitionVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.CustomerRepo;
import com.efitops.basesetup.repository.HolidayMasterDetailsRepo;
import com.efitops.basesetup.repository.HolidayMasterRepo;
import com.efitops.basesetup.repository.ListOfValuesRepo;
import com.efitops.basesetup.repository.MappingDetailsRepo;
import com.efitops.basesetup.repository.MappingPartyToAccRepo;
import com.efitops.basesetup.repository.PartyProjection;
import com.efitops.basesetup.repository.TSBankRepo;
import com.efitops.basesetup.repository.TaxDefinitionDetailsRepo;
import com.efitops.basesetup.repository.TaxDefinitionRepo;




@Service
public class TransportMasterServiceImpl implements TransportMasterService {
	
	
	  
}