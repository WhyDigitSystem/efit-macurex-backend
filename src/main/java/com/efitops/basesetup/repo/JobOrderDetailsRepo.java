package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderDetailsVO;
import com.efitops.basesetup.entity.JobOrderVO;

@Repository
public interface JobOrderDetailsRepo extends JpaRepository<JobOrderDetailsVO, Long> {

	List<JobOrderDetailsVO> findByJobOrderVO(JobOrderVO jobOrderVO);

}
