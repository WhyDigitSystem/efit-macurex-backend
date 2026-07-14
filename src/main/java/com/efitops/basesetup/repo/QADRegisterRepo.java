package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QADRegisterVO;

@Repository
public interface QADRegisterRepo extends JpaRepository<QADRegisterVO, Long> {
	@Query(nativeQuery = true, value = "select * from qadregister where orgid=?1 and finyear=?2 and branchCode=?3")
	List<QADRegisterVO> getAllQADRegisterByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from qadregister where qadregisterid=?1")
	QADRegisterVO getQADRegisterById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getQADRegisterByDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "        h.docname,\r\n"
			+ "        h.docdate,\r\n"
			+ "        h.branch,\r\n"
			+ "        h.branchcode,\r\n"
			+ "        h.docformatno,\r\n"
			+ "        h.narration,\r\n"
			+ "        h.summary,\r\n"
			+ "        d.admendmentdetails,\r\n"
			+ "        d.document_no,\r\n"
			+ "        d.newdocissue,\r\n"
			+ "        d.newdocrev,\r\n"
			+ "        d.olddocissue,\r\n"
			+ "        d.olddocrev,\r\n"
			+ "        d.reasonforadmendment,\r\n"
			+ "        d.remarks,\r\n"
			+ "        d.reviewedby\r\n"
			+ "    FROM qadregister h\r\n"
			+ "    JOIN qadregisterdetails d\r\n"
			+ "      ON d.qadregisterid = h.qadregisterid\r\n"
			+ "    WHERE h.orgid = :orgId\r\n"
			+ "      AND (:docName IS NULL OR h.docname = :docName)\r\n"
			+ "    GROUP BY \r\n"
			+ "        h.docname,\r\n"
			+ "        h.docdate,\r\n"
			+ "        h.branch,\r\n"
			+ "        h.branchcode,\r\n"
			+ "        h.docformatno,\r\n"
			+ "        h.narration,\r\n"
			+ "        h.summary,\r\n"
			+ "        d.admendmentdetails,\r\n"
			+ "        d.document_no,\r\n"
			+ "        d.newdocissue,\r\n"
			+ "        d.newdocrev,\r\n"
			+ "        d.olddocissue,\r\n"
			+ "        d.olddocrev,\r\n"
			+ "        d.reasonforadmendment,\r\n"
			+ "        d.remarks,\r\n"
			+ "        d.reviewedby")
	Set<Object[]> getQADRegisterReport(Long orgId, String docName);

}
