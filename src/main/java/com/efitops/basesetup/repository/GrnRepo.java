package com.efitops.basesetup.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnVO;

@Repository
public interface GrnRepo extends JpaRepository<GrnVO, Long> {

	@Query(nativeQuery = true, value = "select * from grn_basic where grn_basic_id=?1 and active=1 and cancel=0")
	GrnVO getGrnById(Long id);

	@Query(nativeQuery = true, value = "select * from grn_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<GrnVO> getGrnByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getGrnDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    c.customer_id,\r\n"
			+ "    c.customer_name,\r\n"
			+ "    c.customer_code,\r\n"
			+ "    c.address,\r\n"
			+ "    c.pincode,\r\n"
			+ "    c.gst_no,\r\n"
			+ "    g.state_name,\r\n"
			+ "    c.is_registered\r\n"
			+ "FROM customer_header c\r\n"
			+ "LEFT JOIN listofvaluesdetails l1\r\n"
			+ "    ON c.customer_category = l1.listofvaluesdetails_id\r\n"
			+ "LEFT JOIN listofvaluesdetails l2\r\n"
			+ "    ON c.customer_category1 = l2.listofvaluesdetails_id\r\n"
			+ "LEFT JOIN listofvaluesdetails l3\r\n"
			+ "    ON c.customer_category2 = l3.listofvaluesdetails_id\r\n"
			+ "left JOIN gststatemaster g\r\n"
			+ "    ON g.gststatemaster_id = c.gst_state\r\n"
			+ "WHERE c.org_id = ?1\r\n"
			+ "  AND c.branch = ?2\r\n"
			+ "  AND c.active = 1\r\n"
			+ "  AND c.cancel = 0\r\n"
			+ "  AND (\r\n"
			+ "        l1.value_description = 'Supplier'\r\n"
			+ "        OR l2.value_description = 'Supplier'\r\n"
			+ "        OR l3.value_description = 'Supplier'\r\n"
			+ "      )\r\n"
			+ "ORDER BY c.customer_code")
	Set<Object[]> getSupplierDetailsForGrn(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select multiplication_factor from uomconversion where org_id=?1 and from_unit=?2 and to_unit=?3")
	Set<Object[]> getConversionFactorAmount(Long orgId, BigDecimal poQty, BigDecimal receivedQty);

}
