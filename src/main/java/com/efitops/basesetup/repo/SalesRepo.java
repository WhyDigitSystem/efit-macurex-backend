package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesVO;

@Repository
public interface SalesRepo extends JpaRepository<SalesVO, Long> {

	@Query(nativeQuery = true, value = "select * from  sales where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<SalesVO> getAllSalesByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from sales  where salesid=?1")
	SalesVO getSalesById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSalesDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    a.partyname,\r\n"
			+ "    a.partycode,\r\n"
			+ "    a.currency,\r\n"
			+ "\r\n"
			+ "    CONCAT(\r\n"
			+ "        COALESCE(a1.addressline1, ''), ' ',\r\n"
			+ "        COALESCE(a1.addressline2, ''), ' ',\r\n"
			+ "        COALESCE(a1.addressline3, ''), ' ',\r\n"
			+ "        COALESCE(a1.city, ''), ' ',\r\n"
			+ "        COALESCE(a1.pincode, '')\r\n"
			+ "    ) AS address,\r\n"
			+ "\r\n"
			+ "    s.contactperson,\r\n"
			+ "    s.email,\r\n"
			+ "    d1.sellingexrate\r\n"
			+ "\r\n"
			+ "FROM partymaster a\r\n"
			+ "\r\n"
			+ "JOIN partyaddress a1\r\n"
			+ "    ON a.partymasterid = a1.partymasterid\r\n"
			+ "\r\n"
			+ "JOIN partystate s\r\n"
			+ "    ON a.partymasterid = s.partymasterid\r\n"
			+ "\r\n"
			+ "JOIN dailymonthlyexrates d\r\n"
			+ "    ON a.orgid = d.orgid\r\n"
			+ "\r\n"
			+ "/* ✅ Join only latest exchange rate */\r\n"
			+ "JOIN dailymonthlyexratesdtl d1\r\n"
			+ "    ON d1.dailymonthlyexratesid = d.dailymonthlyexratesid\r\n"
			+ "    AND d1.currency = a.currency\r\n"
			+ "\r\n"
			+ "JOIN (\r\n"
			+ "    SELECT \r\n"
			+ "        currency, \r\n"
			+ "        MAX(modifiedon) AS max_modifiedon\r\n"
			+ "    FROM dailymonthlyexratesdtl\r\n"
			+ "    GROUP BY currency\r\n"
			+ ") latest\r\n"
			+ "    ON latest.currency = d1.currency\r\n"
			+ "    AND latest.max_modifiedon = d1.modifiedon\r\n"
			+ "\r\n"
			+ "WHERE \r\n"
			+ "    a.active = 1\r\n"
			+ "    AND a.partytype = 'CUSTOMER'\r\n"
			+ "    AND a.orgid = ?1\r\n"
			+ "   AND a.finyear = ?2\r\n"
			+ "    AND a.branchCode = ?3\r\n"
			+ "    AND a1.addresstype = 'BILLING'\r\n"
			+ "ORDER BY \r\n"
			+ "    a.partyname")
	Set<Object[]> findByCustomerNameFromPartyMasterSalesOrder(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select concat(a1.addressline1,' ',a1.addressline2,' ',a1.addressline3,' ',a1.city,' ',a1.pincode)as  address , a1.state,a.partyname from 	\n"
			+ "			      partymaster a,partyaddress a1 where a.partymasterid=a1.partymasterid and a.active = 1 and \n"
			+ "				a1.addresstype='SHIPPING' and a.partytype='CUSTOMER' and a.orgid=?1 and a.finyear=?2 and a.branchcode=?3  and a.partyname=?4 group by address ,state")
	Set<Object[]> findByShippingAddressFromPartyMaster(Long orgId, String finYear, String branchCode,
			String customerName);

	@Query(nativeQuery = true, value = "select distinct a.customerpono from workorder a where a.orgid=?1  and a.finyear=?2 and a.branchcode=?3 and a.customername=?4 and \r\n"
			+ "a.status='PENDING' group by  a.customerpono order by a.customerpono")
	Set<Object[]> findByCustomerPoNoFromWorkOrder(Long orgId, String finYear, String branchCode, String customerName);

	@Query(nativeQuery = true, value = "SELECT\r\n" + "    a.docid,\r\n" + "    a.duedate\r\n" + "FROM workorder a\r\n"
			+ "WHERE a.orgid = ?1\r\n" + "  AND a.finyear = ?2\r\n" + "  AND a.branchcode = ?3\r\n"
			+ "  AND a.customerpono IN (?4)\r\n" + "  AND a.status = 'PENDING'\r\n" + "\r\n" + "  AND EXISTS (\r\n"
			+ "      SELECT 1\r\n" + "      FROM workorderdetails wod\r\n"
			+ "      WHERE wod.workorderid = a.workorderid\r\n" + "        AND (\r\n"
			+ "            SELECT COALESCE(SUM(sip.qtyofferd), 0)\r\n" + "            FROM sales s\r\n"
			+ "            JOIN salesitemparticulars sip\r\n" + "                 ON sip.salesid = s.salesid\r\n"
			+ "            WHERE s.orgid = a.orgid\r\n" + "              AND s.finyear = a.finyear\r\n"
			+ "              AND s.branchcode = a.branchcode\r\n" + "              AND sip.workorderno = a.docid\r\n"
			+ "              AND sip.partno = wod.partno\r\n" + "        ) < wod.ordqty\r\n" + "  )\r\n" + "\r\n"
			+ "GROUP BY a.docid, a.duedate\r\n" + "ORDER BY a.docid;\r\n" + "")
	Set<Object[]> findByWorkOrderNo(Long orgId, String finYear, String branchCode, List<String> customerPoList);

	@Query(nativeQuery = true, value = "select a1.contactperson,a1.city from partymaster a, partyaddress a1 where\r\n"
			+ " a.partymasterid=a1.partymasterid and a1.addresstype='BILLING' and a.orgid=?1  and a.finyear=?2 and a.branchcode=?3 and  a.partycode=?4 group by\r\n"
			+ " a1.contactperson,a1.city order by a1.contactperson")
	Set<Object[]> findByContactPersonFromPartyMaster(Long orgId, String finYear, String branchCode,
			String customerCode);

	@Query(nativeQuery = true, value = "select case when a.country='INDIA' then 'LOCAL' else 'EXPORT' end as invoiceType from \r\n"
			+ " partymaster a where a.orgid=?1 and a.finyear=?2 and a.branchcode=?3  and a.partycode=?4 and a.currency=?5")
	Set<Object[]> findByInvoiceType(Long orgId, String finYear, String branchCode, String customerCode,
			String currency);

