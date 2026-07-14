package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RecieveFromSubContractDetailsVO;
import com.efitops.basesetup.entity.RecieveFromSubcontractVO;

@Repository
public interface RecieveFromSubcontractDetailsRepo extends JpaRepository<RecieveFromSubContractDetailsVO, Long> {


	List<RecieveFromSubContractDetailsVO> findByRecieveFromSubcontractVO(
			RecieveFromSubcontractVO recieveFromSubcontractVO);

}
