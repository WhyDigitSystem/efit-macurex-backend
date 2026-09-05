package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseReturnVO;

@Repository
public interface PurchaseReturnRepo extends JpaRepository<PurchaseReturnVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchase_return_basic where purchase_return_basic_id=?1 and active=1 and cancel=0")
	PurchaseReturnVO getPurchaseReturnById(Long id);

	@Query(nativeQuery = true, value = "select * from purchase_return_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<PurchaseReturnVO> getPurchaseReturnByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseReturnDocId(Long orgId, String financialYear, String screenCode);
}
