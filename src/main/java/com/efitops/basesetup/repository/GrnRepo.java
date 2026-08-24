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

	@Query(nativeQuery = true, value = "SELECT\r\n" + "			    c.customer_id,\r\n"
			+ "			    c.customer_name,\r\n" + "			    c.customer_code,\r\n"
			+ "			    c.address,\r\n" + "			    c.pincode,\r\n" + "			    c.gst_no,\r\n"
			+ "			    g.state_name,\r\n" + "			    c.is_registered,\r\n" + "                c1.country\r\n"
			+ "			FROM customer_header c\r\n" + "			LEFT JOIN listofvaluesdetails l1\r\n"
			+ "			    ON c.customer_category = l1.listofvaluesdetails_id\r\n"
			+ "			LEFT JOIN listofvaluesdetails l2\r\n"
			+ "			    ON c.customer_category1 = l2.listofvaluesdetails_id\r\n"
			+ "			LEFT JOIN listofvaluesdetails l3\r\n"
			+ "			    ON c.customer_category2 = l3.listofvaluesdetails_id\r\n"
			+ "			left JOIN gststatemaster g\r\n" + "			    ON g.gststatemaster_id = c.gst_state\r\n"
			+ "                left join country c1 on c1.countryid=c.country\r\n" + "			WHERE c.org_id = ?1\r\n"
			+ "			  AND c.branch = ?2\r\n" + "			  AND c.active = 1\r\n"
			+ "			  AND c.cancel = 0\r\n" + "			  AND (\r\n"
			+ "			        l1.value_description = 'Supplier'\r\n"
			+ "			        OR l2.value_description = 'Supplier'\r\n"
			+ "			        OR l3.value_description = 'Supplier'\r\n" + "			      )\r\n"
			+ "			ORDER BY c.customer_code")
	Set<Object[]> getSupplierDetailsForGrn(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select multiplication_factor from uomconversion where org_id=?1 and from_unit=?2 and to_unit=?3")
	Set<Object[]> getConversionFactorAmount(Long orgId, BigDecimal poQty, BigDecimal receivedQty);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    g.doc_id, \r\n" + "    g.doc_date, \r\n"
			+ "    g.gate_inward_entry_basic_id\r\n" + "FROM\r\n" + "    gate_inward_entry_basic g\r\n" + "WHERE\r\n"
			+ "    g.org_id = ?1\r\n" + "    AND g.branch = ?2\r\n" + "    AND g.customer = ?3  \r\n"
			+ "    AND g.active = 1\r\n" + "    AND g.cancel = 0\r\n" + "    AND (\r\n"
			+ "        g.doc_id NOT IN (\r\n" + "            SELECT DISTINCT gate_pass_no\r\n"
			+ "            FROM grn_basic\r\n" + "            WHERE org_id = ?1 \r\n"
			+ "                AND branch = ?2\r\n" + "                AND supplier_code = ?3  \r\n"
			+ "                AND cancel = 0\r\n" + "                AND active = 1\r\n"
			+ "                AND gate_pass_no IS NOT NULL)OR\r\n" + "        g.doc_id IN (\r\n"
			+ "            SELECT DISTINCT gate_pass_no\r\n" + "            FROM grn_basic\r\n"
			+ "            WHERE org_id = ?1\r\n" + "                AND branch = ?2\r\n"
			+ "                AND supplier_code = ?3  \r\n" + "                AND cancel = 1\r\n"
			+ "                AND active = 1\r\n" + "                AND gate_pass_no IS NOT NULL))")
	Set<Object[]> getGatePassDocId(Long orgId, Long branch, Long supplierCode);

