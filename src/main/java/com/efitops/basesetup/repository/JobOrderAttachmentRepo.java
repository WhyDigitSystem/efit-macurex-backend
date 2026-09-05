package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobOrderAttachmentVO;
import com.efitops.basesetup.entity.JobOrderVO;

@Repository
public interface JobOrderAttachmentRepo extends JpaRepository<JobOrderAttachmentVO, Long>{

	List<JobOrderAttachmentVO> findByJobOrderVO(JobOrderVO jobOrderVO);

}
