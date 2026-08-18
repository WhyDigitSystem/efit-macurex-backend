package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrderVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchase_order_basic where purchase_order_basic_id=?1 and active=1 and cancel=0 and po_type=?2")
	PurchaseOrderVO getPurchaseOrderById(Long id,String type);

	@Query(nativeQuery = true, value = "select * from purchase_order_basic where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<PurchaseOrderVO> getPurchaseOrderByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select multiplication_factor from uomconversion where org_id=?1 and from_unit=?2 and to_unit=?3")
	Set<Object[]> getMutipleFactorAmount(Long orgId, Long primaryUnit, Long purchaseUnit);

	 @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseOrderImportDocId(Long orgId, String financialYear, String screenCode);

	 
	 @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	 String getPurchaseOrderLocalDocId(Long orgId, String financialYear, String screenCode);
	
	

}