//	@Query(nativeQuery = true, value = "Select  distinct A.doc_id AS ORDNO,A.doc_date, A.purchase_order_basic_id AS ID,1 as sno\r\n"
//			+ "From purchase_order_basic A,purchase_order_local_details B,customer_header PM, gate_inward_entry_basic G\r\n"
//			+ "-- PLANTMASTER PL,\r\n"
//			+ "-- (select sum(scloseqty) preclqty,itemid,ordplcdno from ordclbasic x,ordcldetail y\r\n"
//			+ "-- where x.ordclbasicid=y.ordclbasicid\r\n"
//			+ "-- and nvl(x.cancel,'F')='F'\r\n"
//			+ "-- group by itemid,ordplcdno) x\r\n"
//			+ "WHERE\r\n"
//			+ "-- (b.po_qty_in_purchase_unit - (x.PRECLQTY+QTYSUPP)) > 0\r\n"
//			+ " A.supplier_code = PM.customer_id\r\n"
//			+ "and g.customer =A.supplier_code\r\n"
//			+ "-- AND PL.PLANTMASTERID = A.PLANTID\r\n"
//			+ "AND a.branch=?2\r\n"
//			+ "and a.org_id=?1\r\n"
//			+ "AND A.purchase_order_basic_id = B.purchase_order_basic_id\r\n"
//			+ "AND PM.customer_id =?3\r\n"
//			+ "and A.doc_id not like'%/15-16/%'\r\n"
//			+ "and A.doc_id not like'%/16-17/%'\r\n"
//			+ "and A.doc_id not like'%/17-18/%'\r\n"
//			+ "and A.doc_id not like'%/18-19/%'\r\n"
//			+ "and A.doc_id not like'%/19-20/%'\r\n"
//			+ "and A.doc_id not like'%/20-21/%'\r\n"
//			+ "AND G.doc_id=?4\r\n"
////			+ "-- and x.itemid=b.itemid\r\n"
////			+ "-- and :recid = 0\r\n"
////			+ "-- and A.APP_LEVEL>=2\r\n"
//			+ "\r\n"
//			+ "union\r\n"
//			+ "Select distinct A.doc_id AS ORDNO,A.doc_date,A.purchase_order_basic_id AS ID,2 as sno\r\n"
//			+ "From purchase_order_basic A,purchase_order_local_details B,customer_header PM, gate_inward_entry_basic G, branch PL\r\n"
//			+ "WHERE  A.supplier_code = PM.customer_id\r\n"
//			+ "AND G.customer = A.supplier_code\r\n"
//			+ "AND PL.branch_id = A.branch\r\n"
//			+ "AND PL.branch_id = ?2\r\n"
//			+ "AND A.purchase_order_basic_id = B.purchase_order_basic_id\r\n"
//			+ "AND PM.customer_id =?3 \r\n"
//			+ "and A.doc_id not like'%/15-16/%'\r\n"
//			+ "and A.doc_id not like'%/16-17/%'\r\n"
//			+ "and A.doc_id not like'%/17-18/%'\r\n"
//			+ "and A.doc_id not like'%/18-19/%'\r\n"
//			+ "and A.doc_id not like'%/19-20/%'\r\n"
//			+ "and A.doc_id not like'%/20-21/%'\r\n"
//			+ "and A.doc_id not like'POR%'\r\n"
//			+ "-- and :recid = 0\r\n"
//			+ "-- and A.APP_LEVEL>=2\r\n"
//			+ "AND G.doc_id = ?4\r\n"
////			+ "-- and not exists (select * from purchase_contract_basic p ,purchase_contract_details d \r\n"
////			+ "-- where P.pono = a.ordplcdbasicid and p.purchobasicid=d.purchobasicid and p.cancel='F'  and B.ORDPLCDDETAILID = D.UPDID\r\n"
////			+ "-- )\r\n"
//			+ "union\r\n"
//			+ "Select  distinct  A.doc_id AS ORDNO,A.doc_date ,A.purchase_order_basic_id AS ID,3 as sno\r\n"
//			+ "From purchase_order_basic A,purchase_order_local_details B,customer_header PM, gate_inward_entry_basic G, branch PL\r\n"
//			+ "-- (select sum(recqty) qtysupp,itemid,pono from purchobasic x,purchodetail y\r\n"
//			+ "-- where x.purchobasicid=y.purchobasicid\r\n"
//			+ "-- and nvl(x.cancel,'F')='F'\r\n"
//			+ "-- group by itemid,pono) x\r\n"
//			+ "WHERE\r\n"
////			+ "-- (POQTY - (0+x.QTYSUPP)) > 0\r\n"
////			+ "-- and\r\n"
//			+ " A.supplier_code = PM.customer_id\r\n"
//			+ "AND G.customer = A.supplier_code\r\n"
//			+ "AND PL.branch_id = A.branch\r\n"
//			+ "AND PL.branch_id = ?2\r\n"
//			+ "AND  A.purchase_order_basic_id = B.purchase_order_basic_id\r\n"
//			+ "AND PM.customer_id =?3 \r\n"
//			+ "and A.doc_id not like'%/15-16/%'\r\n"
//			+ "and A.doc_id not like'%/16-17/%'\r\n"
//			+ "and A.doc_id not like'%/17-18/%'\r\n"
//			+ "and A.doc_id not like'%/18-19/%'\r\n"
//			+ "and A.doc_id not like'%/19-20/%'\r\n"
//			+ "and A.doc_id not like'%/20-21/%'\r\n"
//			+ "AND G.doc_id = ?4\r\n"
////			+ "-- and x.itemid=b.itemid\r\n"
////			+ "-- and :recid = 0\r\n"
////			+ "-- and A.APP_LEVEL>=2\r\n"
////			+ "-- and x.pono=a.ordplcdbasicid\r\n"
////			+ "-- and not exists (select * from  ordclbasic p where p.ordplcdno = a.ordplcdbasicid)\r\n"
//			+ "union \r\n"
//			+ "Select  distinct A.doc_id AS ORDNO,a.doc_date as ORDPLCDDT, A.purchase_contract_basic_id AS ID,4 as sno\r\n"
//			+ "From purchase_contract_basic A,purchase_contract_details B,customer_header PM, gate_inward_entry_basic G, branch PL\r\n"
//			+ "WHERE  A.supplier = PM.customer_id\r\n"
//			+ "AND A.purchase_contract_basic_id = B.purchase_contract_basic_id\r\n"
//			+ "AND PL.branch_id = A.branch\r\n"
//			+ "AND PL.branch_id = ?2\r\n"
//			+ "AND G.customer = A.supplier\r\n"
//			+ "AND PM.customer_id = ?3\r\n"
//			+ "and A.doc_id not like'%/15-16/%'\r\n"
//			+ "and A.doc_id not like'%/16-17/%'\r\n"
//			+ "and A.doc_id not like'%/17-18/%'\r\n"
//			+ "and A.doc_id not like'%/18-19/%'\r\n"
//			+ "and A.doc_id not like'%/19-20/%'\r\n"
//			+ "and A.doc_id not like'%/20-21/%'\r\n"
//			+ "AND G.doc_id = ?4")
//	Set<Object[]> getPurchaseOrderNoBasedDocId(Long orgId, Long branch,Long supplierCode,String gatePass);

	@Query(nativeQuery = true, value = "Select  distinct A.doc_id AS ORDNO,A.doc_date, A.purchase_order_basic_id AS ID,1 as sno\r\n"
			+ "From purchase_order_basic A,purchase_order_local_details B,customer_header PM, gate_inward_entry_basic G\r\n"
			+ "WHERE\r\n" + " A.supplier_code = PM.customer_id\r\n" + "and g.customer =A.supplier_code\r\n"
			+ "AND a.branch=?2\r\n" + "and a.org_id=?1\r\n"
			+ "AND A.purchase_order_basic_id = B.purchase_order_basic_id\r\n" + "AND PM.customer_id =?3\r\n"
			+ "and A.doc_id not like'%/15-16/%'\r\n" + "and A.doc_id not like'%/16-17/%'\r\n"
			+ "and A.doc_id not like'%/17-18/%'\r\n" + "and A.doc_id not like'%/18-19/%'\r\n"
			+ "and A.doc_id not like'%/19-20/%'\r\n" + "and A.doc_id not like'%/20-21/%'\r\n" + "AND G.doc_id=?4\r\n"
			+ "union\r\n" + "Select distinct A.doc_id AS ORDNO,A.doc_date,A.purchase_order_basic_id AS ID,2 as sno\r\n"
			+ "From purchase_order_basic A,purchase_order_local_details B,customer_header PM, gate_inward_entry_basic G, branch PL\r\n"
			+ "WHERE  A.supplier_code = PM.customer_id\r\n" + "AND G.customer = A.supplier_code\r\n"
			+ "AND PL.branch_id = A.branch\r\n" + "AND PL.branch_id = ?2\r\n"
			+ "AND A.purchase_order_basic_id = B.purchase_order_basic_id\r\n" + "AND PM.customer_id =?3 \r\n"
			+ "and A.doc_id not like'%/15-16/%'\r\n" + "and A.doc_id not like'%/16-17/%'\r\n"
			+ "and A.doc_id not like'%/17-18/%'\r\n" + "and A.doc_id not like'%/18-19/%'\r\n"
			+ "and A.doc_id not like'%/19-20/%'\r\n" + "and A.doc_id not like'%/20-21/%'\r\n"
			+ "and A.doc_id not like'POR%'\r\n" + "AND G.doc_id = ?4\r\n" + "union\r\n"
			+ "Select  distinct  A.doc_id AS ORDNO,A.doc_date ,A.purchase_order_basic_id AS ID,3 as sno\r\n"
			+ "From purchase_order_basic A,purchase_order_local_details B,customer_header PM, gate_inward_entry_basic G, branch PL\r\n"
			+ "WHERE\r\n" + " A.supplier_code = PM.customer_id\r\n" + "AND G.customer = A.supplier_code\r\n"
			+ "AND PL.branch_id = A.branch\r\n" + "AND PL.branch_id = ?2\r\n"
			+ "AND  A.purchase_order_basic_id = B.purchase_order_basic_id\r\n" + "AND PM.customer_id =?3 \r\n"
			+ "and A.doc_id not like'%/15-16/%'\r\n" + "and A.doc_id not like'%/16-17/%'\r\n"
			+ "and A.doc_id not like'%/17-18/%'\r\n" + "and A.doc_id not like'%/18-19/%'\r\n"
			+ "and A.doc_id not like'%/19-20/%'\r\n" + "and A.doc_id not like'%/20-21/%'\r\n" + "AND G.doc_id = ?4\r\n"
			+ "union\r\n"
			+ "Select  distinct A.doc_id AS ORDNO,a.doc_date as ORDPLCDDT, A.purchase_contract_basic_id AS ID,4 as sno\r\n"
			+ "From purchase_contract_basic A,purchase_contract_details B,customer_header PM, gate_inward_entry_basic G, branch PL\r\n"
			+ "WHERE  A.supplier = PM.customer_id\r\n"
			+ "AND A.purchase_contract_basic_id = B.purchase_contract_basic_id\r\n" + "AND PL.branch_id = A.branch\r\n"
			+ "AND PL.branch_id = ?2\r\n" + "AND G.customer = A.supplier\r\n" + "AND PM.customer_id = ?3\r\n"
			+ "and A.doc_id not like'%/15-16/%'\r\n" + "and A.doc_id not like'%/16-17/%'\r\n"
			+ "and A.doc_id not like'%/17-18/%'\r\n" + "and A.doc_id not like'%/18-19/%'\r\n"
			+ "and A.doc_id not like'%/19-20/%'\r\n" + "and A.doc_id not like'%/20-21/%'\r\n" + "AND G.doc_id = ?4")
	Set<Object[]> getPurchaseOrderNoBasedDocId(Long orgId, Long branch, Long supplierCode, String gatePass);

