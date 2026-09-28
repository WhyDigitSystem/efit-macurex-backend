package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionEntryVO;

@Repository
public interface ProductionEntryRepo extends JpaRepository<ProductionEntryVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getProductionEntryDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from production_entry_basic where production_entry_basic_id=?1 and active=1 and cancel=0")
	ProductionEntryVO getProductionEntryById(Long id);

	@Query(nativeQuery = true, value = "select * from production_entry_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<ProductionEntryVO> getProductionEntryByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select doc_id,doc_date from production_schedule_order_basic where org_id=?1 and branch=?2 and\r\n"
			+ " fg_item=?3  and active=1 and cancel=0 group by doc_id,doc_date")
	Set<Object[]> getSchNoFromProductionEntry(Long orgId, Long branch, Long fgItem);

	@Query(nativeQuery = true, value = "select doc_id,doc_date,bill_of_material_id from bill_of_material where org_id=?1 and branch=?2")
	Set<Object[]> getBomNoFromProductionEntry(Long orgId, Long branch);
}