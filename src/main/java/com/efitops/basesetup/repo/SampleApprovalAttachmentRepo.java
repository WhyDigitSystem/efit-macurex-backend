package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SampleApprovalAttachmentVO;
import com.efitops.basesetup.entity.SampleApprovalVO;

@Repository
public interface SampleApprovalAttachmentRepo extends JpaRepository<SampleApprovalAttachmentVO, Long> {

	List<SampleApprovalAttachmentVO> findBySampleApprovalVO(SampleApprovalVO sampleApprovalVO);

}
