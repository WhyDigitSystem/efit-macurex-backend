package com.efitops.basesetup.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.efitops.basesetup.dto.DocumentTypeMappingDTO;
import com.efitops.basesetup.dto.DocumentTypeMappingDetailsDTO;
import com.efitops.basesetup.dto.SalesZoneMasterDTO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;
import com.efitops.basesetup.entity.FinancialYearVO;
import com.efitops.basesetup.entity.SalesZoneMasterVO;
import com.efitops.basesetup.exception.ApplicationException;
import com.efitops.basesetup.repository.BranchRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingDetailsRepo;
import com.efitops.basesetup.repository.DocumentTypeMappingRepo;

import com.efitops.basesetup.repository.FinancialYearRepo;
import com.efitops.basesetup.repository.SalesZoneMasterRepo;


@Service
public class DevelopServiceImpl implements DevelopService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DevelopServiceImpl.class);
	

	
@PersistenceContext
private EntityManager entityManager;


    
   }






