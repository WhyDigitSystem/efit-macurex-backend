package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesContractAttachVO;
import com.efitops.basesetup.entity.SalesContractVO;

@Repository
public interface SalesContractAttachRepo extends JpaRepository< SalesContractAttachVO, Long>{

	List<SalesContractAttachVO> findBySalesContract(SalesContractVO salesContractVO);

}
