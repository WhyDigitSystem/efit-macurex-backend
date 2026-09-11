package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MaterialIndentForProductionVO;

@Repository
public interface MaterialIndentForProductionRepo extends JpaRepository<MaterialIndentForProductionVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getMaterialIndentForProductionDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from material_indent_for_production where material_indent_for_production_id=?1 and active=1 and cancel=0")
	MaterialIndentForProductionVO getMaterialIndentForProductionById(Long id);

	@Query(nativeQuery = true, value = "select * from material_indent_for_production where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<MaterialIndentForProductionVO> getMaterialIndentForProductionByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select p.doc_id,p.doc_date,p.fg_itme,i.item_code,i.item_description from production_schedule_order_basic p left join item i on p.fg_itme=i.item_id\r\n"
			+ "  where p.org_id=?1 \r\n"
			+ "    and p.branch=?2 and p.active=1 and p.cancel=0")
	Set<Object[]> getFgAndSfgItemDetailsFromMaterial(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select p1.item,i.item_code,i.item_description,p1.qty_required from production_schedule_order_basic p join production_schedule_order_details p1 on p.production_schedule_order_basic_id=p1.production_schedule_order_basic_id  left join item i on p1.item=i.item_id\r\n"
			+ "  where p.org_id=?1 \r\n"
			+ "    and p.branch=?2 and p.fg_itme=?3 and p.active=1 and p.cancel=0")
	Set<Object[]> getFgAndSfgItemDetailsFromMaterialDetails(Long orgId, Long branch, Long fgItem);

}
