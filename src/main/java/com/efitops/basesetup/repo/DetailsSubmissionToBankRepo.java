package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;

@Repository
public interface DetailsSubmissionToBankRepo extends JpaRepository<DetailsSubmissionToBankVO, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM detailssubmissiontobank WHERE orgid=?1 and finyear=?2 and branchcode=?3")
	List<DetailsSubmissionToBankVO> getAllDetailsSubmissionToBankByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "SELECT * FROM detailssubmissiontobank WHERE detailssubmissiontobankid=?1")
	List<DetailsSubmissionToBankVO> getAllDetailsSubmissionToBankById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getBankDetailsDocId(Long orgId, String finYear, String branchCode, String screenCode);

	 @Query(
		        nativeQuery = true,
		        value =
		            "SELECT " +
		            " dsb.detailssubmissiontobankid, " +
		            " dsb.docid, " +
		            " dsb.docdate, " +
		            " dsb.invoiceno, " +
		            " dsb.invoicedate, " +
		            " dsb.branch, " +
		            " dsb.branchcode, " +
		            " dsb.finyear, " +
		            " dsb.narration, " +

		            " dsbd.documentname, " +
		            " dsbd.status " +

		            "FROM detailssubmissiontobank dsb " +
		            "LEFT JOIN detailssubmissiontobankdetails dsbd " +
		            " ON dsb.detailssubmissiontobankid = dsbd.detailssubmissiontobankid " +
		            "WHERE dsb.orgid = ?1 " +
		            "AND dsb.docdate BETWEEN ?2 AND ?3"
		    )
		    Set<Object[]> getDetailsSubmissionToBankReport(
		            Long orgId,
		            String fromDate,
		            String toDate
		    );
		}