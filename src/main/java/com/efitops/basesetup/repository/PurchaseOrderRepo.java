package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrderVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchase_order_basic where purchase_order_basic_id=?1 and active=1 and cancel=0 and po_type=?2")
	PurchaseOrderVO getPurchaseOrderById(Long id, Integer type);

	@Query(nativeQuery = true, value = "select * from purchase_order_basic where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<PurchaseOrderVO> getPurchaseOrderByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select multiplication_factor from uomconversion where org_id=?1 and from_unit=?2 and to_unit=?3")
	Set<Object[]> getMutipleFactorAmount(Long orgId, Long primaryUnit, Long purchaseUnit);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseOrderImportDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getPurchaseOrderLocalDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "SELECT i.item_id,\r\n" + "    		                i.item_code,\r\n"
			+ "    		                i.item_description,\r\n" + "    		                u.unit_id,\r\n"
			+ "    		                h.hsn,\r\n" + "                            i.customer_part_no,\r\n"
			+ "    		                i.primary_unit,\r\n" + "                            i.purchase_unit\r\n"
			+ "    		            FROM item i\r\n" + "    		            INNER JOIN unitmaster u\r\n"
			+ "    		                ON u.unitmaster_id = i.primary_unit  and u.unitmaster_id=i.purchase_unit\r\n"
			+ "    		            INNER JOIN hsn h\r\n" + "    		                ON h.hsn_id = i.hsn_code\r\n"
			+ "    		            WHERE i.cancel = 0	\r\n" + "    		              AND i.org_id = ?1\r\n"
			+ "    		              AND i.branch = ?2 group by  i.item_id,\r\n"
			+ "    		                i.item_code,\r\n" + "    		                i.item_description,\r\n"
			+ "    		                u.unit_id,\r\n" + "    		                h.hsn,\r\n"
			+ "    		                i.customer_part_no order by i.item_id")
	Set<Object[]> getItemDetailsResponsePurchaseLocal(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "SELECT i.item_id,\r\n" + "    		                i.item_code,\r\n"
			+ "    		                i.item_description,\r\n" + "    		                u.unit_id,\r\n"
			+ "    		                h.hsn,\r\n" + "                            i.customer_part_no,\r\n"
			+ "                            u.unitmaster_id\r\n" + "\r\n" + "    		            FROM item i\r\n"
			+ "    		            INNER JOIN unitmaster u\r\n"
			+ "    		                ON u.unitmaster_id = i.primary_unit \r\n"
			+ "    		            INNER JOIN hsn h\r\n" + "    		                ON h.hsn_id = i.hsn_code\r\n"
			+ "    		            WHERE i.cancel = 0	\r\n" + "    		              AND i.org_id = ?1\r\n"
			+ "    		              AND i.branch = ?2 group by  i.item_id,\r\n"
			+ "    		                i.item_code,\r\n" + "    		                i.item_description,\r\n"
			+ "    		                u.unit_id,\r\n" + "    		                h.hsn,\r\n"
			+ "    		                i.customer_part_no order by i.item_id")
	Set<Object[]> getItemDetailsResponsePurchaseImport(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "SELECT\r\n" + "    c.customer_id,\r\n" + "    c.customer_name,\r\n"
			+ "    c.customer_code,\r\n" + "    c.address,\r\n" + "    c.pincode,\r\n" + "    c.gst_no,\r\n"
			+ "    g.state_name,\r\n" + "    c.is_registered\r\n" + "FROM customer_header c\r\n"
			+ "LEFT JOIN listofvaluesdetails l1\r\n" + "    ON c.customer_category = l1.listofvaluesdetails_id\r\n"
			+ "LEFT JOIN listofvaluesdetails l2\r\n" + "    ON c.customer_category1 = l2.listofvaluesdetails_id\r\n"
			+ "LEFT JOIN listofvaluesdetails l3\r\n" + "    ON c.customer_category2 = l3.listofvaluesdetails_id\r\n"
			+ "left JOIN gststatemaster g\r\n" + "    ON g.gststatemaster_id = c.gst_state\r\n"
			+ "WHERE c.org_id = ?1\r\n" + "  AND c.branch = ?2\r\n" + "  AND c.active = 1\r\n"
			+ "  AND c.cancel = 0\r\n" + "  AND (\r\n" + "        l1.value_description = 'Supplier'\r\n"
			+ "        OR l2.value_description = 'Supplier'\r\n" + "        OR l3.value_description = 'Supplier'\r\n"
			+ "      )\r\n" + "ORDER BY c.customer_code")
	Set<Object[]> getSupplierDetails(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select d.selling_ex_rate from currency c join dailyexchangerate d on c.currency_id=d.currency  where d.org_id=?1\r\n"
			+ " and d.branch=?2 and d.active=1 and d.cancel=0 and d.currency=?3")
	Set<Object[]> getExchangeRateDetails(Long orgId, Long branch, Long currency);


	  @Query(nativeQuery = true, value = "SELECT " +
	            "IB.doc_id AS doc_id, " +
	            "IB.indent_basic_id AS indent_basic_id, " +
	            "IB.doc_date AS doc_date, " +
	            "I.item_code AS item_code, " +
	            "I.item_description AS item_desc, " +
	            "U.unit_id AS unit_id, " +
	            "ID.qtyinpurchase_unit AS qtyinpurchase_unit, " +
	            "CAST(COALESCE((ID.qtyinpurchase_unit - X.opqty), ID.qtyinpurchase_unit) AS DECIMAL(20,2)) AS pndqty, " +
	            "CAST(ID.qtyinprimary_unit AS DECIMAL(20,2)) AS qtyinprimary_unit, " +
	            "ID.required_date AS required_date, " +
	            "ID.indent_detail_id AS indent_detail_id, " +
	            "I.item_id AS item_id " +
	            "FROM indent_basic IB " +
	            "INNER JOIN indent_detail ID ON ID.indent_basic_id = IB.indent_basic_id " +
	            "INNER JOIN item I ON ID.item = I.item_id " +
	            "INNER JOIN unitmaster U ON ID.primary_unit = U.unitmaster_id " +
	            "INNER JOIN department D ON D.departmentid = IB.department " +
	            "LEFT JOIN ( " +
	            "    SELECT " +
	            "        COALESCE(SUM(A.po_qty_in_purchase_unit), 0) AS opqty, " +
	            "        A.item AS item_id, " +
	            "        A.indent_no AS indentno " +
	            "    FROM purchase_order_local_details A " +
	            "    INNER JOIN purchase_order_basic A1 ON A.purchase_order_basic_id = A1.purchase_order_basic_id " +
	            "    GROUP BY A.item, A.indent_no " +
	            ") X ON X.item_id = I.item_id AND X.indentno = IB.doc_id " +
	            "WHERE IB.cancel = 0 " +
	            "AND :type = 'YES' " +
	            "AND IB.belongs_to = :belongsTo " +
	            "AND IB.org_id = :orgId " +
	            "AND IB.doc_date NOT LIKE 'BLR/%/15-16/%' " +
	            "AND (ID.qtyinpurchase_unit - COALESCE(X.opqty, 0)) > 0 " +
	            "UNION " +
	            "SELECT " +
	            "IB.doc_id AS doc_id, " +
	            "IB.indent_basic_id AS indent_basic_id, " +
	            "IB.doc_date AS doc_date, " +
	            "I.item_code AS item_code, " +
	            "I.item_description AS item_desc, " +
	            "U.unit_id AS unit_id, " +
	            "ID.qtyinpurchase_unit AS qtyinpurchase_unit, " +
	            "CAST(ID.qtyinpurchase_unit AS DECIMAL(20,2)) AS pndqty, " +
	            "CAST(ID.qtyinprimary_unit AS DECIMAL(20,2)) AS qtyinprimary_unit, " +
	            "ID.required_date AS required_date, " +
	            "ID.indent_detail_id AS indent_detail_id, " +
	            "I.item_id AS item_id " +
	            "FROM indent_basic IB " +
	            "INNER JOIN indent_detail ID ON ID.indent_basic_id = IB.indent_basic_id " +
	            "INNER JOIN item I ON ID.item = I.item_id " +
	            "INNER JOIN unitmaster U ON ID.primary_unit = U.unitmaster_id " +
	            "INNER JOIN department D ON D.departmentid = IB.department " +
	            "WHERE IB.cancel = 0 " +
	            "AND :type = 'YES' " +
	            "AND IB.belongs_to = :belongsTo " +
	            "AND IB.org_id = :orgId " +
	            "AND IB.doc_date NOT LIKE 'BLR/%/15-16/%' " +
	            "ORDER BY doc_id, indent_basic_id, item_code")
	    Set<Object[]> getIndentNoBasedLocal(
	            @Param("orgId") Long orgId, 
	            @Param("belongsTo") String belongsTo, 
	            @Param("type") String type);
	    
