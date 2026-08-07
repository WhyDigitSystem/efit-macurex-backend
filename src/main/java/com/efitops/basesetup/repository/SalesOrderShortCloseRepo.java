package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesOrderShortCloseVO;

@Repository
public interface SalesOrderShortCloseRepo extends JpaRepository<SalesOrderShortCloseVO, Long> {

	@Query(nativeQuery = true, value = "select * from salesordershortclose where salesordershortclose_id=?1 and active=1 and cancel=0")
	SalesOrderShortCloseVO getSalesOrderShortCloseById(Long id);

	@Query(nativeQuery = true, value = "select * from salesordershortclose where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<SalesOrderShortCloseVO> getSalesOrderShortCloseByOrgId(Long orgId, Long branchId);

	@Query(nativeQuery = true, value = "select i.item_id, i.item_code,i.item_description from item i  left join quotation_detail a1 on i.item_id=a1.item  join\r\n"
			+ "quotation_header a on a.quotation_id=a.quotation_id and i.org_id=?1 and i.branch=?2 and a.doc_id=?3 \r\n"
			+ "group by i.item_code,i.item_description\r\n" + "union \r\n"
			+ "select i.item_id,i.item_code,i.item_description from item i where  i.org_id=?1 and i.branch=?2\r\n"
			+ "group by i.item_code,i.item_description")
	List<Object[]> getSalesOrderItemDetails(Long orgId, Long branch, String docId);

}
