package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FGTransferSlipDetailsVO;
import com.efitops.basesetup.entity.FgTransferSlipVO;

@Repository
public interface FGTransferSlipDetailsRepo extends JpaRepository<FGTransferSlipDetailsVO, Long> {

	List<FGTransferSlipDetailsVO> findByFgTransferSlipVO(FgTransferSlipVO vo);

}
