package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderAmendmentDetailsVO;
import com.efitops.basesetup.entity.JobOrderAmendmentVO;

@Repository
public interface JobOrderAmendmentDetailsRepo extends JpaRepository<JobOrderAmendmentDetailsVO, Long>{

	List<JobOrderAmendmentDetailsVO> findByJobOrderAmendment(JobOrderAmendmentVO jobOrderAmendmentVO);

}
