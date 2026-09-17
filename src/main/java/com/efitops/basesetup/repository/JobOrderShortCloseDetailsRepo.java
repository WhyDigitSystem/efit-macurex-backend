package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderShortCloseDetailsVO;
import com.efitops.basesetup.entity.JobOrderShortCloseVO;

@Repository
public interface JobOrderShortCloseDetailsRepo extends JpaRepository<JobOrderShortCloseDetailsVO, Long>{


	List<JobOrderShortCloseDetailsVO> findByJobOrderShortCloseVO(JobOrderShortCloseVO jobOrderShortCloseVO);

}
