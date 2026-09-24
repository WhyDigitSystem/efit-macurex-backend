package com.efitops.basesetup.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionIssueVO;

@Repository
public interface ProductionIssueRepo extends JpaRepository<ProductionIssueVO, Long> {

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
    String getProductionIssueDocId(Long orgId, String financialYear, String screenCode);

    @Query(nativeQuery = true, value = "select * from production_issue_basic where production_issue_basic_id=?1 and active=1 and cancel=0")
    ProductionIssueVO getProductionIssueById(Long id);

    @Query(nativeQuery = true, value = "select * from production_issue_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<ProductionIssueVO> getProductionIssueByOrgId(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select sch_order_no,doc_id,doc_date from material_indent_for_production   where org_id=?1 and \r\n"
			+ " branch=?2 and fg_item=?3 group by sch_order_no,doc_id,doc_date")
	Set<Object[]> getIndentNoForProductionIssue(Long orgId, Long branch,Long fgItem);
	
	@Query(nativeQuery = true, value = "select g.doc_id,doc_date,g1.accept_qty from grn_basic g join grn_details g1\r\n"
			+ " on g.grn_basic_id=g1.grn_basic_id where g.org_id=?1 and g.branch=?2 and \r\n"
			+ " g1.item=?3 group by g.doc_id,doc_date,g1.accept_qty")
	Set<Object[]> getGrnNoForProductionIssue(Long orgId, Long branch,Long item);
	
	@Query(nativeQuery = true, value = "select m1.item,i.item_code,i.item_description,m1.required_qty,m1.unit,u.unit_id from material_indent_for_production m join material_indent_for_production_details m1 on\r\n"
			+ " m.material_indent_for_production_id=m1.material_indent_for_production_id left join item i on i.item_id=m1.item left join unitmaster u on u.unitmaster_id=m1.unit where m.org_id=?1 and \r\n"
			+ " m.branch=?2 and m.doc_id=?3 group by  m1.item,i.item_code,i.item_description,m1.required_qty,m1.unit,u.unit_id")
	Set<Object[]> getIndentNoDetailsForProductionIssue(Long orgId, Long branch,String indentNo);
}