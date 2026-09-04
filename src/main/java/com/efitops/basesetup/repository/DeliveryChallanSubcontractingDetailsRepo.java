package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChallanSubcontractingDetailsVO;
import com.efitops.basesetup.entity.DeliveryChallanSubcontractingVO;

@Repository
public interface DeliveryChallanSubcontractingDetailsRepo extends JpaRepository<DeliveryChallanSubcontractingDetailsVO, Long>{

	List<DeliveryChallanSubcontractingDetailsVO> findByDeliveryChallanSubcontracting(
			DeliveryChallanSubcontractingVO deliveryChallanSubcontractingVO);

	

}
