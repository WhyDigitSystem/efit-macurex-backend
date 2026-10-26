package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	List<Object[]> getItemDropdownForInternalIndent(Long branch, Long orgId);

	@Query(value = """
			SELECT *
			FROM internal_indent_basic
			WHERE org_id = ?1
			  AND branch = ?2
			  AND cancel = FALSE
			ORDER BY internal_indent_basic_id DESC
			""", nativeQuery = true)
	List<InternalIndentVO> getInternalIndentByOrgId(Long orgId, Long branch);

	@Query(value = """
			SELECT
			    dm.department_name AS DEPTNAME,
			    ih.doc_id AS DOCID,
			    ih.time_of_indent AS indtime,
			    ih.doc_date AS DOCDATE,
			    ih.prepared_by AS PREBY,
			    i.item_code AS ITEMID,
			    i.item_description AS ITEMDESC,
			    id.required_qty AS QTY,
			    id.purpose AS DELIVERYDATE
			FROM internal_indent_basic ih
			INNER JOIN internal_indent_details id
			    ON ih.internal_indent_basic_id = id.internal_indent_basic_id
			INNER JOIN department dm
			    ON dm.departmentid = ih.department
			INNER JOIN item i
			    ON i.item_id = id.item
			WHERE ih.doc_date BETWEEN :fromDate AND :toDate
			  AND ih.belongs_to = :bel
			  and ih.branch = :branch
			  and ih.org_id = :orgId
			ORDER BY ih.doc_id, ih.doc_date, i.item_code
			""", nativeQuery = true)
	List<Object[]> getInternalIndentReport(@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("bel") String bel,@Param("branch") Long branch , @Param("orgId") Long orgId);
}
