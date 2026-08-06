package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.OrderAcceptanceVO;
import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface OrderAcceptanceRepo extends JpaRepository<OrderAcceptanceVO, Long> {

	@Query(nativeQuery = true, value = "select * from orderacceptance where orderacceptance_id=?1 and active=1 and cancel=0")
	OrderAcceptanceVO getOrderAcceptanceById(Long id);

	QuotationVO findByDocId(String docId);

	@Query(nativeQuery = true, value = "select * from orderacceptance where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<OrderAcceptanceVO> getQuotationByOrgId(Long orgId, Long branchId);

}
