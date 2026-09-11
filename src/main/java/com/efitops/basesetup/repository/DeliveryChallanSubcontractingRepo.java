package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChallanSubcontractingVO;

@Repository
public interface DeliveryChallanSubcontractingRepo extends JpaRepository<DeliveryChallanSubcontractingVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getDeliveryChallanSubcontractingDocId(Long orgId, String financialYear, String screenCode);


	@Query(" SELECT d FROM DeliveryChallanSubcontractingVO d WHERE d.orgId = :orgId AND d.branch.id = :branch  and cancel=0 ORDER BY d.id DESC ") 
	List<DeliveryChallanSubcontractingVO> findAllByOrgIdAndBranch( @Param("orgId") Long orgId, @Param("branch") Long branch);
}
