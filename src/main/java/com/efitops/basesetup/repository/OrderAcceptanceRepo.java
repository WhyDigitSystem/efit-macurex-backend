package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.OrderAcceptanceVO;
import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface OrderAcceptanceRepo extends JpaRepository<OrderAcceptanceVO, Long> {

	@Query(nativeQuery = true, value = "select * from orderacceptance where orderacceptance_id=?1 and active=1 and cancel=0")
	OrderAcceptanceVO getOrderAcceptanceById(Long id);

	QuotationVO findByDocId(String docId);

	@Query(nativeQuery = true, value = "select * from orderacceptance where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<OrderAcceptanceVO> getQuotationByOrgId(Long orgId, Long branchId);

	@Query(value = "SELECT\r\n" + "		    i.item_id,\r\n" + "		    i.item_code,\r\n"
			+ "		    i.item_description,\r\n" + "		    u.unit_id,\r\n" + "		    i.min_sell_price,\r\n"
			+ "		    h.hsn,\r\n" + "		    gr.rate,\r\n" + "		    gr.cgst,\r\n" + "		    gr.sgst,\r\n"
			+ "		    gr.igst,u.unitmaster_id,gr.gstratemaster_id\r\n" + "		FROM item i\r\n"
			+ "		INNER JOIN unitmaster u\r\n" + "		    ON u.unitmaster_id = i.primary_unit\r\n"
			+ "		INNER JOIN hsn h\r\n" + "		    ON h.hsn_id = i.hsn_code\r\n"
			+ "		LEFT JOIN gstratemaster gr\r\n" + "		    ON gr.hsn_sac_code = h.hsn_id\r\n"
			+ "		    AND gr.active = 1\r\n" + "		    AND gr.cancel = 0\r\n"
			+ "		    AND gr.org_id = i.org_id\r\n" + "		    AND gr.branch = i.branch\r\n"
			+ "		WHERE i.cancel = 0\r\n" + "		  AND i.org_id = ?1\r\n" + "		  AND i.branch =?2 \r\n"
			+ "		ORDER BY i.item_code", nativeQuery = true)
	List<Object[]> getOrderAcceptanceItemDetails(Long orgId, Long branch);

}
