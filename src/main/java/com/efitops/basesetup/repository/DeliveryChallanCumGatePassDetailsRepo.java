package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChallanCumGatePassDetailsVO;

@Repository
public interface DeliveryChallanCumGatePassDetailsRepo extends JpaRepository<DeliveryChallanCumGatePassDetailsVO, Long>{


	List<DeliveryChallanCumGatePassDetailsVO> findByDeliveryChallanCumGatePassVO(Long id);

}
