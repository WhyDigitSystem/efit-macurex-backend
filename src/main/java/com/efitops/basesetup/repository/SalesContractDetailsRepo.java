package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SalesContractAmendmentVO;
import com.efitops.basesetup.entity.SalesContractDetailsVO;

public interface SalesContractDetailsRepo extends JpaRepository<SalesContractDetailsVO, Long>{

	List<SalesContractDetailsVO> findBySalesContractAmendmentVO(SalesContractAmendmentVO salesContractAmendmentVO);

	
	
}