//	@Query(nativeQuery = true, value = "SELECT\r\n"
//			+ "    a2.partno,\r\n"
//			+ "    a2.partname,\r\n"
//			+ "    a1.docid,\r\n"
//			+ "    a1.duedate,\r\n"
//			+ "    a2.ordqty,\r\n"
//			+ "    a3.price,"
//			+ "    a1.customerpono \r\n"
//			+ "FROM workorder a1\r\n"
//			+ "JOIN workorderdetails a2\r\n"
//			+ "    ON a1.workorderid = a2.workorderid\r\n"
//			+ "JOIN item a\r\n"
//			+ "    ON a.itemname = a2.partno\r\n"
//			+ "   AND a.orgid = a1.orgid\r\n"
//			+ "JOIN itempriceslab a3\r\n"
//			+ "    ON a3.itemid = a.itemid\r\n"
//			+ "WHERE a1.orgid = ?1\r\n"
//			+ "  AND a1.finyear = ?2\r\n"
//			+ "  AND a1.branchcode = ?3\r\n"
//			+ "  AND a1.docid IN (?4)\r\n"
//			+ "\r\n"
//			+ "  -- ✅ EXCLUDE ITEMS ALREADY SAVED IN SALES ORDER\r\n"
//			+ " AND NOT EXISTS (\r\n"
//			+ "    SELECT 1\r\n"
//			+ "    FROM sales s\r\n"
//			+ "    JOIN salesitemparticulars sip\r\n"
//			+ "         ON sip.salesid = s.salesid\r\n"
//			+ "    WHERE s.orgid = a1.orgid\r\n"
//			+ "      AND s.finyear = a1.finyear\r\n"
//			+ "      AND s.branchcode = a1.branchcode\r\n"
//			+ "      AND sip.workorderno = a1.docid\r\n"
//			+ "      AND sip.partno = a2.partno\r\n"
//			+ ")\r\n"
//			+ "\r\n"
//			+ "\r\n"
//			+ "GROUP BY\r\n"
//			+ "    a2.partno,\r\n"
//			+ "    a2.partname,\r\n"
//			+ "    a1.docid,\r\n"
//			+ "    a1.duedate,\r\n"
//			+ "    a2.ordqty,\r\n"
//			+ "    a3.price, a1.customerpono\r\n"
//			+ "ORDER BY a2.partno")
//	Set<Object[]> findByPartNoAndDescFromWorkOrder(Long orgId, String finYear, String branchCode, List<String> workOrderList);

	@Query(nativeQuery = true, value = "SELECT\r\n" + "    a2.partno,\r\n" + "    a2.partname,\r\n"
			+ "    a1.docid,\r\n" + "    a1.duedate,\r\n" + "    a2.ordqty,\r\n" + "    a3.price,\r\n"
			+ "    a1.customerpono\r\n" + "FROM workorder a1\r\n" + "JOIN workorderdetails a2\r\n"
			+ "    ON a1.workorderid = a2.workorderid\r\n" + "JOIN item a\r\n" + "    ON a.itemname = a2.partno\r\n"
			+ "   AND a.orgid = a1.orgid\r\n" + "JOIN itempriceslab a3\r\n" + "    ON a3.itemid = a.itemid\r\n"
			+ "WHERE a1.orgid = ?1\r\n" + "  AND a1.finyear = ?2\r\n" + "  AND a1.branchcode = ?3\r\n"
			+ "  AND a1.docid IN (?4)\r\n" + "\r\n" + "  /* ✅ INCLUDE ONLY IF SOLD QTY < ORDER QTY */\r\n"
			+ "  AND (\r\n" + "        SELECT COALESCE(SUM(sip.qtyofferd), 0)\r\n" + "        FROM sales s\r\n"
			+ "        JOIN salesitemparticulars sip\r\n" + "             ON sip.salesid = s.salesid\r\n"
			+ "        WHERE s.orgid = a1.orgid\r\n" + "          AND s.finyear = a1.finyear\r\n"
			+ "          AND s.branchcode = a1.branchcode\r\n" + "          AND sip.workorderno = a1.docid\r\n"
			+ "          AND sip.partno = a2.partno\r\n" + "      ) < a2.ordqty\r\n" + "\r\n" + "GROUP BY\r\n"
			+ "    a2.partno,\r\n" + "    a2.partname,\r\n" + "    a1.docid,\r\n" + "    a1.duedate,\r\n"
			+ "    a2.ordqty,\r\n" + "    a3.price,\r\n" + "    a1.customerpono\r\n" + "ORDER BY a2.partno;\r\n" + "")
	Set<Object[]> findByPartNoAndDescFromWorkOrder(Long orgId, String finYear, String branchCode,
			List<String> workOrderList);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    CASE \r\n" + "        WHEN p.country = 'INDIA' THEN\r\n"
			+ "            CASE \r\n" + "                WHEN p1.state = b.state THEN 'INTRA'\r\n"
			+ "                ELSE 'INTER'\r\n" + "            END\r\n" + "        ELSE 'NIL'\r\n"
			+ "    END AS TaxType\r\n" + "FROM partymaster p\r\n" + "JOIN partyaddress p1 \r\n"
			+ "    ON p.partymasterid = p1.partymasterid\r\n" + "JOIN branch b\r\n" + "    ON b.orgid = p.orgid\r\n"
			+ "   AND b.branchcode = ?2\r\n" + "WHERE p.orgid = ?1\r\n" + "  AND p.partycode = ?3 and p.partyType=?4")
	Set<Object[]> findByTaxType(Long orgId, String branchCode, String customerCode, String partyType);

	@Query(nativeQuery = true, value = "SELECT\r\n" + "    i.itemname,\r\n" + "    i1.taxslab,\r\n"
			+ "    g.gstpercentage,\r\n" + "\r\n" + "    CASE \r\n" + "        WHEN UPPER(?2) = 'INR'\r\n"
			+ "         AND UPPER(?4) = 'INTRA'\r\n" + "        THEN g.cgstpercentage\r\n" + "        ELSE 0\r\n"
			+ "    END AS cgstpercentage,\r\n" + "\r\n" + "    CASE \r\n" + "        WHEN UPPER(?2) = 'INR'\r\n"
			+ "         AND UPPER(?4) = 'INTRA'\r\n" + "        THEN g.sgstpercentage\r\n" + "        ELSE 0\r\n"
			+ "    END AS sgstpercentage,\r\n" + "\r\n" + "    CASE \r\n" + "        WHEN UPPER(?2) = 'INR'\r\n"
			+ "         AND UPPER(?4) = 'INTER'\r\n" + "        THEN g.igstpercentage\r\n" + "        ELSE 0\r\n"
			+ "    END AS igstpercentage\r\n" + "\r\n" + "FROM item i\r\n" + "JOIN itemtaxslab i1\r\n"
			+ "    ON i.itemid = i1.itemid\r\n" + "JOIN gst g\r\n" + "    ON g.gstslab = i1.taxslab\r\n"
			+ "   AND g.orgid = i.orgid\r\n" + "WHERE i.itemname = ?3\r\n" + "  AND i.orgid = ?1")
	Set<Object[]> findByGstForSalesOrder(Long orgId, String currency, String item, String taxType);

	@Query(nativeQuery = true, value = "SELECT\n"
			+ "a.salesid,\n"
			+ "    a.docid,\n"
			+ "    a.docdate,\n"
			+ "    a.contactperson,\n"
			+ "    a.currency,\n"
			+ "    a.customercode,\n"
			+ "    a.customermail,\n"
			+ "    a.customername,\n"
			+ "    a.customerpono,\n"
			+ "    a.billingaddress,\n"
			+ "    a.placeofsupply,\n"
			+ "	a.shippingaddress,\n"
			+ "    a.workordeno,\n"
			+ "    b.partno,\n"
			+ "    b.partdesc,\n"
			+ "    b.unitprice,\n"
			+ "    b.qtyofferd,\n"
			+ "    b.basicamount,\n"
			+ "    b.igst,\n"
			+ "    b.cgst,\n"
			+ "    b.sgst,\n"
			+ "    b.taxamount,\n"
			+ "    b.discount,\n"
			+ "    b.basicamount*b.discount/100 as discountamount,\n"
			+ "    b.amount + b.taxamount AS totalAmount\n"
			+ "FROM sales a,\n"
			+ "     salesitemparticulars b\n"
			+ "WHERE a.salesid = b.salesid\n"
			+ "  AND a.orgid = ?1\n"
			+ "  AND (a.customername = ?2 OR ?2 = 'ALL')\n"
			+ "  AND (?3 IS NULL OR a.docdate >= ?3)\n"
			+ "  AND (?4 IS NULL OR a.docdate <= ?4)\n"
			+ "  AND (a.branchcode = ?5 OR ?5 = 'ALL')\n"
			+ "ORDER BY a.createdon DESC")
	Set<Object[]> getSalesOrderDetails(Long orgId, String customerName,String fromDate,String toDate,String branchCode);
	
	@Query(nativeQuery = true, value = "SELECT\n"
			+ "a.salesid,\n"
			+ "    a.docid,\n"
			+ "    a.docdate,\n"
			+ "    a.billingaddress,\n"
			+ "    a.currency,\n"
			+ "    a.customercode,\n"
			+ "    a.customername,\n"
			+ "    a.customermail,\n"
			+ "    a.customerpono,\n"
			+ "    a.invoicetype,\n"
			+ "    a.placeofsupply,\n"
			+ "    a.shippingaddress,\n"
			+ "    a.taxtype,\n"
			+ "    a.grossamount,\n"
			+ "    a.totaltaxamount,\n"
			+ "    a.netamount,\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.taxtype = 'INTER' THEN a.totaltaxamount\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS igst,\n"
			+ "\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.taxtype = 'INTRA' THEN a.totaltaxamount / 2\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS cgst,\n"
			+ "\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.taxtype = 'INTRA' THEN a.totaltaxamount / 2\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS sgst\n"
			+ "FROM sales a\n"
			+ "WHERE a.orgid = ?1\n"
			+ "  AND (a.customername = ?2 OR ?2 = 'ALL')\n"
			+ "  AND (?3 IS NULL OR a.docdate >= ?3)\n"
			+ "  AND (?4 IS NULL OR a.docdate <= ?4)\n"
			+ "  AND (a.branchcode = ?5 OR ?5 = 'ALL')\n"
			+ "ORDER BY a.createdon DESC")
	Set<Object[]> getSalesOrderSummaryDetails(Long orgId, String customerName,String fromDate,String toDate,String branchCode);


}