//UNION
//Select  distinct A.PURCONTNo AS ORDNO,a.purcontdt as ORDPLCDDT, a.TAXBASICID ,A.SUPPCONTBASICID AS ID,4 as sno
//From SUPPCONTBASIC A,SUPPCONTDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL
//WHERE  A.SUPPCODE = PM.PARTYMASTID
//AND A.SUPPCONTBASICID = B.SUPPCONTBASICID
//AND PL.PLANTMASTERID = A.PLANT
//AND PL.PLANTID = :PLANT
//AND G.suppcode = A.SUPPCOde
//AND PM.PARTYid = :SUPPcode
//and A.purcontno not like'%/15-16/%'
//and A.purcontno not like'%/16-17/%'
//and A.purcontno not like'%/17-18/%'
//and A.purcontno not like'%/18-19/%'
//and A.purcontno not like'%/19-20/%'
//and A.purcontno not like'%/20-21/%'
//AND G.GPNO = :GPNO
//and :recid = 0
//and A.APP_LEVEL>=2
//union
//Select   distinct  A.DCNO   AS ORDNO,a.dcdate as ORDPLCDDT, 0 TAXBASICID ,A.RETDCBASICID as ID,5 as sno
//From RetDCBasic a,  RetDCDetail b , PARTYMAST PM, GPBASIC G, PLANTMASTER PL
//WHERE  A.DCNO NOT like'%DCG%'
//AND A.DCNO NOT like'%DG%'
//AND A.PARTYID = PM.PARTYID
//AND A.RetDCBasicID = B.RetDCBasicID
//AND PL.PLANTMASTERID = A.PLANT
//AND G.custid = A.partyid
//AND PM.PARTYid = :SUPPcode
//AND G.GPNO = :GPNO
//and :recid = 0
//union
//Select   distinct  A.DCNO   AS ORDNO,a.dcdate as ORDPLCDDT, 0 TAXBASICID ,A.RETDCBASICID as ID,5.5 as sno
//From RetDCBasic a,  RetDCDetail b , PARTYMAST PM, GPBASIC G, PLANTMASTER PL
//WHERE A.DCNO NOT like'%DCG%'
//AND A.DCNO NOT like'%DG%'
//and A.PARTYID = PM.PARTYID
//AND A.RetDCBasicID = B.RetDCBasicID
//AND PL.PLANTMASTERID = A.PLANT
//AND G.custid = A.partyid
//AND PM.PARTYid = :SUPPcode
//UNION
//SELECT distinct  SB.PURCONTNO AS ORDNO ,SB.PURCONTDT as ORDPLCDDT, SB.TAXBASICID ,SB.SUPPCONTBASICID AS ID ,6 as sno
//FROM SUPPCONTBASIC 
//SB,PLANTMASTER PL,PARTYMAST PM,GPBASIC GP 
//WHERE 
//SB.suppcode  = GP.suppcode 
//AND PM.PARTYMASTID=SB.SUPPCODE
//AND PL.PLANTMASTERID = SB.PLANT 
//and gp.gpno = :GPNO
//AND PL.PLANTID = :PLANT
//AND PM.PARTYID = :SUPPCODE
//and sb.purcontno not like'%/15-16/%'
//and sb.purcontno not like'%/16-17/%'
//and sb.purcontno not like'%/17-18/%'
//and sb.purcontno not like'%/18-19/%'
//and sb.purcontno not like'%/19-20/%'
//and sb.purcontno not like'%/20-21/%'
//and :recid >0
//and sb.purcontno = :oldcpono
//union
//Select  distinct  A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.iORDPLCDBASICID AS ID,7 as sno
//From iORDPLCDBASIC A,iORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL,
//(select sum(scloseqty) preclqty,itemid,ordplcdno from ordclbasic x,ordcldetail y
//where x.ordclbasicid=y.ordclbasicid
//and nvl(x.cancel,'F')='F'
//group by itemid,ordplcdno) x
//WHERE  (POQTY - (x.PRECLQTY+QTYSUPP)) > 0
//AND A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.iORDPLCDBASICID = B.iORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and A.ORDPLCDNO not like'%/15-16/%'
//and A.ORDPLCDNO not like'%/16-17/%'
//and A.ORDPLCDNO not like'%/17-18/%'
//and A.ORDPLCDNO not like'%/18-19/%'
//and A.ORDPLCDNO not like'%/19-20/%'
//and A.ORDPLCDNO not like'%/20-21/%'
//AND G.GPNO = :GPNO
//and x.itemid=b.itemid
//and :recid = 0
//and A.APP_LEVEL>=2
//and x.ordplcdno=a.iordplcdbasicid
//and exists (select * from  ordclbasic p where p.ordplcdno = a.iordplcdbasicid)
//union
//Select distinct  A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.iORDPLCDBASICID AS ID,8 as sno
//From iORDPLCDBASIC A,iORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL
//WHERE  A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.iORDPLCDBASICID = B.iORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and A.ORDPLCDNO not like'%/15-16/%'
//and A.ORDPLCDNO not like'%/16-17/%'
//and A.ORDPLCDNO not like'%/17-18/%'
//and A.ORDPLCDNO not like'%/18-19/%'
//and A.ORDPLCDNO not like'%/19-20/%'
//and A.ORDPLCDNO not like'%/20-21/%'
//and :recid = 0
//and A.APP_LEVEL>=2
//AND G.GPNO = :GPNO
//and not exists (select * from purchobasic p ,purchodetail d 
//where P.pono = a.iordplcdbasicid and p.purchobasicid=d.purchobasicid and d.updid = b.iordplcddetailid )
//union
//Select  distinct  A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.iORDPLCDBASICID AS ID,9 as sno
//From iORDPLCDBASIC A,iORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL,
//(select sum(recqty) qtysupp,itemid,pono from purchobasic x,purchodetail y
//where x.purchobasicid=y.purchobasicid
//and nvl(x.cancel,'F')='F'
//group by itemid,pono) x
//WHERE (POQTY - (0+x.QTYSUPP)) > 0
//AND  A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.iORDPLCDBASICID = B.iORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and A.ORDPLCDNO not like'%/15-16/%'
//and A.ORDPLCDNO not like'%/16-17/%'
//and A.ORDPLCDNO not like'%/17-18/%'
//and A.ORDPLCDNO not like'%/18-19/%'
//and A.ORDPLCDNO not like'%/19-20/%'
//and A.ORDPLCDNO not like'%/20-21/%'
//AND G.GPNO = :GPNO
//--and x.itemid=b.itemid
//and :recid =0
//and A.APP_LEVEL>=2
//--and x.pono=a.iordplcdbasicid
//and not exists (select * from  ordclbasic p where p.ordplcdno = a.iordplcdbasicid)
//union
//Select  distinct A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.ORDPLCDBASICID AS ID,10 as sno
//From ORDPLCDBASIC A,ORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL
//WHERE  A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.ORDPLCDBASICID = B.ORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and A.ORDPLCDNO not like'%/15-16/%'
//and A.ORDPLCDNO not like'%/16-17/%'
//and A.ORDPLCDNO not like'%/17-18/%'
//and A.ORDPLCDNO not like'%/18-19/%'
//and A.ORDPLCDNO not like'%/19-20/%'
//and A.ORDPLCDNO not like'%/20-21/%'
//and :recid > 0
//and A.APP_LEVEL>=2
//union
//Select distinct  A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.iORDPLCDBASICID AS ID,11 as sno
//From iORDPLCDBASIC A,iORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL
//WHERE  A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.iORDPLCDBASICID = B.iORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and :recid > 0
//and A.APP_LEVEL>=2
//AND G.GPNO = :GPNO
//union
//Select distinct A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.ORDPLCDBASICID AS ID,12 as sno
//From ORDPLCDBASIC A,ORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL
//WHERE  A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.ORDPLCDBASICID = B.ORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and A.ORDPLCDNO not like'%/15-16/%'
//and A.ORDPLCDNO not like'%/16-17/%'
//and A.ORDPLCDNO not like'%/17-18/%'
//and A.ORDPLCDNO not like'%/18-19/%'
//and A.ORDPLCDNO not like'%/19-20/%'
//and A.ORDPLCDNO not like'%/20-21/%'
//and A.ORDPLCDNO not like'POR%'
//and :recid > 0
//and A.APP_LEVEL>=2
//AND G.GPNO = :GPNO
//union
//Select  distinct  A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.ORDPLCDBASICID AS ID,13 as sno
//From ORDPLCDBASIC A,ORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL,poamdbasic p,poamddetail q,
//(select sum(scloseqty) preclqty,itemid,ordplcdno from ordclbasic x,ordcldetail y
//where x.ordclbasicid=y.ordclbasicid
//and nvl(x.cancel,'F')='F'
//group by itemid,ordplcdno) x
//WHERE  (q.newQTY - (x.PRECLQTY+QTYSUPP)) > 0 AND 
//A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.ORDPLCDBASICID = B.ORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and A.ORDPLCDNO not like'%/15-16/%'
//and A.ORDPLCDNO not like'%/16-17/%'
//and A.ORDPLCDNO not like'%/17-18/%'
//and A.ORDPLCDNO not like'%/18-19/%'
//and A.ORDPLCDNO not like'%/19-20/%'
//and A.ORDPLCDNO not like'%/20-21/%'
//AND G.GPNO = :GPNO
//and x.itemid=b.itemid
//and :recid = 0
//and A.APP_LEVEL>=2
//and x.ordplcdno=a.ordplcdbasicid
//and p.poamdbasicid=q.poamdbasicid
//and q.itemid=b.itemid
//and p.pono=a.ordplcdbasicid
//and nvl(p.cancel,'F')='F' and revno=(select max(revno) from poamdbasic x,poamddetail y 
//where  x.poamdbasicid=y.poamdbasicid
//and y.itemid=b.itemid
//and x.pono=a.ordplcdbasicid )
//union
//Select  distinct A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.ORDPLCDBASICID AS ID,14 as sno
//From ORDPLCDBASIC A,ORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL,poamdbasic p,poamddetail q,
//(select sum(recqty) qtysupp,itemid,pono from purchobasic x,purchodetail y
//where x.purchobasicid=y.purchobasicid
//and nvl(x.cancel,'F')='F'
//group by itemid,pono) x
//WHERE  (q.newqTY - (0+x.QTYSUPP)) > 0
//AND A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.ORDPLCDBASICID = B.ORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and A.ORDPLCDNO not like'%/15-16/%'
//and A.ORDPLCDNO not like'%/16-17/%'
//and A.ORDPLCDNO not like'%/17-18/%'
//and A.ORDPLCDNO not like'%/18-19/%'
//and A.ORDPLCDNO not like'%/19-20/%'
//and A.ORDPLCDNO not like'%/20-21/%'
//AND G.GPNO = :GPNO
//and x.itemid=b.itemid
//and :recid = 0
//and A.APP_LEVEL>=2
//and x.pono=a.ordplcdbasicid
//and p.poamdbasicid=q.poamdbasicid
//and p.pono=a.ordplcdbasicid
//and q.itemid=b.itemid
//and nvl(p.cancel,'F')='F' and  revno=(select max(revno) from poamdbasic x,poamddetail y 
//where  x.poamdbasicid=y.poamdbasicid
//and y.itemid=b.itemid
//and x.pono=a.ordplcdbasicid )
//and not exists (select * from  ordclbasic p where p.ordplcdno = a.ordplcdbasicid)
//union
//Select  distinct A.OrdPlcdNo AS ORDNO,A.ORDPLCDDT, a.TAXBASICID ,A.ORDPLCDBASICID AS ID,15 as sno
//From ORDPLCDBASIC A,ORDPLCDDETAIL B,PARTYMAST PM, GPBASIC G, PLANTMASTER PL,poamdbasic p,poamddetail q,
//(select sum(recqty) qtysupp,itemid,pono from purchobasic x,purchodetail y
//where x.purchobasicid=y.purchobasicid
//and nvl(x.cancel,'F')='F'
//group by itemid,pono) x
//WHERE  (q.newqTY - (0+x.QTYSUPP)) > 0
//AND A.SUPPCODE = PM.PARTYMASTID
//AND G.suppcode = A.suppcode
//AND PL.PLANTMASTERID = A.PLANTID
//AND PL.PLANTID = :PLANT
//AND A.ORDPLCDBASICID = B.ORDPLCDBASICID
//AND PM.PARTYid = :SUPPcode
//and A.ORDPLCDNO not like'%/15-16/%'
//and A.ORDPLCDNO not like'%/16-17/%'
//and A.ORDPLCDNO not like'%/17-18/%'
//and A.ORDPLCDNO not like'%/18-19/%'
//and A.ORDPLCDNO not like'%/19-20/%'
//and A.ORDPLCDNO not like'%/20-21/%'
//AND G.GPNO = :GPNO
//and :recid = 0
//and p.APP_LEVEL>=2
//and p.poamdbasicid=q.poamdbasicid
//and p.pono=a.ordplcdbasicid
//and q.itemid=b.itemid
//and nvl(p.cancel,'F')='F' and  revno=(select max(revno) from poamdbasic x,poamddetail y 
//where  x.poamdbasicid=y.poamdbasicid
//and y.itemid=b.itemid
//and x.pono=a.ordplcdbasicid )
//and not exists (select * from  ordclbasic p where p.ordplcdno = a.ordplcdbasicid)
//order by 1 desc

	// PendingQury
