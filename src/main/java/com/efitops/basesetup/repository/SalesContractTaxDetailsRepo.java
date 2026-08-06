package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesContractTaxDetailsVO;
import com.efitops.basesetup.entity.SalesContractVO;

@Repository
public interface SalesContractTaxDetailsRepo extends JpaRepository<SalesContractTaxDetailsVO, Long>{

	List<SalesContractTaxDetailsVO> findBySalesContract(SalesContractVO salesContractVO);

}
