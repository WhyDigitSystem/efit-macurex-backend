package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.IssuesVO;

public interface IssuesRepo extends JpaRepository<IssuesVO, Long> {

	// =========================================================
	// Get Issues By Organization + Branch
	// =========================================================

	List<IssuesVO> findByOrgIdAndBranch(Long orgId, BranchVO branchVO);

	// =========================================================
	// Issue From Location Dropdown
	// =========================================================

	@Query(value = """
			SELECT
			    l.id,
			    l.location_id,
			    l.location_name
			FROM location l
			LEFT JOIN listofvaluesdetails lov
			    ON lov.listofvaluesdetails_id = l.location_type
			WHERE l.cancel = 0
			  AND l.org_id = :orgId
			  AND l.branch = :branch
			  AND UPPER(lov.value_description) = 'STORES'
			ORDER BY l.location_id
			""", nativeQuery = true)
	List<Object[]> getIssueFromLocationDropdown(@Param("orgId") Long orgId, @Param("branch") Long branch);

	// =========================================================
	// Issue To Location Dropdown
	// =========================================================

	@Query(value = """
			SELECT
			    l.id,
			    l.location_id,
			    l.location_name
			FROM location l
			LEFT JOIN listofvaluesdetails lov
			    ON lov.listofvaluesdetails_id = l.location_type
			WHERE l.cancel = 0
			  AND l.org_id = :orgId
			  AND l.branch = :branch
			  AND UPPER(lov.value_description) <> 'SUB CONTRACTOR'
			  AND l.id <> :issueFrom
			ORDER BY l.location_id
			""", nativeQuery = true)
	List<Object[]> getIssueToLocationDropdown(@Param("orgId") Long orgId, @Param("branch") Long branch,
			@Param("issueFrom") Long issueFrom);

	// =========================================================
	// Issue Indent Number Dropdown
	// =========================================================

	@Query(value = """
			SELECT DISTINCT
			    iib.internal_indent_basic_id,
			    iib.doc_id,
			    iib.doc_date
			FROM internal_indent_basic iib

			INNER JOIN internal_indent_details iid
			    ON iid.internal_indent_basic_id =
			       iib.internal_indent_basic_id

			LEFT JOIN (
			    SELECT
			        ib.indent_no,
			        id.item,
			        SUM(id.qty) AS issued_qty
			    FROM issue_basic ib

			    INNER JOIN issue_detail id
			        ON id.issue_basic_id = ib.issue_basic_id

			    WHERE ib.cancel = 0

			    GROUP BY
			        ib.indent_no,
			        id.item
			) issued
			    ON issued.indent_no = iib.doc_id
			    AND issued.item = iid.item

			WHERE iib.cancel = 0
			  AND iib.org_id = :orgId
			  AND iib.branch = :branch
			  AND iid.required_qty > COALESCE(issued.issued_qty, 0)

			ORDER BY iib.doc_id
			""", nativeQuery = true)
	List<Object[]> getIssueIndentNoDropdown(@Param("orgId") Long orgId, @Param("branch") Long branch);

	// =========================================================
	// Issue Item Code Dropdown
	// =========================================================

	@Query(value = """
			SELECT DISTINCT
			    A.item_id,
			    A.item_code,
			    A.item_description,
			    U.unit_id,
			    A.stock
			FROM internal_indent_basic IIB

			INNER JOIN internal_indent_details IID
			    ON IID.internal_indent_basic_id =
			       IIB.internal_indent_basic_id

			INNER JOIN item A
			    ON A.item_id = IID.item

			INNER JOIN unitmaster U
			    ON A.primary_unit = U.unitmaster_id

			WHERE IIB.cancel = 0
			  AND IIB.org_id = :orgId
			  AND IIB.branch = :branch
			  AND A.cancel = 0
			  AND IIB.doc_id = :indentNo

			ORDER BY A.item_code
			""", nativeQuery = true)
	List<Object[]> getIssueItemCodeDropdown(@Param("orgId") Long orgId, @Param("branch") Long branch,
			@Param("indentNo") String indentNo);

	@Query(nativeQuery = true, value = """
			SELECT CONCAT(prefix, LPAD(last_no, 5, '0')) AS docid
			FROM documenttypemapping_details
			WHERE org_id = ?1
			  AND fin_year = ?2
			  AND screen_code = ?3
			""")
	String getIssuesDocId(Long orgId, String financialYear, String screenCode1);

}