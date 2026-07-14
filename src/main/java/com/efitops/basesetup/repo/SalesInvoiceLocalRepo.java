package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesInvoiceLocalVO;

@Repository
public interface SalesInvoiceLocalRepo extends JpaRepository<SalesInvoiceLocalVO, Long> {

	@Query(nativeQuery = true, value = "select * from  salesinvoicelocal where orgid=?1 and finyear=?2 and branchcode=?3")
	List<SalesInvoiceLocalVO> getAllSalesInvoiceLocalByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from salesinvoicelocal  where salesinvoicelocalid=?1")
	SalesInvoiceLocalVO getSalesInvoiceLocalById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSalesInvoiceLocalDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.partyname,a1.stategstin,a1.taxtype,a.currency,concat(a1.addressline1,',',a1.addressline2,',',a1.addressline3,',',a1.state,',',a1.pincode)as address,a.partycode\r\n"
			+ " from partymaster a,partyaddress a1 where \r\n"
			+ "a.orgid=?1 and a.partymasterid=a1.partymasterid and upper(a.country='INDIA') and \r\n"
			+ "upper(a1.addresstype='BILLING')  and a.active = 1 group by \r\n"
			+ "            a.partyname,a1.stategstin,a1.taxtype,a.currency,address,a.partycode order by a.partyname")
	Set<Object[]> getpartyNameFromPartyMaster(Long orgId);

	@Query(nativeQuery = true, value = "select concat(a1.addressline1,',',a1.addressline2,',',a1.addressline3,a1.state,',',a1.pincode)as address from\r\n"
			+ " partymaster a,partyaddress a1 where\r\n" + " a.partymasterid=a1.partymasterid and \r\n"
			+ " a.orgid=?1  and a.country='INDIA' and \r\n"
			+ "upper (a1.addresstype='SHIPPING') and a.active = 1 order by address")
	Set<Object[]> getShippingAddressFromPartyMaster(Long orgId);

	@Query(nativeQuery = true, value = "select a.docid,a.salesorderno,a.deliveryplace from packinglist a where\r\n"
			+ " a.orgid=?1 and a.customername=?2 group by a.docid,a.salesorderno,a.deliveryplace order by\r\n"
			+ " a.docid")
	Set<Object[]> getDocIdFromPackingList(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select a1.partno,a1.partdescription,a1.unit,a1.quantity from\r\n"
			+ " packinglist a,packinglistdetails a1 where a.packinglistid=a1.packinglistid and \r\n"
			+ " a.orgid=?1 and a.docid=?2 and a.customername=?3 group by \r\n"
			+ " a1.partno,a1.partdescription,a1.unit,a1.quantity order by  a1.partno")
	Set<Object[]> getItemNameFromPackingList(Long orgId, String packingListNo, String customerName);

	@Query(nativeQuery = true, value = "SELECT \n"
			+ "    p.docid,\n"
			+ "    p.docdate,\n"
			+ "    p.salesorderno,\n"
			+ "    e.currency,\n"
			+ "    p1.gstin,\n"
			+ "    d.sellingexrate,\n"
			+ "\n"
			+ "    MAX(\n"
			+ "        CASE \n"
			+ "            WHEN UPPER(p2.addresstype) = 'BILLING' \n"
			+ "            THEN CONCAT(\n"
			+ "                p2.addressline1, ', ',\n"
			+ "                p2.addressline2, ', ',\n"
			+ "                p2.addressline3\n"
			+ "            )\n"
			+ "        END\n"
			+ "    ) AS billingaddress,\n"
			+ "\n"
			+ "    MAX(\n"
			+ "        CASE \n"
			+ "            WHEN UPPER(p2.addresstype) = 'SHIPPING' \n"
			+ "            THEN CONCAT(\n"
			+ "                p2.addressline1, ', ',\n"
			+ "                p2.addressline2, ', ',\n"
			+ "                p2.addressline3\n"
			+ "            )\n"
			+ "        END\n"
			+ "    ) AS shippingaddress,\n"
			+ "\n"
			+ "    /* 🔴 TaxType strictly based on BILLING address */\n"
			+ "    CASE \n"
			+ "        WHEN e.country <> 'INDIA' THEN 'NIL'\n"
			+ "        WHEN MAX(\n"
			+ "                CASE \n"
			+ "                    WHEN UPPER(p2.addresstype) = 'BILLING'\n"
			+ "                         AND p2.state = b.state \n"
			+ "                    THEN 1 \n"
			+ "                END\n"
			+ "             ) = 1\n"
			+ "        THEN 'INTRA'\n"
			+ "        ELSE 'INTER'\n"
			+ "    END AS taxtype\n"
			+ "\n"
			+ "FROM packinglist p\n"
			+ "JOIN partymaster e \n"
			+ "    ON p.customername = e.partyname\n"
			+ "JOIN partystate p1 \n"
			+ "    ON p1.partymasterid = e.partymasterid\n"
			+ "JOIN dailymonthlyexratesdtl d \n"
			+ "    ON d.currency = e.currency\n"
			+ "JOIN partyaddress p2 \n"
			+ "    ON p2.partymasterid = e.partymasterid\n"
			+ "JOIN branch b\n"
			+ "    ON b.orgid = p.orgid\n"
			+ "\n"
			+ "WHERE \n"
			+ "    p.orgid = ?1\n"
			+ "    AND p.branchcode =?2\n"
			+ "    AND p.cancel = 0\n"
			+ "    AND p.customername = ?3\n"
			+ "\n"
			+ "GROUP BY \n"
			+ "    p.docid,\n"
			+ "    p.docdate,\n"
			+ "    p.salesorderno,\n"
			+ "    e.currency,\n"
			+ "    p1.gstin,\n"
			+ "    d.sellingexrate,\n"
			+ "    e.country")
	Set<Object[]> getPackListDetails(Long orgId, String branchCode, String customerName);
	
	@Query(nativeQuery = true, value = "select p1.partno,p1.partdesc,p1.unit,p1.qty,s1.unitprice from packinglist p join packinglistdetails p1 on p.packinglistid=p1.packinglistid \n"
			+ "join sales s on s.docid=p.salesorderno and s.docid=p1.salesorderno join salesitemparticulars s1 on s.salesid=s1.salesid where p.orgid=?1 and p.branchcode=?2 and \n"
			+ " p.customername=?3 and p.docid=?4")
	Set<Object[]> getItemPackListDetails(Long orgId, String branchCode, String customerName,String packlistNo);
	
	@Query(value = "select s.orgid,s.docid,s.docdate,s.customername,s.packinglistno,s.salesorderno,\n"
			+ "s1.item,s1.itemdesc,s1.units,s1.avlstkqty,s1.rate,s1.landedvalue,s.salesinvoicelocalid\n"
			+ "from salesinvoicelocal s\n"
			+ "join salesinvoicelocaldetails s1 on s.salesinvoicelocalid = s1.salesinvoicelocalid\n"
			+ "where s.orgid =  ?1    " +
			"AND (?2 IS NULL OR s.docdate >=  ?2) \n" +
	        "AND (?3 IS NULL OR s.docdate <=  ?3)",
	        nativeQuery = true)
	Set<Object[]> getSalesInvoiceLocalDetails(Long orgId, String fromDate, String toDate);
}
