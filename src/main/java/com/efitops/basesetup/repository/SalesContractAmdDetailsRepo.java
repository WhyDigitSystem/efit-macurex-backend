package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SalesContractAmdDetailsVO;
import com.efitops.basesetup.entity.SalesContractAmendmentVO;
import com.efitops.basesetup.entity.SalesContractDetailsVO;

public interface SalesContractAmdDetailsRepo extends JpaRepository<SalesContractAmdDetailsVO, Long>{

	List<SalesContractAmdDetailsVO> findBySalesContractAmendmentVO(SalesContractAmendmentVO salesContractAmendmentVO);

	
	
}