//	-- and :recid  = 0  
//			--      union
//			-- select distinct p.purchase_delivery_schedule_basicid,p.docid,p.docdt,p.schedule_start_date,p.schedule_end_date 
//			-- from purchase_delivery_schedule_basic p,purchobasic pb ,purchase_contract_basic s
//			-- where P.CANCEL='F' AND p.purchase_delivery_schedule_basicid = pb.schno
//			-- and pb.grnno = 
//			-- and  s.purchase_contract_basicid=p.pono 
//			-- and s.PURCONTNO = :cPONO 
//			-- and :recordid > 0
//			-- union
//			-- select distinct p.purchase_delivery_schedule_basicid,p.docid,p.docdt,p.schedule_start_date,p.schedule_end_date 
//			-- from purchase_delivery_schedule_basic p,gate_inward_entry_basic gp,purchase_contract_basic s
//			-- where P.CANCEL='F' AND gp.suppcode = p.custid
//			-- and gp.gpno = :gpno
//			-- and  s.purchase_contract_basicid=p.pono 
//			-- and s.PURCONTNO = :cPONO
//			-- and :recid  > 0 
//			-- order by docid;

	@Query(nativeQuery = true, value = "select distinct p.purchase_delivery_schedule_basic_id,p.doc_id,p.doc_date,p.schedule_start_date,p.schedule_end_date \r\n"
			+ "from purchase_delivery_schedule_basic p,gate_inward_entry_basic gp,purchase_contract_basic s\r\n"
			+ "where P.cancel=0 AND gp.customer = p.supplier\r\n"
			+ "and ?3 between p.schedule_start_date and p.schedule_end_date\r\n" + "and gp.doc_id = ?4\r\n"
			+ "and  s.doc_id=p.purchase_order_no \r\n" + "and s.doc_id =?2  and p.org_id=?1")
	Set<Object[]> getScheduleDocId(Long orgId, String purchaseOrderNo, String date, String gatePass);
	
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,p1.qty_in_primary_unit,h.hsn,u.unitmaster_id,p1.rate_in_inr from purchase_order_basic p join purchase_order_local_details p1 \r\n"
			+ "on p.purchase_order_basic_id=p1.purchase_order_basic_id left join item i on i.item_id=p1.item left join hsn h on h.hsn_id=i.hsn_code\r\n"
			+ "left join unitmaster u on u.unitmaster_id=i.purchase_unit where p.org_id=?1\r\n"
			+ "and p.branch=?2 and p.active=1 and p.cancel=0 and p.doc_id=?3\r\n"
			+ "union \r\n"
			+ "select i.item_id,i.item_code,i.item_description,p1.po_qty,h.hsn,u.unitmaster_id,p1.order_rate from purchase_order_basic p join purchase_order_import_details p1 \r\n"
			+ "on p.purchase_order_basic_id=p1.purchase_order_basic_id left join item i on i.item_id=p1.item left join hsn h on h.hsn_id=i.hsn_code\r\n"
			+ "left join unitmaster u on u.unitmaster_id=i.purchase_unit where p.org_id=?1\r\n"
			+ "and p.branch=?2 and p.active=1 and  p.cancel=0 and p.doc_id=?3\r\n"
			+ "union\r\n"
			+ "select i.item_id,i.item_code,i.item_description,0 as po ,h.hsn,u.unitmaster_id,p1.rate_in_currency from purchase_contract_basic p join purchase_contract_details p1 \r\n"
			+ "on p.purchase_contract_basic_id=p1.purchase_contract_basic_id left join item i on i.item_id=p1.item_id left join hsn h on h.hsn_id=i.hsn_code\r\n"
			+ "left join unitmaster u on u.unitmaster_id=i.purchase_unit where p.org_id=?1\r\n"
			+ "and p.branch=?2 and p.active=1 and p.cancel=0 and p.doc_id=?3")
	Set<Object[]> getPoNmberBasedItemDetails(Long orgId,Long branch, String purchaseOrderNo);

}
