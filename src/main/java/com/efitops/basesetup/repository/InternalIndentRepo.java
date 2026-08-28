package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.InternalIndentVO;

public interface InternalIndentRepo extends JpaRepository<InternalIndentVO, Long> {

	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getInternalIndentDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
	        SELECT
	            i.item_id,
	            i.item_code,
	            i.item_description,
	            u.unit_id
	        FROM item i
	        INNER JOIN unitmaster u
	        ON i.primary_unit = unitmaster_id
	        INNER JOIN listofvaluesdetails lov
	            ON i.item_type = lov.listofvaluesdetails_id
	        WHERE i.active = TRUE
	          AND i.cancel = FALSE
	          AND UPPER(lov.value_code) <> 'FG'
	          AND i.branch = :branch
	          AND i.org_id = :orgId
	        ORDER BY i.item_code
	        """, nativeQuery = true)
	List<Object[]> getItemDropdownForInternalIndent(Long branch,Long orgId);
	@Query(value = """
	        SELECT *
	        FROM internal_indent_basic
	        WHERE org_id = ?1
	          AND branch = ?2
	          AND cancel = FALSE
	        ORDER BY internal_indent_basic_id DESC
	        """, nativeQuery = true)
	List<InternalIndentVO> getInternalIndentByOrgId(
	        Long orgId,
	        Long branch);

	
}
