package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChallanCapitalItemsDetailsVO;
import com.efitops.basesetup.entity.DeliveryChallanCapitalItemsVO;

@Repository
public interface DeliveryChallanCapitalItemsDetailsRepo extends JpaRepository<DeliveryChallanCapitalItemsDetailsVO, Long>{

	List<DeliveryChallanCapitalItemsDetailsVO> findByDeliveryChallanCapitalItemsVO(
			DeliveryChallanCapitalItemsVO deliveryChallanVO);

}
