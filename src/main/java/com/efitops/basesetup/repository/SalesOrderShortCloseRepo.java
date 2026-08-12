package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesOrderShortCloseVO;

@Repository
public interface SalesOrderShortCloseRepo extends JpaRepository<SalesOrderShortCloseVO, Long> {

	@Query(nativeQuery = true, value = "select * from sales_order_short_close_basic where sales_order_short_close_basic_id=?1 and active=1 and cancel=0")
	SalesOrderShortCloseVO getSalesOrderShortCloseById(Long id);

	@Query(nativeQuery = true, value = "select * from sales_order_short_close_basic where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<SalesOrderShortCloseVO> getSalesOrderShortCloseByOrgId(Long orgId, Long branchId);

	@Query(nativeQuery = true, value = "select i.item_id, i.item_code,i.item_description from item i  left join quotation_detail a1 on i.item_id=a1.item  join\r\n"
			+ "quotation_header a on a.quotation_id=a.quotation_id and i.org_id=?1 and i.branch=?2 and a.doc_id=?3 \r\n"
			+ "group by i.item_code,i.item_description\r\n" + "union \r\n"
			+ "select i.item_id,i.item_code,i.item_description from item i where  i.org_id=?1 and i.branch=?2\r\n"
			+ "group by i.item_code,i.item_description")
	List<Object[]> getSalesOrderItemDetails(Long orgId, Long branch, String docId);

	@Query(nativeQuery = true, value = "select ORB.order_acceptance_basic_id ,  ORB.doc_id,orb.doc_date \r\n"
			+ "			From order_acceptance_basic ORB, customer_header PM,order_acceptance_detail od\r\n"
			+ "			WHERE ORB.CANCEL='F'  AND ORB.customer = PM.customer_id \r\n"
			+ "			AND PM.customer_id =?1\r\n"
			+ "			and orb.order_acceptance_basic_id=od.order_acceptance_basic_id\r\n"
			+ "			and not exists (select * from sales_order_short_close_detail y,sales_order_short_close_basic x where X.sales_order_short_close_basic_id=Y.sales_order_short_close_basic_id\r\n"
			+ "			and COALESCE(x.cancel,'F')='F')\r\n"
			+ "		-- 	and  ORB.order_acceptance_basic_id= 0\r\n"
			+ "			union\r\n"
			+ "			Select ORB.order_acceptance_basic_id ,  ORB.doc_id,orb.doc_date  \r\n"
			+ "			From order_acceptance_basic ORB, customer_header PM,sales_order_short_close_basic s\r\n"
			+ "			WHERE ORB.cancel='F'  AND ORB.customer = PM.customer_id\r\n"
			+ "			AND PM.customer_id =?1 \r\n"
			+ "			and s.doc_id=?2\r\n"
			+ "			and ORB.order_acceptance_basic_id > 0\r\n"
			+ "			union\r\n"
			+ "			select ORB.order_acceptance_basic_id ,  ORB.doc_id,orb.doc_date\r\n"
			+ "			From order_acceptance_basic ORB, customer_header PM,order_acceptance_detail od,(\r\n"
			+ "			select sales_order_short_close_detail_id,short_close_qty from sales_order_short_close_detail y,sales_order_short_close_basic x where X.sales_order_short_close_basic_id=Y.sales_order_short_close_basic_id\r\n"
			+ "			and COALESCE(x.cancel,'F')='F'  ) y\r\n"
			+ "			WHERE ORB.cancel='F'  AND ORB.customer = PM.customer_id \r\n"
			+ "			AND PM.customer_id =?1\r\n"
			+ "			and ORB.order_acceptance_basic_id  = 0\r\n"
			+ "			and od.order_acceptance_basic_id=orb.order_acceptance_basic_id\r\n"
			+ "			and OD.quantity-(OD.quantity+y.short_close_qty) > 0\r\n"
			+ "			order by 3 desc")
	List<Object[]> getOrderAcceptanceDocIdDetails(Long customer, String docId);

	@Query(nativeQuery = true, value = "select  IM.item_id,IM.item_code,IM.item_description,(od.quantity-(x.sqty)) \r\n"
			+ "pqty,od.order_acceptance_detail_id,od.quantity\r\n"
			+ "From item IM,order_acceptance_detail od,order_acceptance_basic ORB, \r\n"
			+ "(select sum(short_close_qty) as sqty,sales_order_short_close_detail_id from sales_order_short_close_detail x\r\n"
			+ " group by sales_order_short_close_detail_id ) x\r\n"
			+ "--  (select sum(qty) qtysupp,ordaccpdetailid from dccuminvbasic \r\n"
			+ "-- a,dccuminvdetail b\r\n"
			+ "-- where a.dccuminvbasicid=b.dccuminvbasicid\r\n"
			+ "-- and a.cancel='F'\r\n"
			+ "-- group by ordaccpdetailid) y\r\n"
			+ "WHERE\r\n"
			+ "--  x.uniqueid=od.ordaccpdetailid\r\n"
			+ " od.order_acceptance_basic_id=orb.order_acceptance_basic_id\r\n"
			+ "and OD.quantity-(x.sqty) > 0\r\n"
			+ "and orb.doc_id= ?1\r\n"
			+ "AND IM.item_id = OD.item\r\n"
			+ "-- and y.order_acceptance_detail_id=od.order_acceptance_detail_id_id\r\n"
			+ "union\r\n"
			+ "select  IM.item_id, IM.item_code,IM.item_description,(od.quantity) \r\n"
			+ "-- -(y.QTYSUPP)) \r\n"
			+ "pqty,od.order_acceptance_detail_id,od.quantity\r\n"
			+ "From  item IM,order_acceptance_detail od,order_acceptance_basic	 ORB\r\n"
			+ "-- ,\r\n"
			+ "-- (select sum(qty) qtysupp,ordaccpdetailid from dccuminvbasic a,dccuminvdetail b\r\n"
			+ "-- where a.dccuminvbasicid=b.dccuminvbasicid\r\n"
			+ "-- and a.cancel='F'\r\n"
			+ "-- group by ordaccpdetailid) y\r\n"
			+ "WHERE od.order_acceptance_basic_id=orb.order_acceptance_basic_id\r\n"
			+ "and orb.doc_id= ?1\r\n"
			+ "AND IM.item_id = OD.item\r\n"
			+ "-- and y.order_acceptance_detail_id=od.order_acceptance_detail_id\r\n"
			+ "and not exists (select * from sales_order_short_close_detail y,sales_order_short_close_basic x where \r\n"
			+ "X.sales_order_short_close_basic_id=Y.sales_order_short_close_basic_id\r\n"
			+ "and coalesce(x.cancel,'F')='F' )\r\n"
			+ "order by 2")
	List<Object[]> getOrderAcceptanceItemDetailsDetails(String docId);

}
