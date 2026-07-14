package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChalanForFgVO;
import com.efitops.basesetup.entity.DeliveryChallanForFgDetailsVO;

@Repository
public interface DeliveryChallanForFgDetailsRepo extends JpaRepository<DeliveryChallanForFgDetailsVO, Long> {

	List<DeliveryChallanForFgDetailsVO> findByDeliveryChalanForFgVO(DeliveryChalanForFgVO deliveryChalanForFgVO);

}
