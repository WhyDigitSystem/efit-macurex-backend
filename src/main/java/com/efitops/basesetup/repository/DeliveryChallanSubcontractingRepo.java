package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChallanSubcontractingVO;

@Repository
public interface DeliveryChallanSubcontractingRepo extends JpaRepository<DeliveryChallanSubcontractingVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getDeliveryChallanSubcontractingDocId(Long orgId, String financialYear, String screenCode);

}
