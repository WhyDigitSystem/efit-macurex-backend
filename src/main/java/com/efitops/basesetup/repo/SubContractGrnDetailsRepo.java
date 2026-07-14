package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractGrnDetailsVO;
import com.efitops.basesetup.entity.SubContractGrnVO;

@Repository
public interface SubContractGrnDetailsRepo extends JpaRepository<SubContractGrnDetailsVO, Long> {

	List<SubContractGrnDetailsVO> findBySubContractGrnVO(SubContractGrnVO subContractGrnVO);

}