//	    -- union all
//	    -- SELECT IB.DOCID, IB.INDENTBASICID,IB.DOCDATE,   I.ITEMID ITEM_ID,  
//	    -- I.ITEMDESC ITEM_DESC,  U.UNITID, ID.QTY,(ID.QTY-ID.POQTY) pndqty
//	    -- , id.qtykgs,ID.DELIVERYDATE ,  ID.INDENTDETAILID,i.itemmasterid
//	    -- FROM IndentBasic IB, IndentDETAIL ID, ITEMMASTER I, UNITMAST U ,DEPTMAST d
//	    -- WHERE IB.CANCEL='F' AND ID.INDENTBASICID=IB.INDENTBASICID
//	    -- AND ID.ITEMID = I.ITEMMASTERID
//	    -- AND ID.UNIT = U.UNITMASTID
//	    -- and d.DeptName = :Deptname
//	    -- and d.DEPTMASTid = IB.DEPTNAME
//	    -- and :recordid > 0
//	    -- and :indent = 'YES'
//	    -- and ib.APP_LEVEL >= 1
	    
	    
//	    
//		  @Query(nativeQuery = true, value = "SELECT IB.indent_basic_id,IB.doc_id, IB.doc_date,   I.item_code ITEM_ID,  \r\n"
//		  		+ "I.item_description ITEM_DESC,  U.unit_id, ID.qtyinpurchase_unit,(ID.qtyinpurchase_unit) pndqty,\r\n"
//		  		+ " id.qtyinprimary_unit,ID.required_date ,  ID.indent_detail_id,i.item_id\r\n"
//		  		+ "FROM indent_basic IB, indent_detail ID, item I, unitmaster U,department d\r\n"
//		  		+ "WHERE IB.cancel=0 AND ID.indent_basic_id=IB.indent_basic_id\r\n"
//		  		+ "AND ID.item = I.item_id\r\n"
//		  		+ "AND ID.purchase_unit = U.unitmaster_id\r\n"
//		  		+ "and d.departmentid = IB.department\r\n"
//		  		+ "-- and :recordid =0\r\n"
//		  		+ "and not exists (select * from purchase_order_local_details x ,purchase_order_basic y,item i,indent_basic ib where y.cancel=0 and x.item=i.item_id\r\n"
//		  		+ "and x.indent_no=ib.doc_id and x.purchase_order_basic_id=y.purchase_order_basic_id ) \r\n"
//		  		+ "and :type = 'YES'\r\n"
//		  		+ " and IB.org_id= :orgId\r\n"
//		  		+ "-- and IB.UNDER=:under;\r\n"
//		  		+ "union\r\n"
//		  		+ "SELECT IB.indent_basic_id,IB.doc_id, IB.doc_date,   I.item_code ITEM_ID,  \r\n"
//		  		+ "I.item_description ITEM_DESC,  U.unitmaster_id, ID.qtyinpurchase_unit,(ID.qtyinpurchase_unit) pndqty,\r\n"
//		  		+ " id.qtyinprimary_unit,ID.required_date ,  ID.indent_detail_id,i.item_id\r\n"
//		  		+ "FROM indent_basic IB, indent_detail ID, item I, unitmaster U,department d\r\n"
//		  		+ "WHERE IB.cancel=0 AND ID.indent_basic_id=IB.indent_basic_id\r\n"
//		  		+ "AND ID.item = I.item_id\r\n"
//		  		+ "AND ID.primary_unit = U.unitmaster_id\r\n"
//		  		+ "and d.departmentid = IB.department\r\n"
//		  		+ "and IB.org_id= :orgId")
//		    Set<Object[]> getIndentNoBasedImport(@Param("orgId") Long orgId, 
//		            @Param("type") String type);
	    
	    @Query(nativeQuery = true, value = "SELECT " +
	            "IB.indent_basic_id AS indent_basic_id, " +
	            "IB.doc_id AS doc_id, " +
	            "IB.doc_date AS doc_date, " +
	            "I.item_code AS item_code, " +
	            "I.item_description AS item_desc, " +
	            "U.unit_id AS unit_id, " +
	            "ID.qtyinpurchase_unit AS qtyinpurchase_unit, " +
	            "(ID.qtyinpurchase_unit) AS pndqty, " +
	            "ID.qtyinprimary_unit AS qtyinprimary_unit, " +
	            "ID.required_date AS required_date, " +
	            "ID.indent_detail_id AS indent_detail_id, " +
	            "I.item_id AS item_id " +
	            "FROM indent_basic IB, " +
	            "indent_detail ID, " +
	            "item I, " +
	            "unitmaster U, " +
	            "department d " +
	            "WHERE IB.cancel = 0 " +
	            "AND ID.indent_basic_id = IB.indent_basic_id " +
	            "AND ID.item = I.item_id " +
	            "AND ID.purchase_unit = U.unitmaster_id " +
	            "AND d.departmentid = IB.department " +
	            "AND NOT EXISTS ( " +
	            "    SELECT * " +
	            "    FROM purchase_order_local_details x, " +
	            "         purchase_order_basic y " +
	            "    WHERE y.cancel = 0 " +
	            "    AND x.item = I.item_id " +
	            "    AND x.indent_no = IB.doc_id " +
	            "    AND x.purchase_order_basic_id = y.purchase_order_basic_id " +
	            ") " +
	            "AND :type = 'YES' " +
	            "AND IB.org_id = :orgId " +
	            "UNION " +
	            "SELECT " +
	            "IB.indent_basic_id AS indent_basic_id, " +
	            "IB.doc_id AS doc_id, " +
	            "IB.doc_date AS doc_date, " +
	            "I.item_code AS item_code, " +
	            "I.item_description AS item_desc, " +
	            "U.unit_id AS unit_id, " +
	            "ID.qtyinpurchase_unit AS qtyinpurchase_unit, " +
	            "(ID.qtyinpurchase_unit) AS pndqty, " +
	            "ID.qtyinprimary_unit AS qtyinprimary_unit, " +
	            "ID.required_date AS required_date, " +
	            "ID.indent_detail_id AS indent_detail_id, " +
	            "I.item_id AS item_id " +
	            "FROM indent_basic IB, " +
	            "indent_detail ID, " +
	            "item I, " +
	            "unitmaster U, " +
	            "department d " +
	            "WHERE IB.cancel = 0 " +
	            "AND ID.indent_basic_id = IB.indent_basic_id " +
	            "AND ID.item = I.item_id " +
	            "AND ID.primary_unit = U.unitmaster_id " +
	            "AND d.departmentid = IB.department " +
	            "AND IB.org_id = :orgId " +
	            "ORDER BY doc_id, indent_basic_id, item_code")
	    Set<Object[]> getIndentNoBasedImport(
	            @Param("orgId") Long orgId,
	            @Param("type") String type);
	    
		@Query(nativeQuery = true, value = "select h.hsn,i.customer_part_no from indent_detail i1,hsn h,item i where i1.item=i.item_id and h.hsn_id=i.hsn_code and i.org_id=?1\r\n"
				+ "and i.branch=?2 and i.item_id=?3 and i.active=1 and i.cancel=0 and ?4='YES' group by h.hsn,i.customer_part_no")
		Set<Object[]> getHsnCodeDetails(Long orgId, Long branch, Long item,String type);
	    
}
