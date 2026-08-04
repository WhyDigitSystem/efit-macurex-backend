package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesContractAmdDetailsVO;
import com.efitops.basesetup.entity.SalesContractAmendmentVO;
import com.efitops.basesetup.entity.SalesContractDetailsVO;
import com.efitops.basesetup.entity.SalesContractVO;

@Repository
public interface SalesContractDetailsRepo extends JpaRepository<SalesContractDetailsVO, Long>{




	List<SalesContractDetailsVO> findBySalesContract(SalesContractVO salesContractVO);

}
