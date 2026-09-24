package com.efitops.basesetup.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionSchOrderShortCloseVO;

@Repository
public interface ProductionSchOrderShortCloseRepo extends JpaRepository<ProductionSchOrderShortCloseVO, Long> {

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
    String getProductionSchOrderShortCloseDocId(Long orgId, String financialYear, String screenCode);

    @Query(nativeQuery = true, value = "select * from production_sch_order_short_close_basic where production_sch_order_short_close_basic_id=?1 and active=1 and cancel=0")
    ProductionSchOrderShortCloseVO getProductionSchOrderShortCloseById(Long id);

    @Query(nativeQuery = true, value = "select * from production_sch_order_short_close_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<ProductionSchOrderShortCloseVO> getProductionSchOrderShortCloseByOrgId(Long orgId, Long branch);
    
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,u.unitmaster_id,u.unit_id from item i left join unitmaster u on u.unitmaster_id=i.primary_unit\r\n"
			+ " where i.org_id=?1 and i.branch=?2")
	Set<Object[]> getItemDetailsFromProductionShortClose(Long orgId, Long branch);
	
	
	@Query(nativeQuery = true, value = "select p.doc_id,p.doc_date,sum(p1.qty_required) as qty from production_schedule_order_basic p join production_schedule_order_details p1 on\r\n"
			+ " p.production_schedule_order_basic_id=p1.production_schedule_order_basic_id where p.org_id=?1 and p.branch=?2 group by \r\n"
			+ " p.doc_id,p.doc_date")
	Set<Object[]> getSchOrderNoProductionShortClose(Long orgId, Long branch);
}
