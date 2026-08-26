package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DirectPurchaseVO;

@Repository
public interface DirectPurchaseRepo extends JpaRepository<DirectPurchaseVO, Long> {

	@Query(nativeQuery = true, value = "select * from direct_purchase_basic where direct_purchase_basic_id=?1 and active=1 and cancel=0")
	DirectPurchaseVO getDirectPurchaseById(Long id);

	@Query(nativeQuery = true, value = "select * from direct_purchase_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<DirectPurchaseVO> getDirectPurchaseByOrgId(Long orgId, Long branch);

	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getDirectPurchaseDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select concat(departmen_tname,' -- ','BOSCH') as issueTo from department where org_id=?1 and branch=?2\r\n"
			+ "union\r\n"
			+ "select concat(departmen_tname,' -- ','APPLIANCES') as issueTo from department where org_id=?1 and branch=?2")
	Set<Object[]> getIssueTo(Long orgId, Long branch);

	
	@Query(nativeQuery = true, value = "select item_id,item_code from item\r\n"
			+ "where org_id=?1 and branch=?2 and item_type=?3\r\n"
			+ "order by 1")
	Set<Object[]> getItemType(Long orgId, Long branch, Long itemType);

}
