package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentVO;

@Repository
public interface PurchaseIndentRepo extends JpaRepository<PurchaseIndentVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM purchaseindent where orgid=?1 and finyear=?2 and branchcode=?3")
	List<PurchaseIndentVO> getAllPurchaseIndentByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "SELECT * FROM purchaseindent where purchaseindentid=?1")
	Optional<PurchaseIndentVO> getPurchaseIndentById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPurchaseIndentByDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT docid FROM purchaseindent where orgid=?1 and screencode=?2")
	String getpurchaseIndentDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT materialtype FROM materialtype where orgid=?1 and active=1")
	Set<Object[]> findIndentType(Long orgId);

	@Query(nativeQuery = true, value = "select distinct partyname,partycode from partymaster where partytype = 'customer' and orgid=?1 and active=1 order by 1")
	Set<Object[]> findCustomerDetails(Long orgId);

	@Query(nativeQuery = true, value = "select  a.employeename from employeemaster a join employeedetails b on a.employeemasterid=b.employeemasterid  where a.orgid=?1 and b.designation='OPERATOR' and active=1 and cancel=0 group by \r\n"
			+ "			a.employeename order by  a.employeename ")
	Set<Object[]> getRequestedByDetails(Long orgId);

	@Query(nativeQuery = true, value = "select departmentname from department where orgid=?1 and  active=1 order by 1")
	Set<Object[]> getDepartmentDetails(Long orgId);

	@Query(nativeQuery = true, value = "select b.itemcode,b.itemdesc,b.uom,b.qty from bom a join bomdetails b where a.bomid = b.bomid and a.orgid=?1 and (a.productcode=?2  or ?2 is null) and active=1 order by 1")
	Set<Object[]> findBomItemDetailsForPurchase(Long orgId, String fgPart);

	@Query(nativeQuery = true, value = "SELECT DISTINCT a.docid FROM workorder a  WHERE a.orgid = ?1 AND a.customercode = ?2 and a.status='PENDING'  AND a.active = 1 AND a.docid NOT IN (SELECT c.workorderno FROM purchaseindent c WHERE c.orgid = ?1) ORDER BY a.docid")
	Set<Object[]> findWorkOrderNoForPurchaseIndent(Long orgId, String customerCode);

	@Query(nativeQuery = true, value = "select  a.employee from employee a where a.orgid=?1 and designation='PRODUCTION MANAGER' and active=1 and cancel=0 group by \r\n"
			+ " a.employee order by  a.employee")
	Set<Object[]> getVerifiedByForPurchase(Long orgId);

	@Query(nativeQuery = true, value = "SELECT DISTINCT  b.partno, b.partname, b.requiredqty, a.customerpono FROM workorder a JOIN workorderdetails b ON a.workorderid = b.workorderid WHERE a.orgid = ?1 AND a.docid = ?2 AND a.active = 1  ORDER BY b.partno")
	Set<Object[]> findWorkOrderDetailsForPurchaseIndent(Long orgId, String workOrderNo);

	@Query(nativeQuery = true, value = "SELECT partno, SUM(\r\n" + "    CASE \r\n"
			+ "        WHEN plusorminus = 'p' THEN qty \r\n" + "        ELSE qty \r\n" + "    END\r\n"
			+ ") AS stock \r\n" + "FROM efit_ops.stockdetails \r\n" + "where location ='WDS-STORE'\r\n"
			+ "and orgid= ?1  \r\n" + "and partno= ?2  \r\n" + "group by partno")
	Set<Object[]> findPurchaseIndentAvlStock(Long orgId, String item);

	@Query(nativeQuery = true, value = "SELECT\n"
			+ "    p.docid,\n"
			+ "    p.docdate,\n"
			+ "    p.customername,\n"
			+ "    p.customercode,\n"
			+ "    p.customerpono,\n"
			+ "    p.fgpart,\n"
			+ "    p.fgpartdesc,\n"
			+ "    p1.item,\n"
			+ "    p1.indentqty,\n"
			+ "    p1.itemdesc,\n"
			+ "    p.purchaseindentid,\n"
			+ "    p2.purchaseorderid,\n"
			+ "    p3.purchaseenquiryid,\n"
			+ "\n"
			+ "    CASE\n"
			+ "        WHEN p2.purchaseindentno IS NOT NULL\n"
			+ "          OR p3.purchaseindentno IS NOT NULL\n"
			+ "        THEN 'Completed'\n"
			+ "        ELSE 'Pending'\n"
			+ "    END AS status\n"
			+ "FROM purchaseindent p\n"
			+ "JOIN purchaseindentdetails p1\n"
			+ "    ON p.purchaseindentid = p1.purchaseindentid\n"
			+ "LEFT JOIN purchaseorder p2\n"
			+ "    ON p.docid = p2.purchaseindentno\n"
			+ "LEFT JOIN purchaseenquiry p3\n"
			+ "    ON p.docid = p3.purchaseindentno\n"
			+ "WHERE p.orgid = ?1\n"
			+ "  AND p.branchcode =?2\n"
			+ "  AND (p.customername = ?3 OR ?3 = 'ALL')\n"
			+ "  AND (\n"
			+ "        ?4 = 'ALL'\n"
			+ "        OR (\n"
			+ "             ?4 = 'Completed'\n"
			+ "             AND (p2.purchaseindentno IS NOT NULL\n"
			+ "                  OR p3.purchaseindentno IS NOT NULL)\n"
			+ "           )\n"
			+ "        OR (\n"
			+ "             ?4 = 'Pending'\n"
			+ "             AND (p2.purchaseindentno IS NULL\n"
			+ "                  AND p3.purchaseindentno IS NULL)\n"
			+ "           )\n"
			+ "      )\n"
			+ "  AND (?5 IS NULL OR p.docdate >= ?5)\n"
			+ "  AND (?6 IS NULL OR p.docdate <= ?6)\n"
			+ "GROUP BY\n"
			+ "    p.docid,\n"
			+ "    p.docdate,\n"
			+ "    p.customername,\n"
			+ "    p.customercode,\n"
			+ "    p.customerpono,\n"
			+ "    p.fgpart,\n"
			+ "    p.fgpartdesc,\n"
			+ "    p1.item,\n"
			+ "    p1.indentqty,\n"
			+ "    p1.itemdesc,\n"
			+ "    p.purchaseindentid,\n"
			+ "    p2.purchaseorderid,\n"
			+ "    p3.purchaseenquiryid")
	Set<Object[]> getPurchaseIndentReport(Long orgId, String branchCode, String customerName, String status,String fromDate,String toDate);

}
