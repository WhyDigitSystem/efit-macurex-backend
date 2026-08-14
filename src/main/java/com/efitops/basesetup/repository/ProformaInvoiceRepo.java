package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProformaInvoiceVO;

@Repository
public interface ProformaInvoiceRepo extends JpaRepository<ProformaInvoiceVO, Long> {

    @Query(nativeQuery = true, value = "select * from proforma_invoice_basic where proforma_invoice_basic_id=?1 and active=1 and cancel=0")
    ProformaInvoiceVO getProformaInvoiceById(Long id);

    @Query(nativeQuery = true, value = "select * from proforma_invoice_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<ProformaInvoiceVO> getProformaInvoiceByOrgId(Long orgId, Long branch);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,4,0)) AS docid from documenttypemapping_details where org_id=?1 and screen_code=?2")
    String getProformaInvoiceDocId(Long orgId, String screenCode);

    @Query(nativeQuery = true, value = "select a.rate,a.igst,a.sgst,a.cgst from gstratemaster a join hsn h on h.hsn_id=a.hsn_sac_code\r\n"
    		+ " where a.cancel=0 and h.hsn=?2 and a.org_id=?1")
    Set<Object[]> getTaxValue(Long orgId, Long hsn);

    @Query(nativeQuery = true, value = "SELECT\r\n"
    		+ "                i.item_id,\r\n"
    		+ "                i.item_code,\r\n"
    		+ "                i.item_description,\r\n"
    		+ "                u.unit_id,\r\n"
    		+ "                h.hsn,\r\n"
    		+ "                i.customer_part_no\r\n"
    		+ "            FROM item i\r\n"
    		+ "            INNER JOIN unitmaster u\r\n"
    		+ "                ON u.unitmaster_id = i.primary_unit\r\n"
    		+ "            INNER JOIN hsn h\r\n"
    		+ "                ON h.hsn_id = i.hsn_code\r\n"
    		+ "            WHERE i.cancel = 0\r\n"
    		+ "              AND i.org_id = ?1\r\n"
    		+ "              AND i.branch = ?2 group by  i.item_id,\r\n"
    		+ "                i.item_code,\r\n"
    		+ "                i.item_description,\r\n"
    		+ "                u.unit_id,\r\n"
    		+ "                h.hsn,\r\n"
    		+ "                i.customer_part_no order by i.item_id")
	Set<Object[]> getItemDetailsResponse(Long orgId, Long branch);

}