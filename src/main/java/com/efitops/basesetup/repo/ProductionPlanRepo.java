package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ProductionPlanVO;

public interface ProductionPlanRepo extends JpaRepository<ProductionPlanVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM productionplan WHERE orgid=?1")
	List<ProductionPlanVO> getAllProductionPlanByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "SELECT * FROM productionplan WHERE productionplanid=?1")
	List<ProductionPlanVO> getAllProductionPlanById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getProductionPlanDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "SELECT A.docid,a.wono, b.docdate as workorderdate,A.customername,D.partycode,D.partyname,A.fgqty,fgpartname,fgpartdesc FROM routecardentry A, workorder B,partymaster D\r\n"
			+ " WHERE A.wono=B.docid  AND A.customername=D.partyname\r\n"
			+ "			 AND a.orgid=?1 GROUP BY A.docid,a.wono,B.docdate,A.customername,D.partycode,D.partyname,A.fgqty,a.fgpartname,a.fgpartdesc")
	Set<Object[]> getRouteCardNo(Long orgId);

	@Query(nativeQuery = true, value = "SELECT A.itemname ,A.itemdesc FROM item A WHERE A.orgid=?1 and itemtype ='RAW MATERIAL' ")
	Set<Object[]> getRawMaterialDetails(Long orgId);

	@Query(nativeQuery = true, value = "SELECT DISTINCT B.processname FROM itemwiseprocess A,itemwiseprocessdetails B WHERE A.itemwiseprocessid=B.itemwiseprocessid AND A.processtype='PRODUCTION' AND A.orgid=?1 AND A.item=?2")
	Set<Object[]> getProcessName(Long orgId, String item);

	@Query(nativeQuery = true, value = "SELECT CAST(A.machineno AS CHAR(100)) as machineno, A.machinename FROM machinemaster A WHERE A.machinename NOT IN (SELECT C.machinename FROM productionplan B JOIN productionplandetails C ON B.productionplanid = C.productionplanid WHERE A.orgid=?1 AND (?2 BETWEEN C.fromdate AND C.todate) OR C.status = 'PENDING') GROUP BY A.machineno, A.machinename HAVING (?3 = 0)\r\n"
			+ " UNION\r\n"
			+ " SELECT CAST(B.machineno AS CHAR(100)) AS machineno, B.machinename FROM productionplan A, productionplandetails B WHERE A.productionplanid = B.productionplanid AND a.orgid=?1 AND A.docid = ?4 GROUP BY B.machineno, B.machinename HAVING ?3 > 0 ORDER BY machineno")
	Set<Object[]> getMachineName(Long orgId, String fromDate, Long id, String docId);

//	@Query(nativeQuery = true, value = "select customername,docdate,docid,narration,orgid,part,partdesc,productionenddate,productionqty,productionstartdate,rawmaterial,rawmaterialdesc,routecardno,wosodate,wosono from productionplan where orgid=?1 and customername=?2 and routecardno=?3")
//	Set<Object[]> getProductionPlanReport(Long orgId, String customerName, String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT " + " customername, docdate, docid, narration, orgid, part, partdesc, "
			+ " productionenddate, productionqty, productionstartdate, "
			+ " rawmaterial, rawmaterialdesc, routecardno, wosodate, wosono " + "FROM productionplan "
			+ "WHERE orgid = ?1 AND (customername = ?2 or ?2='ALL') AND (routecardno = ?3 or  ?3='ALL')")
	List<Object[]> getProductionPlanReport(Long orgId, String customerName, String routeCardNo);
}
