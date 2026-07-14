package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DetailsSubmissionToBankDetailsVO;
import com.efitops.basesetup.entity.DetailsSubmissionToBankVO;
import com.efitops.basesetup.entity.JobOrderVO;

public interface DetailsSubmissionToBankDetailsRepo extends JpaRepository<DetailsSubmissionToBankDetailsVO, Long> {

	List<DetailsSubmissionToBankDetailsVO> findByDetailsSubmissionToBankVO(DetailsSubmissionToBankVO bankVO);

}
