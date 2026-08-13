package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RejectionInvoiceVO;

@Repository
public interface RejectionInvoiceRepo extends JpaRepository<RejectionInvoiceVO, Long> {
	
	@Query(nativeQuery = true, value = "select * from rejection_invoice_basic where rejection_invoice_basic_id=?1 and active=1 and cancel=0")
	RejectionInvoiceVO getRejectionInvoiceById(Long id);

	@Query(nativeQuery = true, value = "select * from rejection_invoice_basic where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<RejectionInvoiceVO> getRejectionInvoiceByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,4,0)) AS docid from documenttypemapping_details where org_id=?1 and screen_code=?2")
	String getRejectionDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select  c.selling_ex_rate From  dailyexchangerate c,currency cu WHERE c.cancel=0  AND c.currency=cu.currency_id AND cu.currency_id=?2 and cu.org_id=?1")
	Set<Object[]> getExchangeRate(Long orgId, Long currency);

	@Query(nativeQuery = true, value = "select a.rate from gstratemaster a where a.cancel=0 and hsn_sac_code=?2 and org_id=?1")
	Set<Object[]> getTaxPercentage(Long orgId, Long hsn);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    i.item_id,\r\n"
			+ "    i.item_code,\r\n"
			+ "    i.item_description,\r\n"
			+ "    u.unit_id,\r\n"
			+ "    h.hsn,\r\n"
			+ "    null AS customer_part_no,\r\n"
			+ "    d1.desc_qty\r\n"
			+ "FROM despatch_basic d\r\n"
			+ "JOIN despatch_detail d1\r\n"
			+ "    ON d.despatch_basic_id = d1.despatch_basic_id\r\n"
			+ "LEFT JOIN item i\r\n"
			+ "    ON i.item_id = d1.item_id\r\n"
			+ "LEFT JOIN unitmaster u\r\n"
			+ "    ON u.unitmaster_id = i.primary_unit\r\n"
			+ "LEFT JOIN hsn h\r\n"
			+ "    ON h.hsn_id = i.hsn_code\r\n"
			+ "WHERE d.org_id = ?1\r\n"
			+ "  AND d.branch = ?2\r\n"
			+ "  AND d.despatch_basic_id = ?3\r\n"
			+ "\r\n"
			+ "UNION ALL\r\n"
			+ "\r\n"
			+ "SELECT\r\n"
			+ "    i.item_id,\r\n"
			+ "    i.item_code,\r\n"
			+ "    i.item_description,\r\n"
			+ "    u.unit_id,\r\n"
			+ "    h.hsn,\r\n"
			+ "    i.customer_part_no,\r\n"
			+ "    CAST(NULL AS DECIMAL(18,2)) AS desc_qty\r\n"
			+ "FROM item i\r\n"
			+ "INNER JOIN unitmaster u\r\n"
			+ "    ON u.unitmaster_id = i.primary_unit\r\n"
			+ "INNER JOIN hsn h\r\n"
			+ "    ON h.hsn_id = i.hsn_code\r\n"
			+ "WHERE i.cancel = 0\r\n"
			+ "  AND i.org_id = ?1\r\n"
			+ "  AND i.branch = ?2\r\n"
			+ "  AND NOT EXISTS (\r\n"
			+ "      SELECT 1\r\n"
			+ "      FROM despatch_basic d\r\n"
			+ "      WHERE d.org_id = ?1\r\n"
			+ "        AND d.branch = ?2\r\n"
			+ "        AND d.despatch_basic_id = ?3\r\n"
			+ "  )\r\n"
			+ "ORDER BY item_code")
	Set<Object[]> getItemDetailsBasedDesPatch(Long orgId, Long branch, Long despatch);

	
	@Query(nativeQuery = true, value = "select ob.doc_id,ob.doc_date,ob.order_acceptance_basic_id as id\r\n"
			+ "from order_acceptance_basic ob,customer_header p\r\n"
			+ "where  p.customer_id =?1  and ob.cancel=0\r\n"
			+ "union all\r\n"
			+ "select ob.customer_contract_no docid,ob.contract_date docdt,  ob.salescontract_id as id\r\n"
			+ "from  sales_contract_basic ob,customer_header p\r\n"
			+ "where p.customer_id =?1 and ob.cancel='F'\r\n"
			+ "order by  2")
	Set<Object[]> getSalesOrderNo(Long customer);


}
