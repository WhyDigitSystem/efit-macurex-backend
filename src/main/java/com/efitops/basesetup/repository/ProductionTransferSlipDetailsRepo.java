package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionTransferSlipDetailsVO;
import com.efitops.basesetup.entity.ProductionTransferSlipVO;

@Repository
public interface ProductionTransferSlipDetailsRepo extends JpaRepository<ProductionTransferSlipDetailsVO, Long> {

	List<ProductionTransferSlipDetailsVO> findByProductionTransferSlipVO(ProductionTransferSlipVO vo);

}
