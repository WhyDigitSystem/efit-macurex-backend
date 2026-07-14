package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobWorkOutDetailsVO;
import com.efitops.basesetup.entity.JobWorkOutVO;

@Repository
public interface JobWorkOutDetailsRepo extends JpaRepository<JobWorkOutDetailsVO, Long> {

	List<JobWorkOutDetailsVO> findByJobWorkOutVO(JobWorkOutVO jobWorkOutVO);

}
