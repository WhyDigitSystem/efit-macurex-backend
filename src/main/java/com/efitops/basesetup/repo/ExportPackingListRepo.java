package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ExportPackingListVO;

@Repository
public interface ExportPackingListRepo extends JpaRepository<ExportPackingListVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM exportpackinglist where orgid=?1 and finyear=?2 and branchcode=?3")
	List<ExportPackingListVO> findExportPackingListByOrgId(Long orgid,String finYear, String branchCode);

	@Query(nativeQuery = true, value = "SELECT * FROM exportpackinglist where exportpackinglistid=?1")
	ExportPackingListVO findExportPackingListById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getExportPackingListDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true,value = "select  a.partyname,a.partycode from partymaster a where  a.partytype='CUSTOMER' and a.country!='INDIA' and  a.orgid=?1 and active=1 and cancel=0 group by \r\n"
			+ " a.partyname,a.partycode order by  a.partyname")
	Set<Object[]> findCustomerNameAndCodeForExportPackingList(Long orgId);

	@Query(nativeQuery = true,value = "SELECT CONCAT(b.addressline1, b.addressline2,b.addressline3) AS customeraddress,b.city,a.country,b.addresstype\r\n"
			+ "			FROM partymaster a join partyaddress b ON a.partymasterid=b .partymasterid where orgid=?1 and a.partycode=?2  and active=1 and cancel=0 group by\r\n"
			+ "		customeraddress,b.city,a.country,addresstype ")
	Set<Object[]> findCustomerDetailsForExportPackingList(Long orgId, String customerCode);

	@Query(nativeQuery = true, value = "SELECT country,countrycode FROM country where orgid=?1 and active=1 and cancel=0 ")
	Set<Object[]> findAllCountryForExportPackingList(Long orgId);

	@Query(nativeQuery = true, value = "SELECT docid FROM sales where orgid=?1 and customercode=?2  and active=1 and cancel=0 group by docid ")
	Set<Object[]> findSalesOrderNoForExportPackingList(Long orgId, String customerCode);

	@Query(nativeQuery = true, value = "SELECT distinct a.itemname, a.itemdesc, a.primaryunit, b.unitprice, b.qtyofferd, a.hsncode,c.docid,c.workordeno,b.customerpono FROM item a \r\n"
			+ "   LEFT JOIN salesitemparticulars b ON b.partno = a.itemname \r\n"
			+ "   LEFT JOIN sales c ON c.salesid = b.salesid \r\n"
			+ "   WHERE c.orgid=?1 and c.docid IN ( ?2 ) "
			+ "AND NOT EXISTS (\r\n"
			+ "        SELECT 1 \r\n"
			+ "        FROM exportpackinglist ex \r\n"
			+ "        WHERE c.docid = ex.salesorderno\r\n"
			+ "        AND ex.orgid = c.orgid\r\n"
			+ "    )\r\n"
			+ "   ORDER BY a.itemname ")
	Set<Object[]> findSalesOrderDetailsForExportPackingList(Long orgId, List<String> salesOrderNos);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    P.PARTYNAME,\r\n"
			+ "    P.PARTYCODE,\r\n"
			+ "    PS.GSTIN,\r\n"
			+ "\r\n"
			+ "    -- BILLING ADDRESS\r\n"
			+ "    MAX(CASE WHEN UPPER(PA.ADDRESSTYPE) = 'BILLING'\r\n"
			+ "        THEN CONCAT(PA.ADDRESSLINE1, ', ', PA.ADDRESSLINE2, ', ', PA.ADDRESSLINE3)\r\n"
			+ "    END) AS BILLINGADDRESS,\r\n"
			+ "\r\n"
			+ "    MAX(CASE WHEN UPPER(PA.ADDRESSTYPE) = 'BILLING' THEN PA.CITY END)    AS BILLINGCITY,\r\n"
			+ "    MAX(CASE WHEN UPPER(PA.ADDRESSTYPE) = 'BILLING' THEN PA.STATE END)   AS BILLINGSTATE,\r\n"
			+ "    MAX(CASE WHEN UPPER(PA.ADDRESSTYPE) = 'BILLING' THEN PA.PINCODE END) AS BILLINGPINCODE,\r\n"
			+ "\r\n"
			+ "    -- SHIPPING ADDRESS\r\n"
			+ "    MAX(CASE WHEN UPPER(PA.ADDRESSTYPE) = 'SHIPPING'\r\n"
			+ "        THEN CONCAT(PA.ADDRESSLINE1, ', ', PA.ADDRESSLINE2, ', ', PA.ADDRESSLINE3)\r\n"
			+ "    END) AS SHIPPINGADDRESS,\r\n"
			+ "\r\n"
			+ "    MAX(CASE WHEN UPPER(PA.ADDRESSTYPE) = 'SHIPPING' THEN PA.CITY END)    AS SHIPPINGCITY,\r\n"
			+ "    MAX(CASE WHEN UPPER(PA.ADDRESSTYPE) = 'SHIPPING' THEN PA.STATE END)   AS SHIPPINGSTATE,\r\n"
			+ "    MAX(CASE WHEN UPPER(PA.ADDRESSTYPE) = 'SHIPPING' THEN PA.PINCODE END) AS SHIPPINGPINCODE,\r\n"
			+ "\r\n"
			+ "    P.COUNTRY\r\n"
			+ "\r\n"
			+ "FROM PARTYMASTER P\r\n"
			+ "JOIN PARTYSTATE PS\r\n"
			+ "    ON P.PARTYMASTERID = PS.PARTYMASTERID\r\n"
			+ "JOIN PARTYADDRESS PA\r\n"
			+ "    ON P.PARTYMASTERID = PA.PARTYMASTERID\r\n"
			+ "\r\n"
			+ "WHERE P.ORGID = ?1\r\n"
			+ "  AND P.PARTYCODE = ?2\r\n"
			+ "\r\n"
			+ "GROUP BY\r\n"
			+ "    P.PARTYNAME,\r\n"
			+ "    P.PARTYCODE,\r\n"
			+ "    PS.GSTIN,\r\n"
			+ "    P.COUNTRY \r\n"
			+ " ")
	Set<Object[]> getCustomerDetailsForExportPackingListReport(Long orgId, String CustomerCode);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    CONCAT(\r\n"
			+ "        'PO NO. ',\r\n"
			+ "        c.docid,\r\n"
			+ "        ' Dated ',\r\n"
			+ "        c.docdate\r\n"
			+ "    ) AS article_1,\r\n"
			+ "    i.itemname,\r\n"
			+ "    i.itemdesc,\r\n"
			+ "    'HTS Code' AS hts_code,\r\n"
			+ "    i.primaryunit,\r\n"
			+ "    b.qtyofferd,\r\n"
			+ "	t.weightkg\r\n"
			+ "FROM item i\r\n"
			+ "JOIN salesitemparticulars b\r\n"
			+ "    ON b.partno = i.itemname\r\n"
			+ "JOIN sales c\r\n"
			+ "    ON c.salesid = b.salesid\r\n"
			+ "JOIN exportpackinglistdetails t\r\n"
			+ "    ON b.partno = t.partno\r\n"
			+ "JOIN exportpackinglist t1\r\n"
			+ "    ON t.exportpackinglistid = t1.exportpackinglistid\r\n"
			+ "WHERE FIND_IN_SET(c.docid, ?2) > 0\r\n"
			+ "and t1.docid= ?3 and t1.orgid=?1")
	Set<Object[]> getItemDetailsFromExportPackingListReport(Long orgId, String salesOrderNo,
			String exportPackingListDocid);

	
