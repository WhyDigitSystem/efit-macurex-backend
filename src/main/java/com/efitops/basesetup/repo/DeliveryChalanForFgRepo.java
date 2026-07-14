package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChalanForFgVO;

@Repository
public interface DeliveryChalanForFgRepo extends JpaRepository<DeliveryChalanForFgVO, Long> {

	@Query(nativeQuery = true, value = "select * from  deliverychalanforfg where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<DeliveryChalanForFgVO> getAllDeliveryChalanForFgByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from deliverychalanforfg  where deliverychalanforfgid=?1")
	DeliveryChalanForFgVO getDeliveryChalanForFgById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getDeliveryChalanForFgDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.partyname,concat(a1.addressline1,',',a1.addressline2,',',a1.addressline3,a1.state,',',a1.pincode)as address\r\n"
			+ " from partymaster a,partyaddress a1 where a.partymasterid=a1.partymasterid and \r\n"
			+ " a.orgid=?1 and a.partytype='CUSTOMER' and a1.addresstype='BILLING' and a.active = 1 group by a.partyname,concat(a1.addressline1,',',a1.addressline2,',',a1.addressline3,a1.state,',',a1.pincode)\r\n"
			+ " order by a.partyname")
	Set<Object[]> getCustomerNameFromPartyMaster(Long orgId);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a.duedate from sales a,salesitemparticulars a1 where\r\n"
			+ "	 a.salesid=a1.salesid and a.orgid=?1 and a.customername=?2 group by\r\n"
			+ "	a.docid,a.docdate,a.duedate order by a.docid")
	Set<Object[]> getSoNoFromSaleOrder(Long orgId, String customerName);

	@Query(nativeQuery = true, value = "select  a1.itemname,a1.itemdesc,a2.qtyofferd,a1.primaryunit from \r\n"
			+ "  sales a,salesitemparticulars a2,item a1 where a1.itemname=a2.partno and a1.itemType='FG' and \r\n"
			+ " a.customername=?1 and a.customercode=?2 and a.salesid=a2.salesid  group by \r\n"
			+ " a1.itemname,a1.itemdesc,a2.qtyofferd,a1.primaryunit order by a1.itemname")
	Set<Object[]> getItemNameFromSaleOrder(String customerName, String customerCode);

	@Query(nativeQuery = true, value = "select s1.partno,s1.partdesc,s1.qtyofferd,s1.unitprice,s.docid from sales s join salesitemparticulars s1 on  s.salesid=s1.salesid where s.orgid=?1 and s.branchcode=?2 and s.finyear=?3 AND s.docid IN (?4);\r\n"
			+ "")
	Set<Object[]> getItemDetailsforDCFGFromSaleOrder(Long orgId, String branchCode, String finYear,
			List<String> salesOrderNos);

	// Reporting Query
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    dcfg.deliverychalanforfgid,\r\n"
			+ "    dcfg.docid,\r\n"
			+ "    dcfg.docdate,\r\n"
			+ "    dcfg.customername,\r\n"
			+ "    dcfg.customeraddress,\r\n"
			+ "    dcfg.sono,\r\n"
			+ "    dcfg.sodate,\r\n"
			+ "    dcfg.dudate,\r\n"
			+ "    dcfg.vehicletype,\r\n"
			+ "    dcfg.vehicleno,\r\n"
			+ "    dcfg.naration,\r\n"
			+ "    dcfgd.itemno,\r\n"
			+ "    dcfgd.itemdescription,\r\n"
			+ "    dcfgd.quantity,\r\n"
			+ "    dcfgd.unit,\r\n"
			+ "    dcfgd.weight,\r\n"
			+ "    dcfgd.remarks\r\n"
			+ "FROM deliverychalanforfg dcfg\r\n"
			+ " JOIN deliverychallanforfgdetails dcfgd\r\n"
			+ "    ON dcfg.deliverychalanforfgid = dcfgd.deliverychalanforfgid\r\n"
			+ "WHERE dcfg.orgid = ?1\r\n"
			+ "  AND  (?2 is null or dcfg.docdate >= ?2) and (?3 is null or dcfg.docdate <= ?3)\r\n"
			+ "  and (dcfg.sono=?4 or ?4='ALL')")
	Set<Object[]> getDeliveryChallanForFGReport(Long orgId, String fromDate, String toDate,String saleOrderNo);

}
