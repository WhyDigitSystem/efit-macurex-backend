package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrderVO, Long> {
	
	@Query(nativeQuery = true,value="select * from purchaseorder where orgid=?1 and finyear=?2 and branchcode=?3")
	List<PurchaseOrderVO> findPurchaseOrderByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true,value="select*from purchaseorder where purchaseorderid=?1")
	List<PurchaseOrderVO> getPurchaseOrderById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPurchaseOrderDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query (nativeQuery = true, value ="SELECT p.contactperson,a.contact,CONCAT(\r\n"
			+ "        COALESCE(a.addressline1, ''), \r\n"
			+ "        COALESCE(a.addressline2, ''), \r\n"
			+ "        COALESCE(a.addressline3, ''), \r\n"
			+ "        COALESCE(a.city, ''), \r\n"
			+ "        COALESCE(a.pincode, ''), \r\n"
			+ "        COALESCE(a.state, '')\r\n"
			+ "    ) AS fulladdress,\r\n"
			+ "    a.stategstin,\r\n"
			+ "--     a.taxtype as taxcode,\r\n"
			+ "    a.state,\r\n"
			+ "    a.pincode,\r\n"
			+ "    a.city\r\n"
			+ "FROM   partyaddress a JOIN partymaster b  ON a.partymasterid = b.partymasterid  \r\n"
			+ "join partystate p on p.partymasterid=a.partymasterid \r\n"
			+ "    WHERE b.cancel = 0  AND b.active = 1  and b.partytype = 'SUPPLIER' and a.addresstype='BILLING' and b.partyname = ?2 and orgid=?1")
	Set<Object[]> findgetSupplierAddressForPurchaseOrder(Long orgId, String supplierName);


	@Query(
		    nativeQuery = true,
		    value =
		        "SELECT DISTINCT docid " +
		        "FROM purchaseindent " +
		        "WHERE TRIM(UPPER('Purchase Indent')) = TRIM(UPPER(?4)) " +
		        "AND orgid = ?1 " +
		        "AND active = 1 " +
		        "AND cancel = 0 " +
		        "AND ( " +
		        "      ( (?2 IS NOT NULL AND ?2 <> '' AND customercode = ?2) " +
		        "        AND (?3 IS NOT NULL AND ?3 <> '' AND workorderno = ?3) ) " +
		        "   OR (customercode IS NULL AND workorderno IS NULL) " +
		        ")"
		)
		Set<String> findgetPurchaseIndentForPurchaseOrder(
		        Long orgId,
		        String customerCode,
		        String workorderno,
		        String basedOn
		);



	@Query (nativeQuery = true, value ="select docid from purchasequotation where  'Quotation'=?4 and customercode = ?2 and workorderno=?3 and orgid=?1 and active=1 and cancel=0")
	Set<Object[]> findgetQuotationForPurchaseOrder(Long orgId, String customerCode, String workorderno, String basedOn);

	@Query (nativeQuery = true, value ="SELECT \r\n"
			+ "  DISTINCT  a1.item,\r\n"
			+ "    a1.itemdesc,\r\n"
			+ "    a1.indentqty,\r\n"
			+ "    a1.uom,\r\n"
			+ "    e.taxslab,\r\n"
			+ "    d.price\r\n"
			+ "FROM purchaseindentdetails a1\r\n"
			+ "JOIN item c \r\n"
			+ "    ON a1.item = c.itemname\r\n"
			+ "JOIN purchaseindent b \r\n"
			+ "    ON a1.purchaseindentid = b.purchaseindentid\r\n"
			+ "JOIN itempriceslab d \r\n"
			+ "    ON c.itemid = d.itemid\r\n"
			+ "JOIN itemtaxslab e \r\n"
			+ "    ON e.itemid = c.itemid\r\n"
			+ "WHERE b.docid = ?2\r\n"
			+ "  AND b.orgid = ?1  \r\n"
			+ "  AND taxeffectivefrom = (\r\n"
			+ "        SELECT MAX(taxeffectivefrom)\r\n"
			+ "        FROM itemtaxslab\r\n"
			+ "        WHERE itemid = e.itemid\r\n"
			+ "      )\r\n"
			+ "  AND priceeffectivefrom = (\r\n"
			+ "        SELECT MAX(priceeffectivefrom)\r\n"
			+ "        FROM itempriceslab\r\n"
			+ "        WHERE itemid = d.itemid\r\n"
			+ "      )\r\n"
			+ "\r\n"
			+ "UNION ALL\r\n"
			+ "\r\n"
			+ "SELECT \r\n"
			+ "   DISTINCT a1.item,\r\n"
			+ "    a1.itemdesc,\r\n"
			+ "    a1.qty,\r\n"
			+ "    a1.unit,\r\n"
			+ "    e.taxslab,\r\n"
			+ "    a1.unitprice\r\n"
			+ "FROM purchasequotationdetails a1\r\n"
			+ "JOIN purchasequotation b \r\n"
			+ "    ON a1.purchasequotationid = b.purchasequotationid\r\n"
			+ "JOIN item c \r\n"
			+ "    ON a1.item = c.itemname\r\n"
			+ "JOIN itemtaxslab e \r\n"
			+ "    ON e.itemid = c.itemid\r\n"
			+ "WHERE b.docid = ?3\r\n"
			+ "  AND b.orgid = ?1\r\n"
			+ "  AND taxeffectivefrom = (\r\n"
			+ "        SELECT MAX(taxeffectivefrom)\r\n"
			+ "        FROM itemtaxslab\r\n"
			+ "        WHERE itemid = c.itemid\r\n"
			+ "      )")
	Set<Object[]> findgetItemForPurchaseOrder(Long orgId, String purchaseIndentNo,String quotationNo);
	

	@Query(value = "select p.customername,p.docid,p1.item,sum(p1.qty) as ordered_qty,p.orgid,\n"
			+ "			coalesce(max(gsum.received_qty), 0) as received_qty,\n"
			+ "			case \n"
			+ "			when coalesce(max(gsum.received_qty), 0) >= sum(p1.qty) then 'Completed'\n"
			+ "			else 'Pending'end as status,\n"
			+ "            p.purchaseorderid,p.suppliername\n"
			+ "			from purchaseorder p\n"
			+ "			join purchaseorderdetails p1\n"
			+ "			     on p.purchaseorderid = p1.purchaseorderid\n"
			+ "			left join (\n"
			+ "			     select g.pono, g1.itemcode, sum(g1.challanqty) as received_qty\n"
			+ "			     from grn g\n"
			+ "			     join grndetails g1 on g.grnid = g1.grnid\n"
			+ "			     group by g.pono, g1.itemcode\n"
			+ "			) gsum\n"
			+ "			on gsum.pono = p.docid and gsum.itemcode = p1.item where\n"
			+ "			(p.suppliername =?2 or ?2='ALL') and p.orgid=?1\n"
			+ "			group by p.customername, p.docid, p1.item,p.orgid, p.purchaseorderid,p.suppliername having (status=?3 or ?3= 'ALL')", nativeQuery = true)
	Set<Object[]> getPurchaseOrderDetails(Long orgId, String supplierName, String status);
}