//	@Query(nativeQuery = true, value = "select e.exportpackinglistid,e.docid,e.orgid,e.salesorderno,e.customername,e1.customerpoitem,e1.custpo,e1.partno,e1.partdesc\r\n"
//			+ ",e1.poquantity,e1.quantity from exportpackinglist e\r\n"
//			+ "join exportpackinglistdetails e1 on e1.exportpackinglistid = e.exportpackinglistid\r\n"
//			+ "where orgid = ?1 and  (?2 is null or e.docid >= ?2) "
//			+ " and (?3 is null or e.docid <= ?3)  and e.customername = ?4 and e.salesorderno = ?5 ")
//	List<ExportPackingListVO> getExportPackingListDetails(Long orgid,String fromdate,String todate, String customername,String salesorderno);
//
//	Set<Object[]> getExportPackingListReport(Long orgId, String fromdate, String todate, String customername,
//			String salesorderno);
	
	@Query(
		    nativeQuery = true,
		    value =
		        "SELECT " +
		        " e.exportpackinglistid, " +
		        " e.docid, " +
		        " e.orgid, " +
		        " e.salesorderno, " +
		        " e.customername, " +
		        " e1.customerpoitem, " +
		        " e1.custpo, " +
		        " e1.partno, " +
		        " e1.partdesc, " +
		        " e1.poquantity, " +
		        " e1.quantity " +
		        "FROM exportpackinglist e " +
		        "JOIN exportpackinglistdetails e1 " +
		        " ON e1.exportpackinglistid = e.exportpackinglistid " +
		        "WHERE e.orgid = ?1 " +
		        "AND (?2 IS NULL OR e.docdate >= ?2) " +
		        "AND (?3 IS NULL OR e.docdate <= ?3) " +
		        "AND ( ?4 = 'All' OR e.customername = ?4 ) " +
		        "AND ( ?5 = 'All' OR e.salesorderno = ?5 ) " +
		        "ORDER BY e.docdate DESC, e.exportpackinglistid"
		)
		List<Object[]> getExportPackingListReport(
		    Long orgId,
		    String fromdate,
		    String todate,
		    String customername,   
		    String salesorderno
		);

}
