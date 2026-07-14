package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DcForSubContractDetailsVO;
import com.efitops.basesetup.entity.DcForSubContractVO;

@Repository
public interface DcForSubContractDetailsRepo  extends JpaRepository<DcForSubContractDetailsVO, Long>
{

	List<DcForSubContractDetailsVO> findByDcForSubContractVO(DcForSubContractVO dcForSubContractVO);

}
