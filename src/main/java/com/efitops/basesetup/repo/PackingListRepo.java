package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PackingListVO;

@Repository
public interface PackingListRepo extends JpaRepository<PackingListVO, Long> {

	@Query(nativeQuery = true, value = "select * from packinglist where orgid=?1 and finyear=?2 and branchcode=?3")
	List<PackingListVO> getAllPackingListByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from packinglist where packinglistid=?1")

	PackingListVO getAllPackingListById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPackingListDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.partyname,a.partycode,\r\n"
			+ "concat(a1.addressline1,',',a1.addressline2,',',a1.addressline3,',',a1.pincode)as\r\n"
			+ " address,a1.city from partymaster a,partyaddress a1 where a.partymasterid=a1.partymasterid and a.country='INDIA' and\r\n"
			+ " a.partytype='CUSTOMER' and a1.addresstype='SHIPPING' and a.orgid=?1 group by \r\n"
			+ " a.partyname,a.partycode, address,city order by a.partyname")
	Set<Object[]> getCustomerNameFromPartyMasterPacking(Long orgId);

	@Query(nativeQuery = true, value = "select a.docid from sales a where a.orgid=?1 and a.customername=?2 group by \r\n"
			+ " a.docid order by a.docid")
	Set<Object[]> getDocIdFromSalesOrderNo(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select a1.partno,a1.partdesc,b.primaryunit,a.docid,a1.qtyofferd from \r\n"
			+ " sales a,salesitemparticulars a1,item b where a.salesid=a1.salesid and a.orgid=b.orgid and\r\n"
			+ " a1.partno=b.itemname and \r\n"
			+ " a.orgid=?1 and a.docid IN( ?2  ) group by a1.partno,a1.partdesc,b.primaryunit,a.docid,a1.qtyofferd order by\r\n"
			+ " a1.partno")
	Set<Object[]> getPartNoFromSalesOrder(Long orgId, List<String> salesOrderList);

	@Query(value = "select packinglistid,orgid,docid,docdate,customername,customeraddress,salesorderno,salesorderdate,deliveryplace,noofpackage\n"
			+ " from packinglist\n" + " where orgid = ?1 " + "and (?2 is null or docdate >= ?2) "
			+ " and  (?3 is null or docdate<= ?3) " + " and (customername= ?4 or 'All' = ?4)\n"
			+ " and (salesorderno = ?5 or 'All' = ?5)", nativeQuery = true)
	Set<Object[]> getPackingListDetails(Long orgId, String fromdate, String todate, String customername,
			String salesorderno);

}
