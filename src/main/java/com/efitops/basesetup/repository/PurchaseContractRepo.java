package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseContractVO;

@Repository
public interface PurchaseContractRepo extends JpaRepository<PurchaseContractVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchase_contract_basic where purchase_contract_basic_id=?1")
	PurchaseContractVO getPurchaseContractById(Long id);

	@Query(nativeQuery = true, value = "select * from purchase_contract_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<PurchaseContractVO> getPurchaseContractByOrgId(Long orgId, Long branch);

	@Query(value = """
			SELECT
			    d.item_id,
			    i.item_code,
			    i.item_description
			FROM purchase_contract_details d
			INNER JOIN item i
			        ON d.item_id = i.item_id
			WHERE d.purchasecontract_id = :contractId
			ORDER BY i.item_code
			""", nativeQuery = true)
	List<Object[]> getItemsByContractId(@Param("contractId") Long contractId);

	@Query(value = "SELECT\r\n"
			+ "    c.customer_id,\r\n"
			+ "    c.customer_code,\r\n"
			+ "    c.customer_name\r\n"
			+ "FROM customer_header c\r\n"
			+ "LEFT JOIN listofvaluesdetails a\r\n"
			+ "    ON c.customer_category = a.listofvaluesdetails_id\r\n"
			+ "LEFT JOIN listofvaluesdetails b\r\n"
			+ "    ON c.customer_category1 = b.listofvaluesdetails_id\r\n"
			+ "LEFT JOIN listofvaluesdetails cc\r\n"
			+ "    ON c.customer_category2 = cc.listofvaluesdetails_id\r\n"
			+ "WHERE c.cancel = FALSE\r\n"
			+ "  AND c.active = TRUE\r\n"
			+ "  AND c.branch = :branch\r\n"
			+ "  AND c.org_id = :orgId\r\n"
			+ "  AND (a.value_description = 'Supplier'\r\n"
			+ "        OR b.value_description = 'Supplier'\r\n"
			+ "        OR cc.value_description = 'Supplier'\r\n"
			+ "      )\r\n"
			+ "ORDER BY c.customer_code", nativeQuery = true)
	List<Object[]> getSupplierDropdownForPurchaseContract(@Param("branch") Long branch, @Param("orgId") Long orgId);

	List<PurchaseContractVO> findByBranchIdAndOrgIdAndCancelFalse(Long branch, Long orgId);

	@Query(value = """
			SELECT
			    e.employeemaster_id,
			    e.employee_id,
			    e.emp_name
			FROM employeemaster e
			INNER JOIN department d
			    ON e.department = d.departmentid
			INNER JOIN branch b
			    ON e.branch = b.branch_id
			WHERE e.cancel = FALSE
			  AND e.active = TRUE
			  AND b.branch_id = :branch
			  AND e.org_id = :orgId
			ORDER BY e.emp_name
			""", nativeQuery = true)
	List<Object[]> getEmployeeDropdownPurchaseContract(@Param("branch") Long branch, @Param("orgId") Long orgId);

}